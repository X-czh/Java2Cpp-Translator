package inputs.test006;

class A {
    private String fld = "A";

    public A() { }

    public void setFld(String f) {
        fld = f;
    }

    public void almostSetFld(String f) {
        String fld;
        fld = f;
    }

    public String getFld() {
        return fld;
    }
}

public class Test006 {
    public static void main(String[] args) {
        A a = new A();
        a.almostSetFld("B");
        System.out.println(a.getFld());
        a.setFld("B");
        System.out.println(a.getFld());
    }
}