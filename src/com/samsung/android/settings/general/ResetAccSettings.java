package com.samsung.android.settings.general;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.settings.R;
import com.android.settings.core.SubSettingLauncher;
import com.android.settings.password.ChooseLockSettingsHelper;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class ResetAccSettings extends ResetSettingsPreferenceFragment {
    public View mContentView;

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 8019;
    }

    @Override // com.samsung.android.settings.general.ResetSettingsPreferenceFragment
    public final String getResetButtonTitle() {
        return getString(R.string.reset_network_button_text);
    }

    @Override // androidx.fragment.app.Fragment
    public final void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        if (i == 55 && i == 55 && i2 == -1) {
            showFinalConfirmation$1();
        }
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final View onCreateView(
            LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.sec_reset_accsettings, viewGroup, false);
        this.mContentView = inflate;
        return inflate;
    }

    @Override // com.samsung.android.settings.general.ResetSettingsPreferenceFragment
    public final void onResetButtonClick() {
        Resources resources = getActivity().getResources();
        ChooseLockSettingsHelper.Builder builder =
                new ChooseLockSettingsHelper.Builder(getActivity(), this);
        builder.mRequestCode = 55;
        builder.mTitle = resources.getText(R.string.sec_reset_accessibility_title);
        if (builder.show()) {
            return;
        }
        showFinalConfirmation$1();
    }

    public final void showFinalConfirmation$1() {
        Bundle bundle = new Bundle();
        bundle.putBoolean("extra_accsettings", true);
        SubSettingLauncher subSettingLauncher = new SubSettingLauncher(getContext());
        String name = ResetSettingsConfirm.class.getName();
        SubSettingLauncher.LaunchRequest launchRequest = subSettingLauncher.mLaunchRequest;
        launchRequest.mDestinationName = name;
        launchRequest.mArguments = bundle;
        subSettingLauncher.setTitleRes(R.string.sec_reset_accessibility_title, null);
        launchRequest.mSourceMetricsCategory = 8019;
        subSettingLauncher.launch();
    }
}
