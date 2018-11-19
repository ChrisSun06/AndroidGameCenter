package fall2018.csc2017.GameCentre;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.util.HashMap;


/**
 * The login activity.
 */
public class LoginActivity extends AppCompatActivity {

    /**
     * The email EditText
     */
    private EditText mEmailView;

    /**
     * The password EditText
     */
    private EditText mPasswordView;

    /**
     * The file that stores accounts information
     */
    public static final String ACC_INFO = "acc_save.ser";


    /**
     * The account manager
     */
    public static UserAccManager accManager;

    /**
     * The file saver
     */
    public static FileSaver fileSaver;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        accManager = UserAccManager.getInstance();
        fileSaver = FileSaver.getInstance();
        mEmailView = findViewById(R.id.emailEdit);
        mPasswordView = findViewById(R.id.passwordEdit);
        loadAccounts();
        addLoginButtonListener();
        addRegisterButtonListener();
    }

    /**
     * Load the UserAccManager.
     */
    private void loadAccounts(){
        UserAccManager tempManager = (UserAccManager)fileSaver.loadFromFile
                (getApplicationContext(), ACC_INFO);
        if (tempManager != null){
            accManager.setAccountMap(tempManager.getAccountMap());
        }
        //accManager.loadAccManager(getApplicationContext());
    }

    /**
     * Activate login button
     */
    private void addLoginButtonListener() {
        Button loginButton = findViewById(R.id.login);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (accManager.accountExist(mEmailView.getText().toString(),
                        mPasswordView.getText().toString())) {
                    //currentUser = mEmailView.getText().toString();
                    accManager.setCurrentUser(mEmailView.getText().toString());
                    Intent intent = new Intent(LoginActivity.this,
                            GameSelectionActivity.class);
                    intent.putExtra("accountManager", accManager);
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_inright, R.anim.slide_outleft);
                } else {
                    makeToastInvalidText();
                }
            }
        });
    }

    /**
     * Make a toast message for invalid password or email
     */
    private void makeToastInvalidText() {
        Toast.makeText(this, "Invalid password or email", Toast.LENGTH_SHORT).show();
    }

    /**
     * Activate register button
     */
    private void addRegisterButtonListener(){
        Button registerButton = findViewById(R.id.register);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                writeAccount(mEmailView.getText().toString(), mPasswordView.getText().toString());
            }
        });
    }

    /**
     * Write new account into UserAccManager and save it into local storage
     * If the username is already registered, then cannot register this account again.
     *
     * @param email email/username
     * @param password password of the user
     */
    private void writeAccount(String email, String password) {
        if (accManager.getAccountMap().containsKey(email)){
            Toast.makeText(this, "Username already taken!", Toast.LENGTH_SHORT).show();
        } else if (password.equals("") || email.equals("")){
            Toast.makeText(this, "Field cannot be empty!", Toast.LENGTH_SHORT).show();
        } else {
            accManager.writeAcc(email, password);
            FileSaver.getInstance().saveToFile(getApplicationContext(), UserAccManager.getInstance()
                    , LoginActivity.ACC_INFO);
            //accManager.writeAccManager(getApplicationContext());
        }
    }

}