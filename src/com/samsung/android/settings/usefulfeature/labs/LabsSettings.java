package com.samsung.android.settings.usefulfeature.labs;

import android.content.Context;
import android.content.pm.Signature;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.picker.adapter.AbsAdapter$1$$ExternalSyntheticOutline0;
import androidx.preference.Preference;
import androidx.recyclerview.widget.RecyclerView;

import com.android.settings.R;
import com.android.settings.Utils;
import com.android.settingslib.search.Indexable$SearchIndexProvider;
import com.android.settingslib.widget.LayoutPreference;

import com.samsung.android.settings.Rune;
import com.samsung.android.settings.SettingsPreferenceFragmentLinkData;
import com.samsung.android.settings.display.SecDisplayUtils;
import com.samsung.android.settings.dynamicmenu.SecDynamicFragment;
import com.samsung.android.settings.widget.SecRelativeLinkView;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt__StringsJVMKt;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
@Metadata(
        d1 = {
            "\u0000\f\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\b\u0003\b\u0007\u0018\u00002\u00020\u0001B\u0007¢\u0006\u0004\b\u0002\u0010\u0003¨\u0006\u0004"
        },
        d2 = {
            "Lcom/samsung/android/settings/usefulfeature/labs/LabsSettings;",
            "Lcom/samsung/android/settings/dynamicmenu/SecDynamicFragment;",
            "<init>",
            "()V",
            "applications__sources__apps__SecSettings__android_common__SecSettings-core"
        },
        k = 1,
        mv = {1, 9, 0})
/* loaded from: classes3.dex */
public final class LabsSettings extends SecDynamicFragment {
    public static final Indexable$SearchIndexProvider SEARCH_INDEX_DATA_PROVIDER =
            new LabsSettings$Companion$SEARCH_INDEX_DATA_PROVIDER$1();
    public final String TAG = "LabsSettings";
    public Configuration mConfiguration;
    public SecRelativeLinkView mRelativeLinkView;

    public static SettingsPreferenceFragmentLinkData getLinkData(String str) {
        SettingsPreferenceFragmentLinkData settingsPreferenceFragmentLinkData =
                new SettingsPreferenceFragmentLinkData();
        if (StringsKt__StringsJVMKt.equals(str, "FullScreenInSplitScreenView", false)) {
            settingsPreferenceFragmentLinkData.fragment =
                    "com.samsung.android.settings.usefulfeature.multiwindow.MultiwindowSettings";
            settingsPreferenceFragmentLinkData.flowId = "68111";
            settingsPreferenceFragmentLinkData.callerMetric = 68000;
            settingsPreferenceFragmentLinkData.titleRes =
                    R.string.sec_full_screen_in_split_screen_view_title;
            settingsPreferenceFragmentLinkData.topLevelKey = "top_level_advanced_features";
            settingsPreferenceFragmentLinkData.extras =
                    AbsAdapter$1$$ExternalSyntheticOutline0.m(
                            ":settings:fragment_args_key", "full_screen_in_split_screen_view");
        } else if (StringsKt__StringsJVMKt.equals(str, "SwipeForPopUpView", false)) {
            settingsPreferenceFragmentLinkData.fragment =
                    "com.samsung.android.settings.usefulfeature.multiwindow.MultiwindowSettings";
            settingsPreferenceFragmentLinkData.flowId = "68120";
            settingsPreferenceFragmentLinkData.callerMetric = 68000;
            settingsPreferenceFragmentLinkData.titleRes = R.string.sec_swpie_for_popup_view_title;
            settingsPreferenceFragmentLinkData.topLevelKey = "top_level_advanced_features";
            settingsPreferenceFragmentLinkData.extras =
                    AbsAdapter$1$$ExternalSyntheticOutline0.m(
                            ":settings:fragment_args_key", "swpie_for_popup_view");
        } else if (StringsKt__StringsJVMKt.equals(str, "SwipeForSplitScreen", false)) {
            settingsPreferenceFragmentLinkData.fragment =
                    "com.samsung.android.settings.usefulfeature.multiwindow.MultiwindowSettings";
            settingsPreferenceFragmentLinkData.flowId = "68130";
            settingsPreferenceFragmentLinkData.callerMetric = 68000;
            settingsPreferenceFragmentLinkData.titleRes = R.string.sec_swpie_for_split_view_title;
            settingsPreferenceFragmentLinkData.topLevelKey = "top_level_advanced_features";
            settingsPreferenceFragmentLinkData.extras =
                    AbsAdapter$1$$ExternalSyntheticOutline0.m(
                            ":settings:fragment_args_key", "swpie_for_split_view");
        } else if (StringsKt__StringsJVMKt.equals(str, "ShowMultiWindowMenuWithOneWindow", false)) {
            settingsPreferenceFragmentLinkData.fragment =
                    "com.samsung.android.settings.usefulfeature.multiwindow.MultiwindowSettings";
            settingsPreferenceFragmentLinkData.flowId = "68102";
            settingsPreferenceFragmentLinkData.callerMetric = 68000;
            settingsPreferenceFragmentLinkData.titleRes =
                    R.string.sec_show_multi_window_menu_in_full_screen_view_title;
            settingsPreferenceFragmentLinkData.topLevelKey = "top_level_advanced_features";
            settingsPreferenceFragmentLinkData.extras =
                    AbsAdapter$1$$ExternalSyntheticOutline0.m(
                            ":settings:fragment_args_key",
                            "show_multi_window_menu_in_full_screen_view");
        }
        return settingsPreferenceFragmentLinkData;
    }

