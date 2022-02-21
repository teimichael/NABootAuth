package stu.napls.nabootauth.core.exception;

import io.jsonwebtoken.ExpiredJwtException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import stu.napls.nabootauth.core.response.Response;

/**
 * @Author Tei Michael
 * @Date 2/21/2022
 */
@ControllerAdvice
public class GlobalExceptionHandler {
    private final static Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ResponseBody
    @ExceptionHandler(value = SystemException.class)
    public Response<String> systemExceptionHandler(SystemException e) {
        logger.error(e.getMessage());
        return Response.failure(e.getCode(), e.getMessage());
    }

    @ResponseBody
    @ExceptionHandler(value = ExpiredJwtException.class)
    public Response<String> expiredJwtExceptionHandler(ExpiredJwtException e) {
        logger.error(e.getMessage());
        return Response.failure("Token expired.");
    }

    @ResponseBody
    @ExceptionHandler(value = Exception.class)
    public Response<String> errorHandler(Exception e) {
        logger.error(e.getMessage());
        return Response.failure("Internal Error.");
    }


}
