import processing.core.PApplet;
import processing.core.PGraphics;

import java.sql.Array;
import java.util.ArrayList;

public class Field extends Monad{

    Scroller scroller;
    ContactSheet sheet;
    int scrollW;
    float latitude;
    float sPillow;
    boolean sardine;

    float zoomPillow;


    public Field(Cockpit parent){
        this.parent = parent;
        this.parent.children.add(this);
        setPos(parent.x, parent.y);
        setSize(parent.w, parent.h);
        latitude = 0;
        scrollW = 10;
        sheet = new ContactSheet(this);
        scroller = new Scroller(this);
        sPillow = 2;
        sardine = false;
        zoomPillow = 30;
    }

    @Override
    public void draw(PGraphics g){
        g.background(49);
    }

    @Override
    public void cascadeUpdate(){
        update();
        if (children == null) return;
        for (Monad c : children){
            c.cascadeUpdate();
        }
        // Weird and ugly. The only way I could figure out how to
        // work this was to override this so I can put this updateGrip
        // here. there has to be a better way.
        //TODO  GALAXY BRAIN: instead of reaching down into grip to
        //TODO  change the grip, reach down to read the grip position.
        //TODO  Latitude is based on grip position, not the other way around.
        scroller.grip.updateGrip(latitude, sheet.foot);
    }

    @Override
    public void update(){
        setSize(parent.w, parent.h);
        followScroller();
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
////        updateScroller();
//    }

    public void zoom(int z){
//        sheetZoom -= z; TODO: temporarily commented to make things work for now
//        fussMenagerie(parent.library);
    }

    public float scrollWidth(){
        return scrollW;
    }

    @Override
    public boolean isOnscreen(float latitude) {
        if (y < latitude + parent.h && y >= latitude - h * 1.5) {
            return true;
        } else {
            System.out.println("FIELD OFFSCREEN");
            return false;
        }
    }

    public void setLatitude(float latY){
        latitude = latY;
    }

    // The size of the scroller grip is determined by the contact sheet.
    // The latitude is determined by the position of the grip.
    // contact sheet > grip > latitude.
    public void followScroller(){
        setLatitude(scroller.grip.y / h * sheet.foot);
    }

}
