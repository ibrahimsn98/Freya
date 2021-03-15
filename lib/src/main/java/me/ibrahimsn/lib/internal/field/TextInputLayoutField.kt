package me.ibrahimsn.lib.internal.field

import com.google.android.material.textfield.TextInputLayout
import me.ibrahimsn.lib.internal.core.Config
import me.ibrahimsn.lib.internal.watcher.FocusOutWatcher
import me.ibrahimsn.lib.internal.watcher.TextChangeWatcher

internal data class TextInputLayoutField(
    override val view: TextInputLayout,
    override val config: Config
) : BaseField<TextInputLayout>() {

    private val textChangeWatcher = view.editText?.let {
        TextChangeWatcher(it) {
            val isValid = this.isValid
            val validated = validate()

            if (validated.isValid && isValid != validated.isValid) {
                onValidationChangeListener?.invoke(validated)
                clearError()
            }
        }
    }

    private val focusOutWatcher = view.editText?.let {
        FocusOutWatcher(it) {
            if (!validate().isValid) {
                onValidationChangeListener?.invoke(this)
            }
        }
    }

    override fun getValue(): Any? {
        return view.editText?.text
    }

    override fun setValue(value: Any?) {
        view.editText?.setText(value.toString())
    }

    override fun setError(error: String?) {
        view.isErrorEnabled = true
        view.error = error
    }

    override fun clearError() {
        view.isErrorEnabled = false
        view.error = null
    }

    override fun onAttached() {
        textChangeWatcher?.register()
        focusOutWatcher?.register()
    }

    override fun onDetached() {
        textChangeWatcher?.unregister()
        focusOutWatcher?.unregister()
    }
}
