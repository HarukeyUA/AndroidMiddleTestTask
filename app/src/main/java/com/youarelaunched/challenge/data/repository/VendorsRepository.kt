package com.youarelaunched.challenge.data.repository

import com.youarelaunched.challenge.data.repository.model.Vendor

interface VendorsRepository {

    suspend fun getVendors(): List<Vendor>
    suspend fun getVendors(query: String): List<Vendor>
}