package inputs.test037;

class A {
  void m(Object o1, Object o2) { System.out.println("A.m(Object, Object)"); }
  void m(A a1, Object o2) { System.out.println("A.m(A, Object)"); }
  void m(Object o1, A a2) { System.out.println("A.m(Object, A)"); }
}

class B extends A {
}

public class Test037 {
  public static void main(String[] args) {
    B b = new B();
    b.m((Object) new A(), (Object) b);
    b.m(b, new Object());
    b.m((Object) b, b);
  }
}
