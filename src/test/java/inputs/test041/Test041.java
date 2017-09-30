package inputs.test041;

class A {
  void m(Object o1, Object o2) { System.out.println("A.m(Object, Object)"); }
  void m(A a1, Object o2) { System.out.println("A.m(A, Object)"); }
  void m(Object o1, A a2) { System.out.println("A.m(Object, A)"); }
}

class B extends A {
  void m(Object o1, Object o2) { System.out.println("B.m(Object, Object)"); }
  void m(B a1, Object o2) { System.out.println("B.m(B, Object)"); }
  void m(Object o1, B a2) { System.out.println("B.m(Object, B)"); }
}

class C extends A {
  void m(C c1, C c2) { System.out.println("C.m(C, C)"); }
}

public class Test041 {
  public static void main(String[] args) {
    C c = new C();
    c.m(new A(), (Object) c);
    c.m(c, c);
    c.m((Object) c, c);
  }
}
