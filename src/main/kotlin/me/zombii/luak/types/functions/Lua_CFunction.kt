package me.zombii.luak.types.functions

import jnr.ffi.Pointer
import jnr.ffi.Runtime
import jnr.ffi.annotations.Delegate
import me.zombii.luak.LuaK
import me.zombii.luak.util.LuaState

// typedef int (*lua_CFunction) (lua_State *L);
fun interface Lua_CFunction {

    companion object {

        @JvmStatic
        fun fromRefPointer(pointer: Pointer): Lua_CFunction =
            LuaK.nativeAPI.getReferenceManager().get(pointer) as Lua_CFunction

        fun getRefPointer(runtime: Runtime, func: Lua_CFunction): Pointer {
            val refManager = LuaK.nativeAPI.getReferenceManager()
            val ptr: Pointer = refManager.add(func as Object?) as Pointer
            return ptr
        }

    }

    fun invoke(luaState: LuaState): Int

    @Delegate
    fun invoke(luaState: Pointer): Int = invoke(LuaState.fromPointer(luaState))

}