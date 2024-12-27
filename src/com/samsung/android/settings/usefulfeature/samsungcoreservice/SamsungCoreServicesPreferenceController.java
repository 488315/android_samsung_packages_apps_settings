package com.samsung.android.settings.usefulfeature.samsungcoreservice;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleEventObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.preference.Preference;

import com.android.settings.Utils;
import com.android.settings.core.TogglePreferenceController;

import com.samsung.android.settings.Rune;
import com.samsung.android.settings.cube.Controllable$ControllableType;
import com.samsung.android.util.SemLog;

import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public class SamsungCoreServicesPreferenceController extends TogglePreferenceController
        implements LifecycleEventObserver {
    private static final String CALENDAR_PACKAGE_NAME = "com.samsung.android.calendar";
    private static final String CALLLOG_PACKAGE_NAME = "logs.call";
    private static final String CONTACTS_PACKAGE_NAME = "com.samsung.android.contacts";
    private static final String FILES_PACKAGE_NAME = "com.sec.android.app.myfiles";
    private static final String GALLERY_PACKAGE_NAME = "com.google.android.providers.media";
    private static final String KEY_SAMSUNG_CORE_SERVICES = "samsung_core_services";
    private static final String METHOD_UPDATE_DATA_CONFIG_STATUS = "update_data_config_status";
    private static final String SCS_PACKAGE_NAME = "com.samsung.android.scs";
    private static final String SETTINGS_DB_DATA_CONFIG_ENABLED = "scs_search_data_config_enabled";
    private static final String SYNCTASKS_PACKAGE_NAME = "com.samsung.android.calendar.synctasks";
    private static final String TAG = "SamsungCoreServicesPreferenceController";
    private final int mDisabledFilesMediaCallendar;
    private boolean mIsChecked;
    private static final String AUTHORITY = "com.samsung.android.scs.ai.search";
    private static final Uri SEARCH_URI =
            new Uri.Builder().scheme("content").authority(AUTHORITY).build();

    public SamsungCoreServicesPreferenceController(Context context) {
        this(context, KEY_SAMSUNG_CORE_SERVICES);
    }

    private static int turnOffKthBit(int i, int... iArr) {
        for (int i2 : iArr) {
            i &= ~(1 << i2);
        }
        return i;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        return (Rune.isChinaModel() && Utils.hasPackage(this.mContext, "com.samsung.android.scs"))
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
        return 0;
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
        try {
            return Settings.Global.getInt(
                            this.mContext.getContentResolver(), SETTINGS_DB_DATA_CONFIG_ENABLED)
                    == -1;
        } catch (Settings.SettingNotFoundException unused) {
            SemLog.e(
                    TAG,
                    "It's before SetupWizard in Chinese device. Disable call log, contacts, files,"
                        + " gallery, synctasks and calendar");
            return false;
        }
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

    @Override // androidx.lifecycle.LifecycleEventObserver
    public void onStateChanged(LifecycleOwner lifecycleOwner, Lifecycle.Event event) {
        if (Lifecycle.Event.ON_RESUME.equals(event)) {
            this.mIsChecked = getThreadEnabled();
            return;
        }
        if (!Lifecycle.Event.ON_PAUSE.equals(event) || this.mIsChecked == getThreadEnabled()) {
            return;
        }
        SemLog.i(TAG, "Preference is updated. call SCS to update");
        ContentResolver contentResolver = this.mContext.getContentResolver();
        Uri uri = SEARCH_URI;
        contentResolver.call(
                uri, METHOD_UPDATE_DATA_CONFIG_STATUS, GALLERY_PACKAGE_NAME, (Bundle) null);
        this.mContext
                .getContentResolver()
                .call(uri, METHOD_UPDATE_DATA_CONFIG_STATUS, FILES_PACKAGE_NAME, (Bundle) null);
        this.mContext
                .getContentResolver()
                .call(uri, METHOD_UPDATE_DATA_CONFIG_STATUS, CALENDAR_PACKAGE_NAME, (Bundle) null);
        this.mContext
                .getContentResolver()
                .call(uri, METHOD_UPDATE_DATA_CONFIG_STATUS, CONTACTS_PACKAGE_NAME, (Bundle) null);
        this.mContext
                .getContentResolver()
                .call(uri, METHOD_UPDATE_DATA_CONFIG_STATUS, CALLLOG_PACKAGE_NAME, (Bundle) null);
        this.mContext
                .getContentResolver()
                .call(uri, METHOD_UPDATE_DATA_CONFIG_STATUS, SYNCTASKS_PACKAGE_NAME, (Bundle) null);
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return super.runDefaultAction();
    }

    @Override // com.android.settings.core.TogglePreferenceController
    public boolean setChecked(boolean z) {
        int i = z ? -1 : this.mDisabledFilesMediaCallendar;
        Settings.Global.putInt(
                this.mContext.getContentResolver(), SETTINGS_DB_DATA_CONFIG_ENABLED, i);
        SemLog.i(TAG, "Updated SCS config to " + Integer.toBinaryString(i));
        return true;
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }

    public SamsungCoreServicesPreferenceController(Context context, String str) {
        super(context, str);
        this.mDisabledFilesMediaCallendar =
                turnOffKthBit(Preference.DEFAULT_ORDER, 1, 2, 3, 6, 7, 8);
    }
}
