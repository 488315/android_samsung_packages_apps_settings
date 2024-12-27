package com.samsung.android.settings.wifi.mobileap.views;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;

import androidx.preference.Preference;
import androidx.preference.PreferenceViewHolder;

import com.android.settings.R;

import com.samsung.android.settings.wifi.mobileap.utils.WifiApSettingsUtils;
import com.samsung.android.settings.wifi.mobileap.views.progressbar.StackProgressbar;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public class WifiApStackedProgressBarPreference extends Preference {
    public static final int PREFERENCE_LAYOUT;
    public static final int[] PROGRESS_COLORS;
    public static final String TAG;
    public static final int UNUSED_LIGHT_COLOR = 0;
    public final Context mContext;
    public boolean mIsAnimateProgressBarRequired;
    public boolean mIsDisabledProgressBar;
    public boolean mIsNonWhiteBackgroundRequired;
    public final float[] mStackedDataArray;
    public float mStackedDataTotalSum;

    static {
        int convertToColorRGB = WifiApSettingsUtils.convertToColorRGB("#FFC0C9D8");
        WifiApSettingsUtils.convertToColorRGB("#80E0E0E0");
        WifiApSettingsUtils.convertToColorRGB("#80E0E0E0");
        WifiApSettingsUtils.convertToColorRGB("#393939");
        PROGRESS_COLORS =
                new int[] {
                    WifiApSettingsUtils.convertToColorRGB("#FF387AFF"),
                    WifiApSettingsUtils.convertToColorRGB("#FF154050"),
                    WifiApSettingsUtils.convertToColorRGB("#FF0FBE7A"),
                    convertToColorRGB,
                    convertToColorRGB,
                    convertToColorRGB,
                    convertToColorRGB,
                    convertToColorRGB,
                    convertToColorRGB,
                    convertToColorRGB,
                    convertToColorRGB,
                    convertToColorRGB,
                    convertToColorRGB,
                    convertToColorRGB
                };
        TAG = "WifiApStackedProgressBarPreference";
        PREFERENCE_LAYOUT = R.layout.sec_wifi_ap_stacked_progress_bar_view_layout;
    }

    public WifiApStackedProgressBarPreference(
            Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.mStackedDataArray = new float[21];
        this.mStackedDataTotalSum = 0.0f;
        this.mIsAnimateProgressBarRequired = true;
        this.mIsNonWhiteBackgroundRequired = false;
        this.mContext = context;
        setLayoutResource(PREFERENCE_LAYOUT);
        for (int i3 = 0; i3 < 21; i3++) {
            this.mStackedDataArray[i3] = 0.0f;
        }
    }

    @Override // androidx.preference.Preference
    public final void onBindViewHolder(PreferenceViewHolder preferenceViewHolder) {
        super.onBindViewHolder(preferenceViewHolder);
        StackProgressbar stackProgressbar =
                (StackProgressbar) preferenceViewHolder.findViewById(R.id.stacked_progress_bar);
        stackProgressbar.getClass();
        Log.i("StackProgressbar", "setIsTop3ColorsToBeShown - false");
        stackProgressbar.mIsTop10ColorsToBeShown = false;
        if (this.mIsNonWhiteBackgroundRequired) {
            preferenceViewHolder.itemView.setBackgroundColor(
                    this.mContext.getColor(R.color.sesl_round_and_bgcolor_light));
        } else {
            TypedValue typedValue = new TypedValue();
            getContext()
                    .getTheme()
                    .resolveAttribute(android.R.attr.itemBackground, typedValue, true);
            preferenceViewHolder.itemView.setBackgroundResource(typedValue.resourceId);
        }
        stackProgressbar.setProgressbar(
                this.mStackedDataArray,
                this.mStackedDataTotalSum,
                this.mIsAnimateProgressBarRequired);
        this.mIsAnimateProgressBarRequired = false;
    }

    public WifiApStackedProgressBarPreference(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public WifiApStackedProgressBarPreference(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public WifiApStackedProgressBarPreference(Context context) {
        this(context, null);
    }
}
