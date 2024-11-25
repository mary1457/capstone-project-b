package mariapiabaldoin.capstone_project_b.exceptions;


import java.util.UUID;

public class NotFoundException extends RuntimeException {
    public NotFoundException(long id) {
        super("The record with ID " + id + " was not found");
    }

    public NotFoundException(UUID id) {
        super("The record with ID " + id + " was not found");
    }

    public NotFoundException(String message) {
        super(message);
    }
}