import processing.core.PGraphics;

public class ScrollRail extends Organelle {

    int color = 0xff1A1A1A;
    Scroller scroller;
    ScrollGrip grip;

    float scrollLatitude = 0;
    float foot = 0;
    float gripY;
    float gripH;

    public ScrollRail(){
        grip = new ScrollGrip();
        addChild(grip);
    }

    @Override
    public void draw(PGraphics g) {
        g.noStroke();
        g.fill(color);
        g.rect(x, y, w, h);
    }

    @Override
    public void shift() {
        x = getParent().x + getParent().w - getParent().scrollW;
        y = getParent().y;
        w = getParent().scrollW;
        h = getParent().h;
        scrollLatitude = getParent().latitude;
        Scroller scroller = (Scroller)getParent();
        foot = scroller.foot;
    }
}
