package com.samsung.android.settings.general;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.credentials.CredentialManager;
import android.os.Bundle;
import android.os.LocaleList;
import android.os.UserHandle;
import android.provider.SearchIndexableResource;
import android.provider.Settings;
import android.util.Log;

import androidx.fragment.app.FragmentActivity;

import com.android.internal.app.LocalePicker;
import com.android.internal.app.LocaleStore;
import com.android.settings.DisplaySettings$$ExternalSyntheticOutline0;
import com.android.settings.R;
import com.android.settings.Utils;
import com.android.settings.inputmethod.PhysicalKeyboardPreferenceController;
import com.android.settings.inputmethod.SpellCheckerPreferenceController;
import com.android.settings.inputmethod.VirtualKeyboardPreferenceController;
import com.android.settings.regionalpreferences.RegionalPreferencesController;
import com.android.settings.search.BaseSearchIndexProvider;
import com.android.settingslib.core.lifecycle.Lifecycle;
import com.android.settingslib.search.Indexable$SearchIndexProvider;

import com.samsung.android.knox.net.vpn.KnoxVpnPolicyConstants;
import com.samsung.android.settings.Rune;
import com.samsung.android.settings.SettingsPreferenceFragmentLinkData;
import com.samsung.android.settings.analyzestorage.data.constant.FileType;
import com.samsung.android.settings.dynamicmenu.SecDynamicFragment;
import com.samsung.android.settings.gts.GtsGroup;
import com.samsung.android.settings.inputmethod.DefaultKeyboardPreferenceController;
import com.samsung.android.settings.knox.KnoxUtils;
import com.samsung.android.settings.logging.status.BaseStatusLoggingProvider;
import com.samsung.android.settings.logging.status.StatusData;
import com.samsung.android.settings.logging.status.StatusLogger$StatusLoggingProvider;
import com.samsung.android.settings.widget.SecRelativeLinkView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class GeneralDeviceSettings extends SecDynamicFragment {
    public static final Indexable$SearchIndexProvider SEARCH_INDEX_DATA_PROVIDER =
            new AnonymousClass1();
    public static final StatusLogger$StatusLoggingProvider STATUS_LOGGING_PROVIDER =
            new AnonymousClass2();
    public Context mContext;
    public SecRelativeLinkView mRelativeLinkView;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.settings.general.GeneralDeviceSettings$1, reason: invalid class name */
    public final class AnonymousClass1 extends BaseSearchIndexProvider {
        @Override // com.android.settings.search.BaseSearchIndexProvider
        public final List createPreferenceControllers(Context context) {
            return GeneralDeviceSettings.buildPreferenceControllers$6(context, null);
        }

        @Override // com.android.settings.search.BaseSearchIndexProvider
        public final HashMap getGtsResourceGroup() {
            HashMap hashMap = new HashMap();
            hashMap.put(
                    "mouse_and_trackpad_pref",
                    GtsGroup.GROUP_KEY_MOUSE_AND_TRACKPAD.getGroupName());
            return hashMap;
        }

        @Override // com.android.settings.search.BaseSearchIndexProvider,
                  // com.android.settingslib.search.Indexable$SearchIndexProvider
        public final List getXmlResourcesToIndex(Context context) {
            SearchIndexableResource searchIndexableResource = new SearchIndexableResource(context);
            searchIndexableResource.className = GeneralDeviceSettings.class.getName();
            searchIndexableResource.xmlResId = R.xml.sec_general_device_settings;
            return Arrays.asList(searchIndexableResource);
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.settings.general.GeneralDeviceSettings$2, reason: invalid class name */
    public final class AnonymousClass2 extends BaseStatusLoggingProvider {
        @Override // com.samsung.android.settings.logging.status.StatusLogger$StatusLoggingProvider
        public final List getStatusLoggingData(Context context) {
            ArrayList arrayList = new ArrayList();
            String locale = LocalePicker.getLocales().get(0).toString();
            String valueOf = String.valueOf(FileType.XDW);
            StatusData statusData = new StatusData();
            statusData.mStatusValue = locale;
            statusData.mStatusKey = valueOf;
            arrayList.add(statusData);
            ArrayList arrayList2 = new ArrayList();
            LocaleList locales = LocalePicker.getLocales();
            for (int i = 0; i < locales.size(); i++) {
                arrayList2.add(LocaleStore.getLocaleInfo(locales.get(i)));
            }
            int size = arrayList2.size();
            String valueOf2 = String.valueOf(8001);
            String valueOf3 = String.valueOf(size);
            StatusData statusData2 = new StatusData();
            statusData2.mStatusValue = valueOf3;
            statusData2.mStatusKey = valueOf2;
            arrayList.add(statusData2);
            String string =
                    Settings.Secure.getString(context.getContentResolver(), "default_input_method");
            String valueOf4 = String.valueOf(4701);
            StatusData statusData3 = new StatusData();
            statusData3.mStatusValue = string;
            statusData3.mStatusKey = valueOf4;
            arrayList.add(statusData3);
            return arrayList;
        }
    }

    public static List buildPreferenceControllers$6(Context context, Lifecycle lifecycle) {
        ArrayList arrayList = new ArrayList();
        arrayList.add(new SmartManagerFeaturePreferenceController(context));
        VirtualKeyboardPreferenceController virtualKeyboardPreferenceController =
                new VirtualKeyboardPreferenceController(context);
        PhysicalKeyboardPreferenceController physicalKeyboardPreferenceController =
                new PhysicalKeyboardPreferenceController(context, lifecycle);
        arrayList.add(virtualKeyboardPreferenceController);
        arrayList.add(physicalKeyboardPreferenceController);
        arrayList.add(new RegionalPreferencesController(context, "regional_preferences"));
        arrayList.add(new SpellCheckerPreferenceController(context));
        return arrayList;
    }

    @Override // com.android.settings.dashboard.DashboardFragment
    public final List createPreferenceControllers(Context context) {
        return buildPreferenceControllers$6(context, getSettingsLifecycle());
    }

    @Override // com.android.settings.dashboard.DashboardFragment
    /* renamed from: getLogTag */
    public final String getTAG() {
        return null;
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 4820;
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment
    public final int getPreferenceScreenResId() {
        return R.xml.sec_general_device_settings;
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.mContext = getActivity().getBaseContext();
        KnoxUtils.removeKnoxCustomSettingsHiddenItems(this);
    }

    @Override // com.samsung.android.settings.dynamicmenu.SecDynamicFragment,
              // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onResume() {
        SettingsPreferenceFragmentLinkData settingsPreferenceFragmentLinkData;
        SettingsPreferenceFragmentLinkData settingsPreferenceFragmentLinkData2;
        SettingsPreferenceFragmentLinkData settingsPreferenceFragmentLinkData3;
        super.onResume();
        FragmentActivity activity = getActivity();
        if (activity == null) {
            return;
        }
        activity.setTitle(R.string.general_device_management_title);
        getActivity();
        if (Rune.supportRelativeLink() && this.mRelativeLinkView == null) {
            this.mRelativeLinkView = new SecRelativeLinkView(getActivity());
            SettingsPreferenceFragmentLinkData settingsPreferenceFragmentLinkData4 = null;
            if (Rune.supportNavigationBar() && Rune.isNavigationBarEnabled(this.mContext)) {
                settingsPreferenceFragmentLinkData = new SettingsPreferenceFragmentLinkData();
                settingsPreferenceFragmentLinkData.callerMetric = 4820;
                settingsPreferenceFragmentLinkData.intent =
                        DisplaySettings$$ExternalSyntheticOutline0.m(
                                KnoxVpnPolicyConstants.ANDROID_SETTINGS_PKG,
                                "com.android.settings.Settings$NavigationBarSettingsActivity");
                settingsPreferenceFragmentLinkData.titleRes =
                        Rune.supportNavigationBarForHardKey()
                                ? R.string.navigationbar_gesture_category_name
                                : R.string.navigationbar_title;
                settingsPreferenceFragmentLinkData.topLevelKey = "top_level_display";
            } else {
                settingsPreferenceFragmentLinkData = null;
            }
            SettingsPreferenceFragmentLinkData settingsPreferenceFragmentLinkData5 =
                    new SettingsPreferenceFragmentLinkData();
            settingsPreferenceFragmentLinkData5.callerMetric = 4820;
            settingsPreferenceFragmentLinkData5.intent =
                    DisplaySettings$$ExternalSyntheticOutline0.m(
                            KnoxVpnPolicyConstants.ANDROID_SETTINGS_PKG,
                            "com.android.settings.Settings$PasswordsAndAutofillPickerActivity");
            settingsPreferenceFragmentLinkData5.titleRes =
                    CredentialManager.isServiceEnabled(this.mContext)
                            ? R.string.sec_spass_title_header
                            : R.string.passwords_and_autofill;
            settingsPreferenceFragmentLinkData5.topLevelKey = "top_level_security_and_privacy";
            if (!Rune.isSamsungDexMode(this.mContext)
                    || Utils.isDesktopStandaloneMode(this.mContext)) {
                settingsPreferenceFragmentLinkData2 = new SettingsPreferenceFragmentLinkData();
                settingsPreferenceFragmentLinkData2.callerMetric = 4820;
                Intent intent = new Intent();
                intent.setAction("com.samsung.settings.ScreenTimeout");
                settingsPreferenceFragmentLinkData2.intent = intent;
                settingsPreferenceFragmentLinkData2.titleRes = R.string.screen_timeout;
                settingsPreferenceFragmentLinkData2.topLevelKey = "top_level_display";
            } else {
                settingsPreferenceFragmentLinkData2 = null;
            }
            if (UserHandle.myUserId() != 0 || Rune.isSamsungDexMode(this.mContext)) {
                settingsPreferenceFragmentLinkData3 = null;
            } else {
                settingsPreferenceFragmentLinkData3 = new SettingsPreferenceFragmentLinkData();
                settingsPreferenceFragmentLinkData3.callerMetric = 4820;
                Intent intent2 = new Intent("com.samsung.settings.FontPreview");
                settingsPreferenceFragmentLinkData3.intent = intent2;
                intent2.putExtra("fromRelativeLink", true);
                settingsPreferenceFragmentLinkData3.topLevelKey = "top_level_display";
                settingsPreferenceFragmentLinkData3.titleRes =
                        R.string.sec_font_size_and_style_title;
            }
            if (Utils.hasPackage(getActivity(), "com.sec.android.easyMover")
                    && !Rune.isSamsungDexMode(this.mContext)) {
                settingsPreferenceFragmentLinkData4 = new SettingsPreferenceFragmentLinkData();
                settingsPreferenceFragmentLinkData4.flowId = "95015";
                settingsPreferenceFragmentLinkData4.callerMetric = 4820;
                try {
                    Intent launchIntentForPackage =
                            getPackageManager()
                                    .getLaunchIntentForPackage("com.sec.android.easyMover");
                    if (launchIntentForPackage != null) {
                        settingsPreferenceFragmentLinkData4.intent = launchIntentForPackage;
                    } else {
                        Intent intent3 =
                                new Intent("com.sec.android.easyMover.LAUNCH_SMART_SWITCH");
                        intent3.addFlags(268435456);
                        settingsPreferenceFragmentLinkData4.intent = intent3;
                    }
                } catch (ActivityNotFoundException unused) {
                    Log.e("GeneralDeviceSettings", "not found activity");
                }
                settingsPreferenceFragmentLinkData4.titleRes =
                        R.string.sec_bring_data_from_old_device;
            }
            if (settingsPreferenceFragmentLinkData != null) {
                this.mRelativeLinkView.pushLinkData(settingsPreferenceFragmentLinkData);
            }
            this.mRelativeLinkView.pushLinkData(settingsPreferenceFragmentLinkData5);
            if (settingsPreferenceFragmentLinkData2 != null) {
                this.mRelativeLinkView.pushLinkData(settingsPreferenceFragmentLinkData2);
            }
            if (settingsPreferenceFragmentLinkData3 != null) {
                this.mRelativeLinkView.pushLinkData(settingsPreferenceFragmentLinkData3);
            }
            if (settingsPreferenceFragmentLinkData4 != null) {
                this.mRelativeLinkView.pushLinkData(settingsPreferenceFragmentLinkData4);
            }
            this.mRelativeLinkView.create(this);
        }
        ((DefaultKeyboardPreferenceController) use(DefaultKeyboardPreferenceController.class))
                .updateInputMethodPreferenceViews();
    }
}
