package com.samsung.android.settings.accessibility.dexterity.touchsettings;

import android.content.Context;
import android.os.Bundle;
import android.provider.Settings;
import android.text.method.ScrollingMovementMethod;
import android.view.View;

import com.android.settings.R;

import com.samsung.android.settings.accessibility.base.widget.PickerCompat;
import com.samsung.android.settings.accessibility.dexterity.TapDurationUtils;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class TapDurationPickerFragment extends TouchSettingsPickerFragment
        implements PickerCompat.setOnValueChangeListener {
    @Override // com.samsung.android.settings.accessibility.dexterity.touchsettings.TouchSettingsPickerFragment, com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 5006;
    }

    @Override // com.samsung.android.settings.accessibility.base.widget.PickerCompat.setOnValueChangeListener
    public final void onValueChange(String str) {
        Context context = this.mContext;
        Settings.Secure.putFloat(
                context.getContentResolver(), "tap_duration_threshold", Float.parseFloat(str));
    }

    @Override // com.samsung.android.settings.accessibility.dexterity.touchsettings.TouchSettingsPickerFragment, androidx.fragment.app.Fragment
    public final void onViewCreated(View view, Bundle bundle) {
        super.onViewCreated(view, bundle);
        this.mPickerDescription.setText(R.string.tap_duration_picker_description);
        this.mPickerDescription.setMovementMethod(new ScrollingMovementMethod());
        this.mPickerDescription.setClickable(false);
        this.mPickerDescription.setLongClickable(false);
        setButton(
                getString(R.string.accessibility_common_button_try_it_out),
                new View
                        .OnClickListener() { // from class:
                                             // com.samsung.android.settings.accessibility.dexterity.touchsettings.TapDurationPickerFragment$$ExternalSyntheticLambda0
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view2) {
                        TapDurationPickerFragment tapDurationPickerFragment =
                                TapDurationPickerFragment.this;
                        tapDurationPickerFragment.getClass();
                        tapDurationPickerFragment.launchFragment$1(
                                R.string.tap_duration_title,
                                TapDurationExperienceFragment.class.getName());
                    }
                });
        setupPicker(Float.toString(TapDurationUtils.getTapDurationValue(this.mContext)), "4.0");
        this.mPicker.mValueChangeListener = this;
    }
}
