package com.android.settings;

import android.accounts.AccountManager;
import android.app.ActivityManager;
import android.app.ProgressDialog;
import android.app.admin.DevicePolicyManager;
import android.app.admin.FactoryResetProtectionPolicy;
import android.content.ActivityNotFoundException;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Process;
import android.os.SemSystemProperties;
import android.os.ServiceManager;
import android.os.SystemProperties;
import android.os.UserHandle;
import android.provider.Settings;
import android.security.Flags;
import android.service.oemlock.OemLockManager;
import android.service.persistentdata.PersistentDataBlockManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDelegateImpl$$ExternalSyntheticOutline0;
import androidx.appcompat.widget.ActionBarContextView$$ExternalSyntheticOutline0;
import androidx.fragment.app.DialogFragment$$ExternalSyntheticOutline0;

import com.android.internal.widget.LockPatternUtils;
import com.android.settings.deviceinfo.StorageWizardFormatProgress;
import com.android.settings.enterprise.ActionDisabledByAdminDialogHelper;
import com.android.settingslib.RestrictedLockUtils;
import com.android.settingslib.RestrictedLockUtilsInternal;

import com.google.android.setupcompat.util.WizardManagerHelper;
import com.samsung.android.feature.SemCscFeature;
import com.samsung.android.knox.EnterpriseContainerCallback;
import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.knox.net.nap.NetworkAnalyticsConstants;
import com.samsung.android.knox.ucm.core.IUcmService;
import com.samsung.android.security.SemSdCardEncryption;
import com.samsung.android.settings.Rune;
import com.samsung.android.settings.account.AccountUtils;
import com.samsung.android.settings.general.MainClearModemReset;
import com.samsung.android.settings.general.ResetSettingsPreferenceFragment;
import com.samsung.android.settings.logging.LoggingHelper;
import com.sec.ims.configuration.DATA;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes.dex */
public class MainClearConfirm extends ResetSettingsPreferenceFragment {
    public static final String mSalesCode =
            SystemProperties.get("ro.csc.sales_code", "NONE").trim().toUpperCase();
    public boolean isSprFamily;
    View mContentView;
    boolean mEraseEsims;
    public boolean mEraseSdCard;
    public String mEraseSdCardId = null;
    public boolean mCanEraseExternalOnFuseSystem = false;
    public boolean mMinorAccountLogOut = false;
    public boolean mIsSamsungAccountConfirmed = false;
    public final AnonymousClass1 omadmReceiver =
            new BroadcastReceiver() { // from class: com.android.settings.MainClearConfirm.1
                @Override // android.content.BroadcastReceiver
                public final void onReceive(Context context, Intent intent) {
                    Log.i(
                            "MainClearConfirm",
                            "RTN Reset broadcast Receiver"
                                + " [com.samsung.hiddenmenu.OMADM_CLEAR_DATA_DONE] in settings"
                                + " received");
                    if (SemCscFeature.getInstance()
                            .getBoolean("CscFeature_Common_EnableSprintExtension")) {
                        MainClearConfirm mainClearConfirm = MainClearConfirm.this;
                        String str = MainClearConfirm.mSalesCode;
                        if (mainClearConfirm.checkSDCardEncryptionEnabled()) {
                            MainClearConfirm.this.decryptSDCardBeforeFactoryReset();
                        } else {
                            Log.d("MainClearConfirm", "Starting MainClearModemReset() for RTN");
                            MainClearConfirm.this.startMainClearModemReset();
                        }
                    }
                }
            };

    public final boolean checkSDCardEncryptionEnabled() {
        boolean z;
        SemSdCardEncryption semSdCardEncryption =
                new SemSdCardEncryption(getActivity().getApplicationContext());
        String volumeState = semSdCardEncryption.getVolumeState();
        String str = SystemProperties.get("sec.fle.encryption.status", ApnSettings.MVNO_NONE);
        if (semSdCardEncryption.isEncryptionSupported() && volumeState != null) {
            if (("checking".equals(volumeState) | "mounted".equals(volumeState))
                    && ("encrypted".equals(str)
                            || "encrypting".equals(str)
                            || "decrypting".equals(str))) {
                z = true;
                AirplaneModeEnabler$$ExternalSyntheticOutline0.m(
                        "checkSDCardEncryptionEnabled : ", "MainClearConfirm", z);
                return z;
            }
        }
        z = false;
        AirplaneModeEnabler$$ExternalSyntheticOutline0.m(
                "checkSDCardEncryptionEnabled : ", "MainClearConfirm", z);
        return z;
    }

