package net.blusutils.bron.personpage.layouts

import androidx.compose.runtime.*
import com.varabyte.kobweb.compose.css.Background
import com.varabyte.kobweb.compose.css.background
import com.varabyte.kobweb.compose.foundation.layout.Arrangement
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.attrsModifier
import com.varabyte.kobweb.compose.ui.modifiers.attr
import com.varabyte.kobweb.compose.ui.modifiers.background
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxSize
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxWidth
import com.varabyte.kobweb.compose.ui.styleModifier
import net.blusutils.bron.personpage.layouts.common.CardSection
import net.blusutils.bron.personpage.layouts.sections.*
import net.blusutils.bron.personpage.shared.config.CardModel
import net.blusutils.bron.personpage.shared.config.ConfigModel
import net.blusutils.bron.personpage.util.attachScrollEvent
import org.jetbrains.compose.web.dom.Hr

@Composable
fun MainLayout(
    config: ConfigModel,
    currentLang: String,
    langs: Map<String, ConfigModel>? = null,
    setLanguage: (String) -> Unit
) {
    var modalContent by remember { mutableStateOf<CardModel?>(null) }
    var titleMaxHeight by remember { mutableStateOf(100.0) }
    var titlePadding by remember { mutableStateOf(20.0) }

    var colMod = Modifier.fillMaxWidth()
    if (config.background != null)
        colMod = colMod.attr("style", "background: ${config.background};").fillMaxSize()

    Column(
        Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LaunchedEffect(Unit) {
            attachScrollEvent(
                titlePadding,
                titleMaxHeight,
                { titlePadding = it }
            ) { titleMaxHeight = it }
        }

        TitleSection(
            config.title,
            titleMaxHeight,
            titlePadding,
            langs,
            currentLang,
            setLanguage
        )

        AboutSection(config.about)

        TimelineSection(config.timeline)

        listOf( // TODO maybe split these sections into distinct @Composables?
            config.skills,
            config.achievements,
            config.portfolio
        ).forEach { section ->
            CardSection(section) { modalContent = it }
        }

        BlogSection()

        Hr()

        // TODO move socials from title to a separate JSON property
        FooterSection(config.footer, config.title.socials)

        CardSectionVerboseModal(modalContent) {
            modalContent = null
        }
    }
}