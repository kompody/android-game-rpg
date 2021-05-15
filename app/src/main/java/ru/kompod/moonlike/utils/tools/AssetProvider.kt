// Copyright (c) 2021 Kompod. All rights reserved
// Description: todo

package ru.kompod.moonlike.utils.tools

import android.content.Context
import ru.kompod.moonlike.utils.extensions.kotlin.empty
import java.io.*
import java.nio.charset.Charset
import javax.inject.Inject

class AssetProvider @Inject constructor(private val context: Context) {
    companion object {
        private val UTF8: Charset = Charset.forName("UTF-8")
    }

    @Throws(IOException::class)
    fun loadFileFromAsset(assetName: String): File {
        val outFile = File(context.cacheDir, assetName)
        if (assetName.contains("/")) {
            outFile.parentFile.mkdirs()
        }
        copy(context.assets.open(assetName), outFile)
        return outFile
    }

    fun loadJSONFromAsset(fileName: String): String {
        return try {
            val inputStream: InputStream = context.assets.open(fileName)
            val size = inputStream.available()
            val buffer = ByteArray(size)
            inputStream.read(buffer)
            inputStream.close()
            String(buffer, UTF8)
        } catch (ex: IOException) {
            ex.printStackTrace()
            return String.empty
        }
    }

    @Throws(IOException::class)
    private fun copy(inputStream: InputStream, output: File) {
        var outputStream: OutputStream? = null
        try {
            outputStream = FileOutputStream(output)
            var read = 0
            val bytes = ByteArray(1024)
            while (inputStream.read(bytes).also { read = it } != -1) {
                outputStream.write(bytes, 0, read)
            }
        } finally {
            try {
                inputStream.close()
            } finally {
                outputStream?.close()
            }
        }
    }
}