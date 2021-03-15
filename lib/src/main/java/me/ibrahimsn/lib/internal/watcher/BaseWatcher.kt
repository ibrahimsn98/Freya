package me.ibrahimsn.lib.internal.watcher

import android.view.View

internal abstract class BaseWatcher<T> : Watcher {

    abstract val view: View

    abstract var listener: T?
}
