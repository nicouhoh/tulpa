import processing.core.PGraphics;
import processing.core.PImage;
import processing.core.PVector;

public class Thumbnail extends Organelle {

    Clipping clipping;
    private float dropZonePercent = 20;

    float thumbW, thumbH;
    PVector offset = new PVector(0, 0);

    public Thumbnail(PGraphics g, Clipping clipping){
        this.clipping = clipping;
        this.drawBehavior = new DrawThumbnail(g, this);
        this.shape = new Shaper();
    }

//    public void drawSelect(){
//        g.stroke(255);
//        g.strokeWeight(2);
//        g.noFill();
//        g.rect(thumbX, thumbY, thumbW, thumbH);
//    }

    public void setPos(float x, float y){
        this.x = x;
        this.y = y;
    }

    public float getThumbX(){
        float tw = getThumbSize().x;
        float offset = (w / 2) - (tw / 2);
        return x + offset;
    }

    public float getThumbY(){
        float th = getThumbSize().y;
        float offset = (h / 2) - (th / 2);
        return y + offset;
    }

    public void setSize(float newW, float newH){
        w = newW;
        h = newH;
    }

//    public void setSize(float newW, float newH){
//        w = newW;
//        h = newH;
//        PImage img = clipping.img;
//        if (img == null){
//            thumbW = w; thumbH = h;
//            return;
//        }
//
//        if (img.width >= img.height){
//            thumbW = w;
//            thumbH = (w * img.height) / img.width;
//            offset = new PVector(0, (h - thumbH) / 2);
//        } else {
//            thumbH = h;
//            thumbW = (h * img.width) / img.height;
//            offset = new PVector((w - thumbW) / 2, 0);
//        }
//    }

    public PVector getThumbSize(){
        PImage img = clipping.img;
        if (img == null)
            return new PVector(w, h);
        if (img.width >= img.height){
            float tw = w;
            float th = (w * img.height) / img.width;
            return new PVector(tw, th);
        }
        else {
            float th = h;
            float tw = (h * img.width) / img.height;
            return new PVector(tw, th);
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