package me.zombii.luak

import jnr.ffi.Pointer
import me.zombii.luak.types.functions.Lua_CFunction
import me.zombii.luak.types.functions.Lua_KFunction

interface LuaC {

    fun luaL_newstate(): Pointer
    fun lua_close(luaState: Pointer)
    fun lua_version(luaState: Pointer): Double
    fun lua_gc(luaState: Pointer, what: Int): Int

    //
    fun luaL_loadstring(luaState: Pointer, s: String): Int
    fun lua_pcallk(luaState: Pointer, nargs: Int, nresults: Int, errfunc: Int, ctx: Pointer, k: Lua_KFunction): Int
    fun lua_pcallk(luaState: Pointer, nargs: Int, nresults: Int, errfunc: Int, ctx: Pointer, k: Pointer): Int
    fun lua_pcall(luaState: Pointer, n: Int, r: Int, f: Int): Int {
        return lua_pcallk(luaState, n, r, f, Pointer.newIntPointer(luaState.runtime, 0), Pointer.newIntPointer(luaState.runtime, 0))
    }
    fun luaL_dostring(luaState: Pointer, s: String) {
        luaL_loadstring(luaState, s)
        lua_pcall(luaState, 0, -1, 0)
    }

    fun lua_pushnil(luaState: Pointer)
    fun lua_pushnumber(luaState: Pointer, luaNumber: Double)
    fun lua_pushinteger(luaState: Pointer, luaInteger: Long)
    fun lua_pushboolean(luaState: Pointer, luaBoolean: Int)
    fun lua_pushcclosure(luaState: Pointer, luaCFunction: Lua_CFunction, n: Int)
    fun lua_pushvalue(luaState: Pointer, idx: Int)
    fun lua_setfield(luaState: Pointer, idx: Int, name: String)
    fun lua_rawgeti(luaState: Pointer, idx: Int, n: Long)

    // stack-manipulation
    fun lua_absindex(luaState: Pointer, idx: Int): Int
    fun lua_gettop(luaState: Pointer): Int
    fun lua_settop(luaState: Pointer, idx: Int)
    fun lua_closeslot(luaState: Pointer, idx: Int)

    fun luaL_openselectedlibs(luaState: Pointer, load: Int, preload: Int)

    fun luaL_openlibs(luaState: Pointer) {
        luaL_openselectedlibs(luaState, -1, 0)
    }

    fun lua_pop(luaState: Pointer, n: Int) {
        lua_settop(luaState, -(n)-1)
    }

    fun lua_pushglobaltable(luaState: Pointer) {
        lua_rawgeti(luaState, -1000000 - 1000, 2)
    }

    fun luaL_requiref(luaState: Pointer, moduleName: CharSequence, luaCFunction: Lua_CFunction, glb: Int)

    fun luaopen_base(luaState: Pointer): Int
    fun lua_createtable(luaState: jnr.ffi.Pointer, narray: Int, nrec: Int)
    fun luaL_checkstack(luaState: jnr.ffi.Pointer, nup: Int, string: String)

    companion object {



    }

}