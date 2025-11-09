package me.zombii.luak.ffi

import jnr.ffi.ObjectReferenceManager
import jnr.ffi.Runtime
import java.util.concurrent.atomic.AtomicReference

interface NativeAPI : LUA, LUA_MACRO, LUALIB_API, LUALIB_API_MACRO {

    companion object {
        val atomicManager: AtomicReference<ObjectReferenceManager<Object>> = AtomicReference(null)
    }

    fun getReferenceManager(): ObjectReferenceManager<Object> {
        if (atomicManager.get() != null) {
            return atomicManager.get()
        }

        val referenceManager: ObjectReferenceManager<Object> = Runtime.getRuntime(this).newObjectReferenceManager<Object>()
        atomicManager.set(referenceManager)
        return referenceManager
    }

}