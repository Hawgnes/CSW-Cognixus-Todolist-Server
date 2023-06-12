package todolist.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;


@ControllerAdvice
public class CustomExceptionHandler {
	/* attempting to update todoStatus with values other than the enum will be caught here
	 * 
	 * */
	@ExceptionHandler(HttpMessageNotReadableException.class)
	protected ResponseEntity<Object> httpMessageNotReadableException (
			HttpMessageNotReadableException ex, 
			WebRequest request){
		return ResponseEntityBuilder.responseBuilder("Malformed request body. Possible Todo status are: [NEW, COMPLETED, IN_PROGRESS]", HttpStatus.BAD_REQUEST);
	}
	
	/* attempting to use char instead of Long for /get/{todoId} will be caught here
	 * 
	 * */
	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	protected ResponseEntity<Object> methodArgumentTypeMismatchException (
			MethodArgumentTypeMismatchException ex, 
			WebRequest request){
		return ResponseEntityBuilder.responseBuilder("MethodArgumentTypeMismatchException: Please ensure you are passing the correct data type in the API endpoint and request parameters", HttpStatus.BAD_REQUEST);
	}
}
