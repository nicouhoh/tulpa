import processing.core.PApplet;
import processing.core.PGraphics;

public class ScrollerGrip extends Organelle {

    PGraphics g;
    Scroller scroller;

    int gripColor = 0xff6C6C6C, gripActiveColor = 225;

//    float foot;

    public ScrollerGrip(PGraphics g, Scroller parent){
        this.parent = parent;
        this.scroller = parent;
        this.g = g;
        x = parent.x;
        y = parent.y;
        w = parent.w;
    }

    public void update(){
        this.x = parent.x;
        this.w = parent.w;
//        foot = scroller.foot;
    }

    public void draw(){
        g.noStroke();
        g.fill(gripColor);
        g.rect(x, y, w, h);
    }

//    public void updateGrip(float latitude, float foot){
//        setGripH(foot);
//        setGripPos(latitude, foot);
//    }

    public void setGripH(float foot){
        this.h = PApplet.constrain(parent.h / foot * parent.h, 0, parent.h);
        System.out.println("Updated " + this + " height: " + h);
    }

    public void setGripPos(float scrollValue, float bottomOfScroll){
        this.y = PApplet.constrain(scrollValue / bottomOfScroll * parent.h, 0, parent.h - h);
    }
}
