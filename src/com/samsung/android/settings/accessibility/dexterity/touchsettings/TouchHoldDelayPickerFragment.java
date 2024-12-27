package com.samsung.android.settings.accessibility.dexterity.touchsettings;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import com.android.settings.R;
import com.android.settings.core.SubSettingLauncher;

import com.samsung.android.settings.accessibility.dexterity.TouchAndHoldFragment;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class TouchHoldDelayPickerFragment extends TouchAndHoldFragment {
    public Context mContext;

    @Override // com.samsung.android.settings.accessibility.dexterity.TouchAndHoldFragment,
              // com.android.settings.core.InstrumentedFragment,
              // com.android.settingslib.core.lifecycle.ObservableFragment,
              // androidx.fragment.app.Fragment
    public final void onAttach(Context context) {
        super.onAttach(context);
        this.mContext = context;
    }

    @Override // com.samsung.android.settings.accessibility.dexterity.TouchAndHoldFragment,
              // com.android.settings.core.InstrumentedFragment,
              // com.android.settingslib.core.lifecycle.ObservableFragment,
              // androidx.fragment.app.Fragment
    public final void onResume() {
        super.onResume();
        this.mDescription.setText(R.string.touch_and_hold_delay_picker_description);
        final Bundle bundle = new Bundle();
        bundle.putBoolean("fromPicker", true);
        this.mTryButton.setOnClickListener(
                new View
                        .OnClickListener() { // from class:
                                             // com.samsung.android.settings.accessibility.dexterity.touchsettings.TouchHoldDelayPickerFragment$$ExternalSyntheticLambda0
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        TouchHoldDelayPickerFragment touchHoldDelayPickerFragment =
                                TouchHoldDelayPickerFragment.this;
                        Bundle bundle2 = bundle;
                        SubSettingLauncher subSettingLauncher =
                                new SubSettingLauncher(touchHoldDelayPickerFragment.mContext);
                        String name = TouchHoldDelayExperienceFragment.class.getName();
                        SubSettingLauncher.LaunchRequest launchRequest =
                                subSettingLauncher.mLaunchRequest;
                        launchRequest.mDestinationName = name;
                        launchRequest.mSourceMetricsCategory = 5005;
                        subSettingLauncher.addFlags(335544320);
                        subSettingLauncher.setTitleRes(R.string.touch_and_hold_delay_header, null);
                        launchRequest.mExtras = bundle2;
                        subSettingLauncher.launch();
                    }
                });
    }
}
