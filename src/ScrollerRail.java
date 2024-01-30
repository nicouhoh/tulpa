import processing.core.PGraphics;

public class ScrollerRail extends Organelle implements Mousish {

    int color = 0xff1A1A1A;
    Scroller scroller;

    public ScrollerRail(Scroller scroller){
        this.scroller = scroller;
        addMousish(this);
    }

    @Override
    public void resize(float parentX, float parentY, float parentW, float parentH){
        setPos(parentX + parentW - scroller.scrollW, parentY);
        setSize(scroller.scrollW, parentH);
        resizeChildren();
    }

    @Override
    public void draw(PGraphics g){
        g.noStroke();
        g.fill(color);
        g.rect(x, y, w, h);
    }

    @Override
    public void mouseDown(Controller controller, Mouse mouse, int mod) {
    }

    @Override
    public void buttonPress(Controller controller, int mod){}
}
