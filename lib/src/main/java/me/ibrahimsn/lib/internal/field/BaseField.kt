package me.ibrahimsn.lib.internal.field

import android.view.View
import me.ibrahimsn.lib.api.field.Field
import me.ibrahimsn.lib.internal.core.Config
import me.ibrahimsn.lib.api.rule.Ruler

abstract class BaseField<T : View> : Field {

    abstract val view: T

    internal abstract val config: Config

    override val id: Int get() = view.id

    override var isValid: Boolean = false

    override var error: Ruler? = null

    override var onValidationChangeListener: ((Field) -> Unit)? = null

    override fun validate(): Field {
        this.isValid = true
        this.error = null
        config.rulers.forEach {
            if (!it.validate(getValue())) {
                this.error = it
                this.isValid = false
                return this
            }
        }
        return this
    }
}
