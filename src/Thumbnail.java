import processing.core.PGraphics;
import processing.core.PImage;
import processing.core.PVector;
import processing.core.PFont;

public class Thumbnail extends Organelle implements Mousish, Draggish, Droppish {

    Clipping clipping;

    PVector offset = new PVector(0,0);
    PFont font;

    public Thumbnail(Clipping clipping){
        this.clipping = clipping;
        font = tulpa.SOLE.getSkrivBordFont();
        addMousish(this);
        addDraggish(this);
    }

    @Override
    public void draw(PGraphics g){
        draw(g, x, y, w, h);
    }

    public void draw(PGraphics g, float x, float y, float w, float h){
        if (clipping.isSelected) {
            drawSelect(g, x, y);
        }
        textThing(g, x, y, w, h);
        for (Dropzone d : dropZones){
            d.draw(g);
        }
    }

    public void textThing(PGraphics g, float x, float y, float w, float h){

        // TEXT
        if (clipping.img == null && clipping.hasText()){
            drawBackground(g, x, y, w, h, 255);
            drawText(g, x, y, w, h);
        }

        // IMAGE
        else if (clipping.img != null && !clipping.hasText()){
            drawThumbnail(g, x, y, w, h);
        }

        // IMAGE AND TEXT
        else if (clipping.img != null && clipping.hasText()){

            // caption
            if (clipping.passage.text.length() <= 300){
                // TODO caption bg
                Twombly scribe = new Twombly();
                drawThumbnail(g, x, y, w, h);
                scribe.setGravity("bottom");
                g.fill(233);
                g.textFont(font);
                g.textSize(18);
                scribe.text(g, clipping.passage.text, -1, x, y, w, h);
            }
            // long text with image
            else{
                g.tint(128);
                drawThumbnail(g, x, y, w, h);
                g.tint(255);
                drawText(g, x, y, w, h);
            }
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
    }

    public void drawText(PGraphics g, float textX, float textY, float textW, float textH){
        drawText(g, textX, textY, textW, textH, 10, 255);
    }

    public void drawText(PGraphics g, float textX, float textY, float textW, float textH, float margin, float alpha){
        g.fill(233, alpha);
        g.textSize(16);
        g.text(clipping.passage.text, textX + margin, textY + margin, textW - margin*2, textH - margin*2);
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
            if (clipping.hasText()) drawText(g, casperX, casperY, w, h, 10, 128);
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

    public TagBubble openTagBubble(){
        TagBubble bubble = new TagBubble(this);
        addChild(bubble);
        return bubble;
    }
}
