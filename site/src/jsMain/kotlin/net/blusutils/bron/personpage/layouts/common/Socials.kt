package net.blusutils.bron.personpage.layouts.common

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.css.Transition
import com.varabyte.kobweb.compose.dom.RawHtml
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.framework.annotations.DelicateApi
import com.varabyte.kobweb.silk.style.CssStyle
import com.varabyte.kobweb.silk.style.breakpoint.Breakpoint
import com.varabyte.kobweb.silk.style.selectors.hover
import com.varabyte.kobweb.silk.style.toAttrs
import com.varabyte.kobweb.silk.style.until
import net.blusutils.bron.personpage.shared.config.SocialNetworkModel
import org.jetbrains.compose.web.css.*
import org.jetbrains.compose.web.dom.A
import org.jetbrains.compose.web.dom.Div

val SocialStyle = CssStyle {
    base {
        Modifier
            .display(DisplayStyle.Flex)
            .gap(15.px)
    }
    until(Breakpoint.LG) {
        Modifier.justifyContent(JustifyContent.Center)
    }
}
val SocialLinkStyle = CssStyle {
    base {
        Modifier
            .fontSize(1.5.cssRem)
            .transition(Transition.of("transform", .2.s))
            .width(32.px)
            .color(Color.white)
    }
    hover {
        Modifier.transform {
            scale(1.2)
        }
    }
}

@OptIn(DelicateApi::class)
@Composable
fun Socials(socials: List<SocialNetworkModel>) {
    Div(SocialStyle.toAttrs()) {
        socials.forEach { social ->
            A(social.link, SocialLinkStyle.toAttrs {
                title(social.text)
            }) {
                RawHtml(social.icon)
            }
        }
    }
}
