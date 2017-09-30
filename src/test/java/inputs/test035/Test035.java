package inputs.test035;

class A {
  int m(byte b) { System.out.println("A.m(byte)"); return b; }
  void m(double d) { System.out.println("A.m(double)"); }
}

public class Test035 {
  public static void main(String[] args) {
    A a = new A();
    byte b = 1;
    double d = 1.0;
    a.m(b);
    a.m(b + b);
    a.m(d);
  }
}
