package inputs.test029;

class A {
  int i;
  public A(int i) {
    this.i = i;
  }

  public int get() {
    return i;
  }
}

public class Test029 {
  public static void main(String[] args) {
    A[] as = new A[-1]; // throws java.lang.NegativeArraySizeException
    
    System.out.println(as.getClass().toString());
  }
}
