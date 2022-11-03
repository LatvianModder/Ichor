package dev.latvian.apps.ichor.ast.statement;

import dev.latvian.apps.ichor.Scope;
import dev.latvian.apps.ichor.ast.AstStringBuilder;
import dev.latvian.apps.ichor.error.ScriptError;

public class AstContinue extends AstStatement {
	@Override
	public void append(AstStringBuilder builder) {
		builder.append("continue");
	}

	@Override
	public void interpret(Scope scope) {
		throw new ContinueException();
	}

	public static class ContinueException extends ScriptError {
		private ContinueException() {
			super("continue is not supported here");
		}
	}
}