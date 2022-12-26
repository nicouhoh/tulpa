import processing.core.PApplet;
import processing.core.PGraphics;
import processing.core.PVector;

import java.awt.event.KeyEvent;

public class Visipalp {

    tulpa t;
    Library lib;

    int bgColor = 49;

    int nextID;

    float gutter = 10;
    float x = gutter;
    float y = gutter;
    float clipSize = 200;

    int columns = 7;


    int MOUSE1 = 37;
    int MOUSE2 = 39;

    int hotItem = 0;
    int activeItem = 0;

    // Scroller
    int scrollerColor = 0xff1A1A1A;
    int gripColor = 0xff6C6C6C;
    int gripActiveColor = 225;
    int scrollerW = 20;

    float latitude = 0;
    float foot = 0;
    int scrollSpeed = 200;
    float scrollGrabY = 0;
    Clipping goTo = null;

    char keyDebugKey;
    int keyDebugKC;


    Visipalp(tulpa t, Library lib){

        this.lib = lib;
        this.t = t;
    }

    // This is the main update method, called every draw frame.
    void showtime(PGraphics g, MouseInput mi, KeyInput ki){
        prepare();

        readKeyInput(ki);
        if (mi.wheel != 0){
           changeLatitude(mi.wheel * scrollSpeed);
        }

        g.background(bgColor);
        contactSheet(g, getID(), 0, 0, t.w - scrollerW, t.h, latitude, mi, ki);
        debugMouseInput(g, mi);
        debugKeyInput(g, ki);

        finish(mi);
    }

    // VISIPALP BUSINESS

    public void debugMouseInput(PGraphics g, MouseInput mi){
        g.textSize(36);
        g.fill(255, 0, 255);
        if(mi == null) {
            g.text("MouseInput is NULL", 100, 100);
            return;
        }
        g.text("button :" + mi.button +
                    "\nx: " + mi.x +
                    "\ny: " + mi.y +
                    "\nwheel: " + mi.wheel +
                    "\nmod: " + mi.mod +
                    "\nhot: " + hotItem +
                    "\nactive: " + activeItem,
                    100, 100);
    }

    public void debugKeyInput(PGraphics g, KeyInput ki){
        g.textSize(36);
        g.fill(255,0, 255);
        if (ki == null){
            g.text("KeyInput is NULL", 400, 100);
            return;
        }
        if(ki.key != '\0'){
            keyDebugKey = ki.key;
            keyDebugKC = ki.kc;
        }
        g.text("action: " + ki.action +
                "\nkey: " + keyDebugKey +
                "\nkeycode: " + keyDebugKC +
                "\nmodifiers: " + ki.mod,
                400, 100);
    }

    public void prepare(){
        hotItem = 0;
    }
    public void finish(MouseInput mi){
        if (mi.button == 0) activeItem = 0;
        else if (activeItem == 0) activeItem = -1; //If the mouse button is still held down, but the button isn't active, we make activeItem unavailable so we don't pick up other elements with the mouse
        nextID = 1;
    }

    public int getID(){
        int newID = nextID;
        nextID++;
        return newID;
    }

    // INPUT

    public boolean mouseOver(float x, float y, float w, float h, MouseInput mi){
        if (    mi.x < x ||
                mi.y < y ||
                mi.x >= x + w ||
                mi.y >= y + h){
            return false;
        }
        return true;
    }

    public void readKeyInput(KeyInput ki){
        if (ki == null) return;

        else if (ki.kc == KeyEvent.VK_RIGHT) directionalSelect(1);
        else if (ki.kc == KeyEvent.VK_LEFT) directionalSelect(-1);
        else if (ki.kc == KeyEvent.VK_UP) {
            directionalSelect(-1 * columns);
        }
        else if (ki.kc == KeyEvent.VK_DOWN) directionalSelect(columns);
        else if (ki.key == '\b') {
            if (lib.selected.size() == 1) {
                lib.whackClipping(lib.selected.get(0));
                System.out.println("BACKSPACE");
            }
        }else if (ki.key == '-') columns--;
        else if (ki.key == '=') columns++;
    }

    public void changeLatitude(int l){
        latitude = PApplet.constrain(latitude + l, 0, foot - t.h);
    }

    // CONTACT SHEET
    // makes all the clippings

    void contactSheet(PGraphics g, int id, float sheetX, float sheetY, float sheetW, float sheetH, float lat, MouseInput mi, KeyInput ki){
        clipSize = (sheetW - ((columns + 1) * gutter)) / columns;
        x = sheetX + gutter;
        y = sheetY + gutter;
        int count = 0;
        for (Clipping c : lib.clippings){
//            if (x + clipSize + gutter > sheetW){
            if (count == columns) { //TODO something screwy is going on here, check your rows. Clippings lost between rows
                x = gutter;
                y += clipSize + gutter;
                count = 0;
            }
            count++;
            if (c == goTo){
                goToThumbnail(y, lat, sheetY, sheetH);
            }
            g.push();
            g.translate(0, -lat);
            clipping(g, getID(), c, x, y, lat, mi);
            g.pop();
            x += clipSize + gutter;
        }
        x = gutter;
        y = gutter;

        scroller(g, getID(), t.w - scrollerW, 0, scrollerW, sheetH, mi);
    }

