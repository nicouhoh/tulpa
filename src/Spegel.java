import processing.core.PGraphics;

import java.awt.*;

public class Spegel extends Monad {

    Clipping clipping;
    ContactSheet parent;
    float displayW;
    float displayH;
    float airW;
    float airH;

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
        w = wid;
        displayH = hei;
        h = hei;
    }

    public void drawSelection(PGraphics g){
        if (clipping.selected) {
            g.stroke(tulpa.SOLE.color(255));
            g.noFill();
            g.rect(x + airW, y + airH, displayW, displayH);
        }
    }
}
