package com.android.zlibrary.base;

import android.app.Activity;
import android.os.Handler;
import android.os.Looper;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

public abstract class SDBaseAdapter<T> extends BaseAdapter
{
	private Handler mHandler ;
	protected List<T> mListModel = new ArrayList<T>();
	protected ViewGroup mParent;
	protected LayoutInflater mInflater;
	public Activity mActivity;
	protected EnumAdapterMode mMode = EnumAdapterMode.SINGLE;
	protected View mView;

	/** 单选模式时候被选中的项 */
	protected int mSelectedPosition = -1;
	/** 多选模式时候被选中的项集合 */
	protected List<T> mListSelectedModel = new ArrayList<T>();

	/**
	 * 返回adapter的模式（多选，单选）
	 * 
	 * @return
	 */
	public EnumAdapterMode getmMode()
	{
		return mMode;
	}

	/**
	 * 设置adapter的模式（多选，单选）
	 * 
	 * @param mode
	 */
	public void setmMode(EnumAdapterMode mode)
	{
		this.mMode = mode;
	}

	public void setmView(View mView)
	{
		this.mView = mView;
	}

	/**
	 * 获得选中的项
	 * 
	 * @return
	 */
	public int getmSelectedPosition()
	{
		return mSelectedPosition;
	}

	public T getmSelectedModel()
	{
		return getItem(mSelectedPosition);
	}

	/**
	 * 获得选中的list实体集合
	 * 
	 * @return
	 */
	public List<T> getmListSelectedModel()
	{
		return mListSelectedModel;
	}

	/**
	 * 设置选中的项
	 * 
	 * @param position
	 *            项的位置
	 * @param selected
	 *            true选中，false未选中
	 */
	public void setmSelectedPosition(int position, boolean selected)
	{
		if (isPositionLegal(position))
		{
			switch (getmMode())
			{
			case SINGLE:
				if (selected) // 如果设置选中
				{
					if (mSelectedPosition >= 0) // 如果存在旧的位置，把旧的位置设置为未选
					{
						onSelectedChange(mSelectedPosition, false, false);
					}
					onSelectedChange(position, true, true); // 让新的位置选中
					mSelectedPosition = position;
				} else
				{
					onSelectedChange(position, false, false);
					if (mSelectedPosition == position)
					{
						mSelectedPosition = -1;
					}
				}
				break;
			case MULTI:
				T model = mListModel.get(position);
				if (selected)
				{
					if (!mListSelectedModel.contains(model))
					{
						mListSelectedModel.add(model);
						onSelectedChange(position, true, true);
					} else
					{
						mListSelectedModel.remove(model);
						onSelectedChange(position, false, true);
					}
				} else
				{
					if (mListSelectedModel.contains(model))
					{
						mListSelectedModel.remove(model);
						onSelectedChange(position, false, true);
					}
				}
				break;
			default:
				break;
			}

		}
	}

	/**
	 * 当调用public void setmSelectedPosition(int position, boolean
	 * selected)方法后，会回调这个方法，次方法用来重写，改变实体状态
	 * 
	 * @param position
	 *            项位置
	 * @param selected
	 *            true选中，false未选中
	 * @param notify
	 *            是否需要刷新adapter
	 */
	protected void onSelectedChange(int position, boolean selected, boolean notify)
	{

	}

	public void addSelectedModel(T model)
	{
		if (!mListSelectedModel.contains(model))
		{
			mListSelectedModel.add(model);
		}
	}

	public void removeSelectedModel(T model)
	{
		if (mListSelectedModel.contains(model))
		{
			mListSelectedModel.remove(model);
		}
	}

	public SDBaseAdapter(List<T> listModel, Activity activity)
	{
		mHandler = new Handler(Looper.getMainLooper());
		setData(listModel);
		this.mActivity = activity;
		this.mInflater = mActivity.getLayoutInflater();

	}

	/**
	 * 获得adapter的实体集合
	 * 
	 * @return
	 */
	public List<T> getData()
	{
		return mListModel;
	}

