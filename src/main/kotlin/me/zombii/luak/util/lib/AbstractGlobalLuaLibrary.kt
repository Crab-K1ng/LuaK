package me.zombii.luak.util.lib

import me.zombii.luak.LuaK
import me.zombii.luak.ffi.NativeAPI
import me.zombii.luak.util.LuaState

abstract class AbstractGlobalLuaLibrary : ILuaLibrary {

    constructor()

    override fun expose(luaState: LuaState) {
        val nativeApi: NativeAPI = LuaK.nativeAPI

        for (method in (AbstractGlobalLuaLibrary::class as Any).javaClass.methods) {
            ILuaLibrary.exposeFunction(method, nativeApi, luaState)
        }
    }

    @ILuaLibrary.LuaMethod
    fun println(test1: String, test2: Int): String {
        println("$test1 $test2")
        return test1
//        val output: StringBuilder = StringBuilder()
//        for (`object` in a) {
//            output.append(`object`)
//        }
//
//        return "Test"
    }

}