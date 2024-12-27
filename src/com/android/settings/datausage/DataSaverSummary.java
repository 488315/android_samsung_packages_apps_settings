package com.android.settings.datausage;

import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.preference.Preference;
import androidx.preference.SecSwitchPreference;

import com.android.settings.R;
import com.android.settings.SettingsPreferenceFragment;
import com.android.settings.search.BaseSearchIndexProvider;
import com.android.settingslib.applications.ApplicationsState;

import com.samsung.android.knox.EnterpriseDeviceManager;
import com.samsung.android.knox.restriction.RestrictionPolicy;
import com.samsung.android.settings.Rune;
import com.samsung.android.settings.analyzestorage.data.constant.FileType;
import com.samsung.android.settings.connection.ConnectionsUtils;
import com.samsung.android.util.SemLog;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
@Metadata(
        d1 = {
            "\u0000\f\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\b\u0003\b\u0017\u0018\u00002\u00020\u0001B\u0007¢\u0006\u0004\b\u0002\u0010\u0003¨\u0006\u0004"
        },
        d2 = {
            "Lcom/android/settings/datausage/DataSaverSummary;",
            "Lcom/android/settings/SettingsPreferenceFragment;",
            "<init>",
            "()V",
            "applications__sources__apps__SecSettings__android_common__SecSettings-core"
        },
        k = 1,
        mv = {1, 9, 0})
/* loaded from: classes2.dex */
public class DataSaverSummary extends SettingsPreferenceFragment {
    public static final BaseSearchIndexProvider SEARCH_INDEX_DATA_PROVIDER =
            new DataSaverSummary$Companion$SEARCH_INDEX_DATA_PROVIDER$1(R.xml.sec_data_saver);
    public DataSaverBackend dataSaverBackend;
    public RestrictionPolicy mRestrictionPolicy;
    public View mainDataSaverView;
    public SecSwitchPreference switchBar;
    public boolean switching;
    public final String TAG$1 = "DataSaverSummary";
    public final String KEY_DATA_SAVER_SWITCH = "data_saver_switch";
    public final DataSaverSummary$dataSaverBackendListener$1 dataSaverBackendListener =
            new DataSaverBackend
                    .Listener() { // from class:
                                  // com.android.settings.datausage.DataSaverSummary$dataSaverBackendListener$1
                @Override // com.android.settings.datausage.DataSaverBackend.Listener
                public final void onDataSaverChanged(boolean z) {
                    DataSaverSummary dataSaverSummary = DataSaverSummary.this;
                    synchronized (this) {
                        dataSaverSummary.getSwitchBar().setChecked(z);
                        dataSaverSummary.switching = false;
                    }
                }
            };

