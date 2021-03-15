package me.ibrahimsn.lib.api.rule

interface Rule {

    val param: Any?

    fun validate(data: Any?): Boolean
}
