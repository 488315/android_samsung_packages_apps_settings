package com.samsung.android.settings.autopoweronoff;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.PowerManager;
import android.util.secutil.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import com.android.internal.app.AlertActivity;
import com.android.settings.R;
import com.android.settings.Utils;

import com.samsung.android.knox.net.vpn.KnoxVpnPolicyConstants;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class AutoPowerOnOffConfirmDialog extends AlertActivity {
    public static final /* synthetic */ int $r8$clinit = 0;
    public TextView mConfirmText;
    public Context mContext;
    public CountDownTimer mCountdownTimer;
    public PowerManager.WakeLock mCpuWakeLock;
    public long mRemainTime = 0;

    /* renamed from: -$$Nest$mstartShutDown, reason: not valid java name */
    public static void m1124$$Nest$mstartShutDown(
            AutoPowerOnOffConfirmDialog autoPowerOnOffConfirmDialog) {
        autoPowerOnOffConfirmDialog.getClass();
        Intent intent = new Intent("com.android.internal.intent.action.REQUEST_SHUTDOWN");
        intent.putExtra("android.intent.extra.KEY_CONFIRM", false);
        intent.setFlags(268435456);
        intent.putExtra(
                "android.intent.extra.REBOOT_REASON", "[AutoPowerOnOff] AutoPower off shudown");
        autoPowerOnOffConfirmDialog.startActivity(intent);
    }

    public final void cancelCountdownTimer() {
        CountDownTimer countDownTimer = this.mCountdownTimer;
        if (countDownTimer != null) {
            countDownTimer.cancel();
            this.mCountdownTimer = null;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        Log.secD("AutoPowerOnOffConfirmOnLock", "onCreate");
        this.mContext = getApplicationContext();
        if (bundle != null) {
            this.mRemainTime = bundle.getLong("remain_time");
            Log.secD("AutoPowerOnOffConfirmOnLock", "restore:remainTime=" + this.mRemainTime);
        }
        Window window = getWindow();
        window.addFlags(2621568);
        window.setCloseOnTouchOutside(false);
        window.setGravity(80);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View inflate =
                getLayoutInflater().inflate(R.layout.sec_auto_power_off_confirm, (ViewGroup) null);
        this.mConfirmText = (TextView) inflate.findViewById(R.id.confirmText);
        this.mConfirmText.setText(
                getResources()
                        .getQuantityString(
                                Utils.isTablet()
                                        ? R.plurals.sec_auto_power_off_dialog_text_plurals_tablet
                                        : R.plurals.sec_auto_power_off_dialog_text_plurals,
                                (int) 30,
                                30L));
        builder.setView(inflate);
        builder.setTitle(R.string.sec_auto_power_off_dialog_title);
        final int i = 0;
        builder.setPositiveButton(
                R.string.common_ok,
                new DialogInterface.OnClickListener(
                        this) { // from class:
                                // com.samsung.android.settings.autopoweronoff.AutoPowerOnOffConfirmDialog.1
                    public final /* synthetic */ AutoPowerOnOffConfirmDialog this$0;

                    {
                        this.this$0 = this;
                    }

                    @Override // android.content.DialogInterface.OnClickListener
                    public final void onClick(DialogInterface dialogInterface, int i2) {
                        switch (i) {
                            case 0:
                                AutoPowerOnOffConfirmDialog autoPowerOnOffConfirmDialog =
                                        this.this$0;
                                int i3 = AutoPowerOnOffConfirmDialog.$r8$clinit;
                                autoPowerOnOffConfirmDialog.releaseWakeLock();
                                this.this$0.cancelCountdownTimer();
                                this.this$0.regNextAutoPowerOffAlarm();
                                AutoPowerOnOffConfirmDialog.m1124$$Nest$mstartShutDown(this.this$0);
                                break;
                            default:
                                AutoPowerOnOffConfirmDialog autoPowerOnOffConfirmDialog2 =
                                        this.this$0;
                                int i4 = AutoPowerOnOffConfirmDialog.$r8$clinit;
                                autoPowerOnOffConfirmDialog2.releaseWakeLock();
                                this.this$0.cancelCountdownTimer();
                                this.this$0.regNextAutoPowerOffAlarm();
                                this.this$0.finish();
                                break;
                        }
                    }
                });
        final int i2 = 1;
        builder.setNegativeButton(
                R.string.common_cancel,
                new DialogInterface.OnClickListener(
                        this) { // from class:
                                // com.samsung.android.settings.autopoweronoff.AutoPowerOnOffConfirmDialog.1
                    public final /* synthetic */ AutoPowerOnOffConfirmDialog this$0;

                    {
                        this.this$0 = this;
                    }

                    @Override // android.content.DialogInterface.OnClickListener
                    public final void onClick(DialogInterface dialogInterface, int i22) {
                        switch (i2) {
                            case 0:
                                AutoPowerOnOffConfirmDialog autoPowerOnOffConfirmDialog =
                                        this.this$0;
                                int i3 = AutoPowerOnOffConfirmDialog.$r8$clinit;
                                autoPowerOnOffConfirmDialog.releaseWakeLock();
                                this.this$0.cancelCountdownTimer();
                                this.this$0.regNextAutoPowerOffAlarm();
                                AutoPowerOnOffConfirmDialog.m1124$$Nest$mstartShutDown(this.this$0);
                                break;
                            default:
                                AutoPowerOnOffConfirmDialog autoPowerOnOffConfirmDialog2 =
                                        this.this$0;
                                int i4 = AutoPowerOnOffConfirmDialog.$r8$clinit;
                                autoPowerOnOffConfirmDialog2.releaseWakeLock();
                                this.this$0.cancelCountdownTimer();
                                this.this$0.regNextAutoPowerOffAlarm();
                                this.this$0.finish();
                                break;
                        }
                    }
                });
        builder.setOnCancelListener(
                new DialogInterface
                        .OnCancelListener() { // from class:
                                              // com.samsung.android.settings.autopoweronoff.AutoPowerOnOffConfirmDialog.3
                    @Override // android.content.DialogInterface.OnCancelListener
                    public final void onCancel(DialogInterface dialogInterface) {
                        AutoPowerOnOffConfirmDialog autoPowerOnOffConfirmDialog =
                                AutoPowerOnOffConfirmDialog.this;
                        int i3 = AutoPowerOnOffConfirmDialog.$r8$clinit;
                        autoPowerOnOffConfirmDialog.releaseWakeLock();
                        AutoPowerOnOffConfirmDialog.this.cancelCountdownTimer();
                        AutoPowerOnOffConfirmDialog.this.regNextAutoPowerOffAlarm();
                        AutoPowerOnOffConfirmDialog.this.finish();
                    }
                });
        builder.create().show();
        long j = this.mRemainTime;
        if (j == 0) {
            j = 30000;
        }
        this.mCountdownTimer =
                new CountDownTimer(j) { // from class:
                    // com.samsung.android.settings.autopoweronoff.AutoPowerOnOffConfirmDialog.4
                    @Override // android.os.CountDownTimer
                    public final void onFinish() {
                        Log.secD("AutoPowerOnOffConfirmOnLock", "Timer expired, shutting down");
                        AutoPowerOnOffConfirmDialog autoPowerOnOffConfirmDialog =
                                AutoPowerOnOffConfirmDialog.this;
                        int i3 = AutoPowerOnOffConfirmDialog.$r8$clinit;
                        autoPowerOnOffConfirmDialog.releaseWakeLock();
                        AutoPowerOnOffConfirmDialog.this.cancelCountdownTimer();
                        AutoPowerOnOffConfirmDialog.this.regNextAutoPowerOffAlarm();
                        AutoPowerOnOffConfirmDialog.m1124$$Nest$mstartShutDown(
                                AutoPowerOnOffConfirmDialog.this);
                    }

                    /* JADX WARN: Code restructure failed: missing block: B:18:0x0027, code lost:

                       if (r5 > 30000) goto L4;
                    */
                    @Override // android.os.CountDownTimer
                    /*
                        Code decompiled incorrectly, please refer to instructions dump.
                        To view partially-correct code enable 'Show inconsistent code' option in preferences
                    */
                    public final void onTick(long r5) {
                        /*
                            r4 = this;
                            java.lang.StringBuilder r0 = new java.lang.StringBuilder
                            java.lang.String r1 = "onTick : millisUntilFinished = "
                            r0.<init>(r1)
                            r0.append(r5)
                            java.lang.String r0 = r0.toString()
                            java.lang.String r1 = "AutoPowerOnOffConfirmOnLock"
                            android.util.secutil.Log.secD(r1, r0)
                            com.samsung.android.settings.autopoweronoff.AutoPowerOnOffConfirmDialog r4 = com.samsung.android.settings.autopoweronoff.AutoPowerOnOffConfirmDialog.this
                            int r0 = com.samsung.android.settings.autopoweronoff.AutoPowerOnOffConfirmDialog.$r8$clinit
                            r4.getClass()
                            r0 = 0
                            int r2 = (r5 > r0 ? 1 : (r5 == r0 ? 0 : -1))
                            if (r2 >= 0) goto L23
                        L21:
                            r5 = r0
                            goto L2a
                        L23:
                            r0 = 30000(0x7530, double:1.4822E-319)
                            int r2 = (r5 > r0 ? 1 : (r5 == r0 ? 0 : -1))
                            if (r2 <= 0) goto L2a
                            goto L21
                        L2a:
                            r4.mRemainTime = r5
                            r0 = 1000(0x3e8, double:4.94E-321)
                            long r5 = r5 / r0
                            r0 = 1
                            int r0 = (r5 > r0 ? 1 : (r5 == r0 ? 0 : -1))
                            r1 = 2131886130(0x7f120032, float:1.940683E38)
                            r2 = 2131886131(0x7f120033, float:1.9406832E38)
                            if (r0 != 0) goto L59
                            android.content.res.Resources r5 = r4.getResources()
                            boolean r6 = com.android.settings.Utils.isTablet()
                            if (r6 == 0) goto L46
                            r1 = r2
                        L46:
                            r6 = 1
                            java.lang.Integer r0 = java.lang.Integer.valueOf(r6)
                            java.lang.Object[] r0 = new java.lang.Object[]{r0}
                            java.lang.String r5 = r5.getQuantityString(r1, r6, r0)
                            android.widget.TextView r4 = r4.mConfirmText
                            r4.setText(r5)
                            goto L76
                        L59:
                            android.content.res.Resources r0 = r4.getResources()
                            boolean r3 = com.android.settings.Utils.isTablet()
                            if (r3 == 0) goto L64
                            r1 = r2
                        L64:
                            int r2 = (int) r5
                            java.lang.Long r5 = java.lang.Long.valueOf(r5)
                            java.lang.Object[] r5 = new java.lang.Object[]{r5}
                            java.lang.String r5 = r0.getQuantityString(r1, r2, r5)
                            android.widget.TextView r4 = r4.mConfirmText
                            r4.setText(r5)
                        L76:
                            return
                        */
                        throw new UnsupportedOperationException(
                                "Method not decompiled:"
                                    + " com.samsung.android.settings.autopoweronoff.AutoPowerOnOffConfirmDialog.AnonymousClass4.onTick(long):void");
                    }
                }.start();
    }

    public final void onDestroy() {
        Log.secD("AutoPowerOnOffConfirmOnLock", "onDestroy()");
        cancelCountdownTimer();
        regNextAutoPowerOffAlarm();
        super.onDestroy();
    }

    public final void onPause() {
        releaseWakeLock();
        super.onPause();
    }

    public final void onResume() {
        super.onResume();
        PowerManager powerManager = (PowerManager) this.mContext.getSystemService("power");
        releaseWakeLock();
        PowerManager.WakeLock newWakeLock =
                powerManager.newWakeLock(805306378, "AutoPowerOffConfirm");
        this.mCpuWakeLock = newWakeLock;
        newWakeLock.acquire();
    }

    public final void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        bundle.putLong("remain_time", this.mRemainTime);
        Log.secD(
                "AutoPowerOnOffConfirmOnLock",
                "onSaveInstanceState:remainTime=" + this.mRemainTime);
    }

    public final void regNextAutoPowerOffAlarm() {
        Intent intent = new Intent("com.samsung.settings.action.SET_AUTO_POWER_OFF");
        intent.setPackage(KnoxVpnPolicyConstants.ANDROID_SETTINGS_PKG);
        intent.putExtra("power_off_reg", true);
        intent.putExtra("by_user", false);
        this.mContext.sendBroadcast(intent);
    }

    public final void releaseWakeLock() {
        PowerManager.WakeLock wakeLock = this.mCpuWakeLock;
        if (wakeLock != null) {
            wakeLock.release();
            this.mCpuWakeLock = null;
        }
    }
}
