import tester.Tester;
//Assignment 6
//partner1-Leyi Qiang
//partner1-Drunkbug
//partner2-Ranran He
//partner2-heranran


// to represent a list of string
interface ILoString {
    // determine whether the given string comesbefore all elements of the list
    // by the given way
    boolean isSortedHelper(String s, IStringsCompare sc);

    // determine whether the list is already sorted by the given way
    boolean isSorted(IStringsCompare sc);

    // merge two sorted lists into one sorted list by the given way
    ILoString merge(ILoString los, IStringsCompare sc);

    // insert a string into a sorted list by the give way
    ILoString mergeHelper(String s, IStringsCompare sc);

    // sort a list by the given way
    ILoString sort(IStringsCompare sc);

    // insert a String to the LoString
    ILoString insert(IStringsCompare sc, String that);

    // determine whether the two lists have the same data in same order
    boolean sameList(ILoString los);
}

// to represent a empty list
class MtLoString implements ILoString {

    /*
     * ... isSortedHelper(String s, IStringsCompare sc) ... -- boolean ...
     * isSorted(IStringsCompare sc) ... -- boolean ... merge(ILoString los,
     * IStringsCompare sc) ... -- ILoString ... mergeHelper(String s,
     * IStringsCompare sc) ... -- ILoString ... sort(IStringsCompare sc) ... --
     * ILoString ... sameList(ILoString los) ... -- boolean
     */

    // determine whether the given string comesbefore all elements of the list
    // by the given way
    public boolean isSortedHelper(String s, IStringsCompare sc) {
        return true;
    }

    // determine whether the list is already sorted by the given way
    public boolean isSorted(IStringsCompare sc) {
        return true;
    }

    // merge two sorted lists into one sorted list by the given way
    public ILoString merge(ILoString los, IStringsCompare sc) {
        return los;
    }

    // insert a string into a sorted list by the give way
    public ILoString mergeHelper(String s, IStringsCompare sc) {
        return new ConsLoString(s, new MtLoString());
    }

    // sort a list by the given way
    public ILoString sort(IStringsCompare sc) {
        return new MtLoString();
    }

    // determine whether the two lists have the same data in same order
    public boolean sameList(ILoString los) {
        return los instanceof MtLoString;
    }

    // insert that string to a empty list
    public ILoString insert(IStringsCompare sc, String that) {
        return new ConsLoString(that, this);
    }
}

// to represent a list of string
class ConsLoString implements ILoString {
    String first;
    ILoString rest;

    // constructor
    ConsLoString(String first, ILoString rest) {
        this.first = first;
        this.rest = rest;
    }

    /*
     * ... this.first... -- String ... this.rest ... -- ILoString
     * 
     * ... isSortedHelper(String s, IStringsCompare sc) ... -- boolean ...
     * isSorted(IStringsCompare sc) ... -- boolean ... merge(ILoString los,
     * IStringsCompare sc) ... -- ILoString ... mergeHelper(String s,
     * IStringsCompare sc) ... -- ILoString ... sort(IStringsCompare sc) ... --
     * ILoString ... sameList(ILoString los) ... -- boolean
     * 
     * ... this.rest.isSortedHelper(String s, IStringsCompare sc) ... -- boolean
     * ... this.rest.isSorted(IStringsCompare sc) ... -- boolean ...
     * this.rest.merge(ILoString los, IStringsCompare sc) ... -- ILoString ...
     * this.rest.mergeHelper(String s, IStringsCompare sc) ... -- ILoString ...
     * this.rest.sort(IStringsCompare sc) ... -- ILoString ...
     * this.rest.sameList(ILoString los) ... -- boolean
     */

    // determine whether the given string comesbefore all elements of the list
    // by the given way
    public boolean isSortedHelper(String s, IStringsCompare sc) {
        return sc.comesBefore(s, this.first) && this.rest.isSortedHelper(s, sc);
    }

    // determine whether the list is already sorted by the given way
    public boolean isSorted(IStringsCompare sc) {
        return this.rest.isSortedHelper(this.first, sc)
                && this.rest.isSorted(sc);
    }

    // merge two sorted lists into one sorted list by the given way
    public ILoString merge(ILoString los, IStringsCompare sc) {
        if (los instanceof MtLoString) {
            return this;
        }
        else {
            return this.mergeCons((ConsLoString) los, (IStringsCompare) sc);
        }
    }

    // merge two sorted conslists into one sorted conslist by the given way
    ILoString mergeCons(ConsLoString los, IStringsCompare sc) {
        return this.mergeHelper(los.first, sc).merge(los.rest, sc);
    }

    // insert a string into a sorted list by the give way
    public ILoString mergeHelper(String s, IStringsCompare sc) {
        if (sc.comesBefore(s, this.first)) {
            return new ConsLoString(s, this);
        }
        else {
            return new ConsLoString(this.first, this.rest.mergeHelper(s, sc));
        }
    }

    // sort a list by the given way
    public ILoString sort(IStringsCompare sc) {
        return this.rest.sort(sc).insert(sc, this.first);
    }

    // insert a string to a list
    public ILoString insert(IStringsCompare sc, String that) {
        if (sc.comesBefore(that, this.first)) {
            return new ConsLoString(that, this);
        }
        else {
            return new ConsLoString(this.first, this.rest.insert(sc, that));
        }
    }

    // determine whether the two lists have the same data in same order
    public boolean sameList(ILoString los) {
        return los instanceof ConsLoString
                && this.sameConsLoString((ConsLoString) los);
    }

