package dev.latvian.apps.ichor.ast.statement;

import dev.latvian.apps.ichor.Context;
import dev.latvian.apps.ichor.Interpretable;
import dev.latvian.apps.ichor.Parser;
import dev.latvian.apps.ichor.Scope;
import dev.latvian.apps.ichor.ast.AstStringBuilder;
import dev.latvian.apps.ichor.exit.BreakExit;
import dev.latvian.apps.ichor.exit.ExitType;
import dev.latvian.apps.ichor.exit.ReturnExit;

public class AstBlock extends AstLabeledStatement {
	public Interpretable[] interpretable;
	public boolean forceReturn;

	@Override
	public void append(AstStringBuilder builder) {
		if (!label.isEmpty()) {
			builder.append(label);
			builder.append(':');
		}

		builder.append('{');

		for (Interpretable value : interpretable) {
			builder.append(value);
		}

		builder.append('}');
	}

	@Override
	public boolean handle(ExitType type) {
		return type == ExitType.BREAK && !label.isEmpty();
	}

	@Override
	public void interpret(Context cx, Scope scope) {
		var s = scope.push();

		for (var statement : interpretable) {
			try {
				statement.interpretSafe(cx, s);
			} catch (BreakExit exit) {
				if (exit.stop == this) {
					break;
				} else {
					throw exit;
				}
			}
		}

		if (forceReturn) {
			throw ReturnExit.DEFAULT_RETURN;
		}
	}

	@Override
	public void optimize(Parser parser) {
		for (var statement : interpretable) {
			statement.optimize(parser);
		}
	}
}
