package com.justnd.octoryeclient.module.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.justnd.octoryeclient.R;
import com.justnd.octoryeclient.entity.base.BaseBean;
import com.justnd.octoryeclient.entity.user.MobileInfo;
import com.justnd.octoryeclient.entity.user.UserInfo;
import com.justnd.octoryeclient.module.base.RxBaseActivity;
import com.justnd.octoryeclient.network.RetrofitHelper;
import com.justnd.octoryeclient.security.SecurityModule;
import com.justnd.octoryeclient.utils.MobileInfoUtil;
import com.justnd.octoryeclient.utils.ToastUtil;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class SplashActivity extends RxBaseActivity {
    /**
    * @Fields: 界面停留时间，3秒
    */
    private static int delayTime = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        loadData();

        Handler handler = new Handler();
        //当计时结束时，跳转至主界面
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashActivity.this, MainActivity.class));
                SplashActivity.this.finish();
            }
        }, delayTime);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    public void initViews(Bundle savedInstanceState) { }

    @Override
    public void initToolBar() {}

    @Override
    public void loadData() {
        MobileInfo mobile = new MobileInfo();
        mobile.setImei(MobileInfoUtil.getIMEI(this));
        mobile.setImsi(MobileInfoUtil.getIMSI(this));

        UserInfo userInfo = new UserInfo();
        userInfo.setMobileInfo(mobile);
//        String userStr = GsonUtil.objectToJsonStr(userInfo);

        // 从服务器获取公钥字符串
        RetrofitHelper.getSecurityService()
                .getSecurityInfo()
                .compose(bindToLifecycle())
                .map(BaseBean::getData)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(resultBeans -> {
                    // 静态变量保存公钥的Base64编码字符串
                    SecurityModule.mRSAPublicKeyStrBase64 = resultBeans.getPublicKeyString();
                    // 测试代码，返回公钥字符串
                    if (SecurityModule.mRSAPublicKeyStrBase64 != null)
                    ToastUtil.ShortToast("成功连接服务器，获取公钥");
                    },
                        throwable ->  {ToastUtil.ShortToast(R.string.error_server_connect);
                Log.i("CusRequestConverter", throwable.getMessage());});
    }

    @Override
    public void finishTask() {

    }
}
