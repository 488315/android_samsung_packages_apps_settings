package com.samsung.android.settings.general;

import android.R;
import android.app.ActivityManager;
import android.app.ProgressDialog;
import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.SystemProperties;
import android.util.secutil.Log;
import android.view.ContextThemeWrapper;

import com.android.settings.Utils;
import com.android.settingslib.applications.RecentAppOpsAccess;

import com.samsung.android.knox.EnterpriseContainerCallback;
import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.sec_platform_library.FactoryPhone;
import com.sec.ims.volte2.data.VolteConstants;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class MainClearModemReset extends Service {
    public AnonymousClass2 mDelayRunnable;
    public boolean mEraseEsims;
    public boolean mEraseSdCard;
    public FactoryPhone mPhone;
    public String isHiddenM = null;
    public String requestedTimeArg = null;
    public final AnonymousClass1 mHandler =
            new Handler() { // from class:
                            // com.samsung.android.settings.general.MainClearModemReset.1
                @Override // android.os.Handler
                public final void handleMessage(Message message) {
                    int i = message.what;
                    MainClearModemReset mainClearModemReset = MainClearModemReset.this;
                    if (i == 1008) {
                        Log.secI("MainClearModemReset", "Modem reset is done");
                        MainClearModemReset.m1230$$Nest$msendMainClearIntent(mainClearModemReset);
                    } else if (i != 1010) {
                        Log.secW("MainClearModemReset", "Something weird happened");
                    } else {
                        Log.i("MainClearModemReset", "modem CP2 reset done");
                        MainClearModemReset.m1230$$Nest$msendMainClearIntent(mainClearModemReset);
                    }
                }
            };

    /* renamed from: -$$Nest$msendMainClearIntent, reason: not valid java name */
    public static void m1230$$Nest$msendMainClearIntent(MainClearModemReset mainClearModemReset) {
        synchronized (mainClearModemReset) {
            try {
                if ("1".equals(String.valueOf(SystemProperties.get("dev.rtnreset.property")))) {
                    Log.d(
                            "MainClearModemReset",
                            "rtn reset property is 1, send WipeCustomerPartiotion");
                    SystemProperties.set("dev.rtnreset.property", "-1");
                    Log.i(
                            "MainClearModemReset",
                            "RTN_RESET_PROPERTY ,Default value is set to -1 "
                                    .concat(
                                            String.valueOf(
                                                    SystemProperties.get(
                                                            "dev.rtnreset.property"))));
                    Intent intent = new Intent("android.intent.action.MASTER_CLEAR");
                    intent.putExtra("WipeCustomerPartiotion", true);
                    intent.putExtra("android.intent.extra.REASON", "HIDDEN_MENU");
                    Log.d(
                            "MainClearModemReset",
                            "setting package android and sending explicit broadcast");
                    intent.setPackage(RecentAppOpsAccess.ANDROID_SYSTEM_PACKAGE_NAME);
                    intent.addFlags(268435456);
                    Log.d(
                            "MainClearModemReset",
                            "sent MASTER_CLEAR intent and add WipeCustomerPartiotion for"
                                + " wipe_carrier&wipe_data");
                    Log.secI(
                            "MainClearModemReset",
                            "Factory reset requested time is "
                                    + mainClearModemReset.requestedTimeArg);
                    String str = mainClearModemReset.requestedTimeArg;
                    if (str != null) {
                        intent.putExtra("com.android.internal.intent.extra.FDR_REQUEST_TIME", str);
                    }
                    mainClearModemReset.sendBroadcast(intent);
                } else {
                    Log.d(
                            "MainClearModemReset",
                            "rtn reset property is not 1, don`t send WipeCustomerPartiotion");
                    Intent intent2 = new Intent("android.intent.action.FACTORY_RESET");
                    intent2.addFlags(16777216);
                    intent2.addFlags(268435456);
                    intent2.putExtra("android.intent.extra.REASON", "MasterClearConfirm");
                    intent2.putExtra(
                            "android.intent.extra.WIPE_EXTERNAL_STORAGE",
                            mainClearModemReset.mEraseSdCard);
                    intent2.putExtra(
                            "com.android.internal.intent.extra.WIPE_ESIMS",
                            mainClearModemReset.mEraseEsims);
                    Log.secI(
                            "MainClearModemReset",
                            "Factory reset requested time is "
                                    + mainClearModemReset.requestedTimeArg);
                    String str2 = mainClearModemReset.requestedTimeArg;
                    if (str2 != null) {
                        intent2.putExtra(
                                "com.android.internal.intent.extra.FDR_REQUEST_TIME", str2);
                    }
                    mainClearModemReset.sendBroadcast(intent2);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    @Override // android.app.Service
    public final IBinder onBind(Intent intent) {
        return null;
    }

    @Override // android.app.Service
    public final void onCreate() {
        super.onCreate();
        StringBuilder sb = Utils.sBuilder;
        if (ActivityManager.isUserAMonkey()) {
            stopSelf();
            return;
        }
        FactoryPhone factoryPhone = new FactoryPhone(this);
        this.mPhone = factoryPhone;
        factoryPhone.connectToRilService();
    }

    @Override // android.app.Service
    public final void onDestroy() {
        this.mPhone.disconnectFromRilService();
        super.onDestroy();
    }

    /* JADX WARN: Type inference failed for: r10v9, types: [com.samsung.android.settings.general.MainClearModemReset$2] */
    @Override // android.app.Service
    public final void onStart(Intent intent, int i) {
        boolean z;
        short s;
        byte[] bArr;
        super.onStart(intent, i);
        if (intent != null) {
            this.isHiddenM = intent.getStringExtra("hiddenmenu");
            Log.d(
                    "MainClearModemReset",
                    "MasterClearModemReset, onStart isHiddenM: " + this.isHiddenM);
        }
        if (intent != null) {
            z = intent.getBooleanExtra("FACTORY", false);
            this.mEraseSdCard = intent.getBooleanExtra("WIPE_EXTERNAL_STORAGE", false);
            Log.secI("MainClearModemReset", "WIPE_EXTERNAL_STORAGE = " + this.mEraseSdCard);
            this.mEraseEsims = intent.getBooleanExtra("WIPE_ESIMS", false);
            Log.secI("MainClearModemReset", "WIPE_ESIMS = " + this.mEraseEsims);
        } else {
            z = false;
        }
        if (!z) {
            stopSelf();
            return;
        }
        ProgressDialog progressDialog =
                new ProgressDialog(
                        new ContextThemeWrapper(this, R.style.Theme.DeviceDefault.Light));
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);
        progressDialog.getWindow().setType(VolteConstants.ErrorCode.MAKECALL_REG_FAILURE_REG_403);
        progressDialog.setMessage(getText(com.android.settings.R.string.shutdown_progress));
        progressDialog.show();
        Log.secI("MainClearModemReset", "Modem reset started...");
        long currentTimeMillis = System.currentTimeMillis();
        this.requestedTimeArg =
                "requested_time="
                        + new SimpleDateFormat("yyyy/MM/dd HH:mm:ss.SSS")
                                .format(new Date(currentTimeMillis));
        Log.secI("MainClearModemReset", "Factory reset requested time is " + this.requestedTimeArg);
        Log.d(
                "MainClearModemReset",
                "modem name: " + SystemProperties.get("ril.modem.board", ApnSettings.MVNO_NONE));
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream);
        byte[] bArr2 = {12, 1};
        short s2 = (short) 4;
        if ("true".equalsIgnoreCase(this.isHiddenM)) {
            try {
                dataOutputStream.writeByte(2);
            } catch (IOException e) {
                e.printStackTrace();
            }
            s = (short) (s2 + 1);
            Log.d("MainClearModemReset", "sendResetCommandToRIL() 0x02 ");
        } else {
            try {
                dataOutputStream.writeByte(4);
            } catch (IOException e2) {
                e2.printStackTrace();
            }
            s = (short) (s2 + 1);
            Log.d("MainClearModemReset", "sendResetCommandToRIL() 0x04 ");
        }
        FactoryPhone factoryPhone = this.mPhone;
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        ByteArrayOutputStream byteArrayOutputStream2 = new ByteArrayOutputStream();
        DataOutputStream dataOutputStream2 = new DataOutputStream(byteArrayOutputStream2);
        try {
            try {
                dataOutputStream2.write(bArr2, 0, 2);
                dataOutputStream2.writeShort(s);
                dataOutputStream2.write(byteArray, 0, byteArray.length);
                try {
                    dataOutputStream2.close();
                    byteArrayOutputStream2.close();
                } catch (IOException e3) {
                    e3.printStackTrace();
                }
                bArr = byteArrayOutputStream2.toByteArray();
            } catch (IOException unused) {
                dataOutputStream2.close();
                byteArrayOutputStream2.close();
                bArr = null;
                factoryPhone.invokeOemRilRequestRaw(
                        bArr,
                        obtainMessage(
                                EnterpriseContainerCallback.CONTAINER_PACKAGE_UNINSTALL_SUCCESS));
                this.mDelayRunnable =
                        new Runnable() { // from class:
                                         // com.samsung.android.settings.general.MainClearModemReset.2
                            @Override // java.lang.Runnable
                            public final void run() {
                                Log.secI("MainClearModemReset", "MasterClearModemReset-Timeout");
                                MainClearModemReset.m1230$$Nest$msendMainClearIntent(
                                        MainClearModemReset.this);
                            }
                        };
                new Handler().postDelayed(this.mDelayRunnable, 20000L);
            } catch (Throwable th) {
                try {
                    dataOutputStream2.close();
                    byteArrayOutputStream2.close();
                } catch (IOException e4) {
                    e4.printStackTrace();
                }
                throw th;
            }
        } catch (IOException e5) {
            e5.printStackTrace();
            bArr = null;
            factoryPhone.invokeOemRilRequestRaw(
                    bArr,
                    obtainMessage(EnterpriseContainerCallback.CONTAINER_PACKAGE_UNINSTALL_SUCCESS));
            this.mDelayRunnable =
                    new Runnable() { // from class:
                                     // com.samsung.android.settings.general.MainClearModemReset.2
                        @Override // java.lang.Runnable
                        public final void run() {
                            Log.secI("MainClearModemReset", "MasterClearModemReset-Timeout");
                            MainClearModemReset.m1230$$Nest$msendMainClearIntent(
                                    MainClearModemReset.this);
                        }
                    };
            new Handler().postDelayed(this.mDelayRunnable, 20000L);
        }
        factoryPhone.invokeOemRilRequestRaw(
                bArr,
                obtainMessage(EnterpriseContainerCallback.CONTAINER_PACKAGE_UNINSTALL_SUCCESS));
        this.mDelayRunnable =
                new Runnable() { // from class:
                                 // com.samsung.android.settings.general.MainClearModemReset.2
                    @Override // java.lang.Runnable
                    public final void run() {
                        Log.secI("MainClearModemReset", "MasterClearModemReset-Timeout");
                        MainClearModemReset.m1230$$Nest$msendMainClearIntent(
                                MainClearModemReset.this);
                    }
                };
        new Handler().postDelayed(this.mDelayRunnable, 20000L);
    }
}
