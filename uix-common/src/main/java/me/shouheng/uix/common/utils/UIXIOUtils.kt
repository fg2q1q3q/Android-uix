package me.shouheng.uix.common.utils

import java.io.ByteArrayOutputStream
import java.io.Closeable
import java.io.IOException
import java.io.InputStream

/**
 * UIX io 工具类
 *
 * @author <a href="mailto:shouheng2015@gmail.com">WngShhng</a>
 * @version 2019-10-13 11:57
 */
object UIXIOUtils {

    private var sBufferSize = 8192

    fun is2Bytes(ins: InputStream?): ByteArray {
        if (ins == null) return ByteArray(0)
        var os: ByteArrayOutputStream? = null
        return try {
            os = ByteArrayOutputStream()
            val b = ByteArray(sBufferSize)
            var len: Int = ins.read(b, 0, sBufferSize)
            while (len != -1) {
                os.write(b, 0, len)
                len = ins.read(b, 0, sBufferSize)
            }
            os.toByteArray()
        } catch (e: IOException) {
            e.printStackTrace()
            ByteArray(0)
        } finally {
            safeCloseAll(ins, os)
        }
    }

    private fun safeCloseAll(vararg closeables: Closeable?) {
        for (closeable in closeables) {
            try {
                closeable?.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }
}
