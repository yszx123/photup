package uk.co.senab.photup.adapters;

import java.util.List;

import uk.co.senab.photup.PhotoUploadController;
import uk.co.senab.photup.PhotupApplication;
import uk.co.senab.photup.R;
import uk.co.senab.photup.MediaStoreAsyncTask.MediaStoreResultListener;
import uk.co.senab.photup.model.PhotoSelection;
import uk.co.senab.photup.views.PhotoItemLayout;
import uk.co.senab.photup.views.PhotupImageView;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Checkable;

public class UsersPhotosBaseAdapter extends BaseAdapter implements MediaStoreResultListener {

	private List<PhotoSelection> mItems;

	private final Context mContext;
	private final LayoutInflater mLayoutInflater;
	private final PhotoUploadController mController;

	public UsersPhotosBaseAdapter(Context context) {
		mContext = context;
		mLayoutInflater = LayoutInflater.from(mContext);

		PhotupApplication app = PhotupApplication.getApplication(context);
		mController = app.getPhotoUploadController();
		mItems = PhotupApplication.getApplication(mContext).getMediaStorePhotos(this);
	}

	public int getCount() {
		return null != mItems ? mItems.size() : 0;
	}

	public long getItemId(int position) {
		return position;
	}

	public PhotoSelection getItem(int position) {
		return mItems.get(position);
	}

	public View getView(int position, View view, ViewGroup parent) {
		if (null == view) {
			view = mLayoutInflater.inflate(R.layout.item_grid_photo, parent, false);
		}

		PhotoItemLayout layout = (PhotoItemLayout) view;
		PhotupImageView iv = layout.getImageView();

		final PhotoSelection upload = getItem(position);

		iv.setFadeInDrawables(true);
		iv.requestThumbnail(upload, false);
		layout.setPhotoSelection(upload);

		if (null != mController) {
			((Checkable) view).setChecked(mController.isPhotoUploadSelected(upload));
		}

		return view;
	}

	public void refresh() {
		PhotupApplication.getApplication(mContext).getMediaStorePhotos(this);
	}

	public void onPhotosLoaded(List<PhotoSelection> selection) {
		mItems = selection;
		notifyDataSetChanged();
	}

}