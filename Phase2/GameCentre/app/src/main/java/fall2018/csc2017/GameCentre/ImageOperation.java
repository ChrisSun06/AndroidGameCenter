package fall2018.csc2017.GameCentre;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;

import java.util.ArrayList;
import java.util.List;

public class ImageOperation {
    /**
     * The default source image width.
     */
    static final int SourceImageWidth = 992;
    /**
     * The default source image height.
     */
    static final int SourceImageHeight = 1276;

    /**
     * Resize the source image into 992x1276 and return the resized image bitmap.
     *
     * @param image the bitmap of the given image
     * @return the resized source image
     */
    public static Bitmap resizeSourceImage(Bitmap image) {
        return Bitmap.createScaledBitmap(image,
                SourceImageWidth, SourceImageHeight, false);
    }

    /**
     * Superpose one bitmap above the centre of one another.
     *
     * @param back back image
     * @param front front image
     * @return the superposed image
     */
    public static Bitmap superpose(Bitmap back, Bitmap front) {
        Bitmap superposed= Bitmap.createBitmap(back.getWidth(), back.getHeight(), back.getConfig());
        Canvas canvas = new Canvas(superposed);
        canvas.drawBitmap(back, new Matrix(), null);
        canvas.drawBitmap(front,
                (back.getWidth() - front.getWidth()) / 2,
                (back.getHeight() - front.getHeight()) / 2,
                null);
        return superposed;
    }

    /**
     * Setup default game display and image source. Only update when data folders are empty.
     */
    /*public void setupDefault() {
        createBlankTiles();
        createDefaultImageSet();
        createAllDefaultNumberTiles(false);
    }*/

    /**
     * Crop the image into a 3x3, 4x4 and 5x5 grid and save those images into internal storage
     * tileDir for gaming access.
     *
     * @param image the image bitmap which is about to be cropped
     */
    public void createAllCroppedTiles(Bitmap image) {
        cropImage(image, 3, 3);
        cropImage(image, 4, 4);
        cropImage(image, 5, 5);
    }

    /**
     * Resize and save all the default number tiles into internal storage tileDir for gaming access.
     *
     * @param overwrite the permission to overwrite the tiles when tileDir is not empty
     */
    /*public void createAllDefaultNumberTiles(boolean overwrite) {
        File imageTile = new File(getTilePath(3, 3, 1));
        if (!imageTile.exists() || overwrite) {
            createDefaultNumberTiles(3);
            createDefaultNumberTiles(4);
            createDefaultNumberTiles(5);
        }
    }*/

    /**
     * Resize and save all the default number tiles by a given grid size.
     *
     * @param gridSize size of the sliding tile grid, either 3, 4 or 5
     */
    /*private void createDefaultNumberTiles(int gridSize) {
        int segWidth = SourceImageWidth / gridSize;
        int segHeight = SourceImageHeight / gridSize;
        int count = 1;
        for (int drawableId = FirstNumberTileId;
             drawableId < (FirstNumberTileId - 1) + gridSize * gridSize; drawableId++) {
            Bitmap numberTile = BitmapFactory.decodeResource(getResources(), drawableId);
            Bitmap resized = Bitmap.createScaledBitmap(numberTile,
                    segWidth, segHeight, false);
            savePNGToInternalStorage(resized, "tileDir",
                    createTileName(gridSize, gridSize, count++));
        }
    }*/

    /**
     * Create the default image set.
     */
    /*private void createDefaultImageSet() {
        File image = new File(getImagePath("batman"));
        if (!image.exists()) {
            int count = 0;
            for (int imageId = FirstDefaultImageId;
                 imageId < FirstDefaultImageId + DefaultImages; imageId++) {
                Bitmap sourceImage = BitmapFactory.decodeResource(getResources(), imageId);
                Bitmap resized = resizeSourceImage(sourceImage);
                savePNGToInternalStorage(resized, "imageDir", ImageSet[count++]);
            }
        }
    }*/

    /**
     * Crop the given image into a given numRows x numCols grid. Then save it into tileDir for later
     * gaming access.
     *
     * @param image the image bitmap about to be cropped
     * @param numRows the # of rows
     * @param numCols the # of cols
     */
    public static List<Bitmap> cropImage(Bitmap image, int numRows, int numCols) {
        int segWidth = SourceImageWidth / numCols;
        int segHeight = SourceImageHeight / numRows;
        List<Bitmap> bitmaps = new ArrayList<>();

        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numCols; j++) {
                Bitmap imgTile = Bitmap.createBitmap(image,
                        j * segWidth, i * segHeight,
                        segWidth, segHeight);
                bitmaps.add(imgTile);
                // String tileName = createTileName(numRows, numCols, numCols * i + j + 1);
                // savePNGToInternalStorage(imgTile, "tileDir", tileName);
                if (i == numRows - 1 && j == numCols - 2) break;
            }
        }
        return bitmaps;
    }
}
