package com.tarbadev.carambar.repository

import android.content.Context
import com.google.gson.Gson
import com.tarbadev.carambar.annotation.AllOpen
import java.io.FileNotFoundException
import java.lang.reflect.Type

@AllOpen
class InternalFileRepository<T>(
    private val fileName: String,
    private val context: Context,
    val entityType: Type
) {
    fun read(): T? {
        return try {
            Gson().fromJson<T>(
                String(context.openFileInput(fileName).readBytes()),
                entityType
            )
        } catch (e: FileNotFoundException) {
            null
        }
    }

    fun save(entity: T): T {
        context.openFileOutput(fileName, Context.MODE_PRIVATE).use {
            it.write(Gson().toJson(entity).toByteArray())
        }

        return entity
    }

    fun delete() {
        context.deleteFile(fileName)
    }
}
