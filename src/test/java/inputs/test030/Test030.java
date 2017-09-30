package inputs.test030;

class A {
  int i;
  public A(int i) {
    this.i = i;
  }

  public int get() {
    return i;
  }
}

public class Test030 {
  public static void main(String[] args) {
    A[][] as = new A[5][5]; 
    
    System.out.println(as.getClass().toString());
  }
}
