public class ExaminerViewMode extends BaseMode implements Context {

    public ExaminerViewMode(Controller controller){
        super(controller);
    }

    @Override
    public void draw(Visipalp visipalp, Mouse mouse){
        visipalp.draw();
        visipalp.drawClippingView();
        mouse.drawHeldItem(visipalp.g); // FIXME
    }

    @Override
    public void mouseEvent(Mouse mouse, Squeak squeak){
        mouse.interpretSqueak(squeak, controller.visipalp.examinerView);
    }

    @Override
    public void space() {
        controller.changeMode(new ContactSheetMode(controller));
    }

    @Override
    public void backspace(){}

    @Override
    public void up(){}

    @Override
    public void down(){}

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
        controller.changeMode(new ContactSheetMode(controller));
    }

    @Override
    public void zero(){}

    @Override
    public void minus(){}
    @Override
    public void equals(){}

    @Override
    public void type(char c){
    }
}
