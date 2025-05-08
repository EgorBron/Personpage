package net.blusutils.bron.personpage.util

import kotlinx.browser.document
import kotlinx.browser.window
import org.w3c.dom.events.Event

fun attachScrollEvent(
    titlePadding: Double,
    titleMaxHeight: Double, // unused_ for some reason...
    setTitlePadding: (Double) -> Unit,
    setMaxHeight: (Double) -> Unit
) {
    val startPadding = 20.0
    val minPadding = 1.0
    val startHeight = 100.0
    val minHeight = 20.0
    val scrollRange = 700.0
    val ev = { _: Event ->
        // This is a sort of mobile device detection...
        // A bad one. I know it.
        if (window.innerWidth <= 960) {
            if (titlePadding != 20.0)
                setTitlePadding(20.0)
        } else {
            val sectionTop = document.getElementById("about")?.getBoundingClientRect()?.top ?: 0.0
            val scrollProgress = minOf(
                maxOf(
                    (window.innerHeight - sectionTop) / scrollRange * 0.4,
                    0.0
                ),
                1.0
            )
            setMaxHeight(startHeight - (startHeight - minHeight) * scrollProgress)
            setTitlePadding(startPadding - (startPadding - minPadding) * scrollProgress)
        }
    }
    document.body?.onscroll = ev
    document.body?.onresize = ev
}