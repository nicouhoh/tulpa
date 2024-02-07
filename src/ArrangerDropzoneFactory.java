import java.util.ArrayList;
import processing.core.PApplet;

public class ArrangerDropzoneFactory {

    ArrayList<Thumbnail> thumbnails;
    int allowance;
    float x, w;

    public ArrangerDropzoneFactory(ArrayList<Thumbnail> thumbnails, int allowance, float x, float w){
        this.thumbnails = thumbnails;
        this.allowance = allowance;
        this.x = x;
    }

    public ArrayList<Dropzone> produceAllZones() {
        ArrayList<Dropzone> result = new ArrayList<>();
        result.add(firstArranger(thumbnails.get(0), thumbnails.get(1), allowance));

        for (int i = 1; i < thumbnails.size() - 1; i++){
            Thumbnail left = thumbnails.get(i);
            Thumbnail right = thumbnails.get(i + 1);
            result.addAll(createThumbnailDropZones(left, right));
        }
        return result;
    }

    private ArrangerDropzone firstArranger(Thumbnail left, Thumbnail right, int allowance) {
        return new ArrangerDropzone(left, x, left.y, left.x - x + allowance, left.h, x);
    }

    private ArrayList<ArrangerDropzone> createThumbnailDropZones(Thumbnail left, Thumbnail right) {
        ArrayList<ArrangerDropzone> result = new ArrayList<>();
        if (rowBreak(left, right)) result.addAll(startAndEndOfRowDropZones(left, right));
        else result.add(createDropZoneBetweenThumbnails(left, right));
        return result;
    }


    private ArrayList<ArrangerDropzone> startAndEndOfRowDropZones(Thumbnail left, Thumbnail right) {
        ArrayList<ArrangerDropzone> result = new ArrayList<>();
        result.add(rowEndArranger(left, right));
        result.add(rowStartArranger(right));
        return result;
    }

    //FIXME this ain't actually working
    private ArrangerDropzone rowEndArranger(Thumbnail left, Thumbnail right){
        return new ArrangerDropzone(right,
                left.x + left.w - allowance,
                left.y,
                x + w - (left.x + left.w) + allowance,
                left.h,
                left.x + left.w);

    }

    private ArrangerDropzone rowStartArranger(Thumbnail right){
        return new ArrangerDropzone(right, x, right.y, right.x - x + allowance, right.h, right.x);
    }

    private boolean rowBreak(Thumbnail left, Thumbnail right){
        return right.x <= left.x;
    }

    public ArrangerDropzone createDropZoneBetweenThumbnails(Thumbnail left, Thumbnail right){
                return new ArrangerDropzone(right,
                        left.x + left.w - allowance,
                        PApplet.min(left.y, right.y),
                        right.x - (left.x + left.w) + 2 * allowance,
                        PApplet.max(left.h, right.h), (left.x + left.w + right.x)/2
                );
    }
}
