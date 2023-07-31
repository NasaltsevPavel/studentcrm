package studentcrm.filter

import jakarta.servlet.ServletInputStream
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletRequestWrapper
import org.springframework.util.StreamUtils
import java.io.BufferedReader
import java.io.ByteArrayInputStream
import java.io.InputStreamReader


class CachedHttpServletRequest(request: HttpServletRequest) : HttpServletRequestWrapper(request) {
    private val cachedPayload: ByteArray

    init {
        cachedPayload = StreamUtils.copyToByteArray(request.inputStream)
    }

    override fun getInputStream(): ServletInputStream {
        return CachedServletInputStream(cachedPayload)
    }

    override fun getReader(): BufferedReader {
        val byteArrayInputStream = ByteArrayInputStream(cachedPayload)
        return BufferedReader(InputStreamReader(byteArrayInputStream))
    }
}