package com.samsung.android.settings.notification.brief;

import android.content.Context;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;

import com.android.settings.R;
import com.android.settings.Utils;
import com.android.settings.accessibility.AccessibilitySettings$$ExternalSyntheticOutline0;
import com.android.settings.dashboard.DashboardFragment;
import com.android.settings.search.BaseSearchIndexProvider;
import com.android.settingslib.core.AbstractPreferenceController;

import com.samsung.android.feature.SemFloatingFeature;
import com.samsung.android.settings.Rune;
import com.samsung.android.settings.gts.GtsGroup;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class BriefPopUpSettings extends DashboardFragment {
    public static final BaseSearchIndexProvider SEARCH_INDEX_DATA_PROVIDER =
            new AnonymousClass1(R.xml.brief_popup_main_settings);
    public Handler mHandler;
    public SettingsObserver mSettingsObserver;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.settings.notification.brief.BriefPopUpSettings$1, reason: invalid class name */
    public final class AnonymousClass1 extends BaseSearchIndexProvider {
        @Override // com.android.settings.search.BaseSearchIndexProvider
        public final List createPreferenceControllers(Context context) {
            BaseSearchIndexProvider baseSearchIndexProvider =
                    BriefPopUpSettings.SEARCH_INDEX_DATA_PROVIDER;
            ArrayList arrayList = new ArrayList();
            arrayList.add(new BriefPopupListController(context, "apps_to_view_as_brief_popup"));
            arrayList.add(new BriefPopUpStyleController(context, "brief_popup_style_settings"));
            arrayList.add(
                    new BriefPopUpKeywordColorController(
                            context, "brief_popup_keyword_color_settings"));
            arrayList.add(
                    new BriefPopUpConditionController(context, "brief_popup_show_even_screen_off"));
            arrayList.add(
                    new BriefPopUpStyleDescriptionController(
                            context, "brief_popup_style_settings_description"));
            arrayList.add(
                    new DetailedPopUpStyleDescriptionController(
                            context, "detailed_popup_style_description"));
            return arrayList;
        }

        @Override // com.android.settings.search.BaseSearchIndexProvider
        public final HashMap getGtsResourceGroup() {
            HashMap hashMap = new HashMap();
            hashMap.put(
                    "notification_popup_style", GtsGroup.GROUP_KEY_NOTIFICATIONS.getGroupName());
            return hashMap;
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // com.android.settings.search.BaseSearchIndexProvider,
                  // com.android.settingslib.search.Indexable$SearchIndexProvider
        public final List getNonIndexableKeys(Context context) {
            List nonIndexableKeys = super.getNonIndexableKeys(context);
            byte b =
                    Settings.System.getInt(
                                    context.getContentResolver(),
                                    "edge_lighting",
                                    !Rune.isEdgeLightingDefaultOff() ? 1 : 0)
                            == 0;
            if (Utils.isMinimalBatteryUseEnabled(context)
                    || !SemFloatingFeature.getInstance()
                            .getBoolean(
                                    "SEC_FLOATING_FEATURE_SYSTEMUI_SUPPORT_BRIEF_NOTIFICATION",
                                    true)
                    || ((Rune.isSamsungDexMode(context) && Utils.isDesktopDualMode(context))
                            || b != false)) {
                AccessibilitySettings$$ExternalSyntheticOutline0.m(
                        (ArrayList) nonIndexableKeys,
                        "apps_to_view_as_brief_popup",
                        "brief_popup_style_settings",
                        "brief_popup_keyword_color_settings",
                        "brief_popup_show_even_screen_off");
            }
            if (b == false) {
                ((ArrayList) nonIndexableKeys).add("detailed_popup_style_description");
            }
            if (Settings.System.getInt(context.getContentResolver(), "remove_animations", 0) == 0) {
                ((ArrayList) nonIndexableKeys).add("brief_popup_style_settings_description");
            }
            return nonIndexableKeys;
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class SettingsObserver extends ContentObserver {
        public final Uri EDGE_LIGHTING;
        public final Uri EDGE_LIGHTING_STYLE;

        public SettingsObserver(Handler handler) {
            super(handler);
            this.EDGE_LIGHTING = Settings.System.getUriFor("edge_lighting");
            this.EDGE_LIGHTING_STYLE = Settings.System.getUriFor("edge_lighting_style_type_str");
        }

        @Override // android.database.ContentObserver
        public final void onChange(boolean z, Uri uri) {
            BriefPopUpSettings briefPopUpSettings = BriefPopUpSettings.this;
            BaseSearchIndexProvider baseSearchIndexProvider =
                    BriefPopUpSettings.SEARCH_INDEX_DATA_PROVIDER;
            briefPopUpSettings.updateVisible$3();
        }

        public final void setListening(boolean z) {
            if (!z) {
                BriefPopUpSettings.this
                        .getContext()
                        .getContentResolver()
                        .unregisterContentObserver(this);
            } else {
                BriefPopUpSettings.this
                        .getContext()
                        .getContentResolver()
                        .registerContentObserver(this.EDGE_LIGHTING, false, this);
                BriefPopUpSettings.this
                        .getContext()
                        .getContentResolver()
                        .registerContentObserver(this.EDGE_LIGHTING_STYLE, false, this);
            }
        }
    }

    @Override // com.android.settings.dashboard.DashboardFragment
    /* renamed from: getLogTag */
    public final String getTAG() {
        return "BriefPopUpSettings";
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 0;
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment
    public final int getPreferenceScreenResId() {
        return R.xml.brief_popup_main_settings;
    }

    @Override // com.android.settings.SettingsPreferenceFragment
    public final boolean isPreferenceAnimationAllowed() {
        return false;
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        if (getPreferenceScreen() == null) {
            return;
        }
        this.mHandler = new Handler(Looper.getMainLooper());
        this.mSettingsObserver = new SettingsObserver(this.mHandler);
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onResume() {
        super.onResume();
        this.mSettingsObserver.setListening(true);
        updateVisible$3();
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onStop() {
        super.onStop();
        this.mSettingsObserver.setListening(false);
    }

    public final void updateVisible$3() {
        Iterator<List<AbstractPreferenceController>> it = getPreferenceControllers().iterator();
        while (it.hasNext()) {
            for (AbstractPreferenceController abstractPreferenceController : it.next()) {
                if (abstractPreferenceController instanceof BriefPopupListController) {
                    ((BriefPopupListController) abstractPreferenceController).updateVisible();
                }
                if (abstractPreferenceController instanceof BriefPopUpStyleController) {
                    ((BriefPopUpStyleController) abstractPreferenceController).updateVisible();
                }
                if (abstractPreferenceController instanceof BriefPopUpKeywordColorController) {
                    ((BriefPopUpKeywordColorController) abstractPreferenceController)
                            .updateVisible();
                }
                if (abstractPreferenceController instanceof BriefPopUpConditionController) {
                    ((BriefPopUpConditionController) abstractPreferenceController).updateVisible();
                }
                if (abstractPreferenceController
                        instanceof DetailedPopUpStyleDescriptionController) {
                    ((DetailedPopUpStyleDescriptionController) abstractPreferenceController)
                            .updateVisible();
                }
                if (abstractPreferenceController instanceof BriefPopUpStyleDescriptionController) {
                    ((BriefPopUpStyleDescriptionController) abstractPreferenceController)
                            .updateVisible();
                }
            }
        }
    }
}
