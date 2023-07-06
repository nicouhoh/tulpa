import processing.core.*;

import java.util.ArrayList;

import processing.event.KeyEvent;
import processing.event.MouseEvent;

public class Visipalp {

    PGraphics g;

    tulpa t;
    Library lib;

    Mode mode = Mode.CONTACTSHEET;
    Mode lastMode = Mode.CONTACTSHEET;

    int bgColor = 49;

    int nextID;

    // Contact Sheet
        ArrayList<Thumbnail> thumbs = new ArrayList<Thumbnail>();

        float gutter = 10;
        int columns = 5;
        float latitude = 0;
        float foot = 0;
        Clipping goToClipping = null;
        float goToWhere = 0;
        float goToY;
        float selectY;
        float selectScreenY;
        boolean puzzleToggle = false;

        // Puzzle View
        boolean puzzleView = false;
        float puzzleGutter = 2;

        // Arrow Up/Down
        int UDDirection = 0;
        int arrowUDrowNum = -1;
        float arrowUDX = 0;

    // Mouse Input
    MouseEvent me;
    int hotItem = 0;
    int activeItem = 0;

    // Text Boxes
    Text focusText;
    PFont duo;
    PFont sans;
    float minBoxH = 50;
    int cornerRadius = 5;
    int hintColor = 70;
    int bodyTextColor = 225;
    int textBoxMinMargin = 3;

    // Casper
    PVector casperSize = new PVector(0, 0);
    PVector casperOffset = new PVector(0, 0);
    ArrayList<Clipping> heldClippings = new ArrayList<>();

    // Scroller
    int scrollerColor = 0xff1A1A1A;
    int gripColor = 0xff6C6C6C;
    int gripActiveColor = 225;
    int scrollerW = 20;
    int scrollSpeed = 100;
    float scrollGrabY = 0;

    // Clipping View
    int clippingBackgroundAlpha = 235;
    float minClippingSize = 10;
    float clippingViewCushion = 20;
    float clippingViewLatitude = 0;
    float clippingViewFoot = 0;

    // Text Editor
    float textEditorWidth = 1200;
    float textEditorHeight = 1000;
    float textEditorMinHeight = 300;
    float textEditorMargin = 100;
    PVector textEditorCaptionMinMaxW = new PVector(300, 1500);
    int textEditorColor = 30;
    // iA Writer limits line length to 64 characters. food for thought

    // Panel
    Text search = new Text("", "Search for tags");
    boolean panelIsOpen;
    boolean tabProtection; // This is to help keep tab from being inputed to the search bar immediately after opening the panel
    float panelWidth = 300;
    float panelCushion = 20;
    int panelColor = 30;


    Visipalp(tulpa t, PGraphics g, Library lib) {

        this.lib = lib;
        this.g = g;
        this.t = t;
        duo = t.createFont("iAWriterDuoS-Regular.ttf", 32);
        sans = t.createFont("Gill Sans.otf", 32);
        for (Clipping clip : lib.clippings){
            thumbs.add(new Thumbnail(g, getID(), clip, 0, 0, getClipSize(), getClipSize()));
        }

        arrangeContactSheet();
    }

    void rearrange(){
        if (puzzleView) arrangePuzzleSheet();
        else arrangeContactSheet();
    }

    void arrangeContactSheet(){
        for (int i = 0; i < thumbs.size(); i++){
            Thumbnail t = thumbs.get(i);
            t.setSize(getClipSize());
            t.setPos(gutter + ((i % columns ) * getClipSize()) + gutter * (i % columns) + t.offset.x,
                    gutter + (getClipSize() + gutter) * (i / columns) + t.offset.y);
        }
        foot = thumbs.get(thumbs.size() - 1).y + getClipSize() + gutter;
    }

