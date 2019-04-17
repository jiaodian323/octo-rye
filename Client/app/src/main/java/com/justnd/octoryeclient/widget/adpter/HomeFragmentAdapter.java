package com.justnd.octoryeclient.widget.adpter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;
import android.view.ViewGroup;

import com.justnd.octoryeclient.module.home.HomeRecommendedFragment;

/**
 * @author Justiniano  Email:jiaodian822@163.com
 * @Description:
 * @throws
 * @time 2019/4/12 0012 下午 3:15
 */
public class HomeFragmentAdapter extends FragmentStatePagerAdapter {
    // SparseArray是Hashmap的改良品，其核心是折半查找函数（binarySearch）
//    SparseArray<WeakReference<Fragment>> registeredFragments = new SparseArray<WeakReference<Fragment>>();
//    private List<Integer> mList;

    public HomeFragmentAdapter(FragmentManager fm) {
        super(fm);
    }

//    public CustomViewPagerAdapter(FragmentManager fm, List<Integer> list) {
//        this(fm);
//        mList = list;
//    }

    /*
     * 生成新的 Fragment 对象。 .instantiateItem() 在大多数情况下，都将调用 getItem() 来生成新的对象
     */
    @Override
    public Fragment getItem(int position) {
        // TODO Auto-generated method stub
        HomeRecommendedFragment fragment = HomeRecommendedFragment.newInstance();
        return fragment;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        // TODO Auto-generated method stub
        // 得到缓存的fragment
        HomeRecommendedFragment fragment = (HomeRecommendedFragment) super.instantiateItem(container,
                position);
        Log.i("AdapterTest", String.valueOf(position));
//        WeakReference<Fragment> weak = new WeakReference<Fragment>(fragment);
//        registeredFragments.put(position, weak);
        return fragment;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
//        return 1;
        return Integer.MAX_VALUE;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        // TODO Auto-generated method stub
//        registeredFragments.remove(position);

        super.destroyItem(container, position, object);

    }

    /**
     * 要求getItemPosition、FragmentStatePagerAdapter
     */
//    public void remove(int position) {
//        mList.remove(position);
//        notifyDataSetChanged();
//    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

//    public HomeContainerFragment getRegisteredFragment(int position) {
//        return (HomeContainerFragment) registeredFragments.get(position).get();
//    }
}
