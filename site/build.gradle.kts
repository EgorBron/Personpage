import com.varabyte.kobweb.gradle.application.util.configAsKobwebApplication
import kotlinx.html.script
import kotlinx.html.style
import kotlinx.html.styleLink
import kotlinx.html.unsafe

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.serialization)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.kobweb.application)
    alias(libs.plugins.kobwebx.markdown)
}

group = "net.blusutils.bron.personpage"
version = "1.0-SNAPSHOT"

kobweb {
    app {
        index {
            description.set("Powered by Kobweb")
            this.head.add {
                styleLink("main.css")
            }
        }
    }
}

kotlin {
    configAsKobwebApplication("personpage")

    sourceSets {
        jsMain.dependencies {
            implementation(libs.compose.runtime)
            implementation(libs.compose.html.core)

            // Shared project includes the serialization library
            // (see the shared/build.gradle.kts file)
            implementation(project(":shared"))

            implementation(libs.kobweb.core)
            implementation(libs.kobweb.silk)
            implementation(libs.silk.icons.fa)

            // Kobweb's Markdown is replaced by the Jetbrains one
            // because I just haven't figured out on how to
            // call Markdown renderer directly from JS 😅
            //implementation(libs.kobwebx.markdown)
            implementation(libs.jetbrains.markdown)
        }
    }
}
