package inputs.test;

class A {
    public String str = "A";

    public A() {
        str = "A.default";
    }

    public A(int i) {
        str = "A.i";
    }

    public int method() { return 12345;}
    public String toString() {
        return str;
    }
}

class B extends A {
    int i = 5;

    public B() {

    }

    public void test(int i) {
        int t = i;
    }

    static public void test() {
        int st = 3;
    }

    public B(int i) {
        super(i);
        this.i = 4;
        test(i);
        test();
    }

    public String toString() { return str; }

}

public class Test {
    public static void main(String[] args) {
        B a = new B(3);
        System.out.print(a.toString());
    }
}
