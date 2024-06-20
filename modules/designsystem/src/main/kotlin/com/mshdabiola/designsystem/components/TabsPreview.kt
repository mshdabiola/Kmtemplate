/*
 *abiola 2022
 */

package com.mshdabiola.designsystem.components

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.mshdabiola.designsystem.component.SkTab
import com.mshdabiola.designsystem.component.SkTabRow
import com.mshdabiola.designsystem.theme.SkTheme


@ThemePreviews
@Composable
fun TabsPreview() {
    SkTheme {
        val titles = listOf("Topics", "People")
        SkTabRow(selectedTabIndex = 0) {
            titles.forEachIndexed { index, title ->
                SkTab(
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
