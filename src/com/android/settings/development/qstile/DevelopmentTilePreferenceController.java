package com.android.settings.development.qstile;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.pm.ServiceInfo;
import android.os.Bundle;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.SystemProperties;
import android.util.Log;

import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;
import androidx.preference.SwitchPreferenceCompat;

import com.android.internal.statusbar.IStatusBarService;
import com.android.settings.core.BasePreferenceController;

import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class DevelopmentTilePreferenceController extends BasePreferenceController {
    private static final String TAG = "DevTilePrefController";
    private static final List<String> mDenyListQstile =
            Arrays.asList(
                    "com.samsung.android.settings.bluetooth.BluetoothCastTile",
                    "com.samsung.android.settings.qstile.NightThemeTiles");
    private final String META_DATA_DEXMODE_QUICK_PANEL;
    private final OnChangeHandler mOnChangeHandler;
    private final PackageManager mPackageManager;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public static class OnChangeHandler implements Preference.OnPreferenceChangeListener {
        public final Context mContext;
        public final PackageManager mPackageManager;
        public final IStatusBarService mStatusBarService =
                IStatusBarService.Stub.asInterface(ServiceManager.checkService("statusbar"));

        public OnChangeHandler(Context context) {
            this.mContext = context;
            this.mPackageManager = context.getPackageManager();
        }

        @Override // androidx.preference.Preference.OnPreferenceChangeListener
        public final boolean onPreferenceChange(Preference preference, Object obj) {
            boolean booleanValue = ((Boolean) obj).booleanValue();
            ComponentName componentName =
                    new ComponentName(this.mContext.getPackageName(), preference.getKey());
            this.mPackageManager.setComponentEnabledSetting(componentName, booleanValue ? 1 : 2, 1);
            try {
                IStatusBarService iStatusBarService = this.mStatusBarService;
                if (iStatusBarService != null) {
                    if (booleanValue) {
                        iStatusBarService.addTile(componentName);
                    } else {
                        iStatusBarService.remTile(componentName);
                    }
                }
            } catch (RemoteException e) {
                Log.e(
                        DevelopmentTilePreferenceController.TAG,
                        "Failed to modify QS tile for component " + componentName.toString(),
                        e);
            }
            return true;
        }
    }

    public DevelopmentTilePreferenceController(Context context, String str) {
        super(context, str);
        this.META_DATA_DEXMODE_QUICK_PANEL =
                "android.service.quicksettings.SEM_DEFAULT_TILE_DEXMODE_ONLY";
        this.mOnChangeHandler = new OnChangeHandler(context);
        this.mPackageManager = context.getPackageManager();
    }

    public static List<ServiceInfo> getTileServiceList(Context context) {
        List<ResolveInfo> queryIntentServices =
                context.getPackageManager()
                        .queryIntentServices(
                                new Intent("android.service.quicksettings.action.QS_TILE")
                                        .setPackage(context.getPackageName()),
                                640);
        ArrayList arrayList = new ArrayList();
        Iterator<ResolveInfo> it = queryIntentServices.iterator();
        while (it.hasNext()) {
            arrayList.add(it.next().serviceInfo);
        }
        return arrayList;
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        String string;
        super.displayPreference(preferenceScreen);
        Context context = preferenceScreen.getContext();
        for (ServiceInfo serviceInfo : getTileServiceList(context)) {
            Bundle bundle = serviceInfo.metaData;
            if (bundle == null
                    || (string =
                                    bundle.getString(
                                            "com.android.settings.development.qstile.REQUIRES_SYSTEM_PROPERTY"))
                            == null
                    || SystemProperties.getBoolean(string, false)) {
                int componentEnabledSetting =
                        this.mPackageManager.getComponentEnabledSetting(
                                new ComponentName(serviceInfo.packageName, serviceInfo.name));
                boolean z = true;
                if (componentEnabledSetting != 1
                        && (componentEnabledSetting != 0 || !serviceInfo.enabled)) {
                    z = false;
                }
                if (!mDenyListQstile.contains(serviceInfo.name)) {
                    Bundle bundle2 = serviceInfo.metaData;
                    if (bundle2 == null
                            || !bundle2.getBoolean(
                                    "android.service.quicksettings.SEM_DEFAULT_TILE_DEXMODE_ONLY",
                                    false)) {
                        SwitchPreferenceCompat switchPreferenceCompat =
                                new SwitchPreferenceCompat(context);
                        switchPreferenceCompat.setTitle(
                                serviceInfo.loadLabel(this.mPackageManager));
                        switchPreferenceCompat.setIcon(serviceInfo.icon);
                        switchPreferenceCompat.setKey(serviceInfo.name);
                        switchPreferenceCompat.setChecked(z);
                        switchPreferenceCompat.setOnPreferenceChangeListener(this.mOnChangeHandler);
                        preferenceScreen.addPreference(switchPreferenceCompat);
                    }
                } else if (serviceInfo.name.equals(
                                "com.samsung.android.settings.qstile.NightThemeTiles")
                        && !z) {
                    Preference preference = new Preference(context);
                    preference.setKey(serviceInfo.name);
                    this.mOnChangeHandler.onPreferenceChange(preference, Boolean.TRUE);
                }
            }
        }
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        return 0;
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

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return false;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlResult setValue(ControlValue controlValue) {
        return null;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }

    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {}
}
