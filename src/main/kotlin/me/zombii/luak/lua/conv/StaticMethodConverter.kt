package me.zombii.luak.lua.conv

import me.zombii.luak.LuaK
import me.zombii.luak.ffi.NativeAPI
import me.zombii.luak.lua.LuaValue
import me.zombii.luak.types.functions.Lua_CFunction
import me.zombii.luak.util.LuaState
import java.lang.reflect.Method

object StaticMethodConverter {

    @JvmStatic
    fun <T> process_method(obj: StaticObject<T>, method: Method) {
        val methodName = method.name
        val argCount = method.parameterCount

        val func = Lua_CFunction lambda@{ state: LuaState ->
            val nativeAPI: NativeAPI = LuaK.nativeAPI;

            val doesReturn = if (method.returnType != Void.TYPE) 1 else 0

            if (nativeAPI.lua_gettop(state) != argCount) {
                println("Lua: Argument Count Error")
                for (i in 0 until argCount) {
                    nativeAPI.lua_pop(state, 1)
                }
                nativeAPI.lua_pushstring(state, "Argument Count Error")
                return@lambda 1
            }

            if (argCount == 0) {
                method.invoke(null)
                return@lambda doesReturn
            }

            val luaValues = MutableList(0) { _ -> LuaValue(LuaValue.LuaType.NIL) }

            for (i in 0 until argCount) {
                val lvalue = LuaValue.get(state, i + 1)
                luaValues.add(lvalue)
            }

            val paramValues = MutableList<Any?>(0) { _ -> null }

            for ((index, value) in luaValues.withIndex()) {
                val clazz = method.parameterTypes[index]
                if (value.type == LuaValue.LuaType.NUMBER) {
                    val oldVal = value.object_value
                    when (clazz) {
                        Byte, java.lang.Byte.TYPE, java.lang.Byte::class.java -> value.object_value = (value.object_value as Number).toByte()
                        Short, java.lang.Short.TYPE, java.lang.Short::class.java -> value.object_value = (value.object_value as Number).toShort()
                        Int, Integer.TYPE, Integer::class.java-> value.object_value = (value.object_value as Number).toInt()
                        Long, java.lang.Long.TYPE, java.lang.Long::class.java -> value.object_value = (value.object_value as Number).toLong()
                        Float, java.lang.Float.TYPE, java.lang.Float::class.java -> value.object_value = (value.object_value as Number).toFloat()
                        Double, java.lang.Double.TYPE, java.lang.Double::class.java -> value.object_value = (value.object_value as Number).toDouble()
                    }
                    if ((oldVal as Number).toDouble() != (value.object_value as Number).toDouble()) {
                        val msg = "Type mismatch between " + oldVal.javaClass.simpleName + " and " + value.object_value?.javaClass?.simpleName
                        nativeAPI.lua_pushstring(state, msg)
                        return@lambda 1
                    }
                }
                paramValues.add(value.object_value)
            }

            method.invoke(null, *paramValues.toTypedArray())

            return@lambda doesReturn
        }

        obj.staticMethodMap[methodName] = func
    }

}