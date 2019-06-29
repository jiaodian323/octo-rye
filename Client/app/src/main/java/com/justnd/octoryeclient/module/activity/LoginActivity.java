package com.justnd.octoryeclient.module.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.justnd.octoryeclient.R;
import com.justnd.octoryeclient.entity.base.BaseBean;
import com.justnd.octoryeclient.entity.user.LoginBean;
import com.justnd.octoryeclient.entity.user.UserInfo;
import com.justnd.octoryeclient.module.base.RxBaseActivity;
import com.justnd.octoryeclient.module.user.MeFragment;
import com.justnd.octoryeclient.network.RetrofitHelper;
import com.justnd.octoryeclient.utils.ConstantUtil;
import com.justnd.octoryeclient.utils.DebugTagUtil;
import com.justnd.octoryeclient.utils.SMSUtil;
import com.justnd.octoryeclient.utils.ToastUtil;

import butterknife.BindView;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class LoginActivity extends RxBaseActivity {
    @BindView(R.id.login_title_image)
    ImageView mTitleImage;
    @BindView(R.id.login_title_toolbar)
    Toolbar mToolbar;
    @BindView(R.id.sign_up_tv)
    TextView mSignUpTv;
    @BindView(R.id.phoneNumber)
    EditText mPhoneNumber;
    @BindView(R.id.password)
    EditText mPass;
    @BindView(R.id.login_btn)
    Button mLogin;

    View.OnClickListener loginListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String phoneNumber = mPhoneNumber.getText().toString();
            String pass = mPass.getText().toString();

            if (inputLegalCheck(phoneNumber, pass)) {
                LoginBean bean = new LoginBean();
                bean.setPhoneNumber(phoneNumber);
                bean.setPassword(pass);

                RetrofitHelper.getUserService()
                        .login(bean)
                        .compose(bindToLifecycle())
                        .flatMap(new Func1<BaseBean<Integer>, Observable<BaseBean<UserInfo>>>() {
                                     @Override
                                     public Observable<BaseBean<UserInfo>> call(BaseBean<Integer> integerBaseBean) {
                                         return handleLoginResponse(integerBaseBean);
                                     }
                                 }
                        )
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(s -> {
                            if (s.getCode() == ConstantUtil.STATUS_CODE_SUCCESS) {
                                UserInfo info = s.getData();

                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                // 约定键值对的值为2时，启动个人中心界面
                                intent.putExtra(MainActivity.ME_TAG, 2);
                                intent.putExtra(MeFragment.NICKNAME_KEY, info.getNickName());
                                intent.putExtra(MeFragment.PROFILE_PIC_KEY, info.getProfilePicture());
                                intent.putExtra(MeFragment.BACKGROUND_KEY, info.getBackground());
                                LoginActivity.this.startActivity(intent);
                            }
                        });
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        mSignUpTv.setOnClickListener(v -> {
            // 启动注册界面
            Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            LoginActivity.this.startActivity(intent);

            // 测试代码
//            Intent intent = new Intent(LoginActivity.this, FullScreenPlayerActivity.class);
//            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            LoginActivity.this.startActivity(intent);
        });

        mLogin.setOnClickListener(loginListener);
    }

    @Override
    public void initToolBar() {
        setSupportActionBar(mToolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);  // 左侧添加一个默认的返回图标
            getSupportActionBar().setHomeButtonEnabled(true);  // 设置返回键可用
        }

        mToolbar.setNavigationOnClickListener(v -> finish());

        // 设置标题栏背景图
        Glide.with(this)
                .load(R.drawable.login_title)
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .dontAnimate()
                .into(mTitleImage);
    }

    public static void launch(Activity activity) {
        Intent intent = new Intent(activity, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        activity.startActivity(intent);
    }

    private boolean inputLegalCheck(String phoneNumber, String pass) {
        if (phoneNumber.length() == 0) {
            ToastUtil.ShortToast(R.string.error_phone_number_null);
            return false;
        }

        if (!SMSUtil.isPhoneNumber(phoneNumber)) {
            ToastUtil.ShortToast(R.string.error_invalid_phone_number);
            return false;
        }

        if (pass.length() == 0) {
            ToastUtil.ShortToast(R.string.error_pass_null);
            return false;
        }

        return true;
    }

    private Observable<BaseBean<UserInfo>> handleLoginResponse(BaseBean<Integer> integerBaseBean) {
        if (integerBaseBean.getCode() == ConstantUtil.STATUS_CODE_SUCCESS) {
            // 登录成功
            ToastUtil.ShortToast(R.string.success_login);
            Integer userId = integerBaseBean.getData();

            // 登录成功后，继续向服务器请求该用户信息
            return RetrofitHelper.getUserService().getUserInfo(userId);
        } else if (integerBaseBean.getCode() == ConstantUtil.STATUS_CODE_FAIL) {
            // 登录失败
            ToastUtil.ShortToast(R.string.error_phone_or_pass);
            return null;
        }

        return null;
    }

    @Override
    public void onStart() {
        Log.i(DebugTagUtil.FULLSCREEN_ACTIVITY_TAG, "LoginActivity----------onStart()");
        super.onStart();
    }

    @Override
    public void onResume() {
        Log.i(DebugTagUtil.FULLSCREEN_ACTIVITY_TAG, "LoginActivity----------onResume()");
        super.onResume();
    }

    @Override
    public void onPause() {
        Log.i(DebugTagUtil.FULLSCREEN_ACTIVITY_TAG, "LoginActivity----------onPause()");
        super.onPause();
    }

    @Override
    public void onStop() {
        Log.i(DebugTagUtil.FULLSCREEN_ACTIVITY_TAG, "LoginActivity----------onStop()");
        super.onStop();
    }

    @Override
    public void onDestroy() {
        Log.i(DebugTagUtil.FULLSCREEN_ACTIVITY_TAG, "LoginActivity----------onDestroy()");
        super.onDestroy();
    }
}
