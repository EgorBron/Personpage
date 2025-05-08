package net.blusutils.bron.personpage.components.common

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.css.Cursor
import com.varabyte.kobweb.compose.css.TextDecorationLine
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.compose.ui.toAttrs
import net.blusutils.bron.personpage.shared.config.ConfigModel
import org.jetbrains.compose.web.css.cssRem
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.Text

@Composable
fun LangSwitch(
    currentLang: String,
    langs: Map<String, ConfigModel>,
    setLanguage: (String) -> Unit
) {
    Column(
        Modifier
            .margin(top = 20.px)
    ) {
        for ((it, sw) in langs.filterNot { it.key == currentLang })
            Div(
                Modifier
                    .fontSize(.9.cssRem)
                    .cursor(Cursor.Pointer)
                    .textDecorationLine(TextDecorationLine.Underline)
                    .onClick { _ ->
                        setLanguage(it)
                    }
                    .toAttrs()
            ) {
                Text(sw.switch)
            }
    }
}