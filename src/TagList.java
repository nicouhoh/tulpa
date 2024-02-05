import processing.data.JSONArray;
import processing.data.JSONObject;

import java.util.ArrayList;

public class TagList implements ISubject {


    ArrayList<IObserver> observers;
    public TagList(){
        observers = new ArrayList<IObserver>();
    }

    public boolean tagExists(JSONObject data, String tagName){
        String[] tags = getTags(data);
        for (String string : tags){
            if (string.equalsIgnoreCase(tagName)) return true;
        }
        return false;
    }

    public String[] getTags(JSONObject data){
        if (!data.hasKey("tags")) data.setJSONArray("tags", new JSONArray());
        return data.getJSONArray("tags").toStringArray();
    }

    public ArrayList<Clipping> getClippingsTagged(ArrayList<Clipping> clippings, String tag){
        ArrayList<Clipping> basket = new ArrayList<Clipping>();
        for (Clipping berry : clippings) {
            if (berry.hasTag(tag)) basket.add(berry);
        }
        return basket;
    }

    public void addTag(JSONObject data, String tagName){
        if (!tagExists(data, tagName)) data.getJSONArray("tags").append(tagName);
    }

    public void addTag(JSONObject data, ArrayList<String> newTags){
        for (String tag : newTags){
            addTag(data, tag);
        }
    }

    public void addTag(JSONObject data, String[] newTags){
        for (String tag : newTags){
            addTag(data, tag);
        }
    }

    public void removeTag(JSONArray array, String tag){
        for (int i = 0; i < array.size(); i++){
            String jsonTag = array.getString(i);
            if (jsonTag.equalsIgnoreCase(tag)){
                array.remove(i);
            }
        }
    }

    public void purgeEmptyTags(JSONObject data, ArrayList<Clipping> clippings){
        for (String tag : getTags(data)){
            if (count(clippings, tag) == 0) removeTag(data.getJSONArray("tags"), tag);
        }
    }

    public void updateTags(ArrayList<Clipping> clippings, JSONObject libraryData){
        for (Clipping c : clippings){
            addTag(libraryData, c.getTags());
        }
        purgeEmptyTags(libraryData, clippings);
    }

    public int count(ArrayList<Clipping> clippings, String tag){
        int result = 0;
        for (Clipping clipping : clippings){
            if (clipping.hasTag(tag)) result++;
        }
        return result;
    }

    // ----------------------------------------------------

    @Override
    public void registerObserver(IObserver observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserve(IObserver observer) {
        observers.remove(observer);
    }

    @Override
    public void broadcast() {
        for (IObserver observer : observers){
            observer.update();
        }
    }
}