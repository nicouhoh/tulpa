import processing.core.PGraphics;

public class Visipalp extends Organelle implements Drawish {

    PGraphics g;

    int bgColor = 49;

    public Visipalp(PGraphics g){
        this.g = g;
        shape = new VisipalpShape();
    }

    @Override
    public void update(PGraphics g, Conductor c, float parentX, float parentY, float parentW, float parentH){
        shift(parentX, parentY, parentW, parentH);
        draw(g, x, y);
        updateChildren(g, c);
    }

    @Override
    public void draw(PGraphics g, float drawX, float drawY){
        g.background(bgColor);
    }

    @Override
    public float getLatitude(){
        return 0;
    }
}