import processing.core.PGraphics;

public class TagList extends Organelle {

    @Override
    public void resize(float parentX, float parentY, float parentW, float parentH){}

    @Override
    public void draw(PGraphics g){
        g.noFill();
        g.stroke(255, 128, 255);
        g.rect(x, y, w, h);
    }
}
