package com.justnd.octoryeclient.module.home.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;
import android.view.ViewGroup;

import com.justnd.octoryeclient.module.home.HomeRecommendedFragment;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author Justiniano  Email:jiaodian822@163.com
 * @Description:
 * @throws
 * @time 2019/4/12 0012 下午 3:15
 */
public class HomeFragmentAdapter extends FragmentStatePagerAdapter {
    // SparseArray是Hashmap的改良品，其核心是折半查找函数（binarySearch）
//    SparseArray<WeakReference<Fragment>> registeredFragments = new
// SparseArray<WeakReference<Fragment>>();
//    private List<Integer> mList;

    public HomeFragmentAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        HomeRecommendedFragment fragment = HomeRecommendedFragment.newInstance(getDateOfFragment
                (position));
        return fragment;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        // 得到缓存的fragment
        HomeRecommendedFragment fragment = (HomeRecommendedFragment) super.instantiateItem
                (container, position);
        Log.i("AdapterTest", String.valueOf(position));
//        WeakReference<Fragment> weak = new WeakReference<Fragment>(fragment);
//        registeredFragments.put(position, weak);
        return fragment;
    }

    @Override
    public int getCount() {
        return Integer.MAX_VALUE;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        super.destroyItem(container, position, object);
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    public String getDateOfFragment(int position) {
        Date today = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(today);
        int day = c.get(Calendar.DATE);
        c.set(Calendar.DATE, day - position);

        String dateOfFragment = new SimpleDateFormat("yyyyMMdd").format(c.getTime());
//        String dateOfFragment = SimpleDateFormat.getDateInstance().format(c.getTime());
        return dateOfFragment;
    }

}
