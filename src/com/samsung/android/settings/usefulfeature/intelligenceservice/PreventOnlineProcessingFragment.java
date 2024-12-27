package com.samsung.android.settings.usefulfeature.intelligenceservice;

import android.content.Context;
import android.os.Bundle;
import android.os.UserHandle;
import android.provider.Settings;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.appcompat.widget.SeslSwitchBar;
import androidx.fragment.app.FragmentActivity;
import androidx.preference.PreferenceScreen;
import androidx.preference.SecPreferenceCategory;

import com.android.settings.R;
import com.android.settings.SettingsActivity;
import com.android.settings.dashboard.DashboardFragment;
import com.android.settings.widget.SettingsMainSwitchBar;
import com.android.settingslib.utils.ThreadUtils;
import com.android.settingslib.widget.LayoutPreference;

import com.samsung.android.settings.logging.SALogging;
import com.samsung.android.settings.widget.SecCustomDividerItemDecorator;

import kotlin.Metadata;
import kotlin.collections.ArraysKt___ArraysKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.collections.MapsKt__MapsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlin.text.StringsKt___StringsKt;

import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.TreeSet;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
@Metadata(
        d1 = {
            "\u0000\u0010\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\b\u0003\b\u0007\u0018\u00002\u00020\u00012\u00020\u0002B\u0007¢\u0006\u0004\b\u0003\u0010\u0004¨\u0006\u0005"
        },
        d2 = {
            "Lcom/samsung/android/settings/usefulfeature/intelligenceservice/PreventOnlineProcessingFragment;",
            "Lcom/android/settings/dashboard/DashboardFragment;",
            "Landroid/widget/CompoundButton$OnCheckedChangeListener;",
            "<init>",
            "()V",
            "applications__sources__apps__SecSettings__android_common__SecSettings-core"
        },
        k = 1,
        mv = {1, 9, 0})
