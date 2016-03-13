import tester.Tester;
//Assignment 6
//partner1-Leyi Qiang
//partner1-Drunkbug
//partner2-Ranran He
//partner2-heranran
// to represent a list of string
interface IList<T> {

    // determine whether the given string comes before all elements of the list
    // by the given way
    boolean isSortedHelper(T t, IComparator<T> comp);

    // determine whether the list is already sorted by the given way
    boolean isSorted(IComparator<T> comp);

    // merge two sorted lists into one sorted list by the given way
    IList<T> merge(IList<T> lot, IComparator<T> comp);

    // insert a string into a sorted list by the give way
    IList<T> mergeHelper(T t, IComparator<T> comp);

    // sort a list by the given way
    IList<T> sort(IComparator<T> comp);

    // insert a type to a list
    IList<T> insert(IComparator<T> comp, T t);

    // determine whether the two lists have the same data in same order
    boolean sameList(IList<T> lot);
}

// to represent a empty list
class Empty<T> implements IList<T> {

    /*
     * ... isSortedHelper(String s, IStringsCompare comp) ... -- boolean 
     * ... isSorted(IStringsCompare comp) ... -- boolean 
     * ... merge(IList lot, IStringsCompare comp) ... -- IList 
     * ... mergeHelper(String s, IStringsCompare comp) ... -- IList 
     * ... sort(IStringsCompare comp) ... -- IList 
     * ... sameList(IList lot) ... -- boolean
     */

    // determine whether the given string comes before all elements of the list
    // by the given way
    public boolean isSortedHelper(T t, IComparator<T> comp) {
        return true;
    }

    // determine whether the list is already sorted by the given way
    public boolean isSorted(IComparator<T> comp) {
        return true;
    }

    // merge two sorted lists into one sorted list by the given way
    public IList<T> merge(IList<T> lot, IComparator<T> comp) {
        return lot;
    }

    // insert a string into a sorted list by the give way
    public IList<T> mergeHelper(T t, IComparator<T> comp) {
        return new Cons<T>(t, new Empty<T>());
    }

    // sort a list by the given way
    public IList<T> sort(IComparator<T> comp) {
        return new Empty<T>();
    }

    // insert a type to a empty list
    public IList<T> insert(IComparator<T> comp, T t) {
        return new Cons<T>(t, this);
    }

    // determine whether the two lists have the same data in same order
    public boolean sameList(IList<T> lot) {
        return lot instanceof Empty;
    }
}

// to represent a list of string
class Cons<T> implements IList<T> {
    T first;
    IList<T> rest;

    // constructor
    Cons(T first, IList<T> rest) {
        this.first = first;
        this.rest = rest;
    }

    /*
     * ... this.first... -- String ... this.rest ... -- IList<T>
     * 
     * ... isSortedHelper(T s, IComparator<T> comp) ... -- boolean 
     * ... isSorted(IComparator<T> comp) ... -- boolean 
     * ... merge(IList lot, IStringsCompare<T> comp) ... -- IList<T>
     * ... mergeHelper(T t, IStringsCompare<T> comp) ... -- IList<T>
     * ... sort(IStringsCompare comp) ... -- IList<T>
     * ... sameList(IList<T> lot) ... -- boolean
     * ... this.rest.isSortedHelper(T t, IComparator comp) ... -- boolean
     * ... this.rest.isSorted(IComparator comp) ... -- boolean 
     * ... this.rest.merge(IList<T> lot, IComparator<T> comp) ... -- IList<T>
     * ... this.rest.mergeHelper(T t, IComparator<T> comp) ... -- IList<T>
     * ... this.rest.sort(IComparator<T> comp) ... -- IList<T>
     * ... this.rest.sameList(IList<T> lot) ... -- boolean
     */

    // determine whether the given string comesbefore all elements of the list
    // by the given way
    public boolean isSortedHelper(T t, IComparator<T> comp) {
        return comp.compare(t, this.first) <= 0
                && this.rest.isSortedHelper(t, comp);
    }

