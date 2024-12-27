package com.samsung.android.settings.usefulfeature.labs.darkmodeapps;

import android.app.UiModeManager;
import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.picker.model.AppInfo;
import androidx.picker.widget.AppPickerState$OnStateChangeListener;
import androidx.picker.widget.SeslAppPickerView;

import com.android.settings.R;
import com.android.settings.SettingsPreferenceFragment;
import com.android.settings.Utils;

import com.samsung.android.settings.logging.LoggingHelper;
import com.samsung.android.settings.usefulfeature.labs.LabsSettings;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.internal.MainDispatcherLoader;
import kotlinx.coroutines.scheduling.DefaultScheduler;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
@Metadata(
        d1 = {
            "\u0000\f\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\b\u0003\b\u0007\u0018\u00002\u00020\u0001B\u0007¢\u0006\u0004\b\u0002\u0010\u0003¨\u0006\u0004"
        },
        d2 = {
            "Lcom/samsung/android/settings/usefulfeature/labs/darkmodeapps/DarkModeAppsSettings;",
            "Lcom/android/settings/SettingsPreferenceFragment;",
            "<init>",
            "()V",
            "applications__sources__apps__SecSettings__android_common__SecSettings-core"
        },
        k = 1,
        mv = {1, 9, 0})
/* loaded from: classes3.dex */
public final class DarkModeAppsSettings extends SettingsPreferenceFragment {
    public SeslAppPickerView mAppPickerView;
    public ViewGroup mProgressBar;
    public UiModeManager mUiModeManager;

    @Override // com.android.settings.core.InstrumentedPreferenceFragment
    public final int getFragmentTitleResId(Context context) {
        return R.string.sec_labs_dark_mode_apps_title;
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment
    public final String getHierarchicalParentFragment(Context context) {
        return LabsSettings.class.getName();
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 68703;
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final View onCreateView(LayoutInflater inflater, ViewGroup viewGroup, Bundle bundle) {
        Resources resources;
        ViewGroup viewGroup2;
        Intrinsics.checkNotNullParameter(inflater, "inflater");
        Context context = getContext();
        this.mUiModeManager =
                context != null
                        ? (UiModeManager) context.getSystemService(UiModeManager.class)
                        : null;
        View inflate = inflater.inflate(R.layout.sec_labs_apppicker_view_layout, (ViewGroup) null);
        this.mAppPickerView =
                (SeslAppPickerView) inflate.findViewById(R.id.sec_labs_app_picker_view);
        int listHorizontalPadding = Utils.getListHorizontalPadding(getContext());
        SeslAppPickerView seslAppPickerView = this.mAppPickerView;
        if (seslAppPickerView != null) {
            seslAppPickerView.setPadding(listHorizontalPadding, 0, listHorizontalPadding, 0);
        }
        SeslAppPickerView seslAppPickerView2 = this.mAppPickerView;
        if (seslAppPickerView2 != null) {
            seslAppPickerView2.seslSetFillHorizontalPaddingEnabled(true);
        }
        SeslAppPickerView seslAppPickerView3 = this.mAppPickerView;
        if (seslAppPickerView3 != null) {
            seslAppPickerView3.setAppListOrder(1);
        }
        SeslAppPickerView seslAppPickerView4 = this.mAppPickerView;
        if (seslAppPickerView4 != null) {
            seslAppPickerView4.setItemAnimator(null);
        }
        SeslAppPickerView seslAppPickerView5 = this.mAppPickerView;
        if (seslAppPickerView5 != null) {
            seslAppPickerView5.mOnStateChangeListener =
                    new AppPickerState$OnStateChangeListener() { // from class:
                                                                 // com.samsung.android.settings.usefulfeature.labs.darkmodeapps.DarkModeAppsSettings$onCreateView$1
                        @Override // androidx.picker.widget.AppPickerState$OnStateChangeListener
                        public final void onStateChanged(AppInfo appInfo, boolean z) {
                            Intrinsics.checkNotNullParameter(appInfo, "appInfo");
                            String str = appInfo.packageName;
                            UiModeManager uiModeManager = DarkModeAppsSettings.this.mUiModeManager;
                            if (uiModeManager != null) {
                                uiModeManager.setPackageNightMode(str, z ? 2 : 0);
                            }
                            LoggingHelper.insertEventLogging(68703, z ? 68705 : 68706, str);
                        }

                        @Override // androidx.picker.widget.AppPickerState$OnStateChangeListener
                        public final void onStateAllChanged(boolean z) {}
                    };
        }
        View findViewById = inflate.findViewById(R.id.loading_panel);
        Intrinsics.checkNotNull(
                findViewById, "null cannot be cast to non-null type android.view.ViewGroup");
        ViewGroup viewGroup3 = (ViewGroup) findViewById;
        this.mProgressBar = viewGroup3;
        viewGroup3.semSetRoundedCorners(3);
        Context context2 = getContext();
        if (context2 != null
                && (resources = context2.getResources()) != null
                && (viewGroup2 = this.mProgressBar) != null) {
            viewGroup2.semSetRoundedCornerColor(
                    3, resources.getColor(R.color.sec_widget_round_and_bgcolor));
        }
        TextView textView = (TextView) inflate.findViewById(R.id.description_summary);
        if (Utils.isTablet()) {
            if (textView != null) {
                textView.setText(R.string.sec_labs_dark_mode_apps_tablet_description);
            }
        } else if (textView != null) {
            textView.setText(R.string.sec_labs_dark_mode_apps_description);
        }
        return inflate;
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onPause() {
        super.onPause();
        ViewGroup viewGroup = this.mProgressBar;
        Intrinsics.checkNotNull(viewGroup);
        if (viewGroup.getVisibility() == 0) {
            ViewGroup viewGroup2 = this.mProgressBar;
            Intrinsics.checkNotNull(viewGroup2);
            viewGroup2.setVisibility(8);
        }
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onResume() {
        super.onResume();
        DefaultScheduler defaultScheduler = Dispatchers.Default;
        BuildersKt.launch$default(
                CoroutineScopeKt.CoroutineScope(MainDispatcherLoader.dispatcher),
                null,
                null,
                new DarkModeAppsSettings$onResume$1(this, null),
                3);
    }
}
