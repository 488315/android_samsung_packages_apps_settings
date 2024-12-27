package com.samsung.android.settings.usefulfeature;

import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.Signature;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.UserHandle;
import android.provider.SearchIndexableData;
import android.util.Log;

import androidx.fragment.app.DialogFragment$$ExternalSyntheticOutline0;
import androidx.picker.adapter.AbsAdapter$1$$ExternalSyntheticOutline0;
import androidx.preference.Preference;

import com.android.settings.DisplaySettings$$ExternalSyntheticOutline0;
import com.android.settings.R;
import com.android.settings.Utils;
import com.android.settings.core.OnActivityResultListener;
import com.android.settings.search.BaseSearchIndexProvider;
import com.android.settingslib.search.SearchIndexableRaw;

import com.samsung.android.feature.SemFloatingFeature;
import com.samsung.android.knox.custom.SettingsManager;
import com.samsung.android.knox.net.vpn.KnoxVpnPolicyConstants;
import com.samsung.android.settings.Rune;
import com.samsung.android.settings.SettingsPreferenceFragmentLinkData;
import com.samsung.android.settings.accessibility.AccessibilityRune;
import com.samsung.android.settings.display.SecDisplayUtils;
import com.samsung.android.settings.dynamicmenu.SecDynamicFragment;
import com.samsung.android.settings.gts.GtsGroup;
import com.samsung.android.settings.usefulfeature.applock.AppLockPreferenceController;
import com.samsung.android.settings.usefulfeature.functionkey.FunctionKeySettings;
import com.samsung.android.settings.usefulfeature.intelligenceservice.IntelligenceServiceForWorkPreferenceController;
import com.samsung.android.settings.usefulfeature.videoenhancer.VideoEnhancerPreferenceController;
import com.samsung.android.settings.widget.SecRelativeLinkView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public class Usefulfeature extends SecDynamicFragment implements OnActivityResultListener {
    public static final BaseSearchIndexProvider SEARCH_INDEX_DATA_PROVIDER;
    public SecRelativeLinkView mRelativeLinkView;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.settings.usefulfeature.Usefulfeature$1, reason: invalid class name */
    public final class AnonymousClass1 extends BaseSearchIndexProvider {
        @Override // com.android.settings.search.BaseSearchIndexProvider
        public final List createPreferenceControllers(Context context) {
            BaseSearchIndexProvider baseSearchIndexProvider =
                    Usefulfeature.SEARCH_INDEX_DATA_PROVIDER;
            ArrayList arrayList = new ArrayList();
            arrayList.add(new VideoEnhancerPreferenceController(context, (Usefulfeature) null));
            arrayList.add(new AppLockPreferenceController(context, (Usefulfeature) null));
            return arrayList;
        }

        @Override // com.android.settings.search.BaseSearchIndexProvider
        public final HashMap getGtsResourceGroup() {
            HashMap hashMap = new HashMap();
            GtsGroup gtsGroup = GtsGroup.GROUP_KEY_ADVANCED;
            hashMap.put("onehand_operation_settings", gtsGroup.getGroupName());
            hashMap.put(VideoEnhancerPreferenceController.KEY_HDR_EFFECT, gtsGroup.getGroupName());
            hashMap.put("function_key_setting", gtsGroup.getGroupName());
            return hashMap;
        }

        @Override // com.android.settings.search.BaseSearchIndexProvider,
                  // com.android.settingslib.search.Indexable$SearchIndexProvider
        public final List getNonIndexableKeys(Context context) {
            List nonIndexableKeys = super.getNonIndexableKeys(context);
            SettingsManager settingsManager = SettingsManager.getInstance();
            if (((settingsManager != null ? settingsManager.getSettingsHiddenState() : 0) & 8)
                    != 0) {
                ((ArrayList) nonIndexableKeys).add("multi_window_setting");
            }
            StringBuilder sb = Utils.sBuilder;
            if (Rune.SUPPORT_SMARTMANAGER_CN) {
                UserHandle.myUserId();
            }
            return nonIndexableKeys;
        }

        @Override // com.android.settings.search.BaseSearchIndexProvider,
                  // com.android.settingslib.search.Indexable$SearchIndexProvider
        public final List getRawDataToIndex(Context context) {
            ArrayList arrayList = new ArrayList();
            StringBuilder sb = Utils.sBuilder;
            if (Rune.SUPPORT_SMARTMANAGER_CN && UserHandle.myUserId() == 0) {
                SearchIndexableRaw searchIndexableRaw = new SearchIndexableRaw(context);
                ((SearchIndexableData) searchIndexableRaw).key = "key_applock";
                searchIndexableRaw.screenTitle = context.getString(R.string.useful_feature_title);
                searchIndexableRaw.title = String.valueOf(R.string.applock_app_name);
                arrayList.add(searchIndexableRaw);
            }
            BaseSearchIndexProvider baseSearchIndexProvider =
                    Usefulfeature.SEARCH_INDEX_DATA_PROVIDER;
            SearchIndexableRaw searchIndexableRaw2 = new SearchIndexableRaw(context);
            searchIndexableRaw2.title = context.getString(R.string.sec_function_key_setting_title);
            searchIndexableRaw2.screenTitle = context.getString(R.string.useful_feature_title);
            ((SearchIndexableData) searchIndexableRaw2).className =
                    FunctionKeySettings.class.getName();
            if (Utils.hasPackage(context, "com.samsung.android.aliveprivacy")) {
                searchIndexableRaw2.keywords =
                        context.getString(R.string.keywords_function_key_setting_secure_folder);
            } else {
                searchIndexableRaw2.keywords =
                        context.getString(R.string.keywords_function_key_setting);
            }
            ((SearchIndexableData) searchIndexableRaw2).key = "function_key_setting";
            arrayList.add(searchIndexableRaw2);
            return arrayList;
        }
    }

    static {
        SemFloatingFeature.getInstance()
                .getString("SEC_FLOATING_FEATURE_FRAMEWORK_CONFIG_AOD_ITEM")
                .contains("nightclock");
        SEARCH_INDEX_DATA_PROVIDER = new AnonymousClass1(R.xml.sec_useful_feature);
    }

    @Override // com.android.settings.dashboard.DashboardFragment
    public final List createPreferenceControllers(Context context) {
        ArrayList arrayList = new ArrayList();
        arrayList.add(new VideoEnhancerPreferenceController(context, this));
        arrayList.add(new AppLockPreferenceController(context, this));
        return arrayList;
    }

    @Override // com.android.settings.dashboard.DashboardFragment
    /* renamed from: getLogTag */
    public final String getTAG() {
        return "Usefulfeature";
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 4350;
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment
    public final int getPreferenceScreenResId() {
        return R.xml.sec_useful_feature;
    }

    @Override // com.samsung.android.settings.dynamicmenu.SecDynamicFragment,
              // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onAttach(Context context) {
        super.onAttach(context);
        ((IntelligenceServiceForWorkPreferenceController)
                        use(IntelligenceServiceForWorkPreferenceController.class))
                .init(this);
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment,
              // android.content.ComponentCallbacks
    public final void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        String str = Rune.COMMON_CONFIG_PACKAGE_NAME_SMART_MANAGER;
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        getContext();
        Signature[] signatureArr = SecDisplayUtils.SIGNATURES;
        String str = Rune.COMMON_CONFIG_PACKAGE_NAME_SMART_MANAGER;
        setAnimationAllowed(true);
        SettingsManager settingsManager = SettingsManager.getInstance();
        if (((settingsManager != null ? settingsManager.getSettingsHiddenState() : 0) & 8) != 0) {
            removePreference("multi_window_setting");
        }
    }

    @Override // androidx.fragment.app.Fragment
    public final void onMultiWindowModeChanged(boolean z) {
        super.onMultiWindowModeChanged(z);
        Log.d("Usefulfeature", "onMultiWindowModeChanged : " + z);
        ((VideoEnhancerPreferenceController) use(VideoEnhancerPreferenceController.class))
                .updatePreference();
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat,
              // androidx.preference.PreferenceManager.OnPreferenceTreeClickListener
    public final boolean onPreferenceTreeClick(Preference preference) {
        Log.d("Usefulfeature", "onPreferenceTreeClick: ");
        DialogFragment$$ExternalSyntheticOutline0.m(
                "onPreferenceTreeClick: key = ", preference.getKey(), "Usefulfeature");
        return super.onPreferenceTreeClick(preference);
    }

    @Override // com.samsung.android.settings.dynamicmenu.SecDynamicFragment,
              // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onResume() {
        int i;
        super.onResume();
        getActivity();
        if (Rune.supportRelativeLink() && this.mRelativeLinkView == null) {
            this.mRelativeLinkView = new SecRelativeLinkView(getActivity());
            if (UserHandle.myUserId() == 0) {
                SettingsPreferenceFragmentLinkData settingsPreferenceFragmentLinkData =
                        new SettingsPreferenceFragmentLinkData();
                settingsPreferenceFragmentLinkData.flowId = "95012";
                settingsPreferenceFragmentLinkData.callerMetric = 4350;
                settingsPreferenceFragmentLinkData.intent =
                        DisplaySettings$$ExternalSyntheticOutline0.m(
                                KnoxVpnPolicyConstants.ANDROID_SETTINGS_PKG,
                                "com.android.settings.Settings$FloatingIconsNotificationSettingsActivity");
                settingsPreferenceFragmentLinkData.titleRes =
                        R.string.notification_floating_icons_title;
                settingsPreferenceFragmentLinkData.topLevelKey = "top_level_notifications";
                this.mRelativeLinkView.pushLinkData(settingsPreferenceFragmentLinkData);
                i = 1;
            } else {
                i = 0;
            }
            if (UserHandle.myUserId() == 0) {
                SettingsPreferenceFragmentLinkData settingsPreferenceFragmentLinkData2 =
                        new SettingsPreferenceFragmentLinkData();
                settingsPreferenceFragmentLinkData2.fragment =
                        "com.samsung.android.settings.accessibility.vision.VisibilityEnhancementsFragment";
                settingsPreferenceFragmentLinkData2.flowId = "95013";
                settingsPreferenceFragmentLinkData2.callerMetric = 4350;
                settingsPreferenceFragmentLinkData2.extras =
                        AbsAdapter$1$$ExternalSyntheticOutline0.m(
                                ":settings:fragment_args_key", "toggle_remove_animations");
                settingsPreferenceFragmentLinkData2.topLevelKey = "top_level_accessibility";
                if (AccessibilityRune.isAtLeastOneUI_6_1()) {
                    settingsPreferenceFragmentLinkData2.titleRes = R.string.reduce_animations_title;
                } else {
                    settingsPreferenceFragmentLinkData2.titleRes =
                            R.string.accessibility_disable_animations;
                }
                this.mRelativeLinkView.pushLinkData(settingsPreferenceFragmentLinkData2);
                i++;
            }
            if (Utils.isIntentAvailable(
                            getContext(),
                            "com.samsung.android.app.telephonyui.action.OPEN_CALL_SETTINGS")
                    && Utils.hasPackage(getContext(), "com.samsung.android.dialer")
                    && UsefulfeatureUtils.isEnabledComponent(
                            getContext(),
                            new ComponentName(
                                    "com.samsung.android.dialer",
                                    "com.samsung.android.dialer.DialtactsActivity"))) {
                SettingsPreferenceFragmentLinkData settingsPreferenceFragmentLinkData3 =
                        new SettingsPreferenceFragmentLinkData();
                settingsPreferenceFragmentLinkData3.flowId = "95014";
                settingsPreferenceFragmentLinkData3.callerMetric = 4350;
                Intent intent =
                        new Intent("com.samsung.android.app.telephonyui.action.OPEN_CALL_SETTINGS");
                intent.putExtra("root_key", "SWIPE_TO_CALL_OR_TEXT");
                settingsPreferenceFragmentLinkData3.intent = intent;
                settingsPreferenceFragmentLinkData3.titleRes =
                        R.string.sec_relative_link_swipe_to_call;
                this.mRelativeLinkView.pushLinkData(settingsPreferenceFragmentLinkData3);
                i++;
            }
            if (Utils.hasPackage(getActivity(), "com.sec.android.easyMover")) {
                SettingsPreferenceFragmentLinkData settingsPreferenceFragmentLinkData4 =
                        new SettingsPreferenceFragmentLinkData();
                settingsPreferenceFragmentLinkData4.flowId = "95015";
                settingsPreferenceFragmentLinkData4.callerMetric = 4350;
                try {
                    Intent launchIntentForPackage =
                            getPackageManager()
                                    .getLaunchIntentForPackage("com.sec.android.easyMover");
                    if (launchIntentForPackage != null) {
                        settingsPreferenceFragmentLinkData4.intent = launchIntentForPackage;
                    } else {
                        Intent intent2 =
                                new Intent("com.sec.android.easyMover.LAUNCH_SMART_SWITCH");
                        intent2.addFlags(268435456);
                        settingsPreferenceFragmentLinkData4.intent = intent2;
                    }
                    settingsPreferenceFragmentLinkData4.titleRes =
                            R.string.sec_bring_data_from_old_device;
                    if (!Rune.supportDesktopMode() || !Rune.isSamsungDexMode(getContext())) {
                        this.mRelativeLinkView.pushLinkData(settingsPreferenceFragmentLinkData4);
                        i++;
                    }
                } catch (ActivityNotFoundException e) {
                    e.printStackTrace();
                    Log.e("Usefulfeature", "not found activity");
                }
            }
            if (Utils.isEasyModeEnabled(getContext())) {
                SettingsPreferenceFragmentLinkData settingsPreferenceFragmentLinkData5 =
                        new SettingsPreferenceFragmentLinkData();
                settingsPreferenceFragmentLinkData5.flowId = "9012";
                settingsPreferenceFragmentLinkData5.callerMetric = 4350;
                settingsPreferenceFragmentLinkData5.intent =
                        DisplaySettings$$ExternalSyntheticOutline0.m(
                                KnoxVpnPolicyConstants.ANDROID_SETTINGS_PKG,
                                "com.android.settings.Settings$EasyModeAppActivity");
                settingsPreferenceFragmentLinkData5.titleRes = R.string.easy_mode;
                settingsPreferenceFragmentLinkData5.topLevelKey = "top_level_display";
                boolean z =
                        getContext().getResources().getConfiguration().semDisplayDeviceType == 5;
                if ((!Rune.supportDesktopMode() || !Rune.isSamsungDexMode(getContext())) && !z) {
                    this.mRelativeLinkView.pushLinkData(settingsPreferenceFragmentLinkData5);
                    i++;
                }
            }
            if (i > 0) {
                this.mRelativeLinkView.create(this);
            }
        }
    }
}
