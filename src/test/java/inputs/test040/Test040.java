package inputs.test040;

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

class C extends A {}

public class Test040 {
  public static void main(String[] args) {
    C c = new C();
    c.m(new A(), (Object) c);
    c.m(c, new Object());
    c.m((Object) c, c);
  }
}
