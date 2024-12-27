package com.samsung.android.settings.notification;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.ViewGroup;

import androidx.appcompat.widget.ActionBarContextView$$ExternalSyntheticOutline0;
import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import androidx.preference.PreferenceViewHolder;

import com.android.settings.MainClearConfirm$$ExternalSyntheticOutline0;
import com.android.settings.R;

import com.samsung.android.settings.widget.RadioItemDataInfo;
import com.samsung.android.settings.widget.SecHorizontalRadioPreference;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class SecNotificationHorizontalRadioPreference extends SecHorizontalRadioPreference {
    public final int paddingInBetween;

    public SecNotificationHorizontalRadioPreference(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.paddingStartEnd =
                (int)
                        (TypedValue.applyDimension(
                                        0,
                                        getContext()
                                                .getResources()
                                                .getDimension(
                                                        R.dimen
                                                                .sec_notification_new_style_widget_multi_btn_preference_padding_start_end),
                                        getContext().getResources().getDisplayMetrics())
                                / getContext().getResources().getDisplayMetrics().density);
        this.paddingTop =
                (int)
                        (TypedValue.applyDimension(
                                        0,
                                        getContext()
                                                .getResources()
                                                .getDimension(
                                                        R.dimen
                                                                .sec_notification_new_style_widget_multi_btn_preference_padding_top),
                                        getContext().getResources().getDisplayMetrics())
                                / getContext().getResources().getDisplayMetrics().density);
        this.paddingInBetween =
                (int)
                        (TypedValue.applyDimension(
                                        0,
                                        getContext()
                                                .getResources()
                                                .getDimension(
                                                        R.dimen
                                                                .sec_notification_new_style_widget_multi_btn_preference_padding_in_between),
                                        getContext().getResources().getDisplayMetrics())
                                / getContext().getResources().getDisplayMetrics().density);
        this.paddingBottom =
                (int)
                        (TypedValue.applyDimension(
                                        0,
                                        getContext()
                                                .getResources()
                                                .getDimension(
                                                        R.dimen
                                                                .sec_notification_new_style_widget_multi_radio_btn_preference_padding_bottom),
                                        getContext().getResources().getDisplayMetrics())
                                / getContext().getResources().getDisplayMetrics().density);
    }

    @Override // com.samsung.android.settings.widget.SecHorizontalRadioPreference,
              // androidx.preference.Preference
    public final void onBindViewHolder(PreferenceViewHolder preferenceViewHolder) {
        int i;
        int i2;
        super.onBindViewHolder(preferenceViewHolder);
        RadioItemDataInfo radioItemDataInfo = this.mRadioItemDataInfo;
        int i3 = 0;
        if (radioItemDataInfo != null) {
            i = radioItemDataInfo.mItemValueList.size();
            if (i > 3) {
                throw new IllegalArgumentException("Out of index");
            }
        } else {
            i = 0;
        }
        Log.d("SecNotificationHorizontalRadioPreference", "onBindViewHolder");
        RadioItemDataInfo radioItemDataInfo2 = this.mRadioItemDataInfo;
        if (radioItemDataInfo2 != null) {
            for (String str : radioItemDataInfo2.mItemValueList) {
                ViewGroup viewGroup = this.mContainerLayout;
                if (i3 == 0) {
                    i2 = R.id.item1;
                } else if (i3 == 1) {
                    i2 = R.id.item2;
                } else {
                    if (i3 != 2) {
                        throw new IllegalArgumentException("Out of index");
                    }
                    i2 = R.id.item3;
                }
                ViewGroup viewGroup2 = (ViewGroup) viewGroup.findViewById(i2);
                int i4 = this.paddingInBetween;
                ActionBarContextView$$ExternalSyntheticOutline0.m(
                        ListPopupWindow$$ExternalSyntheticOutline0.m(
                                i4,
                                "onBindViewHolder before paddingTemp: ",
                                " mIsDividerEnabled: "),
                        this.mIsDividerEnabled,
                        "SecNotificationHorizontalRadioPreference");
                if (!this.mIsDividerEnabled) {
                    i4 = Math.round(this.paddingInBetween / 2.0f);
                }
                MainClearConfirm$$ExternalSyntheticOutline0.m(
                        "onBindViewHolder After paddingTemp: ",
                        " index: ",
                        i4,
                        i3,
                        "SecNotificationHorizontalRadioPreference");
                if (i3 == 0) {
                    viewGroup2.setPadding(
                            this.paddingStartEnd, this.paddingTop, i4, this.paddingBottom);
                } else if (i3 == i - 1) {
                    viewGroup2.setPadding(
                            i4, this.paddingTop, this.paddingStartEnd, this.paddingBottom);
                } else {
                    viewGroup2.setPadding(i4, this.paddingTop, i4, this.paddingBottom);
                }
                i3++;
            }
            invalidate(preferenceViewHolder);
        }
    }
}
