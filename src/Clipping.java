import processing.core.PApplet;
import processing.core.PGraphics;
import processing.core.PImage;

import java.io.File;

public class Clipping {

    String id;

    PImage img;
    String imgPath;

    String bodyText;

    Spegel spegel;

    boolean selected;

    public Clipping(File file, String idIn) {
        id = idIn;
        imgPath = file.getAbsolutePath();
        img = tulpa.SOLE.loadImage(imgPath);
    }

    public boolean isSelected(){
        return selected;
    }

    public void setSelected(boolean set){
        selected = set;
    }



}
