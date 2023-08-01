package com.youarelaunched.challenge

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onLast
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performImeAction
import androidx.compose.ui.test.performScrollToIndex
import androidx.compose.ui.test.performTextInput
import com.youarelaunched.challenge.fakes.FakeApiVendors
import com.youarelaunched.challenge.middle.R
import com.youarelaunched.challenge.ui.screen.view.VENDORS_LIST_TEST_TAG
import com.youarelaunched.challenge.ui.screen.view.components.SEARCH_FIELD_TEST_TAG
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class VendorsScreenIntegrationTests {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun vendorsList_onVendorsAvailable_allVendorsShown() {
        FakeApiVendors.mockVendors.forEachIndexed { index, vendor ->
            composeTestRule.onNodeWithTag(VENDORS_LIST_TEST_TAG)
                .performScrollToIndex(index)
            composeTestRule.onNodeWithText(vendor.companyName)
                .assertExists()
        }
    }

    @Test
    fun vendorsList_onSearched_filteredVendorsShown() {
        composeTestRule.onNodeWithTag(SEARCH_FIELD_TEST_TAG)
            .apply {
                performTextInput(FakeApiVendors.mockVendors[0].companyName)
                performImeAction()
            }
        composeTestRule.waitForIdle()
        composeTestRule.onAllNodesWithText(FakeApiVendors.mockVendors[0].companyName)
            .onLast() // First item with company name is the search field, so grab the last one
            .assertExists()


        FakeApiVendors.mockVendors.drop(1).forEach { vendor ->
            composeTestRule.onNodeWithText(vendor.companyName)
                .assertDoesNotExist()
        }
    }

    @Test
    fun vendorsList_onVendorsListEmpty_displayPlaceholder() {
        composeTestRule.onNodeWithTag(SEARCH_FIELD_TEST_TAG)
            .apply {
                performTextInput("NON_EXISTENT_COMPANY")
                performImeAction()
            }
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithText(
            composeTestRule.activity.resources.getString(R.string.sorry_no_results_found)
        ).assertExists()
        composeTestRule.onNodeWithText(
            composeTestRule.activity.resources.getString(R.string.no_results_description)
        ).assertExists()
    }
}