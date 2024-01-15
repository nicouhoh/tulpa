import processing.core.PApplet;
import processing.data.JSONObject;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;

public class TulpaHeart {

    Library library;
    String path;

    ArrayList<Clipping> selectedClippings = new ArrayList<Clipping>();

    public TulpaHeart(){
        path = tulpa.SOLE.sketchPath() + "/data/";
//        constructLibrary();
        loadLibrary();
    }

    // here we load the library from a folder of clipping jsons. TODO we'll probably also need an overall library JSON
    public void loadLibrary(){
        File clippingData = new File(path + "/clippings/");
        System.out.println("LOADING DATA FROM " + clippingData.getName());
        File[] allData = tulpa.SOLE.listFiles(clippingData);
        ArrayList<Clipping> brood = new ArrayList<Clipping>();
        int num = 0;
        for (File roe : allData){
            System.out.println("Loading clipping #" + num + ": " + roe);
            num++;
            Clipping salmon = new Clipping(PApplet.loadJSONObject(roe));
            brood.add(salmon);
        }
        library = new Library();
        library.add(brood);
    }

    // here's the old way, for testing -- rebuilds the library each time from a folder.
    public void constructLibrary(){
        File data = new File(path);
        library = new Library();
        library.add(ingestDirectory(data));
        int num = 0;
        for (Clipping c : library.clippings){
            String jsonPath = path + "/clippings/" + c.getId() + ".json";
            saveClippingData(c);
        }
    }

    public Clipping createBlankClipping() {
        Clipping larva = new Clipping();
        larva.initializeMetaData();
        return new Clipping();
    }

    public Clipping loadClipping(JSONObject json){
        Clipping result = new Clipping();
        result.setData(json);
        result.loadData();
        return result;
    }

    public Library getLibrary(){
        return library;
    }

    // takes a file, copies it to our data folder, and creates a clipping referring to it.
    // text will be copied directly into the json file.
    public Clipping ingestFile(File file) {
        String filename = file.getName();
        System.out.println("INGESTING FILE: " + filename);
        Path source = Paths.get(file.getAbsolutePath());
        Clipping clip = new Clipping();


        if (filename.toLowerCase().endsWith(".jpg")){
//            Path target = Paths.get(path + "/images/" + filename);
            String targetPath = path + "/images/" + filename;
            String renamedPath = targetPath;

            int num = 0;
            while (new File(renamedPath).isFile()){
                System.out.println("Duplicate filename, renaming");
                num++;
                int lastDotIndex = targetPath.lastIndexOf('.');
                renamedPath = targetPath.substring(0, lastDotIndex) + PApplet.nf(num, 3) + targetPath.substring(lastDotIndex);
            }
            Path path = Paths.get(renamedPath);
            try {
                Files.copy(source, path);
            } catch (IOException e) {
                System.out.println("Error copying file.");
                throw new RuntimeException(e);
            }
            clip.data.setString("imagePath", path.toString());
        }
        else if (filename.toLowerCase().endsWith(".txt")){
            String textPath = file.getAbsolutePath();
            StringBuilder text = new StringBuilder();
            for (String s : tulpa.SOLE.loadStrings(textPath)){
                text.append(s);
                text.append('\n');
            }
            clip.data.setString("text", text.toString());
        }
        saveClippingData(clip);
        clip.loadData();
        return clip;
    }

    public ArrayList<Clipping> ingestDirectory(File dir) {
        System.out.println("INCUBATE DIR");
        File[] files = tulpa.SOLE.listFiles(dir);
        ArrayList<Clipping> brood = new ArrayList<Clipping>();
        if (files != null) {
            for (File file : files) {
                String filename = file.getName();
                if (filename.toLowerCase().endsWith(".jpg") || filename.toLowerCase().endsWith(".txt")) {
                    brood.add(ingestFile(file));
                } else if (file.isDirectory()) {
                    brood.addAll(ingestDirectory(file));
                }
            }
        }
        System.out.println("BROOD: " + brood);
        return brood;
    }

    public void saveClippingData(Clipping c){
        tulpa.SOLE.saveJSONObject(c.data, path + "/clippings/" + c.getId() + ".json");
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
}
