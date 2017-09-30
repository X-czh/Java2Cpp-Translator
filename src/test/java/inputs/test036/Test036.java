package inputs.test036;

class A {
  void m(Object o1, Object o2) { System.out.println("A.m(Object, Object)"); }
  void m(A a1, Object o2) { System.out.println("A.m(A, Object)"); }
  void m(Object o1, A a2) { System.out.println("A.m(Object, A)"); }
}

public class Test036 {
  public static void main(String[] args) {
    A a = new A();
    a.m((Object) new A(), (Object) a);
    a.m(a, new Object());
    a.m((Object) a, a);
  }
}
