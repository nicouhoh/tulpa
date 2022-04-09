import processing.core.PApplet;
import processing.core.PGraphics;
import processing.core.PImage;

import java.io.File;

public class Clipping extends Monad{

    String id;

    PImage img;
    String imgPath;

    String bodyText;

    float displayW;
    float displayH;
    float airW;
    float airH;

    boolean selected;

    public Clipping(File file, String idIn) {
        id = idIn;
        imgPath = file.getAbsolutePath();
        img = tulpa.SOLE.loadImage(imgPath);
    }

    public void draw(PGraphics g){
        g.image(img, x + airW, y + airH, displayW, displayH);
        drawSelection(g);
    }

    public void mouseOver(){
        System.out.println(this);
    }

    public void zoomClipping(PGraphics g, float w, float h, float p){
        float zoomW = 0;
        float zoomH = 0;

        if (img.height >= img.width){
            zoomH = PApplet.constrain(img.height, 10, h - p);
            zoomW = (zoomH / img.height) * img.width;
        }
//        else if (img.width > img.height || zoomW > w - p * 2)
          else {
            zoomW = PApplet.constrain(img.width, 10, w - p);
            zoomH = (zoomW / img.width) * img.height;
        }
        g.image(img, w / 2 - zoomW / 2, h / 2 - zoomH / 2, zoomW, zoomH);
    }

    public void drawSelection(PGraphics g){
        if (selected) {
            g.stroke(tulpa.SOLE.color(255));
            g.noFill();
            g.rect(x + airW, y + airH, displayW, displayH);
        }
    }

    public void setSize(float clipW, float clipH) {
        float wid = img.width;
        float hei = img.height;
        if (img.width >= img.height) {
            wid = clipW;
            hei = img.height / (img.width / clipW);
            airH = (clipH - hei) / 2;
        } else {
            hei = clipH;
            wid = img.width / (img.height / clipH);
            airW = (clipW - wid) / 2;
        }
        displayW = wid;
        w = wid;
        displayH = hei;
        h = hei;
    }

}
