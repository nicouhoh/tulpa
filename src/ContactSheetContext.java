import processing.core.PConstants;
import drop.DropEvent;

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
        contactSheetView.performUpdate(0, 0, tulpa.SOLE.width, tulpa.SOLE.height);
    }

    @Override
    public void receiveMouseEvent(Mouse mouse, Squeak squeak){
        mouse.interpretSqueak(squeak, contactSheetView);
    }

    @Override
    public void receiveDropEvent(DropEvent e){
        if (e.isImage()){
            controller.heart.library.add(controller.heart.ingestFile(e.file()));
            controller.visipalp.displayAllClippings();
        }
    }

    @Override
    public void backspace(){
        // TODO make us stay in the same spot when deleting
        controller.heart.deleteSelectedClippings();
        controller.visipalp.displayAllClippings();
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
        if (!controller.heart.selectedClippings.isEmpty()) controller.heart.clearSelection();
        else if (contactSheetView.filtered){
            controller.visipalp.displayAllClippings();
            contactSheetView.searchPanel.searchBar.clear();
        }
    }

    @Override
    public void zero(){
        contactSheetView.contactSheet.toggleViewMode();
        contactSheetView.performUpdate(0, 0, tulpa.SOLE.width, tulpa.SOLE.height);
    }

    @Override
    public void plus(){
        contactSheetView.contactSheet.zoom(-1);
        contactSheetView.scroller.updateChildren();
    }

    @Override
    public void minus(){
        contactSheetView.contactSheet.zoom(1);
        contactSheetView.scroller.updateChildren();
    }

    @Override
    public void equals(){
        contactSheetView.contactSheet.zoom(-1);
        contactSheetView.scroller.updateChildren();
    }

    @Override
    public void type(char c){
        switch (c){
            case '.' -> controller.visipalp.update();
            case ' ' -> controller.openExaminer(); // if we hit space, open the clipping
            case PConstants.ESC -> {}
            default -> {
                controller.openExaminer(c); // if we start typing, open it and start typing immediately TODO only really feels right when it's empty. or adding tags?
            }
        }
    }
}
