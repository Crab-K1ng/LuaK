package me.zombii.luak.util.lib

import me.zombii.luak.LuaK
import me.zombii.luak.ffi.NativeAPI
import me.zombii.luak.types.functions.Lua_CFunction
import me.zombii.luak.util.LuaState
import java.lang.reflect.Method

interface ILuaLibrary {

    annotation class LuaMethod {}

    companion object {

        @JvmStatic
        fun expose(luaState: LuaState, library: ILuaLibrary) {
            val nativeApi: NativeAPI = LuaK.nativeAPI

            nativeApi.luaL_requiref(luaState, library.getName(), library::expose as Lua_CFunction, 1)
            nativeApi.lua_pop(luaState, 1)
        }

        fun exposeFunction(method: Method, nativeApi: NativeAPI, luaState: LuaState) {

        }

        fun functionInvoker(luaState: LuaState) {
            val nativeApi: NativeAPI = LuaK.nativeAPI;
            val argCount = nativeApi.lua_pop(luaState, 1)
            val signature = nativeApi.lua_tostring(luaState, 1).getString(0)
            nativeApi.lua_pop(luaState, 1)

            val parts = signature.split("~");
            val clazz = parts[0]
            val method = parts[1]
            val descriptor = parts[2]


        }

    }

    fun expose(luaState: LuaState)
    fun getName(): String

}

