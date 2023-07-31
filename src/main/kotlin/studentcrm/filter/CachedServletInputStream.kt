package studentcrm.filter

import jakarta.servlet.ReadListener
import jakarta.servlet.ServletInputStream
import mu.KotlinLogging
import java.io.ByteArrayInputStream
import java.io.IOException
import java.io.InputStream


class CachedServletInputStream(cachedBody: ByteArray?) : ServletInputStream() {
    private val cachedInputStream: InputStream

    private val kLogger = KotlinLogging.logger {}

    init {
        cachedInputStream = ByteArrayInputStream(cachedBody)
    }


    override fun read(): Int {
        return cachedInputStream.read()
    }

    override fun isFinished(): Boolean {
        try {
            return cachedInputStream.available() == 0
        } catch (exp: IOException) {
            kLogger.error(exp.message)
        }
        return false
    }

    override fun isReady(): Boolean {
        return true
    }

    override fun setReadListener(listener: ReadListener?) {
        throw UnsupportedOperationException()
    }
}