package inputs.test014;

class A {
    A some;

    public void printOther(A other) {
        System.out.println(other.toString());
    }
}

public class Test014 {
    public static void main(String[] args) {
        A a = new A();
        A other = a.some;
        a.printOther(other); // throws NullPointerException
    }
}