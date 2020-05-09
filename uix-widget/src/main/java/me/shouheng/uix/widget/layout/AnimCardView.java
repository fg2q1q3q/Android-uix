package me.shouheng.uix.widget.layout;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;

public class AnimCardView extends CardView {

    private View.OnClickListener listener;

    private boolean isDelay = false;
    private boolean isAnimEnd = false;
    private boolean isOnClick = true;

    private Animator.AnimatorListener outListener = new Animator.AnimatorListener() {
        @Override
        public void onAnimationStart(Animator animation) {
            // noop
        }

        @Override
        public void onAnimationEnd(Animator animation) {
            isAnimEnd = true;
            if(!isOnClick){
                handleClickEvent();
            }
        }

        @Override
        public void onAnimationCancel(Animator animation) {
            // noop
        }

        @Override
        public void onAnimationRepeat(Animator animation) {
            // noop
        }
    };

    private Animator.AnimatorListener inListener = new Animator.AnimatorListener() {
        @Override
        public void onAnimationStart(Animator animation) {
            // noop
        }

        @Override
        public void onAnimationEnd(Animator animation) {
            resetControl();
        }

        @Override
        public void onAnimationCancel(Animator animation) {
            // noop
        }

        @Override
        public void onAnimationRepeat(Animator animation) {
            // noop
        }
    };

    public AnimCardView(@NonNull Context context) {
        this(context, null);
    }

    public AnimCardView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AnimCardView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                zoomOut();
                return true;
            case MotionEvent.ACTION_UP:
                if (isAnimEnd) {
                    handleClickEvent();
                } else {
                    isOnClick = false;
                }
                break;
            case MotionEvent.ACTION_CANCEL:
                zoomIn();
                break;
            default:break;
        }
        return super.onTouchEvent(ev);
    }

    @Override
    public void setOnClickListener(View.OnClickListener listener) {
        this.listener = listener;
    }

    public void setDelay(boolean delay) {
        isDelay = delay;
    }

    public AnimatorSet zoomIn() {
        ObjectAnimator animX = ObjectAnimator.ofFloat(this, "scaleX", 0.96f, 1.0f);
        ObjectAnimator animY = ObjectAnimator.ofFloat(this, "scaleY", 0.96f, 1.0f);
        AnimatorSet upSet = new AnimatorSet();
        upSet.play(animX).with(animY);
        upSet.setDuration(120);
        upSet.setInterpolator(new AccelerateDecelerateInterpolator());
        upSet.addListener(inListener);
        upSet.start();
        return upSet;
    }

    public AnimatorSet zoomOut() {
        ObjectAnimator animX = ObjectAnimator.ofFloat(this,"scaleX",1.0f, 0.96f);
        ObjectAnimator animY = ObjectAnimator.ofFloat(this,"scaleY",1.0f, 0.96f);
        AnimatorSet downSet = new AnimatorSet();
        downSet.play(animX).with(animY);
        downSet.setDuration(120);
        downSet.setInterpolator(new AccelerateDecelerateInterpolator());
        downSet.addListener(outListener);
        downSet.start();
        return downSet;
    }

    private void handleClickEvent() {
        if (null != listener) {
            listener.onClick(this);
        }
        resetControl();
        if (isDelay) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    zoomIn();
                }
            }, 500);
        } else {
            zoomIn();
        }
    }

    private void resetControl() {
        isOnClick = true;
        isAnimEnd = false;
    }
}

