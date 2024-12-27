package com.android.settings.wifi.tether;

import android.net.wifi.SoftApConfiguration;
import android.os.Bundle;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStore;
import androidx.lifecycle.viewmodel.CreationExtras;
import androidx.lifecycle.viewmodel.ViewModelProviderImpl;

import com.android.settings.R;
import com.android.settings.applications.intentpicker.ProgressDialogFragment$$ExternalSyntheticOutline0;
import com.android.settings.dashboard.DashboardFragment;
import com.android.settings.overlay.FeatureFactoryImpl;
import com.android.settings.wifi.factory.WifiFeatureProvider;
import com.android.settings.wifi.repository.WifiHotspotRepository;
import com.android.settingslib.widget.SelectorWithWidgetPreference;

import com.samsung.android.knox.EnterpriseContainerCallback;

import kotlin.jvm.JvmClassMappingKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.KClass;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class WifiHotspotSecuritySettings extends DashboardFragment
        implements SelectorWithWidgetPreference.OnClickListener {
    public WifiHotspotSecurityViewModel mWifiHotspotSecurityViewModel;

    public WifiHotspotSecuritySettings() {
        new HashMap();
    }

    public static void log(String str) {
        FeatureFactoryImpl featureFactoryImpl = FeatureFactoryImpl._factory;
        if (featureFactoryImpl == null) {
            throw new UnsupportedOperationException("No feature factory configured");
        }
        featureFactoryImpl.getWifiFeatureProvider().verboseLog("WifiHotspotSecuritySettings", str);
    }

    @Override // com.android.settings.dashboard.DashboardFragment
    /* renamed from: getLogTag */
    public final String getTAG() {
        return "WifiHotspotSecuritySettings";
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return EnterpriseContainerCallback.CONTAINER_VERIFY_PWD_SUCCESSFUL;
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment
    public final int getPreferenceScreenResId() {
        return R.xml.wifi_hotspot_security;
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        FeatureFactoryImpl featureFactoryImpl = FeatureFactoryImpl._factory;
        if (featureFactoryImpl == null) {
            throw new UnsupportedOperationException("No feature factory configured");
        }
        WifiFeatureProvider wifiFeatureProvider = featureFactoryImpl.getWifiFeatureProvider();
        wifiFeatureProvider.getClass();
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
        KClass kotlinClass = JvmClassMappingKt.getKotlinClass(WifiHotspotSecurityViewModel.class);
        String qualifiedName = kotlinClass.getQualifiedName();
        if (qualifiedName == null) {
            throw new IllegalArgumentException(
                    "Local and anonymous classes can not be ViewModels".toString());
        }
        WifiHotspotSecurityViewModel wifiHotspotSecurityViewModel =
                (WifiHotspotSecurityViewModel)
                        m.getViewModel$lifecycle_viewmodel_release(
                                kotlinClass,
                                "androidx.lifecycle.ViewModelProvider.DefaultKey:"
                                        .concat(qualifiedName));
        wifiFeatureProvider.verboseLog(
                "WifiFeatureProvider",
                "getWifiHotspotSecurityViewModel():" + wifiHotspotSecurityViewModel);
        this.mWifiHotspotSecurityViewModel = wifiHotspotSecurityViewModel;
        if (wifiHotspotSecurityViewModel.mViewInfoListData == null) {
            wifiHotspotSecurityViewModel.mViewInfoListData = new MutableLiveData();
            wifiHotspotSecurityViewModel.updateViewItemListData();
            WifiHotspotSecurityViewModel.log$3(
                    "getViewItemListData(), mViewInfoListData:"
                            + wifiHotspotSecurityViewModel.mViewInfoListData.getValue());
        }
        MutableLiveData mutableLiveData = wifiHotspotSecurityViewModel.mViewInfoListData;
        final int i = 0;
        mutableLiveData.observe(
                this,
                new Observer(
                        this) { // from class:
                                // com.android.settings.wifi.tether.WifiHotspotSecuritySettings$$ExternalSyntheticLambda0
                    public final /* synthetic */ WifiHotspotSecuritySettings f$0;

                    {
                        this.f$0 = this;
                    }

                    @Override // androidx.lifecycle.Observer
                    public final void onChanged(Object obj) {
                        switch (i) {
                            case 0:
                                List<WifiHotspotSecurityViewModel.ViewItem> list = (List) obj;
                                WifiHotspotSecuritySettings wifiHotspotSecuritySettings = this.f$0;
                                wifiHotspotSecuritySettings.getClass();
                                WifiHotspotSecuritySettings.log(
                                        "onViewItemListDataChanged(), viewItems:" + list);
                                for (WifiHotspotSecurityViewModel.ViewItem viewItem : list) {
                                    SelectorWithWidgetPreference selectorWithWidgetPreference =
                                            (SelectorWithWidgetPreference)
                                                    wifiHotspotSecuritySettings.findPreference(
                                                            viewItem.mKey);
                                    if (selectorWithWidgetPreference != null) {
                                        boolean z = selectorWithWidgetPreference.mChecked;
                                        boolean z2 = viewItem.mIsChecked;
                                        if (z != z2) {
                                            selectorWithWidgetPreference.setChecked(z2);
                                        }
                                        boolean isEnabled =
                                                selectorWithWidgetPreference.isEnabled();
                                        boolean z3 = viewItem.mIsEnabled;
                                        if (isEnabled != z3) {
                                            selectorWithWidgetPreference.setEnabled(z3);
                                            if (viewItem.mIsEnabled) {
                                                selectorWithWidgetPreference.setSummary(
                                                        (CharSequence) null);
                                            } else {
                                                selectorWithWidgetPreference.setSummary(
                                                        R.string
                                                                .wifi_hotspot_security_summary_unavailable);
                                            }
                                        }
                                    }
                                }
                                break;
                            default:
                                this.f$0.onRestartingChanged((Boolean) obj);
                                break;
                        }
                    }
                });
        Iterator it = ((List) mutableLiveData.getValue()).iterator();
        while (it.hasNext()) {
            ((SelectorWithWidgetPreference)
                                    findPreference(
                                            ((WifiHotspotSecurityViewModel.ViewItem) it.next())
                                                    .mKey))
                            .mListener =
                    this;
        }
        final int i2 = 1;
        this.mWifiHotspotSecurityViewModel
                .mWifiHotspotRepository
                .getRestarting()
                .observe(
                        this,
                        new Observer(
                                this) { // from class:
                                        // com.android.settings.wifi.tether.WifiHotspotSecuritySettings$$ExternalSyntheticLambda0
                            public final /* synthetic */ WifiHotspotSecuritySettings f$0;

                            {
                                this.f$0 = this;
                            }

                            @Override // androidx.lifecycle.Observer
                            public final void onChanged(Object obj) {
                                switch (i2) {
                                    case 0:
                                        List<WifiHotspotSecurityViewModel.ViewItem> list =
                                                (List) obj;
                                        WifiHotspotSecuritySettings wifiHotspotSecuritySettings =
                                                this.f$0;
                                        wifiHotspotSecuritySettings.getClass();
                                        WifiHotspotSecuritySettings.log(
                                                "onViewItemListDataChanged(), viewItems:" + list);
                                        for (WifiHotspotSecurityViewModel.ViewItem viewItem :
                                                list) {
                                            SelectorWithWidgetPreference
                                                    selectorWithWidgetPreference =
                                                            (SelectorWithWidgetPreference)
                                                                    wifiHotspotSecuritySettings
                                                                            .findPreference(
                                                                                    viewItem.mKey);
                                            if (selectorWithWidgetPreference != null) {
                                                boolean z = selectorWithWidgetPreference.mChecked;
                                                boolean z2 = viewItem.mIsChecked;
                                                if (z != z2) {
                                                    selectorWithWidgetPreference.setChecked(z2);
                                                }
                                                boolean isEnabled =
                                                        selectorWithWidgetPreference.isEnabled();
                                                boolean z3 = viewItem.mIsEnabled;
                                                if (isEnabled != z3) {
                                                    selectorWithWidgetPreference.setEnabled(z3);
                                                    if (viewItem.mIsEnabled) {
                                                        selectorWithWidgetPreference.setSummary(
                                                                (CharSequence) null);
                                                    } else {
                                                        selectorWithWidgetPreference.setSummary(
                                                                R.string
                                                                        .wifi_hotspot_security_summary_unavailable);
                                                    }
                                                }
                                            }
                                        }
                                        break;
                                    default:
                                        this.f$0.onRestartingChanged((Boolean) obj);
                                        break;
                                }
                            }
                        });
    }

    @Override // com.android.settingslib.widget.SelectorWithWidgetPreference.OnClickListener
    public final void onRadioButtonClicked(
            SelectorWithWidgetPreference selectorWithWidgetPreference) {
        String key = selectorWithWidgetPreference.getKey();
        log("onRadioButtonClicked(), key:" + key);
        if (key.isEmpty()) {
            return;
        }
        WifiHotspotSecurityViewModel wifiHotspotSecurityViewModel =
                this.mWifiHotspotSecurityViewModel;
        wifiHotspotSecurityViewModel.getClass();
        WifiHotspotSecurityViewModel.log$3("handleRadioButtonClicked(), key:".concat(key));
        for (Map.Entry entry : ((HashMap) wifiHotspotSecurityViewModel.mViewItemMap).entrySet()) {
            if (((WifiHotspotSecurityViewModel.ViewItem) entry.getValue()).mKey.equals(key)) {
                int intValue = ((Integer) entry.getKey()).intValue();
                WifiHotspotRepository wifiHotspotRepository =
                        wifiHotspotSecurityViewModel.mWifiHotspotRepository;
                wifiHotspotRepository.getClass();
                WifiHotspotRepository.log("setSecurityType():" + intValue);
                if (wifiHotspotRepository.mSecurityType == null) {
                    wifiHotspotRepository.getSecurityType();
                }
                if (intValue
                        == ((Integer) wifiHotspotRepository.mSecurityType.getValue()).intValue()) {
                    Log.w(
                            "WifiHotspotRepository",
                            "setSecurityType() is no changed! mSecurityType:"
                                    + wifiHotspotRepository.mSecurityType.getValue());
                    return;
                }
                SoftApConfiguration softApConfiguration =
                        wifiHotspotRepository.mWifiManager.getSoftApConfiguration();
                if (softApConfiguration == null) {
                    wifiHotspotRepository.mSecurityType.setValue(0);
                    Log.e(
                            "WifiHotspotRepository",
                            "setSecurityType(), WifiManager#getSoftApConfiguration() return null!");
                    return;
                } else {
                    SoftApConfiguration.Builder builder =
                            new SoftApConfiguration.Builder(softApConfiguration);
                    builder.setPassphrase(
                            intValue == 0
                                    ? null
                                    : wifiHotspotRepository.generatePassword(softApConfiguration),
                            intValue);
                    wifiHotspotRepository.setSoftApConfiguration(builder.build());
                    wifiHotspotRepository.mWifiManager
                            .queryLastConfiguredTetheredApPassphraseSinceBoot(
                                    wifiHotspotRepository.mAppContext.getMainExecutor(),
                                    wifiHotspotRepository.mLastPasswordListener);
                    return;
                }
            }
        }
    }

    public void onRestartingChanged(Boolean bool) {
        log("onRestartingChanged(), restarting:" + bool);
        setLoading(bool.booleanValue(), false);
    }
}
