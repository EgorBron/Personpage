package net.blusutils.bron.personpage.shared.config

import kotlinx.serialization.Serializable

@Serializable
data class TranslatableConfigModel(
    val defaultLanguage: String,
    val translations: Map<String, ConfigModel>
)

@Serializable
data class ConfigModel(
    val switch: String,
    val background: String?,
    val title: TitleModel,
    val about: AboutModel,
    val timeline: Titleable<TimelineItemModel>,
    val skills: Titleable<CardModel>,
    val achievements: Titleable<CardModel>,
    val portfolio: Titleable<CardModel>,
    val footer: String
)

@Serializable
data class Titleable<T>(
    val title: String,
    val items: List<T>
)

@Serializable
data class TitleModel(
    val name: String,
    val tagline: String,
    val avatar: String,
    val socials: List<SocialNetworkModel>,
)

@Serializable
data class SocialNetworkModel(
    val icon: String,
    val link: String,
    val text: String
)

@Serializable
data class AboutModel(
    val title: String,
    val text: String,
    val images: List<String>
)

@Serializable
data class TimelineItemModel(
    val year: String,
    val text: String
)

@Serializable
data class CardModel(
    val name: String,
    val desc: String,
    val verbose: String?,
    val bg: String,
    val thumb: List<String>
)