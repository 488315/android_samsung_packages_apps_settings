package com.samsung.android.settings.homepage;

import android.app.PendingIntent;
import android.content.ActivityNotFoundException;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentSender;
import android.content.pm.PackageInstaller;
import android.net.Uri;
import android.os.Bundle;
import android.os.UserHandle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import androidx.preference.Preference;

import com.android.settings.R;
import com.android.settings.Utils;
import com.android.settingslib.utils.ThreadUtils;

import com.samsung.android.feature.SemFloatingFeature;
import com.samsung.android.settings.PkgUtils;
import com.samsung.android.settings.Rune;
import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;

import java.io.File;
import java.io.IOException;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class TopLevelRemoteSupportPreferenceController extends TopLevelPreferenceController {
    private static final String INTENT_NAME = "install_complete";
    protected static final String PREFERENCE_KEY = "top_level_remote_support";
    private static final String REMOTE_SUPPORT_LOCATION = "/system/hidden/SmartTutor_SEP.apk";
    private static final String SMART_TUTOR_PACKAGE_NAME = "com.rsupport.rs.activity.rsupport.aas2";
    private static final String TAG = "TopLevelRemoteSupportPreferenceController";
    private int mPackageInstallerSessionId;
    final Uri mSmartTutorDownloadUri;
    private String mSmartTutorPkgLocation;
    private String mSmartTutorPkgName;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class PackageInstallerListener extends BroadcastReceiver {
        public PackageInstallerListener() {}

        @Override // android.content.BroadcastReceiver
        public final void onReceive(Context context, Intent intent) {
            TopLevelRemoteSupportPreferenceController.this.mPackageInstallerSessionId = -1;
            int intExtra = intent.getIntExtra("android.content.pm.extra.STATUS", 1);
            Log.i(
                    TopLevelRemoteSupportPreferenceController.TAG,
                    "PackageInstallerCallback: result ["
                            + intExtra
                            + "], message ["
                            + intent.getStringExtra("android.content.pm.extra.STATUS_MESSAGE")
                            + "], packageName ["
                            + intent.getStringExtra("android.content.pm.extra.PACKAGE_NAME")
                            + "]");
            if (intExtra == -1) {
                Log.i(
                        TopLevelRemoteSupportPreferenceController.TAG,
                        "STATUS_PENDING_USER_ACTION - no reaction");
            } else if (intExtra != 0) {
                Log.i(TopLevelRemoteSupportPreferenceController.TAG, " INSTALL failed.");
            } else {
                Log.i(TopLevelRemoteSupportPreferenceController.TAG, " INSTALL STATUS_SUCCESS");
                TopLevelRemoteSupportPreferenceController.this.launchRemoteSupportPackage();
            }
            try {
                context.unregisterReceiver(this);
            } catch (Exception unused) {
                Log.e(
                        TopLevelRemoteSupportPreferenceController.TAG,
                        " Something wrong at unregisterReceiver");
            }
        }
    }

    public TopLevelRemoteSupportPreferenceController(Context context, String str) {
        super(context, str);
        this.mPackageInstallerSessionId = -1;
        this.mSmartTutorPkgName =
                SemFloatingFeature.getInstance()
                        .getString("SEC_FLOATING_FEATURE_COMMON_CONFIG_SMARTTUTOR_PACKAGES_NAME");
        this.mSmartTutorPkgLocation =
                SemFloatingFeature.getInstance()
                        .getString("SEC_FLOATING_FEATURE_COMMON_CONFIG_SMARTTUTOR_PACKAGES_PATH");
        this.mSmartTutorDownloadUri =
                Uri.parse(
                        "samsungapps://ProductDetail/"
                                + this.mSmartTutorPkgName
                                + "?form=popup&source=TIPS_TEST");
        if (TextUtils.isEmpty(this.mSmartTutorPkgName)) {
            this.mSmartTutorPkgName = SMART_TUTOR_PACKAGE_NAME;
        }
        if (TextUtils.isEmpty(this.mSmartTutorPkgLocation)) {
            this.mSmartTutorPkgLocation = REMOTE_SUPPORT_LOCATION;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:50:0x006b A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:57:? A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:58:0x0061 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private boolean addApkToInstallSession(
            java.lang.String r8, android.content.pm.PackageInstaller.Session r9)
            throws java.io.IOException {
        /*
            r7 = this;
            r7 = 0
            r0 = 0
            java.lang.String r2 = "package"
            r3 = 0
            r5 = -1
            r1 = r9
            java.io.OutputStream r9 = r1.openWrite(r2, r3, r5)     // Catch: java.lang.Throwable -> L41 java.lang.Exception -> L44
            java.io.FileInputStream r1 = new java.io.FileInputStream     // Catch: java.lang.Throwable -> L3b java.lang.Exception -> L3e
            r1.<init>(r8)     // Catch: java.lang.Throwable -> L3b java.lang.Exception -> L3e
            r8 = 16384(0x4000, float:2.2959E-41)
            byte[] r8 = new byte[r8]     // Catch: java.lang.Throwable -> L21 java.lang.Exception -> L24
        L17:
            int r0 = r1.read(r8)     // Catch: java.lang.Throwable -> L21 java.lang.Exception -> L24
            if (r0 < 0) goto L27
            r9.write(r8, r7, r0)     // Catch: java.lang.Throwable -> L21 java.lang.Exception -> L24
            goto L17
        L21:
            r7 = move-exception
        L22:
            r0 = r9
            goto L5f
        L24:
            r8 = move-exception
        L25:
            r0 = r9
            goto L46
        L27:
            if (r9 == 0) goto L31
            r9.close()     // Catch: java.io.IOException -> L2d
            goto L31
        L2d:
            r7 = move-exception
            r7.printStackTrace()
        L31:
            r1.close()     // Catch: java.io.IOException -> L35
            goto L39
        L35:
            r7 = move-exception
            r7.printStackTrace()
        L39:
            r7 = 1
            return r7
        L3b:
            r7 = move-exception
            r1 = r0
            goto L22
        L3e:
            r8 = move-exception
            r1 = r0
            goto L25
        L41:
            r7 = move-exception
            r1 = r0
            goto L5f
        L44:
            r8 = move-exception
            r1 = r0
        L46:
            r8.printStackTrace()     // Catch: java.lang.Throwable -> L5e
            if (r0 == 0) goto L53
            r0.close()     // Catch: java.io.IOException -> L4f
            goto L53
        L4f:
            r8 = move-exception
            r8.printStackTrace()
        L53:
            if (r1 == 0) goto L5d
            r1.close()     // Catch: java.io.IOException -> L59
            goto L5d
        L59:
            r8 = move-exception
            r8.printStackTrace()
        L5d:
            return r7
        L5e:
            r7 = move-exception
        L5f:
            if (r0 == 0) goto L69
            r0.close()     // Catch: java.io.IOException -> L65
            goto L69
        L65:
            r8 = move-exception
            r8.printStackTrace()
        L69:
            if (r1 == 0) goto L73
            r1.close()     // Catch: java.io.IOException -> L6f
            goto L73
        L6f:
            r8 = move-exception
            r8.printStackTrace()
        L73:
            throw r7
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.samsung.android.settings.homepage.TopLevelRemoteSupportPreferenceController.addApkToInstallSession(java.lang.String,"
                    + " android.content.pm.PackageInstaller$Session):boolean");
    }

    private IntentSender createIntentSender(Context context, int i) {
        return PendingIntent.getBroadcast(context, i, new Intent(INTENT_NAME), 67108864)
                .getIntentSender();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void launchRemoteSupportPackage() {
        Intent launchIntentForPackage =
                this.mContext
                        .getPackageManager()
                        .getLaunchIntentForPackage(this.mSmartTutorPkgName);
        Bundle bundle = new Bundle();
        bundle.putString("packageName", this.mContext.getPackageName());
        bundle.putString("appName", "Settings");
        launchIntentForPackage.putExtras(bundle);
        this.mContext.startActivity(launchIntentForPackage);
    }

    @Override // com.samsung.android.settings.homepage.TopLevelPreferenceController,
              // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        if (Rune.isChinaModel() || Rune.isJapanModel()) {
            return 3;
        }
        String salesCode = Utils.getSalesCode();
        if ("VZW".equals(salesCode) || "ATT".equals(salesCode) || "AIO".equals(salesCode)) {
            return 3;
        }
        Context context = this.mContext;
        if (PkgUtils.isPackageEnabled(
                        context, context.getString(R.string.config_sec_toplevel_tips_package))
                || this.mContext.getPackageManager().isSafeMode()) {
            return 2;
        }
        if (UserHandle.myUserId() != 0) {
            return 4;
        }
        if (Utils.hasPackage(this.mContext, this.mSmartTutorPkgName)) {
            return 0;
        }
        File file = new File(this.mSmartTutorPkgLocation);
        if (file.exists() && !file.isDirectory()) {
            return 0;
        }
        Intent intent = new Intent();
        intent.setData(this.mSmartTutorDownloadUri);
        return !Utils.isIntentAvailable(this.mContext, intent) ? 2 : 0;
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

    @Override // com.samsung.android.settings.homepage.TopLevelPreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlValue getValue() {
        return super.getValue();
    }

    @Override // com.samsung.android.settings.homepage.TopLevelPreferenceController,
              // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public boolean handlePreferenceTreeClick(Preference preference) {
        if (!TextUtils.equals(preference.getKey(), getPreferenceKey())) {
            return super.handlePreferenceTreeClick(preference);
        }
        if (Utils.hasPackage(this.mContext, this.mSmartTutorPkgName)) {
            launchRemoteSupportPackage();
            return true;
        }
        File file = new File(this.mSmartTutorPkgLocation);
        if (file.exists() && !file.isDirectory()) {
            Toast.makeText(this.mContext, R.string.sec_installing_applications, 0).show();
            ThreadUtils.postOnBackgroundThread(
                    new Runnable() { // from class:
                                     // com.samsung.android.settings.homepage.TopLevelRemoteSupportPreferenceController$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            TopLevelRemoteSupportPreferenceController.this
                                    .lambda$handlePreferenceTreeClick$0();
                        }
                    });
            return true;
        }
        try {
            Intent intent = new Intent();
            intent.setData(this.mSmartTutorDownloadUri);
            intent.addFlags(67108896);
            this.mContext.startActivity(intent);
            return true;
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
            return true;
        }
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

    /* renamed from: startInstall, reason: merged with bridge method [inline-methods] */
    public void lambda$handlePreferenceTreeClick$0() {
        PackageInstaller.Session session = null;
        try {
            PackageInstaller packageInstaller =
                    this.mContext.getPackageManager().getPackageInstaller();
            int i = this.mPackageInstallerSessionId;
            if (i != -1) {
                PackageInstaller.SessionInfo sessionInfo = packageInstaller.getSessionInfo(i);
                if (sessionInfo != null && sessionInfo.isActive()) {
                    Log.e(TAG, "already installing. reqeust ignored");
                    return;
                }
                this.mPackageInstallerSessionId = -1;
            }
            int createSession =
                    packageInstaller.createSession(new PackageInstaller.SessionParams(1));
            this.mPackageInstallerSessionId = createSession;
            if (createSession == -1) {
                Log.e(TAG, "Cannot create packageInstaller session");
                return;
            }
            PackageInstaller.Session openSession = packageInstaller.openSession(createSession);
            if (addApkToInstallSession(this.mSmartTutorPkgLocation, openSession)) {
                this.mContext.registerReceiver(
                        new PackageInstallerListener(),
                        new IntentFilter(INTENT_NAME),
                        null,
                        null,
                        2);
                openSession.commit(
                        createIntentSender(this.mContext, this.mPackageInstallerSessionId));
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Couldn't install package", e);
        } catch (RuntimeException e2) {
            if (0 != 0) {
                session.abandon();
            }
            e2.printStackTrace();
            throw e2;
        }
    }

    @Override // com.samsung.android.settings.homepage.TopLevelPreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }
}
