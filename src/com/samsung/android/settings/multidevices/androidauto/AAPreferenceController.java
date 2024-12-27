package com.samsung.android.settings.multidevices.androidauto;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;

import androidx.preference.Preference;

import com.android.settings.core.BasePreferenceController;
import com.android.settings.dashboard.DashboardFeatureProviderImpl;
import com.android.settings.overlay.FeatureFactoryImpl;
import com.android.settingslib.drawer.DashboardCategory;
import com.android.settingslib.drawer.Tile;

import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;
import com.samsung.android.util.SemLog;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class AAPreferenceController extends BasePreferenceController {
    private static final String KEY_ANDROID_AUTO_SETTINGS = "android_auto_connections_settings";
    private static final String TAG = "AAPreferenceController";
    protected final Context mContext;

    public AAPreferenceController(Context context, String str) {
        super(context, str);
        this.mContext = context;
    }

    private Tile getAndroidAutoTile(Context context) {
        Log.d(TAG, "getAndroidAutoTile: ");
        FeatureFactoryImpl featureFactoryImpl = FeatureFactoryImpl._factory;
        if (featureFactoryImpl == null) {
            throw new UnsupportedOperationException("No feature factory configured");
        }
        DashboardFeatureProviderImpl dashboardFeatureProvider =
                featureFactoryImpl.getDashboardFeatureProvider();
        DashboardCategory tilesForCategory =
                dashboardFeatureProvider.getTilesForCategory(
                        "com.android.settings.category.ia.device");
        if (tilesForCategory == null) {
            Log.e(TAG, "dashboardCategory is Null...return NULL");
            return null;
        }
        Iterator it = ((ArrayList) tilesForCategory.getTiles()).iterator();
        while (it.hasNext()) {
            Tile tile = (Tile) it.next();
            if ("com.google.android.projection.gearhead".equalsIgnoreCase(tile.mComponentPackage)) {
                tile.mMetaData.putString("update", "update tile");
                Log.d(
                        TAG,
                        "getAndroidAutoTile() : Tile Title : " + ((Object) tile.getTitle(context)));
                Log.d(
                        TAG,
                        "getAndroidAutoTile() : Tile Key : "
                                + dashboardFeatureProvider.getDashboardKeyForTile(tile));
                Log.d(
                        TAG,
                        "getAndroidAutoTile() : Tile Summary : "
                                + ((Object) tile.getSummary(context)));
                Log.d(TAG, "getAndroidAutoTile() : Tile PackageName : " + tile.mComponentPackage);
                Log.d(TAG, "getAndroidAutoTile() : Tile ComponentName : " + tile.mComponentName);
                Log.d(TAG, "getAndroidAutoTile() : Tile Intent : " + tile.mIntent);
                Log.d(TAG, "getAndroidAutoTile() : Tile Description : " + tile.getDescription());
                Log.d(TAG, "getAndroidAutoTile() : Tile order : " + tile.getOrder());
                Log.d(TAG, "getAndroidAutoTile() : Tile MetaData : " + tile.mMetaData);
                return tile;
            }
        }
        return null;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        SemLog.d(TAG, "getAvailabilityStatus()");
        return !AndroidAutoUtils.isAndroidAutoInstalledEnabled(this.mContext) ? 2 : 0;
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
        if (!KEY_ANDROID_AUTO_SETTINGS.equals(preference.getKey())) {
            return false;
        }
        Tile androidAutoTile = getAndroidAutoTile(this.mContext);
        if (androidAutoTile == null) {
            SemLog.e(TAG, "onPreferenceTreeClick: AA Tile is null");
            return false;
        }
        try {
            this.mContext.startActivity(new Intent(androidAutoTile.mIntent));
            return true;
        } catch (ActivityNotFoundException e) {
            SemLog.e(TAG, "ActivityNotFoundException e = " + e.getMessage());
            return true;
        }
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
