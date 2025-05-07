/*
 *abiola 2024
 */

package com.mshdabiola.designsystem.components

import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.mshdabiola.designsystem.component.HyaBackground
import com.mshdabiola.designsystem.component.HyaButton
import com.mshdabiola.designsystem.icon.HyaIcons
import com.mshdabiola.designsystem.theme.HyaTheme

@ThemePreviews
@Composable
fun ButtonPreview() {
    HyaTheme {
        HyaBackground(modifier = Modifier.size(150.dp, 50.dp)) {
            HyaButton(onClick = {}, text = { Text("Test button") })
        }
    }
}

@ThemePreviews
@Composable
fun ButtonPreview2() {
    HyaTheme {
        HyaBackground(modifier = Modifier.size(150.dp, 50.dp)) {
            HyaButton(onClick = {}, text = { Text("Test button") })
        }
    }
}

@ThemePreviews
@Composable
fun ButtonLeadingIconPreview() {
    HyaTheme {
        HyaBackground(modifier = Modifier.size(150.dp, 50.dp)) {
            HyaButton(
                onClick = {},
                text = { Text("Test button") },
                leadingIcon = { Icon(imageVector = HyaIcons.Add, contentDescription = null) },
            )
        }
    }
}
