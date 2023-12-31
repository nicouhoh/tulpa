import processing.core.PImage;

import java.io.File;
import java.util.ArrayList;

public class Clipping {

    PImage img;
    String imgPath;

    Passage passage;
    String textPath;


    boolean isSelected;

    ArrayList<Tag> tags = new ArrayList<Tag>();

    public Clipping(){
        imgPath = "";
        img = null;
//        text = new ClippingText("", "Type here");
    }

    public Clipping(File file) {
        if (file.getName().contains(".jpg")) {
            imgPath = file.getAbsolutePath();
            img = tulpa.SOLE.loadImage(imgPath);
//            text = new ClippingText("", "Type here");
        } else if (file.getName().contains(".txt")){
            textPath = file.getAbsolutePath();
            imgPath = "";
            String newText = "";
            for (String s : tulpa.SOLE.loadStrings(textPath)){
                newText += s;
                newText += '\n';
            }
            passage = new Passage(newText);
        }
    }

    public Clipping(String string){
        imgPath = "";
        img = null;
//        text = new ClippingText(string,"Type here");
    }

    public void addTag(Tag t){
        if (!tags.contains(t)) tags.add(t);
    }

    public void addTag(ArrayList<Tag> tags){
        for (Tag t : tags){
            addTag(t);
        }
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

    public boolean hasText(){
        return passage != null && !passage.text.isBlank();
    }
}