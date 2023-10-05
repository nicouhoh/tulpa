import processing.core.PGraphics;
import processing.event.MouseEvent;

public class Conductor {

    tulpa t;
    PGraphics g;
    Library library;
    Organelle visipalp;
    Apparition apparition;

    MouseInput mouseInput;

    public Conductor(tulpa t, PGraphics g){
        this.t = t;
        this.g = g;
    }

    public void bigBang(){
        library = new Library();
        library.stockShelves();
        this.visipalp = new Visipalp(g);
        ContactSheet contactSheet = new ContactSheet();
        Organelle scroller = new Scroller(contactSheet);
        visipalp.addChild(scroller);
        apparition = new Apparition();
        mouseInput = new MouseInput();
        mouseInput.conductor = this;
        contactSheet.materialize(library.clippings);
        contactSheet.arrangeThumbnails();
    }

    public void update(){
        visipalp.update(g, this, 0, 0, tulpa.SOLE.width, tulpa.SOLE.height);
        apparition.draw(g);
    }

    public void passMouseInput(MouseEvent e){
        mouseInput.receiveAction(e, visipalp);
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

//    public boolean isOnscreen(Organelle o){
//        if (o.x + o.w < visipalp.x || o.x > visipalp.w
//            || o.y + o.h + o.latitude < visipalp.y || o.y + o.latitude > visipalp.h){
//            return false;
//        } else
//            return true;
//    }
}