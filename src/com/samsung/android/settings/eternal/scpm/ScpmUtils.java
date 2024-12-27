package com.samsung.android.settings.eternal.scpm;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.ParcelFileDescriptor;

import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;

import com.samsung.android.scloud.lib.platform.api.Scpm;
import com.samsung.android.scloud.lib.platform.data.ConfigurationDataSet;
import com.samsung.android.scloud.lib.platform.data.ScpmDataSet;
import com.samsung.android.scloud.lib.platform.vo.ConfigurationVo;
import com.samsung.android.settings.eternal.log.EternalFileLog;
import com.samsung.android.settings.eternal.utils.FileUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.PosixFilePermission;
import java.nio.file.attribute.PosixFilePermissions;
import java.util.Set;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class ScpmUtils {
    public static ScpmUtils sScpmUtils;
    public Context mContext;
    public Scpm mScpm;
    public String mToken;

    public static void copyPFDToSettingsBixby(
            String str, ParcelFileDescriptor parcelFileDescriptor) {
        StringBuilder sb = new StringBuilder();
        try {
            FileReader fileReader = new FileReader(parcelFileDescriptor.getFileDescriptor());
            try {
                BufferedReader bufferedReader = new BufferedReader(fileReader);
                while (true) {
                    String readLine = bufferedReader.readLine();
                    if (readLine == null) {
                        break;
                    } else {
                        sb.append(readLine);
                    }
                }
                FileOutputStream fileOutputStream = new FileOutputStream(str);
                try {
                    fileOutputStream.write(sb.toString().getBytes());
                    fileOutputStream.close();
                    fileReader.close();
                } finally {
                }
            } finally {
            }
        } catch (Exception e) {
            EternalFileLog.e("ScpmUtils", "copyPFDToSettingsBixby() : " + e.getMessage());
        }
        EternalFileLog.i("ScpmUtils", "copyPFDToSettingsBixby() " + sb.length());
    }

    public static ScpmUtils getInstance(Context context) {
        if (sScpmUtils == null) {
            ScpmUtils scpmUtils = new ScpmUtils();
            scpmUtils.mContext = context;
            scpmUtils.mScpm = new Scpm(context);
            sScpmUtils = scpmUtils;
        }
        return sScpmUtils;
    }

    public final String jsonToString() {
        FileInputStream openFileInput = this.mContext.openFileInput("eternalPolicy.json");
        try {
            byte[] bArr = new byte[openFileInput.available()];
            if (openFileInput.read(bArr) <= 0) {
                EternalFileLog.e(
                        "ScpmUtils",
                        "jsonToString() : Fail json convert to String. buffer is empty. -"
                            + " eternalPolicy.json");
            }
            String str = new String(bArr, "UTF-8");
            openFileInput.close();
            return str;
        } catch (Throwable th) {
            if (openFileInput != null) {
                try {
                    openFileInput.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
            }
            throw th;
        }
    }

    public final void registerScpm() {
        if (this.mScpm
                        .configuration
                        .context
                        .getPackageManager()
                        .resolveContentProvider("com.samsung.android.scpm.policy", 0)
                == null) {
            EternalFileLog.i("ScpmUtils", "startSetup() : ScpmAgent is not installed");
            return;
        }
        SharedPreferences sharedPreferences = this.mContext.getSharedPreferences("scpmInfo", 0);
        if ((sharedPreferences != null ? sharedPreferences.getString("token", null) : null)
                == null) {
            new Thread(
                            new Runnable() { // from class:
                                // com.samsung.android.settings.eternal.scpm.ScpmUtils.1
                                @Override // java.lang.Runnable
                                public final void run() {
                                    ScpmUtils scpmUtils = ScpmUtils.this;
                                    Scpm scpm = scpmUtils.mScpm;
                                    try {
                                        ScpmDataSet register =
                                                scpm.registration.register(
                                                        scpmUtils
                                                                .mContext
                                                                .getPackageManager()
                                                                .getPackageInfo(
                                                                        "com.samsung.android.app.settings.bixby",
                                                                        64)
                                                                .signatures[0]
                                                                .toCharsString());
                                        String str = register.token;
                                        scpmUtils.mToken = str;
                                        if (str == null) {
                                            EternalFileLog.e(
                                                    "ScpmUtils",
                                                    "registerAppToScpm() : token is null = {rcode :"
                                                        + " "
                                                            + register.rcode
                                                            + "} {rmsg : "
                                                            + register.rmsg
                                                            + "}");
                                        } else {
                                            SharedPreferences sharedPreferences2 =
                                                    scpmUtils.mContext.getSharedPreferences(
                                                            "scpmInfo", 0);
                                            if (sharedPreferences2 != null) {
                                                SharedPreferences.Editor edit =
                                                        sharedPreferences2.edit();
                                                edit.putString("token", str);
                                                edit.apply();
                                            }
                                            ConfigurationDataSet initialize =
                                                    scpm.configuration.initialize(
                                                            new ConfigurationVo(
                                                                    scpmUtils
                                                                            .mContext
                                                                            .getPackageManager()
                                                                            .getPackageInfo(
                                                                                    "com.samsung.android.app.settings.bixby",
                                                                                    0)
                                                                            .versionName,
                                                                    scpmUtils.mToken));
                                            int i = initialize.result;
                                            if (i == 0) {
                                                EternalFileLog.d(
                                                        "ScpmUtils",
                                                        "DO_NOTHING : There is no newly updated"
                                                            + " configuration, so it does not need"
                                                            + " to operate.");
                                            } else if (i != 1) {
                                                EternalFileLog.d(
                                                        "ScpmUtils",
                                                        "FAIL : Update fails - error code : "
                                                                + initialize.rcode);
                                            } else {
                                                EternalFileLog.d(
                                                        "ScpmUtils", "SUCCESS : initialize");
                                            }
                                        }
                                    } catch (PackageManager.NameNotFoundException e) {
                                        EternalFileLog.e(
                                                "ScpmUtils",
                                                "registerAppToScpm() : " + e.getMessage());
                                    }
                                    final ScpmUtils scpmUtils2 = ScpmUtils.this;
                                    String str2 =
                                            scpmUtils2.mContext.getFilesDir().getAbsolutePath()
                                                    + "/";
                                    File file = new File(str2);
                                    if (!file.exists() && !file.mkdir()) {
                                        EternalFileLog.d(
                                                "ScpmUtils",
                                                "getDefaultFile() : mkDir error "
                                                        + file.getAbsolutePath());
                                        return;
                                    }
                                    final File file2 =
                                            new File(
                                                    AbstractResolvableFuture$$ExternalSyntheticOutline0
                                                            .m(str2, "eternalPolicy.json"));
                                    if (file2.exists()) {
                                        EternalFileLog.d(
                                                "ScpmUtils",
                                                "getDefaultFile() : file already exist -"
                                                    + " eternalPolicy.json");
                                        return;
                                    }
                                    try {
                                        if (file2.createNewFile()) {
                                            EternalFileLog.d(
                                                    "ScpmUtils",
                                                    "getDefaultFile() : file cretaed"
                                                        + " eternalPolicy.json");
                                        } else {
                                            EternalFileLog.d(
                                                    "ScpmUtils",
                                                    "getDefaultFile() : check eternalPolicy.json");
                                        }
                                        new Thread(
                                                        new Runnable() { // from class:
                                                            // com.samsung.android.settings.eternal.scpm.ScpmUtils.2
                                                            @Override // java.lang.Runnable
                                                            public final void run() {
                                                                try {
                                                                    if (file2.exists()) {
                                                                        Set<PosixFilePermission>
                                                                                fromString =
                                                                                        PosixFilePermissions
                                                                                                .fromString(
                                                                                                        "rw-rw----");
                                                                        Path path =
                                                                                Paths.get(
                                                                                        file2
                                                                                                .getAbsolutePath(),
                                                                                        new String
                                                                                                [0]);
                                                                        Set<PosixFilePermission>
                                                                                posixFilePermissions =
                                                                                        Files
                                                                                                .getPosixFilePermissions(
                                                                                                        path,
                                                                                                        new LinkOption
                                                                                                                [0]);
                                                                        ScpmUtils.this.getClass();
                                                                        if (posixFilePermissions
                                                                                        .contains(
                                                                                                PosixFilePermission
                                                                                                        .GROUP_READ)
                                                                                && posixFilePermissions
                                                                                        .contains(
                                                                                                PosixFilePermission
                                                                                                        .GROUP_WRITE)
                                                                                && posixFilePermissions
                                                                                        .contains(
                                                                                                PosixFilePermission
                                                                                                        .OWNER_READ)
                                                                                && posixFilePermissions
                                                                                        .contains(
                                                                                                PosixFilePermission
                                                                                                        .OWNER_WRITE)) {
                                                                            return;
                                                                        }
                                                                        Files
                                                                                .setPosixFilePermissions(
                                                                                        path,
                                                                                        fromString);
                                                                    }
                                                                } catch (Exception e2) {
                                                                    EternalFileLog.e(
                                                                            "ScpmUtils",
                                                                            "changePermission() : "
                                                                                    + e2
                                                                                            .getMessage());
                                                                }
                                                            }
                                                        })
                                                .start();
                                    } catch (IOException e2) {
                                        EternalFileLog.e(
                                                "ScpmUtils",
                                                "getDefaultFile() : ERROR CREATING FILE -"
                                                    + " eternalPolicy.json  "
                                                        + e2.getMessage());
                                        FileUtils.deleteFile(
                                                file2, "ScpmUtils", "getDefaultFile()");
                                    }
                                }
                            })
                    .start();
        } else {
            EternalFileLog.i("ScpmUtils", "startSetup() : Already registered with ScpmAgent");
        }
    }
}
