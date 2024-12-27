package com.samsung.android.settings.deviceinfo.statusinfo.simlockstatus;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemProperties;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.appcompat.widget.ActionBarContextView$$ExternalSyntheticOutline0;
import androidx.fragment.app.FragmentActivity;
import androidx.mediarouter.app.MediaRouteControllerDialog$VolumeGroupAdapter$$ExternalSyntheticOutline0;

import com.android.settings.DisplaySettings$$ExternalSyntheticOutline0;
import com.android.settings.R;
import com.android.settings.SettingsPreferenceFragment;
import com.android.settings.Utils$$ExternalSyntheticOutline0;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.settings.Rune;
import com.sec.ims.configuration.DATA;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.nio.ByteBuffer;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class SimLockStatus extends SettingsPreferenceFragment implements View.OnClickListener {
    public final AnonymousClass6 mChangeUserName1Listener;
    public final AnonymousClass6 mChangeUserName2Listener;
    public final AnonymousClass6 mChangeUserName3Listener;
    public FragmentActivity mContext;
    public final AnonymousClass6 mNormalTestListener;
    public AlertDialog mRebootDialog;
    public Resources mRes;
    public SamsungRilConnector mSamsungRilConnector;
    public ByteBuffer mSimLockPolicyBuffer;
    public String mSimState;
    public AlertDialog mTestDialog;
    public Button mUpdateBtn;
    public ProgressDialog mUpdateDialog;
    public String mSimCode = "-";
    public final AnonymousClass1 mHandler =
            new Handler() { // from class:
                            // com.samsung.android.settings.deviceinfo.statusinfo.simlockstatus.SimLockStatus.1
                @Override // android.os.Handler
                public final void handleMessage(Message message) {
                    if (message.what != 1) {
                        return;
                    }
                    Log.d("SimLockStatus", "MESSAGE_UPDATE_LIST -> updateSimStatus()");
                    SimLockStatus.this.updateSimStatus();
                }
            };

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.settings.deviceinfo.statusinfo.simlockstatus.SimLockStatus$3, reason: invalid class name */
    public final class AnonymousClass3 implements Runnable {
        public AnonymousClass3() {}

        @Override // java.lang.Runnable
        public final void run() {
            if ("eng".equals(Build.TYPE)) {
                SystemProperties.set("persist.sys.shutdown", "SCSR");
            }
            Intent intent = new Intent("com.android.internal.intent.action.REQUEST_SHUTDOWN");
            intent.setAction("android.intent.action.REBOOT");
            intent.putExtra("android.intent.extra.KEY_CONFIRM", false);
            intent.putExtra(
                    "android.intent.extra.REBOOT_REASON",
                    "[SecSettings] SIM card has been updated");
            intent.setFlags(268435456);
            try {
                SimLockStatus.this.mContext.startActivity(intent);
            } catch (ActivityNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class SimLockUpdateTask extends AsyncTask {
        public KddiHttpsUrlConnection mKddiConnection;

        public SimLockUpdateTask() {}

        /* JADX WARN: Removed duplicated region for block: B:40:0x011f  */
        /* JADX WARN: Removed duplicated region for block: B:43:0x0125 A[EXC_TOP_SPLITTER, SYNTHETIC] */
        @Override // android.os.AsyncTask
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final java.lang.Object doInBackground(java.lang.Object[] r12) {
            /*
                Method dump skipped, instructions count: 297
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException(
                    "Method not decompiled:"
                        + " com.samsung.android.settings.deviceinfo.statusinfo.simlockstatus.SimLockStatus.SimLockUpdateTask.doInBackground(java.lang.Object[]):java.lang.Object");
        }

        @Override // android.os.AsyncTask
        public final void onPostExecute(Object obj) {
            Boolean bool = (Boolean) obj;
            ProgressDialog progressDialog = SimLockStatus.this.mUpdateDialog;
            if (progressDialog != null && progressDialog.isShowing()) {
                SimLockStatus.this.removeDialog(1);
            }
            if (bool.booleanValue()) {
                SimLockStatus.this.showDialog(2);
            } else {
                SimLockStatus.this.showDialog(3);
            }
        }
    }

    /* JADX WARN: Type inference failed for: r0v1, types: [com.samsung.android.settings.deviceinfo.statusinfo.simlockstatus.SimLockStatus$1] */
    /* JADX WARN: Type inference failed for: r0v2, types: [com.samsung.android.settings.deviceinfo.statusinfo.simlockstatus.SimLockStatus$6] */
    /* JADX WARN: Type inference failed for: r0v3, types: [com.samsung.android.settings.deviceinfo.statusinfo.simlockstatus.SimLockStatus$6] */
    /* JADX WARN: Type inference failed for: r0v4, types: [com.samsung.android.settings.deviceinfo.statusinfo.simlockstatus.SimLockStatus$6] */
    /* JADX WARN: Type inference failed for: r0v5, types: [com.samsung.android.settings.deviceinfo.statusinfo.simlockstatus.SimLockStatus$6] */
    public SimLockStatus() {
        final int i = 0;
        this.mNormalTestListener =
                new View.OnClickListener(
                        this) { // from class:
                                // com.samsung.android.settings.deviceinfo.statusinfo.simlockstatus.SimLockStatus.6
                    public final /* synthetic */ SimLockStatus this$0;

                    {
                        this.this$0 = this;
                    }

                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        switch (i) {
                            case 0:
                                SystemProperties.set(
                                        "persist.sys.kddi_sim_lock",
                                        DATA.DM_FIELD_INDEX.PCSCF_DOMAIN);
                                AlertDialog alertDialog = this.this$0.mTestDialog;
                                if (alertDialog != null) {
                                    alertDialog.dismiss();
                                }
                                this.this$0.startMainSIMLock();
                                break;
                            case 1:
                                SystemProperties.set("persist.sys.kddi_sim_lock", "1");
                                Settings.System.putInt(
                                        this.this$0.getContentResolver(), "kddi_sim_lock_test", 1);
                                AlertDialog alertDialog2 = this.this$0.mTestDialog;
                                if (alertDialog2 != null) {
                                    alertDialog2.dismiss();
                                }
                                this.this$0.startMainSIMLock();
                                break;
                            case 2:
                                SystemProperties.set("persist.sys.kddi_sim_lock", "2");
                                Settings.System.putInt(
                                        this.this$0.getContentResolver(), "kddi_sim_lock_test", 2);
                                AlertDialog alertDialog3 = this.this$0.mTestDialog;
                                if (alertDialog3 != null) {
                                    alertDialog3.dismiss();
                                }
                                this.this$0.startMainSIMLock();
                                break;
                            default:
                                SystemProperties.set(
                                        "persist.sys.kddi_sim_lock",
                                        DATA.DM_FIELD_INDEX.PUBLIC_USER_ID);
                                Settings.System.putInt(
                                        this.this$0.getContentResolver(), "kddi_sim_lock_test", 3);
                                AlertDialog alertDialog4 = this.this$0.mTestDialog;
                                if (alertDialog4 != null) {
                                    alertDialog4.dismiss();
                                }
                                this.this$0.startMainSIMLock();
                                break;
                        }
                    }
                };
        final int i2 = 1;
        this.mChangeUserName1Listener =
                new View.OnClickListener(
                        this) { // from class:
                                // com.samsung.android.settings.deviceinfo.statusinfo.simlockstatus.SimLockStatus.6
                    public final /* synthetic */ SimLockStatus this$0;

                    {
                        this.this$0 = this;
                    }

                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        switch (i2) {
                            case 0:
                                SystemProperties.set(
                                        "persist.sys.kddi_sim_lock",
                                        DATA.DM_FIELD_INDEX.PCSCF_DOMAIN);
                                AlertDialog alertDialog = this.this$0.mTestDialog;
                                if (alertDialog != null) {
                                    alertDialog.dismiss();
                                }
                                this.this$0.startMainSIMLock();
                                break;
                            case 1:
                                SystemProperties.set("persist.sys.kddi_sim_lock", "1");
                                Settings.System.putInt(
                                        this.this$0.getContentResolver(), "kddi_sim_lock_test", 1);
                                AlertDialog alertDialog2 = this.this$0.mTestDialog;
                                if (alertDialog2 != null) {
                                    alertDialog2.dismiss();
                                }
                                this.this$0.startMainSIMLock();
                                break;
                            case 2:
                                SystemProperties.set("persist.sys.kddi_sim_lock", "2");
                                Settings.System.putInt(
                                        this.this$0.getContentResolver(), "kddi_sim_lock_test", 2);
                                AlertDialog alertDialog3 = this.this$0.mTestDialog;
                                if (alertDialog3 != null) {
                                    alertDialog3.dismiss();
                                }
                                this.this$0.startMainSIMLock();
                                break;
                            default:
                                SystemProperties.set(
                                        "persist.sys.kddi_sim_lock",
                                        DATA.DM_FIELD_INDEX.PUBLIC_USER_ID);
                                Settings.System.putInt(
                                        this.this$0.getContentResolver(), "kddi_sim_lock_test", 3);
                                AlertDialog alertDialog4 = this.this$0.mTestDialog;
                                if (alertDialog4 != null) {
                                    alertDialog4.dismiss();
                                }
                                this.this$0.startMainSIMLock();
                                break;
                        }
                    }
                };
        final int i3 = 2;
        this.mChangeUserName2Listener =
                new View.OnClickListener(
                        this) { // from class:
                                // com.samsung.android.settings.deviceinfo.statusinfo.simlockstatus.SimLockStatus.6
                    public final /* synthetic */ SimLockStatus this$0;

                    {
                        this.this$0 = this;
                    }

                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        switch (i3) {
                            case 0:
                                SystemProperties.set(
                                        "persist.sys.kddi_sim_lock",
                                        DATA.DM_FIELD_INDEX.PCSCF_DOMAIN);
                                AlertDialog alertDialog = this.this$0.mTestDialog;
                                if (alertDialog != null) {
                                    alertDialog.dismiss();
                                }
                                this.this$0.startMainSIMLock();
                                break;
                            case 1:
                                SystemProperties.set("persist.sys.kddi_sim_lock", "1");
                                Settings.System.putInt(
                                        this.this$0.getContentResolver(), "kddi_sim_lock_test", 1);
                                AlertDialog alertDialog2 = this.this$0.mTestDialog;
                                if (alertDialog2 != null) {
                                    alertDialog2.dismiss();
                                }
                                this.this$0.startMainSIMLock();
                                break;
                            case 2:
                                SystemProperties.set("persist.sys.kddi_sim_lock", "2");
                                Settings.System.putInt(
                                        this.this$0.getContentResolver(), "kddi_sim_lock_test", 2);
                                AlertDialog alertDialog3 = this.this$0.mTestDialog;
                                if (alertDialog3 != null) {
                                    alertDialog3.dismiss();
                                }
                                this.this$0.startMainSIMLock();
                                break;
                            default:
                                SystemProperties.set(
                                        "persist.sys.kddi_sim_lock",
                                        DATA.DM_FIELD_INDEX.PUBLIC_USER_ID);
                                Settings.System.putInt(
                                        this.this$0.getContentResolver(), "kddi_sim_lock_test", 3);
                                AlertDialog alertDialog4 = this.this$0.mTestDialog;
                                if (alertDialog4 != null) {
                                    alertDialog4.dismiss();
                                }
                                this.this$0.startMainSIMLock();
                                break;
                        }
                    }
                };
        final int i4 = 3;
        this.mChangeUserName3Listener =
                new View.OnClickListener(
                        this) { // from class:
                                // com.samsung.android.settings.deviceinfo.statusinfo.simlockstatus.SimLockStatus.6
                    public final /* synthetic */ SimLockStatus this$0;

                    {
                        this.this$0 = this;
                    }

                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        switch (i4) {
                            case 0:
                                SystemProperties.set(
                                        "persist.sys.kddi_sim_lock",
                                        DATA.DM_FIELD_INDEX.PCSCF_DOMAIN);
                                AlertDialog alertDialog = this.this$0.mTestDialog;
                                if (alertDialog != null) {
                                    alertDialog.dismiss();
                                }
                                this.this$0.startMainSIMLock();
                                break;
                            case 1:
                                SystemProperties.set("persist.sys.kddi_sim_lock", "1");
                                Settings.System.putInt(
                                        this.this$0.getContentResolver(), "kddi_sim_lock_test", 1);
                                AlertDialog alertDialog2 = this.this$0.mTestDialog;
                                if (alertDialog2 != null) {
                                    alertDialog2.dismiss();
                                }
                                this.this$0.startMainSIMLock();
                                break;
                            case 2:
                                SystemProperties.set("persist.sys.kddi_sim_lock", "2");
                                Settings.System.putInt(
                                        this.this$0.getContentResolver(), "kddi_sim_lock_test", 2);
                                AlertDialog alertDialog3 = this.this$0.mTestDialog;
                                if (alertDialog3 != null) {
                                    alertDialog3.dismiss();
                                }
                                this.this$0.startMainSIMLock();
                                break;
                            default:
                                SystemProperties.set(
                                        "persist.sys.kddi_sim_lock",
                                        DATA.DM_FIELD_INDEX.PUBLIC_USER_ID);
                                Settings.System.putInt(
                                        this.this$0.getContentResolver(), "kddi_sim_lock_test", 3);
                                AlertDialog alertDialog4 = this.this$0.mTestDialog;
                                if (alertDialog4 != null) {
                                    alertDialog4.dismiss();
                                }
                                this.this$0.startMainSIMLock();
                                break;
                        }
                    }
                };
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 44;
    }

    @Override // android.view.View.OnClickListener
    public final void onClick(View view) {
        if ("eng".equals(Build.TYPE)) {
            Log.i("SimLockStatus", "SimCardStatus onClick eng");
            showDialog(5);
        } else {
            Log.i("SimLockStatus", "SimCardStatus onClick user");
            startMainSIMLock();
        }
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        Log.i("SimLockStatus", "onCreate()");
        String str = Rune.COMMON_CONFIG_PACKAGE_NAME_SMART_MANAGER;
        String str2 = SystemProperties.get("ro.product.device", ApnSettings.MVNO_NONE);
        if (!"SCG09".equalsIgnoreCase(str2) && !"SCG10".equalsIgnoreCase(str2)) {
            Log.i("SimLockStatus", "Unexpected sales code");
            finish();
            return;
        }
        addPreferencesFromResource(R.xml.sec_device_info_sim_lock_status_kdi);
        this.mContext = getActivity();
        this.mRes = getResources();
        SamsungRilConnector samsungRilConnector = SamsungRilConnector.getInstance(this.mContext);
        this.mSamsungRilConnector = samsungRilConnector;
        samsungRilConnector.mListenerRef = new WeakReference(this);
        SamsungRilConnector samsungRilConnector2 = this.mSamsungRilConnector;
        samsungRilConnector2.getClass();
        Log.d("SamsungRilConnector", "connect() => connect to the RIL service");
        if (samsungRilConnector2.mIsConnected) {
            Log.d("SamsungRilConnector", "already connected");
            return;
        }
        Intent m =
                DisplaySettings$$ExternalSyntheticOutline0.m(
                        "com.sec.phone", "com.sec.phone.SecPhoneService");
        Context context = samsungRilConnector2.mContext;
        if (context == null) {
            Log.w("SamsungRilConnector", "Context is null. ignore");
        } else {
            context.bindService(m, samsungRilConnector2.mRilServiceConnection, 1);
        }
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.DialogCreatable
    public final Dialog onCreateDialog(int i) {
        switch (i) {
            case 1:
                ProgressDialog progressDialog = new ProgressDialog(this.mContext);
                this.mUpdateDialog = progressDialog;
                progressDialog.setTitle(
                        this.mRes.getString(R.string.sec_sim_lock_status_btn_update));
                this.mUpdateDialog.setMessage(
                        this.mRes.getString(R.string.sec_sim_lock_status_updating));
                return this.mUpdateDialog;
            case 2:
                final int i2 = 0;
                return new AlertDialog.Builder(this.mContext)
                        .setTitle(R.string.sec_sim_lock_status_btn_update)
                        .setMessage(R.string.sec_sim_lock_status_policy_file_download)
                        .setPositiveButton(
                                R.string.sec_sim_lock_status_download,
                                new DialogInterface.OnClickListener(
                                        this) { // from class:
                                                // com.samsung.android.settings.deviceinfo.statusinfo.simlockstatus.SimLockStatus.4
                                    public final /* synthetic */ SimLockStatus this$0;

                                    {
                                        this.this$0 = this;
                                    }

                                    @Override // android.content.DialogInterface.OnClickListener
                                    public final void onClick(
                                            DialogInterface dialogInterface, int i3) {
                                        switch (i2) {
                                            case 0:
                                                Log.d("SimLockStatus", "Download policy file");
                                                SamsungRilConnector samsungRilConnector =
                                                        SamsungRilConnector.getInstance(
                                                                this.this$0.mContext);
                                                byte[] array =
                                                        this.this$0.mSimLockPolicyBuffer.array();
                                                Log.d("SamsungRilConnector", "setBlob()");
                                                if (!samsungRilConnector.mIsConnected) {
                                                    Log.w(
                                                            "SamsungRilConnector",
                                                            "not connected to RIL yet");
                                                    return;
                                                }
                                                if (array == null || array.length <= 0) {
                                                    Log.w(
                                                            "SamsungRilConnector",
                                                            "ignore invalid blob");
                                                    return;
                                                }
                                                ByteArrayOutputStream byteArrayOutputStream =
                                                        new ByteArrayOutputStream();
                                                DataOutputStream dataOutputStream =
                                                        new DataOutputStream(byteArrayOutputStream);
                                                int length = array.length + 7;
                                                try {
                                                    try {
                                                        dataOutputStream.writeByte(35);
                                                        dataOutputStream.writeByte(5);
                                                        dataOutputStream.writeShort(length);
                                                        dataOutputStream.writeByte(2);
                                                        dataOutputStream.writeShort(array.length);
                                                        dataOutputStream.write(array);
                                                        samsungRilConnector.invokeRilService(
                                                                byteArrayOutputStream.toByteArray(),
                                                                samsungRilConnector.mMessageHandler
                                                                        .obtainMessage(1));
                                                        SimLockStatusUtils.closeSilently(
                                                                dataOutputStream,
                                                                byteArrayOutputStream);
                                                        return;
                                                    } catch (IOException e) {
                                                        e.printStackTrace();
                                                        SimLockStatusUtils.closeSilently(
                                                                dataOutputStream,
                                                                byteArrayOutputStream);
                                                        return;
                                                    }
                                                } catch (Throwable th) {
                                                    SimLockStatusUtils.closeSilently(
                                                            dataOutputStream,
                                                            byteArrayOutputStream);
                                                    throw th;
                                                }
                                            default:
                                                SimLockStatus simLockStatus = this.this$0;
                                                simLockStatus.getClass();
                                                Log.w("SimLockStatus", "Device reboot!");
                                                new Handler()
                                                        .postDelayed(
                                                                simLockStatus.new AnonymousClass3(),
                                                                1500L);
                                                return;
                                        }
                                    }
                                })
                        .setNegativeButton(
                                R.string.sec_sim_lock_status_later,
                                (DialogInterface.OnClickListener) null)
                        .create();
            case 3:
                return new AlertDialog.Builder(this.mContext)
                        .setTitle(R.string.sec_sim_lock_status_connect_error)
                        .setMessage(R.string.sec_sim_lock_status_update_failed)
                        .setPositiveButton(
                                android.R.string.ok, (DialogInterface.OnClickListener) null)
                        .create();
            case 4:
                AlertDialog.Builder builder = new AlertDialog.Builder(this.mContext);
                builder.setTitle(R.string.sec_sim_lock_status_reboot_title);
                builder.setMessage(R.string.sec_sim_lock_status_reboot_msg);
                final int i3 = 1;
                builder.setPositiveButton(
                        android.R.string.ok,
                        new DialogInterface.OnClickListener(
                                this) { // from class:
                                        // com.samsung.android.settings.deviceinfo.statusinfo.simlockstatus.SimLockStatus.4
                            public final /* synthetic */ SimLockStatus this$0;

                            {
                                this.this$0 = this;
                            }

                            @Override // android.content.DialogInterface.OnClickListener
                            public final void onClick(DialogInterface dialogInterface, int i32) {
                                switch (i3) {
                                    case 0:
                                        Log.d("SimLockStatus", "Download policy file");
                                        SamsungRilConnector samsungRilConnector =
                                                SamsungRilConnector.getInstance(
                                                        this.this$0.mContext);
                                        byte[] array = this.this$0.mSimLockPolicyBuffer.array();
                                        Log.d("SamsungRilConnector", "setBlob()");
                                        if (!samsungRilConnector.mIsConnected) {
                                            Log.w(
                                                    "SamsungRilConnector",
                                                    "not connected to RIL yet");
                                            return;
                                        }
                                        if (array == null || array.length <= 0) {
                                            Log.w("SamsungRilConnector", "ignore invalid blob");
                                            return;
                                        }
                                        ByteArrayOutputStream byteArrayOutputStream =
                                                new ByteArrayOutputStream();
                                        DataOutputStream dataOutputStream =
                                                new DataOutputStream(byteArrayOutputStream);
                                        int length = array.length + 7;
                                        try {
                                            try {
                                                dataOutputStream.writeByte(35);
                                                dataOutputStream.writeByte(5);
                                                dataOutputStream.writeShort(length);
                                                dataOutputStream.writeByte(2);
                                                dataOutputStream.writeShort(array.length);
                                                dataOutputStream.write(array);
                                                samsungRilConnector.invokeRilService(
                                                        byteArrayOutputStream.toByteArray(),
                                                        samsungRilConnector.mMessageHandler
                                                                .obtainMessage(1));
                                                SimLockStatusUtils.closeSilently(
                                                        dataOutputStream, byteArrayOutputStream);
                                                return;
                                            } catch (IOException e) {
                                                e.printStackTrace();
                                                SimLockStatusUtils.closeSilently(
                                                        dataOutputStream, byteArrayOutputStream);
                                                return;
                                            }
                                        } catch (Throwable th) {
                                            SimLockStatusUtils.closeSilently(
                                                    dataOutputStream, byteArrayOutputStream);
                                            throw th;
                                        }
                                    default:
                                        SimLockStatus simLockStatus = this.this$0;
                                        simLockStatus.getClass();
                                        Log.w("SimLockStatus", "Device reboot!");
                                        new Handler()
                                                .postDelayed(
                                                        simLockStatus.new AnonymousClass3(), 1500L);
                                        return;
                                }
                            }
                        });
                AlertDialog create = builder.create();
                this.mRebootDialog = create;
                return create;
            case 5:
                View inflate =
                        getActivity()
                                .getLayoutInflater()
                                .inflate(
                                        R.layout.sec_device_info_sim_lock_status_kdi_test,
                                        (ViewGroup) null);
                AlertDialog.Builder builder2 = new AlertDialog.Builder(this.mContext);
                ((LinearLayout) inflate.findViewById(R.id.normal_test))
                        .setOnClickListener(this.mNormalTestListener);
                ((LinearLayout) inflate.findViewById(R.id.chage_username1))
                        .setOnClickListener(this.mChangeUserName1Listener);
                ((LinearLayout) inflate.findViewById(R.id.chage_username2))
                        .setOnClickListener(this.mChangeUserName2Listener);
                ((LinearLayout) inflate.findViewById(R.id.chage_username3))
                        .setOnClickListener(this.mChangeUserName3Listener);
                builder2.setView(inflate);
                builder2.setTitle(R.string.sim_status_title);
                builder2.setNegativeButton(
                        android.R.string.cancel, (DialogInterface.OnClickListener) null);
                AlertDialog create2 = builder2.create();
                this.mTestDialog = create2;
                return create2;
            case 6:
                return new AlertDialog.Builder(this.mContext)
                        .setTitle(R.string.sec_sim_lock_status_connect_error)
                        .setMessage(R.string.sec_sim_lock_status_allshare_popup_wifi_connect)
                        .setPositiveButton(
                                android.R.string.ok, (DialogInterface.OnClickListener) null)
                        .create();
            default:
                return null;
        }
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final View onCreateView(
            LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View onCreateView = super.onCreateView(layoutInflater, viewGroup, bundle);
        ViewGroup viewGroup2 = (ViewGroup) onCreateView.findViewById(R.id.container_material);
        View inflate =
                layoutInflater.inflate(
                        R.layout.sec_device_info_sim_lock_status_kdi, viewGroup2, false);
        viewGroup2.addView(inflate);
        Button button = (Button) inflate.findViewById(R.id.button);
        this.mUpdateBtn = button;
        button.setOnClickListener(this);
        return onCreateView;
    }

    @Override // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onDestroy() {
        super.onDestroy();
        SamsungRilConnector samsungRilConnector = this.mSamsungRilConnector;
        if (samsungRilConnector != null) {
            ActionBarContextView$$ExternalSyntheticOutline0.m(
                    new StringBuilder("disconnect() => mIsConnected: "),
                    samsungRilConnector.mIsConnected,
                    "SamsungRilConnector");
            if (samsungRilConnector.mIsConnected) {
                samsungRilConnector.mContext.unbindService(
                        samsungRilConnector.mRilServiceConnection);
                samsungRilConnector.mIsConnected = false;
            }
        }
    }

    @Override // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() != 16908332) {
            return super.onOptionsItemSelected(menuItem);
        }
        Log.d("SimLockStatus", "onOptionsItemSelected() up button pressed");
        finish();
        return true;
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onPause() {
        super.onPause();
        Log.i("SimLockStatus", "onPause()");
        AlertDialog alertDialog = this.mRebootDialog;
        if (alertDialog == null || !alertDialog.isShowing()) {
            return;
        }
        Log.w("SimLockStatus", "Device reboot!");
        new Handler().postDelayed(new AnonymousClass3(), 1500L);
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onResume() {
        super.onResume();
        Log.i("SimLockStatus", "onResume()");
        updateSimStatus();
    }

    public final void startMainSIMLock() {
        if (!((ConnectivityManager) this.mContext.getSystemService("connectivity"))
                .getNetworkInfo(1)
                .isConnected()) {
            showDialog(6);
        } else {
            new SimLockUpdateTask().execute(0);
            showDialog(1);
        }
    }

    public final void updateSimStatus() {
        this.mSimState = SystemProperties.get("gsm.sim.state");
        this.mSimCode = this.mSamsungRilConnector.mOperationName;
        StringBuilder sb = new StringBuilder("updateSimStatus() => mSimState: ");
        sb.append(this.mSimState);
        sb.append(", mSimCode: ");
        Utils$$ExternalSyntheticOutline0.m(sb, this.mSimCode, "SimLockStatus");
        if ("ABSENT".equals(this.mSimState)) {
            this.mSimCode = "-";
        }
        findPreference("sim_state_status")
                .setSummary(
                        "ABSENT".equals(this.mSimState)
                                ? "-"
                                : ("READY".equals(this.mSimState)
                                                || "LOADED".equals(this.mSimState))
                                        ? this.mRes.getString(
                                                R.string.sec_sim_lock_status_sim_state)
                                        : "UNKNOWN".equals(this.mSimState)
                                                ? this.mRes.getString(R.string.device_info_default)
                                                : this.mRes.getString(
                                                        R.string.sec_sim_lock_status_not_allowed));
        findPreference("sim_state_code").setSummary(this.mSimCode);
        Button button = this.mUpdateBtn;
        if (button != null) {
            int simState =
                    ((TelephonyManager) this.mContext.getSystemService("phone")).getSimState();
            MediaRouteControllerDialog$VolumeGroupAdapter$$ExternalSyntheticOutline0.m(
                    simState, "sim state : ", " / 1", "SimLockStatus");
            button.setEnabled(
                    (simState == 1
                                    || "READY".equals(this.mSimState)
                                    || "LOADED".equals(this.mSimState))
                            ? false
                            : true);
        }
    }
}
