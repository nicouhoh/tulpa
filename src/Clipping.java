import processing.core.PApplet;
import processing.core.PGraphics;
import processing.core.PImage;

import java.io.File;
import java.util.ArrayList;

public class Clipping {

    String id;

    PImage img;
    String imgPath;

    String bodyText = "";

    ArrayList<Tag> tags = new ArrayList<Tag>();

    public Clipping(File file, String idIn) {
        id = idIn;
        imgPath = file.getAbsolutePath();
        img = tulpa.SOLE.loadImage(imgPath);
    }

    public boolean isSelected(Library lib) {
        return lib.selected.contains(this);
    }

    public void addTag(Tag t){
        tags.add(t);
    }

    public boolean taggedWith(String s){
        for (Tag t : tags){
            if (t.name.equalsIgnoreCase(s)){
                return true;
            }
        }
        return false;
    }

    public boolean taggedWith(Tag gubbe){
        for (Tag t : tags){
            if (t.name.toLowerCase() == gubbe.name.toLowerCase()){
                return true;
            }
        }
        return false;
    }

    public ArrayList<Tag> getTags(){
        return tags;
    }
}