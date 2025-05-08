package net.blusutils.bron.personpage.components.common

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.dom.RawHtml
import com.varabyte.kobweb.framework.annotations.DelicateApi
import org.intellij.markdown.flavours.gfm.GFMFlavourDescriptor
import org.intellij.markdown.html.HtmlGenerator
import org.intellij.markdown.parser.MarkdownParser

val gfm = GFMFlavourDescriptor()
val pars = MarkdownParser(gfm)

@Composable
@OptIn(DelicateApi::class)
fun Markdown(src: String) {
    val newMd = HtmlGenerator(src, pars.buildMarkdownTreeFromString(src), gfm).generateHtml()
    RawHtml(newMd)
}