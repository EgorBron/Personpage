package net.blusutils.bron.personpage.layouts.common

import androidx.compose.runtime.Composable
import net.blusutils.bron.personpage.components.common.Card
import net.blusutils.bron.personpage.layouts.sections.PageSection
import net.blusutils.bron.personpage.shared.config.CardModel
import net.blusutils.bron.personpage.shared.config.Titleable

@Composable
fun CardSection(
    section: Titleable<CardModel>,
    setModalContent: (CardModel) -> Unit
) {
    PageSection(section.title) {
        section.items.forEach { card ->
            Card(card, setModalContent)
        }
    }
}