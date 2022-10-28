package dev.latvian.apps.ichor.parser.expression.binary;

import dev.latvian.apps.ichor.parser.expression.AstExpression;

public class AstSneq extends AstBinary {
	public AstSneq(AstExpression left, AstExpression right) {
		super(left, right);
	}

	@Override
	public void appendSymbol(StringBuilder builder) {
		builder.append("!==");
	}
}