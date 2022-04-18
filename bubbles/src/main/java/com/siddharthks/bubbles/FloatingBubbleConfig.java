package com.siddharthks.bubbles;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.Gravity;
import android.view.View;
import android.support.v4.content.ContextCompat;
import android.widget.TextView;

public class FloatingBubbleConfig {
    private Drawable bubbleIcon;
    private Drawable removeBubbleIcon;
    private View expandableView;
    private int bubbleIconDp;
    private int removeBubbleIconDp;
    private float removeBubbleAlpha;
    private int expandableColor;
    private int triangleColor;
    private int gravity;
    private int paddingDp;
    private int borderRadiusDp;
    private boolean physicsEnabled;

    private FloatingBubbleConfig(Builder builder) {
        bubbleIcon = builder.bubbleIcon;
        removeBubbleIcon = builder.removeBubbleIcon;
        expandableView = builder.expandableView;
        bubbleIconDp = builder.bubbleIconDp;
        removeBubbleIconDp = builder.removeBubbleIconDp;
        expandableColor = builder.expandableColor;
        triangleColor = builder.triangleColor;
        gravity = builder.gravity;
        paddingDp = builder.paddingDp;
        borderRadiusDp = builder.borderRadiusDp;
        physicsEnabled = builder.physicsEnabled;
        removeBubbleAlpha = builder.removeBubbleAlpha;
    }

    public static Builder getDefaultBuilder(Context context) {
        return new Builder()
                .bubbleIcon(ContextCompat.getDrawable(context, R.drawable.bubble_default_icon))
                .removeBubbleIcon(ContextCompat.getDrawable(context, R.drawable.close_default_icon))
                .bubbleIconDp(64)
                .removeBubbleIconDp(64)
                .paddingDp(4)
                .removeBubbleAlpha(1.0f)
                .physicsEnabled(true)
                .expandableColor(Color.WHITE)
                .triangleColor(Color.WHITE)
                .gravity(Gravity.END);
    }

    public static FloatingBubbleConfig getDefault(Context context) {
        return getDefaultBuilder(context).build();
    }

    public Drawable getBubbleIcon() {
        return bubbleIcon;
    }

    public Drawable getRemoveBubbleIcon() {
        return removeBubbleIcon;
    }

    public View getExpandableView() {
        return expandableView;
    }

    public int getBubbleIconDp() {
        return bubbleIconDp;
    }

    public int getRemoveBubbleIconDp() {
        return removeBubbleIconDp;
    }

    public int getExpandableColor() {
        return expandableColor;
    }

    public int getTriangleColor() {
        return triangleColor;
    }

    public int getGravity() {
        return gravity;
    }

    public int getPaddingDp() {
        return paddingDp;
    }

    public boolean isPhysicsEnabled() {
        return physicsEnabled;
    }

    public int getBorderRadiusDp() {
        return borderRadiusDp;
    }

    public float getRemoveBubbleAlpha() {
        return removeBubbleAlpha;
    }

    public static final class Builder {
        private Drawable bubbleIcon;
        private Drawable removeBubbleIcon;
        private View expandableView;
        private int bubbleIconDp = 64;
        private int removeBubbleIconDp = 64;
        private int expandableColor = Color.WHITE;
        private int triangleColor = Color.WHITE;
        private int gravity = Gravity.END;
        private int paddingDp = 4;
        private int borderRadiusDp = 4;
        private float removeBubbleAlpha = 1.0f;
        private boolean physicsEnabled = true;

        public Builder() {
        }

        public Builder bubbleIcon(Drawable val) {
            bubbleIcon = val;
            return this;
        }

        public Builder removeBubbleIcon(Drawable val) {
            removeBubbleIcon = val;
            return this;
        }

        public Builder expandableView(View val) {
            expandableView = val;
            return this;
        }

        public Builder bubbleIconDp(int val) {
            bubbleIconDp = val;
            return this;
        }

        public Builder removeBubbleIconDp(int val) {
            removeBubbleIconDp = val;
            return this;
        }

        public Builder triangleColor(int val) {
            triangleColor = val;
            return this;
        }

        public Builder expandableColor(int val) {
            expandableColor = val;
            return this;
        }

        public FloatingBubbleConfig build() {
            return new FloatingBubbleConfig(this);
        }

        public Builder gravity(int val) {
            gravity = val;
            if (gravity == Gravity.CENTER ||
                    gravity == Gravity.CENTER_VERTICAL ||
                    gravity == Gravity.CENTER_HORIZONTAL) {
                gravity = Gravity.CENTER_HORIZONTAL;
            } else if (gravity == Gravity.TOP ||
                    gravity == Gravity.BOTTOM) {
                gravity = Gravity.END;
            }
            return this;
        }

        public Builder paddingDp(int val) {
            paddingDp = val;
            return this;
        }

        public Builder borderRadiusDp(int val) {
            borderRadiusDp = val;
            return this;
        }

        public Builder physicsEnabled(boolean val) {
            physicsEnabled = val;
            return this;
        }

        public Builder removeBubbleAlpha(float val) {
            removeBubbleAlpha = val;
            return this;
        }
    }


}
