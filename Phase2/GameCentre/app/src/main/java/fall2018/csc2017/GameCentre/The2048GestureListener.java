package fall2018.csc2017.GameCentre;

import android.content.Context;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.Toast;

import java.util.List;
import java.util.Set;

class The2048GestureListener extends GestureDetector.SimpleOnGestureListener {
    private float flingMin = 100;
    private float velocityMin = 100;
    private String direction;
    private boolean directionValue;
    private The2048MovementController mController;

    @Override
    public boolean onDown(MotionEvent e) {
        return true;
    }

    public Context context;

    public void setContext(Context context) {
        this.context = context;
    }


    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {

        float horizontalDiff = e2.getX() - e1.getX();
        float verticalDiff = e2.getY() - e1.getY();
        float absHDiff = Math.abs(horizontalDiff);
        float absVDiff = Math.abs(verticalDiff);
        float absVelocityX = Math.abs(velocityX);
        float absVelocityY = Math.abs(velocityY);

        if (absHDiff > absVDiff && absHDiff > flingMin && absVelocityX > absVelocityY) {
            if (horizontalDiff > 0) {
                //swipe left
                mController.processMovement(context, "row", false);
            } else {
                //swipe right
                mController.processMovement(context, "row", true);
            }
        } else if (absVDiff > flingMin && absVelocityY > velocityMin) {
            if (verticalDiff > 0) {
                //swipe down
                mController.processMovement(context, "col", true);
            } else {
                //swipe up
                mController.processMovement(context, "col", false);
            }
        }

        return true;
    }

}
