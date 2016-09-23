package com.android.zlibrary.customView;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Paint.FontMetricsInt;
import android.graphics.Paint.Style;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.android.zlibrary.R;
import com.android.zlibrary.util.ResourceUtil;
import com.android.zlibrary.util.TypeUtil;


/**
 * 滚动选择器 更多详解见博客http://blog.csdn.net/zhongkejingwang/article/details/38513301
 * 
 * @author chenjing
 * 
 */
public class ZPickerView extends View
{

	public static final String TAG = "PickerView";
	/**
	 * text之间间距和minTextSize之比
	 */
	public static final float MARGIN_ALPHA = 2.8f;
	/**
	 * 自动回滚到中间的速度
	 */
	public static final float SPEED = 2;

	private List<String> mDataList;
	/**
	 * 选中的位置，这个位置是mDataList的中心位置，一直不变
	 */
	private int mCurrentSelected,selectIndex;
	private Paint mPaint;

	private float mMaxTextSize = 80;
	private float mMinTextSize = 40;

	private float mMaxTextAlpha = 255;
	private float mMinTextAlpha = 120;

	private int selector_TextColor = 0x333333,normal_TextColor=0x333333;
	private  float selector_TextSize=30, normal_TextSize= 25;
	private int mViewHeight;
	private int mViewWidth;

	private float mLastDownY;
	private int selectTextLength=7;
	/**
	 * 滑动的距离
	 */
	private float mMoveLen = 0;
	private boolean isInit = false;
	private onSelectListener mSelectListener;
	private Timer timer;
	private MyTimerTask mTask;
   private boolean textThumbnail=true;
	Handler updateHandler = new Handler()
	{

		@Override
		public void handleMessage(Message msg)
		{
			if (Math.abs(mMoveLen) < SPEED)
			{
				if (mTask != null)
				{
					mTask.cancel();
					mTask = null;
					mMoveLen = 0;
					performSelect();

				}
			} else
				// 这里mMoveLen / Math.abs(mMoveLen)是为了保有mMoveLen的正负号，以实现上滚或下滚
				mMoveLen = mMoveLen - mMoveLen / Math.abs(mMoveLen) * SPEED;

			invalidate();
		}

	};

	public ZPickerView(Context context)
	{
		super(context);
		init();
	}

	public ZPickerView(Context context, AttributeSet attrs)
	{
		this(context, attrs,0);
	}
	public ZPickerView(Context context, AttributeSet attrs,int defStyle)
	{
		super(context, attrs,defStyle);
		TypedArray a=context.obtainStyledAttributes(attrs,R.styleable.ZPickerView);
		selector_TextColor=a.getColor(R.styleable.ZPickerView_select_textcolor,0xff333333);
		normal_TextColor=a.getColor(R.styleable.ZPickerView_normal_textcolor,0xff333333);
		selector_TextSize=a.getDimensionPixelSize(R.styleable.ZPickerView_select_textsize,TypeUtil.dp2px(17));
		normal_TextSize=a.getDimensionPixelSize(R.styleable.ZPickerView_normal_textsize, TypeUtil.dp2px(15));
/*		mMaxTextSize=a.getDimensionPixelSize(R.styleable.ZPickerView_select_textsize,40);
		mMinTextSize=a.getDimensionPixelSize(R.styleable.ZPickerView_normal_textsize,25);*/
		a.recycle();
		init();
	}
  public ZPickerView setSelectTextSize(int selector_TextSize)
  {
	  this.selector_TextSize=selector_TextSize;
	  return this;
  }
	public ZPickerView setNormalTextSize(int normal_TextSize)
	{
		this.normal_TextSize=normal_TextSize;
		return this;
	}

//	文字超出缩率
	public ZPickerView setTextThumbnail(boolean textThumbnail)
	{
		this.textThumbnail=textThumbnail;
		return this;
	}
	public void setOnSelectListener(onSelectListener listener)
	{
		mSelectListener = listener;
	}

