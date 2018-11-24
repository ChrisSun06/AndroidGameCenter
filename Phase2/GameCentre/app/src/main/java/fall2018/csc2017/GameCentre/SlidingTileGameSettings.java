package fall2018.csc2017.GameCentre;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class SlidingTileGameSettings extends ImageOperationActivity {

    public final static int PICK_IMAGE = 1046;
    /**
     * The edit text of number of undo user chose.
     */
    private EditText undoNum;

    /**
     * The edit text of user's choice of url address of the photo.
     */
    private EditText urlAddress;

    private UserAccManager accManager;

    /**
     * Activate all the buttons when InGameSettings is created
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_in_game_settings);
        accManager = (UserAccManager) getIntent().getSerializableExtra("accManager");
        addConfirmUndoNumButtonListener();
        addUrlButtonListener();
        addChooseFromGalleryButtonListener();
        addSetDefaultTileBackgroundButtonListener();
        addSetBatmanTileBackgroundButtonListener();
        addSetSupermanTileBackgroundButtonListener();
    }

    /**
     * Activate Confirm Button for undo
     */
    private void addConfirmUndoNumButtonListener() {
        ImageButton confirmUndoButton = findViewById(R.id.undoConfirm);
        confirmUndoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                undoNum = findViewById(R.id.chooseUndo);

                try{
                int num = Integer.parseInt(undoNum.getText().toString());
                accManager.updateUndoTime(num);
                makeToastSetUndo(num);
                InputMethodManager imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);}
                catch (NumberFormatException e){
                    Toast.makeText(getApplicationContext(), "Please type in an Integer",
                            Toast.LENGTH_LONG).show();}
            }

        });
    }

    /**
     * Activate URL Button for undo
     */
    private void addUrlButtonListener(){
        ImageButton confirmURLButton = findViewById(R.id.URLConfirm);
        confirmURLButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                urlAddress = findViewById(R.id.urlInput);
                final String address = urlAddress.getText().toString();
                AsyncTask.execute(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            URL url = new URL(address);
                            Bitmap bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                            Bitmap resized = resizeSourceImage(bmp);
                            createAllCroppedTiles(resized);
                        }
                        catch (MalformedURLException e) {
                            e.printStackTrace();
                        }
                        catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
                InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
            }
        });
    }


    /**
     * Activate set default tile Button
     */
    private void addSetDefaultTileBackgroundButtonListener() {
        Button setDefaultButton = findViewById(R.id.SetDefault);
        setDefaultButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createAllDefaultNumberTiles(true);
                makeToastChangeBackground();
            }
        });
    }

    /**
     * Activate set batman tile Button
     */
    private void addSetBatmanTileBackgroundButtonListener() {
        Button setBatmanButton = findViewById(R.id.SetBatman);
        setBatmanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bitmap batman = BitmapFactory.decodeFile(getImagePath("batman"));
                createAllCroppedTiles(batman);
                makeToastChangeBackground();
            }
        });
    }

    /**
     * Activate set Superman tile Button.
     */
    private void addSetSupermanTileBackgroundButtonListener() {
        Button setSupermanButton = findViewById(R.id.SetSuperman);
        setSupermanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bitmap superman = BitmapFactory.decodeFile(getImagePath("superman"));
                createAllCroppedTiles(superman);
                makeToastChangeBackground();
            }
        });
    }

    /**
     * Activate choose from gallery Button.
     */
    private void addChooseFromGalleryButtonListener() {
        Button chooseFromGalleryButton = findViewById(R.id.ChooseFromGalleryButton);
        chooseFromGalleryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK,
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(intent, PICK_IMAGE);
                }
            }
        });
    }


    /**
     * Recieve pick image request
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data != null && requestCode == PICK_IMAGE && resultCode == RESULT_OK) {
            try {
                Uri photoUri = data.getData();
                Bitmap selectedImage = MediaStore.Images.Media.
                        getBitmap(this.getContentResolver(), photoUri);
                Bitmap resized = resizeSourceImage(selectedImage);
                createAllCroppedTiles(resized);
                makeToastChangeBackground();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Save user preferences when closing.
     */
    @Override
    protected void onStop() {
        super.onStop();
        FileSaver.saveToFile(getApplicationContext(), accManager,
                LoginActivity.ACC_INFO);
        //UserAccManager.getInstance().writeAccManager(getApplicationContext());
    }

    /**
     * Make toast for the background change
     */
    private void makeToastChangeBackground() {
        Toast.makeText(getApplicationContext(),
                "Tile Background Changed", Toast.LENGTH_SHORT).show();
    }

    /**
     * Make toast for undo SetUndo
     */
    private void makeToastSetUndo(int i) {
        Toast.makeText(this, "Undo Set To" + i, Toast.LENGTH_SHORT).show();
    }
}
