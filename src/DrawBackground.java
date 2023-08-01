import processing.core.PGraphics;

public class DrawBackground implements DrawBehavior{

    PGraphics g;
    int bgColor = 49;

    public DrawBackground(PGraphics g){
        this.g = g;
    }

    @Override
    public void draw() {
        g.background(bgColor);
    }
}
