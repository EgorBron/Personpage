package net.blusutils.bron.personpage.layouts.sections

import androidx.compose.runtime.Composable
import org.jetbrains.compose.web.attributes.AttrsScope
import org.jetbrains.compose.web.dom.H2
import org.jetbrains.compose.web.dom.Section
import org.jetbrains.compose.web.dom.Text
import org.w3c.dom.HTMLElement

@Composable
fun PageSection(
    title: String,
    attrs: AttrsScope<HTMLElement>.() -> Unit = {},
    content: @Composable () -> Unit,
) {
    Section(attrs) {
        H2 { Text(title) }
        content()
    }
}