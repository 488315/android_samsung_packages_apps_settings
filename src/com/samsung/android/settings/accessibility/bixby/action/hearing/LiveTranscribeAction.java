package com.samsung.android.settings.accessibility.bixby.action.hearing;

import android.accessibilityservice.AccessibilityServiceInfo;
import android.accessibilityservice.AccessibilityShortcutInfo;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.android.settings.accessibility.RestrictedPreferenceHelper;
import com.android.settings.core.SubSettingLauncher;
import com.android.settingslib.RestrictedPreference;

import com.samsung.android.settings.accessibility.AccessibilityConstant;
import com.samsung.android.settings.accessibility.SecAccessibilityUtils;
import com.samsung.android.settings.accessibility.bixby.action.BixbyControllerAction;
import com.samsung.android.settings.accessibility.bixby.data.ParsedBundle;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class LiveTranscribeAction extends BixbyControllerAction {
    public RestrictedPreference connectionPreference;

    @Override // com.samsung.android.settings.accessibility.bixby.action.BixbyControllerAction,
              // com.samsung.android.settings.accessibility.bixby.action.BixbyActionTarget
    public final Bundle doGetSupportFeature(Context context, ParsedBundle parsedBundle) {
        Bundle doGetSupportFeature = super.doGetSupportFeature(context, parsedBundle);
        if (doGetSupportFeature.getString("result") == "true") {
            if (this.connectionPreference == null) {
                init(context);
            }
            if (!this.connectionPreference.isEnabled()) {
                doGetSupportFeature.putString("result", "false");
            }
        }
        return doGetSupportFeature;
    }

    @Override // com.samsung.android.settings.accessibility.bixby.action.BixbyControllerAction
    public final String getControllerName(Context context) {
        return "com.samsung.android.settings.accessibility.hearing.controllers.LiveTranscribePreferenceController";
    }

    @Override // com.samsung.android.settings.accessibility.bixby.action.BixbyActionTarget
    public final Intent getPunchoutIntent(Context context) {
        if (this.connectionPreference == null) {
            init(context);
        }
        SubSettingLauncher subSettingLauncher = new SubSettingLauncher(context);
        SubSettingLauncher.LaunchRequest launchRequest = subSettingLauncher.mLaunchRequest;
        launchRequest.mSourceMetricsCategory = 0;
        launchRequest.mDestinationName = this.connectionPreference.getFragment();
        launchRequest.mArguments = this.connectionPreference.getExtras();
        launchRequest.mTitle = this.connectionPreference.getTitle();
        return subSettingLauncher.toIntent();
    }

    public final void init(Context context) {
        RestrictedPreference restrictedPreference;
        RestrictedPreferenceHelper restrictedPreferenceHelper =
                new RestrictedPreferenceHelper(context);
        AccessibilityServiceInfo accessibilityServiceInfo =
                SecAccessibilityUtils.getAccessibilityServiceInfo(
                        context, AccessibilityConstant.COMPONENT_NAME_LIVE_TRANSCRIBE);
        if (accessibilityServiceInfo != null) {
            restrictedPreference =
                    (RestrictedPreference)
                            ((ArrayList)
                                            restrictedPreferenceHelper
                                                    .createAccessibilityServicePreferenceList(
                                                            List.of(accessibilityServiceInfo)))
                                    .get(0);
        } else {
            AccessibilityShortcutInfo accessibilityShortcutInfo =
                    SecAccessibilityUtils.getAccessibilityShortcutInfo(
                            context,
                            AccessibilityConstant.COMPONENT_NAME_GOOGLE_LIVE_TRANSCRIBE_SHORTCUT);
            restrictedPreference =
                    accessibilityShortcutInfo != null
                            ? (RestrictedPreference)
                                    ((ArrayList)
                                                    restrictedPreferenceHelper
                                                            .createAccessibilityActivityPreferenceList(
                                                                    List.of(
                                                                            accessibilityShortcutInfo)))
                                            .get(0)
                            : null;
        }
        this.connectionPreference = restrictedPreference;
    }
}
