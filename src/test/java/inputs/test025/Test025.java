package inputs.test025;

class A {
  int i;
  public A(int i) {
    this.i = i;
  }

  public int get() {
    return i;
  }
}

class B extends A {
  public B(int i) {
    super(i);
  }

  public int get() {
    return (10 - i);
  }
}

public class Test025 {
  public static void main(String[] args) {
    Object[] as = new A[10];
    
    for(int i = 0; i < as.length; i++) {
      as[i] = new B(i);
    }

    int k = 0;
    while(k < 10) {
      System.out.println(((A) as[k]).get());
      k = k + 1;
    }
  }
}
