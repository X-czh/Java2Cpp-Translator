package inputs.test026;

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

public class Test026 {
  public static void main(String[] args) {
    A[] as = new B[10];
    
    for(int i = 0; i < as.length; i++) {
      as[i] = new A(i); // throws java.lang.ArrayStoreException
    }

    int k = 0;
    while(k < 10) {
      System.out.println(((A) as[k]).get());
      k = k + 1;
    }
  }
}
