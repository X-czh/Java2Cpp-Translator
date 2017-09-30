package inputs.test020;

class A {
    static int x;
    static int x() {
        return 4;
    }
}

public class Test020 {
    public static void main(String[] args) {
        int x;
        x = A.x;
        System.out.println(A.x());
    }
}