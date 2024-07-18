import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import com.mshdabiola.testing.fake.repository.FakeNoteRepository
import org.junit.Rule
import org.junit.Test

class Example {
    @get:Rule
    val rule = createComposeRule()

    @Test
    fun myTest() {
        val fakeRepo=FakeNoteRepository()
        // Declares a mock UI to demonstrate API calls
        //
        // Replace with your own declarations to test the code in your project
        rule.setContent {
            var text by remember { mutableStateOf("Hello") }

            Text(
                text = text,
                modifier = Modifier.testTag("text"),
            )
            Button(
                onClick = { text = "Compose" },
                modifier = Modifier.testTag("button"),
            ) {
                Text("Click me")
            }
        }

        // Tests the declared UI with assertions and actions of the JUnit-based testing API
        rule.onNodeWithTag("text").assertTextEquals("Hello")
        rule.onNodeWithTag("button").performClick()
        rule.onNodeWithTag("text").assertTextEquals("Compose")
    }
}
