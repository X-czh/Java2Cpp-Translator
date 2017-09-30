package inputs.test034;

class A {
  int m(byte b) { System.out.println("A.m(byte)"); return b; }
  int m(int i) { System.out.println("A.m(int)"); return i; }
  void m(double d) { System.out.println("A.m(double)"); }
}

public class Test034 {
  public static void main(String[] args) {
    A a = new A();
    byte b = 1;
    double d = 1.0;
    a.m(b);
    a.m(b + b);
    a.m(d);
  }
}
