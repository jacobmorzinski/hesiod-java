package edu.mit.hesiod;

@SuppressWarnings("serial")
public class HesiodException extends Exception {
	HesiodException(String message) {
		super(message);
	}
	HesiodException() {
		super();
	}
}
