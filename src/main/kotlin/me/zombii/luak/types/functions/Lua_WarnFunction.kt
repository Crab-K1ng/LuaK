package me.zombii.luak.types.functions

import jnr.ffi.Pointer
import jnr.ffi.annotations.Delegate
import me.zombii.luak.LuaK

// typedef void (*lua_WarnFunction) (void *ud, const char *msg, int tocont);
interface Lua_WarnFunction {

    companion object {

        @JvmStatic
        fun fromPointer(pointer: Pointer): Lua_WarnFunction =
            LuaK.nativeAPI.getReferenceManager().get(pointer) as Lua_WarnFunction

    }

    @Delegate
    fun invoke(ud: Pointer, msg: String, tocont: Int)

}