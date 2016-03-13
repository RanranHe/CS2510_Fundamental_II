import tester.Tester;
// Assignment 2
// partner1-Leyi Qiang
// partner1-Drunkbug
// partner2-Ranran He
// partner2-heranran

//+----------------+
//|    WebPage     |<----------------------------+
//|----------------|                             |
//|  String url    |                             |
//|  String title  |                             |
//|  ILoItem   items--|------------>+------+        |
//+----------------+             | ILoItem |        |
//                               |------|        |
//                            +--|-IItem|        |
//                            |  | ILoItem |        |
//                            |  +------+        |
//                            |                  |
//                       +----+                  |
//                       |                       |
//                      \ /                      |
//                   +------+                    |
//                   | IItem|                    |
// +---------+       |------|      +--------+    |
// | image   |       | Text-|----->| text   |    |
// |---------| <-----|-Image|      |--------|    |
// | String  |  +----|-Link |      |String  |    |
// |file-name|  |    +------+      |contents|    |
// |---------|  |                  +--------+    |
// |int size |  |                                |
// |---------|  |                                |
// | String  |  +------>+--------+               |
// |file-type|          |  link  |               |
// +---------+          |--------|               |
//                      | String |               |
//                      |  name  |               |
//                      |--------|               |
//                      | WebPage|---------------
//                      |  page  |
//                      +--------+

class WebPage {
    String url;
    String title;
    ILoItem items;
    
    // the constructor
    WebPage(String url, String title, ILoItem items) {
        this.url = url;
        this.title = title;
        this.items = items;
    }
    
    // count the total image size of the webpage
    int totalImageSize() {
        return this.items.totalImageSize();
    }
    
    // count the total number of letters in all text that
    // appears on the web site
    int textLength() {
        return this.title.length() + this.items.textLength();
    }
    
    // produces one String that has all names of images in the website
    String images() {
        return this.items.images();
    }
    /*
     * fields:
     * this.url ... String
     * this.title ... String
     * this.items ... ILoItem
     * methods:
     * totalImageSize() ... int
     * textLength() ... int
     * images() ... String
     * methods for fields:
     * this.items.totalImageSize() ... int
     * this.items.textLength() ... int
     * this.items.images() ... String 
     */
    
}

interface ILoItem {
    // count the total image size in the item list
    int totalImageSize();
    
    // count the total number of letters in all text that
    // appears in the list of item
    int textLength();
    
    // produces one String that has all names of images in the list of items
    String images();
}

// represent the empty list of items
class MtLoItem implements ILoItem {
    // count the total image size in empty list
    public int totalImageSize() {
        return 0;
    }
    // count the total number of letters in all text that
    // appears in the empty list
    public int textLength() {
        return 0;
    }
    // produces one String that has all names of images in the empty list
    public String images() {
        return "";
    }
}
 
class ConsLoItem implements ILoItem {
    IItem first;
    ILoItem rest;
    
    // the constructor
    ConsLoItem(IItem first, ILoItem rest) {
        this.first = first;
        this.rest = rest;
    }
    // count the total image size in list
    public int totalImageSize() {
        return this.first.countImage() + this.rest.totalImageSize();
    }
    
    // count the total number of letters in all text that
    // appears in the list of item
    public int textLength() {
        return this.first.textHelp() + this.rest.textLength();
    }
    
    // produces one String that has all names of images in the list of items
    public String images() {
        if (this.first.imagesHelp().length() > 1 && this.rest.images().length() > 1) {
            return this.first.imagesHelp() + ", " + this.rest.images();
        } 
        else {
            return this.first.imagesHelp() + this.rest.images();
        }
    }
    /*
     * fields:
     * this.first ... IItem
     * this.rest ... ILoItem
     * methods:
     * totalImageSize() ... int
     * textLength() ... int
     * images() ... String
     * methods for fields:
     * this.first.totalImageSize() ... int
     * this.rest.toatalImageSize() ... int
     * this.first.textLength() ... int
     * this.rest.textLength() ... int
     * this.first.images() ... String
     * this.rest.images() ... String
     */
}
interface IItem {
    // count the image size in an item
    int countImage();
    
    // count the total number of letters in all text that
    // appears in the item
    int textHelp();
    
    // produces one String that has all names of images in the Items
    String imagesHelp();
}


class Text implements IItem {
    String contents;
    
    // the constructor
    Text(String contents) {
        this.contents = contents;
    }
    
    // count the image size in Text
    public int countImage() {
        return 0;
    }
    // count the total number of letters in all text that
    // appears in the text
    public int textHelp() {
        return this.contents.length();
    }
    
    // produces one String that has all names of images in the Text
    public String imagesHelp() {
        return "";
    }
    /*
     * fields:
     * this.contents ... String
     * methods:
     * countImage() ... int
     * textHelp() ... int
     * imagesHelp() ... String
     */
}

class Image implements IItem {
    String fileName;
    int size;
    String fileType;
    
    // the constructor
    Image(String fileName, int size, String fileType) {
        this.fileName = fileName;
        this.size = size;
        this.fileType = fileType;
    }
    // count the image size in Image
    public int countImage() {
        return this.size;
    }
    // count the total number of letters in all text that
    // appears in the image
    public int textHelp() {
        return this.fileName.length() + this.fileType.length();
    }
    // produces one String that the name of the image
    public String imagesHelp() {
        return this.fileName + "." + this.fileType;
    }
    /*
     * fields:
     * this.fileName ... String
     * this.size ... int
     * this.fileType ... String
     * methods:
     * countImage() ... int
     * textHelp() ... int
     * imagesHelp() ... String
     */
}

