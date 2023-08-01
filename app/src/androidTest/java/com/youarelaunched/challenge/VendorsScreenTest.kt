package com.youarelaunched.challenge

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performScrollToIndex
import com.youarelaunched.challenge.data.repository.model.Vendor
import com.youarelaunched.challenge.middle.R
import com.youarelaunched.challenge.ui.screen.state.VendorsScreenUiState
import com.youarelaunched.challenge.ui.screen.view.VENDORS_LIST_TEST_TAG
import com.youarelaunched.challenge.ui.screen.view.VendorsScreen
import com.youarelaunched.challenge.ui.theme.VendorAppTheme
import org.junit.Rule
import org.junit.Test

class VendorsScreenTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun vendorsList_onVendorsAvailable_allVendorsShown() {
        val uiState = VendorsScreenUiState(
            MOCK_VENDORS,
            ""
        )

        composeTestRule.setContent {
            VendorAppTheme {
                VendorsScreen(uiState = uiState, onSearchQueryChanged = {}, onSearchAction = {})
            }
        }

        MOCK_VENDORS.forEachIndexed { index, vendor ->
            composeTestRule.onNodeWithTag(VENDORS_LIST_TEST_TAG)
                .performScrollToIndex(index)

            composeTestRule.onNodeWithText(vendor.companyName)
                .assertExists()
        }
    }

    @Test
    fun vendorsList_onVendorsListEmpty_displayPlaceholder() {
        val uiState = VendorsScreenUiState(
            listOf(),
            ""
        )

        composeTestRule.setContent {
            VendorAppTheme {
                VendorsScreen(uiState = uiState, onSearchQueryChanged = {}, onSearchAction = {})
            }
        }

        composeTestRule.onNodeWithText(
            composeTestRule.activity.resources.getString(R.string.sorry_no_results_found)
        ).assertExists()
        composeTestRule.onNodeWithText(
            composeTestRule.activity.resources.getString(R.string.no_results_description)
        ).assertExists()
    }
}

private val MOCK_VENDORS = listOf(
    Vendor(
        id = 4734,
        companyName = "Morgan Patrick",
        coverPhoto = "nostrum",
        area = "lacinia",
        favorite = false,
        categories = listOf(),
        tags = null
    ),
    Vendor(
        id = 4072,
        companyName = "Reed Sharp",
        coverPhoto = "ex",
        area = "nibh",
        favorite = false,
        categories = listOf(),
        tags = null
    ),
    Vendor(
        id = 2491,
        companyName = "Rosemarie Booker",
        coverPhoto = "montes",
        area = "conceptam",
        favorite = false,
        categories = listOf(),
        tags = null
    ),
    Vendor(
        id = 7481,
        companyName = "Lavonne Greene",
        coverPhoto = "luctus",
        area = "eum",
        favorite = false,
        categories = listOf(),
        tags = null
    ),
    Vendor(
        id = 9440,
        companyName = "Whitney Wilkerson",
        coverPhoto = "phasellus",
        area = "bibendum",
        favorite = false,
        categories = listOf(),
        tags = null
    ),
    Vendor(
        id = 7358,
        companyName = "Cleveland Hinton",
        coverPhoto = "sodales",
        area = "mus",
        favorite = false,
        categories = listOf(),
        tags = null
    )
)