package com.mokhnatkinkirill.hotellist.details.presentation.composable.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import com.mokhnatkinkirill.hotellist.R
import com.mokhnatkinkirill.hotellist.details.presentation.composable.design.Dimen
import com.mokhnatkinkirill.hotellist.details.presentation.composable.getColorFromAttr

@Composable
fun ErrorScreen(message: String, onRetry: () -> Unit) {
    val context = LocalContext.current
    val textColorMain = getColorFromAttr(R.attr.textColorMain, context)
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(Dimen.distanceMedium)
        ) {
            Text(
                text = message,
                color = textColorMain,
                style = MaterialTheme.typography.bodyLarge,
            )
            Spacer(modifier = Modifier.height(Dimen.distanceMedium))
            Button(onClick = onRetry) {
                Text(
                    text = stringResource(R.string.retry)
                )
            }
        }
    }
}