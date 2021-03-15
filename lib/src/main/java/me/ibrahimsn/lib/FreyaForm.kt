package me.ibrahimsn.lib

import android.content.Context
import android.util.AttributeSet
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.widget.EditText
import androidx.appcompat.widget.LinearLayoutCompat
import com.google.android.material.textfield.TextInputLayout
import me.ibrahimsn.lib.internal.Constants
import me.ibrahimsn.lib.internal.core.Parser
import me.ibrahimsn.lib.internal.field.EditTextField
import me.ibrahimsn.lib.api.field.Field
import me.ibrahimsn.lib.internal.field.TextInputLayoutField
import me.ibrahimsn.lib.api.rule.Ruler
import me.ibrahimsn.lib.internal.core.Config

class FreyaForm @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayoutCompat(context, attrs, defStyleAttr) {

    /**
     * Holds form fields in conjunction to their view id's
     */
    private val fields: MutableMap<Int, Field> = LinkedHashMap()

    /**
     * Holds field configs in conjunction to their view id's
     */
    private var fieldConfigs = mapOf<Int, Config>()

    /**
     * Provides all of the form field values
     */
    val values get() = fields.mapValues {
        it.value.getValue()
    }

    /**
     * Listens for validation change of the whole form
     */
    var onValidationChangeListener: ((Boolean) -> Unit)? = null

    /**
     * Listens for validation change of each form field
     */
    var onFieldValidationChangeListener: ((Field) -> Unit)? = null

    /**
     * Listens for field errors after form validation
     */
    var onErrorListener: ((List<Ruler>) -> Unit)? = null

    init {
        obtainStyledAttributes(attrs, defStyleAttr)
        orientation = VERTICAL
    }

    /**
     * Obtains styled view attributes
     *
     * @param attrs Any attribute values in the given AttributeSet
     * @param defStyleAttr The default style specified by defStyleAttr and defStyleRes
     */
    private fun obtainStyledAttributes(attrs: AttributeSet?, defStyleAttr: Int) {
        val typedArray = context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.Freya,
            defStyleAttr,
            0
        )

        try {
            val configRes = typedArray.getResourceId(
                R.styleable.Freya_config,
                Constants.INVALID_RES
            )

            fieldConfigs = Parser(context, configRes).parse()
        } catch (e: Exception) {
            // ignore
        } finally {
            typedArray.recycle()
        }
    }

    /**
     * Listens global layout changes to make sure the layout is completely rendered
     */
    private val onGlobalLayoutListener = object: ViewTreeObserver.OnGlobalLayoutListener {
        override fun onGlobalLayout() {
            handleViewChildren(this@FreyaForm)
            viewTreeObserver.removeOnGlobalLayoutListener(this)
        }
    }

    /**
     * Listens for form field validation changes
     */
    private val onFieldValidationListener: ((Field) -> Unit) = { field: Field ->
        onFieldValidationChangeListener?.invoke(field)
    }

    /**
     * Validates the whole form
     *
     * @return Is the form actually valid
     */
    fun validate(): Boolean {
        var isValid = true
        val errors = mutableListOf<Ruler>()

        fields.values.forEach { field ->
            field.validate()
            if (!field.isValid) {
                isValid = false
                field.error?.let { ruler ->
                    errors.add(ruler)
                }
            }
        }
        if (errors.isNotEmpty()) {
            onErrorListener?.invoke(errors.toList())
        }
        return isValid
    }

    /**
     * Iterates and separates form fields
     *
     * @param group layout container view
     */
    private fun handleViewChildren(group: ViewGroup) {
        for (i in 0 until group.childCount) {
            when (val child = group.getChildAt(i)) {
                is TextInputLayout -> handleTextInputLayout(child)
                is EditText -> handleEditText(child)
                is ViewGroup -> handleViewChildren(child)
            }
        }
    }

    /**
     * Handles TextInputLayout fields to setup
     *
     * @param input TextInputLayout view to handle
     */
    private fun handleTextInputLayout(input: TextInputLayout) {
        fieldConfigs[input.id]?.let { config ->
            val field = TextInputLayoutField(input, config)
            field.onValidationChangeListener = onFieldValidationListener
            field.onAttached()
            fields[input.id] = field
        }
    }

    /**
     * Handles EditText fields to setup
     *
     * @param input EditText view to handle
     */
    private fun handleEditText(input: EditText) {
        fieldConfigs[input.id]?.let { config ->
            val field = EditTextField(input, config)
            field.onValidationChangeListener = onFieldValidationListener
            field.onAttached()
            fields[input.id] = field
        }
    }

    /**
     * Setup data to pre-fill form fields
     *
     * @param values Map instance that holds field data in conjunction to their view id's
     */
    fun setup(values: Map<Int, Any?>) {
        post {
            values.forEach {
                if (it.value != null) {
                    fields[it.key]?.setValue(it.value)
                }
            }
        }
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        viewTreeObserver.addOnGlobalLayoutListener(onGlobalLayoutListener)
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        viewTreeObserver.removeOnGlobalLayoutListener(onGlobalLayoutListener)
        fields.values.forEach {
            it.onDetached()
        }
    }
}
