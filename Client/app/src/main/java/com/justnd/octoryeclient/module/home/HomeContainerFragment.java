package com.justnd.octoryeclient.module.home;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.widget.TextView;

import com.justnd.octoryeclient.R;
import com.justnd.octoryeclient.module.base.RxLazyFragment;
import com.justnd.octoryeclient.module.home.adapter.DepthPageTransformer;
import com.justnd.octoryeclient.module.home.adapter.HomeFragmentAdapter;

import butterknife.BindView;

public class HomeContainerFragment extends RxLazyFragment {
    @BindView(R.id.day)
    TextView mday;
    @BindView(R.id.month_and_year)
    TextView mMonthAndYear;
    @BindView(R.id.viewpager)
    ViewPager mViewPager;

    public HomeContainerFragment() {
    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_home_container;
    }

    @Override
    public void finishCreateView(Bundle sate) {
        HomeFragmentAdapter adapter = new HomeFragmentAdapter(getChildFragmentManager());
        mViewPager.setAdapter(adapter);
        mViewPager.setPageTransformer(true, new DepthPageTransformer());
//        mViewPager.setPageTransformer(true, new ZoomOutPageTransformer());
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int
                    positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                String dateStr = adapter.getDateOfFragment(position);
                mday.setText(extractDay(dateStr));
                mMonthAndYear.setText(extractMonthAndYear(dateStr));
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        // 第一次创建ViewPager时，停留在位置0处，需要单独获取当前时间，向TextView填入日期字符
        String dateStr = adapter.getDateOfFragment(0);
        mday.setText(extractDay(dateStr));
        mMonthAndYear.setText(extractMonthAndYear(dateStr));
    }

    // TODO: Rename and change types and number of parameters
    public static HomeContainerFragment newInstance() {
        HomeContainerFragment fragment = new HomeContainerFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    /**
    * @Description: 提取日字符串
    * @param
    * @return
    * @throws
    * @author Justiniano  Email:jiaodian822@163.com
    */
    private String extractDay(String date) {
        return date.substring(6);
    }

    /**
    * @Description: 提取年和月字符串
    * @param
    * @return
    * @throws
    * @author Justiniano  Email:jiaodian822@163.com
    */
    private String extractMonthAndYear(String date) {
        String year = date.substring(0, 4);
        String monthOrigin = date.substring(4, 6);
        String month = monthConvert(monthOrigin);

        return month + year;
    }

    /** 
    * @Description: 将月份转换为英文缩写形式
    * @param 
    * @return 
    * @throws 
    * @author Justiniano  Email:jiaodian822@163.com
    */
    private String monthConvert(String origin) {
        String month = "";
        switch (origin) {
            case "01":
                month = "Jan.";
                break;
            case "02":
                month = "Feb.";
                break;
            case "03":
                month = "Mar.";
                break;
            case "04":
                month = "Apr.";
                break;
            case "05":
                month = "May.";
                break;
            case "06":
                month = "Jun.";
                break;
            case "07":
                month = "Jul.";
                break;
            case "08":
                month = "Aug.";
                break;
            case "09":
                month = "Sept.";
                break;
            case "10":
                month = "Oct.";
                break;
            case "11":
                month = "Nov.";
                break;
            case "12":
                month = "Dec.";
                break;
            default:
                break;
        }

        return month;
    }
}
