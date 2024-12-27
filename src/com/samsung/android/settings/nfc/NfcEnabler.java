package com.samsung.android.settings.nfc;

import android.app.ActivityManager;
import android.app.UiModeManager;
import android.app.role.RoleManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.database.ContentObserver;
import android.net.Uri;
import android.nfc.NfcAdapter;
import android.os.Handler;
import android.os.SystemProperties;
import android.os.UserHandle;
import android.provider.Settings;
import android.util.Log;
import android.widget.CompoundButton;
import android.widget.Toast;

import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import androidx.core.util.Pair;
import androidx.preference.Preference;
import androidx.preference.Preference$$ExternalSyntheticOutline0;
import androidx.preference.PreferenceScreen;
import androidx.preference.SecPreference;
import androidx.preference.SecPreferenceScreen;
import androidx.preference.SecPreferenceUtils;
import androidx.preference.SecSwitchPreference;
import androidx.preference.SecSwitchPreferenceScreen;

import com.android.internal.hidden_from_bootclasspath.android.permission.flags.Flags;
import com.android.settings.AirplaneModeEnabler$$ExternalSyntheticOutline0;
import com.android.settings.R;
import com.android.settings.Utils;
import com.android.settings.widget.SettingsMainSwitchBar;

import com.samsung.android.emergencymode.SemEmergencyManager;
import com.samsung.android.feature.SemGateConfig;
import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.settings.Rune;
import com.samsung.android.settings.logging.LoggingHelper;
import com.sec.ims.volte2.data.VolteConstants;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class NfcEnabler
        implements CompoundButton.OnCheckedChangeListener,
                Preference.OnPreferenceChangeListener,
                Preference.OnPreferenceClickListener {
    public static boolean isGpFelicaSupported;
    public int isNFCStateChangeAllowed;
    public final Context mContext;
    public final IntentFilter mIntentFilter;
    public final NfcAdapter mNfcAdapter;
    public final NfcSettingPref mNfcSettingPref;
    public final IntentFilter mSwitchNfcModeFilter;
    public final RoleManager roleManager;
    public final AnonymousClass1 mReceiver =
            new BroadcastReceiver() { // from class: com.samsung.android.settings.nfc.NfcEnabler.1
                @Override // android.content.BroadcastReceiver
                public final void onReceive(Context context, Intent intent) {
                    String action = intent.getAction();
                    if ("android.nfc.action.ADAPTER_STATE_CHANGED".equals(action)) {
                        NfcEnabler.this.handleNfcStateChanged(
                                intent.getIntExtra("android.nfc.extra.ADAPTER_STATE", 1));
                        NfcEnabler.this.handleNfcSecureNfcChanged();
                        return;
                    }
                    if (UiModeManager.SEM_ACTION_ENTER_DESKTOP_MODE.equals(action)) {
                        NfcEnabler.this.getClass();
                        NfcEnabler nfcEnabler = NfcEnabler.this;
                        nfcEnabler.handleNfcStateChanged(nfcEnabler.mNfcAdapter.getAdapterState());
                    } else if (UiModeManager.SEM_ACTION_EXIT_DESKTOP_MODE.equals(action)) {
                        NfcEnabler.this.getClass();
                        NfcEnabler nfcEnabler2 = NfcEnabler.this;
                        nfcEnabler2.handleNfcStateChanged(
                                nfcEnabler2.mNfcAdapter.getAdapterState());
                    } else if ("android.app.action.SWITCH_NFC_MODE_NOTIFICATION".equals(action)) {
                        NfcEnabler nfcEnabler3 = NfcEnabler.this;
                        nfcEnabler3.handleNfcStateChanged(
                                nfcEnabler3.mNfcAdapter.getAdapterState());
                    }
                }
            };
    public final AnonymousClass2 mContentObserver =
            new ContentObserver(
                    new Handler()) { // from class: com.samsung.android.settings.nfc.NfcEnabler.2
                @Override // android.database.ContentObserver
                public final void onChange(boolean z) {
                    if (NfcEnabler.this.isNfcStateChangeAllowed()) {
                        Log.d("NfcEnabler", "Satellite mode is off. Enabling NFC.");
                        SettingsMainSwitchBar settingsMainSwitchBar =
                                NfcEnabler.this.mNfcSettingPref.NfcSettingToggle;
                        if (settingsMainSwitchBar != null) {
                            settingsMainSwitchBar.setEnabled(true);
                        }
                        SecSwitchPreferenceScreen secSwitchPreferenceScreen =
                                NfcEnabler.this.mNfcSettingPref.ConnectionsSettingToggle;
                        if (secSwitchPreferenceScreen != null) {
                            secSwitchPreferenceScreen.setEnabled(true);
                            return;
                        }
                        return;
                    }
                    Log.d("NfcEnabler", "Satellite mode is on. Disabling NFC.");
                    SettingsMainSwitchBar settingsMainSwitchBar2 =
                            NfcEnabler.this.mNfcSettingPref.NfcSettingToggle;
                    if (settingsMainSwitchBar2 != null) {
                        settingsMainSwitchBar2.setEnabled(false);
                    }
                    SecSwitchPreferenceScreen secSwitchPreferenceScreen2 =
                            NfcEnabler.this.mNfcSettingPref.ConnectionsSettingToggle;
                    if (secSwitchPreferenceScreen2 != null) {
                        secSwitchPreferenceScreen2.setEnabled(false);
                    }
                }
            };

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class Builder {
        public SecSwitchPreferenceScreen ConnectionsSettingToggle;
        public SecSwitchPreference NfcJapanPreferenceSecureNfc;
        public SettingsMainSwitchBar NfcSettingToggle;
        public PaymentDropDownPreference PaymentSim;
        public SecSwitchPreference ReaderMode;
        public final Context context;
        public SecPreferenceScreen otherService;
        public PreferenceScreen screenPref;
        public SecPreference tapPay;

        public Builder(Context context) {
            this.context = context;
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class NfcSettingPref {
        public SecSwitchPreferenceScreen ConnectionsSettingToggle;
        public SecSwitchPreference NfcJapanPreferenceSecureNfc;
        public SettingsMainSwitchBar NfcSettingToggle;
        public SecPreferenceScreen OtherService;
        public PaymentDropDownPreference PaymentSim;
        public SecSwitchPreference ReaderMode;
        public PreferenceScreen ScreenPref;
        public SecPreference TapPay;

        public final String toString() {
            StringBuilder sb = new StringBuilder(600);
            StringBuilder sb2 = new StringBuilder("ConnectionsSettingToggle = ");
            sb2.append(this.ConnectionsSettingToggle);
            sb.append(sb2.toString() != null ? 1 : 0);
            StringBuilder sb3 = new StringBuilder("\tNfcSettingToggle = ");
            sb3.append(this.NfcSettingToggle);
            sb.append(sb3.toString() != null ? 1 : 0);
            StringBuilder sb4 = new StringBuilder("\tReaderMode = ");
            sb4.append(this.ReaderMode);
            sb.append(sb4.toString() != null ? 1 : 0);
            StringBuilder sb5 = new StringBuilder("\tPaymentSim = ");
            sb5.append(this.PaymentSim);
            sb.append(sb5.toString() != null ? 1 : 0);
            StringBuilder sb6 = new StringBuilder("\tTapPay = ");
            sb6.append(this.TapPay);
            sb.append(sb6.toString() != null ? 1 : 0);
            StringBuilder sb7 = new StringBuilder("\tOtherService = ");
            sb7.append(this.OtherService);
            sb.append(sb7.toString() != null ? 1 : 0);
            StringBuilder sb8 = new StringBuilder("\tScreenPref = ");
            sb8.append(this.ScreenPref);
            sb.append(sb8.toString() != null ? 1 : 0);
            StringBuilder sb9 = new StringBuilder("\tNfcJapanPreferenceSecureNfc = ");
            sb9.append(this.NfcJapanPreferenceSecureNfc);
            sb.append(sb9.toString() != null ? 1 : 0);
            return sb.toString();
        }
    }

    static {
        Rune.isJapanKDIModel();
    }

    /* JADX WARN: Type inference failed for: r0v0, types: [com.samsung.android.settings.nfc.NfcEnabler$1] */
    /* JADX WARN: Type inference failed for: r0v1, types: [com.samsung.android.settings.nfc.NfcEnabler$2] */
    public NfcEnabler(Builder builder) {
        Context context = builder.context;
        this.mContext = context;
        NfcAdapter defaultAdapter = NfcAdapter.getDefaultAdapter(context);
        this.mNfcAdapter = defaultAdapter;
        if (defaultAdapter == null) {
            return;
        }
        new PaymentBackend(context);
        NfcSettingPref nfcSettingPref = new NfcSettingPref();
        this.mNfcSettingPref = nfcSettingPref;
        nfcSettingPref.ConnectionsSettingToggle = builder.ConnectionsSettingToggle;
        nfcSettingPref.NfcSettingToggle = builder.NfcSettingToggle;
        nfcSettingPref.ReaderMode = builder.ReaderMode;
        nfcSettingPref.PaymentSim = builder.PaymentSim;
        nfcSettingPref.TapPay = builder.tapPay;
        nfcSettingPref.OtherService = builder.otherService;
        nfcSettingPref.ScreenPref = builder.screenPref;
        Log.d("NfcEnabler", "NfcEnabler is created. NfcSettingPref = " + nfcSettingPref);
        boolean isGpFelicaSupported2 = Rune.isGpFelicaSupported(context);
        isGpFelicaSupported = isGpFelicaSupported2;
        if (isGpFelicaSupported2) {
            nfcSettingPref.NfcJapanPreferenceSecureNfc = builder.NfcJapanPreferenceSecureNfc;
        }
        this.roleManager = (RoleManager) context.getSystemService(RoleManager.class);
        this.mIntentFilter = new IntentFilter("android.nfc.action.ADAPTER_STATE_CHANGED");
        this.mSwitchNfcModeFilter =
                new IntentFilter("android.app.action.SWITCH_NFC_MODE_NOTIFICATION");
        handleNfcStateChanged(defaultAdapter.getAdapterState());
    }

    public static int getCLFLockState() {
        int i = 256;
        try {
            FileInputStream fileInputStream = new FileInputStream("/efs/sec_efs/FeliCaLock/01");
            try {
                int read = fileInputStream.read();
                if (read >= 0 && 3 >= read) {
                    i = read;
                }
                fileInputStream.close();
            } finally {
            }
        } catch (FileNotFoundException unused) {
            Log.e("NfcEnabler", "FileNotFoundException!");
            Log.d("NfcEnabler", "result = 0");
            i = 0;
        } catch (IOException unused2) {
            Log.e("NfcEnabler", "IOException! ");
            Log.d("NfcEnabler", "result = 256");
        }
        ListPopupWindow$$ExternalSyntheticOutline0.m1m(i, "result = ", "NfcEnabler");
        return i;
    }

    public final void handleNfcSecureNfcChanged() {
        SecSwitchPreference secSwitchPreference;
        if (this.mNfcAdapter.isSecureNfcSupported() || isGpFelicaSupported) {
            if (NfcSettings.DBG) {
                Log.i("NfcEnabler", "SecureNfcEnabled : " + this.mNfcAdapter.isSecureNfcEnabled());
            }
            int adapterState = this.mNfcAdapter.getAdapterState();
            NfcAdapter nfcAdapter = this.mNfcAdapter;
            if (adapterState == 3) {
                SecSwitchPreference secSwitchPreference2 =
                        this.mNfcSettingPref.NfcJapanPreferenceSecureNfc;
                if (secSwitchPreference2 != null) {
                    secSwitchPreference2.setEnabled(true);
                    this.mNfcSettingPref.NfcJapanPreferenceSecureNfc.setChecked(
                            this.mNfcAdapter.isSecureNfcEnabled());
                    return;
                }
                return;
            }
            int adapterState2 = nfcAdapter.getAdapterState();
            NfcAdapter nfcAdapter2 = this.mNfcAdapter;
            if (adapterState2 == 2) {
                SecSwitchPreference secSwitchPreference3 =
                        this.mNfcSettingPref.NfcJapanPreferenceSecureNfc;
                if (secSwitchPreference3 != null) {
                    secSwitchPreference3.setEnabled(false);
                    this.mNfcSettingPref.NfcJapanPreferenceSecureNfc.setChecked(
                            this.mNfcAdapter.isSecureNfcEnabled());
                    return;
                }
                return;
            }
            int adapterState3 = nfcAdapter2.getAdapterState();
            NfcAdapter nfcAdapter3 = this.mNfcAdapter;
            if ((adapterState3 == 1 || nfcAdapter3.getAdapterState() == 4)
                    && (secSwitchPreference = this.mNfcSettingPref.NfcJapanPreferenceSecureNfc)
                            != null) {
                secSwitchPreference.setEnabled(false);
                this.mNfcSettingPref.NfcJapanPreferenceSecureNfc.setChecked(false);
            }
        }
    }

    public final void handleNfcStateChanged(int i) {
        SecSwitchPreference secSwitchPreference;
        ListPopupWindow$$ExternalSyntheticOutline0.m1m(
                i, "handleNfcStateChanged. newState is = ", "NfcEnabler");
        if (i == 1) {
            updateNfcSettingToggle(false, true);
            if (SemGateConfig.isGateEnabled() && NfcSettings.DBG) {
                Log.i("GATE", "<GATE-M>NFC_OFF</GATE-M>");
            }
            SecSwitchPreference secSwitchPreference2 = this.mNfcSettingPref.ReaderMode;
            if (secSwitchPreference2 != null) {
                secSwitchPreference2.setEnabled(false);
                this.mNfcSettingPref.ReaderMode.setChecked(
                        this.mNfcAdapter.isReaderOptionEnabled());
            }
            SecSwitchPreferenceScreen secSwitchPreferenceScreen =
                    this.mNfcSettingPref.ConnectionsSettingToggle;
            if (secSwitchPreferenceScreen != null) {
                secSwitchPreferenceScreen.setChecked(false);
                this.mNfcSettingPref.ConnectionsSettingToggle.setEnabled(isNfcStateChangeAllowed());
                if (!isGpFelicaSupported) {
                    this.mNfcSettingPref.ConnectionsSettingToggle.setSummary((CharSequence) null);
                    SecSwitchPreferenceScreen secSwitchPreferenceScreen2 =
                            this.mNfcSettingPref.ConnectionsSettingToggle;
                    secSwitchPreferenceScreen2.getClass();
                    SecPreferenceUtils.applySummaryColor(secSwitchPreferenceScreen2, false);
                } else if (Rune.isSupportAndroidBeam(this.mContext)) {
                    this.mNfcSettingPref.ConnectionsSettingToggle.setSummary(
                            R.string.nfc_settings_title_support_p2p);
                }
            }
            PaymentDropDownPreference paymentDropDownPreference = this.mNfcSettingPref.PaymentSim;
            if (paymentDropDownPreference != null) {
                paymentDropDownPreference.setEnabled(false);
                this.mNfcSettingPref.PaymentSim.setSummary((CharSequence) null);
            }
            if (isGpFelicaSupported) {
                SecSwitchPreference secSwitchPreference3 =
                        this.mNfcSettingPref.NfcJapanPreferenceSecureNfc;
                if (secSwitchPreference3 != null) {
                    secSwitchPreference3.setEnabled(false);
                    this.mNfcSettingPref.NfcJapanPreferenceSecureNfc.setChecked(false);
                }
                if (getCLFLockState() != 0) {
                    SecSwitchPreferenceScreen secSwitchPreferenceScreen3 =
                            this.mNfcSettingPref.ConnectionsSettingToggle;
                    if (secSwitchPreferenceScreen3 != null) {
                        secSwitchPreferenceScreen3.setChecked(false);
                        this.mNfcSettingPref.ConnectionsSettingToggle.setEnabled(false);
                    }
                    updateNfcSettingToggle(false, false);
                }
            }
        } else if (i == 2) {
            updateNfcSettingToggle(true, false);
            SecSwitchPreference secSwitchPreference4 = this.mNfcSettingPref.ReaderMode;
            if (secSwitchPreference4 != null) {
                secSwitchPreference4.setEnabled(false);
                this.mNfcSettingPref.ReaderMode.setChecked(
                        this.mNfcAdapter.isReaderOptionEnabled());
                this.mNfcSettingPref.ReaderMode.setSummary(R.string.nfc_reader_mode_desc_common);
            }
            SecSwitchPreferenceScreen secSwitchPreferenceScreen4 =
                    this.mNfcSettingPref.ConnectionsSettingToggle;
            if (secSwitchPreferenceScreen4 != null) {
                secSwitchPreferenceScreen4.setChecked(true);
                this.mNfcSettingPref.ConnectionsSettingToggle.setEnabled(false);
                if (!isGpFelicaSupported) {
                    SecSwitchPreferenceScreen secSwitchPreferenceScreen5 =
                            this.mNfcSettingPref.ConnectionsSettingToggle;
                    secSwitchPreferenceScreen5.getClass();
                    SecPreferenceUtils.applySummaryColor(secSwitchPreferenceScreen5, false);
                }
                if (isGpFelicaSupported) {
                    if (Rune.isSupportAndroidBeam(this.mContext)) {
                        this.mNfcSettingPref.ConnectionsSettingToggle.setSummary(
                                R.string.nfc_settings_title_support_p2p);
                    } else {
                        SecSwitchPreference secSwitchPreference5 =
                                this.mNfcSettingPref.NfcJapanPreferenceSecureNfc;
                        if (secSwitchPreference5 != null) {
                            secSwitchPreference5.setEnabled(false);
                        }
                    }
                }
            }
            PaymentDropDownPreference paymentDropDownPreference2 = this.mNfcSettingPref.PaymentSim;
            if (paymentDropDownPreference2 != null) {
                paymentDropDownPreference2.setEnabled(false);
                this.mNfcSettingPref.PaymentSim.setSummary((CharSequence) null);
            }
        } else if (i == 3) {
            updateNfcSettingToggle(true, true);
            SecPreferenceScreen secPreferenceScreen = this.mNfcSettingPref.OtherService;
            if (secPreferenceScreen != null) {
                secPreferenceScreen.setEnabled(true);
            }
            if (this.mNfcAdapter.isReaderOptionSupported()) {
                if (SemGateConfig.isGateEnabled() && NfcSettings.DBG) {
                    Log.i("GATE", "<GATE-M>NFC_ON</GATE-M>");
                }
                SecSwitchPreference secSwitchPreference6 = this.mNfcSettingPref.ReaderMode;
                if (secSwitchPreference6 != null) {
                    secSwitchPreference6.setEnabled(true);
                    this.mNfcSettingPref.ReaderMode.setChecked(
                            this.mNfcAdapter.isReaderOptionEnabled());
                    this.mNfcSettingPref.ReaderMode.setSummary(
                            R.string.nfc_reader_mode_desc_common);
                }
                SecSwitchPreferenceScreen secSwitchPreferenceScreen6 =
                        this.mNfcSettingPref.ConnectionsSettingToggle;
                if (secSwitchPreferenceScreen6 != null) {
                    secSwitchPreferenceScreen6.setChecked(true);
                    this.mNfcSettingPref.ConnectionsSettingToggle.setEnabled(
                            isNfcStateChangeAllowed());
                    if (!isGpFelicaSupported) {
                        SecSwitchPreferenceScreen secSwitchPreferenceScreen7 =
                                this.mNfcSettingPref.ConnectionsSettingToggle;
                        secSwitchPreferenceScreen7.getClass();
                        SecPreferenceUtils.applySummaryColor(secSwitchPreferenceScreen7, true);
                    }
                    if (this.mNfcAdapter.isReaderOptionEnabled()) {
                        this.mNfcSettingPref.ConnectionsSettingToggle.setSummary(
                                R.string.nfc_reader_mode_title);
                    }
                }
                PaymentDropDownPreference paymentDropDownPreference3 =
                        this.mNfcSettingPref.PaymentSim;
                if (paymentDropDownPreference3 != null) {
                    paymentDropDownPreference3.initSubscriptionDetails();
                }
                if (isGpFelicaSupported
                        && (secSwitchPreference = this.mNfcSettingPref.NfcJapanPreferenceSecureNfc)
                                != null) {
                    secSwitchPreference.setEnabled(true);
                }
            } else {
                SecSwitchPreference secSwitchPreference7 = this.mNfcSettingPref.ReaderMode;
                if (secSwitchPreference7 != null) {
                    secSwitchPreference7.setChecked(this.mNfcAdapter.isReaderOptionEnabled());
                    this.mNfcSettingPref.ReaderMode.setEnabled(true);
                    this.mNfcSettingPref.ReaderMode.setSummary(
                            R.string.nfc_reader_mode_desc_common);
                }
                SecSwitchPreferenceScreen secSwitchPreferenceScreen8 =
                        this.mNfcSettingPref.ConnectionsSettingToggle;
                if (secSwitchPreferenceScreen8 != null) {
                    secSwitchPreferenceScreen8.setChecked(true);
                    this.mNfcSettingPref.ConnectionsSettingToggle.setEnabled(
                            isNfcStateChangeAllowed());
                    this.mNfcSettingPref.ConnectionsSettingToggle.setSummary((CharSequence) null);
                }
                PaymentDropDownPreference paymentDropDownPreference4 =
                        this.mNfcSettingPref.PaymentSim;
                if (paymentDropDownPreference4 != null) {
                    paymentDropDownPreference4.initSubscriptionDetails();
                }
            }
        } else if (i == 4) {
            updateNfcSettingToggle(false, false);
            SecSwitchPreference secSwitchPreference8 = this.mNfcSettingPref.ReaderMode;
            if (secSwitchPreference8 != null) {
                secSwitchPreference8.setEnabled(false);
                this.mNfcSettingPref.ReaderMode.setChecked(
                        this.mNfcAdapter.isReaderOptionEnabled());
                this.mNfcSettingPref.ReaderMode.setSummary(R.string.nfc_reader_mode_desc_common);
            }
            SecSwitchPreferenceScreen secSwitchPreferenceScreen9 =
                    this.mNfcSettingPref.ConnectionsSettingToggle;
            if (secSwitchPreferenceScreen9 != null) {
                secSwitchPreferenceScreen9.setChecked(false);
                this.mNfcSettingPref.ConnectionsSettingToggle.setEnabled(false);
                if (!isGpFelicaSupported) {
                    SecSwitchPreferenceScreen secSwitchPreferenceScreen10 =
                            this.mNfcSettingPref.ConnectionsSettingToggle;
                    secSwitchPreferenceScreen10.getClass();
                    SecPreferenceUtils.applySummaryColor(secSwitchPreferenceScreen10, true);
                }
            }
            PaymentDropDownPreference paymentDropDownPreference5 = this.mNfcSettingPref.PaymentSim;
            if (paymentDropDownPreference5 != null) {
                paymentDropDownPreference5.setEnabled(false);
                this.mNfcSettingPref.PaymentSim.setSummary((CharSequence) null);
            }
        }
        this.isNFCStateChangeAllowed =
                Utils.getEnterprisePolicyEnabled(
                        this.mContext,
                        "content://com.sec.knox.provider2/MiscPolicy",
                        "isNFCStateChangeAllowed",
                        new String[] {"false"});
        Preference$$ExternalSyntheticOutline0.m(
                new StringBuilder("isNFCStateChangeAllowed = "),
                this.isNFCStateChangeAllowed,
                "NfcEnabler");
        if (this.isNFCStateChangeAllowed == 0) {
            Log.e("NfcEnabler", "EDM : NFC state change not allowed.");
            SettingsMainSwitchBar settingsMainSwitchBar = this.mNfcSettingPref.NfcSettingToggle;
            if (settingsMainSwitchBar != null) {
                settingsMainSwitchBar.setEnabled(false);
            }
            SecSwitchPreferenceScreen secSwitchPreferenceScreen11 =
                    this.mNfcSettingPref.ConnectionsSettingToggle;
            if (secSwitchPreferenceScreen11 != null) {
                secSwitchPreferenceScreen11.setEnabled(false);
            }
            SecSwitchPreference secSwitchPreference9 = this.mNfcSettingPref.ReaderMode;
            if (secSwitchPreference9 != null) {
                secSwitchPreference9.setEnabled(false);
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:15:0x0036  */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0034  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean isNfcStateChangeAllowed() {
        /*
            r7 = this;
            java.lang.String r0 = "satellite_mode_enabled"
            android.net.Uri r1 = android.provider.Settings.Global.getUriFor(r0)
            java.lang.String r2 = "NfcEnabler"
            r3 = 1
            r4 = 0
            if (r1 != 0) goto L15
            java.lang.String r0 = "satellite mode key does not exist in Settings"
            android.util.Log.d(r2, r0)
        L13:
            r0 = r4
            goto L22
        L15:
            android.content.Context r1 = r7.mContext
            android.content.ContentResolver r1 = r1.getContentResolver()
            int r0 = android.provider.Settings.Global.getInt(r1, r0, r4)
            if (r0 != r3) goto L13
            r0 = r3
        L22:
            android.content.Context r7 = r7.mContext
            java.lang.String r1 = "false"
            java.lang.String[] r1 = new java.lang.String[]{r1}
            java.lang.String r5 = "content://com.sec.knox.provider2/MiscPolicy"
            java.lang.String r6 = "isNFCStateChangeAllowed"
            int r7 = com.android.settings.Utils.getEnterprisePolicyEnabled(r7, r5, r6, r1)
            if (r7 == 0) goto L36
            r7 = r3
            goto L37
        L36:
            r7 = r4
        L37:
            java.lang.String r1 = "satelliteMode = "
            java.lang.String r5 = " knoxNfcStateChangeAllowed = "
            com.android.settings.Utils$$ExternalSyntheticOutline0.m653m(r1, r0, r5, r7, r2)
            if (r0 != 0) goto L44
            if (r7 == 0) goto L44
            goto L45
        L44:
            r3 = r4
        L45:
            return r3
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.samsung.android.settings.nfc.NfcEnabler.isNfcStateChangeAllowed():boolean");
    }

    @Override // android.widget.CompoundButton.OnCheckedChangeListener
    public final void onCheckedChanged(CompoundButton compoundButton, boolean z) {
        SecSwitchPreference secSwitchPreference;
        SettingsMainSwitchBar settingsMainSwitchBar;
        if (NfcSettings.DBG) {
            AirplaneModeEnabler$$ExternalSyntheticOutline0.m(
                    "onSwitchChanged, desiredState is ", "NfcEnabler", z);
        }
        SemEmergencyManager.getInstance(this.mContext);
        if (SemEmergencyManager.isEmergencyMode(this.mContext)) {
            Log.d("NfcEnabler", "emergency mode on. so can't control nfc state");
            return;
        }
        if ((this.mNfcAdapter.getAdapterState() == 2 || this.mNfcAdapter.getAdapterState() == 3)
                == z) {
            return;
        }
        SettingsMainSwitchBar settingsMainSwitchBar2 = this.mNfcSettingPref.NfcSettingToggle;
        if (settingsMainSwitchBar2 != null) {
            settingsMainSwitchBar2.setEnabled(false);
        }
        boolean isGpFelicaSupported2 = Rune.isGpFelicaSupported(this.mContext);
        isGpFelicaSupported = isGpFelicaSupported2;
        if (!z) {
            this.mNfcAdapter.disable();
            LoggingHelper.insertEventLogging(3650, 3651, z);
            return;
        }
        if (isGpFelicaSupported2
                && getCLFLockState() != 0
                && (settingsMainSwitchBar = this.mNfcSettingPref.NfcSettingToggle) != null) {
            settingsMainSwitchBar.setChecked(false);
            return;
        }
        this.mNfcAdapter.enable();
        if (isGpFelicaSupported
                && (secSwitchPreference = this.mNfcSettingPref.NfcJapanPreferenceSecureNfc)
                        != null) {
            secSwitchPreference.setEnabled(true);
            this.mNfcSettingPref.NfcJapanPreferenceSecureNfc.setChecked(
                    this.mNfcAdapter.isSecureNfcEnabled());
        }
        LoggingHelper.insertEventLogging(3650, 3651, z);
    }

    @Override // androidx.preference.Preference.OnPreferenceChangeListener
    public final boolean onPreferenceChange(Preference preference, Object obj) {
        boolean enableSecureNfc;
        SecSwitchPreference secSwitchPreference;
        SecSwitchPreference secSwitchPreference2;
        boolean booleanValue = ((Boolean) obj).booleanValue();
        Log.d(
                "NfcEnabler",
                "onPreferenceChange" + preference.getClass().getName() + " " + booleanValue);
        if (!preference.equals(this.mNfcSettingPref.ConnectionsSettingToggle)) {
            if (preference.equals(this.mNfcSettingPref.ReaderMode)) {
                if (this.mNfcAdapter.isReaderOptionEnabled()) {
                    Toast.makeText(
                                    this.mContext,
                                    R.string.nfc_reader_mode_turn_off_toast_popup_msg,
                                    0)
                            .show();
                    this.mNfcAdapter.enableReaderOption(false);
                    LoggingHelper.insertEventLogging(3650, 7015, 1L);
                } else {
                    LoggingHelper.insertEventLogging(3650, 7015, 0L);
                    this.mNfcAdapter.enableReaderOption(true);
                }
                this.mNfcSettingPref.ReaderMode.setSummary(R.string.nfc_reader_mode_desc_common);
            } else if (preference.equals(this.mNfcSettingPref.NfcJapanPreferenceSecureNfc)) {
                if (NfcSettings.DBG) {
                    Log.d("NfcEnabler", "onPreferenceChange - mSecureNfcSwitchPref switch");
                }
                if (booleanValue) {
                    Log.e("NfcEnabler", "secure nfc on");
                    enableSecureNfc = this.mNfcAdapter.enableSecureNfc(true);
                } else {
                    Log.e("NfcEnabler", "secure nfc off");
                    enableSecureNfc = this.mNfcAdapter.enableSecureNfc(false);
                }
                LoggingHelper.insertEventLogging(3650, 9502, booleanValue ? 1L : 0L);
                if (enableSecureNfc) {
                    this.mNfcSettingPref.NfcJapanPreferenceSecureNfc.setChecked(booleanValue);
                }
            }
        } else {
            if (this.isNFCStateChangeAllowed == 0) {
                Log.e(
                        "NfcEnabler",
                        "EDM : NFC state change not allowed. so can't change toggle button");
                return false;
            }
            if (booleanValue) {
                if (isGpFelicaSupported && getCLFLockState() != 0) {
                    return false;
                }
                this.mNfcAdapter.enable();
                if (isGpFelicaSupported
                        && (secSwitchPreference2 = this.mNfcSettingPref.NfcJapanPreferenceSecureNfc)
                                != null) {
                    secSwitchPreference2.setEnabled(true);
                    this.mNfcSettingPref.NfcJapanPreferenceSecureNfc.setChecked(
                            this.mNfcAdapter.isSecureNfcEnabled());
                }
                LoggingHelper.insertEventLogging(3650, 3214, booleanValue);
            } else if ("KOREA".equalsIgnoreCase(SystemProperties.get("ro.csc.country_code"))) {
                this.mNfcAdapter.disable();
                LoggingHelper.insertEventLogging(
                        VolteConstants.ErrorCode.SIMULTANEOUS_CALL_LIMIT_HAS_ALREADY_BEEN_REACHED,
                        3214,
                        booleanValue);
            } else {
                this.mNfcAdapter.disable();
                if (isGpFelicaSupported
                        && (secSwitchPreference = this.mNfcSettingPref.NfcJapanPreferenceSecureNfc)
                                != null) {
                    secSwitchPreference.setEnabled(false);
                    this.mNfcSettingPref.NfcJapanPreferenceSecureNfc.setChecked(false);
                }
                LoggingHelper.insertEventLogging(3650, 3214, booleanValue);
            }
        }
        return true;
    }

    @Override // androidx.preference.Preference.OnPreferenceClickListener
    public final boolean onPreferenceClick(Preference preference) {
        Log.d("NfcEnabler", "onPreferenceClick called for " + preference.getKey());
        if (!this.mNfcSettingPref.TapPay.getKey().equals(preference.getKey())) {
            return false;
        }
        LoggingHelper.insertFlowLogging(3653);
        LoggingHelper.insertEntranceLogging(3653);
        if (!Flags.walletRoleEnabled()) {
            Log.d("NfcEnabler", "walletRoleEnabled is false");
            return false;
        }
        if (!((RoleManager) this.mContext.getSystemService(RoleManager.class))
                .isRoleAvailable("android.app.role.WALLET")) {
            return false;
        }
        this.mContext.startActivity(
                new Intent("android.nfc.cardemulation.action.ACTION_CHANGE_DEFAULT")
                        .setPackage(
                                this.mContext
                                        .getPackageManager()
                                        .getPermissionControllerPackageName()));
        return true;
    }

    public final void pause() {
        if (this.mNfcAdapter == null) {
            return;
        }
        this.mContext.unregisterReceiver(this.mReceiver);
        this.mContext.getContentResolver().unregisterContentObserver(this.mContentObserver);
        SettingsMainSwitchBar settingsMainSwitchBar = this.mNfcSettingPref.NfcSettingToggle;
        if (settingsMainSwitchBar != null) {
            settingsMainSwitchBar.removeOnSwitchChangeListener(this);
        }
        SecSwitchPreference secSwitchPreference = this.mNfcSettingPref.ReaderMode;
        if (secSwitchPreference != null) {
            secSwitchPreference.setOnPreferenceChangeListener(null);
        }
        SecSwitchPreferenceScreen secSwitchPreferenceScreen =
                this.mNfcSettingPref.ConnectionsSettingToggle;
        if (secSwitchPreferenceScreen != null) {
            secSwitchPreferenceScreen.setOnPreferenceChangeListener(null);
        }
        if (this.mNfcSettingPref.TapPay != null) {
            Log.d("NfcEnabler", "Removing OnPreferenceClickListener");
            this.mNfcSettingPref.TapPay.setOnPreferenceClickListener(null);
        }
    }

    public final void resume() {
        Object obj;
        Log.d("NfcEnabler", ApnSettings.MVNO_NONE);
        if (this.mNfcAdapter == null) {
            return;
        }
        this.mContext.registerReceiver(this.mReceiver, this.mIntentFilter);
        this.mContext.registerReceiver(this.mReceiver, this.mSwitchNfcModeFilter, 2);
        handleNfcStateChanged(this.mNfcAdapter.getAdapterState());
        Uri uriFor = Settings.Global.getUriFor("satellite_mode_enabled");
        if (uriFor == null) {
            Log.d("NfcEnabler", "satellite mode key does not exist in Settings");
        } else {
            this.mContext
                    .getContentResolver()
                    .registerContentObserver(uriFor, false, this.mContentObserver);
        }
        SettingsMainSwitchBar settingsMainSwitchBar = this.mNfcSettingPref.NfcSettingToggle;
        if (settingsMainSwitchBar != null) {
            settingsMainSwitchBar.addOnSwitchChangeListener(this);
            this.mNfcSettingPref.NfcSettingToggle.setEnabled(isNfcStateChangeAllowed());
        }
        SecSwitchPreference secSwitchPreference = this.mNfcSettingPref.ReaderMode;
        if (secSwitchPreference != null) {
            secSwitchPreference.setOnPreferenceChangeListener(this);
        }
        SecSwitchPreference secSwitchPreference2 = this.mNfcSettingPref.NfcJapanPreferenceSecureNfc;
        if (secSwitchPreference2 != null) {
            secSwitchPreference2.setOnPreferenceChangeListener(this);
            handleNfcSecureNfcChanged();
        }
        SecSwitchPreferenceScreen secSwitchPreferenceScreen =
                this.mNfcSettingPref.ConnectionsSettingToggle;
        if (secSwitchPreferenceScreen != null) {
            secSwitchPreferenceScreen.setOnPreferenceChangeListener(this);
            this.mNfcSettingPref.ConnectionsSettingToggle.setEnabled(isNfcStateChangeAllowed());
        }
        if (this.mNfcSettingPref.TapPay != null) {
            Log.d("NfcEnabler", "Setting OnPreferenceClickListener");
            this.mNfcSettingPref.TapPay.setOnPreferenceClickListener(this);
        }
        SecPreference secPreference = this.mNfcSettingPref.TapPay;
        if (secPreference != null) {
            SecPreferenceUtils.applySummaryColor(secPreference, true);
            SecPreference secPreference2 = this.mNfcSettingPref.TapPay;
            String string = this.mContext.getString(R.string.none);
            try {
                int currentUser = ActivityManager.getCurrentUser();
                List roleHoldersAsUser =
                        this.roleManager.getRoleHoldersAsUser(
                                "android.app.role.WALLET", UserHandle.of(currentUser));
                ApplicationInfo applicationInfo =
                        this.mContext
                                .getPackageManager()
                                .getApplicationInfo(
                                        roleHoldersAsUser.isEmpty()
                                                ? null
                                                : (String) roleHoldersAsUser.get(0),
                                        0);
                Context context = this.mContext;
                WalletRoleUiBehavior walletRoleUiBehavior = new WalletRoleUiBehavior();
                walletRoleUiBehavior.mContext = context;
                Pair prepareApplicationPreferenceAsUser =
                        walletRoleUiBehavior.prepareApplicationPreferenceAsUser(
                                applicationInfo, UserHandle.of(currentUser), this.mContext);
                if (prepareApplicationPreferenceAsUser != null
                        && (obj = prepareApplicationPreferenceAsUser.second) != null) {
                    string = ((CharSequence) obj).toString();
                }
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
            if (string.equalsIgnoreCase(this.mContext.getString(R.string.none))) {
                String defaultApplication =
                        this.roleManager.getDefaultApplication("android.app.role.WALLET");
                PackageManager packageManager = this.mContext.getPackageManager();
                try {
                    string =
                            (String)
                                    packageManager.getApplicationLabel(
                                            packageManager.getApplicationInfo(
                                                    defaultApplication, 128));
                } catch (PackageManager.NameNotFoundException e2) {
                    Log.e(
                            "NfcEnabler",
                            "package name not found for the given package. exception - "
                                    + e2.getMessage());
                }
            }
            secPreference2.setSummary(string);
        }
    }

    public final void updateNfcSettingToggle(boolean z, boolean z2) {
        SettingsMainSwitchBar settingsMainSwitchBar = this.mNfcSettingPref.NfcSettingToggle;
        if (settingsMainSwitchBar != null) {
            settingsMainSwitchBar.removeOnSwitchChangeListener(this);
            this.mNfcSettingPref.NfcSettingToggle.setChecked(z);
            this.mNfcSettingPref.NfcSettingToggle.setEnabled(z2 && isNfcStateChangeAllowed());
            this.mNfcSettingPref.NfcSettingToggle.addOnSwitchChangeListener(this);
        }
    }
}
