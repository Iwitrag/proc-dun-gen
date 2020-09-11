package cz.iwitrag.procdungenweb.controllers;

import cz.iwitrag.procdungenweb.Utils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.ModelAndView;

/**
 * Exception handler for Spring and additional modules
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handle exception thrown when user uploads too large file
     * @param ex Exception thrown
     * @return Error page
     */
    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ModelAndView handleMaxUploadException(MaxUploadSizeExceededException ex){
        ModelAndView model = new ModelAndView("error");
        model.addObject("message", ex.getMessage());
        model.addObject("stack", Utils.getStackTrace(ex));
        return model;
    }

}
