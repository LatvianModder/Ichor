package dev.latvian.apps.ichor.js;

import dev.latvian.apps.ichor.Scope;
import dev.latvian.apps.ichor.java.ListValueHandler;
import dev.latvian.apps.ichor.prototype.Prototype;
import dev.latvian.apps.ichor.prototype.PrototypeBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class ArrayJS {
	public static final Prototype PROTOTYPE = new PrototypeBuilder("Array")
			.constructor((cx, args, hasNew) -> args.length == 0 ? new ArrayList<>() : Arrays.asList(args))
			.asString((scope, self, builder) -> {
				builder.append('[');

				boolean first = true;

				for (Object o : collection(self)) {
					if (first) {
						first = false;
					} else {
						builder.append(',');
					}

					scope.getContext().asString(scope, o, builder);
				}

				builder.append(']');
			})
			.property("length", ArrayJS::length)
			.indexedValueHandler(ListValueHandler.INSTANCE);

	@SuppressWarnings({"unchecked", "rawtypes"})
	public static Collection<Object> collection(Object self) {
		return (Collection) self;
	}

	@SuppressWarnings({"unchecked", "rawtypes"})
	public static List<Object> list(Object self) {
		return (List) self;
	}

	private static Object length(Scope scope, Object self) {
		return collection(self).size();
	}
}
