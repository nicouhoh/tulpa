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
    }

    public void update(){
        visipalp.performUpdate();
    }

}