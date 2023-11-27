package com.example.hnhapp.custom.views

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import com.bumptech.glide.Glide
import com.example.hnhapp.R
import com.example.hnhapp.data.productResponse.Product
import com.example.hnhapp.databinding.PicturesBinding
import com.example.hnhapp.presentation.productItemFragment.adapters.ProductItemPicturesAdapter
import java.text.ParsePosition

class ProductPicture @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : ConstraintLayout(context, attrs) {

    private var _binding:PicturesBinding?
    val binding get() = _binding!!

    private lateinit var product:Product

    private var currentItem:Int = 0

    private var listOfImages:MutableList<String> = emptyList<String>().toMutableList()

    private var listOfImageViews: MutableList<ImageView> = emptyList<ImageView>().toMutableList()

    init {
        _binding = PicturesBinding.bind(
            LayoutInflater.from(context).inflate(R.layout.pictures,this, true)
        )
    }

    /**
     * Метод проверяющий кол-во картинок, если их меньше трех то добавляет пустые
     */
    private fun setListPictures(){
        listOfImages = product.images.toMutableList()
        while (listOfImages.size<3){
            listOfImages.add("")
        }
        repeat(listOfImages.size){position ->
            setImage(position = position)
        }
    }

    /**
     * метод для создания листа биндингов, для удобства отрисовки маленьких картинок
     */
    private fun setListOfImageViews(){
        listOfImageViews.add(binding.firstPic)
        listOfImageViews.add(binding.secondPic)
        listOfImageViews.add(binding.thirdPic)
    }

    /**
     * главный метод класса, который создает лист биндингов, картинок продукта и инициализирует пейджер
     */
    fun setProduct(product: Product){
        this.product = product
        setListOfImageViews()
        setListPictures()
        initAdapter()
    }

    /**
     * Задание адаптера для вью пейджера
     */
    private fun initAdapter(){
        val adapter = ProductItemPicturesAdapter(listOfPictures = listOfImages)
        binding.productImages.adapter = adapter

    }

    /**
     * Установка каждого маленького изобрадения
     */
    private fun setImage(position: Int){
        Glide.with(listOfImageViews[position])
            .load(listOfImages[position])
            .error(R.drawable.error_image)
            .into(listOfImageViews[position])
    }

    /**
     *  Метод для управления главным изображением
     */
    fun onClick(){
        binding.firstPic.setOnClickListener {
            binding.productImages.currentItem = 0
        }
        binding.secondPic.setOnClickListener {
            binding.productImages.currentItem = 1
        }
        binding.thirdPic.setOnClickListener {
            binding.productImages.currentItem = 2
        }

    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        _binding = null
    }

}