package telran.exceptions;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;


@ControllerAdvice
public class GlobalExaptionsHandler {
static Logger LOG = LoggerFactory.getLogger(GlobalExaptionsHandler.class);

@ExceptionHandler(MethodArgumentNotValidException.class)
ResponseEntity<String>  handlerMethodArgument(MethodArgumentNotValidException e){
	List<ObjectError> errors = e.getAllErrors();
	String body = errors.stream().map(err -> err.getDefaultMessage())
			.collect(Collectors.joining(";"));
	return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
}

@ExceptionHandler(ConstraintViolationException.class)
ResponseEntity<String> handlerConstraintViolation(ConstraintViolationException e){
	Set<ConstraintViolation<?>> constraints = e.getConstraintViolations();
	String body = constraints.stream().map(constraint -> constraint.getMessage())
			.collect(Collectors.joining(";"));
	return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
}

@ExceptionHandler(NoSuchElementException.class)
ResponseEntity<String> handlerNoSuchElement(NoSuchElementException e){
	String error = e.getMessage();
	return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
}

@ExceptionHandler(IllegalArgumentException.class)
ResponseEntity<String> handlerIllegalArgument(IllegalArgumentException e){
	String error = e.getMessage();
	return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
}
	
	
}