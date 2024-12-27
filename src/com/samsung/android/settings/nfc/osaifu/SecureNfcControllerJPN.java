package com.samsung.android.settings.nfc.osaifu;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.NfcAdapter;

import androidx.appcompat.app.AppCompatDelegateImpl$$ExternalSyntheticOutline0;

import com.android.settings.core.TogglePreferenceController;
import com.android.settingslib.core.lifecycle.LifecycleObserver;

import com.samsung.android.settings.Rune;
import com.samsung.android.settings.cube.Controllable$ControllableType;

import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class SecureNfcControllerJPN extends TogglePreferenceController
        implements LifecycleObserver {
    private static final String KEY_SECURE_NFC_DCM = "secure_nfc_settings_dcm";
    private static final String KEY_SECURE_NFC_KDI = "secure_nfc_settings_kdi";
    private static final String TAG = "[SecureNfcController]";
    private Context mContext;
    private final NfcAdapter mNfcAdapter;

    public SecureNfcControllerJPN(Context context, String str) {
        super(context, str);
        this.mNfcAdapter = NfcAdapter.getDefaultAdapter(context);
        this.mContext = context;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        NfcAdapter nfcAdapter;
        boolean isGpFelicaSupported = Rune.isGpFelicaSupported(this.mContext);
        if (isGpFelicaSupported
                && (nfcAdapter = this.mNfcAdapter) != null
                && (nfcAdapter.getAdapterState() == 1 || this.mNfcAdapter.getAdapterState() == 4)) {
            return 5;
        }
        return (this.mNfcAdapter == null || !isGpFelicaSupported) ? 3 : 0;
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ Class getBackgroundWorkerClass() {
        return null;
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

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public String getPreferenceKey() {
        return Rune.isJapanKDIModel() ? KEY_SECURE_NFC_KDI : KEY_SECURE_NFC_DCM;
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ String getStatusText() {
        return super.getStatusText();
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean hasAsyncUpdate() {
        return false;
    }

    @Override // com.android.settings.core.TogglePreferenceController
    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {
        super.ignoreUserInteraction();
    }

    @Override // com.android.settings.core.TogglePreferenceController
    /* renamed from: isChecked */
    public boolean getThreadEnabled() {
        return this.mNfcAdapter.isSecureNfcEnabled()
                && (this.mNfcAdapter.getAdapterState() == 3
                        || this.mNfcAdapter.getAdapterState() == 2);
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public boolean isControllable() {
        return true;
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Controllable$ControllableType needUserInteraction(
            Object obj) {
        return super.needUserInteraction(obj);
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return super.runDefaultAction();
    }

    @Override // com.android.settings.core.TogglePreferenceController
    public boolean setChecked(boolean z) {
        this.mNfcAdapter.enableSecureNfc(z);
        return true;
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }
}
