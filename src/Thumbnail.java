import java.sql.Array;
import java.util.ArrayList;
import processing.core.PGraphics;
import processing.core.PImage;
import processing.core.PVector;

public class Thumbnail extends Organelle implements Mousish, Draggish, Droppish {

    Clipping clipping;

    PVector offset = new PVector(0,0);
    ArrayList<DropZone> dropZones = new ArrayList<DropZone>();


    public Thumbnail(PGraphics g, Clipping clipping){
        this.clipping = clipping;
        addMousish(this);
        addDraggish(this);
    }

    @Override
    public void update(float parentX, float parentY, float parentW, float parentH){}

    @Override
    public void draw(PGraphics g){
        if (clipping.img != null)
            g.image(clipping.img, x, y, w, h);
        if (clipping.isSelected){
            drawSelect(g, x, y);
        }
        for (DropZone d : dropZones){
            d.debugDraw(g);
        }
    }

    public void draw(PGraphics g, float x, float y, float w, float h){
        if (clipping.img != null)
            g.image(clipping.img, x, y, w, h);
        if (clipping.isSelected){
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

    public void clearOffset(){
        offset = new PVector(0, 0);
    }

    @Override
    public void mouseDown(Controller controller, Mouse mouse, int mod) {
        switch (mod){
            case 2, 4 -> {
                if (!clipping.isSelected){
                    controller.addSelection(clipping);
                    mouse.setPreventUnclick(this);
                }
            }
            default -> controller.selectClipping(clipping);
        }
        System.out.println("clicked " + this);
    }

    @Override
    public void buttonPress(Controller controller, int mod){
        switch (mod){
            case 2, 4 -> {if (clipping.isSelected) controller.removeSelection(clipping);}
        }
    }

    @Override
    public void grab(Controller controller) {
    }

    @Override
    public void drag(Controller controller, float mouseX, float mouseY, float originX, float originY, float offsetX, float offsetY) {
        System.out.println("Dragging " + this);
    }

    @Override
    public void casper(PGraphics g, float casperX, float casperY, float casperW, float casperH){
        if (clipping.img != null){
            g.tint(255, 128);
            g.image(clipping.img, casperX, casperY, w, h);
            g.tint(255, 255);
        }
    }

    @Override
    public void release(Controller controller){
    }

    public void createDropZones(){
//        dropZones.add(new ThumbArrangeDropZone(this, x, y, w, h));
    }

    public void clearDropZones(){
        dropZones.clear();
    }
}
