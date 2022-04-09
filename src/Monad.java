import processing.core.PGraphics;

public abstract class Monad {

    float x;
    float y;
    float w;
    float h;

    boolean onscreen;

    public abstract void draw(PGraphics g);
    public void mouseOver(){}


    public void update(PGraphics g, float latitude) {
        if (isOnscreen(latitude)) {
            draw(g);
            if (checkMouseOver(tulpa.SOLE.mouseX, tulpa.SOLE.mouseY, latitude)) {
                mouseOver();
            }
        }
    }

    public void setPos(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public void setSize(float newW, float newH) {
        w = newW;
        h = newH;
    }

    public boolean isOnscreen(float latitude) {
        if (y < latitude + tulpa.SOLE.height && y >= latitude - h * 1.5) {
            return true;
        } else {
            return false;
        }
    }


    public boolean checkMouseOver(float mouseX, float mouseY, float latitude) {
        if (mouseX >= x && mouseX <= x + w &&
                mouseY >= y - latitude
                && mouseY <= y + h - latitude) {
            return true;
        } else {
            return false;
        }
    }
}