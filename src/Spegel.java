import processing.core.PGraphics;
import processing.event.KeyEvent;

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
        this.clipping.spegel = this;
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
    public boolean pinPoint(float pinX, float pinY, float latitude){
        pinY += latitude;
        //            monadDebugInfo();
        return pinX >= x + airW && pinX <= x + airW + displayW
                && pinY >= y + airH && pinY <= y + airH + displayH;
    }

    public void debugClipSize(){
        g.stroke(100);
        g.noFill();
        g.rect(x, y, w, h);
    }

    @Override
    public void clicked(Operator operator, int mod, float clickX, float clickY, Callosum c){
        monadDebugInfo();
        System.out.println(mod);
        if (mod == 0) {
            c.library.select(this.clipping);
        } else if(mod == KeyEvent.CTRL){
            c.library.toggleSelect(this.clipping);
        } else if(mod == KeyEvent.SHIFT){
           // TODO select range ( & if some but not all are selected, select all; if all are selected, deselect)
        }
    }
}
