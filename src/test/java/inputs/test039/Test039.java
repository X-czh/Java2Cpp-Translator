package inputs.test039;

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

public class Test039 {
  public static void main(String[] args) {
    B b = new B();
    b.m(new A(), (Object) b);
    b.m(b, new Object());
    b.m((Object) b, b);
  }
}
