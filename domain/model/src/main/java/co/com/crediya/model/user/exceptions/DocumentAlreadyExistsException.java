package co.com.crediya.model.user.exceptions;

public class DocumentAlreadyExistsException extends DomainException {
    public DocumentAlreadyExistsException() {
        super("El número de documento ya está en uso");
    }
}
