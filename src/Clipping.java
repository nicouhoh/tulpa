import processing.core.PApplet;
import processing.core.PGraphics;
import processing.core.PImage;

import javax.swing.event.MouseInputListener;
import java.awt.event.MouseEvent;
import java.io.File;
import java.lang.annotation.Documented;

public class Clipping {

    String id;

    PImage img;
    String imgPath;

    String bodyText;

    boolean selected;

    public Clipping(File file, String idIn) {
        id = idIn;
        imgPath = file.getAbsolutePath();
        img = tulpa.SOLE.loadImage(imgPath);
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





}
