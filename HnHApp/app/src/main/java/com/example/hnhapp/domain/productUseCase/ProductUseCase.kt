package com.example.hnhapp.domain.productUseCase

import com.example.hnhapp.data.productResponse.Product
import com.example.hnhapp.data.repository.HnHRepositoryImpl
import javax.inject.Inject

class ProductUseCase @Inject constructor(
    private val repository: HnHRepositoryImpl
) {

    suspend fun getProductList(): List<Product> =
        repository.getProductList()

}