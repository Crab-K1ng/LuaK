package me.zombii.luak.types.functions

import jnr.ffi.Pointer
import jnr.ffi.annotations.Delegate
import jnr.ffi.types.u_int64_t
import me.zombii.luak.LuaK

// typedef void * (*lua_Alloc) (void *ud, void *ptr, size_t osize, size_t nsize);
interface Lua_Alloc {

    companion object {

        @JvmStatic
        fun fromPointer(pointer: Pointer): Lua_Alloc =
            LuaK.nativeAPI.getReferenceManager().get(pointer) as Lua_Alloc

    }

    @Delegate
    fun invoke(ud: Pointer, ptr: Pointer, @u_int64_t osize: Long, @u_int64_t nsize: Long): Pointer

}