package me.zombii.luak.types.functions

import jnr.ffi.Pointer
import jnr.ffi.annotations.Delegate
import me.zombii.luak.LuaK
import me.zombii.luak.util.LuaState

// typedef const char * (*lua_Reader) (lua_State *L, void *ud, size_t *sz);
interface Lua_Reader {

    companion object {

        @JvmStatic
        fun fromPointer(pointer: Pointer): Lua_Reader =
            LuaK.nativeAPI.getReferenceManager().get(pointer) as Lua_Reader

    }

    fun invoke(luaState: LuaState, ud: Pointer, size: Pointer): Pointer

    @Delegate
    fun invoke(luaState: Pointer, ud: Pointer, size: Pointer): Pointer = invoke(LuaState.fromPointer(luaState), ud, size)

}