package com.android.settings.system;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.widget.NestedScrollView;
import androidx.reflect.view.SeslViewReflector;
import androidx.window.embedding.ActivityEmbeddingController;

import com.android.settings.R;
import com.android.settings.Utils;
import com.android.settings.applications.manageapplications.ResetAppPrefPreferenceController;
import com.android.settings.applications.manageapplications.ResetAppsHelper;
import com.android.settings.network.NetworkResetPreferenceController;
import com.android.settings.privatespace.delete.ResetOptionsDeletePrivateSpaceController;
import com.android.settings.search.BaseSearchIndexProvider;
import com.android.settingslib.core.lifecycle.Lifecycle;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.settings.Rune;
import com.samsung.android.settings.dynamicmenu.SecDynamicFragment;
import com.samsung.android.settings.general.TemporaryBackupPreferenceController;
import com.samsung.android.util.SemLog;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class ResetDashboardFragment extends SecDynamicFragment {
    public static final BaseSearchIndexProvider SEARCH_INDEX_DATA_PROVIDER =
            new AnonymousClass1(R.xml.sec_reset_dashboard_fragment);
    public Context mContext;
    public NestedScrollView mNestedScrollView;
    public View mainResetView;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.android.settings.system.ResetDashboardFragment$1, reason: invalid class name */
    public final class AnonymousClass1 extends BaseSearchIndexProvider {
        @Override // com.android.settings.search.BaseSearchIndexProvider
        public final List createPreferenceControllers(Context context) {
            return ResetDashboardFragment.buildPreferenceControllers$1(context, null);
        }
    }

    public static List buildPreferenceControllers$1(Context context, Lifecycle lifecycle) {
        ArrayList arrayList = new ArrayList();
        arrayList.add(new NetworkResetPreferenceController(context));
        arrayList.add(new FactoryResetPreferenceController(context));
        ResetAppPrefPreferenceController resetAppPrefPreferenceController =
                new ResetAppPrefPreferenceController(context);
        resetAppPrefPreferenceController.mResetAppsHelper = new ResetAppsHelper(context);
        if (lifecycle != null) {
            lifecycle.addObserver(resetAppPrefPreferenceController);
        }
        arrayList.add(resetAppPrefPreferenceController);
        return arrayList;
    }

    @Override // com.android.settings.dashboard.DashboardFragment
    public final List createPreferenceControllers(Context context) {
        return buildPreferenceControllers$1(context, getSettingsLifecycle());
    }

    public final View createResetView(LayoutInflater layoutInflater, ViewGroup viewGroup) {
        String str;
        if (viewGroup != null) {
            viewGroup.removeAllViews();
        }
        ViewGroup viewGroup2 =
                ((Utils.isTablet()
                                        && ActivityEmbeddingController.getInstance(getActivity())
                                                .isActivityEmbedded(getActivity()))
                                || Rune.isSamsungDexMode(this.mContext))
                        ? (ViewGroup)
                                layoutInflater.inflate(
                                        R.layout.sec_reset_container_tablet, viewGroup)
                        : (ViewGroup)
                                layoutInflater.inflate(R.layout.sec_reset_container, viewGroup);
        this.mNestedScrollView =
                (NestedScrollView) viewGroup2.findViewById(R.id.nested_scroll_view);
        if (new TemporaryBackupPreferenceController(getActivity(), "external_storage_transfer")
                        .getAvailabilityStatus()
                == 0) {
            View inflate =
                    layoutInflater.inflate(R.layout.sec_temporary_backup_layout, (ViewGroup) null);
            int listHorizontalPadding = Utils.getListHorizontalPadding(this.mContext);
            ((ViewGroup) viewGroup2.findViewById(R.id.temporary_backup_preview_container))
                    .addView(inflate);
            LinearLayout linearLayout =
                    (LinearLayout) inflate.findViewById(R.id.preview_image_container);
            linearLayout.semSetRoundedCorners(15);
            linearLayout.semSetRoundedCornerColor(
                    15,
                    getActivity().getResources().getColor(R.color.sec_widget_round_and_bgcolor));
            TextView textView = (TextView) inflate.findViewById(R.id.help_description_text);
            try {
                str =
                        this.mContext
                                .getPackageManager()
                                .getApplicationLabel(
                                        this.mContext
                                                .getPackageManager()
                                                .getApplicationInfo(
                                                        "com.samsung.android.scloud", 0))
                                .toString();
            } catch (PackageManager.NameNotFoundException e) {
                SemLog.e(
                        "ResetDashboardFragment", "failed to get the app name : " + e.getMessage());
                str = ApnSettings.MVNO_NONE;
            }
            if (textView != null) {
                textView.setText(
                        this.mContext.getString(R.string.temporary_backup_description, str));
            }
            if (getResources().getConfiguration().orientation != 2
                    || ((Utils.isTablet()
                                    && ActivityEmbeddingController.getInstance(getActivity())
                                            .isActivityEmbedded(getActivity()))
                            || Rune.isSamsungDexMode(this.mContext))) {
                int dimensionPixelSize =
                        getContext()
                                .getResources()
                                .getDimensionPixelSize(R.dimen.sec_nested_view_scroll_padding);
                NestedScrollView nestedScrollView = this.mNestedScrollView;
                nestedScrollView.mScrollbarTopPadding = dimensionPixelSize;
                nestedScrollView.mScrollbarBottomPadding = dimensionPixelSize;
                SeslViewReflector.semSetScrollBarTopPadding(nestedScrollView, dimensionPixelSize);
                SeslViewReflector.semSetScrollBarBottomPadding(
                        nestedScrollView, nestedScrollView.mScrollbarBottomPadding);
            } else {
                View findViewById = viewGroup2.findViewById(R.id.temporary_backup_preview_parent);
                if (findViewById != null) {
                    findViewById.setVisibility(0);
                }
                View findViewById2 =
                        viewGroup2.findViewById(R.id.reset_preference_container_parent);
                if (findViewById2 != null) {
                    findViewById2.setVisibility(0);
                }
            }
            inflate.setPadding(listHorizontalPadding, 0, listHorizontalPadding, 0);
        } else {
            removePreference("backup_data_category");
        }
        ViewGroup viewGroup3 = (ViewGroup) viewGroup2.findViewById(R.id.reset_preference_container);
        if (this.mainResetView.getParent() != null) {
            ((ViewGroup) this.mainResetView.getParent()).removeView(this.mainResetView);
        }
        viewGroup3.addView(this.mainResetView);
        View findViewById3 = viewGroup2.findViewById(R.id.sec_reset_settings_layout);
        findViewById3.semSetRoundedCorners(15);
        findViewById3.semSetRoundedCornerColor(
                15, getResources().getColor(R.color.sec_widget_round_and_bgcolor));
        findViewById3.semSetRoundedCornerOffset(
                this.mContext
                        .getResources()
                        .getDimensionPixelSize(R.dimen.sec_preference_horizontal_padding));
        return viewGroup2;
    }

    @Override // com.android.settings.dashboard.DashboardFragment
    /* renamed from: getLogTag */
    public final String getTAG() {
        return "ResetDashboardFragment";
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 924;
    }

    @Override // com.android.settings.SettingsPreferenceFragment
    public final NestedScrollView getNestedScrollView() {
        return this.mNestedScrollView;
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment
    public final int getPreferenceScreenResId() {
        return R.xml.sec_reset_dashboard_fragment;
    }

    @Override // com.android.settings.dashboard.DashboardFragment, androidx.fragment.app.Fragment
    public final void onActivityResult(int i, int i2, Intent intent) {
        if (((ResetOptionsDeletePrivateSpaceController)
                        use(ResetOptionsDeletePrivateSpaceController.class))
                .handleActivityResult(i, i2, intent)) {
            return;
        }
        super.onActivityResult(i, i2, intent);
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment,
              // android.content.ComponentCallbacks
    public final void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        createResetView(
                LayoutInflater.from(this.mContext),
                (ViewGroup) getActivity().findViewById(R.id.sec_reset_settings_layout));
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final View onCreateView(
            LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        this.mainResetView = super.onCreateView(layoutInflater, viewGroup, bundle);
        this.mContext = getContext();
        return createResetView(layoutInflater, null);
    }

    @Override // com.android.settings.SettingsPreferenceFragment
    public final boolean shouldSkipForInitialSUW() {
        return true;
    }
}
