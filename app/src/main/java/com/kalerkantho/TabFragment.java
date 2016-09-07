package com.kalerkantho;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bangladesh_pratidin.R;
import com.kalerkantho.Fragment.AllnewsFragment;
import com.kalerkantho.Fragment.LatestNewsFragment;
import com.kalerkantho.Fragment.MostReadFragment;
import com.kalerkantho.Fragment.NirbaChitoCategoryFragment;
import com.kalerkantho.Fragment.PrintVFragment;
import com.kalerkantho.Fragment.SelectedNewsFragment;
import com.kalerkantho.Fragment.SubjectLikeFragment;
import com.kalerkantho.Fragment.TopNewsFragment;


public class TabFragment extends Fragment {

    public static TabLayout tabLayout;
    public static ViewPager viewPager;
   public static int int_items = 9;
    private int fragmentPos=0;
    private String tabTitles[] = new String[] { "হোম", "শীর্ষ সংবাদ", "সর্বশেষ সংবাদ", "সর্বাধিক দর্শন", "নির্বাচিত সংবাদ", "প্রিন্ট ভার্সন", "নির্বাচিত ক্যাটাগরি","বিষয় পছন্দ করুন","সকল সংবাদ"};
    private MyAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        /**
         *Inflate tab_layout and setup Views.
         */
             View x =  inflater.inflate(R.layout.tab_layout,null);
             tabLayout = (TabLayout) x.findViewById(R.id.tabs);
             viewPager = (ViewPager) x.findViewById(R.id.viewpager);

       // final Typeface face_reg = Typeface.createFromAsset(getActivity().getAssets(), "fonts/SolaimanLipi_reg.ttf");





        Bundle bundle = this.getArguments();
        if (bundle != null) {
            fragmentPos = bundle.getInt("pos", 0);
        }

        adapter=new MyAdapter(getChildFragmentManager());

            viewPager.setAdapter(adapter);
            setPageItem(fragmentPos);



        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                Log.e("position",""+position);

            }

            @Override
            public void onPageScrollStateChanged(int state) {


            }
        });




        tabLayout.post(new Runnable() {
            @Override
            public void run() {

                    tabLayout.setupWithViewPager(viewPager);
                // Iterate over all tabs and set the custom view
                for (int i = 0; i < tabLayout.getTabCount(); i++) {
                    TabLayout.Tab tab = tabLayout.getTabAt(i);
                    tab.setCustomView(adapter.getTabView(i));
                }
                   }
        });

        setPageItem(fragmentPos);

//        // Iterate over all tabs and set the custom view




        return x;

    }

    void setPageItem(int i)
    {
        viewPager.setCurrentItem(i);
    }




    class MyAdapter extends FragmentPagerAdapter {


        public MyAdapter(FragmentManager fm) {
            super(fm);

        }

        /**
         * Return fragment with respect to Position .
         *///        for (int i = 0; i < tabLayout.getTabCount(); i++) {
//            TabLayout.Tab tab = tabLayout.getTabAt(i);
//            tab.setCustomView(adapter.getTabView(i));
//        }
//
        @Override
        public Fragment getItem(int position)
        {
           // position = AppConstant.POSITION;

            switch (position){

              case 0 :

                  return new HomeFragment();

              case 1 :

                  return new TopNewsFragment();

              case 2 :

                  return new LatestNewsFragment();

              case 3 :

                  return new MostReadFragment();

              case 4 :

                  return new SelectedNewsFragment();

              case 5 :

                  return new PrintVFragment();

              case 6 :

                  return new NirbaChitoCategoryFragment();

              case 7 :

                  return new SubjectLikeFragment();

                case 8 :

                    return new AllnewsFragment();

          }
        return null;
        }

        @Override
        public int getCount() {

            return int_items;

        }

        @Override
        public CharSequence getPageTitle(int position) {
                return tabTitles[position];
        }

        public View getTabView(int position) {
            // Given you have a custom layout in `res/layout/custom_tab.xml` with a TextView and ImageView
            View v = LayoutInflater.from(getActivity()).inflate(R.layout.tab_title_layout, null);
            TextView tv = (TextView) v.findViewById(R.id.tableTitle);
            final Typeface face_reg = Typeface.createFromAsset(getActivity().getAssets(), "fonts/SolaimanLipi_reg.ttf");
            tv.setTypeface(face_reg);
            tv.setText(tabTitles[position]);

            return v;
        }
    }


}
