class Wranglist{
  
  Clipping[] library;
  String path;
  int clipSize;
  int pillow;
  
  Wranglist(){
    path = sketchPath() + "/data/";
    library = BirthingPool();
    pillow = 10;
    clipSize = 200;
    FussMenagerie();
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
      if (x + clipSize >= field.size){
        x = pillow;
        y += clipSize + pillow;
      }
        library[i].setPos(x, y);
        x += clipSize + pillow;
    }
  }  
  
  void Showtime(){
    for (int i = 0; i < library.length; i++){
      library[i].display();
    }
  }
  
}
