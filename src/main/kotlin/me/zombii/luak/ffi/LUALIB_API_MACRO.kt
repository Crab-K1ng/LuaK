package me.zombii.luak.ffi

import jnr.ffi.Pointer
import jnr.ffi.Struct
import jnr.ffi.types.u_int64_t
import me.zombii.luak.LuaK
import me.zombii.luak.types.functions.Lua_CFunction
import me.zombii.luak.types.structs.LuaL_Reg
import me.zombii.luak.util.LuaState

interface LUALIB_API_MACRO : LUALIB_API {

    /*
    ** ===============================================================
    ** some useful macros
    ** ===============================================================
    */

    // #define luaL_loadfile(L,f)	luaL_loadfilex(L,f,NULL)
    fun luaL_loadfile(@u_int64_t luaState: LuaState, filename: String) =
        luaL_loadfilex(luaState, filename, null)

    // #define luaL_newlibtable(L,l)	\
    // lua_createtable(L, 0, sizeof(l)/sizeof((l)[0]) - 1)
    fun luaL_newlibtable(@u_int64_t luaState: LuaState, l: Pointer) {
        val size = (l.size() / Struct.size(LuaL_Reg::class.java)).toInt()
        lua_createtable(luaState, 0, size - 1)
    }

    // #define luaL_newlib(L,l)  \
    // (luaL_checkversion(L), luaL_newlibtable(L,l), luaL_setfuncs(L,l,0))
    fun luaL_newlib(@u_int64_t luaState: LuaState, l: Pointer) {
        luaL_newlibtable(luaState, l)
        luaL_setfuncs_impl(luaState, l, 0)
    }

    fun luaL_setfuncs_impl(@u_int64_t luaState: LuaState, l: Pointer, nup: Int) {
        var idx = 0L;
        val addrSize = l.runtime.addressSize().toLong();

        luaL_checkstack(luaState, nup, "too many upvalues")
        while (idx < l.size() && l.getAddress(idx) != 0L) {
            val name = l.getPointer(idx).getString(0);
            /* fill the table with given functions */
            idx += addrSize
            if ( l.getAddress(idx) == 0L )  /* placeholder? */
                lua_pushboolean(luaState, 0)
            else {
                var i = 0
                while (i < nup) {
                    /* copy upvalues to the top */
                    lua_pushvalue(luaState, -nup)
                    i++
                }
                lua_pushcclosure(luaState, LuaK.nativeAPI.getReferenceManager().get(l.getPointer(idx)) as Lua_CFunction, nup) /* closure with those upvalues */
            }
            lua_setfield(luaState, -(nup + 2), name)
            idx += addrSize
        }
        lua_pop(luaState, nup) /* remove upvalues */
    }

    // #define luaL_argcheck(L, cond,arg,extramsg)	\
    // ((void)(luai_likely(cond) || luaL_argerror(L, (arg), (extramsg))))

    // #define luaL_argexpected(L,cond,arg,tname)	\
    // ((void)(luai_likely(cond) || luaL_typeerror(L, (arg), (tname))))

    // #define luaL_checkstring(L,n)	(luaL_checklstring(L, (n), NULL))
    // #define luaL_optstring(L,n,d)	(luaL_optlstring(L, (n), (d), NULL))

    // #define luaL_typename(L,i)	lua_typename(L, lua_type(L,(i)))
    fun luaL_dofile(@u_int64_t luaState: LuaState, idx: Int) =
        lua_typename(luaState, lua_type(luaState, idx))

    // #define luaL_dofile(L, fn) \
    // (luaL_loadfile(L, fn) || lua_pcall(L, 0, LUA_MULTRET, 0))
    fun luaL_dofile(@u_int64_t luaState: LuaState, filename: String) {
        luaL_loadfile(luaState, filename)
        lua_pcall(luaState, 0, LUA.LUA_MULTIRET, 0)
    }

    // #define luaL_dostring(L, s) \
    // (luaL_loadstring(L, s) || lua_pcall(L, 0, LUA_MULTRET, 0))
    fun luaL_dostring(@u_int64_t luaState: LuaState, s: String) {
        luaL_loadstring(luaState, s)
        lua_pcall(luaState, 0, LUA.LUA_MULTIRET, 0)
    }

    // #define luaL_getmetatable(L,n)	(lua_getfield(L, LUA_REGISTRYINDEX, (n)))

    // #define luaL_opt(L,f,n,d)	(lua_isnoneornil(L,(n)) ? (d) : f(L,(n)))

    // #define luaL_loadbuffer(L,s,sz,n)	luaL_loadbufferx(L,s,sz,n,NULL)


    /*
    ** Perform arithmetic operations on lua_Integer values with wrap-around
    ** semantics, as the Lua core does.
    */
    // #define luaL_intop(op,v1,v2)  \
    // ((lua_Integer)((lua_Unsigned)(v1) op (lua_Unsigned)(v2)))


    /* push the value used to represent failure/error */
    // #define luaL_pushfail(L)	lua_pushnil(L)

}