package me.zombii.luak.ffi

import jnr.ffi.Pointer
import jnr.ffi.annotations.Out
import jnr.ffi.byref.PointerByReference
import jnr.ffi.types.int64_t
import jnr.ffi.types.u_int64_t
import me.zombii.luak.types.functions.Lua_Alloc
import me.zombii.luak.types.functions.Lua_CFunction
import me.zombii.luak.types.functions.Lua_KFunction
import me.zombii.luak.types.functions.Lua_Reader
import me.zombii.luak.types.functions.Lua_WarnFunction
import me.zombii.luak.types.functions.Lua_Writer
import me.zombii.luak.util.LuaState

interface LUA {

    companion object {
        @JvmStatic val LUA_MULTIRET: Int; get() = -1

        @JvmStatic val LUAI_MAXSTACK: Int; get() = 1000000
        @JvmStatic val LUA_REGISTRYINDEX: Int; get() = -LUAI_MAXSTACK - 1000

        @JvmStatic fun lua_upvalueindex(i: Int) = LUA_REGISTRYINDEX - i

        /* thread status */
        @JvmStatic val LUA_OK: Int; get() = 0
        @JvmStatic val LUA_YIELD: Int; get() = 1
        @JvmStatic val LUA_ERRRUN: Int; get() = 2
        @JvmStatic val LUA_ERRSYNTAX: Int; get() = 3
        @JvmStatic val LUA_ERRMEM: Int; get() = 4
        @JvmStatic val LUA_ERRERR: Int; get() = 5

        /*
        ** basic types
        */
        @JvmStatic val LUA_TNONE: Int; get() = -1

        @JvmStatic val LUA_TNIL: Int; get() = 0
        @JvmStatic val LUA_TBOOLEAN: Int; get() = 1
        @JvmStatic val LUA_TLIGHTUSERDATA: Int; get() = 2
        @JvmStatic val LUA_TNUMBER: Int; get() = 3
        @JvmStatic val LUA_TSTRING: Int; get() = 4
        @JvmStatic val LUA_TTABLE: Int; get() = 5
        @JvmStatic val LUA_TFUNCTION: Int; get() = 6
        @JvmStatic val LUA_TUSERDATA: Int; get() = 7
        @JvmStatic val LUA_TTHREAD: Int; get() = 8

        @JvmStatic val LUA_NUMTYPES: Int; get() = LUA_TTHREAD + 1

        /* minimum Lua stack available to a C function */
        @JvmStatic val LUA_MINSTACK: Int; get() = 20

        /* predefined values in the registry */
        @JvmStatic val LUA_RIDX_MAINTHREAD: Int; get() = 1
        @JvmStatic val LUA_RIDX_GLOBALS: Int; get() = 2
        @JvmStatic val LUA_RIDX_LAST: Int; get() = LUA_RIDX_GLOBALS

        @JvmStatic val LUA_OPADD: Int; get() = 0 /* ORDER TM, ORDER OP */
        @JvmStatic val LUA_OPSUB: Int; get() = 1
        @JvmStatic val LUA_OPMUL: Int; get() = 2
        @JvmStatic val LUA_OPMOD; get() = 3
        @JvmStatic val LUA_OPPOW; get() = 4
        @JvmStatic val LUA_OPDIV; get() = 5
        @JvmStatic val LUA_OPIDIV; get() = 6
        @JvmStatic val LUA_OPBAND; get() = 7
        @JvmStatic val LUA_OPBOR; get() = 8
        @JvmStatic val LUA_OPBXOR; get() = 9
        @JvmStatic val LUA_OPSHL; get() = 10
        @JvmStatic val LUA_OPSHR; get() = 11
        @JvmStatic val LUA_OPUNM; get() = 12
        @JvmStatic val LUA_OPBNOT; get() = 13

        @JvmStatic val LUA_OPEQ; get() = 0
        @JvmStatic val LUA_OPLT; get() = 1
        @JvmStatic val LUA_OPLE; get() = 2

        @JvmStatic val LUA_GCSTOP; get() = 0
        @JvmStatic val LUA_GCRESTART; get() = 1
        @JvmStatic val LUA_GCCOLLECT; get() = 2
        @JvmStatic val LUA_GCCOUNT; get() = 3
        @JvmStatic val LUA_GCCOUNTB; get() = 4
        @JvmStatic val LUA_GCSTEP; get() = 5
        @JvmStatic val LUA_GCSETPAUSE; get() = 6
        @JvmStatic val LUA_GCSETSTEPMUL; get() = 7
        @JvmStatic val LUA_GCISRUNNING; get() = 9
        @JvmStatic val LUA_GCGEN; get() = 10
        @JvmStatic val LUA_GCINC; get() = 11

    }

