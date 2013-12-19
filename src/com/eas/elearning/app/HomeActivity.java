package com.eas.elearning.app;

import org.holoeverywhere.app.Activity;
import org.holoeverywhere.app.Fragment;
import org.holoeverywhere.widget.ExpandableListView;
import org.holoeverywhere.widget.ExpandableListView.OnChildClickListener;
import org.holoeverywhere.widget.ExpandableListView.OnGroupClickListener;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBar.OnNavigationListener;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;

import com.eas.elearning.AppConfig;
import com.eas.elearning.R;
import com.eas.elearning.adapter.AccountSettingAdapter;
import com.eas.elearning.adapter.DrawerListAdapter;
import com.eas.elearning.bean.ObjAccount;
import com.eas.elearning.bean.OnLogoutListenner;
import com.eas.elearning.db.DBAccount;
import com.eas.elearning.db.MyPreference;
import com.eas.elearning.util.Util;
import com.sherlock.navigationdrawer.compat.SherlockActionBarDrawerToggle;

public class HomeActivity extends Activity {
	private String TAG = "HomeActivity";
	private DrawerLayout mDrawerLayout;
	private ExpandableListView mDrawerList;
	private ActionBarHelper mActionBar;
	private DrawerListAdapter mDrawerAdapter;
	private SherlockActionBarDrawerToggle mDrawerToggle;
	private DBAccount mdb;
	boolean isCheck = false;
	ObjAccount objAccount = null;
	AccountSettingAdapter adapter;
	String name = "", pass = "", nameSchool = "", strID, strName;
	Util utils;
	int idAccount = 0;
	OnLogoutListenner callback;
	private static String nav_item_selected ="";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerList = (ExpandableListView) findViewById(R.id.left_drawer);
		mDrawerLayout.setDrawerListener(new DemoDrawerListener());
		mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow,
				GravityCompat.START);
		mDrawerAdapter = new DrawerListAdapter(getActivity(),
				AppConfig.generateMenuDrawerList(), objAccount);
		mdb = new DBAccount(this);
		utils = new Util(this);

		objAccount = new ObjAccount();
		MyPreference.getInstance().Initialize(this);
		adapter = new AccountSettingAdapter(this,
				AppConfig.generateAccountSettingItems(), "guess@gmail.com");
		mDrawerList.setAdapter(mDrawerAdapter);

		mDrawerList.setOnGroupClickListener(new OnGroupClickListener() {

			@Override
			public boolean onGroupClick(ExpandableListView parent, View v,
					int groupPosition, long id) {
				selectItem(groupPosition);
				return false;
			}
		});

		mDrawerList.setOnChildClickListener(new OnChildClickListener() {

			@Override
			public boolean onChildClick(ExpandableListView parent, View v,
					int groupPosition, int childPosition, long id) {
				switch (groupPosition) {
				case 5:
					HomeFragment fragment = new HomeFragment();
					FragmentManager fm = getSupportFragmentManager();
					switch (childPosition) {
					case 0:
						fragment.setSubjectID(23);
						fm.beginTransaction().replace(R.id.content_frame, fragment)
								.commit();
						mActionBar.setTitle("Đại số");
						mDrawerLayout.closeDrawer(mDrawerList);
						break;
					case 1:
						fragment.setSubjectID(1);
						fm.beginTransaction().replace(R.id.content_frame, fragment)
								.commit();
						mActionBar.setTitle("Hình học");
						mDrawerLayout.closeDrawer(mDrawerList);
						break;
					case 2:
						fragment.setSubjectID(4);
						fm.beginTransaction().replace(R.id.content_frame, fragment)
								.commit();
						mActionBar.setTitle("Hóa học");
						mDrawerLayout.closeDrawer(mDrawerList);
						break;

					default:
						break;
					}
					break;

				default:
					break;
				}
				return false;
			}
		});

		// mDrawerList
		// .setOnGroupClickListener(new
		// ExpandableListView.OnGroupClickListener() {
		//
		// @Override
		// public boolean onGroupClick(ExpandableListView parent,
		// View v, int groupPosition, long id) {
		// switch (groupPosition) {
		// case 0:
		// if (isCheck) {
		// Toast.makeText(getBaseContext(), "Favorite",
		// Toast.LENGTH_SHORT).show();
		// } else {
		// Intent intent = new Intent(HomeActivity.this,
		// LoginAccountPopup.class);
		// intent.putExtra("CHECK", false);
		// startActivity(intent);
		// }
		//
		// break;
		// case 1:
		// if (isCheck) {
		// Toast.makeText(getBaseContext(), "Download",
		// Toast.LENGTH_SHORT).show();
		// } else {
		// Intent intentRegister = new Intent(
		// HomeActivity.this,
		// RegisterAccountActivity.class);
		// startActivityForResult(intentRegister, 1);
		// }
		//
		// break;
		// case 6:
		// Intent i = new Intent(getBaseContext(),
		// FeedbackActivity.class);
		// startActivity(i);
		// break;
		// case 7:
		// Intent z = new Intent(getBaseContext(),
		// AboutActivity.class);
		// startActivity(z);
		// break;
		//
		// default:
		// break;
		// }
		// return false;
		// }
		// });
		mDrawerList.setCacheColorHint(0);
		mDrawerList.setScrollingCacheEnabled(false);
		mDrawerList.setScrollContainer(false);
		mDrawerList.setFastScrollEnabled(true);
		mDrawerList.setSmoothScrollbarEnabled(true);
		mDrawerList.setGroupIndicator(null);
		mActionBar = createActionBarHelper();
		mActionBar.init();

		// ActionBarDrawerToggle provides convenient helpers for tying together
		// the
		// prescribed interactions between a top-level sliding drawer and the
		// action bar.
		mDrawerToggle = new SherlockActionBarDrawerToggle(this.getActivity(),
				mDrawerLayout, R.drawable.ic_drawer_light,
				R.string.drawer_open, R.string.drawer_close);
		mDrawerToggle.syncState();

		if (savedInstanceState == null) {
			if(isCheck==false) selectItem(6);
			else selectItem(4);
		}

		// spAccount.setEnabled(false);
		// spAccount.setSelection(5);
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		Log.e(TAG, "onresume");
		int id = 0;
		//
		strID = MyPreference.getInstance().getString("SETTING_ID");
		strName = MyPreference.getInstance().getString("SETTING_NAME");
		if (!(strID.equals(""))) {
			id = Integer.parseInt(strID);
		}

		if (!isCheck) {
			if (!(strID.equals("")) || !(strName.equals(""))) {
				objAccount = mdb.getAccount(id);
				if (objAccount != null) {
					// spAccount.setEnabled(true);
					isCheck = true;
					Log.e(TAG, "ok: " + objAccount.getEmail());
					// DrawerItem item1 = new DrawerItem(0,
					// R.drawable.ic_menu_favorite_ldpi, "Mục ưa thích",
					// DrawerItem.NORMAL_ITEM, null);
					// DrawerItem item2 = new DrawerItem(1,
					// R.drawable.ic_menu_download_hdpi, "Mục download",
					// DrawerItem.NORMAL_ITEM, null);
					// ArrayList<DrawerItem> arrDrawer = AppConfig
					// .generateMenuDrawerList();
					// arrDrawer.set(0, item1);
					// arrDrawer.set(1, item2);
					/*
					 * adapter = new AccountSettingAdapter(this,
					 * AppConfig.generateAccountSettingItems1());
					 */

					// spAccount.setAdapter(adapter);
					// mDrawerList.addHeaderView(v);
					// mDrawerAdapter = new DrawerListAdapter(getActivity(),
					// AppConfig.generateMenuDrawerListAfterChange(),objAccount,"guess@gmai.com");
					mDrawerAdapter = new DrawerListAdapter(this,
							AppConfig.generateMenuDrawerListAfterChange(),
							objAccount);
					mDrawerList.setAdapter(mDrawerAdapter);
					// mDrawerAdapter.AddGroupContent(1,item1);
					// mDrawerAdapter.AddGroupContent(2,item2);
					// mDrawerAdapter.notifyDataSetChanged();
					/*utils.setLogin("" + objAccount.getId(),
							objAccount.getUserName());*/
				}
			}

		} else {
			mDrawerAdapter = new DrawerListAdapter(getActivity(),
					AppConfig.generateMenuDrawerListAfterChange(), objAccount);
			mDrawerList.setAdapter(mDrawerAdapter);
		}

	}

	public void onClickAccount(int position) {
		switch (position) {
		case 1:
			Intent intent = new Intent(this, LoginAccountPopup.class);
			startActivity(intent);
			break;

		default:
			break;
		}
	}

	private class ActionBarHelper {
		private final ActionBar mActionBar;
		private CharSequence mDrawerTitle;
		private CharSequence mTitle;

		private ActionBarHelper() {
			mActionBar = (getActivity()).getSupportActionBar();
		}

		public void init() {
			mActionBar.setDisplayHomeAsUpEnabled(true);
			mActionBar.setHomeButtonEnabled(true);
			mActionBar.setDisplayShowTitleEnabled(false);
			mActionBar.setListNavigationCallbacks(new ArrayAdapter<String>(getActivity(), R.layout.simple_dropdown_item_1line, new String[]{"Mới cập nhật","Xem nhiều nhất","Tải nhiều nhất"}), new OnNavigationListener() {
				
				@Override
				public boolean onNavigationItemSelected(int itemPosition, long itemId) {
					HomeFragment fragment = new HomeFragment();
					switch(itemPosition){
						case 0:
							nav_item_selected = "new";		
							break;
						case 1:
							nav_item_selected = "view";	
							break;
						case 2:
							nav_item_selected = "download";	
							break;
					}
					fragment.setOrder(nav_item_selected);
					FragmentManager fm = getSupportFragmentManager();
					fm.beginTransaction().replace(R.id.content_frame, fragment)
							.commit();
					
					return false;
				}
			});
			mTitle = mDrawerTitle = getActivity().getTitle();
		}

		/**
		 * When the drawer is closed we restore the action bar state reflecting
		 * the specific contents in view.
		 */
		public void onDrawerClosed() {
			mActionBar.setTitle(getTitle());
		}

		/**
		 * When the drawer is open we set the action bar to a generic title. The
		 * action bar should only contain data relevant at the top level of the
		 * nav hierarchy represented by the drawer, as the rest of your content
		 * will be dimmed down and non-interactive.
		 */
		public void onDrawerOpened() {
			mActionBar.setTitle(R.string.app_name);
		}

		public void setTitle(CharSequence title) {
			mTitle = title;
			// private option: Drawer Title = Actionbar Title
			mDrawerTitle = title;
		}
	}

	public Activity getActivity() {
		return this;
	}

	private ActionBarHelper createActionBarHelper() {
		return new ActionBarHelper();
	}

	/**
	 * A drawer listener can be used to respond to drawer events such as
	 * becoming fully opened or closed. You should always prefer to perform
	 * expensive operations such as drastic relayout when no animation is
	 * currently in progress, either before or after the drawer animates.
	 * 
	 * When using ActionBarDrawerToggle, all DrawerLayout listener methods
	 * should be forwarded if the ActionBarDrawerToggle is not used as the
	 * DrawerLayout listener directly.
	 */
	private class DemoDrawerListener implements DrawerLayout.DrawerListener {
		@Override
		public void onDrawerOpened(View drawerView) {
			mDrawerToggle.onDrawerOpened(drawerView);
			mActionBar.onDrawerOpened();
		}

		@Override
		public void onDrawerClosed(View drawerView) {
			mDrawerToggle.onDrawerClosed(drawerView);
			mActionBar.onDrawerClosed();
		}

		@Override
		public void onDrawerSlide(View drawerView, float slideOffset) {
			mDrawerToggle.onDrawerSlide(drawerView, slideOffset);
		}

		@Override
		public void onDrawerStateChanged(int newState) {
			mDrawerToggle.onDrawerStateChanged(newState);
		}
	}

	private class DrawerItemClickListener implements
			ExpandableListView.OnChildClickListener {

		@Override
		public boolean onChildClick(ExpandableListView parent, View v,
				int groupPosition, int childPosition, long id) {
			Log.e("group pos", groupPosition + "");
			Log.e("child pos", childPosition + "");
			return false;
		}

	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		mDrawerToggle.onConfigurationChanged(newConfig);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {
		case android.R.id.home:
			if (mDrawerLayout.isDrawerOpen(mDrawerList)) {
				mDrawerLayout.closeDrawer(mDrawerList);
			} else {
				mDrawerLayout.openDrawer(mDrawerList);
			}
			return true;
		case R.id.menu_search:
			onSearchRequested();
			return true;

		default:
			return super.onOptionsItemSelected(item);
		}
	}

	private void selectItem(int position) {

		switch (position) {
		case 1:
			if (isCheck) {
				Fragment newFragment = new FarvoriteFragment();
				FragmentManager fm = getSupportFragmentManager();
				fm.beginTransaction().replace(R.id.content_frame, newFragment)
						.commit();
				mDrawerLayout.closeDrawer(mDrawerList);
			} else {
				Intent intent = new Intent(HomeActivity.this,
						LoginAccountPopup.class);
				intent.putExtra("CHECK", false);
				startActivity(intent);
			}

			break;
		case 2:
			if (isCheck) {
				Intent intent = new Intent(HomeActivity.this,
						DownloadActivity.class);
				startActivityForResult(intent, 1);
			} else {
				Intent intentRegister = new Intent(HomeActivity.this,
						RegisterAccountActivity.class);
				startActivityForResult(intentRegister, 1);
			}

			break;
			
		case 3:
			if(!isCheck){
			Fragment fragment = new FarvoriteFragment();
			FragmentManager fm1 = getSupportFragmentManager();
			fm1.beginTransaction().replace(R.id.content_frame, fragment)
					.commit();
			mDrawerLayout.closeDrawer(mDrawerList);
			
			} else{
				PostListFragment postFragment = new PostListFragment();
				getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, postFragment)
				.commit();
				
				mDrawerLayout.closeDrawer(mDrawerList);
				
			}
			break;
		case 4:
			if(isCheck){
				HomeFragment newFragment = new HomeFragment();
				newFragment.setOrder(nav_item_selected);
				FragmentManager fm = getSupportFragmentManager();
				fm.beginTransaction().replace(R.id.content_frame, newFragment)
						.commit();
				mDrawerList.setItemChecked(position, true);
				mDrawerLayout.closeDrawer(mDrawerList);
			} else {
				Intent intent = new Intent(HomeActivity.this,
						DownloadActivity.class);
				startActivityForResult(intent, 1);
			}

			break;
		case 5:
			if(!isCheck){
				PostListFragment postFragment = new PostListFragment();
				getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, postFragment)
				.commit();
				/*HomeFragment newFragment = new HomeFragment();
				newFragment.setOrder(nav_item_selected);
				FragmentManager fm = getSupportFragmentManager();
				fm.beginTransaction().replace(R.id.content_frame, newFragment)
						.commit();
				mDrawerList.setItemChecked(position, true);*/
				
				mDrawerLayout.closeDrawer(mDrawerList);
			} else {
				/*Intent i = new Intent(getBaseContext(), FeedbackActivity.class);
				startActivity(i);*/
			}
			break;
		case 6:
			if(isCheck){
				Intent i = new Intent(getBaseContext(), FeedbackActivity.class);
				startActivity(i);
				/*Intent z = new Intent(getBaseContext(), AboutActivity.class);
				startActivity(z);*/
			} else {
				HomeFragment newFragment = new HomeFragment();
				newFragment.setOrder(nav_item_selected);
				FragmentManager fm = getSupportFragmentManager();
				fm.beginTransaction().replace(R.id.content_frame, newFragment)
						.commit();
				mDrawerList.setItemChecked(position, true);
				
				mDrawerLayout.closeDrawer(mDrawerList);
			}
			break;
