package net.blusutils.bron.personpage.editor.pages

import androidx.compose.runtime.*
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxHeight
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxSize
import com.varabyte.kobweb.compose.ui.modifiers.padding
import com.varabyte.kobweb.core.Page
import com.varabyte.kobweb.silk.components.forms.Button
import com.varabyte.kobweb.silk.components.layout.Surface
import kotlinx.browser.document
import kotlinx.browser.window
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.serialization.json.Json
import net.blusutils.bron.personpage.shared.config.*
import org.jetbrains.compose.web.attributes.accept
import org.jetbrains.compose.web.attributes.placeholder
import org.jetbrains.compose.web.css.DisplayStyle
import org.jetbrains.compose.web.css.cssRem
import org.jetbrains.compose.web.css.display
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.dom.*
import org.w3c.dom.Storage
import org.w3c.files.get
import kotlin.collections.List
import kotlin.collections.emptyList
import kotlin.collections.filter
import kotlin.collections.filterIndexed
import kotlin.collections.forEach
import kotlin.collections.forEachIndexed
import kotlin.collections.joinToString
import kotlin.collections.map
import kotlin.collections.mapOf
import kotlin.collections.mutableMapOf
import kotlin.collections.plus
import kotlin.collections.set
import kotlin.collections.setOf
import kotlin.collections.toMutableList
import kotlin.collections.toMutableMap
import kotlin.collections.toSet
import kotlin.reflect.KProperty

class ConfigStateManager(
    private val key: String = "config",
    private val localStorage: Storage = window.localStorage,
    private val json: Json = Json { ignoreUnknownKeys = true }
) {
    private val _config = MutableStateFlow<TranslatableConfigModel?>(null)
    val config: StateFlow<TranslatableConfigModel?> = _config.asStateFlow()
    
    private var currentLang: String = ""

    init {
        loadFromStorage()
    }

    fun setCurrentLang(lang: String) {
        currentLang = lang
    }

    inner class TranslationProperty<T>(
        private val getter: (ConfigModel) -> T,
        private val setter: (ConfigModel, T) -> ConfigModel,
        private val defaultValue: T
    ) {
        operator fun getValue(thisRef: Any?, property: KProperty<*>): T {
            return _config.value?.translations?.get(currentLang)?.let(getter) ?: defaultValue
        }

        operator fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
            _config.value?.let { currentConfig ->
                val currentTranslation = currentConfig.translations[currentLang]
                if (currentTranslation != null) {
                    val newTranslation = setter(currentTranslation, value)
                    val newTranslations = currentConfig.translations.toMutableMap()
                    newTranslations[currentLang] = newTranslation
                    
                    val newConfig = currentConfig.copy(translations = newTranslations)
                    _config.value = newConfig
                    saveToStorage(newConfig)
                } else {
                    // Create new translation if it doesn't exist
                    val newTranslation = setter(ConfigModel("", "", TitleModel("", "", "", emptyList()), AboutModel("", "", emptyList()), Titleable("", emptyList()), Titleable("", emptyList()), Titleable("", emptyList()), Titleable("", emptyList()), ""), value)
                    val newTranslations = currentConfig.translations.toMutableMap()
                    newTranslations[currentLang] = newTranslation
                    
                    val newConfig = currentConfig.copy(translations = newTranslations)
                    _config.value = newConfig
                    saveToStorage(newConfig)
                }
            } ?: run {
                // Create new config if it doesn't exist
                val newTranslation = setter(ConfigModel("", "", TitleModel("", "", "", emptyList()), AboutModel("", "", emptyList()), Titleable("", emptyList()), Titleable("", emptyList()), Titleable("", emptyList()), Titleable("", emptyList()), ""), value)
                val newConfig = TranslatableConfigModel(currentLang, mapOf(currentLang to newTranslation))
                _config.value = newConfig
                saveToStorage(newConfig)
            }
        }
    }

    private fun loadFromStorage() {
        val savedConfig = localStorage.getItem(key)
        if (savedConfig != null) {
            try {
                _config.value = json.decodeFromString<TranslatableConfigModel>(savedConfig)
            } catch (e: Exception) {
                console.error("Failed to load config from localStorage", e)
                createDefaultConfig()
            }
        } else {
            createDefaultConfig()
        }
    }

    private fun createDefaultConfig() {
        val defaultConfig = TranslatableConfigModel(
            defaultLanguage = "en",
            translations = mapOf(
                "en" to ConfigModel(
                    switch = "en",
                    background = null,
                    title = TitleModel("", "", "", emptyList()),
                    about = AboutModel("", "", emptyList()),
                    timeline = Titleable("", emptyList()),
                    skills = Titleable("", emptyList()),
                    achievements = Titleable("", emptyList()),
                    portfolio = Titleable("", emptyList()),
                    footer = ""
                )
            )
        )
        _config.value = defaultConfig
        saveToStorage(defaultConfig)
    }

    fun updateConfig(update: (TranslatableConfigModel) -> TranslatableConfigModel) {
        _config.value?.let { currentConfig ->
            val newConfig = update(currentConfig)
            _config.value = newConfig
            saveToStorage(newConfig)
        }
    }

    fun updateTranslation(lang: String, update: (ConfigModel) -> ConfigModel) {
        _config.value?.let { currentConfig ->
            val currentTranslation = currentConfig.translations[lang]
            if (currentTranslation != null) {
                val newTranslation = update(currentTranslation)
                val newTranslations = currentConfig.translations.toMutableMap()
                newTranslations[lang] = newTranslation
                
                val newConfig = currentConfig.copy(translations = newTranslations)
                _config.value = newConfig
                saveToStorage(newConfig)
            }
        }
    }

    private fun saveToStorage(config: TranslatableConfigModel) {
        try {
            localStorage.setItem(key, json.encodeToString(TranslatableConfigModel.serializer(), config))
        } catch (e: Exception) {
            console.error("Failed to save config to localStorage", e)
        }
    }

    fun loadFromJson(jsonString: String): Boolean {
        return try {
            val newConfig = json.decodeFromString<TranslatableConfigModel>(jsonString)
            _config.value = newConfig
            saveToStorage(newConfig)
            true
        } catch (e: Exception) {
            console.error("Failed to parse JSON", e)
            false
        }
    }

    fun getCurrentTranslation(lang: String): ConfigModel? {
        return _config.value?.translations?.get(lang)
    }
}

