import processing.core.PGraphics;
import processing.core.PVector;

public class Thumbnail {

    PGraphics g;
    int id;
    Clipping clipping;
    float x, y, w, h;
    PVector offset = new PVector(0, 0);

    public Thumbnail(PGraphics g, int id, Clipping clipping, float x, float y, float w, float h){
        this.g = g;
        this.id = id;
        this.clipping = clipping;
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
    }

    public void draw(){
        if (clipping.img != null) g.image(clipping.img, x, y, w, h);
        // else thumbnailText()
    }

    public void drawSelect(){
        g.stroke(255);
        g.strokeWeight(2);
        g.noFill();
        g.rect(x, y, w, h);
    }

    public void setPos(float x, float y){
        this.x = x;
        this.y = y;
    }

    public void setSize(float width, float height){
        w = width;
        h = height;
    }

    public void setSize(float clipSize){
        if (clipping.img == null) return;

        if (clipping.img.width >= clipping.img.height){
            w = clipSize;
            h = (clipSize * clipping.img.height) / clipping.img.width;
            offset = new PVector(0, (clipSize - h) / 2);
        } else {
            h = clipSize;
            w = (clipSize * clipping.img.width) / clipping.img.height;
            offset = new PVector((clipSize - w) / 2, 0);
        }
    }

    public void resizeByHeight(float height){
        if (clipping.img == null){
            h = height;
            w = height;
            return;
        }
        h = height;
        w = (height * clipping.img.width) / clipping.img.height;
    }
}