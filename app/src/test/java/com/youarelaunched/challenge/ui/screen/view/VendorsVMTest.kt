package com.youarelaunched.challenge.ui.screen.view

import com.youarelaunched.challenge.data.repository.VendorsRepository
import com.youarelaunched.challenge.data.repository.model.Vendor
import com.youarelaunched.challenge.ui.screen.view.utils.MainDispatcherRule
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.io.IOException
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue

class VendorsVMTest {
    @get:Rule
    val mainCoroutineRule = MainDispatcherRule()

    private lateinit var mockRepository: VendorsRepository
    private lateinit var errorRepository: VendorsRepository

    @Before
    fun setup() {
        mockRepository = object : VendorsRepository {
            override suspend fun getVendors(): List<Vendor> {
                return MOCK_VENDORS
            }

            override suspend fun getVendors(query: String): List<Vendor> {
                return MOCK_VENDORS.filter { it.companyName.contains(query, true) }
            }
        }

        errorRepository = object : VendorsRepository {
            override suspend fun getVendors(): List<Vendor> {
                throw IOException()
            }

            override suspend fun getVendors(query: String): List<Vendor> {
                throw IOException()
            }
        }
    }

    @Test
    fun uiState_whenInitialized_allVendorsDisplayed() = runTest {
        val vendorsVM = VendorsVM(mockRepository)

        val collectJob = launch(UnconfinedTestDispatcher()) {
            vendorsVM.uiState.collect()
        }

        val vendorsList = vendorsVM.uiState.first().vendors

        assertNotNull(vendorsList)
        assertEquals(MOCK_VENDORS.size, vendorsList.size)

        collectJob.cancel()
    }

    @Test
    fun uiState_whenInitialized_searchQueryEmpty() = runTest {
        val vendorsVM = VendorsVM(mockRepository)

        val collectJob = launch(UnconfinedTestDispatcher()) {
            vendorsVM.uiState.collect()
        }

        val searchQuery = vendorsVM.uiState.first().searchQuery

        assertTrue(searchQuery.isEmpty())

        collectJob.cancel()
    }

    @Test
    fun uiState_whenFilterQueryEntered_vendorsListFilters() = runTest {
        val vendorsVM = VendorsVM(mockRepository)

        val collectJob = launch(UnconfinedTestDispatcher()) {
            vendorsVM.uiState.collect()
        }

        vendorsVM.onSearchQueryChange(MOCK_VENDORS[0].companyName)
        vendorsVM.onSearchAction()

        val vendorsList = vendorsVM.uiState.first().vendors

        assertNotNull(vendorsList)
        assertEquals(1, vendorsList.size)
        assertEquals(
            MOCK_VENDORS[0],
            vendorsList[0]
        )

        collectJob.cancel()
    }

    @Test
    fun uiState_whenErrorOccuredWhileLoadingVendors_vendorsListIsNull() = runTest {
        val vendorsVM = VendorsVM(errorRepository)

        val collectJob = launch(UnconfinedTestDispatcher()) {
            vendorsVM.uiState.collect()
        }

        val vendorsList = vendorsVM.uiState.first().vendors

        assertNull(vendorsList)

        collectJob.cancel()
    }

}

private val MOCK_VENDORS = listOf(
    Vendor(
        id = 7800,
        companyName = "Adrienne Ortega",
        coverPhoto = "doming",
        area = "pro",
        favorite = false,
        categories = listOf(),
        tags = null
    ),
    Vendor(
        id = 9448,
        companyName = "Lamar Rasmussen",
        coverPhoto = "eum",
        area = "per",
        favorite = false,
        categories = listOf(),
        tags = null
    ),
    Vendor(
        id = 5072,
        companyName = "Leland May",
        coverPhoto = "dapibus",
        area = "tristique",
        favorite = false,
        categories = listOf(),
        tags = null
    )
)