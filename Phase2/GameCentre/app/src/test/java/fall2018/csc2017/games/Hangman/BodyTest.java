package fall2018.csc2017.games.Hangman;

import android.support.v7.app.AppCompatActivity;
import android.view.View;


import org.junit.Before;
import org.junit.Test;


import fall2018.csc2017.games.R;

import static junit.framework.Assert.*;

public class BodyTest {

    private HangmanBody body;

    @Before
    public void setUp() {
        body = new HangmanBody();
    }

    //todo: how to test these without having this file be an acitivity?
    //due to findViewById
    //is there another way to create view objects to test on?
//    @Test
//    public void testinitBodyParts(){
//        body.initBodyParts(findViewById(R.id.head), findViewById(R.id.body),
//            findViewById(R.id.arm1), findViewById(R.id.arm2), findViewById(R.id.leg1),
//            findViewById(R.id.leg2));
//        assertTrue(body.getBodyParts() != null);
//
//    }
//
//    @Test
//    public void testCreate(){
//        body.initBodyParts(findViewById(R.id.head), findViewById(R.id.body),
//                findViewById(R.id.arm1), findViewById(R.id.arm2), findViewById(R.id.leg1),
//                findViewById(R.id.leg2));
//        body.createHangman();
//        for (int p = 0; p < body.getNUMPARTS(); p++) {
//            assertTrue(body.getBodyParts()[p].getVisibility() == View.VISIBLE);
//        }
//
//    }


}