    /*
    ** state manipulation
    */

    //LUA_API lua_State *(lua_newstate) (lua_Alloc f, void *ud);
    fun lua_newstate(f: Lua_Alloc, ud: Pointer): LuaState

    //LUA_API void       (lua_close) (lua_State *L);
    fun lua_close(luaState: LuaState)

    //LUA_API lua_State *(lua_newthread) (lua_State *L);
    fun lua_newthread(luaState: LuaState): Pointer

    //LUA_API int        (lua_closethread) (lua_State *L, lua_State *from);
    fun lua_closethread(luaState: LuaState, fromluaState: LuaState): Int

    //LUA_API int        (lua_resetthread) (lua_State *L);  /* Deprecated! */
    @Deprecated("deprecated within lua")
    fun lua_resetthread(luaState: LuaState): Int

    //LUA_API lua_CFunction (lua_atpanic) (lua_State *L, lua_CFunction panicf);
    fun lua_atpanic(luaState: LuaState, panicF: Lua_CFunction): Lua_CFunction

    //LUA_API lua_Number (lua_version) (lua_State *L);
    fun lua_version(luaState: LuaState): Double

    /*
    ** basic stack manipulation
    */

    //LUA_API int   (lua_absindex) (lua_State *L, int idx);
    fun lua_absindex(luaState: LuaState, idx: Int): Int

    //LUA_API int   (lua_gettop) (lua_State *L);
    fun lua_gettop(luaState: LuaState): Int

    //LUA_API void  (lua_settop) (lua_State *L, int idx);
    fun lua_settop(luaState: LuaState, idx: Int)

    //LUA_API void  (lua_pushvalue) (lua_State *L, int idx);
    fun lua_pushvalue(luaState: LuaState, idx: Int)

    //LUA_API void  (lua_rotate) (lua_State *L, int idx, int n);
    fun lua_rotate(luaState: LuaState, idx: Int, n: Int)

    //LUA_API void  (lua_copy) (lua_State *L, int fromidx, int toidx);
    fun lua_copy(luaState: LuaState, fromIdx: Int, toIdx: Int)

    //LUA_API int   (lua_checkstack) (lua_State *L, int n);
    fun lua_checkstack(luaState: LuaState, n: Int): Int

    //LUA_API void  (lua_xmove) (lua_State *from, lua_State *to, int n);
    fun lua_xmove(fromLuaState: LuaState, toLuaState: LuaState, n: Int)

    /*
    ** access functions (stack -> C)
    */

    //LUA_API int             (lua_isnumber) (lua_State *L, int idx);
    fun lua_isnumber(luaState: LuaState, idx: Int): Int

    //LUA_API int             (lua_isstring) (lua_State *L, int idx);
    fun lua_isstring(luaState: LuaState, idx: Int): Int

    //LUA_API int             (lua_iscfunction) (lua_State *L, int idx);
    fun lua_iscfunction(luaState: LuaState, idx: Int): Int

    //LUA_API int             (lua_isinteger) (lua_State *L, int idx);
    fun lua_isinteger(luaState: LuaState, idx: Int): Int

    //LUA_API int             (lua_isuserdata) (lua_State *L, int idx);
    fun lua_isuserdata(luaState: LuaState, idx: Int): Int

    //LUA_API int             (lua_type) (lua_State *L, int idx);
    fun lua_type(luaState: LuaState, idx: Int): Int

    //LUA_API const char     *(lua_typename) (lua_State *L, int tp);
    fun lua_typename(luaState: LuaState, tp: Int): Pointer

