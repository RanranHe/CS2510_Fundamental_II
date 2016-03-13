// CS 2510 Fall 2014
// Assignment 3
// partner1-Leyi Qiang
// partner1-Drunkbug
// partner2-Ranran He
// partner2-heranran

import tester.*;

// to represent a list of Strings
interface ILoString {
    // combine all Strings in this list into one
    String combine();

    // produces a new list and sorted in alphabetical order
    ILoString sort();

    // does this String come before the given String lexicographically?
    ILoString insert(String that);

    // is this list sorted in alphabetical order?
    boolean isSorted();

    // is this String comes before that String?
    // using accumulator to keeps track of the String
    boolean isSortedHelper(String acc);

    // take two lists and crossover this two lists
    ILoString interleave(ILoString that);

    // cross the other lists to this lists
    ILoString interleaveHelper(ILoString that);

    // sort two lists by using merge sort
    ILoString merge(ILoString that);

    // reverse the order of that string
    ILoString reverse();

    // reverse the order of string by accumulate the string
    ILoString reverseHelper(ILoString acc);

    // determines if this list contains pairs of identical strings
    boolean isDoubledList();

    // determines if the first element of the list is the same as that string
    boolean isDoubledHelper(String that);

    // determines whether this list is palindrome list of that list
    boolean isPalindromeList();
}

// to represent an empty list of Strings
class MtLoString implements ILoString {

    // combine all Strings in this list into one
    public String combine() {
        return "";
    }

    // sorted a empty string in alphabetical order
    public ILoString sort() {
        return this;
    }

    // does this String come before the given String lexicographically?
    public ILoString insert(String that) {
        return new ConsLoString(that, this);
    }

    // is this list sorted in alphabetical order?
    public boolean isSorted() {
        return true;
    }

    // is this String comes before the empty list?
    public boolean isSortedHelper(String acc) {
        return true;
    }

    // cross this list of string to a empty list
    public ILoString interleave(ILoString that) {
        return that;
    }

    // cross that empty lists of string to this string
    public ILoString interleaveHelper(ILoString that) {
        return that;
    }

    // sort a list with a empty list
    public ILoString merge(ILoString that) {
        return that.sort();
    }

    // reverse the order of a empty string
    public ILoString reverse() {
        return reverseHelper(new MtLoString());
    }

    // reverse the order of string by accumulate the empty list
    public ILoString reverseHelper(ILoString acc) {
        return acc;
    }

    // determines if the empty list contains pairs of identical strings
    public boolean isDoubledList() {
        return true;
    }

    // determines if the first element of the list is the same as the empty list
    public boolean isDoubledHelper(String that) {
        return false;
    }

    // determines whether this list is palindrome list of that list
    public boolean isPalindromeList() {
        return true;
    }
}

// to represent a nonempty list of Strings
class ConsLoString implements ILoString {
    String first;
    ILoString rest;

    ConsLoString(String first, ILoString rest) {
        this.first = first;
        this.rest = rest;
    }

    /*
     * TEMPLATE FIELDS: ... this.first ...         -- String 
     * ... this.rest ...                           --ILoString
     * 
     * METHODS 
     * ... this.combine() ...                      -- String
     * ... this.sort()...                          -- ILoString
     * ... this.insert(String) ...                 -- ILoString
     * ... this.isSorted() ...                     -- boolean
     * ... this.isSortedHelper(String) ...         -- boolean
     * ... this.interleave(ILoString) ...          -- ILoString
     * ... this.interleaveHelper(ILoString)        -- ILoString
     * ... this.merge(ILoString) ...               -- ILoString
     * ... this.reverse() ...                      -- ILoString
     * ... this.reverseHelper(ILoString) ...       -- ILoString
     * ... this.isDoubledList() ...                -- boolean
     * ... this.isDoubledHelper(String) ...        -- boolean
     * ... this.isPalindromwList() ...             -- boolean
     * 
     * METHODS FOR FIELDS 
     * ... this.first.concat(String) ...                  -- String
     * ... this.first.compareTo(String) ...               -- int 
     * ... this.rest.combine() ...                        -- String 
     * ... rhis.rest.sort() ...                           -- ILoString
     * ... this.rest.insert(String)...                    -- ILoString 
     * ... this.rest.isSorted()...                        -- boolean 
     * ... this.rest.isSortedHelper(String)...            -- boolean
     * ... this.rest.interleave(ILoString)...             -- ILoString
     * ... this.rest.interleaveHelper(ILostring)...       -- ILoString
     * ... this.rest.merge(ILoString) ...                 -- ILoString
     * ... this.rest.reverse() ...                        -- ILoString
     * ... this.rest.reverseHelper(ILoString) ...         -- ILoString
     * ... this.rest.isDoubledList() ...                  -- boolean
     * ... this.rest.isDoubledHelper(String)...           -- boolean
     * ... this.rest.isPalindromwList() ...               -- boolean
     */

