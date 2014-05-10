package com.sunita.infantlearning;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v13.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class AlphaSwipeActivity extends Activity {

	/**
	 * The {@link android.support.v4.view.PagerAdapter} that will provide
	 * fragments for each of the sections. We use a {@link FragmentPagerAdapter}
	 * derivative, which will keep every loaded fragment in memory. If this
	 * becomes too memory intensive, it may be best to switch to a
	 * {@link android.support.v13.app.FragmentStatePagerAdapter}.
	 */
	SectionsPagerAdapter mSectionsPagerAdapter;

	/**
	 * The {@link ViewPager} that will host the section contents.
	 */
	ViewPager mViewPager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.alpha_swipe);

		// Create the adapter that will return a fragment for each of the three
		// primary sections of the activity.
		mSectionsPagerAdapter = new SectionsPagerAdapter(getFragmentManager());

		// Set up the ViewPager with the sections adapter.
		mViewPager = (ViewPager) findViewById(R.id.pager);
		mViewPager.setAdapter(mSectionsPagerAdapter);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		//getMenuInflater().inflate(R.menu.alpha_swipe, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
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
			// Return a PlaceholderFragment (defined as a static inner class
			// below).
			return PlaceholderFragment.newInstance(position + 1);
		}

		@Override
		public int getCount() {
			// Show 3 total pages.
			return 26;
		}

		@Override
		public CharSequence getPageTitle(int position) {
			/*Locale l = Locale.getDefault();
			switch (position) {
			case 0:
				return getString(R.string.title_section1).toUpperCase(l);
			case 1:
				return getString(R.string.title_section2).toUpperCase(l);
			case 2:
				return getString(R.string.title_section3).toUpperCase(l);
			}*/
			return null;
		}
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {
		/**
		 * The fragment argument representing the section number for this
		 * fragment.
		 */
		private static final String ARG_SECTION_NUMBER = "section_number";
		private Map<String, String> audioMap = new HashMap<String, String>();
		String[] strArr = new String[]{"A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z"};
		private final Integer fontSize1 = 150;
		  private int fontColour = Color.GREEN;
		/**
		 * Returns a new instance of this fragment for the given section number.
		 */
		public static PlaceholderFragment newInstance(int sectionNumber) {
			PlaceholderFragment fragment = new PlaceholderFragment();
			Bundle args = new Bundle();
			args.putInt(ARG_SECTION_NUMBER, sectionNumber);
			fragment.setArguments(args);
			return fragment;
		}

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_alpha_swipe,
					container, false);
			Integer fontSize = (int) (fontSize1 * getResources().getDisplayMetrics().density);
			TextView txt = (TextView) rootView
					.findViewById(R.id.section_label);
			
			txt.setTextColor(fontColour);
	        //txt.setTextSize(TypedValue.COMPLEX_UNIT_SP,fontSize);
	        txt.setTextSize(fontSize);
	        txt.setGravity(Gravity.CENTER_HORIZONTAL);
	        String str = strArr[getArguments().getInt(ARG_SECTION_NUMBER)-1];
	        txt.setText(str);
	        final MediaPlayer mp = MediaPlayer.create(this.getActivity(), getId(str.toLowerCase(), R.raw.class));

	        txt.setOnClickListener(new View.OnClickListener() {
	            public void onClick(View v) {
	                mp.start();
	            }
	        });
			return rootView;
		}
		
		public int getId(String resourceName, Class<?> c) {
		    try {
		        Field idField = c.getDeclaredField(resourceName);
		        return idField.getInt(idField);
		    } catch (Exception e) {
		        throw new RuntimeException("No resource ID found for: "
		                + resourceName + " / " + c, e);
		    }
		}
		private void populateHasMap() {
			audioMap.put("A", "a.mp3");
			audioMap.put("B", "b.mp3");
			audioMap.put("C", "c.mp3");
			audioMap.put("D", "d.mp3");
			audioMap.put("E", "e.mp3");
			audioMap.put("F", "f.mp3");
			audioMap.put("G", "g.mp3");
			audioMap.put("H", "h.mp3");
			audioMap.put("I", "i.mp3");
			audioMap.put("J", "j.mp3");
			audioMap.put("K", "k.mp3");
			audioMap.put("L", "l.mp3");
			audioMap.put("M", "m.mp3");
			audioMap.put("N", "n.mp3");
			audioMap.put("O", "o.mp3");
			audioMap.put("P", "p.mp3");
			audioMap.put("Q", "q.mp3");
			audioMap.put("R", "r.mp3");
			audioMap.put("S", "s.mp3");
			audioMap.put("T", "t.mp3");
			audioMap.put("U", "u.mp3");
			audioMap.put("V", "v.mp3");
			audioMap.put("W", "w.mp3");
			audioMap.put("X", "x.mp3");
			audioMap.put("Y", "y.mp3");
			audioMap.put("Z", "z.mp3");
			
		}
	}

}
