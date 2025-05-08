package net.blusutils.bron.personpage.layouts.sections

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.css.AlignItems
import com.varabyte.kobweb.compose.css.Height
import com.varabyte.kobweb.compose.css.TextAlign
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.silk.style.CssStyle
import com.varabyte.kobweb.silk.style.breakpoint.Breakpoint
import com.varabyte.kobweb.silk.style.selectors.before
import com.varabyte.kobweb.silk.style.toAttrs
import com.varabyte.kobweb.silk.style.until
import net.blusutils.bron.personpage.components.TimelineItem
import net.blusutils.bron.personpage.shared.config.TimelineItemModel
import net.blusutils.bron.personpage.shared.config.Titleable
import org.jetbrains.compose.web.css.*
import org.jetbrains.compose.web.dom.Div

val TimelineStyle = CssStyle {
    base {
        Modifier
            .position(Position.Relative)
            .margin(40.px)
            .padding(20.px, 0.px)
            .width(90.percent)
            .maxWidth(800.px)
    }
    before {
        Modifier
            .content("")
            .position(Position.Absolute)
            .top(0.px)
            .left(50.percent)
            .width(2.px)
            .height(100.percent)
            .backgroundColor(Color("#555"))
            .transform {
                translateX((-50).percent)
            }
    }
    until(Breakpoint.LG) {
        Modifier.left(20.px)
    }
}
val TitleSectionStyle = CssStyle {
    base {
        Modifier
            .height(100.vh)
            .display(DisplayStyle.Flex)
            .alignItems(AlignItems.Center)
            .justifyContent(JustifyContent.Center)
            .padding(0.px, 20.vw)
            .position(Position.Relative)
    }
    until(Breakpoint.LG) {
        Modifier
            .height(Height.Unset)
            .flexDirection(FlexDirection.ColumnReverse)
            .textAlign(TextAlign.Center)
            .padding(20.px)
            .gap(15.px)
    }
}

@Composable
fun TimelineSection(timeline: Titleable<TimelineItemModel>) {
    PageSection(timeline.title, { style { backgroundColor(Color("#181818")) } }) {
        Div(TimelineStyle.toAttrs()) {
            timeline.items.forEachIndexed { index, item ->
                TimelineItem(index, item)
            }
        }
    }
}
