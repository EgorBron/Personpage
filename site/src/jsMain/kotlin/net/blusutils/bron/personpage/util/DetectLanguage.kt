package net.blusutils.bron.personpage.util

import kotlinx.browser.window
import net.blusutils.bron.personpage.shared.config.TranslatableConfigModel

fun detectLanguage(config: TranslatableConfigModel): String {
    val lang = window.navigator.language.slice(0..2).lowercase()
    return if (lang in config.translations.keys) lang else config.defaultLanguage
}