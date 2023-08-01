import processing.core.PGraphics;

public class Visipalp extends Organelle {

    private tulpa parent;

    int bgColor = 49;

    public Visipalp(tulpa parent){
        this.parent = parent;
    }

    public void draw(PGraphics g){
        g.background(bgColor);
    }
}