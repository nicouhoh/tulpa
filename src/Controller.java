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
        heart.selectClipping(clipping);
        setUpClippingView();
    }

    public void toggleSelection(Clipping clipping){
        heart.toggleSelection(clipping);
    }

    public void addSelection(Clipping clipping){
        heart.addToSelection(clipping);
    }

    public void removeSelection(Clipping clipping){
        heart.removeFromSelection(clipping);
    }

    public Thumbnail findThumbnail(Clipping clipping){
        for (Thumbnail thumbnail : visipalp.contactSheetView.contactSheet.getThumbnails()){
            if (thumbnail.clipping == clipping) return thumbnail;
        }
        return null;
    }

    public void horizontalStepSelect(int direction){
        if (heart.selectedClippings.size() != 1) return;
        Clipping clip = heart.stepSelection(direction);
        Thumbnail thumb = findThumbnail(clip);
        visipalp.contactSheetView.scroller.jumpToOrganelle(thumb, visipalp.contactSheetView.contactSheet.getGutter());
        setUpClippingView();
    }

    public void verticalStepSelect(int direction){
        if (heart.selectedClippings.size() != 1) return;
        Thumbnail selectedThumb = findThumbnail(heart.selectedClippings.get(0));
        Thumbnail targetThumb = visipalp.verticalStep(selectedThumb, direction);
        heart.selectClipping(targetThumb.clipping);
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
        if(heart.selectedClippings.size() != 1) return;
        changeContext(new ExaminerContext(this, visipalp.examinerView.examiner));
    }

    public void openExaminer(char c){
        if(heart.selectedClippings.size() != 1) return;
        changeContext(new ExaminerContext(this, visipalp.examinerView.examiner));
        context.type(c);
    }

    public void focusSkrivsak(Skrivsak skrivsak){
        context.setFocusedSkrivsak(skrivsak);
    }

    public void setUpClippingView(){
        visipalp.examinerView.setup(heart.getSelectedClippings().get(0));
        visipalp.update();
    }

    public void saveClippingText(){
        String string = visipalp.examinerView.examiner.skrivbord.buffer.toString();
        Clipping clipping = visipalp.examinerView.examiner.clipping;
        clipping.passage = new Passage(string);

        clipping.data.setString("text", string);
        heart.saveClippingData(clipping);

<<<<<<< HEAD
        ArrayList<Tag> tags = heart.library.parseTags(string);
        if (tags != null) heart.library.addTag(tags);
        clipping.addTag(tags);
=======
        if (string.isBlank()) return;

        ArrayList<String> tags = heart.library.findTagStrings(string);
        if (tags != null) heart.library.pigeonholer.addTag(heart.library.libraryData, tags);
        heart.tagClipping(clipping, tags);
>>>>>>> 63ff47c (i've embarked on a big refactoring expedition. importing directories may be broken right now. from here on better refactoring practices.)

        heart.library.findNewTags(clipping);
        updateTagList(visipalp.g);
    }

    public void updateTagList(PGraphics g){
        visipalp.contactSheetView.searchPanel.tagList.updateTagList(g, heart.getLibrary().pigeonholer.getTags(heart.library.libraryData));
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
<<<<<<< HEAD
            Tag tag = heart.library.getTagByName(term);
            if (tag != null) results.addAll(heart.library.getClippingsTagged(tag));
=======
            if (heart.library.pigeonholer.tagExists(heart.library.libraryData, term)){
                results.addAll(heart.library.getClippingsWithTag(term));
            }
>>>>>>> 63ff47c (i've embarked on a big refactoring expedition. importing directories may be broken right now. from here on better refactoring practices.)
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
