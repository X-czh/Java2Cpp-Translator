package inputs.test018;

class A {
    static int x;
}

public class Test018 {

    public static void main(String[] args) {
        {
            A.x = 3;
        }
        System.out.println(A.x);
    }
}