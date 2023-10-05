import processing.core.PGraphics;
import processing.core.PImage;
import processing.core.PVector;

public class Thumbnail extends Organelle implements Drawish {

    Clipping clipping;
    private float dropZonePercent = 20;

    PVector offset = new PVector(0, 0);

    public Thumbnail(PGraphics g, Clipping clipping){
        this.clipping = clipping;
        clickish = new ThumbnailClickish();
        draggish = new ThumbnailDraggish();
        droppish = new ThumbnailDroppish();
    }

    @Override
    public void update(PGraphics g, Conductor c, float parentX, float parentY, float parentW, float parentH) {
        shift(parentX, parentY, parentW, parentH);
        draw(g, x, y);
        if (clipping.isSelected) drawSelect(g, x, y);
        updateChildren(g, c);
    }

    @Override
    public void draw(PGraphics g, float drawX, float drawY){
        if (clipping.img != null)
            g.image(clipping.img, drawX, drawY, w, h);
    }

    public void drawSelect(PGraphics g, float drawX, float drawY){
        g.stroke(255);
        g.strokeWeight(2);
        g.noFill();
        g.rect(drawX - 1, drawY - 1, w + 2, h + 2);
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

    public void fitToHeight(float thumbH){
        PImage img = clipping.img;
        if (img == null){
            w = thumbH;
            h = thumbH;
        }else{
            h = thumbH;
            w = (h * img.width) / img.height;
        }
    }

}

//TODO: dragging thumbnails around. MEANING:
//TODO: - you have to be able to drag thumbnails.
//TODO: - things have to be able to receive dropped items.
//TODO: - I need to make dropzones that receive dropped items.
//TODO: - It has to be able to reach the Conductor to tell it to make changes to the library.
