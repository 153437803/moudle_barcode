package lib.kalu.zxing.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.BlendMode;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;

import lib.kalu.zxing.util.LogUtil;

/**
 * description: 二维码扫描框
 * created by kalu on 2021-02-26
 */
public final class FinderView extends AppCompatTextView {

    public FinderView(Context context) {
        super(context);
        setLayerType(View.LAYER_TYPE_HARDWARE, null);
    }

    public FinderView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setLayerType(View.LAYER_TYPE_HARDWARE, null);
    }

    public FinderView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setLayerType(View.LAYER_TYPE_HARDWARE, null);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int height = MeasureSpec.getSize(heightMeasureSpec);
        int width = 1080 * height / 1920;
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawColor(Color.TRANSPARENT);

        float height = getHeight();
        float width = getWidth();
        float min = Math.min(height, width) * 0.6f;

        float left = width * 0.5f - min * 0.5f;
        float top = height * 0.5f - min * 0.5f;
        float right = left + min;
        float bottom = top + min;

        // 画笔
        TextPaint paint = getPaint();
        paint.setAntiAlias(true);
        paint.setStrokeWidth(0);
        paint.setStyle(Paint.Style.FILL);
        paint.setPathEffect(null);

        // 直角
        int rectH = (int) (2 * 4 + 0.5f);
        int rectW = (int) (14 * 4 + 0.5f);

        // 阴影1
        paint.setColor(Color.parseColor("#66000000"));
        canvas.drawRect(0, 0, width, top, paint);
        // canvas.drawRect(0, 0, width, top + rectH, paint);

        // 阴影2
        paint.setColor(Color.parseColor("#66000000"));
        canvas.drawRect(0, bottom, width, height, paint);
        // canvas.drawRect(0, bottom, width, height, paint);

        // 阴影3
        paint.setColor(Color.parseColor("#66000000"));
        canvas.drawRect(0, top, left, bottom, paint);
        // canvas.drawRect(0, top + rectH, left + rectH, bottom - rectH, paint);

        // 阴影4
        paint.setColor(Color.parseColor("#66000000"));
        canvas.drawRect(right, top, width, bottom, paint);
        // canvas.drawRect(right - rectH, top + rectH, width, bottom - rectH, paint);

        //左上角
        paint.setColor(Color.BLACK);
        canvas.drawRect(left, top, left + rectW, top + rectH, paint);
        canvas.drawRect(left, top, left + rectH, top + rectW, paint);
        //右上角
        canvas.drawRect(right - rectW, top, right + 1, top + rectH, paint);
        canvas.drawRect(right - rectH, top, right + 1, top + rectW, paint);
        //左下角
        canvas.drawRect(left, bottom - rectH, left + rectW, bottom + 1, paint);
        canvas.drawRect(left, bottom - rectW, left + rectH, bottom + 1, paint);
        //右下角
        canvas.drawRect(right - rectW, bottom - rectH, right + 1, bottom + 1, paint);
        canvas.drawRect(right - rectH, bottom - rectW, right + 1, bottom + 1, paint);

        // 文字
        paint.setStrokeWidth(0);
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.FILL);
        paint.setPathEffect(null);
        super.onDraw(canvas);

        CharSequence text = getText();
        if (null == text || text.length() == 0)
            return;

        // 扫描动画, 局部刷新
        float displacement;
        try {
            CharSequence hint = getHint();
            float parseFloat = Float.parseFloat(hint.toString());
            displacement = Math.abs(parseFloat);

            if (parseFloat < 0) {
                displacement -= 5f;
            } else {
                displacement += 5f;
            }
            setHint(String.valueOf(parseFloat < 0 ? -displacement : displacement));

        } catch (Exception e) {
            displacement = 5f;
            setHint(String.valueOf(displacement));
        }

        float margin = Math.abs(right - left) * 0.1f;
        float lineHeight = margin * 0.05f;
        float rectLeft = left + margin;
        float rectTop = top + margin + displacement;
        float rectRight = right - margin;
        float rectBottom = rectTop + lineHeight;

        // 下移
        if (null == getHint() || !getHint().toString().startsWith("-")) {
            displacement += 5f;
        }
        // 上移
        else {
            displacement -= 5f;
        }

        // 底部
        if (rectBottom + margin > bottom) {
            setHint(String.valueOf(-displacement));
        }
        // 顶部
        else if (rectTop - margin < top) {
            setHint(String.valueOf(displacement));
        }

        paint.setStrokeWidth(0);
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.STROKE);
        paint.setAntiAlias(true);
        paint.setPathEffect(new DashPathEffect(new float[]{4, 4}, 0));
        canvas.drawLine(rectLeft, rectTop, rectRight, rectBottom, paint);
//        canvas.drawRect(rectLeft, rectTop, rectRight, rectBottom, paint);
        postInvalidateDelayed(20, (int) left, (int) top, (int) right, (int) bottom);
        // LogUtil.log("onDraw[二维码扫描框] => lineDisplacement = " + getHint());
    }

    /***************/

    @Override
    public void setBackgroundColor(int color) {
    }

    @Override
    public int getDrawingCacheBackgroundColor() {
        return Color.TRANSPARENT;
    }

    @Override
    public void setDrawingCacheBackgroundColor(int color) {
    }

    @Override
    public void setBackgroundDrawable(Drawable background) {
    }

    @Override
    public void setBackgroundResource(int resId) {
    }

    @Override
    public void setBackground(Drawable background) {
    }

    @Override
    public void setBackgroundTintBlendMode(@Nullable BlendMode blendMode) {
    }

    @Override
    public void setBackgroundTintList(@Nullable ColorStateList tint) {
    }

    @Override
    public void setBackgroundTintMode(@Nullable PorterDuff.Mode tintMode) {
    }

    @Override
    public void setSupportBackgroundTintList(@Nullable @org.jetbrains.annotations.Nullable ColorStateList tint) {
    }

    @Override
    public void setSupportBackgroundTintMode(@Nullable @org.jetbrains.annotations.Nullable PorterDuff.Mode tintMode) {
    }
}