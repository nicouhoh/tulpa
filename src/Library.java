import processing.core.PApplet;
<<<<<<< HEAD
=======
import processing.data.JSONArray;
import processing.data.JSONObject;
>>>>>>> 63ff47c (i've embarked on a big refactoring expedition. importing directories may be broken right now. from here on better refactoring practices.)

import java.util.ArrayList;

public class Library {
    // TODO ermm can't this just extend ArrayList instead of having a reference to one

    ArrayList<Clipping> clippings;
<<<<<<< HEAD
    ArrayList<Tag> tags = new ArrayList<Tag>();
=======
    JSONObject libraryData;
    Pigeonholer pigeonholer;
>>>>>>> 63ff47c (i've embarked on a big refactoring expedition. importing directories may be broken right now. from here on better refactoring practices.)

    public Library(){
        clippings = new ArrayList<Clipping>();
        pigeonholer = new Pigeonholer();
    }

    public Library(JSONObject json){
        clippings = new ArrayList<Clipping>();
        pigeonholer = new Pigeonholer();
        loadLibraryData(json);
    }

    public void add(Clipping clipping){
        clippings.add(clipping);
    }

    public void add(ArrayList<Clipping> clips){
        clippings.addAll(clips);
    }

    public void remove(Clipping clipping){
        clippings.remove(clipping);
    }

    public int indexOf(Clipping clipping){
        return clippings.indexOf(clipping);
    }

<<<<<<< HEAD
    public void addTag(Tag newTag){
        if (getTagByName(newTag.name) == null) tags.add(newTag);
    }

    public void addTag(String string){
        if (getTagByName(string) == null) tags.add(new Tag(string));
    }

    public void addTag(ArrayList<Tag> tags){
        for (Tag t : tags){
            addTag(t);
        }
    }

    public Tag getTagByName(String tagName){
        // returns a tag from the library that matches the string, if it exists. otherwise returns null
        for (Tag t : tags){
            if (t.name.equals(tagName.toLowerCase())) return t;
        }
        return null;
    }

    public Tag stringToTag(String string){
        // returns either an existing tag that matches the string or creates one if it doesn't exist in the library
        Tag result = getTagByName(string);
        if (result == null) result = new Tag(string);
        return result;
    }

=======
>>>>>>> 63ff47c (i've embarked on a big refactoring expedition. importing directories may be broken right now. from here on better refactoring practices.)
    public ArrayList<String> findTagStrings(String string){
        if (string.isBlank()) return null;
        // this just finds strings that look like tags and separates them out.
        ArrayList<String> result = new ArrayList<String>();
        String[] words = string.trim().split("\\s+");
        for (String word : words){
            if (isTag(word)){
                result.add(word.substring(1));
            }
        }
        return result;
    }

<<<<<<< HEAD
    public ArrayList<Tag> parseTags(String string){
        // this separates a string out and returns a list of tags, creating new tags as necessary
        ArrayList<Tag> foundTags = new ArrayList<Tag>();
        ArrayList<String> strings = findTagStrings(string);
        for (String word : strings){
            foundTags.add(stringToTag(word));
        }
        return foundTags;
    }

    public ArrayList<Clipping> getClippingsTagged(Tag tag){
        ArrayList<Clipping> basket = new ArrayList<Clipping>();
        for (Clipping berry : clippings){
            if (berry.taggedWith(tag)) basket.add(berry);
=======
    public ArrayList<Clipping> getClippingsWithTag(String tag){
        ArrayList<Clipping> basket = new ArrayList<Clipping>();
        for (Clipping berry : clippings){
            if (berry.hasTag(tag)) basket.add(berry);
>>>>>>> 63ff47c (i've embarked on a big refactoring expedition. importing directories may be broken right now. from here on better refactoring practices.)
        }
        return basket;
    }

<<<<<<< HEAD
    public void tagClipping(Clipping clipping, Tag tag){
        clipping.addTag(tag);
    }

    public void tagClipping(Clipping clipping, ArrayList<Tag> tags){
        for (Tag t : tags){
            clipping.addTag(t);
        }
    }


    public String getID(){
        return "id" + PApplet.year() + PApplet.month() + PApplet.day() + PApplet.hour() + PApplet.minute() + PApplet.second();
    }
=======
    public String getID(){
        return "id" + PApplet.year() + PApplet.month() + PApplet.day() + PApplet.hour() + PApplet.minute() + PApplet.second();
    }

    public void loadLibraryData(JSONObject json){
        libraryData = json;
    }

    public void findNewTags(Clipping c){
        pigeonholer.addTag(libraryData, c.getTags());
    }

    public void updateTags(){
        for (Clipping c : clippings){
            findNewTags(c);
        }
    }

    public boolean isTag(String word){
        return word.charAt(0) == '#';
    }
>>>>>>> 63ff47c (i've embarked on a big refactoring expedition. importing directories may be broken right now. from here on better refactoring practices.)
}
