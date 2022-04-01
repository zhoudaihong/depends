class B{
	final static String a = ComplexExpressionTest.foo();

	final static String b = new ComplexExpressionTest();

	final static String c = func();

	String d = ComplexExpressionTest.var;

	string func() {
		string a = ComplexExpressionTest.foo();
		String b = new ComplexExpressionTest();
		return a;
	}
}

class ComplexExpressionTest{
	static String foo() {
		return "foo()";
	}

	static String var = "var";
}