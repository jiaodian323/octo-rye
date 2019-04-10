package com.justnd.octoryeclient.module.user;

import android.content.Context;
import android.os.Bundle;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.justnd.octoryeclient.R;
import com.justnd.octoryeclient.module.base.RxLazyFragment;
import com.justnd.octoryeclient.widget.customview.CircleImageView;

import butterknife.BindView;

public class MeFragment extends RxLazyFragment {
    @BindView(R.id.profile_picture)
    CircleImageView mProfilePic;
    @BindView(R.id.nickname)
    TextView mNicknameTv;

    public static final String NICKNAME_KEY = "nickname_key";
    public static final String PROFILE_PIC_KEY = "profile_key";
    public static final String BACKGROUND_KEY = "background";

    /**
     * @Fields: 用户昵称
     */
    private String mNicknameStr;
    /**
     * @Fields: 用户头像地址
     */
    private String mProfilePicUrl;
    /**
     * @Fields: 用户背景图地址
     */
    private String mBackgroundUrl;


    public static MeFragment newInstance() {
        return new MeFragment();
    }

    public MeFragment() {
        // Required empty public constructor
    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_me;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle extras = getActivity().getIntent().getExtras();
        mNicknameStr = extras.getString(NICKNAME_KEY);
        mProfilePicUrl = extras.getString(PROFILE_PIC_KEY);
        mBackgroundUrl = extras.getString(BACKGROUND_KEY);
    }

    @Override
    public void finishCreateView(Bundle state) {
        // 设置昵称
        mNicknameTv.setText(mNicknameStr);

        // 设置头像
        Glide.with(this)
                .load(mProfilePicUrl)
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.default_man)
                .dontAnimate()
                .into(mProfilePic);

        // 设置背景图
//        Glide.with(this)
//                .load(mBackgroundUrl)
//                .centerCrop()
//                .diskCacheStrategy(DiskCacheStrategy.ALL)
//                .placeholder(R.drawable.me_default_background)
//                .dontAnimate()
//                .into(mProfilePic);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
