package com.samsung.android.settings.usefulfeature.videoenhancer;

import android.content.Context;
import android.graphics.Typeface;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.preference.PreferenceViewHolder;
import com.android.settings.R;
import com.android.settings.Utils;
import com.samsung.android.settings.widget.RadioItemDataInfo;
import com.samsung.android.settings.widget.SecHorizontalRadioPreference;
import com.samsung.android.settings.widget.SecHorizontalRadioViewContainer;
import com.samsung.android.util.SemLog;
import java.util.HashMap;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public class SecVideoBrightnessHorizontalRadioPreference extends SecHorizontalRadioPreference {
    public ViewGroup mContainerLayout;
    public final MutableLiveData mCurrentValue;
    public boolean mIsEnabled;
    public final HashMap mIsItemEnabledMap;
    public int mMinHeight;
    public SecVideoBrightnessHorizontalRadioPreference$$ExternalSyntheticLambda3 mObserver;
    public RadioItemDataInfo mRadioItemDataInfo;

    public SecVideoBrightnessHorizontalRadioPreference(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mIsItemEnabledMap = new HashMap();
        this.mCurrentValue = new MutableLiveData();
        this.mIsEnabled = true;
        setLayoutResource(R.layout.sec_video_brightness_radio_button_layout_horizontal);
    }

    @Override // com.samsung.android.settings.widget.SecHorizontalRadioPreference
    public final String getCurrentValue$1() {
        return (String) this.mCurrentValue.getValue();
    }

    @Override // com.samsung.android.settings.widget.SecHorizontalRadioPreference
    public final void invalidate(PreferenceViewHolder preferenceViewHolder) {
        int i;
        int i2 = 0;
        for (String str : this.mRadioItemDataInfo.mItemValueList) {
            View view = preferenceViewHolder.itemView;
            if (i2 == 0) {
                i = R.id.item1;
            } else if (i2 == 1) {
                i = R.id.item2;
            } else {
                if (i2 != 2) {
                    throw new IllegalArgumentException("Out of index");
                }
                i = R.id.item3;
            }
            ViewGroup viewGroup = (ViewGroup) view.findViewById(i);
            RadioButton radioButton = (RadioButton) viewGroup.findViewById(R.id.radio_button);
            TextView textView = (TextView) viewGroup.findViewById(R.id.icon_title);
            viewGroup.findViewById(R.id.image_frame).setVisibility(0);
            boolean equals = TextUtils.equals(str, (CharSequence) this.mCurrentValue.getValue());
            radioButton.setChecked(equals);
            radioButton.jumpDrawablesToCurrentState();
            textView.setSelected(equals);
            textView.setTypeface(Typeface.create("sec", equals ? 1 : 0));
            boolean z = this.mIsEnabled && this.mIsItemEnabledMap.get(str) != Boolean.FALSE;
            viewGroup.setEnabled(z);
            viewGroup.setAlpha(z ? 1.0f : 0.6f);
            i2++;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r2v1, types: [androidx.lifecycle.Observer, com.samsung.android.settings.usefulfeature.videoenhancer.SecVideoBrightnessHorizontalRadioPreference$$ExternalSyntheticLambda3] */
    @Override // com.samsung.android.settings.widget.SecHorizontalRadioPreference, androidx.preference.Preference
    public final void onBindViewHolder(final PreferenceViewHolder preferenceViewHolder) {
        int i;
        int size = this.mRadioItemDataInfo.mItemValueList.size();
        if (size > 3) {
            throw new IllegalArgumentException("Out of index");
        }
        ViewGroup viewGroup = (ViewGroup) preferenceViewHolder.itemView.findViewById(R.id.sec_radio_layout);
        this.mContainerLayout = viewGroup;
        ((SecHorizontalRadioViewContainer) viewGroup).mIsDividerEnabled = Boolean.FALSE;
        boolean isRTL = Utils.isRTL(getContext());
        int i2 = isRTL ? 10 : 5;
        int i3 = isRTL ? 5 : 10;
        int i4 = 0;
        for (final String str : this.mRadioItemDataInfo.mItemValueList) {
            ViewGroup viewGroup2 = this.mContainerLayout;
            if (i4 == 0) {
                i = R.id.item1;
            } else if (i4 == 1) {
                i = R.id.item2;
            } else {
                if (i4 != 2) {
                    throw new IllegalArgumentException("Out of index");
                }
                i = R.id.item3;
            }
            ViewGroup viewGroup3 = (ViewGroup) viewGroup2.findViewById(i);
            ((ImageView) viewGroup3.findViewById(R.id.icon)).setImageResource(((Integer) this.mRadioItemDataInfo.mImageList.get(i4)).intValue());
            ((ImageView) viewGroup3.findViewById(R.id.icon)).setMinimumHeight(this.mMinHeight);
            ((TextView) viewGroup3.findViewById(R.id.icon_title)).setText((CharSequence) this.mRadioItemDataInfo.mTitleList.get(i4));
            viewGroup3.findViewById(R.id.image_frame).setVisibility(0);
            viewGroup3.setVisibility(0);
            viewGroup3.setBackground(null);
            viewGroup3.setOnTouchListener(new SecVideoBrightnessHorizontalRadioPreference$$ExternalSyntheticLambda0());
            viewGroup3.setOnKeyListener(new SecVideoBrightnessHorizontalRadioPreference$$ExternalSyntheticLambda1());
            viewGroup3.setOnClickListener(new View.OnClickListener() { // from class: com.samsung.android.settings.usefulfeature.videoenhancer.SecVideoBrightnessHorizontalRadioPreference$$ExternalSyntheticLambda2
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    SecVideoBrightnessHorizontalRadioPreference.this.setValue(str);
                }
            });
            if (i4 == 0) {
                ((ImageView) viewGroup3.findViewById(R.id.icon)).semSetRoundedCorners(i2);
                ((ImageView) viewGroup3.findViewById(R.id.icon)).semSetRoundedCornerColor(i2, getContext().getResources().getColor(R.color.sec_widget_round_and_bgcolor));
            } else if (i4 == size - 1) {
                ((ImageView) viewGroup3.findViewById(R.id.icon)).semSetRoundedCorners(i3);
                ((ImageView) viewGroup3.findViewById(R.id.icon)).semSetRoundedCornerColor(i3, getContext().getResources().getColor(R.color.sec_widget_round_and_bgcolor));
            }
            i4++;
        }
        this.mCurrentValue.removeObserver(this.mObserver);
        MutableLiveData mutableLiveData = this.mCurrentValue;
        LifecycleOwner lifecycleOwner = (LifecycleOwner) getContext();
        ?? r2 = new Observer() { // from class: com.samsung.android.settings.usefulfeature.videoenhancer.SecVideoBrightnessHorizontalRadioPreference$$ExternalSyntheticLambda3
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                PreferenceViewHolder preferenceViewHolder2 = preferenceViewHolder;
                SecVideoBrightnessHorizontalRadioPreference secVideoBrightnessHorizontalRadioPreference = SecVideoBrightnessHorizontalRadioPreference.this;
                secVideoBrightnessHorizontalRadioPreference.invalidate(preferenceViewHolder2);
                secVideoBrightnessHorizontalRadioPreference.callChangeListener(secVideoBrightnessHorizontalRadioPreference.mCurrentValue.getValue());
            }
        };
        this.mObserver = r2;
        mutableLiveData.observe(lifecycleOwner, r2);
    }

    @Override // androidx.preference.Preference
    public final void setEnabled(boolean z) {
        super.setEnabled(z);
        this.mIsEnabled = z;
        notifyChanged();
    }

    @Override // com.samsung.android.settings.widget.SecHorizontalRadioPreference
    public final void setEntryEnabled(String str, boolean z) {
        this.mIsItemEnabledMap.put(str, Boolean.valueOf(z));
    }

    @Override // com.samsung.android.settings.widget.SecHorizontalRadioPreference
    public final void setRadioItemDataInfo(RadioItemDataInfo radioItemDataInfo) {
        if (radioItemDataInfo == null) {
            SemLog.d("SecHorizontalRadioPreference", "There are no ItemDataInfo for RadioItems");
        } else {
            this.mRadioItemDataInfo = radioItemDataInfo;
        }
    }

    @Override // com.samsung.android.settings.widget.SecHorizontalRadioPreference
    public final void setValue(String str) {
        if (TextUtils.equals(str, (CharSequence) this.mCurrentValue.getValue())) {
            return;
        }
        this.mCurrentValue.setValue(str);
    }
}
