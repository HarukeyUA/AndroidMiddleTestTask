package com.youarelaunched.challenge.ui.screen.view.components

import androidx.compose.foundation.layout.Arrangement.Absolute.spacedBy
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.youarelaunched.challenge.middle.R
import com.youarelaunched.challenge.ui.theme.VendorAppTheme

@Composable
fun NoResultsPlaceholder(
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = spacedBy(8.dp),
        modifier = modifier.fillMaxWidth()
    ) {
        Text(
            text = stringResource(R.string.sorry_no_results_found),
            style = VendorAppTheme.typography.h2,
            color = VendorAppTheme.colors.textAccented,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
        Text(
            text = stringResource(R.string.no_results_description),
            style = VendorAppTheme.typography.subtitle2,
            color = VendorAppTheme.colors.textDark,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )

    }
}
