package com.sunita.quotesforall;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.ListFragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.sunita.quotesforall.util.SAXXMLParser;
import com.sunita.quotesforall.vo.QuotesVo;

public class QuotelistActivity extends FragmentActivity implements
ActionBar.TabListener {
	private AdView adView;
	private static final int RESULT_SETTINGS = 1;

	/**
	 * The {@link android.support.v4.view.PagerAdapter} that will provide
	 * fragments for each of the sections. We use a
	 * {@link android.support.v4.app.FragmentPagerAdapter} derivative, which
	 * will keep every loaded fragment in memory. If this becomes too memory
	 * intensive, it may be best to switch to a
	 * {@link android.support.v4.app.FragmentStatePagerAdapter}.
	 */
	SectionsPagerAdapter mSectionsPagerAdapter;
	Map<String, String> positionXmlMap = new HashMap<String, String>();

	/**
	 * The {@link ViewPager} that will host the section contents.
	 */
	ViewPager mViewPager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// Set up the action bar.
		final ActionBar actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

		loadPositionMap();
		if(false){
			addAd();	
		}
		
		
		// Create the adapter that will return a fragment for each of the three
		// primary sections of the app.
		mSectionsPagerAdapter = new SectionsPagerAdapter(
				getSupportFragmentManager());

		// Set up the ViewPager with the sections adapter.
		mViewPager = (ViewPager) findViewById(R.id.pager);
		mViewPager.setAdapter(mSectionsPagerAdapter);

		// When swiping between different sections, select the corresponding
		// tab. We can also use ActionBar.Tab#select() to do this if we have
		// a reference to the Tab.
		mViewPager
		.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
			@Override
			public void onPageSelected(int position) {
				actionBar.setSelectedNavigationItem(position);
			}
		});

		// For each of the sections in the application, add a tab to the action bar.
		for (int i = 0; i < mSectionsPagerAdapter.getCount(); i++) {
			// Create a tab with text corresponding to the page title defined by
			// the adapter. Also specify this Activity object, which implements
			// the TabListener interface, as the callback (listener) for when
			// this tab is selected.
			actionBar.addTab(actionBar.newTab()
					.setText(mSectionsPagerAdapter.getPageTitle(i))
					.setTabListener(this));
		}
	}
	
	private void addAd() {
		 // Create the adView.
       adView = new AdView(this);
       adView.setAdUnitId("ca-app-pub-4300070308662571/2467683040");
       adView.setAdSize(AdSize.BANNER);
       
    
       // Set the AdListener.
       //adView.setAdListener(this);
    // Lookup your LinearLayout assuming it's been given
       // the attribute android:id="@+id/mainLayout".
       RelativeLayout layout = (RelativeLayout)findViewById(R.id.mainLinearLayout);
       RelativeLayout.LayoutParams adsParams =new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT); 
       adsParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
       layout.addView(adView,adsParams);
       // Add the adView to it.
       //layout.addView(adView);

       // Initiate a generic request.
       AdRequest adRequest = new AdRequest.Builder().build();

       // Load the adView with the ad request.
       adView.loadAd(adRequest);
       
      
	}

	private void loadPositionMap() {
		this.positionXmlMap.put("1", "experience_quotations.xml");
		this.positionXmlMap.put("2", "fear_of_failure.xml");
		this.positionXmlMap.put("3", "follow_your_dreams.xml");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		//getMenuInflater().inflate(R.menu.main, menu);

		return true;
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		switch (requestCode) {
		case RESULT_SETTINGS:
			showUserSettings();
			break;

		}

	}

	@Override
	public void onTabSelected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {
		// When the given tab is selected, switch to the corresponding page in
		// the ViewPager.
		mViewPager.setCurrentItem(tab.getPosition());
	}

	@Override
	public void onTabUnselected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {
	}

	@Override
	public void onTabReselected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {
	}

	private void showUserSettings() {
		SharedPreferences sharedPrefs = PreferenceManager
				.getDefaultSharedPreferences(this);

		StringBuilder builder = new StringBuilder();

		builder.append("\n Username: "
				+ sharedPrefs.getString("prefUsername", "NULL"));

		builder.append("\n Send report:"
				+ sharedPrefs.getBoolean("prefSendReport", false));

		builder.append("\n Sync Frequency: "
				+ sharedPrefs.getString("prefSyncFrequency", "NULL"));

		//TextView settingsTextView = (TextView) findViewById(R.id.textUserSettings);
		//settingsTextView.setText(builder.toString());
		//System.out.println("builder.toString() : " + builder.toString());
	}

	/**
	 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
	 * one of the sections/tabs/pages.
	 */
	public class SectionsPagerAdapter extends FragmentPagerAdapter {

		public SectionsPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			// getItem is called to instantiate the fragment for the given page.
			// Return a DummySectionFragment (defined as a static inner class
			// below) with the page number as its lone argument.
			Fragment fragment = new DummySectionFragment();
			Bundle args = new Bundle();
			int pos = position+1;
            args.putInt(DummySectionFragment.ARG_SECTION_NUMBER, pos);
            QuotesVo quotesVo = getQuotesVo(pos);
            args.putParcelable("Name", quotesVo);
			
			fragment.setArguments(args);
			return fragment;
		}

		@Override
		public int getCount() {
			// Show 3 total pages.
			return 3;
		}

		@Override
		public CharSequence getPageTitle(int position) {
			int pos = position + 1;
			QuotesVo quotesVo = getQuotesVo(pos);
			
			
			if(quotesVo != null && quotesVo.getTopic() != null){
				return quotesVo.getTopic();
			}
			return null;
		}
		
		private QuotesVo getQuotesVo(int position){
			QuotesVo quotesVo = null;
			try {
				if(positionXmlMap.get("" + position) != null){
					quotesVo = SAXXMLParser.parse(getAssets().open(positionXmlMap.get("" + (position))));	
				}
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return quotesVo;
		}
	}

	/**
	 * A dummy fragment representing a section of the app, but that simply
	 * displays dummy text.
	 */
	public static class DummySectionFragment extends ListFragment {
		/**
		 * The fragment argument representing the section number for this
		 * fragment.
		 */
		public static final String ARG_SECTION_NUMBER = "section_number";

		public DummySectionFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.rowlayout,	container, false);
			//TextView dummyTextView = (TextView) rootView.findViewById(R.id.section_label);
			//dummyTextView.setText(Integer.toString(getArguments().getInt(ARG_SECTION_NUMBER)));

			//int pos = getArguments().getInt(ARG_SECTION_NUMBER);
        	QuotesVo quotesVo = getArguments().getParcelable("Name");
        	
			MySimpleArrayAdapter adapter = new MySimpleArrayAdapter(this.getActivity(), quotesVo.getQuoteVoList());
			setListAdapter(adapter);

			return rootView;
		}

	}

}
