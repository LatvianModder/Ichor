package dev.latvian.apps.ichor.prototype;

import dev.latvian.apps.ichor.Context;

@FunctionalInterface
public interface PrototypeConstructor {
	Object construct(Context cx, Object[] args, boolean hasNew);
}