package com.android.settings.development;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.ParcelFileDescriptor;
import android.os.PersistableBundle;
import android.os.PowerManager;
import android.os.SystemProperties;
import android.os.SystemUpdateManager;
import android.os.UpdateEngineStable;
import android.os.UpdateEngineStableCallback;
import android.provider.Settings;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManagerImpl;
import androidx.preference.Preference;
import androidx.preference.SwitchPreference;
import androidx.window.area.WindowAreaControllerImpl$RearDisplayPresentationSessionConsumer$$ExternalSyntheticOutline0;

import com.android.settings.R;
import com.android.settings.core.PreferenceControllerMixin;
import com.android.settingslib.core.AbstractPreferenceController;
import com.android.settingslib.development.DeveloperOptionsPreferenceController;

import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
import com.samsung.android.knox.ucm.core.UniversalCredentialUtil;
import com.sec.ims.IMSParameter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class Enable16kPagesPreferenceController extends DeveloperOptionsPreferenceController
        implements Preference.OnPreferenceChangeListener, PreferenceControllerMixin {
    public boolean mEnable16k;
    public final ListeningExecutorService mExecutorService;
    public final DevelopmentSettingsDashboardFragment mFragment;
    public AlertDialog mProgressDialog;

    public static void $r8$lambda$316NZEq35ADdErAn0hq0_g2UmHc(
            Enable16kPagesPreferenceController enable16kPagesPreferenceController) {
        SystemUpdateManager systemUpdateManager =
                (SystemUpdateManager)
                        enable16kPagesPreferenceController.mContext.getSystemService(
                                SystemUpdateManager.class);
        int i = systemUpdateManager.retrieveSystemUpdateInfo().getInt(IMSParameter.CALL.STATUS);
        if (i != 0 && i != 1) {
            throw new RuntimeException(
                    "System has pending update! Please restart the device to complete applying"
                        + " pending update. If you are seeing this after using 16KB developer"
                        + " options, please check configuration and OTA packages!");
        }
        PersistableBundle persistableBundle = new PersistableBundle();
        persistableBundle.putInt(IMSParameter.CALL.STATUS, 3);
        persistableBundle.putBoolean("is_security_update", false);
        persistableBundle.putString(
                UniversalCredentialUtil.AGENT_TITLE, "Android 16K Kernel Experimental Update");
        systemUpdateManager.updateSystemUpdateInfo(persistableBundle);
        try {
            File otaFile = enable16kPagesPreferenceController.getOtaFile();
            Log.i("Enable16kPages", "Update file path is " + otaFile.getAbsolutePath());
            enable16kPagesPreferenceController.applyUpdateFile(otaFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /* renamed from: -$$Nest$mdisplayToast, reason: not valid java name */
    public static void m823$$Nest$mdisplayToast(
            Enable16kPagesPreferenceController enable16kPagesPreferenceController, String str) {
        Toast.makeText(enable16kPagesPreferenceController.mContext, str, 1).show();
    }

    public Enable16kPagesPreferenceController(
            Context context,
            DevelopmentSettingsDashboardFragment developmentSettingsDashboardFragment) {
        super(context);
        this.mExecutorService =
                MoreExecutors.listeningDecorator(Executors.newSingleThreadExecutor());
        this.mFragment = developmentSettingsDashboardFragment;
        this.mEnable16k = Enable16kUtils.isUsing16kbPages();
    }

    public void applyPayload(File file, long j, long j2, List<String> list)
            throws FileNotFoundException {
        String[] strArr =
                (String[])
                        list.stream()
                                .toArray(
                                        new Enable16kPagesPreferenceController$$ExternalSyntheticLambda0());
        UpdateEngineStable updateEngineStable = new UpdateEngineStable();
        try {
            ParcelFileDescriptor open = ParcelFileDescriptor.open(file, 268435456);
            updateEngineStable.bind(
                    new OtaUpdateCallback(updateEngineStable),
                    new Handler(this.mContext.getMainLooper()));
            updateEngineStable.applyPayloadFd(open, j, j2, strArr);
        } finally {
            Log.e("Enable16kPages", "Failure while applying an update using update engine");
        }
    }

    public void applyUpdateFile(File file) throws IOException, FileNotFoundException {
        ArrayList arrayList = new ArrayList();
        ZipFile zipFile = new ZipFile(file);
        try {
            Enumeration<? extends ZipEntry> entries = zipFile.entries();
            boolean z = false;
            boolean z2 = false;
            long j = 0;
            long j2 = 0;
            long j3 = 0;
            while (entries.hasMoreElements()) {
                ZipEntry nextElement = entries.nextElement();
                String name = nextElement.getName();
                long j4 = j;
                j3 +=
                        name.length()
                                + 30
                                + (nextElement.getExtra() == null
                                        ? 0L
                                        : nextElement.getExtra().length);
                if (nextElement.isDirectory()) {
                    j = j4;
                } else {
                    long compressedSize = nextElement.getCompressedSize();
                    if (!"payload.bin".equals(name)) {
                        if ("payload_properties.txt".equals(name)) {
                            InputStream inputStream = zipFile.getInputStream(nextElement);
                            if (inputStream != null) {
                                BufferedReader bufferedReader =
                                        new BufferedReader(new InputStreamReader(inputStream));
                                while (true) {
                                    String readLine = bufferedReader.readLine();
                                    if (readLine == null) {
                                        break;
                                    } else {
                                        arrayList.add(readLine);
                                    }
                                }
                            }
                            arrayList.add("POWERWASH=1");
                            z2 = true;
                        }
                        j = j4;
                    } else {
                        if (nextElement.getMethod() != 0) {
                            throw new IOException("Unknown compression method.");
                        }
                        j2 = compressedSize;
                        z = true;
                        j = j3;
                    }
                    j3 += compressedSize;
                }
            }
            long j5 = j;
            zipFile.close();
            if (!z) {
                throw new FileNotFoundException(
                        "Failed to find payload in zip: " + file.getAbsolutePath());
            }
            if (!z2) {
                throw new FileNotFoundException(
                        "Failed to find payload properties in zip: " + file.getAbsolutePath());
            }
            if (j2 != 0) {
                applyPayload(file, j5, j2, arrayList);
            } else {
                throw new IOException("Found empty payload in zip: " + file.getAbsolutePath());
            }
        } finally {
        }
    }

    public final File getOtaFile() {
        String str = this.mEnable16k ? "/boot_otas/boot_ota_16k.zip" : "/boot_otas/boot_ota_4k.zip";
        File file = new File("/vendor".concat(str));
        if (file.exists()) {
            return file;
        }
        String concat = "/system".concat(str);
        File file2 = new File(concat);
        if (file2.exists()) {
            return file2;
        }
        throw new FileNotFoundException(
                AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m(
                        "File not found at path ", concat));
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final String getPreferenceKey() {
        return "enable_16k_pages";
    }

    @Override // com.android.settingslib.development.DeveloperOptionsPreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public final boolean isAvailable() {
        String str = Enable16kUtils.DEV_OPTION_PROPERTY;
        return SystemProperties.getBoolean("ro.product.build.16k_page.enabled", false);
    }

    public final void on16kPagesDialogConfirmed() {
        DevelopmentSettingsDashboardFragment developmentSettingsDashboardFragment = this.mFragment;
        AlertDialog.Builder builder =
                new AlertDialog.Builder(developmentSettingsDashboardFragment.getActivity());
        builder.setTitle(R.string.progress_16k_ota_title);
        ProgressBar progressBar =
                new ProgressBar(developmentSettingsDashboardFragment.getActivity());
        progressBar.setLayoutParams(new LinearLayout.LayoutParams(-2, -2));
        progressBar.setPadding(0, 24, 0, 24);
        builder.setView(progressBar);
        builder.P.mCancelable = false;
        AlertDialog create = builder.create();
        this.mProgressDialog = create;
        create.show();
        ListenableFuture submit =
                ((MoreExecutors.ListeningDecorator) this.mExecutorService)
                        .submit(
                                new Runnable() { // from class:
                                                 // com.android.settings.development.Enable16kPagesPreferenceController$$ExternalSyntheticLambda1
                                    @Override // java.lang.Runnable
                                    public final void run() {
                                        Enable16kPagesPreferenceController
                                                .$r8$lambda$316NZEq35ADdErAn0hq0_g2UmHc(
                                                        Enable16kPagesPreferenceController.this);
                                    }
                                });
        FutureCallback futureCallback =
                new FutureCallback() { // from class:
                                       // com.android.settings.development.Enable16kPagesPreferenceController.1
                    @Override // com.google.common.util.concurrent.FutureCallback
                    public final void onFailure(Throwable th) {
                        Enable16kPagesPreferenceController enable16kPagesPreferenceController =
                                Enable16kPagesPreferenceController.this;
                        AlertDialog alertDialog =
                                enable16kPagesPreferenceController.mProgressDialog;
                        if (alertDialog != null && alertDialog.isShowing()) {
                            enable16kPagesPreferenceController.mProgressDialog.hide();
                        }
                        Log.e(
                                "Enable16kPages",
                                "Failed to call applyPayload of UpdateEngineStable!",
                                th);
                        Enable16kPagesPreferenceController.m823$$Nest$mdisplayToast(
                                enable16kPagesPreferenceController,
                                ((AbstractPreferenceController) enable16kPagesPreferenceController)
                                        .mContext.getString(R.string.toast_16k_update_failed_text));
                    }

                    @Override // com.google.common.util.concurrent.FutureCallback
                    public final void onSuccess(Object obj) {
                        Log.i(
                                "Enable16kPages",
                                "applyPayload call to UpdateEngineStable succeeded.");
                    }
                };
        submit.addListener(
                new Futures.CallbackListener(submit, futureCallback),
                this.mContext.getMainExecutor());
    }

    @Override // com.android.settingslib.development.DeveloperOptionsPreferenceController
    public final void onDeveloperOptionsSwitchDisabled() {
        super.onDeveloperOptionsSwitchDisabled();
        Settings.Global.putInt(this.mContext.getContentResolver(), "enable_16k_pages", 0);
        ((SwitchPreference) this.mPreference).setChecked(false);
    }

    @Override // com.android.settingslib.development.DeveloperOptionsPreferenceController
    public final void onDeveloperOptionsSwitchEnabled() {
        Settings.Global.putInt(
                this.mContext.getContentResolver(),
                "enable_16k_pages",
                Enable16kUtils.isUsing16kbPages() ? 1 : 0);
    }

    @Override // androidx.preference.Preference.OnPreferenceChangeListener
    public final boolean onPreferenceChange(Preference preference, Object obj) {
        this.mEnable16k = ((Boolean) obj).booleanValue();
        boolean isDeviceOEMUnlocked = Enable16kUtils.isDeviceOEMUnlocked(this.mContext);
        Fragment fragment = this.mFragment;
        if (!isDeviceOEMUnlocked) {
            FragmentManagerImpl supportFragmentManager =
                    fragment.getActivity().getSupportFragmentManager();
            Fragment findFragmentByTag =
                    supportFragmentManager.findFragmentByTag("Enable16KOemUnlockDialog");
            if (findFragmentByTag == null) {
                findFragmentByTag = new Enable16KOemUnlockDialog();
            }
            if (findFragmentByTag instanceof Enable16KOemUnlockDialog) {
                findFragmentByTag.setTargetFragment(fragment, 0);
                ((Enable16KOemUnlockDialog) findFragmentByTag)
                        .show(supportFragmentManager, "Enable16KOemUnlockDialog");
            }
            return false;
        }
        boolean z = this.mEnable16k;
        FragmentManagerImpl supportFragmentManager2 =
                fragment.getActivity().getSupportFragmentManager();
        Fragment findFragmentByTag2 = supportFragmentManager2.findFragmentByTag("Enable16KDialog");
        if (findFragmentByTag2 == null) {
            findFragmentByTag2 = new Enable16kPagesWarningDialog();
        }
        if (!(findFragmentByTag2 instanceof Enable16kPagesWarningDialog)) {
            return true;
        }
        Bundle bundle = new Bundle();
        bundle.putBoolean("SHOW_16K_DIALOG", z);
        findFragmentByTag2.setArguments(bundle);
        findFragmentByTag2.setTargetFragment(fragment, 0);
        Enable16kPagesWarningDialog enable16kPagesWarningDialog =
                (Enable16kPagesWarningDialog) findFragmentByTag2;
        enable16kPagesWarningDialog.mHost = this;
        enable16kPagesWarningDialog.show(supportFragmentManager2, "Enable16KDialog");
        return true;
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final void updateState(Preference preference) {
        ((SwitchPreference) this.mPreference)
                .setChecked(
                        Settings.Global.getInt(
                                        this.mContext.getContentResolver(),
                                        "enable_16k_pages",
                                        Enable16kUtils.isUsing16kbPages() ? 1 : 0)
                                == 1);
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class OtaUpdateCallback extends UpdateEngineStableCallback {
        public final UpdateEngineStable mUpdateEngineStable;

        public OtaUpdateCallback(UpdateEngineStable updateEngineStable) {
            this.mUpdateEngineStable = updateEngineStable;
        }

        public final void onPayloadApplicationComplete(int i) {
            Log.i("Enable16kPages", "Callback from update engine stable received. unbinding..");
            this.mUpdateEngineStable.unbind();
            Enable16kPagesPreferenceController enable16kPagesPreferenceController =
                    Enable16kPagesPreferenceController.this;
            AlertDialog alertDialog = enable16kPagesPreferenceController.mProgressDialog;
            if (alertDialog != null && alertDialog.isShowing()) {
                enable16kPagesPreferenceController.mProgressDialog.hide();
            }
            if (i != 0) {
                WindowAreaControllerImpl$RearDisplayPresentationSessionConsumer$$ExternalSyntheticOutline0
                        .m(i, "applyPayload failed, error code: ", "Enable16kPages");
                Enable16kPagesPreferenceController enable16kPagesPreferenceController2 =
                        Enable16kPagesPreferenceController.this;
                Enable16kPagesPreferenceController.m823$$Nest$mdisplayToast(
                        enable16kPagesPreferenceController2,
                        ((AbstractPreferenceController) enable16kPagesPreferenceController2)
                                .mContext.getString(R.string.toast_16k_update_failed_text));
                return;
            }
            Log.i("Enable16kPages", "applyPayload successful");
            Settings.Global.putInt(
                    ((AbstractPreferenceController) Enable16kPagesPreferenceController.this)
                            .mContext.getContentResolver(),
                    "enable_16k_pages",
                    Enable16kPagesPreferenceController.this.mEnable16k ? 1 : 0);
            SystemUpdateManager systemUpdateManager =
                    (SystemUpdateManager)
                            ((AbstractPreferenceController) Enable16kPagesPreferenceController.this)
                                    .mContext.getSystemService(SystemUpdateManager.class);
            Enable16kPagesPreferenceController.this.getClass();
            PersistableBundle persistableBundle = new PersistableBundle();
            persistableBundle.putInt(IMSParameter.CALL.STATUS, 5);
            persistableBundle.putBoolean("is_security_update", false);
            persistableBundle.putString(
                    UniversalCredentialUtil.AGENT_TITLE, "Android 16K Kernel Experimental Update");
            systemUpdateManager.updateSystemUpdateInfo(persistableBundle);
            ((PowerManager)
                            ((AbstractPreferenceController) Enable16kPagesPreferenceController.this)
                                    .mContext.getSystemService(PowerManager.class))
                    .reboot("recovery");
        }

        public final void onStatusUpdate(int i, float f) {}
    }
}
