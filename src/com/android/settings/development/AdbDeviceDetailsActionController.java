package com.android.settings.development;

import android.content.Context;
import android.content.Intent;
import android.debug.PairDevice;
import android.os.Parcelable;
import android.view.View;

import androidx.fragment.app.Fragment;
import androidx.preference.PreferenceScreen;

import com.android.settings.R;
import com.android.settingslib.core.AbstractPreferenceController;
import com.android.settingslib.widget.ActionButtonsPreference;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class AdbDeviceDetailsActionController extends AbstractPreferenceController {
    static final String KEY_BUTTONS_PREF = "buttons";
    public final Fragment mFragment;
    public final PairDevice mPairedDevice;

    public AdbDeviceDetailsActionController(
            PairDevice pairDevice, Context context, Fragment fragment) {
        super(context);
        this.mPairedDevice = pairDevice;
        this.mFragment = fragment;
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        ActionButtonsPreference actionButtonsPreference =
                (ActionButtonsPreference) preferenceScreen.findPreference(KEY_BUTTONS_PREF);
        ActionButtonsPreference.ButtonInfo buttonInfo = actionButtonsPreference.mButton1Info;
        if (buttonInfo.mIsVisible) {
            buttonInfo.mIsVisible = false;
            actionButtonsPreference.notifyChanged();
        }
        actionButtonsPreference.setButton2Icon(R.drawable.ic_settings_delete);
        actionButtonsPreference.setButton2Text(R.string.adb_device_forget);
        actionButtonsPreference.setButton2OnClickListener(
                new View
                        .OnClickListener() { // from class:
                                             // com.android.settings.development.AdbDeviceDetailsActionController$$ExternalSyntheticLambda0
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        AdbDeviceDetailsActionController adbDeviceDetailsActionController =
                                AdbDeviceDetailsActionController.this;
                        adbDeviceDetailsActionController.getClass();
                        Intent intent = new Intent();
                        intent.putExtra("request_type", 0);
                        intent.putExtra(
                                "paired_device",
                                (Parcelable) adbDeviceDetailsActionController.mPairedDevice);
                        Fragment fragment = adbDeviceDetailsActionController.mFragment;
                        fragment.getActivity().setResult(-1, intent);
                        fragment.getActivity().finish();
                    }
                });
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final String getPreferenceKey() {
        return KEY_BUTTONS_PREF;
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final boolean isAvailable() {
        return true;
    }
}
