import processing.core.PGraphics;

public class Visipalp extends Organelle {

    PGraphics g;

    public Visipalp(PGraphics g){
        this.g = g;
        this.shapeShifter = new ShapeOfPalp();
        this.drawBehavior = new DrawBackground(g);
    }
}