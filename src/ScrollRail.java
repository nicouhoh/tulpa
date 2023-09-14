import processing.core.PGraphics;

public class ScrollRail extends Organelle implements Drawish {

    int color = 0xff1A1A1A;
    Scroller scroller;

    public ScrollRail(Scroller scroller){
        this.scroller = scroller;
    }

    @Override
    public void shift(float parentX, float parentY, float parentW, float parentH) {
        x = getParent().x + getParent().w - scroller.scrollW;
        y = getParent().y;
        w = scroller.scrollW;
        h = getParent().h;
    }

    @Override
    public void draw(PGraphics g, float drawX, float drawY){
        g.noStroke();
        g.fill(color);
        g.rect(drawX, drawY, w, h);
    }
}
