package br.com.b3.exception;


import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDateTime;

@RestController
@ControllerAdvice
public class GlobalExceptionHandlers {
	
	public static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandlers.class);

	@ExceptionHandler(IllegalArgumentException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ModelAndView unexpectedError(HttpServletRequest req, IllegalArgumentException e) {
		LOGGER.error("IllegalArgumentException", e);

		ModelAndView mav = new ModelAndView();
		mav.addObject("timestamp", LocalDateTime.now());
		mav.addObject("path", req.getRequestURL());
		mav.addObject("error", e.getMessage());
		mav.addObject("status", HttpStatus.BAD_REQUEST);
		mav.addObject("trace", ExceptionUtils.getStackTrace(e));

		mav.setViewName("error");

		return mav;
	}
}