class Link implements IItem {
    String name;
    WebPage page;
    
    // the constructor
    Link(String name, WebPage page) {
        this.name = name;
        this.page = page;
    }
    // count the image size in link
    public int countImage() {
        return this.page.totalImageSize();
    }
    // count the total number of letters in all text that
    // appears in the link
    public int textHelp() {
        return this.name.length() + this.page.textLength();
    }
    
    // produces one String that has all names of images in the link
    public String imagesHelp() {
        return this.page.images();
    }
    /*
     * fields:
     * this.name ... String
     * this.page ... WebPage
     * methods:
     * countImage() ... int
     * textHelp() ... int
     * imagesHelp() ... String
     */
}

// the contents of a web site that has at least one text, 
// two images, three pages, and four links:
// I made a webpage(finalweb) with a list of Items
// there are 5 Items in ILoItem, item1 is a text
// item 2 and 3 are images, item 4 is Link
// that has a Webpage with empty item list
// item 5 is link that has another Webpage with another 
// Item that has a new Webpage

class ExamplesWebPage {
    ILoItem mt = new MtLoItem();
    
    WebPage webbase = new WebPage("url0", "title0", this.mt);
    IItem item0 = new Link("0", this.webbase);
    ILoItem list0 = new ConsLoItem(this.item0, this.mt);
    WebPage webbase2 = new WebPage("url1", "title1", this.list0);
    IItem item1 = new Text("asdsfjkgj");
    IItem item2 = new Image("a", 50, "type1");
    IItem item3 = new Image("b", 100, "type2");
    IItem item4 = new Link("c", this.webbase);
    IItem item5 = new Link("d", this.webbase2);
    ILoItem list1 = new ConsLoItem(this.item1, this.mt);
    ILoItem list2 = new ConsLoItem(this.item2, this.list1);
    ILoItem list3 = new ConsLoItem(this.item3, this.list2);
    ILoItem list4 = new ConsLoItem(this.item4, this.list3);
    ILoItem list5 = new ConsLoItem(this.item5, this.list4);
    // webPage that has at least one text, 
    // two images, three pages, and four link
    WebPage finalweb = new WebPage("urlx", "titlex", this.list5);
    
    // HtDP
    IItem itemHtdp = new Text("How to Design Programs");
    IItem itemHtdp1 = new Image("htdp", 4300, "tiff");
    ILoItem listHtdp = new ConsLoItem(this.itemHtdp1, this.mt);
    ILoItem listHtdp1 = new ConsLoItem(this.itemHtdp, this.listHtdp);
    WebPage htDP = new WebPage("htdp.org", "HtDP", this.listHtdp1);
    // OOD
    IItem itemOOD = new Text("Stay classy, Java");
    IItem itemOOD1 = new Link("Back to the Future", this.htDP);
    ILoItem listOOD = new ConsLoItem(this.itemOOD1, this.mt);
    ILoItem listOOD1 = new ConsLoItem(this.itemOOD, this.listOOD);
    WebPage ood = new WebPage("ccs.neu.edu/OOD", "OOD", this.listOOD1);
    // Fundies II
    IItem itemfundi = new Text("Home sweet home");
    IItem itemfundi1 = new Image("wvh-lab", 400, "png");
    IItem itemfundi2 = new Text("The staff");
    IItem itemfundi3 = new Image("profs", 240, "jpeg");
    IItem itemfundi4 = new Link("A Look Back", this.htDP);
    IItem itemfundi5 = new Link("A Look Ahead", this.ood);
    ILoItem listfundi = new ConsLoItem(this.itemfundi5, mt);
    ILoItem listfundi1 = new ConsLoItem(this.itemfundi4, listfundi);
    ILoItem listfundi2 = new ConsLoItem(this.itemfundi3, listfundi1);
    ILoItem listfundi3 = new ConsLoItem(this.itemfundi2, listfundi2);
    ILoItem listfundi4 = new ConsLoItem(this.itemfundi1, listfundi3);
    ILoItem listfundi5 = new ConsLoItem(this.itemfundi, listfundi4);   
    WebPage fundiesWP = new WebPage("ccs.neu.edu/Fundies2", "Fundies II", this.listfundi5);
    
    boolean testWP(Tester t) {
        return t.checkExpect(this.fundiesWP.totalImageSize(), 9240);
    }
    boolean testTextlength(Tester t) {
        return t.checkExpect(this.htDP.textLength(), 34) &&
                t.checkExpect(this.fundiesWP.textLength(), 182);
    }
    boolean testImages(Tester t) {
        return t.checkExpect(this.fundiesWP.images(), "wvh-lab.png, profs.jpeg, "
                    + "htdp.tiff, htdp.tiff");
    }
    boolean testtextHelp(Tester t) {
        return t.checkExpect(this.itemfundi4.textHelp(), 45);
    }
    
    // the htdp.tiff appear twice because the first "htdp.tiff" appeared in fundiesII's 
    // A Look Back webLink,
    // and the second "htdp.tiff" appeared in ODD's Back to the future webLink
    // and I used a recursive function in the Link Class
}