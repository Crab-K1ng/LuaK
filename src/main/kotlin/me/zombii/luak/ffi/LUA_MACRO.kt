package me.zombii.luak.ffi

import jnr.ffi.Pointer
import jnr.ffi.Runtime
import jnr.ffi.provider.LoadedLibrary
import me.zombii.luak.types.functions.Lua_CFunction
import me.zombii.luak.util.LuaState

interface LUA_MACRO : LUA {

    /*
    ** 'load' and 'call' functions (load and run Lua code)
    */

    // #define lua_call(L,n,r)		lua_callk(L, (n), (r), 0, NULL)
    fun lua_call(luaState: LuaState, nargs: Int, nresults: Int) =
        lua_callk(luaState, nargs, nresults, 0, 0)

    // #define lua_pcall(L,n,r,f)	lua_pcallk(L, (n), (r), (f), 0, NULL)
    fun lua_pcall(luaState: LuaState, nargs: Int, nresults: Int, errfunc: Int) =
        lua_pcallk(luaState, nargs, nresults, errfunc, 0, 0)

    /*
    ** coroutine functions
    */

    // #define lua_yield(L,n)		lua_yieldk(L, (n), 0, NULL)
    fun lua_yield(luaState: LuaState, nresults: Int): Int =
        lua_yieldk(luaState, nresults, 0, 0)

    /*
    ** {==============================================================
    ** some useful macros
    ** ===============================================================
    */

    // #define lua_getextraspace(L)	((void *)((char *)(L) - LUA_EXTRASPACE))
    fun lua_getextraspace(luaState: LuaState): Pointer {
        val lib: LoadedLibrary = this as LoadedLibrary
        val runtime: Runtime = lib.runtime;

        return runtime.memoryManager.newPointer(luaState.luaStatePtr.getAddress((-runtime.addressSize()).toLong()))
    }

    // #define lua_tonumber(L,i)	lua_tonumberx(L,(i),NULL)
    fun lua_tonumber(luaState: LuaState, idx: Int): Double =
        lua_tonumberx(luaState, idx, 0);

    // #define lua_tointeger(L,i)	lua_tointegerx(L,(i),NULL)
    fun lua_tointeger(luaState: LuaState, idx: Int): Long =
        lua_tointegerx(luaState, idx, 0);

    // #define lua_pop(L,n)		lua_settop(L, -(n)-1)
    fun lua_pop(luaState: LuaState, idx: Int) =
        lua_settop(luaState, -idx-1);

    // #define lua_newtable(L)		lua_createtable(L, 0, 0)
    fun lua_newtable(luaState: LuaState) =
        lua_createtable(luaState, 0, 0);

    // #define lua_register(L,n,f) (lua_pushcfunction(L, (f)), lua_setglobal(L, (n)))
    fun lua_register(luaState: LuaState, name: String, f: Lua_CFunction) {
        lua_pushcfunction(luaState, f)
        lua_setglobal(luaState, name)
    }

    // #define lua_pushcfunction(L,f)	lua_pushcclosure(L, (f), 0)
    fun lua_pushcfunction(luaState: LuaState, f: Lua_CFunction) =
        lua_pushcclosure(luaState, f, 0)

    // #define lua_isfunction(L,n)	(lua_type(L, (n)) == LUA_TFUNCTION)
    fun lua_isfunction(luaState: LuaState, idx: Int): Boolean =
        lua_type(luaState, idx) == LUA.LUA_TFUNCTION

    // #define lua_istable(L,n)	(lua_type(L, (n)) == LUA_TTABLE)
    fun lua_istable(luaState: LuaState, idx: Int): Boolean =
        lua_type(luaState, idx) == LUA.LUA_TTABLE

    // #define lua_islightuserdata(L,n)	(lua_type(L, (n)) == LUA_TLIGHTUSERDATA)
    fun lua_islightuserdata(luaState: LuaState, idx: Int): Boolean =
        lua_type(luaState, idx) == LUA.LUA_TLIGHTUSERDATA

    // #define lua_isnil(L,n)		(lua_type(L, (n)) == LUA_TNIL)
    fun lua_isnil(luaState: LuaState, idx: Int): Boolean =
        lua_type(luaState, idx) == LUA.LUA_TNIL

    // #define lua_isboolean(L,n)	(lua_type(L, (n)) == LUA_TBOOLEAN)
    fun lua_isboolean(luaState: LuaState, idx: Int): Boolean =
        lua_type(luaState, idx) == LUA.LUA_TBOOLEAN

    // #define lua_isthread(L,n)	(lua_type(L, (n)) == LUA_TTHREAD)
    fun lua_isthread(luaState: LuaState, idx: Int): Boolean =
        lua_type(luaState, idx) == LUA.LUA_TTHREAD

    // #define lua_isnone(L,n)		(lua_type(L, (n)) == LUA_TNONE)
    fun lua_isnone(luaState: LuaState, idx: Int): Boolean =
        lua_type(luaState, idx) == LUA.LUA_TNONE

    // #define lua_isnoneornil(L, n)	(lua_type(L, (n)) <= 0)
    fun lua_isnoneornil(luaState: LuaState, idx: Int): Boolean =
        lua_type(luaState, idx) <= 0

    // #define lua_pushliteral(L, s)	lua_pushstring(L, "" s)
    fun lua_pushliteral(luaState: LuaState, s: String): Pointer =
        lua_pushstring(luaState, s)

    // #define lua_pushglobaltable(L)  \
    // ((void)lua_rawgeti(L, LUA_REGISTRYINDEX, LUA_RIDX_GLOBALS))
    fun lua_pushglobaltable(luaState: LuaState): Int =
        lua_rawgeti(luaState, LUA.LUA_REGISTRYINDEX, LUA.LUA_RIDX_GLOBALS.toLong())

    // #define lua_tostring(L,i)	lua_tolstring(L, (i), NULL)
    fun lua_tostring(luaState: LuaState, idx: Int): Pointer =
        lua_tolstring(luaState, idx, 0)

    // #define lua_insert(L,idx)	lua_rotate(L, (idx), 1)
    fun lua_insert(luaState: LuaState, idx: Int) =
        lua_rotate(luaState, idx, 1)

    // #define lua_remove(L,idx)	(lua_rotate(L, (idx), -1), lua_pop(L, 1))
    fun lua_remove(luaState: LuaState, idx: Int) {
        lua_rotate(luaState, idx, -1)
        lua_pop(luaState, 1)
    }

    // #define lua_replace(L,idx)	(lua_copy(L, -1, (idx)), lua_pop(L, 1))
    fun lua_replace(luaState: LuaState, idx: Int) {
        lua_copy(luaState, -1, idx)
        lua_pop(luaState, 1)
    }

}