package modules;

public class CustomException extends RuntimeException {
    CustomException(String stringValue, String dataType) {
        super("Value (" + stringValue.trim() + ") can't cast to " + dataType.trim());
    }

}
