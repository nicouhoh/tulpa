import processing.core.PApplet;
import processing.core.PGraphics;

public class ScrollerGrip extends Organelle {

    private int gripColor = 0xff6C6C6C, gripActiveColor = 225;

    public void update(PGraphics g){
        x = getParent().x;
        w = getParent().w;
        Scroller scroller = (Scroller)getParent();
        h = scroller.getGripH();
        y = scroller.getGripY();
        super.update(g);
    }

    @Override
    void draw(PGraphics g) {
        g.noStroke();
        g.fill(gripColor);
        g.rect(x, y, w, h);
    }
}
