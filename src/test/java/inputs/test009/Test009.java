package inputs.test009;

class A {
    public A self;

    public A() {
        self = this;
    }
}

public class Test009 {
    public static void main(String[] args) {
        A a = new A();
        System.out.println(a.self.toString());
    }
}