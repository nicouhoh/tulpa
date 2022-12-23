import processing.core.PApplet;
import processing.core.PGraphics;
import processing.core.PVector;

public class Visipalp {

    tulpa t;
    Library lib;

    int bgColor = 49;

    int nextID;

    float gutter = 10;
    float x = gutter;
    float y = gutter;
    float clipSize = 200;

    int zoomLevel = 7;

    PVector mousePos = new PVector(0, 0);
    int mouseButton;

    int LEFT = 37;
    int RIGHT = 39;

    int hotItem = 0;
    int activeItem = 0;

    // Scroller
    int scrollerColor = 0xff1A1A1A;
    int gripColor = 0xff6C6C6C;
    int gripActiveColor = 225;
    int scrollerW = 20;

    float latitude = 0;
    float foot = 0;
    int scrollSpeed = 20;
    float scrollGrabY = 0;


    Visipalp(tulpa t, Library lib){

        this.lib = lib;
        this.t = t;
    }

    // This is the main update method, called every draw frame.
    void showtime(PGraphics g, int mouseInput, int keycode){
        prepare();

        mInput(t.mouseX, t.mouseY, mouseInput);
        keyInput(keycode);

        g.background(bgColor);
        contactSheet(g, getID(), 0, 0, t.w - scrollerW, t.h, latitude);

        finish();
    }

    // VISIPALP BUSINESS

    public void prepare(){
        hotItem = 0;
    }
    public void finish(){
        if (mouseButton == 0) activeItem = 0;
        else if (activeItem == 0) activeItem = -1; //If the mouse button is still held down, but the button isn't active, we lock the activeItem variable so we don't pick up other elements with the mouse
        nextID = 1;
    }

    public int getID(){
        int newID = nextID;
        nextID++;
        return newID;
    }

    // INPUT

    public boolean mouseOver(float x, float y, float w, float h){
        if (    mousePos.x < x ||
                mousePos.y < y ||
                mousePos.x >= x + w ||
                mousePos.y >= y + h){
            return false;
        }
        return true;
    }

    public void mInput(float mx, float my, int mouseInput){
        mousePos.x = mx;
        mousePos.y = my;
        if (mouseInput == 37) mouseButton = 1;
        else if (mouseInput == 39) mouseButton = 2;
        else mouseButton = 0;
    }

    public void keyInput(int kc){
        if (kc == 38){ // UP
            changeLatitude(-scrollSpeed);
        }
        if (kc == 40){ // DOWN
            changeLatitude(scrollSpeed);
        }
    }

    public void changeLatitude(int l){
        latitude = PApplet.constrain(latitude + l, 0, foot - t.h);
    }


    // CONTACT SHEET
    // makes all the clippings

    void contactSheet(PGraphics g, int id, float sheetX, float sheetY, float sheetW, float sheetH, float lat){
        clipSize = (sheetW - ((zoomLevel + 1) * gutter)) / zoomLevel;
        x = sheetX + gutter;
        y = sheetY + gutter;
        for (Clipping c : lib.clippings){
            if (x + clipSize + gutter > sheetW){
                x = gutter;
                y += clipSize + gutter;
            }
            g.push();
            g.translate(0, -lat);
            clipping(g, getID(), c, x, y, lat);
            g.pop();
            x += clipSize + gutter;
        }
        x = gutter;
        y = gutter;

        scroller(g, getID(), t.w - scrollerW, 0, scrollerW, sheetH);
    }

    // CLIPPINGS

    boolean clipping(PGraphics g, int id, Clipping c, float clipX, float clipY, float lat){

        foot = clipY + clipSize + gutter;

        if(clipY < lat - clipSize) return false;
        if(clipY > lat + t.h) return false;

        PVector size = findDisplaySize(c);
        PVector offset = findOffset(c, size.x, size.y);

        if(mouseOver(clipX + offset.x, clipY + offset.y - lat, size.x, size.y)){
            hotItem = id;
            if(activeItem == 0 && mouseButton == 1){
                activeItem = id;
                lib.select(c);
            }
        }

        g.image(c.img, clipX + offset.x, clipY + offset.y, size.x, size.y);
        if (c.isSelected()) drawClippingSelect(g, clipX + offset.x, clipY + offset.y, size.x, size.y);

        return false;

//        if(mouseButton == 0 &&
//            hotItem == id &&
//            activeItem == id){
//            return true;
//        }
//        return false;
    }

    void checkTemperature(int id, float x, float y, float w, float h){
        if (mouseOver(x, y, w, h)){
            hotItem = id;
            if(activeItem == 0 && mouseButton == 1){
                activeItem = id;
            }
        }
    }

    PVector findDisplaySize(Clipping c) {
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

    public boolean scroller(PGraphics g, int id, float scrollerX, float scrollerY, float scrollerW, float scrollerH){
        g.noStroke();
        g.fill(scrollerColor);
        g.rect(scrollerX, scrollerY, scrollerW, scrollerH);

        grip(g, getID(), scrollerX, scrollerW, scrollerH);

        return false;
    }

    public boolean grip(PGraphics g, int id, float gripX, float gripW, float scrollerH){
        float gripH = setGripSize(foot, scrollerH);
        float gripY;

        if(activeItem == id){
            gripY = PApplet.constrain( mousePos.y - scrollGrabY,0, scrollerH - gripH);
            latitude = gripY / scrollerH * foot;
        }
        else gripY = setGripPos(latitude, foot, scrollerH, gripH);

        g.noStroke();
        if (activeItem == id) g.fill(gripActiveColor);
        else g.fill(gripColor);
        g.rect(gripX, gripY, gripW, gripH);

        if (mouseOver(gripX, gripY, gripW, gripH)){
            hotItem = id;
            if(activeItem == 0 && mouseButton == 1){
                activeItem = id;
                scrollGrabY = mousePos.y - gripY;
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




}