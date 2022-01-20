class Field{

  int fieldW;
  Scroller scroller;
  float latitude;
  
  Clipping[] library;
  String path;
  int clipSize;
  int pillow;
  float foot;
  int scrollDist;
  
  Field(){
    scroller = new Scroller();
    latitude = 0;
    scrollDist = 50;
    path = sketchPath() + "/data/";
    library = BirthingPool();
    pillow = 10;
    clipSize = 200;
    FussMenagerie();
  }
  
  void initializeField(){
    fieldW = width - scroller.scrollW;
  }
  
  void updateField(){
    fieldW = width - scroller.scrollW;
    if (resized){
      FussMenagerie(); //<>//
    }
    scroller.updateScroller(foot, latitude);
    followScroller();
    Showtime();
  }
  
  String[] listFileNames(String dir){
    File file = new File(dir);
    if (file.isDirectory()) {
      String names[] = file.list();
      return names;
    } else {
      return null;
    }
  }
  
  Clipping[] BirthingPool(){
    String[] filenames = listFileNames(path);
    Clipping[] brood = new Clipping[0];
    for (int i = 0; i < filenames.length; i++){
      if (filenames[i].contains(".jpg")){
        Clipping spawn = new Clipping(filenames[i]);
        if (spawn.img == null){
           println("ERROR ERROR HELP ME");
        }
        brood = (Clipping[])append(brood, spawn);
      }
    }
    return brood;
  }
  
  void FussMenagerie(){
    int x = pillow;
    int y = pillow;
    for (int i = 0; i < library.length; i++){
      library[i].setSize(clipSize, clipSize);
      if (x + clipSize >= fieldW){
        x = pillow;
        y += clipSize + pillow;
      }
        library[i].setPos(x, y);
        x += clipSize + pillow;
    }
    foot = y + clipSize + pillow;
    
  }  

  void Showtime(){
    push();
    background(50);
    translate(0, latitude);
    for (int i = 0; i < library.length; i++){
      library[i].display(latitude);
    }
    pop();
    
    scroller.drawScroller();
  }
  
  void followScroller(){
    float old = latitude;
     latitude = -(scroller.gripY / height) * foot;
    if (old != latitude){
      println("latitude: " + latitude);
    }
  }
 
  
}