	public ZPickerView setTextThumbnailNum(int selectTextLength)
	{
		this.selectTextLength=selectTextLength;
		return this;
	}
	private void performSelect()
	{

		if (mSelectListener != null)
			mSelectListener.onSelect(mDataList.get(mCurrentSelected),selectIndex);
	}

	public void setData(List<String> datas)
	{
		mDataList = datas;
		selectIndex=0;
		mCurrentSelected = datas.size() / 2;
		invalidate();
	}

	/**
	 * 选择选中的item的index
	 * 
	 * @param selected
	 */
	public void setSelected(int selected)
	{
		selectIndex=selected;
		mCurrentSelected = selected;
		int distance = mDataList.size() / 2 - mCurrentSelected;
		if (distance < 0)
			for (int i = 0; i < -distance; i++)
			{
				moveHeadToTail();
				mCurrentSelected--;

			}
		else if (distance > 0)
			for (int i = 0; i < distance; i++)
			{
				moveTailToHead();
				mCurrentSelected++;
			}
		invalidate();
	}

	/**
	 * 选择选中的内容
	 * 
	 * @param mSelectItem
	 */
	public void setSelected(String mSelectItem)
	{
		for (int i = 0; i < mDataList.size(); i++)
			if (mDataList.get(i).equals(mSelectItem))
			{
				setSelected(i);
				break;
			}
	}

	private void moveHeadToTail()
	{
		String head = mDataList.get(0);
		mDataList.remove(0);
		mDataList.add(head);
	}

