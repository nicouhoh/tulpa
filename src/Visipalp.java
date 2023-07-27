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
        ArrayList<Thumbnail> selectedThumbnails = new ArrayList<Thumbnail>();

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
        float dropRange = 30;
        float dropHeight = 1;

        // Puzzle View
        boolean puzzleView = false;
        float puzzleGutter = 2;

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
    ArrayList<Thumbnail> heldThumbnails = new ArrayList<>();

    // Scroller
    Scroller scroller;
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
        scroller = new Scroller(getID(), getID());
        rearrange();
    }

    void rearrange(){
        if (puzzleView) arrangePuzzleSheet();
        else arrangeContactSheet();
        scroller.update(getSheetW(), getSheetH(), getSheetY(), latitude, foot);
    }

    float getGutter(){
        if (puzzleView) return puzzleGutter;
        else return gutter;
    }

    void arrangeContactSheet(){
        for (int i = 0; i < thumbs.size(); i++){
            Thumbnail t = thumbs.get(i);
            t.setSize(getClipSize(), getClipSize());
            t.setPos(getGutter() + ((i % columns ) * getClipSize()) + getGutter() * (i % columns),
                    getGutter() + (getClipSize() + getGutter()) * (i / columns));
        }
        foot = thumbs.get(thumbs.size() - 1).thumbY + getClipSize() + getGutter();
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
            if (rowW + t.thumbW <= getSheetW() - getGutter() * (i + 2)){
                allRows.get(row).add(t);
                rowW += t.thumbW;
            } else{
                System.out.println(allRows.get(row));
                allRows.add(new ArrayList<Thumbnail>());
                row++;
                allRows.get(row).add(t);
                rowW = t.thumbW;
            }
        }

        // resize and reposition rows

        float thumbY = getGutter();

        for (ArrayList<Thumbnail> r : allRows){
            rowW = 0;
            for (Thumbnail t : r){
                rowW += t.thumbW;
            }
            float ratio = (getSheetW() - getGutter() * (r.size() + 1)) / rowW;
            float thumbX = getGutter();
            for (Thumbnail t : r){
                t.setSize(t.thumbW * ratio, t.thumbH * ratio);
                t.setPos(thumbX, thumbY);
                thumbX += t.thumbW + getGutter();
            }
            thumbY += r.get(0).thumbH + getGutter();
        }

        foot = thumbs.get(thumbs.size() - 1).thumbY + thumbs.get(thumbs.size() - 1).thumbH + getGutter();
    }

    // This is the main update method, called every draw frame.
    void showtime(boolean resize) {
        prepare();

        g.background(bgColor);

        if (resize){
            rearrange();
        }

        latitude = PApplet.constrain(latitude, 0, foot - getSheetH());

        patientContactSheet();

        if (me == null || me.getAction() == MouseEvent.RELEASE) heldThumbnails.clear();
        casperLayer();

        if (panelIsOpen) panel();

        if (mode == Mode.CLIPPINGVIEW) clippingView();
        finish();
    }

    public void casperLayer(){
        if (!heldThumbnails.isEmpty()){
            casper(heldThumbnails.get(0).clipping, casperSize.x, casperSize.y);
        }
    }

    public void prepare() {
        hotItem = 0;
        nextID = 1;
    }

    public void finish(){
        if (me == null) return;
        if (me.getButton() == 0){
            heldThumbnails.clear();
            activeItem = 0;
        }else if (me.getButton() == 1 && activeItem == 0) activeItem = -1;
    }

    public int getID() {
        int newID = nextID;
        nextID++;
        return newID;
    }

    public float getClipSize() {
        return (getSheetW() - getGutter() * (columns + 1)) / columns;
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
        if (activeItem != 0) return;
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
                    case PConstants.UP -> arrowUpDown(-1);
                    case PConstants.DOWN -> arrowUpDown(1);

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
                        zoomOut();
                    }
                    case '=' -> {
                        zoomIn();
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
        float previousThumbnailEdge = getSheetX();
        for (int i = 0; i < thumbs.size(); i++){
        }
        for (int i = 0; i < thumbs.size(); i++){
            Thumbnail t = thumbs.get(i);

            if (t.thumbY + t.thumbH < latitude) continue;
            if (t.thumbY > latitude + getSheetH()) break;

            thumbnailMouseInteraction(t);
            t.draw();
            if (lib.isSelected(t.clipping)) {
                t.drawSelect();
            }

            if (!heldThumbnails.isEmpty()) drawDropZones(t, previousThumbnailEdge);
            previousThumbnailEdge = t.thumbX + t.thumbW;
        }
        g.pop();

        scrollerMouseInteraction();
        scroller.draw(g);
    }

    void drawDropZones(Thumbnail t, float previousThumbnailEdge){
        if (me == null) return;
        if (me.getY() + latitude < t.y || me.getY() + latitude > t.y + t.h) return;
        g.stroke(255);
        g.strokeWeight(2);
        g.noFill();

        float zoneX;
        if (previousThumbnailEdge > t.thumbX) zoneX = (getSheetX() + t.thumbX) / 2; // first thumbnail in row
        else zoneX = (previousThumbnailEdge + t.thumbX) / 2;

        dropZone(t, zoneX, t.y + t.h / 2 - t.h * dropHeight / 2, t.h * dropHeight);

        //very last thumbnail
        if (thumbs.indexOf(t) == thumbs.size() - 1){
            g.line(t.x + t.w + getGutter(), t.y + t.h / 4, t.x + t.w + getGutter(), t.y + t.h * .75f);
            return; // so we don't do two dropzones if the last thumbnail is on the right edge
        }

        // right edge of screen
        if (thumbs.indexOf(t) + 1 < thumbs.size() && t.x + t.w + getGutter() == getSheetW()) {
            Thumbnail nextThumb = thumbs.get(thumbs.indexOf(t) + 1);
            dropZone(nextThumb, (t.thumbX + t.thumbW + getSheetW()) / 2, t.y, t.h);
        }
    }

    public void dropZone(Thumbnail thumbnail, float dropX){
        dropZone(thumbnail, dropX, thumbnail.y, thumbnail.h);
    }

    public void dropZone(Thumbnail thumbnail, float dropX, float dropY, float dropH){
        if (PApplet.abs(me.getX() - dropX) > dropRange) return;
        g.line(dropX, dropY, dropX, dropY + dropH);
    }

    Thumbnail getThumbnailByClipping(Clipping c){
        for (Thumbnail t : thumbs){
            if (c == t.clipping) return t;
        }
        return null;
    }

    void thumbnailText(Text text, float clipX, float clipY, float thumbW, float thumbH, float alpha){
        g.noStroke();
        g.fill(textEditorColor, alpha);
        g.rect(clipX, clipY, thumbW, thumbH, cornerRadius);
        g.fill(bodyTextColor, alpha);
        g.textFont(duo);
        g.textSize(24);
        g.textLeading(26);
        g.text(text.bodyText, clipX + 2 * getGutter(), clipY + 2*getGutter(), thumbW - 4*getGutter(), thumbH - 4*getGutter());
    }

    void thumbnailText(Text text, float clipX, float clipY, float thumbW, float thumbH) {
        thumbnailText(text, clipX, clipY, thumbW, thumbH, 255);
    }

    void mouseInteraction(){

    }

    void thumbnailMouseInteraction(Thumbnail thumbnail) {
        if (me == null) return;
        if (mode != Mode.CONTACTSHEET) return;

        // MOUSEOVER
        if (mouseOver(thumbnail.thumbX, thumbnail.thumbY - latitude, thumbnail.thumbW, thumbnail.thumbH)) {
            setHotItem(thumbnail.id);

            // MOUSE DOWN
            if (mouseDown()) {
                setActiveItem(thumbnail.id);
                setCasper(new PVector(me.getX() - thumbnail.thumbX, me.getY() - thumbnail.thumbY + latitude), new PVector(thumbnail.thumbW, thumbnail.thumbH));

                // CLICK
                if (!lib.isSelected(thumbnail.clipping) && me.getModifiers() == 0)
                    selectThumbnail(thumbnail);

                // CTRL CLICK
                else if (me.getModifiers() == MouseEvent.CTRL)
                    addSelectedThumbnail(thumbnail);
            }

            // DRAG
            else if (me.getAction() == MouseEvent.DRAG && heldThumbnails.isEmpty() && activeItem == thumbnail.id) {
                grabThumbnail(thumbnail);
            }

            //RELEASE
            else if (me.getAction() == MouseEvent.RELEASE && !heldThumbnails.isEmpty()){
                heldThumbnails.clear();
            }

        }
    }

    void grabThumbnail(Thumbnail t){
        //multiple thumbnails selected
        if(lib.isSelected(t.clipping) && lib.selected.size() > 1)
            setHeldThumbnails(selectedThumbnails);
        else setHeldThumbnails(t);
    }

    void arrowUpDown(int direction) {
        if (lib.selected.size() != 1) return;
        Thumbnail start = getThumbnailByClipping(lib.selected.get(0));
        float center = start.thumbX + start.thumbW / 2;
        int current = PApplet.constrain(thumbs.indexOf(start) + direction, 0, thumbs.size());
        while (center < thumbs.get(current).thumbX || center > thumbs.get(current).thumbX + thumbs.get(current).thumbW){
            current = current + direction;
            if (current < 0 || current >= thumbs.size()) return;
        }
        Thumbnail target = thumbs.get(current);
        lib.select(target.clipping);
    }

    boolean mouseDown(){
        return activeItem == 0 && me.getAction() == MouseEvent.PRESS && me.getButton() == PConstants.LEFT;
    }

    void setHeldThumbnails(Thumbnail t){
        heldThumbnails.clear();
        heldThumbnails.add(t);
    }

    void setHeldThumbnails(ArrayList<Thumbnail> t){
        heldThumbnails.clear();
        heldThumbnails.addAll(t);
    }

    public void arrowLeftRight(int amount) {
        if (lib.selected.size() != 1) return;
        Thumbnail selThumb = getThumbnailByClipping(lib.selected.get(0));
        int index = thumbs.indexOf(selThumb);
        lib.select(thumbs.get(PApplet.constrain(index + amount, 0, thumbs.size() - 1)).clipping);

        setGoTo(lib.selected.get(0), 0, 0);
    }

    void setGoTo(Clipping clipping, float y, float where){
        goToClipping = clipping;
        goToY = y;
        goToWhere = where;
    }

    public void setLatitude(float y) {
        latitude = y;
    }

    public void changeLatitude(float l) {
        latitude = PApplet.constrain(latitude + l, 0, foot - getSheetH());
        scroller.update(getSheetW(), getSheetH(), getSheetY(), latitude, foot);
    }

    public void followClippingOffscreen(float y, float h) {
        if (y - getGutter() < latitude) {
            goToLocation(y, getGutter());
        } else if (y + h + 2 * getGutter() > latitude + getSheetH()) {
            goToLocation(y + h, getSheetH() - getGutter());
        }
    }

    public void goToLocation(float y, float where) {
        setLatitude(y - where);
        setGoTo(null, 0,0);
    }


    public void zoomIn(){
        columns--;
        rearrange();
    }

    public void zoomOut(){
        columns++;
        rearrange();
    }

    void casper(Clipping c, float w, float h){
        if (heldThumbnails == null) return;
        g.tint(255, 85);
        if (c.img != null) g.image(c.img, me.getX() - casperOffset.x, me.getY() - casperOffset.y, w, h);
        else if (c.text.bodyText != "") thumbnailText(c.text, me.getX() - casperOffset.x, me.getY() - casperOffset.y, w, h, 128);
        g.tint(255);
    }

    void setCasper(PVector offset, PVector size){
        casperOffset = offset;
        casperSize = size;
    }

    void scrollerMouseInteraction(){
        if (me == null) return;

        if (mouseOver(scroller.x, scroller.y, scroller.w, scroller.h)) {

            //mouse over grip
            if (mouseOver(scroller.x, scroller.gripY, scrollerW, scroller.gripH)) {
                setHotItem(scroller.gripID);
                if (mouseDown()) {
                    setActiveItem(scroller.gripID);
                    scrollGrabY = me.getY() - scroller.gripY;
                }
            // mouse over scroller
            } else{
                setHotItem(scroller.id);
                if (mouseDown()){
                    setHotItem(scroller.gripID);
                    setActiveItem(scroller.gripID);
                    scrollGrabY = scroller.gripH/2;
                }
            }
        }

        if (activeItem == scroller.gripID) {

            scroller.gripY = PApplet.constrain(me.getY() - scroller.grabY, 0, scroller.h - scroller.gripH);
            latitude = scroller.gripY / scroller.h * foot;
            if (me.getAction() == MouseEvent.RELEASE &&me.getButton() == PConstants.LEFT){
                activeItem = 0;
            }
        } else scroller.setGripPos(latitude, foot);
    }


    public void togglePuzzleView() {
//        goToClipping = lib.selected.get(0);
        puzzleToggle = true;
        puzzleView = !puzzleView;
        rearrange();
    }

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

    public void selectThumbnail(Thumbnail t){
        selectedThumbnails.clear();
        selectedThumbnails.add(t);
        lib.select(t.clipping);
    }

    public void addSelectedThumbnail(Thumbnail t){
        selectedThumbnails.add(t);
        lib.addSelect(t.clipping);
    }

    public void clearSelectedThumbnails(){
        selectedThumbnails.clear();
        lib.clearSelection();
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
