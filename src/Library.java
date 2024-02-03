import processing.core.PApplet;
import processing.data.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Library {

    ArrayList<Clipping> clippings;
    ArrayList<Tag> tags = new ArrayList<Tag>();
    JSONObject libraryData;
    Pigeonholer pigeonholer;

    private static int idCounter = 0;

    public Library(){
        clippings = new ArrayList<Clipping>();
        pigeonholer = new Pigeonholer();
    }

    public Library(JSONObject json){
        setLibraryData(json);
        clippings = new ArrayList<Clipping>();
        pigeonholer = new Pigeonholer();
        add(loadClippingDirectory(getClippingsPath()));
        updateTags();
    }

    public ArrayList<Clipping> loadClippings(File[] jsons){
        ArrayList<Clipping> brood = new ArrayList<Clipping>();
        int num = 0;
        for (File roe : jsons){
            System.out.println("Loading clipping #" + num++ + ": " + roe);
            brood.add(new Clipping(PApplet.loadJSONObject(roe)));
        }
        return brood;
    }

    public ArrayList<Clipping> loadClippingDirectory(File clippingPath){
        return loadClippings(PApplet.listFiles(clippingPath));
    }

    public void add(Clipping clipping){
        clippings.add(clipping);
        clipping.loadData(); // FIXME temporary
    }

    public void add(ArrayList<Clipping> clips){
        clippings.addAll(clips);
        // FIXME temporary
        for (Clipping c : clips){
            c.loadData();
        }
    }

    public void createEmptyClipping(){
        Clipping beautifulBaby = new Clipping();
        add(beautifulBaby);
    }

    public void remove(Clipping clipping){
        clippings.remove(clipping);
    }

    public int indexOf(Clipping clipping){
        return clippings.indexOf(clipping);
    }

    public void addTag(String string){
        if (getTagByName(string) == null) tags.add(new Tag(string));
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

    public ArrayList<Clipping> getClippingsTagged(String tag) {
        ArrayList<Clipping> basket = new ArrayList<Clipping>();
        for (Clipping berry : clippings) {
            if (berry.taggedWith(tag)) basket.add(berry);
        }
        return basket;
    }

    public ArrayList<Clipping> getClippingsWithTag(String tag){
        ArrayList<Clipping> basket = new ArrayList<Clipping>();
        for (Clipping berry : clippings){
            if (berry.hasTag(tag)) basket.add(berry);
        }
        return basket;
    }

    public void tagClipping(Clipping clipping, String tag){
        clipping.addTag(tag);
    }

    public void tagClipping(Clipping clipping, ArrayList<String> tags){
        for (String t : tags){
            clipping.addTag(t);
        }
    }

    public void loadLibraryData(JSONObject json){

    }

    public void setLibraryData(JSONObject json){
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

    public String getDataPathName(){
        return libraryData.getString("dataPath");
    }

    public File getDataPath(){
        return new File(getDataPathName());
    }

    public File getClippingsPath(){
        File clippingsDir = new File(getDataPathName() + "/clippings/");
        if (!clippingsDir.exists()){
            clippingsDir.mkdir();
        }
        return clippingsDir;
    }

    public ArrayList<Clipping> getClippings(){
        clippings.sort(idComparator());
        return clippings;
    }

    public ArrayList<Clipping> getSortedClippings(){
        ArrayList<Clipping> result = getClippings();
        result.sort(idComparator());
        return result;
    }

    public void sortClippings(){
        clippings.sort(idComparator());
    }

    public File getImagesPath(){
        return new File(getDataPathName() + "/images/");
    }

    public Comparator<Clipping> idComparator(){
        return new Comparator<Clipping>(){
            public int compare(Clipping i, Clipping j){
                if (i.getDateAdded().compareTo(j.getDateAdded()) > 0){
                    return -1;
                }
                else return 1;
            }
        };
    }

    public static synchronized String createID(String date){
        return date + PApplet.nf(idCounter, 4);
    }
}