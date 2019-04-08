package com.justnd.octoryeclient.module.activity;

import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.justnd.octoryeclient.R;
import com.justnd.octoryeclient.entity.user.SignUpInfo;
import com.justnd.octoryeclient.module.base.RxBaseActivity;
import com.justnd.octoryeclient.network.RetrofitHelper;
import com.justnd.octoryeclient.security.SecurityModule;
import com.justnd.octoryeclient.utils.ToastUtil;

import butterknife.BindView;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class SignUpConfirmActivity extends RxBaseActivity {
    @BindView(R.id.sign_up_confirm_toolbar)
    Toolbar mToolbar;
    @BindView(R.id.sign_up_confirm_title_image)
    ImageView mTitleImage;
    @BindView(R.id.pass_layout)
    TextInputLayout mPassLayout;
    @BindView(R.id.pass)
    EditText mPassEt;
    @BindView(R.id.sign_up_btn)
    Button mSignUpBtn;

    /**
     * @Fields: 保存注册手机号字段
     */
    private String mSignUpPhoneNumber;


    /**
     * @Fields: 用户注册按钮监听器
     */
    private View.OnClickListener signUpBtnListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String pass = mPassEt.getText().toString();
            if (!SecurityModule.isPassCorrect(pass)) {
                ToastUtil.ShortToast(R.string.tip_incorrect_pass);
                return;
            } else {
                // 发送注册请求
                sendSignUpRequest(pass);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_sign_up_confirm;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        mSignUpPhoneNumber = getIntent().getStringExtra(SignUpActivity.PHONE_EXTRA_KEY);
        mSignUpBtn.setOnClickListener(signUpBtnListener);

    }

    @Override
    public void initToolBar() {
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);  // 左侧添加一个默认的返回图标
        getSupportActionBar().setHomeButtonEnabled(true);  // 设置返回键可用

        mToolbar.setNavigationOnClickListener(v -> finish());

        // 设置标题栏背景图
        Glide.with(this)
                .load(R.drawable.sign_up_confirm_title)
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .dontAnimate()
                .into(mTitleImage);
    }

    private void sendSignUpRequest(String pass) {
        SignUpInfo sign = new SignUpInfo();
        sign.setSignUpMode(1);
        sign.setPhoneNumber(mSignUpPhoneNumber);
        sign.setPass(pass);

        RetrofitHelper.getUserService()
                .signUp(sign)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(s -> {
                            int result = s.getCode();
                            if (result == 1) {
                                // 注册成功
                                ToastUtil.ShortToast(R.string.success_sign_up);
//                                        UserInfo user = s.getData();
                            } else if (result == 0) {
                                ToastUtil.ShortToast(R.string.failed_sign_up);
                            }

                        },
                        throwable -> ToastUtil.showShort(SignUpConfirmActivity.this, R
                                .string.error_server_connect));
    }
}
