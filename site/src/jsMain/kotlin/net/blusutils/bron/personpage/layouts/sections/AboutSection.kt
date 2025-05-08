package net.blusutils.bron.personpage.layouts.sections

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.silk.style.toAttrs
import net.blusutils.bron.personpage.styles.CenterTextStyle
import net.blusutils.bron.personpage.components.common.Markdown
import net.blusutils.bron.personpage.shared.config.AboutModel
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.Img

@Composable
fun AboutSection(about: AboutModel) {
    PageSection(about.title, { id("about") }) {
        Div(CenterTextStyle.toAttrs()) {
            Markdown(about.text)
        }
        Div {
            about.images.forEach { src ->
                Img(src, "")
            }
        }
    }
}