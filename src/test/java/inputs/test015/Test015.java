package inputs.test015;

class A {
    public A some;

    public void printOther(A other) {
        System.out.println(other.toString());
    }
}

class B extends A {
    public void printOther(A other) {
        System.out.println(other.toString());
    }

    public String toString() {
        return some.toString();
    }
}

public class Test015 {
    public static void main(String[] args) {
        A a = new A();
        B other = new B();
        other.some = a;
        a.printOther(other);
    }
}