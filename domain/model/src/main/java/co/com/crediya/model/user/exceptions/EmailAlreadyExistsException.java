package co.com.crediya.model.user.exceptions;

public class EmailAlreadyExistsException extends DomainException {
    public EmailAlreadyExistsException() {
        super("El correo electrónico ya está en uso");
    }
}