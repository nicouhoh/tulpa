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
        search(buffer.toString());
    }

    @Override
    public void esc(Controller controller){
        controller.changeContext(new ContactSheetContext(controller));
    }

    public void search(String string){
        System.out.println("SEARCH: " + string);
    }
}