    // CLIPPINGS

    boolean clipping(PGraphics g, int id, Clipping c, float clipX, float clipY, float lat, MouseInput mi){

        foot = clipY + clipSize + gutter;

        if(clipY < lat - clipSize) return false;
        if(clipY > lat + t.h) return false;

        PVector size = SizeThumbnail(c);
        PVector offset = findOffset(c, size.x, size.y);

        if(mouseOver(clipX + offset.x, clipY + offset.y - lat, size.x, size.y, mi)){
            hotItem = id;
            if(activeItem == 0 && mi.button == MOUSE1){
                activeItem = id;
                lib.select(c);
            }
        }

        g.image(c.img, clipX + offset.x, clipY + offset.y, size.x, size.y);
        if (c.isSelected()) drawClippingSelect(g, clipX + offset.x, clipY + offset.y, size.x, size.y);

        return false;

        // FOR SAFE KEEPING THIS IS "BUTTON" BEHAVIOR
//        if(mouseButton == 0 &&
//            hotItem == id &&
//            activeItem == id){
//            return true;
//        }
//        return false;
    }

    void checkTemperature(int id, float x, float y, float w, float h, MouseInput mi){
        if (mouseOver(x, y, w, h, mi)){
            hotItem = id;
            if(activeItem == 0 && mi.button == MOUSE1){
                activeItem = id;
            }
        }
    }

    PVector SizeThumbnail(Clipping c) {
        float wid = c.img.width;
        float hei = c.img.height;

        if (wid >= hei) {
            wid = clipSize;
            hei = c.img.height / (c.img.width / clipSize);
        } else {
            hei = clipSize;
            wid = c.img.width / (c.img.height / clipSize);
        }
        return new PVector(wid, hei);
    }

    PVector findOffset(Clipping c, float wid, float hei){
        float offX = 0;
        float offY = 0;
        if (wid >= hei) offY = (clipSize - hei) / 2;
        else offX = (clipSize - wid) / 2;
        return new PVector(offX, offY);
    }

    public void drawClippingSelect(PGraphics g, float x, float y, float w, float h){
        g.stroke(255);
        g.noFill();
        g.rect(x, y, w, h);
    }

    // SCROLLER

    public boolean scroller(PGraphics g, int id, float scrollerX, float scrollerY, float scrollerW, float scrollerH, MouseInput mi){
        g.noStroke();
        g.fill(scrollerColor);
        g.rect(scrollerX, scrollerY, scrollerW, scrollerH);

        grip(g, getID(), scrollerX, scrollerW, scrollerH, mi);

        return false;
    }

    public boolean grip(PGraphics g, int id, float gripX, float gripW, float scrollerH, MouseInput mi){
        float gripH = setGripSize(foot, scrollerH);
        float gripY;

        if(activeItem == id){
            gripY = PApplet.constrain( mi.y - scrollGrabY,0, scrollerH - gripH);
            latitude = gripY / scrollerH * foot;
        }
        else gripY = setGripPos(latitude, foot, scrollerH, gripH);

        g.noStroke();
        if (activeItem == id) g.fill(gripActiveColor);
        else g.fill(gripColor);
        g.rect(gripX, gripY, gripW, gripH);

        if (mouseOver(gripX, gripY, gripW, gripH, mi)){
            hotItem = id;
            if(activeItem == 0 && mi.button == MOUSE1){
                activeItem = id;
                scrollGrabY = mi.y - gripY;
            }
        }

        return false;
    }

    public float setGripSize(float bottomOfScroll, float scrollerH){
        return PApplet.constrain(scrollerH / bottomOfScroll * scrollerH, 0, scrollerH);
    }

    public float setGripPos(float scrollValue, float bottomOfScroll, float scrollerH, float gripH){
        return PApplet.constrain(scrollValue / bottomOfScroll * scrollerH, 0, scrollerH - gripH);
    }

    public void directionalSelect(int amount) {
        if (lib.selected.size() == 1) {
            Clipping selClip = lib.selected.get(0);
            int index = lib.clippings.indexOf(selClip);
            lib.select(lib.clippings.get(PApplet.constrain(index + amount,0,lib.clippings.size() - 1)));
            goTo = lib.selected.get(0);
        }
    }

    public void setLatitude(float y){
        latitude = y;
    }

    public void goToThumbnail(float thumbY, float lat, float sheetY, float sheetH){
        if (thumbY - gutter < lat) {
            System.out.println("thumbnail above screen at " + thumbY + ". Setting latitude to" + (thumbY - gutter));
            setLatitude(thumbY - gutter);
        }
        else if (thumbY + clipSize + gutter > lat + sheetH) {
            System.out.println("thumbnail below screen at " + thumbY + ". Setting latitude to" + ((thumbY + clipSize + gutter) - sheetH));
            setLatitude((thumbY + clipSize + gutter) - sheetH);
        }
        goTo = null;
    }
}