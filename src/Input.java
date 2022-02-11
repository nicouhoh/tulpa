import processing.core.PApplet;

import processing.event.MouseEvent;
import processing.event.KeyEvent;
import java.awt.event.KeyEvent.*;
import java.awt.event.MouseEvent.*;
import java.awt.event.MouseWheelEvent.*;

import java.awt.event.MouseWheelEvent;
import java.io.File;
import java.util.ArrayList;

import drop.DropEvent;

public class Input{
  
  Field field;
  Scroller scroller;
  Library library;

  State state;

  public Input(Library libraryIn, Field fieldIn){
    field = fieldIn;
    scroller = field.scroller;
    library = libraryIn;
    state = State.LIBRARY;
  }

  public void keyEvent(KeyEvent e){
    if (e.getAction() != 1) return;
    System.out.print(e.getKeyCode());

    if (state == State.LIBRARY){
      switch (e.getKeyCode()) {
        case 8 -> {                                   // BACKSPACE
          library.whackClipping();
          field.fussMenagerie();
        }
        case 38 -> System.out.println("scroll up");   // TODO UP ARROW
        case 40 -> System.out.println("scroll down"); // TODO DOWN ARROW
        case 48 -> field.switchView();                // 0
        case 45 -> field.zoom(-1);                 // -
        case 61 -> field.zoom(1);                  // =
      }
    }
  }

  public void mouseEvent(MouseEvent e){
    if (state == State.LIBRARY){
      if (e.getAction() == MouseEvent.PRESS){
        switch(e.getButton()){
          case 37 -> {                           // TODO Left mouse button. Why the heck is this 37
            if(e.getX() > field.w){
              scroller.grabScroller();
            }
          }
        }
      }
      else if (e.getAction() == MouseEvent.RELEASE) {
        switch(e.getButton()){
          case 37 -> {                           // TODO Left mouse button. Why the heck is this 37
              scroller.grabbed = false;
              library.clickSelect(e);
          }
        }
      }
    }
  }

  public void mouseWheelEvent(MouseWheelEvent e){
//    if (state == State.LIBRARY){
//      if (e.get) // TODO This seems like a more complex topic that I don't feel like figuring out right now. PERHAPS LATER
//    }
  }

  public void wheel(MouseEvent event){
    scroller.moveScroller(event.getCount());
  }
  
  public void dropInput(DropEvent drop){
    System.out.println("dropInput");
    if (drop.isImage()){
      Clipping clipping = library.incubateFile(drop.file());
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