package net.blusutils.bron.personpage

import com.varabyte.kobweb.compose.css.Overflow
import com.varabyte.kobweb.compose.css.TextAlign
import com.varabyte.kobweb.compose.css.TextDecorationLine
import com.varabyte.kobweb.compose.css.functions.blur
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.silk.init.InitSilk
import com.varabyte.kobweb.silk.init.InitSilkContext
import com.varabyte.kobweb.silk.init.registerStyleBase
import org.jetbrains.compose.web.css.*

@InitSilk
fun initSilk(ctx: InitSilkContext) {
    ctx.stylesheet.registerStyleBase("*") {
        Modifier
            .color(Color.white)
    }
    ctx.stylesheet.registerStyleBase("body, silk-surface") {
        Modifier
            .fontFamily("Arial", "sans-serif")
            .color(Color.white)
            .backgroundColor(Color.black)
            .overflow(overflowX = Overflow.Hidden, overflowY = Overflow.Inherit)
    }

    ctx.stylesheet.registerStyleBase("a") {
        Modifier.textDecorationLine(TextDecorationLine.Underline)
    }

    ctx.stylesheet.registerStyleBase("img") {
        Modifier
            .display(DisplayStyle.Block)
            .maxWidth(100.percent)
    }

    ctx.stylesheet.registerStyleBase("canvas.p-canvas-webgl") {
        Modifier
            .position(Position.Fixed)
            .zIndex(-1)
            .filter(blur(5.px))
    }

    ctx.stylesheet.registerStyleBase("section") {
        Modifier
            .fillMaxWidth()
            .padding(60.px, 20.px)
    }

    ctx.stylesheet.registerStyleBase("h1, h2") {
        Modifier
            .textAlign(TextAlign.Center)
            .margin(bottom = 1.em)
    }

    ctx.stylesheet.registerStyleBase("p") {
        Modifier.padding(bottom = 1.em)
    }

    ctx.stylesheet.registerStyleBase("footer") {
        Modifier
            .fillMaxWidth()
            .backgroundColor(Color("#181818"))
    }
}

data object CssVars {
    val cardBg = rgba(255, 255, 255, 0.05)
    val radius = 8.px
    val transition = 0.3.s
}