    //LUA_API lua_Number      (lua_tonumberx) (lua_State *L, int idx, int *isnum);
    fun lua_tonumberx(luaState: LuaState, idx: Int, isnum: Pointer): Double
    fun lua_tonumberx(luaState: LuaState, idx: Int, @u_int64_t isnum: Long): Double

    //LUA_API lua_Integer     (lua_tointegerx) (lua_State *L, int idx, int *isnum);
    fun lua_tointegerx(luaState: LuaState, idx: Int, isnum: Pointer): Long
    fun lua_tointegerx(luaState: LuaState, idx: Int, @u_int64_t isnum: Long): Long

    //LUA_API int             (lua_toboolean) (lua_State *L, int idx);
    fun lua_toboolean(luaState: LuaState, idx: Int): Int

    //LUA_API const char     *(lua_tolstring) (lua_State *L, int idx, size_t *len);
    fun lua_tolstring(luaState: LuaState, idx: Int, len: Pointer): Pointer
    fun lua_tolstring(luaState: LuaState, idx: Int, @u_int64_t len: Long): Pointer

    //LUA_API lua_Unsigned    (lua_rawlen) (lua_State *L, int idx);
    @u_int64_t fun lua_rawlen(luaState: LuaState, idx: Int): Long

    //LUA_API lua_CFunction   (lua_tocfunction) (lua_State *L, int idx);
    fun lua_tocfunction(luaState: LuaState, idx: Int): Lua_CFunction

    //LUA_API void	       *(lua_touserdata) (lua_State *L, int idx);
    fun lua_touserdata(luaState: LuaState, idx: Int): Pointer

    //LUA_API lua_State      *(lua_tothread) (lua_State *L, int idx);
    fun lua_tothread(luaState: LuaState, idx: Int): Pointer

    //LUA_API const void     *(lua_topointer) (lua_State *L, int idx);
    fun lua_topointer(luaState: LuaState, idx: Int): Pointer

    /*
    ** Comparison and arithmetic functions
    */

    // LUA_API void  (lua_arith) (lua_State *L, int op);
    fun lua_arith(luaState: LuaState, op: Int)

    // LUA_API int   (lua_rawequal) (lua_State *L, int idx1, int idx2);
    fun lua_rawequal(luaState: LuaState, idx1: Int, idx2: Int): Int

    // LUA_API int   (lua_compare) (lua_State *L, int idx1, int idx2, int op);
    fun lua_compare(luaState: LuaState, idx1: Int, idx2: Int, op: Int): Int

    /*
    ** push functions (C -> stack)
    */

    // LUA_API void        (lua_pushnil) (lua_State *L);
    fun lua_pushnil(luaState: LuaState)

    // LUA_API void        (lua_pushnumber) (lua_State *L, lua_Number n);
    fun lua_pushnumber(luaState: LuaState, n: Double)

    // LUA_API void        (lua_pushinteger) (lua_State *L, lua_Integer n);
    fun lua_pushinteger(luaState: LuaState, n: Long)

    // LUA_API const char *(lua_pushlstring) (lua_State *L, const char *s, size_t len);
    fun lua_pushlstring(luaState: LuaState, s: String, @u_int64_t len: Long): Pointer

    // LUA_API const char *(lua_pushstring) (lua_State *L, const char *s);
    fun lua_pushstring(luaState: LuaState, s: String): Pointer

    // LUA_API const char *(lua_pushvfstring) (lua_State *L, const char *fmt, va_list argp);
    fun lua_pushvfstring(luaState: LuaState, fmt: String, argp: Pointer): Pointer

    // LUA_API const char *(lua_pushfstring) (lua_State *L, const char *fmt, ...);
    fun lua_pushvfstring(luaState: LuaState, fmt: String, vararg args: Object): Pointer

    // LUA_API void  (lua_pushcclosure) (lua_State *L, lua_CFunction fn, int n);
    fun lua_pushcclosure(luaState: LuaState, fn: Lua_CFunction, n: Int)
    fun lua_pushcclosure(luaState: LuaState, fn: Pointer, n: Int)

    // LUA_API void  (lua_pushboolean) (lua_State *L, int b);
    fun lua_pushboolean(luaState: LuaState, b: Int)

