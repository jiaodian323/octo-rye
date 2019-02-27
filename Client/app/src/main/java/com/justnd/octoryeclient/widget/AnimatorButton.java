package com.justnd.octoryeclient.widget;

import android.content.Context;
import android.util.AttributeSet;

public class AnimatorButton extends android.support.v7.widget.AppCompatButton{
    public AnimatorButton(Context context) {
        super(context);
        init(context);
    }
    public AnimatorButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }
    public AnimatorButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public void init(Context context) { }
}
