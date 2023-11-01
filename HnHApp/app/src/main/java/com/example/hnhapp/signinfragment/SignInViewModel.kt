package com.example.hnhapp.signinfragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SignInViewModel: ViewModel() {

    private val _someVariable = MutableLiveData<String>()
    val someVariable:LiveData<String> = _someVariable

    init {
        _someVariable.value = "Hello its init text"
    }

    fun changeSomeVariable(text:String){
        _someVariable.value = text
    }

}