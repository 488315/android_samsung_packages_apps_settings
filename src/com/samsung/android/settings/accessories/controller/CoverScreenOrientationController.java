package com.samsung.android.settings.accessories.controller;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.ContentObserver;
import android.os.Handler;
import android.os.Looper;
import android.os.UserHandle;
import android.provider.Settings;

import androidx.preference.PreferenceScreen;
import androidx.preference.SecPreferenceScreen;
import androidx.preference.SecPreferenceUtils;

import com.android.settings.R;
import com.android.settings.Utils;
import com.android.settings.core.BasePreferenceController;
import com.android.settings.core.PreferenceControllerMixin;
import com.android.settingslib.core.lifecycle.LifecycleObserver;
import com.android.settingslib.core.lifecycle.events.OnResume;
import com.android.settingslib.core.lifecycle.events.OnStart;
import com.android.settingslib.core.lifecycle.events.OnStop;

import com.samsung.android.knox.SemPersonaManager;
import com.samsung.android.settings.accessories.AccessoriesUtils;
import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;
import com.samsung.android.settings.usefulfeature.UsefulfeatureUtils;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class CoverScreenOrientationController extends BasePreferenceController
        implements PreferenceControllerMixin, LifecycleObserver, OnResume, OnStart, OnStop {
    private static final int BOTTOM_TO_TOP_MODE = 1;
    private static final int TOP_TO_BOTTOM_MODE = 0;
    private ContentObserver mAodModeObserver;
    private SecPreferenceScreen mPreference;

    public CoverScreenOrientationController(Context context, String str) {
        super(context, str);
        this.mAodModeObserver = null;
    }

    private ContentObserver getCoverTextDirectionObserver() {
        if (this.mAodModeObserver == null) {
            this.mAodModeObserver =
                    new ContentObserver(
                            new Handler(
                                    Looper
                                            .getMainLooper())) { // from class:
                                                                 // com.samsung.android.settings.accessories.controller.CoverScreenOrientationController.1
                        @Override // android.database.ContentObserver
                        public final void onChange(boolean z) {
                            CoverScreenOrientationController.this.updatePreference();
                        }
                    };
        }
        return this.mAodModeObserver;
    }

    private String setCoverScreenOrientationSummary() {
        return Settings.System.getInt(this.mContext.getContentResolver(), "cover_text_direction", 0)
                        == 0
                ? this.mContext.getString(R.string.top_to_bottom_mode)
                : this.mContext.getString(R.string.bottom_to_top_mode);
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        SecPreferenceScreen secPreferenceScreen =
                (SecPreferenceScreen) preferenceScreen.findPreference(getPreferenceKey());
        this.mPreference = secPreferenceScreen;
        if (secPreferenceScreen != null) {
            secPreferenceScreen.getClass();
            SecPreferenceUtils.applySummaryColor(secPreferenceScreen, true);
        }
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        return (UsefulfeatureUtils.isCoverVerified(this.mContext)
                        && AccessoriesUtils.hasCoverSettingCoverOrientation(this.mContext)
                        && !Utils.isTablet()
                        && !SemPersonaManager.isKnoxId(UserHandle.myUserId()))
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
    public /* bridge */ /* synthetic */ boolean isSliceable() {
        return false;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Controllable$ControllableType needUserInteraction(
            Object obj) {
        return Controllable$ControllableType.NO_INTERACTION;
    }

    @Override // com.android.settingslib.core.lifecycle.events.OnResume
    public void onResume() {
        updatePreference();
    }

    @Override // com.android.settingslib.core.lifecycle.events.OnStart
    public void onStart() {
        this.mContext
                .getContentResolver()
                .registerContentObserver(
                        Settings.System.getUriFor("cover_text_direction"),
                        true,
                        getCoverTextDirectionObserver());
    }

    @Override // com.android.settingslib.core.lifecycle.events.OnStop
    public void onStop() {
        this.mContext
                .getContentResolver()
                .unregisterContentObserver(getCoverTextDirectionObserver());
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return false;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlResult setValue(ControlValue controlValue) {
        return null;
    }

    public void updatePreference() {
        SecPreferenceScreen secPreferenceScreen = this.mPreference;
        if (secPreferenceScreen != null) {
            secPreferenceScreen.setSummary(setCoverScreenOrientationSummary());
        }
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }

    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {}
}
