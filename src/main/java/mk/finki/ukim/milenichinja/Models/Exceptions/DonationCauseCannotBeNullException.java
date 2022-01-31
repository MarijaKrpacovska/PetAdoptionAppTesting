package mk.finki.ukim.milenichinja.Models.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class DonationCauseCannotBeNullException extends RuntimeException {
    public DonationCauseCannotBeNullException() {
        super(String.format("Donation cause cannot be null"));
    }
}