	private void moveTailToHead()
	{
		String tail = mDataList.get(mDataList.size() - 1);
		mDataList.remove(mDataList.size() - 1);
		mDataList.add(0, tail);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
	{
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		mViewHeight = getMeasuredHeight();
		mViewWidth = getMeasuredWidth();
		// 按照View的高度计算字体大小
		mMaxTextSize = mViewHeight / 4.0f;
		mMinTextSize = mMaxTextSize / 2f;
		isInit = true;
		invalidate();
	}

	private void init()
	{

		timer = new Timer();
		mDataList = new ArrayList<String>();
		mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		mPaint.setStyle(Style.FILL);
		mPaint.setTextAlign(Align.CENTER);

	}
	@Override
	protected void onDraw(Canvas canvas)
	{
		super.onDraw(canvas);
		// 根据index绘制view
		if (isInit)
			drawData(canvas);
	}

	private void drawData(Canvas canvas)
	{
		mPaint.setColor(selector_TextColor);
		// 先绘制选中的text再往上往下绘制其余的text
		float scale = parabola(mViewHeight / 4.0f, mMoveLen);
		float size = (mMaxTextSize - mMinTextSize) * scale + mMinTextSize;
		mPaint.setTextSize(selector_TextSize);
		mPaint.setAlpha((int) ((mMaxTextAlpha - mMinTextAlpha) * scale + mMinTextAlpha));
		// text居中绘制，注意baseline的计算才能达到居中，y值是text中心坐标
		float x = (float) (mViewWidth / 2.0);
		float y = (float) (mViewHeight / 2.0 + mMoveLen);
		FontMetricsInt fmi = mPaint.getFontMetricsInt();
		float baseline = (float) (y - (fmi.bottom / 2.0 + fmi.top / 2.0));
		String text;
		if(textThumbnail) {
			if (mDataList.get(mCurrentSelected).length() > selectTextLength)
				text = mDataList.get(mCurrentSelected).substring(0, selectTextLength) + "..";
			else
				text = mDataList.get(mCurrentSelected);

		}
		else
			text = mDataList.get(mCurrentSelected);
		canvas.drawText(text, x, baseline, mPaint);
		// 绘制上方data
		for (int i = 1; (mCurrentSelected - i) >= 0; i++)
		{
			drawOtherText(canvas, i, -1);
		}
		// 绘制下方data
		for (int i = 1; (mCurrentSelected + i) < mDataList.size(); i++)
		{
			drawOtherText(canvas, i, 1);
		}

	}

	/**
	 * @param canvas
	 * @param position
	 *            距离mCurrentSelected的差值
	 * @param type
	 *            1表示向下绘制，-1表示向上绘制
	 */
	private void drawOtherText(Canvas canvas, int position, int type)
	{
		mPaint.setColor(normal_TextColor);
		float d = (float) (MARGIN_ALPHA * mMinTextSize * position + type
				* mMoveLen);
		float scale = parabola(mViewHeight / 4.0f, d);
		float size = (mMaxTextSize - mMinTextSize) * scale + mMinTextSize;
		mPaint.setTextSize(normal_TextSize);
		mPaint.setAlpha((int) ((mMaxTextAlpha - mMinTextAlpha) * scale + mMinTextAlpha));
		float y = (float) (mViewHeight / 2.0 + type * d);
		FontMetricsInt fmi = mPaint.getFontMetricsInt();
		float baseline = (float) (y - (fmi.bottom / 2.0 + fmi.top / 2.0));
		canvas.drawText(mDataList.get(mCurrentSelected + type * position),
				(float) (mViewWidth / 2.0), baseline, mPaint);
	}

	/**
	 * 抛物线
	 * 
	 * @param zero
	 *            零点坐标
	 * @param x
	 *            偏移量
	 * @return scale
	 */
	private float parabola(float zero, float x)
	{
		float f = (float) (1 - Math.pow(x / zero, 2));
		return f < 0 ? 0 : f;
	}

	@Override
	public boolean onTouchEvent(MotionEvent event)
	{
		switch (event.getActionMasked())
		{
		case MotionEvent.ACTION_DOWN:
			doDown(event);
			break;
		case MotionEvent.ACTION_MOVE:
			doMove(event);
			break;
		case MotionEvent.ACTION_UP:
			doUp(event);
			break;
		}
		return true;
	}

	private void doDown(MotionEvent event)
	{
		if (mTask != null)
		{
			mTask.cancel();
			mTask = null;
		}
		mLastDownY = event.getY();
	}

	private void doMove(MotionEvent event)
	{

		mMoveLen += (event.getY() - mLastDownY);

		if (mMoveLen > MARGIN_ALPHA * mMinTextSize / 2)
		{
			// 往下滑超过离开距离
			moveTailToHead();
			selectIndex--;
			if(selectIndex<0)
				selectIndex=selectIndex+mDataList.size();
			mMoveLen = mMoveLen - MARGIN_ALPHA * mMinTextSize;
		} else if (mMoveLen < -MARGIN_ALPHA * mMinTextSize / 2)
		{
			// 往上滑超过离开距离
			moveHeadToTail();
			selectIndex++;
			if(selectIndex>mDataList.size())
				selectIndex=selectIndex-mDataList.size();
			mMoveLen = mMoveLen + MARGIN_ALPHA * mMinTextSize;
		}

		mLastDownY = event.getY();
		invalidate();
	}

	private void doUp(MotionEvent event)
	{
		// 抬起手后mCurrentSelected的位置由当前位置move到中间选中位置
		if (Math.abs(mMoveLen) < 0.0001)
		{
			mMoveLen = 0;
			return;
		}
		if (mTask != null)
		{
			mTask.cancel();
			mTask = null;
		}
		mTask = new MyTimerTask(updateHandler);
		timer.schedule(mTask, 0, 10);
	}

	class MyTimerTask extends TimerTask
	{
		Handler handler;

		public MyTimerTask(Handler handler)
		{
			this.handler = handler;
		}

		@Override
		public void run()
		{
			handler.sendMessage(handler.obtainMessage());
		}

	}

	public interface onSelectListener
	{
		void onSelect(String text,int index);
	}
}
