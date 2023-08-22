import processing.core.PGraphics;

public class ScrollGrip extends Organelle {

    int color = 0xff6C6C6C, activeColor = 225;

    @Override
    public void draw(PGraphics g) {
        g.noStroke();
        g.fill(color);
        g.rect(x, y, w, h);
    }

    @Override
    public void shift() {
        ScrollRail rail = (ScrollRail)getParent();
        x = rail.x;
        w = rail.w;
        y = rail.gripY;
        h = rail.gripH;
        //TODO update y + h based on Scroller's latitude and foot
    }
}
