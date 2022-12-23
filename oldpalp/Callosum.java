import processing.core.PApplet;
import processing.core.PGraphics;
import java.util.ArrayList;

import java.io.File;
import java.util.Collections;

public class Callosum {

    Library library;
    Cockpit cockpit;
    Field field;
    Panel panel;
    EventEar ear;
    Operator operator;
    Vision vision;

    String path;

    Scrawler currentText;

    float betweenPillow = 15;

    public Callosum(){
       bigBang();
    }

    public void update(){
//        field.betweenClips = getSurroundingClippings(tulpa.SOLE.mouseX, tulpa.SOLE.mouseY);
        if (tulpa.SOLE.resized){
            cockpit.cascadeUpdate();
        }
    }

    public void bigBang(){
        path = tulpa.SOLE.sketchPath() + "/data/";

        library = new Library();
        cockpit = new Cockpit(this);
        field = cockpit.field;
        panel = cockpit.panel;
        ear = new EventEar(cockpit);
        operator = new Operator(this);
        ear.operator = operator;
        vision = field.vision;

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
        cockpit.cascadeUpdate();
    }

    public void addClippings(File path){
        ArrayList<Clipping> c = library.newClippings(path);
        field.sheet.newSpegels(c);
        cockpit.cascadeUpdate();
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

    public Scrawler getCurrentText(){
        return currentText;
    }

    public void focusText(Scrawler s){
        currentText = s;
        operator.state = State.TEXT;
        if (currentText != null) currentText.setFocused(true);
    }

    public void unfocusText(){
        if(currentText != null){
            currentText.setFocused(false);
            currentText = null;
        }
        operator.state = State.LIBRARY;
    }

    public void toggleBrine(){
        System.out.println("toggling sardine mode");
        // keep selected clippings in roughly the same place
        // TODO someday, make this work on x axis as well figure out a better solution for multiple/no select
        // TODO OR, riddle me this????? if the lone selected clipping is offscreen???? then what???
        if(library.selected.size() > 0){
            Clipping c = library.selected.get(0);

            float screenY = c.spegel.y - field.latitude;
            field.sheet.fishKoan();
            cockpit.cascadeUpdate();
            field.setLatitude(c.spegel.y - screenY);
        }else{
            field.sheet.fishKoan();
        }
        cockpit.cascadeUpdate();
    }

    public void selectLeftRight(int n){
        if (library.selected.size() == 1){
            Clipping selClip = library.selected.get(0);
            ArrayList<Spegel> s = field.sheet.getFilteredSpegels();
            int current = s.indexOf(selClip.spegel);
            if (current + n < 0 || current + n >= s.size()) return;
            library.removeSelect(selClip);
            selClip = s.get(current + n).clipping;
            library.addSelect(selClip);
            field.jumpToSpegel(selClip.spegel);
            field.vision.setClipping(selClip);
            field.vision.setUpVision();
        }
    }

    //----------------
    //----------------
    //----------------
    // TODO I AM HERE. CHANGING SELECTUPDOWNGRID TO BE BASED ON SPEGELS, FOR FILTER PURPOSES
    //----------------
    //----------------
    //-------------

    public void selectUpDownGrid(int columns){
        if(library.selected.size() == 1) {
            ArrayList<Spegel> s = field.sheet.getFilteredSpegels();
            int index = s.indexOf(library.selected.get(0).spegel);
            Clipping c = s.get(PApplet.constrain(index + columns, 0, library.clippings.size() - 1)).clipping;
            library.select(c);
            field.vision.setClipping(c);
        }
    }

    public void selectUpDown(int direction){
        if(library.selected.size() != 1) return;
        if (field.sheet.sardine) library.selectUpDownSardine(direction);
        else selectUpDownGrid(direction * field.sheet.sheetZoom);
        field.jumpToSpegel(library.selected.get(0).spegel);
        field.vision.setClipping(library.selected.get(0));
    }

    // FIXME if you zoom in too far, you'll crash the dang thing
    public void zoom(int z){
        field.zoom(z);
        cockpit.cascadeUpdate();
    }

    public void viewClipping(){
        if (library.selected.size() == 1) {
            operator.changeState(State.CLIPPING);
            Clipping c = library.selected.get(0);
            field.vision.setClipping(c);
            field.vision.setUpVision();
            field.vision.enable();
        }
    }

    public void moveClipping(Clipping c1, Clipping c2){
        int ci = library.clippings.indexOf(c1);
        int c2i = library.clippings.indexOf(c2);
        if (ci < c2i){
            Collections.rotate(library.clippings.subList(ci, c2i), -1);
            Collections.rotate(field.sheet.children.subList(ci, c2i), -1);
        }else if(ci > c2i){
            Collections.rotate(library.clippings.subList(c2i, ci+1), 1);
            Collections.rotate(field.sheet.children.subList(c2i, ci+1), 1);
        }

        cockpit.cascadeUpdate();
    }

    public void exitClippingView(){
        field.vision.disable();
    }



    public Clipping[] getBetweenClippings(float x, float y){
        Clipping[] out = new Clipping[2];
        Spegel rightBoy = null;
        Spegel leftBoy = null;
        Spegel currentBoy = null;
        Spegel nextBoy = null;

        for (Monad m : field.sheet.children){

            // eliminate clippings offscreen or above the mouse
            if(!m.isOnscreen(field.latitude)) continue;
            if (m.y + m.h - field.latitude <= y) continue;
            if (m.y - field.latitude > y) return null; // if we get down past the mouse we're not going to hit it.

            currentBoy = (Spegel)m;
            nextBoy = stepSpegel(currentBoy, 1);

            // between two Spegels
            if (isBetween(x, y, currentBoy, nextBoy, field.latitude)){
               out[0] = currentBoy.clipping;
               out[1] = nextBoy.clipping;
               return out;
            }
            // to the left of the leftmost clipping
            else if (collision(x, y, field.x, currentBoy.x + currentBoy.airW, currentBoy.y - field.latitude, currentBoy.y + currentBoy.h - field.latitude)) {
                rightBoy = currentBoy;
                if (field.sheet.children.indexOf(currentBoy) == 0){ // if it's the very first clipping
                    out[0] = null;
                } else{
                    out[0] = stepSpegel(currentBoy, -1).clipping;
                }
                out[1] = currentBoy.clipping;
                return out;
            }
            // if this is the very last clipping
            else if (stepSpegel(currentBoy, 1) == currentBoy) {
                out[0] = currentBoy.clipping;
                out[1] = null;
                return out;
            }
            // to the right of the rightmost clipping
            else if (currentBoy.y < nextBoy.y){
                out[0] = currentBoy.clipping;
                out[1] = stepSpegel(currentBoy, 1).clipping;
                return out;
            }
        }
        return null;
    }

    public Clipping stepClipping(Clipping c, int step){
        int index = library.clippings.indexOf(c);
        if (index + step > 0 && index + step <= library.clippings.size()){
            return (library.clippings.get(index + step));
        }
        return null;
    }

    public Spegel stepSpegel(Spegel s, int step){
        int index = field.sheet.children.indexOf(s);
        return (Spegel)field.sheet.children.get(PApplet.constrain(index + step, 0, field.sheet.children.size() - 1));
    }

    public boolean isBetween(float x, float y, Spegel left, Spegel right, float latitude){
        return collision(x, y, left.x + left.airW + left.displayW, right.x + right.airW, left.y - latitude, left.y + left.h - latitude);
    }

    public boolean collision(float checkX, float checkY, float leftX, float rightX, float topY, float bottomY){
        return checkX > leftX && checkX < rightX && checkY > topY && checkY < bottomY;
    }

    public void debugInit(String p){
        File data = new File(path);
        addClippings(data);
    }

    public void openPanel(){
        panel.open();
        field.setOffset(panel.panelWidth);
        // field.setSize(cockpit.w - panel.panelWidth, cockpit.h);
        focusText(panel.rum.searchBar);
        operator.state = State.TEXT;
        cockpit.cascadeUpdate();
    }

    public void closePanel(){
        panel.close();
        field.setOffset(0);
        if(getCurrentText() instanceof SearchBar) {
            operator.state = State.LIBRARY;
            unfocusText();
        }
        unfocusText();
        cockpit.cascadeUpdate();
    }

    public void togglePanel(){
        if(panel.isOpen()){
            closePanel();
        }else{
            openPanel();
        }
        cockpit.cascadeUpdate();
    }

    public void search(String s){
        field.sheet.filterSpegels(library.search(s));
        cockpit.cascadeUpdate();
    }

    public Clipping getSelectedClip(){
        if(library.selected.size() == 1) {
            return library.selected.get(0);
        }
        else return null;
    }

    public Graffito getGraffito(){
        return field.vision.graffito;
    }

}