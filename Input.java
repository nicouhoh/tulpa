
import processing.event.MouseEvent;
import Field;

public class Input{
  
  Field field;
  Scroller scroller;
  Library library;
  
  boolean click;
  
  public Input(Library libraryIn, Field fieldIn){
    field = fieldIn;
    scroller = field.scroller;
    library = libraryIn;
  }
  
  public void update(){
    click = false;
  }
  
  public void keyInput(){
    if (key == CODED){
      switch(keyCode){
        case UP:
          println("scroll up");
          break;
        case DOWN:
          println("scroll down");
          break;
      }
    }
    else{
      println(key);
      switch(key){
        case '-':
          field.columns += 1; //<>//
          field.fussMenagerie();
          break;
        case '=':
          field.columns -= 1;
          field.fussMenagerie();
          break;
      }
    }
  }
  
  public void mouseClick(){
    click = true;
  }
  
  public void mouseDown(){
    if (mouseX > field.w){
      scroller.grabScroller();
    }
  }
  
  public void mouseUp(){
    if (scroller.grabbed){
      scroller.grabbed = false;
    }
  }
  
  public void wheel(MouseEvent event){
    scroller.moveScroller(event.getCount());
  }
  
  public void dropInput(DropEvent drop){
    println("dropInput");
    if (drop.isImage()){
      Clipping clipping = library.incubateFile(drop.file());
      //print("ABSOLUTE PATH: " + drop.file().path);
      library.addToLibrary(clipping);
    }else if(drop.isFile()){
      println("drop.isFile");
      File file = new File(drop.toString());
      print("ABSOLUTE PATH: " + file.getAbsolutePath());
      if (file.isDirectory()){
        Clipping[] clippings = library.incubateDir(file);
        library.addToLibrary(clippings);
      }
    }
    field.fussMenagerie();
  }
  
}
