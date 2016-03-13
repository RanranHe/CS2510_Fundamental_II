import tester.Tester;
// Assignment 5
// partner1-Qiang Leyi
// partner1-Drunkbug
// partner2-He Ranran
// partner2-heranran

// represent the WebPage
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

    // is this WebPage the same as that WebPage?
    boolean sameWebPage(WebPage that) {
        return this.url.equals(that.url) && this.title.equals(that.title)
                && this.items.sameLoItem(that.items);
    }
}

// represent a list of items
interface ILoItem {
    boolean sameLoItem(ILoItem that);
    boolean sameMtLoItem(MtLoItem that);
    boolean sameConsLoItem(ConsLoItem that);
}

// represent a empty list
class MtLoItem implements ILoItem {
    public boolean sameLoItem(ILoItem that) {
        return that.sameMtLoItem(this);
    }
    // is this MtLoItem the same as that MtLoItem?
    public boolean sameMtLoItem(MtLoItem that) {
        return true;
    }
    // is this MtLoItem the same as that ConsLoItem?
    public boolean sameConsLoItem(ConsLoItem that) {
        return false;
    }
}

// represent the list of items
class ConsLoItem implements ILoItem {
    IItem first;
    ILoItem rest;

    // the constructor
    ConsLoItem(IItem first, ILoItem rest) {
        this.first = first;
        this.rest = rest;
    }

    // is this list of items the same as that list of items?
    public boolean sameLoItem(ILoItem that) {
        return that.sameConsLoItem(this);
    }
    // is this ConsLoItem the same as that MtLoItem?
    public boolean sameMtLoItem(MtLoItem that) {
        return false;
    }
    // is this ConsLoItem the same as that ConsLoItem?
    public boolean sameConsLoItem(ConsLoItem that) {
        if (this.first.sameItem(that.first)) {
            return this.rest.sameLoItem(that.rest);
        } 
        else {
            return false;
        }
    }
}

// represent Item
// An item is one of:
// text
// image
// link
interface IItem {
    // is this item the same as that item?
    boolean sameItem(IItem that);

    // is this a text?
    boolean isText();

    // is this a image?
    boolean isImage();

    // is this a link?
    boolean isLink();
    
    // is this a Paragraph?
    boolean isPar();
    
    // is this a Header?
    boolean isHeader();

}

// represent texts
class Text extends AItem {
    String contents;

    // the constructor
    Text(String contents) {
        this.contents = contents;
    }

    // is this Text the same as that text?
    boolean equalText(Text that) {
        return this.contents == that.contents;
    }

    // is this a text?
    public boolean isText() {
        return true;
    }

    // is this Text the same as that?
    public boolean sameItem(IItem that) {
        if (that.isText()) {
            return this.equalText((Text) that);
        } 
        else {
            return false;
        }
    }

}

// represent paragraph
class Paragraph extends Text {
    String par;
    
    // the constructor
    Paragraph(String par) {
        super(par);
    }
    // is this a Paragraph?
    public boolean isPar() {
        return true;
    }
    // is this a text?
    public boolean isText() {
        return false;
    }
    // is this paragraph the same as that paragraph?
    boolean equalPar(Paragraph that) {
        return this.par == that.par;
    }
    // is this Paragraph the same as that?
    public boolean sameItem(IItem that) {
        if (that.isPar()) {
            return this.equalPar((Paragraph) that);
        } 
        else {
            return false;
        }
    }
}

// represent header
class Header extends Text {
    String head;
    
    // the constructor
    Header(String head) {
        super(head);
    }
    
    // is this a Header?
    public boolean isHeader() {
        return true;
    }
    
    // is this Header the same as that Header?
    boolean equalHead(Header that) {
        return this.head == that.head;
    }
    
    // is this a text?
    public boolean isText() {
        return false;
    }
    // is this Header the same as that?
    public boolean sameItem(IItem that) {
        if (that.isHeader()) {
            return this.equalHead((Header) that);
        } 
        else {
            return false;
        }
    }
}

// represent images
class Image extends AItem {
    String fileName;
    int size;
    String fileType;

    // the constructor
    Image(String fileName, int size, String fileType) {
        this.fileName = fileName;
        this.size = size;
        this.fileType = fileType;
    }

    // is this image the same as that image?
    boolean sameImage(Image that) {
        return this.fileName.equals(that.fileName) && this.size == that.size
                && this.fileType.equals(that.fileType);
    }

    // is this a image?
    public boolean isImage() {
        return true;
    }

    // is this image the same as that?
    public boolean sameItem(IItem that) {
        if (that.isImage()) {
            return this.sameImage((Image) that);
        } 
        else {
            return false;
        }
    }

}

// represent links
class Link extends AItem {
    String name;
    WebPage page;

