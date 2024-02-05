import processing.core.PGraphics;
import processing.event.MouseEvent;
import processing.event.KeyEvent;
import drop.DropEvent;

import java.util.ArrayList;

public class Controller {

    // NOTICE: Controller itself implements Keyish and can recognize keyboard commands (FOR NOW)

    TulpaHeart heart;
    Visipalp visipalp;

    Mouse mouse;

    BaseContext context;

    public Controller(TulpaHeart heart, PGraphics g){
        this.heart = heart;
        visipalp = new Visipalp(g, this, heart);
        updateTagList(visipalp.g);
        this.mouse = new Mouse(this);
        context = new ContactSheetContext(this);
    }

    public void draw(){
        context.draw(visipalp, mouse);
    }

    public void resizeWindow(){
        visipalp.update();
        context.resize(visipalp);
    }

    public void receiveMouseEvent(MouseEvent e){
        context.checkForUnfocus(mouse, new Squeak(e));
    }

    public void receiveKeyEvent(KeyEvent e){
        context.receiveKeyEvent(e);
    }

    public void receiveDropEvent(DropEvent e){
        context.receiveDropEvent(e);
    }

    public void selectClipping(Clipping clipping){
        heart.selection.select(clipping);
        setUpClippingView();
    }

    public void addSelection(Clipping clipping){
        heart.selection.add(clipping);
    }

    public void removeSelection(Clipping clipping){
        heart.selection.remove(clipping);
    }

    public Thumbnail findThumbnail(Clipping clipping){
        for (Thumbnail thumbnail : visipalp.contactSheetView.contactSheet.getThumbnails()){
            if (thumbnail.clipping == clipping) return thumbnail;
        }
        return null;
    }

    public void horizontalStepSelect(int direction){
        if (heart.selection.size() != 1) return;
        Clipping clip = heart.stepSelection(direction);
        Thumbnail thumb = findThumbnail(clip);
        visipalp.contactSheetView.scroller.jumpToOrganelle(thumb, visipalp.contactSheetView.contactSheet.getGutter());
        setUpClippingView();
    }

    public void verticalStepSelect(int direction){
        if (heart.selection.size() != 1) return;
        Thumbnail selectedThumb = findThumbnail(heart.selection.get(0));
        Thumbnail targetThumb = visipalp.verticalStep(selectedThumb, direction);
        heart.selection.select(targetThumb.clipping);
        visipalp.contactSheetView.scroller.jumpToOrganelle(targetThumb, visipalp.contactSheetView.contactSheet.getGutter());
        visipalp.examinerView.setup(targetThumb.clipping);
        setUpClippingView();
    }

    public void rearrangeThumbnails(Thumbnail moving, Thumbnail destination){
        visipalp.contactSheetView.contactSheet.rearrangeThumbnails(moving, destination);
        visipalp.update();
    }

    public void changeContext(BaseContext newContext){
        context.exitContext();
        context = newContext;
        visipalp.update();
    }

    public void openExaminer(){
        if(heart.selection.size() != 1) return;
        changeContext(new ExaminerContext(this, visipalp.examinerView.examiner));
    }

    public void openExaminer(char c){
        if(heart.selection.size() != 1) return;
        changeContext(new ExaminerContext(this, visipalp.examinerView.examiner));
        context.type(c);
    }

    public void focusSkrivsak(Skrivsak skrivsak){
        context.setFocusedSkrivsak(skrivsak);
    }

    public void setUpClippingView(){
        visipalp.examinerView.setup(heart.selection.get(0));
        visipalp.update();
    }

    public void saveClippingText(){
        String bufferString = visipalp.examinerView.examiner.skrivbord.buffer.toString();
        Clipping clipping = visipalp.examinerView.examiner.clipping;
        clipping.passage = new Passage(bufferString);

        clipping.data.setString("text", bufferString);
        heart.saveClippingData(clipping);

        ArrayList<String> tags = heart.library.findTagStrings(bufferString);
        if (tags != null) heart.library.tagList.addTag(heart.library.libraryData, tags);
        heart.tagClipping(clipping, tags);

        heart.library.tagList.addTag(heart.library.libraryData, clipping.getTags());
        updateTagList(visipalp.g);
    }

    public void updateTagList(PGraphics g){
        visipalp.contactSheetView.searchPanel.tagList.updateTagList(g, heart.getLibrary().tagList.getTags(heart.library.libraryData));
    }

    public void displaySearchResults(String [] queryWords, String query){
        ArrayList<Clipping> searchResults = search(queryWords);
        if (searchResults.isEmpty()){
            System.out.println("NO RESULTS");
            return;
        }
        visipalp.displayClippings(search(queryWords), query);
    }

    public void displaySearchResults(String query){
        displaySearchResults(query.trim().split(" +|\n+"), query);
    }

    public ArrayList<Clipping> search(String[] query){ // "query" to sound professional and real
       // for now i'm only worrying about tags more to come later

        ArrayList<Clipping> results = new ArrayList<Clipping>();

        for (String term : query){
            if (term != null) results.addAll(heart.library.tagList.getClippingsTagged(heart.library.getClippings(), term));
            if (heart.library.tagList.tagExists(heart.library.libraryData, term)){
                results.addAll(heart.library.tagList.getClippingsTagged(heart.library.getClippings(), term));
            }
        }
        return results;
    }

    public ArrayList<Clipping> search(String query){
        return search(query.trim().split(" +|\n+"));
    }

    public void clearSearch(){
        visipalp.displayAllClippings();
    }
}
