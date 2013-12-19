package com.eas.elearning.adapter;

import java.util.ArrayList;

import org.holoeverywhere.LayoutInflater;
import org.holoeverywhere.app.Activity;
import org.holoeverywhere.widget.AdapterView;
import org.holoeverywhere.widget.BaseExpandableListAdapter;
import org.holoeverywhere.widget.Spinner;
import org.holoeverywhere.widget.TextView;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap.Config;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.eas.elearning.AppConfig;
import com.eas.elearning.R;
import com.eas.elearning.app.HomeActivity;
import com.eas.elearning.app.UpdateInfoActivity;
import com.eas.elearning.app.ViewAccountActivity;
import com.eas.elearning.bean.DrawerItem;
import com.eas.elearning.bean.ObjAccount;
import com.eas.elearning.util.HTMLUtil;
import com.eas.elearning.util.Util;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

public class DrawerListAdapter extends BaseExpandableListAdapter {
	private String TAG = "DrawerListAdapter";
	private Context context;
	private ArrayList<ArrayList<DrawerItem>> CHILDRENS;
	private ArrayList<DrawerItem> GROUPS;
	Util utils;
	ObjAccount account;

	public DrawerListAdapter(Context context, ArrayList<DrawerItem> items,
			ObjAccount obj) {
		this.context = context;
		CHILDRENS = new ArrayList<ArrayList<DrawerItem>>();
		GROUPS = new ArrayList<DrawerItem>();
		this.account = obj;
		reorganizeData(items);
		utils=new Util(context);
	}

	private void reorganizeData(ArrayList<DrawerItem> items) {
		for (int i = 0; i < items.size(); i++) {
			DrawerItem mItem = items.get(i);
			GROUPS.add(mItem.getPosition(), mItem);
			if (mItem.hasChildren() == true) {
				ArrayList<DrawerItem> CHILDREN_TEMP = mItem.getChildren();
				CHILDRENS.add(mItem.getPosition(), CHILDREN_TEMP);
			} else {
				CHILDRENS.add(mItem.getPosition(), new ArrayList<DrawerItem>());
			}
		}

	}

	@Override
	public DrawerItem getChild(int groupPosition, int childPosition) {
		return CHILDRENS.get(groupPosition).get(childPosition);
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		return childPosition;
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		return CHILDRENS.get(groupPosition).size();
	}

