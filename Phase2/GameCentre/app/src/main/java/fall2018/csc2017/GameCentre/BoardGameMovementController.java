package fall2018.csc2017.GameCentre;

import android.content.Context;

interface BoardGameMovementController {

    void setBoardManager(AbstractBoardManager boardManager);

    void processTapMovement(Context context, int position);

}

