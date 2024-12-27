package com.android.settings.nfc;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.UserHandle;
import android.os.UserManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.preference.PreferenceScreen;

import com.android.settings.R;
import com.android.settings.dashboard.DashboardFragment;
import com.android.settings.search.BaseSearchIndexProvider;
import com.android.settingslib.core.lifecycle.Lifecycle;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class PaymentSettings extends DashboardFragment {
    public static final BaseSearchIndexProvider SEARCH_INDEX_DATA_PROVIDER =
            new AnonymousClass1(R.xml.nfc_payment_settings);
    public PaymentBackend mPaymentBackend;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.android.settings.nfc.PaymentSettings$1, reason: invalid class name */
    public final class AnonymousClass1 extends BaseSearchIndexProvider {
        @Override // com.android.settings.search.BaseSearchIndexProvider
        public final boolean isPageSearchEnabled(Context context) {
            if (((UserManager) context.getSystemService(UserManager.class))
                    .getUserInfo(UserHandle.myUserId())
                    .isGuest()) {
                return false;
            }
            return context.getPackageManager().hasSystemFeature("android.hardware.nfc");
        }
    }

    @Override // com.android.settings.dashboard.DashboardFragment
    public final List createPreferenceControllers(Context context) {
        Lifecycle settingsLifecycle = getSettingsLifecycle();
        ArrayList arrayList = new ArrayList();
        NfcDefaultPaymentPreferenceController nfcDefaultPaymentPreferenceController =
                new NfcDefaultPaymentPreferenceController(context);
        nfcDefaultPaymentPreferenceController.mContext = context;
        nfcDefaultPaymentPreferenceController.mPaymentBackend = new PaymentBackend(context);
        if (settingsLifecycle != null) {
            settingsLifecycle.addObserver(nfcDefaultPaymentPreferenceController);
        }
        arrayList.add(nfcDefaultPaymentPreferenceController);
        return arrayList;
    }

    @Override // com.android.settings.dashboard.DashboardFragment
    /* renamed from: getLogTag */
    public final String getTAG() {
        return "PaymentSettings";
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 70;
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment
    public final int getPreferenceScreenResId() {
        return R.xml.nfc_payment_settings;
    }

    public boolean isShowEmptyImage(PreferenceScreen preferenceScreen) {
        for (int i = 0; i < preferenceScreen.getPreferenceCount(); i++) {
            if (preferenceScreen.getPreference(i).isVisible()) {
                return false;
            }
        }
        return true;
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onAttach(Context context) {
        super.onAttach(context);
        this.mPaymentBackend = new PaymentBackend(getActivity());
        setHasOptionsMenu(true);
        ((NfcPaymentPreferenceController) use(NfcPaymentPreferenceController.class))
                .setPaymentBackend(this.mPaymentBackend);
        ((NfcForegroundPreferenceController) use(NfcForegroundPreferenceController.class))
                .setPaymentBackend(this.mPaymentBackend);
    }

    @Override // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
        super.onCreateOptionsMenu(menu, menuInflater);
        MenuItem add = menu.add(R.string.nfc_payment_how_it_works);
        add.setIntent(new Intent(getActivity(), (Class<?>) HowItWorks.class));
        add.setShowAsActionFlags(0);
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onPause() {
        super.onPause();
        this.mPaymentBackend.mSettingsPackageMonitor.unregister();
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onResume() {
        super.onResume();
        PaymentBackend paymentBackend = this.mPaymentBackend;
        Context context = paymentBackend.mContext;
        paymentBackend.mSettingsPackageMonitor.register(context, context.getMainLooper(), false);
        paymentBackend.refresh();
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onViewCreated(View view, Bundle bundle) {
        super.onViewCreated(view, bundle);
        if (isShowEmptyImage(getPreferenceScreen())) {
            ((ViewGroup) view.findViewById(android.R.id.list_container))
                    .addView(
                            getActivity()
                                    .getLayoutInflater()
                                    .inflate(R.layout.nfc_payment_empty, (ViewGroup) null, false));
        }
    }
}
