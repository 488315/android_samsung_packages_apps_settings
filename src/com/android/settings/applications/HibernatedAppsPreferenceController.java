package com.android.settings.applications;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.icu.text.MessageFormat;
import android.permission.PermissionControllerManager;
import android.provider.DeviceConfig;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;
import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;

import com.android.settings.R;
import com.android.settings.core.BasePreferenceController;

import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.Executor;
import java.util.function.IntConsumer;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class HibernatedAppsPreferenceController extends BasePreferenceController
        implements LifecycleObserver {
    private static final String TAG = "HibernatedAppsPrefController";
    private boolean mLoadedUnusedCount;
    private boolean mLoadingUnusedApps;
    private final Executor mMainExecutor;
    private PreferenceScreen mScreen;
    private int mUnusedCount;

    public HibernatedAppsPreferenceController(Context context, String str) {
        this(context, str, context.getMainExecutor());
    }

    private static boolean isHibernationEnabled() {
        return DeviceConfig.getBoolean("app_hibernation", "app_hibernation_enabled", true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$updatePreference$0(int i) {
        this.mUnusedCount = i;
        this.mLoadingUnusedApps = false;
        this.mLoadedUnusedCount = true;
        refreshSummary(this.mScreen.findPreference(this.mPreferenceKey));
    }

    private void updatePreference() {
        if (this.mScreen == null || this.mLoadingUnusedApps) {
            return;
        }
        ((PermissionControllerManager)
                        this.mContext.getSystemService(PermissionControllerManager.class))
                .getUnusedAppCount(
                        this.mMainExecutor,
                        new IntConsumer() { // from class:
                                            // com.android.settings.applications.HibernatedAppsPreferenceController$$ExternalSyntheticLambda0
                            @Override // java.util.function.IntConsumer
                            public final void accept(int i) {
                                HibernatedAppsPreferenceController.this.lambda$updatePreference$0(
                                        i);
                            }
                        });
        this.mLoadingUnusedApps = true;
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        this.mScreen = preferenceScreen;
        Preference findPreference = preferenceScreen.findPreference(getPreferenceKey());
        if (findPreference != null) {
            findPreference.setIntent(
                    new Intent("android.intent.action.MANAGE_UNUSED_APPS")
                            .setPackage(
                                    this.mContext
                                            .getPackageManager()
                                            .getPermissionControllerPackageName()));
        }
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        return isHibernationEnabled() ? 0 : 2;
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
        MessageFormat messageFormat =
                new MessageFormat(
                        this.mContext.getResources().getString(R.string.unused_apps_summary),
                        Locale.getDefault());
        HashMap hashMap = new HashMap();
        hashMap.put("count", Integer.valueOf(this.mUnusedCount));
        return this.mLoadedUnusedCount
                ? messageFormat.format(hashMap)
                : this.mContext.getResources().getString(R.string.summary_placeholder);
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

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    public void onResume() {
        updatePreference();
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

    public HibernatedAppsPreferenceController(Context context, String str, Executor executor) {
        super(context, str);
        this.mUnusedCount = 0;
        this.mMainExecutor = executor;
    }

    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {}
}
