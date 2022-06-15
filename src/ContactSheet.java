import java.sql.Array;
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

    ArrayList<Monad> filter = new ArrayList<Monad>();

    public ContactSheet(Field parent){
       this.parent = parent;
       this.parent.children.add(this);
       sardine = false;
       pillow = 10;
       sPillow = 2;
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
            ArrayList<Monad> toDraw = getFilteredMonads();

            g.push();
            g.translate(0, -latitude);
//            for (Monad m : children){
            for (Monad dSpegel : toDraw) {
                dSpegel.cascadeDraw(g, latitude);
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
        setBounds(parent.x, parent.y, parent.w - parent.scrollWidth(), parent.h);
        setClipSize();
        if (sardine) packSardines();
        else arrangeSpegels();
    }

    public void setClipSize(){
        clipSize = PApplet.constrain((w - (pillow * (sheetZoom + 1))) / sheetZoom, 10, w);
    }

    public void newSpegel(Clipping clip){
        Spegel speg = new Spegel(this, clip);
//        children.add(speg);
    }

    public void newSpegels(ArrayList<Clipping> clips){
        ArrayList<Spegel> s = new ArrayList<Spegel>();
        for (Clipping c : clips){
          s.add(new Spegel(this, c));
        }
//        children.addAll(s);
    }

    public void shatterSpegel(Spegel s){
        children.remove(s);
    }

    public void shatterSpegel(ArrayList<Spegel> s){
        children.removeAll(s);
    }

    public void filterSpegels(ArrayList<Clipping> c){
        clearFilter();
        for (Clipping clip : c){
            filter.add((Monad)clip.spegel);
        }
    }

    public void clearFilter(){
        filter.clear();
    }

    public void arrangeSpegels(){
        ArrayList<Monad> toArrange = getFilteredMonads();
        float fussX = x + pillow;
        float fussY = x + pillow;
        for (int i = 0; i < toArrange.size(); i++){
            fussX = x + pillow + (i % sheetZoom) * (pillow + clipSize);
            fussY = y + pillow + (i / sheetZoom) * (pillow + clipSize);
            toArrange.get(i).setSize(clipSize, clipSize);
            toArrange.get(i).setPos(fussX, fussY);
        }
        setF(fussY + clipSize + pillow);
    }

    // FIXME closed the panel while scrolled to the very bottom of sardine view, ended up outside latitude

    // TODO THIS IS WHAT I'M DOING I'M REFACTORING PACKSARDINES TO MAKE IT LESS FUCKED

    public void packSardines(){ // I hate this method
        ArrayList<Monad> toArrange = getFilteredMonads();
        clipSize = PApplet.constrain(h / sheetZoom, 9, h); // we start with clipSize as a sort of minimum height for rows
        ArrayList<Spegel> row = new ArrayList<Spegel>();
        float rowWidth = sPillow;
        float sx = sPillow;
        float sy = sPillow;
        float endY = 0;

        for (Monad m : toArrange){
            Spegel s = (Spegel)m;
            float imgW = s.clipping.img.width;
            float imgH = s.clipping.img.height;
            float newWidth = (imgW / imgH) * clipSize;
            float lastRowH = clipSize;

            //if it doesn't fit, we resize the row we just finished
            if (rowWidth + newWidth > w + sPillow) {
                resizeRow(row, rowWidth);
                lastRowH = row.get(0).displayH;

                // then reset for the next row
                sx = sPillow;
                sy += lastRowH + sPillow;
                rowWidth = sPillow;
                row.clear();

                // then back to our spegel that didn't fit to start the new row.
            }

            rowWidth += newWidth;
            s.setSize(newWidth, clipSize);
            s.setPos(sx, sy);
            sx += newWidth + sPillow;
            row.add(s);
        }
        resizeRow(row, rowWidth); // resize the final row
        endY = row.get(0).h;

        setF(sy + endY + sPillow);
//        updateScroller();
    }

    public void resizeRow(ArrayList<Spegel> row, float rowW){
       float ratio = (w - sPillow) / rowW;
       for (Spegel s : row){
           s.setSize(s.displayW * ratio, s.displayH * ratio);
           s.setPos(x + s.x * ratio, s.y);
       }
    }

    public void fishKoan(){
        sardine = !sardine;
        // TODO keep your place when switching back and forth
    }

    public void setF(float floop){
        f = floop;
    }

    public float getF(){
        return f;
    }

    public ArrayList<Spegel> getFilteredSpegels(){
        ArrayList<Spegel> output = new ArrayList<Spegel>();
        ArrayList<Monad> gum;
        if (filter.size() > 0) gum = filter;
        else gum = children;
        for (Monad m : gum){
            output.add((Spegel)m);
        }
        return output;
    }

    public ArrayList<Monad> getFilteredMonads(){
        if (filter.size() > 0) return filter;
        else return children;
    }
}