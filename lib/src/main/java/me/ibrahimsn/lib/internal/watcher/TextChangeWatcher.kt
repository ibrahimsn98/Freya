package me.ibrahimsn.lib.internal.watcher

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import me.ibrahimsn.lib.internal.util.DefaultTextWatcher

internal class TextChangeWatcher(
    override val view: EditText,
    private inline val onTextChanged: ((Int) -> Unit)
) : BaseWatcher<TextWatcher>() {

    override var listener: TextWatcher? = object: DefaultTextWatcher() {
        override fun afterTextChanged(p0: Editable?) {
            onTextChanged.invoke(view.id)
        }
    }

    override fun register() {
        view.addTextChangedListener(listener)
    }

    override fun unregister() {
        view.removeTextChangedListener(listener)
    }
}
