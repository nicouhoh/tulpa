import processing.data.JSONArray;
import processing.data.JSONObject;
import processing.data.StringList;

import java.util.List;
import java.util.ArrayList;
import java.util.Comparator;

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
        for (String tag : findDeadTags(data, clippings)){
            if (tag.equalsIgnoreCase("test")){
                System.out.println("Stop!");
            }
            removeTag(data.getJSONArray("tags"), tag);
        }
    }

    public ArrayList<String> findDeadTags(JSONObject data, ArrayList<Clipping> clippings){
        ArrayList<String> result = new ArrayList<>();
        for (String tag : getTags(data)){
            if (tag.equalsIgnoreCase("test")){
                System.out.println("Stop!");
            }
            if (isDead(tag, clippings)){
                result.add(tag);
            }
        }
        return result;
    }

    public boolean isDead(String tag, ArrayList<Clipping> clippings){
        return count(clippings, tag) == 0;
    }

    public void updateTags(ArrayList<Clipping> clippings, JSONObject libraryData){
        for (Clipping c : clippings){
            addTag(libraryData, c.getTags());
        }
        purgeEmptyTags(libraryData, clippings);
        sortTags(libraryData);
    }

    public int count(ArrayList<Clipping> clippings, String tag){
        int result = 0;
        for (Clipping clipping : clippings){
            if (clipping.hasTag(tag)) result++;
        }
        return result;
    }

    public void sortTags(JSONObject data){
        StringList sorted = data.getJSONArray("tags").toStringList();
        sorted.sort();
        data.setJSONArray("tags", new JSONArray(sorted));
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