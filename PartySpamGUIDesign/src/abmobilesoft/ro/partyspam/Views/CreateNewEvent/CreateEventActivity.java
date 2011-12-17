package abmobilesoft.ro.partyspam.Views.CreateNewEvent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.component.partyspam.Party;

import abmobilesoft.ro.partyspam.BusinessLogic;
import abmobilesoft.ro.partyspam.R;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;
import android.widget.TabHost;

public class CreateEventActivity extends FragmentActivity {
	static final int NUM_STEPS = 3;
	MyAdapter mAdapter;
	TabHost mTabHost;
	ViewPager mViewPager;
	TabsAdapterTabHost mTabsAdapter;
	Button mCreateEventBtn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.createevent_layout);
		mTabHost = (TabHost) findViewById(android.R.id.tabhost);
		mTabHost.setup();
		mViewPager = (ViewPager) findViewById(R.id.pager);
		Button lCreateEventBtn = (Button) findViewById(R.id.btnCreateEvent);

		NewEventDataRepository lNewEventRepository = NewEventDataRepository
				.getInstance();;
		lNewEventRepository.clearEventData();

		mTabsAdapter = new TabsAdapterTabHost(this, mTabHost, mViewPager,
				lCreateEventBtn);
		mTabsAdapter.addTab(mTabHost.newTabSpec("simple")
				.setIndicator("Step 1"),
				CreateEventsStep1Activity.CreateEventStep1Fragment.class, null);
		mTabsAdapter.addTab(
				mTabHost.newTabSpec("simple2").setIndicator("Step 2"),
				CreateEventsStep2Activity.CreateEventStep2Fragment.class, null);
		mTabsAdapter.addTab(
				mTabHost.newTabSpec("simple3").setIndicator("Step 3"),
				CreateEventsStep3Activity.CreateEventStep3Fragment.class, null);
		if (savedInstanceState != null) {
			mTabHost.setCurrentTabByTag(savedInstanceState.getString("tab"));
		}

	}

	public static class MyAdapter extends FragmentStatePagerAdapter {
		public MyAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public int getCount() {
			return NUM_STEPS;
		}

		@Override
		public Fragment getItem(int position) {
			return new CreateEventsStep1Activity.CreateEventStep1Fragment();
		}
	}

	public static class TabsAdapterTabHost extends FragmentPagerAdapter
			implements
				TabHost.OnTabChangeListener,
				ViewPager.OnPageChangeListener {
		private static final String LAST_TAB_ID = "simple3";
		private final Context mContext;
		private final TabHost mTabHost;
		private final ViewPager mViewPager;
		private final ArrayList<TabInfo> mTabs = new ArrayList<TabInfo>();
		Button mCreateEventBtn;
		String mCurrentTabID;

		static final class TabInfo {
			private final String tag;
			private final Class<?> clss;
			private final Bundle args;

			TabInfo(String _tag, Class<?> _class, Bundle _args) {
				tag = _tag;
				clss = _class;
				args = _args;
			}
		}

		static class DummyTabFactory implements TabHost.TabContentFactory {
			private final Context mContext;

			public DummyTabFactory(Context context) {
				mContext = context;
			}

			@Override
			public View createTabContent(String tag) {
				View v = new View(mContext);
				v.setMinimumWidth(0);
				v.setMinimumHeight(10);
				return v;
			}
		}

		public TabsAdapterTabHost(FragmentActivity activity, TabHost tabHost,
				ViewPager pager, Button iCreateEventButton) {
			super(activity.getSupportFragmentManager());
			mContext = activity;
			mTabHost = tabHost;
			mViewPager = pager;
			mTabHost.setOnTabChangedListener(this);
			mViewPager.setAdapter(this);
			mViewPager.setOnPageChangeListener(this);
			mCreateEventBtn = iCreateEventButton;

			mCreateEventBtn.setOnClickListener(new View.OnClickListener() {
				public void onClick(View v) {
					if (mCurrentTabID == LAST_TAB_ID) {
						//we force the save on all steps
						
						for (int i=0;i<NUM_STEPS;++i)
						{
							ICreateEventWizardStep lFragment =(ICreateEventWizardStep) getItem(i);
							lFragment.saveEventData();
						}
						createNewEventBasedOnWizardData();						
					}
				}
			});
		}

		private void createNewEventBasedOnWizardData() {
			
			
			NewEventDataRepository lNewEventRepository = NewEventDataRepository
					.getInstance();;
			BusinessLogic lBl = BusinessLogic.getInstance();
			Party lNewParty = lNewEventRepository.getParty();
			lBl.createEvent(lNewParty);
			
			FragmentActivity lParentActivity = (FragmentActivity) mContext;
			lParentActivity.setResult(RESULT_OK);
			lParentActivity.finish();
		}

		public void addTab(TabHost.TabSpec tabSpec, Class<?> clss, Bundle args) {
			tabSpec.setContent(new DummyTabFactory(mContext));
			String tag = tabSpec.getTag();

			TabInfo info = new TabInfo(tag, clss, args);
			mTabs.add(info);
			mTabHost.addTab(tabSpec);
			notifyDataSetChanged();
		}

		@Override
		public int getCount() {
			return mTabs.size();
		}

		Map<Integer,Fragment> mFragmentList =new HashMap<Integer,Fragment>();
		@Override
		public Fragment getItem(int position) {
			//we need to keep a reference to our wizard steps
			TabInfo info = mTabs.get(position);
			if (mFragmentList.containsKey(position))
			{
				return mFragmentList.get(position);				
			}
			else
			{
				Fragment lFragment = Fragment.instantiate(mContext, info.clss.getName(),
						info.args);
				mFragmentList.put(position, lFragment);
				return lFragment;	
			}
			
		}

		@Override
		public void onTabChanged(String tabId) {
			mCurrentTabID = tabId;
			if (tabId == LAST_TAB_ID) {
				mCreateEventBtn.setText("Done");
			} else {
				mCreateEventBtn
						.setText("     Swipe to get to the next and previous steps");
			}
			int position = mTabHost.getCurrentTab();
			mViewPager.setCurrentItem(position);
			
			
		}

		@Override
		public void onPageScrolled(int position, float positionOffset,
				int positionOffsetPixels) {
		}

		@Override
		public void onPageSelected(int position) {
			mTabHost.setCurrentTab(position);
		}

		@Override
		public void onPageScrollStateChanged(int state) {
		}
	}
}
