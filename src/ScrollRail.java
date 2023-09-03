import processing.core.PGraphics;

public class ScrollRail extends Organelle {

    int color = 0xff1A1A1A;
    ScrollGrip grip;

    @Override
    public void draw(PGraphics g, float x, float y) {
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
        Scroller scroller = (Scroller)getParent();
    }
}
