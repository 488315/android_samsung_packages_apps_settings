package com.samsung.android.settings.multidevices;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import androidx.preference.PreferenceViewHolder;
import androidx.preference.SecSwitchPreferenceScreen;
import androidx.preference.internal.PreferenceImageView;

import com.android.settings.R;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class SecMultiDevicesSwitchPreferenceScreen extends SecSwitchPreferenceScreen {
    public SecMultiDevicesSwitchPreferenceScreen(
            Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
    }

    @Override // androidx.preference.SecSwitchPreferenceScreen,
              // androidx.preference.SeslSwitchPreferenceScreen,
              // androidx.preference.SwitchPreferenceCompat, androidx.preference.Preference
    public final void onBindViewHolder(PreferenceViewHolder preferenceViewHolder) {
        super.onBindViewHolder(preferenceViewHolder);
        View view = preferenceViewHolder.itemView;
        view.setPaddingRelative(
                getContext()
                        .getResources()
                        .getDimensionPixelSize(R.dimen.sec_multi_devices_item_start_padding),
                0,
                view.getPaddingEnd(),
                0);
        PreferenceImageView preferenceImageView =
                (PreferenceImageView) preferenceViewHolder.itemView.findViewById(android.R.id.icon);
        LinearLayout.LayoutParams layoutParams =
                (LinearLayout.LayoutParams) preferenceImageView.getLayoutParams();
        layoutParams.width =
                getContext()
                        .getResources()
                        .getDimensionPixelSize(R.dimen.sec_multi_devices_item_image_size);
        layoutParams.height =
                getContext()
                        .getResources()
                        .getDimensionPixelSize(R.dimen.sec_multi_devices_item_image_size);
        preferenceImageView.setLayoutParams(layoutParams);
        View findViewById = preferenceViewHolder.itemView.findViewById(R.id.icon_frame);
        findViewById.setMinimumWidth(
                getContext()
                        .getResources()
                        .getDimensionPixelSize(R.dimen.sec_multi_devices_item_end_padding));
        findViewById.setPaddingRelative(
                0,
                0,
                getContext()
                        .getResources()
                        .getDimensionPixelSize(R.dimen.sec_multi_devices_item_end_padding),
                0);
    }

    public SecMultiDevicesSwitchPreferenceScreen(
            Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    public SecMultiDevicesSwitchPreferenceScreen(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public SecMultiDevicesSwitchPreferenceScreen(Context context) {
        super(context);
    }
}
