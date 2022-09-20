package project.moseup.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@Slf4j
public class ExceptionController implements ErrorController {

    @GetMapping("/error")
    public String handleError(HttpServletResponse response, HttpServletRequest request) {
        int stat = response.getStatus();

        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

        if(status != null){
            int statusCode = Integer.valueOf(status.toString());
            log.info("statusCode : " + statusCode);
            if(statusCode == HttpStatus.NOT_FOUND.value()) {
                return "errors/notFound";
            } else if (statusCode == HttpStatus.FORBIDDEN.value()){
                return "errors/forbidden";
            } else if (statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()){
                return "errors/InternalServerError";
            } else {
                return "errors/error";
            }
        }

        return "forbidden";
    }

//    @ExceptionHandler(NoHandlerFoundException.class)
//    @ResponseStatus(HttpStatus.NOT_FOUND)
//    public String handle404(NoHandlerFoundException ex) {
//        System.out.println("ExceptionController 지나감");
//        return "errors/notFound";
//    }
}
