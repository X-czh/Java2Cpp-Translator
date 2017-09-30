package inputs.test042;

class A {
  void m() { System.out.println("A.m()"); }
  A m(A a) { System.out.println("A.m(A)"); return a; }
}

class B extends A {
  void m() { System.out.println("B.m()"); }
  B m(B b) { System.out.println("B.m(B)"); return b; }
  A m(A a) { System.out.println("B.m(A)"); return a; }
}

public class Test042 {
  public static void main(String[] args) {
    A a = new A();
    a.m(a).m();
    B b = new B();
    b.m(b).m();
    b.m((A) b).m();
  }
}
