package com.wishland.www.xinwanhaotest.view.customgridview;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

/**
 * Created by Administrator on 2017/7/24.
 */

public class CustomTextView extends android.support.v7.widget.AppCompatTextView {


    public CustomTextView(Context context) {
        super(context);
    }

    public CustomTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override

    protected void onDraw(Canvas canvas) {

// getCompoundDrawables() : Returns drawables for the left, top, right, and bottom borders.

        Drawable[] drawables = getCompoundDrawables();

// 得到drawableLeft设置的drawable对象

        Drawable leftDrawable = drawables[0];

        if (leftDrawable != null) {

// 得到leftDrawable的宽度

            int leftDrawableWidth = leftDrawable.getIntrinsicWidth();

// 得到drawable与text之间的间距

            int drawablePadding = getCompoundDrawablePadding();

// 得到文本的宽度

            int textWidth = (int) getPaint().measureText(getText().toString().trim());

            int bodyWidth = leftDrawableWidth + drawablePadding + textWidth;

            canvas.save();

            canvas.translate((getWidth() - bodyWidth) / 2, 0);

        }

        super.onDraw(canvas);

    }

}