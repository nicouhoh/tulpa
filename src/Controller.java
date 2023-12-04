import processing.core.PGraphics;
import processing.event.MouseEvent;
import processing.event.KeyEvent;
import processing.core.PConstants;
import java.util.ArrayList;
import java.util.Collections;

public class Controller implements Keyish {
//    private static Controller uniqueInstance;

    // NOTICE: Controller itself implements Keyish and can recognize keyboard commands

    TulpaHeart heart;
    Visipalp visipalp;

    Mouse mouse;
    Ouija ouija;

    ArrayList<Dropzone> dropZones = new ArrayList<Dropzone>();

    public Controller(TulpaHeart heart, PGraphics g){
        this.heart = heart;
        visipalp = new Visipalp(g, this, heart);
        this.mouse = new Mouse(this);
        this.ouija = new Ouija();
        ouija.setFocus(this);
    }

//    // TODO this might cause problems if we mess with multithreading. nico, check that book to see what to do about it.
//    public static Controller getInstance(TulpaHeart heart, PGraphics g){
//        if (uniqueInstance == null){
//            uniqueInstance = new Controller(heart, g);
//        }
//        return uniqueInstance;
//    }
//
//    public static Controller getInstance(){
//        if (uniqueInstance != null) return uniqueInstance;
//        else return null;
//    }

    public void draw(){
        visipalp.draw();
        mouse.drawHeldItem(visipalp.g);
    }

    public void resizeWindow(){
        visipalp.sycamore.update(0, 0, tulpa.SOLE.width, tulpa.SOLE.height);
    }

    public void receiveMouseEvent(MouseEvent e){
        mouse.interpretSqueak(new Squeak(e, mouse), visipalp.sycamore);
    }

    public void receiveKeyEvent(KeyEvent e){
        if (e.getAction() != KeyEvent.PRESS) return;
        ouija.focus.receiveKey(e);
    }

    @Override
    public void receiveKey(KeyEvent e) {
        System.out.println("Key: " + e.getKey() + " KeyCode: " + e.getKeyCode());

        switch (e.getKey()){

            case PConstants.CODED -> {
                switch(e.getKeyCode()){
                    case PConstants.LEFT -> horizontalStepSelect(-1);
                    case PConstants.RIGHT -> horizontalStepSelect(1);
                    case PConstants.UP -> verticalStepSelect(-1);
                    case PConstants.DOWN -> verticalStepSelect(1);
                }
            }
            case PConstants.BACKSPACE -> {
                heart.deleteSelectedClippings();
                visipalp.refreshContactSheet(); // TODO Is there a way to do this without rebuilding the whole library of thumbnails each time?
            }
            case '0' -> {
                visipalp.contactSheet.toggleViewMode();
                visipalp.update();
            }
            case '.' -> visipalp.update();
            case '-' -> {
                visipalp.contactSheet.zoom(1);
                visipalp.scroller.updateChildren();
                System.out.println("zooming out");
            }
            case '=' -> {
                visipalp.contactSheet.zoom(-1);
                visipalp.scroller.updateChildren();
                System.out.println("zooming in");
            }
        }
    }

    public void selectClipping(Clipping clipping){
        heart.selectClipping(clipping);
    }

    public void toggleSelection(Clipping clipping){
        heart.toggleSelection(clipping);
    }

    public void addSelection(Clipping clipping){
        heart.addToSelection(clipping);
    }

    public void removeSelection(Clipping clipping){
        heart.removeFromSelection(clipping);
    }

    public Thumbnail findThumbnail(Clipping clipping){
        for (Thumbnail thumbnail : visipalp.contactSheet.getThumbnails()){
            if (thumbnail.clipping == clipping) return thumbnail;
        }
        return null;
    }

    public void horizontalStepSelect(int direction){
        if (heart.selectedClippings.size() != 1) return;
        Clipping clip = heart.stepSelection(direction);
        Thumbnail thumb = findThumbnail(clip);
        visipalp.scroller.jumpToOrganelle(thumb, visipalp.contactSheet.getGutter());
    }

    public void verticalStepSelect(int direction){
        if (heart.selectedClippings.size() != 1) return;
        Thumbnail selectedThumb = findThumbnail(heart.selectedClippings.get(0));
        Thumbnail targetThumb = visipalp.verticalStep(selectedThumb, direction);
        heart.selectClipping(targetThumb.clipping);
        visipalp.scroller.jumpToOrganelle(targetThumb, visipalp.contactSheet.getGutter());
    }

    public void rearrangeThumbnails(Thumbnail moving, Thumbnail destination){
        visipalp.contactSheet.rearrangeThumbnails(moving, destination);
        visipalp.update();
    }
}
