/*
 *abiola 2022
 */

package com.mshdabiola.designsystem.components

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.mshdabiola.designsystem.component.HyaTab
import com.mshdabiola.designsystem.component.HyaTabRow
import com.mshdabiola.designsystem.theme.HyaTheme

@ThemePreviews
@Composable
fun TabsPreview() {
    HyaTheme {
        val titles = listOf("Topics", "People")
        HyaTabRow(selectedTabIndex = 0) {
            titles.forEachIndexed { index, title ->
                HyaTab(
                    selected = index == 0,
                    onClick = { },
                    text = { Text(text = title) },
                )
            }
        }
    }
}

object SkTabDefaults {
    val TabTopPadding = 7.dp
}
