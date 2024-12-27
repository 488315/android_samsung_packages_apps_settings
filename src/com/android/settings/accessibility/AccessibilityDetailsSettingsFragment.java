package com.android.settings.accessibility;

import android.accessibilityservice.AccessibilityServiceInfo;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.pm.ResolveInfo;
import android.content.pm.ServiceInfo;
import android.os.Bundle;
import android.os.UserHandle;
import android.text.TextUtils;
import android.util.Log;
import android.view.accessibility.AccessibilityManager;

import androidx.fragment.app.FragmentActivity;

import com.android.internal.accessibility.AccessibilityShortcutController;
import com.android.internal.annotations.VisibleForTesting;
import com.android.settings.R;
import com.android.settings.core.InstrumentedFragment;
import com.android.settings.core.SubSettingLauncher;
import com.android.settings.overlay.FeatureFactoryImpl;
import com.android.settingslib.RestrictedLockUtilsInternal;
import com.android.settingslib.accessibility.AccessibilityUtils;

import com.samsung.android.knox.ucm.core.UniversalCredentialUtil;
import com.samsung.android.settings.accessibility.AccessibilityConstant;
import com.samsung.android.settings.accessibility.home.TalkBackPreferenceFragment;

import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class AccessibilityDetailsSettingsFragment extends InstrumentedFragment {

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class LaunchFragmentArguments {
        public final Bundle mArguments = null;
        public final String mDestination;

        public LaunchFragmentArguments(String str) {
            this.mDestination = str;
        }
    }

    public final Bundle buildArguments(AccessibilityServiceInfo accessibilityServiceInfo) {
        ResolveInfo resolveInfo = accessibilityServiceInfo.getResolveInfo();
        String charSequence = resolveInfo.loadLabel(getActivity().getPackageManager()).toString();
        ServiceInfo serviceInfo = resolveInfo.serviceInfo;
        String str = serviceInfo.packageName;
        ComponentName componentName = new ComponentName(str, serviceInfo.name);
        boolean contains =
                AccessibilityUtils.getEnabledServicesFromSettings(getActivity())
                        .contains(componentName);
        String loadDescription =
                accessibilityServiceInfo.loadDescription(getActivity().getPackageManager());
        if (contains && accessibilityServiceInfo.crashed) {
            loadDescription = getString(R.string.accessibility_description_state_stopped);
        }
        Bundle bundle = new Bundle();
        bundle.putString("preference_key", componentName.flattenToString());
        bundle.putBoolean("checked", contains);
        bundle.putString(UniversalCredentialUtil.AGENT_TITLE, charSequence);
        bundle.putParcelable("resolve_info", resolveInfo);
        bundle.putString(UniversalCredentialUtil.AGENT_SUMMARY, loadDescription);
        String settingsActivityName = accessibilityServiceInfo.getSettingsActivityName();
        if (!TextUtils.isEmpty(settingsActivityName)) {
            bundle.putString(
                    "settings_title", getString(R.string.accessibility_menu_item_settings));
            bundle.putString(
                    "settings_component_name",
                    new ComponentName(str, settingsActivityName).flattenToString());
        }
        String tileServiceName = accessibilityServiceInfo.getTileServiceName();
        if (!TextUtils.isEmpty(tileServiceName)) {
            bundle.putString(
                    "tile_service_component_name",
                    new ComponentName(str, tileServiceName).flattenToString());
        }
        FeatureFactoryImpl featureFactoryImpl = FeatureFactoryImpl._factory;
        if (featureFactoryImpl == null) {
            throw new UnsupportedOperationException("No feature factory configured");
        }
        ((AccessibilityMetricsFeatureProviderImpl)
                        featureFactoryImpl.accessibilityMetricsFeatureProvider$delegate.getValue())
                .getClass();
        bundle.putInt("metrics_category", 4);
        bundle.putParcelable("component_name", componentName);
        bundle.putInt("animated_image_res", accessibilityServiceInfo.getAnimatedImageRes());
        bundle.putString(
                "html_description",
                accessibilityServiceInfo.loadHtmlDescription(getActivity().getPackageManager()));
        bundle.putCharSequence(
                "intro", accessibilityServiceInfo.loadIntro(getActivity().getPackageManager()));
        bundle.putLong(
                "start_time_to_log_a11y_tool",
                getActivity().getIntent().getLongExtra("start_time_to_log_a11y_tool", 0L));
        return bundle;
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 1682;
    }

    @VisibleForTesting
    public boolean isServiceAllowed(int i, String str) {
        List permittedAccessibilityServices =
                ((DevicePolicyManager) getContext().getSystemService(DevicePolicyManager.class))
                        .getPermittedAccessibilityServices(UserHandle.myUserId());
        if (permittedAccessibilityServices == null
                || permittedAccessibilityServices.contains(str)) {
            return !RestrictedLockUtilsInternal.isEnhancedConfirmationRestricted(getContext(), str);
        }
        return false;
    }

    @Override // com.android.settingslib.core.lifecycle.ObservableFragment,
              // androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        AccessibilityServiceInfo accessibilityServiceInfo;
        super.onCreate(bundle);
        String stringExtra =
                getActivity().getIntent().getStringExtra("android.intent.extra.COMPONENT_NAME");
        if (stringExtra == null) {
            Log.w(
                    "A11yDetailsSettings",
                    "Open accessibility services list due to no component name.");
            openSubSettings(null, AccessibilitySettings.class.getName());
            FragmentActivity activity = getActivity();
            if (activity == null) {
                return;
            }
            activity.finish();
            return;
        }
        ComponentName unflattenFromString = ComponentName.unflattenFromString(stringExtra);
        LaunchFragmentArguments launchFragmentArguments =
                AccessibilityShortcutController.MAGNIFICATION_COMPONENT_NAME.equals(
                                unflattenFromString)
                        ? new LaunchFragmentArguments(
                                ToggleScreenMagnificationPreferenceFragment.class.getName())
                        : AccessibilityShortcutController.ACCESSIBILITY_BUTTON_COMPONENT_NAME
                                        .equals(unflattenFromString)
                                ? new LaunchFragmentArguments(
                                        AccessibilityButtonFragment.class.getName())
                                : AccessibilityShortcutController
                                                .ACCESSIBILITY_HEARING_AIDS_COMPONENT_NAME
                                                .equals(unflattenFromString)
                                        ? new LaunchFragmentArguments(
                                                AccessibilityHearingAidsFragment.class.getName())
                                        : null;
        if (launchFragmentArguments != null) {
            openSubSettings(
                    launchFragmentArguments.mArguments, launchFragmentArguments.mDestination);
            FragmentActivity activity2 = getActivity();
            if (activity2 == null) {
                return;
            }
            activity2.finish();
            return;
        }
        if (unflattenFromString != null) {
            List<AccessibilityServiceInfo> installedAccessibilityServiceList =
                    AccessibilityManager.getInstance(getActivity())
                            .getInstalledAccessibilityServiceList();
            int size = installedAccessibilityServiceList.size();
            for (int i = 0; i < size; i++) {
                accessibilityServiceInfo = installedAccessibilityServiceList.get(i);
                ResolveInfo resolveInfo = accessibilityServiceInfo.getResolveInfo();
                if (unflattenFromString.getPackageName().equals(resolveInfo.serviceInfo.packageName)
                        && unflattenFromString
                                .getClassName()
                                .equals(resolveInfo.serviceInfo.name)) {
                    break;
                }
            }
        }
        accessibilityServiceInfo = null;
        if (accessibilityServiceInfo == null) {
            Log.w(
                    "A11yDetailsSettings",
                    "openAccessibilityDetailsSettingsAndFinish : invalid component name.");
        } else {
            if (isServiceAllowed(
                    accessibilityServiceInfo.getResolveInfo().serviceInfo.applicationInfo.uid,
                    unflattenFromString.getPackageName())) {
                if (AccessibilityConstant.COMPONENT_NAME_SAMSUNG_TALKBACK.equals(
                        unflattenFromString)) {
                    openSubSettings(
                            buildArguments(accessibilityServiceInfo),
                            TalkBackPreferenceFragment.class.getName());
                } else {
                    openSubSettings(
                            buildArguments(accessibilityServiceInfo),
                            ToggleAccessibilityServicePreferenceFragment.class.getName());
                }
                FragmentActivity activity3 = getActivity();
                if (activity3 == null) {
                    return;
                }
                activity3.finish();
                return;
            }
            Log.w(
                    "A11yDetailsSettings",
                    "openAccessibilityDetailsSettingsAndFinish: target accessibility service"
                        + " isprohibited by Device Admin or App Op.");
        }
        openSubSettings(null, AccessibilitySettings.class.getName());
        FragmentActivity activity4 = getActivity();
        if (activity4 == null) {
            return;
        }
        activity4.finish();
    }

    public final void openSubSettings(Bundle bundle, String str) {
        SubSettingLauncher subSettingLauncher = new SubSettingLauncher(getActivity());
        SubSettingLauncher.LaunchRequest launchRequest = subSettingLauncher.mLaunchRequest;
        launchRequest.mDestinationName = str;
        launchRequest.mSourceMetricsCategory = 1682;
        launchRequest.mArguments = bundle;
        subSettingLauncher.launch();
    }
}