    // combine all Strings in this list into one
    public String combine() {
        return this.first.concat(this.rest.combine());
    }

    // sorted a list of string in alphabetical order
    public ILoString sort() {
        return this.rest.sort().insert(this.first);
    }

    // does this String come before the given String lexicographically?
    public ILoString insert(String that) {
        if (this.first.toLowerCase().compareTo(that.toLowerCase()) < 0
                || this.first.toLowerCase().compareTo(that.toLowerCase()) == 0) {
            return new ConsLoString(this.first, this.rest.insert(that));
        } 
        else {
            return new ConsLoString(that, this);
        }
    }

    // is this list sorted in alphabetical order?
    public boolean isSorted() {
        return this.rest.isSorted() && this.rest.isSortedHelper(this.first);
    }

    // is this String comes before that String?
    public boolean isSortedHelper(String acc) {
        return acc.toLowerCase().compareTo(this.first.toLowerCase()) <= 0;
    }

    // take two lists and crossover this two lists
    public ILoString interleave(ILoString that) {
        return new ConsLoString(this.first, that.interleaveHelper(this.rest));
    }

    // cross that list to this list
    public ILoString interleaveHelper(ILoString that) {
        return new ConsLoString(this.first, that.interleave(this.rest));
    }

    // sort two list by merge sort
    public ILoString merge(ILoString that) {
        return this.sort().interleave(that.sort()).sort();
    }

    // reverse the order of this list
    public ILoString reverse() {
        return this.reverseHelper(new MtLoString());
    }

    // reverse the order of the list by accumulate the string
    public ILoString reverseHelper(ILoString acc) {
        return this.rest.reverseHelper(new ConsLoString(this.first, acc));
    }

    // determines if this list contains pairs of identical strings
    public boolean isDoubledList() {
        return this.rest.isDoubledHelper(this.first);
    }

    // determines if the first element of the list is the same as that string
    public boolean isDoubledHelper(String that) {
        if (this.first == that) {
            return this.rest.isDoubledList();
        } 
        else {
            return false;
        }
    }

    // determines whether this list is palindrome list of that list
    public boolean isPalindromeList() {
        return this.interleave(this.reverse()).isDoubledList();
    }
}

// /////////////////////////////////////////////////////////////////////////////
// to represent examples for lists of strings
class ExamplesStrings {

    ILoString mary = new ConsLoString("Mary ", new ConsLoString("had ",
            new ConsLoString("a ", new ConsLoString("little ",
                    new ConsLoString("lamb.", new MtLoString())))));
    
    ILoString unsorted1 = new ConsLoString("dEf", new ConsLoString("dxy",
            new ConsLoString("aBc", new ConsLoString("uvw", new ConsLoString(
                    "lmn", new MtLoString())))));
    
    ILoString unsorted2 = new ConsLoString("All", new ConsLoString("Broken",
            new ConsLoString("any", new ConsLoString("before", new MtLoString()))));
    
    
    ILoString sorted1 = new ConsLoString("All", new ConsLoString("any",
            new ConsLoString("before", new ConsLoString("Broken", new MtLoString()))));

    
    ILoString mergelist1 = new ConsLoString("abc", new ConsLoString("ghi",
            new ConsLoString("mno", new MtLoString())));
    
    ILoString mergelist2 = new ConsLoString("def", new ConsLoString("jkl",
            new ConsLoString("pqi", new MtLoString())));
    

