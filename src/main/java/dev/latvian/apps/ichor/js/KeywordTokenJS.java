package dev.latvian.apps.ichor.js;

import dev.latvian.apps.ichor.Special;
import dev.latvian.apps.ichor.token.DeclaringToken;
import dev.latvian.apps.ichor.token.KeywordToken;
import dev.latvian.apps.ichor.token.Token;

import java.util.HashMap;
import java.util.Map;

public interface KeywordTokenJS {
	Map<String, Object> CACHE = new HashMap<>();

	private static Object cache(String name, Object token) {
		CACHE.put(name, token);
		return token;
	}

	private static KeywordToken cache(KeywordToken token) {
		return (KeywordToken) cache(token.name, token);
	}

	private static KeywordToken cache(String name) {
		var token = new KeywordToken(name);
		cache(name, token);
		return token;
	}

	Object NULL = cache("null", Special.NULL);
	Object UNDEFINED = cache("undefined", Special.UNDEFINED);
	Object TRUE = cache("true", Boolean.TRUE);
	Object FALSE = cache("false", Boolean.FALSE);
	Token IN = cache(new InKeywordJS());
	Token INSTANCEOF = cache(new InstanceofKeywordJS());
	// cacheKeyword(new KeywordToken("arguments")); // TODO

	DeclaringToken LET = (DeclaringToken) cache(new DeclaringToken("let", false));
	DeclaringToken CONST = (DeclaringToken) cache(new DeclaringToken("const", true));
	DeclaringToken VAR = (DeclaringToken) cache(new DeclaringToken("var", false));

	Token AS = cache("as").literalPre(); // TODO
	Token ASYNC = cache("async");
	Token AWAIT = cache("await").literalPre();
	Token BREAK = cache("break").literalPre();
	Token CASE = cache("case").literalPre();
	Token CATCH = cache("catch");
	Token CLASS = cache("class").identifier();
	Token CONTINUE = cache("continue").literalPre();
	Token DEBUGGER = cache("debugger").identifier();
	Token DEFAULT = cache("default");
	Token DELETE = cache("delete").literalPre();
	Token DO = cache("do");
	Token ELSE = cache("else");
	Token ENUM = cache("enum").identifier(); // TODO
	Token EXPORT = cache("export").identifier(); // TODO
	Token EXTENDS = cache("extends");
	Token FINALLY = cache("finally");
	Token FOR = cache("for");
	Token FROM = cache("from").literalPre().identifier();// TODO
	Token FUNCTION = cache("function");
	Token GET = cache("get").identifier(); // TODO
	Token IF = cache("if").literalPre();
	Token IMPORT = cache("import").identifier(); // TODO
	Token INTERFACE = cache("interface").identifier();
	Token NEW = cache("new").literalPre();
	Token OF = cache("of").literalPre().identifier();
	Token PACKAGE = cache("package").identifier(); // TODO
	Token PRIVATE = cache("private").identifier(); // TODO
	Token PROTECTED = cache("protected").identifier(); // TODO
	Token PUBLIC = cache("public").identifier(); // TODO
	Token RETURN = cache("return").literalPre();
	Token SET = cache("set").identifier(); // TODO
	Token STATIC = cache("static").identifier(); // TODO
	Token SUPER = cache("super"); // TODO
	Token SWITCH = cache("switch");
	Token THIS = cache("this"); // TODO
	Token THROW = cache("throw").literalPre();
	Token TRY = cache("try");
	Token TYPEOF = cache("typeof").literalPre();
	Token VOID = cache("void").literalPre().identifier(); // TODO
	Token WHILE = cache("while");
	Token YIELD = cache("yield").identifier(); // TODO
}
