package com.samsung.android.settings.general;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.ContentProviderClient;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.pm.ProviderInfo;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.PowerManager;
import android.os.RemoteException;
import android.os.UserHandle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityManager;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegateImpl$$ExternalSyntheticOutline0;
import com.android.internal.accessibility.util.ShortcutUtils;
import com.android.settings.DisplaySettings$$ExternalSyntheticOutline0;
import com.android.settings.R;
import com.android.settings.Utils;
import com.samsung.android.feature.SemCscFeature;
import com.samsung.android.settings.Rune;
import com.samsung.android.settings.accessibility.SecAccessibilityUtils;
import com.samsung.android.settings.logging.LoggingHelper;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class ResetSettingsConfirm extends ResetSettingsPreferenceFragment {
    public View mContentView;
    public Context mContext;
    public ProgressDialog mProgressDialog;
    public AnonymousClass1 mResetReceiver;
    public ResetSettingsTask mResetTask;
    public boolean mDatabaseReset = false;
    public boolean mIsAccReset = false;
    public Messenger mService = null;
    public final AnonymousClass2 handler = new AnonymousClass2(this, 0);
    public final AnonymousClass3 mConnection = new ServiceConnection() { // from class: com.samsung.android.settings.general.ResetSettingsConfirm.3
        @Override // android.content.ServiceConnection
        public final void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            ResetSettingsConfirm.this.mService = new Messenger(iBinder);
            Log.d("ResetSettings", "svc connected: " + componentName);
            ResetSettingsConfirm.this.registerAccCallbackToService();
            ResetSettingsConfirm resetSettingsConfirm = ResetSettingsConfirm.this;
            resetSettingsConfirm.getClass();
            try {
                resetSettingsConfirm.mService.send(Message.obtain((Handler) null, 4));
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override // android.content.ServiceConnection
        public final void onServiceDisconnected(ComponentName componentName) {
            ResetSettingsConfirm.this.mService = null;
            Log.d("ResetSettings", "svc disconnected");
        }
    };

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.settings.general.ResetSettingsConfirm$2, reason: invalid class name */
    public final class AnonymousClass2 extends Handler {
        public final /* synthetic */ int $r8$classId;
        public final /* synthetic */ ResetSettingsConfirm this$0;

        public /* synthetic */ AnonymousClass2(ResetSettingsConfirm resetSettingsConfirm, int i) {
            this.$r8$classId = i;
            this.this$0 = resetSettingsConfirm;
        }

        @Override // android.os.Handler
        public final void handleMessage(Message message) {
            switch (this.$r8$classId) {
                case 0:
                    ResetSettingsConfirm resetSettingsConfirm = this.this$0;
                    if (resetSettingsConfirm.mDatabaseReset) {
                        ProgressDialog progressDialog = resetSettingsConfirm.mProgressDialog;
                        if (progressDialog != null) {
                            if (progressDialog.isShowing()) {
                                resetSettingsConfirm.mProgressDialog.dismiss();
                            }
                            resetSettingsConfirm.mProgressDialog = null;
                        }
                        resetSettingsConfirm.mDatabaseReset = false;
                        final Context context = resetSettingsConfirm.mContext;
                        new Timer().schedule(new TimerTask() { // from class: com.samsung.android.settings.general.ResetSettingsConfirm.2.1
                            @Override // java.util.TimerTask, java.lang.Runnable
                            public final void run() {
                                ((PowerManager) context.getSystemService("power")).reboot("reset settings");
                            }
                        }, 100L);
                        break;
                    }
                    break;
                default:
                    if (message.what != 6) {
                        super.handleMessage(message);
                        break;
                    } else {
                        Log.d("ResetSettings", "Acc Reset Complete!");
                        ResetSettingsConfirm resetSettingsConfirm2 = this.this$0;
                        Toast.makeText(resetSettingsConfirm2.mContext, resetSettingsConfirm2.mContext.getResources().getString(R.string.accreset_done_message), 1).show();
                        if (resetSettingsConfirm2.mService != null) {
                            resetSettingsConfirm2.getClass();
                            try {
                                resetSettingsConfirm2.mService.send(Message.obtain((Handler) null, 2));
                                break;
                            } catch (RemoteException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                    break;
            }
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class ResetSettingsTask extends AsyncTask {
        public ResetSettingsTask() {
        }

        @Override // android.os.AsyncTask
        public final Object doInBackground(Object[] objArr) {
            Context[] contextArr = (Context[]) objArr;
            try {
                Log.d("ResetSettings", "doInBackground... proceed reset settings");
                ResetSettingsConfirm.this.resetSettingsExternal(contextArr[0]);
                ResetSettingsConfirm.resetSettingsInternal(contextArr[0]);
                return null;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override // android.os.AsyncTask
        public final void onPostExecute(Object obj) {
            Log.d("ResetSettings", "onPostExecute");
            ResetSettingsConfirm.this.handler.sendEmptyMessageDelayed(1, 3000L);
        }

        @Override // android.os.AsyncTask
        public final void onPreExecute() {
            super.onPreExecute();
        }
    }

    static {
        UserHandle.myUserId();
    }

    /* JADX WARN: Can't wrap try/catch for region: R(16:0|1|(1:3)(1:55)|4|(2:6|(2:8|(10:10|11|12|13|(1:15)(1:49)|16|17|(6:20|21|22|(1:42)(5:24|25|26|27|(3:29|30|31)(1:33))|32|18)|45|46)(11:52|53|11|12|13|(0)(0)|16|17|(1:18)|45|46)))|54|53|11|12|13|(0)(0)|16|17|(1:18)|45|46) */
    /* JADX WARN: Code restructure failed: missing block: B:50:0x0076, code lost:
    
        r2 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:51:0x0077, code lost:
    
        r2.printStackTrace();
     */
    /* JADX WARN: Removed duplicated region for block: B:15:0x0059 A[Catch: Exception -> 0x0076, TryCatch #2 {Exception -> 0x0076, blocks: (B:13:0x0049, B:15:0x0059, B:49:0x006f), top: B:12:0x0049 }] */
    /* JADX WARN: Removed duplicated region for block: B:20:0x0086  */
    /* JADX WARN: Removed duplicated region for block: B:29:0x00ee A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:33:0x0080 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:49:0x006f A[Catch: Exception -> 0x0076, TRY_LEAVE, TryCatch #2 {Exception -> 0x0076, blocks: (B:13:0x0049, B:15:0x0059, B:49:0x006f), top: B:12:0x0049 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static void resetSettingsInternal(android.content.Context r8) {
        /*
            Method dump skipped, instructions count: 246
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.samsung.android.settings.general.ResetSettingsConfirm.resetSettingsInternal(android.content.Context):void");
    }

    public final void doSoftReset() {
        if (!this.mIsAccReset) {
            this.mContext.sendOrderedBroadcast(new Intent("com.samsung.intent.action.SETTINGS_SOFT_RESET").addFlags(16777216), "com.sec.android.settings.permission.SOFT_RESET");
            ProgressDialog progressDialog = new ProgressDialog(this.mContext);
            this.mProgressDialog = progressDialog;
            progressDialog.setMessage(getResources().getString(R.string.settings_reset_boot_device));
            this.mProgressDialog.setCancelable(false);
            this.mProgressDialog.show();
            return;
        }
        Context context = this.mContext;
        Log.d("ResetSettings", "bind resetAccessibilitySettingsOnly");
        this.mContext = context;
        context.getSharedPreferences("accessibility_prefs", 0).edit().putStringSet("user_shortcut_type", Collections.emptySet()).apply();
        Set shortcutTargetsFromSettings = ShortcutUtils.getShortcutTargetsFromSettings(context, 16, UserHandle.myUserId());
        HashSet hashSet = SecAccessibilityUtils.excludeDeviceNameSet;
        AccessibilityManager accessibilityManager = (AccessibilityManager) context.getSystemService(AccessibilityManager.class);
        if (accessibilityManager != null) {
            accessibilityManager.enableShortcutsForTargets(false, 16, shortcutTargetsFromSettings, UserHandle.myUserId());
        }
        if (this.mService == null) {
            context.bindService(DisplaySettings$$ExternalSyntheticOutline0.m("com.samsung.accessibility", "com.samsung.accessibility.datamanager.DataManagerService"), this.mConnection, 1);
            return;
        }
        registerAccCallbackToService();
        try {
            this.mService.send(Message.obtain((Handler) null, 4));
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 4661;
    }

    @Override // com.samsung.android.settings.general.ResetSettingsPreferenceFragment
    public final String getResetButtonTitle() {
        return getString(this.mIsAccReset ? R.string.reset : R.string.main_clear_button_text);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v0, types: [android.content.BroadcastReceiver, com.samsung.android.settings.general.ResetSettingsConfirm$1] */
    @Override // com.android.settings.SettingsPreferenceFragment, androidx.fragment.app.Fragment
    public final void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);
        this.mContext = getActivity();
        if (this.mIsAccReset) {
            ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(R.string.sec_reset_accessibility_title);
        }
        IntentFilter m = AppCompatDelegateImpl$$ExternalSyntheticOutline0.m("com.samsung.intent.action.SETTINGS_SOFT_RESET_COMPLETED");
        ?? r1 = new BroadcastReceiver() { // from class: com.samsung.android.settings.general.ResetSettingsConfirm.1
            @Override // android.content.BroadcastReceiver
            public final void onReceive(Context context, Intent intent) {
                if (intent.getAction().equals("com.samsung.intent.action.SETTINGS_SOFT_RESET_COMPLETED")) {
                    ResetSettingsConfirm resetSettingsConfirm = ResetSettingsConfirm.this;
                    resetSettingsConfirm.mDatabaseReset = true;
                    resetSettingsConfirm.mResetTask = ResetSettingsConfirm.this.new ResetSettingsTask();
                    ResetSettingsConfirm.this.mResetTask.execute(context);
                }
            }
        };
        this.mResetReceiver = r1;
        this.mContext.registerReceiver(r1, m, "com.sec.android.settings.permission.SOFT_RESET", null, 2);
        String str = Rune.COMMON_CONFIG_PACKAGE_NAME_SMART_MANAGER;
        if ("VZW".equals(Utils.getSalesCode()) && SemCscFeature.getInstance().getBoolean("CscFeature_Common_SupportHuxDeviceQualityStatistics")) {
            Intent intent = new Intent("com.sec.android.statistics.VZW_QUALITY_STATISTICS");
            intent.putExtra("event_type", "Q025");
            this.mContext.sendBroadcast(intent);
        }
    }

    @Override // androidx.fragment.app.Fragment
    public final void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        if ((i == 55 || i == 58) && i2 == -1) {
            doSoftReset();
        }
    }

    @Override // com.android.settings.SettingsPreferenceFragment, com.android.settings.core.InstrumentedPreferenceFragment, com.android.settingslib.core.lifecycle.ObservablePreferenceFragment, androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.mIsAccReset = getArguments().getBoolean("extra_accsettings", false);
    }

    @Override // com.android.settings.SettingsPreferenceFragment, androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        if (this.mIsAccReset) {
            this.mContentView = layoutInflater.inflate(R.layout.sec_reset_accsettings_confirm, (ViewGroup) null);
        } else {
            this.mContentView = layoutInflater.inflate(R.layout.sec_reset_settings_confirm, (ViewGroup) null);
        }
        return this.mContentView;
    }

    @Override // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onDestroyView() {
        ProgressDialog progressDialog = this.mProgressDialog;
        if (progressDialog != null) {
            if (progressDialog.isShowing()) {
                this.mProgressDialog.dismiss();
            }
            this.mProgressDialog = null;
        }
        this.mContext.unregisterReceiver(this.mResetReceiver);
        super.onDestroyView();
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment, com.android.settingslib.core.lifecycle.ObservablePreferenceFragment, androidx.fragment.app.Fragment
    public final void onPause() {
        super.onPause();
        if (this.mService != null) {
            this.mContext.unbindService(this.mConnection);
            this.mService = null;
        }
    }

    @Override // com.samsung.android.settings.general.ResetSettingsPreferenceFragment
    public final void onResetButtonClick() {
        if (this.mIsAccReset) {
            LoggingHelper.insertEventLogging(4661, 4667, 0L);
        } else {
            LoggingHelper.insertEventLogging(4661, 4662, 0L);
        }
        doSoftReset();
    }

    public final void registerAccCallbackToService() {
        try {
            Message obtain = Message.obtain((Handler) null, 1);
            obtain.replyTo = new Messenger(new AnonymousClass2(this, 1));
            this.mService.send(obtain);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public final void resetSettingsExternal(Context context) {
        ContentProviderClient contentProviderClient;
        ArrayList arrayList = new ArrayList();
        List<ResolveInfo> queryIntentContentProviders = this.mContext.getPackageManager().queryIntentContentProviders(new Intent("com.samsung.action.RESET_SETTINGS"), 0);
        ContentProviderClient contentProviderClient2 = null;
        if (queryIntentContentProviders == null || queryIntentContentProviders.isEmpty()) {
            arrayList = null;
        } else {
            for (int i = 0; i < queryIntentContentProviders.size(); i++) {
                ProviderInfo providerInfo = queryIntentContentProviders.get(i).providerInfo;
                if (providerInfo != null && !TextUtils.isEmpty(providerInfo.authority)) {
                    arrayList.add(new Uri.Builder().scheme("content").authority(providerInfo.authority).build());
                }
            }
        }
        if (arrayList == null) {
            return;
        }
        ContentResolver contentResolver = context.getContentResolver();
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            Uri uri = (Uri) it.next();
            try {
                contentProviderClient = contentResolver.acquireUnstableContentProviderClient(uri);
            } catch (RemoteException e) {
                e = e;
                contentProviderClient = null;
            } catch (Throwable th) {
                th = th;
            }
            try {
                try {
                    Log.d("ResetSettings", "ResetSettings Provider Call, URI = " + uri);
                } catch (Throwable th2) {
                    th = th2;
                    contentProviderClient2 = contentProviderClient;
                    if (contentProviderClient2 != null) {
                        contentProviderClient2.close();
                    }
                    throw th;
                }
            } catch (RemoteException e2) {
                e = e2;
                Log.d("ResetSettings", "can not call the provider");
                e.printStackTrace();
                if (contentProviderClient != null) {
                    contentProviderClient.close();
                }
            }
            if (contentProviderClient == null) {
                if (contentProviderClient != null) {
                    contentProviderClient.close();
                    return;
                }
                return;
            } else {
                if (contentProviderClient.call("reset_data_settings", null, null) != null) {
                    Log.d("ResetSettings", "getBundle");
                } else {
                    Log.d("ResetSettings", "Bundle is null");
                }
                contentProviderClient.close();
            }
        }
    }
}
