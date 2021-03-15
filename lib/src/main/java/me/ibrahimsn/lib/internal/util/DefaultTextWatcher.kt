package me.ibrahimsn.lib.internal.util

import android.text.TextWatcher

internal abstract class DefaultTextWatcher : TextWatcher {

    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        // can be overridden
    }

    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        // can be overridden
    }
}
