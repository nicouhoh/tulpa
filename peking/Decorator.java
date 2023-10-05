import processing.core.PGraphics;

public abstract class Decorator extends Organelle {

    Organelle organelle;

    public abstract void update(PGraphics g, Conductor c, float parentX, float parentY, float parentW, float parentH);

    public abstract void draw(PGraphics g, float x, float y);

    public abstract void shift(float parentX, float parentY, float parentW, float parentH);

}