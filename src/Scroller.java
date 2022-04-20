import processing.core.PApplet;
import processing.core.PGraphics;

import java.util.ArrayList;


public class Scroller extends Monad{
  // TODO would be nice to see an indication on the scrollbar of offscreen selected clippings

  Grip grip;
  int color;
  int scrollDist;

  public Scroller(Field parent){
    this.parent = parent;
    this.parent.children.add(this);
    setPos(parent.x + parent.w - parent.scrollWidth(), parent.y);
    setSize(parent.scrollW, parent.h);
    color = 0xff1A1A1A;
    scrollDist = 1;
    grip = new Grip(this);
  }

  @Override
  public boolean isOnscreen(float latitude) {
    if (y < latitude + parent.h && y >= latitude - h * 1.5) {
      return true;
    } else {
      System.out.println("SCROLLER OFFSCREEN");
      return false;
    }
  }

  @Override
  public void draw(PGraphics g){
    g.noStroke();
    g.fill(color);
    g.rect(x, y, w, h);
  }

  @Override
  public void update(){
    setPos(parent.x + parent.w - parent.scrollWidth(), parent.y);
    setSize(parent.scrollWidth(), parent.h);
  }

  public void goTo(float latitude, float height, float contentH){
    grip.y = (latitude * height) / contentH;
  }

  public void grabGrip(){
    grip.grabbed = true;
    System.out.println("grabbed");
    grip.grabY = tulpa.SOLE.mouseY - y;
  }

}
