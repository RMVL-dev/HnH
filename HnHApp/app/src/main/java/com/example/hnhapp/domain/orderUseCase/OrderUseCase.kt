package com.example.hnhapp.domain.orderUseCase

import com.example.hnhapp.data.repository.HnHRepositoryImpl
import javax.inject.Inject

class OrderUseCase @Inject constructor(
    private val repository: HnHRepositoryImpl
) {

}