/* loaded from: classes3.dex */
public final class PreventOnlineProcessingFragment extends DashboardFragment
        implements CompoundButton.OnCheckedChangeListener {
    public static final String TAG =
            Reflection.factory
                    .getOrCreateKotlinClass(PreventOnlineProcessingFragment.class)
                    .getSimpleName();
    public IntelligenceServiceAppsManager mISAManager;
    public int mUserId = UserHandle.myUserId();
    public SettingsMainSwitchBar switchBar;

    public final void addPreference(
            SecPreferenceCategory secPreferenceCategory,
            IntelligenceServiceAppInfo intelligenceServiceAppInfo,
            String[] strArr) {
        SecIntelligenceServicePreference secIntelligenceServicePreference =
                new SecIntelligenceServicePreference(requireContext());
        secIntelligenceServicePreference.setKey(intelligenceServiceAppInfo.key);
        secIntelligenceServicePreference.setOrder(intelligenceServiceAppInfo.order);
        secIntelligenceServicePreference.setTitle(intelligenceServiceAppInfo.title);
        if (strArr != null) {
            StringBuilder sb = new StringBuilder();
            int length = strArr.length;
            for (int i = 0; i < length; i++) {
                sb.append(strArr[i]);
                if (i < strArr.length - 1) {
                    sb.append("\n");
                }
            }
            secIntelligenceServicePreference.setSummary(sb.toString());
        }
        secIntelligenceServicePreference.setIcon(intelligenceServiceAppInfo.icon);
        if (secPreferenceCategory != null) {
            secPreferenceCategory.addPreference(secIntelligenceServicePreference);
        }
    }

    @Override // com.android.settings.dashboard.DashboardFragment
    /* renamed from: getLogTag */
    public final String getTAG() {
        return TAG;
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 80001;
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment
    public final int getPreferenceScreenResId() {
        return R.xml.sec_prevent_online_processing;
    }

    @Override // com.android.settings.SettingsPreferenceFragment
    public final boolean isPreferenceAnimationAllowed() {
        return false;
    }

    @Override // android.widget.CompoundButton.OnCheckedChangeListener
    public final void onCheckedChanged(CompoundButton compoundButton, boolean z) {
        updateCategoryVisible(z);
        Context context = getContext();
        int intForUser =
                Settings.System.getIntForUser(
                        context != null ? context.getContentResolver() : null,
                        "prevent_online_processing",
                        -1,
                        this.mUserId);
        if (intForUser == -1 || intForUser != z) {
            Context context2 = getContext();
            Settings.System.putIntForUser(
                    context2 != null ? context2.getContentResolver() : null,
                    "prevent_online_processing",
                    z ? 1 : 0,
                    this.mUserId);
            SALogging.insertSALog(z ? 1L : 0L, "AI003", String.valueOf(80001), (String) null);
        }
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        Bundle arguments = getArguments();
        Intrinsics.checkNotNull(arguments);
        this.mUserId = arguments.getInt("USER_ID");
        this.mISAManager = new IntelligenceServiceAppsManager(getContext(), this.mUserId);
    }

    @Override // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onDestroyView() {
        super.onDestroyView();
        SettingsMainSwitchBar settingsMainSwitchBar = this.switchBar;
        if (settingsMainSwitchBar != null) {
            settingsMainSwitchBar.removeOnSwitchChangeListener(this);
        }
        SettingsMainSwitchBar settingsMainSwitchBar2 = this.switchBar;
        if (settingsMainSwitchBar2 != null) {
            settingsMainSwitchBar2.hide();
        }
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onResume() {
        super.onResume();
        SettingsMainSwitchBar settingsMainSwitchBar = this.switchBar;
        if (settingsMainSwitchBar != null) {
            Context context = getContext();
            settingsMainSwitchBar.setChecked(
                    Settings.System.getIntForUser(
                                    context != null ? context.getContentResolver() : null,
                                    "prevent_online_processing",
                                    0,
                                    this.mUserId)
                            != 0);
        }
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onViewCreated(View view, Bundle bundle) {
        Intrinsics.checkNotNullParameter(view, "view");
        super.onViewCreated(view, bundle);
        SettingsActivity settingsActivity = (SettingsActivity) getActivity();
        Intrinsics.checkNotNull(settingsActivity);
        SettingsMainSwitchBar settingsMainSwitchBar = settingsActivity.mMainSwitch;
        this.switchBar = settingsMainSwitchBar;
        if (settingsMainSwitchBar != null) {
            settingsMainSwitchBar.addOnSwitchChangeListener(this);
        }
        SettingsMainSwitchBar settingsMainSwitchBar2 = this.switchBar;
        if (settingsMainSwitchBar2 != null) {
            settingsMainSwitchBar2.show();
        }
        LayoutPreference layoutPreference =
                (LayoutPreference) getPreferenceScreen().findPreference("key_description");
        TextView textView =
                layoutPreference != null
                        ? (TextView) layoutPreference.mRootView.findViewById(R.id.description)
                        : null;
        if (textView != null) {
            Context context = getContext();
            textView.setText(
                    context != null
                            ? context.getString(
                                    R.string
                                            .sec_intelligence_service_prevent_online_processing_description)
                            : null);
        }
        setDivider(null);
        getListView()
                .addItemDecoration(
                        new SecCustomDividerItemDecorator(
                                getResources().getDrawable(R.drawable.sec_top_level_list_divider),
                                getContext(),
                                (int)
                                        (getResources()
                                                        .getDimension(
                                                                R.dimen
                                                                        .sec_intelligence_service_item_padding_end)
                                                + getResources()
                                                        .getDimension(
                                                                R.dimen
                                                                        .sec_intelligence_service_ic_size)
                                                + getResources()
                                                        .getDimension(
                                                                R.dimen
                                                                        .sec_intelligence_service_item_padding_start)),
                                R.id.icon_frame,
                                android.R.id.icon));
        SettingsMainSwitchBar settingsMainSwitchBar3 = this.switchBar;
        Intrinsics.checkNotNull(settingsMainSwitchBar3);
        updateCategoryVisible(((SeslSwitchBar) settingsMainSwitchBar3).mSwitch.isChecked());
        setLoading(true, false);
        ThreadUtils.postOnBackgroundThread(
                new Runnable() { // from class:
                                 // com.samsung.android.settings.usefulfeature.intelligenceservice.PreventOnlineProcessingFragment$initPreferences$1
                    @Override // java.lang.Runnable
                    public final void run() {
                        IntelligenceServiceAppsManager intelligenceServiceAppsManager =
                                PreventOnlineProcessingFragment.this.mISAManager;
                        if (intelligenceServiceAppsManager == null) {
                            Intrinsics.throwUninitializedPropertyAccessException("mISAManager");
                            throw null;
                        }
                        final TreeSet[] appList = intelligenceServiceAppsManager.getAppList(null);
                        FragmentActivity activity =
                                PreventOnlineProcessingFragment.this.getActivity();
                        if (activity != null) {
                            final PreventOnlineProcessingFragment preventOnlineProcessingFragment =
                                    PreventOnlineProcessingFragment.this;
                            activity.runOnUiThread(
                                    new Runnable() { // from class:
                                                     // com.samsung.android.settings.usefulfeature.intelligenceservice.PreventOnlineProcessingFragment$initPreferences$1.1
                                        @Override // java.lang.Runnable
                                        public final void run() {
                                            PreventOnlineProcessingFragment
                                                    preventOnlineProcessingFragment2 =
                                                            PreventOnlineProcessingFragment.this;
                                            TreeSet[] candidateAppList = appList;
                                            Intrinsics.checkNotNullExpressionValue(
                                                    candidateAppList, "$candidateAppList");
                                            String str = PreventOnlineProcessingFragment.TAG;
                                            PreferenceScreen preferenceScreen =
                                                    preventOnlineProcessingFragment2
                                                            .getPreferenceScreen();
                                            if (preferenceScreen != null) {
                                                SecPreferenceCategory secPreferenceCategory =
                                                        (SecPreferenceCategory)
                                                                preferenceScreen.findPreference(
                                                                        "key_offline_ai_features");
                                                SecPreferenceCategory secPreferenceCategory2 =
                                                        (SecPreferenceCategory)
                                                                preferenceScreen.findPreference(
                                                                        "key_all_ai_features");
                                                for (TreeSet treeSet : candidateAppList) {
                                                    Iterator it = treeSet.iterator();
                                                    while (it.hasNext()) {
                                                        IntelligenceServiceAppInfo
                                                                intelligenceServiceAppInfo =
                                                                        (IntelligenceServiceAppInfo)
                                                                                it.next();
                                                        String str2 =
                                                                intelligenceServiceAppInfo
                                                                        .supportedMode;
                                                        if (str2 != null) {
                                                            if (StringsKt___StringsKt.contains(
                                                                    str2, "offline", false)) {
                                                                preventOnlineProcessingFragment2
                                                                        .addPreference(
                                                                                secPreferenceCategory,
                                                                                intelligenceServiceAppInfo,
                                                                                intelligenceServiceAppInfo
                                                                                        .offlineAIFeatures);
                                                            }
                                                            String supportedMode =
                                                                    intelligenceServiceAppInfo
                                                                            .supportedMode;
                                                            Intrinsics.checkNotNullExpressionValue(
                                                                    supportedMode, "supportedMode");
                                                            if (StringsKt___StringsKt.contains(
                                                                    supportedMode,
                                                                    "online",
                                                                    false)) {
                                                                String[] strArr =
                                                                        intelligenceServiceAppInfo
                                                                                .offlineAIFeatures;
                                                                if (strArr == null) {
                                                                    strArr = new String[0];
                                                                }
                                                                String[] strArr2 =
                                                                        intelligenceServiceAppInfo
                                                                                .onlineAIFeatures;
                                                                if (strArr2 == null) {
                                                                    strArr2 = new String[0];
                                                                }
                                                                int length = strArr.length;
                                                                int length2 = strArr2.length;
                                                                Object[] copyOf =
                                                                        Arrays.copyOf(
                                                                                strArr,
                                                                                length + length2);
                                                                System.arraycopy(
                                                                        strArr2, 0, copyOf, length,
                                                                        length2);
                                                                Intrinsics.checkNotNull(copyOf);
                                                                LinkedHashSet linkedHashSet =
                                                                        new LinkedHashSet(
                                                                                MapsKt__MapsKt
                                                                                        .mapCapacity(
                                                                                                copyOf.length));
                                                                ArraysKt___ArraysKt.toCollection(
                                                                        linkedHashSet, copyOf);
                                                                preventOnlineProcessingFragment2
                                                                        .addPreference(
                                                                                secPreferenceCategory2,
                                                                                intelligenceServiceAppInfo,
                                                                                (String[])
                                                                                        CollectionsKt___CollectionsKt
                                                                                                .toList(
                                                                                                        linkedHashSet)
                                                                                                .toArray(
                                                                                                        new String
                                                                                                                [0]));
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                            PreventOnlineProcessingFragment.this.setLoading(
                                                    false, false);
                                        }
                                    });
                        }
                    }
                });
    }

    public final void updateCategoryVisible(boolean z) {
        PreferenceScreen preferenceScreen = getPreferenceScreen();
        if (preferenceScreen == null) {
            return;
        }
        SecPreferenceCategory secPreferenceCategory =
                (SecPreferenceCategory) preferenceScreen.findPreference("key_offline_ai_features");
        if (secPreferenceCategory != null) {
            secPreferenceCategory.setVisible(z);
        }
        SecPreferenceCategory secPreferenceCategory2 =
                (SecPreferenceCategory) preferenceScreen.findPreference("key_all_ai_features");
        if (secPreferenceCategory2 == null) {
            return;
        }
        secPreferenceCategory2.setVisible(!z);
    }
}
