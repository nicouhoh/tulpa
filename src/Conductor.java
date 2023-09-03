import processing.core.PGraphics;
import processing.event.MouseEvent;

public class Conductor {

    tulpa t;
    PGraphics g;
    Library library;
    Visipalp visipalp;
    Apparition apparition;

    MouseInput mouseInput;

    public Conductor(tulpa t, PGraphics g){
        this.t = t;
        this.g = g;
    }

    public void setup(){
        library = new Library();
        library.stockShelves();
        this.visipalp = new Visipalp(g);
        ContactSheet contactSheet = new ContactSheet();
        Scroller contactSheetScrollthing = new Scroller(contactSheet);
        visipalp.addChild(contactSheetScrollthing);
        contactSheetScrollthing.addChild(contactSheet);
        contactSheet.materialize(library.clippings);
        contactSheet.arrangeThumbnails();
        apparition = new Apparition();
        mouseInput = new MouseInput();
        mouseInput.conductor = this;
    }

    public void update(){
        visipalp.performUpdate(g);
        apparition.draw(g);
    }

    public void passMouseInput(MouseEvent e){
        mouseInput.receiveMouseInput(e, visipalp);
    }

    public void selectClipping(Thumbnail thumbnail){
        library.select(thumbnail.clipping);
    }

    public void addSelect(Thumbnail thumbnail){
        library.addSelect(thumbnail.clipping);
    }

    public void toggleSelect(Thumbnail thumbnail){
        library.toggleSelect(thumbnail.clipping);
    }

    public void drawApparition(PGraphics g, float x, float y){
        apparition.draw(g);
    }

}