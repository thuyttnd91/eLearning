package com.eas.elearning.app;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import org.holoeverywhere.LayoutInflater;
import org.holoeverywhere.app.Fragment;
import org.holoeverywhere.widget.AdapterView.OnItemSelectedListener;
import org.holoeverywhere.widget.Button;
import org.holoeverywhere.widget.ImageButton;
import org.holoeverywhere.widget.ProgressBar;
import org.holoeverywhere.widget.Spinner;
import org.holoeverywhere.widget.TextView;
import org.holoeverywhere.widget.Toast;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.MenuItemCompat.OnActionExpandListener;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.SearchView.OnCloseListener;
import android.support.v7.widget.SearchView.OnQueryTextListener;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View.OnClickListener;
import android.view.View.OnCreateContextMenuListener;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.eas.elearning.R;
import com.eas.elearning.adapter.DocumentAdapter;
import com.eas.elearning.app.CommentDialog.onCommentListener;
import com.eas.elearning.bean.Document;
import com.eas.elearning.business.DataNetwork;
import com.eas.elearning.business.DocumentTask;
import com.eas.elearning.db.DBAccount;
import com.eas.elearning.db.MyPreference;
import com.eas.elearning.network.NetworkUtility;
import com.eas.elearning.network.Parser;
import com.eas.elearning.util.DataHolder;
import com.loopj.android.http.AsyncHttpResponseHandler;

public class HomeFragment extends Fragment implements OnClickListener, OnItemSelectedListener {

	private ListView lvSub;
	private DocumentAdapter adapter;
	protected ArrayList<Document> document;
	protected ArrayList<Document> docForSubj;
	private boolean isLoading;
	private int pageCount;
	private View footer;
	private int currentPage;
	private int subjID = -1;
	LinearLayout layout_filter;
	ImageButton imgFilter_hor, imgFilter_ver;
	private Button btnRetry;
	private ProgressBar pBar;
	private Spinner spSubj;
	private Spinner spGrade;
	private TextView tvNoData;
	
	private DBAccount mdb;
	int subjectID=23;
	int grade=12;
	private static String order="";
	ArrayList<Integer> alSubjectID;
	String strClass;
	private boolean isSearchContext = false;
	

