public class SkrivsakContext extends BaseContext {

    Skrivsak skrivsak;

    public SkrivsakContext(Controller controller, Skrivsak skrivsak) {
        super(controller);
        this.skrivsak = skrivsak;
    }

    @Override
    public void resize(Visipalp visipalp){
        visipalp.contactSheetView.performResize(0, 0, tulpa.SOLE.width, tulpa.SOLE.height);
        visipalp.examinerView.performResize(0, 0, tulpa.SOLE.width, tulpa.SOLE.height);
    }

    @Override
    public void left() {
        skrivsak.buffer.cursorLeft();
    }

    @Override
    public void right() {
        skrivsak.buffer.cursorRight();
    }

    @Override
    public void esc() {
        skrivsak.esc(controller);
    }

    @Override
    public void enter(){
        skrivsak.enter(controller);
    }

    @Override
    public void type(char c) {
        switch (c){
            case '\b' -> {
                skrivsak.buffer.delete();
            }
            default -> skrivsak.type(controller, c);
        }
    }
}
