package me.zombii.luak.lua.conv

import me.zombii.luak.LuaK
import me.zombii.luak.ffi.NativeAPI
import me.zombii.luak.types.functions.Lua_CFunction
import me.zombii.luak.util.LuaState
import java.util.concurrent.ConcurrentHashMap

class StaticObject<T>(val clazz: Class<T>, val name: String) {

    val staticMethodMap: MutableMap<String, Lua_CFunction> = ConcurrentHashMap()

    fun register(luaState: LuaState) {
        val nativeApi: NativeAPI = LuaK.nativeAPI;

        nativeApi.luaL_requiref(luaState, name, this::setup, 1)
        nativeApi.lua_pop(luaState, 1)
    }

    private fun setup(luaState: LuaState): Int {
        val nativeApi: NativeAPI = LuaK.nativeAPI;

        nativeApi.lua_createtable(luaState, 0, staticMethodMap.size - 1)
        for (entry in staticMethodMap) {
            nativeApi.lua_pushcclosure(luaState, entry.value, 0)
            nativeApi.lua_setfield(luaState, -2, entry.key)
        }

        nativeApi.lua_pushliteral(luaState, clazz.name)
        nativeApi.lua_setfield(luaState, -2, "class_name")

        return 1
    }

}