    ILoString doubledlist1 = new ConsLoString("a", new ConsLoString("a",
            new ConsLoString("b", new ConsLoString("b", new MtLoString()))));
    
    ILoString doubledlist2 = new ConsLoString("a", new ConsLoString("a",
            new ConsLoString("a", new ConsLoString("b", new MtLoString()))));
    
    ILoString doubledlist3 = new ConsLoString("a", new ConsLoString("a",
            new ConsLoString("a", new MtLoString())));

    
    ILoString reverse1 = new ConsLoString("onion", new ConsLoString("jkl",
            new ConsLoString("onion", new MtLoString())));

    // test the method combine for the lists of Strings
    boolean testCombine(Tester t) {
        return t.checkExpect(this.mary.combine(), "Mary had a little lamb.");
    }

    // test the method sort for the lists of Strings
    boolean testSort(Tester t) {
        return t.checkExpect(this.unsorted1.sort(), new ConsLoString("aBc",
                new ConsLoString("dEf", new ConsLoString("dxy",
                        new ConsLoString("lmn", new ConsLoString("uvw",
                                new MtLoString()))))))
                && t.checkExpect(this.unsorted2.sort(), new ConsLoString("All",
                        new ConsLoString("any", new ConsLoString("before",
                                new ConsLoString("Broken", new MtLoString())))));
    }
     // test the method insert of the lists of Strings
    boolean testInsert(Tester t) {
        return t.checkExpect(this.sorted1.insert("am"), 
                new ConsLoString("All", new ConsLoString("am", new ConsLoString("any",
                        new ConsLoString("before", 
                                new ConsLoString("Broken", new MtLoString()))))))
               && t.checkExpect(new MtLoString().insert("haha"),
                       new ConsLoString("haha", new MtLoString()));
    }

    // test the method isSorted for the lists of Strings
    boolean testSorted(Tester t) {
        return t.checkExpect(this.unsorted1.isSorted(), false)
                && t.checkExpect(this.unsorted1.sort().isSorted(), true)
                && t.checkExpect(this.sorted1.isSorted(), true);
    }
    //test the method isSortedHelper for the lists of Strings
    boolean testSortedHelper(Tester t) {
        return t.checkExpect(this.sorted1.isSortedHelper("aa"), true)
                && t.checkExpect(this.sorted1.isSortedHelper("zz"), false);
    }
    // test the method interleave for two lists of Strings
    boolean testCross(Tester t) {
        return t.checkExpect(this.unsorted1.interleave(this.mary), 
                new ConsLoString("dEf", new ConsLoString("Mary ",
                        new ConsLoString("dxy", new ConsLoString("had ",
                                new ConsLoString("aBc", new ConsLoString("a ",
                                        new ConsLoString("uvw",
                                                new ConsLoString("little ",
                                                        new ConsLoString("lmn",
                                                                new ConsLoString("lamb.",
                                                                        new MtLoString())))))))))))
                && t.checkExpect(this.unsorted1.interleave(new MtLoString()),
                        new ConsLoString("dEf", new ConsLoString("dxy",
                                new ConsLoString("aBc", new ConsLoString("uvw",
                                        new ConsLoString("lmn",
                                                new MtLoString()))))))
                && t.checkExpect(new MtLoString().interleave(this.unsorted1),
                        new ConsLoString("dEf", new ConsLoString("dxy",
                                new ConsLoString("aBc", new ConsLoString("uvw",
                                        new ConsLoString("lmn",
                                                new MtLoString()))))));
    }
    // test the method interleaveHelper for two lists of Strings
    boolean testCrossHelper(Tester t) {
        return t.checkExpect(this.unsorted1.interleaveHelper(this.mary), 
                new ConsLoString("dEf", new ConsLoString("Mary ",
                        new ConsLoString("dxy", new ConsLoString("had ",
                                new ConsLoString("aBc", new ConsLoString("a ",
                                        new ConsLoString("uvw",
                                                new ConsLoString("little ",
                                                        new ConsLoString("lmn",
                                                                new ConsLoString("lamb.",
                                                                        new MtLoString())))))))))))
                && t.checkExpect(this.unsorted1.interleaveHelper(new MtLoString()),
                        new ConsLoString("dEf", new ConsLoString("dxy",
                                new ConsLoString("aBc", new ConsLoString("uvw",
                                        new ConsLoString("lmn",
                                                new MtLoString()))))))
                && t.checkExpect(new MtLoString().interleaveHelper(this.unsorted1),
                        new ConsLoString("dEf", new ConsLoString("dxy",
                                new ConsLoString("aBc", new ConsLoString("uvw",
                                        new ConsLoString("lmn",
                                                new MtLoString()))))));
    }

