package com.android.settings.display;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.text.TextUtils;

import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;

import com.android.settings.R;
import com.android.settings.core.BasePreferenceController;
import com.android.settings.core.PreferenceControllerMixin;
import com.android.settingslib.utils.ThreadUtils;

import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class CustomizableLockScreenQuickAffordancesPreferenceController
        extends BasePreferenceController implements PreferenceControllerMixin {
    public CustomizableLockScreenQuickAffordancesPreferenceController(Context context, String str) {
        super(context, str);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean lambda$displayPreference$0(Preference preference) {
        Uri uri = CustomizableLockScreenUtils.FLAGS_URI;
        Intent intent = new Intent("android.intent.action.SET_WALLPAPER");
        intent.putExtra("com.android.wallpaper.LAUNCH_SOURCE", "app_launched_settings");
        String string = this.mContext.getString(R.string.config_wallpaper_picker_package);
        if (!TextUtils.isEmpty(string)) {
            intent.setPackage(string);
        }
        intent.putExtra("destination", "quick_affordances");
        this.mContext.startActivity(intent);
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Code restructure failed: missing block: B:7:0x0018, code lost:

       if (r2 != null) goto L7;
    */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void lambda$refreshSummary$2(androidx.preference.Preference r9) {
        /*
            r8 = this;
            android.content.Context r8 = r8.mContext
            android.net.Uri r0 = com.android.settings.display.CustomizableLockScreenUtils.FLAGS_URI
            java.lang.String r0 = "CustomizableLockScreenUtils"
            r1 = 0
            android.content.ContentResolver r2 = r8.getContentResolver()     // Catch: java.lang.Exception -> L1f
            android.net.Uri r3 = com.android.settings.display.CustomizableLockScreenUtils.SELECTIONS_URI     // Catch: java.lang.Exception -> L1f
            android.database.Cursor r2 = r2.query(r3, r1, r1, r1)     // Catch: java.lang.Exception -> L1f
            if (r2 != 0) goto L24
            java.lang.String r8 = "Cursor was null!"
            android.util.Log.w(r0, r8)     // Catch: java.lang.Throwable -> L22
            if (r2 == 0) goto La2
        L1a:
            r2.close()     // Catch: java.lang.Exception -> L1f
            goto La2
        L1f:
            r8 = move-exception
            goto L9d
        L22:
            r8 = move-exception
            goto L92
        L24:
            java.lang.String r3 = "affordance_name"
            int r3 = r2.getColumnIndex(r3)     // Catch: java.lang.Throwable -> L22
            r4 = -1
            if (r3 != r4) goto L33
            java.lang.String r8 = "Cursor doesn't contain \"affordance_name\" column!"
            android.util.Log.w(r0, r8)     // Catch: java.lang.Throwable -> L22
            goto L1a
        L33:
            java.util.ArrayList r4 = new java.util.ArrayList     // Catch: java.lang.Throwable -> L22
            int r5 = r2.getCount()     // Catch: java.lang.Throwable -> L22
            r4.<init>(r5)     // Catch: java.lang.Throwable -> L22
        L3c:
            boolean r5 = r2.moveToNext()     // Catch: java.lang.Throwable -> L22
            if (r5 == 0) goto L50
            java.lang.String r5 = r2.getString(r3)     // Catch: java.lang.Throwable -> L22
            boolean r6 = android.text.TextUtils.isEmpty(r5)     // Catch: java.lang.Throwable -> L22
            if (r6 != 0) goto L3c
            r4.add(r5)     // Catch: java.lang.Throwable -> L22
            goto L3c
        L50:
            int r3 = r4.size()     // Catch: java.lang.Throwable -> L22
            r5 = 2
            int r3 = java.lang.Math.min(r5, r3)     // Catch: java.lang.Throwable -> L22
            java.util.ArrayList r5 = new java.util.ArrayList     // Catch: java.lang.Throwable -> L22
            r5.<init>(r3)     // Catch: java.lang.Throwable -> L22
            boolean r6 = r4.isEmpty()     // Catch: java.lang.Throwable -> L22
            if (r6 != 0) goto L6e
            r6 = 0
            java.lang.Object r6 = r4.get(r6)     // Catch: java.lang.Throwable -> L22
            java.lang.String r6 = (java.lang.String) r6     // Catch: java.lang.Throwable -> L22
            r5.add(r6)     // Catch: java.lang.Throwable -> L22
        L6e:
            int r6 = r4.size()     // Catch: java.lang.Throwable -> L22
            r7 = 1
            if (r6 <= r7) goto L7e
            java.lang.Object r4 = r4.get(r7)     // Catch: java.lang.Throwable -> L22
            java.lang.String r4 = (java.lang.String) r4     // Catch: java.lang.Throwable -> L22
            r5.add(r4)     // Catch: java.lang.Throwable -> L22
        L7e:
            android.content.res.Resources r8 = r8.getResources()     // Catch: java.lang.Throwable -> L22
            java.lang.Object[] r4 = r5.toArray()     // Catch: java.lang.Throwable -> L22
            r5 = 2131886112(0x7f120020, float:1.9406794E38)
            java.lang.String r8 = r8.getQuantityString(r5, r3, r4)     // Catch: java.lang.Throwable -> L22
            r2.close()     // Catch: java.lang.Exception -> L1f
            r1 = r8
            goto La2
        L92:
            if (r2 == 0) goto L9c
            r2.close()     // Catch: java.lang.Throwable -> L98
            goto L9c
        L98:
            r2 = move-exception
            r8.addSuppressed(r2)     // Catch: java.lang.Exception -> L1f
        L9c:
            throw r8     // Catch: java.lang.Exception -> L1f
        L9d:
            java.lang.String r2 = "Exception while querying quick affordance content provider"
            android.util.Log.e(r0, r2, r8)
        La2:
            com.android.settings.display.CustomizableLockScreenQuickAffordancesPreferenceController$$ExternalSyntheticLambda0 r8 = new com.android.settings.display.CustomizableLockScreenQuickAffordancesPreferenceController$$ExternalSyntheticLambda0
            r8.<init>(r9, r1)
            com.android.settingslib.utils.ThreadUtils.postOnMainThread(r8)
            return
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.settings.display.CustomizableLockScreenQuickAffordancesPreferenceController.lambda$refreshSummary$2(androidx.preference.Preference):void");
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        Preference findPreference = preferenceScreen.findPreference(getPreferenceKey());
        if (findPreference != null) {
            findPreference.setOnPreferenceClickListener(
                    new Preference
                            .OnPreferenceClickListener() { // from class:
                                                           // com.android.settings.display.CustomizableLockScreenQuickAffordancesPreferenceController$$ExternalSyntheticLambda2
                        @Override // androidx.preference.Preference.OnPreferenceClickListener
                        public final boolean onPreferenceClick(Preference preference) {
                            boolean lambda$displayPreference$0;
                            lambda$displayPreference$0 =
                                    CustomizableLockScreenQuickAffordancesPreferenceController.this
                                            .lambda$displayPreference$0(preference);
                            return lambda$displayPreference$0;
                        }
                    });
            refreshSummary(findPreference);
        }
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        return CustomizableLockScreenUtils.isFeatureEnabled(this.mContext) ? 0 : 3;
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

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public void refreshSummary(Preference preference) {
        ThreadUtils.postOnBackgroundThread(
                new CustomizableLockScreenQuickAffordancesPreferenceController$$ExternalSyntheticLambda0(
                        this, preference));
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
