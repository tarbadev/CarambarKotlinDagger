package com.tarbadev.carambar.helper

import android.content.Context
import com.tarbadev.carambar.annotation.AllOpen
import java.io.FileNotFoundException
import javax.inject.Inject
import javax.inject.Named

@AllOpen
class InternalFileHelper @Inject constructor(@Named("ApplicationContext") val context: Context) {
    fun saveToFile(filename: String, expectedFileContent: String) {
        context.openFileOutput(filename, Context.MODE_PRIVATE).use {
            it.write(expectedFileContent.toByteArray())
        }
    }

    fun getFileContent(filename: String): String? {
        return try {
            String(context.openFileInput(filename).readBytes())
        } catch (e: FileNotFoundException) {
            null
        }
    }

}