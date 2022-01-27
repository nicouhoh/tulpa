class Input{
  
  Field field;
  Scroller scroller;
  Library library;
  
  Input(Library libraryIn, Field fieldIn){
    field = fieldIn;
    scroller = field.scroller;
    library = libraryIn;
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
      Clipping clipping = library.incubateFile(drop.file());
      //print("ABSOLUTE PATH: " + drop.file().path);
      library.addToLibrary(clipping);
    }else if(drop.isFile()){
      println("drop.isFile"); //<>//
      File file = new File(drop.toString());
      print("ABSOLUTE PATH: " + file.getAbsolutePath());
      if (file.isDirectory()){
        Clipping[] clippings = library.incubateDir(file);
        library.addToLibrary(clippings);
      }
    }
    field.fussMenagerie();
  }
  
}
