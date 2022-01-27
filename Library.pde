class Library{
  
  Clipping[] clippings;
  String path;
  
  Library(){
    path = sketchPath() + "/data/";
    clippings = incubateDir(new File(path));
  }
  
    void addToLibrary(Clipping clip){
    clippings = (Clipping[])append(clippings, clip);
  }
  
  void addToLibrary(Clipping[] clips){
    clippings = (Clipping[])concat(clippings, clips);
  }
  
  
    
  Clipping incubateFile(File file){
    println("INCUBATE FILE: " + file.getName());
    if (file.getName().contains(".jpg")){
      Clipping clipping = new Clipping(file);
      return clipping;
    }else{ return null; }
  }
  
  Clipping[] incubateDir(File dir){
    println("INCUBATE DIR");
    File[] files = listFiles(dir);
    Clipping[] brood = new Clipping[0];
    for (int i = 0; i < files.length; i++){
      if (files[i].getName().contains(".jpg")){
        brood = (Clipping[])append(brood, incubateFile(files[i]));
      } else if(files[i].isDirectory()){
        brood = (Clipping[])concat(brood, incubateDir(files[i]));
      }
    }
    println("BROOD: " + brood);
    return brood;
  }
}
