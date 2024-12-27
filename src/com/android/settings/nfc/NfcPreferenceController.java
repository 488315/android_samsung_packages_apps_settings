package com.android.settings.nfc;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.nfc.NfcAdapter;
import android.util.Log;
import android.widget.CompoundButton;

import androidx.preference.PreferenceScreen;

import com.android.settings.R;
import com.android.settings.Utils;
import com.android.settings.core.TogglePreferenceController;
import com.android.settings.slices.SliceBackgroundWorker;
import com.android.settingslib.core.lifecycle.LifecycleObserver;
import com.android.settingslib.core.lifecycle.events.OnPause;
import com.android.settingslib.core.lifecycle.events.OnResume;
import com.android.settingslib.widget.MainSwitchPreference;

import com.samsung.android.settings.Rune;
import com.samsung.android.settings.cube.Controllable$ControllableType;

import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class NfcPreferenceController extends TogglePreferenceController
        implements LifecycleObserver, OnResume, OnPause, CompoundButton.OnCheckedChangeListener {
    public static final String KEY_TOGGLE_NFC = "toggle_nfc";
    private final NfcAdapter mNfcAdapter;
    private NfcEnabler mNfcEnabler;
    private MainSwitchPreference mPreference;

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
        this.mNfcAdapter = NfcAdapter.getDefaultAdapter(context);
    }

    private boolean isBlockedByMDM() {
        return Utils.getEnterprisePolicyEnabled(
                        this.mContext,
                        "content://com.sec.knox.provider2/MiscPolicy",
                        "isNFCStateChangeAllowed",
                        new String[] {"false"})
                == 0;
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        isAvailable();
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        if (isBlockedByMDM()) {
            return 5;
        }
        if (!Rune.isGpFelicaSupported(this.mContext) || Utils.getCLFLockState() == 0) {
            return this.mNfcAdapter != null ? 0 : 3;
        }
        return 5;
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
    public /* bridge */ /* synthetic */ IntentFilter getIntentFilter() {
        return null;
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Intent getLaunchIntent() {
        return super.getLaunchIntent();
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public int getSliceHighlightMenuRes() {
        return R.string.menu_key_connected_devices;
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ String getStatusText() {
        return super.getStatusText();
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
        return this.mNfcAdapter.isEnabled() || this.mNfcAdapter.getAdapterState() == 2;
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean isControllable() {
        return super.isControllable();
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

    @Override // android.widget.CompoundButton.OnCheckedChangeListener
    public void onCheckedChanged(CompoundButton compoundButton, boolean z) {
        if (z != this.mNfcAdapter.isEnabled()) {
            setChecked(z);
        }
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return super.runDefaultAction();
    }

    @Override // com.android.settings.core.TogglePreferenceController
    public boolean setChecked(boolean z) {
        if (z) {
            this.mNfcAdapter.enable();
            return true;
        }
        this.mNfcAdapter.disable();
        return true;
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }

    @Override // com.android.settingslib.core.lifecycle.events.OnPause
    public void onPause() {}

    @Override // com.android.settingslib.core.lifecycle.events.OnResume
    public void onResume() {}
}
