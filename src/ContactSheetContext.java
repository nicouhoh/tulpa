import processing.core.PConstants;

public class ContactSheetContext extends BaseContext {

    public ContactSheetContext(Controller controller){
        super(controller);
    }

    @Override
    public void draw(Visipalp visipalp, Mouse mouse){
        visipalp.draw();
        mouse.drawHeldItem(controller.visipalp.g); // FIXME
    }

    @Override
    public void resize(Visipalp visipalp){
        visipalp.contactSheetView.performUpdate(0, 0, tulpa.SOLE.width, tulpa.SOLE.height);
    }

    @Override
    public void receiveMouseEvent(Mouse mouse, Squeak squeak){
        mouse.interpretSqueak(squeak, controller.visipalp.contactSheetView);
    }

    @Override
    public void backspace(){
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
    }

    @Override
    public void zero(){
        controller.visipalp.contactSheetView.contactSheet.toggleViewMode();
        controller.visipalp.contactSheetView.performUpdate(0, 0, tulpa.SOLE.width, tulpa.SOLE.height);
    }

    @Override
    public void plus(){
        controller.visipalp.contactSheetView.contactSheet.zoom(-1);
        controller.visipalp.contactSheetView.scroller.updateChildren();
    }

    @Override
    public void minus(){
        controller.visipalp.contactSheetView.contactSheet.zoom(1);
        controller.visipalp.contactSheetView.scroller.updateChildren();
    }

    @Override
    public void equals(){
        controller.visipalp.contactSheetView.contactSheet.zoom(-1);
        controller.visipalp.contactSheetView.scroller.updateChildren();
    }

    @Override
    public void type(char c){
        switch (c){
            case ' ' -> controller.openExaminer(); // if we hit space, open the clipping
            case PConstants.ESC -> {}
            default -> {
                controller.openExaminer(c); // if we start typing, open it and start typing immediately TODO only really feels right when it's empty. or adding tags?
            }
        }
    }
}
