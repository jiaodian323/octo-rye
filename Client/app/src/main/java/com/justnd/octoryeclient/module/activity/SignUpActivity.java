package com.justnd.octoryeclient.module.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.justnd.octoryeclient.R;
import com.justnd.octoryeclient.module.base.RxBaseActivity;
import com.justnd.octoryeclient.utils.SMSUtil;
import com.justnd.octoryeclient.utils.ToastUtil;


import java.lang.ref.WeakReference;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;

/**
 * @author Justiniano  Email:jiaodian822@163.com
 * @Title: SignUpActivity
 * @Description: 注册界面
 * @throws
 * @time 2019/2/14 0014 Justiniano  Email:jiaodian822@163.com
 */
public class SignUpActivity extends RxBaseActivity {
    @BindView(R.id.sign_up_title_image)
    ImageView mTitleImage;
    @BindView(R.id.sign_up_title_toolbar)
    Toolbar mToolbar;
    @BindView(R.id.phoneNumber)
    EditText mPhoneNumber;
    @BindView(R.id.auth_code)
    EditText mAuthCode;
    @BindView(R.id.send_auth_code_textView)
    TextView mSendAuthCode;
    @BindView(R.id.next_step_btn)
    Button mNextStep;

    /**
     * @Fields: 重发验证码定时器
     */
    private Timer reSendAuthCodeTimer = new Timer();
    private SendAuthCodeHandler handler = new SendAuthCodeHandler(SignUpActivity.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mSendAuthCode.setOnClickListener(v -> {
            Editable phoneStr = mPhoneNumber.getText();

            sendAuthCode(phoneStr.toString());
        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_sign_up;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        Glide.with(SignUpActivity.this)
                .load(R.drawable.sign_up_title_img)
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .dontAnimate()
                .into(mTitleImage);

        mPhoneNumber.addTextChangedListener(changedWatcher);
        mAuthCode.addTextChangedListener(changedWatcher);

        mNextStep.setOnClickListener(v -> {
            // ① 再次验证文本框里手机号是否合法
            if (!phoneNumberLegalCheck(mPhoneNumber.getText().toString()))
                return;
            // ② 查询数据库，手机号是否已注册，如已注册，Toast提示该手机已存在
            // ③ 校验验证码是否正确
            if (SMSUtil.isAuthCodeCorrect(mPhoneNumber.getText().toString(), mAuthCode.getText()
                    .toString())) {

            }
            else {
               ToastUtil.ShortToast(R.string.error_incorrect_auth_code);
            }
        });
    }

    @Override
    public void initToolBar() {
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);  // 左侧添加一个默认的返回图标
        getSupportActionBar().setHomeButtonEnabled(true);  // 设置返回键可用

        mToolbar.setNavigationOnClickListener(v -> finish());
    }

    public static void launch(Activity activity) {
        Intent intent = new Intent(activity, SignUpActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        activity.startActivity(intent);
    }

    /**
     * @param phoneNumber 手机号码
     * @return
     * @throws
     * @Description: 发送验证码
     * @author Justiniano  Email:jiaodian822@163.com
     */
    public void sendAuthCode(String phoneNumber) {
        if (!phoneNumberLegalCheck(phoneNumber))
            return;

        SMSUtil.sendAuthCodeSMS();
        // 设置发送验证码文本不可点击
        mSendAuthCode.setClickable(false);
        // 重发机制延迟0.5秒开始计时，计时间隔为1秒
        reSendAuthCodeTimer.schedule(new ReSendTimerTask(), 500, 1000);
    }

    private TextWatcher changedWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        /**
         * @Description: 监听EditText输入值，根据输入值设置按钮是否可用
         * @param
         * @return
         * @throws
         * @author Justiniano  Email:jiaodian822@163.com
         */
        @Override
        public void afterTextChanged(Editable s) {
            if (SMSUtil.isPhoneNumber(mPhoneNumber.getText().toString()) &&
                    (mAuthCode.getText().length() > 0)) {
                mNextStep.setEnabled(true);
            } else {
                mNextStep.setEnabled(false);
            }
        }
    };

    final class ReSendTimerTask extends TimerTask {
        /**
         * @Fields: 重发初始读秒为60秒，应调试需要暂时设置为10秒
         */
        int resendInitCount = 10;

        @Override
        public void run() {
            if (resendInitCount >= 0) {
                Message message = new Message();
                message.what = 1;
                message.arg1 = resendInitCount;
                handler.sendMessage(message);

                resendInitCount--;
            } else {
                Message completeMessage = new Message();
                completeMessage.what = 2;
                handler.sendMessage(completeMessage);
                this.cancel();
            }
        }
    }

    // 使用弱引用包装Activity，避免Handler未停止时，GC无法释放对Activity的引用而导致的内存泄露
    final class SendAuthCodeHandler extends Handler {
        WeakReference<SignUpActivity> activityWeakReference;

        public SendAuthCodeHandler(SignUpActivity activity) {
            activityWeakReference = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            TextView tv = activityWeakReference.get().mSendAuthCode;
            switch (msg.what) {
                case 1:
                    String refreshText = msg.arg1 + getString(R.string.resend_after_seconds);
                    tv.setText(refreshText);
                    break;
                case 2:
                    // 重发限制计时结束
                    tv.setText(R.string.send_auth_code);
                    // 设置发送验证码文本可点击
                    tv.setClickable(true);
            }
            super.handleMessage(msg);
        }
    }

    /**
     * @param
     * @return
     * @throws
     * @Description: 电话号码文本框输入值的合法性验证
     * @author Justiniano  Email:jiaodian822@163.com
     */
    private boolean phoneNumberLegalCheck(String phoneNumber) {
        if (phoneNumber.length() == 0) {
            ToastUtil.ShortToast(R.string.error_phone_number_null);
            return false;
        }

        if (!SMSUtil.isPhoneNumber(phoneNumber)) {
            ToastUtil.ShortToast(R.string.error_invalid_phone_number);
            return false;
        }

        return true;
    }

    @Override
    protected void onDestroy() {
        // 停止timer
        if (reSendAuthCodeTimer != null) {
            reSendAuthCodeTimer.cancel();
            reSendAuthCodeTimer = null;
        }
        super.onDestroy();
    }
}
