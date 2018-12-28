package com.justnd.octoryeclient.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.justnd.octoryeclient.R;

public class CustomEmptyView extends FrameLayout {
    private ImageView mEmptyImg;
    private TextView mEmptyText;

    public CustomEmptyView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public CustomEmptyView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomEmptyView(Context context) {
        this(context, null);
    }

    public void init() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.layout_empty, this);
        mEmptyImg = (ImageView) view.findViewById(R.id.empty_img);
        mEmptyText = (TextView) view.findViewById(R.id.empty_text);
    }

    public void setEmptyImage(int img) {
        mEmptyImg.setImageResource(img);
    }

    public void setEmptyText(String text) {
        mEmptyText.setText(text);
    }
}
