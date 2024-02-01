import processing.core.PApplet;
import processing.data.JSONObject;

import java.io.File;
import java.lang.reflect.Array;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class TulpaHeart {

    Library library;
    ArrayList<Clipping> selectedClippings = new ArrayList<Clipping>();

    public TulpaHeart(){
        library = new Library(getLibraryJSON());
    }

    public void loadLibrary(){
        File clippingData = new File(getDataPath() + "/clippings/");
        System.out.println("LOADING DATA FROM " + clippingData.getName());
        ArrayList<Clipping> brood = new ArrayList<Clipping>();
        File[] allData = PApplet.listFiles(library.getClippingsPath());
        int num = 0;
        for (File roe : allData){
            System.out.println("Loading clipping #" + num + ": " + roe);
            num++;
            Clipping salmon = new Clipping(PApplet.loadJSONObject(roe));
            brood.add(salmon);
        }
        library = new Library();
        library.add(brood);
        library.add(library.loadClippings(allData));
        library.updateTags();
        library = new Library(getLibraryJSON());
    }

    public void constructLibraryFromPath(String path, String newLibraryPath){
        File data = new File(path);
        library = new Library();
        ingestFiles(data);
    }

    public void createNewLibrary(String desiredPath){
        File root = new File(desiredPath + "/data/");
        if (!root.exists()){
            root.mkdir();
            JSONObject newLibraryData = new JSONObject();
            newLibraryData.setString("author", "Trazan Apansson");
            newLibraryData.setString("dataPath", desiredPath + "/data/");
            tulpa.SOLE.saveJSONObject(newLibraryData, root.getName());
        }
    }

    public Library getLibrary(){
        return library;
    }

    public void saveClippingData(Clipping c) {
        tulpa.SOLE.saveJSONObject(c.data, getDataPath() + "/clippings/" + c.getId() + ".json");
    }

    public void selectClipping(Clipping clipping){
        clearSelection();
        addToSelection(clipping);
    }

    public void clearSelection(){
        for (Clipping c : selectedClippings){
            c.isSelected = false;
        }
        selectedClippings.clear();
    }

    public void addToSelection(Clipping c){
        if (selectedClippings.contains(c)) return;
        c.isSelected = true;
        selectedClippings.add(c);
    }

    public void removeFromSelection(Clipping c){
        if (!selectedClippings.contains(c)) return;
        c.isSelected = false;
        selectedClippings.remove(c);
    }

    public void toggleSelection(Clipping c){
        if (selectedClippings.contains(c)){
            removeFromSelection(c);
        }
        else addToSelection(c);
    }

    public ArrayList<Clipping> getSelectedClippings(){
        return selectedClippings;
    }

    public void deleteClipping(Clipping clipping){
        removeFromSelection(clipping);
        library.remove(clipping);
    }

    public void deleteSelectedClippings(){
        for (Clipping clipping : selectedClippings){
            library.remove(clipping);
        }
        clearSelection();
    }

    public Clipping stepClipping(Clipping clipping, int step){
        int index = library.indexOf(clipping);
        index = PApplet.constrain(index + step, 0, library.clippings.size() - 1);
        return library.clippings.get(index);
    }

    public Clipping stepSelection(int amount){
        // steps the selection forward or back by amount; returns the newly selected clipping if successful
        if (selectedClippings.size() != 1) return null;
        Clipping clipping = stepClipping(selectedClippings.get(0), amount);
        selectClipping(clipping);
        return clipping;
    }

    public void tagClipping(Clipping clipping, String tagName){
        if (!clipping.hasTag(tagName)){
            clipping.data.getJSONArray("tags").append(tagName);
        }
        saveClippingData(clipping);

        library.pigeonholer.addTag(library.libraryData, tagName);
    }

    public void tagClipping(Clipping clipping, ArrayList<String> tags){
        if (tags == null) return;
        for (String t : tags) {
            tagClipping(clipping, t);
        }
    }

    public JSONObject getLibraryJSON(){
        return PApplet.loadJSONObject(new File(getDataPath() + "/librarydata.json"));
    }


    public String getDataPath(){
        return tulpa.SOLE.sketchPath() + "/data/";
    }

    public FileEater createFileEater(File file){
        if (file.isDirectory()) return new DirectoryEater(file);
        else if (isJPG(file)) return new JPGEater(file);
        else if (isText(file)) return new TXTEater(file);
        else return null;
    }

    public void ingestFiles(File file){
        ArrayList<Clipping> newClippings = createFileEater(file).importAsClippings();
        library.add(newClippings);
        for (Clipping c : newClippings){
            saveClippingData(c);
        }
    }

    public boolean extensionIs(File file, String extension){
        return file.getName().toLowerCase().endsWith(extension);
    }

    public boolean isJPG(File file){
        return extensionIs(file, ".jpg");
    }

    public boolean isText(File file){
        return extensionIs(file, ".txt");
    }
}
