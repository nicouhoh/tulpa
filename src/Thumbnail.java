import processing.core.PGraphics;
import processing.core.PImage;
import processing.core.PVector;

public class Thumbnail extends Organelle implements Clickish, Draggish{

    Clipping clipping;

    PVector offset = new PVector(0,0);

    public Thumbnail(PGraphics g, Clipping clipping){
        this.clipping = clipping;
    }

    @Override
    public void update(float parentX, float parentY, float parentW, float parentH){}

    @Override
    public void draw(PGraphics g){
        if (clipping.img != null)
            g.image(clipping.img, x, y, w, h);
    }

    public void draw(PGraphics g, float x, float y, float w, float h){
        if (clipping.img != null)
            g.image(clipping.img, x, y, w, h);
        if (hot){
            drawSelect(g, x, y);
        }
    }

    public void drawSelect(PGraphics g, float drawX, float drawY){
        g.stroke(255);
        g.strokeWeight(2);
        g.noFill();
        g.rect(x - 1, y - 1, w + 2, h + 2);
    }

    public void setPos(float newX, float newY){

        x = newX + offset.x;
        y = newY + offset.y;
    }

    @Override
    public void setSize(float newW, float newH){
        PImage img = clipping.img;
        if (img == null){
            w = newW;
            h = newH;
        }
        else if (img.width >= img.height){
            w = newW;
            h = (newW * img.height) / img.width;
        }
        else {
            h = newH;
            w = (newH * img.width) / img.height;
        }
        offset = new PVector((newW - w) / 2, (newH - h) / 2);

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

    @Override
    public void click(){
        System.out.println(this);
    }

    @Override
    public void grab(){}

    @Override
    public void drag(float dragX, float dragY, float offsetX, float offsetY){}

    @Override
    public void drawCasper(PGraphics g, float dragX, float dragY, float offsetX, float offsetY) {
        draw(g, dragX - offsetX, dragY - offsetY, w, h);
    }


}
