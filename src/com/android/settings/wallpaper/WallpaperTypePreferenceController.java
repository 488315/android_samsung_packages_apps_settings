package com.android.settings.wallpaper;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.text.TextUtils;
import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;
import com.android.settings.core.BasePreferenceController;
import com.android.settingslib.core.lifecycle.LifecycleObserver;
import com.android.settingslib.core.lifecycle.events.OnStart;
import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class WallpaperTypePreferenceController extends BasePreferenceController implements LifecycleObserver, OnStart {
    private PreferenceScreen mScreen;

    public WallpaperTypePreferenceController(Context context, String str) {
        super(context, str);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$removeUselessExistingPreference$0(Preference preference, ResolveInfo resolveInfo) {
        return TextUtils.equals(preference.getKey(), resolveInfo.activityInfo.packageName);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r3v5, types: [androidx.preference.PreferenceGroup, androidx.preference.PreferenceScreen] */
    /* JADX WARN: Type inference failed for: r4v2, types: [java.lang.CharSequence, java.lang.String] */
    /* JADX WARN: Type inference failed for: r4v3, types: [java.lang.CharSequence] */
    /* JADX WARN: Type inference failed for: r4v4 */
    /* JADX WARN: Type inference failed for: r5v0, types: [androidx.preference.PreferenceGroup, androidx.preference.PreferenceScreen] */
    /* JADX WARN: Type inference failed for: r5v2, types: [androidx.preference.Preference] */
    /* JADX WARN: Type inference failed for: r5v4 */
    /* JADX WARN: Type inference failed for: r5v5 */
    private void populateWallpaperTypes() {
        Intent intent = new Intent("android.intent.action.SET_WALLPAPER");
        PackageManager packageManager = this.mContext.getPackageManager();
        List<ResolveInfo> queryIntentActivities = packageManager.queryIntentActivities(intent, 65536);
        removeUselessExistingPreference(queryIntentActivities);
        this.mScreen.mOrderingAsAdded = false;
        for (ResolveInfo resolveInfo : queryIntentActivities) {
            ?? r4 = resolveInfo.activityInfo.packageName;
            Preference findPreference = this.mScreen.findPreference(r4);
            ?? r5 = findPreference;
            if (findPreference == null) {
                r5 = new Preference(this.mScreen.getContext());
            }
            Intent addFlags = new Intent(intent).addFlags(33554432);
            addFlags.setComponent(new ComponentName((String) r4, resolveInfo.activityInfo.name));
            r5.setIntent(addFlags);
            r5.setKey(r4);
            CharSequence loadLabel = resolveInfo.loadLabel(packageManager);
            if (loadLabel != null) {
                r4 = loadLabel;
            }
            r5.setTitle(r4);
            r5.setIcon(resolveInfo.loadIcon(packageManager));
            this.mScreen.addPreference(r5);
        }
    }

    private void removeUselessExistingPreference(List<ResolveInfo> list) {
        int preferenceCount = this.mScreen.getPreferenceCount();
        if (preferenceCount <= 0) {
            return;
        }
        for (int i = preferenceCount - 1; i >= 0; i--) {
            final Preference preference = this.mScreen.getPreference(i);
            if (((List) list.stream().filter(new Predicate() { // from class: com.android.settings.wallpaper.WallpaperTypePreferenceController$$ExternalSyntheticLambda0
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    boolean lambda$removeUselessExistingPreference$0;
                    lambda$removeUselessExistingPreference$0 = WallpaperTypePreferenceController.lambda$removeUselessExistingPreference$0(Preference.this, (ResolveInfo) obj);
                    return lambda$removeUselessExistingPreference$0;
                }
            }).collect(Collectors.toList())).isEmpty()) {
                this.mScreen.removePreference(preference);
            }
        }
    }

    @Override // com.android.settings.core.BasePreferenceController, com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        this.mScreen = preferenceScreen;
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

    @Override // com.android.settings.core.BasePreferenceController, com.android.settingslib.core.AbstractPreferenceController
    public boolean handlePreferenceTreeClick(Preference preference) {
        if (preference.getIntent() == null) {
            return super.handlePreferenceTreeClick(preference);
        }
        this.mContext.startActivity(preference.getIntent());
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
    public /* bridge */ /* synthetic */ Controllable$ControllableType needUserInteraction(Object obj) {
        return Controllable$ControllableType.NO_INTERACTION;
    }

    @Override // com.android.settingslib.core.lifecycle.events.OnStart
    public void onStart() {
        populateWallpaperTypes();
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

    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {
    }
}
