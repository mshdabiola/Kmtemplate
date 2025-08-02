package com.mshdabiola.setting.detailscreen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.mshdabiola.designsystem.drawable.KmtIcons
import com.mshdabiola.designsystem.strings.KmtStrings
import org.jetbrains.compose.ui.tooling.preview.Preview

object LanguageScreenTestTags {
    const val LANGUAGE_LIST = "languageList"
    fun languageItem(languageCode: String) = "languageItem_$languageCode"
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LanguageScreen(
    currentLanguageCode: String = "en-US",
    onLanguageSelected: (languageCode: String) -> Unit,
    modifier: Modifier = Modifier,
) {
    val supportedLanguages = KmtStrings.supportedLanguage
    LazyColumn(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 8.dp)
            .testTag(LanguageScreenTestTags.LANGUAGE_LIST),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        items(supportedLanguages, key = { it.second }) { language ->
            LanguageItem(
                languageName = language.first,
                languageCode = language.second,
                isSelected = language.second == currentLanguageCode,
                onClick = { onLanguageSelected(language.second) },
            )
        }
    }
}

@Preview
@Composable
fun LanguageScreenPreview() {
    LanguageScreen(
        currentLanguageCode = "en-US",
        onLanguageSelected = {}
    )
}

@Composable
private fun LanguageItem(
    languageName: String,
    languageCode: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .testTag(LanguageScreenTestTags.languageItem(languageCode)),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
    ) {

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(16.dp).fillMaxWidth(),
            ) {
                Text(
                    text = languageName,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier
                        .weight(1f)
                )
                Spacer(modifier = Modifier.width(8.dp))
                if (isSelected) {
                    Icon(
                        imageVector = KmtIcons.Check,
                        contentDescription = "Selected language", // stringResource
                        tint = MaterialTheme.colorScheme.primary,
                    )
                }
            }

    }
}
