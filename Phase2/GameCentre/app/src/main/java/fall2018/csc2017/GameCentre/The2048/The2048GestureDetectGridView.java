package fall2018.csc2017.GameCentre.The2048;

/*
Adapted from:
https://github.com/DaveNOTDavid/sample-puzzle/blob/master/app/src/main/java/com/davenotdavid/samplepuzzle/GestureDetectGridView.java

This extension of GridView contains built in logic for handling swipes between buttons
 */

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.GridView;
import android.widget.TextView;

import fall2018.csc2017.GameCentre.R;

public class The2048GestureDetectGridView extends GridView {
    public static final int flingMin = 100;
    public static final int VelocityMin = 100;
    //public static final int SWIPE_THRESHOLD_VELOCITY = 100;
    private GestureDetector gDetector;
    private The2048MovementController mController;

    private The2048BoardManager boardManager;

    public The2048GestureDetectGridView(Context context) {
        super(context);
        init(context);
    }

    public The2048GestureDetectGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public The2048GestureDetectGridView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP) // API 21
    public The2048GestureDetectGridView(Context context, AttributeSet attrs, int defStyleAttr,
                                        int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    private void init(Context context) {
        mController = new The2048MovementController();
        The2048GestureListener gListener = new The2048GestureListener();
        gListener.setContext(context);
        gListener.setController(mController);
        gDetector = new GestureDetector(context, gListener);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event){
        gDetector.onTouchEvent(event);
        return super.onTouchEvent(event);
    }

    public void setBoardManager(The2048BoardManager boardManager) {
        this.boardManager = boardManager;
        mController.setBoardManager(boardManager);
    }

    public void processUndo(){
        mController.processUndo(getContext());
    }

    public void UpdateScore(){
        TextView score = ((The2048GameActivity)getContext()).findViewById(R.id.scoreTextView);
        score.setText(getResources().getString(R.string.Score,
                String.valueOf(boardManager.getBoard().getScore())));
    }
}

