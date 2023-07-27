import processing.core.PGraphics;
import processing.core.PImage;
import processing.core.PVector;

public class Thumbnail {

    PGraphics g;
    int id;
    Clipping clipping;
    float x, y, w, h;
    float thumbX, thumbY, thumbW, thumbH;
    PVector offset = new PVector(0, 0);
    float dropZonePercent = 20;

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
        if (clipping.img != null) g.image(clipping.img, thumbX, thumbY, thumbW, thumbH);
        // else thumbnailText()
    }

    public void drawSelect(){
        g.stroke(255);
        g.strokeWeight(2);
        g.noFill();
        g.rect(thumbX, thumbY, thumbW, thumbH);
    }

    public void setPos(float x, float y){
        this.x = x;
        this.y = y;
        this.thumbX = x + offset.x;
        this.thumbY = y + offset.y;
    }

    public void setSize(float newW, float newH){
        w = newW;
        h = newH;
        PImage img = clipping.img;
        if (img == null){
            thumbW = w; thumbH = h;
            return;
        }

        if (img.width >= img.height){
            thumbW = w;
            thumbH = (w * img.height) / img.width;
            offset = new PVector(0, (h - thumbH) / 2);
        } else {
            thumbH = h;
            thumbW = (h * img.width) / img.height;
            offset = new PVector((w - thumbW) / 2, 0);
        }
    }

    public void setSize(float newW, float newH, float newThumbW, float newThumbH){
        w = newW;
        h = newH;
        thumbW = newThumbW;
        thumbH = newThumbH;
        offset = new PVector(0,0);
    }

    public void resizeByHeight(float height){
        if (clipping.img == null){
            h = height;
            w = height;
            return;
        }
        h = height;
        thumbH = h;
        thumbW = (height * clipping.img.width) / clipping.img.height;
        w = thumbW;
    }

}