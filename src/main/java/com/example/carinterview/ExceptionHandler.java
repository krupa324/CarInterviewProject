package com.example.carinterview;

import com.example.carinterview.entities.NotFoundMessage;
import javassist.NotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class ExceptionHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler(NotFoundException.class)
    @ResponseBody
    public NotFoundMessage handleNotFoundMessage(NotFoundException ex)
    {
        NotFoundMessage notFoundMessage = new NotFoundMessage();

        notFoundMessage.setErrorCode(404);
        notFoundMessage.setErrorMessage(ex.getMessage());

        return notFoundMessage;
    }

}
