package net.blusutils.bron.personpage.components.common

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.css.AlignItems
import com.varabyte.kobweb.compose.css.Overflow
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.silk.style.CssStyle
import com.varabyte.kobweb.silk.style.toAttrs
import net.blusutils.bron.personpage.CssVars
import org.jetbrains.compose.web.css.*
import org.jetbrains.compose.web.dom.Div

@Composable
fun ModalWindow(
    visibility: Boolean,
    onDismiss: () -> Unit,
    modalContent: @Composable () -> Unit
) {
    Div(ModalStyle.toAttrs {
        style {
            display(
                if (visibility)
                    DisplayStyle.Flex
                else
                    DisplayStyle.None
            )
        }
        onClick { onDismiss() }
    }) {
        if (visibility) {
            Div(ModalContentStyle.toAttrs {
                onClick { it.stopPropagation() }
            }) {
                modalContent()
            }
        }
    }
}

val ModalStyle = CssStyle {
    base {
        Modifier
            .position(Position.Fixed)
            .backgroundColor(rgba(0, 0, 0, 0.8))
            .display(DisplayStyle.None)
            .alignItems(AlignItems.Center)
            .justifyContent(JustifyContent.Center)
            .padding(20.px)
    }
}
val ModalContentStyle = CssStyle {
    base {
        Modifier
            .backgroundColor(Color("#222"))
            .padding(20.px)
            .borderRadius(CssVars.radius)
            .maxWidth(600.px)
            .maxHeight(90.vh)
            .overflow(Overflow.Auto)
    }
}