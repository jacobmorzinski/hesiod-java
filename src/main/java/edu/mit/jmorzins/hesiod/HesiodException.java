package edu.mit.jmorzins.hesiod;

public class HesiodException extends Exception {
	private static final long serialVersionUID = 3942301354700361844L;

	/**
     * Constructs a {@code HesiodException} with {@code null}
     * as its error detail message.
     */
    public HesiodException() {
    	super();
    }

    /**
     * Constructs an {@code HesiodException} with the specified detail message.
     *
     * @param message
     *        The detail message (which is saved for later retrieval
     *        by the {@link #getMessage()} method)
     */
    public HesiodException(String message) {
    	super(message);
    }

    /**
     * Constructs an {@code HesiodException} with the specified detail message
     * and cause.
     *
     * <p> Note that the detail message associated with {@code cause} is
     * <i>not</i> automatically incorporated into this exception's detail
     * message.
     *
     * @param message
     *        The detail message (which is saved for later retrieval
     *        by the {@link #getMessage()} method)
     *
     * @param cause
     *        The cause (which is saved for later retrieval by the
     *        {@link #getCause()} method).  (A null value is permitted,
     *        and indicates that the cause is nonexistent or unknown.)
     */
    public HesiodException(String message, Throwable cause) {
    	super(message, cause);
    }

    /**
     * Constructs a {@code HesiodException} with the specified cause and a
     * detail message of {@code (cause==null ? null : cause.toString())}
     * (which typically contains the class and detail message of {@code cause}).
     * This constructor is useful catching an exception from a lower subsystem.
     *
     * @param cause
     *        The cause (which is saved for later retrieval by the
     *        {@link #getCause()} method).  (A null value is permitted,
     *        and indicates that the cause is nonexistent or unknown.)
     */
    public HesiodException(Throwable cause) {
    	super(cause);
    }
}
