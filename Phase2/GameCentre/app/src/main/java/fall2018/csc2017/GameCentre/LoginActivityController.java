package fall2018.csc2017.GameCentre;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class LoginActivityController {

    Class<? extends Activity> accountExistListener(String email, String password, UserAccManager
            accManager, Context context){
        if (accManager.accountExist(email, password)) {
            accManager.setCurrentUser(email);
            //FileSaver.saveToFile(context, accManager, LoginActivity.ACC_INFO);
            //overridePendingTransition(R.anim.slide_inright, R.anim.slide_outleft);
            return GameSelectionActivity.class;
        } else {
            Toast.makeText(context, "Invalid password or email", Toast.LENGTH_SHORT).show();
            return LoginActivity.class;
        }
    }

    UserAccManager loadAccount(UserAccManager userAccManager) {
        if (userAccManager != null) {
            return userAccManager;
        } else {
            return new UserAccManager();
        }
    }
}