    // determine whether the two ConsLoString is the same
    boolean sameConsLoString(ConsLoString los) {
        return this.first.equals(los.first) && this.rest.sameList(los.rest);
    }
}

// to represent the comparation between two strings
interface IStringsCompare {
    // Returns true if s1 comes before s2 according to this ordering
    boolean comesBefore(String s1, String s2);
}

// compare two strings lexicographically
class StringLexComp implements IStringsCompare {
    public boolean comesBefore(String s1, String s2) {
        return s1.compareTo(s2) <= 0;
    }
}

// compare the two strings depend on their length
class StringLengthComp implements IStringsCompare {
    public boolean comesBefore(String s1, String s2) {
        return s1.length() <= s2.length();
    }
}

// give the examples to list of string
class ExamplesLoString {
    ILoString mt = new MtLoString();
    // not sorted lists
    ILoString ln1 = new ConsLoString("F", new ConsLoString("A",
            new ConsLoString("C", this.mt)));
    ILoString lln1 = new ConsLoString("aa", new ConsLoString("a",
            new ConsLoString("aabbccdd", new ConsLoString("aabbc",
                    new ConsLoString("aabb", new MtLoString())))));

    // sorted lists by lex
    ILoString l1 = new ConsLoString("A", new ConsLoString("C",
            new ConsLoString("F", this.mt)));
    ILoString l2 = new ConsLoString("B", new ConsLoString("E",
            new ConsLoString("G", new ConsLoString("H", new MtLoString()))));
    ILoString l3 = new ConsLoString("A", new ConsLoString("B",
            new ConsLoString("C", new ConsLoString("E", new ConsLoString("F",
                    new ConsLoString("G", new ConsLoString("H",
                            new MtLoString())))))));

    // sorted lists by length
    ILoString ll1 = new ConsLoString("a", new ConsLoString("aa",
            new ConsLoString("aabb", new ConsLoString("aabbc",
                    new ConsLoString("aabbccdd", new MtLoString())))));
    ILoString ll2 = new ConsLoString("aaa", new ConsLoString("aabbcc",
            new ConsLoString("aabbccd", new ConsLoString("aabbccdde",
                    new MtLoString()))));
    ILoString ll3 = new ConsLoString("a", new ConsLoString("aa",
            new ConsLoString("aaa", new ConsLoString("aabb", new ConsLoString(
                    "aabbc", new ConsLoString("aabbcc", new ConsLoString(
                            "aabbccd", new ConsLoString("aabbccdd",
                                    new ConsLoString("aabbccdde",
                                            new MtLoString())))))))));

    // test isSortedHelper(String s, IStringsCompare sc)
    boolean testIsSortedHelper(Tester t) {
        return t.checkExpect(this.mt.isSortedHelper("a", new StringLexComp()),
                true)
                && t.checkExpect(
                        this.l2.isSortedHelper("A", new StringLexComp()), true)
                && t.checkExpect(
                        this.mt.isSortedHelper("a", new StringLengthComp()),
                        true)
                && t.checkExpect(
                        this.ll2.isSortedHelper("a", new StringLengthComp()),
                        true);

    }

    // test the method isSorted
    boolean testIsSorted(Tester t) {
        return t.checkExpect(this.mt.isSorted(new StringLexComp()), true)
                && t.checkExpect(this.l1.isSorted(new StringLexComp()), true)
                && t.checkExpect(this.ln1.isSorted(new StringLexComp()), false)
                && t.checkExpect(this.lln1.isSorted(new StringLengthComp()),
                        false)
                && t.checkExpect(this.ll1.isSorted(new StringLengthComp()),
                        true)
                && t.checkExpect(this.mt.isSorted(new StringLengthComp()), true);
    }

    // test the method mergeHelper(String s, IStringsCompare sc)
    boolean testMergeHelper(Tester t) {
        return t.checkExpect(this.mt.mergeHelper("a", new StringLexComp()),
                new ConsLoString("a", this.mt))
                && t.checkExpect(this.l2.mergeHelper("A", new StringLexComp()),
                        new ConsLoString("A", this.l2))
                && t.checkExpect(
                        this.ll2.mergeHelper("a", new StringLengthComp()),
                        new ConsLoString("a", this.ll2))
                && t.checkExpect(
                        this.mt.mergeHelper("aaa", new StringLengthComp()),
                        new ConsLoString("aaa", this.mt));

    }

    // test the method merge
    boolean testMerge(Tester t) {
        return t.checkExpect(this.mt.merge(this.l1, new StringLexComp()),
                this.l1)
                && t.checkExpect(this.l1.merge(this.l2, new StringLexComp()),
                        this.l3)
                && t.checkExpect(
                        this.ll1.merge(this.ll2, new StringLengthComp()),
                        this.ll3)
                && t.checkExpect(
                        this.mt.merge(this.ll2, new StringLengthComp()),
                        this.ll2)
                && t.checkExpect(
                        this.ll1.merge(this.mt, new StringLengthComp()),
                        this.ll1);

    }

    // test the method sort
    boolean testSort(Tester t) {
        return t.checkExpect(this.ln1.sort(new StringLexComp()), this.l1)
                && t.checkExpect(this.lln1.sort(new StringLengthComp()),
                        this.ll1)
                && t.checkExpect(this.mt.sort(new StringLengthComp()), this.mt);
    }

    // test the method sameList
    boolean testSameList(Tester t) {
        return t.checkExpect(this.l1.sameList(ln1), false)
                && t.checkExpect(this.mt.sameList(this.mt), true)
                && t.checkExpect(this.ll1.sameList(ln1), false)
                && t.checkExpect(this.lln1.sameList(ll1), false);
    }
}