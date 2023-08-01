package com.youarelaunched.challenge.di

import com.youarelaunched.challenge.data.network.api.ApiVendors
import com.youarelaunched.challenge.fakes.FakeApiVendors
import dagger.Binds
import dagger.Module
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.testing.TestInstallIn

@Module
@TestInstallIn(
    components = [ViewModelComponent::class],
    replaces = [ApiModule::class]
)
interface FakeApiModule {

    @Binds
    fun bindApi(fakeApiVendors: FakeApiVendors): ApiVendors
}