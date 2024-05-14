package com.mshdabiola.ui

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.res.loadImageBitmap
import androidx.compose.ui.unit.dp
import com.mshdabiola.designsystem.string.appName
import java.io.File
import java.nio.file.Paths
import kotlin.io.path.exists
import kotlin.io.path.inputStream

@Composable
actual fun SplashScreen(modifier: Modifier) {

    //
    val path = "/home/mshdabiola/StudioProjects/Hydraulic/output/app/icon-square-1024.png"


    val appIcon = remember {
        System.getProperty("app.dir")
            ?.let { Paths.get(it, "icon-square-1024.png") }
            ?.takeIf { it.exists() }
            ?.inputStream()
            ?.buffered()
            ?.use { BitmapPainter(loadImageBitmap(it)) }
            ?: BitmapPainter(loadImageBitmap(File(path).inputStream().buffered()))

    }

    Surface(
        modifier = modifier,
        color = MaterialTheme.colorScheme.primary,
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Image(
                modifier = Modifier.sizeIn(maxWidth = 200.dp, maxHeight = 200.dp),
                painter = appIcon, contentDescription = "app icon",
            )
            Spacer(Modifier.height(32.dp))
            Text(text = appName, style = MaterialTheme.typography.headlineSmall)


        }

    }


}

@Preview
@Composable
fun SplashPreview() {
    SplashScreen()
}