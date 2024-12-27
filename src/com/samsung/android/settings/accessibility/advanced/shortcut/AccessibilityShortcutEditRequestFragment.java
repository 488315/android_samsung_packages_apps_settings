package com.samsung.android.settings.accessibility.advanced.shortcut;

import android.accessibilityservice.AccessibilityServiceInfo;
import android.accessibilityservice.AccessibilityShortcutInfo;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.fragment.app.FragmentActivity;

import com.android.settings.MainClearConfirm$$ExternalSyntheticOutline0;
import com.android.settings.R;

import com.samsung.android.settings.accessibility.AccessibilityConstant;
import com.samsung.android.settings.accessibility.SecAccessibilityUtils;
import com.sec.ims.IMSParameter;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class AccessibilityShortcutEditRequestFragment extends AccessibilityShortcutEditFragment {
    public String componentString;

    @Override // com.samsung.android.settings.accessibility.advanced.shortcut.AccessibilityShortcutEditFragment, com.samsung.android.settings.accessibility.base.widget.CheckBoxPickerFragment, com.android.settings.dashboard.DashboardFragment, com.android.settings.SettingsPreferenceFragment, com.android.settings.core.InstrumentedPreferenceFragment, com.android.settingslib.core.lifecycle.ObservablePreferenceFragment, androidx.fragment.app.Fragment
    public final void onAttach(Context context) {
        Intent intent;
        Log.d("AccessibilityShortcutEditRequestFragment", "onAttach");
        FragmentActivity activity = getActivity();
        if (activity != null) {
            Intent intent2 = activity.getIntent();
            if (intent2 == null) {
                Log.w("AccessibilityShortcutEditRequestFragment", "activity.getIntent() is null.");
            } else {
                this.componentString = intent2.getStringExtra("EXTRA_SHORTCUT_COMPONENT");
                MainClearConfirm$$ExternalSyntheticOutline0.m(
                        new StringBuilder("EXTRA_SHORTCUT_COMPONENT: "),
                        this.componentString,
                        "AccessibilityShortcutEditRequestFragment");
                String str = this.componentString;
                if (str == null) {
                    Log.w("AccessibilityShortcutEditRequestFragment", "componentString is null.");
                } else {
                    AccessibilityShortcutInfo accessibilityShortcutInfo =
                            SecAccessibilityUtils.getAccessibilityShortcutInfo(
                                    activity, ComponentName.unflattenFromString(str));
                    AccessibilityServiceInfo accessibilityServiceInfo =
                            SecAccessibilityUtils.getAccessibilityServiceInfo(
                                    activity,
                                    ComponentName.unflattenFromString(this.componentString));
                    Log.d(
                            "AccessibilityShortcutEditRequestFragment",
                            "onAttach shortcutInfo : "
                                    + accessibilityShortcutInfo
                                    + ", serviceInfo : "
                                    + accessibilityServiceInfo);
                    if (accessibilityShortcutInfo != null) {
                        String str2 =
                                (String)
                                        accessibilityShortcutInfo
                                                .getActivityInfo()
                                                .loadLabel(activity.getPackageManager());
                        String str3 = this.componentString;
                        Bundle arguments = getArguments();
                        if (arguments == null) {
                            arguments = new Bundle();
                        }
                        arguments.putString(IMSParameter.CALL.ACTION, str3);
                        arguments.putString("label", str2);
                        if (AccessibilityConstant.COMPONENT_NAME_SPEAK_KEYBOARD_INPUT_ALOUD
                                .flattenToString()
                                .equals(str3)) {
                            arguments.putInt("defaultType", 1);
                        }
                        if (AccessibilityConstant.COMPONENT_NAME_INTERACTION_CONTROL_SHORTCUT
                                .flattenToShortString()
                                .equals(str3)) {
                            arguments.putBoolean("isSupportSoftware", false);
                            arguments.putInt("defaultType", 512);
                            arguments.putString("exclusive_feature", "interaction_control");
                        }
                        setArguments(arguments);
                        activity.setTitle(
                                activity.getString(R.string.accessibility_shortcut_title, str2));
                    } else if (accessibilityServiceInfo != null
                            && (intent = activity.getIntent()) != null) {
                        Bundle bundleExtra = intent.getBundleExtra("bundle");
                        setArguments(bundleExtra);
                        activity.setTitle(
                                activity.getString(
                                        R.string.accessibility_shortcut_title,
                                        bundleExtra.getString("label")));
                    }
                }
            }
            activity.finish();
        }
        super.onAttach(context);
    }
}