    // LUA_API void  (lua_pushlightuserdata) (lua_State *L, void *p);
    fun lua_pushlightuserdata(luaState: LuaState, p: Pointer)

    // LUA_API int   (lua_pushthread) (lua_State *L);
    fun lua_pushthread(luaState: LuaState): Int

    /*
    ** get functions (Lua -> stack)
    */

    // LUA_API int (lua_getglobal) (lua_State *L, const char *name);
    fun lua_getglobal(luaState: LuaState, name: String): Int

    // LUA_API int (lua_gettable) (lua_State *L, int idx);
    fun lua_gettable(luaState: LuaState, idx: Int): Int

    // LUA_API int (lua_getfield) (lua_State *L, int idx, const char *k);
    fun lua_gettable(luaState: LuaState, idx: Int, k: String): Int

    // LUA_API int (lua_geti) (lua_State *L, int idx, lua_Integer n);
    fun lua_geti(luaState: LuaState, idx: Int, n: Long): Int

    // LUA_API int (lua_rawget) (lua_State *L, int idx);
    fun lua_rawget(luaState: LuaState, idx: Int): Int

    // LUA_API int (lua_rawgeti) (lua_State *L, int idx, lua_Integer n);
    fun lua_rawgeti(luaState: LuaState, idx: Int, n: Long): Int

    // LUA_API int (lua_rawgetp) (lua_State *L, int idx, const void *p);
    fun lua_rawgetp(luaState: LuaState, idx: Int, p: Pointer): Int

    // LUA_API void  (lua_createtable) (lua_State *L, int narr, int nrec);
    fun lua_createtable(luaState: LuaState, narr: Int, nrec: Int)

    // LUA_API void *(lua_newuserdatauv) (lua_State *L, size_t sz, int nuvalue);
    fun lua_newuserdatauv(luaState: LuaState, @u_int64_t sz: Long, nuvalue: Int): Pointer

    // LUA_API int   (lua_getmetatable) (lua_State *L, int objindex);
    fun lua_getmetatable(luaState: LuaState, objIndex: Int): Int

    // LUA_API int  (lua_getiuservalue) (lua_State *L, int idx, int n);
    fun lua_getiuservalue(luaState: LuaState, idx: Int, n: Int): Int

    /*
    ** set functions (stack -> Lua)
    */

    // LUA_API void  (lua_setglobal) (lua_State *L, const char *name);
    fun lua_setglobal(luaState: LuaState, name: String)

    // LUA_API void  (lua_settable) (lua_State *L, int idx);
    fun lua_settable(luaState: LuaState, idx: Int)

    // LUA_API void  (lua_setfield) (lua_State *L, int idx, const char *k);
    fun lua_setfield(luaState: LuaState, idx: Int, k: String)

    // LUA_API void  (lua_seti) (lua_State *L, int idx, lua_Integer n);
    fun lua_seti(luaState: LuaState, idx: Int, n: Long)

    // LUA_API void  (lua_rawset) (lua_State *L, int idx);
    fun lua_rawset(luaState: LuaState, idx: Int)

    // LUA_API void  (lua_rawseti) (lua_State *L, int idx, lua_Integer n);
    fun lua_rawseti(luaState: LuaState, idx: Int, n: Long)

    // LUA_API void  (lua_rawsetp) (lua_State *L, int idx, const void *p);
    fun lua_rawsetp(luaState: LuaState, idx: Int, p: Pointer)

    // LUA_API int   (lua_setmetatable) (lua_State *L, int objindex);
    fun lua_setmetatable(luaState: LuaState, objindex: Int): Int

    // LUA_API int   (lua_setiuservalue) (lua_State *L, int idx, int n);
    fun lua_setmetatable(luaState: LuaState, idx: Int, n: Int): Int

    /*
    ** 'load' and 'call' functions (load and run Lua code)
    */

    // LUA_API void  (lua_callk) (lua_State *L, int nargs, int nresults, lua_KContext ctx, lua_KFunction k);
    fun lua_callk(luaState: LuaState, nargs: Int, nresults: Int, ctx: Pointer, k: Lua_KFunction)
    fun lua_callk(luaState: LuaState, nargs: Int, nresults: Int, @u_int64_t ctx: Long, @u_int64_t k: Long)

