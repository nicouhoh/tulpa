import processing.core.PApplet;
import processing.core.PGraphics;

public class Grip extends Monad{

    Scroller parent;
    int color;

    boolean grabbed;
    float grabY;

    public Grip(Scroller parent){
        this.parent = parent;
        parent.children.add(this);
        setPos(parent.x, parent.y);
        w = parent.parent.scrollerW;
        color = 0xff6C6C6C;
        grabbed = false;
    }

    @Override
    public void draw(PGraphics g){
        g.fill(this.color);
        g.rect(x, y, w, h);
    }

    @Override
    public void update(){
        setPos(parent.x, PApplet.constrain(y, 0, parent.h - h));
        setSize(parent.w, PApplet.constrain(parent.h / parent.parent.foot * parent.h, 0, parent.h));
    }

}
