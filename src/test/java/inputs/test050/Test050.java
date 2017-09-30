package inputs.test050;

class A {
  void m() { System.out.println("A.m()"); }
  A m(A a) { System.out.println("A.m(A)"); return a; }
}

class B extends A {
  void m() { System.out.println("B.m()"); }
  C m(B b) { System.out.println("B.m(B)"); return new C(); }
  A m(A a) { System.out.println("B.m(A)"); return a; }
}

class C extends A {
  void m() { System.out.println("C.m()"); }
}

public class Test050 {
  public static void main(String[] args) {
    B b = new B();
    A a = new A();
    a.m(b).m(b.m(a)).m();
  }
}