	/**
	 * 更新adapter的数据集合，并刷新adapter
	 * 
	 * @param listModel
	 */
	public void updateData(List<T> listModel)
	{
		setData(listModel);
		this.notifyDataSetChanged();
	}

	/**
	 * 给adapter设置数据
	 * 
	 * @param listModel
	 */
	public void setData(List<T> listModel)
	{
		if (listModel != null)
		{
			this.mListModel = listModel;
		} else
		{
			this.mListModel = new ArrayList<T>();
		}
		resetSelection();
	}

	private void resetSelection()
	{
		mListSelectedModel.clear();
		mSelectedPosition = -1;
	}

	public void clearSelection()
	{
		// 清除单选模式选中实体
		if (mSelectedPosition >= 0)
		{
			setmSelectedPosition(mSelectedPosition, false);
		}

		// 清除多选模式选中实体
		for (T model : mListSelectedModel)
		{
			int position = indexOf(model);
			setmSelectedPosition(position, false);
		}
		resetSelection();
	}

	public boolean isPositionLegal(int position)
	{
		if (mListModel != null && !mListModel.isEmpty() && position >= 0 && position < mListModel.size())
		{
			return true;
		} else
		{
			return false;
		}
	}

	@Override
	public int getCount()
	{
		if (mListModel != null)
		{
			return mListModel.size();
		} else
		{
			return 0;
		}
	}

	@Override
	public T getItem(int position)
	{
		if (isPositionLegal(position))
		{
			return mListModel.get(position);
		} else
		{
			return null;
		}
	}

	@Override
	public long getItemId(int position)
	{
		return position;
	}

	@SuppressWarnings("unchecked")
	public static <V extends View> V get(int id, View convertView)
	{
		SparseArray<View> viewHolder = (SparseArray<View>) convertView.getTag();
		if (viewHolder == null)
		{
			viewHolder = new SparseArray<View>();
			convertView.setTag(viewHolder);
		}
		View childView = viewHolder.get(id);
		if (childView == null)
		{
			childView = convertView.findViewById(id);
			viewHolder.put(id, childView);
		}
		return (V) childView;
	}

	@SuppressWarnings("unchecked")
	public static <V extends View> V find(int id, View convertView)
	{
		return (V) convertView.findViewById(id);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		return getView(position, convertView, parent, getItem(position));
	}

	public View getView(int position, View convertView, ViewGroup parent, T model)
	{
		return convertView;
	}

	public void getViewUpdate(int position, View convertView, ViewGroup parent)
	{
		mParent = parent;
		convertView.setId(position);
	}

	/**
	 * 更新某一item项的布局，要在getview中调用getViewUpdate(int position, View convertView,
	 * ViewGroup parent)方法后此方法才有效
	 * 
	 * @param position
	 */
	public void updateItem(int position)
	{
		if (mParent != null && isPositionLegal(position))
		{
			View itemView = mParent.findViewById(position);
			if (itemView != null)
			{
				getView(position, itemView, mParent);
			}
		}
	}

	public void updateItem(T model)
	{
		updateItem(indexOf(model));
	}

	public void removeItem(int position)
	{
		if (isPositionLegal(position))
		{
			mListModel.remove(position);
			notifyDataSetChanged();
		}
	}

	public void removeItem(T t)
	{
		removeItem(indexOf(t));
	}

	public void notifyDataSetChanged(long delay)
	{
		if (delay < 0)
		{
			delay = 0;
		}
		runOnUiThreadDelayed(new Runnable()
		{

			@Override
			public void run()
			{
				notifyDataSetChanged();
			}
		}, delay);
	}
	public  void runOnUiThreadDelayed(Runnable r, long delayMillis)
	{
		synchronized (mHandler)
		{
			mHandler.postDelayed(r, delayMillis);
		}
	}
	public int indexOf(T t)
	{
		int index = -1;
		if (t != null && mListModel != null)
		{
			index = mListModel.indexOf(t);
		}
		return index;
	}

	public enum EnumAdapterMode
	{
		/** 单选模式 */
		SINGLE,
		/** 多选模式 */
		MULTI;
	}

}
