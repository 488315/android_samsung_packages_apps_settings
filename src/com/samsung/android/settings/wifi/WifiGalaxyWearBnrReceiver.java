package com.samsung.android.settings.wifi;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.util.Log;

import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;
import androidx.core.content.FileProvider;
import androidx.fragment.app.DialogFragment$$ExternalSyntheticOutline0;

import com.android.settingslib.qrcode.QrCamera$DecodingTask$$ExternalSyntheticOutline0;

import com.sec.ims.settings.ImsProfile;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public class WifiGalaxyWearBnrReceiver extends BroadcastReceiver {
    public static Context mContext;
    public static Handler mInstance;
    public static HandlerThread mWorkerThread;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class FileProviderHandler implements Handler.Callback {
        public FileProviderHandler() {}

        @Override // android.os.Handler.Callback
        public final boolean handleMessage(Message message) {
            StringBuilder sb;
            FileOutputStream fileOutputStream;
            if (message.what == 1) {
                int i = message.arg1;
                if (i == 0) {
                    Log.d(
                            "WifiGalaxyWearBnrReceiver",
                            "handleMessage :: Config File Process Done ");
                    WifiGalaxyWearBnrReceiver wifiGalaxyWearBnrReceiver =
                            WifiGalaxyWearBnrReceiver.this;
                    String str = (String) message.obj;
                    Context context = WifiGalaxyWearBnrReceiver.mContext;
                    wifiGalaxyWearBnrReceiver.getClass();
                    context.revokeUriPermission(str, WifiGalaxyWearBnrReceiver.getUri(), 1);
                    WifiGalaxyWearBnrReceiver.this.getClass();
                    String bnrFilePath = WifiGalaxyWearBnrReceiver.getBnrFilePath();
                    WifiGalaxyWearBnrReceiver.this.getClass();
                    File file = new File(bnrFilePath);
                    if (file.exists()) {
                        file.delete();
                    }
                } else if (i == 1) {
                    Log.d("WifiGalaxyWearBnrReceiver", "handleMessage :: Config File Request ");
                    WifiGalaxyWearBnrReceiver wifiGalaxyWearBnrReceiver2 =
                            WifiGalaxyWearBnrReceiver.this;
                    Context context2 = WifiGalaxyWearBnrReceiver.mContext;
                    wifiGalaxyWearBnrReceiver2.getClass();
                    byte[] retrieveBackupData =
                            ((WifiManager)
                                            WifiGalaxyWearBnrReceiver.mContext.getSystemService(
                                                    ImsProfile.PDN_WIFI))
                                    .retrieveBackupData();
                    String bnrFilePath2 = WifiGalaxyWearBnrReceiver.getBnrFilePath();
                    DialogFragment$$ExternalSyntheticOutline0.m(
                            " path=", bnrFilePath2, "WifiGalaxyWearBnrReceiver");
                    try {
                        File file2 = new File(bnrFilePath2);
                        if (file2.exists()) {
                            file2.delete();
                        }
                        file2.createNewFile();
                    } catch (IOException e) {
                        QrCamera$DecodingTask$$ExternalSyntheticOutline0.m(
                                "createBnRFile exception: ", e, "WifiGalaxyWearBnrReceiver");
                    }
                    File file3 = new File(bnrFilePath2);
                    file3.setReadable(true, false);
                    FileOutputStream fileOutputStream2 = null;
                    try {
                        try {
                            fileOutputStream = new FileOutputStream(file3, true);
                            try {
                                DataOutputStream dataOutputStream =
                                        new DataOutputStream(fileOutputStream);
                                dataOutputStream.write(retrieveBackupData);
                                dataOutputStream.flush();
                                dataOutputStream.close();
                            } catch (IOException e2) {
                                e = e2;
                                fileOutputStream2 = fileOutputStream;
                                Log.e("WifiGalaxyWearBnrReceiver", "doBnr IO exception " + e);
                                if (fileOutputStream2 != null) {
                                    try {
                                        fileOutputStream2.close();
                                    } catch (IOException e3) {
                                        e = e3;
                                        sb = new StringBuilder("IOException  ");
                                        sb.append(e.toString());
                                        Log.e("WifiGalaxyWearBnrReceiver", sb.toString());
                                        WifiGalaxyWearBnrReceiver.this.getClass();
                                        Uri uri = WifiGalaxyWearBnrReceiver.getUri();
                                        String str2 = (String) message.obj;
                                        Intent intent =
                                                new Intent(
                                                        "com.sec.android.intent.action.RESPONSE_CREATE_URI_WIFIWPACONF");
                                        WifiGalaxyWearBnrReceiver.mContext.grantUriPermission(
                                                str2, uri, 1);
                                        intent.setPackage(str2);
                                        intent.setDataAndType(uri, "text/*");
                                        intent.addFlags(32);
                                        intent.addFlags(16777216);
                                        intent.addFlags(1);
                                        WifiGalaxyWearBnrReceiver.mContext.sendBroadcast(intent);
                                        return false;
                                    }
                                }
                                WifiGalaxyWearBnrReceiver.this.getClass();
                                Uri uri2 = WifiGalaxyWearBnrReceiver.getUri();
                                String str22 = (String) message.obj;
                                Intent intent2 =
                                        new Intent(
                                                "com.sec.android.intent.action.RESPONSE_CREATE_URI_WIFIWPACONF");
                                WifiGalaxyWearBnrReceiver.mContext.grantUriPermission(
                                        str22, uri2, 1);
                                intent2.setPackage(str22);
                                intent2.setDataAndType(uri2, "text/*");
                                intent2.addFlags(32);
                                intent2.addFlags(16777216);
                                intent2.addFlags(1);
                                WifiGalaxyWearBnrReceiver.mContext.sendBroadcast(intent2);
                                return false;
                            } catch (Throwable th) {
                                th = th;
                                fileOutputStream2 = fileOutputStream;
                                if (fileOutputStream2 != null) {
                                    try {
                                        fileOutputStream2.close();
                                    } catch (IOException e4) {
                                        Log.e(
                                                "WifiGalaxyWearBnrReceiver",
                                                "IOException  " + e4.toString());
                                    }
                                }
                                throw th;
                            }
                        } catch (Throwable th2) {
                            th = th2;
                        }
                    } catch (IOException e5) {
                        e = e5;
                    }
                    try {
                        fileOutputStream.close();
                    } catch (IOException e6) {
                        e = e6;
                        sb = new StringBuilder("IOException  ");
                        sb.append(e.toString());
                        Log.e("WifiGalaxyWearBnrReceiver", sb.toString());
                        WifiGalaxyWearBnrReceiver.this.getClass();
                        Uri uri22 = WifiGalaxyWearBnrReceiver.getUri();
                        String str222 = (String) message.obj;
                        Intent intent22 =
                                new Intent(
                                        "com.sec.android.intent.action.RESPONSE_CREATE_URI_WIFIWPACONF");
                        WifiGalaxyWearBnrReceiver.mContext.grantUriPermission(str222, uri22, 1);
                        intent22.setPackage(str222);
                        intent22.setDataAndType(uri22, "text/*");
                        intent22.addFlags(32);
                        intent22.addFlags(16777216);
                        intent22.addFlags(1);
                        WifiGalaxyWearBnrReceiver.mContext.sendBroadcast(intent22);
                        return false;
                    }
                    WifiGalaxyWearBnrReceiver.this.getClass();
                    Uri uri222 = WifiGalaxyWearBnrReceiver.getUri();
                    String str2222 = (String) message.obj;
                    Intent intent222 =
                            new Intent(
                                    "com.sec.android.intent.action.RESPONSE_CREATE_URI_WIFIWPACONF");
                    WifiGalaxyWearBnrReceiver.mContext.grantUriPermission(str2222, uri222, 1);
                    intent222.setPackage(str2222);
                    intent222.setDataAndType(uri222, "text/*");
                    intent222.addFlags(32);
                    intent222.addFlags(16777216);
                    intent222.addFlags(1);
                    WifiGalaxyWearBnrReceiver.mContext.sendBroadcast(intent222);
                }
            }
            return false;
        }
    }

    public static String getBnrFilePath() {
        String path = mContext.getDataDir().getPath();
        String str = File.separator;
        if (!path.endsWith(str)) {
            path = AbstractResolvableFuture$$ExternalSyntheticOutline0.m(path, str);
        }
        return path + "files" + str + "wifi_config.config";
    }

    public static Uri getUri() {
        String bnrFilePath = getBnrFilePath();
        Log.d("WifiGalaxyWearBnrReceiver", "getUri path: " + bnrFilePath);
        File file = new File(bnrFilePath);
        Context context = mContext;
        int i = FileProviderSender.$r8$clinit;
        Uri uriForFile = FileProvider.getUriForFile(context, file, "com.android.settings.files");
        Log.d("WifiGalaxyWearBnrReceiver", "getUri path Uri =  " + uriForFile.toString());
        return uriForFile;
    }

    @Override // android.content.BroadcastReceiver
    public final void onReceive(Context context, Intent intent) {
        Log.d("WifiGalaxyWearBnrReceiver", "onReceive" + intent.getAction());
        String action = intent.getAction();
        if (action.equals("com.sec.android.intent.action.REQUEST_CREATE_URI_WIFIWPACONF")
                || action.equals("com.sec.android.intent.action.RESPONSE_CREATE_URI_WIFIWPACONF")) {
            mContext = context;
            if (context == null) {
                Log.e("WifiGalaxyWearBnrReceiver", "startHandlerThread : Context is null");
            } else if (mWorkerThread == null) {
                Log.d("WifiGalaxyWearBnrReceiver", "startHandlerThread new Handler Thread created");
                HandlerThread handlerThread = new HandlerThread("WifiGalaxyWearBnr");
                mWorkerThread = handlerThread;
                handlerThread.start();
                mInstance = new Handler(mWorkerThread.getLooper(), new FileProviderHandler());
            }
        }
        if (action.equals("com.sec.android.intent.action.REQUEST_CREATE_URI_WIFIWPACONF")) {
            String stringExtra = intent.getStringExtra("requestPackage");
            int intExtra = intent.getIntExtra("actionValue", -1);
            Log.d(
                    "WifiGalaxyWearBnrReceiver",
                    "onReceive FILE_PROIVIDER_INTENT : " + intExtra + " / " + stringExtra);
            Message obtainMessage = mInstance.obtainMessage(1);
            obtainMessage.obj = stringExtra;
            obtainMessage.arg1 = intExtra;
            mInstance.sendMessage(obtainMessage);
        }
    }
}
