import processing.core.PGraphics;
public class Conductor {

    tulpa t;
    PGraphics g;
    Library library;
    Visipalp visipalp;

    public Conductor(tulpa parent, PGraphics g){
        this.t = parent;
        this.g = g;
    }

    public void setup(){
        library = new Library();
        library.stockShelves();
        this.visipalp = new Visipalp(t);
        visipalp.setBounds(0, 0, t.w, t.h);
        ContactSheet contactSheet = new ContactSheet();
        visipalp.addChild(contactSheet);
        contactSheet.liquidBounds();
        visipalp.addChild(contactSheet);
        Scroller scroller = new Scroller(contactSheet);
        contactSheet.addChild(scroller);
        Materializer materializer = new Materializer();
        contactSheet.addChildren(materializer.materialize(library.clippings));
        contactSheet.arrangeThumbnails();
    }

    public void update(){
        visipalp.update(g);
    }

}