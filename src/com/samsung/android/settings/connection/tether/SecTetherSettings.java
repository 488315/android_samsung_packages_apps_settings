package com.samsung.android.settings.connection.tether;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.UserHandle;
import android.provider.SearchIndexableResource;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.compose.foundation.layout.RowColumnMeasurePolicyKt$$ExternalSyntheticOutline0;
import androidx.lifecycle.Observer;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStore;
import androidx.lifecycle.viewmodel.CreationExtras;
import androidx.lifecycle.viewmodel.ViewModelProviderImpl;
import androidx.preference.Preference$$ExternalSyntheticOutline0;

import com.android.settings.MainClearConfirm$$ExternalSyntheticOutline0;
import com.android.settings.R;
import com.android.settings.applications.intentpicker.ProgressDialogFragment$$ExternalSyntheticOutline0;
import com.android.settings.core.OnActivityResultListener;
import com.android.settings.dashboard.RestrictedDashboardFragment;
import com.android.settings.datausage.DataSaverBackend;
import com.android.settings.search.BaseSearchIndexProvider;
import com.android.settingslib.RestrictedLockUtils;
import com.android.settingslib.RestrictedLockUtilsInternal;
import com.android.settingslib.Utils;
import com.android.settingslib.core.AbstractPreferenceController;

import com.samsung.android.settings.Rune;

import kotlin.jvm.JvmClassMappingKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.KClass;

