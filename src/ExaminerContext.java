public class ExaminerContext extends BaseContext {

    Examiner examiner;

    public ExaminerContext(Controller controller, Examiner examiner){
        super(controller);
        this.examiner = examiner;
    }

    @Override
    public void draw(Visipalp visipalp, Mouse mouse){
        visipalp.draw();
        visipalp.drawClippingView();
        mouse.drawHeldItem(visipalp.g); // FIXME
    }

    @Override
    public void resize(Visipalp visipalp){
        visipalp.contactSheetView.performUpdate(0, 0, tulpa.SOLE.width, tulpa.SOLE.height);
        visipalp.examinerView.performUpdate(0, 0, tulpa.SOLE.width, tulpa.SOLE.height);
    }

    @Override
    public void receiveMouseEvent(Mouse mouse, Squeak squeak){
        mouse.interpretSqueak(squeak, controller.visipalp.examinerView);
    }

    @Override
    public void space() {
        controller.changeContext(new ContactSheetContext(controller));
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
        controller.saveCurrentClippingData();
        controller.changeContext(new ContactSheetContext(controller));
    }
    @Override
    public void type(char c){
        controller.focusSkrivsak(examiner.skrivbord);
        examiner.skrivbord.type(controller, c);
    }
}
