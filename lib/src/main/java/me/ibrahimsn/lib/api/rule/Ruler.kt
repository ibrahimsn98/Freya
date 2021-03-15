package me.ibrahimsn.lib.api.rule

import android.util.Patterns

sealed class Ruler : Rule {

    class Required(override val param: Boolean) : Ruler() {

        override fun validate(data: Any?): Boolean {
            return !param || !data?.toString().isNullOrEmpty()
        }

        companion object {
            const val ATTR = "required"
        }
    }

    class MinSize(override val param: Int) : Ruler() {

        override fun validate(data: Any?): Boolean {
            return data.toString().isEmpty() || data.toString().length >= param
        }

        companion object {
            const val ATTR = "minSize"
        }
    }

    class MaxSize(override val param: Int) : Ruler() {

        override fun validate(data: Any?): Boolean {
            return data.toString().isEmpty() || data.toString().length <= param
        }

        companion object {
            const val ATTR = "maxSize"
        }
    }

    class Email(override val param: Boolean) : Ruler() {

        override fun validate(data: Any?): Boolean {
            return !param || data.toString().isEmpty() || (data != null && Patterns.EMAIL_ADDRESS
                .matcher(data.toString()).matches())
        }

        companion object {
            const val ATTR = "email"
        }
    }

    class PhoneNumber(override val param: Boolean) : Ruler() {

        override fun validate(data: Any?): Boolean {
            return !param || data.toString().isEmpty() || (data != null && Patterns.PHONE
                .matcher(data.toString()).matches())
        }

        companion object {
            const val ATTR = "phoneNumber"
        }
    }

    class Regex(override val param: String) : Ruler() {

        override fun validate(data: Any?): Boolean {
            return data.toString().isEmpty() || data.toString().matches(param.toRegex())
        }

        companion object {
            const val ATTR = "regex"
        }
    }
}
