public class ContactSheetContext extends BaseContext implements Context {

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
    public void mouseEvent(Mouse mouse, Squeak squeak){
        mouse.interpretSqueak(squeak, controller.visipalp.contactSheetView);
    }

    @Override
    public void space() {
        controller.changeContext(new ExaminerContext(controller));
    }

    @Override
    public void backspace(){
        controller.heart.deleteSelectedClippings();
        controller.visipalp.refreshContactSheet();
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
    public void esc(){}

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

    }
}
