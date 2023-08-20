import processing.core.PGraphics;

public class Conductor {

    tulpa t;
    PGraphics g;
    Library library;
    Visipalp visipalp;

    public Conductor(tulpa t, PGraphics g){
        this.t = t;
        this.g = g;
    }

    public void setup(){
        library = new Library();
        library.stockShelves();
        this.visipalp = new Visipalp(g);
        visipalp.addChild(new ContactSheet(g));
        ContactSheet contactSheet = new ContactSheet(g);
        visipalp.addChild(contactSheet);
        contactSheet.materialize(library.clippings);
        contactSheet.arrangeThumbnails();
    }

    public void update(){
        visipalp.performUpdate(g);
    }

}