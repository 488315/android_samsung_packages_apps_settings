package com.android.settings.system;

import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.UserManager;

import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;

import com.android.settings.R;
import com.android.settings.Utils;
import com.android.settings.core.BasePreferenceController;
import com.android.settings.core.PreferenceControllerMixin;

import com.samsung.android.desktopmode.SemDesktopModeManager;
import com.samsung.android.knox.net.vpn.KnoxVpnPolicyConstants;
import com.samsung.android.settings.Rune;
import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;
import com.samsung.android.settings.general.GeneralUtils;
import com.sec.ims.IMSParameter;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class FactoryResetPreferenceController extends BasePreferenceController
        implements PreferenceControllerMixin {
    private static final String KEY_FACTORY_RESET = "factory_reset";
    private final UserManager mUm;

    public FactoryResetPreferenceController(Context context) {
        super(context, KEY_FACTORY_RESET);
        this.mUm = (UserManager) context.getSystemService("user");
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        Preference findPreference = preferenceScreen.findPreference(getPreferenceKey());
        boolean z = false;
        boolean z2 =
                Utils.getEnterprisePolicyEnabled(
                                this.mContext,
                                "content://com.sec.knox.provider/RestrictionPolicy1",
                                "isFactoryResetAllowed")
                        != 0;
        if (findPreference != null) {
            Context context = this.mContext;
            if (!Utils.isDesktopModeEnabled(context)) {
                SemDesktopModeManager semDesktopModeManager =
                        (SemDesktopModeManager) context.getSystemService("desktopmode");
                if (!(semDesktopModeManager != null
                                ? semDesktopModeManager.isDeviceConnected()
                                : false)
                        && z2) {
                    z = true;
                }
            }
            findPreference.setEnabled(z);
            if (Rune.isChinaModel()) {
                findPreference.setSummary(R.string.sec_master_clear_list_summary_chn);
            }
        }
        super.displayPreference(preferenceScreen);
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        return (this.mUm.isAdminUser() || Utils.isDemoUser(this.mContext)) ? 0 : 4;
    }

    public /* bridge */ /* synthetic */ Class getBackgroundWorkerClass() {
        return null;
    }

    public List getBackupKeys() {
        return new ArrayList();
    }

    public /* bridge */ /* synthetic */ IntentFilter getIntentFilter() {
        return null;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public Intent getLaunchIntent() {
        Intent intent = new Intent("android.intent.action.MAIN");
        intent.setClassName(
                KnoxVpnPolicyConstants.ANDROID_SETTINGS_PKG,
                "com.android.settings.Settings$FactoryResetActivity");
        intent.addFlags(268468224);
        return intent;
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public String getPreferenceKey() {
        return KEY_FACTORY_RESET;
    }

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
        Bundle temporaryBackupBundle;
        if (KEY_FACTORY_RESET.equals(preference.getKey())
                && (temporaryBackupBundle = GeneralUtils.getTemporaryBackupBundle(this.mContext))
                        != null) {
            String string = temporaryBackupBundle.getString(IMSParameter.CALL.STATUS);
            if ("RESTORE_RUNNING".equalsIgnoreCase(string)
                    || "BACKUP_RUNNING".equalsIgnoreCase(string)
                    || "BACKUP_NON_FINISHED".equalsIgnoreCase(string)) {
                Context context = this.mContext;
                new AlertDialog.Builder(context)
                        .setMessage(
                                context.getString(R.string.temporary_backup_warning_dialog_message))
                        .setCancelable(true)
                        .setPositiveButton(
                                R.string.go_to_samsung_cloud,
                                new DialogInterface
                                        .OnClickListener() { // from class:
                                                             // com.samsung.android.settings.general.GeneralUtils.2
                                    public final /* synthetic */ Context val$context;

                                    public AnonymousClass2(Context context2) {
                                        r1 = context2;
                                    }

                                    @Override // android.content.DialogInterface.OnClickListener
                                    public final void onClick(
                                            DialogInterface dialogInterface, int i) {
                                        try {
                                            r1.startActivity(
                                                    GeneralUtils.getTemporaryBackupIntent(r1));
                                        } catch (ActivityNotFoundException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                })
                        .setNegativeButton(R.string.cancel, new GeneralUtils.AnonymousClass1())
                        .show();
                return true;
            }
        }
        return super.handlePreferenceTreeClick(preference);
    }

    public /* bridge */ /* synthetic */ boolean hasAsyncUpdate() {
        return false;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public boolean isControllable() {
        return true;
    }

    public /* bridge */ /* synthetic */ boolean isPublicSlice() {
        return false;
    }

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

    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }

    public FactoryResetPreferenceController(Context context, String str) {
        super(context, str);
        this.mUm = (UserManager) context.getSystemService("user");
    }

    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {}
}
