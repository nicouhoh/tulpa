import processing.core.PGraphics;

public class ScrollRailDrawer implements DrawBehavior{

    @Override
    public void draw(PGraphics g, Organelle o, float x, float y) {
        ScrollRail r = (ScrollRail)o;
        g.noStroke();
        g.fill(r.color);
        g.rect(x, y, r.w, r.h);
    }
}
