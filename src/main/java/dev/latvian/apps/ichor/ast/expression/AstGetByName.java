package dev.latvian.apps.ichor.ast.expression;

import dev.latvian.apps.ichor.Scope;
import dev.latvian.apps.ichor.Special;
import dev.latvian.apps.ichor.ast.AstStringBuilder;
import dev.latvian.apps.ichor.error.ScriptError;
import dev.latvian.apps.ichor.prototype.Evaluable;

import java.util.regex.Pattern;

public class AstGetByName extends AstGetFrom {
	private static final Pattern PLAIN_PATTERN = Pattern.compile("^[a-zA-Z_$][\\w$]*$");

	public final String name;

	public AstGetByName(Evaluable from, String name) {
		super(from);
		this.name = name;
	}

	@Override
	public Object evalKey(Scope scope) {
		return name;
	}

	@Override
	public void append(AstStringBuilder builder) {
		builder.appendValue(from);

		if (PLAIN_PATTERN.matcher(name).find()) {
			builder.append('.');
			builder.append(name);
		} else {
			builder.append("['");
			builder.append(name);
			builder.append("']");
		}
	}

	@Override
	public Object eval(Scope scope) {
		var cx = scope.getContext();
		var self = scope.eval(from);
		var p = cx.getPrototype(self);

		if (cx.debugger != null) {
			cx.debugger.pushSelf(self);
		}

		var r = p.get(scope, self, name);

		if (r == Special.NOT_FOUND) {
			throw new ScriptError("Cannot find " + this + " of " + p);
		}

		if (cx.debugger != null) {
			cx.debugger.get(this, r);
		}

		return r;
	}

	@Override
	public void set(Scope scope, Object value) {
		var cx = scope.getContext();
		var self = scope.eval(from);
		var p = cx.getPrototype(self);

		if (cx.debugger != null) {
			cx.debugger.pushSelf(self);
		}

		p.set(scope, self, name, value);

		if (cx.debugger != null) {
			cx.debugger.set(this, value);
		}
	}
}