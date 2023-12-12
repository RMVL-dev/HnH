package com.example.hnhapp.domain.orderUseCase

import com.example.hnhapp.data.order.request.OrderRequest
import com.example.hnhapp.data.order.response.OrderResponse
import com.example.hnhapp.data.repository.HnHRepositoryImpl
import javax.inject.Inject

class OrderUseCase @Inject constructor(
    private val repository: HnHRepositoryImpl
) {

    suspend fun order(orderRequest: OrderRequest):OrderResponse =
        repository.order(orderRequest = orderRequest)
}