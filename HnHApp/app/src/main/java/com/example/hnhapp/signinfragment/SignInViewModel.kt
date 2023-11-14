package com.example.hnhapp.signinfragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hnhapp.data.requestModel.LoginResponse
import com.example.hnhapp.data.responseModel.ResponseState
import com.example.hnhapp.domain.usecase.LoginUseCase
import com.example.hnhapp.utils.getError
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

class SignInViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase
): ViewModel() {

    private val _loginLiveData = MutableLiveData<ResponseState<LoginResponse>>()
    val loginLiveData:LiveData<ResponseState<LoginResponse>> = _loginLiveData


    fun login(login:String, password:String){
        viewModelScope.launch {
            _loginLiveData.value = ResponseState.Loading()
            _loginLiveData.value = try {
                ResponseState.Success(
                    data = loginUseCase.execute(
                        login = login,
                        password = password
                    )
                )
            }catch (e:Exception){
                ResponseState.Error(
                    e = e,
                    message = e.getError()
                )
            }
        }
    }


}