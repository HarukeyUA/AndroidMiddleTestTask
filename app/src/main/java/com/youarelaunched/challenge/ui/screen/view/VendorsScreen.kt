package com.youarelaunched.challenge.ui.screen.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import com.youarelaunched.challenge.ui.screen.state.VendorsScreenUiState
import com.youarelaunched.challenge.ui.screen.view.components.ChatsumerSnackbar
import com.youarelaunched.challenge.ui.screen.view.components.NoResultsPlaceholder
import com.youarelaunched.challenge.ui.screen.view.components.SearchField
import com.youarelaunched.challenge.ui.screen.view.components.VendorItem
import com.youarelaunched.challenge.ui.theme.VendorAppTheme

const val VENDORS_LIST_TEST_TAG = "VENDORS_LIST"

@Composable
fun VendorsRoute(
    viewModel: VendorsVM
) {
    val uiState by viewModel.uiState.collectAsState()

    VendorsScreen(
        uiState = uiState,
        onSearchAction = viewModel::onSearchAction,
        onSearchQueryChanged = viewModel::onSearchQueryChange
    )
}

@Composable
fun VendorsScreen(
    uiState: VendorsScreenUiState,
    onSearchQueryChanged: (String) -> Unit,
    onSearchAction: () -> Unit
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        backgroundColor = VendorAppTheme.colors.background,
        snackbarHost = { ChatsumerSnackbar(it) },
        topBar = {
            SearchField(
                value = uiState.searchQuery,
                onValueChange = onSearchQueryChanged,
                onSearch = onSearchAction,
                modifier = Modifier
                    .padding(top = 24.dp, start = 16.dp, end = 16.dp)
            )
        }
    ) { paddings ->
        Box(
            modifier = Modifier
                .padding(paddings)
                .fillMaxSize()
        ) {
            if (!uiState.vendors.isNullOrEmpty()) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .testTag(VENDORS_LIST_TEST_TAG),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    contentPadding = PaddingValues(
                        vertical = 24.dp,
                        horizontal = 16.dp
                    )
                ) {
                    items(uiState.vendors) { vendor ->
                        VendorItem(
                            vendor = vendor
                        )
                    }

                }
            } else if (uiState.vendors?.isEmpty() == true) {
                NoResultsPlaceholder(
                    modifier = Modifier
                        .padding(16.dp)
                        .align(Alignment.Center)
                )
            }
        }

    }
}