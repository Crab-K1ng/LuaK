package me.zombii.luak.types.functions

import jnr.ffi.Pointer
import jnr.ffi.annotations.Delegate
import jnr.ffi.types.u_int64_t
import me.zombii.luak.LuaK
import me.zombii.luak.util.LuaState

// typedef int (*lua_Writer) (lua_State *L, const void *p, size_t sz, void *ud);
interface Lua_Writer {

    companion object {

        @JvmStatic
        fun fromPointer(pointer: Pointer): Lua_Writer =
            LuaK.nativeAPI.getReferenceManager().get(pointer) as Lua_Writer

    }

    fun invoke(luaState: LuaState, p: Pointer, @u_int64_t size: Long, ud: Pointer): Int

    @Delegate
    fun invoke(luaState: Pointer, p: Pointer, @u_int64_t size: Long, ud: Pointer): Int = invoke(LuaState.fromPointer(luaState), p, size, ud)

}