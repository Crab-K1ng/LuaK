package me.zombii.luak.lua.conv

import java.lang.reflect.Modifier

object ClassConverter {

    @JvmStatic
    fun <T> process_class(clazz: Class<T>): StaticObject<T> {
        val obj = StaticObject(clazz, clazz.simpleName)

        for (method in clazz.methods) {
            val isStatic = method.modifiers.and(Modifier.STATIC) != 0

            if (isStatic) {
                StaticMethodConverter.process_method(obj, method)
                continue
            }
        }

        return obj
    }

}