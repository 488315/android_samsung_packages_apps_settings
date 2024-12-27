package com.samsung.android.settings.display.controller;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.Signature;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.util.Log;

import androidx.lifecycle.LifecycleObserver;
import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;

import com.android.settings.Utils;
import com.android.settings.core.BasePreferenceController;
import com.android.settingslib.core.lifecycle.events.OnStart;
import com.android.settingslib.core.lifecycle.events.OnStop;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.settings.Rune;
import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;
import com.samsung.android.settings.display.SecDisplayUtils;
import com.samsung.android.settings.widget.SecRestrictedPreference;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class SecDexTimeoutPreferenceController extends BasePreferenceController
        implements LifecycleObserver, OnStart, OnStop {
    private static final String TAG = "SecDexTimeoutPreferenceController";
    private static final String TIMEOUT_DEX = "timeout_dex";
    private ContentObserver mContentObserver;
    private SecRestrictedPreference mPreference;

    public SecDexTimeoutPreferenceController(Context context, String str) {
        super(context, str);
        this.mContentObserver =
                new ContentObserver(
                        new Handler(
                                Looper
                                        .getMainLooper())) { // from class:
                                                             // com.samsung.android.settings.display.controller.SecDexTimeoutPreferenceController.1
                    @Override // android.database.ContentObserver
                    public final void onChange(boolean z) {
                        super.onChange(z);
                        SecDexTimeoutPreferenceController secDexTimeoutPreferenceController =
                                SecDexTimeoutPreferenceController.this;
                        secDexTimeoutPreferenceController.updateState(
                                secDexTimeoutPreferenceController.mPreference);
                    }
                };
    }

    private ContentResolver getContentResolver() {
        return this.mContext.getContentResolver();
    }

    private Uri getDatabaseUri() {
        return Settings.System.getUriFor("screen_off_timeout");
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        this.mPreference =
                (SecRestrictedPreference) preferenceScreen.findPreference(getPreferenceKey());
        super.displayPreference(preferenceScreen);
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        Log.i(TAG, "isSamsungDexMode : " + Rune.isSamsungDexMode(this.mContext));
        Log.i(TAG, "isDesktopStandaloneMode : " + Utils.isDesktopStandaloneMode(this.mContext));
        Log.i(TAG, "isNewDex : " + Utils.isNewDexMode(this.mContext));
        return (!Rune.isSamsungDexMode(this.mContext)
                        || Utils.isDesktopStandaloneMode(this.mContext)
                        || Utils.isNewDexMode(this.mContext))
                ? 3
                : 0;
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

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public CharSequence getSummary() {
        long longValue =
                Long.valueOf(
                                Utils.getStringFromDeXSettings(
                                        getContentResolver(),
                                        TIMEOUT_DEX,
                                        Integer.toString(600000)))
                        .longValue();
        Context context = this.mContext;
        if (longValue < 0) {
            Signature[] signatureArr = SecDisplayUtils.SIGNATURES;
            return ApnSettings.MVNO_NONE;
        }
        CharSequence[] dexScreenTimeoutEntryandValue =
                SecDisplayUtils.getDexScreenTimeoutEntryandValue(1, longValue, context);
        CharSequence[] dexScreenTimeoutEntryandValue2 =
                SecDisplayUtils.getDexScreenTimeoutEntryandValue(2, longValue, context);
        if (dexScreenTimeoutEntryandValue == null || dexScreenTimeoutEntryandValue.length == 0) {
            return ApnSettings.MVNO_NONE;
        }
        int i = 0;
        for (int i2 = 0; i2 < dexScreenTimeoutEntryandValue2.length; i2++) {
            if (longValue >= Long.parseLong(dexScreenTimeoutEntryandValue2[i2].toString())) {
                i = i2;
            }
        }
        return dexScreenTimeoutEntryandValue[i].toString();
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlValue getValue() {
        return null;
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public boolean handlePreferenceTreeClick(Preference preference) {
        if (!getPreferenceKey().equals(preference.getKey())) {
            return false;
        }
        if (isAvailable()) {
            Intent intent = new Intent("com.samsung.settings.ScreenTimeout");
            intent.putExtra("Screen_timeout_dex_mode", true);
            this.mContext.startActivity(intent);
        }
        return true;
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

    @Override // com.android.settingslib.core.lifecycle.events.OnStart
    public void onStart() {
        getContentResolver()
                .registerContentObserver(getDatabaseUri(), false, this.mContentObserver);
    }

    @Override // com.android.settingslib.core.lifecycle.events.OnStop
    public void onStop() {
        getContentResolver().unregisterContentObserver(this.mContentObserver);
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return false;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlResult setValue(ControlValue controlValue) {
        return null;
    }

    /* JADX WARN: Removed duplicated region for block: B:15:0x0060  */
    /* JADX WARN: Removed duplicated region for block: B:17:0x0066  */
    @Override // com.android.settingslib.core.AbstractPreferenceController
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void updateState(androidx.preference.Preference r10) {
        /*
            r9 = this;
            super.updateState(r10)
            if (r10 != 0) goto L6
            return
        L6:
            com.samsung.android.settings.widget.SecRestrictedPreference r10 = (com.samsung.android.settings.widget.SecRestrictedPreference) r10
            r9.refreshSummary(r10)
            r0 = 1
            androidx.preference.SecPreferenceUtils.applySummaryColor(r10, r0)
            android.content.Context r10 = r9.mContext
            android.content.pm.Signature[] r1 = com.samsung.android.settings.display.SecDisplayUtils.SIGNATURES
            java.lang.String r1 = "SecDisplayUtils"
            java.lang.String r2 = "content://com.sec.knox.provider/DexPolicy"
            android.net.Uri r4 = android.net.Uri.parse(r2)
            android.content.ContentResolver r3 = r10.getContentResolver()     // Catch: android.database.sqlite.SQLiteException -> L29
            java.lang.String r6 = "isScreenTimeoutChangeAllowed"
            r8 = 0
            r5 = 0
            r7 = 0
            android.database.Cursor r10 = r3.query(r4, r5, r6, r7, r8)     // Catch: android.database.sqlite.SQLiteException -> L29
            goto L2f
        L29:
            java.lang.String r10 = "SQLiteException MDM DeXPolicy"
            android.util.Log.e(r1, r10)
            r10 = 0
        L2f:
            if (r10 == 0) goto L58
            r10.moveToFirst()
            java.lang.String r2 = "isScreenTimeoutChangeAllowed"
            int r2 = r10.getColumnIndex(r2)     // Catch: java.lang.Throwable -> L49 android.database.CursorIndexOutOfBoundsException -> L4b
            java.lang.String r2 = r10.getString(r2)     // Catch: java.lang.Throwable -> L49 android.database.CursorIndexOutOfBoundsException -> L4b
            java.lang.String r3 = "false"
            boolean r2 = r2.equals(r3)     // Catch: java.lang.Throwable -> L49 android.database.CursorIndexOutOfBoundsException -> L4b
            r2 = r2 ^ r0
            r10.close()
            goto L59
        L49:
            r9 = move-exception
            goto L54
        L4b:
            java.lang.String r2 = "CursorIndexOutOfBoundsException in isScreenTimeoutChangeAllowed"
            android.util.Log.e(r1, r2)     // Catch: java.lang.Throwable -> L49
            r10.close()
            goto L58
        L54:
            r10.close()
            throw r9
        L58:
            r2 = r0
        L59:
            java.lang.String r10 = "isDeXScreenTimeoutChangeAllowed  "
            com.android.settings.AirplaneModeEnabler$$ExternalSyntheticOutline0.m(r10, r1, r2)
            if (r2 == 0) goto L66
            com.samsung.android.settings.widget.SecRestrictedPreference r9 = r9.mPreference
            r9.setEnabled(r0)
            goto L6c
        L66:
            com.samsung.android.settings.widget.SecRestrictedPreference r9 = r9.mPreference
            r10 = 0
            r9.setEnabled(r10)
        L6c:
            return
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.samsung.android.settings.display.controller.SecDexTimeoutPreferenceController.updateState(androidx.preference.Preference):void");
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }

    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {}
}
