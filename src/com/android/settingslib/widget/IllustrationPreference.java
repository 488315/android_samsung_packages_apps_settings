package com.android.settingslib.widget;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Animatable2;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.preference.Preference;
import androidx.preference.PreferenceViewHolder;
import androidx.vectordrawable.graphics.drawable.Animatable2Compat;
import androidx.vectordrawable.graphics.drawable.AnimatedVectorDrawableCompat;

import com.android.settings.R;

import com.airbnb.lottie.LottieAnimationView;
import com.airbnb.lottie.LottieListener;
import com.airbnb.lottie.R$styleable;

import java.io.IOException;
import java.io.InputStream;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class IllustrationPreference extends Preference {
    public final AnonymousClass1 mAnimationCallback;
    public final AnonymousClass2 mAnimationCallbackCompat;
    public boolean mCacheComposition;
    public CharSequence mContentDescription;
    public Drawable mImageDrawable;
    public int mImageResId;
    public boolean mLottieDynamicColor;
    public final int mMaxHeight;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.android.settingslib.widget.IllustrationPreference$1, reason: invalid class name */
    public final class AnonymousClass1 extends Animatable2.AnimationCallback {
        /* JADX WARN: Multi-variable type inference failed */
        @Override // android.graphics.drawable.Animatable2.AnimationCallback
        public final void onAnimationEnd(Drawable drawable) {
            ((Animatable) drawable).start();
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.android.settingslib.widget.IllustrationPreference$2, reason: invalid class name */
    public final class AnonymousClass2 extends Animatable2Compat.AnimationCallback {
        /* JADX WARN: Multi-variable type inference failed */
        @Override // androidx.vectordrawable.graphics.drawable.Animatable2Compat.AnimationCallback
        public final void onAnimationEnd(Drawable drawable) {
            ((Animatable) drawable).start();
        }
    }

    public IllustrationPreference(Context context) {
        super(context);
        this.mMaxHeight = -1;
        this.mCacheComposition = true;
        this.mAnimationCallback = new AnonymousClass1();
        this.mAnimationCallbackCompat = new AnonymousClass2();
        setLayoutResource(R.layout.illustration_preference);
    }

    public static void resetAnimations(LottieAnimationView lottieAnimationView) {
        Object drawable = lottieAnimationView.getDrawable();
        if (drawable instanceof Animatable) {
            if (drawable instanceof Animatable2) {
                ((Animatable2) drawable).clearAnimationCallbacks();
            } else if (drawable instanceof Animatable2Compat) {
                ((AnimatedVectorDrawableCompat) ((Animatable2Compat) drawable))
                        .clearAnimationCallbacks();
            }
            ((Animatable) drawable).stop();
        }
        lottieAnimationView.cancelAnimation();
    }

    public final void init$7(Context context, AttributeSet attributeSet) {
        setLayoutResource(R.layout.illustration_preference);
        if (attributeSet != null) {
            TypedArray obtainStyledAttributes =
                    context.obtainStyledAttributes(
                            attributeSet, R$styleable.LottieAnimationView, 0, 0);
            this.mImageResId = obtainStyledAttributes.getResourceId(13, 0);
            this.mCacheComposition = obtainStyledAttributes.getBoolean(2, true);
            TypedArray obtainStyledAttributes2 =
                    context.obtainStyledAttributes(
                            attributeSet,
                            com.android.settingslib.widget.preference.illustration.R$styleable
                                    .IllustrationPreference,
                            0,
                            0);
            this.mLottieDynamicColor = obtainStyledAttributes2.getBoolean(0, false);
            obtainStyledAttributes2.recycle();
        }
    }

    @Override // androidx.preference.Preference
    public final void onBindViewHolder(PreferenceViewHolder preferenceViewHolder) {
        InputStream openRawResource;
        super.onBindViewHolder(preferenceViewHolder);
        ImageView imageView = (ImageView) preferenceViewHolder.findViewById(R.id.background_view);
        FrameLayout frameLayout =
                (FrameLayout) preferenceViewHolder.findViewById(R.id.middleground_layout);
        LottieAnimationView lottieAnimationView =
                (LottieAnimationView) preferenceViewHolder.findViewById(R.id.lottie_view);
        if (lottieAnimationView != null && !TextUtils.isEmpty(this.mContentDescription)) {
            lottieAnimationView.setContentDescription(this.mContentDescription);
            lottieAnimationView.setImportantForAccessibility(1);
        }
        int i = getContext().getResources().getDisplayMetrics().widthPixels;
        int i2 = getContext().getResources().getDisplayMetrics().heightPixels;
        FrameLayout frameLayout2 =
                (FrameLayout) preferenceViewHolder.findViewById(R.id.illustration_frame);
        ViewGroup.LayoutParams layoutParams = frameLayout2.getLayoutParams();
        if (i >= i2) {
            i = i2;
        }
        layoutParams.width = i;
        frameLayout2.setLayoutParams(layoutParams);
        lottieAnimationView.cacheComposition = this.mCacheComposition;
        if (this.mImageDrawable != null) {
            resetAnimations(lottieAnimationView);
            lottieAnimationView.setImageDrawable(this.mImageDrawable);
            Drawable drawable = lottieAnimationView.getDrawable();
            if (drawable != null) {
                startAnimation(drawable);
            }
        }
        if (this.mImageResId > 0) {
            try {
                openRawResource =
                        lottieAnimationView.getResources().openRawResource(this.mImageResId);
                try {
                } finally {
                }
            } catch (IOException e) {
                Log.w("IllustrationPreference", "Unable to open Lottie raw resource", e);
            }
            if (openRawResource.read() == -1) {
                lottieAnimationView.setVisibility(8);
                frameLayout2.setVisibility(8);
                openRawResource.close();
            } else {
                openRawResource.close();
                lottieAnimationView.setVisibility(0);
                frameLayout2.setVisibility(0);
                resetAnimations(lottieAnimationView);
                lottieAnimationView.setImageResource(this.mImageResId);
                Drawable drawable2 = lottieAnimationView.getDrawable();
                if (drawable2 != null) {
                    startAnimation(drawable2);
                } else {
                    final int i3 = this.mImageResId;
                    lottieAnimationView.failureListener =
                            new LottieListener() { // from class:
                                                   // com.android.settingslib.widget.IllustrationPreference$$ExternalSyntheticLambda0
                                @Override // com.airbnb.lottie.LottieListener
                                public final void onResult(Object obj) {
                                    Log.w(
                                            "IllustrationPreference",
                                            "Invalid illustration resource id: " + i3,
                                            (Throwable) obj);
                                }
                            };
                    lottieAnimationView.setAnimation(i3);
                    lottieAnimationView.setRepeatCount(-1);
                    lottieAnimationView.playAnimation$1();
                }
            }
        }
        if (this.mMaxHeight != -1) {
            Resources resources = imageView.getResources();
            int dimensionPixelSize =
                    resources.getDimensionPixelSize(R.dimen.settingslib_illustration_width);
            int dimensionPixelSize2 =
                    resources.getDimensionPixelSize(R.dimen.settingslib_illustration_height);
            int min = Math.min(this.mMaxHeight, dimensionPixelSize2);
            imageView.setMaxHeight(min);
            lottieAnimationView.setMaxHeight(min);
            lottieAnimationView.setMaxWidth(
                    (int) (min * (dimensionPixelSize / dimensionPixelSize2)));
        }
        frameLayout.removeAllViews();
        frameLayout.setVisibility(8);
        if (this.mLottieDynamicColor) {
            LottieColorUtils.applyDynamicColors(getContext(), lottieAnimationView);
        }
    }

    public final void setLottieAnimationResId(int i) {
        if (i != this.mImageResId) {
            this.mImageDrawable = null;
            this.mImageResId = i;
            notifyChanged();
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final void startAnimation(Drawable drawable) {
        if (drawable instanceof Animatable) {
            if (drawable instanceof Animatable2) {
                ((Animatable2) drawable).registerAnimationCallback(this.mAnimationCallback);
            } else if (drawable instanceof Animatable2Compat) {
                ((AnimatedVectorDrawableCompat) ((Animatable2Compat) drawable))
                        .registerAnimationCallback(this.mAnimationCallbackCompat);
            } else if (drawable instanceof AnimationDrawable) {
                ((AnimationDrawable) drawable).setOneShot(false);
            }
            ((Animatable) drawable).start();
        }
    }

    public IllustrationPreference(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mMaxHeight = -1;
        this.mCacheComposition = true;
        this.mAnimationCallback = new AnonymousClass1();
        this.mAnimationCallbackCompat = new AnonymousClass2();
        init$7(context, attributeSet);
    }

    public IllustrationPreference(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mMaxHeight = -1;
        this.mCacheComposition = true;
        this.mAnimationCallback = new AnonymousClass1();
        this.mAnimationCallbackCompat = new AnonymousClass2();
        init$7(context, attributeSet);
    }

    public IllustrationPreference(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.mMaxHeight = -1;
        this.mCacheComposition = true;
        this.mAnimationCallback = new AnonymousClass1();
        this.mAnimationCallbackCompat = new AnonymousClass2();
        init$7(context, attributeSet);
    }
}
