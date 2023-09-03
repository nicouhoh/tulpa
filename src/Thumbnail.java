import processing.core.PGraphics;
import processing.core.PImage;
import processing.core.PVector;

public class Thumbnail extends Organelle implements Shape, DrawBehavior, Clickish, Draggish
{

    Clipping clipping;
    private float dropZonePercent = 20;

    float thumbW, thumbH;
    PVector offset = new PVector(0, 0);

    public Thumbnail(PGraphics g, Clipping clipping){
        this.clipping = clipping;
    }

    @Override
    public void shift(){}

    @Override
    public void draw(PGraphics g, float x, float y) {
        if (clipping.img != null)
            g.image(clipping.img, x, y, w, h);
        if (clipping.isSelected) drawSelect(g);
    }

    public void drawSelect(PGraphics g){
        g.stroke(255);
        g.strokeWeight(2);
        g.noFill();
        g.rect(x - 1, y - 1, w + 2, h + 2);
    }

    public void setPos(float x, float y){
        this.x = x + offset.x;
        this.y = y + offset.y;
    }

    public void setSize(float thumbW, float thumbH){
        PImage img = clipping.img;
        if (img == null){
            w = thumbW;
            h = thumbH;
        }
        else if (img.width >= img.height){
            w = thumbW;
            h = (thumbW * img.height) / img.width;
        }
        else {
            float th = thumbH;
            h = thumbH;
            w = (thumbH * img.width) / img.height;
        }
        offset = new PVector((thumbW - w) / 2, (thumbH - h) / 2);
    }

    @Override
    public void hot(){}

    public void active(){}

    @Override
    public void click(Conductor conductor, int mod) {
        switch(mod){
            case 2, 4 -> conductor.toggleSelect(this);
            default -> conductor.selectClipping(this);
        }
    }

    @Override
    public void drag(float x, float y) {

    }

    @Override
    public void release() {

    }
}