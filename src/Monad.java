import processing.core.PGraphics;

public abstract class Monad {

    float x;
    float y;
    float w;
    float h;

    boolean onscreen;

    public abstract void draw(PGraphics g);

    public void update(PGraphics g, float latitude){
        if (isOnscreen(latitude)){
            draw(g);
        }
    }

    public void setPos(float x, float y){
        this.x = x;
        this.y = y;
    }

    public void setSize(float newW, float newH){
        w = newW;
        h = newH;
    }

    public boolean isOnscreen(float latitude){
        if(y < latitude + tulpa.SOLE.height && y >= latitude - h * 1.5){
            return true;
        } else{
            return false;
        }
    }
}