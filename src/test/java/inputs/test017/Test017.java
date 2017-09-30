package inputs.test017;

class A {
    A self;

    public A(int x) {
        self = this;
    }

    public A self() {
        return self;
    }
}

public class Test017 {
    public static void main(String[] args) {
        A a = new A(5);
        System.out.println(a.self().toString());
    }
}
