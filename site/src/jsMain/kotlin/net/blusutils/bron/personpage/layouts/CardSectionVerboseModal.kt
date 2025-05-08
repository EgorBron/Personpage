package net.blusutils.bron.personpage.layouts

import androidx.compose.runtime.Composable
import net.blusutils.bron.personpage.components.common.ModalWindow
import net.blusutils.bron.personpage.components.common.Markdown
import net.blusutils.bron.personpage.shared.config.CardModel
import org.jetbrains.compose.web.dom.H2
import org.jetbrains.compose.web.dom.Text

@Composable
fun CardSectionVerboseModal(
    modalContent: CardModel?,
    onDismiss: () -> Unit
) {
    ModalWindow(
        modalContent != null,
        onDismiss
    ) {
        H2 { Text(modalContent!!.name) }
        Markdown(modalContent!!.verbose!!)
    }
}