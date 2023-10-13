package uz.devops.requestLimit.cache.exception;

/**
 * You need to add an @ExceptionHandler method to the @AdviceController class for this exception in your project.
 */
public class TooManyRequestsException extends RuntimeException {
    public TooManyRequestsException(String message) {
        super(message);
    }
}