    @Override // com.android.settings.dashboard.DashboardFragment
    /* renamed from: getLogTag, reason: from getter */
    public final String getTAG() {
        return this.TAG;
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 68000;
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment
    public final int getPreferenceScreenResId() {
        return R.xml.sec_useful_feature_labs;
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment,
              // android.content.ComponentCallbacks
    public final void onConfigurationChanged(Configuration newConfig) {
        RecyclerView.Adapter adapter;
        Intrinsics.checkNotNullParameter(newConfig, "newConfig");
        super.onConfigurationChanged(newConfig);
        Configuration configuration = this.mConfiguration;
        if (configuration == null || configuration.orientation != newConfig.orientation) {
            Preference preference = getPreferenceScreen().getPreference(0);
            Intrinsics.checkNotNullExpressionValue(preference, "getPreference(...)");
            getPreferenceScreen().removePreference(preference);
            LayoutPreference layoutPreference =
                    new LayoutPreference(getContext(), R.layout.sec_labs_settings_layout);
            layoutPreference.setOrder(0);
            getPreferenceScreen().addPreference(layoutPreference);
            setPreview$1$1();
            if (getListView() != null && (adapter = getListView().getAdapter()) != null) {
                adapter.notifyDataSetChanged();
            }
        }
        Configuration configuration2 = this.mConfiguration;
        if (configuration2 != null) {
            configuration2.updateFrom(newConfig);
        }
        String str = Rune.COMMON_CONFIG_PACKAGE_NAME_SMART_MANAGER;
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        Resources resources;
        Resources resources2;
        Configuration configuration;
        super.onCreate(bundle);
        this.mConfiguration = new Configuration();
        Context context = getContext();
        if (context != null
                && (resources2 = context.getResources()) != null
                && (configuration = resources2.getConfiguration()) != null) {
            Configuration configuration2 = this.mConfiguration;
            Intrinsics.checkNotNull(configuration2);
            configuration2.updateFrom(configuration);
        }
        setPreview$1$1();
        this.mRelativeLinkView = new SecRelativeLinkView(getActivity());
        Context context2 = getContext();
        Boolean valueOf =
                (context2 == null || (resources = context2.getResources()) == null)
                        ? null
                        : Boolean.valueOf(
                                resources.getBoolean(
                                        android.R.bool.config_unfoldTransitionHapticsEnabled));
        Intrinsics.checkNotNull(valueOf);
        if (valueOf.booleanValue()) {
            SecRelativeLinkView secRelativeLinkView = this.mRelativeLinkView;
            Intrinsics.checkNotNull(secRelativeLinkView);
            secRelativeLinkView.pushLinkData(getLinkData("FullScreenInSplitScreenView"));
            SecRelativeLinkView secRelativeLinkView2 = this.mRelativeLinkView;
            Intrinsics.checkNotNull(secRelativeLinkView2);
            secRelativeLinkView2.pushLinkData(getLinkData("SwipeForPopUpView"));
            SecRelativeLinkView secRelativeLinkView3 = this.mRelativeLinkView;
            Intrinsics.checkNotNull(secRelativeLinkView3);
            secRelativeLinkView3.pushLinkData(getLinkData("SwipeForSplitScreen"));
        }
        if (Utils.isTablet()) {
            SecRelativeLinkView secRelativeLinkView4 = this.mRelativeLinkView;
            Intrinsics.checkNotNull(secRelativeLinkView4);
            secRelativeLinkView4.pushLinkData(getLinkData("ShowMultiWindowMenuWithOneWindow"));
        } else if (Build.VERSION.SEM_PLATFORM_INT > 130100) {
            String str = Rune.COMMON_CONFIG_PACKAGE_NAME_SMART_MANAGER;
        }
        SecRelativeLinkView secRelativeLinkView5 = this.mRelativeLinkView;
        Intrinsics.checkNotNull(secRelativeLinkView5);
        secRelativeLinkView5.create(this);
    }

    @Override // com.samsung.android.settings.dynamicmenu.SecDynamicFragment,
              // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onResume() {
        super.onResume();
        getContext();
        Signature[] signatureArr = SecDisplayUtils.SIGNATURES;
        String str = Rune.COMMON_CONFIG_PACKAGE_NAME_SMART_MANAGER;
    }

    public final void setPreview$1$1() {
        Preference preference = getPreferenceScreen().getPreference(0);
        Intrinsics.checkNotNull(
                preference,
                "null cannot be cast to non-null type"
                    + " com.android.settingslib.widget.LayoutPreference");
        View findViewById =
                ((LayoutPreference) preference)
                        .mRootView.findViewById(R.id.preview_image_container);
        Intrinsics.checkNotNull(
                findViewById, "null cannot be cast to non-null type android.widget.LinearLayout");
        LinearLayout linearLayout = (LinearLayout) findViewById;
        linearLayout.semSetRoundedCorners(15);
        Context context = getContext();
        Intrinsics.checkNotNull(context);
        Resources resources = context.getResources();
        Intrinsics.checkNotNull(resources);
        linearLayout.semSetRoundedCornerColor(
                15, resources.getColor(R.color.sec_widget_round_and_bgcolor));
    }
}