    void arrangePuzzleSheet(){
        ArrayList<ArrayList<Thumbnail>> allRows = new ArrayList<ArrayList<Thumbnail>>();
        allRows.add(new ArrayList<Thumbnail>());
        float rowW = 0;
        int row = 0;

        // resize all images and build rows
        for (int i = 0; i < thumbs.size(); i++){

            Thumbnail t = thumbs.get(i);

            t.resizeByHeight(getClipSize());
            if (rowW + t.w <= getSheetW() - puzzleGutter * (i + 2)){
                allRows.get(row).add(t);
                rowW += t.w;
            } else{
                System.out.println(allRows.get(row));
                allRows.add(new ArrayList<Thumbnail>());
                row++;
                allRows.get(row).add(t);
                rowW = t.w;
            }
        }

        // resize and reposition rows

        float thumbY = puzzleGutter;

        for (ArrayList<Thumbnail> r : allRows){
            rowW = 0;
            for (Thumbnail t : r){
                rowW += t.w;
            }
            float ratio = (getSheetW() - puzzleGutter * (r.size() + 1)) / rowW;
            float thumbX = puzzleGutter;
            for (Thumbnail t : r){
                t.setSize(t.w * ratio, t.h * ratio);
                t.setPos(thumbX, thumbY);
                thumbX += t.w + puzzleGutter;
            }
            thumbY += r.get(0).h + puzzleGutter;
        }

        foot = thumbs.get(thumbs.size() - 1).y + thumbs.get(thumbs.size() - 1).h + puzzleGutter;
    }

    // This is the main update method, called every draw frame.
    void showtime(boolean resize) {
        prepare();

        g.background(bgColor);

        if (resize){
            if (!puzzleView) arrangeContactSheet();
            if (puzzleView) arrangePuzzleSheet();
        }

        latitude = PApplet.constrain(latitude, 0, foot - getSheetH());

        patientContactSheet();

        if ( me == null || me.getAction() == MouseEvent.RELEASE) heldClippings.clear();
        casperLayer();

        if (panelIsOpen) panel();

        if (mode == Mode.CLIPPINGVIEW) clippingVi:while ()ew();
        finish();
    }

    public void casperLayer(){
        if (!heldClippings.isEmpty()){
            casper(heldClippings.get(0), casperSize.x, casperSize.y);
        }
    }

    public void prepare() {
        hotItem = 0;
        nextID = 1;
    }

    public void finish(){
        if (me == null) return;
        if (me.getButton() == 0){
            heldClippings.clear();
            activeItem = 0;
        }else if (me.getButton() == 1 && activeItem == 0) activeItem = -1;
    }

    public int getID() {
        int newID = nextID;
        nextID++;
        return newID;
    }

