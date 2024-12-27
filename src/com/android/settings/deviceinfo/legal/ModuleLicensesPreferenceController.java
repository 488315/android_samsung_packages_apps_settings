package com.android.settings.deviceinfo.legal;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ModuleInfo;
import android.content.pm.PackageManager;

import androidx.preference.PreferenceGroup;
import androidx.preference.PreferenceScreen;

import com.android.internal.util.ArrayUtils;
import com.android.settings.core.BasePreferenceController;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Consumer;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class ModuleLicensesPreferenceController extends BasePreferenceController {

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class Predicate implements java.util.function.Predicate {
        public final Context mContext;

        public Predicate(Context context) {
            this.mContext = context;
        }

        @Override // java.util.function.Predicate
        public final boolean test(Object obj) {
            ModuleInfo moduleInfo = (ModuleInfo) obj;
            try {
                PackageManager packageManager = this.mContext.getPackageManager();
                String packageName = moduleInfo.getPackageName();
                int i = ModuleLicenseProvider.$r8$clinit;
                return ArrayUtils.contains(
                        packageManager
                                .getResourcesForApplication(
                                        packageManager.getPackageInfo(packageName, 1073741824)
                                                .applicationInfo)
                                .getAssets()
                                .list(ApnSettings.MVNO_NONE),
                        "NOTICE.html.gz");
            } catch (PackageManager.NameNotFoundException | IOException unused) {
                return false;
            }
        }
    }

    public ModuleLicensesPreferenceController(Context context, String str) {
        super(context, str);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ String lambda$displayPreference$0(ModuleInfo moduleInfo) {
        return moduleInfo.getName().toString();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$displayPreference$1(
            PreferenceGroup preferenceGroup, ModuleInfo moduleInfo) {
        preferenceGroup.addPreference(
                new ModuleLicensePreference(preferenceGroup.getContext(), moduleInfo));
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        List<ModuleInfo> installedModules =
                this.mContext.getPackageManager().getInstalledModules(0);
        final PreferenceGroup preferenceGroup =
                (PreferenceGroup) preferenceScreen.findPreference(getPreferenceKey());
        installedModules.stream()
                .sorted(
                        Comparator.comparing(
                                new ModuleLicensesPreferenceController$$ExternalSyntheticLambda0()))
                .filter(new Predicate(this.mContext))
                .forEach(
                        new Consumer() { // from class:
                                         // com.android.settings.deviceinfo.legal.ModuleLicensesPreferenceController$$ExternalSyntheticLambda1
                            @Override // java.util.function.Consumer
                            public final void accept(Object obj) {
                                ModuleLicensesPreferenceController.lambda$displayPreference$1(
                                        PreferenceGroup.this, (ModuleInfo) obj);
                            }
                        });
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        return 1;
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
