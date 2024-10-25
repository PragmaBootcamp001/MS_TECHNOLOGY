package co.onclass.model.technology.errors;

public class DuplicatedTechnologyException extends RuntimeException {
    public DuplicatedTechnologyException(String message) {
        super(message);
    }
}