    public final void decryptSDCardBeforeFactoryReset() {
        try {
            Intent intent = new Intent("com.sec.app.action.START_SDCARD_ENCRYPTION");
            intent.putExtra("fromWhere", "MainClearConfirm");
            Log.d("MainClearConfirm", "doMainClear() startActivity SDCard Decryption");
            startActivityForResult(intent, EnterpriseContainerCallback.CONTAINER_MOUNT_STATUS);
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final void doMainClear() {
        boolean z;
        LoggingHelper.insertEventLogging(67, 4665);
        if (ActivityManager.isUserAMonkey()) {
            return;
        }
        int enterprisePolicyEnabled =
                Utils.getEnterprisePolicyEnabled(
                        getActivity(),
                        "content://com.sec.knox.provider/RestrictionPolicy1",
                        "isFactoryResetAllowed");
        boolean z2 = false;
        if ((enterprisePolicyEnabled == -1 || enterprisePolicyEnabled == 1) != true) {
            Log.i("MainClearConfirm", "doMainClear blocked by MDM ");
            return;
        }
        sendAuditLog();
        if (AccountManager.get(getContext()).getAccountsByType("com.osp.app.signin").length > 0) {
            Log.d("MainClearConfirm", "isHaveSA() - true");
            if (!this.mIsSamsungAccountConfirmed) {
                Intent intent = new Intent();
                intent.setClassName("com.osp.app.signin", "com.osp.app.signin.UserValidateCheck");
                intent.putExtra("MODE", "REMOTE_CONTROLS");
                intent.putExtra("fromWhere", "MasterClearConfirm");
                intent.putExtra("with", "SignOut");
                try {
                    startActivityForResult(intent, 1002);
                    return;
                } catch (ActivityNotFoundException e) {
                    e.printStackTrace();
                    return;
                }
            }
        } else {
            Log.d("MainClearConfirm", "isHaveSA() - false");
        }
        if (!this.mMinorAccountLogOut) {
            Context context = getContext();
            if ("China".equalsIgnoreCase(SemSystemProperties.get("ro.csc.country_code"))) {
                z =
                        Settings.Secure.getInt(
                                        context.getContentResolver(), "minors_mode_enabled", 0)
                                == 1;
                AirplaneModeEnabler$$ExternalSyntheticOutline0.m(
                        "minor mode enabled: ", "SettingsLib/MinorModeUtils", z);
            } else {
                z = false;
            }
            if (z && !AccountUtils.isChildAccount(getPrefContext())) {
                try {
                    startActivityForResult(
                            new Intent("com.samsung.android.minormode.CONFIRM_PASSWORD"), 200);
                    return;
                } catch (ActivityNotFoundException e2) {
                    Log.d("MainClearConfirm", "start minor mode fail");
                    e2.printStackTrace();
                }
            }
        }
        if (this.mEraseSdCard && this.mCanEraseExternalOnFuseSystem) {
            Intent intent2 =
                    new Intent(getActivity(), (Class<?>) StorageWizardFormatProgress.class);
            intent2.putExtra("android.os.storage.extra.DISK_ID", this.mEraseSdCardId);
            intent2.putExtra("from_factory_reset", true);
            MainClearConfirm$$ExternalSyntheticOutline0.m(
                    new StringBuilder("mEraseSdCardId = "),
                    this.mEraseSdCardId,
                    "MainClearConfirm");
            startActivityForResult(intent2, 111);
            return;
        }
        if (this.mEraseEsims) {
            try {
                Intent intent3 = new Intent();
                intent3.setComponent(
                        new ComponentName(
                                "com.samsung.android.app.telephonyui",
                                "com.samsung.android.app.telephonyui.netsettings.ui.esim.ResetEsimActivity"));
                startActivityForResult(intent3, 112);
                return;
            } catch (ActivityNotFoundException e3) {
                e3.printStackTrace();
                return;
            }
        }
        Log.d("MainClearConfirm", "doMainClear() send_OMADM_CLEAR");
        Bundle arguments = getArguments();
        String str = null;
        PackageInfo packageInfo = null;
        if (arguments != null) {
            String string = arguments.getString("hiddenmenu");
            DialogFragment$$ExternalSyntheticOutline0.m(
                    "isHiddenMenu :", string, "MainClearConfirm");
            if (string == null || !string.equals("true")) {
                Log.d("MainClearConfirm", "Intent not coming from Hiddenmenu");
            } else if (SemCscFeature.getInstance()
                    .getBoolean("CscFeature_Common_EnableSprintExtension")) {
                Log.i("MainClearConfirm", "OMADM Clear Data inside send_OMADM_CLEAR");
                Intent intent4 = new Intent();
                try {
                    boolean hasPackage =
                            Utils.hasPackage(
                                    getActivity().getApplicationContext(), "com.sec.omadmspr");
                    packageInfo =
                            getActivity()
                                    .getApplicationContext()
                                    .getPackageManager()
                                    .getPackageInfo("com.sec.omadmspr", 0);
                    z2 = hasPackage;
                } catch (PackageManager.NameNotFoundException | NullPointerException unused) {
                }
                if (!z2 || packageInfo == null) {
                    intent4.setPackage("com.samsung.sdm");
                } else {
                    intent4.setPackage("com.sec.omadmspr");
                }
                intent4.setAction("com.samsung.syncservice.OMADM_CLEAR_DATA");
                intent4.putExtra("type", "RTN");
                intent4.addFlags(268435456);
                getActivity().sendBroadcast(intent4);
            }
            str = string;
        }
        if (str == null || !str.equals("true")) {
            if (checkSDCardEncryptionEnabled()) {
                decryptSDCardBeforeFactoryReset();
            } else {
                Log.d("MainClearConfirm", "doMainClear() startService MainClearModemReset");
                startMainClearModemReset();
            }
        }
    }

    public final void doSecMainClear() {
        String str = Rune.COMMON_CONFIG_PACKAGE_NAME_SMART_MANAGER;
        if ("VZW".equals(Utils.getSalesCode())) {
            if (SemCscFeature.getInstance()
                    .getBoolean("CscFeature_Common_SupportHuxDeviceQualityStatistics")) {
                Intent intent = new Intent("com.sec.android.statistics.VZW_QUALITY_STATISTICS");
                intent.putExtra("event_type", "Q024");
                getActivity().sendBroadcast(intent);
            }
            String format = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").format(new Date());
            FileWriter fileWriter = null;
            try {
                try {
                    try {
                        File file = new File("/efs/sec_efs/LastResetDate.txt");
                        if (!file.exists()) {
                            file.createNewFile();
                            file.setReadable(true, false);
                        }
                        FileWriter fileWriter2 = new FileWriter(file);
                        try {
                            fileWriter2.write(format);
                            fileWriter2.close();
                        } catch (Exception e) {
                            fileWriter = fileWriter2;
                            e = e;
                            e.printStackTrace();
                            if (fileWriter != null) {
                                fileWriter.close();
                            }
                            doMainClear();
                        } catch (Throwable th) {
                            th = th;
                            fileWriter = fileWriter2;
                            if (fileWriter != null) {
                                try {
                                    fileWriter.close();
                                } catch (IOException e2) {
                                    e2.printStackTrace();
                                }
                            }
                            throw th;
                        }
                    } catch (Exception e3) {
                        e = e3;
                    }
                } catch (IOException e4) {
                    e4.printStackTrace();
                }
            } catch (Throwable th2) {
                th = th2;
            }
        }
        doMainClear();
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 67;
    }

    @Override // com.samsung.android.settings.general.ResetSettingsPreferenceFragment
    public final String getResetButtonTitle() {
        return getString(R.string.main_clear_final_button_text);
    }

    public boolean isDeviceStillBeingProvisioned() {
        return !WizardManagerHelper.isDeviceProvisioned(getActivity());
    }

    public boolean isOemUnlockedAllowed() {
        return ((OemLockManager) getActivity().getSystemService("oem_lock")).isOemUnlockAllowed();
    }

    @Override // androidx.fragment.app.Fragment
    public final void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        MainClearConfirm$$ExternalSyntheticOutline0.m(
                "onActivityResult : requestCode : ", " resultCode : ", i, i2, "MainClearConfirm");
        if (i == 1002) {
            if (i2 == -1) {
                this.mIsSamsungAccountConfirmed = true;
                doMainClear();
                return;
            }
            return;
        }
        if (i == 111) {
            if (i2 == -1) {
                Log.d("MainClearConfirm", "Success to clear SD card.");
                this.mEraseSdCard = false;
                doMainClear();
                return;
            }
            return;
        }
        if (i == 1010) {
            if (i2 == -1) {
                Log.d("MainClearConfirm", "Success to Decrypt the SD Card.");
                doMainClear();
                return;
            }
            return;
        }
        if (i == 112) {
            if (i2 != -1) {
                Log.d("MainClearConfirm", "eSIM profile deletion has been canceled.");
                getActivity().finish();
                return;
            } else {
                Log.d("MainClearConfirm", "Removed eSIM information");
                this.mEraseEsims = false;
                doMainClear();
                return;
            }
        }
        if (i == 101) {
            MainClearConfirm$$ExternalSyntheticOutline0.m(
                    i2, "onActivityResult: ", "MainClearConfirm");
            if (i2 != 1003) {
                doSecMainClear();
                return;
            }
            return;
        }
        if (i == 200) {
            if (i2 != -1) {
                Log.d("MainClearConfirm", "minor mode verification fail");
                return;
            }
            Log.d("MainClearConfirm", "minor mode verification success");
            this.mMinorAccountLogOut = true;
            doMainClear();
        }
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        Bundle arguments = getArguments();
        this.isSprFamily = "SPR|BST|XAS|VMU".contains(mSalesCode);
        if (arguments != null) {
            this.mCanEraseExternalOnFuseSystem = arguments.getBoolean("can_erase_sd_on_fuse");
            this.mEraseSdCard = arguments.getBoolean("erase_sd");
            this.mEraseEsims = arguments.getBoolean("erase_esim");
            this.mEraseSdCardId = arguments.getString("erase_sd_id");
            MainClearConfirm$$ExternalSyntheticOutline0.m(
                    MainClearConfirm$$ExternalSyntheticOutline0.m(
                            MainClearConfirm$$ExternalSyntheticOutline0.m(
                                    new StringBuilder("mCanEraseExternalOnFuseSystem= "),
                                    this.mCanEraseExternalOnFuseSystem,
                                    "MainClearConfirm",
                                    "mEraseSdCard= "),
                            this.mEraseSdCard,
                            "MainClearConfirm",
                            "mEraseSdCardId= "),
                    this.mEraseSdCardId,
                    "MainClearConfirm");
        }
        new LockPatternUtils(getActivity());
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final View onCreateView(
            LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        super.onCreateView(layoutInflater, viewGroup, bundle);
        RestrictedLockUtils.EnforcedAdmin checkIfRestrictionEnforced =
                RestrictedLockUtilsInternal.checkIfRestrictionEnforced(
                        getActivity(), UserHandle.myUserId(), "no_factory_reset");
        if (RestrictedLockUtilsInternal.hasBaseUserRestriction(
                getActivity(), UserHandle.myUserId(), "no_factory_reset")) {
            return layoutInflater.inflate(R.layout.main_clear_disallowed_screen, (ViewGroup) null);
        }
        if (checkIfRestrictionEnforced != null) {
            AlertDialog.Builder prepareDialogBuilder =
                    new ActionDisabledByAdminDialogHelper(getActivity(), null)
                            .prepareDialogBuilder("no_factory_reset", checkIfRestrictionEnforced);
            prepareDialogBuilder.P.mOnDismissListener =
                    new DialogInterface
                            .OnDismissListener() { // from class:
                                                   // com.android.settings.MainClearConfirm$$ExternalSyntheticLambda4
                        @Override // android.content.DialogInterface.OnDismissListener
                        public final void onDismiss(DialogInterface dialogInterface) {
                            MainClearConfirm mainClearConfirm = MainClearConfirm.this;
                            String str = MainClearConfirm.mSalesCode;
                            mainClearConfirm.getActivity().finish();
                        }
                    };
            prepareDialogBuilder.show();
            return new View(getActivity());
        }
        this.mContentView =
                layoutInflater.inflate(R.layout.sec_main_clear_confirm, (ViewGroup) null);
        getActivity();
        if (this.mEraseSdCard && this.mCanEraseExternalOnFuseSystem) {
            this.mContentView.findViewById(R.id.main_clear_smartswitch_desc_view).setVisibility(8);
        } else if (Rune.supportRelativeLink()
                && "1"
                        .equals(
                                SystemProperties.get(
                                        "storage.support.sdcard",
                                        DATA.DM_FIELD_INDEX.PCSCF_DOMAIN))) {
            this.mContentView.findViewById(R.id.main_clear_smartswitch_desc_view).setVisibility(0);
        } else {
            this.mContentView.findViewById(R.id.main_clear_smartswitch_desc_view).setVisibility(8);
        }
        CharSequence title = getActivity().getTitle();
        TextView textView = (TextView) this.mContentView.findViewById(R.id.main_clear_confirm);
        if (textView != null) {
            getActivity()
                    .setTitle(
                            Utils.createAccessibleSequence(
                                    title + "," + textView.getText(), title));
        }
        return this.mContentView;
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onPause() {
        super.onPause();
        if (this.isSprFamily) {
            getActivity().getApplicationContext().unregisterReceiver(this.omadmReceiver);
            Log.d("MainClearConfirm", "OMADM receiver unregistered");
        }
    }

    @Override // com.samsung.android.settings.general.ResetSettingsPreferenceFragment
    public final void onResetButtonClick() {
        StringBuilder sb = Utils.sBuilder;
        if (ActivityManager.isUserAMonkey()
                || SystemProperties.get("ro.frp.pst").equals(ApnSettings.MVNO_NONE)) {
            return;
        }
        int enterprisePolicyEnabled =
                Utils.getEnterprisePolicyEnabled(
                        getActivity(),
                        "content://com.sec.knox.provider/RestrictionPolicy",
                        "isPowerOffAllowed",
                        new String[] {"true"});
        final boolean z = true;
        if (enterprisePolicyEnabled != -1 && enterprisePolicyEnabled != 1) {
            Log.i("MainClearConfirm", "doSecMainClear blocked by MDM ");
            return;
        }
        sendAuditLog();
        try {
            IUcmService asInterface =
                    IUcmService.Stub.asInterface(
                            ServiceManager.getService("com.samsung.ucs.ucsservice"));
            if (asInterface != null) {
                asInterface.notifyChangeToPlugin(null, 101, null);
            }
        } catch (Exception unused) {
        }
        final PersistentDataBlockManager persistentDataBlockManager =
                (PersistentDataBlockManager)
                        getActivity().getSystemService("persistent_data_block");
        try {
            getPackageManager().getPackageInfo("com.samsung.android.dkey", 0);
        } catch (PackageManager.NameNotFoundException e) {
            Log.e("MainClearConfirm", "Digital key package not installed", e);
            z = false;
        }
        if (shouldWipePersistentDataBlock(persistentDataBlockManager)) {
            new AsyncTask() { // from class: com.android.settings.MainClearConfirm.2
                public int mOldOrientation;
                public ProgressDialog mProgressDialog;

                @Override // android.os.AsyncTask
                public final Object doInBackground(Object[] objArr) {
                    persistentDataBlockManager.wipe();
                    return null;
                }

                @Override // android.os.AsyncTask
                public final void onPostExecute(Object obj) {
                    ActionBarContextView$$ExternalSyntheticOutline0.m(
                            new StringBuilder("onPostExecute: is digitalKey present "),
                            z,
                            "MainClearConfirm");
                    if (!z) {
                        this.mProgressDialog.dismiss();
                    }
                    if (MainClearConfirm.this.getActivity() != null) {
                        MainClearConfirm.this
                                .getActivity()
                                .setRequestedOrientation(this.mOldOrientation);
                        if (z) {
                            MainClearConfirm.this.resetDigitalKey();
                        } else {
                            MainClearConfirm.this.doSecMainClear();
                        }
                    }
                }

                @Override // android.os.AsyncTask
                public final void onPreExecute() {
                    ActionBarContextView$$ExternalSyntheticOutline0.m(
                            new StringBuilder("onPreExecute: is digitalKey present "),
                            z,
                            "MainClearConfirm");
                    if (!z) {
                        MainClearConfirm mainClearConfirm = MainClearConfirm.this;
                        String str = MainClearConfirm.mSalesCode;
                        mainClearConfirm.getClass();
                        ProgressDialog progressDialog =
                                new ProgressDialog(mainClearConfirm.getActivity());
                        progressDialog.setIndeterminate(true);
                        progressDialog.setCancelable(false);
                        progressDialog.setTitle(
                                mainClearConfirm
                                        .getActivity()
                                        .getString(R.string.main_clear_progress_title));
                        progressDialog.setMessage(
                                mainClearConfirm
                                        .getActivity()
                                        .getString(R.string.main_clear_progress_text));
                        this.mProgressDialog = progressDialog;
                        progressDialog.show();
                    }
                    this.mOldOrientation =
                            MainClearConfirm.this.getActivity().getRequestedOrientation();
                    MainClearConfirm.this.getActivity().setRequestedOrientation(14);
                }
            }.execute(new Void[0]);
            return;
        }
        AirplaneModeEnabler$$ExternalSyntheticOutline0.m(
                "onResetButtonClick: is digitalKey present ", "MainClearConfirm", z);
        if (z) {
            resetDigitalKey();
        } else {
            doSecMainClear();
        }
    }

    @Override // com.samsung.android.settings.general.ResetSettingsPreferenceFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onResume() {
        super.onResume();
        if (this.isSprFamily) {
            getActivity()
                    .getApplicationContext()
                    .registerReceiver(
                            this.omadmReceiver,
                            AppCompatDelegateImpl$$ExternalSyntheticOutline0.m(
                                    "com.samsung.hiddenmenu.OMADM_CLEAR_DATA_DONE"),
                            2);
            Log.d("MainClearConfirm", "OMADM receiver registered");
        }
    }

    public final void resetDigitalKey() {
        PackageManager packageManager = getPackageManager();
        Intent intent = new Intent();
        intent.setAction("com.samsung.android.digitalkey.REMOVE_KEYS");
        if (!packageManager.queryIntentActivities(intent, 0).isEmpty()) {
            startActivityForResult(intent, 101);
        } else {
            Log.d("MainClearConfirm", "Digital key activity not found");
            doSecMainClear();
        }
    }

    public final void sendAuditLog() {
        Uri parse = Uri.parse("content://com.sec.knox.provider/AuditLog");
        ContentValues contentValues = new ContentValues();
        contentValues.put("severity", (Integer) 5);
        contentValues.put("group", (Integer) 2);
        contentValues.put("outcome", Boolean.TRUE);
        contentValues.put(
                NetworkAnalyticsConstants.DataPoints.UID, Integer.valueOf(Process.myPid()));
        contentValues.put("component", "FactoryReset");
        contentValues.put("message", "User Interaction: factory reset");
        getActivity().getContentResolver().insert(parse, contentValues);
    }

    public void setSubtitle() {
        if (this.mEraseEsims) {
            ((TextView) this.mContentView.findViewById(R.id.sud_layout_description))
                    .setText(R.string.main_clear_final_desc_esim);
        }
    }

    public boolean shouldWipePersistentDataBlock(
            PersistentDataBlockManager persistentDataBlockManager) {
        if (persistentDataBlockManager == null || isDeviceStillBeingProvisioned()) {
            return false;
        }
        if (!Flags.frpEnforcement() && isOemUnlockedAllowed()) {
            return false;
        }
        DevicePolicyManager devicePolicyManager =
                (DevicePolicyManager) getActivity().getSystemService("device_policy");
        if (!devicePolicyManager.isFactoryResetProtectionPolicySupported()) {
            return false;
        }
        FactoryResetProtectionPolicy factoryResetProtectionPolicy =
                devicePolicyManager.getFactoryResetProtectionPolicy(null);
        return (devicePolicyManager.isOrganizationOwnedDeviceWithManagedProfile()
                        && factoryResetProtectionPolicy != null
                        && factoryResetProtectionPolicy.isNotEmpty())
                ? false
                : true;
    }

    public final void startMainClearModemReset() {
        Intent intent =
                new Intent(
                        getActivity().getApplicationContext(),
                        (Class<?>) MainClearModemReset.class);
        intent.putExtra("FACTORY", true);
        intent.putExtra("WIPE_EXTERNAL_STORAGE", this.mEraseSdCard);
        intent.putExtra("WIPE_ESIMS", this.mEraseEsims);
        Log.d("MainClearConfirm", "startMainClearModemReset()");
        Bundle arguments = getArguments();
        if (arguments != null) {
            String string = arguments.getString("hiddenmenu");
            DialogFragment$$ExternalSyntheticOutline0.m(
                    "startMainClearModemReset(), isHiddenMenu :", string, "MainClearConfirm");
            intent.putExtra("hiddenmenu", string);
        } else {
            Log.d(
                    "MainClearConfirm",
                    "Intent not coming from RTN_RESET->MainClear in MainClearConfirm");
        }
        getActivity().startService(intent);
    }
}
