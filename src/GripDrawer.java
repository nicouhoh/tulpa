import processing.core.PGraphics;

public class GripDrawer implements DrawBehavior{

    @Override
    public void draw(PGraphics g, Organelle o, float x, float y) {
        ScrollGrip sg = (ScrollGrip)o;
        g.noStroke();
        g.fill(sg.getColor());
        g.rect(sg.x, sg.y, sg.w, sg.h);
    }
}
