package com.android.pxfifthtest;

import java.util.ArrayList;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;

import com.android.pxfifthtest.ImageListView.OnLoadMoreListener;
import com.android.widget.pxfifthtest.R;
import com.kiumiu.ca.api500px.primitiveDataType.PhotoShort;
import com.kiumiu.ca.api500px.response.photo.get_photos_response;

public class MainActivity extends ListActivity {

	private int mPage = 1;
	public static final int IMAGE_PER_PAGE = 25;
	public static final int DEFAULT_IMAGE_SIZE = 1;
	public static final String mFeature = "popular";
	private ProgressDialog mProgressBar;
	private AlertDialog mAlertDialog;
	ImageListAdapter mImageListAdapter;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.image_list_view);
		initProgressDialog();
		initProblemDialogShow();
		new LoadDataTask().execute();
		// set a listener to be invoked when the list reaches the end
		((ImageListView) getListView())
				.setOnLoadMoreListener(new OnLoadMoreListener() {
					@Override
					public void onLoadMore() {
						new LoadDataTask().execute();
					}
				});
	}

	private class LoadDataTask extends
			AsyncTask<Void, Void, get_photos_response> {

		@Override
		protected void onPreExecute() {
			if (mPage == 1) {
				mProgressBar.show();
			}
		}

		@Override
		protected get_photos_response doInBackground(Void... params) {
			return ApiHelper.getPhotoStream(MainActivity.this, mFeature,
					IMAGE_PER_PAGE, DEFAULT_IMAGE_SIZE, mPage);
		}

		@Override
		protected void onPostExecute(get_photos_response response) {

			((ImageListView) getListView()).onLoadMoreComplete();
			if (response != null) {
				updateList(response);
				mPage++;
			} else {
				if (!mAlertDialog.isShowing()) {
					mAlertDialog.show();
				}
				this.cancel(false);
			}
			if (mProgressBar.isShowing()) {
				mProgressBar.dismiss();
			}
			super.onPostExecute(null);
		}

		@Override
		protected void onCancelled() {
			// Notify the loading more operation has finished
			((ImageListView) getListView()).onLoadMoreComplete();
		}
	}

	void initProblemDialogShow() {

		mAlertDialog = new AlertDialog.Builder(this).create();
		mAlertDialog.setTitle(this.getString(R.string.error));
		mAlertDialog.setMessage(this.getString(R.string.connectionError));
		mAlertDialog.setButton(DialogInterface.BUTTON_NEUTRAL, "OK",
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						if (mImageListAdapter == null
								|| mImageListAdapter.getCount() == 0) {
							try {
								MainActivity.this.finish();
							} catch (Throwable e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					}
				});
	}

	private void updateList(get_photos_response response) {

		ArrayList<PhotoShort> list = new ArrayList<PhotoShort>();
		if (mPage == 1) {
			for (int x = 0; x < response.photos.length; x++) {
				list.add(response.photos[x]);
			}
			mImageListAdapter = new ImageListAdapter(MainActivity.this, list);
			setListAdapter(mImageListAdapter);
		} else {
			for (int x = 0; x < response.photos.length; x++)
				list.add(response.photos[x]);
			mImageListAdapter.appendPhotos(list);
		}
		mImageListAdapter.notifyDataSetChanged();
	}

	void initProgressDialog() {
		mProgressBar = new ProgressDialog(this, R.style.DialogTheme);
		mProgressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		mProgressBar.setCancelable(true);
		mProgressBar.setProgressStyle(android.R.style.Widget_ProgressBar_Small);
		mProgressBar.setIndeterminate(true);
		mProgressBar.setCanceledOnTouchOutside(false);
		mProgressBar.setCancelable(false);
	}
}