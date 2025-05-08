package net.blusutils.bron.personpage.components

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.css.TextAlign
import com.varabyte.kobweb.compose.css.functions.blur
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.silk.style.CssStyle
import com.varabyte.kobweb.silk.style.breakpoint.Breakpoint
import com.varabyte.kobweb.silk.style.toAttrs
import com.varabyte.kobweb.silk.style.until
import net.blusutils.bron.personpage.components.common.Markdown
import net.blusutils.bron.personpage.CssVars
import net.blusutils.bron.personpage.shared.config.TimelineItemModel
import org.jetbrains.compose.web.css.*
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.H3
import org.jetbrains.compose.web.dom.P
import org.jetbrains.compose.web.dom.Text

val TimelineItemStyle = CssStyle {
    base {
        Modifier
            .position(Position.Relative)
            .width(50.percent)
            .padding(20.px, 40.px)
    }
    until(Breakpoint.LG) {
        Modifier
            .width(100.percent)
            .padding(20.px)
            .textAlign(TextAlign.Left)
            .padding(left = 60.px, right = 20.px)
            .left(0.px)
    }
}
val TimelineDotStyle = CssStyle {
    base {
        Modifier
            .position(Position.Absolute)
            .top(30.px)
            .left(100.percent)
            .width(12.px)
            .height(12.px)
            .backgroundColor(Color("#bcbcbc"))
            .borderRadius(50.percent)
            .transform {
                translateX((-50).percent)
            }
            .zIndex(1)
    }
    until(Breakpoint.LG) {
        Modifier.left(20.px)
    }
}
val TimelineContentStyle = CssStyle {
    base {
        Modifier
            .background(CssVars.cardBg)
            .backdropFilter(blur(5.px))
            .borderRadius(CssVars.radius)
            .padding(15.px)
    }
}

@Composable
fun TimelineItem(index: Int, item: TimelineItemModel) {
    Div(TimelineItemStyle.toAttrs {
        style {
            if (index % 2 == 0) {
                left(0.px)
                textAlign("right")
            } else {
                left(50.percent)
            }
        }
    }) {
        Div(TimelineDotStyle.toAttrs {
            style {
                if (index % 2 != 0) {
                    left(0.percent)
                }
            }
        })
        Div(TimelineContentStyle.toAttrs()) {
            H3 { Text(item.year) }
            P { Markdown(item.text) }
        }
    }
}
