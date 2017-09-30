package inputs.test019;

class A {
    static int x;
}

public class Test019 {
    public static void main(String[] args) {
        int x;
        x = A.x;
        System.out.println(x);
    }
}