import processing.core.PGraphics;
import processing.event.KeyEvent;

public class Spegel extends Monad implements Clickable{

    Clipping clipping;
    float displayW;
    float displayH;
    float airW;
    float airH;
    boolean casper;

    public Spegel(ContactSheet parent, Clipping clipping){
        this.parent = parent;
        this.parent.children.add(this);
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
        return y - latitude < parent.y + parent.h && y - latitude + h >= parent.y;
    }

    @Override
    public boolean pinPoint(float pinX, float pinY, float latitude){
        pinY += latitude;
        return pinX >= x + airW && pinX <= x + airW + displayW
                && pinY >= y + airH && pinY <= y + airH + displayH;
    }

    public void debugClipSize(PGraphics g){
        g.stroke(100);
        g.noFill();
        g.rect(x, y, w, h);
    }

    @Override
    public void clicked(Operator operator, int mod, float clickX, float clickY, Callosum c){
//        monadDebugInfo();
        System.out.println(mod);
        if (mod == 0) {
            c.library.select(this.clipping);
        } else if(mod == KeyEvent.CTRL || mod == KeyEvent.META){
            c.library.toggleSelect(this.clipping);
//        } else if(mod == KeyEvent.SHIFT){
//           // TODO select range ( & if some but not all are selected, select all; if all are selected, deselect)
        }
    }

    @Override
    public void grabbed(Operator operator, int mod, float grabX, float grabY, Callosum c){
        System.out.println("Spegel grabbed");
        float l = c.field.latitude;
        c.cockpit.setCasper(this, grabX - this.x - airW, grabY - this.y - airH + l);
    }

    @Override
    public void dragged(Operator operator, int mod, float dragX, float dragY, Callosum c){

    }

    @Override
    public void dropped(Operator operator, int mod, float releaseX, float releaseY, Callosum c){
    }

}
