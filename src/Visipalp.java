import processing.core.PGraphics;
import processing.event.MouseEvent;

import java.util.ArrayList;

public class Visipalp { // TODO later we'll implement some Observer interfaces

    PGraphics g;
    TulpaHeartInterface heart;
    Controller instrument;

    Organelle sycamore;
    Organelle contactSheet;

    Mouse mouse;

    public Visipalp(PGraphics g, Controller instrument, TulpaHeartInterface heart){
        this.g = g;
        this.heart = heart;
        this.instrument = instrument;
        this.mouse = new Mouse();

        materialize();
    }

    public void materialize(){
        sycamore = new Nothing();
        contactSheet = new ContactSheet(mouse);
        contactSheet.addChildren(manifestClippings(heart.getLibrary().clippings));
        contactSheet = new Scroller(contactSheet);
        sycamore.addChild(contactSheet);
        sycamore.update(0, 0, tulpa.SOLE.width, tulpa.SOLE.height);
    }

    public void draw(){
        sycamore.performDraw(g);
//        mouse.debug(g,);
//        mouse.drawHeldItem(g);
    }

    public ArrayList<Organelle> manifestClippings(ArrayList<Clipping> clippings){
        ArrayList<Organelle> thumbs = new ArrayList<Organelle>();
        for (Clipping clip : clippings){
            thumbs.add(new Thumbnail(g, clip, mouse.claw));
        }
        return thumbs;
    }

    public void receiveMouseEvent(MouseEvent e){
//        MouseState mouseState = new MouseState(e);
//        mouse.palpate(mouseState, sycamore);
        mouse.receiveMouseySqueak(new Squeak(e, mouse), sycamore);
    }


}