package me.zombii.luak.ffi

import jnr.ffi.Pointer
import jnr.ffi.types.u_int64_t
import me.zombii.luak.types.functions.Lua_CFunction
import me.zombii.luak.util.LuaState

interface LUALIB_API : LUA_MACRO {

    val LUA_GNAME: String
        get() = "_G"

    val LUA_ERRFIOE: Int
        get() = (LUA.LUA_ERRERR + 1)

    // LUALIB_API void (luaL_checkversion_) (lua_State *L, lua_Number ver, size_t sz);
    fun luaL_checkversion_(luaState: LuaState, ver: Double, @u_int64_t size: Long)

    // LUALIB_API int (luaL_getmetafield) (lua_State *L, int obj, const char *e);
    fun luaL_getmetafield(luaState: LuaState, obj: Int, e: String)

    // LUALIB_API int (luaL_callmeta) (lua_State *L, int obj, const char *e);
    fun luaL_callmeta(luaState: LuaState, obj: Int, e: String)

    // LUALIB_API const char *(luaL_tolstring) (lua_State *L, int idx, size_t *len);
    fun luaL_tolstring(luaState: LuaState, idx: Int, len: Pointer): Pointer
    fun luaL_tolstring(luaState: LuaState, idx: Int, len: Long): Pointer

    // LUALIB_API int (luaL_argerror) (lua_State *L, int arg, const char *extramsg);
    fun luaL_argerror(luaState: LuaState, arg: Int, extramsg: String): Int

    // LUALIB_API int (luaL_typeerror) (lua_State *L, int arg, const char *tname);
    fun luaL_typeerror(luaState: LuaState, arg: Int, tname: String): Int

    // LUALIB_API const char *(luaL_checklstring) (lua_State *L, int arg, size_t *l);
    fun luaL_checklstring(luaState: LuaState, arg: Int, l: Pointer): Pointer

    // LUALIB_API const char *(luaL_optlstring) (lua_State *L, int arg, const char *def, size_t *l);
    fun luaL_optlstring(luaState: LuaState, arg: Int, def: String, l: Pointer): Pointer

    // LUALIB_API lua_Number (luaL_checknumber) (lua_State *L, int arg);
    fun luaL_checknumber(luaState: LuaState, arg: Int): Double

    // LUALIB_API lua_Number (luaL_optnumber) (lua_State *L, int arg, lua_Number def);
    fun luaL_optnumber(luaState: LuaState, arg: Int, def: Double): Double

    // LUALIB_API lua_Integer (luaL_checkinteger) (lua_State *L, int arg);
    fun luaL_checkinteger(luaState: LuaState, arg: Int): Long

    // LUALIB_API lua_Integer (luaL_optinteger) (lua_State *L, int arg, lua_Integer def);
    fun luaL_optinteger(luaState: LuaState, arg: Int, def: Long): Double

    // LUALIB_API void (luaL_checkstack) (lua_State *L, int sz, const char *msg);
    fun luaL_checkstack(luaState: LuaState, size: Int, msg: String)

    // LUALIB_API void (luaL_checktype) (lua_State *L, int arg, int t);
    fun luaL_checktype(luaState: LuaState, size: Int, t: Int)

    // LUALIB_API void (luaL_checkany) (lua_State *L, int arg);
    fun luaL_checkany(luaState: LuaState, arg: Int)

    // LUALIB_API int   (luaL_newmetatable) (lua_State *L, const char *tname);
    fun luaL_newmetatable(luaState: LuaState, tname: String): Int

    // LUALIB_API void  (luaL_setmetatable) (lua_State *L, const char *tname);
    fun luaL_setmetatable(luaState: LuaState, tname: String)

    // LUALIB_API void *(luaL_testudata) (lua_State *L, int ud, const char *tname);
    fun luaL_setmetatable(luaState: LuaState, ud: Int, tname: String): Pointer

    // LUALIB_API void *(luaL_checkudata) (lua_State *L, int ud, const char *tname);
    fun luaL_checkudata(luaState: LuaState, ud: Int, tname: String): Pointer

    // LUALIB_API void (luaL_where) (lua_State *L, int lvl);
    fun luaL_where(luaState: LuaState, lvl: Int)

    // LUALIB_API int (luaL_error) (lua_State *L, const char *fmt, ...);
    fun luaL_where(luaState: LuaState, fmt: String, args: Pointer): Int

    // LUALIB_API int (luaL_checkoption) (lua_State *L, int arg, const char *def, const char *const lst[]);
    fun luaL_checkoption(luaState: LuaState, arg: Int, def: String): Int