	@Override
	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
		View view = LayoutInflater.inflate(context,
				R.layout.row_drawerlist_normalitem);
		TextView tvTitle = (TextView) view.findViewById(R.id.tvTitle);
		ImageView imgIcon = (ImageView) view.findViewById(R.id.imgIcon);
		tvTitle.setText(getChild(groupPosition, childPosition).getTitle());
		imgIcon.setImageResource(getChild(groupPosition, childPosition)
				.getIconRes());
		LinearLayout layoutDrawerlist = (LinearLayout) view
				.findViewById(R.id.layout_drawerlist);
		layoutDrawerlist.setPadding(30, 0, 0, 0);
		return view;
	}

	@Override
	public DrawerItem getGroup(int groupPosition) {
		return GROUPS.get(groupPosition);
	}

	@Override
	public int getGroupCount() {
		return GROUPS.size();
	}

	@Override
	public long getGroupId(int groupPosition) {
		return groupPosition;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {
		View view = null;
		switch (getGroupType(groupPosition)) {
		case 0:
			view = LayoutInflater.inflate(context,
					R.layout.row_drawerlist_specialitem);
			ImageView imgAvatar = (ImageView) view.findViewById(R.id.imgAvatar);
			TextView tvUsername = (TextView) view.findViewById(R.id.tvUserName);
			tvUsername.setText(getGroup(groupPosition).getTitle());
			
			DisplayImageOptions options = new DisplayImageOptions.Builder()
			.bitmapConfig(Config.RGB_565)
			.cacheInMemory(true)
			.cacheOnDisc(true)
			.resetViewBeforeLoading(true)
			.showStubImage(R.drawable.avatar_placeholder2)
			.build();
			
			
			
			final Spinner spAccount = (Spinner) view.findViewById(R.id.spAccount);
			AccountSettingAdapter adapter;
			
			if(account!=null){
				spAccount.setClickable(true);
				tvUsername.setText(account.getUserName());
				ImageLoader.getInstance().displayImage(HTMLUtil.getFullUrl(account.getImgAvatar()), imgAvatar, options);
				
				 adapter = new AccountSettingAdapter(context,
						AppConfig.generateAccountSettingItems(),account.getEmail());
			}
			else{
				 adapter = new AccountSettingAdapter(context,
						AppConfig.generateAccountSettingItems(),"guess@gmail.com");
				spAccount.setClickable(false);
			}
			spAccount.setAdapter(adapter);
			spAccount.setSelection(10);
			spAccount.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

				@Override
				public void onItemSelected(AdapterView<?> parent, View view,
						int position, long id) {
					Log.e(TAG, "position: "+position);
					SelectSpinner(position);
					spAccount.setSelection(-1);
				}

				@Override
				public void onNothingSelected(AdapterView<?> parent) {
					
				}
				
			});
//			spAccount.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//
//				@Override
//				public void onItemSelected(AdapterView<?> parent, View view,
//						int position, long id) {
//					Log.e(TAG, "position: "+position);
//					SelectSpinner(position);
//					
//				}
//
//				@Override
//				public void onNothingSelected(AdapterView<?> parent) {
//					// TODO Auto-generated method stub
//					
//				}
//				
//			)};
			
			/*OnItemSelectedListener() {

				@Override
				public void onItemSelected(AdapterView<?> parent, View view,
						int position, long id) {
					Log.e(TAG, "position: "+position);
					SelectSpinner(position);
				}

				@Override
				public void onNothingSelected(AdapterView<?> parent) {
					// TODO Auto-generated method stub

				}
			});*/
			
			
			break;
		case 1:
			view = LayoutInflater.inflate(context,
					R.layout.row_drawerlist_header);
			TextView tvHeader = (TextView) view.findViewById(R.id.tvHeader);
			tvHeader.setText(getGroup(groupPosition).getTitle());
			break;
		default:
			view = LayoutInflater.inflate(context,
					R.layout.row_drawerlist_normalitem);
			ImageView imgIcon = (ImageView) view.findViewById(R.id.imgIcon);
			TextView tvTitle = (TextView) view.findViewById(R.id.tvTitle);
			tvTitle.setText(getGroup(groupPosition).getTitle());
			imgIcon.setImageResource(getGroup(groupPosition).getIconRes());
		}

		return view;
	}

	@Override
	public boolean hasStableIds() {
		return true;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return true;
	}

	@Override
	public int getGroupTypeCount() {
		return 3;
	}

	@Override
	public int getGroupType(int groupPosition) {
		if (groupPosition == 0)
			return 0; // special item
		else if (getGroup(groupPosition).getType() == DrawerItem.HEADER)
			return 1;
		else
			return 2; // default:normal item
	}

	public void AddGroupContent(int position, DrawerItem item) {
		Log.e("ADAPTER", "" + position);
		GROUPS.set(position, item);
		this.notifyDataSetChanged();
	}

	public void SelectSpinner(int position) {
		switch (position) {
		case 0:
			Log.e(TAG, "id school: "+account.getIdSchool());
			Log.e(TAG, "id : "+account.getId());
			Intent intentInfo = new Intent(context, ViewAccountActivity.class);
			intentInfo.putExtra("ID", account.getId());
			((Activity) context).startActivityForResult(intentInfo,1);
			//context.startActivity(intentInfo);
			break;
		case 1:
			Log.e(TAG, "id school1: "+account.getIdSchool());
			Log.e(TAG, "id1 : "+account.getId());
			Intent intentUpdate = new Intent(context, UpdateInfoActivity.class);
			intentUpdate.putExtra("ID", account.getId());
			((Activity) context).startActivityForResult(intentUpdate,1);
			//context.startActivity(intentUpdate);
			break;
		case 2:
			utils.setLogin("", "");
			 // ((HomeActivity)context).LogoutAccount();
			Intent intentLogout = new Intent(context, HomeActivity.class);
			context.startActivity(intentLogout);
			((Activity)context).finish();
			
			break;
		default:
			
			break;
		}
	}

	
}
