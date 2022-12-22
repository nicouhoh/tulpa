import processing.core.PGraphics;

public class Visipalp {

    tulpa t;
    Library lib;

    int bgColor = 49;

    float gutter = 10;
    float x = gutter;
    float y = gutter;
    float clipSize = 200;

    int nextID;

    int zoomLevel = 7;

    float mouseX;
    float mouseY;
    int mouseButton;

    int LEFT = 37;
    int RIGHT = 39;

    int hotItem = 0;
    int activeItem = 0;

    Visipalp(tulpa t, Library lib){

        this.lib = lib;
        this.t = t;
    }

    // This is the main update method, called every draw frame.
    void showtime(PGraphics g, int mouseInput){
        prepare();
        mInput(t.mouseX, t.mouseY, mouseInput);
        g.background(bgColor);

        contactSheet(g, 0, 0, t.w, t.h, getID());

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

    // CONTACT SHEET
    // makes all the clippings

    void contactSheet(PGraphics g, float sheetX, float sheetY, float sheetW, float sheetH, int id){
        clipSize = (sheetW - ((zoomLevel + 1) * gutter)) / zoomLevel;
        x = sheetX + gutter;
        y = sheetY + gutter;
        for (Clipping c : lib.clippings){
            if (x + clipSize + gutter > sheetW){
                x = gutter;
                y += clipSize + gutter;
            }
            clipping(g, getID(), c, x, y);
            x += clipSize + gutter;
        }
        x = gutter;
        y = gutter;
    }

    // CLIPPINGS

    boolean clipping(PGraphics g, int id, Clipping c, float clipX, float clipY){
        float wid = c.img.width;
        float hei = c.img.height;
        float offX = 0;
        float offY = 0;

        if (wid >= hei){
            wid = clipSize;
            hei = c.img.height / (c.img.width / clipSize);
            offY = (clipSize - hei) / 2;
        } else {
            hei = clipSize;
            wid = c.img.width / (c.img.height / clipSize);
            offX = (clipSize - wid) / 2;
        }

        if (mouseOver(clipX + offX, clipY + offY, wid, hei)){
            hotItem = id;
            if(activeItem == 0 && mouseButton == 1){
                activeItem = id;
            }
        }

        g.image(c.img, clipX + offX, clipY + offY, wid, hei);

        if(activeItem == id){
            drawClippingSelect(g, clipX + offX, clipY + offY, wid, hei);
        }

        return false;
    }

    public void drawClippingSelect(PGraphics g, float x, float y, float w, float h){
        g.stroke(255);
        g.noFill();
        g.rect(x, y, w, h);
    }


    // INPUT

    public boolean mouseOver(float x, float y, float w, float h){
        if (mouseX < x ||
            mouseY < y ||
            mouseX >= x + w ||
            mouseY >= y + h){
            return false;
        }
        return true;
    }

    public void mInput(float mx, float my, int mouseInput){
        mouseX = mx;
        mouseY = my;
        if (mouseInput == 37) mouseButton = 1;
        else if (mouseInput == 39) mouseButton = 2;
        else mouseButton = 0;
    }



}