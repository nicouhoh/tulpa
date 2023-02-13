import processing.core.PApplet;
import processing.core.PGraphics;
import processing.core.PVector;
import processing.core.PImage;

import java.util.ArrayList;
import java.util.List;

import java.awt.event.KeyEvent;

public class Visipalp {

    tulpa t;
    Library lib;

    PGraphics g;

    Mode mode = Mode.CONTACTSHEET;

    int bgColor = 49;

    int nextID;

    float gutter = 10;

    int columns = 7;


    int MOUSE1 = 37;
    int MOUSE2 = 39;

    int hotItem = 0;
    int activeItem = 0;

    // Casper
    PVector casperSize = new PVector(0, 0);
    PVector casperOffset;
    ArrayList<Clipping> heldClippings = new ArrayList<Clipping>();

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

    // Puzzle View
    boolean puzzleView = true;
    float puzzleGutter = 2;
    PVector upDownSelect = new PVector(0,0);

    // Clipping View


    Visipalp(tulpa t, PGraphics g, Library lib) {

        this.lib = lib;
        this.g = g;
        this.t = t;
    }

    // This is the main update method, called every draw frame.
    void showtime(MouseInput mi, KeyInput ki) {
        prepare(mi);

        readKeyInput(ki);

        if (mi.wheel != 0) {
            changeLatitude(mi.wheel * scrollSpeed);
        }

        g.background(bgColor);
        if (puzzleView) puzzleView(0, t.w - scrollerW, t.h, latitude, mi, ki);
        else thumbnailView(0, 0, t.h, latitude, mi, ki);
        casperLayer(mi);
        if (mode == Mode.CLIPPINGVIEW) clippingView();
        finish(mi);
//        debugMouseInput(mi);
//        debugKeyInput(g, ki);
    }

    // VISIPALP BUSINESS

    public void prepare(MouseInput mi) {
        hotItem = 0;
        nextID = 1;
    }

    public void finish(MouseInput mi){
        if (mi.button == 0){
            heldClippings.clear();
            activeItem = 0;
        }else if (mi.button == 1 && activeItem == 0) activeItem = -1;
    }

    public void casperLayer(MouseInput mi){
        if (!heldClippings.isEmpty()){
            casper(heldClippings.get(0), casperSize.x, casperSize.y, mi);
        }
    }

    public int getID() {
        int newID = nextID;
        nextID++;
        return newID;
    }

    public float getClipSize() {
        return (getSheetWidth() - (gutter * (columns + 1))) / columns;
    }

    public float getSheetWidth() {
        return t.w - scrollerW;
    }

    // INPUT

    public boolean mouseOver(float x, float y, float w, float h, MouseInput mi) {
        return !(mi.x < x) &&
                !(mi.y < y) &&
                !(mi.x >= x + w) &&
                !(mi.y >= y + h);
    }

    public void readKeyInput(KeyInput ki) {
        if (ki == null) return;

        if (mode == Mode.CONTACTSHEET){
            if (ki.kc == KeyEvent.VK_RIGHT) directionalSelect(1);
            else if (ki.kc == KeyEvent.VK_LEFT) directionalSelect(-1);
            else if (ki.key == '\b') {
                if (lib.selected.size() > 0) {
                    lib.whackClipping(lib.selected);
                    System.out.println("BACKSPACE");
                }
            } else if (ki.key == '-') columns++;
            else if (ki.key == '=') columns--;
            else if (ki.key == '0') puzzleView = !puzzleView;
            else if (ki.key == KeyEvent.VK_SPACE && lib.selected.size() == 1) mode = Mode.CLIPPINGVIEW;

        } else if(mode == Mode.CLIPPINGVIEW){
            if (ki. kc == KeyEvent.VK_RIGHT) directionalSelect(1);
            else if (ki.kc == KeyEvent.VK_LEFT) directionalSelect(-1);
            else if (ki.key == KeyEvent.VK_SPACE) mode = Mode.CONTACTSHEET;
        }
    }

    // CONTACT SHEET
    // makes all the clippings

    // FIXME doesn't set foot correctly at all zoom levels

    void thumbnailView(float sheetX, float sheetY, float sheetH, float lat, MouseInput mi, KeyInput ki) {
        List<Clipping> row;

        g.push();
        g.translate(0, -lat);
        for (int i = 0; i < lib.clippings.size() / columns; i++){
            row = lib.clippings.subList(columns * i, PApplet.constrain((columns * (i+1)) , 0, lib.clippings.size()));
            thumbnailRow(row, i, (((i+1) * gutter) + (i * getClipSize())), sheetX, sheetY, sheetH, mi, ki);
        }
        g.pop();

        scroller(getID(), t.w - scrollerW, 0, scrollerW, sheetH, mi);
    }

