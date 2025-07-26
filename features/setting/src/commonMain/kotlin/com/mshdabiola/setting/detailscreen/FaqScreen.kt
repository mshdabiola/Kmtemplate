/*
 * Designed and developed by 2024 mshdabiola (lawal abiola)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.mshdabiola.setting.detailscreen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.ui.tooling.preview.Preview

data class FaqItem(
    val id: Int,
    val question: String,
    val answer: String,
)

@Composable
fun FaqScreen(
    modifier: Modifier = Modifier,
) {
    val questions = listOf(
        FaqItem(
            id = 1,
            question = "What is Kotlin Multiplatform (KMP)?",
            answer = "Kotlin Multiplatform allows you to share code " +
                "(like business logic, data layers, and more) across different platforms such as" +
                " Android, iOS, Web, Desktop, and Server-side, all while writing platform-specific " +
                "code only where necessary (e.g., for UI or platform-specific APIs).",
        ),
        FaqItem(
            id = 2,
            question = "How does this template help with KMP development?",
            answer = "This template provides a pre-configured project structure" +
                " with shared modules, platform-specific modules, and examples of how to implement" +
                " common patterns like shared ViewModels, data repositories, and Compose Multiplatform for UI.",
        ),
        FaqItem(
            id = 3,
            question = "Can I share UI code with KMP?",
            answer = "Yes, with Compose Multiplatform, you can write your UI" +
                " once using Jetpack Compose and deploy it on Android, Desktop (Windows, macOS, Linux)," +
                " iOS (Alpha), and Web (Experimental). This template utilizes Compose Multiplatform.",
        ),
        FaqItem(
            id = 4,
            question = "What are the benefits of using KMP?",
            answer = "Key benefits include: \n" +
                "- Code Reuse: Write common logic once and share it.\n" +
                "- Consistency: Ensure consistent behavior across platforms.\n" +
                "- Faster Development: Reduce redundant work.\n" +
                "- Native Performance: Shared Kotlin code compiles to native code for each platform.\n" +
                "- Flexibility: Choose how much code to share.",
        ),
        FaqItem(
            id = 5,
            question = "Where can I find the shared code in this template?",
            answer = "Shared code is typically located in modules named " +
                "`commonMain` within shared source sets (e.g., `shared/src/commonMain`," +
                " `features/featureName/src/commonMain`). Platform-specific code resides " +
                "in corresponding platform source sets like `androidMain` or `iosMain`.",
        ),
    )

    if (questions.isEmpty()) {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = "No FAQs available at the moment.",
                style = MaterialTheme.typography.bodyLarge,
            )
        }
        return
    }

    LazyColumn(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        items(questions, key = { it.id }) { faqItem ->
            FaqListItem(faqItem = faqItem)
        }
    }
}

@Composable
fun FaqListItem(
    faqItem: FaqItem,
    modifier: Modifier = Modifier,
) {
    var expanded by remember { mutableStateOf(false) }

    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable { expanded = !expanded },
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth(),
            ) {
                Text(
                    text = faqItem.question,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.weight(1f),
                )
                Spacer(modifier = Modifier.width(8.dp))
                Icon(
                    imageVector = if (expanded) Icons.Filled.ExpandLess else Icons.Filled.ExpandMore,
                    contentDescription = if (expanded) "Collapse" else "Expand",
                )
            }

            AnimatedVisibility(visible = expanded) {
                Column {
                    // Added column to ensure proper spacing for multi-line answers
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = faqItem.answer,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true, name = "FAQ Screen Preview")
@Composable
fun FaqScreenPreview() {
    MaterialTheme {
        // Replace with your app's specific theme if needed
        FaqScreen()
    }
}

@Preview(showBackground = true, name = "FAQ List Item Preview (Collapsed)")
@Composable
fun FaqListItemCollapsedPreview() {
    MaterialTheme {
        FaqListItem(
            faqItem = FaqItem(
                id = 5,
                question = "Where can I find the shared code in this template?",
                answer = "Shared code is typically located in modules named " +
                    "`commonMain` within shared source sets (e.g., `shared/src/commonMain`," +
                    " `features/featureName/src/commonMain`). Platform-specific code " +
                    "resides in corresponding platform source sets like `androidMain` or `iosMain`.",
            ),
        )
    }
}

@Preview(showBackground = true, name = "FAQ List Item Preview (Expanded)")
@Composable
fun FaqListItemExpandedPreview() {
    MaterialTheme {
        val item = FaqItem(
            id = 5,
            question = "Where can I find the shared code in this template?",
            answer = "Shared code is typically located in modules named `commonMain` " +
                "within shared source sets (e.g., `shared/src/commonMain`," +
                " `features/featureName/src/commonMain`)." +
                " Platform-specific code resides in corresponding" +
                " platform source sets like `androidMain` or `iosMain`.",
        )

        // Hacky way to show expanded in preview, normally state drives this
        FaqListItem(faqItem = item.copy(question = item.question + " (Expanded Preview)"))
        // For a true expanded preview of FaqListItem itself, you'd need to modify FaqListItem
        // to accept an initialExpandedState or similar, or just click it in interactive preview.
        // The current FaqListItem preview will always start collapsed.
    }
}
