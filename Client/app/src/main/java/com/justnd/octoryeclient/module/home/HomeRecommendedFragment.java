package com.justnd.octoryeclient.module.home;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.justnd.octoryeclient.R;
import com.justnd.octoryeclient.entity.common.ContentType;
import com.justnd.octoryeclient.module.home.section.HomeMusicSection;
import com.justnd.octoryeclient.module.home.section.HomeRecommendBannerSection;
import com.justnd.octoryeclient.module.home.section.HomeArticleSection;
import com.justnd.octoryeclient.entity.base.BaseBean;
import com.justnd.octoryeclient.entity.recommond.RecommendBannerInfo;
import com.justnd.octoryeclient.entity.recommond.RecommendInfo;
import com.justnd.octoryeclient.module.base.RxLazyFragment;
import com.justnd.octoryeclient.network.RetrofitHelper;
import com.justnd.octoryeclient.utils.ConstantUtil;
import com.justnd.octoryeclient.widget.CustomEmptyView;
import com.justnd.octoryeclient.widget.Sectioned.SectionedRecyclerViewAdapter;
import com.justnd.octoryeclient.widget.banner.BannerEntity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class HomeRecommendedFragment extends RxLazyFragment {
    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @BindView(R.id.recycle)
    RecyclerView mRecyclerView;
    @BindView(R.id.empty_layout)
    CustomEmptyView mCustomEmptyView;

    public static final String TAG = "HomeRecommendedFG_JD";
    private static final String DATE_TAG = "date";

    // 测试代码
    private static int testCount = 0;

    private boolean mIsRefreshing = false;
    private SectionedRecyclerViewAdapter mSectionedAdapter;
    private List<BannerEntity> banners = new ArrayList<>();
    private List<RecommendInfo.ResultBean> results = new ArrayList<>();
    private List<RecommendBannerInfo.DataBean> recommendBanners = new ArrayList<>();

    public HomeRecommendedFragment() {
    }

    public static HomeRecommendedFragment newInstance(String dateStr) {
        HomeRecommendedFragment fragment = new HomeRecommendedFragment();
        Bundle bundle = new Bundle();
        bundle.putString(DATE_TAG, dateStr);
        fragment.setArguments(bundle);

        return fragment;
    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_home_recommended;
    }

    @Override
    public void finishCreateView(Bundle state) {
        // 测试代码，给Fragment设置ID,用来测试懒加载
//        int id = getArguments().getInt("id");
//        Log.i("SlideTest", "fragment id=" + id + "---in finishCreateView()");

        isPrepared = true;
        lazyLoad();
    }

    @Override
    protected void lazyLoad() {
        // 测试代码，给Fragment设置ID,用来测试懒加载
//        int id = getArguments().getInt("id");
//        Log.i("SlideTest", "fragment id=" + id + "---in lazyLoad():isPrepared=" + isPrepared +
//        "," +
//                "isVisible=" + isVisible);

        if (!isPrepared || !isVisible) {
            return;
        }
        initRefreshLayout();
        initRecyclerView();
        isPrepared = false;
    }

    @Override
    protected void initRecyclerView() {
        mSectionedAdapter = new SectionedRecyclerViewAdapter();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(mSectionedAdapter);
        setRecycleNoScroll();
    }

    @Override
    protected void initRefreshLayout() {
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
        mSwipeRefreshLayout.post(() -> {
            mSwipeRefreshLayout.setRefreshing(true);
            mIsRefreshing = true;
            loadData();
        });
        mSwipeRefreshLayout.setOnRefreshListener(() -> {
            clearData();
            loadData();
        });
    }

    @SuppressWarnings("all")
    @Override
    protected void loadData() {
        // 此处代码较为晦涩，请小心阅读
        RetrofitHelper.getBiliTestService()
                .getRecommendedBannerInfo()
                .compose(bindToLifecycle())
                .map(RecommendBannerInfo::getData)
                .flatMap(new Func1<List<RecommendBannerInfo.DataBean>,
                        Observable<BaseBean<RecommendInfo>>>() {
                    @Override
                    public Observable<BaseBean<RecommendInfo>> call(List<RecommendBannerInfo
                            .DataBean> dataBeans) {
                        recommendBanners.addAll(dataBeans);
                        Log.i(TAG, "向服务器发送recommend查询请求");
                        String dateStr = getArguments().getString(DATE_TAG);
                        return RetrofitHelper.getRecommendService().getRecommendedInfo(dateStr);
                    }
                })
                .compose(bindToLifecycle())
                .map(new Func1<BaseBean<RecommendInfo>, List<RecommendInfo.ResultBean>>() {
                    @Override
                    public List<RecommendInfo.ResultBean> call(BaseBean<RecommendInfo>
                                                                                recommendInfoBaseBean) {
                        // 将返回的BaseBean<RecommendInfo>类型转换为，
                        // RecommendInfo内部resultbean数据,然后再转换为实际数据BodyBean列表
                        return recommendInfoBaseBean.getData().getResult();
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(resultBeans -> {
                    results.addAll(resultBeans);
                    finishTask();
                }, throwable -> initEmptyView());
    }

    public void initEmptyView() {
        mSwipeRefreshLayout.setRefreshing(false);
        mCustomEmptyView.setVisibility(View.VISIBLE);
        mRecyclerView.setVisibility(View.GONE);
        mCustomEmptyView.setEmptyImage(R.drawable.img_tips_error_load_error);
        mCustomEmptyView.setEmptyText("客官，请稍后");
//        SnackbarUtil.showMessage(mRecyclerView, "数据加载失败,请重新加载或者检查网络是否链接");
    }

    @Override
    protected void finishTask() {
        mSwipeRefreshLayout.setRefreshing(false);
        mIsRefreshing = false;
        hideEmptyView();
        convertBanner();
        mSectionedAdapter.addSection(new HomeRecommendBannerSection(banners));
        int size = results.size();
        for (int i = 0; i < size; i++) {
            String type = results.get(i).getStyle();
            ContentType typeC = ContentType.valueOf(type);
            if (!TextUtils.isEmpty(type)) {

                switch (typeC) {
                    case ARTICLE:
                        mSectionedAdapter.addSection(new HomeArticleSection(
                                getActivity(),
                                results.get(i).getStyle(),
                                results.get(i).getBody()));
                        break;
                    case MUSIC:
                        mSectionedAdapter.addSection(new HomeMusicSection(
                                getActivity(),
                                results.get(i).getStyle(),
                                results.get(i).getBody()));
                        break;
                    case VIDEO:
                        break;
                    case AUDIO:
                        break;
                    default:
                        mSectionedAdapter.addSection(new HomeArticleSection(
                                getActivity(),
                                results.get(i).getStyle(),
                                results.get(i).getBody()));
                        break;
                }
            }
        }
        mSectionedAdapter.notifyDataSetChanged();
    }

    private void convertBanner() {
        Observable.from(recommendBanners)
                .compose(bindToLifecycle())
                .forEach(dataBean -> banners.add(new BannerEntity(dataBean.getValue(),
                        dataBean.getTitle(), dataBean.getImage())));
    }

    public void hideEmptyView() {
        mCustomEmptyView.setVisibility(View.GONE);
        mRecyclerView.setVisibility(View.VISIBLE);
    }

    private void clearData() {
        banners.clear();
        recommendBanners.clear();
        results.clear();
        mIsRefreshing = true;
        mSectionedAdapter.removeAllSections();
    }

    private void setRecycleNoScroll() {
        mRecyclerView.setOnTouchListener((v, event) -> mIsRefreshing);
    }

}
