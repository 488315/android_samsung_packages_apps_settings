package com.android.settings.development;

import android.app.ActivityManager;
import android.content.Context;
import android.content.DialogInterface;
import android.os.PowerManager;
import android.os.RemoteException;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;

import androidx.appcompat.app.AlertDialog;
import androidx.preference.ListPreference;
import androidx.preference.Preference;

import com.android.settings.R;
import com.android.settings.core.PreferenceControllerMixin;
import com.android.settingslib.development.DeveloperOptionsPreferenceController;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class CachedAppsFreezerPreferenceController
        extends DeveloperOptionsPreferenceController
        implements Preference.OnPreferenceChangeListener, PreferenceControllerMixin {
    private static final String CACHED_APPS_FREEZER_KEY = "cached_apps_freezer";
    public final String[] mListSummaries;
    public final String[] mListValues;

    public static /* synthetic */ void $r8$lambda$LHOC4FYAdL09DfnxnMReYvcTs14(
            CachedAppsFreezerPreferenceController cachedAppsFreezerPreferenceController,
            Object obj) {
        Settings.Global.putString(
                cachedAppsFreezerPreferenceController.mContext.getContentResolver(),
                CACHED_APPS_FREEZER_KEY,
                obj.toString());
        cachedAppsFreezerPreferenceController.updateState(
                cachedAppsFreezerPreferenceController.mPreference);
        ((PowerManager)
                        cachedAppsFreezerPreferenceController.mContext.getSystemService(
                                PowerManager.class))
                .reboot(null);
    }

    public CachedAppsFreezerPreferenceController(Context context) {
        super(context);
        this.mListValues =
                context.getResources().getStringArray(R.array.cached_apps_freezer_values);
        this.mListSummaries =
                context.getResources().getStringArray(R.array.cached_apps_freezer_entries);
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final String getPreferenceKey() {
        return CACHED_APPS_FREEZER_KEY;
    }

    @Override // com.android.settingslib.development.DeveloperOptionsPreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public final boolean isAvailable() {
        try {
            return ActivityManager.getService().isAppFreezerSupported();
        } catch (RemoteException unused) {
            Log.w(
                    "PrefControllerMixin",
                    "Unable to obtain freezer support status from ActivityManager");
            return false;
        }
    }

    @Override // com.android.settingslib.development.DeveloperOptionsPreferenceController
    public final void onDeveloperOptionsDisabled() {
        super.onDeveloperOptionsDisabled();
        Settings.Global.putString(
                this.mContext.getContentResolver(),
                CACHED_APPS_FREEZER_KEY,
                this.mListValues[0].toString());
    }

    @Override // androidx.preference.Preference.OnPreferenceChangeListener
    public final boolean onPreferenceChange(Preference preference, final Object obj) {
        if (obj.equals(
                Settings.Global.getString(
                        this.mContext.getContentResolver(), CACHED_APPS_FREEZER_KEY))) {
            return true;
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(this.mContext);
        builder.setMessage(R.string.cached_apps_freezer_reboot_dialog_text);
        builder.setPositiveButton(
                android.R.string.ok,
                new DialogInterface
                        .OnClickListener() { // from class:
                                             // com.android.settings.development.CachedAppsFreezerPreferenceController$$ExternalSyntheticLambda0
                    @Override // android.content.DialogInterface.OnClickListener
                    public final void onClick(DialogInterface dialogInterface, int i) {
                        CachedAppsFreezerPreferenceController
                                .$r8$lambda$LHOC4FYAdL09DfnxnMReYvcTs14(
                                        CachedAppsFreezerPreferenceController.this, obj);
                    }
                });
        builder.setNegativeButton(
                android.R.string.cancel,
                new DialogInterface
                        .OnClickListener() { // from class:
                                             // com.android.settings.development.CachedAppsFreezerPreferenceController$$ExternalSyntheticLambda1
                    @Override // android.content.DialogInterface.OnClickListener
                    public final void onClick(DialogInterface dialogInterface, int i) {
                        CachedAppsFreezerPreferenceController
                                cachedAppsFreezerPreferenceController =
                                        CachedAppsFreezerPreferenceController.this;
                        cachedAppsFreezerPreferenceController.updateState(
                                cachedAppsFreezerPreferenceController.mPreference);
                    }
                });
        builder.create().show();
        return true;
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final void updateState(Preference preference) {
        String[] strArr;
        ListPreference listPreference = (ListPreference) preference;
        String string =
                Settings.Global.getString(
                        this.mContext.getContentResolver(), CACHED_APPS_FREEZER_KEY);
        int i = 0;
        int i2 = 0;
        while (true) {
            strArr = this.mListValues;
            if (i2 >= strArr.length) {
                break;
            }
            if (TextUtils.equals(string, strArr[i2])) {
                i = i2;
                break;
            }
            i2++;
        }
        listPreference.setValue(strArr[i]);
        listPreference.setSummary(this.mListSummaries[i]);
    }
}
