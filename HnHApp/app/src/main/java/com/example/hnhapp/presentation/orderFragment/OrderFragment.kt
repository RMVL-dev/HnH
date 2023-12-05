package com.example.hnhapp.presentation.orderFragment

import android.app.DatePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.doOnAttach
import androidx.fragment.app.createViewModelLazy
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.ui.setupWithNavController
import com.bumptech.glide.Glide
import com.example.hnhapp.R
import com.example.hnhapp.data.order.response.OrderResponse
import com.example.hnhapp.data.responseModel.ResponseState
import com.example.hnhapp.databinding.FragmentOrderBinding
import com.example.hnhapp.presentation.contracts.MapActivityContract
import com.example.hnhapp.utils.formatDate
import com.example.hnhapp.utils.getFormattedCurrency
import com.example.hnhapp.utils.settingSnackBar
import dagger.android.support.AndroidSupportInjection
import java.text.SimpleDateFormat
import java.util.Calendar
import javax.inject.Inject

class OrderFragment : Fragment() {

    private var _binding:FragmentOrderBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var orderViewModelFactory: ViewModelProvider.Factory

    private val orderViewModel:OrderViewModel by createViewModelLazy(
        OrderViewModel::class,
        {this.viewModelStore},
        factoryProducer = {orderViewModelFactory}
    )

    /**
     * Принятие продукта в формате json
     */
    private val arg:OrderFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidSupportInjection.inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOrderBinding.inflate(inflater, container,false)
        binding.etHouseAddress.doOnAttach {
            it.requestFocus()
        }
        initToolBar()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //конвертирование товара в нормальный вид
        orderViewModel.convertProduct(product = arg.product)

        orderViewModel.product.observe(viewLifecycleOwner){product ->
            Glide.with(binding.orderItemImage)
                .load(product.previewImageUrl)
                .error(R.drawable.error_image)
                .into(binding.orderItemImage)
            binding.orderItemTitle.text = "${product.sizes[0].value} • ${product.title}"
            binding.orderItemDepartment.text = product.department
            binding.btBuyNow.text = "Купить за ${getFormattedCurrency(product.price)}"
        }

        setHouseOnClickListeners()
        initCounter()
        addDateClickListener()
        setOnOrderClickListener()

        orderViewModel.orderResponseState.observe(viewLifecycleOwner){responseState ->
            when(responseState){
                is ResponseState.Error -> {
                    errorState(responseState.message)
                }
                is ResponseState.Loading -> {
                    binding.orderNowButton.loading()
                }
                is ResponseState.Success -> {
                    successState(orderResponse = responseState.data)
                }
            }
        }
    }

    private fun successState(orderResponse: OrderResponse?){
        orderResponse?.let {order ->
            val date = formatDate(
                date = order.createdDelivery,
                newPattern = resources.getString(R.string.snackbar_pattern),
                oldPattern = resources.getString(R.string.order_pattern)
            )
            val message = String.format(resources.getString(R.string.success_order),order.number, date)
            view?.settingSnackBar(
                message = message,
                colorId = R.color.smalt
            )?.show()
        }
        binding.orderNowButton.otherStates(
            buttonText = resources.getString(R.string.success_order_button_text)
        )
    }

    private fun errorState(message:String?){
        message?.let {
            view?.settingSnackBar(
                message = it,
                colorId = R.color.error_sign_in
            )?.show()
        }
        binding.orderNowButton.otherStates(
            buttonText = resources.getString(R.string.try_again)
        )
    }

    /**
     * слушатель нажатий на кнопку
     */
    private fun setOnOrderClickListener(){
        binding.btBuyNow.setOnClickListener {

            if (binding.etHouseAddress.text.isNullOrEmpty() ||
                binding.etApartmentsNumber.text.isNullOrEmpty() ||
                binding.etOrderDate.text.isNullOrEmpty()
                ){
                view?.settingSnackBar(
                    message = "Не все поля заполнены",
                    colorId = R.color.error_sign_in
                )?.show()
            }else{
                orderViewModel.checkToOrder(
                    house = binding.etHouseAddress.text.toString(),
                    apartment = binding.etApartmentsNumber.text.toString()
                )
            }
        }
    }


    /**
     * Установка слушателей нажаний на инкремент и декремент и отображение значения
     */
    private fun initCounter(){
        //каунтер для кол-ва товаров
        orderViewModel.counterOrderItems.observe(viewLifecycleOwner){counter ->
            binding.counter.setCountedValue(counter = counter)
        }

        binding.counter.setIncrease { orderViewModel.increaseOrderCounter() }
        binding.counter.setDecrease { orderViewModel.decreaseOrderCounter() }
    }

    /**
     * Создание слушателей нажаний на TextInputLayout выбора дома
     */
    private fun setHouseOnClickListeners(){
        //создание запускателя активити с контрактом
        val activityLauncher = registerForActivityResult(MapActivityContract()){result ->
            binding.etHouseAddress.setText(result)
        }
        binding.tilHouseAddress.setEndIconOnClickListener {
            activityLauncher.launch("What is address?")
            binding.etApartmentsNumber.requestFocus()
        }
        binding.etHouseAddress.setOnClickListener {
            activityLauncher.launch("What is address?")
            binding.etApartmentsNumber.requestFocus()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    /**
     * Создание слушателей нажатия на TextInputLayout Даты
     */
    private fun addDateClickListener(){
        binding.etOrderDate.setOnClickListener {
            createDialog()
            binding.etApartmentsNumber.clearFocus()
        }
        binding.tilOrderDate.setEndIconOnClickListener {
            createDialog()
            binding.etApartmentsNumber.clearFocus()
        }
    }

    /**
     * Инициализация тулбара
     */
    private fun initToolBar(){
        binding.orderToolbar
            .setupWithNavController(
                findNavController()
            )
        binding.orderToolbar.title = getString(R.string.order_toolbar_title)
    }

    /**
     * Создание диалогового окна для даты доставки
     */
    private fun createDialog(){
        val datePicker = DatePicker(
            context = requireContext(),
            getListener()
        )
        datePicker.createDialog().show()
    }

    /**
     * Создание слушателя нажатий на диалоговое окно
     */
    private fun getListener() =
        DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
            val calendarBox = Calendar.getInstance()
            calendarBox.set(Calendar.YEAR, year)
            calendarBox.set(Calendar.MONTH, month)
            calendarBox.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            updateDate(calendar = calendarBox)
            orderViewModel.setCalendar(calendar = calendarBox)
        }

    /**
     * Обновление поля ввода
     */
    private fun updateDate(calendar: Calendar){
        binding.etOrderDate.setText(formatDate(calendar = calendar, pattern = "dd MMMM"))
    }
}