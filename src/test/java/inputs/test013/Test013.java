package inputs.test013;

class A {
    String a;

    public void setA(String x) {
        a = x;
    }

    public void printOther(A other) {
        System.out.println(other.toString());
    }
}

public class Test013 {
    public static void main(String[] args) {
        A a = new A();
        A other = a;
        other.setA("A");
        a.printOther(other);
    }
}