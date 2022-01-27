class Input{
  
  Field field;
  Scroller scroller;
  
  Input(Field fieldIn){
    field = fieldIn;
    scroller = field.scroller;
  }
  
  void keyInput(){
    if (key == CODED){
      switch(keyCode){
        case UP:
          println("scroll up");
          break;
        case DOWN:
          println("scroll down");
          break;
      }
    }
  }
  
  void mouseDown(){
    if (mouseX > field.w){
      scroller.grabScroller();
    }
  }
  
  void mouseUp(){
    if (scroller.grabbed){
      scroller.grabbed = false;
    }
  }
  
  void wheel(MouseEvent event){
    scroller.moveScroller(event.getCount());
  }
  
  void dropInput(DropEvent drop){
    println("dropInput"); //<>//
    if (drop.isImage()){
      Clipping clipping = field.incubateFile(drop.file());
      //print("ABSOLUTE PATH: " + drop.file().path);
      field.addToLibrary(clipping);
    }else if(drop.isFile()){
      println("drop.isFile"); //<>//
      File file = new File(drop.toString());
      print("ABSOLUTE PATH: " + file.getAbsolutePath());
      if (file.isDirectory()){
        Clipping[] clippings = field.incubateDir(file);
        field.addToLibrary(clippings);
      }
    }
    field.fussMenagerie();
  }
  
}