@Composable
fun FormSection(title: String, content: @Composable () -> Unit) {
    Column(Modifier.padding(top = 4.cssRem)) {
        Text(title)
        content()
    }
}

@Composable
fun FormInput(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String = "",
    isTextArea: Boolean = false
) {
    Label {
        Text(label)
        if (isTextArea) {
            TextArea(value) {
                this.placeholder(placeholder)
                onInput { event -> onValueChange(event.value) }
            }
        } else {
            TextInput(value) {
                this.placeholder(placeholder)
                onInput { event -> onValueChange(event.value) }
            }
        }
    }
}

@Composable
fun<T> ListItemEditor(
    items: List<T>,
    onAdd: () -> Unit,
    onRemove: (Int) -> Unit,
    itemContent: @Composable (T, Int) -> Unit
) {
    Column {
        Button({ onAdd() }) {
            Text("+ Add Item")
        }
        items.forEachIndexed { index, item ->
            Row {
                itemContent(item, index)
                Button({ onRemove(index) }) {
                    Text("Remove")
                }
            }
        }
    }
}

@Composable
fun CardEditor(
    card: CardModel,
    onUpdate: (CardModel) -> Unit
) {
    Column {
        FormInput("Name:", card.name, { onUpdate(card.copy(name = it)) })
        FormInput("Description:", card.desc, { onUpdate(card.copy(desc = it)) }, isTextArea = true)
        if (card.verbose != null) {
            FormInput("Verbose Description:", card.verbose!!, { onUpdate(card.copy(verbose = it)) }, isTextArea = true)
        }
        FormInput("Background:", card.bg, { onUpdate(card.copy(bg = it)) })
        FormInput("Thumbnails (comma-separated):", card.thumb.joinToString(","), 
            { onUpdate(card.copy(thumb = it.split(",").map { it.trim() })) })
    }
}

@Composable
fun CardSection(
    title: String,
    sectionTitle: String,
    onTitleChange: (String) -> Unit,
    cards: List<CardModel>,
    onCardsChange: (List<CardModel>) -> Unit,
    addButtonText: String = "+ Add Item"
) {
    FormSection(title) {
        FormInput("Section Title:", sectionTitle, onTitleChange)
        ListItemEditor(
            items = cards,
            onAdd = { onCardsChange(cards + CardModel("", "", null, "", emptyList())) },
            onRemove = { onCardsChange(cards.filterIndexed { i, _ -> i != it }) }
        ) { card, index ->
            CardEditor(card) { newCard ->
                onCardsChange(cards.toMutableList().apply {
                    this[index] = newCard
                })
            }
        }
    }
}

