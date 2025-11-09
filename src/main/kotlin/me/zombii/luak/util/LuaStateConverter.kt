package me.zombii.luak.util

import jnr.ffi.Pointer
import jnr.ffi.mapper.DataConverter
import jnr.ffi.mapper.FromNativeContext
import jnr.ffi.mapper.FromNativeConverter
import jnr.ffi.mapper.FromNativeType
import jnr.ffi.mapper.SignatureType
import jnr.ffi.mapper.SignatureTypeMapper
import jnr.ffi.mapper.ToNativeContext
import jnr.ffi.mapper.ToNativeConverter
import jnr.ffi.mapper.ToNativeType

@ToNativeConverter.NoContext
@FromNativeConverter.NoContext
@ToNativeConverter.Cacheable
@FromNativeConverter.Cacheable

class LuaStateConverter : DataConverter<LuaState, Pointer>, SignatureTypeMapper {

    companion object {
        val INSTANCE = LuaStateConverter();
    }

    override fun toNative(
        value: LuaState,
        context: ToNativeContext?
    ): Pointer {
        return value.luaStatePtr
    }

    override fun fromNative(
        nativeValue: Pointer,
        context: FromNativeContext?
    ): LuaState {
        return LuaState(nativeValue)
    }

    override fun nativeType(): Class<Pointer> {
        return Pointer::class.java
    }

    override fun getFromNativeType(
        type: SignatureType?,
        context: FromNativeContext?
    ): FromNativeType? {
        val javaType = (type?.javaClass as Any).javaClass
        if (javaType == LuaState::class.java) {
            return FromNativeType {
                INSTANCE
            }
        }
        return null
    }

    override fun getToNativeType(
        type: SignatureType?,
        context: ToNativeContext?
    ): ToNativeType? {
        val javaType = (type?.javaClass as Any).javaClass
        if (javaType == LuaState::class.java) {
            return ToNativeType {
                INSTANCE
            }
        }
        return null
    }

}