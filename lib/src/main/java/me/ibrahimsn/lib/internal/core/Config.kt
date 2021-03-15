package me.ibrahimsn.lib.internal.core

import me.ibrahimsn.lib.api.rule.Ruler

internal data class Config(
    val id: Int,
    val rulers: List<Ruler>
)
