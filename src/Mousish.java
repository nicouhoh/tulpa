public interface Mousish extends Palpable {

    void mouseDown(Controller controller, Mouse mouse, int mod);

    void buttonPress(Controller controller, int mod);

}