import processing.core.PApplet;
import processing.core.PGraphics;

import java.sql.Array;
import java.util.ArrayList;

public class Field extends Monad{

    Cockpit parent;
    Scroller scroller;
    ContactSheet sheet;
    int scrollerW;
    int sheetZoom;
    float latitude;
    float clipSize;
    float pillow;
    float sPillow;
    float foot;
    boolean sardine;

    float zoomPillow;


    public Field(Cockpit parent){
        this.parent = parent;
        this.parent.children.add(this);
        setPos(parent.x, parent.y);
        setSize(parent.w, parent.h);
        latitude = 0;
        scrollerW = 10;
        sheet = new ContactSheet(this);
        scroller = new Scroller(this);
        sheetZoom = 5;
        pillow = 10;
        sPillow = 2;
        clipSize = 200;
        sardine = false;
        zoomPillow = 30;
    }

    @Override // gets called by Cockpit's draw().
    public void draw(PGraphics g){
        g.background(49);
    }

    @Override
    public void update(){
        setSize(parent.x, parent.y);
    }

    public void newSpegel(Clipping clip){
        Spegel speg = new Spegel(this.sheet, clip);
        sheet.children.add(speg);
    }

    public void arrangeSpegels(){
        float fussX = pillow;
        float fussY = pillow;
        for (int i = 0; i < sheet.children.size(); i++){
            fussX = pillow + (i % sheetZoom) * (pillow + clipSize);
            fussY = pillow + (i / sheetZoom) * (pillow + clipSize);
            sheet.children.get(i).setSize(clipSize, clipSize);
            sheet.children.get(i).setPos(fussX, fussY);
        }
        setFoot(fussY);
    }

//    public void packSardines(Library library) {
//        clipSize = PApplet.constrain(h / sheetZoom, 9, 9999999);
//        ArrayList<Spegel> row = new ArrayList<Spegel>();
//        float rowWidth = sPillow;
//        float rowHeight = sPillow;
//        float x = sPillow;
//        float y = sPillow;
//
//        for (Spegel clip : library.Spegels) {
//            float clipW = clip.img.width;
//            float clipH = clip.img.height;
//            float newWidth = (clipW / clipH) * clipSize;
//            float nextY = clipSize;
//            if (rowWidth + newWidth > w + sPillow) {    // if it dont fit
//                // resize the row to fit the window
//                float ratio = (w - sPillow) / rowWidth;
//                for (Spegel c : row){
//                    c.setSize(c.displayW * ratio, c.displayH * ratio);
//                    c.setPos(c.x * ratio, c.y);
//                    nextY = c.displayH;
//                }
//                x = sPillow;
//                y += nextY + sPillow;
//                rowWidth = sPillow;
//                row.clear();
//            }
//            rowWidth += newWidth;
//            clip.setSize(newWidth, clipSize);
//            clip.setPos(x, y);
//            x += newWidth + sPillow;
//            row.add(clip);
//        }
//        foot = y + clipSize + sPillow;
////        updateScroller();
//    }

    public void zoom(int z){
        sheetZoom -= z;
//        fussMenagerie(parent.library);
    }

    public void updateSize(){
        w = parent.w - scrollerW;
    }

    public void setClipSize(){
        clipSize = PApplet.constrain((w - (pillow * (sheetZoom + 0))) / sheetZoom, 10, 9999999);
    }

    public void setFoot(float footY){
        foot = footY + clipSize + pillow;
    }

}
