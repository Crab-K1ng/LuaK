package me.zombii.luak.types.functions

import jnr.ffi.Pointer
import jnr.ffi.annotations.Delegate
import me.zombii.luak.LuaK
import me.zombii.luak.util.LuaState

// typedef void (*lua_Hook) (lua_State *L, lua_Debug *ar);
interface Lua_Hook {

    companion object {

        @JvmStatic
        fun fromPointer(pointer: Pointer): Lua_Hook =
            LuaK.nativeAPI.getReferenceManager().get(pointer) as Lua_Hook
    }

    fun invoke(luaState: LuaState, ar: Pointer)

    @Delegate
    fun invoke(luaState: Pointer, ar: Pointer) = invoke(LuaState.fromPointer(luaState), ar)

}