@Page
@Composable
fun Index() {
    val configManager = remember { ConfigStateManager() }
    val config by configManager.config.collectAsState()
    
    var currentLang by remember { mutableStateOf("") }
    var showJsonLoad by remember { mutableStateOf(false) }
    var jsonInput by remember { mutableStateOf("") }
    var languageList by remember { mutableStateOf("") }

    // Initialize current language and language list when config is loaded
    LaunchedEffect(config) {
        config?.let {
            currentLang = it.defaultLanguage
            configManager.setCurrentLang(currentLang)
            languageList = it.translations.keys.joinToString(",")
        }
    }

    // Handle language list updates
    fun updateLanguageList(newList: String) {
        val newLanguages = newList.split(",").map { it.trim() }.filter { it.isNotEmpty() }
        val currentLanguages = config?.translations?.keys?.toSet() ?: setOf()
        
        // Create new translations map
        val newTranslations = config?.translations?.toMutableMap() ?: mutableMapOf()
        
        // Add new languages
        newLanguages.forEach { lang ->
            if (lang !in currentLanguages) {
                newTranslations[lang] = ConfigModel(
                    switch = lang,
                    background = null,
                    title = TitleModel("", "", "", emptyList()),
                    about = AboutModel("", "", emptyList()),
                    timeline = Titleable("", emptyList()),
                    skills = Titleable("", emptyList()),
                    achievements = Titleable("", emptyList()),
                    portfolio = Titleable("", emptyList()),
                    footer = ""
                )
            }
        }
        
        // Remove languages that are no longer in the list
        currentLanguages.forEach { lang ->
            if (lang !in newLanguages) {
                newTranslations.remove(lang)
            }
        }
        
        // Update config with new translations
        configManager.updateConfig { it.copy(translations = newTranslations) }
        languageList = newList
    }

    // Form state using property delegation with default values
    var titleName by configManager.TranslationProperty(
        getter = { it.title.name },
        setter = { config, value -> config.copy(title = config.title.copy(name = value)) },
        defaultValue = ""
    )
    
    var titleTagline by configManager.TranslationProperty(
        getter = { it.title.tagline },
        setter = { config, value -> config.copy(title = config.title.copy(tagline = value)) },
        defaultValue = ""
    )
    
    var titleAvatar by configManager.TranslationProperty(
        getter = { it.title.avatar },
        setter = { config, value -> config.copy(title = config.title.copy(avatar = value)) },
        defaultValue = ""
    )
    
    var socials by configManager.TranslationProperty(
        getter = { it.title.socials },
        setter = { config, value -> config.copy(title = config.title.copy(socials = value)) },
        defaultValue = emptyList()
    )
    
    var aboutTitle by configManager.TranslationProperty(
        getter = { it.about.title },
        setter = { config, value -> config.copy(about = config.about.copy(title = value)) },
        defaultValue = ""
    )
    
    var aboutText by configManager.TranslationProperty(
        getter = { it.about.text },
        setter = { config, value -> config.copy(about = config.about.copy(text = value)) },
        defaultValue = ""
    )
    
    var aboutImages by configManager.TranslationProperty(
        getter = { it.about.images },
        setter = { config, value -> config.copy(about = config.about.copy(images = value)) },
        defaultValue = emptyList()
    )
    
    var timelineTitle by configManager.TranslationProperty(
        getter = { it.timeline.title },
        setter = { config, value -> config.copy(timeline = config.timeline.copy(title = value)) },
        defaultValue = ""
    )
    
    var milestones by configManager.TranslationProperty(
        getter = { it.timeline.items },
        setter = { config, value -> config.copy(timeline = config.timeline.copy(items = value)) },
        defaultValue = emptyList()
    )
    
    var skillsTitle by configManager.TranslationProperty(
        getter = { it.skills.title },
        setter = { config, value -> config.copy(skills = config.skills.copy(title = value)) },
        defaultValue = ""
    )
    
    var skillsCards by configManager.TranslationProperty(
        getter = { it.skills.items },
        setter = { config, value -> config.copy(skills = config.skills.copy(items = value)) },
        defaultValue = emptyList()
    )
    
    var achievementsTitle by configManager.TranslationProperty(
        getter = { it.achievements.title },
        setter = { config, value -> config.copy(achievements = config.achievements.copy(title = value)) },
        defaultValue = ""
    )
    
    var achievementItems by configManager.TranslationProperty(
        getter = { it.achievements.items },
        setter = { config, value -> config.copy(achievements = config.achievements.copy(items = value)) },
        defaultValue = emptyList()
    )
    
    var portfolioTitle by configManager.TranslationProperty(
        getter = { it.portfolio.title },
        setter = { config, value -> config.copy(portfolio = config.portfolio.copy(title = value)) },
        defaultValue = ""
    )
    
    var portfolioCards by configManager.TranslationProperty(
        getter = { it.portfolio.items },
        setter = { config, value -> config.copy(portfolio = config.portfolio.copy(items = value)) },
        defaultValue = emptyList()
    )
    
    var footerText by configManager.TranslationProperty(
        getter = { it.footer },
        setter = { config, value -> config.copy(footer = value) },
        defaultValue = ""
    )

    fun downloadJson() {
        val json = Json.encodeToString(TranslatableConfigModel.serializer(), config!!)
        val blob = org.w3c.files.Blob(arrayOf(json), org.w3c.files.BlobPropertyBag("application/json"))
        val url = window.asDynamic().URL.createObjectURL(blob)
        val a = window.document.createElement("a") as org.w3c.dom.HTMLAnchorElement
        a.href = url
        a.download = "config.json"
        a.click()
        window.asDynamic().URL.revokeObjectURL(url)
    }

    fun copyJson() {
        val json = Json.encodeToString(TranslatableConfigModel.serializer(), config!!)
        window.navigator.clipboard.writeText(json).then {
            window.alert("JSON copied to clipboard!")
        }
    }

    Surface(Modifier.fillMaxSize()) {
        Row(Modifier.fillMaxSize()) {
            // Sidebar
            Box(Modifier.fillMaxHeight()) {
                Column(Modifier.padding(16.px).fillMaxHeight()) {
                    H2 { Text("Actions") }
                    Button({ document.getElementById("finp")?.asDynamic()?.click() }) {
                        Text("\uD83D\uDCC1 Load File")
                    }
                    Button({ showJsonLoad = true }) { Text("\uD83D\uDCCB Paste JSON") }
                    Button({ copyJson() }) { Text("\uD83D\uDCD1 Copy JSON") }
                    Button({ downloadJson() }) { Text("\uD83D\uDCBE Download JSON") }
                    FileInput() {
                        id("finp")
                        style { display(DisplayStyle.None) }
                        accept("application/json")
                        onChange { event ->
                            event.target.files?.let{ it[0] }?.let { file ->
                                val reader = org.w3c.files.FileReader()
                                reader.onload = { e ->
                                    val content = e.target.asDynamic().result as String
                                    configManager.loadFromJson(content)
                                }
                                reader.readAsText(file)
                            }
                        }
                    }
                }
            }

            // Main content
            Box(Modifier.fillMaxSize()) {
                Column(Modifier.fillMaxSize()) {
                    if (showJsonLoad) {
                        Column {
                            TextArea(jsonInput) {
                                placeholder("Paste JSON here")
                                onInput { event -> jsonInput = event.value }
                            }
                            Row {
                                Button({
                                    if (configManager.loadFromJson(jsonInput)) {
                                        showJsonLoad = false
                                    }
                                }) { Text("Load Data") }
                                Button({ showJsonLoad = false }) { Text("Cancel") }
                            }
                        }
                    }

                    Form {
                        Column {
                            // Configuration Section
                            FormSection("Configuration") {
                                FormInput(
                                    label = "Available Languages (comma-separated):",
                                    value = languageList,
                                    onValueChange = { updateLanguageList(it) }
                                )
                                FormInput(
                                    label = "Default Language:",
                                    value = config?.defaultLanguage ?: "",
                                    onValueChange = { newValue ->
                                        configManager.updateConfig { it.copy(defaultLanguage = newValue) }
                                    }
                                )
                                Label {
                                    Text("Current Language:")
                                    Select({
                                        onChange { event ->
                                            currentLang = event.target.value
                                            configManager.setCurrentLang(currentLang)
                                        }
                                    }) {
                                        languageList.split(",").map { it.trim() }.filter { it.isNotEmpty() }.forEach { lang ->
                                            Option(lang) { Text(lang) }
                                        }
                                    }
                                }
                            }

                            // Title Section
                            FormSection("Title") {
                                FormInput(
                                    label = "Name:",
                                    value = titleName,
                                    onValueChange = { titleName = it }
                                )
                                FormInput(
                                    label = "Tagline:",
                                    value = titleTagline,
                                    onValueChange = { titleTagline = it }
                                )
                                FormInput(
                                    label = "Avatar URL:",
                                    value = titleAvatar,
                                    onValueChange = { titleAvatar = it }
                                )
                                ListItemEditor(
                                    items = socials,
                                    onAdd = { socials = socials + SocialNetworkModel("", "", "") },
                                    onRemove = { socials = socials.filterIndexed { i, _ -> i != it } }
                                ) { social, index ->
                                    Column {
                                        FormInput(
                                            label = "Icon:",
                                            value = social.icon,
                                            onValueChange = {
                                                socials = socials.toMutableList().apply {
                                                    this[index] = this[index].copy(icon = it)
                                                }
                                            }
                                        )
                                        FormInput(
                                            label = "Link:",
                                            value = social.link,
                                            onValueChange = {
                                                socials = socials.toMutableList().apply {
                                                    this[index] = this[index].copy(link = it)
                                                }
                                            }
                                        )
                                        FormInput(
                                            label = "Text:",
                                            value = social.text,
                                            onValueChange = {
                                                socials = socials.toMutableList().apply {
                                                    this[index] = this[index].copy(text = it)
                                                }
                                            }
                                        )
                                    }
                                }
                            }

                            // About Section
                            FormSection("About") {
                                FormInput(
                                    label = "Section Title:",
                                    value = aboutTitle,
                                    onValueChange = { aboutTitle = it }
                                )
                                FormInput(
                                    label = "Description:",
                                    value = aboutText,
                                    onValueChange = { aboutText = it },
                                    isTextArea = true
                                )
                                ListItemEditor(
                                    items = aboutImages,
                                    onAdd = { aboutImages = aboutImages + "" },
                                    onRemove = { aboutImages = aboutImages.filterIndexed { i, _ -> i != it } }
                                ) { image, index ->
                                    FormInput(
                                        label = "Image URL:",
                                        value = image,
                                        onValueChange = {
                                            aboutImages = aboutImages.toMutableList().apply {
                                                this[index] = it
                                            }
                                        }
                                    )
                                }
                            }

                            // Timeline Section
                            FormSection("Timeline") {
                                FormInput(
                                    label = "Section Title:",
                                    value = timelineTitle,
                                    onValueChange = { timelineTitle = it }
                                )
                                ListItemEditor(
                                    items = milestones,
                                    onAdd = { milestones = milestones + TimelineItemModel("", "") },
                                    onRemove = { milestones = milestones.filterIndexed { i, _ -> i != it } }
                                ) { milestone, index ->
                                    Column {
                                        FormInput(
                                            label = "Year:",
                                            value = milestone.year,
                                            onValueChange = {
                                                milestones = milestones.toMutableList().apply {
                                                    this[index] = this[index].copy(year = it)
                                                }
                                            }
                                        )
                                        FormInput(
                                            label = "Text:",
                                            value = milestone.text,
                                            onValueChange = {
                                                milestones = milestones.toMutableList().apply {
                                                    this[index] = this[index].copy(text = it)
                                                }
                                            }
                                        )
                                    }
                                }
                            }

                            // Skills Section
                            CardSection(
                                title = "Skills",
                                sectionTitle = skillsTitle,
                                onTitleChange = { skillsTitle = it },
                                cards = skillsCards,
                                onCardsChange = { skillsCards = it },
                                addButtonText = "+ Add Skill"
                            )

                            // Achievements Section
                            CardSection(
                                title = "Achievements",
                                sectionTitle = achievementsTitle,
                                onTitleChange = { achievementsTitle = it },
                                cards = achievementItems,
                                onCardsChange = { achievementItems = it },
                                addButtonText = "+ Add Achievement"
                            )

                            // Portfolio Section
                            CardSection(
                                title = "Portfolio",
                                sectionTitle = portfolioTitle,
                                onTitleChange = { portfolioTitle = it },
                                cards = portfolioCards,
                                onCardsChange = { portfolioCards = it },
                                addButtonText = "+ Add Project"
                            )

                            // Footer Section
                            FormSection("Footer") {
                                FormInput(
                                    label = "Footer Text:",
                                    value = footerText,
                                    onValueChange = { footerText = it }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}