    // LUALIB_API int (luaL_fileresult) (lua_State *L, int stat, const char *fname);
    fun luaL_fileresult(luaState: LuaState, state: Int, fname: String): Int

    // LUALIB_API int (luaL_execresult) (lua_State *L, int stat);
    fun luaL_execresult(luaState: LuaState, state: Int): Int

    // LUALIB_API int (luaL_ref) (lua_State *L, int t);
    fun luaL_ref(luaState: LuaState, t: Int): Int

    // LUALIB_API void (luaL_unref) (lua_State *L, int t, int ref);
    fun luaL_unref(luaState: LuaState, t: Int, ref: Int)

    // LUALIB_API int (luaL_loadfilex) (lua_State *L, const char *filename, const char *mode);
    fun luaL_loadfilex(luaState: LuaState, filename: String, mode: String?): Int

    // LUALIB_API int (luaL_loadbufferx) (lua_State *L, const char *buff, size_t sz, const char *name, const char *mode);
    fun luaL_loadbufferx(luaState: LuaState, buff: String, @u_int64_t size: Long, name: String, mode: String): Int

    // LUALIB_API int (luaL_loadstring) (lua_State *L, const char *s);
    fun luaL_loadstring(luaState: LuaState, s: String): Int

    // LUALIB_API lua_State *(luaL_newstate) (void);
    fun luaL_newstate(): LuaState

    // LUALIB_API lua_Integer (luaL_len) (lua_State *L, int idx);
    fun luaL_len(luaState: LuaState, idx: Int): Long

    // LUALIB_API void (luaL_addgsub) (luaL_Buffer *b, const char *s, const char *p, const char *r);
    fun luaL_addgsub(luaState: LuaState, s: String, p: String, r: String)

    // LUALIB_API const char *(luaL_gsub) (lua_State *L, const char *s, const char *p, const char *r);
    fun luaL_gsub(luaState: LuaState, s: String, p: String, r: String): Pointer

    // LUALIB_API void (luaL_setfuncs) (lua_State *L, const luaL_Reg *l, int nup);
    fun luaL_setfuncs(luaState: LuaState, l: Pointer, nup: Int)

    // LUALIB_API int (luaL_getsubtable) (lua_State *L, int idx, const char *fname);
    fun luaL_getsubtable(luaState: LuaState, idx: Int, fname: String): Int

    // LUALIB_API void (luaL_traceback) (lua_State *L, lua_State *L1, const char *msg, int level);
    fun luaL_traceback(luaState: LuaState, luaState1: LuaState, msg: String, level: Int)

    // LUALIB_API void (luaL_requiref) (lua_State *L, const char *modname, lua_CFunction openf, int glb);
    fun luaL_requiref(luaState: LuaState, modname: String, openFunction: Lua_CFunction, glb: Int)
    fun luaL_requiref(luaState: LuaState, modname: String, openFunction: Pointer, glb: Int)

    // LUALIB_API void (luaL_buffinit) (lua_State *L, luaL_Buffer *B);
    fun luaL_buffinit(luaState: LuaState, buffer: Pointer)

    // LUALIB_API char *(luaL_prepbuffsize) (luaL_Buffer *B, size_t sz);
    fun luaL_prepbuffsize(buffer: Pointer, @u_int64_t size: Long): Pointer

    // LUALIB_API void (luaL_addlstring) (luaL_Buffer *B, const char *s, size_t l);
    fun luaL_addlstring(buffer: Pointer, s: String, @u_int64_t l: Long)

    // LUALIB_API void (luaL_addstring) (luaL_Buffer *B, const char *s);
    fun luaL_addlstring(buffer: Pointer, s: String)

    // LUALIB_API void (luaL_addvalue) (luaL_Buffer *B);
    fun luaL_addvalue(buffer: Pointer)

    // LUALIB_API void (luaL_pushresult) (luaL_Buffer *B);
    fun luaL_pushresult(buffer: Pointer)

    // LUALIB_API void (luaL_pushresultsize) (luaL_Buffer *B, size_t sz);
    fun luaL_pushresultsize(buffer: Pointer, @u_int64_t size: Long)

    // LUALIB_API char *(luaL_buffinitsize) (lua_State *L, luaL_Buffer *B, size_t sz);
    fun luaL_buffinitsize(luaState: LuaState, buffer: Pointer, @u_int64_t size: Long)

    // LUALIB_API void (luaL_openlibs) (lua_State *L);
    fun luaL_openlibs(luaState: LuaState)

}