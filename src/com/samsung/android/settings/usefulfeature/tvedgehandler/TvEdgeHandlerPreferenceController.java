package com.samsung.android.settings.usefulfeature.tvedgehandler;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.ContentObserver;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;

import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;
import androidx.preference.SecPreferenceScreen;

import com.android.settings.Utils;
import com.android.settings.core.BasePreferenceController;
import com.android.settingslib.core.lifecycle.LifecycleObserver;
import com.android.settingslib.core.lifecycle.events.OnPause;
import com.android.settingslib.core.lifecycle.events.OnResume;

import com.samsung.android.feature.SemCscFeature;
import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;
import com.samsung.android.settings.usefulfeature.Usefulfeature;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public class TvEdgeHandlerPreferenceController extends BasePreferenceController
        implements LifecycleObserver, OnResume, OnPause {
    private static final String KEY_TV_EDGE = "tv_edge_handler";
    public static final String TVMODE_EDGE_STATUS_DB = "tvmode_edge_status_state";
    public static final int TVMODE_EDGE_STATUS_OFF = 0;
    private Usefulfeature mHost;
    private SecPreferenceScreen mPreference;
    private ContentObserver mTvModeEdgeObserver;

    public TvEdgeHandlerPreferenceController(Context context) {
        this(context, KEY_TV_EDGE);
    }

    private boolean getTvmodeEdgeStatus() {
        return Settings.System.getInt(this.mContext.getContentResolver(), TVMODE_EDGE_STATUS_DB, 0)
                != 0;
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        this.mPreference = (SecPreferenceScreen) preferenceScreen.findPreference(KEY_TV_EDGE);
        this.mTvModeEdgeObserver =
                new ContentObserver(
                        new Handler(
                                Looper
                                        .getMainLooper())) { // from class:
                                                             // com.samsung.android.settings.usefulfeature.tvedgehandler.TvEdgeHandlerPreferenceController.1
                    @Override // android.database.ContentObserver
                    public final void onChange(boolean z) {
                        TvEdgeHandlerPreferenceController tvEdgeHandlerPreferenceController =
                                TvEdgeHandlerPreferenceController.this;
                        tvEdgeHandlerPreferenceController.updateState(
                                tvEdgeHandlerPreferenceController.mPreference);
                    }
                };
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        return (SemCscFeature.getInstance()
                                .getString(
                                        "CscFeature_SystemUI_ConfigDefQuickSettingItem",
                                        ApnSettings.MVNO_NONE)
                                .contains("TvMode")
                        && Utils.hasPackage(this.mContext, "com.samsung.tvmode")
                        && getTvmodeEdgeStatus())
                ? 0
                : 3;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ Class getBackgroundWorkerClass() {
        return null;
    }

    public List getBackupKeys() {
        return new ArrayList();
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ IntentFilter getIntentFilter() {
        return null;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Intent getLaunchIntent() {
        return null;
    }

    @Override // com.android.settings.slices.Sliceable
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

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public boolean handlePreferenceTreeClick(Preference preference) {
        if (preference.equals(this.mPreference)) {
            try {
                Intent intent = new Intent();
                intent.setClassName(
                        "com.samsung.tvmode", "com.samsung.tvmode.handler.EdgeSettingsActivity");
                this.mContext.startActivity(intent);
            } catch (ActivityNotFoundException e) {
                e.printStackTrace();
            }
        }
        return super.handlePreferenceTreeClick(preference);
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean hasAsyncUpdate() {
        return false;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean isControllable() {
        return false;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean isPublicSlice() {
        return false;
    }

    @Override // com.android.settings.slices.Sliceable
    public boolean isSliceable() {
        return getAvailabilityStatus() != 3;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Controllable$ControllableType needUserInteraction(
            Object obj) {
        return Controllable$ControllableType.NO_INTERACTION;
    }

    @Override // com.android.settingslib.core.lifecycle.events.OnPause
    public void onPause() {
        if (this.mPreference == null || this.mTvModeEdgeObserver == null) {
            return;
        }
        this.mContext.getContentResolver().unregisterContentObserver(this.mTvModeEdgeObserver);
    }

    @Override // com.android.settingslib.core.lifecycle.events.OnResume
    public void onResume() {
        if (this.mTvModeEdgeObserver != null) {
            this.mContext
                    .getContentResolver()
                    .registerContentObserver(
                            Settings.System.getUriFor(TVMODE_EDGE_STATUS_DB),
                            true,
                            this.mTvModeEdgeObserver);
        }
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return false;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlResult setValue(ControlValue controlValue) {
        return null;
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public void updateState(Preference preference) {
        super.updateState(preference);
        this.mPreference.setVisible(isAvailable());
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }

    public TvEdgeHandlerPreferenceController(Context context, Usefulfeature usefulfeature) {
        this(context, KEY_TV_EDGE);
        this.mHost = usefulfeature;
        if (usefulfeature != null) {
            usefulfeature.getLifecycle().addObserver(this);
        }
    }

    public TvEdgeHandlerPreferenceController(Context context, String str) {
        super(context, str);
    }

    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {}
}
