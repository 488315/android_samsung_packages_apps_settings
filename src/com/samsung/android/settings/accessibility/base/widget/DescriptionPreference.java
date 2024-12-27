package com.samsung.android.settings.accessibility.base.widget;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.net.Uri;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.preference.Preference;
import androidx.preference.PreferenceViewHolder;

import com.android.settings.R;
import com.android.settings.R$styleable;

import com.airbnb.lottie.LottieAnimationView;
import com.airbnb.lottie.LottieCompositionFactory;
import com.airbnb.lottie.LottieCompositionFactory$$ExternalSyntheticLambda1;
import com.airbnb.lottie.LottieCompositionFactory$$ExternalSyntheticLambda2;
import com.airbnb.lottie.LottieDrawable;
import com.airbnb.lottie.LottieDrawable$$ExternalSyntheticLambda7;
import com.airbnb.lottie.LottieListener;

import java.io.FileNotFoundException;
import java.io.InputStream;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class DescriptionPreference extends Preference {
    public int animationMaxFrame;
    public int animationMinFrame;
    public final int animationRawResId;
    public String imageContentDescription;
    public Uri mImageUri;
    public final boolean showBottomPadding;
    public final boolean showFullSizeImage;
    public final boolean showTopPadding;

    public DescriptionPreference(Context context) {
        this(context, null);
    }

    public final void applyDescriptionPadding(View view, boolean z) {
        int dimensionPixelSize =
                getContext()
                        .getResources()
                        .getDimensionPixelSize(R.dimen.description_text_top_padding);
        int paddingStart = view.getPaddingStart();
        if (z) {
            dimensionPixelSize = 0;
        }
        view.setPaddingRelative(
                paddingStart, dimensionPixelSize, view.getPaddingEnd(), view.getPaddingBottom());
    }

    @Override // androidx.preference.Preference
    public void onBindViewHolder(PreferenceViewHolder preferenceViewHolder) {
        boolean z;
        InputStream inputStream;
        super.onBindViewHolder(preferenceViewHolder);
        View findViewById = preferenceViewHolder.itemView.findViewById(R.id.top_padding);
        View findViewById2 = preferenceViewHolder.itemView.findViewById(R.id.bottom_padding);
        if (findViewById != null) {
            findViewById.setVisibility(this.showTopPadding ? 0 : 8);
        }
        if (findViewById2 != null) {
            findViewById2.setVisibility(this.showBottomPadding ? 0 : 8);
        }
        LottieAnimationView lottieAnimationView =
                (LottieAnimationView) preferenceViewHolder.itemView.findViewById(android.R.id.icon);
        if (lottieAnimationView != null) {
            lottieAnimationView.setClickable(false);
            if (this.animationRawResId != 0 || this.mImageUri != null) {
                lottieAnimationView.setVisibility(0);
                int i = this.animationRawResId;
                if (i != 0) {
                    lottieAnimationView.setImageResource(i);
                    if (lottieAnimationView.getDrawable() == null) {
                        lottieAnimationView.setAnimation(this.animationRawResId);
                        lottieAnimationView.playAnimation$1();
                        lottieAnimationView.setClickable(true);
                    }
                } else {
                    lottieAnimationView.setImageURI(this.mImageUri);
                    if (lottieAnimationView.getDrawable() == null) {
                        Context context = getContext();
                        Uri uri = this.mImageUri;
                        try {
                            inputStream = context.getContentResolver().openInputStream(uri);
                        } catch (FileNotFoundException e) {
                            Log.w("DescriptionPreference", "Cannot find content uri: " + uri, e);
                            inputStream = null;
                        }
                        lottieAnimationView.failureListener =
                                new LottieListener() { // from class:
                                                       // com.samsung.android.settings.accessibility.base.widget.DescriptionPreference$$ExternalSyntheticLambda0
                                    @Override // com.airbnb.lottie.LottieListener
                                    public final void onResult(Object obj) {
                                        Log.w(
                                                "DescriptionPreference",
                                                "Invalid illustration image uri: "
                                                        + DescriptionPreference.this.mImageUri,
                                                (Throwable) obj);
                                    }
                                };
                        lottieAnimationView.setCompositionTask(
                                LottieCompositionFactory.cache(
                                        null,
                                        new LottieCompositionFactory$$ExternalSyntheticLambda1(
                                                inputStream),
                                        new LottieCompositionFactory$$ExternalSyntheticLambda2(
                                                inputStream)));
                        lottieAnimationView.playAnimation$1();
                        lottieAnimationView.setClickable(true);
                    }
                }
                int i2 = this.animationMinFrame;
                if (i2 != 0 || this.animationMaxFrame != 0) {
                    int i3 = this.animationMaxFrame;
                    LottieDrawable lottieDrawable = lottieAnimationView.lottieDrawable;
                    if (lottieDrawable.composition == null) {
                        lottieDrawable.lazyCompositionTasks.add(
                                new LottieDrawable$$ExternalSyntheticLambda7(
                                        lottieDrawable, i2, i3));
                    } else {
                        lottieDrawable.animator.setMinAndMaxFrames(i2, i3 + 0.99f);
                    }
                }
            }
            lottieAnimationView.setContentDescription(this.imageContentDescription);
            if (this.showFullSizeImage) {
                lottieAnimationView.setMaxWidth(Preference.DEFAULT_ORDER);
            }
            if (TextUtils.isEmpty(this.imageContentDescription)) {
                lottieAnimationView.setImportantForAccessibility(2);
            }
        }
        View findViewById3 = preferenceViewHolder.itemView.findViewById(android.R.id.icon_frame);
        if (findViewById3 != null) {
            if ((this.animationRawResId != 0 || this.mImageUri != null)
                    && findViewById3.getVisibility() != 0) {
                findViewById3.setVisibility(0);
            }
            if (this.showFullSizeImage) {
                findViewById3.setPadding(0, 0, 0, 0);
            }
            findViewById3.semSetRoundedCorners(15);
            findViewById3.semSetRoundedCornerColor(
                    15, getContext().getColor(R.color.rounded_corner_color));
        }
        TextView textView =
                (TextView) preferenceViewHolder.itemView.findViewById(android.R.id.title);
        if (!(preferenceViewHolder.itemView instanceof ConstraintLayout)
                || findViewById3 == null
                || textView == null) {
            z = false;
        } else {
            Configuration configuration = getContext().getResources().getConfiguration();
            int id = findViewById3.getId();
            int id2 = textView.getId();
            ConstraintSet constraintSet = new ConstraintSet();
            constraintSet.clone((ConstraintLayout) preferenceViewHolder.itemView);
            if (configuration.orientation == 2) {
                applyDescriptionPadding(textView, true);
                constraintSet.connect(id, 7, id2, 6);
                constraintSet.connect(id, 4, R.id.bottom_padding, 3);
                constraintSet.connect(id2, 6, id, 7);
                constraintSet.connect(id2, 3, R.id.top_padding, 4);
                float f =
                        getContext()
                                .getResources()
                                .getFloat(R.dimen.description_image_area_weight_land);
                float f2 =
                        getContext()
                                .getResources()
                                .getFloat(R.dimen.description_text_area_weight_land);
                constraintSet.get(id).layout.horizontalWeight = f;
                constraintSet.get(id2).layout.horizontalWeight = f2;
            } else {
                applyDescriptionPadding(textView, findViewById3.getVisibility() == 8);
                constraintSet.connect(id, 7, 0, 7);
                constraintSet.connect(id, 4, id2, 3);
                constraintSet.connect(id2, 6, 0, 6);
                constraintSet.connect(id2, 3, id, 4);
                constraintSet.get(id).layout.horizontalWeight = 0.0f;
                constraintSet.get(id2).layout.horizontalWeight = 0.0f;
            }
            constraintSet.applyTo((ConstraintLayout) preferenceViewHolder.itemView);
            z = false;
        }
        preferenceViewHolder.mDividerAllowedAbove = z;
        preferenceViewHolder.mDividerAllowedBelow = z;
    }

    public DescriptionPreference(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.showTopPadding = false;
        this.showBottomPadding = true;
        this.animationMaxFrame = 0;
        this.animationMinFrame = 0;
        setLayoutResource(R.layout.preference_category_description_container);
        seslSetSubheaderRoundedBackground(15);
        setSelectable(false);
        setIconSpaceReserved(false);
        TypedArray obtainStyledAttributes =
                context.getTheme()
                        .obtainStyledAttributes(
                                attributeSet, R$styleable.DescriptionPreference, 0, 0);
        for (int i = 0; i < obtainStyledAttributes.getIndexCount(); i++) {
            int index = obtainStyledAttributes.getIndex(i);
            if (index == 4) {
                this.showTopPadding = obtainStyledAttributes.getBoolean(index, false);
            }
            if (index == 2) {
                this.showBottomPadding = obtainStyledAttributes.getBoolean(index, true);
            }
            if (index == 3) {
                this.showFullSizeImage = obtainStyledAttributes.getBoolean(index, false);
            }
            if (index == 0) {
                this.imageContentDescription = obtainStyledAttributes.getString(index);
            }
            if (index == 1) {
                this.animationRawResId = obtainStyledAttributes.getResourceId(index, 0);
            }
        }
        obtainStyledAttributes.recycle();
    }
}
