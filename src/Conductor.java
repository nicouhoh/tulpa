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
        visipalp.updater.performUpdate(g, this, visipalp);
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

    public boolean isOnscreen(Organelle o){
        if (o.x + o.w < visipalp.x || o.x > visipalp.w
            || o.y + o.h + o.latitude < visipalp.y || o.y + o.latitude > visipalp.h){
            return false;
        } else
            return true;
    }

}