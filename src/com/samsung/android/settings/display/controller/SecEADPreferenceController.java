package com.samsung.android.settings.display.controller;

import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;

import androidx.preference.PreferenceScreen;
import androidx.preference.SecSwitchPreference;

import com.android.settings.R;
import com.android.settings.core.TogglePreferenceController;
import com.android.settingslib.core.lifecycle.LifecycleObserver;
import com.android.settingslib.core.lifecycle.events.OnPause;
import com.android.settingslib.core.lifecycle.events.OnResume;

import com.samsung.android.settings.Rune;
import com.samsung.android.settings.cube.Controllable$ControllableType;
import com.samsung.android.settings.logging.LoggingHelper;

import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class SecEADPreferenceController extends TogglePreferenceController
        implements LifecycleObserver, OnResume, OnPause {
    private ContentObserver mContentObserver;
    private AlertDialog mDialogNoticeCameraUse;
    private boolean mNotifiedCameraUse;
    private SecSwitchPreference mPreference;

    public SecEADPreferenceController(Context context, String str) {
        super(context, str);
        this.mNotifiedCameraUse = false;
    }

    private ContentResolver getContentResolver() {
        return this.mContext.getContentResolver();
    }

    private Uri getDatabaseUri() {
        return Settings.System.getUriFor("ead_enabled");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showBrightnessAlertDialog$0(
            DialogInterface dialogInterface) {
        Settings.System.putInt(getContentResolver(), "notified_ead_camera_use", 1);
        this.mDialogNoticeCameraUse = null;
        this.mNotifiedCameraUse = true;
    }

    private void showBrightnessAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this.mContext);
        builder.setMessage(R.string.sec_ead_notice_camera_use)
                .setPositiveButton(R.string.common_ok, (DialogInterface.OnClickListener) null)
                .setOnDismissListener(
                        new DialogInterface
                                .OnDismissListener() { // from class:
                                                       // com.samsung.android.settings.display.controller.SecEADPreferenceController$$ExternalSyntheticLambda0
                            @Override // android.content.DialogInterface.OnDismissListener
                            public final void onDismiss(DialogInterface dialogInterface) {
                                SecEADPreferenceController.this.lambda$showBrightnessAlertDialog$0(
                                        dialogInterface);
                            }
                        });
        if (this.mDialogNoticeCameraUse == null) {
            this.mDialogNoticeCameraUse = builder.create();
        }
        this.mDialogNoticeCameraUse.show();
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        SecSwitchPreference secSwitchPreference =
                (SecSwitchPreference) preferenceScreen.findPreference(getPreferenceKey());
        this.mPreference = secSwitchPreference;
        refreshSummary(secSwitchPreference);
        super.displayPreference(preferenceScreen);
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        String str = Rune.COMMON_CONFIG_PACKAGE_NAME_SMART_MANAGER;
        return 0;
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
        return R.string.menu_key_display;
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
        return Settings.System.getInt(getContentResolver(), "ead_enabled", 0) == 1;
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

    @Override // com.android.settingslib.core.lifecycle.events.OnPause
    public void onPause() {
        getContentResolver().unregisterContentObserver(this.mContentObserver);
    }

    @Override // com.android.settingslib.core.lifecycle.events.OnResume
    public void onResume() {
        if (this.mContentObserver == null) {
            this.mContentObserver =
                    new ContentObserver(
                            new Handler(
                                    Looper
                                            .getMainLooper())) { // from class:
                                                                 // com.samsung.android.settings.display.controller.SecEADPreferenceController.1
                        @Override // android.database.ContentObserver
                        public final void onChange(boolean z, Uri uri) {
                            super.onChange(z, uri);
                            SecEADPreferenceController secEADPreferenceController =
                                    SecEADPreferenceController.this;
                            secEADPreferenceController.updateState(
                                    secEADPreferenceController.mPreference);
                        }
                    };
        }
        getContentResolver()
                .registerContentObserver(getDatabaseUri(), false, this.mContentObserver);
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return super.runDefaultAction();
    }

    @Override // com.android.settings.core.TogglePreferenceController
    public boolean setChecked(boolean z) {
        LoggingHelper.insertEventLogging(46, 7419, z ? 1L : 0L);
        Settings.System.putInt(getContentResolver(), "ead_enabled", z ? 1 : 0);
        if (z && !this.mNotifiedCameraUse) {
            boolean z2 =
                    Settings.System.getInt(getContentResolver(), "notified_ead_camera_use", 0) == 1;
            this.mNotifiedCameraUse = z2;
            if (!z2) {
                showBrightnessAlertDialog();
            }
        }
        return true;
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }
}
