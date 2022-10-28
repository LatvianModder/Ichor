package dev.latvian.apps.ichor.parser.expression.binary;

import dev.latvian.apps.ichor.parser.expression.AstExpression;

public class AstModSet extends AstSet {
	public AstModSet(AstExpression left, AstExpression right) {
		super(left, right);
	}

	@Override
	public void appendSymbol(StringBuilder builder) {
		builder.append("%=");
	}
}