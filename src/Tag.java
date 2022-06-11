import java.util.ArrayList;

public class Tag {

    String name;

    Tag parent;
    ArrayList<Tag> subtags;

    public Tag(String name){
        this.name = name;
    }

    public Tag(String name, Tag parent){
        this.name = name;
        this.parent = parent;
    }
}