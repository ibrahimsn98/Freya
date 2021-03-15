package me.ibrahimsn.lib.internal.watcher

import android.view.View
import android.view.ViewTreeObserver

internal class FocusOutWatcher(
    override val view: View,
    private inline val onFocusOut: ((Int) -> Unit)
) : BaseWatcher<ViewTreeObserver.OnGlobalFocusChangeListener>() {

    override var listener: ViewTreeObserver.OnGlobalFocusChangeListener? =
        ViewTreeObserver.OnGlobalFocusChangeListener { oldFocus, _ ->
            if (oldFocus == view) {
                onFocusOut.invoke(view.id)
            }
        }

    override fun register() {
        view.viewTreeObserver.addOnGlobalFocusChangeListener(listener)
    }

    override fun unregister() {
        view.viewTreeObserver.removeOnGlobalFocusChangeListener(listener)
    }
}
