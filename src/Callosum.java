import processing.core.PApplet;
import processing.core.PGraphics;
import java.util.ArrayList;

import java.io.File;

public class Callosum {

    Library library;
    Cockpit cockpit;
    Field field;
    EventEar ear;
    Operator operator;
    ClippingView cv;

    String path;


    public Callosum(){
       bigBang();
    }

    public void update(){
        if (tulpa.SOLE.resized){
            cockpit.cascadeUpdate();
        }
    }

    public void bigBang(){
        path = tulpa.SOLE.sketchPath() + "/data/";

        library = new Library();
        cockpit = new Cockpit();
        field = cockpit.field;
        ear = new EventEar(cockpit);
        operator = new Operator(this);
        ear.operator = operator;
        cv = new ClippingView(field);

        debugInit(path); // outmoded once we have a persistent library.
    }

    // this is what keeps the universe running
    public void showtime(PGraphics g){
        update();
        cockpit.cascadeDraw(g, field.latitude);
    }

    public void addClipping(File file){
        Clipping c = library.newClipping(file);
        field.sheet.newSpegel(c);
    }

    public void addClippings(File path){
        ArrayList<Clipping> c = library.newClippings(path);
        field.sheet.newSpegels(c);
    }

    public void obliterateClipping(Spegel s){
        Clipping c = s.clipping;
        library.whackClipping(c);
        field.sheet.shatterSpegel(s);
    }

    public void obliterateClipping(Clipping c){
        Spegel s = c.spegel;
        library.whackClipping(c);
        field.sheet.shatterSpegel(s);
    }

    public void powerWordKill(){
        if (library.selected.size() > 0){
            for (Clipping c : library.selected){
                obliterateClipping(c);
            }
            cockpit.cascadeUpdate();
        }
    }

    public void toggleBrine(){
        // keep selected clippings in roughly the same place
        // TODO someday, make this work on x axis as well figure out a better solution for multiple/no select
        // TODO OR, riddle me this????? if the one selected clipping is offscreen???? then what???
        if(library.selected.size() > 0){
            Clipping c = library.selected.get(0);

            float screenY = c.spegel.y - field.latitude;
            field.sheet.toggleFishiness();
            cockpit.cascadeUpdate();
            field.setLatitude(c.spegel.y - screenY);
        }else{
            field.sheet.toggleFishiness();
        }
        cockpit.cascadeUpdate();
    }

    public void selectLeftRight(int n){
        if (library.selected.size() == 1){
            int current = library.clippings.indexOf(library.selected.get(0));
            if (current + n < 0 || current + n >= library.clippings.size()) return;
            library.removeSelect(library.selected.get(0));
            library.addSelect(library.clippings.get(current + n));
            cv.setupImage(library.selected.get(0));
        }
    }

    public void selectUpDownGrid(int columns){
        if(library.selected.size() == 1) {
            int index = library.clippings.indexOf(library.selected.get(0));
            Clipping c = library.clippings.get(PApplet.constrain(index + columns, 0, library.clippings.size() - 1));
            library.select(c);
            cv.setupImage(c);
        }
    }

    public void selectUpDown(int direction){
        if(library.selected.size() != 1) return;
        if (field.sheet.sardine) library.selectUpDownSardine(direction);
        else selectUpDownGrid(direction * field.sheet.sheetZoom);
        field.jumpToSpegel(library.selected.get(0).spegel);
        cv.setupImage(library.selected.get(0));
    }

    // FIXME if you zoom in too far, you'll crash the dang thing
    public void zoom(int z){
        field.zoom(z);
        cockpit.cascadeUpdate();
    }

    public void viewClipping(){
        if (library.selected.size() == 1) {
            Clipping c = library.selected.get(0);
            cv.setupImage(c);
            cv.setEnabled(true);
        }
    }

    public void exitClippingView(){
        cv.setEnabled(false);
    }

    public void debugInit(String p){
        File data = new File(path);
        addClippings(data);
    }
}
