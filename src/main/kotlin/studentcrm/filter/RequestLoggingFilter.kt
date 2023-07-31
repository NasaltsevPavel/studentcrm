package studentcrm.filter

import jakarta.servlet.FilterChain
import jakarta.servlet.ServletException
import jakarta.servlet.ServletRequest
import jakarta.servlet.ServletResponse
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import lombok.extern.slf4j.Slf4j
import org.springframework.stereotype.Component
import org.springframework.web.filter.GenericFilterBean
import java.io.IOException
import java.util.*
import java.util.stream.Collectors

@Slf4j
@Component
class RequestLoggingFilter : GenericFilterBean() {

    @Throws(IOException::class, ServletException::class)
    override fun doFilter(servletRequest: ServletRequest, servletResponse: ServletResponse, chain: FilterChain) {

        val currentRequest = servletRequest as HttpServletRequest
        val currentResponse = servletResponse as HttpServletResponse
        val cachedHttpServletRequest = CachedHttpServletRequest(currentRequest)

        val headerNames: Enumeration<String> = currentRequest.headerNames
        currentResponse.addHeader("Access-Control-Allow-Origin", "*");

        var header = currentRequest.getHeader(headerNames.nextElement())
        while (headerNames.hasMoreElements()) {
            header = header + ", " + currentRequest.getHeader(headerNames.nextElement())
        }

        logger.info("==========================request begin=================================================")
        logger.info("URI    : {${currentRequest.requestURI}}, " +
                "Method    : {${currentRequest.method.uppercase(Locale.getDefault())}}, Request headers: {${header}}," +
                "Request body: ${cachedHttpServletRequest.reader.lines().collect(Collectors.joining())} ")
        logger.info("==========================request end===================================================")

        try {
            chain.doFilter(cachedHttpServletRequest, servletResponse)
        } finally {
            logger.info("Response status: {${currentResponse.status}}")
        }
    }
    // val jsonString: String = IOUtils.toString(cachedHttpServletRequest.inputStream)
    // logger.info("Request body: $jsonString ")

}