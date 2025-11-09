package me.zombii.luak.util.lib

interface INamedLuaLibrary : ILuaLibrary {

    override fun getName(): String

}