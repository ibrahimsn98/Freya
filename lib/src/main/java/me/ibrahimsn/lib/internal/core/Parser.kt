package me.ibrahimsn.lib.internal.core

import android.content.Context
import android.content.res.Resources
import android.content.res.XmlResourceParser
import androidx.annotation.XmlRes
import me.ibrahimsn.lib.api.rule.Ruler
import me.ibrahimsn.lib.internal.Constants
import me.ibrahimsn.lib.internal.Constants.FIELD_TAG
import me.ibrahimsn.lib.internal.Constants.ID_ATTR

internal class Parser(context: Context, @XmlRes res: Int) {

    private val parser: XmlResourceParser = context.resources.getXml(res)

    fun parse(): Map<Int, Config> {
        val items: MutableMap<Int, Config> = mutableMapOf()
        var eventType: Int?

        do {
            eventType = parser.next()
            if (eventType == XmlResourceParser.START_TAG && parser.name == FIELD_TAG) {
                getFieldConfig(parser).let {
                    items[it.id] = it
                }
            }
        } while (eventType != XmlResourceParser.END_DOCUMENT)

        return items
    }

    private fun getFieldConfig(parser: XmlResourceParser): Config {
        var fieldId = Constants.INVALID_RES
        val rulers = mutableListOf<Ruler>()

        for (index in 0 until parser.attributeCount) {
            try {
                when (parser.getAttributeName(index)) {
                    ID_ATTR -> {
                        fieldId = parser.getAttributeResourceValue(index, 0)
                    }
                    Ruler.Required.ATTR -> {
                        rulers.add(
                            Ruler.Required(parser.getAttributeBooleanValue(index, false))
                        )
                    }
                    Ruler.Email.ATTR -> {
                        rulers.add(
                            Ruler.Email(parser.getAttributeBooleanValue(index, false))
                        )
                    }
                    Ruler.PhoneNumber.ATTR -> {
                        rulers.add(
                            Ruler.PhoneNumber(parser.getAttributeBooleanValue(index, false))
                        )
                    }
                    Ruler.MinSize.ATTR -> {
                        rulers.add(
                            Ruler.MinSize(parser.getAttributeIntValue(index, 0))
                        )
                    }
                    Ruler.MaxSize.ATTR -> {
                        rulers.add(
                            Ruler.MaxSize(parser.getAttributeIntValue(index, 0))
                        )
                    }
                    Ruler.Regex.ATTR -> {
                        rulers.add(
                            Ruler.Regex(parser.getAttributeValue(index))
                        )
                    }
                }
            } catch (e: Resources.NotFoundException) {
                // ignored
            }
        }
        return Config(fieldId, rulers)
    }
}