	public HomeFragment() {

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		setHasOptionsMenu(true);
		View view = inflater.inflate(R.layout.list_document_activity, null);
		layout_filter = (LinearLayout) view.findViewById(R.id.layout_filter);
		// final TextView tvTest = (TextView) view.findViewById(R.id.tvTest);
		// tvTest.setText("hello");
		imgFilter_hor = (ImageButton) view.findViewById(R.id.imgFilter_ho);
		imgFilter_ver = (ImageButton) view.findViewById(R.id.imgFilter_ve);
		
		mdb = new DBAccount(getSupportActivity());
		alSubjectID = mdb.getIDSubject();
		strClass = MyPreference.getInstance().getString("ID_CLASS");
		if(strClass.equals("")) strClass = null;
		
		ActionBar mActionBar = getSupportActionBar();
		mActionBar.setDisplayShowTitleEnabled(false);
		mActionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
		
		imgFilter_hor.setVisibility(View.GONE);
		imgFilter_ver.setVisibility(View.GONE);
		spGrade = (Spinner) view.findViewById(R.id.spi_class);
		spSubj = (Spinner) view.findViewById(R.id.spi_type);
		lvSub = (ListView) view.findViewById(R.id.lv_subjects);
		btnRetry = (Button) view.findViewById(R.id.btnRetryData);
		pBar = (ProgressBar) view.findViewById(R.id.pro_data);
		tvNoData = (TextView) view.findViewById(R.id.tvNoData);
		
		btnRetry.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				loadStart();
			}
		});
		
		imgFilter_hor.setOnClickListener(this);
		imgFilter_ver.setOnClickListener(this);
		lvSub.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> a, View v, int pos, long id) {
				DataHolder.setDoc((Document) a.getItemAtPosition(pos));
				Intent i; 
				if (DataHolder.getDoc().getDocumentType() == 1){
				i = new Intent(getSupportActivity(),
						DocDetailActivity.class);
				} else {
					i = new Intent(getSupportActivity(), VideoDetailActivity.class);
				}
				startActivity(i);
			}
		});
		
		spGrade.setOnItemSelectedListener(this);
		spSubj.setOnItemSelectedListener(this);

		// footer = inflater.inflate(R.layout.row_load_more, lvSub, false);
		//
		// lvSub.addFooterView(footer);
		//
		// lvSub.setOnScrollListener(new PauseOnScrollListener(ImageLoader
		// .getInstance(), true, true, new OnScrollListener() {
		//
		// @Override
		// public void onScrollStateChanged(AbsListView view, int scrollState) {
		//
		// }
		//
		// @Override
		// public void onScroll(AbsListView view, int firstVisibleItem,
		// int visibleItemCount, int totalItemCount) {
		// if (visibleItemCount + firstVisibleItem == totalItemCount
		// && totalItemCount != 0) {
		// if (document == null)
		// return;
		// if (!isLoading && currentPage<=pageCount) {
		// loadMore();
		// }
		// }
		// }
		// }));

		if (subjID == -1) {
			if (document == null) {
				currentPage = 1;
				isLoading = false;
				adapter = new DocumentAdapter(new ArrayList<Document>(), getSupportActivity());
				lvSub.setAdapter(adapter);
				loadStart();
			} else {
				lvSub.setAdapter(adapter);
			}
		} else if (docForSubj == null)
			loadDocForSubj();
		else
			lvSub.setAdapter(new DocumentAdapter(docForSubj,
					getSupportActivity()));

		return view;
	}
	
	@Override
	public void onResume() {
		super.onResume();
		
		if (document != null) {
			if (order.equals("download")){
				Collections.sort(document, new Comparator<Document>() {

					@Override
					public int compare(Document lhs, Document rhs) {
						return rhs.getDownload() - lhs.getDownload();			
					}
				});
			} else {
				Collections.sort(document, new Comparator<Document>() {

					@Override
					public int compare(Document lhs, Document rhs) {
						return rhs.getView() - lhs.getView();			
					}
				});
			}
		}
		
		if (adapter != null) adapter.notifyDataSetChanged();
	}
	
	private void filterData(){
		if (document != null){
			Log.e("class", grade + "");
			Log.e("subj", subjectID + "");
			final ArrayList<Document> result = new ArrayList<Document>();
			for (Document doc : document) {
				if (doc.getSubjectID()==subjectID&&doc.getGrade()==grade) result.add(doc);
			}
			adapter.setDocumentData(result);
		}
	}

	public void setSubjectID(int id) {
		subjID = id;
	}

	protected void loadMore() {
		isLoading = true;
		footer.setVisibility(View.VISIBLE);
		if (NetworkUtility.checkNetworkState(getSupportActivity())) {
			DataNetwork.getNewDocuments(currentPage + 1,
					new AsyncHttpResponseHandler() {
						@Override
						public void onSuccess(String response) {
							footer.setVisibility(View.GONE);
							if (response == null) {
								NetworkUtility
										.showConnectionErrorToast(getSupportActivity());
								isLoading = false;
								return;
							}
							if (Parser.checkSuccess(response)) {
								ArrayList<Document> data = Parser
										.getDocument(response);
								if (data.size() < 1) {
									isLoading = false;
									NetworkUtility
											.showConnectionErrorToast(getSupportActivity());
									return;
								}
								document.addAll(data);
								adapter.setDocumentData(document);
								adapter.notifyDataSetChanged();
								currentPage++;
								isLoading = false;
							} else {
								isLoading = false;
								NetworkUtility
										.showConnectionErrorToast(getSupportActivity());
							}
						}

						@Override
						public void onFailure(Throwable error) {

						}
					});
		} else
			NetworkUtility.showNoInternetToast(getSupportActivity());
	}

	protected void loadStart() {
		
		
		if (NetworkUtility.checkNetworkState(getSupportActivity())) {
			
			btnRetry.setVisibility(View.GONE);
			pBar.setVisibility(View.VISIBLE);
			lvSub.setVisibility(View.INVISIBLE);
			tvNoData.setVisibility(View.GONE);
//			DataNetwork.getNewDocuments(1, new AsyncHttpResponseHandler(){
			DataNetwork.getAdvanceDocuments(1, strClass, alSubjectID, order, null, new AsyncHttpResponseHandler() {
				@Override
				public void onSuccess(String response) {

					if (response == null) {
						btnRetry.setVisibility(View.VISIBLE);
						pBar.setVisibility(View.GONE);
						return;
					}
					if (Parser.checkSuccess(response)
							&& getSupportActivity() != null) {

						pageCount = Parser.getPageCount(response);

						document = Parser.getDocument(response);
						if (document.size() > 0) {
							
							btnRetry.setVisibility(View.GONE);
							pBar.setVisibility(View.GONE);
							adapter.setDocumentData(document);
							lvSub.setVisibility(View.VISIBLE);
							/*adapter = new DocumentAdapter(document,
									getSupportActivity());
							lvSub.setAdapter(adapter);*/
							
						} else{
							
							btnRetry.setVisibility(View.GONE);
							pBar.setVisibility(View.GONE);
							tvNoData.setVisibility(View.VISIBLE);
							
//							Toast.makeText(getSupportActivity(),
//									"Không có dữ liệu", Toast.LENGTH_SHORT)
//									.show();
							}
					} else {
						
						btnRetry.setVisibility(View.GONE);
						pBar.setVisibility(View.GONE);
						tvNoData.setVisibility(View.VISIBLE);
						
					}
				}

				@Override
				public void onFailure(Throwable error) {
					
					btnRetry.setVisibility(View.VISIBLE);
					pBar.setVisibility(View.GONE);
				}
			});
		} else{
			
			btnRetry.setVisibility(View.VISIBLE);
			pBar.setVisibility(View.GONE);
			
			NetworkUtility.showNoInternetToast(getSupportActivity());
			}
	}

	private void loadDocForSubj() {

		if (NetworkUtility.checkNetworkState(getSupportActivity())) {
			DocumentTask.getDocForSubj(subjID, 1,
					new AsyncHttpResponseHandler() {
						@Override
						public void onSuccess(String response) {

							if (response == null) {
								return;
							}
							if (Parser.checkSuccess(response)
									&& getSupportActivity() != null) {

								pageCount = Parser.getPageCount(response);

								docForSubj = Parser.getDocument(response);
								if (docForSubj.size() > 0) {
									lvSub.setAdapter(new DocumentAdapter(
											docForSubj, getSupportActivity()));
								} else
									Toast.makeText(getSupportActivity(),
											"Không có dữ liệu",
											Toast.LENGTH_SHORT).show();
							} else {

							}
						}

						@Override
						public void onFailure(Throwable error) {

						}
					});
		} else
			NetworkUtility.showNoInternetToast(getSupportActivity());
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		inflater.inflate(R.menu.home, menu);

		MenuItem searchItem = menu.findItem(R.id.menu_search);
		
		final SearchView searchView = (SearchView) MenuItemCompat
				.getActionView(searchItem);
		if (searchView != null) {
			searchView.setOnQueryTextListener(new OnQueryTextListener() {
				
				@Override
				public boolean onQueryTextSubmit(final String query) {
						btnRetry.setVisibility(View.GONE);
						pBar.setVisibility(View.VISIBLE);
						tvNoData.setVisibility(View.GONE);
						lvSub.setVisibility(View.INVISIBLE);
						//lvSub.setAdapter(null);
					DataNetwork.getAdvanceDocuments(1, strClass, alSubjectID, order, query,
							new AsyncHttpResponseHandler() {
								@Override
								public void onSuccess(String response) {
									
									ArrayList<Document> data = Parser
											.getDocument(response);
									if (data.size() > 0) {
										adapter.setDocumentData(data);
//										searchView.setQuery("", false);
										lvSub.setVisibility(View.VISIBLE);
										searchView.clearFocus();
										btnRetry.setVisibility(View.GONE);
										pBar.setVisibility(View.GONE);
										tvNoData.setVisibility(View.GONE);
									} else {
										btnRetry.setVisibility(View.GONE);
										pBar.setVisibility(View.GONE);
										tvNoData.setVisibility(View.VISIBLE);
										searchView.clearFocus();
									}
								}

								@Override
								public void onFailure(Throwable error) {
									btnRetry.setVisibility(View.VISIBLE);
									pBar.setVisibility(View.GONE);
								}
							});
					return false;
				}
				
				
				@Override
				public boolean onQueryTextChange(String newText) {
					isSearchContext = true;
					return false;
				}
			});
			
			searchView.setOnCloseListener(new OnCloseListener() {
				
				@Override
				public boolean onClose() {
//					btnRetry.setVisibility(View.GONE);
//					pBar.setVisibility(View.GONE);
//					tvNoData.setVisibility(View.GONE);
					loadStart();
					return false;
				}
			});
			
			
			
		}
		super.onCreateOptionsMenu(menu, inflater);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.imgFilter_ho:
			layout_filter.setVisibility(View.GONE);
			imgFilter_ver.setVisibility(View.VISIBLE);

			break;
		case R.id.imgFilter_ve:
			layout_filter.setVisibility(View.VISIBLE);
			imgFilter_ver.setVisibility(View.GONE);
			break;
		default:
			break;
		}

	}

	@Override
	public void onItemSelected(org.holoeverywhere.widget.AdapterView<?> parent,
			View view, int position, long id) {
		switch (parent.getId()) {
		case R.id.spi_class:
			Toast.makeText(getSupportActivity(), "class " + position, Toast.LENGTH_SHORT).show();
			if (position==2) grade = 12;
			else grade = position + 6;
			break;
        case R.id.spi_type:
        	Toast.makeText(getSupportActivity(), "subj " + position, Toast.LENGTH_SHORT).show();
			if (position==0) subjectID = 23;
			else subjectID = position;
			break;

		default:
			break;
		}
		filterData();
	}

	@Override
	public void onNothingSelected(
			org.holoeverywhere.widget.AdapterView<?> parent) {
		
	}
	
	public void setOrder(String order){
		this.order = order;
	}

}
