package com.manage.company.company.exeption;

import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Data
@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class ResourceCannotBeDeletedException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    private String resourceName;
    private String message;

    public ResourceCannotBeDeletedException(String resourceName, String message) {
        super(message);
        this.resourceName = resourceName;
        this.message = message;
    }

    public ResourceCannotBeDeletedException(String resourceName, Long id, String message) {
        super(String.format("%s with id %d cannot be deleted. %s", resourceName, id, message));
        this.resourceName = resourceName;
        this.message = String.format("%s with id %d cannot be deleted. Because %s", resourceName, id, message);
    }
}
