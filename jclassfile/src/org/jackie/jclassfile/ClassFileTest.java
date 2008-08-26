package org.jackie.jclassfile;

import java.util.List;

class Sample<T> {
	List<T> items;
	<T> void method(T t) {}
}
