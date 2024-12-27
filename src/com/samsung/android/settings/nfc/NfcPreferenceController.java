package com.samsung.android.settings.nfc;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.nfc.NfcAdapter;
import android.os.UserManager;
import android.provider.Settings;
import android.util.Log;

import androidx.appcompat.app.AppCompatDelegateImpl$$ExternalSyntheticOutline0;
import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;
import androidx.preference.SecSwitchPreferenceScreen;

import com.android.settings.Utils;
import com.android.settings.core.TogglePreferenceController;
import com.android.settings.slices.SliceBackgroundWorker;
import com.android.settingslib.core.lifecycle.LifecycleObserver;
import com.android.settingslib.core.lifecycle.events.OnPause;
import com.android.settingslib.core.lifecycle.events.OnResume;
import com.android.settingslib.core.lifecycle.events.OnStart;
import com.android.settingslib.core.lifecycle.events.OnStop;

import com.samsung.android.settings.Rune;
import com.samsung.android.settings.cube.Controllable$ControllableType;

import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class NfcPreferenceController extends TogglePreferenceController
        implements LifecycleObserver, OnResume, OnPause, OnStart, OnStop {
    private static final String KEY_NFC_SETTINGS = "nfc_settings";
    private static final String KEY_NFC_SETTINGS_GP = "nfc_settings_gp";
    private static final String SETTINGS_SATELLITE_MODE_ENABLED = "satellite_mode_enabled";
    private static final String TAG = "NfcPreferenceController";
    private static boolean mIsGlobal = !Rune.isJapanModel();
    private static boolean mIsGpFelicaSupported;
    private Context mContext;
    private boolean mHasNFCSystemFeature;
    private final NfcAdapter mNfcAdapter;
    private NfcEnabler mNfcEnabler;
    private PackageManager mPm;
    private BroadcastReceiver mReceiver;
    private UserManager mUm;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.settings.nfc.NfcPreferenceController$1, reason: invalid class name */
    public final class AnonymousClass1 extends BroadcastReceiver {
        @Override // android.content.BroadcastReceiver
        public final void onReceive(Context context, Intent intent) {
            intent.getAction();
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public static class NfcSliceWorker extends SliceBackgroundWorker {
        public static final IntentFilter NFC_FILTER =
                new IntentFilter("android.nfc.action.ADAPTER_STATE_CHANGED");
        public NfcUpdateReceiver mUpdateObserver;

        /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
        public final class NfcUpdateReceiver extends BroadcastReceiver {
            public final NfcSliceWorker mSliceBackgroundWorker;

            public NfcUpdateReceiver(NfcSliceWorker nfcSliceWorker) {
                this.mSliceBackgroundWorker = nfcSliceWorker;
            }

            @Override // android.content.BroadcastReceiver
            public final void onReceive(Context context, Intent intent) {
                int intExtra = intent.getIntExtra("android.nfc.extra.ADAPTER_STATE", -1);
                if (intExtra == -1 || intExtra == 2 || intExtra == 4) {
                    Log.d("NfcSliceWorker", "Transitional update, dropping broadcast");
                } else {
                    Log.d("NfcSliceWorker", "Nfc broadcast received, updating Slice.");
                    this.mSliceBackgroundWorker.notifySliceChange();
                }
            }
        }

        public NfcSliceWorker(Context context, Uri uri) {
            super(context, uri);
            this.mUpdateObserver = new NfcUpdateReceiver(this);
        }

        @Override // java.io.Closeable, java.lang.AutoCloseable
        public final void close() {
            this.mUpdateObserver = null;
        }

        @Override // com.android.settings.slices.SliceBackgroundWorker
        public final void onSlicePinned() {
            this.mContext.registerReceiver(this.mUpdateObserver, NFC_FILTER);
        }

        @Override // com.android.settings.slices.SliceBackgroundWorker
        public final void onSliceUnpinned() {
            this.mContext.unregisterReceiver(this.mUpdateObserver);
        }
    }

    public NfcPreferenceController(Context context, String str) {
        super(context, str);
        this.mHasNFCSystemFeature = true;
        this.mReceiver = new AnonymousClass1();
        this.mContext = context;
        if (context.getPackageManager().hasSystemFeature("android.hardware.nfc")) {
            this.mNfcAdapter = NfcAdapter.getDefaultAdapter(this.mContext);
        } else {
            this.mNfcAdapter = null;
            this.mHasNFCSystemFeature = false;
        }
    }

    private boolean isBlockedByMDM() {
        return Utils.getEnterprisePolicyEnabled(
                        this.mContext,
                        "content://com.sec.knox.provider2/MiscPolicy",
                        "isNFCStateChangeAllowed",
                        new String[] {"false"})
                == 0;
    }

    private boolean isSatelliteModeOn() {
        return Settings.Global.getUriFor(SETTINGS_SATELLITE_MODE_ENABLED) != null
                && Settings.Global.getInt(
                                this.mContext.getContentResolver(),
                                SETTINGS_SATELLITE_MODE_ENABLED,
                                0)
                        == 1;
    }

    public static boolean isToggleableInAirplaneMode(Context context) {
        String string =
                Settings.Global.getString(
                        context.getContentResolver(), "airplane_mode_toggleable_radios");
        return string != null && string.contains("nfc");
    }

    public static boolean shouldTurnOffNFCInAirplaneMode(Context context) {
        String string =
                Settings.Global.getString(context.getContentResolver(), "airplane_mode_radios");
        return string != null && string.contains("nfc");
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        if (!isAvailable()) {
            this.mNfcEnabler = null;
            return;
        }
        this.mPm = this.mContext.getPackageManager();
        this.mUm = (UserManager) this.mContext.getSystemService("user");
        SecSwitchPreferenceScreen secSwitchPreferenceScreen =
                (SecSwitchPreferenceScreen) preferenceScreen.findPreference(KEY_NFC_SETTINGS);
        SecSwitchPreferenceScreen secSwitchPreferenceScreen2 =
                (SecSwitchPreferenceScreen) preferenceScreen.findPreference(KEY_NFC_SETTINGS_GP);
        mIsGpFelicaSupported = Rune.isGpFelicaSupported(this.mContext);
        if (!this.mHasNFCSystemFeature) {
            this.mNfcEnabler = null;
            return;
        }
        if (Rune.isGpFelicaSupported(this.mContext)) {
            NfcEnabler.Builder builder = new NfcEnabler.Builder(this.mContext);
            builder.ConnectionsSettingToggle = secSwitchPreferenceScreen2;
            this.mNfcEnabler = new NfcEnabler(builder);
        } else {
            NfcEnabler.Builder builder2 = new NfcEnabler.Builder(this.mContext);
            builder2.ConnectionsSettingToggle = secSwitchPreferenceScreen;
            this.mNfcEnabler = new NfcEnabler(builder2);
        }
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        if (!this.mHasNFCSystemFeature) {
            return 3;
        }
        boolean isGpFelicaSupported = Rune.isGpFelicaSupported(this.mContext);
        mIsGpFelicaSupported = isGpFelicaSupported;
        if (isGpFelicaSupported) {
            if (KEY_NFC_SETTINGS.equals(getPreferenceKey())) {
                return 3;
            }
        } else if (KEY_NFC_SETTINGS_GP.equals(getPreferenceKey())) {
            return 3;
        }
        if (isBlockedByMDM() || isSatelliteModeOn()) {
            return 5;
        }
        return (!mIsGpFelicaSupported || Utils.getCLFLockState() == 0) ? 0 : 5;
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public Class<? extends SliceBackgroundWorker> getBackgroundWorkerClass() {
        return NfcSliceWorker.class;
    }

    @Override // com.android.settings.core.TogglePreferenceController
    public /* bridge */ /* synthetic */ List getBackupKeys() {
        return super.getBackupKeys();
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public IntentFilter getIntentFilter() {
        return AppCompatDelegateImpl$$ExternalSyntheticOutline0.m(
                "android.nfc.action.ADAPTER_STATE_CHANGED");
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Intent getLaunchIntent() {
        return super.getLaunchIntent();
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ String getStatusText() {
        return super.getStatusText();
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public boolean handlePreferenceTreeClick(Preference preference) {
        Log.d(TAG, "Inside handlePreferenceTreeClick");
        String key = preference.getKey();
        Log.d(TAG, "handlePreferenceTreeClick: key = " + key);
        if (!"data_usage_settings".equals(key) || !Rune.SUPPORT_SMARTMANAGER_CN) {
            return super.handlePreferenceTreeClick(preference);
        }
        Intent intent = new Intent();
        intent.setAction("com.samsung.android.sm.ACTION_DATA_USAGE");
        this.mContext.startActivity(intent);
        return true;
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public boolean hasAsyncUpdate() {
        return true;
    }

    @Override // com.android.settings.core.TogglePreferenceController
    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {
        super.ignoreUserInteraction();
    }

    @Override // com.android.settings.core.TogglePreferenceController
    /* renamed from: isChecked */
    public boolean getThreadEnabled() {
        NfcAdapter nfcAdapter = this.mNfcAdapter;
        if (nfcAdapter != null) {
            return nfcAdapter.isEnabled() || this.mNfcAdapter.getAdapterState() == 2;
        }
        return false;
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public boolean isControllable() {
        return true;
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public boolean isPublicSlice() {
        return true;
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Controllable$ControllableType needUserInteraction(
            Object obj) {
        return super.needUserInteraction(obj);
    }

    @Override // com.android.settingslib.core.lifecycle.events.OnPause
    public void onPause() {
        NfcEnabler nfcEnabler = this.mNfcEnabler;
        if (nfcEnabler != null) {
            nfcEnabler.pause();
        }
    }

    @Override // com.android.settingslib.core.lifecycle.events.OnResume
    public void onResume() {
        NfcEnabler nfcEnabler = this.mNfcEnabler;
        if (nfcEnabler != null) {
            nfcEnabler.resume();
        }
    }

    @Override // com.android.settingslib.core.lifecycle.events.OnStart
    public void onStart() {
        if (this.mNfcEnabler != null) {
            IntentFilter intentFilter = new IntentFilter("android.intent.action.SIM_STATE_CHANGED");
            IntentFilter intentFilter2 = new IntentFilter("com.samsung.ims.action.onsimloaded");
            this.mContext.registerReceiver(this.mReceiver, intentFilter);
            this.mContext.registerReceiver(this.mReceiver, intentFilter2, 2);
        }
    }

    @Override // com.android.settingslib.core.lifecycle.events.OnStop
    public void onStop() {
        try {
            if (this.mNfcEnabler != null) {
                this.mContext.unregisterReceiver(this.mReceiver);
            }
        } catch (Exception unused) {
            Log.e(TAG, "Receiver not registered");
        }
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return super.runDefaultAction();
    }

    @Override // com.android.settings.core.TogglePreferenceController
    public boolean setChecked(boolean z) {
        NfcAdapter nfcAdapter = this.mNfcAdapter;
        if (nfcAdapter == null) {
            return true;
        }
        if (z) {
            nfcAdapter.enable();
            return true;
        }
        nfcAdapter.disable();
        return true;
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }
}
