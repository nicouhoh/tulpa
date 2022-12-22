import processing.core.PApplet;
import processing.core.PGraphics;
import processing.event.MouseEvent;

public class Visipalp {

    tulpa t;
    Library lib;
    int bgColor = 49;
    float gutter = 10;
    float x = gutter;
    float y = gutter;
    float clipSize = 200;

    int nextVPID;

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

    void showtime(PGraphics g, int mouseInput){
        mouseX = t.mouseX;
        mouseY = t.mouseY;
        mouseButton = mouseInput;

        g.background(bgColor);

        contactSheet(g, 0, 0, t.w, t.h, getID());
    }

    void contactSheet(PGraphics g, float sheetX, float sheetY, float sheetW, float sheetH, String id){
        clipSize = (sheetW - ((zoomLevel + 1) * gutter)) / zoomLevel;
        x = sheetX + gutter;
        y = sheetY + gutter;
        for (Clipping c : lib.clippings){
            clipping(g, c, x, y, getID());

            if (x + clipSize > t.width - gutter) {
                x = gutter;
                y += clipSize + gutter;
            } else {
                x += clipSize + gutter;
            }
        }
        x = gutter;
        y = gutter;
    }

    boolean clipping(PGraphics g, Clipping c, float clipX, float clipY, String id){
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
//            System.out.println(c.imgPath);
        }

        g.image(c.img, clipX + offX, clipY + offY, wid, hei);

        return false;
    }

    String getID(){
        String newID = PApplet.nf(nextVPID, 5);
        nextVPID++;
        return newID;
    }

    boolean mouseOver(float x, float y, float w, float h){
        if (mouseX < x ||
            mouseY < y ||
            mouseX >= x + w ||
            mouseY >= y + h){
            return false;
        }
        return true;

    }

    public void mouseEvent(MouseEvent e){
        System.out.println(e.getAction());
    }


}