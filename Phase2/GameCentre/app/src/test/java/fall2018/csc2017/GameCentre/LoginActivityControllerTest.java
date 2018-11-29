package fall2018.csc2017.GameCentre;

import android.content.Context;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

public class LoginActivityControllerTest {

    private LoginActivityController lController;

    private GameSelectionActivity gameSelectionActivity;
    private LoginActivity loginActivity;
    private UserAccManager accManager;

    private Context context = mock(Context.class);

    private void setUp(){
        lController = new LoginActivityController();
        gameSelectionActivity = new GameSelectionActivity();
        loginActivity = new LoginActivity();
    }

    private void setUpUserAcc(){
        accManager = new UserAccManager();
        accManager.writeAcc("a","a", context);
    }

    @Test
    public void testAccExistListener(){
        setUp();
        setUpUserAcc();
        assertEquals(gameSelectionActivity.getClass(),
                lController.accountExistListener("a", "a", accManager, context));
        //assertEquals(loginActivity.getClass(),
                //lController.accountExistListener("b", "a", accManager, context));
    }

    @Test
    public void testLoadAccount(){
        setUp();
        setUpUserAcc();
        assertEquals(accManager, lController.loadAccount(accManager));
        assertEquals(UserAccManager.class,
                lController.loadAccount(null).getClass());
    }
}
