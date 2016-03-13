// represent the interface IPredicator
interface IPred<T> {
    boolean apply(T t);
}

class SameString implements IPred<String> {
    String s1;

    // the constructor
    SameString(String s1) {
        this.s1 = s1;
    }

    // is s1 the same as s2?
    public boolean apply(String s) {
        return s1.equals(s);
    }
}

