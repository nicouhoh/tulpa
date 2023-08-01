import processing.core.PGraphics;

public class ContactSheet extends Organelle {

    public ContactSheet(PGraphics g){
        this.shapeShifter = new ShapeOfGas();
        this.drawBehavior = new DrawDebug(g, this);
    }

}
