import java.lang.reflect.Array;
import java.util.ArrayList;

import processing.core.PApplet;
import processing.core.PGraphics;


public class ContactSheet extends Monad{

    float clipSize;
    float pillow;
    float sPillow;
    int sheetZoom;
    float f; // This gets passed up to become Field.foot
    boolean sardine;

    public ContactSheet(Field parent){
       this.parent = parent;
       this.parent.children.add(this);
       sardine = false;
       pillow = 10;
       sPillow = 5;
       clipSize = 200;
       sheetZoom = 5;
       setPos(parent.x, parent.y);
       setSize(parent.w - parent.scrollWidth(), parent.h);
    }

    // we make our translation here in Contact Sheet so nothing else needs to worry about it.
    @Override
    public void cascadeDraw(PGraphics g, float latitude) {
        if(isOnscreen(latitude)) {
            draw(g);
            if (children == null) return;
            g.push();
            g.translate(0, -latitude);
//            for (Spegel s : children) {
            for (Monad m : children){
                m.cascadeDraw(g, latitude);
            }
            g.pop();
        }
    }

    @Override
    public boolean isOnscreen(float latitude) {
        if (y < parent.y + parent.h && y + h >= parent.h) {
            return true;
        } else {
            System.out.println("COCKPIT OFFSCREEN");
            return false;
        }
    }



    @Override
    public void update(){
        setPos(parent.x, parent.y);
        setSize(parent.w - parent.scrollWidth(), parent.h);
        setClipSize();
        if (sardine) packSardines();
        else arrangeSpegels();
    }

    public void setClipSize(){
        clipSize = PApplet.constrain((w - (pillow * (sheetZoom + 1))) / sheetZoom, 10, w);
    }

    public void newSpegel(Clipping clip){
        Spegel speg = new Spegel(this, clip);
        children.add(speg);
    }

    public void newSpegels(ArrayList<Clipping> clips){
        ArrayList<Spegel> s = new ArrayList<Spegel>();
        for (Clipping c : clips){
          s.add(new Spegel(this, c));
        }
        children.addAll(s);
    }

    public void shatterSpegel(Spegel s){
        children.remove(s);
    }

    public void shatterSpegel(ArrayList<Spegel> s){
        children.removeAll(s);
    }

    public void arrangeSpegels(){
        float fussX = pillow;
        float fussY = pillow;
        for (int i = 0; i < children.size(); i++){
            fussX = pillow + (i % sheetZoom) * (pillow + clipSize);
            fussY = pillow + (i / sheetZoom) * (pillow + clipSize);
            children.get(i).setSize(clipSize, clipSize);
            children.get(i).setPos(fussX, fussY);
        }
        setF(fussY + clipSize + pillow);
    }

    public void packSardines(){
        clipSize = PApplet.constrain(h / sheetZoom, 9, h);
        ArrayList<Spegel> row = new ArrayList<Spegel>();
        float rowWidth = sPillow;
        float x = sPillow;
        float y = sPillow;
        float endY = 0;

        for (Monad m : children){
            Spegel s = (Spegel)m;
            float clipW = s.clipping.img.width;
            float clipH = s.clipping.img.height;
            float newWidth = (clipW / clipH) * clipSize;
            float nextY = clipSize;
            if (rowWidth + newWidth > w + sPillow) {    // if it dont fit
                // resize the row to fit the window
                float ratio = (w - sPillow) / rowWidth;
                for (Spegel c : row){
                    c.setSize(c.displayW * ratio, c.displayH * ratio);
                    c.setPos(c.x * ratio, c.y);
                    nextY = c.displayH;
                }
                x = sPillow;
                y += nextY + sPillow;
                rowWidth = sPillow;
                row.clear();
            }
            rowWidth += newWidth;
            s.setSize(newWidth, clipSize);
            s.setPos(x, y);
            x += newWidth + sPillow;
            row.add(s);
            endY = nextY;
        }
        setF(y + endY + sPillow);
//        updateScroller();
    }

    public void toggleFishiness(){
        sardine = !sardine; // ahhhh, the dao.......
        // TODO keep your place when switching back and forth
    }

    public void setF(float floop){
        f = floop;
    }

    public float getF(){
        return f;
    }
}