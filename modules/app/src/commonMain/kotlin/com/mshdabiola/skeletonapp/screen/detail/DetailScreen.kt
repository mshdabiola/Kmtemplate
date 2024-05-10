package com.mshdabiola.skeletonapp.screen.detail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.mshdabiola.mvvn.KoinCommonViewModel
import com.mshdabiola.skeletonapp.ui.MyCard

@Composable
fun DetailScreenn(windowSizeClass: WindowSizeClass, onBack: () -> Unit) {
    val viewModel: DetailViewModel = KoinCommonViewModel()
    DetailScreen(windowSizeClass, back = onBack)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun DetailScreen(
    windowSizeClass: WindowSizeClass,
    back: () -> Unit = {},
) {
    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = back) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "back")
                    }
                },
                title = {
                    Text(text = "Detail")
                },
            )
        },
    ) { paddingValues ->
        Column(Modifier.padding(paddingValues)) {
            MyCard()
        }
    }
}

@Composable
expect fun DetailScreenPreview()
