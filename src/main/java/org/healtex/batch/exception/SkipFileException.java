package org.healtex.batch.exception;

public class SkipFileException extends Exception {

    public SkipFileException() {
        super();
    }

    public SkipFileException(String message) {
        super(message);
    }

    public SkipFileException(Throwable cause) {
        super(cause);
    }
}
