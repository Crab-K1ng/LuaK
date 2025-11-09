package me.zombii.luak.util.objects

import jnr.ffi.Pointer
import me.zombii.luak.LuaK
import me.zombii.luak.util.LuaState

object ObjectConverter {

    @JvmStatic
    fun pushObjectRef(luaState: LuaState, o: Object) {
        val ptr = LuaK.nativeAPI.getReferenceManager().add(o)
        LuaK.nativeAPI.lua_pushinteger(luaState, ptr.address().and(0xFFFFFFFF))
    }

    @JvmStatic
    fun <T> toObjectFromRef(luaState: LuaState, idx: Int): T {
        val num = LuaK.nativeAPI.lua_tointeger(luaState, idx)
        val ptr = Pointer.wrap(luaState.luaStatePtr.runtime, num.or(0xcafebabe.shl(32)))

        return LuaK.nativeAPI.getReferenceManager().get(ptr) as T;
    }

}