    public float getClipSize() {
        return (getSheetW() - (gutter * (columns + 1))) / columns;
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

    public float getSheetW() {
        if (panelIsOpen) return t.w - scrollerW - panelWidth;
        else return t.w - scrollerW;
    }

    public void setHotItem(int id){
        hotItem = id;
    }

    public void setActiveItem(int id){
        activeItem = id;
    }

    public void setMode(Mode m){
        lastMode = mode;
        mode = m;
    }
    //endregion
    //region Input

    public boolean mouseOver(float x, float y, float w, float h) {
        return !(me.getX() < x) &&
                !(me.getY() < y) &&
                !(me.getX() >= x + w) &&
                !(me.getY() >= y + h);
    }

    public void receiveMouseInput(MouseEvent e){
        me = e;
        scrollWheel();
    }

    public boolean noMouseButton(MouseEvent e){
        if (me == null) return true;
        if (me != null && me.getButton() == 0) return true;
        return false;
    }

    public void receiveKeyInput(KeyEvent e) {
        if (e.getAction() == 0) return;

        // reroute TYPE events to the type input method
        if (focusText != null){
            if (e.getAction() == KeyEvent.TYPE) typeInput(e);
            return;
        }

        if(e.getAction() != KeyEvent.PRESS) return;

        if (mode == Mode.CONTACTSHEET){
            if (e.getKey() == PConstants.CODED){
                switch (e.getKeyCode()) {
                    case PConstants.RIGHT -> arrowLeftRight(1);
                    case PConstants.LEFT -> arrowLeftRight(-1);
                    case PConstants.UP -> UDDirection = -1;
                    case PConstants.DOWN -> UDDirection = 1;
                }
            }
            else {
                switch (e.getKey()) {
                    case '\b' -> {
                        if (lib.selected.size() > 0) {
                            lib.whackClipping(lib.selected);
                        }
                    }
                    case 'n' ->
                    {lib.newClipping();}
                    case '-' -> {
                        columns++;
                        rearrange();
                    }
                    case '=' -> {
                        columns--;
                        rearrange();
                    }
                    case '0' -> togglePuzzleView();
                    case ' ' -> {
                        if (lib.selected.size() == 1) mode = Mode.CLIPPINGVIEW;
                    }
                    case '\t' -> togglePanel();
                }
            }

        } else if(mode == Mode.CLIPPINGVIEW){
            if (e.getKeyCode() == PConstants.RIGHT) {
                arrowLeftRight(1);
                clippingViewLatitude = 0;
            }
            else if (e.getKeyCode() == PConstants.LEFT) {
                arrowLeftRight(-1);
                clippingViewLatitude = 0;
            }
            else if (e.getKey() == ' ') closeClippingView();
        }
    }

    public void typeInput(KeyEvent e) {
        if (focusText == null) return;

        if (tabProtection && e.getKey() == '\t'){
            tabProtection = false;
            return;
        }

        if (focusText == search){
            if(e.getKey() == PConstants.ENTER) {
                System.out.println("Search for " + search.bodyText);
                unfocusText();
                return;
            }else if (e.getKey() == PConstants.TAB){
                closePanel();
                return;
            }
        }else if(focusText instanceof ClippingText){
            if (e.getKey() == PConstants.ENTER) ((ClippingText)focusText).collectTags(lib);
        }

        focusText.type(e.getKey(), lib);
    }

    private void scrollWheel() {
        if (me == null) return;
        if (me.getAction() != MouseEvent.WHEEL) return;
        if (mode == Mode.CONTACTSHEET) changeLatitude(me.getCount() * scrollSpeed);
        if (mode == Mode.CLIPPINGVIEW) clippingViewLatitude = PApplet.constrain(clippingViewLatitude + me.getCount() * scrollSpeed, 0, clippingViewFoot - getSheetH());
    }

    void patientContactSheet(){

        g.push();
        g.translate(0, -latitude);
        for (Thumbnail t : thumbs){
            if (t.y + t.h < latitude) continue;
            if (t.y > latitude + getSheetH()) break;

            thumbnailMouseInteraction(t);
            t.draw();
            if (lib.isSelected(t.clipping)) t.drawSelect();
        }
        g.pop();

        scroller(getID(), getID(), t.w - scrollerW, 0, scrollerW, getSheetH());
    }

    void thumbnailText(Text text, float clipX, float clipY, float thumbW, float thumbH, float alpha){
        g.noStroke();
        g.fill(textEditorColor, alpha);
        g.rect(clipX, clipY, thumbW, thumbH, cornerRadius);
        g.fill(bodyTextColor, alpha);
        g.textFont(duo);
        g.textSize(24);
        g.textLeading(26);
        g.text(text.bodyText, clipX + 2 * gutter, clipY + 2*gutter, thumbW - 4*gutter, thumbH - 4*gutter);
    }

    void thumbnailText(Text text, float clipX, float clipY, float thumbW, float thumbH) {
        thumbnailText(text, clipX, clipY, thumbW, thumbH, 255);
    }

    void thumbnailMouseInteraction(Thumbnail thumbnail) {
        if (me == null) return;
        if (mode != Mode.CONTACTSHEET) return;

        // MOUSEOVER
        if (mouseOver(thumbnail.x, thumbnail.y - latitude, thumbnail.w, thumbnail.h)) {

            // MOUSE DOWN
            if (mouseDown()) {

                setCasper(new PVector(me.getX() - thumbnail.x, me.getY() - thumbnail.y + latitude), new PVector(thumbnail.w, thumbnail.h));

                // CLICK
                if (!lib.isSelected(thumbnail.clipping) && me.getModifiers() == 0)
                    lib.select(thumbnail.clipping);

                // CTRL CLICK
                else if (me.getModifiers() == MouseEvent.CTRL)
                    lib.addSelect(thumbnail.clipping);
            }

            // DRAG
            else if (me.getAction() == MouseEvent.DRAG && heldClippings.isEmpty())
                grabThumbnail(thumbnail);
        }
    }

    void grabThumbnail(Thumbnail t){
        if(lib.isSelected(t.clipping) && lib.selected.size() > 1)
            setHeldClippings(lib.selected);
        else setHeldClippings(t.clipping);
    }

    // OLD MOUSE INTERACTIOn
//    void thumbnailMouseinteraction(int id, Clipping c, float x, float y, float w, float h) {
//        if (me == null) return;
//        if (mode != Mode.CONTACTSHEET) return;
//
//        // MOUSEOVER
//        if (mouseOver(x, y, w, h)) {
//            setHotItem(id);
//
//            // MOUSE DOWN
//            if (mouseDown()) {
//                setActiveItem(id);
//                setCasper(new PVector(me.getX() - x, me.getY() - y), new PVector(w, h));
//                if (!lib.isSelected(c) && me.getModifiers() == 0) {
//                    lib.select(c);
//                }
//                else if (me.getModifiers() == MouseEvent.CTRL)
//                    lib.addSelect(c);
//            }
//            else if (mouseDrag(id)){
//                dragThumbnail(id, c);
//            }
//        }
//    }

    void setArrowUDTarget(int rowNum, float x){
        arrowUDrowNum = rowNum;
        arrowUDX = x;
    }

    boolean mouseDown(){
        return activeItem == 0 && me.getAction() == MouseEvent.PRESS && me.getButton() == PConstants.LEFT;
    }

    boolean mouseDrag(int id){
       return activeItem == id && me.getAction() == MouseEvent.DRAG && me.getButton() == PConstants.LEFT;
    }


    void setHeldClippings(Clipping c){
        heldClippings.clear();
        heldClippings.add(c);
    }

    void setHeldClippings(ArrayList<Clipping> c){
        heldClippings.clear();
        heldClippings.addAll(c);
    }

    void dropZone(Clipping c, float dropX, float range, float rowY, float rowH, float lat){
        if (heldClippings.isEmpty()) return;
        if (me.getY() < rowY - lat || me.getY() > rowY + rowH - lat) return;
        if (PApplet.dist(me.getX(), me.getY(), dropX, me.getY()) > range)return;
        g.stroke(255, 0, 255);
        g.strokeWeight(2);
        g.line(dropX, rowY, dropX, rowY + rowH);
        g.strokeWeight(1);
        if(me.getAction() == MouseEvent.RELEASE && me.getButton() == PConstants.LEFT){
            lib.moveClipping(heldClippings, c);
            heldClippings.clear();
        }
    }

    public void arrowLeftRight(int amount) {
        if (lib.selected.size() != 1) return;
        Clipping selClip = lib.selected.get(0);
        int index = lib.clippings.indexOf(selClip);
        lib.select(lib.clippings.get(PApplet.constrain(index + amount, 0, lib.clippings.size() - 1)));

        setGoTo(lib.selected.get(0), 0, 0);
    }

    void arrowUpDownCheck(Clipping c, int rowNum, float clipX, PVector offset, float thumbW){
        if (arrowUDrowNum != -1){
            if (rowNum == arrowUDrowNum && clipX <= arrowUDX && clipX + offset.x + thumbW > arrowUDX){
                lib.select(c);
                setGoTo(lib.selected.get(0), 0, 0);
                setArrowUDTarget(-1, 0);
                UDDirection = 0;
            }
        }
        if (UDDirection != 0 && lib.isSelected(c)){
            setArrowUDTarget(rowNum + UDDirection, clipX + offset.x + thumbW/2);
            UDDirection = 0;
        }

    }

    void setGoTo(Clipping clipping, float y, float where){
        goToClipping = clipping;
        goToY = y;
        goToWhere = where;
    }

    PVector sizeThumbnail(Clipping c) {
        float wid, hei;
        if (c.img != null) {
            wid = c.img.width;
            hei = c.img.height;

            if (wid >= hei) {
                wid = getClipSize();
                hei = c.img.height / (c.img.width / getClipSize());
            } else {
                hei = getClipSize();
                wid = c.img.width / (c.img.height / getClipSize());
            }
        } else {
            wid = getClipSize();
            hei = getClipSize();
        }
        return new PVector(wid, hei);
    }

//    public void drawThumbnailSelect(float x, float y, float w, float h, float r) {
//        g.stroke(255);
//        g.strokeWeight(2);
//        g.noFill();
//        g.rect(x, y, w, h, r);
//    }
//
//    public void drawThumbnailSelect(float x, float y, float w, float h) {
//        drawThumbnailSelect(x, y, w, h, 0);
//    }

    public void setLatitude(float y) {
        latitude = y;
    }

    public void changeLatitude(float l) {
        latitude = PApplet.constrain(latitude + l, 0, foot - getSheetH());
    }

    public void followClippingOffscreen(float y, float h) {
        if (y - gutter < latitude) {
            goToLocation(y, gutter);
        } else if (y + h + 2 * gutter > latitude + getSheetH()) {
            goToLocation(y + h, getSheetH() - gutter);
        }
    }

    public void goToLocation(float y, float where) {
        setLatitude(y - where);
        setGoTo(null, 0,0);
    }


    public void zoomIn(){
        columns--;
    }

    public void zoomOut(){
        columns++;
    }
    //endregion
    //region Casper

    void casper(Clipping c, float w, float h){
        if (heldClippings == null) return;
        g.tint(255, 128);
        if (c.img != null) g.image(c.img, me.getX() - casperOffset.x, me.getY() - casperOffset.y, w, h);
        else if (c.text.bodyText != "") thumbnailText(c.text, me.getX() - casperOffset.x, me.getY() - casperOffset.y, w, h, 128);
        g.tint(255);
    }

    void setCasper(PVector offset, PVector size){
        casperOffset = offset;
        casperSize = size;
    }
    //endregion
    //region Scroller

    //TODO grip is slightly wonky if you quickly click on the scroller, outside of the grip. otherwise functional
    public void scroller(int scrollerID, int gripID, float scrollerX, float scrollerY, float scrollerW, float scrollerH) {
        if (me == null) return;

        g.noStroke();
        g.fill(scrollerColor);
        g.rect(scrollerX, scrollerY, scrollerW, scrollerH);

        float gripH = setGripSize(foot, scrollerH);
        float gripY = setGripPos(latitude, foot, scrollerH, gripH);

        if (mouseOver(scrollerX, scrollerY, scrollerW, scrollerH)) {
            //mouse over grip
            if (mouseOver(scrollerX, gripY, scrollerW, gripH)) {
                setHotItem(gripID);
                if (mouseDown()) {
                    setActiveItem(gripID);
                    scrollGrabY = me.getY() - gripY;
                }
            } else{ // mouse over scroller
                setHotItem(scrollerID);
                if (mouseDown()){
                    setHotItem(gripID);
                    setActiveItem(gripID);
                    scrollGrabY = gripH/2;
                }
            }
        }

        if (activeItem == gripID) {
            gripY = PApplet.constrain(me.getY() - scrollGrabY, 0, scrollerH - gripH);
            latitude = gripY / scrollerH * foot;
            if (me.getAction() == MouseEvent.RELEASE && me.getButton() == PConstants.LEFT) activeItem = 0;
        } else gripY = setGripPos(latitude, foot, scrollerH, gripH);

        g.noStroke();
        if (activeItem == gripID) g.fill(gripActiveColor);
        else g.fill(gripColor);
        g.rect(scrollerX, gripY, scrollerW, gripH);
    }

    public float setGripSize(float bottomOfScroll, float scrollerH) {
        return PApplet.constrain(scrollerH / bottomOfScroll * scrollerH, 0, scrollerH);
    }

    public float setGripPos(float scrollValue, float bottomOfScroll, float scrollerH, float gripH) {
        return PApplet.constrain(scrollValue / bottomOfScroll * scrollerH, 0, scrollerH - gripH);
    }

    public void togglePuzzleView() {
//        goToClipping = lib.selected.get(0);
        puzzleToggle = true;
        puzzleView = !puzzleView;
        rearrange();
    }
    //endregion
    //region Clipping View

    public void closeClippingView(){
        mode = Mode.CONTACTSHEET;
        clippingViewLatitude = 0;
    }

    public void clippingView(){
        if (lib.selected.size() != 1){
            closeClippingView();
            return;
        }
        clippingViewBG();
        g.push();
        g.translate(0, -clippingViewLatitude);
        Clipping c = lib.selected.get(0);
        if (c.img != null) clippingViewImage(c);
        else clippingViewText(c);
        g.pop();
    }

    public void clippingViewImage(Clipping c){
        PVector size;
        size = fitImage(c.img, PApplet.constrain(t.w - clippingViewCushion * 2, minClippingSize, c.img.width),
                PApplet.constrain(getSheetH() - clippingViewCushion * 3 - minBoxH, minClippingSize, c.img.height));
        float textH = minBoxH;
        float blockH = size.y + clippingViewCushion + textH;
        float imgX = getSheetX() + getSheetW() / 2 - (size.x / 2);
        float imgY = getSheetY() + getSheetH() / 2 - (blockH / 2);
        g.image(c.img, imgX, imgY, size.x, size.y);

        float eX = PApplet.lerp(imgX, getSheetX() + getSheetW()/2 - textEditorWidth/2, clippingViewLatitude / (clippingViewFoot - getSheetH()) );
        float eY = imgY + size.y + clippingViewCushion;
        float eW = PApplet.lerp(PApplet.constrain(g.textWidth(c.text.bodyText), PApplet.max(textEditorCaptionMinMaxW.x, size.x), textEditorWidth), textEditorWidth, clippingViewLatitude / (clippingViewFoot - getSheetH()) );
        float eH = PApplet.lerp(minBoxH, textEditorHeight, clippingViewLatitude / (clippingViewFoot - getSheetH()) );
        float margin = PApplet.lerp(textBoxMinMargin, textEditorMargin, clippingViewLatitude / (clippingViewFoot - getSheetH()) );
        clippingViewFoot = eY + textEditorHeight + clippingViewCushion;
        float textBoxAlpha = (mouseOver(eX, eY, eW, eH) || focusText == c.text) ? 255 : 0;
        textEditor(c, eX, eY, eW, eH, margin, margin, clippingViewLatitude, PApplet.lerp(textBoxAlpha, 255, clippingViewLatitude / (clippingViewFoot - getSheetH())) );
        if (mouseDown()){
            if (!(mouseOver(imgX, imgY - clippingViewLatitude, size.x, size.y) || mouseOver(eX, eY, eW, eH))) closeClippingView();
        }
    }

    //TODO I'm pretty sure this can actually just be absorbed into the above function eventually
    public void clippingViewText(Clipping c){
        float eW = PApplet.constrain(textEditorWidth, 200, getSheetW() - 2*clippingViewCushion);
        float eH = PApplet.constrain(textEditorHeight, 200, getSheetH() - 2*clippingViewCushion);
        float eX = getSheetX() + getSheetW()/2 - eW/2;
        float eY = getSheetY() + getSheetH()/2 - eH/2;
        clippingViewFoot = eY + textEditorHeight + clippingViewCushion;
        textEditor(c, eX, eY, eW, eH, textEditorMargin, textEditorMargin, clippingViewLatitude, 255);
        if (mouseDown()){
            if (mouseOver(eX, eY, eW, eH)) closeClippingView();
        }
    }

    public void textEditor(Clipping c, float editorX, float editorY, float editorW, float editorH){
        textBox(getID(), c.text, duo, editorX, editorY, editorW, editorH);
    }

    public void textEditor(Clipping c, float editorX, float editorY, float editorW, float editorH, float marginX, float marginY, float cvlatitude, float alpha){
        textBox(getID(), c.text, duo, editorX, editorY, editorW, editorH, marginX, marginY, cvlatitude, alpha);
    }

    public void clippingViewBG(){
        g.noStroke();
        g.fill(0, clippingBackgroundAlpha);
        g.rect(0, 0, t.w, getSheetH());
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
    //endregion
    //region Panel

    public void panel(){
        g.noStroke();
        g.fill(panelColor);
        g.rect(0, 0, panelWidth, t.h);
        textBox(getID(), search, sans, panelCushion, 50, panelWidth - 2*panelCushion, minBoxH);
        tagList(panelCushion, 50 + minBoxH + panelCushion * 2 , 0, 0);
    }

    // TODO fill out an area rather than one to a row
    public void tagList(float x, float y, float w, float h){
        float tagY = y;
        for (Tag t : lib.tags){
            tagLink(t, x, tagY);
            tagY += 35;
        }
    }

    public void tagLink(Tag tag, float x, float y){
        g.fill(bodyTextColor);
        g.textSize(24);
        g.textFont(sans);
        g.text(tag.name, x, y);

    }

    public void openPanel(){
        panelIsOpen = true;
        tabProtection = true;
        setFocusText(search);
    }

    public void closePanel(){
        panelIsOpen = false;
        search.bodyText = "";
        tabProtection = false;
        unfocusText();
    }

    public void togglePanel(){
        if (panelIsOpen) closePanel();
        else openPanel();
    }
    //endregion
    //region Text

    public void textBox(int id, Text text, PFont font, float x, float y, float w, float h){
        textBox(id, text, font, x, y, w, h,  textBoxMinMargin,  textBoxMinMargin, 0, textEditorColor);
    }

    public void textBox(int id, Text text, PFont font,
                        float boxX,  float boxY,  float boxW,  float boxH,
                        float marginX, float marginY, float cvlatitude, float alpha){

        g.noStroke();
        g.fill(textEditorColor, alpha);
        g.rect(boxX, boxY, boxW, boxH, cornerRadius);
        g.textFont(font);
        g.textSize(32);
        g.textLeading(37);

        textBoxMouseInteraction(id, text, boxX, boxY - cvlatitude, boxW, boxH);

        float textX = boxX + marginX;
        float textY = boxY + marginY;
        float textW = boxW - 2*marginX;
        float textH = boxH - 2*marginY;

        // FIXME temporary shitty cursor situation; this will be expunged
        if (focusText == text || text.bodyText != "") {
            String s = focusText == text ? text.bodyText + "|" : text.bodyText;
            g.fill(bodyTextColor);
            g.text(s, textX, textY, textW, textH);
        } else{
            g.fill(hintColor);
            g.text(text.hint, textX, textY, textW, textH);
        }
    }

    public void textCursor(Text text){
    }

    public void textBoxMouseInteraction(int id, Text text, float x, float y, float w, float h) {
        // MOUSEOVER
        if (mouseOver(x, y, w, h)) {
            setHotItem(id);

            // MOUSE DOWN
            if (mouseDown()) {
                setActiveItem(id);
                setFocusText(text);
            }
        }
        else if (me.getButton() == PConstants.LEFT && focusText == text){
            unfocusText();
        }
    }

    public void setFocusText(Text text){
        focusText = text;
    }

    public void unfocusText(){
        if (focusText instanceof ClippingText){
            ((ClippingText)focusText).collectTags(lib);
        }
        focusText = null;
    }
    //endregion

    public void debugKeyInput(KeyEvent e){
        g.fill(255, 255, 255);
        g.textSize(32);
        String keystring = e.getKey() == PConstants.CODED ? "CODED" : "" + e.getKey();
        g.text(keystring + "\n" + e.getModifiers(), 50, 200);
    }
}

//TODO Search

//FIXME clipping view is off center when the panel is open...
//TODO ...which leads us to -- need to make a decision on how the clipping view and the panel work together. Which is on top? What's the hierarchy

//FIXME when arrowing up off the top of the screen clippings pop in at the bottom
//FIXME I noticed funkiness when scrolling down to the bottom in puzzle view and switching back to
//FIXME I think dropzones are brokenish in puzzle mode
//FIXME when I open the program, select a clipping, then open the panel, the search bar doesnt focus automatically

// FOR SAFE KEEPING THIS IS "BUTTON" BEHAVIOR
//        if(mouseButton == 0 &&
//            hotItem == id &&
//            activeItem == id){
//            return true;
//        }
//        return false;
