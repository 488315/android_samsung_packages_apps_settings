package com.android.settingslib.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.preference.Preference;
import androidx.preference.PreferenceViewHolder;
import androidx.preference.R$styleable;

import com.android.settings.R;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class LayoutPreference extends Preference {
    public boolean mAllowDividerAbove;
    public boolean mAllowDividerBelow;
    public final View.OnClickListener mClickListener;
    public final boolean mIsRelativeLinkView;
    public View mRootView;

    public LayoutPreference(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mClickListener =
                new View
                        .OnClickListener() { // from class:
                                             // com.android.settingslib.widget.LayoutPreference$$ExternalSyntheticLambda0
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        LayoutPreference.this.performClick();
                    }
                };
        this.mIsRelativeLinkView = false;
        init$2(context, attributeSet, 0);
    }

    public final void init$2(Context context, AttributeSet attributeSet, int i) {
        int[] iArr = R$styleable.Preference;
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, iArr);
        this.mAllowDividerAbove =
                obtainStyledAttributes.getBoolean(16, obtainStyledAttributes.getBoolean(16, false));
        this.mAllowDividerBelow =
                obtainStyledAttributes.getBoolean(17, obtainStyledAttributes.getBoolean(17, false));
        obtainStyledAttributes.recycle();
        TypedArray obtainStyledAttributes2 =
                context.obtainStyledAttributes(attributeSet, iArr, i, 0);
        int resourceId = obtainStyledAttributes2.getResourceId(3, 0);
        if (resourceId == 0) {
            throw new IllegalArgumentException("LayoutPreference requires a layout to be defined");
        }
        obtainStyledAttributes2.recycle();
        View inflate =
                LayoutInflater.from(getContext()).inflate(resourceId, (ViewGroup) null, false);
        setLayoutResource(R.layout.layout_preference_frame);
        this.mRootView = inflate;
        setShouldDisableView(false);
    }

    @Override // androidx.preference.Preference
    public void onBindViewHolder(PreferenceViewHolder preferenceViewHolder) {
        if (this.mIsRelativeLinkView) {
            preferenceViewHolder.itemView.setOnClickListener(null);
            preferenceViewHolder.itemView.setFocusable(false);
            preferenceViewHolder.itemView.setClickable(false);
        } else {
            preferenceViewHolder.itemView.setOnClickListener(this.mClickListener);
            boolean isSelectable = isSelectable();
            preferenceViewHolder.itemView.setFocusable(isSelectable);
            preferenceViewHolder.itemView.setClickable(isSelectable);
            preferenceViewHolder.mDividerAllowedAbove = this.mAllowDividerAbove;
            preferenceViewHolder.mDividerAllowedBelow = this.mAllowDividerBelow;
        }
        FrameLayout frameLayout = (FrameLayout) preferenceViewHolder.itemView;
        frameLayout.removeAllViews();
        ViewGroup viewGroup = (ViewGroup) this.mRootView.getParent();
        if (viewGroup != null) {
            viewGroup.removeView(this.mRootView);
        }
        frameLayout.addView(this.mRootView);
    }

    public LayoutPreference(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mClickListener =
                new View
                        .OnClickListener() { // from class:
                                             // com.android.settingslib.widget.LayoutPreference$$ExternalSyntheticLambda0
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        LayoutPreference.this.performClick();
                    }
                };
        this.mIsRelativeLinkView = false;
        init$2(context, attributeSet, i);
    }

    public LayoutPreference(Context context, int i) {
        this(context, LayoutInflater.from(context).inflate(i, (ViewGroup) null, false));
    }

    public LayoutPreference(Context context, View view) {
        super(context);
        this.mClickListener =
                new View
                        .OnClickListener() { // from class:
                                             // com.android.settingslib.widget.LayoutPreference$$ExternalSyntheticLambda0
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view2) {
                        LayoutPreference.this.performClick();
                    }
                };
        this.mIsRelativeLinkView = false;
        setLayoutResource(R.layout.layout_preference_frame);
        this.mRootView = view;
        setShouldDisableView(false);
    }

    public LayoutPreference(Context context, View view, boolean z) {
        super(context);
        this.mClickListener =
                new View
                        .OnClickListener() { // from class:
                                             // com.android.settingslib.widget.LayoutPreference$$ExternalSyntheticLambda0
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view2) {
                        LayoutPreference.this.performClick();
                    }
                };
        this.mIsRelativeLinkView = false;
        setLayoutResource(R.layout.layout_preference_frame);
        this.mRootView = view;
        setShouldDisableView(false);
        this.mIsRelativeLinkView = z;
    }

    public LayoutPreference(Context context, View view, int i) {
        super(context);
        this.mClickListener =
                new View
                        .OnClickListener() { // from class:
                                             // com.android.settingslib.widget.LayoutPreference$$ExternalSyntheticLambda0
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view2) {
                        LayoutPreference.this.performClick();
                    }
                };
        this.mIsRelativeLinkView = false;
        setLayoutResource(R.layout.layout_preference_frame);
        this.mRootView = view;
        setShouldDisableView(false);
    }
}
