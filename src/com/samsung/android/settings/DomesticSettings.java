package com.samsung.android.settings;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.SemSystemProperties;
import android.provider.SearchIndexableData;
import android.provider.SearchIndexableResource;
import android.provider.Settings;

import androidx.preference.PreferenceScreen;

import com.android.settings.R;
import com.android.settings.SettingsPreferenceFragment;
import com.android.settings.Utils;
import com.android.settings.search.BaseSearchIndexProvider;
import com.android.settingslib.search.SearchIndexableRaw;

import com.samsung.android.feature.SemCscFeature;
import com.samsung.android.settings.account.AccountUtils;
import com.samsung.android.settings.connection.ConnectionsUtils;
import com.samsung.android.settings.connection.GigaLteSettings;
import com.samsung.android.settings.inputmethod.TouchPadGestureSettingsController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class DomesticSettings extends SettingsPreferenceFragment {
    public static final BaseSearchIndexProvider SEARCH_INDEX_DATA_PROVIDER = new AnonymousClass1();
    public PreferenceScreen mTwoPhonePreference;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.settings.DomesticSettings$1, reason: invalid class name */
    public final class AnonymousClass1 extends BaseSearchIndexProvider {
        @Override // com.android.settings.search.BaseSearchIndexProvider,
                  // com.android.settingslib.search.Indexable$SearchIndexProvider
        public final List getNonIndexableKeys(Context context) {
            List nonIndexableKeys = super.getNonIndexableKeys(context);
            if (!SemCscFeature.getInstance()
                    .getBoolean("CscFeature_VoiceCall_SupportCallerRingBackTone")) {
                ((ArrayList) nonIndexableKeys).add("v_coloring");
            }
            if (!Rune.isDomesticKTTModel() || !Rune.isOllehSettingsSupport(context)) {
                ((ArrayList) nonIndexableKeys).add("giga_lte");
            } else if (ActivityManager.getCurrentUser() != 0
                    || !ConnectionsUtils.isSupportMptcp()) {
                ((ArrayList) nonIndexableKeys).add("giga_lte");
            }
            if (Rune.supportDesktopMode() && Rune.isSamsungDexMode(context)) {
                ((ArrayList) nonIndexableKeys).add("two_phone");
            }
            if (!AccountUtils.SupportTwoPhone) {
                ((ArrayList) nonIndexableKeys).add("two_phone");
            }
            Intent intent = new Intent();
            intent.setAction(
                    "com.samsung.android.game.gametools.action.KT_PLAY_GAME_FROM_SETTINGS");
            if (!Utils.isIntentAvailable(context, intent)) {
                ((ArrayList) nonIndexableKeys).add("kt_play_game");
            }
            if (!Utils.hasPackage(context, "com.kt.olleh.servicemenu")) {
                ((ArrayList) nonIndexableKeys).add("kt_wifi_settings");
            }
            return nonIndexableKeys;
        }

        @Override // com.android.settings.search.BaseSearchIndexProvider,
                  // com.android.settingslib.search.Indexable$SearchIndexProvider
        public final List getRawDataToIndex(Context context) {
            ArrayList arrayList = new ArrayList();
            Resources resources = context.getResources();
            if (ActivityManager.getCurrentUser() == 0
                    && ConnectionsUtils.isSupportMptcp()
                    && SemSystemProperties.getInt(
                                    TouchPadGestureSettingsController.FIRST_API_LEVEL, 0)
                            < 30
                    && !ConnectionsUtils.isSupport5GConcept()) {
                SearchIndexableRaw searchIndexableRaw = new SearchIndexableRaw(context);
                ((SearchIndexableData) searchIndexableRaw).key = "giga_lte";
                searchIndexableRaw.screenTitle = resources.getString(R.string.olleh_setting);
                ((SearchIndexableData) searchIndexableRaw).className =
                        GigaLteSettings.class.getName();
                searchIndexableRaw.title = resources.getString(R.string.giga_lte_title);
                searchIndexableRaw.keywords =
                        Utils.getKeywordForSearch(context, R.string.giga_lte_title);
                arrayList.add(searchIndexableRaw);
            }
            return arrayList;
        }

        @Override // com.android.settings.search.BaseSearchIndexProvider,
                  // com.android.settingslib.search.Indexable$SearchIndexProvider
        public final List getXmlResourcesToIndex(Context context) {
            SearchIndexableResource searchIndexableResource = new SearchIndexableResource(context);
            searchIndexableResource.className = DomesticSettings.class.getName();
            searchIndexableResource.xmlResId = R.xml.sec_domestic_settings;
            return Arrays.asList(searchIndexableResource);
        }

        @Override // com.android.settings.search.BaseSearchIndexProvider
        public final boolean isPageSearchEnabled(Context context) {
            return Rune.isDomesticKTTModel();
        }
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 0;
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        addPreferencesFromResource(R.xml.sec_domestic_settings);
        if (!SemCscFeature.getInstance()
                .getBoolean("CscFeature_VoiceCall_SupportCallerRingBackTone")) {
            removePreference("v_coloring");
        }
        if (ActivityManager.getCurrentUser() == 0
                && ConnectionsUtils.isSupportMptcp()
                && SemSystemProperties.getInt(TouchPadGestureSettingsController.FIRST_API_LEVEL, 0)
                        < 30) {
            getContext();
            if (!ConnectionsUtils.isSupport5GConcept()) {
                PreferenceScreen preferenceScreen = new PreferenceScreen(getPrefContext(), null);
                preferenceScreen.setKey("giga_lte");
                preferenceScreen.setOrder(1000);
                preferenceScreen.setFragment(GigaLteSettings.class.getName());
                preferenceScreen.setTitle(R.string.giga_lte_title);
                getPreferenceScreen().addPreference(preferenceScreen);
            }
        }
        Context context = getContext();
        boolean z = AccountUtils.SupportTwoPhone;
        Intent intent = new Intent();
        intent.setAction("com.samsung.android.game.gametools.action.KT_PLAY_GAME_FROM_SETTINGS");
        if (!Utils.isIntentAvailable(context, intent)) {
            removePreference("kt_play_game");
        }
        this.mTwoPhonePreference = (PreferenceScreen) findPreference("two_phone");
        if (!Utils.hasPackage(getContext(), "com.kt.olleh.servicemenu")) {
            removePreference("kt_wifi_settings");
        }
        if (AccountUtils.SupportTwoPhone) {
            return;
        }
        removePreference("two_phone");
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onResume() {
        super.onResume();
        PreferenceScreen preferenceScreen = (PreferenceScreen) findPreference("giga_lte");
        boolean z = false;
        if (preferenceScreen != null) {
            preferenceScreen.setSummary(
                    Settings.System.getInt(getContentResolver(), "mptcp_value", 0) != 0
                            ? R.string.switch_on_text
                            : R.string.switch_off_text);
        }
        if (Rune.supportDesktopMode() && Rune.isSamsungDexMode(getActivity())) {
            z = true;
        }
        PreferenceScreen preferenceScreen2 = this.mTwoPhonePreference;
        if (preferenceScreen2 != null) {
            preferenceScreen2.setEnabled(!z);
        }
    }
}
