import processing.core.PGraphics;

import java.io.File;

public class Callosum {

    Library library;
    Cockpit cockpit;
    Field field;
    EventEar ear;
    Operator operator;


    public Callosum(){
       bigBang();
    }

    public void update(){
        if (tulpa.SOLE.resized){
            cockpit.cascadeUpdate();
        }
    }

    public void bigBang(){
        library = new Library();
        cockpit = new Cockpit();
        field = cockpit.field;
        ear = new EventEar(cockpit);
        operator = new Operator(this);
        ear.operator = operator;

        debugInit(); // outmoded once we have a persistent library.
    }

    // this is what keeps the universe running
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
