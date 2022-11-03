package dev.latvian.apps.ichor.ast.expression.binary;

import dev.latvian.apps.ichor.Scope;

public class AstXor extends AstBinary {
	@Override
	public void appendSymbol(StringBuilder builder) {
		builder.append('^');
	}

	@Override
	public Object eval(Scope scope) {
		return evalInt(scope);
	}

	@Override
	public int evalInt(Scope scope) {
		return scope.getContext().asInt(scope, left) ^ scope.getContext().asInt(scope, right);
	}

	@Override
	public boolean evalBoolean(Scope scope) {
		return scope.getContext().asBoolean(scope, left) ^ scope.getContext().asBoolean(scope, right);
	}

	@Override
	public double evalDouble(Scope scope) {
		return evalInt(scope);
	}
}