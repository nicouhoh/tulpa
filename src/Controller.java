import processing.core.PGraphics;
import processing.event.MouseEvent;
import processing.event.KeyEvent;
import processing.core.PConstants;
import java.util.ArrayList;

public class Controller implements Keyish {
//    private static Controller uniqueInstance;

    // NOTICE: Controller itself implements Keyish and can recognize keyboard commands (FOR NOW)

    TulpaHeart heart;
    Visipalp visipalp;

    Mouse mouse;

    Context context;
    Context lastContext;

    public Controller(TulpaHeart heart, PGraphics g){
        this.heart = heart;
        visipalp = new Visipalp(g, this, heart);
        this.mouse = new Mouse(this);
        context = new ContactSheetContext(this);
    }

    public void draw(){
        context.draw(visipalp, mouse);
    }

    public void resizeWindow(){
        visipalp.update();
        context.resize(visipalp);
    }

    public void receiveMouseEvent(MouseEvent e){
        context.mouseEvent(mouse, new Squeak(e));
    }

    public void receiveKeyEvent(KeyEvent e){
        switch (e.getAction()){
            case KeyEvent.PRESS -> receiveKey(e);
            case KeyEvent.TYPE -> receiveType(e.getKey());
        }
    }

    @Override
    public void receiveKey(KeyEvent e) {
        System.out.println("Key: " + e.getKey() + " KeyCode: " + e.getKeyCode());

        switch (e.getKey()){

            case PConstants.CODED -> {
                switch(e.getKeyCode()){
                    case PConstants.LEFT -> context.left();
                    case PConstants.RIGHT -> context.right();
                    case PConstants.UP -> context.up();
                    case PConstants.DOWN -> context.down();
                }
            }
            case PConstants.BACKSPACE -> context.backspace();
            case '0' -> context.zero();
            case '.' -> visipalp.update();
            case '-' -> context.minus();
            case '=' -> context.equals();
            case ' ' -> context.space();
            case PConstants.ESC -> context.esc();
        }
    }

    public void receiveType(char c){
        context.type(c);
    }

    public void selectClipping(Clipping clipping){
        heart.selectClipping(clipping);
        setUpClippingView();
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
        for (Thumbnail thumbnail : visipalp.contactSheetView.contactSheet.getThumbnails()){
            if (thumbnail.clipping == clipping) return thumbnail;
        }
        return null;
    }

    public void horizontalStepSelect(int direction){
        if (heart.selectedClippings.size() != 1) return;
        Clipping clip = heart.stepSelection(direction);
        Thumbnail thumb = findThumbnail(clip);
        visipalp.contactSheetView.scroller.jumpToOrganelle(thumb, visipalp.contactSheetView.contactSheet.getGutter());
        setUpClippingView();
    }

    public void verticalStepSelect(int direction){
        if (heart.selectedClippings.size() != 1) return;
        Thumbnail selectedThumb = findThumbnail(heart.selectedClippings.get(0));
        Thumbnail targetThumb = visipalp.verticalStep(selectedThumb, direction);
        heart.selectClipping(targetThumb.clipping);
        visipalp.contactSheetView.scroller.jumpToOrganelle(targetThumb, visipalp.contactSheetView.contactSheet.getGutter());
        visipalp.examinerView.examiner.setClipping(targetThumb.clipping);
        setUpClippingView();
    }

    public void rearrangeThumbnails(Thumbnail moving, Thumbnail destination){
        visipalp.contactSheetView.contactSheet.rearrangeThumbnails(moving, destination);
        visipalp.update();
    }

    public void changeMode(Context newMode){
        lastContext = context;
        context = newMode;
        visipalp.update();
    }

    public void setUpClippingView(){
        visipalp.examinerView.examiner.setClipping(heart.getSelectedClippings().get(0));
        visipalp.update();
    }

    public void saveCurrentClippingData(){
        String string = visipalp.examinerView.examiner.skrivbord.buffer.toString();
        Clipping clipping = visipalp.examinerView.examiner.clipping;
        ArrayList<Tag> tags = heart.library.parseTags(string);
        heart.library.addTag(tags);
        clipping.addTag(tags);
        clipping.passage = new Passage(string);
    }

}
