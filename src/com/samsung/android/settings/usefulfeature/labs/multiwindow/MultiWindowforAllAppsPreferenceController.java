package com.samsung.android.settings.usefulfeature.labs.multiwindow;

import android.R;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;

import androidx.preference.PreferenceScreen;
import androidx.preference.SecSwitchPreference;

import com.android.settings.core.SubSettingLauncher;
import com.android.settings.core.TogglePreferenceController;
import com.android.settingslib.core.lifecycle.LifecycleObserver;
import com.android.settingslib.core.lifecycle.events.OnPause;
import com.android.settingslib.core.lifecycle.events.OnResume;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.settings.account.controller.SecAccountPreferenceController$$ExternalSyntheticOutline0;
import com.samsung.android.settings.cube.Controllable$ControllableType;
import com.samsung.android.settings.logging.SALogging;
import com.samsung.android.settings.usefulfeature.labs.LabsSettings;
import com.sec.ims.configuration.DATA;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
@Metadata(
        d1 = {
            "\u0000P\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\u0018\u0002\n"
                + "\u0000\n"
                + "\u0002\u0018\u0002\n"
                + "\u0000\n"
                + "\u0002\u0010\u000e\n"
                + "\u0002\b\u0005\n"
                + "\u0002\u0018\u0002\n"
                + "\u0000\n"
                + "\u0002\u0018\u0002\n"
                + "\u0000\n"
                + "\u0002\u0010\u0002\n"
                + "\u0000\n"
                + "\u0002\u0018\u0002\n"
                + "\u0000\n"
                + "\u0002\u0010\b\n"
                + "\u0000\n"
                + "\u0002\u0018\u0002\n"
                + "\u0000\n"
                + "\u0002\u0010\u000b\n"
                + "\u0002\b\u0005\b\u0007\u0018\u00002\u00020\u00012\u00020\u00022\u00020\u00032\u00020\u0004B\u0019\u0012\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006\u0012\b\u0010\u0007\u001a\u0004\u0018\u00010\b¢\u0006\u0002\u0010"
                + "\tJ\u0010\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u0014H\u0016J\b\u0010\u0015\u001a\u00020\u0016H\u0016J\n"
                + "\u0010\u0017\u001a\u0004\u0018\u00010\u0018H\u0016J\b\u0010\u0019\u001a\u00020\u001aH\u0016J\b\u0010\u001b\u001a\u00020\u001aH\u0016J\b\u0010\u001c\u001a\u00020\u0012H\u0016J\b\u0010\u001d\u001a\u00020\u0012H\u0016J\u0010\u0010\u001e\u001a\u00020\u001a2\u0006\u0010\u0019\u001a\u00020\u001aH\u0016R\u0014\u0010\n"
                + "\u001a\u00020\bX\u0086D¢\u0006\b\n"
                + "\u0000\u001a\u0004\b\u000b\u0010\fR\u0010\u0010\r"
                + "\u001a\u0004\u0018\u00010\u000eX\u0082\u000e¢\u0006\u0002\n"
                + "\u0000R\u0010\u0010\u000f\u001a\u0004\u0018\u00010\u0010X\u0082\u000e¢\u0006\u0002\n"
                + "\u0000¨\u0006\u001f"
        },
        d2 = {
            "Lcom/samsung/android/settings/usefulfeature/labs/multiwindow/MultiWindowforAllAppsPreferenceController;",
            "Lcom/android/settings/core/TogglePreferenceController;",
            "Lcom/android/settingslib/core/lifecycle/LifecycleObserver;",
            "Lcom/android/settingslib/core/lifecycle/events/OnResume;",
            "Lcom/android/settingslib/core/lifecycle/events/OnPause;",
            "context",
            "Landroid/content/Context;",
            "preferenceKey",
            ApnSettings.MVNO_NONE,
            "(Landroid/content/Context;Ljava/lang/String;)V",
            "DEVELOPMENT_FORCE_RESIZABLE_ACTIVITIES",
            "getDEVELOPMENT_FORCE_RESIZABLE_ACTIVITIES",
            "()Ljava/lang/String;",
            "mMultiWindowforAllAppsObserver",
            "Landroid/database/ContentObserver;",
            "mPreference",
            "Landroidx/preference/SecSwitchPreference;",
            "displayPreference",
            ApnSettings.MVNO_NONE,
            "screen",
            "Landroidx/preference/PreferenceScreen;",
            "getAvailabilityStatus",
            ApnSettings.MVNO_NONE,
            "getLaunchIntent",
            "Landroid/content/Intent;",
            "isChecked",
            ApnSettings.MVNO_NONE,
            "isControllable",
            "onPause",
            "onResume",
            "setChecked",
            "applications__sources__apps__SecSettings__android_common__SecSettings-core"
        },
        k = 1,
        mv = {1, 9, 0},
        xi = 48)
