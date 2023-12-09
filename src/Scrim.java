import processing.core.PGraphics;
public class Scrim extends Organelle{

    int color;
    int alpha;

    public Scrim(int color, int alpha){
        x = 0;
        y = 0;
        w = tulpa.SOLE.width;
        h = tulpa.SOLE.height;
        this.color = color;
        this.alpha = alpha;
    }

    @Override
    public void draw(PGraphics g){
        g.noStroke();
        g.fill(color, alpha);
        g.rect(0, 0, tulpa.SOLE.width, tulpa.SOLE.height);
    }
}
