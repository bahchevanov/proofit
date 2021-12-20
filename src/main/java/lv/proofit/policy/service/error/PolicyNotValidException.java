package lv.proofit.policy.service.error;


public class PolicyNotValidException extends Exception{
    public PolicyNotValidException(String message) {
        super(message);
    }

    public PolicyNotValidException(String message, Throwable cause) {
        super(message, cause);
    }
}
