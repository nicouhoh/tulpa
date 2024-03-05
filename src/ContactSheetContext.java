import processing.core.PConstants;
import drop.DropEvent;
import processing.event.MouseEvent;

public class ContactSheetContext extends BaseContext {

    ContactSheetView contactSheetView;

    public ContactSheetContext(Controller controller){
        super(controller);
        this.contactSheetView = controller.visipalp.contactSheetView;
    }

    @Override
    public void draw(Visipalp visipalp, Mouse mouse){
        visipalp.draw();
        mouse.drawHeldItem(controller.visipalp.g);
    }

    @Override
    public void resize(Visipalp visipalp){
        contactSheetView.performResize(0, 0, tulpa.SOLE.width, tulpa.SOLE.height);
    }

    @Override
    public void receiveMouseEvent(Mouse mouse, MouseEvent e){
        mouse.interpretSqueak(e, contactSheetView);
    }

    @Override
    public void receiveDropEvent(DropEvent e){
        controller.heart.ingestFiles(e.file());
        controller.visipalp.displayAllClippingsAndKeepLatitude(controller.heart.library.getClippings());
    }

    @Override
    public void backspace(){
        controller.heart.deleteSelectedClippings();
        controller.visipalp.displayAllClippings(controller.heart.library.getClippings(), controller.visipalp.contactSheetView.scroller.host.getLatitude());
    }

    @Override
    public void up(){
        controller.verticalStepSelect(-1);
    }

    @Override
    public void down(){
        controller.verticalStepSelect(1);
    }

    @Override
    public void left() {
        controller.horizontalStepSelect(-1);
    }

    @Override
    public void right() {
        controller.horizontalStepSelect(1);
    }

    @Override
    public void esc(){
        if (!controller.heart.selection.isEmpty()) controller.heart.selection.clear();
        else if (contactSheetView.filtered){
            controller.visipalp.displayAllClippings(controller.heart.library.getClippings());
            contactSheetView.sidePanel.searchBar.clear();
        }
    }

    @Override
    public void zero(){
        contactSheetView.contactSheet.toggleViewMode();
        contactSheetView.performResize(0, 0, tulpa.SOLE.width, tulpa.SOLE.height);
    }

    @Override
    public void type(char c){
        switch (c){
            case '.' -> controller.visipalp.update();
            case ' ' -> controller.openExaminer(); // if we hit space, open the clipping
            case '#' -> controller.openTagBubble();
            case PConstants.ESC -> {}
            default -> {
                controller.openExaminer(c); // if we start typing, open it and start typing immediately TODO only really feels right when it's empty. or adding tags?
            }
        }
    }

    @Override
    public void ctrlType(char c){
        switch (c){
            case '=', '+' -> {
                contactSheetView.contactSheet.zoom(-1);
                contactSheetView.scroller.resizeChildren();
            }
            case '-' -> {
                contactSheetView.contactSheet.zoom(1);
                contactSheetView.scroller.resizeChildren();
            }
            case 'n', '\u000E' -> {
                controller.heart.library.createEmptyClipping();
                controller.visipalp.displayAllClippingsAndKeepLatitude(controller.heart.library.getClippings());
            }
        }
    }

}
