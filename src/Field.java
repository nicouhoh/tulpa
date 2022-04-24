import processing.core.PApplet;
import processing.core.PGraphics;

import java.sql.Array;
import java.util.ArrayList;

public class Field extends Monad implements Scrollable{

    Scroller scroller;
    ContactSheet sheet;
    int scrollW;
    float latitude;
    float foot;
    float sPillow;

    float zoomPillow;

    float scrollAmount = 10;


    public Field(Cockpit parent){
        this.parent = parent;
        this.parent.children.add(this); // Field is a child of the Cockpit, and its children are the ContactSheet and the Scroller.
        setPos(parent.x, parent.y);
        setSize(parent.w, parent.h);
        latitude = 0;
        scrollW = 10;
        sheet = new ContactSheet(this);
        scroller = new Scroller(this);
        sPillow = 2;
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

        setFoot(sheet.getF());
        scroller.updateGrip(latitude, foot); // Not sure about this. maybe it's okay. it only updates on resize, so....
    }


    @Override
    public void update(){
        setSize(parent.w, parent.h);
    }



    public void zoom(int z){
//        sheetZoom -= z; TODO: temporarily commented to make things work for now
//        fussMenagerie(parent.library);
    }

    public float scrollWidth(){
        return scrollW;
    }

    @Override
    public boolean isOnscreen(float latitude) {
        if (y < parent.y + parent.h && y + h >= parent.y) {
            return true;
        } else {
            System.out.println("FIELD OFFSCREEN");
            return false;
        }
    }

    public float getLatitude(){
        return latitude;
    }

    // it's possible the following few will cause scroller problems related
    // to the if(scroller.grip.grabbed) return; line in the future.
    // so if you find you need to change this at some point, go with God

    public void setLatitude(float latY){
        latitude = PApplet.constrain(latY, 0, foot - h);
        if(scroller.grip.grabbed) return;
        scroller.updateGrip(latitude, foot);
    }

    // choose a y value and where onscreen you want to put it
    public void goTo(float lat, float where){
       setLatitude(lat - where);
    }

    public void jumpToSpegel(Spegel s){
            if(s.y - latitude < y) goTo(s.y, sheet.pillow);
            else if (s.y - latitude + s.displayH > h) goTo(s.y, h - s.h - sheet.pillow);
    }

    public void stepLatitude(float step){
        setLatitude(latitude += step);
        if(scroller.grip.grabbed) return;
        scroller.updateGrip(latitude, foot);
    }

    public void setFoot(float f){
       foot = f;
       if(scroller.grip.grabbed) return;
       scroller.updateGrip(latitude, foot);
    }

    // The size of the scroller grip is determined by the contact sheet.
    // The latitude is determined by the position of the grip.
    // contact sheet > grip > latitude.
    public void followScroller(){
        setLatitude(scroller.grip.y / h * foot);
    }

    public void scroll(Operator operator, int count){
       stepLatitude(count * scrollAmount);
    }

}
