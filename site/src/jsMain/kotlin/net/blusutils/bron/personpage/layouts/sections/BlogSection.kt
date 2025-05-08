package net.blusutils.bron.personpage.layouts.sections

import androidx.compose.runtime.Composable
import org.jetbrains.compose.web.css.textAlign
import org.jetbrains.compose.web.dom.P
import org.jetbrains.compose.web.dom.Text

@Composable
fun BlogSection() {
    PageSection("Blog (coming soon)") {
        P({ style { textAlign("center") } }) {
            Text("Stay tuned!")
        }
    }
}