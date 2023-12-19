import java.util.ArrayList;
import java.util.Collections;

public class Library {
    // TODO ermm can't this just extend ArrayList instead of having a reference to one

    ArrayList<Clipping> clippings;
    ArrayList<Tag> tags = new ArrayList<Tag>();

    public Library(){
        clippings = new ArrayList<Clipping>();
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
            if (t.name == tagName.toLowerCase()) return t;
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
        // this just finds strings that look like tags and separates them out.
        ArrayList<String> result = new ArrayList<String>();
        String[] words = string.split(" |\n");
        for (String word : words){
            if (word.charAt(0) == '#'){
                result.add(word.substring(1));
            }
        }
        return result;
    }

    public ArrayList<Tag> parseTags(String string){
        // this separates a string out and returns a list of tags, creating new tags as necessary
        ArrayList<Tag> foundTags = new ArrayList<Tag>();
        ArrayList<String> strings = findTagStrings(string);
        for (String word : strings){
            foundTags.add(stringToTag(word));
        }
        return foundTags;
    }

    public void tagClipping(Clipping clipping, Tag tag){
        clipping.addTag(tag);
    }

    public void tagClipping(Clipping clipping, ArrayList<Tag> tags){
        for (Tag t : tags){
            clipping.addTag(t);
        }
    }
}
