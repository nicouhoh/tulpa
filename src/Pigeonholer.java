import processing.data.JSONArray;
import processing.data.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Pigeonholer implements ISubject {

    ArrayList<IObserver> observers;

    public Pigeonholer(){
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