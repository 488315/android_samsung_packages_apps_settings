package com.samsung.android.settings.accessibility.bixby.action.talkback;

import android.accessibilityservice.AccessibilityServiceInfo;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.os.UserHandle;

import com.android.settings.R;
import com.android.settings.accessibility.AccessibilityMetricsFeatureProviderImpl;
import com.android.settings.accessibility.AccessibilityServicePreference;
import com.android.settings.accessibility.AccessibilitySettings;
import com.android.settings.accessibility.RestrictedPreferenceHelper;
import com.android.settings.core.SubSettingLauncher;
import com.android.settings.development.Enable16kUtils;
import com.android.settings.overlay.FeatureFactoryImpl;
import com.android.settingslib.accessibility.AccessibilityUtils;

import com.samsung.android.settings.accessibility.AccessibilityConstant;
import com.samsung.android.settings.accessibility.SecAccessibilityUtils;
import com.samsung.android.settings.accessibility.bixby.BixbyUtils;
import com.samsung.android.settings.accessibility.bixby.action.BixbyControllerAction;
import com.samsung.android.settings.accessibility.bixby.data.ParsedBundle;
import com.samsung.android.settings.accessibility.home.TalkBackPreferenceFragment;

import java.util.List;
import java.util.Set;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class TalkbackAction extends BixbyControllerAction {
    @Override // com.samsung.android.settings.accessibility.bixby.action.BixbyControllerAction,
              // com.samsung.android.settings.accessibility.bixby.action.BixbyActionTarget
    public final Bundle doEnableAction(Context context, ParsedBundle parsedBundle, boolean z) {
        Bundle bundle = new Bundle();
        AccessibilityUtils.setAccessibilityServiceState(
                context, AccessibilityConstant.COMPONENT_NAME_SAMSUNG_TALKBACK, z);
        bundle.putString("result", "success");
        return bundle;
    }

    @Override // com.samsung.android.settings.accessibility.bixby.action.BixbyControllerAction,
              // com.samsung.android.settings.accessibility.bixby.action.BixbyActionTarget
    public final Bundle doGetCurrentStatus(Context context, ParsedBundle parsedBundle) {
        Bundle bundle = new Bundle();
        bundle.putString(
                "result",
                BixbyUtils.getStateAlreadyChecked(
                        parsedBundle.menuValue,
                        AccessibilityUtils.getEnabledServicesFromSettings(context)
                                .contains(AccessibilityConstant.COMPONENT_NAME_SAMSUNG_TALKBACK)));
        return bundle;
    }

    @Override // com.samsung.android.settings.accessibility.bixby.action.BixbyActionTarget
    public final Bundle doGetExclusivePopup(Context context) {
        Bundle bundle = new Bundle();
        String exclusivepopupDescription =
                BixbyUtils.getExclusivepopupDescription(
                        context, "talkback_se", context.getString(R.string.talkback_title));
        bundle.putString("result", exclusivepopupDescription.isEmpty() ? "false" : "true");
        bundle.putString("description", exclusivepopupDescription);
        return bundle;
    }

    @Override // com.samsung.android.settings.accessibility.bixby.action.BixbyControllerAction
    public final String getControllerName(Context context) {
        return "com.samsung.android.settings.accessibility.home.TalkBackPreferenceController";
    }

    @Override // com.samsung.android.settings.accessibility.bixby.action.BixbyActionTarget
    public final Intent getPunchoutIntent(Context context) {
        AccessibilityServicePreference accessibilityServicePreference;
        AccessibilityServiceInfo accessibilityServiceInfo =
                SecAccessibilityUtils.getAccessibilityServiceInfo(
                        context, AccessibilityConstant.COMPONENT_NAME_SAMSUNG_TALKBACK);
        if (accessibilityServiceInfo == null) {
            return null;
        }
        RestrictedPreferenceHelper restrictedPreferenceHelper =
                new RestrictedPreferenceHelper(context);
        Set enabledServicesFromSettings =
                AccessibilityUtils.getEnabledServicesFromSettings(context);
        List permittedAccessibilityServices =
                restrictedPreferenceHelper.mDpm.getPermittedAccessibilityServices(
                        UserHandle.myUserId());
        ResolveInfo resolveInfo = accessibilityServiceInfo.getResolveInfo();
        String str = resolveInfo.serviceInfo.packageName;
        if (str.contains("android.apps.accessibility.voiceaccess")
                && Enable16kUtils.isPageAgnosticModeOn(context)) {
            accessibilityServicePreference = null;
        } else {
            ComponentName componentName = new ComponentName(str, resolveInfo.serviceInfo.name);
            boolean contains = enabledServicesFromSettings.contains(componentName);
            AccessibilityServicePreference accessibilityServicePreference2 =
                    new AccessibilityServicePreference(
                            context,
                            str,
                            resolveInfo.serviceInfo.applicationInfo.uid,
                            accessibilityServiceInfo,
                            contains);
            restrictedPreferenceHelper.setRestrictedPreferenceEnabled(
                    accessibilityServicePreference2, permittedAccessibilityServices, contains);
            PackageManager packageManager = context.getPackageManager();
            String flattenToString = componentName.flattenToString();
            int animatedImageRes = accessibilityServiceInfo.getAnimatedImageRes();
            CharSequence loadIntro = accessibilityServiceInfo.loadIntro(packageManager);
            CharSequence serviceDescription =
                    AccessibilitySettings.getServiceDescription(
                            context, accessibilityServiceInfo, contains);
            String loadHtmlDescription =
                    accessibilityServiceInfo.loadHtmlDescription(packageManager);
            String settingsActivityName = accessibilityServiceInfo.getSettingsActivityName();
            String tileServiceName = accessibilityServiceInfo.getTileServiceName();
            ResolveInfo resolveInfo2 = accessibilityServiceInfo.getResolveInfo();
            FeatureFactoryImpl featureFactoryImpl = FeatureFactoryImpl._factory;
            if (featureFactoryImpl == null) {
                throw new UnsupportedOperationException("No feature factory configured");
            }
            ((AccessibilityMetricsFeatureProviderImpl)
                            featureFactoryImpl.accessibilityMetricsFeatureProvider$delegate
                                    .getValue())
                    .getClass();
            RestrictedPreferenceHelper.putBasicExtras(
                    accessibilityServicePreference2,
                    flattenToString,
                    accessibilityServiceInfo.getResolveInfo().loadLabel(packageManager),
                    loadIntro,
                    serviceDescription,
                    animatedImageRes,
                    loadHtmlDescription,
                    componentName);
            Bundle extras = accessibilityServicePreference2.getExtras();
            extras.putParcelable("resolve_info", resolveInfo2);
            extras.putBoolean("checked", contains);
            RestrictedPreferenceHelper.putSettingsExtras(
                    accessibilityServicePreference2, str, settingsActivityName);
            RestrictedPreferenceHelper.putTileServiceExtras(
                    accessibilityServicePreference2, str, tileServiceName);
            accessibilityServicePreference = accessibilityServicePreference2;
        }
        if (accessibilityServicePreference == null) {
            return null;
        }
        SubSettingLauncher subSettingLauncher = new SubSettingLauncher(context);
        SubSettingLauncher.LaunchRequest launchRequest = subSettingLauncher.mLaunchRequest;
        launchRequest.mSourceMetricsCategory = 0;
        launchRequest.mDestinationName = TalkBackPreferenceFragment.class.getName();
        launchRequest.mArguments = accessibilityServicePreference.getExtras();
        launchRequest.mTitle = accessibilityServicePreference.getTitle();
        return subSettingLauncher.toIntent();
    }
}
