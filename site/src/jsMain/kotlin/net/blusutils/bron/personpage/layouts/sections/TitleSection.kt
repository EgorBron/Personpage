package net.blusutils.bron.personpage.layouts.sections

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.css.Overflow
import com.varabyte.kobweb.compose.foundation.layout.Arrangement
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.silk.style.CssStyle
import com.varabyte.kobweb.silk.style.breakpoint.Breakpoint
import com.varabyte.kobweb.silk.style.breakpoint.displayIfAtLeast
import com.varabyte.kobweb.silk.style.breakpoint.displayUntil
import com.varabyte.kobweb.silk.style.toAttrs
import com.varabyte.kobweb.silk.style.toModifier
import com.varabyte.kobweb.silk.style.until
import net.blusutils.bron.personpage.components.common.LangSwitch
import net.blusutils.bron.personpage.components.common.Markdown
import net.blusutils.bron.personpage.layouts.common.Socials
import net.blusutils.bron.personpage.shared.config.ConfigModel
import net.blusutils.bron.personpage.shared.config.TitleModel
import org.jetbrains.compose.web.css.*
import org.jetbrains.compose.web.dom.*

val InfoStyle = CssStyle {
    base {
        Modifier.maxWidth(50.percent)
    }
    until(Breakpoint.LG) {
        Modifier.maxWidth(100.percent)
    }
}
val TitleHeadingStyle = CssStyle {
    base {
        Modifier.fontSize(value = 4.cssRem)
    }
}
val TitleParagraphStyle = CssStyle {
    base {
        Modifier
            .margin(top = 1.em)
            .fontSize(1.2.cssRem)
    }
}

@Composable
fun TitleSection(
    title: TitleModel,
    titleMaxHeight: Double,
    titlePadding: Double,
    langs: Map<String, ConfigModel>?,
    currentLang: String,
    setLanguage: (String) -> Unit
) {
    Section(
        TitleSectionStyle
            .toModifier()
            .height(titleMaxHeight.vh)
            .paddingInline(titlePadding.vw)
            .toAttrs()
    ) {
        Column(
            Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                Modifier.fillMaxSize(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Div(InfoStyle.toAttrs {/* maybe todo */ }) {

                    H1(TitleHeadingStyle.toAttrs()) { Text(title.name) }

                    P(TitleParagraphStyle.toAttrs()) { Markdown(title.tagline) }

                    Socials(title.socials)

                    if (langs != null && langs.size > 1)
                        LangSwitch(currentLang, langs, setLanguage)
                }
                Div(AvatarStyle.toModifier().displayIfAtLeast(Breakpoint.LG).toAttrs()) {
                    Img(title.avatar, "Avatar")
                }
            }
        }
        Div(AvatarStyle.toModifier().displayUntil(Breakpoint.LG).toAttrs()) {
            Img(title.avatar, "Avatar")
        }
    }
}

val AvatarStyle = CssStyle {
    base {
        Modifier
            .size(256.px)
            .minSize(256.px)
            .maxSize(256.px)
            .borderRadius(10.percent)
            .overflow(Overflow.Hidden)
    }
    until(Breakpoint.LG) {
        Modifier.maxWidth(100.percent)
    }
}