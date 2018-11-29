package fall2018.csc2017.games.SlidingTiles;

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

/**
 * The Gesture Grid View class.
 */
public class GestureDetectGridView extends GridView {
    /**
     * The minimum swipe distance.
     */
    public static final int SWIPE_MIN_DISTANCE = 100;
    /**
     * The gesture detector.
     */
    private GestureDetector gDetector;
    /**
     * The movement controller.
     */
    private MovementController mController;
    /**
     * Whether or not the fling confirmed.
     */
    private boolean mFlingConfirmed = false;
    /**
     * The touch X.
     */
    private float mTouchX;
    /**
     * The touch Y.
     */
    private float mTouchY;

    /**
     * Creates a new Gesture Detect Grid View.
     *
     * @param context the context
     */
    public GestureDetectGridView(Context context) {
        super(context);
        init(context);
    }

    /**
     * Creates a new Gesture Detect Grid View.
     *
     * @param context the context
     * @param attrs   the attributes
     */
    public GestureDetectGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    /**
     * Creates a new Gesture Detect Grid View.
     *
     * @param context      the context
     * @param attrs        the attributes
     * @param defStyleAttr the style attribute
     */
    public GestureDetectGridView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    /**
     * Creates a new Gesture Detect Grid View.
     *
     * @param context      the context
     * @param attrs        the attributes
     * @param defStyleAttr the style attribute
     * @param defStyleRes  the style resource
     */
    @TargetApi(Build.VERSION_CODES.LOLLIPOP) // API 21
    public GestureDetectGridView(Context context, AttributeSet attrs, int defStyleAttr,
                                 int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    /**
     * Initializes given context.
     *
     * @param context the context
     */
    private void init(final Context context) {
        mController = new MovementController();
        gDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {

            @Override
            public boolean onSingleTapConfirmed(MotionEvent event) {
                int position = GestureDetectGridView.this.pointToPosition
                        (Math.round(event.getX()), Math.round(event.getY()));

                mController.processTapMovement(context, position, true);
                return true;
            }

            @Override
            public boolean onDown(MotionEvent event) {
                return true;
            }

        });
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        int action = ev.getActionMasked();
        gDetector.onTouchEvent(ev);

        if (action == MotionEvent.ACTION_CANCEL || action == MotionEvent.ACTION_UP) {
            mFlingConfirmed = false;
        } else if (action == MotionEvent.ACTION_DOWN) {
            mTouchX = ev.getX();
            mTouchY = ev.getY();
        } else {

            if (mFlingConfirmed) {
                return true;
            }

            float dX = (Math.abs(ev.getX() - mTouchX));
            float dY = (Math.abs(ev.getY() - mTouchY));
            if ((dX > SWIPE_MIN_DISTANCE) || (dY > SWIPE_MIN_DISTANCE)) {
                mFlingConfirmed = true;
                return true;
            }
        }

        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return gDetector.onTouchEvent(ev);
    }

    /**
     * Sets the board manager to specified manager.
     *
     * @param boardManager the board manager
     */
    public void setBoardManager(BoardManager boardManager) {
        mController.setBoardManager(boardManager);
    }
}
