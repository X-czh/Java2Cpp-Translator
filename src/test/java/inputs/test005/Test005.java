package inputs.test005;

class A {
    public String toString() {
        return "A";
    }
}

class B extends A {
    public String toString() {
        return "B";
    }
}


public class Test005 {
    public static void main(String[] args) {
        B b = new B();
        A a1 = new A();
        A a2 = b;
        System.out.println(a1.toString());
        System.out.println(a2.toString());
    }
}