import java.lang.ref.WeakReference;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class SecTetherSettings extends RestrictedDashboardFragment
        implements DataSaverBackend.Listener, OnActivityResultListener {
    public static final BaseSearchIndexProvider SEARCH_INDEX_DATA_PROVIDER = new AnonymousClass1();
    public static int sTetherChoice;
    public SecBluetoothTetherPreferenceController mBluetoothTetherController;
    public DataSaverBackend mDataSaverBackend;
    public SecEthernetTetherPreferenceController mEthernetTetherController;
    public boolean mUnavailable;
    public SecUsbTetherPreferenceController mUsbTetherController;
    public SecWifiTetherPreferenceController mWifiTetherController;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.settings.connection.tether.SecTetherSettings$1, reason: invalid class name */
    public final class AnonymousClass1 extends BaseSearchIndexProvider {
        @Override // com.android.settings.search.BaseSearchIndexProvider,
                  // com.android.settingslib.search.Indexable$SearchIndexProvider
        public final List getXmlResourcesToIndex(Context context) {
            SearchIndexableResource searchIndexableResource = new SearchIndexableResource(context);
            searchIndexableResource.className = SecTetherSettings.class.getName();
            if (Rune.isJapanModel() || Utils.isWifiOnly(context)) {
                searchIndexableResource.xmlResId = R.xml.sec_tether_prefs_for_jpn;
            } else {
                searchIndexableResource.xmlResId = R.xml.sec_tether_prefs;
            }
            return Arrays.asList(searchIndexableResource);
        }

        @Override // com.android.settings.search.BaseSearchIndexProvider
        public final boolean isPageSearchEnabled(Context context) {
            return ((Rune.isJapanModel() || Utils.isWifiOnly(context))
                            ? new SecTabTetherPreferenceController(context)
                            : new SecTetherPreferenceController(context))
                    .isAvailable();
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class OnStartTetheringCallback
            extends ConnectivityManager.OnStartTetheringCallback {
        public final WeakReference mTetherSettings;

        public OnStartTetheringCallback(SecTetherSettings secTetherSettings) {
            this.mTetherSettings = new WeakReference(secTetherSettings);
        }

        public final void onTetheringFailed() {
            SecTetherSettings secTetherSettings = (SecTetherSettings) this.mTetherSettings.get();
            int i = SecTetherSettings.sTetherChoice;
            secTetherSettings.updateState$2$1();
        }

        public final void onTetheringStarted() {
            SecTetherSettings secTetherSettings = (SecTetherSettings) this.mTetherSettings.get();
            int i = SecTetherSettings.sTetherChoice;
            secTetherSettings.updateState$2$1();
        }
    }

    public SecTetherSettings() {
        super("no_config_tethering");
        new Handler();
    }

    @Override // com.android.settings.dashboard.DashboardFragment
    /* renamed from: getLogTag */
    public final String getTAG() {
        return "SecTetherSettings";
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 90;
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment
    public final int getPreferenceScreenResId() {
        return (Rune.isJapanModel() || Utils.isWifiOnly(getContext()))
                ? R.xml.sec_tether_prefs_for_jpn
                : R.xml.sec_tether_prefs;
    }

    @Override // com.android.settings.dashboard.RestrictedDashboardFragment,
              // com.android.settings.dashboard.DashboardFragment, androidx.fragment.app.Fragment
    public final void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        Preference$$ExternalSyntheticOutline0.m(
                RowColumnMeasurePolicyKt$$ExternalSyntheticOutline0.m(
                        "PROVISION Result /requestCode : ",
                        "/resultCode : ",
                        i,
                        i2,
                        "/mTetherChoice : "),
                sTetherChoice,
                "SecTetherSettings");
        if (i == 0) {
            if (intent != null) {
                int intExtra = intent.getIntExtra("TETHER_TYPE", -1);
                if (sTetherChoice == -1) {
                    if (intExtra == -1) {
                        sTetherChoice = 0;
                    } else {
                        sTetherChoice = intExtra;
                    }
                }
            }
            Preference$$ExternalSyntheticOutline0.m(
                    new StringBuilder("delivery choise : "), sTetherChoice, "SecTetherSettings");
            int i3 = sTetherChoice;
            if (i3 == 0) {
                this.mWifiTetherController.onActivityResult(i, i2);
            } else if (i3 == 1) {
                this.mUsbTetherController.onActivityResult(i, i2);
            } else if (i3 == 2) {
                this.mBluetoothTetherController.onActivityResult(i, i2);
            }
        } else if (i == 1001) {
            MainClearConfirm$$ExternalSyntheticOutline0.m(
                    i2,
                    "onActivityResult ETHERNET_TETHER_PROVISION_REQUEST resultCode : ",
                    "SecTetherSettings");
            this.mEthernetTetherController.onActivityResult(i, i2);
        } else if (i == 1) {
            Log.d("SecTetherSettings", "request code : TETHER_HELP_REQUEST");
            this.mWifiTetherController.onActivityResult(i, i2);
        }
        sTetherChoice = -1;
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onAttach(Context context) {
        super.onAttach(context);
        ((SecBluetoothTetherPreferenceController) use(SecBluetoothTetherPreferenceController.class))
                .setHost(this);
        ((SecUsbTetherPreferenceController) use(SecUsbTetherPreferenceController.class))
                .setHost(this);
        ((SecEthernetTetherPreferenceController) use(SecEthernetTetherPreferenceController.class))
                .setHost(this);
        ((SecWifiTetherPreferenceController) use(SecWifiTetherPreferenceController.class))
                .setHost(this);
        ((SecFamilySharingPreferenceController) use(SecFamilySharingPreferenceController.class))
                .setHost(this);
        this.mWifiTetherController =
                (SecWifiTetherPreferenceController) use(SecWifiTetherPreferenceController.class);
        this.mBluetoothTetherController =
                (SecBluetoothTetherPreferenceController)
                        use(SecBluetoothTetherPreferenceController.class);
        this.mUsbTetherController =
                (SecUsbTetherPreferenceController) use(SecUsbTetherPreferenceController.class);
        this.mEthernetTetherController =
                (SecEthernetTetherPreferenceController)
                        use(SecEthernetTetherPreferenceController.class);
        ViewModelStore store = getViewModelStore();
        ViewModelProvider.Factory factory = getDefaultViewModelProviderFactory();
        CreationExtras defaultViewModelCreationExtras = getDefaultViewModelCreationExtras();
        Intrinsics.checkNotNullParameter(store, "store");
        Intrinsics.checkNotNullParameter(factory, "factory");
        ViewModelProviderImpl m =
                ProgressDialogFragment$$ExternalSyntheticOutline0.m(
                        defaultViewModelCreationExtras,
                        "defaultCreationExtras",
                        store,
                        factory,
                        defaultViewModelCreationExtras);
        KClass kotlinClass = JvmClassMappingKt.getKotlinClass(SecTetheringManagerModel.class);
        String qualifiedName = kotlinClass.getQualifiedName();
        if (qualifiedName == null) {
            throw new IllegalArgumentException(
                    "Local and anonymous classes can not be ViewModels".toString());
        }
        Transformations.distinctUntilChanged(
                        ((SecTetheringManagerModel)
                                        m.getViewModel$lifecycle_viewmodel_release(
                                                kotlinClass,
                                                "androidx.lifecycle.ViewModelProvider.DefaultKey:"
                                                        .concat(qualifiedName)))
                                .mTetheredInterfaces)
                .observe(
                        this,
                        new Observer() { // from class:
                                         // com.samsung.android.settings.connection.tether.SecTetherSettings$$ExternalSyntheticLambda0
                            @Override // androidx.lifecycle.Observer
                            public final void onChanged(Object obj) {
                                SecTetherSettings secTetherSettings = SecTetherSettings.this;
                                secTetherSettings.getClass();
                                Log.d(
                                        "SecTetherSettings",
                                        "onTetheredInterfacesChanged() interfaces : "
                                                + ((List) obj).toString());
                                secTetherSettings.updateState$2$1();
                            }
                        });
    }

    @Override // com.android.settings.dashboard.RestrictedDashboardFragment,
              // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        DataSaverBackend dataSaverBackend = new DataSaverBackend(getContext());
        this.mDataSaverBackend = dataSaverBackend;
        onDataSaverChanged(dataSaverBackend.mPolicyManager.getRestrictBackground());
        this.mDataSaverBackend.addListener(this);
        this.mOnlyAvailableForAdmins = true;
        if (!isUiRestricted()) {
            sTetherChoice = -1;
            return;
        }
        this.mUnavailable = true;
        getPreferenceScreen().removeAll();
        Log.d(
                "SecTetherSettings",
                "onCreate: DISALLOW_CONFIG_TETHERING > this activity is finished.");
        RestrictedLockUtils.EnforcedAdmin checkIfRestrictionEnforced =
                RestrictedLockUtilsInternal.checkIfRestrictionEnforced(
                        getActivity(), UserHandle.myUserId(), "no_config_tethering");
        getActivity();
        getActivity()
                .startActivity(
                        RestrictedLockUtils.getShowAdminSupportDetailsIntent(
                                checkIfRestrictionEnforced));
        getActivity().finish();
    }

    @Override // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
        super.onCreateOptionsMenu(menu, menuInflater);
        String str = Rune.COMMON_CONFIG_PACKAGE_NAME_SMART_MANAGER;
        if (("VZW".equals(com.android.settings.Utils.getSalesCode())
                        || "VPP".equals(com.android.settings.Utils.getSalesCode()))
                && com.android.settings.Utils.hasPackage(getActivity(), "com.samsung.helphub")) {
            menuInflater.inflate(R.menu.sec_tethering_help_menu, menu);
        }
    }

    @Override // com.android.settings.datausage.DataSaverBackend.Listener
    public final void onDataSaverChanged(boolean z) {
        Iterator<List<AbstractPreferenceController>> it = getPreferenceControllers().iterator();
        while (it.hasNext()) {
            for (Object obj : it.next()) {
                if (obj instanceof SecTetherControllerInterface) {
                    SecTetherControllerInterface secTetherControllerInterface =
                            (SecTetherControllerInterface) obj;
                    Log.d(
                            "SecTetherSettings",
                            "Calling DataSaver Changed ("
                                    + z
                                    + ") : "
                                    + secTetherControllerInterface);
                    secTetherControllerInterface.onDataSaverChanged(z);
                }
            }
        }
    }

    @Override // com.android.settings.dashboard.RestrictedDashboardFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onDestroy() {
        this.mDataSaverBackend.remListener(this);
        super.onDestroy();
    }

    @Override // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == R.id.tethering_help_menu) {
            String str = Rune.COMMON_CONFIG_PACKAGE_NAME_SMART_MANAGER;
            String salesCode = com.android.settings.Utils.getSalesCode();
            try {
                PackageInfo packageInfo =
                        getPackageManager().getPackageInfo("com.samsung.helphub", 0);
                Intent intent = new Intent("com.samsung.helphub.HELP");
                if (packageInfo != null && packageInfo.versionCode % 10 == 2) {
                    if (!"VZW".equals(salesCode) && !"VPP".equals(salesCode)) {
                        intent.putExtra("helphub:section", "tethering");
                    }
                    intent.putExtra("helphub:section", "tethering_vzw");
                } else if (packageInfo != null && packageInfo.versionCode % 10 == 3) {
                    intent.putExtra("helphub:appid", "tethering");
                }
                startActivity(intent);
            } catch (PackageManager.NameNotFoundException e) {
                Log.e("SecTetherSettings", "onOptionsItemSelected error: " + e);
            }
        }
        return false;
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onStart() {
        super.onStart();
        if (!this.mUnavailable) {
            new OnStartTetheringCallback(this);
            updateState$2$1();
        } else {
            if (!isUiRestrictedByOnlyAdmin()) {
                this.mEmptyTextView.setText(R.string.tethering_settings_not_available);
            }
            getPreferenceScreen().removeAll();
        }
    }

    public final void updateState$2$1() {
        Iterator<List<AbstractPreferenceController>> it = getPreferenceControllers().iterator();
        while (it.hasNext()) {
            for (Object obj : it.next()) {
                if (obj instanceof SecTetherControllerInterface) {
                    SecTetherControllerInterface secTetherControllerInterface =
                            (SecTetherControllerInterface) obj;
                    Log.d(
                            "SecTetherSettings",
                            "Calling updateState : " + secTetherControllerInterface);
                    secTetherControllerInterface.updateController();
                }
            }
        }
    }

    @Override // com.android.settings.datausage.DataSaverBackend.Listener
    public final void onAllowlistStatusChanged(int i, boolean z) {}

    @Override // com.android.settings.datausage.DataSaverBackend.Listener
    public final void onDenylistStatusChanged(int i, boolean z) {}
}
