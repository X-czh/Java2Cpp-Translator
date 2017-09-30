package inputs.test033;

class A {
  static int m(int i) { System.out.println("A.m(int)"); return i; }
  static void m(A a) { System.out.println("A.m(A)"); }
  static void m(double d) { System.out.println("A.m(double)"); }
  static void m(Object o) { System.out.println("A.m(Object)"); }
  static void m(Object o1, Object o2) { System.out.println("A.m(Object, Object)"); }
  static void m(A a1, Object o2) { System.out.println("A.m(A, Object)"); }
}

public class Test033 {
  public static void main(String[] args) {
    A a = new A();
    byte b = 1;
    A.m(b);
    A.m(a);
    A.m(1.0);
    A.m((Object) a);
    A.m(new A(), a);
    A.m(new Object(), a);
  }
}
