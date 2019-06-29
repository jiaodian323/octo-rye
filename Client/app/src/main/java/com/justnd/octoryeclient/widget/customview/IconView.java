package com.justnd.octoryeclient.widget.customview;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.justnd.octoryeclient.R;

/**
 * @author Justiniano  Email:jiaodian822@163.com
 * @Description: 该自定义View已废弃，因为TextView可以直接在文章特定方向加图片。
 *               现此类暂存，仅作相似技术参考
 * @throws
 * @time 2019/4/9 0009 上午 11:03
 */
public class IconView extends LinearLayout {
    ImageView mIcon;
    TextView mName;

    int icon_id;
    int name_id;

    final Integer DEFAULT_ICON = android.R.drawable.ic_dialog_info;
    final Integer DEFAULT_NAME = android.R.string.unknownName;

    public IconView(Context context) {
        this(context, null);
    }

    public IconView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public IconView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LinearLayout iconView = (LinearLayout) LayoutInflater.from(context)
                .inflate(R.layout.layout_icon_view, null);
        mIcon = (ImageView) iconView.findViewById(R.id.icon);
        mName = (TextView) iconView.findViewById(R.id.name);
        ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        this.addView(iconView, lp);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.IconView, defStyleAttr, 0);
        icon_id = typedArray.getResourceId(R.styleable.IconView_custom_icon, DEFAULT_ICON);
        name_id = typedArray.getResourceId(R.styleable.IconView_custom_name, DEFAULT_NAME);
        mIcon.setImageResource(icon_id);
        mName.setText(name_id);
        typedArray.recycle();
    }
}