    void thumbnailRow(List<Clipping> row, int rowNum, float rowY, float sheetX, float sheetY, float sheetH, MouseInput mi, KeyInput ki){
        float previousRightEdge = 0;
//        Clipping nextRowClipping = row.get(-1)
        for (int i = 0; i < row.size(); i++){
            Clipping c = row.get(i);
            float clipX = sheetX + ((i + 1) * gutter) + (i * getClipSize());
            PVector size = sizeThumbnail(c);
            PVector offset = findOffset(size.x, size.y);
            clipping(getID(), rowNum, c, clipX, rowY, size.x, size.y, offset, latitude, sheetY, sheetH, previousRightEdge, mi, ki);
            previousRightEdge = ((i + 1) * gutter) + ((i + 1) * getClipSize()) - offset.x;
        }
        dropZone(lib.clippings.get(rowNum * columns + 1), (previousRightEdge + sheetX + getSheetWidth()) / 2, 50, rowY, getClipSize(), latitude, mi);
        //TODO I think I still need to do a special case for the very last clipping in the library
    }

    void clipping(int id, int rowNum, Clipping c, float clipX, float clipY, float thumbW,
                  float thumbH, PVector offset, float lat, float sheetY, float sheetH, float previousRightEdge, MouseInput mi, KeyInput ki) {

        foot = clipY + thumbH + gutter;
        upDownSelect(c, rowNum, sheetY, sheetH, clipX, clipY, thumbW, lat, ki);

        // don't draw or bother with mouse interaction if offscreen
        if (clipY < lat - thumbH) return;
        if (clipY > lat + t.h) return;

        clippingMouseInteract(id, c, clipX + offset.x,clipY + offset.y - lat, thumbW, thumbH, mi);

        g.image(c.img, clipX + offset.x, clipY + offset.y, thumbW, thumbH);
        if (c.isSelected(lib)) drawClippingSelect(clipX + offset.x, clipY + offset.y, thumbW, thumbH);

        if(heldClippings.isEmpty()) return;
        dropZone(c, (previousRightEdge + clipX + offset.x) / 2, 50, clipY, getClipSize(), lat, mi);
    }

    void clippingMouseInteract(int id, Clipping c, float x, float y, float w, float h, MouseInput mi) {
        // MOUSEOVER
        if (mouseOver(x, y, w, h, mi)) {
            hotItem = id;

            // MOUSE DOWN
            if (activeItem == 0 && mi.button == MOUSE1) {
                System.out.println("Start: " + heldClippings);
                activeItem = id;
                casperOffset = new PVector(mi.x - x, mi.y - y);
                casperSize = new PVector(w, h);
                System.out.println("Casper set: " + heldClippings);
                if (!c.isSelected(lib) && mi.mod == 0) {
                    System.out.println("before: " + heldClippings);
                    lib.select(c);
                    System.out.println("Selected: " + heldClippings);
                }
                else if (mi.mod == 2)
                    lib.addSelect(c);
                System.out.println("Out: " + heldClippings);
            }
            return;
        }

        // DRAGGING CLIPPING
        if (activeItem != id) return;
        if (!heldClippings.isEmpty()) return;
        if(c.isSelected(lib) && lib.selected.size() > 1)
            setDraggedClippings(lib.selected, w, h, mi);
        else setDraggedClippings(c, w, h, mi);
    }

    void setDraggedClippings(Clipping c, float w, float h, MouseInput mi){
        heldClippings.clear();
        heldClippings.add(c);
    }

    void setDraggedClippings(ArrayList<Clipping> c, float w, float h, MouseInput mi){
        heldClippings.clear();
        heldClippings.addAll(c);
    }

    void dropZone(Clipping c, float dropX, float range, float rowY, float rowH, float lat, MouseInput mi){
        if (heldClippings.isEmpty()) return;
        if (mi.y < rowY - lat || mi.y > rowY + rowH - lat) return;
        if (PApplet.dist(mi.x, mi.y, dropX, mi.y) > range)return;
        g.stroke(255, 0, 255);
        g.strokeWeight(2);
        g.line(dropX, rowY, dropX, rowY + rowH);
        g.strokeWeight(1);
        if(mi.button == 0){
            lib.moveClipping(heldClippings, c);
            heldClippings.clear();
        }
    }

