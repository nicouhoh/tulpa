import java.util.ArrayList;

public class Jigsaw implements ThumbnailGrid {

    float gutter = 2;

    @Override
    public void arrangeThumbnails(ArrayList<Organelle> thumbs, float x, float y, float contactSheetW, int columns) {
        ArrayList<ThumbnailRow> allRows = constructRows(thumbs, x, y, contactSheetW, columns);
        resizeAllRows(allRows, contactSheetW);
        positionAllRows(allRows, x);
    }


    private ArrayList<ThumbnailRow> constructRows(ArrayList<Organelle> thumbs, float x, float y, float contactSheetW, int columns) {
        ArrayList<ThumbnailRow> allRows = new ArrayList<>();
        ThumbnailRow currentRow = getNextRow(x, y + gutter, contactSheetW, columns);

        for (int i = 0; i < thumbs.size(); i++){

            Thumbnail nextThumb = (Thumbnail) thumbs.get(i);
            nextThumb.fitToHeight(currentRow.height);

            if (endOfRow(thumbs, i, currentRow, nextThumb,contactSheetW)) {
                finishRow(thumbs, i, currentRow, nextThumb, allRows);
                currentRow = startNewRow(currentRow, nextThumb, x, contactSheetW, columns);
            }else {
                currentRow.add(nextThumb);
            }
        }
        return allRows;
    }

    private boolean endOfRow(ArrayList<Organelle> thumbs, int i, ThumbnailRow currentRow, Thumbnail nextThumb, float contactSheetW){
        return !currentRow.canFit(nextThumb, contactSheetW) || lastThumbnail(thumbs, i);
    }

    private void finishRow(ArrayList<Organelle> thumbs, int i, ThumbnailRow currentRow, Thumbnail nextThumb, ArrayList<ThumbnailRow> allRows) {
        if (lastThumbnail(thumbs, i)) currentRow.add(nextThumb);
        allRows.add(currentRow);
    }

    private ThumbnailRow startNewRow(ThumbnailRow prevRow, Thumbnail nextThumb, float x, float contactSheetW, int columns){
        ThumbnailRow result = getNextRow(x, prevRow.y + prevRow.height + gutter, contactSheetW, columns);
        result.add(nextThumb);
        return result;
    }

    private ThumbnailRow getNextRow(float x, float y, float contactSheetW, int columns) {
        return new ThumbnailRow(x, y, thumbSize(contactSheetW, columns), gutter);
    }

    private void resizeAllRows(ArrayList<ThumbnailRow> allRows, float contactSheetW) {
        for (ThumbnailRow row : allRows){
            float ratio = row.resizeRatio(contactSheetW);
            row.resizeRow(ratio, row.gutter);
        }
    }

    private void positionAllRows(ArrayList<ThumbnailRow> rows, float x){
        for (int i = 0; i < rows.size(); i++){
            ThumbnailRow prevRow = i == 0 ? null : rows.get(i - 1);
            positionRow(x, rows.get(i), prevRow);
            rows.get(i).positionThumbs();
        }
    }

    private void positionRow(float x, ThumbnailRow row, ThumbnailRow previousRow) {
        if (previousRow == null)row.y = gutter;
        else row.y = previousRow.y + previousRow.height + gutter;
        row.x = x + gutter;
    }

    public boolean lastThumbnail(ArrayList<Organelle> thumbs, int index){
        return index == thumbs.size() - 1;
    }

    @Override
    public float getFoot(ArrayList<Organelle> thumbs, float w, int columns) {
        if (thumbs.isEmpty()) return 0;
        Organelle lastThumb = thumbs.get(thumbs.size() - 1);
        return lastThumb.y + lastThumb.h + gutter;
    }

    @Override
    public float getGutter(){
        return gutter;
    }

    @Override
    public ThumbnailGrid toggle() {
        return new Agnes();
    }

    private float thumbSize(float w, int columns){
        return (w - gutter * (columns + 1)) / columns;
    }
}
