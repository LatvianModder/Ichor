package dev.latvian.apps.ichor.prototype;

import dev.latvian.apps.ichor.Evaluable;
import dev.latvian.apps.ichor.Scope;
import dev.latvian.apps.ichor.Special;
import dev.latvian.apps.ichor.js.NumberJS;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class PrototypeBuilder implements Prototype {
	private final String prototypeName;
	protected Prototype parent;
	private PrototypeConstructor constructor;
	private Map<String, Object> members;
	private PrototypeToString toString;
	private PrototypeAsString asString;
	private PrototypeAsNumber asNumber;
	private PrototypeAsBoolean asBoolean;
	private Prototype namedValueHandler;
	private Prototype indexedValueHandler;

	public PrototypeBuilder(String n) {
		prototypeName = n;
	}

	@Override
	public String getPrototypeName() {
		return prototypeName;
	}

	@Override
	public String toString() {
		return prototypeName;
	}

	protected void initLazy() {
	}

	// Builder //

	public PrototypeBuilder constructor(PrototypeConstructor c) {
		constructor = c;
		return this;
	}

	private PrototypeBuilder member(String name, Object member) {
		if (members == null) {
			members = new HashMap<>(1);
		}

		members.put(name, member);
		return this;
	}

	public PrototypeBuilder property(String name, PrototypeProperty value) {
		return member(name, value);
	}

	public PrototypeBuilder constant(String name, Object value) {
		return member(name, value);
	}

	public PrototypeBuilder function(String name, PrototypeFunction value) {
		return member(name, value);
	}

	public PrototypeBuilder toString(PrototypeToString value) {
		toString = value;
		return this;
	}

	public PrototypeBuilder asString(PrototypeAsString value) {
		asString = value;
		return this;
	}

	public PrototypeBuilder asNumber(PrototypeAsNumber value) {
		asNumber = value;
		return this;
	}

	public PrototypeBuilder asBoolean(PrototypeAsBoolean value) {
		asBoolean = value;
		return this;
	}

	public PrototypeBuilder namedValueHandler(Prototype value) {
		namedValueHandler = value;
		return this;
	}

	public PrototypeBuilder indexedValueHandler(Prototype value) {
		indexedValueHandler = value;
		return this;
	}

	// Impl //

	@Override
	@Nullable
	public Object get(Scope scope, Object self, String name) {
		initLazy();

		if (members != null) {
			var m = members.get(name);

			if (m instanceof PrototypeProperty p) {
				return p.get(scope, self);
			} else if (m instanceof Evaluable e) {
				return e.eval(scope);
			} else if (m != null) {
				return m;
			}
		}

		if (namedValueHandler != null && self != null) {
			var m = namedValueHandler.get(scope, self, name);

			if (m != Special.NOT_FOUND) {
				return m;
			}
		}

		var parent0 = parent;

		while (parent0 != null) {
			var v = parent0.get(scope, self, name);

			if (v != Special.NOT_FOUND) {
				return v;
			}

			parent0 = parent0.getPrototype(scope.getContext());
		}

		return Special.NOT_FOUND;
	}

	@Override
	public boolean set(Scope scope, Object self, String name, @Nullable Object value) {
		initLazy();

		if (members != null) {
			var m = members.get(name);

			if (m instanceof PrototypeProperty p) {
				return p.set(scope, self, value);
			} else if (m != null) {
				return false;
			}
		}

		if (namedValueHandler != null && self != null) {
			if (namedValueHandler.set(scope, self, name, value)) {
				return true;
			}
		}

		var parent0 = parent;

		while (parent0 != null) {
			if (parent0.set(scope, self, name, value)) {
				return true;
			}

			parent0 = parent0.getPrototype(scope.getContext());
		}

		return false;
	}

	@Override
	public boolean delete(Scope scope, Object self, String name) {
		initLazy();

		if (namedValueHandler != null && self != null) {
			if (namedValueHandler.delete(scope, self, name)) {
				return true;
			}
		}

		var parent0 = parent;

		while (parent0 != null) {
			if (parent0.delete(scope, self, name)) {
				return true;
			}

			parent0 = parent0.getPrototype(scope.getContext());
		}

		return false;
	}

	@Override
	public Collection<?> keys(Scope scope, Object self) {
		var h = namedValueHandler != null ? namedValueHandler : indexedValueHandler;
		return h != null ? h.keys(scope, self) : Collections.emptySet();
	}

	@Override
	public Collection<?> values(Scope scope, Object self) {
		var h = namedValueHandler != null ? namedValueHandler : indexedValueHandler;
		return h != null ? h.values(scope, self) : Collections.emptySet();
	}

	@Override
	public Collection<?> entries(Scope scope, Object self) {
		var h = namedValueHandler != null ? namedValueHandler : indexedValueHandler;
		return h != null ? h.entries(scope, self) : Collections.emptySet();
	}

	@Override
	public int getMemberCount(Scope scope, Object self) {
		var h = namedValueHandler != null ? namedValueHandler : indexedValueHandler;
		return h != null ? h.getMemberCount(scope, self) : 0;
	}

	@Override
	public boolean isArrayPrototype() {
		return indexedValueHandler != null;
	}

	@Override
	@Nullable
	public Object get(Scope scope, Object self, int index) {
		initLazy();

		if (indexedValueHandler != null && self != null) {
			var m = indexedValueHandler.get(scope, self, index);

			if (m != Special.NOT_FOUND) {
				return m;
			}
		}

		var parent0 = parent;

		while (parent0 != null) {
			var v = parent0.get(scope, self, index);

			if (v != Special.NOT_FOUND) {
				return v;
			}

			parent0 = parent0.getPrototype(scope.getContext());
		}

		return Special.NOT_FOUND;
	}

	@Override
	public boolean set(Scope scope, Object self, int index, @Nullable Object value) {
		initLazy();

		if (indexedValueHandler != null && self != null) {
			if (indexedValueHandler.set(scope, self, index, value)) {
				return true;
			}
		}

		var parent0 = parent;

		while (parent0 != null) {
			if (parent0.set(scope, self, index, value)) {
				return true;
			}

			parent0 = parent0.getPrototype(scope.getContext());
		}

		return false;
	}

	@Override
	public boolean delete(Scope scope, Object self, int index) {
		initLazy();

		if (indexedValueHandler != null && self != null) {
			if (indexedValueHandler.delete(scope, self, index)) {
				return true;
			}
		}

		var parent0 = parent;

		while (parent0 != null) {
			if (parent0.delete(scope, self, index)) {
				return true;
			}

			parent0 = parent0.getPrototype(scope.getContext());
		}

		return false;
	}

	@Override
	public Object construct(Scope scope, Evaluable[] args) {
		initLazy();

		if (constructor != null) {
			return constructor.construct(scope, args, true);
		}

		return Special.NOT_FOUND;
	}

	@Override
	public Object call(Scope scope, Object self, Evaluable[] args) {
		initLazy();

		if (constructor != null) {
			return constructor.construct(scope, args, false);
		}

		return Special.NOT_FOUND;
	}

	@Override
	public void toString(Scope scope, Object self, StringBuilder builder) {
		if (toString != null) {
			toString.toString(scope, self, builder);
		} else {
			builder.append(scope.getContext().asString(scope, self));
		}
	}

	@Override
	public String asString(Scope scope, Object self) {
		if (asString != null) {
			return asString.asString(scope, self);
		}

		return self.toString();
	}

	@Override
	public Number asNumber(Scope scope, Object self) {
		if (asNumber != null) {
			return asNumber.asNumber(scope, self);
		}

		return NumberJS.ONE;
	}

	@Override
	public Boolean asBoolean(Scope scope, Object self) {
		if (asBoolean != null) {
			return asBoolean.asBoolean(scope, self);
		}

		return Boolean.TRUE;
	}
}
