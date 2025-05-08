package net.blusutils.bron.personpage.layouts.sections

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.foundation.layout.Arrangement
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.ui.Alignment
import net.blusutils.bron.personpage.components.common.Markdown
import net.blusutils.bron.personpage.layouts.common.Socials
import net.blusutils.bron.personpage.shared.config.SocialNetworkModel
import org.jetbrains.compose.web.css.cssRem
import org.jetbrains.compose.web.css.padding
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.css.textAlign
import org.jetbrains.compose.web.dom.Footer
import org.jetbrains.compose.web.dom.Small

@Composable
fun FooterSection(footer: String, socials: List<SocialNetworkModel>) {
    Footer({
        style {
            textAlign("center")
            padding(20.px, 0.px)
        }
    }) {
        Column(
            verticalArrangement = Arrangement.spacedBy(0.5.cssRem),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Small { Markdown(footer) }
            Socials(socials)
        }
    }
}