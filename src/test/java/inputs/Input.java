package inputs.javalang;

class A {
    public int method() { return 12345;}
    public String toString() {
        return "A";
    }
}

class B extends A {
    public String toString() { return "B"; }
}

public class Input {
    public static void main(String[] args) {
        B b = new B();
        A a1 = new A();
        A a2 = b;

        System.out.println(a1.toString());
        System.out.println(a2.toString());

        System.out.println(a1.method());
        System.out.println(b.method());

        Class ca1 = a1.getClass();
        System.out.println(ca1.toString());

        Class ca2 = a2.getClass();
        System.out.println(ca2.toString());

        if (a2 instanceof B) {
            System.out.println(a2.getClass().getSuperclass().toString());
        }
    }
}