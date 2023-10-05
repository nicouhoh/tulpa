import processing.core.PGraphics;

public class ScrollerRail extends Organelle implements Clickish{

    int color = 0xff1A1A1A;
    Scroller scroller;

    public ScrollerRail(Scroller scroller){
        this.scroller = scroller;
    }

    @Override
    public void update(float parentX, float parentY, float parentW, float parentH){
        setPos(parentX + parentW - scroller.scrollW, parentY);
        setSize(scroller.scrollW, parentH);
        updateChildren();
    }

    @Override
    public void draw(PGraphics g){
        g.noStroke();
        g.fill(color);
        g.rect(x, y, w, h);
        drawChildren(g);
    }

    @Override
    public void click(){
    }
}
