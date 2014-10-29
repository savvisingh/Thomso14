package in.co.sdslabs.mdg.map;

import java.util.Observable;
import java.util.Observer;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

/**
 * View capable of drawing an image at different zoom state levels
 */
public class ImageZoomView extends View implements Observer {

	/** Paint object used when drawing bitmap. */
	private final Paint mPaint = new Paint(Paint.FILTER_BITMAP_FLAG);

	/** Rectangle used (and re-used) for cropping source image. */
	private final Rect mRectSrc = new Rect();

	/** Rectangle used (and re-used) for specifying drawing area on canvas. */
	private final Rect mRectDst = new Rect();

	/** Object holding aspect quotient */
	private final AspectQuotient mAspectQuotient = new AspectQuotient();
	

	/** The bitmap that we're zooming in, and drawing on the screen. */
	private Bitmap mBitmap;

	/** State of the zoom. */
	private ZoomState mState;

	// Public methods

	/**
	 * Constructor
	 */
	public ImageZoomView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	/**
	 * Set image bitmap
	 * 
	 * @param bitmap
	 *            The bitmap to view and zoom into
	 */
	public void setImage(Bitmap bitmap) {
		mBitmap = bitmap;
		mAspectQuotient.updateAspectQuotient(getWidth(), getHeight(),
				mBitmap.getWidth(), mBitmap.getHeight());
		mAspectQuotient.notifyObservers();

		invalidate();
	}

	/**
	 * Set object holding the zoom state that should be used
	 * 
	 * @param state
	 *            The zoom state
	 */
	public void setZoomState(ZoomState state) {
		if (mState != null) {
			mState.deleteObserver(this);
		}

		mState = state;
		mState.addObserver(this);

		invalidate();
	}

	/**
	 * Gets reference to object holding aspect quotient
	 * 
	 * @return Object holding aspect quotient
	 */
	public AspectQuotient getAspectQuotient() {
		return mAspectQuotient;
	}

	// Superclass overrides

	@Override
	protected void onDraw(Canvas canvas) {
		if (mBitmap != null && mState != null) {
			final float aspectQuotient = mAspectQuotient.get();

			final int viewWidth = getWidth();
			final int viewHeight = getHeight();
			final int bitmapWidth = mBitmap.getWidth();
			final int bitmapHeight = mBitmap.getHeight();

			final float panX = mState.getPanX();
			final float panY = mState.getPanY();
			final float zoomX = mState.getZoomX(aspectQuotient) * viewWidth
					/ bitmapWidth;
			final float zoomY = mState.getZoomY(aspectQuotient) * viewHeight
					/ bitmapHeight;

			// Setup source and destination rectangles
			mRectSrc.left = (int) (panX * bitmapWidth - viewWidth / (zoomX * 2));
			mRectSrc.top = (int) (panY * bitmapHeight - viewHeight
					/ (zoomY * 2));
			mRectSrc.right = (int) (mRectSrc.left + viewWidth / zoomX);
			mRectSrc.bottom = (int) (mRectSrc.top + viewHeight / zoomY);
			mRectDst.left = getLeft();
			mRectDst.top = getTop();
			mRectDst.right = getRight();
			mRectDst.bottom = getBottom();

			// Adjust source rectangle so that it fits within the source image.
			if (mRectSrc.left < 0) {
				mRectDst.left += -mRectSrc.left * zoomX;
				mRectSrc.left = 0;
			}
			if (mRectSrc.right > bitmapWidth) {
				mRectDst.right -= (mRectSrc.right - bitmapWidth) * zoomX;
				mRectSrc.right = bitmapWidth;
			}
			if (mRectSrc.top < 0) {
				mRectDst.top += -mRectSrc.top * zoomY;
				mRectSrc.top = 0;
			}
			if (mRectSrc.bottom > bitmapHeight) {
				mRectDst.bottom -= (mRectSrc.bottom - bitmapHeight) * zoomY;
				mRectSrc.bottom = bitmapHeight;
			}

			canvas.drawBitmap(mBitmap, mRectSrc, mRectDst, mPaint);
		}
	}

	// @Override
	// protected void onLayout(boolean changed, int left, int top, int right,
	// int bottom) {
	// super.onLayout(changed, left, top, right, bottom);
	//
	// mAspectQuotient.updateAspectQuotient(right - left, bottom - top,
	// mBitmap.getWidth(),
	// mBitmap.getHeight());
	// mAspectQuotient.notifyObservers();
	// }

	@Override
	protected void onLayout(boolean changed, int left, int top, int right,
			int bottom) {
		// TODO Auto-generated method stub
		super.onLayout(changed, left, top, right, bottom);
		if (mBitmap != null) {
			mAspectQuotient.updateAspectQuotient(right - left, bottom - top,
					mBitmap.getWidth(), mBitmap.getHeight());
			mAspectQuotient.notifyObservers();
		}

	}

	public int getViewLeft() {
		return mRectDst.left;
	}

	public int getViewTop() {
		return mRectDst.top;
	}

	public int getViewWidth() {
		return mRectDst.right - mRectDst.left;
	}

	public int getViewHeight() {
		return mRectDst.bottom - mRectDst.top;
	}

	public int getBitmapLeft() {
		return mRectSrc.left;
	}

	public int getBitmapTop() {
		return mRectSrc.top;
	}

	public int getBitmapWidth() {
		return mRectSrc.right - mRectSrc.left;
	}

	public int getBitmapHeight() {
		return mRectSrc.bottom - mRectSrc.top;
	}

	// implements Observer
	public void update(Observable observable, Object data) {
		invalidate();
	}

}
