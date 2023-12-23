public class SearchBar extends Skrivsak {

    public SearchBar(){
        super();
        setFont(tulpa.SOLE.getSkrivBordFont());
        setMargin(2);
        this.palimpsest = "WIP search bar";
        buffer = new GapBuffer(50);
        addMousish(this);
    }

    @Override
    public void enter(Controller controller){
        controller.displaySearchResults(buffer.toWords(), buffer.toString());
    }

    @Override
    public void esc(Controller controller){
        controller.changeContext(new ContactSheetContext(controller));
    }
}
