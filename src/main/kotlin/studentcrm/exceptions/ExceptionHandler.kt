package studentcrm.exceptions

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.web.HttpRequestMethodNotSupportedException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException
import java.lang.IllegalArgumentException

@ControllerAdvice
class ExceptionHandler {

    @ExceptionHandler(NotFoundException::class)
    fun handleNotFound(e: NotFoundException) = ResponseEntity(e.message, HttpStatus.NOT_FOUND)

    @ExceptionHandler(InvalidInputException::class)
    fun handleNotFound(e: InvalidInputException) = ResponseEntity(e.message, HttpStatus.NOT_FOUND)

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleValidationException(e: MethodArgumentNotValidException) = ResponseEntity("Validation failed, change input data", HttpStatus.NOT_ACCEPTABLE)

    @ExceptionHandler(MethodArgumentTypeMismatchException::class)
    fun handleTypeMismatch(e: MethodArgumentTypeMismatchException) = ResponseEntity("Wrong input type",HttpStatus.BAD_REQUEST)

    @ExceptionHandler(HttpMessageNotReadableException::class)
    fun handleMessageEx(e: HttpMessageNotReadableException) = ResponseEntity("Required request body is missing",HttpStatus.METHOD_NOT_ALLOWED)

    @ExceptionHandler(HttpRequestMethodNotSupportedException::class)
    fun handleMethodEx(e: HttpRequestMethodNotSupportedException) = ResponseEntity(e.message,HttpStatus.METHOD_NOT_ALLOWED)

}