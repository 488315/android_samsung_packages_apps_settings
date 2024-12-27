package com.samsung.android.settings.usefulfeature.intelligenceservice;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.UserHandle;
import android.provider.Settings;

import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;

import com.android.settings.Utils;
import com.android.settings.core.TogglePreferenceController;
import com.android.settingslib.core.lifecycle.LifecycleObserver;
import com.android.settingslib.core.lifecycle.events.OnPause;
import com.android.settingslib.core.lifecycle.events.OnResume;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.settings.cube.Controllable$ControllableType;
import com.samsung.android.settings.logging.SALogging;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
@Metadata(
        d1 = {
            "\u0000R\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\u0018\u0002\n"
                + "\u0000\n"
                + "\u0002\u0018\u0002\n"
                + "\u0000\n"
                + "\u0002\u0010\u000e\n"
                + "\u0002\b\u0002\n"
                + "\u0002\u0018\u0002\n"
                + "\u0000\n"
                + "\u0002\u0018\u0002\n"
                + "\u0000\n"
                + "\u0002\u0010\b\n"
                + "\u0000\n"
                + "\u0002\u0010\u0002\n"
                + "\u0000\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\b\u0002\n"
                + "\u0002\u0010\u000b\n"
                + "\u0000\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\b"
                + "\t\b\u0007\u0018\u00002\u00020\u00012\u00020\u00022\u00020\u00032\u00020\u0004B\u0019\u0012\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006\u0012\b\u0010\u0007\u001a\u0004\u0018\u00010\b¢\u0006\u0002\u0010"
                + "\tJ\u0010\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u0013H\u0016J\b\u0010\u0014\u001a\u00020\u000fH\u0016J\u0012\u0010\u0015\u001a\u00020\u00162\b\u0010\u0017\u001a\u0004\u0018\u00010\u0018H\u0016J\u000e\u0010\u0019\u001a\u00020\u00112\u0006\u0010\u001a\u001a\u00020\u000fJ\b\u0010\u001b\u001a\u00020\u0016H\u0016J\b\u0010\u001c\u001a\u00020\u0016H\u0016J\b\u0010\u001d\u001a\u00020\u0011H\u0016J\b\u0010\u001e\u001a\u00020\u0011H\u0016J\u0006\u0010\u001f\u001a\u00020\u0011J\u0010\u0010"
                + " \u001a\u00020\u00162\u0006\u0010\u001b\u001a\u00020\u0016H\u0016R\u0010\u0010\n"
                + "\u001a\u0004\u0018\u00010\u000bX\u0082\u000e¢\u0006\u0002\n"
                + "\u0000R\u0010\u0010\f\u001a\u0004\u0018\u00010\r"
                + "X\u0082\u000e¢\u0006\u0002\n"
                + "\u0000R\u000e\u0010\u000e\u001a\u00020\u000fX\u0082\u000e¢\u0006\u0002\n"
                + "\u0000¨\u0006!"
        },
        d2 = {
            "Lcom/samsung/android/settings/usefulfeature/intelligenceservice/PreventOnlineProcessingPreferenceController;",
            "Lcom/android/settings/core/TogglePreferenceController;",
            "Lcom/android/settingslib/core/lifecycle/LifecycleObserver;",
            "Lcom/android/settingslib/core/lifecycle/events/OnResume;",
            "Lcom/android/settingslib/core/lifecycle/events/OnPause;",
            "context",
            "Landroid/content/Context;",
            "preferenceKey",
            ApnSettings.MVNO_NONE,
            "(Landroid/content/Context;Ljava/lang/String;)V",
            "mPreference",
            "Lcom/samsung/android/settings/usefulfeature/intelligenceservice/SecIntelligenceSwitchPreferenceScreen;",
            "mPreventOnlineProcessingObserver",
            "Landroid/database/ContentObserver;",
            "mUserId",
            ApnSettings.MVNO_NONE,
            "displayPreference",
            ApnSettings.MVNO_NONE,
            "screen",
            "Landroidx/preference/PreferenceScreen;",
            "getAvailabilityStatus",
            "handlePreferenceTreeClick",
            ApnSettings.MVNO_NONE,
            "preference",
            "Landroidx/preference/Preference;",
            "init",
            "userId",
            "isChecked",
            "isControllable",
            "onPause",
            "onResume",
            "refreshPreferenceAsPerKnoxPolicy",
            "setChecked",
            "applications__sources__apps__SecSettings__android_common__SecSettings-core"
        },
        k = 1,
        mv = {1, 9, 0},
        xi = 48)
