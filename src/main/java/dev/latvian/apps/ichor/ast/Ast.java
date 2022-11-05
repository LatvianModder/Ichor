package dev.latvian.apps.ichor.ast;

import dev.latvian.apps.ichor.token.PositionedToken;
import dev.latvian.apps.ichor.token.TokenPos;

public abstract class Ast implements AstAppendable {
	public TokenPos pos = TokenPos.UNKNOWN;

	@Override
	public String toString() {
		var sb = new AstStringBuilder();
		append(sb);
		return sb.toString();
	}

	public Ast pos(PositionedToken token) {
		pos = token.pos();
		return this;
	}

	public Ast pos(Ast other) {
		pos = other.pos;
		return this;
	}
}
