import processing.core.PApplet;
import processing.data.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;

public class TulpaHeart {

    Library library;
    ISelection selection;

    public TulpaHeart(){
        library = new Library(getLibraryJSON());
        selection = new EmptySelection();
    }

    public void createNewLibrary(String desiredPath){
        File root = new File(desiredPath + "/data/");
        if (!root.exists()) {
            root.mkdir();
        }
        JSONObject newLibraryData = new JSONObject();
        newLibraryData.setString("author", "Trazan Apansson");
        newLibraryData.setString("dataPath", desiredPath + "/data/");
        tulpa.SOLE.saveJSONObject(newLibraryData, root.getName() + "librarydata.json");
        library = new Library();
        library.setLibraryData(newLibraryData);
        File tempDir = new File("/home/nico/Pictures/images/");
        ingestFiles(tempDir);
    }

    public Library getLibrary(){
        return library;
    }

    public void saveClippingData(Clipping c) {
        tulpa.SOLE.saveJSONObject(c.data, getDataPath() + "clippings/" + c.getId() + ".json");
    }

    public void deleteClipping(Clipping clipping){
        selection = selection.remove(clipping);
        library.trashClipping(clipping);
    }

    public void deleteSelectedClippings(){
        for (Clipping clipping : selection.getSelectedClippings()){
            library.trashClipping(clipping);
        }
        selection = selection.clear();
    }

    public Clipping stepClipping(Clipping clipping, int step){
        int index = library.indexOf(clipping);
        index = PApplet.constrain(index + step, 0, library.getClippings().size() - 1);
        return library.getClippings().get(index);
    }

    public Clipping stepSelection(int amount){
        // steps the selection forward or back by amount; returns the newly selected clipping if successful
        if (selection.size() != 1) return null;
        Clipping clipping = stepClipping(selection.get(0), amount);
        selection = selection.select(clipping);
        return clipping;
    }

    public void tagClipping(Clipping clipping, String tagName){
        if (!clipping.hasTag(tagName)){
            clipping.data.getJSONArray("tags").append(tagName);
        }
        saveClippingData(clipping);

        library.tagList.addTag(library.libraryData, tagName);
    }

    public void tagClipping(Clipping clipping, ArrayList<String> tags){
        if (tags == null) return;
        for (String t : tags) {
            tagClipping(clipping, t);
        }
    }

    public JSONObject getLibraryJSON(){
        return PApplet.loadJSONObject(new File(getDataPath() + "librarydata.json"));
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
        for (Clipping c : newClippings){
            introduceClipping(c);
        }
    }

    public void introduceClipping(Clipping c){
        Date dateAdded = new Date();
        c.data.setLong("dateAdded", dateAdded.getTime());

        saveClippingData(c);
        library.add(c);
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
