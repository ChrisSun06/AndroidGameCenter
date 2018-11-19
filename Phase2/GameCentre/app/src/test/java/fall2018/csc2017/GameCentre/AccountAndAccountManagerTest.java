package fall2018.csc2017.GameCentre;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.junit.Assert.*;

public class AccountAndAccountManagerTest {

    UserAccManager accManager;

    private void setUp(){
        this.accManager = UserAccManager.getInstance();
    }
}
