public class SkrivbordContext extends BaseContext implements Context {

    Skrivbord skrivbord;

    public SkrivbordContext(Controller controller, Skrivbord skrivbord) {
        super(controller);
        this.skrivbord = skrivbord;
    }

    @Override
    public void draw(Visipalp visipalp, Mouse mouse) {
        // FIXME Ok i think eventually i might have to separate the drawing and input contexts
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
    public void mouseEvent(Mouse mouse, Squeak squeak) {

    }

    @Override
    public void space() {

    }

    @Override
    public void backspace() {

    }

    @Override
    public void up() {

    }

    @Override
    public void down() {

    }

    @Override
    public void left() {
        skrivbord.buffer.cursorLeft();
    }

    @Override
    public void right() {
        skrivbord.buffer.cursorRight();
    }

    @Override
    public void esc() {
        controller.saveCurrentClippingData();
        controller.changeContext(new ContactSheetContext(controller));
    }

    @Override
    public void zero() {}

    @Override
    public void plus(){}
    @Override
    public void minus() {}

    @Override
    public void equals() {

    }

    @Override
    public void type(char c) {
        switch (c){
            case '\b' -> {
                skrivbord.buffer.delete();
            }
            case '\u001B' -> {}
            default -> skrivbord.type(c);
        }
    }
}
