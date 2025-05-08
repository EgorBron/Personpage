package net.blusutils.bron.personpage.components.common

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.css.Cursor
import com.varabyte.kobweb.compose.css.ObjectFit
import com.varabyte.kobweb.compose.css.Transition
import com.varabyte.kobweb.compose.css.functions.blur
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.silk.style.CssStyle
import com.varabyte.kobweb.silk.style.selectors.hover
import com.varabyte.kobweb.silk.style.toAttrs
import net.blusutils.bron.personpage.CssVars
import net.blusutils.bron.personpage.shared.config.CardModel
import org.jetbrains.compose.web.css.DisplayStyle
import org.jetbrains.compose.web.css.background
import org.jetbrains.compose.web.css.percent
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.H3
import org.jetbrains.compose.web.dom.Img
import org.jetbrains.compose.web.dom.Text

@Composable
fun Card(
    card: CardModel,
    setModalContent: (CardModel) -> Unit = {},
) {
    Div(CardStyle.toAttrs {
        style {
            background(card.bg)
        }
        onClick {
            if (card.verbose != null) {
                setModalContent(card)
            }
        }
    }) {
        if (card.thumb.isNotEmpty()) {
            Div(ThumbGroupStyle.toAttrs()) {
                card.thumb.forEach { src ->
                    Img(src, "", ThumbStyle.toAttrs())
                }
            }
        }
        H3 { Text(card.name) }
        Markdown(card.desc)
    }
}

val CardStyle = CssStyle {
    base {
        Modifier
            .background(CssVars.cardBg)
            .backdropFilter(blur(5.px))
            .borderRadius(CssVars.radius)
            .padding(20.px)
            .transition(Transition.of("transform", CssVars.transition))
            .cursor(Cursor.Default)
    }
    hover {
        Modifier.transform {
            translateY((-5).px)
        }
    }
}
val ThumbStyle = CssStyle {
    base {
        Modifier
            .width(100.percent)
            .height(120.px)
            .objectFit(ObjectFit.Cover)
            .borderRadius(CssVars.radius)
            .margin(bottom = 15.px)
    }
}
val ThumbGroupStyle = CssStyle {
    base {
        Modifier
            .display(DisplayStyle.Flex)
            .gap(10.px)
            .margin(bottom = 15.px)
    }
}