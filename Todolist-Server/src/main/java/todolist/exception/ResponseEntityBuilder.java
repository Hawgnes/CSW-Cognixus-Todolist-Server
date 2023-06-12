package todolist.exception;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ResponseEntityBuilder {
	public static ResponseEntity<Object> responseBuilder(String message, HttpStatus status){
		Map<String, Object> responseBody = new LinkedHashMap<>();
		responseBody.put("timestamp", new Date());
		responseBody.put("status", status.value());
		
		if (status == HttpStatus.OK || status == HttpStatus.CREATED) {
			responseBody.put("message", message.toString());
		}
		else {
			responseBody.put("errors", message.toString());
		}
		
		return new ResponseEntity<>(responseBody, status);
	}
}
