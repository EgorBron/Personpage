package net.blusutils.bron.personpage.pages

import androidx.compose.runtime.*
import com.varabyte.kobweb.core.Page
import kotlinx.browser.window
import kotlinx.coroutines.await
import kotlinx.serialization.json.Json
import net.blusutils.bron.personpage.layouts.*
import net.blusutils.bron.personpage.shared.config.*
import net.blusutils.bron.personpage.util.detectLanguage
import org.jetbrains.compose.web.dom.*

const val CONFIG_URL = "/config.json"

@Page
@Composable
fun Index() {
    var config by remember { mutableStateOf<TranslatableConfigModel?>(null) }
    var currentLang by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        val response = window.fetch(CONFIG_URL).await()
        val json = response.text().await()
        config = Json.decodeFromString<TranslatableConfigModel>(json).also { currentLang = detectLanguage(it) }
    }

    val currentTranslation = config?.let { c ->
        c.translations.let { t ->
            t[currentLang] ?: t[c.defaultLanguage]
        }
    }

    if (currentTranslation == null)
        Text("No translations loaded or config is invalid. Check the browser console output.")
    else MainLayout(currentTranslation, currentLang, config?.translations) {
            currentLang = it
        }
}

