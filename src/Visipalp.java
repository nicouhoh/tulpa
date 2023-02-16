import processing.core.PApplet;
import processing.core.PGraphics;
import processing.core.PVector;
import processing.core.PImage;
import processing.core.PConstants;

import java.util.ArrayList;
import java.util.List;

//import java.awt.event.KeyEvent;
import processing.event.KeyEvent;

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

    Text focusText;

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

    // Arrow Up/Down
    int UDDirection = 0;
    int arrowUDrowNum = -1;
    float arrowUDX = 0;


    // Puzzle View
    boolean puzzleView = false;
    float puzzleGutter = 2;

    // Panel
    Text search = new Text("", "Search for tags");
    boolean panelIsOpen;
    boolean tabProtection; // This is to help keep tab from being inputed to the search bar immediately after opening the panel
    float panelWidth = 300;
    int panelColor = 30;


    Visipalp(tulpa t, PGraphics g, Library lib) {

        this.lib = lib;
        this.g = g;
        this.t = t;
    }

    // This is the main update method, called every draw frame.
    void showtime(MouseInput mi) {
        prepare(mi);

//        readKeyInput(ki);

        if (mi.wheel != 0) {
            changeLatitude(mi.wheel * scrollSpeed);
        }

        g.background(bgColor);

        if (puzzleView) puzzleView(getSheetX(), 0, getSheetWidth(), t.h, latitude, mi);
        else thumbnailView(getSheetX(), 0, t.h, latitude, mi);

        casperLayer(mi);
        if (panelIsOpen) panel();

        if (mode == Mode.CLIPPINGVIEW) clippingView();
        finish(mi);
//        debugMouseInput(mi);
//        debugKeyInput(g, ki);
    }

    public void casperLayer(MouseInput mi){
        if (!heldClippings.isEmpty()){
            casper(heldClippings.get(0), casperSize.x, casperSize.y, mi);
        }
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

    public int getID() {
        int newID = nextID;
        nextID++;
        return newID;
    }

    public float getClipSize() {
        return (getSheetWidth() - (gutter * (columns + 1))) / columns;
    }

    public float getSheetX(){
        if (panelIsOpen) return panelWidth;
        else return 0;
    }

    public float getSheetY(){
        return 0;
    }

    public float getSheetH(){
        return t.h;
    }

    public float getSheetWidth() {
        if (panelIsOpen) return t.w - scrollerW - panelWidth;
        else return t.w - scrollerW;
    }

    public void setHotItem(int id){
        hotItem = id;
    }

    public void setActiveItem(int id){
        activeItem = id;
    }

    // INPUT

    public boolean mouseOver(float x, float y, float w, float h, MouseInput mi) {
        return !(mi.x < x) &&
                !(mi.y < y) &&
                !(mi.x >= x + w) &&
                !(mi.y >= y + h);
    }

    public void receiveKeyInput(KeyEvent e) {
        if (e.getAction() == 0) return;

        System.out.println();
        System.out.println(e.getKey());
        System.out.println(e.getAction());

        if (focusText != null){
            if (e.getAction() == KeyEvent.TYPE) {
                typeInput(e);
            }
            return;
        }

        if(e.getAction() != KeyEvent.PRESS) return;

        if (mode == Mode.CONTACTSHEET){
            if (e.getKey() == PConstants.CODED){
                switch (e.getKeyCode()) {
                    case PConstants.RIGHT -> directionalSelect(1);
                    case PConstants.LEFT -> directionalSelect(-1);
                    case PConstants.UP -> UDDirection = -1;
                    case PConstants.DOWN -> UDDirection = 1;
                }
            }
            else {
                switch (e.getKey()) {
                    case '\b' -> {
                        if (lib.selected.size() > 0) {
                            lib.whackClipping(lib.selected);
                            System.out.println("BACKSPACE");
                        }
                    }
                    case '-' -> columns++;
                    case '=' -> columns--;
                    case '0' -> togglePuzzleView();
                    case ' ' -> {
                        if (lib.selected.size() == 1) mode = Mode.CLIPPINGVIEW;
                    }
                    case '\t' -> togglePanel();
                }
            }

        } else if(mode == Mode.CLIPPINGVIEW){
            if (e.getKeyCode() == PConstants.RIGHT) directionalSelect(1);
            else if (e.getKeyCode() == PConstants.LEFT) directionalSelect(-1);
            else if (e.getKey() == ' ') mode = Mode.CONTACTSHEET;
        }
    }

    public void typeInput(KeyEvent e) {

        if (tabProtection && e.getKey() == '\t'){
            tabProtection = false;
            return;
        }

        switch (e.getKey()) {
            case '\b' -> { if (focusText.text.length() > 0) focusText.text = focusText.text.substring(0, focusText.text.length() - 1); }
            case '\t' -> { if (focusText == search) closePanel(); }
            default -> focusText.text += e.getKey();
        }
    }

    // CONTACT SHEET
    // makes all the clippings

    // FIXME doesn't set foot correctly at all zoom levels

    void thumbnailView(float sheetX, float sheetY, float sheetH, float lat, MouseInput mi) {
        List<Clipping> row;

        g.push();
        g.translate(0, -lat);
        for (int i = 0; i < lib.clippings.size() / columns; i++){
            row = lib.clippings.subList(columns * i, PApplet.constrain((columns * (i+1)) , 0, lib.clippings.size()));
            thumbnailRow(row, i, (((i+1) * gutter) + (i * getClipSize())), sheetX, sheetY, sheetH, mi);
        }
        g.pop();

        scroller(getID(), t.w - scrollerW, 0, scrollerW, sheetH, mi);
    }

    void thumbnailRow(List<Clipping> row, int rowNum, float rowY, float sheetX, float sheetY, float sheetH, MouseInput mi){
        float previousRightEdge = 0;
//        Clipping nextRowClipping = row.get(-1)
        for (int i = 0; i < row.size(); i++){
            Clipping c = row.get(i);
            float clipX = sheetX + ((i + 1) * gutter) + (i * getClipSize());
            PVector size = sizeThumbnail(c);
            PVector offset = findOffset(size.x, size.y);
            clipping(getID(), rowNum, c, clipX, rowY, size.x, size.y, offset, latitude, sheetY, sheetH, previousRightEdge, mi);
            previousRightEdge = ((i + 1) * gutter) + ((i + 1) * getClipSize()) - offset.x;
        }
        dropZone(lib.clippings.get(rowNum * columns + 1), (previousRightEdge + sheetX + getSheetWidth()) / 2, 50, rowY, getClipSize(), latitude, mi);
        //TODO I think I still need to do a special case for the very last clipping in the library
    }


    void clipping(int id, int rowNum, Clipping c, float clipX, float clipY, float thumbW,
                  float thumbH, PVector offset, float lat, float sheetY, float sheetH, float previousRightEdge, MouseInput mi) {

        foot = clipY + thumbH + gutter;
        arrowUpDown(c, rowNum, clipX, offset, thumbW);


        // don't draw or bother with mouse interaction if offscreen
        if (clipY < lat - thumbH) return;
        if (clipY > lat + getSheetH()) return;

        clippingMouseInteraction(id, c, clipX + offset.x,clipY + offset.y - lat, thumbW, thumbH, mi);

        g.image(c.img, clipX + offset.x, clipY + offset.y, thumbW, thumbH);

        if (lib.isSelected(c)) {
            drawClippingSelect(clipX + offset.x, clipY + offset.y, thumbW, thumbH);
        }

        if(heldClippings.isEmpty()) return;
        dropZone(c, (previousRightEdge + clipX + offset.x) / 2, 50, clipY, getClipSize(), lat, mi);
    }

    void clippingMouseInteraction(int id, Clipping c, float x, float y, float w, float h, MouseInput mi) {
        // MOUSEOVER
        if (mouseOver(x, y, w, h, mi)) {
            setHotItem(id);

            // MOUSE DOWN
            if (activeItem == 0 && mi.button == MOUSE1) {
                setActiveItem(id);
                setCasper(new PVector(mi.x - x, mi.y - y), new PVector(w, h));
                if (!lib.isSelected(c) && mi.mod == 0) {
                    lib.select(c);
                }
                else if (mi.mod == 2)
                    lib.addSelect(c);
            }
            return;
        }

        dragClipping(id, c, w, h, mi);
    }

    void dragClipping(int id, Clipping c, float w, float h, MouseInput mi){
        if (activeItem != id) return;
        if (!heldClippings.isEmpty()) return;
        if(lib.isSelected(c) && lib.selected.size() > 1)
            setHeldClippings(lib.selected, w, h, mi);
        else setHeldClippings(c, w, h, mi);
    }

    void setHeldClippings(Clipping c, float w, float h, MouseInput mi){
        heldClippings.clear();
        heldClippings.add(c);
    }

    void setHeldClippings(ArrayList<Clipping> c, float w, float h, MouseInput mi){
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

    void setCasper(PVector offset, PVector size){
        casperOffset = offset;
        casperSize = size;
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

    void arrowUpDown(Clipping c, int rowNum, float clipX, PVector offset, float thumbW){
        if (arrowUDrowNum != -1){
            if (rowNum == arrowUDrowNum && clipX <= arrowUDX && clipX + offset.x + thumbW > arrowUDX){
                lib.select(c);
                goTo = c;
                setArrowUDTarget(-1, 0);
                UDDirection = 0;
            }
        }
        if (UDDirection != 0 && lib.isSelected(c)){
            setArrowUDTarget(rowNum + UDDirection, clipX + offset.x + thumbW/2);
            UDDirection = 0;
        }

    }

    // LATITUDE AND SCROLLING

    public void setLatitude(float y) {
        latitude = y;
    }

    public void changeLatitude(float l) {
        latitude = PApplet.constrain(latitude + l, 0, foot - getSheetH());
    }

    public void goToThumbnail(float thumbY, float lat, float sheetH) {
        if (thumbY - gutter < lat) {
            setLatitude(thumbY - gutter);
        } else if (thumbY + getClipSize() + gutter > lat + sheetH) {
            setLatitude((thumbY + getClipSize() + gutter) - sheetH);
        }
        goTo = null;
    }

    // PUZZLE VIEW

    // i hate this method :(
    //FIXME for some reason the rows don't come out to the same width. Some of them fall short of the sheet width
    //FIXME I don't think this really works with opening and closing the panel -- shrink it like thumbnail view so it doesn't jostle around

    public void puzzleView(float sheetX, float sheetY, float sheetW, float sheetH, float lat, MouseInput mi) {
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
                puzzleRow(sheetX, row, rowNum, ratio, minH, py, sheetY, sheetH, lat, mi);
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
        puzzleRow(sheetX, row, rowNum, ratio, minH, py, sheetY, sheetH, lat, mi);

        g.pop();

        scroller(getID(), t.w - scrollerW, 0, scrollerW, sheetH, mi);
    }

    public void puzzleRow(float sheetX, ArrayList<Clipping> row, int rowNum, float ratio, float minH, float rowY, float sheetY, float sheetH, float lat, MouseInput mi) {
        float px = sheetX + puzzleGutter;
        for (Clipping clip : row) {
            float displayW = ((minH * clip.img.width) / clip.img.height) * ratio;
            float displayH = minH * ratio;
            followClippingOffscreen(clip, rowY, lat, sheetH);
            PVector offset = new PVector(0,0);

            clipping(getID(), rowNum, clip, px, rowY, displayW, displayH, offset, lat, sheetY, sheetH, 0, mi);

            dropZone(clip, px - puzzleGutter/2, 50, rowY, displayH,lat, mi);

            px += displayW + puzzleGutter;
            foot = rowY + displayH + puzzleGutter;
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
        g.rect(0, 0, t.w, getSheetH());
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

    public void followClippingOffscreen(Clipping c, float thumbY, float lat, float sheetH){
        if (c == goTo) {
            goToThumbnail(thumbY, lat, sheetH);
        }
    }

    public void panel(){
        g.noStroke();
        g.fill(panelColor);
        g.rect(0, 0, panelWidth, t.h);
        textBox(search, 20, 50, 260, 40);
    }

    public void textBox(Text text, float x, float y, float w, float h){
        g.stroke(60);
        g.fill(20);
        g.rect(x, y, w, h);
        g.textSize(32);
        if (text.text != "") {
            g.fill(225);
            g.text(text.text, x + 5, y + h - 8);
        }
        else {
            g.fill(80);
            g.text(text.hint, x + 5, y + h - 8);
        }
    }

    public void textBoxMouseInteraction(int id, Text text, float x, float y, float w, float h, MouseInput mi) {
        // MOUSEOVER
        if (mouseOver(x, y, w, h, mi)) {
            setHotItem(id);

            // MOUSE DOWN
            if (activeItem == 0 && mi.button == MOUSE1) {
                setActiveItem(id);
                setFocusText(text);
            }
        }
    }

    public void textBoxTyping(){

    }

    public void setFocusText(Text text){
        focusText = text;
    }

    public void openPanel(){
        panelIsOpen = true;
        tabProtection = true;
        setFocusText(search);
        System.out.println("focusText: " + focusText);
    }

    public void closePanel(){
        panelIsOpen = false;
        tabProtection = false;
        setFocusText(null);
    }

    public void togglePanel(){
        if (panelIsOpen) closePanel();
        else openPanel();
    }

    public void togglePuzzleView(){
        puzzleView = !puzzleView;
        changeLatitude(latitude);
    }

    void setArrowUDTarget(int rowNum, float x){
        arrowUDrowNum = rowNum;
        arrowUDX = x;
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

//TODO redo mouse control
//TODO panel
//TODO typing
//TODO tags

//FIXME I noticed funkiness when scrolling down to the bottom in puzzle view and switching back to

// FOR SAFE KEEPING THIS IS "BUTTON" BEHAVIOR
//        if(mouseButton == 0 &&
//            hotItem == id &&
//            activeItem == id){
//            return true;
//        }
//        return false;
