package com.example.hnhapp.data.responseModel

import java.lang.Exception

sealed interface ResponseState<T>{

    data class Success <T>(val data:T):ResponseState<T>

    data class Error <T> (val e:Exception, val error:String):ResponseState<T>

    class Loading<T>(): ResponseState<T>

}