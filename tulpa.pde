import drop.*;
import test.*;

SDrop sdrop; //<>//

Library library;
Field field;
Scroller scroller;
Input input;
int w, h;
boolean resized = false;

void setup(){
  surface.setSize(500, 500);
  surface.setResizable(true);
  sdrop = new SDrop(this);
  library = new Library();
  field = new Field(library); //<>//
  field.initializeField();
  input = new Input(library, field);
  
}

void draw(){
  checkResize();
  field.updateField();
  resized = false;
}
  
void checkResize(){
    if (w != width || h != height){
    w = width;
    h = height;
    resized = true;
  }
}


// INPUT--------------------------------------

void keyPressed(){
  input.keyInput();
}

void mousePressed(){
  input.mouseDown();
}

void mouseReleased(){
  input.mouseUp();
}

void mouseWheel(MouseEvent event){
  input.wheel(event);
}

void dropEvent(DropEvent dropEvent){
  input.dropInput(dropEvent);
}
  