/* loaded from: classes3.dex */
public final class PreventOnlineProcessingPreferenceController extends TogglePreferenceController
        implements LifecycleObserver, OnResume, OnPause {
    public static final int $stable = 8;
    private SecIntelligenceSwitchPreferenceScreen mPreference;
    private ContentObserver mPreventOnlineProcessingObserver;
    private int mUserId;

    public PreventOnlineProcessingPreferenceController(Context context, String str) {
        super(context, str);
        this.mUserId = UserHandle.myUserId();
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen screen) {
        Intrinsics.checkNotNullParameter(screen, "screen");
        super.displayPreference(screen);
        this.mPreference =
                (SecIntelligenceSwitchPreferenceScreen) screen.findPreference(getPreferenceKey());
        final Handler handler = new Handler(Looper.getMainLooper());
        this.mPreventOnlineProcessingObserver =
                new ContentObserver(
                        handler) { // from class:
                                   // com.samsung.android.settings.usefulfeature.intelligenceservice.PreventOnlineProcessingPreferenceController$displayPreference$1
                    @Override // android.database.ContentObserver
                    public final void onChange(boolean z) {
                        SecIntelligenceSwitchPreferenceScreen secIntelligenceSwitchPreferenceScreen;
                        SecIntelligenceSwitchPreferenceScreen
                                secIntelligenceSwitchPreferenceScreen2;
                        SecIntelligenceSwitchPreferenceScreen
                                secIntelligenceSwitchPreferenceScreen3;
                        secIntelligenceSwitchPreferenceScreen =
                                PreventOnlineProcessingPreferenceController.this.mPreference;
                        if (secIntelligenceSwitchPreferenceScreen != null) {
                            boolean threadEnabled =
                                    PreventOnlineProcessingPreferenceController.this
                                            .getThreadEnabled();
                            secIntelligenceSwitchPreferenceScreen2 =
                                    PreventOnlineProcessingPreferenceController.this.mPreference;
                            Intrinsics.checkNotNull(secIntelligenceSwitchPreferenceScreen2);
                            if (threadEnabled != secIntelligenceSwitchPreferenceScreen2.mChecked) {
                                secIntelligenceSwitchPreferenceScreen3 =
                                        PreventOnlineProcessingPreferenceController.this
                                                .mPreference;
                                Intrinsics.checkNotNull(secIntelligenceSwitchPreferenceScreen3);
                                secIntelligenceSwitchPreferenceScreen3.setChecked(
                                        PreventOnlineProcessingPreferenceController.this
                                                .getThreadEnabled());
                            }
                        }
                    }
                };
        refreshPreferenceAsPerKnoxPolicy();
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
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
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ String getStatusText() {
        return super.getStatusText();
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public boolean handlePreferenceTreeClick(Preference preference) {
        if (Intrinsics.areEqual(
                getPreferenceKey(), preference != null ? preference.getKey() : null)) {
            Intrinsics.checkNotNull(preference);
            Bundle extras = preference.getExtras();
            Intrinsics.checkNotNullExpressionValue(extras, "getExtras(...)");
            extras.putInt("USER_ID", this.mUserId);
        }
        return super.handlePreferenceTreeClick(preference);
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

    public final void init(int userId) {
        this.mUserId = userId;
    }

    @Override // com.android.settings.core.TogglePreferenceController
    /* renamed from: isChecked */
    public boolean getThreadEnabled() {
        return Settings.System.getIntForUser(
                        this.mContext.getContentResolver(),
                        "prevent_online_processing",
                        0,
                        this.mUserId)
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
        if (this.mPreference != null) {
            if (this.mPreventOnlineProcessingObserver != null) {
                ContentResolver contentResolver = this.mContext.getContentResolver();
                ContentObserver contentObserver = this.mPreventOnlineProcessingObserver;
                Intrinsics.checkNotNull(contentObserver);
                contentResolver.unregisterContentObserver(contentObserver);
            }
            refreshPreferenceAsPerKnoxPolicy();
        }
    }

    @Override // com.android.settingslib.core.lifecycle.events.OnResume
    public void onResume() {
        if (this.mPreference != null) {
            if (this.mPreventOnlineProcessingObserver != null) {
                ContentResolver contentResolver = this.mContext.getContentResolver();
                Uri uriFor = Settings.System.getUriFor("prevent_online_processing");
                ContentObserver contentObserver = this.mPreventOnlineProcessingObserver;
                Intrinsics.checkNotNull(contentObserver);
                contentResolver.registerContentObserverAsUser(
                        uriFor, true, contentObserver, UserHandle.of(this.mUserId));
            }
            refreshPreferenceAsPerKnoxPolicy();
        }
    }

    public final void refreshPreferenceAsPerKnoxPolicy() {
        boolean z =
                Utils.getEnterprisePolicyEnabled(
                                this.mContext,
                                "content://com.sec.knox.provider/RestrictionPolicy",
                                "isIntelligenceOnlineProcessingAllowed",
                                new String[] {String.valueOf(this.mUserId)})
                        != 0;
        SecIntelligenceSwitchPreferenceScreen secIntelligenceSwitchPreferenceScreen =
                this.mPreference;
        if (secIntelligenceSwitchPreferenceScreen != null) {
            secIntelligenceSwitchPreferenceScreen.setEnabled(z);
        }
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return super.runDefaultAction();
    }

    @Override // com.android.settings.core.TogglePreferenceController
    public boolean setChecked(boolean isChecked) {
        Settings.System.putIntForUser(
                this.mContext.getContentResolver(),
                "prevent_online_processing",
                isChecked ? 1 : 0,
                this.mUserId);
        SALogging.insertSALog(isChecked ? 1L : 0L, "AI003", String.valueOf(80001), (String) null);
        return true;
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }
}
