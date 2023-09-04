import processing.core.PGraphics;

public class BackgroundDrawer implements DrawBehavior{
    @Override
    public void draw(PGraphics g, Organelle o, float x, float y) {
        Visipalp v = (Visipalp)o;
        g.background(v.bgColor);
    }
}
