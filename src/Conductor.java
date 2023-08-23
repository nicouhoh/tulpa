import processing.core.PGraphics;
import processing.event.MouseEvent;

public class Conductor {

    tulpa t;
    PGraphics g;
    Library library;
    Visipalp visipalp;

    MouseInput mouseInput;

    public Conductor(tulpa t, PGraphics g){
        this.t = t;
        this.g = g;
    }

    public void setup(){
        library = new Library();
        library.stockShelves();
        this.visipalp = new Visipalp(g);
        Scroller contactSheetScrollthing = new Scroller();
        visipalp.addChild(contactSheetScrollthing);
        ContactSheet contactSheet = new ContactSheet();
        contactSheetScrollthing.addChild(contactSheet);
        contactSheet.materialize(library.clippings);
        contactSheet.arrangeThumbnails();
        mouseInput = new MouseInput();
    }

    public void update(){
        visipalp.performUpdate(g);
    }

    public void passMouseInput(MouseEvent e){
        mouseInput.receiveMouseStatus(e, visipalp);
        mouseInput.receiveClick(e);
    }
}