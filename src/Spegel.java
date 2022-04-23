import processing.core.PGraphics;

import java.awt.*;

public class Spegel extends Monad implements Clickable{

    Clipping clipping;
    float displayW;
    float displayH;
    float airW;
    float airH;
    PGraphics g = new PGraphics();

    public Spegel(ContactSheet parent, Clipping clipping){
        this.parent = parent;
        this.clipping = clipping;
    }

    @Override
    public void draw(PGraphics g){
        g.image(clipping.img, x + airW, y + airH, displayW, displayH);
        drawSelection(g);
    }

    @Override
    public void setSize(float clipW, float clipH) {
        float wid = clipping.img.width;
        float hei = clipping.img.height;
        if (clipping.img.width >= clipping.img.height) {
            wid = clipW;
            hei = clipping.img.height / (clipping.img.width / clipW);
            airH = (clipH - hei) / 2;
        } else {
            hei = clipH;
            wid = clipping.img.width / (clipping.img.height / clipH);
            airW = (clipW - wid) / 2;
        }
        displayW = wid;
        w = clipW;
        displayH = hei;
        h = clipH;
    }

    public void drawSelection(PGraphics g){
        if (clipping.selected) {
            g.stroke(tulpa.SOLE.color(255));
            g.noFill();
            g.rect(x + airW, y + airH, displayW, displayH);
        }
    }

    @Override
    public boolean isOnscreen(float latitude) {
        if (y - latitude < parent.y + parent.h && y - latitude + h >= parent.y) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean pinPoint(float pinX, float pinY){
//        pinY -= latitude;
        if (pinX >= x + airW && pinX <= x + airW + displayW
                && pinY >= y + airH && pinY <= y + airH + displayH){
//            monadDebugInfo();
            return true;

        } else{
            return false;
        }
    }

    public void debugClipSize(){
        g.stroke(100);
        g.noFill();
        g.rect(x, y, w, h);
    }

    @Override
    public void clicked(Operator operator, float clickX, float clickY, Callosum c){
        monadDebugInfo();
        c.library.select(this.clipping);
    }
}