    // determine whether the list is already sorted by the given way
    public boolean isSorted(IComparator<T> comp) {
        return this.rest.isSortedHelper(this.first, comp)
                && this.rest.isSorted(comp);
    }

    // merge two sorted lists into one sorted list by the given way
    public IList<T> merge(IList<T> lot, IComparator<T> comp) {
        if (lot instanceof Empty) {
            return this;
        }
        else {
            return this.mergeCons((Cons<T>) lot, (IComparator<T>) comp);
        }
    }

    // merge two sorted conslists into one sorted conslist by the given way
    IList<T> mergeCons(Cons<T> lot, IComparator<T> comp) {
        return this.mergeHelper(lot.first, comp).merge(lot.rest, comp);
    }

    // insert a string into a sorted list by the give way
    public IList<T> mergeHelper(T t, IComparator<T> comp) {
        if (comp.compare(t, this.first) <= 0) {
            return new Cons<T>(t, this);
        }
        else {
            return new Cons<T>(this.first, this.rest.mergeHelper(t, comp));
        }
    }

    // sort a list by the given way
    public IList<T> sort(IComparator<T> comp) {
        return this.rest.sort(comp).insert(comp, this.first);
    }

    // insert a string to a list
    public IList<T> insert(IComparator<T> comp, T t) {
        if (comp.compare(t, this.first) < 0) {
            return new Cons<T>(t, this);
        }
        else {
            return new Cons<T>(this.first, this.rest.insert(comp, t));
        }
    }

    // determine whether the two lists have the same data in same order
    public boolean sameList(IList<T> lot) {
        return lot instanceof Cons<?> && this.sameListHelper((Cons<T>) lot);
    }

    // determine whether the two Cons is the same
    boolean sameListHelper(Cons<T> lot) {
        return this.first.equals(lot.first) && this.rest.sameList(lot.rest);
    }
}

interface IComparator<T> {
    // Returns a negative number if t1 comes before t2 in this ordering
    // Returns zero if t1 is the same as t2 in this ordering
    // Returns a positive number if t1 comes after t2 in this ordering
    int compare(T t1, T t2);
}

// compare two strings lexicographically
class StringLexCompGen implements IComparator<String> {
    public int compare(String s1, String s2) {
        return s1.compareTo(s2);
    }
}

// compare the two strings depend on their length
class StringLengthCompGen implements IComparator<String> {
    public int compare(String s1, String s2) {
        if (s1.length() < s2.length()) {
            return -1;
        }
        else if (s1.length() == s2.length()) {
            return 0;
        }
        else {
            return 1;
        }
    }
}

// give the examples to list of string
class Examples {
    IComparator<String> LenComp = new StringLengthCompGen();
    IList<String> mt = new Empty<String>();
    // not sorted lists
    IList<String> ln1 = new Cons<String>("F", new Cons<String>("A",
            new Cons<String>("C", this.mt)));
    IList<String> lln1 = new Cons<String>("aa", new Cons<String>("a",
            new Cons<String>("aabbccdd", new Cons<String>("aabbc",
                    new Cons<String>("aabb", new Empty<String>())))));

    // sorted lists by lex
    IList<String> l1 = new Cons<String>("A", new Cons<String>("C",
            new Cons<String>("F", this.mt)));
    IList<String> l2 = new Cons<String>("B", new Cons<String>("E",
            new Cons<String>("G", new Cons<String>("H", new Empty<String>()))));
    IList<String> l3 = new Cons<String>("A", new Cons<String>("B",
            new Cons<String>("C", new Cons<String>("E", new Cons<String>("F",
                    new Cons<String>("G", new Cons<String>("H",
                            new Empty<String>())))))));

