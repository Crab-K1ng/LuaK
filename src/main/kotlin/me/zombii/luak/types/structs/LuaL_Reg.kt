package me.zombii.luak.types.structs

import jnr.ffi.Runtime
import jnr.ffi.Struct
import me.zombii.luak.types.functions.Lua_CFunction

class LuaL_Reg(runtime: Runtime) : Struct(runtime) {

    val namePtr: Struct.AsciiStringRef = AsciiStringRef()
    val funcPtr: Struct.Pointer = Pointer()

    fun getName(): kotlin.String? =
        this.namePtr.get()

    fun getFunc(): Lua_CFunction {
        return Lua_CFunction.fromRefPointer(this.funcPtr.get())
    }

    fun setName(name: kotlin.String) =
        this.namePtr.set(name)

    fun setFunc(func: Lua_CFunction) =
        this.funcPtr.set(Lua_CFunction.getRefPointer(runtime, func))

}