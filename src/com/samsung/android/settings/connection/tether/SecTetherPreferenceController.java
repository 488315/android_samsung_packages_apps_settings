package com.samsung.android.settings.connection.tether;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import androidx.preference.Preference;

import com.android.settings.core.BasePreferenceController;
import com.android.settingslib.Utils;

import com.samsung.android.settings.Rune;
import com.samsung.android.settings.connection.ConnectionsSettings;
import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;
import com.samsung.android.util.SemLog;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class SecTetherPreferenceController extends BasePreferenceController {
    private static final String KEY_TETHERING_SETTINGS = "tethering_and_hotspot";
    private final String TAG;
    private ConnectionsSettings mHost;

    public SecTetherPreferenceController(Context context) {
        this(context, KEY_TETHERING_SETTINGS);
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        if (!Rune.isJapanModel() && !Utils.isWifiOnly(this.mContext)) {
            return !SecTetherUtils.isAvailableTetherMenu(this.mContext) ? 4 : 0;
        }
        SemLog.d("SecTetherPreferenceController", "Unsupported Controller : JPN Wifi");
        return 4;
    }

    public /* bridge */ /* synthetic */ Class getBackgroundWorkerClass() {
        return null;
    }

    public List getBackupKeys() {
        return new ArrayList();
    }

    public /* bridge */ /* synthetic */ IntentFilter getIntentFilter() {
        return null;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Intent getLaunchIntent() {
        return null;
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public String getPreferenceKey() {
        return KEY_TETHERING_SETTINGS;
    }

    public /* bridge */ /* synthetic */ int getSliceHighlightMenuRes() {
        return 0;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ String getStatusText() {
        return null;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlValue getValue() {
        return null;
    }

    public /* bridge */ /* synthetic */ boolean hasAsyncUpdate() {
        return false;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean isControllable() {
        return false;
    }

    public /* bridge */ /* synthetic */ boolean isPublicSlice() {
        return false;
    }

    public /* bridge */ /* synthetic */ boolean isSliceable() {
        return false;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Controllable$ControllableType needUserInteraction(
            Object obj) {
        return Controllable$ControllableType.NO_INTERACTION;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return false;
    }

    public void setHost(ConnectionsSettings connectionsSettings) {
        this.mHost = connectionsSettings;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlResult setValue(ControlValue controlValue) {
        return null;
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public void updateState(Preference preference) {
        super.updateState(preference);
        if (SecTetherUtils.hasUserRestriction(this.mContext)) {
            preference.setEnabled(false);
        } else {
            preference.setEnabled(true);
        }
    }

    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }

    public SecTetherPreferenceController(Context context, String str) {
        super(context, str);
        this.TAG = "SecTetherPreferenceController";
    }

    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {}
}
