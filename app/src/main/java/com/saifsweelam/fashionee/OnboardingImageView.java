package com.saifsweelam.fashionee;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Path;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;

public class OnboardingImageView extends AppCompatImageView {
    private Path path;
    private final float radius = 64f;

    public OnboardingImageView(@NonNull Context context) {
        super(context);
        init();
    }

    public OnboardingImageView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public OnboardingImageView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        path = new Path();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        float h = getMeasuredHeight();
        float w = getMeasuredWidth();
        path.moveTo(radius, 0);
        path.lineTo(w - radius, 0);
        path.arcTo(w - 2*radius, 0, w, 2*radius, -90f, 90f, false);
        path.lineTo(w, radius);
        path.arcTo(w - 2*radius, h*0.8f - 2*radius, w, h*0.8f, 0, 90, false);
        path.lineTo(radius, h);
        path.arcTo(0, h - 2*radius, 2*radius, h, 90, 90, false);
        path.lineTo(0, radius);
        path.arcTo(0, 0, 2*radius, 2*radius, 180, 90, false);



        canvas.clipPath(path);
        super.onDraw(canvas);
    }
}
