package me.zombii.luak.lua

import me.zombii.luak.LuaK
import me.zombii.luak.ffi.NativeAPI
import me.zombii.luak.util.LuaState
import java.util.concurrent.ConcurrentHashMap

class LuaValue(val type: LuaType) {

    enum class LuaType {
        NIL,
        BOOLEAN,
        LIGHTUSERDATA,
        NUMBER,
        STRING,
        TABLE,
        FUNCTION,
        USERDATA,
        THREAD
    }

    var object_value: Any? = null

    companion object {
        fun get(luaState: LuaState, idx: Int): LuaValue {
            val nativeAPI: NativeAPI = LuaK.nativeAPI;
            val typeIdx = nativeAPI.lua_type(luaState, idx)

            val value = LuaValue(LuaType.entries[typeIdx])
            objectValue(luaState, idx, value)
            return value
        }

        fun objectValue(state: LuaState, idx: Int, value: LuaValue) {
            val nativeAPI: NativeAPI = LuaK.nativeAPI;

            when (value.type) {
                LuaType.NIL -> value.object_value = null;
                LuaType.BOOLEAN -> value.object_value = (nativeAPI.lua_toboolean(state, idx) == 1) as Any
                LuaType.NUMBER -> value.object_value = nativeAPI.lua_tonumber(state, idx) as Any
                LuaType.STRING -> value.object_value = nativeAPI.lua_tostring(state, idx).getString(0) as Any
                LuaType.TABLE -> {
                    val map: MutableMap<Any?, Any?> = ConcurrentHashMap()
                    val size = nativeAPI.lua_rawlen(state, idx)
                    nativeAPI.lua_pushnil(state)
                    var hasElement = nativeAPI.lua_next(state, -2) != 0
                    while (hasElement) {
                        val key = get(state, -2)
                        val value = get(state, -1)
                        nativeAPI.lua_pop(state, 1)

                        map[key.object_value] = value.object_value

                        hasElement = nativeAPI.lua_next(state, -2) != 0
                    }

                    value.object_value = map
                }

                else -> {}
            }
        }
    }


    override fun toString(): String {
        return object_value.toString();
    }

    override fun hashCode(): Int {
        if (object_value == null) return 0
        return object_value.hashCode()
    }


}