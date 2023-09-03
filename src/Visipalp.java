import processing.core.PGraphics;

public class Visipalp extends Organelle implements Shape, DrawBehavior {

    PGraphics g;

    int bgColor = 49;

    public Visipalp(PGraphics g){
        this.g = g;
        performUpdate(g);
    }

    @Override
    public void shift(){
        x = 0;
        y = 0;
        w = tulpa.SOLE.width;
        h = tulpa.SOLE.height;
    }

    @Override
    public void draw(PGraphics g, float x, float y){
        g.background(bgColor);
    }


}