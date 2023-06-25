package com.github.dsjt.lispinterpreter;

public interface Operator {
	   Object apply(Object result, Object value) throws LispException;
}
