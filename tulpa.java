import drop.*;
import test.*;

import processing.core.PApplet;

public class tulpa  extends PApplet  {
SDrop sdrop;

Library library;
Field field;
Scroller scroller;
Input input;
int w, h;
boolean resized = false;

public void setup(){
  surface.setSize(500, 500);
  surface.setResizable(true);
  sdrop = new SDrop(this);
  library = new Library();
  field = new Field(library);
  field.initializeField();
  input = new Input(library, field);
  
}

public void draw(){
  checkResize();
  field.updateField();
  input.update();
  resized = false;
}
  
public void checkResize(){
    if (w != width || h != height){
    w = width;
    h = height;
    resized = true;
  }
}


// INPUT--------------------------------------

public void keyPressed(){
  input.keyInput();
}

public void mouseClicked(){
  input.mouseClick();
}

public void mousePressed(){
  input.mouseDown();
}

public void mouseReleased(){
  input.mouseUp();
}

public void mouseWheel(MouseEvent event){
  input.wheel(event);
}

public void dropEvent(DropEvent dropEvent){
  input.dropInput(dropEvent);
}

}
