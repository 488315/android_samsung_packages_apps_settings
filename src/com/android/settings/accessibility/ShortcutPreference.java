package com.android.settings.accessibility;

import android.content.ComponentName;
import android.content.Context;
import android.os.Bundle;

import androidx.preference.Preference;
import androidx.preference.PreferenceViewHolder;
import androidx.preference.SeslSwitchPreferenceScreen;

import com.android.internal.accessibility.AccessibilityShortcutController;
import com.android.settings.R;
import com.android.settings.core.SubSettingLauncher;

import com.samsung.android.settings.accessibility.advanced.shortcut.AccessibilityShortcutEditFragment;
import com.sec.ims.IMSParameter;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class ShortcutPreference extends SeslSwitchPreferenceScreen {
    public OnClickCallback mClickCallback;
    public ComponentName mComponentName;
    public String mExclusiveTaskName;
    public CharSequence mLabel;
    public int mResourceId;
    public boolean mSettingsEditable;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public interface OnClickCallback {
        void onToggleClicked(ShortcutPreference shortcutPreference);
    }

    /* renamed from: $r8$lambda$GJxRQNj-ouFQnP0B6fRjFwWpZyg, reason: not valid java name */
    public static void m682$r8$lambda$GJxRQNjouFQnP0B6fRjFwWpZyg(
            ShortcutPreference shortcutPreference) {
        super.setChecked(!shortcutPreference.mChecked);
        OnClickCallback onClickCallback = shortcutPreference.mClickCallback;
        if (onClickCallback != null) {
            onClickCallback.onToggleClicked(shortcutPreference);
        }
    }

    public ShortcutPreference(Context context) {
        super(context, null);
        this.mClickCallback = null;
        this.mSettingsEditable = true;
        this.mResourceId = -1;
        seslSetSummaryColor(context.getColorStateList(R.color.text_color_primary_dark));
    }

    @Override // androidx.preference.TwoStatePreference
    public final boolean isChecked() {
        return this.mChecked;
    }

    @Override // androidx.preference.SeslSwitchPreferenceScreen,
              // androidx.preference.SwitchPreferenceCompat, androidx.preference.Preference
    public final void onBindViewHolder(PreferenceViewHolder preferenceViewHolder) {
        super.onBindViewHolder(preferenceViewHolder);
        Bundle bundle = new Bundle();
        if (this.mComponentName.equals(
                AccessibilityShortcutController.MAGNIFICATION_COMPONENT_NAME)) {
            bundle.putBoolean("isSupportTriple", true);
            bundle.putString(
                    IMSParameter.CALL.ACTION,
                    "com.android.server.accessibility.MagnificationController");
        } else {
            bundle.putString(IMSParameter.CALL.ACTION, this.mComponentName.flattenToString());
        }
        bundle.putInt("defaultType", 0);
        bundle.putString("exclusive_feature", this.mExclusiveTaskName);
        bundle.putCharSequence("label", this.mLabel);
        bundle.putInt("label_resource_id", this.mResourceId);
        SubSettingLauncher subSettingLauncher = new SubSettingLauncher(getContext());
        String name = AccessibilityShortcutEditFragment.class.getName();
        SubSettingLauncher.LaunchRequest launchRequest = subSettingLauncher.mLaunchRequest;
        launchRequest.mDestinationName = name;
        launchRequest.mArguments = bundle;
        launchRequest.mSourceMetricsCategory = 0;
        setIntent(subSettingLauncher.toIntent());
        setOnPreferenceChangeListener(
                new Preference
                        .OnPreferenceChangeListener() { // from class:
                                                        // com.android.settings.accessibility.ShortcutPreference$$ExternalSyntheticLambda0
                    @Override // androidx.preference.Preference.OnPreferenceChangeListener
                    public final boolean onPreferenceChange(Preference preference, Object obj) {
                        ShortcutPreference.m682$r8$lambda$GJxRQNjouFQnP0B6fRjFwWpZyg(
                                ShortcutPreference.this);
                        return false;
                    }
                });
        preferenceViewHolder.itemView.setClickable(this.mSettingsEditable);
        preferenceViewHolder.itemView.setFocusable(this.mSettingsEditable);
    }
}
