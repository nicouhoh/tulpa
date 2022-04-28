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
  public void cascadeDraw(PGraphics g, float latitude){
    super.cascadeDraw(g, latitude);
  }

  @Override
  public boolean isOnscreen(float latitude) {
    if (y < tulpa.SOLE.h && y + h >= 0) {
      return true;
    } else {
      System.out.println("COCKPIT OFFSCREEN");
      return false;
    }
  }



//  public void setBetweenClips(Clipping[] bc){
//    betweenClips = bc;
//  }
//
//  public void setBetweenClips(Clipping c1, Clipping c2){
//    betweenClips[0] = c1;
//    betweenClips[1] = c2;
//  }
}