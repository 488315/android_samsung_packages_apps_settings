package com.samsung.android.settings.notification.brief;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;

import androidx.picker3.app.SeslColorPickerDialog;

import com.android.settings.R;
import com.android.settings.dashboard.DashboardFragment;

import com.samsung.android.settings.logging.status.BaseStatusLoggingProvider;
import com.samsung.android.settings.logging.status.StatusData;
import com.samsung.android.settings.logging.status.StatusLogger$StatusLoggingProvider;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class BriefPopUpKeywordColorSettings extends DashboardFragment {
    public static final StatusLogger$StatusLoggingProvider STATUS_LOGGING_PROVIDER =
            new AnonymousClass1();
    public BriefPopUpKeywordColorLayoutPreference colorPreference;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.settings.notification.brief.BriefPopUpKeywordColorSettings$1, reason: invalid class name */
    public final class AnonymousClass1 extends BaseStatusLoggingProvider {
        @Override // com.samsung.android.settings.logging.status.StatusLogger$StatusLoggingProvider
        public final List getStatusLoggingData(Context context) {
            ArrayList arrayList = new ArrayList();
            int size = BriefPopUpUtils.loadCustomTextList(context).size();
            String valueOf = String.valueOf(36109);
            String str = Integer.toString(size) + " number of keyword registered";
            StatusData statusData = new StatusData();
            statusData.mStatusValue = str;
            statusData.mStatusKey = valueOf;
            arrayList.add(statusData);
            return arrayList;
        }
    }

    @Override // com.android.settings.dashboard.DashboardFragment
    /* renamed from: getLogTag */
    public final String getTAG() {
        return "BriefPopUpKeywordColorSettings";
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 0;
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment
    public final int getPreferenceScreenResId() {
        return R.xml.brief_popup_keyword_color_settings;
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment,
              // android.content.ComponentCallbacks
    public final void onConfigurationChanged(Configuration configuration) {
        SeslColorPickerDialog seslColorPickerDialog;
        super.onConfigurationChanged(configuration);
        BriefPopUpKeywordColorLayoutPreference briefPopUpKeywordColorLayoutPreference =
                this.colorPreference;
        if (briefPopUpKeywordColorLayoutPreference == null
                || (seslColorPickerDialog =
                                briefPopUpKeywordColorLayoutPreference.mColorPickerDialog)
                        == null
                || !seslColorPickerDialog.isShowing()) {
            return;
        }
        briefPopUpKeywordColorLayoutPreference.mColorPickerDialog.dismiss();
        briefPopUpKeywordColorLayoutPreference.showSeslColorPicker();
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.colorPreference =
                (BriefPopUpKeywordColorLayoutPreference)
                        findPreference("brief_popup_keyword_color_layout_settings");
    }
}
