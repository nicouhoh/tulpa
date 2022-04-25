import processing.core.PGraphics;

public class Cockpit extends Monad{


  Field field;
  Scroller scroller;

  boolean sardine;

  float zoomPillow;

  Spegel casper;
  float casperX;
  float casperY;

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
    drawOnTop(g);
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

  public void drawOnTop(PGraphics g){
    drawCasper(g);
  }

  public void drawCasper(PGraphics g){
    if (casper != null){
      g.tint(255, 130);
      g.image(casper.clipping.img,
              tulpa.SOLE.mouseX - casperX,
              tulpa.SOLE.mouseY - casperY,
              casper.displayW, casper.displayH);
      g.tint(255);
    }
  }

  public void setCasper(Spegel s, float x, float y){
    casper = s;
    casperX = x;
    casperY = y;

  }
}