// Assignment 4
//partner1-Qiang Leyi
//partner1-Drunkbug
//partner2-He Ranran
//partner2-heranran

import tester.Tester;

//to represent different files in a computer
interface IFile {
    // compute the size of this file
    int size();

    // compute the time (in seconds) to download this file
    // at the given download rate
    int downloadTime(int rate);

    // get the owner of a IFile
    String sameOwnerHelper();

    // is the owner of this file the same
    // as the owner of the given file?
    boolean sameOwner(IFile that);
}

// to represent a file
abstract class AFile implements IFile {
    String name;
    String owner;
    
    // constructor
    AFile(String name, String owner) {
        this.name = name;
        this.owner = owner;
    } 
    
    // compute the size of this file
    public abstract int size();
    
    // compute the time (in seconds) to download this file
    // at the given download rate
    public int downloadTime(int rate) {
        if (this.size() % rate == 0) {
            return this.size() / rate;
        }
        else {
            return this.size() / rate + 1;
        }
    }
    
    //get the owner of a IFile
    public String sameOwnerHelper() {
        return this.owner;
    }
    
    // is the owner of this file the same 
    // as the owner of the given file?
    public boolean sameOwner(IFile that) {
        return this.owner.equals(that.sameOwnerHelper());
    }
}


//to represent a text file
class TextFile extends AFile {
    int length;   // in bytes
    
    // constructor
    TextFile(String name, String owner, int length) {
        super(name, owner);
        this.length = length;
    }
    
    /* TEMPLATE
     Fields:
     ... this.name ...  -- String
     ... this.owner ...  -- String
     ... this.length ...  -- int
     Methods:
     ... this.size() ...  -- int
     ... this.downloadTime(int rate) ...  -- int
     ... this.sameOwnerHelper() ...  -- String
     ... this.sameOwner(IFile that) ...  -- boolean
     */
    
    // compute the size of this file
    public int size() {
        return this.length;
    }  
}

//to represent an image file
class ImageFile extends AFile { 
    int width;   // in pixels
    int height;  // in pixels
    
    // constructor
    ImageFile(String name, String owner, int width, int height) {
        super(name, owner);
        this.width = width;
        this.height = height;
    }
  
    /* TEMPLATE
       Fields:
       ... this.name ...  -- String
       ... this.owner ...  -- String
       ... this.width ...  -- int
       ... this.height ...  -- int
       Methods:
       ... this.size() ...  -- int
       ... this.downloadTime(int rate) ...  -- int
       ... this.sameOwnerHelper() ...  -- String
       ... this.sameOwner(IFile that) ...  -- boolean
     */
  
    // compute the size of this file
    public int size() {
        return this.width * this.height;
    }  
}

//to represent an audio file
class AudioFile extends ImageFile {
    
    // constructor
    AudioFile(String name, String owner, int speed, int length) {
        super(name, owner, speed, length);
    }
  
    /* TEMPLATE
      Fields:
      ... this.name ...  -- String
      ... this.owner ...  -- String
      ... this.spped ...  -- int
      ... this.length ...  -- int
      Methods:
      ... this.size() ...  -- int
      ... this.downloadTime(int rate) ...  -- int
      ... this.sameOwnerHelper() ...  -- String
      ... this.sameOwner(IFile that) ...  -- boolean
     */
}

// give examples of files
class ExamplesFiles {
    
    IFile text1 = new TextFile("English paper", "Maria", 1234);
    IFile picture = new ImageFile("Beach", "Maria", 400, 200);
    IFile song = new AudioFile("Help", "Pat", 200, 120);
    
    // one more example of data for each of the three classes
    IFile text2 = new TextFile("Math World", "Daniel", 1000);
    IFile text3 = new TextFile("Times", "Mary", 500);
    IFile picture2 = new ImageFile("Sea", "Daniel", 400, 400);
    IFile picture3 = new ImageFile("Bird", "Sherry", 400, 100);
    IFile song2 = new AudioFile("Y", "Petter", 200, 100);
    IFile song3 = new AudioFile("By", "Mary", 300, 100);
    
    // test the method size for the classes that represent files
    boolean testSize(Tester t) {
        return
            t.checkExpect(this.text1.size(), 1234) &&
            t.checkExpect(this.picture.size(), 80000) &&
            t.checkExpect(this.song.size(), 24000) &&
            t.checkExpect(this.text2.size(), 1000) &&
            t.checkExpect(this.picture2.size(), 160000) &&
            t.checkExpect(this.song2.size(), 20000);
    }
    
    // test the method downloadTime 
    boolean testDownloadTime(Tester t) {
        return 
            t.checkExpect(this.text1.downloadTime(10), 124) &&
            t.checkExpect(this.picture.downloadTime(100), 800) &&
            t.checkExpect(this.song.downloadTime(240), 100) &&
            t.checkExpect(text2.downloadTime(100), 10);
    }
    
    // test the method sameOwnerHelper
    boolean testSameOwnerHelper(Tester t) {
        return 
            t.checkExpect(this.text1.sameOwnerHelper(), "Maria") &&
            t.checkExpect(this.text2.sameOwnerHelper(), "Daniel") &&
            t.checkExpect(this.picture.sameOwnerHelper(), "Maria") &&
            t.checkExpect(this.picture2.sameOwnerHelper(), "Daniel") &&
            t.checkExpect(this.song.sameOwnerHelper(), "Pat") &&
            t.checkExpect(this.song3.sameOwnerHelper(), "Mary");
    }
    
    // test the method sameOwner
    boolean testSameOwner(Tester t) {
        return
            t.checkExpect(this.text1.sameOwner(this.text2), false) &&
            t.checkExpect(this.text1.sameOwner(this.picture), true) &&
            t.checkExpect(this.picture.sameOwner(this.picture2), false);        
    }
}