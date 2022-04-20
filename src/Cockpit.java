import processing.core.PGraphics;


public class Cockpit extends Monad{


  Field field;
  Scroller scroller;

  boolean sardine;

  float zoomPillow;
  
  public Cockpit(){
    setPos(0, 0);
    setSize(tulpa.SOLE.width, tulpa.SOLE.height);
    field = new Field(this);
    sardine = false;
    zoomPillow = 30;
  }

  @Override
  public void update(){
    setSize(tulpa.SOLE.width, tulpa.SOLE.height);
  }

  @Override
  public boolean isOnscreen(float latitude) {
    if (y < latitude + tulpa.SOLE.h && y >= latitude - h * 1.5) {
      return true;
    } else {
      System.out.println("COCKPIT OFFSCREEN");
      return false;
    }
  }


  //
//  public void switchView(){
//    float y = 0;
//    Clipping target;
//    if (library.selected.size() == 1 && library.selected.get(0).onscreen){
//      target = library.selected.get(0);
//      y = target.y - latitude;
//      sardine = !sardine;
//      field.fussMenagerie(library);
//
////      goTo(target.y, y);
//      return;
//    }
//
//    //TODO: Figure out no or multiple selections
//
//    sardine = !sardine;
//    field.fussMenagerie(library);
//  }
//
// SCROLLING ---------------------------------------
//
//  public void followScroller(){
//     latitude = scroller.gripY / tulpa.SOLE.height * foot;
//  }
//
//  public void updateScroller(){
//    scroller.update(foot);
//    followScroller();
//  }
//
//  public void goTo(float lat, float where){          // pick a latitude and where on screen you want to put it
//    scroller.goTo(lat - where, h, foot);
//    followScroller();
//  }
//
//  public void grabScroller(){
//    if (tulpa.SOLE.mouseY > scroller.gripY && tulpa.SOLE.mouseY < scroller.gripY + scroller.gripH){
//      scroller.grab();
//    }
//  }
//
//  public void dragScroller(float contentH){
//    if(scroller.grabbed){
//      scroller.gripY = tulpa.SOLE.mouseY - scroller.grabY;
//      updateScroller();
//    }
//  }

//  public void arrowFollow(){
//    if(library.selected.size() != 1) return;
//
//    Clipping clip = library.selected.get(0);                  // below screen
//    if (clip.y + pillow + clip.displayH/2 > latitude + h){
//      goTo(clip.y, h - pillow - clip.displayH);
//    }
//    else if(clip.y + clip.displayH/2 < latitude){   // above screen
//      goTo(clip.y, pillow);
//    }
//    updateScroller();
//  }
  // END SCROLLING --------------------------------------------
}