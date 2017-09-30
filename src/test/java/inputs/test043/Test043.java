package inputs.test043;

class A {
  void m() { System.out.println("A.m()"); }
  A m(A a) { System.out.println("A.m(A)"); return a; }
}

class B extends A {
  void m() { System.out.println("B.m()"); }
  C m(B b) { System.out.println("B.m(B)"); return new C(); }
}

class C extends A {
  void m() { System.out.println("C.m()"); }
}

public class Test043 {
  public static void main(String[] args) {
    B b = new B();
    b.m(b).m();
  }
}