    // the constructor
    Link(String name, WebPage page) {
        this.name = name;
        this.page = page;
    }

    // is this link the same as that link
    boolean sameLink(Link that) {
        return this.name.equals(that.name) && this.page.sameWebPage(that.page);
    }

    // is this a Link?
    public boolean isLink() {
        return true;
    }

    // is this Link the same as that?
    public boolean sameItem(IItem that) {
        if (that.isLink()) {
            return this.sameLink((Link) that);
        } 
        else {
            return false;
        }
    }

}

abstract class AItem implements IItem {
    // is this a text?
    public boolean isText() {
        return false;
    }

    // is this a image?
    public boolean isImage() {
        return false;
    }

    // is this a Link?
    public boolean isLink() {
        return false;
    }
    // is this a Paragraph?
    public boolean isPar() {
        return false;
    }
    
    // is this a Header?
    public boolean isHeader() {
        return false;
    }
}


// to represent examples of Web Pages
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
    IItem itemfundi6 = new Header("I hate testing");
    IItem itemfundi7 = new Text("I hate testing");
    IItem itemfundi8 = new Paragraph("No joking");
    ILoItem listfundi = new ConsLoItem(this.itemfundi5, mt);
    ILoItem listfundi1 = new ConsLoItem(this.itemfundi4, listfundi);
    ILoItem listfundi2 = new ConsLoItem(this.itemfundi3, listfundi1);
    ILoItem listfundi3 = new ConsLoItem(this.itemfundi2, listfundi2);
    ILoItem listfundi4 = new ConsLoItem(this.itemfundi1, listfundi3);
    ILoItem listfundi5 = new ConsLoItem(this.itemfundi, listfundi4);   
    WebPage fundiesWP = new WebPage("ccs.neu.edu/Fundies2", "Fundies II", this.listfundi5);
    
 // test for the sameWebPage method
    boolean testSameWebPage(Tester t) {
        return t.checkExpect(this.fundiesWP.sameWebPage(this.ood), false) &&
                t.checkExpect(this.ood.sameWebPage(this.ood), true);
    }
    
    // test for the sameLoItem method
    boolean testSameLoItem(Tester t) {
        return t.checkExpect(this.listOOD.sameLoItem(this.listOOD1), false) &&
                t.checkExpect(this.listOOD.sameLoItem(this.listOOD), true) &&
                t.checkExpect(this.list4.sameLoItem(this.list5), false) &&
                t.checkExpect(this.list5.sameLoItem(this.list5), true) &&
                t.checkExpect(this.mt.sameLoItem(this.mt), true) &&
                t.checkExpect(this.list5.sameLoItem(this.mt), false) &&
                t.checkExpect(this.mt.sameLoItem(this.list5), false);
    }
    
    // test for the sameItem method
    boolean testSameItem(Tester t) {
        return t.checkExpect(this.itemfundi.sameItem(itemfundi1), false) &&
                t.checkExpect(this.itemfundi.sameItem(itemfundi2), false) &&
                t.checkExpect(this.itemfundi.sameItem(itemfundi), true) &&
                t.checkExpect(this.itemfundi1.sameItem(itemfundi1), true) &&
                t.checkExpect(this.itemfundi1.sameItem(itemfundi5), false) &&
                t.checkExpect(this.itemfundi5.sameItem(itemfundi1), false) &&
                t.checkExpect(this.itemfundi1.sameItem(itemfundi2), false) &&
                t.checkExpect(this.itemfundi1.sameItem(itemfundi3), false) &&
                t.checkExpect(this.itemfundi4.sameItem(itemfundi5), false) &&
                t.checkExpect(this.itemfundi4.sameItem(itemfundi4), true) &&
                t.checkExpect(this.itemfundi4.sameItem(itemfundi1), false) &&
                t.checkExpect(this.itemfundi6.sameItem(itemfundi7), false) &&
                t.checkExpect(this.itemfundi7.sameItem(itemfundi6), false) &&
                t.checkExpect(this.itemfundi7.sameItem(itemfundi8), false) &&
                t.checkExpect(this.itemfundi6.sameItem(itemfundi6), true) &&
                t.checkExpect(this.itemfundi8.sameItem(itemfundi8), true);
    }
    
    // test for the isXXX method
    boolean testIsX(Tester t) {
        return t.checkExpect(this.itemfundi6.isPar(), false) &&
                t.checkExpect(this.itemfundi7.isPar(), false) &&
                t.checkExpect(this.itemfundi8.isPar(), true) &&
                t.checkExpect(this.itemfundi6.isHeader(), true) &&
                t.checkExpect(this.itemfundi7.isHeader(), false) &&
                t.checkExpect(this.itemfundi8.isHeader(), false);
                
    }
}
