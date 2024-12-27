package com.samsung.android.settings.connection.moreconnection;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.provider.Settings;
import android.util.Log;

import com.android.settings.R;
import com.android.settings.core.TogglePreferenceController;

import com.samsung.android.settings.cube.Controllable$ControllableType;
import com.samsung.android.settings.logging.LoggingHelper;

import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class OmcTogglePreferenceController extends TogglePreferenceController {
    private static final int AUTO_OMC_UPDATE_OFF = 0;
    private static final long AUTO_OMC_UPDATE_OFF_SA_VALUE = -1;
    private static final int AUTO_OMC_UPDATE_ON = 1;
    private static final long AUTO_OMC_UPDATE_ON_SA_VALUE = 1;
    private static final String CIDMANAGER_PACKAGE_NAME = "com.samsung.android.cidmanager";
    private static final String SDMCONFIG_PACKAGE_NAME = "com.samsung.android.sdm.config";
    private static final String TAG = "OmcTogglePreferenceController";
    private final boolean isCidManagerInstalled;
    private final boolean isSdmConfigInstalled;
    private final Context mContext;

    public OmcTogglePreferenceController(Context context, String str) {
        super(context, str);
        this.mContext = context;
        this.isCidManagerInstalled = isInstalled(CIDMANAGER_PACKAGE_NAME);
        this.isSdmConfigInstalled = isInstalled(SDMCONFIG_PACKAGE_NAME);
    }

    private boolean isInstalled(String str) {
        try {
            return this.mContext.getPackageManager().getPackageInfo(str, 0) != null;
        } catch (PackageManager.NameNotFoundException unused) {
            Log.e(TAG, "NameNotFoundException");
            return false;
        }
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        return (this.isCidManagerInstalled && this.isSdmConfigInstalled) ? 0 : 3;
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
        return R.string.menu_key_connections;
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
        return Settings.Global.getInt(this.mContext.getContentResolver(), "auto_omc_update", 1)
                == 1;
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
        int i;
        long j;
        if (z) {
            i = 1;
            j = AUTO_OMC_UPDATE_ON_SA_VALUE;
        } else {
            i = 0;
            j = -1;
        }
        LoggingHelper.insertEventLogging(72001, 72002, j, Integer.toString(i));
        return Settings.Global.putInt(this.mContext.getContentResolver(), "auto_omc_update", i);
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }
}
