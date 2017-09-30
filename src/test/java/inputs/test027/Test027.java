package inputs.test027;

class A {
  int i;
  public A(int i) {
    this.i = i;
  }

  public int get() {
    return i;
  }
}

public class Test027 {
  public static void main(String[] args) {
    A[] as = new A[10];
    
    for(int i = 0; i < as.length; i++) {
      as[i] = new A(i);
    }

    int k = 0;
    while(k < 11) {
      System.out.println(((A) as[k]).get()); // throws java.lang.ArrayIndexOutOfBoundsException
      k = k + 1;
    }
  }
}
