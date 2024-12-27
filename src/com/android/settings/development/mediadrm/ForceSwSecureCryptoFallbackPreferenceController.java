package com.android.settings.development.mediadrm;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.MediaDrm;
import android.sysprop.WidevineProperties;
import android.util.Log;

import androidx.apppickerview.widget.AbsAdapter$$ExternalSyntheticOutline0;
import androidx.preference.Preference;

import com.android.settings.R;
import com.android.settings.core.TogglePreferenceController;
import com.android.settingslib.development.DevelopmentSettingsEnabler;

import com.samsung.android.gtscell.data.FieldName;
import com.samsung.android.settings.cube.Controllable$ControllableType;

import java.util.List;
import java.util.UUID;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class ForceSwSecureCryptoFallbackPreferenceController extends TogglePreferenceController {
    private static final String TAG = "ForceSwSecureCryptoFallbackPreferenceController";
    private static final UUID WIDEVINE_UUID =
            new UUID(-1301668207276963122L, -6645017420763422227L);

    public ForceSwSecureCryptoFallbackPreferenceController(Context context, String str) {
        super(context, str);
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        return DevelopmentSettingsEnabler.isDevelopmentSettingsEnabled(this.mContext) ? 0 : 2;
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
    public /* bridge */ /* synthetic */ Intent getLaunchIntent() {
        return super.getLaunchIntent();
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public int getSliceHighlightMenuRes() {
        return R.string.menu_key_system;
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
        return ((Boolean) WidevineProperties.forcel3_enabled().orElse(Boolean.FALSE))
                .booleanValue();
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean isControllable() {
        return super.isControllable();
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
        WidevineProperties.forcel3_enabled(Boolean.valueOf(z));
        return true;
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void updateState(Preference preference) {
        try {
            MediaDrm mediaDrm = new MediaDrm(WIDEVINE_UUID);
            try {
                r1 =
                        Integer.parseInt(
                                        mediaDrm.getPropertyString(FieldName.VERSION)
                                                .split("\\.", 2)[0])
                                >= 19;
                mediaDrm.close();
            } finally {
            }
        } catch (Exception e) {
            Log.e(TAG, "An exception occurred:", e);
        }
        preference.setEnabled(r1);
        if (!r1) {
            WidevineProperties.forcel3_enabled(Boolean.FALSE);
        }
        AbsAdapter$$ExternalSyntheticOutline0.m("Force software crypto is ", TAG, r1);
        super.updateState(preference);
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }
}
