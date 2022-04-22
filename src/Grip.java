import processing.core.PApplet;
import processing.core.PGraphics;

public class Grip extends Monad{

    int color;

    boolean grabbed;
    float grabY;

    public Grip(Scroller parent){

        this.parent = parent;
        parent.children.add(this);
        setPos(parent.x, parent.y);
        w = parent.parent.scrollWidth();
        color = 0xff6C6C6C;
        grabbed = false;
    }

    // e.g. EXTRA WIDTH, where most Monads it == 0. generalize. make it a method getExtraWidth()

    @Override
    public boolean isOnscreen(float latitude) {
        if (y < latitude + parent.h && y + h >= parent.y) {
            return true;
        } else {
            System.out.println("GRIP OFFSCREEN");
            return false;
        }
    }

    @Override
    public void draw(PGraphics g){
        g.fill(this.color);
        g.rect(x, y, w, h);
    }

    public void updateGripSize(float lat, float foot){
        setSize(parent.w, PApplet.constrain(parent.h / foot * parent.h, 0, parent.h));
    }

    public void updateGripPos(float lat, float foot){
        setPos(parent.x, PApplet.constrain(lat / foot * parent.h, 0, parent.h - h));
    }


    // TODO: on drag, let's reverse the flow of latitude??


}
