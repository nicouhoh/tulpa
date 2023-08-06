import processing.core.PGraphics;

public class Visipalp extends Organelle {

    PGraphics g;

    int bgColor = 49;

    public Visipalp(PGraphics g){
        this.g = g;
        this.shape = new ShapeOfPalp();
        this.drawBehavior = new DrawBackground(g);
        performUpdate();
    }
}