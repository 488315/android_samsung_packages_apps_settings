package com.android.settings;

import android.R;
import android.app.Activity;
import android.app.admin.DevicePolicyManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.UserHandle;

import androidx.appcompat.app.AlertController;
import androidx.appcompat.app.AlertDialog;
import androidx.window.area.WindowAreaControllerImpl$RearDisplayPresentationSessionConsumer$$ExternalSyntheticOutline0;

import java.util.function.Supplier;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes.dex */
public class RemoteBugreportActivity extends Activity {
    public static final /* synthetic */ int $r8$clinit = 0;

    @Override // android.app.Activity
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        DevicePolicyManager devicePolicyManager =
                (DevicePolicyManager) getSystemService(DevicePolicyManager.class);
        int intExtra = getIntent().getIntExtra("android.app.extra.bugreport_notification_type", -1);
        if (intExtra == 2) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            final int i = 0;
            String string =
                    devicePolicyManager
                            .getResources()
                            .getString(
                                    "Settings.SHARING_REMOTE_BUGREPORT_MESSAGE",
                                    new Supplier(
                                            this) { // from class:
                                                    // com.android.settings.RemoteBugreportActivity$$ExternalSyntheticLambda0
                                        public final /* synthetic */ RemoteBugreportActivity f$0;

                                        {
                                            this.f$0 = this;
                                        }

                                        @Override // java.util.function.Supplier
                                        public final Object get() {
                                            int i2 = i;
                                            RemoteBugreportActivity remoteBugreportActivity =
                                                    this.f$0;
                                            switch (i2) {
                                                case 0:
                                                    int i3 = RemoteBugreportActivity.$r8$clinit;
                                                    return remoteBugreportActivity.getString(
                                                            R.string
                                                                    .sharing_remote_bugreport_dialog_message);
                                                default:
                                                    int i4 = RemoteBugreportActivity.$r8$clinit;
                                                    return remoteBugreportActivity.getString(
                                                            R.string
                                                                    .share_remote_bugreport_dialog_title);
                                            }
                                        }
                                    });
            AlertController.AlertParams alertParams = builder.P;
            alertParams.mMessage = string;
            final int i2 = 0;
            alertParams.mOnDismissListener =
                    new DialogInterface.OnDismissListener(
                            this) { // from class: com.android.settings.RemoteBugreportActivity.2
                        public final /* synthetic */ RemoteBugreportActivity this$0;

                        {
                            this.this$0 = this;
                        }

                        @Override // android.content.DialogInterface.OnDismissListener
                        public final void onDismiss(DialogInterface dialogInterface) {
                            switch (i2) {
                                case 0:
                                    this.this$0.finish();
                                    break;
                                default:
                                    this.this$0.finish();
                                    break;
                            }
                        }
                    };
            final int i3 = 0;
            builder.setNegativeButton(
                    R.string.ok,
                    new DialogInterface.OnClickListener(
                            this) { // from class: com.android.settings.RemoteBugreportActivity.1
                        public final /* synthetic */ RemoteBugreportActivity this$0;

                        {
                            this.this$0 = this;
                        }

                        @Override // android.content.DialogInterface.OnClickListener
                        public final void onClick(DialogInterface dialogInterface, int i4) {
                            switch (i3) {
                                case 0:
                                    this.this$0.finish();
                                    break;
                                case 1:
                                    this.this$0.sendBroadcastAsUser(
                                            new Intent(
                                                    "com.android.server.action.REMOTE_BUGREPORT_SHARING_ACCEPTED"),
                                            UserHandle.SYSTEM,
                                            "android.permission.DUMP");
                                    this.this$0.finish();
                                    break;
                                default:
                                    this.this$0.sendBroadcastAsUser(
                                            new Intent(
                                                    "com.android.server.action.REMOTE_BUGREPORT_SHARING_DECLINED"),
                                            UserHandle.SYSTEM,
                                            "android.permission.DUMP");
                                    this.this$0.finish();
                                    break;
                            }
                        }
                    });
            builder.create().show();
            return;
        }
        if (intExtra != 1 && intExtra != 3) {
            WindowAreaControllerImpl$RearDisplayPresentationSessionConsumer$$ExternalSyntheticOutline0
                    .m(
                            intExtra,
                            "Incorrect dialog type, no dialog shown. Received: ",
                            "RemoteBugreportActivity");
            return;
        }
        final int i4 =
                intExtra == 1
                        ? R.string.share_remote_bugreport_dialog_message
                        : R.string.share_remote_bugreport_dialog_message_finished;
        String str =
                intExtra == 1
                        ? "Settings.SHARE_REMOTE_BUGREPORT_NOT_FINISHED_REQUEST_CONSENT"
                        : "Settings.SHARE_REMOTE_BUGREPORT_FINISHED_REQUEST_CONSENT";
        AlertDialog.Builder builder2 = new AlertDialog.Builder(this);
        final int i5 = 1;
        String string2 =
                devicePolicyManager
                        .getResources()
                        .getString(
                                "Settings.SHARE_REMOTE_BUGREPORT_DIALOG_TITLE",
                                new Supplier(
                                        this) { // from class:
                                                // com.android.settings.RemoteBugreportActivity$$ExternalSyntheticLambda0
                                    public final /* synthetic */ RemoteBugreportActivity f$0;

                                    {
                                        this.f$0 = this;
                                    }

                                    @Override // java.util.function.Supplier
                                    public final Object get() {
                                        int i22 = i5;
                                        RemoteBugreportActivity remoteBugreportActivity = this.f$0;
                                        switch (i22) {
                                            case 0:
                                                int i32 = RemoteBugreportActivity.$r8$clinit;
                                                return remoteBugreportActivity.getString(
                                                        R.string
                                                                .sharing_remote_bugreport_dialog_message);
                                            default:
                                                int i42 = RemoteBugreportActivity.$r8$clinit;
                                                return remoteBugreportActivity.getString(
                                                        R.string
                                                                .share_remote_bugreport_dialog_title);
                                        }
                                    }
                                });
        AlertController.AlertParams alertParams2 = builder2.P;
        alertParams2.mTitle = string2;
        alertParams2.mMessage =
                devicePolicyManager
                        .getResources()
                        .getString(
                                str,
                                new Supplier() { // from class:
                                                 // com.android.settings.RemoteBugreportActivity$$ExternalSyntheticLambda2
                                    @Override // java.util.function.Supplier
                                    public final Object get() {
                                        RemoteBugreportActivity remoteBugreportActivity =
                                                RemoteBugreportActivity.this;
                                        int i6 = i4;
                                        int i7 = RemoteBugreportActivity.$r8$clinit;
                                        return remoteBugreportActivity.getString(i6);
                                    }
                                });
        final int i6 = 1;
        alertParams2.mOnDismissListener =
                new DialogInterface.OnDismissListener(
                        this) { // from class: com.android.settings.RemoteBugreportActivity.2
                    public final /* synthetic */ RemoteBugreportActivity this$0;

                    {
                        this.this$0 = this;
                    }

                    @Override // android.content.DialogInterface.OnDismissListener
                    public final void onDismiss(DialogInterface dialogInterface) {
                        switch (i6) {
                            case 0:
                                this.this$0.finish();
                                break;
                            default:
                                this.this$0.finish();
                                break;
                        }
                    }
                };
        final int i7 = 2;
        builder2.setNegativeButton(
                R.string.decline_remote_bugreport_action,
                new DialogInterface.OnClickListener(
                        this) { // from class: com.android.settings.RemoteBugreportActivity.1
                    public final /* synthetic */ RemoteBugreportActivity this$0;

                    {
                        this.this$0 = this;
                    }

                    @Override // android.content.DialogInterface.OnClickListener
                    public final void onClick(DialogInterface dialogInterface, int i42) {
                        switch (i7) {
                            case 0:
                                this.this$0.finish();
                                break;
                            case 1:
                                this.this$0.sendBroadcastAsUser(
                                        new Intent(
                                                "com.android.server.action.REMOTE_BUGREPORT_SHARING_ACCEPTED"),
                                        UserHandle.SYSTEM,
                                        "android.permission.DUMP");
                                this.this$0.finish();
                                break;
                            default:
                                this.this$0.sendBroadcastAsUser(
                                        new Intent(
                                                "com.android.server.action.REMOTE_BUGREPORT_SHARING_DECLINED"),
                                        UserHandle.SYSTEM,
                                        "android.permission.DUMP");
                                this.this$0.finish();
                                break;
                        }
                    }
                });
        final int i8 = 1;
        builder2.setPositiveButton(
                R.string.share_remote_bugreport_action,
                new DialogInterface.OnClickListener(
                        this) { // from class: com.android.settings.RemoteBugreportActivity.1
                    public final /* synthetic */ RemoteBugreportActivity this$0;

                    {
                        this.this$0 = this;
                    }

                    @Override // android.content.DialogInterface.OnClickListener
                    public final void onClick(DialogInterface dialogInterface, int i42) {
                        switch (i8) {
                            case 0:
                                this.this$0.finish();
                                break;
                            case 1:
                                this.this$0.sendBroadcastAsUser(
                                        new Intent(
                                                "com.android.server.action.REMOTE_BUGREPORT_SHARING_ACCEPTED"),
                                        UserHandle.SYSTEM,
                                        "android.permission.DUMP");
                                this.this$0.finish();
                                break;
                            default:
                                this.this$0.sendBroadcastAsUser(
                                        new Intent(
                                                "com.android.server.action.REMOTE_BUGREPORT_SHARING_DECLINED"),
                                        UserHandle.SYSTEM,
                                        "android.permission.DUMP");
                                this.this$0.finish();
                                break;
                        }
                    }
                });
        builder2.create().show();
    }
}
