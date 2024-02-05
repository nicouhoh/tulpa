import processing.core.PApplet;
import processing.data.JSONObject;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;

public class Library {

    ArrayList<Clipping> clippings;
    JSONObject libraryData;
    TagList tagList;

    public Library(){
        clippings = new ArrayList<Clipping>();
        tagList = new TagList();
    }

    public Library(JSONObject json){
        setLibraryData(json);
        clippings = new ArrayList<Clipping>();
        tagList = new TagList();
        add(loadClippingDirectory(getClippingsPath()));
        tagList.updateTags(clippings, libraryData);
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

    public void trashClipping(Clipping clipping){
        try {
            Files.createDirectories(Paths.get(getDataPathName() + "trash/images/"));
            Files.createDirectories(Paths.get(getDataPathName() + "trash/clippings/"));
        } catch (IOException e){
            System.out.println("Error creating trash folder");
            throw new RuntimeException(e);
        }

        try {
            Files.move(Paths.get(getDataPathName() + "clippings/" + clipping.getId() + ".json"),
                    Paths.get(getDataPathName() + "trash/clippings/" + clipping.getId() + ".json"));
        } catch (IOException e){
            System.out.println("Error moving clipping to trash folder");
            throw new RuntimeException(e);
        }

        if (clipping.getImagePath() != null) {
            try {
                Path source = Paths.get(clipping.getImagePath());
                Files.move(source,
                        Paths.get(getDataPathName() + "trash/images/" + source.getFileName()));
            } catch (IOException e) {
                System.out.println("Error moving image to trash folder");
                throw new RuntimeException(e);
            }
        }

        remove(clipping);
    }

    public int indexOf(Clipping clipping){
        return clippings.indexOf(clipping);
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

    public void setLibraryData(JSONObject json){
        libraryData = json;
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
}