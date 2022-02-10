import processing.core.PApplet;
import processing.event.MouseEvent;

import java.io.File;
import java.util.ArrayList;

import drop.DropEvent;


public class Input{
  
  Field field;
  Scroller scroller;
  Library library;

  public Input(Library libraryIn, Field fieldIn){
    field = fieldIn;
    scroller = field.scroller;
    library = libraryIn;
  }
  
  public void keyInput(){
    if (tulpa.SOLE.key == tulpa.SOLE.CODED){
      switch (tulpa.SOLE.keyCode) {
        case PApplet.UP -> System.out.println("scroll up");
        case PApplet.DOWN -> System.out.println("scroll down");
      }
    }
    else{
      System.out.println(tulpa.SOLE.key);
      switch(tulpa.SOLE.key){
        case '-':
          field.columns += 1;
          field.fussMenagerie();
          break;
        case '=':
          field.columns -= 1;
          field.fussMenagerie();
          break;
      }
    }
  }

  public void mouseDown(){
    if (tulpa.SOLE.mouseX > field.w){
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
    System.out.println("dropInput");
    if (drop.isImage()){
      Clipping clipping = library.incubateFile(drop.file());
      //print("ABSOLUTE PATH: " + drop.file().path);
      library.addToLibrary(clipping);
    }else if(drop.isFile()){
      System.out.println("drop.isFile");
      File file = new File(drop.toString());
      System.out.print("ABSOLUTE PATH: " + file.getAbsolutePath());
      if (file.isDirectory()){
        ArrayList<Clipping> clippings = library.incubateDir(file);
        library.addToLibrary(clippings);
      }
    }
    field.fussMenagerie();
  }
  
}
