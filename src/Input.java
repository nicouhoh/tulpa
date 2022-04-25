import processing.event.MouseEvent;
import processing.event.KeyEvent;

import java.awt.event.MouseWheelEvent;
import java.io.File;
import java.util.ArrayList;

import drop.DropEvent;

public class Input{
  
  Cockpit cockpit;
  Scroller scroller;
  Library library;

  State state;

  public Input(Library libraryIn, Cockpit cockpitIn){
    cockpit = cockpitIn;
    scroller = cockpit.scroller;
    library = libraryIn;
    state = State.LIBRARY;
  }

//  public void keyEvent(KeyEvent e){
//    if (e.getAction() != 1) return;
//    System.out.print(e.getKeyCode());
//
//    if (state == State.LIBRARY){                // LIBRARY -----------------------
//      switch (e.getKeyCode()) {
////        case 8 -> {                                       // BACKSPACE
////          library.whackClipping();
////          cockpit.field.fussMenagerie(library);
////        }
////        case 32 -> {                                      // SPACE
////          state = State.ZOOM;
////          library.zoom();
////        }
////        case 37 -> {                                      // LEFT ARROW
////          library.selectLeftRight(-1);
////          cockpit.arrowFollow();
////        }
////        case 38 -> library.selectDownUp(-cockpit.sheetZoom);       // UP ARROW
////        case 38 -> {
////          if (cockpit.sardine) library.selectUpDownSardine(-1);
////          else library.selectUpDownGrid(-cockpit.field.sheetZoom);
////          cockpit.arrowFollow();
//        }
////        case 39 -> {                                      // RIGHT ARROW
////          library.selectLeftRight(1);
////          cockpit.arrowFollow();
//        }
//        //case 40 -> library.selectDownUp(cockpit.sheetZoom);     // DOWN ARROW
////        case 40 -> {
////          if (cockpit.sardine) library.selectUpDownSardine(1);
////          else library.selectUpDownGrid(cockpit.field.sheetZoom);
////          cockpit.arrowFollow();
//        }
////        case 48 -> cockpit.switchView();                    // 0
////        case 45 -> cockpit.field.zoom(-1);                // -
////        case 61 -> cockpit.field.zoom(1);                 // =
////        case 79 -> library.overlapDebug();               // Letter O
//      }
//    }

////    else if (state == State.ZOOM){                    // ZOOM -------------------------
////      if (library.selected.size() < 1) {
////        state = State.LIBRARY;
////        return;
////      }
//      switch(e.getKeyCode()){
//        case 32 -> {                                      // SPACE
//          library.unZoom();
//          state = State.LIBRARY;
//        }
//        case 37 -> library.selectLeftRight(-1);        // LEFT ARROW
//        case 39 -> library.selectLeftRight(1);         // RIGHT ARROW
//      }
//    }
//  }

//  public void mouseEvent(MouseEvent e){
//    if (state == State.LIBRARY){
//      if (e.getAction() == MouseEvent.PRESS){
//        switch(e.getButton()){
//          case 37 -> {                           // TODO Left mouse button. Why the heck is this 37
//            if(e.getX() > cockpit.w){
////              cockpit.grabScroller();
//            }
//          }
//        }
//      }
//      if(e.getAction() == MouseEvent.DRAG){
////        cockpit.dragScroller(cockpit.foot);
//      }
//      else if (e.getAction() == MouseEvent.RELEASE) {
//        switch(e.getButton()){
//          case 37 -> {                           // TODO Left mouse button. Why the heck is this 37
//              scroller.grip.grabbed = false;
//              library.clickSelect(e);
//          }
//        }
//      }
//    }
//    else if (state == State.ZOOM){
//      if (library.selected.size() < 1) {
//        state = State.LIBRARY;
//        return;
//      }
//    }
//  }
//
//  public void wheel(MouseEvent event){
//    if (state != State.LIBRARY) return;
////      cockpit.goTo(cockpit.latitude + event.getCount(), 0);
//  }
  
  public void dropInput(DropEvent drop){
    if(state != State.LIBRARY) return;
    System.out.println("dropInput");
    if (drop.isImage() && drop.file().getName().contains(".jpg")){ //TODO Take out .jpg clause when memory isn't an issue anymore
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
// d   cockpit.field.fussMenagerie(library);
  }
}