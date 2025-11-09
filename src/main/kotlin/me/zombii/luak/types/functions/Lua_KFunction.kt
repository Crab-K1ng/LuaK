package me.zombii.luak.types.functions

import jnr.ffi.Pointer
import jnr.ffi.annotations.Delegate
import me.zombii.luak.LuaK
import me.zombii.luak.util.LuaState

// typedef int (*lua_KFunction) (lua_State *L, int status, lua_KContext ctx);
interface Lua_KFunction {

    companion object {

        @JvmStatic
        fun fromPointer(pointer: Pointer): Lua_KFunction =
            LuaK.nativeAPI.getReferenceManager().get(pointer) as Lua_KFunction

    }

    fun invoke(luaState: LuaState, status: Int, ctx: Pointer): Int

    @Delegate
    fun invoke(luaState: Pointer, status: Int, ctx: Pointer): Int = invoke(LuaState.fromPointer(luaState), status, ctx)

}