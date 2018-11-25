package fall2018.csc2017.GameCentre;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.Serializable;

public class SerializableBitmap implements Serializable {
    private byte[] bitmapByteArray;

    public SerializableBitmap(Bitmap bitmap) {
        this.bitmapByteArray = bitmapToByteArray(bitmap);
    }

    private byte[] bitmapToByteArray(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

    public Bitmap byteArrayToBitmap() {
        return BitmapFactory.decodeByteArray(
                this.bitmapByteArray, 0, this.bitmapByteArray.length);
    }

}
