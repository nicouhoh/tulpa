import processing.core.PImage;

import java.io.File;
import java.util.ArrayList;

public class Clipping {

    String id;

    PImage img;
    String imgPath;

    ClippingText text;

    ArrayList<Tag> tags = new ArrayList<Tag>();

    public Clipping(File file, String idIn) {
        id = idIn;
        imgPath = file.getAbsolutePath();
        img = tulpa.SOLE.loadImage(imgPath);
        text = new ClippingText("", "Type here");
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