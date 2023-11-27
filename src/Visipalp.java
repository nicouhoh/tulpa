import processing.core.PApplet;
import processing.core.PGraphics;
import processing.event.MouseEvent;
import processing.event.KeyEvent;

import java.util.ArrayList;

public class Visipalp {

    PGraphics g;
    TulpaHeart heart;
    Controller instrument;

    Organelle sycamore;
    ContactSheet contactSheet;
    Scroller scroller;

    public Visipalp(PGraphics g, Controller instrument, TulpaHeart heart){
        this.g = g;
        this.heart = heart;
        this.instrument = instrument;

        materialize();
    }

    public void materialize(){
        sycamore = new Nothing();
        contactSheet = new ContactSheet();
        contactSheet.addChildren(manifestClippings(heart.getLibrary().clippings));
        scroller = new Scroller(contactSheet);
        sycamore.addChild(scroller);
        update();
    }

    public void draw(){
        sycamore.performDraw(g);
    }

    public void update() {
        sycamore.update(0, 0, tulpa.SOLE.width, tulpa.SOLE.height);
    }

    public ArrayList<Organelle> manifestClippings(ArrayList<Clipping> clippings){
        ArrayList<Organelle> thumbs = new ArrayList<Organelle>();
        for (Clipping clip : clippings){
            thumbs.add(new Thumbnail(g, clip));
        }
        return thumbs;
    }

    public void refreshContactSheet(){
        contactSheet.children = manifestClippings(heart.getLibrary().clippings);
        update();
    }

    public Thumbnail verticalStep(Thumbnail thumbnail, int direction){
        int thumbIndex = contactSheet.getThumbnails().indexOf(thumbnail);
        while (thumbIndex + direction >= 0 && thumbIndex + direction < contactSheet.getThumbnails().size()) {
            thumbIndex = PApplet.constrain(thumbIndex + direction, 0, contactSheet.getThumbnails().size());
            Thumbnail t2 = contactSheet.getThumbnails().get(thumbIndex);
            float centerX = thumbnail.x + thumbnail.w / 2;
            if (centerX > t2.x && centerX < t2.x + t2.w) return t2;
        }
        return thumbnail;
    }

}