package inputs.test032;

class A {
  int m(int i) { System.out.println("A.m(int)"); return i; }
  void m(A a) { System.out.println("A.m(A)"); }
  void m(double d) { System.out.println("A.m(double)"); }
  void m(Object o) { System.out.println("A.m(Object)"); }
  void m(Object o1, Object o2) { System.out.println("A.m(Object, Object)"); }
  void m(A a1, Object o2) { System.out.println("A.m(A, Object)"); }
}

public class Test032 {
  public static void main(String[] args) {
    A a = new A();
    byte b = 1;
    a.m(b);
    a.m(a);
    a.m(1.0);
    a.m((Object) a);
    a.m(new A(), a);
    a.m(new Object(), a);
  }
}