    void casper(Clipping c, float w, float h, MouseInput mi){
        g.push();
        g.translate(0, -latitude);
        g.tint(255, 128);
        g.image(c.img, mi.x - casperOffset.x, mi.y + latitude - casperOffset.y, w, h);
        g.tint(255);
        g.pop();
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

    PVector findOffset(float wid, float hei) {
        float offX = 0;
        float offY = 0;
        if (wid >= hei) offY = (getClipSize() - hei) / 2;
        else offX = (getClipSize() - wid) / 2;
        return new PVector(offX, offY);
    }

    public void drawClippingSelect(float x, float y, float w, float h) {
        g.stroke(255);
        g.noFill();
        g.rect(x, y, w, h);
    }

    // SCROLLER

    public void scroller(int id, float scrollerX, float scrollerY, float scrollerW, float scrollerH, MouseInput mi) {
        g.noStroke();
        g.fill(scrollerColor);
        g.rect(scrollerX, scrollerY, scrollerW, scrollerH);

        grip(getID(), scrollerX, scrollerW, scrollerH, mi);
    }

    void grip(int id, float gripX, float gripW, float scrollerH, MouseInput mi) {
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
    }

    public float setGripSize(float bottomOfScroll, float scrollerH) {
        return PApplet.constrain(scrollerH / bottomOfScroll * scrollerH, 0, scrollerH);
    }

    public float setGripPos(float scrollValue, float bottomOfScroll, float scrollerH, float gripH) {
        return PApplet.constrain(scrollValue / bottomOfScroll * scrollerH, 0, scrollerH - gripH);
    }

    public void directionalSelect(int amount) {
        if (lib.selected.size() != 1) return;
        Clipping selClip = lib.selected.get(0);
        int index = lib.clippings.indexOf(selClip);
        lib.select(lib.clippings.get(PApplet.constrain(index + amount, 0, lib.clippings.size() - 1)));
        goTo = lib.selected.get(0); // mark this clipping to follow the selection offscreen if we need to
    }

    // LATITUDE AND SCROLLING

    public void setLatitude(float y) {
        latitude = y;
    }

    public void changeLatitude(int l) {
        latitude = PApplet.constrain(latitude + l, 0, foot - t.h);
    }

    public void goToThumbnail(float thumbY, float lat, float sheetH) {
        if (thumbY - gutter < lat) {
            System.out.println("thumbnail above screen at " + thumbY + ". Setting latitude to" + (thumbY - gutter));
            setLatitude(thumbY - gutter);
        } else if (thumbY + getClipSize() + gutter > lat + sheetH) {
            System.out.println("thumbnail below screen at " + thumbY + ". Setting latitude to" + ((thumbY + getClipSize() + gutter) - sheetH));
            setLatitude((thumbY + getClipSize() + gutter) - sheetH);
        }
        goTo = null;
    }

    // PUZZLE VIEW

    // i hate this method :(
    //FIXME for some reason the rows don't come out to the same width. Some of them fall short of the sheet width
    public void puzzleView(float sheetY, float sheetW, float sheetH, float lat, MouseInput mi, KeyInput ki) {
        // set up
        float minH = PApplet.constrain(sheetH / columns, 9, sheetH);
        ArrayList<Clipping> row = new ArrayList<Clipping>();
        float rowWidth = 0;
        Clipping pocketedClipping = null;
        float py = puzzleGutter;
        float rowH = 0;
        int rowNum = 1;

        g.push();
        g.translate(0, -latitude);

        for (Clipping c : lib.clippings) {

            float adjustedW = (minH * c.img.width) / c.img.height;      // find the new width if scaled to minH

            if (rowWidth + adjustedW <= sheetW - puzzleGutter) { // does the next clipping fit on this row
                row.add(c);
                rowWidth += adjustedW + puzzleGutter;

            } else { // if it doesn't fit, we set it aside for later, resize the row we just finished and draw it
                pocketedClipping = c;
                float ratio = (sheetW - 2 * puzzleGutter) / rowWidth;
                puzzleRow(row, rowNum, ratio, minH, py, sheetY, sheetH, lat, mi, ki);
                rowNum++;
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
        // finish off the last row
        float ratio = (sheetW - 2 * puzzleGutter) / rowWidth;
        puzzleRow(row, rowNum, ratio, minH, py, sheetY, sheetH, lat, mi, ki);

        g.pop();

        scroller(getID(), t.w - scrollerW, 0, scrollerW, sheetH, mi);
    }

    public void puzzleRow(ArrayList<Clipping> row, int rowNum, float ratio, float minH, float rowY, float sheetY, float sheetH, float lat, MouseInput mi, KeyInput ki) {
        float px = puzzleGutter;
        for (Clipping clip : row) {
            float displayW = ((minH * clip.img.width) / clip.img.height) * ratio;
            float displayH = minH * ratio;
            followClippingOffscreen(clip, rowY, lat, 0, sheetH);
            PVector offset = new PVector(0,0);

            clipping(getID(), rowNum, clip, px, rowY, displayW, displayH, offset, lat, sheetY, sheetH, 0, mi, ki);

            dropZone(clip, px - puzzleGutter/2, 50, rowY, displayH,lat, mi);

            px += displayW + puzzleGutter;
            foot = rowY + displayH + puzzleGutter;
        }
    }

    // FIXME having problems arrowing up or down to a particular clipping... it's the tall text screenshot, between some flowers and a mansion
    public void upDownSelect(Clipping c, int rowNum, float sheetY, float sheetH, float clipX, float clipY, float thumbW, float lat, KeyInput ki) {
        if (lib.selected.size() != 1) return;

        if (upDownSelect.x != 0 && clipX <= upDownSelect.x && clipX + thumbW > upDownSelect.x) {
            if (rowNum == upDownSelect.y) {
                lib.select(c);
                goTo = c;
                followClippingOffscreen(c, clipY, lat, sheetY, sheetH);
                upDownSelect.x = 0;
                upDownSelect.y = 0;
            }
        }

        // Keyboard input for updown selection
        if (mode != Mode.CONTACTSHEET) return;
        if (lib.selected.get(0) != c) return;
        if (ki.kc == KeyEvent.VK_UP) {
                setUpDownSelect(clipX, rowNum - 1, thumbW);
            ki.kc = 0;
        }
        if (ki.kc == KeyEvent.VK_DOWN) {
                setUpDownSelect(clipX, rowNum + 1, thumbW);
            ki.kc = 0;
        }
    }

    // CLIPPING VIEW

    public void clippingView(){
        if (lib.selected.size() != 1){
            mode = Mode.CONTACTSHEET;
            return;
        }
        g.noStroke();
        g.fill(0, 225);
        g.rect(0, 0, t.w, t.h);
        clippingImage(lib.selected.get(0));
    }

    public void clippingImage(Clipping c){
        PVector size = fitImage(c.img, PApplet.constrain(t.w, 10, c.img.width), PApplet.constrain(t.h, 10, c.img.height));
        g.image(c.img, (t.w / 2) - (size.x / 2), (t.h / 2) - (size.y / 2), size.x, size.y);
    }

    public PVector fitImage(PImage img, float w, float h){
        float newW = 0;
        float newH = 0;
        if (img.width >= img.height){
            newW = w;
            newH = (img.height * newW) / img.width;
        }else if (h > w){
            newH = h;
            newW = (img.width * newH) / img.height;
        }
        return new PVector(newW, newH);
    }

    public void followClippingOffscreen(Clipping c, float thumbY, float lat, float sheetY, float sheetH){
        if (c == goTo) {
            goToThumbnail(thumbY, lat, sheetH);
        }
    }

    public void setUpDownSelect(float clipX, int row, float clipW){
        upDownSelect.x = clipX + (clipW / 2);
        upDownSelect.y = row;
    }

    //region DEBUG --------------------------------------------------

    public void debugMouseInput(MouseInput mi) {
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
                        "\nactive: " + activeItem +
                        "\nselected: " + lib.selected +
                        "\nheld: " + heldClippings,
                100, 100);
    }

    public void debugKeyInput(KeyInput ki) {
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

    public void debugCasper(){
        g.textSize(36);
        g.fill(255,0,255);
        if (heldClippings.size() > 0){
            g.text(heldClippings.toString(), 100, 700);
        }
    }
    //endregion
}


//TODO panel
//TODO typing
//TODO tags

// FOR SAFE KEEPING THIS IS "BUTTON" BEHAVIOR
//        if(mouseButton == 0 &&
//            hotItem == id &&
//            activeItem == id){
//            return true;
//        }
//        return false;
