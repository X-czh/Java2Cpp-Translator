package inputs.test010;

class A {
    String a;

    public void setA(String x) {
        a = x;
    }

    public void printOther(A other) {
        System.out.println(other.toString());
    }

    public String toString() {
        return a;
    }
}

class B1 extends A {
    String b;
}

class B2 extends A {
    String b;
}

class C extends B1 {
    String c;
}

public class Test010 {
    public static void main(String[] args) {
        A a = new A();
        a.setA("A");
        B1 b1 = new B1();
        b1.setA("B1");
        B2 b2 = new B2();
        b2.setA("B2");
        C c = new C();
        c.setA("C");
        a.printOther(a);
        a.printOther(b1);
        a.printOther(b2);
        a.printOther(c);
    }
}