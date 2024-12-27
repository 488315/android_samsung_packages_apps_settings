package com.android.settingslib.widget;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;

import androidx.preference.CheckBoxPreference;
import androidx.preference.PreferenceViewHolder;

import com.android.settings.R;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class SelectorWithWidgetPreference extends CheckBoxPreference {
    public View mAppendix;
    public int mAppendixVisibility;
    public ImageView mExtraWidget;
    public View mExtraWidgetContainer;
    public View.OnClickListener mExtraWidgetOnClickListener;
    public final boolean mIsCheckBox;
    public OnClickListener mListener;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public interface OnClickListener {
        void onRadioButtonClicked(SelectorWithWidgetPreference selectorWithWidgetPreference);
    }

    public SelectorWithWidgetPreference(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i, 0);
        this.mListener = null;
        this.mAppendixVisibility = -1;
        this.mIsCheckBox = false;
        init$9$1();
    }

    public final void init$9$1() {
        if (this.mIsCheckBox) {
            setWidgetLayoutResource(R.layout.preference_widget_checkbox);
        } else {
            setWidgetLayoutResource(R.layout.preference_widget_radiobutton);
        }
        setLayoutResource(R.layout.preference_selector_with_widget);
        setIconSpaceReserved(false);
    }

    @Override // androidx.preference.CheckBoxPreference, androidx.preference.Preference
    public void onBindViewHolder(PreferenceViewHolder preferenceViewHolder) {
        int i;
        super.onBindViewHolder(preferenceViewHolder);
        View findViewById = preferenceViewHolder.findViewById(R.id.summary_container);
        if (findViewById != null) {
            findViewById.setVisibility(TextUtils.isEmpty(getSummary()) ? 8 : 0);
            View findViewById2 = preferenceViewHolder.findViewById(R.id.appendix);
            this.mAppendix = findViewById2;
            if (findViewById2 != null && (i = this.mAppendixVisibility) != -1) {
                findViewById2.setVisibility(i);
            }
        }
        this.mExtraWidget =
                (ImageView) preferenceViewHolder.findViewById(R.id.selector_extra_widget);
        this.mExtraWidgetContainer =
                preferenceViewHolder.findViewById(R.id.selector_extra_widget_container);
        setExtraWidgetOnClickListener(this.mExtraWidgetOnClickListener);
    }

    @Override // androidx.preference.TwoStatePreference, androidx.preference.Preference
    public void onClick() {
        OnClickListener onClickListener = this.mListener;
        if (onClickListener != null) {
            onClickListener.onRadioButtonClicked(this);
        }
    }

    public final void setExtraWidgetOnClickListener(View.OnClickListener onClickListener) {
        this.mExtraWidgetOnClickListener = onClickListener;
        ImageView imageView = this.mExtraWidget;
        if (imageView == null || this.mExtraWidgetContainer == null) {
            return;
        }
        imageView.setOnClickListener(onClickListener);
        this.mExtraWidgetContainer.setVisibility(this.mExtraWidgetOnClickListener != null ? 0 : 8);
    }

    public SelectorWithWidgetPreference(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mListener = null;
        this.mAppendixVisibility = -1;
        this.mIsCheckBox = false;
        init$9$1();
    }

    public SelectorWithWidgetPreference(Context context, boolean z) {
        super(context, null);
        this.mListener = null;
        this.mAppendixVisibility = -1;
        this.mIsCheckBox = z;
        init$9$1();
    }

    public SelectorWithWidgetPreference(Context context) {
        this(context, (AttributeSet) null);
    }
}
