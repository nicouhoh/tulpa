import processing.core.PGraphics;
import java.util.ArrayList;

import java.io.File;

public class Callosum {

    Library library;
    Cockpit cockpit;
    Field field;
    EventEar ear;
    Operator operator;

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
    public void debugInit(String p){
        File data = new File(path);
        addClippings(data);
    }
}
