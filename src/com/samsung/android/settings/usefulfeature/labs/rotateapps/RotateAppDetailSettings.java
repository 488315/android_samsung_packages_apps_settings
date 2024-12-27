package com.samsung.android.settings.usefulfeature.labs.rotateapps;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.content.res.Configuration;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.widget.ActionBarContextView$$ExternalSyntheticOutline0;

import com.android.settings.R;
import com.android.settings.SettingsPreferenceFragment;
import com.android.settings.widget.SettingsMainSwitchBar;
import com.android.settings.widget.SettingsMainSwitchPreference;
import com.android.settingslib.widget.LayoutPreference;

import com.samsung.android.settings.Rune;
import com.samsung.android.settings.display.SecDisplayUtils;
import com.samsung.android.settings.logging.LoggingHelper;
import com.samsung.android.settings.usefulfeature.UsefulfeatureUtils;
import com.samsung.android.settings.widget.SecRadioButtonPreference;

import java.util.ArrayList;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public class RotateAppDetailSettings extends SettingsPreferenceFragment
        implements SecRadioButtonPreference.OnClickListener,
                CompoundButton.OnCheckedChangeListener {
    public SecRadioButtonPreference mAppDefaultType;
    public Context mContext;
    public SecRadioButtonPreference mFullScreenType;
    public PackageManager mPackageManager;
    public String mPackageName;
    public int mPolicyFlag;
    public SettingsMainSwitchPreference mSwitchBarPreference;
    public boolean mSwitchState;

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 68215;
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment
    public final int getPreferenceScreenResId() {
        return R.xml.sec_rotate_app_detail_settings;
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onAttach(Context context) {
        super.onAttach(context);
        Bundle arguments = getArguments();
        if (arguments != null) {
            this.mPackageName = arguments.getString("packageName");
        }
        if (TextUtils.isEmpty(this.mPackageName)) {
            finishFragment();
        }
    }

    @Override // android.widget.CompoundButton.OnCheckedChangeListener
    public final void onCheckedChanged(CompoundButton compoundButton, boolean z) {
        int switchOrientationPolicyFlag =
                UsefulfeatureUtils.setSwitchOrientationPolicyFlag(this.mPolicyFlag, z);
        this.mPolicyFlag = switchOrientationPolicyFlag;
        this.mSwitchState = switchOrientationPolicyFlag == 7 || switchOrientationPolicyFlag == 31;
        StringBuilder sb = new StringBuilder("policy : ");
        sb.append(this.mPolicyFlag);
        sb.append(" / switch : ");
        ActionBarContextView$$ExternalSyntheticOutline0.m(
                sb, this.mSwitchState, "RotateAppDetailSettings");
        setPackageOrientation(this.mPolicyFlag);
        this.mSwitchBarPreference.setTitle(z ? R.string.switch_on_text : R.string.switch_off_text);
        LoggingHelper.insertEventLogging(68215, 68216, z ? 1L : 0L, this.mPackageName);
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment,
              // android.content.ComponentCallbacks
    public final void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        boolean z = UsefulfeatureUtils.mAccessibilityEnabled;
        Signature[] signatureArr = SecDisplayUtils.SIGNATURES;
        String str = Rune.COMMON_CONFIG_PACKAGE_NAME_SMART_MANAGER;
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.mPackageManager = getContext().getPackageManager();
        LayoutPreference layoutPreference =
                (LayoutPreference) getPreferenceScreen().findPreference("pref_app_header");
        ImageView imageView =
                (ImageView) layoutPreference.mRootView.findViewById(R.id.entity_header_icon);
        TextView textView =
                (TextView) layoutPreference.mRootView.findViewById(R.id.entity_header_title);
        ((TextView) layoutPreference.mRootView.findViewById(R.id.entity_header_summary))
                .setVisibility(8);
        try {
            ApplicationInfo applicationInfo =
                    this.mPackageManager.getApplicationInfo(this.mPackageName, 0);
            imageView.setImageDrawable(this.mPackageManager.getApplicationIcon(applicationInfo));
            textView.setText(applicationInfo.loadLabel(this.mPackageManager));
        } catch (PackageManager.NameNotFoundException unused) {
            imageView.setImageDrawable(null);
            textView.setText(this.mPackageName);
        }
        int policyFromLegacyFlag =
                UsefulfeatureUtils.getPolicyFromLegacyFlag(
                        UsefulfeatureUtils.getPackageOrientation(this.mPackageName));
        this.mPolicyFlag = policyFromLegacyFlag;
        this.mSwitchState = policyFromLegacyFlag == 7 || policyFromLegacyFlag == 31;
        SettingsMainSwitchPreference settingsMainSwitchPreference =
                (SettingsMainSwitchPreference) findPreference("main_switch_preference");
        this.mSwitchBarPreference = settingsMainSwitchPreference;
        settingsMainSwitchPreference.addOnSwitchChangeListener(this);
        this.mSwitchBarPreference.setChecked(this.mSwitchState);
        SettingsMainSwitchPreference settingsMainSwitchPreference2 = this.mSwitchBarPreference;
        settingsMainSwitchPreference2.setTitle(
                settingsMainSwitchPreference2.mChecked
                        ? R.string.switch_on_text
                        : R.string.switch_off_text);
        this.mAppDefaultType = (SecRadioButtonPreference) findPreference("app_default");
        SecRadioButtonPreference secRadioButtonPreference =
                (SecRadioButtonPreference) findPreference("full_screen");
        this.mFullScreenType = secRadioButtonPreference;
        this.mAppDefaultType.mListener = this;
        secRadioButtonPreference.mListener = this;
        int i = this.mPolicyFlag;
        if (i != 0) {
            if (i != 7) {
                if (i != 31) {
                    if (i != 32) {
                        return;
                    }
                }
            }
            Log.d(
                    "RotateAppDetailSettings",
                    "policy : "
                            + this.mPolicyFlag
                            + " / switch : "
                            + this.mSwitchState
                            + " updateScreen");
            updateRadioButtons$2(this.mFullScreenType);
            return;
        }
        Log.d(
                "RotateAppDetailSettings",
                "policy : "
                        + this.mPolicyFlag
                        + " / switch : "
                        + this.mSwitchState
                        + " updateScreen");
        updateRadioButtons$2(this.mAppDefaultType);
    }

    @Override // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onDestroyView() {
        super.onDestroyView();
        SettingsMainSwitchPreference settingsMainSwitchPreference = this.mSwitchBarPreference;
        if (settingsMainSwitchPreference != null) {
            ((ArrayList) settingsMainSwitchPreference.mSwitchChangeListeners).remove(this);
            SettingsMainSwitchBar settingsMainSwitchBar =
                    settingsMainSwitchPreference.mMainSwitchBar;
            if (settingsMainSwitchBar != null) {
                settingsMainSwitchBar.removeOnSwitchChangeListener(this);
            }
        }
    }

    @Override // com.samsung.android.settings.widget.SecRadioButtonPreference.OnClickListener
    public final void onRadioButtonClicked(SecRadioButtonPreference secRadioButtonPreference) {
        int i;
        updateRadioButtons$2(secRadioButtonPreference);
        String key = secRadioButtonPreference.getKey();
        key.getClass();
        if (key.equals("full_screen")) {
            this.mPolicyFlag = this.mSwitchState ? 7 : 32;
            i = 1;
        } else if (key.equals("app_default")) {
            this.mPolicyFlag = this.mSwitchState ? 31 : 0;
            i = 0;
        } else {
            i = -1;
        }
        setPackageOrientation(this.mPolicyFlag);
        Log.d(
                "RotateAppDetailSettings",
                "policy : "
                        + this.mPolicyFlag
                        + " / switch : "
                        + this.mSwitchState
                        + " onRadioButtonClicked");
        LoggingHelper.insertEventLogging(68215, 68217, (long) i, this.mPackageName);
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onResume() {
        super.onResume();
        boolean z = UsefulfeatureUtils.mAccessibilityEnabled;
        Signature[] signatureArr = SecDisplayUtils.SIGNATURES;
        String str = Rune.COMMON_CONFIG_PACKAGE_NAME_SMART_MANAGER;
    }

    public final void setPackageOrientation(int i) {
        Log.d(
                "RotateAppDetailSettings",
                "policy : "
                        + this.mPolicyFlag
                        + " / switch : "
                        + this.mSwitchState
                        + " setPackageOrientation");
        UsefulfeatureUtils.setPackageOrientation(i, this.mPackageName);
        if (this.mPackageName.equalsIgnoreCase("com.android.samsung.utilityapp")) {
            UsefulfeatureUtils.setPackageOrientation(i, "com.android.samsung.batteryusage");
            UsefulfeatureUtils.setPackageOrientation(i, "com.samsung.android.statsd");
            UsefulfeatureUtils.setPackageOrientation(i, "com.samsung.android.appbooster");
            UsefulfeatureUtils.setPackageOrientation(i, "com.samsung.android.thermalguardian");
            UsefulfeatureUtils.setPackageOrientation(i, "com.samsung.android.memoryguardian");
            UsefulfeatureUtils.setPackageOrientation(i, "com.samsung.android.mediaguardian");
        }
    }

    public final void updateRadioButtons$2(SecRadioButtonPreference secRadioButtonPreference) {
        if (secRadioButtonPreference == this.mAppDefaultType) {
            this.mFullScreenType.setChecked(false);
            this.mAppDefaultType.setChecked(true);
            return;
        }
        SecRadioButtonPreference secRadioButtonPreference2 = this.mFullScreenType;
        if (secRadioButtonPreference == secRadioButtonPreference2) {
            secRadioButtonPreference2.setChecked(true);
            this.mAppDefaultType.setChecked(false);
        }
    }
}
