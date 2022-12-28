import processing.core.PApplet;
import processing.core.PGraphics;
import processing.core.PVector;

import java.lang.reflect.Array;
import java.util.ArrayList;


import java.awt.event.KeyEvent;

public class Visipalp {

    tulpa t;
    Library lib;

    int bgColor = 49;

    int nextID;

    float gutter = 10;
    float x = gutter;
    float y = gutter;

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

    boolean puzzleView = true;
    float puzzleGutter = 2;


    Visipalp(tulpa t, Library lib) {

        this.lib = lib;
        this.t = t;
    }

    // This is the main update method, called every draw frame.
    void showtime(PGraphics g, MouseInput mi, KeyInput ki) {
        prepare();

        readKeyInput(ki);
        if (mi.wheel != 0) {
            changeLatitude(mi.wheel * scrollSpeed);
        }

        g.background(bgColor);
        if (puzzleView) puzzleView(g, t.w - scrollerW, t.h, latitude, mi);
        else contactSheet(g, getID(), 0, 0, t.w - scrollerW, t.h, latitude, mi, ki);
        debugMouseInput(g, mi);
        debugKeyInput(g, ki);

        finish(mi);
    }

    // VISIPALP BUSINESS

    public void debugMouseInput(PGraphics g, MouseInput mi) {
        g.textSize(36);
        g.fill(255, 0, 255);
        if (mi == null) {
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

    public void debugKeyInput(PGraphics g, KeyInput ki) {
        g.textSize(36);
        g.fill(255, 0, 255);
        if (ki == null) {
            g.text("KeyInput is NULL", 400, 100);
            return;
        }
        if (ki.key != '\0') {
            keyDebugKey = ki.key;
            keyDebugKC = ki.kc;
        }
        g.text("action: " + ki.action +
                        "\nkey: " + keyDebugKey +
                        "\nkeycode: " + keyDebugKC +
                        "\nmodifiers: " + ki.mod,
                400, 100);
    }

    public void prepare() {
        hotItem = 0;
    }

    public void finish(MouseInput mi) {
        if (mi.button == 0) activeItem = 0;
        else if (activeItem == 0)
            activeItem = -1; //If the mouse button is still held down, but the button isn't active, we make activeItem unavailable so we don't pick up other elements with the mouse
        nextID = 1;
    }

    public int getID() {
        int newID = nextID;
        nextID++;
        return newID;
    }

    // INPUT

    public boolean mouseOver(float x, float y, float w, float h, MouseInput mi) {
        if (mi.x < x ||
                mi.y < y ||
                mi.x >= x + w ||
                mi.y >= y + h) {
            return false;
        }
        return true;
    }

    public void readKeyInput(KeyInput ki) {
        if (ki == null) return;

        else if (ki.kc == KeyEvent.VK_RIGHT) directionalSelect(1);
        else if (ki.kc == KeyEvent.VK_LEFT) directionalSelect(-1);
        else if (ki.kc == KeyEvent.VK_UP) {
            directionalSelect(-1 * columns);
        } else if (ki.kc == KeyEvent.VK_DOWN) directionalSelect(columns);
        else if (ki.key == '\b') {
            if (lib.selected.size() == 1) {
                lib.whackClipping(lib.selected.get(0));
                System.out.println("BACKSPACE");
            }
        } else if (ki.key == '-') columns--;
        else if (ki.key == '=') columns++;
        else if (ki.key == '0') puzzleView = !puzzleView;
    }

    public void changeLatitude(int l) {
        latitude = PApplet.constrain(latitude + l, 0, foot - t.h);
    }

    // CONTACT SHEET
    // makes all the clippings

    void contactSheet(PGraphics g, int id, float sheetX, float sheetY, float sheetW, float sheetH, float lat, MouseInput mi, KeyInput ki) {
        float clipSize = (sheetW - ((columns + 1) * gutter)) / columns;
        x = sheetX + gutter;
        y = sheetY + gutter;
        int count = 0;
        for (Clipping c : lib.clippings) {
            if (count == columns) {
                x = gutter;
                y += clipSize + gutter;
                count = 0;
            }
            count++;
            if (c == goTo) {
                goToThumbnail(y, lat, sheetY, sheetH);
            }
            g.push();
            g.translate(0, -lat);
            PVector size = sizeThumbnail(c);
            clipping(g, getID(), c, x, y, size.x, size.y, lat, mi);
            g.pop();
            x += clipSize + gutter;
        }
        x = gutter;
        y = gutter;

        scroller(g, getID(), t.w - scrollerW, 0, scrollerW, sheetH, mi);
    }

    // CLIPPINGS

    boolean clipping(PGraphics g, int id, Clipping c, float clipX, float clipY, float thumbW, float thumbH, float lat, MouseInput mi) {

        foot = clipY + thumbH + gutter;

        if (clipY < lat - thumbH) return false;
        if (clipY > lat + t.h) return false;

        PVector size = sizeThumbnail(c);
        PVector offset = findOffset(c, size.x, size.y);

        if (mouseOver(clipX + offset.x, clipY + offset.y - lat, thumbW, thumbH, mi)) {
            hotItem = id;
            if (activeItem == 0 && mi.button == MOUSE1) {
                activeItem = id;
                lib.select(c);
            }
        }

        g.image(c.img, clipX + offset.x, clipY + offset.y, thumbW, thumbH);
        if (c.isSelected()) drawClippingSelect(g, clipX + offset.x, clipY + offset.y, thumbW, thumbH);

        return false;

        // FOR SAFE KEEPING THIS IS "BUTTON" BEHAVIOR
//        if(mouseButton == 0 &&
//            hotItem == id &&
//            activeItem == id){
//            return true;
//        }
//        return false;
    }

    void checkTemperature(int id, float x, float y, float w, float h, MouseInput mi) {
        if (mouseOver(x, y, w, h, mi)) {
            hotItem = id;
            if (activeItem == 0 && mi.button == MOUSE1) {
                activeItem = id;
            }
        }
    }

    PVector sizeThumbnail(Clipping c) {
        float wid = c.img.width;
        float hei = c.img.height;

        if (wid >= hei) {
            wid = getClipSize();
            hei = c.img.height / (c.img.width / getClipSize());
        } else {
            hei = getClipSize();
            wid = c.img.width / (c.img.height / getClipSize());
        }
        return new PVector(wid, hei);
    }


    PVector findOffset(Clipping c, float wid, float hei) {
        float offX = 0;
        float offY = 0;
        if (wid >= hei) offY = (getClipSize() - hei) / 2;
        else offX = (getClipSize() - wid) / 2;
        return new PVector(offX, offY);
    }

    public void drawClippingSelect(PGraphics g, float x, float y, float w, float h) {
        g.stroke(255);
        g.noFill();
        g.rect(x, y, w, h);
    }

    // SCROLLER

    public boolean scroller(PGraphics g, int id, float scrollerX, float scrollerY, float scrollerW, float scrollerH, MouseInput mi) {
        g.noStroke();
        g.fill(scrollerColor);
        g.rect(scrollerX, scrollerY, scrollerW, scrollerH);

        grip(g, getID(), scrollerX, scrollerW, scrollerH, mi);

        return false;
    }

    public boolean grip(PGraphics g, int id, float gripX, float gripW, float scrollerH, MouseInput mi) {
        float gripH = setGripSize(foot, scrollerH);
        float gripY;

        if (activeItem == id) {
            gripY = PApplet.constrain(mi.y - scrollGrabY, 0, scrollerH - gripH);
            latitude = gripY / scrollerH * foot;
        } else gripY = setGripPos(latitude, foot, scrollerH, gripH);

        g.noStroke();
        if (activeItem == id) g.fill(gripActiveColor);
        else g.fill(gripColor);
        g.rect(gripX, gripY, gripW, gripH);

        if (mouseOver(gripX, gripY, gripW, gripH, mi)) {
            hotItem = id;
            if (activeItem == 0 && mi.button == MOUSE1) {
                activeItem = id;
                scrollGrabY = mi.y - gripY;
            }
        }

        return false;
    }

    public float setGripSize(float bottomOfScroll, float scrollerH) {
        return PApplet.constrain(scrollerH / bottomOfScroll * scrollerH, 0, scrollerH);
    }

    public float setGripPos(float scrollValue, float bottomOfScroll, float scrollerH, float gripH) {
        return PApplet.constrain(scrollValue / bottomOfScroll * scrollerH, 0, scrollerH - gripH);
    }

    public void directionalSelect(int amount) {
        if (lib.selected.size() == 1) {
            Clipping selClip = lib.selected.get(0);
            int index = lib.clippings.indexOf(selClip);
            lib.select(lib.clippings.get(PApplet.constrain(index + amount, 0, lib.clippings.size() - 1)));
            goTo = lib.selected.get(0); // mark this clipping to follow the selection offscreen if we need to
        }
    }

    public void setLatitude(float y) {
        latitude = y;
    }

    public void goToThumbnail(float thumbY, float lat, float sheetY, float sheetH) {
        if (thumbY - gutter < lat) {
            System.out.println("thumbnail above screen at " + thumbY + ". Setting latitude to" + (thumbY - gutter));
            setLatitude(thumbY - gutter);
        } else if (thumbY + getClipSize() + gutter > lat + sheetH) {
            System.out.println("thumbnail below screen at " + thumbY + ". Setting latitude to" + ((thumbY + getClipSize() + gutter) - sheetH));
            setLatitude((thumbY + getClipSize() + gutter) - sheetH);
        }
        goTo = null;
    }

    // i hate this method :(
    //FIXME for some reason the rows don't come out to the same width. Some of them fall short of the sheet width
    public void puzzleView(PGraphics g, float sheetW, float sheetH, float lat, MouseInput mi) {
        // set up
        float minH = PApplet.constrain(sheetH / columns, 9, sheetH);
        ArrayList<Clipping> row = new ArrayList<Clipping>();
        float rowWidth = 0;
        Clipping pocketedClipping = null;
        float px = puzzleGutter;
        float py = puzzleGutter;
        float rowH = 0;

        for (Clipping c : lib.clippings) {

            float adjustedW = (minH * c.img.width) / c.img.height;      // find the new width if scaled to minH

            if (rowWidth + adjustedW <= sheetW - puzzleGutter) { // does the next clipping fit on this row
                row.add(c);
                rowWidth += adjustedW + puzzleGutter;

            } else { // if it doesn't fit, we set it aside for later and resize the row we just finished
                pocketedClipping = c;
                float ratio = (sheetW - 2 * puzzleGutter) / rowWidth;
                puzzleRow(g, row, ratio, minH, py, lat, mi);
                rowH = minH * ratio;

                // reset for the next row
                row.clear();
                py += rowH + puzzleGutter;
                rowWidth = 0;

                // then back to the clipping we set aside to start the new row
                row.add(pocketedClipping);
                rowWidth += adjustedW + puzzleGutter;
            }
        }
        scroller(g, getID(), t.w - scrollerW, 0, scrollerW, sheetH, mi);
    }

//    public void puzzleRow(PGraphics g, ArrayList<Clipping> row){
//        float px = puzzleGutter;
//        float BW = puzzleGutter;
//        for (Clipping clip : row){
//            BW += clip.img.width + puzzleGutter;
//        }
//    }

    public void puzzleRow(PGraphics g, ArrayList<Clipping> row, float ratio, float minH, float rowY, float lat, MouseInput mi) {
        float px = puzzleGutter;
        for (Clipping clip : row) {
            float displayW = ((minH * clip.img.width) / clip.img.height) * ratio;
            float displayH = minH * ratio;
            clippingPuzzle(g, getID(), clip, px, rowY, displayW, displayH, lat, mi);
            px += displayW + puzzleGutter;
            foot = rowY + displayH + puzzleGutter;
        }
    }

    boolean clippingPuzzle(PGraphics g, int id, Clipping c, float clipX, float clipY, float thumbW, float thumbH,
                           float lat, MouseInput mi) {

        if (clipY < lat - getClipSize()) return false;
        if (clipY > lat + t.h) return false;

        if (mouseOver(clipX, clipY - lat, thumbW, thumbH, mi)) {
            hotItem = id;
            if (activeItem == 0 && mi.button == MOUSE1) {
                activeItem = id;
                lib.select(c);
            }
        }

        g.push();
        g.translate(0, -latitude);
        g.image(c.img, clipX, clipY, thumbW, thumbH);
        if (c.isSelected()) drawClippingSelect(g, clipX, clipY, thumbW, thumbH);
        g.pop();

        return false;

    }

    public float getClipSize() {
        return (getSheetWidth() - (gutter * (columns + 1))) / columns;
    }

    public float getSheetWidth() {
        return t.w - scrollerW;
    }
}

//TODO panel
//TODO view
//TODO drag thumbnails
//TODO multiple select
//TODO multiple delete
//TODO multiple drag
//TODO typing
//TODO tags

// FOR SAFE KEEPING THIS IS "BUTTON" BEHAVIOR
//        if(mouseButton == 0 &&
//            hotItem == id &&
//            activeItem == id){
//            return true;
//        }
//        return false;
