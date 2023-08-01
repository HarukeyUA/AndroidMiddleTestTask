package com.youarelaunched.challenge.ui.screen.view

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.youarelaunched.challenge.data.repository.VendorsRepository
import com.youarelaunched.challenge.data.repository.model.Vendor
import com.youarelaunched.challenge.ui.screen.state.VendorsScreenUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VendorsVM @Inject constructor(
    private val repository: VendorsRepository
) : ViewModel() {

    private val _searchQuery = MutableStateFlow("")
    private val _vendorList = MutableStateFlow<List<Vendor>?>(null)

    private var searchJob: Job? = null

    val uiState = combine(
        _vendorList,
        _searchQuery,
        ::VendorsScreenUiState
    ).stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5_000),
        VendorsScreenUiState(
            vendors = null,
            searchQuery = ""
        )
    )

    init {
        getVendors()
        collectQueryChanges()
    }

    private fun collectQueryChanges() {
        viewModelScope.launch {
            _searchQuery.debounce(DEBOUNCE_TIME).collectLatest {
                if (it.isEmpty() || it.length >= MIN_SEARCH_QUERY_LENGTH) {
                    getVendors(it)
                }
            }
        }
    }

    fun getVendors(query: String = "") {
        searchJob?.cancel()

        searchJob = viewModelScope.launch {
            _vendorList.value = if (query.isBlank()) {
                repository.getVendors()
            } else {
                repository.getVendors(query.trim())
            }
        }
    }

    fun onSearchQueryChange(value: String) {
        _searchQuery.value = value
    }

    fun onSearchAction() {
        getVendors(_searchQuery.value)
    }

    private companion object {
        const val MIN_SEARCH_QUERY_LENGTH = 3
        const val DEBOUNCE_TIME = 500L
    }

}