import processing.core.PGraphics;
import processing.core.PImage;
import processing.core.PVector;

public class Thumbnail extends Organelle implements Mousish, Draggish, Droppish {

    Clipping clipping;

    PVector offset = new PVector(0,0);

    public Thumbnail(PGraphics g, Clipping clipping){
        this.clipping = clipping;
        addMousish(this);
        addDraggish(this);
    }

    @Override
    public void draw(PGraphics g){
        draw(g, x, y, w, h);
    }

    public void draw(PGraphics g, float x, float y, float w, float h){
        drawThumbnail(g, x, y, w, h);
        if (clipping.img == null) drawBackground(g, x, y, w, h, 255);
        if (clipping.hasText()) drawText(g, x, y, w, h);
        if (clipping.isSelected) drawSelect(g, x, y);
        for (Dropzone d : dropZones){
            d.draw(g);
        }
    }

    public void drawThumbnail(PGraphics g, float thumbX, float thumbY, float thumbW, float thumbH, float alpha){
        g.tint(255, alpha);
        drawThumbnail(g, thumbX, thumbY, thumbW, thumbH);
        g.tint(255, 255);
    };

    public void drawThumbnail(PGraphics g, float x, float y, float w, float h){
        if (clipping.img != null)
            g.image(clipping.img, x, y, w, h);
    };

    public void drawText(PGraphics g, float textX, float textY, float textW, float textH){
        drawText(g, textX, textY, textW, textH, 255);
    }

    public void drawText(PGraphics g, float textX, float textY, float textW, float textH, float alpha){
        g.fill(233, alpha);
        g.textSize(16);
        g.text(clipping.passage.text, textX + 10, textY + 10, textW - 20, textH - 20);
    }

    public void drawBackground(PGraphics g, float bgX, float bgY, float bgW, float bgH, float alpha){
        g.fill(49, alpha);
        g.noStroke();
        g.rect(bgX, bgY, bgW, bgH);
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
    }

    @Override
    public void mirage(PGraphics g, float casperX, float casperY, float casperW, float casperH){
            g.tint(255, 64);
            drawThumbnail(g, casperX, casperY, w, h);
            g.tint(255, 255);
            if (clipping.img == null) drawBackground(g, casperX, casperY, w, h, 64);
            if (clipping.hasText()) drawText(g, casperX, casperY, w, h, 128);
    }

    @Override
    public void release(Controller controller){
    }

    public void createDropZones(){
        clearDropZones();
        dropZones.add(new ThumbnailDropzone(this, this, 20));
    }

    public void clearDropZones(){
        dropZones.clear();
    }
}
