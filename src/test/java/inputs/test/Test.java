package inputs.test;

class A {
    A self;
    static int x = 3;

    public A(){}
    public A(int x) {
        self = this;
    }
    static int x() {
        return 4;
    }

    public A self() {
        return self;
    }
    void m() { System.out.println("A.m()"); }
    A m(A a) { System.out.println("A.m(A)"); return a; }
}

class B extends A {
    void m() { System.out.println("B.m()"); }
    C m(B b) { System.out.println("B.m(B)"); return new C(); }
    A m(A a) { System.out.println("B.m(A)"); return a; }
}

class C extends B {
    void m() { System.out.println("C.m()"); }
}

class AA {
    int i;
    public AA(int i) {
        this.i = i;
    }

    public int get() {
        return i;
    }
}

class BB extends AA {
    public BB(int i) {
        super(i);
    }

    public int get() {
        return (10 - i);
    }
}

class AAA {
    // class initialization block
    {
        System.out.println("class initialization block");
    }
    int m(byte b) { System.out.println("AAA.m(byte)"); return b; }
    int m(int i) { System.out.println("AAA.m(int)"); return i; }
    void m(double d) { System.out.println("AAA.m(double)"); }
}


public class Test {
    public static void main(String[] args) {
        //simple method call, type resolve and method overloading
        AAA aaa = new AAA();
        byte bbb = 1;
        double ddd = 1.0;
        aaa.m(bbb);
        aaa.m(bbb + bbb);
        aaa.m(ddd);

        //recursive method calls, method overloading and class cast
        B b = new B();
        A a = new A();
        b.m((A) b).m((B) b.m(b)).m();

        //static field, static method, name conflict resolving and constructor overloading
        int x;
        x = A.x;
        System.out.println(x);
        System.out.println(A.x());
        A a1 = new A(5);
        System.out.println(a1.self().toString());

        //array implement
        Object[] as = new AA[5];
        for(int i = 0; i < as.length; i++) {
            as[i] = new BB(i);
        }
        int k = 0;
        while(k < 5) {
            System.out.println(((AA) as[k]).get());
            k = k + 1;
        }

        //array check store
        AA[] a2 = new BB[10];
        for(int i = 0; i < as.length; i++) {
            a2[i] = new AA(i); // throws java.lang.ArrayStoreException
        }

    }
}
