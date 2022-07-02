import processing.core.PApplet;
import processing.core.PGraphics;

public class Field extends Monad implements Scrollable, Clickable {

    Scroller scroller;
    ContactSheet sheet;
    Vision vision;
    int scrollW;
    float latitude;
    float foot;
    float sPillow;
    float offset = 0;

    float zoomPillow;

    float scrollAmount = 10;

    Spegel casper;
    float casperX;
    float casperY;

    Clipping[] betweenClips;

    public Field(Cockpit parent){
        this.parent = parent;
        this.parent.children.add(this); // Field is a child of the Cockpit, and its children are the ContactSheet and the Scroller.
        setBounds(parent.x, parent.y, parent.w, parent.h);
        latitude = 0;
        scrollW = 10;
        sheet = new ContactSheet(this);
        scroller = new Scroller(this);
        vision = new Vision(this);
        sPillow = 2;
        zoomPillow = 30;
    }

    @Override
    public void draw(PGraphics g){
        g.background(49);
    }

    public void drawOnTop(PGraphics g){
        drawCasper(g);
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
    public void cascadeDraw(PGraphics g, float latitude){
       super.cascadeDraw(g, latitude);
       drawOnTop(g);
    }

    @Override
    public void update(){
        setBounds(parent.x + offset, parent.y, parent.w - offset, parent.h);
    }

    public void zoom(int z){
        sheet.sheetZoom -= z;
    }

    @Override
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
        if(scroller.grip.inClutches) return;
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
        if(scroller.grip.inClutches) return;
        scroller.updateGrip(latitude, foot);
    }

    public void setFoot(float f){
       foot = f;
       if(scroller.grip.inClutches) return;
       scroller.updateGrip(latitude, foot);
    }

    public void setOffset(float f){
        offset = f;
    }

    // The size of the scroller grip is determined by the contact sheet.
    // The latitude is determined by the position of the grip.
    // contact sheet > grip > latitude.
    public void followScroller(){
        setLatitude(scroller.grip.y / h * foot);
    }

    // TODO down the line: what if, blur whatever's underneath? too fancy?
    public void drawCasper(PGraphics g){
        if (casper != null){
            g.tint(255, 130);
            g.image(casper.clipping.img,
                    casperX,
                    casperY - latitude,
                    casper.displayW, casper.displayH);
            g.tint(255);
        }
        drawBetweener(g, betweenClips);
    }

    public void setCasper(Spegel s){
        casper = s;
    }

    public void setCasperPos(float cx, float cy) {
        casperX = cx;
        casperY = cy;
    }

    public void clearCasper(){
        setCasper(null);
        setBetweenClips(null);
    }

    public void drawBetweener(PGraphics g, Clipping[] chums){
        if (chums != null) {
            Spegel lefto = chums[0] == null ? null : chums[0].spegel;
            Spegel righto = chums[1] == null ? null : chums[1].spegel;
            g.stroke(255, 255, 70);
            if(lefto != null) g.line(lefto.x + lefto.displayW + lefto.airW, lefto.y + lefto.airH - latitude, lefto.x + lefto.displayW + lefto.airW, lefto.y + + lefto.airH + lefto.displayH - latitude);
            if(righto != null) g.line(righto.x + righto.airW, righto.y + righto.airH - latitude, righto.x + righto.airW, righto.y + + righto.airH + righto.displayH - latitude);
            // hahahahahahahahaha ok
        }
    }

    public void setBetweenClips(Clipping[] bc) {
        betweenClips = bc;
    }

    @Override
    public void scroll(Operator operator, int count){
       stepLatitude(count * scrollAmount);
    }

    @Override
    public void hoveredWithGift(Operator operator, int mod, float hoverX, float hoverY, Draggable gift, float lockedX, float lockedY, Callosum c){
        if(gift instanceof Spegel) {
            setBetweenClips(c.getBetweenClippings(hoverX, hoverY)); // ONLY on hoverWithGift
            // for right now we clear betweenClips in Operator under the RELEASED section. is there a better way?
        }
    }

    @Override
    public void offeredGift(Operator operator, int mod, float giftX, float giftY, Draggable gift, Callosum c){
        if (gift instanceof Spegel && betweenClips != null) {
            c.moveClipping(((Spegel) gift).clipping, betweenClips[1]);
        }
    }

}
