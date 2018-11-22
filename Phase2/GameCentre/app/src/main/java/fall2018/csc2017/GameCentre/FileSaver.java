package fall2018.csc2017.GameCentre;


import android.content.Context;
import android.util.Log;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import static android.content.Context.MODE_PRIVATE;

public class FileSaver {

    public static void saveToFile(Context context, Object obj, String fileLocation){
        try {
            ObjectOutputStream os =
                    new ObjectOutputStream(context.openFileOutput(fileLocation, MODE_PRIVATE));
            os.writeObject(obj);
            os.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Object loadFromFile(Context context, String fileLocation){
        try {
            return loadFile(context, fileLocation);
        } catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
            return null;
        } catch (IOException ioe) {
            Log.e("login activity", "Can not read file: " + ioe.toString());
            return null;
        } catch (ClassNotFoundException cne) {
            Log.e("login activity", "File contained unexpected data type: " + cne.toString());
            return null;
        }
    }

    public static Object loadFile(Context c, String fileLocation) throws IOException,
            ClassNotFoundException {
        Object temp = new Object();
        InputStream inputStream = c.openFileInput(fileLocation);
        if (inputStream != null) {
            ObjectInputStream input = new ObjectInputStream(inputStream);
            temp = input.readObject();
            inputStream.close();
        }
        return temp;
    }


}
