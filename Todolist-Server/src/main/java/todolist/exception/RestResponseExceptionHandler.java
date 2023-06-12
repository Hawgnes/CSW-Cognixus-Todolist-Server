package todolist.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@ControllerAdvice
public class RestResponseExceptionHandler {

	@ExceptionHandler(HttpMessageNotReadableException.class)
	protected ResponseEntity<Object> httpMessageNotReadableException (
			HttpMessageNotReadableException ex, 
			WebRequest request){
		String responseBody = "Malformed request body. Possible Todo status are: [NEW, COMPLETED, IN_PROGRESS]";
		log.info("httpMessageNotReadableException called");
		return new ResponseEntity<>(responseBody, HttpStatus.BAD_REQUEST);
	}
}
