public class Skrivbord extends Skrivsak {

    int margin = 50;
    int minW = 400;
    int maxW = 800;
    int minH =  50;

    public Skrivbord(){
        super();
        setFont(tulpa.SOLE.getSkrivBordFont());
        setMargin(50);
        this.palimpsest = "Click here to type";
        buffer = new GapBuffer(50);
        addMousish(this);
    }

    @Override
    public void esc(Controller controller){
        controller.saveCurrentClippingData();
        controller.changeContext(new ContactSheetContext(controller));
    }
}