    /* JADX WARN: Code restructure failed: missing block: B:16:0x009f, code lost:

       if (r3.isActivityEmbedded(r4) == false) goto L21;
    */
    /* JADX WARN: Code restructure failed: missing block: B:6:0x0027, code lost:

       if (r0.isActivityEmbedded(r3) == false) goto L8;
    */
    /* JADX WARN: Removed duplicated region for block: B:10:0x0068  */
    /* JADX WARN: Removed duplicated region for block: B:13:0x0081  */
    /* JADX WARN: Removed duplicated region for block: B:26:0x0100  */
    /* JADX WARN: Removed duplicated region for block: B:28:0x0115  */
    /* JADX WARN: Removed duplicated region for block: B:32:0x00c4  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final android.view.View createDatSaverView(
            android.view.LayoutInflater r7, android.view.ViewGroup r8) {
        /*
            r6 = this;
            if (r8 == 0) goto L5
            r8.removeAllViews()
        L5:
            boolean r0 = com.android.settings.Utils.isTablet()
            java.lang.String r1 = "requireActivity(...)"
            java.lang.String r2 = "null cannot be cast to non-null type android.view.ViewGroup"
            if (r0 == 0) goto L29
            androidx.window.embedding.ActivityEmbeddingController$Companion r0 = androidx.window.embedding.ActivityEmbeddingController.INSTANCE
            androidx.fragment.app.FragmentActivity r3 = r6.requireActivity()
            kotlin.jvm.internal.Intrinsics.checkNotNullExpressionValue(r3, r1)
            androidx.window.embedding.ActivityEmbeddingController r0 = r0.getInstance(r3)
            androidx.fragment.app.FragmentActivity r3 = r6.requireActivity()
            kotlin.jvm.internal.Intrinsics.checkNotNullExpressionValue(r3, r1)
            boolean r0 = r0.isActivityEmbedded(r3)
            if (r0 != 0) goto L33
        L29:
            android.content.Context r0 = r6.requireContext()
            boolean r0 = com.samsung.android.settings.Rune.isSamsungDexMode(r0)
            if (r0 == 0) goto L40
        L33:
            r0 = 2131560575(0x7f0d087f, float:1.8746526E38)
            android.view.View r8 = r7.inflate(r0, r8)
            kotlin.jvm.internal.Intrinsics.checkNotNull(r8, r2)
            android.view.ViewGroup r8 = (android.view.ViewGroup) r8
            goto L4c
        L40:
            r0 = 2131560574(0x7f0d087e, float:1.8746524E38)
            android.view.View r8 = r7.inflate(r0, r8)
            kotlin.jvm.internal.Intrinsics.checkNotNull(r8, r2)
            android.view.ViewGroup r8 = (android.view.ViewGroup) r8
        L4c:
            r0 = 2131364386(0x7f0a0a22, float:1.8348608E38)
            android.view.View r0 = r8.findViewById(r0)
            androidx.core.widget.NestedScrollView r0 = (androidx.core.widget.NestedScrollView) r0
            r0 = 2131560577(0x7f0d0881, float:1.874653E38)
            r3 = 0
            android.view.View r7 = r7.inflate(r0, r3)
            r0 = 2131362967(0x7f0a0497, float:1.834573E38)
            android.view.View r0 = r8.findViewById(r0)
            android.view.ViewGroup r0 = (android.view.ViewGroup) r0
            if (r0 == 0) goto L6b
            r0.addView(r7)
        L6b:
            android.content.Context r7 = r6.requireContext()
            int r7 = com.android.settings.Utils.getListHorizontalPadding(r7)
            android.content.res.Resources r3 = r6.getResources()
            android.content.res.Configuration r3 = r3.getConfiguration()
            int r3 = r3.orientation
            r4 = 2
            r5 = 0
            if (r3 != r4) goto Lc2
            boolean r3 = com.android.settings.Utils.isTablet()
            if (r3 == 0) goto La1
            androidx.window.embedding.ActivityEmbeddingController$Companion r3 = androidx.window.embedding.ActivityEmbeddingController.INSTANCE
            androidx.fragment.app.FragmentActivity r4 = r6.requireActivity()
            kotlin.jvm.internal.Intrinsics.checkNotNullExpressionValue(r4, r1)
            androidx.window.embedding.ActivityEmbeddingController r3 = r3.getInstance(r4)
            androidx.fragment.app.FragmentActivity r4 = r6.requireActivity()
            kotlin.jvm.internal.Intrinsics.checkNotNullExpressionValue(r4, r1)
            boolean r1 = r3.isActivityEmbedded(r4)
            if (r1 != 0) goto Lc2
        La1:
            android.content.Context r1 = r6.requireContext()
            boolean r1 = com.samsung.android.settings.Rune.isSamsungDexMode(r1)
            if (r1 != 0) goto Lc2
            r1 = 2131362968(0x7f0a0498, float:1.8345732E38)
            android.view.View r1 = r8.findViewById(r1)
            if (r1 != 0) goto Lb5
            goto Lb8
        Lb5:
            r1.setVisibility(r5)
        Lb8:
            if (r0 == 0) goto Lcb
            int r1 = r0.getPaddingBottom()
            r0.setPadding(r7, r5, r5, r1)
            goto Lcb
        Lc2:
            if (r0 == 0) goto Lcb
            int r1 = r0.getPaddingBottom()
            r0.setPadding(r7, r5, r7, r1)
        Lcb:
            r7 = 2131362964(0x7f0a0494, float:1.8345723E38)
            android.view.View r7 = r8.findViewById(r7)
            java.lang.String r0 = "null cannot be cast to non-null type android.widget.LinearLayout"
            kotlin.jvm.internal.Intrinsics.checkNotNull(r7, r0)
            android.widget.LinearLayout r7 = (android.widget.LinearLayout) r7
            r0 = 15
            r7.semSetRoundedCorners(r0)
            android.content.res.Resources r1 = r6.getResources()
            r3 = 2131101488(0x7f060730, float:1.7815387E38)
            int r1 = r1.getColor(r3)
            r7.semSetRoundedCornerColor(r0, r1)
            r7 = 2131362966(0x7f0a0496, float:1.8345727E38)
            android.view.View r7 = r8.findViewById(r7)
            android.view.ViewGroup r7 = (android.view.ViewGroup) r7
            android.view.View r0 = r6.mainDataSaverView
            kotlin.jvm.internal.Intrinsics.checkNotNull(r0)
            android.view.ViewParent r0 = r0.getParent()
            if (r0 == 0) goto L113
            android.view.View r0 = r6.mainDataSaverView
            kotlin.jvm.internal.Intrinsics.checkNotNull(r0)
            android.view.ViewParent r0 = r0.getParent()
            kotlin.jvm.internal.Intrinsics.checkNotNull(r0, r2)
            android.view.ViewGroup r0 = (android.view.ViewGroup) r0
            android.view.View r1 = r6.mainDataSaverView
            r0.removeView(r1)
        L113:
            if (r7 == 0) goto L11a
            android.view.View r6 = r6.mainDataSaverView
            r7.addView(r6)
        L11a:
            return r8
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.settings.datausage.DataSaverSummary.createDatSaverView(android.view.LayoutInflater,"
                    + " android.view.ViewGroup):android.view.View");
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return FileType.ACSM;
    }

    public final SecSwitchPreference getSwitchBar() {
        SecSwitchPreference secSwitchPreference = this.switchBar;
        if (secSwitchPreference != null) {
            return secSwitchPreference;
        }
        Intrinsics.throwUninitializedPropertyAccessException("switchBar");
        throw null;
    }

    public void initUI$6() {
        addPreferencesFromResource(R.xml.sec_data_saver);
        Preference findPreference = findPreference(this.KEY_DATA_SAVER_SWITCH);
        Intrinsics.checkNotNull(
                findPreference,
                "null cannot be cast to non-null type androidx.preference.SecSwitchPreference");
        this.switchBar = (SecSwitchPreference) findPreference;
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment,
              // android.content.ComponentCallbacks
    public final void onConfigurationChanged(Configuration newConfig) {
        Intrinsics.checkNotNullParameter(newConfig, "newConfig");
        super.onConfigurationChanged(newConfig);
        Preference findPreference = findPreference(this.KEY_DATA_SAVER_SWITCH);
        Intrinsics.checkNotNull(
                findPreference,
                "null cannot be cast to non-null type androidx.preference.SecSwitchPreference");
        this.switchBar = (SecSwitchPreference) findPreference;
        getSwitchBar()
                .setOnPreferenceChangeListener(
                        new DataSaverSummary$setSwitchBarChangeListener$1(this));
        SecSwitchPreference switchBar = getSwitchBar();
        DataSaverBackend dataSaverBackend = this.dataSaverBackend;
        if (dataSaverBackend == null) {
            Intrinsics.throwUninitializedPropertyAccessException("dataSaverBackend");
            throw null;
        }
        switchBar.setChecked(dataSaverBackend.mPolicyManager.getRestrictBackground());
        LayoutInflater from = LayoutInflater.from(requireContext());
        ViewGroup viewGroup =
                (ViewGroup) requireActivity().findViewById(R.id.sec_data_saver_settings_layout);
        if (Rune.isSamsungDexMode(requireContext())) {
            return;
        }
        Intrinsics.checkNotNull(from);
        createDatSaverView(from, viewGroup);
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        initUI$6();
        Context applicationContext = requireContext().getApplicationContext();
        Intrinsics.checkNotNull(
                applicationContext, "null cannot be cast to non-null type android.app.Application");
        Intrinsics.checkNotNullExpressionValue(
                ApplicationsState.getInstance((Application) applicationContext),
                "getInstance(...)");
        this.dataSaverBackend = new DataSaverBackend(requireContext());
        EnterpriseDeviceManager enterpriseDeviceManager =
                EnterpriseDeviceManager.getInstance(requireContext().getApplicationContext());
        Intrinsics.checkNotNullExpressionValue(enterpriseDeviceManager, "getInstance(...)");
        RestrictionPolicy restrictionPolicy = enterpriseDeviceManager.getRestrictionPolicy();
        Intrinsics.checkNotNullExpressionValue(restrictionPolicy, "getRestrictionPolicy(...)");
        this.mRestrictionPolicy = restrictionPolicy;
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final View onCreateView(LayoutInflater inflater, ViewGroup viewGroup, Bundle bundle) {
        Intrinsics.checkNotNullParameter(inflater, "inflater");
        this.mainDataSaverView = super.onCreateView(inflater, viewGroup, bundle);
        return createDatSaverView(inflater, null);
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onPause() {
        super.onPause();
        DataSaverBackend dataSaverBackend = this.dataSaverBackend;
        if (dataSaverBackend != null) {
            dataSaverBackend.remListener(this.dataSaverBackendListener);
        } else {
            Intrinsics.throwUninitializedPropertyAccessException("dataSaverBackend");
            throw null;
        }
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onResume() {
        super.onResume();
        getSwitchBar()
                .setOnPreferenceChangeListener(
                        new DataSaverSummary$setSwitchBarChangeListener$1(this));
        DataSaverBackend dataSaverBackend = this.dataSaverBackend;
        if (dataSaverBackend == null) {
            Intrinsics.throwUninitializedPropertyAccessException("dataSaverBackend");
            throw null;
        }
        dataSaverBackend.addListener(this.dataSaverBackendListener);
        RestrictionPolicy restrictionPolicy = this.mRestrictionPolicy;
        if (restrictionPolicy == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mRestrictionPolicy");
            throw null;
        }
        String str = this.TAG$1;
        boolean isBackgroundDataEnabled = restrictionPolicy.isBackgroundDataEnabled();
        RestrictionPolicy restrictionPolicy2 = this.mRestrictionPolicy;
        if (restrictionPolicy2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mRestrictionPolicy");
            throw null;
        }
        SemLog.i(
                str,
                "Background data :"
                        + isBackgroundDataEnabled
                        + " Data saving:"
                        + restrictionPolicy2.isDataSavingAllowed());
        RestrictionPolicy restrictionPolicy3 = this.mRestrictionPolicy;
        if (restrictionPolicy3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mRestrictionPolicy");
            throw null;
        }
        if (restrictionPolicy3.isBackgroundDataEnabled()) {
            RestrictionPolicy restrictionPolicy4 = this.mRestrictionPolicy;
            if (restrictionPolicy4 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("mRestrictionPolicy");
                throw null;
            }
            if (restrictionPolicy4.isDataSavingAllowed()) {
                RestrictionPolicy restrictionPolicy5 = this.mRestrictionPolicy;
                if (restrictionPolicy5 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("mRestrictionPolicy");
                    throw null;
                }
                if (restrictionPolicy5.isBackgroundDataEnabled()) {
                    RestrictionPolicy restrictionPolicy6 = this.mRestrictionPolicy;
                    if (restrictionPolicy6 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("mRestrictionPolicy");
                        throw null;
                    }
                    if (restrictionPolicy6.isDataSavingAllowed()) {
                        getSwitchBar().setEnabled(true);
                    }
                }
                if (ConnectionsUtils.isSupportPco()
                        || Settings.Secure.getInt(
                                        requireContext().getContentResolver(),
                                        "background_data_by_pco",
                                        1)
                                == 1) {}
                Log.i(this.TAG$1, "remove switch by pco");
                removePreference(this.KEY_DATA_SAVER_SWITCH);
                return;
            }
        }
        getSwitchBar().setChecked(false);
        getSwitchBar().setEnabled(false);
        if (ConnectionsUtils.isSupportPco()) {}
    }
}
