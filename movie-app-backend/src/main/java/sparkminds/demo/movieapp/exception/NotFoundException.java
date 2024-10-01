package sparkminds.demo.movieapp.exception;

import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;

import static sparkminds.demo.movieapp.controller.advice.ExceptionTranslator.NOT_FOUND_TITLE;

public class NotFoundException extends AbstractThrowableProblem {
    public NotFoundException(String message) {
        super(null, NOT_FOUND_TITLE, Status.NOT_FOUND, message);
    }
}
