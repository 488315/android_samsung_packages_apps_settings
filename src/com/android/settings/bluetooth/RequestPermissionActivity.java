package com.android.settings.bluetooth;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDump;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.UserHandle;
import android.text.TextUtils;
import android.util.Log;
import androidx.appcompat.app.AlertController;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.Preference$$ExternalSyntheticOutline0;
import com.android.settings.R;
import com.android.settings.bluetooth.RequestPermissionActivity;
import com.android.settings.bluetooth.RequestPermissionActivity.StateChangeReceiver;
import com.android.settingslib.bluetooth.BluetoothDiscoverableTimeoutReceiver;
import com.android.settingslib.spaprivileged.framework.common.ContextsKt;
import com.samsung.android.knox.net.nap.NetworkAnalyticsConstants;
import java.time.Duration;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class RequestPermissionActivity extends AppCompatActivity implements DialogInterface.OnClickListener, DialogInterface.OnDismissListener {
    public static final /* synthetic */ int $r8$clinit = 0;
    public CharSequence mAppLabel;
    public BluetoothAdapter mBluetoothAdapter;
    public int mCallingUid;
    public AlertDialog mDialog;
    public String mPackageName;
    public StateChangeReceiver mReceiver;
    public int mRequest;
    public AlertDialog mRequestDialog;
    public int mTimeout = 120;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class StateChangeReceiver extends BroadcastReceiver {
        public StateChangeReceiver() {
            RequestPermissionActivity.this.getWindow().getDecorView().postDelayed(new Runnable() { // from class: com.android.settings.bluetooth.RequestPermissionActivity$StateChangeReceiver$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    RequestPermissionActivity.StateChangeReceiver stateChangeReceiver = RequestPermissionActivity.StateChangeReceiver.this;
                    if (RequestPermissionActivity.this.isFinishing() || RequestPermissionActivity.this.isDestroyed()) {
                        return;
                    }
                    RequestPermissionActivity requestPermissionActivity = RequestPermissionActivity.this;
                    int i = RequestPermissionActivity.$r8$clinit;
                    requestPermissionActivity.cancelAndFinish();
                }
            }, 10000L);
        }

        @Override // android.content.BroadcastReceiver
        public final void onReceive(Context context, Intent intent) {
            if (intent == null) {
                return;
            }
            int intExtra = intent.getIntExtra("android.bluetooth.adapter.extra.STATE", Integer.MIN_VALUE);
            RequestPermissionActivity requestPermissionActivity = RequestPermissionActivity.this;
            int i = requestPermissionActivity.mRequest;
            if (i == 1 || i == 2) {
                if (intExtra == 12) {
                    requestPermissionActivity.proceedAndFinish();
                }
            } else if (i == 3 && intExtra == 10) {
                requestPermissionActivity.proceedAndFinish();
            }
        }
    }

    public final void cancelAndFinish() {
        setResult(0);
        finish();
    }

    public final void createDialog$1() {
        if (getResources().getBoolean(R.bool.auto_confirm_bluetooth_activation_dialog)) {
            onClick(null, -1);
            return;
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        StateChangeReceiver stateChangeReceiver = this.mReceiver;
        AlertController.AlertParams alertParams = builder.P;
        if (stateChangeReceiver != null) {
            int i = this.mRequest;
            if (i == 1 || i == 2) {
                alertParams.mMessage = getString(R.string.bluetooth_turning_on);
            } else {
                alertParams.mMessage = getString(R.string.bluetooth_turning_off);
            }
            alertParams.mCancelable = false;
        } else {
            int i2 = this.mTimeout;
            if (i2 == 0) {
                CharSequence charSequence = this.mAppLabel;
                alertParams.mMessage = charSequence != null ? getString(R.string.sec_bluetooth_ask_lasting_discovery, new Object[]{charSequence}) : getString(R.string.bluetooth_ask_lasting_discovery_no_name);
            } else {
                CharSequence charSequence2 = this.mAppLabel;
                alertParams.mMessage = charSequence2 != null ? getString(R.string.sec_bluetooth_ask_discovery, new Object[]{charSequence2, Integer.valueOf(i2)}) : getString(R.string.bluetooth_ask_discovery_no_name, new Object[]{Integer.valueOf(i2)});
            }
            builder.setPositiveButton(getString(R.string.allow), this);
            builder.setNegativeButton(getString(R.string.deny), this);
            alertParams.mOnCancelListener = new DialogInterface.OnCancelListener() { // from class: com.android.settings.bluetooth.RequestPermissionActivity.1
                @Override // android.content.DialogInterface.OnCancelListener
                public final void onCancel(DialogInterface dialogInterface) {
                    RequestPermissionActivity requestPermissionActivity = RequestPermissionActivity.this;
                    int i3 = RequestPermissionActivity.$r8$clinit;
                    requestPermissionActivity.cancelAndFinish();
                }
            };
            alertParams.mOnDismissListener = new DialogInterface.OnDismissListener() { // from class: com.android.settings.bluetooth.RequestPermissionActivity.2
                @Override // android.content.DialogInterface.OnDismissListener
                public final void onDismiss(DialogInterface dialogInterface) {
                    RequestPermissionActivity.this.finish();
                }
            };
        }
        alertParams.mOnDismissListener = this;
        AlertDialog create = builder.create();
        this.mDialog = create;
        create.getWindow().setGravity(80);
        this.mDialog.show();
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    public final void onBackPressed() {
        setResult(0);
        super.onBackPressed();
    }

    @Override // android.content.DialogInterface.OnClickListener
    public final void onClick(DialogInterface dialogInterface, int i) {
        if (i == -2) {
            cancelAndFinish();
        } else {
            if (i != -1) {
                return;
            }
            proceedAndFinish();
        }
    }

    /* JADX WARN: Type inference failed for: r1v18, types: [com.android.settings.bluetooth.RequestPermissionActivity$$ExternalSyntheticLambda0] */
    /* JADX WARN: Type inference failed for: r4v2, types: [com.android.settings.bluetooth.RequestPermissionActivity$$ExternalSyntheticLambda0, kotlin.jvm.functions.Function0] */
    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public final void onCreate(Bundle bundle) {
        String string;
        String string2;
        super.onCreate(bundle);
        getWindow().addSystemFlags(NetworkAnalyticsConstants.DataPoints.FLAG_INTERFACE_NAME);
        requestWindowFeature(1);
        setResult(0);
        Intent intent = getIntent();
        if (intent != null) {
            String action = intent.getAction();
            if ("android.bluetooth.adapter.action.REQUEST_ENABLE".equals(action)) {
                this.mRequest = 1;
            } else if ("android.bluetooth.adapter.action.REQUEST_DISABLE".equals(action)) {
                this.mRequest = 3;
            } else if ("android.bluetooth.adapter.action.REQUEST_DISCOVERABLE".equals(action)) {
                this.mRequest = 2;
                this.mTimeout = intent.getIntExtra("android.bluetooth.adapter.extra.DISCOVERABLE_DURATION", 120);
                Preference$$ExternalSyntheticOutline0.m(new StringBuilder("Setting Bluetooth Discoverable Timeout = "), this.mTimeout, "BtRequestPermission");
                int i = this.mTimeout;
                if (i < 1 || i > 3600) {
                    this.mTimeout = 120;
                }
            } else {
                Log.e("BtRequestPermission", "Error: this activity may be started only with intent android.bluetooth.adapter.action.REQUEST_ENABLE, android.bluetooth.adapter.action.REQUEST_DISABLE or android.bluetooth.adapter.action.REQUEST_DISCOVERABLE");
                setResult(0);
            }
            this.mPackageName = getLaunchedFromPackage();
            int launchedFromUid = getLaunchedFromUid();
            this.mCallingUid = launchedFromUid;
            if (UserHandle.isSameApp(launchedFromUid, 1000) && getIntent().getStringExtra("android.intent.extra.PACKAGE_NAME") != null) {
                this.mPackageName = getIntent().getStringExtra("android.intent.extra.PACKAGE_NAME");
            }
            if (!UserHandle.isSameApp(this.mCallingUid, 1000) && getIntent().getStringExtra("android.intent.extra.PACKAGE_NAME") != null) {
                Log.w("BtRequestPermission", "Non-system Uid: " + this.mCallingUid + " tried to override packageName \n");
            }
            if (!TextUtils.isEmpty(this.mPackageName)) {
                try {
                    this.mAppLabel = getPackageManager().getApplicationInfo(this.mPackageName, 0).loadSafeLabel(getPackageManager(), 1000.0f, 5);
                } catch (PackageManager.NameNotFoundException unused) {
                    Log.e("BtRequestPermission", "Couldn't find app with package name " + this.mPackageName);
                    setResult(0);
                }
            }
            BluetoothAdapter defaultAdapter = BluetoothAdapter.getDefaultAdapter();
            this.mBluetoothAdapter = defaultAdapter;
            if (defaultAdapter != null) {
                int state = defaultAdapter.getState();
                int i2 = this.mRequest;
                AlertDialog alertDialog = null;
                if (i2 == 3) {
                    switch (state) {
                        case 10:
                        case 13:
                            proceedAndFinish();
                            break;
                        case 11:
                        case 12:
                            CharSequence charSequence = this.mAppLabel;
                            final int i3 = 0;
                            final Function0 function0 = new Function0(this) { // from class: com.android.settings.bluetooth.RequestPermissionActivity$$ExternalSyntheticLambda0
                                public final /* synthetic */ RequestPermissionActivity f$0;

                                {
                                    this.f$0 = this;
                                }

                                @Override // kotlin.jvm.functions.Function0
                                /* renamed from: invoke */
                                public final Object mo1068invoke() {
                                    Unit unit = Unit.INSTANCE;
                                    RequestPermissionActivity requestPermissionActivity = this.f$0;
                                    switch (i3) {
                                        case 0:
                                            requestPermissionActivity.mBluetoothAdapter.disable();
                                            if (requestPermissionActivity.mBluetoothAdapter.getState() != 10) {
                                                RequestPermissionActivity.StateChangeReceiver stateChangeReceiver = requestPermissionActivity.new StateChangeReceiver();
                                                requestPermissionActivity.mReceiver = stateChangeReceiver;
                                                requestPermissionActivity.registerReceiver(stateChangeReceiver, new IntentFilter("android.bluetooth.adapter.action.STATE_CHANGED"));
                                                requestPermissionActivity.createDialog$1();
                                                break;
                                            } else {
                                                requestPermissionActivity.proceedAndFinish();
                                                break;
                                            }
                                        case 1:
                                            int i4 = RequestPermissionActivity.$r8$clinit;
                                            requestPermissionActivity.cancelAndFinish();
                                            break;
                                        case 2:
                                            if (TextUtils.isEmpty(requestPermissionActivity.mPackageName)) {
                                                BluetoothDump.BtLog("BtRequestPermission -- onEnableConfirmed: Package name is null");
                                            } else {
                                                BluetoothDump.BtLog("BtRequestPermission -- onEnableConfirmed: called by UID : " + requestPermissionActivity.mCallingUid + " @ " + requestPermissionActivity.mPackageName);
                                            }
                                            requestPermissionActivity.mBluetoothAdapter.enable();
                                            if (requestPermissionActivity.mBluetoothAdapter.getState() != 12) {
                                                RequestPermissionActivity.StateChangeReceiver stateChangeReceiver2 = requestPermissionActivity.new StateChangeReceiver();
                                                requestPermissionActivity.mReceiver = stateChangeReceiver2;
                                                requestPermissionActivity.registerReceiver(stateChangeReceiver2, new IntentFilter("android.bluetooth.adapter.action.STATE_CHANGED"));
                                                requestPermissionActivity.createDialog$1();
                                                break;
                                            } else {
                                                requestPermissionActivity.proceedAndFinish();
                                                break;
                                            }
                                        default:
                                            int i5 = RequestPermissionActivity.$r8$clinit;
                                            requestPermissionActivity.cancelAndFinish();
                                            break;
                                    }
                                    return unit;
                                }
                            };
                            final int i4 = 1;
                            final Function0 function02 = new Function0(this) { // from class: com.android.settings.bluetooth.RequestPermissionActivity$$ExternalSyntheticLambda0
                                public final /* synthetic */ RequestPermissionActivity f$0;

                                {
                                    this.f$0 = this;
                                }

                                @Override // kotlin.jvm.functions.Function0
                                /* renamed from: invoke */
                                public final Object mo1068invoke() {
                                    Unit unit = Unit.INSTANCE;
                                    RequestPermissionActivity requestPermissionActivity = this.f$0;
                                    switch (i4) {
                                        case 0:
                                            requestPermissionActivity.mBluetoothAdapter.disable();
                                            if (requestPermissionActivity.mBluetoothAdapter.getState() != 10) {
                                                RequestPermissionActivity.StateChangeReceiver stateChangeReceiver = requestPermissionActivity.new StateChangeReceiver();
                                                requestPermissionActivity.mReceiver = stateChangeReceiver;
                                                requestPermissionActivity.registerReceiver(stateChangeReceiver, new IntentFilter("android.bluetooth.adapter.action.STATE_CHANGED"));
                                                requestPermissionActivity.createDialog$1();
                                                break;
                                            } else {
                                                requestPermissionActivity.proceedAndFinish();
                                                break;
                                            }
                                        case 1:
                                            int i42 = RequestPermissionActivity.$r8$clinit;
                                            requestPermissionActivity.cancelAndFinish();
                                            break;
                                        case 2:
                                            if (TextUtils.isEmpty(requestPermissionActivity.mPackageName)) {
                                                BluetoothDump.BtLog("BtRequestPermission -- onEnableConfirmed: Package name is null");
                                            } else {
                                                BluetoothDump.BtLog("BtRequestPermission -- onEnableConfirmed: called by UID : " + requestPermissionActivity.mCallingUid + " @ " + requestPermissionActivity.mPackageName);
                                            }
                                            requestPermissionActivity.mBluetoothAdapter.enable();
                                            if (requestPermissionActivity.mBluetoothAdapter.getState() != 12) {
                                                RequestPermissionActivity.StateChangeReceiver stateChangeReceiver2 = requestPermissionActivity.new StateChangeReceiver();
                                                requestPermissionActivity.mReceiver = stateChangeReceiver2;
                                                requestPermissionActivity.registerReceiver(stateChangeReceiver2, new IntentFilter("android.bluetooth.adapter.action.STATE_CHANGED"));
                                                requestPermissionActivity.createDialog$1();
                                                break;
                                            } else {
                                                requestPermissionActivity.proceedAndFinish();
                                                break;
                                            }
                                        default:
                                            int i5 = RequestPermissionActivity.$r8$clinit;
                                            requestPermissionActivity.cancelAndFinish();
                                            break;
                                    }
                                    return unit;
                                }
                            };
                            if (getResources().getBoolean(R.bool.auto_confirm_bluetooth_activation_dialog)) {
                                function0.mo1068invoke();
                            } else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                                if (charSequence == null) {
                                    string2 = getString(R.string.bluetooth_ask_disablement_no_name);
                                    Intrinsics.checkNotNullExpressionValue(string2, "getString(...)");
                                } else {
                                    string2 = getString(R.string.bluetooth_ask_disablement, charSequence);
                                    Intrinsics.checkNotNullExpressionValue(string2, "getString(...)");
                                }
                                AlertController.AlertParams alertParams = builder.P;
                                alertParams.mMessage = string2;
                                final int i5 = 1;
                                builder.setPositiveButton(R.string.allow, new DialogInterface.OnClickListener() { // from class: com.android.settings.bluetooth.RequestPermissionHelper$requestEnable$1$2
                                    @Override // android.content.DialogInterface.OnClickListener
                                    public final void onClick(DialogInterface dialogInterface, int i6) {
                                        switch (i5) {
                                            case 0:
                                                function0.mo1068invoke();
                                                break;
                                            case 1:
                                                function0.mo1068invoke();
                                                break;
                                            default:
                                                function0.mo1068invoke();
                                                break;
                                        }
                                    }
                                });
                                final int i6 = 2;
                                builder.setNegativeButton(R.string.deny, new DialogInterface.OnClickListener() { // from class: com.android.settings.bluetooth.RequestPermissionHelper$requestEnable$1$2
                                    @Override // android.content.DialogInterface.OnClickListener
                                    public final void onClick(DialogInterface dialogInterface, int i62) {
                                        switch (i6) {
                                            case 0:
                                                function02.mo1068invoke();
                                                break;
                                            case 1:
                                                function02.mo1068invoke();
                                                break;
                                            default:
                                                function02.mo1068invoke();
                                                break;
                                        }
                                    }
                                });
                                final int i7 = 1;
                                alertParams.mOnCancelListener = new DialogInterface.OnCancelListener() { // from class: com.android.settings.bluetooth.RequestPermissionHelper$requestEnable$1$3
                                    @Override // android.content.DialogInterface.OnCancelListener
                                    public final void onCancel(DialogInterface dialogInterface) {
                                        switch (i7) {
                                            case 0:
                                                function02.mo1068invoke();
                                                break;
                                            default:
                                                function02.mo1068invoke();
                                                break;
                                        }
                                    }
                                };
                                alertDialog = builder.create();
                            }
                            this.mRequestDialog = alertDialog;
                            if (alertDialog != null) {
                                alertDialog.show();
                                break;
                            }
                            break;
                        default:
                            Log.e("BtRequestPermission", "Unknown adapter state: " + state);
                            cancelAndFinish();
                            break;
                    }
                    return;
                }
                switch (state) {
                    case 10:
                    case 11:
                    case 13:
                        CharSequence charSequence2 = this.mAppLabel;
                        int i8 = i2 == 2 ? this.mTimeout : -1;
                        final int i9 = 2;
                        final ?? r1 = new Function0(this) { // from class: com.android.settings.bluetooth.RequestPermissionActivity$$ExternalSyntheticLambda0
                            public final /* synthetic */ RequestPermissionActivity f$0;

                            {
                                this.f$0 = this;
                            }

                            @Override // kotlin.jvm.functions.Function0
                            /* renamed from: invoke */
                            public final Object mo1068invoke() {
                                Unit unit = Unit.INSTANCE;
                                RequestPermissionActivity requestPermissionActivity = this.f$0;
                                switch (i9) {
                                    case 0:
                                        requestPermissionActivity.mBluetoothAdapter.disable();
                                        if (requestPermissionActivity.mBluetoothAdapter.getState() != 10) {
                                            RequestPermissionActivity.StateChangeReceiver stateChangeReceiver = requestPermissionActivity.new StateChangeReceiver();
                                            requestPermissionActivity.mReceiver = stateChangeReceiver;
                                            requestPermissionActivity.registerReceiver(stateChangeReceiver, new IntentFilter("android.bluetooth.adapter.action.STATE_CHANGED"));
                                            requestPermissionActivity.createDialog$1();
                                            break;
                                        } else {
                                            requestPermissionActivity.proceedAndFinish();
                                            break;
                                        }
                                    case 1:
                                        int i42 = RequestPermissionActivity.$r8$clinit;
                                        requestPermissionActivity.cancelAndFinish();
                                        break;
                                    case 2:
                                        if (TextUtils.isEmpty(requestPermissionActivity.mPackageName)) {
                                            BluetoothDump.BtLog("BtRequestPermission -- onEnableConfirmed: Package name is null");
                                        } else {
                                            BluetoothDump.BtLog("BtRequestPermission -- onEnableConfirmed: called by UID : " + requestPermissionActivity.mCallingUid + " @ " + requestPermissionActivity.mPackageName);
                                        }
                                        requestPermissionActivity.mBluetoothAdapter.enable();
                                        if (requestPermissionActivity.mBluetoothAdapter.getState() != 12) {
                                            RequestPermissionActivity.StateChangeReceiver stateChangeReceiver2 = requestPermissionActivity.new StateChangeReceiver();
                                            requestPermissionActivity.mReceiver = stateChangeReceiver2;
                                            requestPermissionActivity.registerReceiver(stateChangeReceiver2, new IntentFilter("android.bluetooth.adapter.action.STATE_CHANGED"));
                                            requestPermissionActivity.createDialog$1();
                                            break;
                                        } else {
                                            requestPermissionActivity.proceedAndFinish();
                                            break;
                                        }
                                    default:
                                        int i52 = RequestPermissionActivity.$r8$clinit;
                                        requestPermissionActivity.cancelAndFinish();
                                        break;
                                }
                                return unit;
                            }
                        };
                        final int i10 = 3;
                        final ?? r4 = new Function0(this) { // from class: com.android.settings.bluetooth.RequestPermissionActivity$$ExternalSyntheticLambda0
                            public final /* synthetic */ RequestPermissionActivity f$0;

                            {
                                this.f$0 = this;
                            }

                            @Override // kotlin.jvm.functions.Function0
                            /* renamed from: invoke */
                            public final Object mo1068invoke() {
                                Unit unit = Unit.INSTANCE;
                                RequestPermissionActivity requestPermissionActivity = this.f$0;
                                switch (i10) {
                                    case 0:
                                        requestPermissionActivity.mBluetoothAdapter.disable();
                                        if (requestPermissionActivity.mBluetoothAdapter.getState() != 10) {
                                            RequestPermissionActivity.StateChangeReceiver stateChangeReceiver = requestPermissionActivity.new StateChangeReceiver();
                                            requestPermissionActivity.mReceiver = stateChangeReceiver;
                                            requestPermissionActivity.registerReceiver(stateChangeReceiver, new IntentFilter("android.bluetooth.adapter.action.STATE_CHANGED"));
                                            requestPermissionActivity.createDialog$1();
                                            break;
                                        } else {
                                            requestPermissionActivity.proceedAndFinish();
                                            break;
                                        }
                                    case 1:
                                        int i42 = RequestPermissionActivity.$r8$clinit;
                                        requestPermissionActivity.cancelAndFinish();
                                        break;
                                    case 2:
                                        if (TextUtils.isEmpty(requestPermissionActivity.mPackageName)) {
                                            BluetoothDump.BtLog("BtRequestPermission -- onEnableConfirmed: Package name is null");
                                        } else {
                                            BluetoothDump.BtLog("BtRequestPermission -- onEnableConfirmed: called by UID : " + requestPermissionActivity.mCallingUid + " @ " + requestPermissionActivity.mPackageName);
                                        }
                                        requestPermissionActivity.mBluetoothAdapter.enable();
                                        if (requestPermissionActivity.mBluetoothAdapter.getState() != 12) {
                                            RequestPermissionActivity.StateChangeReceiver stateChangeReceiver2 = requestPermissionActivity.new StateChangeReceiver();
                                            requestPermissionActivity.mReceiver = stateChangeReceiver2;
                                            requestPermissionActivity.registerReceiver(stateChangeReceiver2, new IntentFilter("android.bluetooth.adapter.action.STATE_CHANGED"));
                                            requestPermissionActivity.createDialog$1();
                                            break;
                                        } else {
                                            requestPermissionActivity.proceedAndFinish();
                                            break;
                                        }
                                    default:
                                        int i52 = RequestPermissionActivity.$r8$clinit;
                                        requestPermissionActivity.cancelAndFinish();
                                        break;
                                }
                                return unit;
                            }
                        };
                        if (getResources().getBoolean(R.bool.auto_confirm_bluetooth_activation_dialog)) {
                            r1.mo1068invoke();
                        } else {
                            AlertDialog.Builder builder2 = new AlertDialog.Builder(this);
                            if (i8 < 0) {
                                string = charSequence2 == null ? getString(R.string.bluetooth_ask_enablement_no_name) : getString(R.string.bluetooth_ask_enablement, charSequence2);
                                Intrinsics.checkNotNull(string);
                            } else if (i8 == 0) {
                                string = charSequence2 == null ? getString(R.string.bluetooth_ask_enablement_and_lasting_discovery_no_name) : getString(R.string.sec_bluetooth_ask_enablement_and_lasting_discovery, charSequence2);
                                Intrinsics.checkNotNull(string);
                            } else {
                                string = charSequence2 == null ? getString(R.string.bluetooth_ask_enablement_and_discovery_no_name, Integer.valueOf(i8)) : getString(R.string.sec_bluetooth_ask_enablement_and_discovery, charSequence2, Integer.valueOf(i8));
                                Intrinsics.checkNotNull(string);
                            }
                            AlertController.AlertParams alertParams2 = builder2.P;
                            alertParams2.mMessage = string;
                            builder2.setPositiveButton(R.string.allow, new DialogInterface.OnClickListener() { // from class: com.android.settings.bluetooth.RequestPermissionHelper$requestEnable$1$1
                                @Override // android.content.DialogInterface.OnClickListener
                                public final void onClick(DialogInterface dialogInterface, int i11) {
                                    Function0 function03;
                                    Context context = this;
                                    if (ContextsKt.getUserManager(context).hasUserRestriction("no_bluetooth")) {
                                        Intent createAdminSupportIntent = ContextsKt.getDevicePolicyManager(context).createAdminSupportIntent("no_bluetooth");
                                        if (createAdminSupportIntent != null) {
                                            createAdminSupportIntent.setPackage(context.getPackageName());
                                            context.startActivity(createAdminSupportIntent);
                                        }
                                        function03 = r4;
                                    } else {
                                        function03 = r1;
                                    }
                                    function03.mo1068invoke();
                                }
                            });
                            final int i11 = 0;
                            builder2.setNegativeButton(R.string.deny, new DialogInterface.OnClickListener() { // from class: com.android.settings.bluetooth.RequestPermissionHelper$requestEnable$1$2
                                @Override // android.content.DialogInterface.OnClickListener
                                public final void onClick(DialogInterface dialogInterface, int i62) {
                                    switch (i11) {
                                        case 0:
                                            r4.mo1068invoke();
                                            break;
                                        case 1:
                                            r4.mo1068invoke();
                                            break;
                                        default:
                                            r4.mo1068invoke();
                                            break;
                                    }
                                }
                            });
                            alertParams2.mOnCancelListener = new DialogInterface.OnCancelListener() { // from class: com.android.settings.bluetooth.RequestPermissionHelper$requestEnable$1$3
                                @Override // android.content.DialogInterface.OnCancelListener
                                public final void onCancel(DialogInterface dialogInterface) {
                                    switch (i11) {
                                        case 0:
                                            r4.mo1068invoke();
                                            break;
                                        default:
                                            r4.mo1068invoke();
                                            break;
                                    }
                                }
                            };
                            alertDialog = builder2.create();
                        }
                        this.mRequestDialog = alertDialog;
                        if (alertDialog != null) {
                            alertDialog.show();
                            break;
                        }
                        break;
                    case 12:
                        if (i2 != 1) {
                            createDialog$1();
                            break;
                        } else {
                            proceedAndFinish();
                            break;
                        }
                    default:
                        Log.e("BtRequestPermission", "Unknown adapter state: " + state);
                        cancelAndFinish();
                        break;
                }
                return;
            }
            Log.e("BtRequestPermission", "Error: there's a problem starting Bluetooth");
            setResult(0);
        }
        finish();
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public final void onDestroy() {
        super.onDestroy();
        StateChangeReceiver stateChangeReceiver = this.mReceiver;
        if (stateChangeReceiver != null) {
            unregisterReceiver(stateChangeReceiver);
            this.mReceiver = null;
        }
        AlertDialog alertDialog = this.mDialog;
        if (alertDialog != null && alertDialog.isShowing()) {
            this.mDialog.dismiss();
            this.mDialog = null;
        }
        AlertDialog alertDialog2 = this.mRequestDialog;
        if (alertDialog2 == null || !alertDialog2.isShowing()) {
            return;
        }
        this.mRequestDialog.dismiss();
        this.mRequestDialog = null;
    }

    @Override // androidx.core.app.ComponentActivity, android.content.DialogInterface.OnDismissListener
    public final void onDismiss(DialogInterface dialogInterface) {
        cancelAndFinish();
    }

    public final void proceedAndFinish() {
        int i;
        int i2 = this.mRequest;
        if (i2 == 1 || i2 == 3) {
            i = -1;
        } else {
            this.mBluetoothAdapter.setDiscoverableTimeout(Duration.ofSeconds(this.mTimeout));
            i = 0;
            if (this.mBluetoothAdapter.setScanMode(23) == 0) {
                long currentTimeMillis = (this.mTimeout * 1000) + System.currentTimeMillis();
                SharedPreferences.Editor edit = getSharedPreferences("bluetooth_settings", 0).edit();
                edit.putLong("discoverable_end_timestamp", currentTimeMillis);
                edit.apply();
                if (this.mTimeout > 0) {
                    int i3 = BluetoothDiscoverableTimeoutReceiver.$r8$clinit;
                    Log.d("BluetoothDiscoverableTimeoutReceiver", "setDiscoverableAlarm(): alarmTime = " + currentTimeMillis);
                    Intent intent = new Intent("android.bluetooth.intent.DISCOVERABLE_TIMEOUT");
                    intent.setClass(this, BluetoothDiscoverableTimeoutReceiver.class);
                    PendingIntent broadcast = PendingIntent.getBroadcast(this, 0, intent, 67108864);
                    AlarmManager alarmManager = (AlarmManager) getSystemService("alarm");
                    if (broadcast != null) {
                        alarmManager.cancel(broadcast);
                        Log.d("BluetoothDiscoverableTimeoutReceiver", "setDiscoverableAlarm(): cancel prev alarm");
                    }
                    alarmManager.setExact(0, currentTimeMillis, PendingIntent.getBroadcast(this, 0, intent, 67108864));
                }
                int i4 = this.mTimeout;
                i = i4 >= 1 ? i4 : 1;
            }
        }
        setResult(i);
        finish();
    }
}
