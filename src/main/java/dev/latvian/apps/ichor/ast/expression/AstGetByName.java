package dev.latvian.apps.ichor.ast.expression;

import dev.latvian.apps.ichor.Context;
import dev.latvian.apps.ichor.Scope;
import dev.latvian.apps.ichor.Special;
import dev.latvian.apps.ichor.ast.AstStringBuilder;
import dev.latvian.apps.ichor.error.ScriptError;

import java.util.regex.Pattern;

public class AstGetByName extends AstGetFrom {
	private static final Pattern PLAIN_PATTERN = Pattern.compile("^[a-zA-Z_$][\\w$]*$");

	public final String name;

	public AstGetByName(Object from, String name) {
		super(from);
		this.name = name;
	}

	@Override
	public Object evalKey(Context cx, Scope scope) {
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
	public Object eval(Context cx, Scope scope) {
		var self = evalSelf(cx, scope);
		var p = cx.getPrototype(scope, self);
		cx.debugger.pushSelf(cx, scope, self);

		var r = p.get(cx, scope, self, name);

		if (r == Special.NOT_FOUND) {
			throw new ScriptError("Cannot find " + this + " of " + p);
		}

		cx.debugger.get(cx, scope, this, r);
		return r;
	}

	@Override
	public void set(Context cx, Scope scope, Object value) {
		var self = evalSelf(cx, scope);
		var p = cx.getPrototype(scope, self);
		cx.debugger.pushSelf(cx, scope, self);
		p.set(cx, scope, self, name, value);
		cx.debugger.set(cx, scope, this, value);
	}

	@Override
	public boolean delete(Context cx, Scope scope) {
		var self = evalSelf(cx, scope);
		var p = cx.getPrototype(scope, self);
		cx.debugger.pushSelf(cx, scope, self);
		p.delete(cx, scope, self, name);
		cx.debugger.delete(cx, scope, this);
		return true;
	}
}
