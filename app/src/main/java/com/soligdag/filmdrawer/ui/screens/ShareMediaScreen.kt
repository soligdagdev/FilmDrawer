package com.soligdag.filmdrawer.ui.screens

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.soligdag.filmdrawer.ui.components.LoadingDialog
import com.soligdag.filmdrawer.ui.theme.FilmDrawerTheme
import com.soligdag.filmdrawer.ui.viewmodels.ShareMediaViewModel

@Composable
fun ShareMediaScreen(
    viewModel: ShareMediaViewModel =
        hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current
    if (uiState.isLoading) {
        LoadingDialog()
    }
    if (uiState.showNativeShareDialog) {
        showShareDialogue(url = uiState.externalShareURL, context = context)
        viewModel.resetState()
    }

    ShareMediaContent(onShareBtnClicked = { viewModel.getExternalLinkToShare(context) })
}


@Composable
private fun ShareMediaContent(onShareBtnClicked: () -> Unit = {}) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.background)
    ) {
        Button(modifier = Modifier.padding(100.dp), onClick = {
            onShareBtnClicked()
        }) {
            Text(text = "Share externally")
        }
    }
}


fun showShareDialogue(url: String, context: Context) {
    val sendIntent: Intent = Intent().apply {
        action = Intent.ACTION_SEND
        putExtra(Intent.EXTRA_TEXT, "This is my text to send \n$url")
        type = "text/plain"
    }
    val shareIntent = Intent.createChooser(sendIntent, null)
    context.startActivity(shareIntent)
}

@Preview
@Composable
fun SharePreview() {
    FilmDrawerTheme {
        ShareMediaContent()
    }
}