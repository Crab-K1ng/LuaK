package me.zombii.luak.util

import jnr.ffi.Pointer
import java.util.Map
import java.util.concurrent.ConcurrentHashMap

class LuaState(var luaStatePtr: Pointer) {

    companion object {
        private val cache: Map<Long, LuaState> = ConcurrentHashMap<Long, LuaState>() as Map<Long, LuaState>

        fun fromPointer(ptr: Pointer): LuaState {
            if (cache.containsKey(ptr.address())) return cache.get(ptr.address())
            val state = LuaState(ptr);
            cache.put(ptr.address(), state)
            return state;
        }
    }

    init {
        cache.put(luaStatePtr.address(), this)
    }

}