/* loaded from: classes3.dex */
public final class MultiWindowforAllAppsPreferenceController extends TogglePreferenceController
        implements LifecycleObserver, OnResume, OnPause {
    public static final int $stable = 8;
    private final String DEVELOPMENT_FORCE_RESIZABLE_ACTIVITIES;
    private ContentObserver mMultiWindowforAllAppsObserver;
    private SecSwitchPreference mPreference;

    public MultiWindowforAllAppsPreferenceController(Context context, String str) {
        super(context, str);
        this.DEVELOPMENT_FORCE_RESIZABLE_ACTIVITIES = "force_resizable_activities";
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen screen) {
        Intrinsics.checkNotNullParameter(screen, "screen");
        super.displayPreference(screen);
        this.mPreference = (SecSwitchPreference) screen.findPreference(getPreferenceKey());
        final Handler handler = new Handler(Looper.getMainLooper());
        this.mMultiWindowforAllAppsObserver =
                new ContentObserver(
                        handler) { // from class:
                                   // com.samsung.android.settings.usefulfeature.labs.multiwindow.MultiWindowforAllAppsPreferenceController$displayPreference$1
                    @Override // android.database.ContentObserver
                    public final void onChange(boolean z) {
                        SecSwitchPreference secSwitchPreference;
                        SecSwitchPreference secSwitchPreference2;
                        SecSwitchPreference secSwitchPreference3;
                        secSwitchPreference =
                                MultiWindowforAllAppsPreferenceController.this.mPreference;
                        if (secSwitchPreference != null) {
                            boolean threadEnabled =
                                    MultiWindowforAllAppsPreferenceController.this
                                            .getThreadEnabled();
                            secSwitchPreference2 =
                                    MultiWindowforAllAppsPreferenceController.this.mPreference;
                            Intrinsics.checkNotNull(secSwitchPreference2);
                            if (threadEnabled != secSwitchPreference2.isChecked()) {
                                secSwitchPreference3 =
                                        MultiWindowforAllAppsPreferenceController.this.mPreference;
                                Intrinsics.checkNotNull(secSwitchPreference3);
                                secSwitchPreference3.setChecked(
                                        MultiWindowforAllAppsPreferenceController.this
                                                .getThreadEnabled());
                            }
                        }
                    }
                };
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        return this.mContext.getResources().getBoolean(R.bool.config_unfoldTransitionHapticsEnabled)
                ? 0
                : 3;
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

    public final String getDEVELOPMENT_FORCE_RESIZABLE_ACTIVITIES() {
        return this.DEVELOPMENT_FORCE_RESIZABLE_ACTIVITIES;
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ IntentFilter getIntentFilter() {
        return null;
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public Intent getLaunchIntent() {
        Bundle bundle = new Bundle();
        bundle.putString(":settings:fragment_args_key", getPreferenceKey());
        SubSettingLauncher subSettingLauncher = new SubSettingLauncher(this.mContext);
        String name = LabsSettings.class.getName();
        SubSettingLauncher.LaunchRequest launchRequest = subSettingLauncher.mLaunchRequest;
        launchRequest.mDestinationName = name;
        launchRequest.mSourceMetricsCategory = 68000;
        launchRequest.mArguments = bundle;
        return SecAccountPreferenceController$$ExternalSyntheticOutline0.m(
                subSettingLauncher,
                null,
                com.android.settings.R.string.sec_labs_settings_title,
                268468224);
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
        return Settings.Global.getInt(
                        this.mContext.getContentResolver(),
                        this.DEVELOPMENT_FORCE_RESIZABLE_ACTIVITIES,
                        0)
                != 0;
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
        if (this.mPreference == null || this.mMultiWindowforAllAppsObserver == null) {
            return;
        }
        ContentResolver contentResolver = this.mContext.getContentResolver();
        ContentObserver contentObserver = this.mMultiWindowforAllAppsObserver;
        Intrinsics.checkNotNull(contentObserver);
        contentResolver.unregisterContentObserver(contentObserver);
    }

    @Override // com.android.settingslib.core.lifecycle.events.OnResume
    public void onResume() {
        if (this.mPreference == null || this.mMultiWindowforAllAppsObserver == null) {
            return;
        }
        ContentResolver contentResolver = this.mContext.getContentResolver();
        Uri uriFor = Settings.Global.getUriFor(this.DEVELOPMENT_FORCE_RESIZABLE_ACTIVITIES);
        ContentObserver contentObserver = this.mMultiWindowforAllAppsObserver;
        Intrinsics.checkNotNull(contentObserver);
        contentResolver.registerContentObserver(uriFor, true, contentObserver);
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return super.runDefaultAction();
    }

    @Override // com.android.settings.core.TogglePreferenceController
    public boolean setChecked(boolean isChecked) {
        Settings.Global.putInt(
                this.mContext.getContentResolver(),
                this.DEVELOPMENT_FORCE_RESIZABLE_ACTIVITIES,
                isChecked ? 1 : 0);
        SALogging.insertSALog(
                String.valueOf(68000),
                "68100",
                isChecked ? "1000" : DATA.DM_FIELD_INDEX.PCSCF_DOMAIN);
        return true;
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }
}
