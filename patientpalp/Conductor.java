import processing.core.PGraphics;
public class Conductor {

    tulpa t;
    PGraphics g;
    Library library;
    Visipalp visipalp;

    public Conductor(tulpa parent, PGraphics g, Library lib, Visipalp vis){
        this.t = parent;
        this.g = g;
        this.library = lib;
        this.visipalp = vis;
    }

    public void setup(){
        library.stockShelves();
        visipalp.materialize(library.clippings);
    }

}