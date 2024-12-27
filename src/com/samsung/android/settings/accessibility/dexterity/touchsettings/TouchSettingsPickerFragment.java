package com.samsung.android.settings.accessibility.dexterity.touchsettings;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import com.android.settings.R;
import com.android.settings.core.SubSettingLauncher;

import com.samsung.android.settings.accessibility.SecAccessibilityUtils;
import com.samsung.android.settings.accessibility.base.widget.PickerFragment;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class TouchSettingsPickerFragment extends PickerFragment {
    public Context mContext;

    @Override // com.samsung.android.settings.accessibility.base.widget.PickerFragment
    public final int getLayoutId() {
        return R.layout.layout_touch_settings_picker;
    }

    public int getMetricsCategory() {
        return 0;
    }

    public final void launchFragment$1(int i, String str) {
        Bundle bundle = new Bundle();
        bundle.putBoolean("fromPicker", true);
        SubSettingLauncher subSettingLauncher = new SubSettingLauncher(this.mContext);
        SubSettingLauncher.LaunchRequest launchRequest = subSettingLauncher.mLaunchRequest;
        launchRequest.mDestinationName = str;
        launchRequest.mSourceMetricsCategory = getMetricsCategory();
        subSettingLauncher.addFlags(335544320);
        subSettingLauncher.setTitleRes(i, null);
        launchRequest.mExtras = bundle;
        subSettingLauncher.launch();
    }

    @Override // com.android.settings.core.InstrumentedFragment,
              // com.android.settingslib.core.lifecycle.ObservableFragment,
              // androidx.fragment.app.Fragment
    public final void onAttach(Context context) {
        super.onAttach(context);
        this.mContext = context;
    }

    @Override // androidx.fragment.app.Fragment
    public void onViewCreated(View view, Bundle bundle) {
        SecAccessibilityUtils.setButtonsColor(this.mContext, this.mTestBtn);
    }
}
