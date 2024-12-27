package com.samsung.android.settings.nfc;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.NfcAdapter;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.android.settings.SettingsActivity;
import com.android.settings.SettingsPreferenceFragment;

import com.samsung.android.knox.ucm.configurator.UniversalCredentialManager;
import com.samsung.android.nfc.adapter.SamsungNfcAdapter;
import com.samsung.android.settings.logging.LoggingHelper;
import com.samsung.android.settings.widget.SecRadioButtonPreference;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class NfcAdvancedRoutingSetting extends SettingsPreferenceFragment
        implements SecRadioButtonPreference.OnClickListener {
    public IntentFilter mFilter;
    public NfcAdapter mNfcAdapter;
    public SamsungNfcAdapter mSamsungNfcAdapter;
    public SettingsActivity mActivity = null;
    public SecRadioButtonPreference mSimPreference = null;
    public SecRadioButtonPreference mDhPreference = null;
    public SecRadioButtonPreference mEsePreference = null;
    public SecRadioButtonPreference mAutoPreference = null;
    public List currentRouteOptions = new ArrayList();
    public final AnonymousClass1 mReceiver =
            new BroadcastReceiver() { // from class:
                                      // com.samsung.android.settings.nfc.NfcAdvancedRoutingSetting.1
                @Override // android.content.BroadcastReceiver
                public final void onReceive(Context context, Intent intent) {
                    String action = intent.getAction();
                    if (NfcAdvancedRoutingSetting.this.mActivity != null) {
                        if ("android.nfc.action.ADAPTER_STATE_CHANGED".equals(action)) {
                            if (intent.getIntExtra("android.nfc.extra.ADAPTER_STATE", -1) == 1) {
                                NfcAdvancedRoutingSetting.this.mActivity.onBackPressed();
                            }
                        } else if ("android.intent.action.USER_SWITCHED".equals(action)) {
                            NfcAdvancedRoutingSetting.this.mActivity.onBackPressed();
                        }
                    }
                }
            };

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 3657;
    }

    public final void handleRoutingState(String str) {
        if (str.equalsIgnoreCase(UniversalCredentialManager.APPLET_FORM_FACTOR_SIM)) {
            SecRadioButtonPreference secRadioButtonPreference = this.mSimPreference;
            if (secRadioButtonPreference != null) {
                secRadioButtonPreference.setChecked(true);
            }
            SecRadioButtonPreference secRadioButtonPreference2 = this.mDhPreference;
            if (secRadioButtonPreference2 != null) {
                secRadioButtonPreference2.setChecked(false);
            }
            SecRadioButtonPreference secRadioButtonPreference3 = this.mEsePreference;
            if (secRadioButtonPreference3 != null) {
                secRadioButtonPreference3.setChecked(false);
            }
            SecRadioButtonPreference secRadioButtonPreference4 = this.mAutoPreference;
            if (secRadioButtonPreference4 != null) {
                secRadioButtonPreference4.setChecked(false);
                return;
            }
            return;
        }
        if (str.equalsIgnoreCase("DH")) {
            SecRadioButtonPreference secRadioButtonPreference5 = this.mSimPreference;
            if (secRadioButtonPreference5 != null) {
                secRadioButtonPreference5.setChecked(false);
            }
            SecRadioButtonPreference secRadioButtonPreference6 = this.mDhPreference;
            if (secRadioButtonPreference6 != null) {
                secRadioButtonPreference6.setChecked(true);
            }
            SecRadioButtonPreference secRadioButtonPreference7 = this.mEsePreference;
            if (secRadioButtonPreference7 != null) {
                secRadioButtonPreference7.setChecked(false);
            }
            SecRadioButtonPreference secRadioButtonPreference8 = this.mAutoPreference;
            if (secRadioButtonPreference8 != null) {
                secRadioButtonPreference8.setChecked(false);
                return;
            }
            return;
        }
        if (str.equalsIgnoreCase("ESE")) {
            SecRadioButtonPreference secRadioButtonPreference9 = this.mSimPreference;
            if (secRadioButtonPreference9 != null) {
                secRadioButtonPreference9.setChecked(false);
            }
            SecRadioButtonPreference secRadioButtonPreference10 = this.mDhPreference;
            if (secRadioButtonPreference10 != null) {
                secRadioButtonPreference10.setChecked(false);
            }
            SecRadioButtonPreference secRadioButtonPreference11 = this.mEsePreference;
            if (secRadioButtonPreference11 != null) {
                secRadioButtonPreference11.setChecked(true);
            }
            SecRadioButtonPreference secRadioButtonPreference12 = this.mAutoPreference;
            if (secRadioButtonPreference12 != null) {
                secRadioButtonPreference12.setChecked(false);
                return;
            }
            return;
        }
        SecRadioButtonPreference secRadioButtonPreference13 = this.mSimPreference;
        if (secRadioButtonPreference13 != null) {
            secRadioButtonPreference13.setChecked(false);
        }
        SecRadioButtonPreference secRadioButtonPreference14 = this.mDhPreference;
        if (secRadioButtonPreference14 != null) {
            secRadioButtonPreference14.setChecked(false);
        }
        SecRadioButtonPreference secRadioButtonPreference15 = this.mEsePreference;
        if (secRadioButtonPreference15 != null) {
            secRadioButtonPreference15.setChecked(false);
        }
        SecRadioButtonPreference secRadioButtonPreference16 = this.mAutoPreference;
        if (secRadioButtonPreference16 != null) {
            secRadioButtonPreference16.setChecked(true);
        }
    }

    @Override // com.android.settings.SettingsPreferenceFragment, androidx.fragment.app.Fragment
    public final void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);
        this.mActivity = (SettingsActivity) getActivity();
    }

    /* JADX WARN: Removed duplicated region for block: B:81:0x0173 A[Catch: Exception -> 0x0184, TryCatch #2 {Exception -> 0x0184, blocks: (B:69:0x0131, B:71:0x0144, B:73:0x014a, B:76:0x0153, B:79:0x015e, B:81:0x0173, B:84:0x0180), top: B:68:0x0131 }] */
    @Override // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void onCreate(android.os.Bundle r10) {
        /*
            Method dump skipped, instructions count: 416
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.samsung.android.settings.nfc.NfcAdvancedRoutingSetting.onCreate(android.os.Bundle):void");
    }

    @Override // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() != 16908332) {
            return super.onOptionsItemSelected(menuItem);
        }
        finish();
        return true;
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onPause() {
        super.onPause();
        try {
            this.mActivity.unregisterReceiver(this.mReceiver);
        } catch (Exception unused) {
            if (NfcSettings.DBG) {
                Log.e("NfcAdvancedRoutingSetting", "Exception occurred - onPause");
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:15:0x00b1  */
    /* JADX WARN: Removed duplicated region for block: B:18:0x00d3 A[Catch: Exception -> 0x00e1, TryCatch #2 {Exception -> 0x00e1, blocks: (B:3:0x0005, B:5:0x0010, B:6:0x0023, B:9:0x0034, B:11:0x0039, B:12:0x0045, B:13:0x00a5, B:16:0x00c5, B:18:0x00d3, B:21:0x00d9, B:23:0x00b3, B:26:0x00bb, B:32:0x0042, B:33:0x0069, B:35:0x006e, B:36:0x007a, B:38:0x0084, B:39:0x009d, B:42:0x0077), top: B:2:0x0005, inners: #0, #1 }] */
    /* JADX WARN: Removed duplicated region for block: B:21:0x00d9 A[Catch: Exception -> 0x00e1, TRY_LEAVE, TryCatch #2 {Exception -> 0x00e1, blocks: (B:3:0x0005, B:5:0x0010, B:6:0x0023, B:9:0x0034, B:11:0x0039, B:12:0x0045, B:13:0x00a5, B:16:0x00c5, B:18:0x00d3, B:21:0x00d9, B:23:0x00b3, B:26:0x00bb, B:32:0x0042, B:33:0x0069, B:35:0x006e, B:36:0x007a, B:38:0x0084, B:39:0x009d, B:42:0x0077), top: B:2:0x0005, inners: #0, #1 }] */
    /* JADX WARN: Removed duplicated region for block: B:23:0x00b3 A[Catch: Exception -> 0x00e1, TryCatch #2 {Exception -> 0x00e1, blocks: (B:3:0x0005, B:5:0x0010, B:6:0x0023, B:9:0x0034, B:11:0x0039, B:12:0x0045, B:13:0x00a5, B:16:0x00c5, B:18:0x00d3, B:21:0x00d9, B:23:0x00b3, B:26:0x00bb, B:32:0x0042, B:33:0x0069, B:35:0x006e, B:36:0x007a, B:38:0x0084, B:39:0x009d, B:42:0x0077), top: B:2:0x0005, inners: #0, #1 }] */
    @Override // com.samsung.android.settings.widget.SecRadioButtonPreference.OnClickListener
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void onRadioButtonClicked(
            com.samsung.android.settings.widget.SecRadioButtonPreference r10) {
        /*
            r9 = this;
            java.lang.String r0 = "NfcAdvancedRoutingSetting"
            java.lang.String r1 = "set "
            java.lang.String r2 = r10.getKey()     // Catch: java.lang.Exception -> Le1
            r9.handleRoutingState(r2)     // Catch: java.lang.Exception -> Le1
            boolean r2 = com.samsung.android.settings.nfc.NfcSettings.DBG     // Catch: java.lang.Exception -> Le1
            if (r2 == 0) goto L23
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch: java.lang.Exception -> Le1
            r2.<init>(r1)     // Catch: java.lang.Exception -> Le1
            java.lang.String r1 = r10.getKey()     // Catch: java.lang.Exception -> Le1
            r2.append(r1)     // Catch: java.lang.Exception -> Le1
            java.lang.String r1 = r2.toString()     // Catch: java.lang.Exception -> Le1
            android.util.Log.d(r0, r1)     // Catch: java.lang.Exception -> Le1
        L23:
            java.lang.String r1 = r10.getKey()     // Catch: java.lang.Exception -> Le1
            java.lang.String r2 = "AUTO_SELECT"
            boolean r1 = r1.equals(r2)     // Catch: java.lang.Exception -> Le1
            java.lang.String r2 = "DH"
            r3 = 2
            r4 = 0
            r5 = 1
            if (r1 == 0) goto L69
            com.samsung.android.nfc.adapter.SamsungNfcAdapter r1 = r9.mSamsungNfcAdapter     // Catch: java.lang.Exception -> Le1
            r1.getClass()     // Catch: java.lang.Exception -> Le1
            com.samsung.android.nfc.adapter.ISamsungNfcAdapter r1 = com.samsung.android.nfc.adapter.SamsungNfcAdapter.sService     // Catch: android.os.RemoteException -> L41 java.lang.Exception -> Le1
            com.samsung.android.nfc.adapter.ISamsungNfcAdapter$Stub$Proxy r1 = (com.samsung.android.nfc.adapter.ISamsungNfcAdapter.Stub.Proxy) r1     // Catch: android.os.RemoteException -> L41 java.lang.Exception -> Le1
            r1.setAutoChangeStatus(r5)     // Catch: android.os.RemoteException -> L41 java.lang.Exception -> Le1
            goto L45
        L41:
            r1 = move-exception
            com.samsung.android.nfc.adapter.SamsungNfcAdapter.attemptDeadServiceRecovery(r1)     // Catch: java.lang.Exception -> Le1
        L45:
            com.samsung.android.nfc.adapter.SamsungNfcAdapter r1 = r9.mSamsungNfcAdapter     // Catch: java.lang.Exception -> Le1
            r1.getClass()     // Catch: java.lang.Exception -> Le1
            java.util.List r1 = com.samsung.android.nfc.adapter.SamsungNfcAdapter.getDefaultRoutingDestination()     // Catch: java.lang.Exception -> Le1
            com.samsung.android.nfc.adapter.SamsungNfcAdapter r6 = r9.mSamsungNfcAdapter     // Catch: java.lang.Exception -> Le1
            java.lang.Object r7 = r1.get(r4)     // Catch: java.lang.Exception -> Le1
            java.lang.String r7 = (java.lang.String) r7     // Catch: java.lang.Exception -> Le1
            java.lang.Object r8 = r1.get(r5)     // Catch: java.lang.Exception -> Le1
            java.lang.String r8 = (java.lang.String) r8     // Catch: java.lang.Exception -> Le1
            java.lang.Object r1 = r1.get(r3)     // Catch: java.lang.Exception -> Le1
            java.lang.String r1 = (java.lang.String) r1     // Catch: java.lang.Exception -> Le1
            r6.getClass()     // Catch: java.lang.Exception -> Le1
            com.samsung.android.nfc.adapter.SamsungNfcAdapter.setDefaultRoutingDestination(r7, r8, r1)     // Catch: java.lang.Exception -> Le1
            goto La5
        L69:
            com.samsung.android.nfc.adapter.SamsungNfcAdapter r1 = r9.mSamsungNfcAdapter     // Catch: java.lang.Exception -> Le1
            r1.getClass()     // Catch: java.lang.Exception -> Le1
            com.samsung.android.nfc.adapter.ISamsungNfcAdapter r1 = com.samsung.android.nfc.adapter.SamsungNfcAdapter.sService     // Catch: android.os.RemoteException -> L76 java.lang.Exception -> Le1
            com.samsung.android.nfc.adapter.ISamsungNfcAdapter$Stub$Proxy r1 = (com.samsung.android.nfc.adapter.ISamsungNfcAdapter.Stub.Proxy) r1     // Catch: android.os.RemoteException -> L76 java.lang.Exception -> Le1
            r1.setAutoChangeStatus(r4)     // Catch: android.os.RemoteException -> L76 java.lang.Exception -> Le1
            goto L7a
        L76:
            r1 = move-exception
            com.samsung.android.nfc.adapter.SamsungNfcAdapter.attemptDeadServiceRecovery(r1)     // Catch: java.lang.Exception -> Le1
        L7a:
            java.lang.String r1 = r10.getKey()     // Catch: java.lang.Exception -> Le1
            boolean r6 = r1.equals(r2)     // Catch: java.lang.Exception -> Le1
            if (r6 == 0) goto L9d
            com.samsung.android.nfc.adapter.SamsungNfcAdapter r6 = r9.mSamsungNfcAdapter     // Catch: java.lang.Exception -> Le1
            java.util.List r7 = r9.currentRouteOptions     // Catch: java.lang.Exception -> Le1
            java.lang.Object r7 = r7.get(r5)     // Catch: java.lang.Exception -> Le1
            java.lang.String r7 = (java.lang.String) r7     // Catch: java.lang.Exception -> Le1
            java.util.List r8 = r9.currentRouteOptions     // Catch: java.lang.Exception -> Le1
            java.lang.Object r8 = r8.get(r3)     // Catch: java.lang.Exception -> Le1
            java.lang.String r8 = (java.lang.String) r8     // Catch: java.lang.Exception -> Le1
            r6.getClass()     // Catch: java.lang.Exception -> Le1
            com.samsung.android.nfc.adapter.SamsungNfcAdapter.setDefaultRoutingDestination(r1, r7, r8)     // Catch: java.lang.Exception -> Le1
            goto La5
        L9d:
            com.samsung.android.nfc.adapter.SamsungNfcAdapter r6 = r9.mSamsungNfcAdapter     // Catch: java.lang.Exception -> Le1
            r6.getClass()     // Catch: java.lang.Exception -> Le1
            com.samsung.android.nfc.adapter.SamsungNfcAdapter.setDefaultRoutingDestination(r1, r1, r1)     // Catch: java.lang.Exception -> Le1
        La5:
            java.lang.String r10 = r10.getKey()     // Catch: java.lang.Exception -> Le1
            java.lang.String r1 = "SIM"
            boolean r1 = r10.equalsIgnoreCase(r1)     // Catch: java.lang.Exception -> Le1
            if (r1 == 0) goto Lb3
            r3 = r4
            goto Lc5
        Lb3:
            boolean r1 = r10.equalsIgnoreCase(r2)     // Catch: java.lang.Exception -> Le1
            if (r1 == 0) goto Lbb
            r3 = r5
            goto Lc5
        Lbb:
            java.lang.String r1 = "ESE"
            boolean r10 = r10.equalsIgnoreCase(r1)     // Catch: java.lang.Exception -> Le1
            if (r10 == 0) goto Lc4
            goto Lc5
        Lc4:
            r3 = 3
        Lc5:
            long r1 = (long) r3     // Catch: java.lang.Exception -> Le1
            r10 = 3657(0xe49, float:5.125E-42)
            r3 = 3658(0xe4a, float:5.126E-42)
            com.samsung.android.settings.logging.LoggingHelper.insertEventLogging(r10, r3, r1)     // Catch: java.lang.Exception -> Le1
            androidx.fragment.app.FragmentActivity r10 = r9.getActivity()     // Catch: java.lang.Exception -> Le1
            if (r10 != 0) goto Ld9
            java.lang.String r9 = "context null, return"
            android.util.Log.d(r0, r9)     // Catch: java.lang.Exception -> Le1
            return
        Ld9:
            androidx.fragment.app.FragmentActivity r9 = r9.getActivity()     // Catch: java.lang.Exception -> Le1
            r9.onBackPressed()     // Catch: java.lang.Exception -> Le1
            goto Lea
        Le1:
            boolean r9 = com.samsung.android.settings.nfc.NfcSettings.DBG
            if (r9 == 0) goto Lea
            java.lang.String r9 = "Exception occurred - setDefaultRoutingDestination"
            android.util.Log.e(r0, r9)
        Lea:
            return
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.samsung.android.settings.nfc.NfcAdvancedRoutingSetting.onRadioButtonClicked(com.samsung.android.settings.widget.SecRadioButtonPreference):void");
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onResume() {
        super.onResume();
        IntentFilter intentFilter = new IntentFilter("android.nfc.action.ADAPTER_STATE_CHANGED");
        this.mFilter = intentFilter;
        intentFilter.addAction("android.intent.action.USER_SWITCHED");
        this.mActivity.registerReceiver(this.mReceiver, this.mFilter);
        NfcAdapter nfcAdapter = this.mNfcAdapter;
        if (nfcAdapter != null) {
            if (nfcAdapter.getAdapterState() != 1) {
                LoggingHelper.insertFlowLogging(3657);
                return;
            }
            this.mActivity.onBackPressed();
            if (NfcSettings.DBG) {
                Log.d("NfcAdvancedRoutingSetting", "NFC is off. Close advance menu");
            }
        }
    }
}
