plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.serialization)
}

group = "net.blusutils.bron.personpage.shared"
version = "1.0-SNAPSHOT"

kotlin {
    js {
        browser()
        binaries.executable()
    }
    sourceSets {
        jsMain.dependencies {
            // There's no common Compose components,
            // `shared` actually shares only the config logic

            // Serialization library dependency declared as API
            // to reduce the number of dependencies in the dependent submodules,
            // because they still may need things from serialization.
            api(libs.serialization.json)
        }
    }
}
