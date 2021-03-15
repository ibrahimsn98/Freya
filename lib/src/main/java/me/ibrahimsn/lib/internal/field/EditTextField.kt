package me.ibrahimsn.lib.internal.field

import android.widget.EditText
import me.ibrahimsn.lib.internal.core.Config
import me.ibrahimsn.lib.internal.watcher.FocusOutWatcher
import me.ibrahimsn.lib.internal.watcher.TextChangeWatcher

internal data class EditTextField(
    override val view: EditText,
    override val config: Config
) : BaseField<EditText>() {

    private val textChangeWatcher = TextChangeWatcher(view) {
        val isValid = this.isValid
        val validated = validate()

        if (validated.isValid && isValid != validated.isValid) {
            onValidationChangeListener?.invoke(validated)
            clearError()
        }
    }

    private val focusOutWatcher = FocusOutWatcher(view) {
        if (!validate().isValid) {
            onValidationChangeListener?.invoke(this)
        }
    }

    override fun getValue(): Any? {
        return view.text
    }

    override fun setValue(value: Any?) {
        view.setText(value.toString())
    }

    override fun setError(error: String?) {
        view.error = error
    }

    override fun clearError() {
        if (view.error != null) view.error = null
    }

    override fun onAttached() {
        textChangeWatcher.register()
        focusOutWatcher.register()
    }

    override fun onDetached() {
        textChangeWatcher.unregister()
        focusOutWatcher.unregister()
    }
}
