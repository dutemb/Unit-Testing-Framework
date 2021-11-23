public class Assertion {
    /* You'll need to change the return type of the assertThat methods */
    static AssertO assertThat(Object o) {
	 return new AssertO(o);
    }
    static AssertS assertThat(String s) {
	 return new AssertS(s);
    }
    static AssertB assertThat(boolean b) {
	return new AssertB(b);
    }
    static AssertI assertThat(int i) {
	return new AssertI(i);
    }
}