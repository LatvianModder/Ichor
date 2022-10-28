package dev.latvian.apps.ichor.parser.expression.binary;

import dev.latvian.apps.ichor.parser.expression.AstExpression;

public class AstXor extends AstBinary {
	public AstXor(AstExpression left, AstExpression right) {
		super(left, right);
	}

	@Override
	public void appendSymbol(StringBuilder builder) {
		builder.append('^');
	}
}