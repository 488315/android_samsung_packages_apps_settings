package com.samsung.android.settings.general;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.util.Pair;

import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;
import androidx.preference.SecPreferenceScreen;

import com.android.settings.R;
import com.android.settings.core.BasePreferenceController;
import com.android.settingslib.core.AbstractPreferenceController;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.settings.Rune;
import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;
import com.sec.ims.IMSParameter;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class TemporaryBackupPreferenceController extends BasePreferenceController {
    private SecPreferenceScreen mPreference;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class TemporaryBackupSummaryTask extends AsyncTask {
        public TemporaryBackupSummaryTask() {}

        @Override // android.os.AsyncTask
        public final Object doInBackground(Object[] objArr) {
            String str;
            String str2;
            Bundle temporaryBackupBundle =
                    GeneralUtils.getTemporaryBackupBundle(
                            ((AbstractPreferenceController)
                                            TemporaryBackupPreferenceController.this)
                                    .mContext);
            if (temporaryBackupBundle != null) {
                if (temporaryBackupBundle.getBoolean("support", false)) {
                    str = temporaryBackupBundle.getString(IMSParameter.CALL.STATUS);
                    str2 = temporaryBackupBundle.getString("intro_description");
                    return Pair.create(str, str2);
                }
                Log.i("GeneralUtils", "isSupportTemporaryBackup = false");
            }
            str = ApnSettings.MVNO_NONE;
            str2 = ApnSettings.MVNO_NONE;
            return Pair.create(str, str2);
        }

        @Override // android.os.AsyncTask
        public final void onPostExecute(Object obj) {
            Pair pair = (Pair) obj;
            super.onPostExecute(pair);
            TemporaryBackupPreferenceController.this.updateSummary(
                    "BACKUP_RUNNING".equals(pair.first)
                            ? ((AbstractPreferenceController)
                                            TemporaryBackupPreferenceController.this)
                                    .mContext.getString(
                                            R.string.temporary_cloud_backup_subtext_backup_running)
                            : "BACKUP_COMPLETED".equals(pair.first)
                                    ? ((AbstractPreferenceController)
                                                    TemporaryBackupPreferenceController.this)
                                            .mContext.getString(
                                                    R.string
                                                            .temporary_cloud_backup_subtext_backup_completed)
                                    : "BACKUP_NON_FINISHED".equals(pair.first)
                                            ? ((AbstractPreferenceController)
                                                            TemporaryBackupPreferenceController
                                                                    .this)
                                                    .mContext.getString(
                                                            R.string
                                                                    .temporary_cloud_backup_subtext_backup_non_finished)
                                            : (String) pair.second);
        }
    }

    public TemporaryBackupPreferenceController(Context context, String str) {
        super(context, str);
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        this.mPreference =
                (SecPreferenceScreen) preferenceScreen.findPreference(getPreferenceKey());
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        Context context = this.mContext;
        String str = Rune.COMMON_CONFIG_PACKAGE_NAME_SMART_MANAGER;
        return Settings.System.getInt(context.getContentResolver(), "settings_reset_ctb_support", 0)
                        == 1
                ? 0
                : 3;
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
        if (!getPreferenceKey().equals(preference.getKey())) {
            return super.handlePreferenceTreeClick(preference);
        }
        try {
            this.mContext.startActivity(GeneralUtils.getTemporaryBackupIntent(this.mContext));
            return true;
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
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

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public void updateState(Preference preference) {
        super.updateState(preference);
        new TemporaryBackupSummaryTask().execute(new Void[0]);
    }

    public void updateSummary(String str) {
        SecPreferenceScreen secPreferenceScreen = this.mPreference;
        if (secPreferenceScreen != null) {
            secPreferenceScreen.setSummary(str);
        }
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }

    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {}
}
