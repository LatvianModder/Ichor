package dev.latvian.apps.ichor.error;

import dev.latvian.apps.ichor.token.TokenPos;
import dev.latvian.apps.ichor.token.TokenPosSupplier;
import dev.latvian.apps.ichor.util.PrintWrapper;

import java.io.PrintStream;
import java.io.PrintWriter;

public abstract class IchorError extends RuntimeException {
	public TokenPos tokenPos = TokenPos.UNKNOWN;

	public IchorError(String message) {
		super(message);
	}

	public IchorError(String message, Throwable cause) {
		super(message, cause);
	}

	public IchorError(Throwable cause) {
		super(cause);
	}

	public IchorError pos(TokenPosSupplier pos) {
		tokenPos = pos.getPos();
		return this;
	}

	public IchorError pos(Object pos) {
		if (pos instanceof TokenPosSupplier supplier) {
			tokenPos = supplier.getPos();
		}

		return this;
	}

	public String getCode() {
		return "";
	}

	@Override
	public String getMessage() {
		var m = super.getMessage();

		if (tokenPos != TokenPos.UNKNOWN) {
			return m == null || m.isEmpty() ? tokenPos.toString() : (tokenPos + ": " + m);
		}

		return m;
	}

	public void printPrettyError(PrintWrapper print) {
		synchronized (print.lock()) {
			var c = getCode();

			if (!c.isEmpty()) {
				print.println("| " + (tokenPos + ": " + getMessage()).trim());
				print.println("| " + c);
				print.println("| " + (tokenPos.col() > 2 ? " ".repeat(tokenPos.col() - 2) : "") + '^');
			}
		}
	}

	@Override
	public void printStackTrace(PrintStream s) {
		printPrettyError(PrintWrapper.of(s));
		super.printStackTrace(s);
	}

	@Override
	public void printStackTrace(PrintWriter s) {
		printPrettyError(PrintWrapper.of(s));
		super.printStackTrace(s);
	}
}
