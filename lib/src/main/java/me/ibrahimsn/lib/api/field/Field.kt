package me.ibrahimsn.lib.api.field

import me.ibrahimsn.lib.api.rule.Ruler

interface Field {

    val id: Int

    var isValid: Boolean

    var error: Ruler?

    var onValidationChangeListener: ((Field) -> Unit)?

    fun getValue(): Any?

    fun setValue(value: Any?)

    fun setError(error: String?)

    fun clearError()

    fun validate(): Field

    fun onAttached()

    fun onDetached()
}
