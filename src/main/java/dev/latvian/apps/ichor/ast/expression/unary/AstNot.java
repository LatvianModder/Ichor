package dev.latvian.apps.ichor.ast.expression.unary;

import dev.latvian.apps.ichor.Evaluable;
import dev.latvian.apps.ichor.Scope;
import dev.latvian.apps.ichor.ast.AstStringBuilder;
import dev.latvian.apps.ichor.ast.expression.AstBoolean;

public class AstNot extends AstUnary {
	@Override
	public void append(AstStringBuilder builder) {
		builder.append('!');
		builder.appendValue(node);
	}

	@Override
	public Object eval(Scope scope) {
		return evalBoolean(scope);
	}

	@Override
	public boolean evalBoolean(Scope scope) {
		return !node.evalBoolean(scope);
	}

	@Override
	public int evalInt(Scope scope) {
		return node.evalBoolean(scope) ? 0 : 1;
	}

	@Override
	public double evalDouble(Scope scope) {
		return evalInt(scope);
	}

	@Override
	public Evaluable optimize() {
		node = node.optimize();

		if (node instanceof AstBoolean n) {
			return new AstBoolean(!n.value).pos(pos);
		}

		return this;
	}
}