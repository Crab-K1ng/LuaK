package me.zombii.luak

import jnr.ffi.Pointer
import me.zombii.luak.ffi.NativeAPI

class LuaStateN(val luaC: NativeAPI, val luaState: Pointer) {

    companion object {
    }

//    fun luaL_newlibtable(array: Map<String, Lua_CFunction>) {
//        this.lua_createtable(0, array.size - 1)
//    }
//
//    fun luaL_newlib(array: Map<String, Lua_CFunction>) {
//        this.lua_createtable(0, array.size - 1)
//        this.luaL_setfuncs(array, 0)
//    }

//    fun luaL_setfuncs(array: Map<String, Lua_CFunction>, nup: Int) {
//        this.luaC.luaL_checkstack(this.luaState, nup, "too many upvalues")
//        for (reg in array) {
//            for (i in 0 until nup)
//                this.luaC.lua_pushvalue(this.luaState, -nup)
//            this.luaC.lua_pushcclosure(this.luaState, reg.value, nup)
//            this.luaC.lua_setfield(this.luaState, -(nup + 2), reg.key)
//        }
//        this.luaC.lua_pop(this.luaState, nup)
//    }
//
//    fun lua_createtable(narray: Int, nrec: Int): Unit {
//        this.luaC.lua_createtable(this.luaState, narray, nrec)
//    }
//
//    fun close() {
//        this.luaC.lua_close(this.luaState)
//    }

}