package fall2018.csc2017.GameCentre;

import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public abstract class ImageOperationActivity extends AppCompatActivity {
    /**
     * The default source image width.
     */
    public static final int SourceImageWidth = 992;
    /**
     * The default source image height.
     */
    public static final int SourceImageHeight = 1276;
    /**
     * The internal storage path to app data.
     */
    public static final String InternalStoragePath = "/data/data/fall2018.csc2017.GameCentre/";
    /**
     * Number of default source images.
     */
    public static final int DefaultImages = 2;
    /**
     * ID of the first tile in R class.
     */
    public static final int FirstNumberTileId = R.drawable.tile_01;
    /**
     * ID of the first default source image in R class.
     */
    public static final int FirstDefaultImageId = R.drawable.z_batman;
    /**
     * A set of all default source image names.
     */
    public static final String[] ImageSet = {"batman", "superman"};

    /**
     * Resize the source image into 992x1276 and return the resized image bitmap.
     *
     * @param image the bitmap of the given image
     * @return the resized source image
     */
    public Bitmap resizeSourceImage(Bitmap image) {
        return Bitmap.createScaledBitmap(image,
                SourceImageWidth, SourceImageHeight, false);
    }

    /**
     * Setup default game display and image source. Only update when data folders are empty.
     */
    public void setupDefault() {
        createBlankTiles();
        createDefaultImageSet();
        createAllDefaultNumberTiles(false);
    }

    /**
     * Create blank tiles for 3x3, 4x4 and 5x5 grid respectively.
     */
    private void createBlankTiles() {
        File tile = new File(getTilePath(3, 3, 9));
        if (!tile.exists()) {
            Bitmap white = BitmapFactory.decodeResource(getResources(), R.drawable.white);
            Bitmap white3 = Bitmap.createScaledBitmap(white,
                    SourceImageWidth / 3, SourceImageHeight / 3, false);
            Bitmap white4 = Bitmap.createScaledBitmap(white,
                    SourceImageWidth / 3, SourceImageHeight / 4, false);
            Bitmap white5 = Bitmap.createScaledBitmap(white,
                    SourceImageWidth / 5, SourceImageHeight / 5, false);

            savePNGToInternalStorage(white3, "tileDir", "tile_3x3_9");
            savePNGToInternalStorage(white4, "tileDir", "tile_4x4_16");
            savePNGToInternalStorage(white5, "tileDir", "tile_5x5_25");
        }
    }

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
    public void createAllDefaultNumberTiles(boolean overwrite) {
        File imageTile = new File(getTilePath(3, 3, 1));
        if (!imageTile.exists() || overwrite) {
            createDefaultNumberTiles(3);
            createDefaultNumberTiles(4);
            createDefaultNumberTiles(5);
        }
    }

    /**
     * Resize and save all the default number tiles by a given grid size.
     *
     * @param gridSize size of the sliding tile grid, either 3, 4 or 5
     */
    private void createDefaultNumberTiles(int gridSize) {
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
    }

    /**
     * Create the default image set.
     */
    private void createDefaultImageSet() {
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
    }

    /**
     * Crop the given image into a given numRows x numCols grid. Then save it into tileDir for later
     * gaming access.
     *
     * @param image the image bitmap about to be cropped
     * @param numRows the # of rows
     * @param numCols the # of cols
     */
    public void cropImage(Bitmap image, int numRows, int numCols) {
        int segWidth = SourceImageWidth / numCols;
        int segHeight = SourceImageHeight / numRows;

        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numCols; j++) {
                Bitmap imgTile = Bitmap.createBitmap(image,
                        j * segWidth, i * segHeight,
                        segWidth, segHeight);
                String tileName = createTileName(numRows, numCols, numCols * i + j + 1);
                savePNGToInternalStorage(imgTile, "tileDir", tileName);
                if (i == numRows - 1 && j == numCols - 2) break;
            }
        }
    }

    /**
     * Return the path of a specific tile.
     *
     * @param numRows board's num rows
     * @param numCols board's num cols
     * @param tileId the tile's id
     * @return tile's path in internal storage
     */
    public String getTilePath(int numRows, int numCols, int tileId) {
        return InternalStoragePath + "app_tileDir/" + createTileName(numRows, numCols, tileId)+ ".png";
    }

    /**
     * Return the path of a source image.
     *
     * @param imageName the name of the image
     * @return source image's path in internal storage
     */
    public String getImagePath(String imageName) {
        return InternalStoragePath + "app_imageDir/" + imageName + ".png";
    }

    /**
     * create the name of a specific tile.
     *
     * @param numRows board's num rows
     * @param numCols board's num cols
     * @param tileId tile's id
     * @return generated tile's file name
     */
    @NonNull
    public static String createTileName(int numRows, int numCols, int tileId) {
        String grid = numRows + "x" + numCols;
        return "tile_" + grid + "_" + tileId;
    }

    /**
     * Save a given bitmap to internal storage as a PNG file.
     *
     * @param bitmapImage bitmap of the image
     * @param dirName name of the data directory
     * @param fileName name of the png
     */
    public void savePNGToInternalStorage(Bitmap bitmapImage, String dirName, String fileName) {
        ContextWrapper cw = new ContextWrapper(this.getApplicationContext());
        File directory = cw.getDir(dirName, MODE_PRIVATE);
        File myPath = new File(directory,fileName + ".png");

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(myPath, false);
            bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, fos);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
