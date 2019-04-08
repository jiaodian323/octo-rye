package com.justnd.octoryeclient.module.user;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.justnd.octoryeclient.R;
import com.justnd.octoryeclient.module.activity.LoginActivity;
import com.justnd.octoryeclient.module.base.RxLazyFragment;

import butterknife.BindView;

public class LoginModeFragment extends RxLazyFragment {
    @BindView(R.id.wechat_login)
    Button mWechatLoginBtn;
    @BindView(R.id.phone_sign_up)
    Button mPhoneSignUpBtn;

    private Context mContext;

    public LoginModeFragment() {
    }

    public static LoginModeFragment newInstance() {
        return new LoginModeFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_login_mode;
    }

    @Override
    public void finishCreateView(Bundle state) {
        mPhoneSignUpBtn.setOnClickListener(phoneSignUpListener);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    View.OnClickListener phoneSignUpListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            LoginActivity.launch((Activity) mContext);
        }
    };
}
