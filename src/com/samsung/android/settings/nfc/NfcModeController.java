package com.samsung.android.settings.nfc;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.NfcAdapter;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.widget.ActionBarContextView$$ExternalSyntheticOutline0;

import com.android.settings.R;
import com.android.settings.core.SubSettingLauncher;
import com.android.settings.core.TogglePreferenceController;

import com.samsung.android.settings.Rune;
import com.samsung.android.settings.account.controller.SecAccountPreferenceController$$ExternalSyntheticOutline0;
import com.samsung.android.settings.cube.Controllable$ControllableType;
import com.samsung.android.settings.logging.LoggingHelper;

import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class NfcModeController extends TogglePreferenceController {
    private static final String TAG = "NfcModeController";
    private NfcAdapter mNfcAdapter;

    public NfcModeController(Context context, String str) {
        super(context, str);
        this.mNfcAdapter = NfcAdapter.getDefaultAdapter(this.mContext);
    }

    private boolean changeNfcMode() {
        Log.d(TAG, "triggered change nfc mode");
        if (this.mNfcAdapter.isReaderOptionEnabled()) {
            new Handler(Looper.getMainLooper())
                    .post(
                            new Runnable() { // from class:
                                             // com.samsung.android.settings.nfc.NfcModeController$$ExternalSyntheticLambda0
                                @Override // java.lang.Runnable
                                public final void run() {
                                    NfcModeController.this.lambda$changeNfcMode$0();
                                }
                            });
            if (Rune.isSupportAndroidBeam(this.mContext)) {
                Settings.System.putInt(this.mContext.getContentResolver(), "android_beam_state", 0);
            }
            this.mNfcAdapter.enableReaderOption(false);
            LoggingHelper.insertEventLogging(3650, 7015, 1L);
        } else {
            LoggingHelper.insertEventLogging(3650, 7015, 0L);
            this.mNfcAdapter.enableReaderOption(true);
        }
        return true;
    }

    private boolean getReaderModeState() {
        return this.mNfcAdapter.isReaderOptionEnabled();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$changeNfcMode$0() {
        Toast.makeText(this.mContext, R.string.nfc_reader_mode_turn_off_toast_popup_msg, 0).show();
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        if (this.mNfcAdapter.isReaderOptionSupported()) {
            Log.d(
                    TAG,
                    "getAvailabilityStatus : "
                            .concat(
                                    this.mNfcAdapter.getAdapterState() == 1
                                            ? "DISABLED_DEPENDENT_SETTING"
                                            : "AVAILABLE"));
            return this.mNfcAdapter.getAdapterState() == 1 ? 5 : 0;
        }
        Log.d(TAG, "getAvailabilityStatus : UNSUPPORTED_ON_DEVICE");
        return 3;
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
    public /* bridge */ /* synthetic */ IntentFilter getIntentFilter() {
        return null;
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public Intent getLaunchIntent() {
        SubSettingLauncher subSettingLauncher = new SubSettingLauncher(this.mContext);
        String name = NfcSettings.class.getName();
        SubSettingLauncher.LaunchRequest launchRequest = subSettingLauncher.mLaunchRequest;
        launchRequest.mDestinationName = name;
        launchRequest.mSourceMetricsCategory = 3650;
        return SecAccountPreferenceController$$ExternalSyntheticOutline0.m(
                subSettingLauncher, null, R.string.nfcpayment_quick_toggle_title, 268468224);
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
        ActionBarContextView$$ExternalSyntheticOutline0.m(
                new StringBuilder("isChecked: "), getReaderModeState(), TAG);
        return getReaderModeState();
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public boolean isControllable() {
        return true;
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public Controllable$ControllableType needUserInteraction(Object obj) {
        return Controllable$ControllableType.NEED_INTERACTION;
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return super.runDefaultAction();
    }

    @Override // com.android.settings.core.TogglePreferenceController
    public boolean setChecked(boolean z) {
        Log.d(TAG, "currentCheckedValue: " + getThreadEnabled() + " , newCheckedValue: " + z);
        return changeNfcMode();
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public String getSummary() {
        return this.mContext.getString(R.string.nfc_reader_mode_desc_common);
    }
}
