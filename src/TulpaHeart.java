import processing.core.PApplet;

import java.io.File;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class TulpaHeart {

    Library library;
    String path;

    ArrayList<Clipping> selectedClippings = new ArrayList<Clipping>();

    public TulpaHeart(){
        path = tulpa.SOLE.sketchPath() + "/data/";
        File data = new File(path);
        library = new Library();
        library.add(ingestDirectory(data));
        //TODO we'll register observers here
    }

    public Clipping createClipping() {
        return new Clipping();
    }

    public Library getLibrary(){
        return library;
    }

    public Clipping ingestFile(File file) {
        System.out.println("INGESTING FILE: " + file.getName());
        if (file.getName().contains(".jpg") || file.getName().contains(".txt")) {
            return new Clipping(file);
        }
        else return null;
    }

    public ArrayList<Clipping> ingestDirectory(File dir) {
        System.out.println("INCUBATE DIR");
        File[] files = tulpa.SOLE.listFiles(dir);
        ArrayList<Clipping> brood = new ArrayList<Clipping>();
        if (files != null)
            for (File file : files) {
                if (file.getName().contains(".jpg") || file.getName().contains(".txt")){
                    brood.add(ingestFile(file));
                } else if (file.isDirectory()) {
                    brood.addAll(ingestDirectory(file));
                }
            }
        System.out.println("BROOD: " + brood);
        return brood;
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
        c.isSelected = true;
        selectedClippings.add(c);
    }

    public void removeFromSelection(Clipping c){
        c.isSelected = false;
        selectedClippings.remove(c);
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