    // LUA_API int   (lua_pcallk) (lua_State *L, int nargs, int nresults, int errfunc, lua_KContext ctx, lua_KFunction k);
    fun lua_pcallk(luaState: LuaState, nargs: Int, nresults: Int, errfunc: Int, ctx: Pointer, k: Lua_KFunction): Int
    fun lua_pcallk(luaState: LuaState, nargs: Int, nresults: Int, errfunc: Int, @u_int64_t ctx: Long, @u_int64_t k: Long): Int

    // LUA_API int   (lua_load) (lua_State *L, lua_Reader reader, void *dt, const char *chunkname, const char *mode);
    fun lua_load(luaState: LuaState, reader: Lua_Reader, dt: Pointer, chunkname: String, mode: String): Int

    // LUA_API int (lua_dump) (lua_State *L, lua_Writer writer, void *data, int strip);
    fun lua_dump(luaState: LuaState, writer: Lua_Writer, data: Pointer, strip: Int): Int

    /*
    ** coroutine functions
    */

    // LUA_API int  (lua_yieldk)     (lua_State *L, int nresults, lua_KContext ctx, lua_KFunction k);
    fun lua_yieldk(luaState: LuaState, nresults: Int, ctx: Pointer, k: Lua_KFunction): Int
    fun lua_yieldk(luaState: LuaState, nresults: Int, @u_int64_t ctx: Long, @u_int64_t k: Long): Int

    // LUA_API int  (lua_resume)     (lua_State *L, lua_State *from, int narg, int *nres);
    fun lua_resume(luaState: LuaState, fromLuaState: LuaState, narg: Int, nres: Pointer): Int

    // LUA_API int  (lua_status)     (lua_State *L);
    fun lua_status(luaState: LuaState): Int

    // LUA_API int (lua_isyieldable) (lua_State *L);
    fun lua_isyieldable(luaState: LuaState): Int

    /*
    ** Warning-related functions
    */

    // LUA_API void (lua_setwarnf) (lua_State *L, lua_WarnFunction f, void *ud);
    fun lua_setwarnf(luaState: LuaState, f: Lua_WarnFunction, ud: Pointer)

    // LUA_API void (lua_warning)  (lua_State *L, const char *msg, int tocont);
    fun lua_warning(luaState: LuaState, msg: String, tocont: String)

    /*
    ** garbage-collection function and options
    */

    // LUA_API int (lua_gc) (lua_State *L, int what, ...);
    fun lua_warning(luaState: LuaState, what: Int, vararg args: Object): Int

    /*
    ** miscellaneous functions
    */

    // LUA_API int   (lua_error) (lua_State *L);
    fun lua_error(luaState: LuaState): Int

    // LUA_API int   (lua_next) (lua_State *L, int idx);
    fun lua_next(luaState: LuaState, idx: Int): Int

    // LUA_API void  (lua_concat) (lua_State *L, int n);
    fun lua_concat(luaState: LuaState, n: Int)

    // LUA_API void  (lua_len)    (lua_State *L, int idx);
    fun lua_len(luaState: LuaState, idx: Int)

    // LUA_API size_t   (lua_stringtonumber) (lua_State *L, const char *s);
    @u_int64_t fun lua_stringtonumber(luaState: LuaState, s: String): Long

    // LUA_API lua_Alloc (lua_getallocf) (lua_State *L, void **ud);
    fun lua_getallocf(luaState: LuaState, @Out ud: PointerByReference): Lua_Alloc

    // LUA_API void      (lua_setallocf) (lua_State *L, lua_Alloc f, void *ud);
    fun lua_setallocf(luaState: LuaState, f: Lua_Alloc, ud: Pointer)

    // LUA_API void (lua_toclose) (lua_State *L, int idx);
    fun lua_toclose(luaState: LuaState, idx: Int)

    // LUA_API void (lua_closeslot) (lua_State *L, int idx);
    fun lua_closeslot(luaState: LuaState, idx: Int)

}