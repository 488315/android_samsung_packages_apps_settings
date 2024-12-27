package com.samsung.android.settings.homepage;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.UserHandle;

import com.android.settings.R;
import com.android.settings.Utils;

import com.samsung.android.settings.PkgUtils;
import com.samsung.android.settings.Rune;
import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class TopLevelWallpaperPreferenceController extends TopLevelPreferenceController {
    public TopLevelWallpaperPreferenceController(Context context, String str) {
        super(context, str);
    }

    @Override // com.samsung.android.settings.homepage.TopLevelPreferenceController,
              // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        Context context = this.mContext;
        if (PkgUtils.hasPackage(
                context, context.getString(R.string.config_sec_toplevel_wallpaper_package))) {
            return ((!Rune.isSamsungDexMode(this.mContext)
                                    || Utils.isDesktopStandaloneMode(this.mContext)
                                    || Utils.isNewDexMode(this.mContext))
                            && !Utils.isMinimalBatteryUseEnabled(this.mContext))
                    ? 0
                    : 2;
        }
        return 2;
    }

    @Override // com.samsung.android.settings.homepage.TopLevelPreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ Class getBackgroundWorkerClass() {
        return null;
    }

    @Override // com.samsung.android.settings.homepage.TopLevelPreferenceController
    public /* bridge */ /* synthetic */ List getBackupKeys() {
        return super.getBackupKeys();
    }

    @Override // com.samsung.android.settings.homepage.TopLevelPreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ IntentFilter getIntentFilter() {
        return null;
    }

    @Override // com.samsung.android.settings.homepage.TopLevelPreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Intent getLaunchIntent() {
        return super.getLaunchIntent();
    }

    @Override // com.samsung.android.settings.homepage.TopLevelPreferenceController
    public int getSaLoggingId() {
        return 10500;
    }

    @Override // com.samsung.android.settings.homepage.TopLevelPreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ int getSliceHighlightMenuRes() {
        return 0;
    }

    @Override // com.samsung.android.settings.homepage.TopLevelPreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ String getStatusText() {
        return super.getStatusText();
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public CharSequence getSummary() {
        String string = this.mContext.getString(R.string.wallpaper_summary_wallpapers);
        String string2 = this.mContext.getString(R.string.wallpaper_summary_color_palette);
        ArrayList arrayList = new ArrayList();
        arrayList.add(string);
        if (UserHandle.myUserId() == 0) {
            arrayList.add(string2);
        }
        return Utils.buildSummaryString(
                Utils.getTopLevelSummarySeparator(this.mContext), arrayList, arrayList.size());
    }

    @Override // com.samsung.android.settings.homepage.TopLevelPreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlValue getValue() {
        return super.getValue();
    }

    @Override // com.samsung.android.settings.homepage.TopLevelPreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean hasAsyncUpdate() {
        return false;
    }

    @Override // com.samsung.android.settings.homepage.TopLevelPreferenceController
    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {
        super.ignoreUserInteraction();
    }

    @Override // com.samsung.android.settings.homepage.TopLevelPreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean isControllable() {
        return super.isControllable();
    }

    @Override // com.samsung.android.settings.homepage.TopLevelPreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean isPublicSlice() {
        return false;
    }

    @Override // com.samsung.android.settings.homepage.TopLevelPreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean isSliceable() {
        return false;
    }

    @Override // com.samsung.android.settings.homepage.TopLevelPreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Controllable$ControllableType needUserInteraction(
            Object obj) {
        return super.needUserInteraction(obj);
    }

    @Override // com.samsung.android.settings.homepage.TopLevelPreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return super.runDefaultAction();
    }

    @Override // com.samsung.android.settings.homepage.TopLevelPreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlResult setValue(ControlValue controlValue) {
        return super.setValue(controlValue);
    }

    @Override // com.samsung.android.settings.homepage.TopLevelPreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }
}
