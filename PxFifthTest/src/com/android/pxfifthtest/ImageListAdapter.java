package com.android.pxfifthtest;

import java.util.ArrayList;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.widget.pxfifthtest.R;
import com.kiumiu.ca.api500px.primitiveDataType.PhotoShort;
import com.squareup.picasso.Picasso;

public class ImageListAdapter extends BaseAdapter {
	int count;
	boolean first = true;
	ArrayList<PhotoShort> mImageList;
	Context mContext;
	int mTodayPosition = 0;

	public ImageListAdapter(Context context,
			ArrayList<PhotoShort> aArticlesList) {
		super();
		mContext = context;
		mImageList = aArticlesList;
	}

	public void appendPhotos(ArrayList<PhotoShort> photos) {
		mImageList.addAll(photos);
	}

	@Override
	public int getCount() {
		if (mImageList != null)
			count = mImageList.size();
		return count;
	}	

	@Override
	public Object getItem(int position) {
		return mImageList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	static class ViewHolder {
		TextView mHeaderText;
		TextView mLeadText;
		// used in calendar list view to show group header
		ImageView mTitleImageView;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		ViewHolder holder;
		View rowView = convertView;
		if (rowView == null) {
		
			rowView = View.inflate(mContext, R.layout.list_item, null);
			holder = new ViewHolder();
			holder.mTitleImageView = (ImageView) rowView
					.findViewById(R.id.title_image);
			holder.mHeaderText = (TextView) rowView
					.findViewById(R.id.header_textview);
			holder.mLeadText = (TextView) rowView
					.findViewById(R.id.lead_textview);
			rowView.setTag(holder);
		} else {
			holder = (ViewHolder) rowView.getTag();
		}
		PhotoShort photoShort = mImageList.get(position);
		Picasso.with( mContext ).load( photoShort.image_url ).into( holder.mTitleImageView);
		if (photoShort.name != null && photoShort.name.length() > 0) {
			holder.mHeaderText.setText(photoShort.name);			
		}
		if (photoShort.description != null
				&& photoShort.description.length() > 0) {
			holder.mLeadText.setText(photoShort.description);			
		}		

		return rowView;
	}

	public void setCurrentArticlesList(ArrayList<PhotoShort> aArticlesList) {
		mImageList = aArticlesList;
	}
}