//		case 6:
//			Fragment newFragment = new HomeFragment();
//			FragmentManager fm = getSupportFragmentManager();
//			fm.beginTransaction().replace(R.id.content_frame, newFragment)
//					.commit();
//			mDrawerList.setItemChecked(position, true);
//			setTitle(mDrawerAdapter.getGroup(position).getTitle());
//			mActionBar.setTitle(mDrawerAdapter.getGroup(position).getTitle());
//			mDrawerLayout.closeDrawer(mDrawerList);
//			break;
		case 7:
			if(!isCheck){
				
			} else {
				Intent z = new Intent(getBaseContext(), AboutActivity.class);
				startActivity(z);
				/*Intent y = new Intent(getBaseContext(), SettingActivity.class);
				startActivity(y);*/
			}
			break;
		case 8:
			if (isCheck){
				Intent y = new Intent(getBaseContext(), SettingActivity.class);
				startActivityForResult(y,2);
			} else {
			Intent z = new Intent(getBaseContext(), FeedbackActivity.class);
			startActivity(z);
			}
			break;
		case 9:
			Intent z = new Intent(getBaseContext(), AboutActivity.class);
			startActivity(z);
			/*Intent y = new Intent(getBaseContext(), SettingActivity.class);
			startActivityForResult(y, 2);*/
			break;
		case 10:
			Intent y = new Intent(getBaseContext(), SettingActivity.class);
			startActivityForResult(y, 2);
			break;
		}
		
		
	}

	// @Override
	// public boolean onPrepareOptionsMenu(Menu menu) {
	// boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
	// menu.findItem(R.id.menu_search).setVisible(!drawerOpen);
	// return super.onPrepareOptionsMenu(menu);
	// }
	//
	// @Override
	// public boolean onCreateOptionsMenu(Menu menu) {
	// // Inflate the menu; this adds items to the action bar if it is present.
	// MenuInflater inflater = getMenuInflater();
	// inflater.inflate(R.menu.home, menu);
	//
	// MenuItem searchItem = menu.findItem(R.id.menu_search);
	// SearchView searchView = (SearchView) MenuItemCompat
	// .getActionView(searchItem);
	// if (searchView != null) {
	// searchView.setOnQueryTextListener(new OnQueryTextListener() {
	//
	// @Override
	// public boolean onQueryTextSubmit(String query) {
	// Toast.makeText(getApplicationContext(), query,
	// Toast.LENGTH_SHORT).show();
	// return false;
	// }
	//
	// @Override
	// public boolean onQueryTextChange(String newText) {
	// // TODO Auto-generated method stub
	// return false;
	// }
	// });
	// }
	// return super.onCreateOptionsMenu(menu);
	// }

	/*
	 * @Override public void onItemClick(AdapterView<?> parent, View view, int
	 * position, long id) { switch (position) { case : Intent intentInfo=new
	 * Intent(HomeActivity.this,ViewAccountActivity.class);
	 * startActivity(intentInfo);
	 * 
	 * break; case 1: Intent intentUpdate=new
	 * Intent(HomeActivity.this,RegisterAccountActivity.class);
	 * startActivity(intentUpdate); break; default: if(isCheck){ Log.e(TAG,
	 * "LOGOUT"); } break; }
	 */
	/*
	 * @Override public void onItemSelected(AdapterView<?> parent, View view,
	 * int position, long id) { Log.e(TAG, "position: " +position); switch
	 * (position) { case 0:
	 * 
	 * Intent intentInfo = new Intent(HomeActivity.this,
	 * ViewAccountActivity.class); intentInfo.putExtra("ID",
	 * objAccount.getId()); startActivity(intentInfo);
	 * 
	 * break; case 1: if(isCheck){ Intent intentUpdate = new
	 * Intent(HomeActivity.this, UpdateInfoActivity.class);
	 * intentUpdate.putExtra("ID", objAccount.getId());
	 * startActivity(intentUpdate); }
	 * 
	 * Bundle b = new Bundle(); b.putSerializable("OBJECT", objAccount);
	 * intentUpdate.putExtras(b); startActivity(intentUpdate);
	 * 
	 * break; case 2: LogoutAccount(); Toast.makeText(getBaseContext(),
	 * "logout", Toast.LENGTH_SHORT).show(); break; case 3: HomeFragment
	 * newFragment = new HomeFragment(); break; }
	 * 
	 * }
	 * 
	 * @Override public void onNothingSelected(AdapterView<?> parent) { // TODO
	 * Auto-generated method stub
	 * 
	 * }
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		Log.d("result", "requestCode: "+ requestCode+", resultCode: "+resultCode);
		if (requestCode == 1) {
			if (resultCode == RESULT_OK) {
				name = data.getExtras().getString("NAME");
				pass = data.getExtras().getString("PASSWORD");
				nameSchool = data.getExtras().getString("NAME_SCHOOL");
				Intent i = new Intent(HomeActivity.this,
						LoginAccountPopup.class);
				i.putExtra("NAME", name);
				i.putExtra("PASS", pass);
				i.putExtra("NAME_SCHOOL", nameSchool);
				i.putExtra("CHECK", true);
				startActivity(i);

			}
		}
		
		if (requestCode == 2) {
			if (resultCode == RESULT_OK) {
				Log.d("result", "ok");
				if(isCheck==false) selectItem(6);
				else selectItem(4);
			}
		}
	}

	public void LogoutAccount() {
		mDrawerAdapter = new DrawerListAdapter(this,
				AppConfig.generateMenuDrawerList(), objAccount);
		mDrawerList.setAdapter(mDrawerAdapter);
		isCheck = false;
		mDrawerAdapter.notifyDataSetChanged();

	}
	
}
