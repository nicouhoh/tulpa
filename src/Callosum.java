import processing.core.PGraphics;

import java.io.File;

public class Callosum {

    Library library;
    Cockpit cockpit;
    Field field;

    public Callosum(Library library, Cockpit cockpit){
        this.library = library;
        this.cockpit = cockpit;
        this.field = cockpit.field;
    }

    public void update(){
        if (tulpa.SOLE.resized){
            cockpit.cascadeUpdate();
        }
    }

    // we kick it all off here

    public void showtime(PGraphics g){
        update();
        cockpit.cascadeDraw(g, field.latitude);
    }

    public void addClipping(File file){
        Clipping clip = library.incubateFile(file);
        library.addToLibrary(clip);
    }

    public void debugInit(){
        library.debugInit();
        for(Clipping clip : library.clippings){ //TODO How are we actually doing this
            field.sheet.newSpegel(clip);
        }
    }
}
