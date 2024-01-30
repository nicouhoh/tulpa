import processing.core.PImage;

import java.io.File;
import java.util.ArrayList;

import processing.core.PApplet; //FIXME TEMPORARY
import processing.data.JSONArray;
import processing.data.JSONObject;

public class Clipping {

    JSONObject data;

    PImage img;
    Passage passage;

    boolean isSelected;

//    ArrayList<Tag> tags = new ArrayList<Tag>();

    public Clipping(){
        initializeMetaData();
    }

    public Clipping(File file) {

        initializeMetaData();
    }

    public Clipping(JSONObject json){
        setData(json);
        loadData();
    }

    public void loadData(){
        if (data.hasKey("imagePath")){
            String path = data.getString("imagePath");
            System.out.println("...loaded image: " + path);
            img = tulpa.SOLE.loadImage(path);
        }
        if (data.hasKey("text")) {
            String text = data.getString("text");
            System.out.println("...loaded text: " + text);
            passage = new Passage(text);
        }
<<<<<<< HEAD
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
            if (t.name.equalsIgnoreCase(gubbe.name.toLowerCase())){
=======
    }

    public boolean hasTag(String s){
        for (String tag : getTags()){
            if (s.equalsIgnoreCase(tag)){
>>>>>>> 63ff47c (i've embarked on a big refactoring expedition. importing directories may be broken right now. from here on better refactoring practices.)
                return true;
            }
        }
        return false;
    }

    public String[] getTags(){
        if (!data.hasKey("tags")) data.setJSONArray("tags", new JSONArray());
        return data.getJSONArray("tags").toStringArray();
    }

    public boolean hasText(){
        return passage != null && !passage.text.isBlank();
    }

    public void setData(JSONObject data){
        this.data = data;
    }

    public JSONObject getData(){
        return data;
    }

    // all the metadata that doesn't involve any external information should go in here. Everything that's an assumed given.
    public void initializeMetaData(){
        JSONObject newData = new JSONObject();
        String date = PApplet.str(PApplet.year()) + PApplet.nf(PApplet.month(), 2) + PApplet.nf(PApplet.day(), 2) + PApplet.nf(PApplet.hour(), 2) + PApplet.nf(PApplet.minute(), 2);
        newData.setString("clippingId", date + System.identityHashCode(this));
        newData.setString("dateCreated", date);
        newData.setString("dateEdited", date);
        newData.setString("author", "Gumby");
        newData.setString("source", "a most reputable source");
        newData.setJSONArray("tags", new JSONArray());
        setData(newData);
    }

    public String getId(){
        return data.getString("clippingId");
    }

    public String getDateCreated(){
        return data.getString("dateCreated");
    }

    public void setDateCreated(String date){
        data.setString("dateCreated", date);
    }

    public String getDateEdited(){
        return data.getString("dateEdited");
    }

    public void setDateEdited(String date){
        data.setString("dateEdited", date);
    }
}