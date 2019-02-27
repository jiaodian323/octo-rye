package com.justnd.octoryeclient.widget.customview;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.justnd.octoryeclient.R;

public class AuthorTagView extends FrameLayout {
    private CircleImageView mAvatarView;
    private TextView mAuthorNameText;
    private TextView mAuthorIntroText;
    private TextView mFocus;
    private View.OnClickListener mOnClickListener;
    private Activity activity;
    private String name;
    private int mid = -1;
    private String avatarUrl;

    public AuthorTagView(Context context) {
        this(context, null);
    }

    public AuthorTagView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AuthorTagView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        @SuppressLint("InflateParams")
        LinearLayout tagView = (LinearLayout) LayoutInflater.from(context)
                .inflate(R.layout.layout_author_tag_view, null);
        mAvatarView = (CircleImageView) tagView.findViewById(R.id.author_avatar);
        mAuthorNameText = (TextView) tagView.findViewById(R.id.author_name);
        mAuthorIntroText = (TextView) tagView.findViewById(R.id.author_intro);
        mFocus = (TextView) tagView.findViewById(R.id.focus);
        ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                getResources().getDimensionPixelSize(R.dimen.author_tag_view_height));
        this.addView(tagView, lp);
//        tagView.setOnClickListener(view -> {
//            if (mid != -1 && activity != null) {
//                UserInfoDetailsActivity.launch(activity, name, mid, avatarUrl);
//            } else if (mOnClickListener != null) {
//                mOnClickListener.onClick(view);
//            }
//        });
    }

    public void setAvatar(Bitmap bitmap) {
        mAvatarView.setImageBitmap(bitmap);
    }

    public void setAvatar(Drawable drawable) {
        mAvatarView.setImageDrawable(drawable);
    }

    public void setAvatar(@DrawableRes int id) {
        mAvatarView.setImageResource(id);
    }

    public CircleImageView getAvatar() {
        return this.mAvatarView;
    }

    public void setAuthorName(String userName) {
        mAuthorNameText.setText(userName);
    }

    public String getmAuthorName() {
        return this.mAuthorNameText.getText().toString();
    }

    public void setAuthorIntro(String text) {
        mAuthorIntroText.setText(text);
    }

    public void setUpWithInfo(Activity activity, int mid, String name, String intro, String
            avatarUrl) {
        this.activity = activity;
        this.name = name;
        this.mid = mid;
        this.avatarUrl = avatarUrl;
        this.setAuthorName(name);
        this.setAuthorIntro(intro);
        Glide.with(getContext())
                .load(this.avatarUrl)
                .centerCrop()
                .dontAnimate()
                .placeholder(R.drawable.ico_user_default)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(mAvatarView);
    }


    @Override
    public void setOnClickListener(View.OnClickListener listener) {
        this.mOnClickListener = listener;
    }
}
