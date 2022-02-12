import processing.event.MouseEvent;
import processing.event.KeyEvent;

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

    if (state == State.LIBRARY){                      // LIBRARY -----------------------
      switch (e.getKeyCode()) {
        case 8 -> {                                       // BACKSPACE
          library.whackClipping();
          field.fussMenagerie();
        }
        case 32 -> {                                      // SPACE
          state = State.ZOOM;
          library.zoom();
        }
        case 37 -> library.selectLeftRight(-1);         // LEFT ARROW
        //case 38 -> library.selectDownUp(-field.sheetZoom);       // UP ARROW TODO Make it do stuff
        case 38 -> library.selectUpDownSardine(-1);
        case 39 -> library.selectLeftRight(1);        // RIGHT ARROW
        //case 40 -> library.selectDownUp(field.sheetZoom);     // DOWN ARROW TODO Make it do stuff
        case 40 -> library.selectUpDownSardine(1);
        case 48 -> field.switchView();                    // 0
        case 45 -> field.sheetZoom(-1);                // -
        case 61 -> field.sheetZoom(1);                 // =
      }
    }

    else if (state == State.ZOOM){                    // ZOOM -------------------------
      if (library.selected.size() < 1) {
        state = State.LIBRARY;
        return;
      }
      switch(e.getKeyCode()){
        case 32 -> {                                      // SPACE
          library.unZoom();
          state = State.LIBRARY;
        }
        case 37 -> library.selectLeftRight(-1);        // LEFT ARROW
        case 39 -> library.selectLeftRight(1);         // RIGHT ARROW
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
    else if (state == State.ZOOM){
      if (library.selected.size() < 1) {
        state = State.LIBRARY;
        return;
      }
    }
  }

  public void mouseWheelEvent(MouseWheelEvent e){
//    if (state == State.LIBRARY){
//      if (e.get) // TODO This seems like a more complex topic that I don't feel like figuring out right now. PERHAPS LATER
//    }
  }

  public void wheel(MouseEvent event){
    if (state != State.LIBRARY) return;
    scroller.moveScroller(event.getCount());
  }
  
  public void dropInput(DropEvent drop){
    if(state != State.LIBRARY) return;
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