package fall2018.csc2017.GameCentre.The2048;

import android.content.Context;
import android.view.GestureDetector;
import android.view.MotionEvent;

class The2048GestureListener extends GestureDetector.SimpleOnGestureListener {

    private float flingMin = 100;
    private float velocityMin = 100;
    private The2048MovementController mController;
    private Context context;

    public void setContext(Context context) {
        this.context = context;
    }

    public void setController(The2048MovementController mController) {
        this.mController = mController;
    }
    @Override
    public boolean onDown(MotionEvent e) {
        return false;
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
                mController.processMovement(context, "row", true);
            } else {
                //swipe right
                mController.processMovement(context, "row", false);
            }
        } else if (absVDiff > flingMin && absVelocityY > velocityMin) {
            if (verticalDiff > 0) {
                //swipe down
                mController.processMovement(context, "column", true);
            } else {
                //swipe up
                mController.processMovement(context, "column", false);
            }
        }
        return true;
    }
}