    // sorted lists by length
    IList<String> ll1 = new Cons<String>("a", new Cons<String>("aa",
            new Cons<String>("aabb", new Cons<String>("aabbc",
                    new Cons<String>("aabbccdd", new Empty<String>())))));
    IList<String> ll2 = new Cons<String>("aaa", new Cons<String>("aabbcc",
            new Cons<String>("aabbccd", new Cons<String>("aabbccdde",
                    new Empty<String>()))));
    IList<String> ll3 = new Cons<String>("a", new Cons<String>("aa",
            new Cons<String>("aaa", new Cons<String>("aabb", new Cons<String>(
                    "aabbc", new Cons<String>("aabbcc", new Cons<String>(
                            "aabbccd", new Cons<String>("aabbccdd",
                                    new Cons<String>("aabbccdde",
                                            new Empty<String>())))))))));

    // test isSortedHelper(String s, IStringsCompare comp)
    boolean testIsSortedHelper(Tester t) {
        return t.checkExpect(
                this.mt.isSortedHelper("a", new StringLexCompGen()), true)
                && t.checkExpect(
                        this.l2.isSortedHelper("A", new StringLexCompGen()),
                        true)
                && t.checkExpect(
                        this.mt.isSortedHelper("a", new StringLengthCompGen()),
                        true)
                && t.checkExpect(
                        this.ll2.isSortedHelper("a", new StringLengthCompGen()),
                        true);

    }

    // test the method isSorted
    boolean testIsSorted(Tester t) {
        return t.checkExpect(this.mt.isSorted(new StringLexCompGen()), true)
                && t.checkExpect(this.l1.isSorted(new StringLexCompGen()), true)
                && t.checkExpect(this.ln1.isSorted(new StringLexCompGen()),
                        false)
                && t.checkExpect(this.lln1.isSorted(new StringLengthCompGen()),
                        false)
                && t.checkExpect(this.ll1.isSorted(new StringLengthCompGen()),
                        true)
                && t.checkExpect(this.mt.isSorted(new StringLengthCompGen()),
                        true);
    }

    // test the method mergeHelper(String s, IStringsCompare comp)
    boolean testMergeHelper(Tester t) {
        return t.checkExpect(this.mt.mergeHelper("a", new StringLexCompGen()),
                new Cons<String>("a", this.mt))
                && t.checkExpect(
                        this.l2.mergeHelper("A", new StringLexCompGen()),
                        new Cons<String>("A", this.l2))
                && t.checkExpect(
                        this.ll2.mergeHelper("a", new StringLengthCompGen()),
                        new Cons<String>("a", this.ll2))
                && t.checkExpect(
                        this.mt.mergeHelper("aaa", new StringLengthCompGen()),
                        new Cons<String>("aaa", this.mt));

    }

    // test the method merge
    boolean testMerge(Tester t) {
        return t.checkExpect(this.mt.merge(this.l1, new StringLexCompGen()),
                this.l1)
                && t.checkExpect(
                        this.l1.merge(this.l2, new StringLexCompGen()), this.l3)
                && t.checkExpect(
                        this.ll1.merge(this.ll2, new StringLengthCompGen()),
                        this.ll3)
                && t.checkExpect(
                        this.mt.merge(this.ll2, new StringLengthCompGen()),
                        this.ll2)
                && t.checkExpect(
                        this.ll1.merge(this.mt, new StringLengthCompGen()),
                        this.ll1);

    }

    // test the method sort
    boolean testSort(Tester t) {
        return t.checkExpect(this.ln1.sort(new StringLexCompGen()), this.l1)
                && t.checkExpect(this.lln1.sort(new StringLengthCompGen()),
                        this.ll1)
                && t.checkExpect(this.mt.sort(new StringLengthCompGen()),
                        this.mt);
    }

    // test the method sameList
    boolean testSameList(Tester t) {
        return t.checkExpect(this.l1.sameList(ln1), false)
                && t.checkExpect(this.mt.sameList(this.mt), true)
                && t.checkExpect(this.ll1.sameList(ln1), false)
                && t.checkExpect(this.lln1.sameList(ll1), false);
    }
}
