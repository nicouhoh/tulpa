import processing.core.PGraphics;
public class Scrim extends Organelle{

    int color;
    int alpha;

    public Scrim(int color, int alpha){
        this.color = color;
        this.alpha = alpha;
    }

    @Override
    public void draw(PGraphics g){
        g.noStroke();
        g.fill(color, alpha);
        g.rect(x, y, w, h);
    }
}