    // test the method merge for two lists of Strings
    boolean testMerge(Tester t) {
        return t.checkExpect(this.unsorted1.merge(new MtLoString()),
                new ConsLoString("aBc", new ConsLoString("dEf",
                        new ConsLoString("dxy", new ConsLoString("lmn",
                                new ConsLoString("uvw", new MtLoString()))))))
                && t.checkExpect(new MtLoString().merge(this.unsorted1),
                        new ConsLoString("aBc", new ConsLoString("dEf",
                                new ConsLoString("dxy", new ConsLoString("lmn",
                                        new ConsLoString("uvw",
                                                new MtLoString()))))))
                && t.checkExpect(this.mergelist1.merge(this.mergelist2),
                        new ConsLoString("abc", new ConsLoString("def",
                                new ConsLoString("ghi", new ConsLoString("jkl",
                                        new ConsLoString("mno",
                                                new ConsLoString("pqi",
                                                        new MtLoString())))))))
                && t.checkExpect(this.mergelist1.merge(unsorted1), 
                        new ConsLoString("aBc", new ConsLoString("abc",
                                new ConsLoString("dEf", new ConsLoString("dxy",
                                        new ConsLoString("ghi", new ConsLoString("lmn",
                                                new ConsLoString("mno",
                                                        new ConsLoString("uvw",
                                                                new MtLoString())))))))));
    }

    // test the method reverse for a list of Strings
    boolean testReverse(Tester t) {
        return t.checkExpect(this.mergelist1.reverse(), new ConsLoString("mno",
                new ConsLoString("ghi", new ConsLoString("abc",
                        new MtLoString()))))
                && t.checkExpect(new MtLoString().reverse(), new MtLoString());
    }
    // test the method reverseHelper for a list of Strings
    boolean testReverseHelper(Tester t) {
        return t.checkExpect(new MtLoString().reverseHelper(this.sorted1), this.sorted1)
                && t.checkExpect(this.mergelist1.reverseHelper(new MtLoString()), 
                        new ConsLoString("mno",
                                new ConsLoString("ghi", new ConsLoString("abc",
                                        new MtLoString()))))
                && t.checkExpect(new ConsLoString("c", new ConsLoString("b", 
                        new MtLoString()).reverseHelper(new ConsLoString("a", new MtLoString()))),
                        new ConsLoString("c", new ConsLoString("b", 
                                new ConsLoString("a", new MtLoString()))));
    }
    // test the method isDoubledList for a list of Strings
    boolean testDoubled(Tester t) {
        return t.checkExpect(this.doubledlist1.isDoubledList(), true)
                && t.checkExpect(this.doubledlist2.isDoubledList(), false)
                && t.checkExpect(this.doubledlist3.isDoubledList(), false)
                && t.checkExpect(new MtLoString().isDoubledList(), true);
    }
    // test the method isDoubledHelper for a lists of Strings
    boolean testDoubledHelper(Tester t) {
        return t.checkExpect(new ConsLoString("dEf", new MtLoString()).isDoubledHelper("dEf"), true)
                && t.checkExpect(this.unsorted1.isDoubledHelper("ddd"), false)
                && t.checkExpect(new MtLoString().isDoubledHelper("abc"), false);
    }
    // test the method isPalindromeList for two lists of Strings
    boolean testPalindromelist(Tester t) {
        return t.checkExpect(this.mergelist2.isPalindromeList(), false)
                && t.checkExpect(new MtLoString().isPalindromeList(), true)
                && t.checkExpect(this.reverse1.isPalindromeList(), true);
    }
}