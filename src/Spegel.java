import processing.core.PGraphics;
import processing.event.KeyEvent;

public class Spegel extends Monad implements Clickable{

    ContactSheet cs;
    Clipping clipping;
    float displayW;
    float displayH;
    float airW;
    float airH;
    TagBubble tagBubble;
    float tagbH = 30;

    public Spegel(ContactSheet parent, Clipping clipping){
        this.parent = parent;
        parent.children.add(this);
        this.clipping = clipping;
        this.clipping.spegel = this;
    }

    @Override
    public void update(){
        if(tagBubble == null) return;
        tagBubble.setBounds(x + airW, y + airH + h - tagbH, w - airW * 2, tagbH);
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

    public void openTagBubble(){
        if (tagBubble == null) {
            tagBubble = new TagBubble(this, x + airW, y + airH + displayH - 30, w - airW * 2, 30);
            children.add(tagBubble);
        } else{
            tagBubble.enable();
        }
    }

    public Scrawler getScrawler(){
        for (Monad m : children){
            if(m instanceof Scrawler){
                return (Scrawler)m;
            }
        }
        return null;
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
    }

    @Override
    public void dragged(Operator operator, int mod, float dragX, float dragY, float lockedX, float lockedY, Callosum c){
        if(c.field.casper == null) c.field.setCasper(this);
        c.field.setCasperPos(lockedX + airW, lockedY + airH);
    }

    @Override
    public void dropped(Operator operator, int mod, float releaseX, float releaseY, Callosum c){
        c.field.setBetweenClips(null);
    }

//    @Override
//    public void hoveredWithGift(Operator operator, int mod, float hoverX, float hoverY, Clickable gift, float lockedX, float lockedY, Callosum c){
        // temporary fix. if we're close enough to the edge, just reroute to Field.
//        float hoverPillow = 5;
//        if (hoverX < x + airW + hoverPillow || hoverX > x + w - airW - hoverPillow){
//           c.field.hoveredWithGift(operator, mod, hoverX, hoverY, gift, lockedX, lockedY, c);
//        }
//    }

}
