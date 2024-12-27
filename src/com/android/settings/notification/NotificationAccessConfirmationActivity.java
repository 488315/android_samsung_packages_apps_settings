package com.android.settings.notification;

import android.app.Activity;
import android.app.NotificationManager;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.UserManager;
import android.text.TextUtils;
import android.util.Slog;
import android.view.accessibility.AccessibilityEvent;
import android.widget.Toast;

import com.android.internal.app.AlertActivity;
import com.android.internal.app.AlertController;
import com.android.settings.R;

import com.samsung.android.knox.net.nap.NetworkAnalyticsConstants;
import com.samsung.android.knox.ucm.plugin.agent.UcmAgentProviderImpl;

import java.util.function.Supplier;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class NotificationAccessConfirmationActivity extends Activity implements DialogInterface {
    public static final /* synthetic */ int $r8$clinit = 0;
    public ComponentName mComponentName;
    public DevicePolicyManager mDpm;
    public NotificationManager mNm;
    public UserManager mUm;

    @Override // android.content.DialogInterface
    public final void cancel() {
        finish();
    }

    @Override // android.content.DialogInterface
    public final void dismiss() {
        if (isFinishing()) {
            return;
        }
        finish();
    }

    @Override // android.app.Activity, android.view.Window.Callback
    public final boolean dispatchPopulateAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        return AlertActivity.dispatchPopulateAccessibilityEvent(this, accessibilityEvent);
    }

    @Override // android.app.Activity
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        getWindow().addSystemFlags(NetworkAnalyticsConstants.DataPoints.FLAG_INTERFACE_NAME);
        this.mUm = (UserManager) getSystemService(UserManager.class);
        this.mDpm = (DevicePolicyManager) getSystemService(DevicePolicyManager.class);
        if (this.mUm.isManagedProfile()) {
            Slog.w(
                    "NotificationAccessConfirmationActivity",
                    "Apps in the work profile do not support notification listeners");
            Toast.makeText(
                            this,
                            this.mDpm
                                    .getResources()
                                    .getString(
                                            "Settings.WORK_APPS_CANNOT_ACCESS_NOTIFICATION_SETTINGS",
                                            new Supplier() { // from class:
                                                // com.android.settings.notification.NotificationAccessConfirmationActivity$$ExternalSyntheticLambda0
                                                @Override // java.util.function.Supplier
                                                public final Object get() {
                                                    NotificationAccessConfirmationActivity
                                                            notificationAccessConfirmationActivity =
                                                                    NotificationAccessConfirmationActivity
                                                                            .this;
                                                    int i =
                                                            NotificationAccessConfirmationActivity
                                                                    .$r8$clinit;
                                                    return notificationAccessConfirmationActivity
                                                            .getString(
                                                                    R.string
                                                                            .notification_settings_work_profile);
                                                }
                                            }),
                            0)
                    .show();
            finish();
            return;
        }
        this.mNm = (NotificationManager) getSystemService("notification");
        this.mComponentName = (ComponentName) getIntent().getParcelableExtra("component_name");
        getIntent().getIntExtra(UcmAgentProviderImpl.UcmAgentSpiProperty.KEY_USER_ID, -10000);
        ComponentName componentName = this.mComponentName;
        if (componentName == null
                || componentName.getPackageName() == null
                || this.mComponentName.flattenToString().length()
                        > NotificationManager.MAX_SERVICE_COMPONENT_NAME_LENGTH) {
            finish();
            return;
        }
        try {
            CharSequence loadSafeLabel =
                    getPackageManager()
                            .getApplicationInfo(this.mComponentName.getPackageName(), 0)
                            .loadSafeLabel(getPackageManager(), 1000.0f, 5);
            if (TextUtils.isEmpty(loadSafeLabel)) {
                finish();
                return;
            }
            AlertController.AlertParams alertParams = new AlertController.AlertParams(this);
            alertParams.mTitle =
                    getString(
                            R.string.notification_listener_security_warning_title,
                            new Object[] {loadSafeLabel});
            alertParams.mMessage =
                    getString(
                            R.string.notification_listener_security_warning_summary,
                            new Object[] {loadSafeLabel});
            alertParams.mPositiveButtonText = getString(R.string.allow);
            final int i = 0;
            alertParams.mPositiveButtonListener =
                    new DialogInterface.OnClickListener(this) { // from class:
                        // com.android.settings.notification.NotificationAccessConfirmationActivity$$ExternalSyntheticLambda1
                        public final /* synthetic */ NotificationAccessConfirmationActivity f$0;

                        {
                            this.f$0 = this;
                        }

                        @Override // android.content.DialogInterface.OnClickListener
                        public final void onClick(DialogInterface dialogInterface, int i2) {
                            int i3 = i;
                            NotificationAccessConfirmationActivity
                                    notificationAccessConfirmationActivity = this.f$0;
                            switch (i3) {
                                case 0:
                                    int i4 = NotificationAccessConfirmationActivity.$r8$clinit;
                                    notificationAccessConfirmationActivity.getClass();
                                    try {
                                        if (!"android.permission.BIND_NOTIFICATION_LISTENER_SERVICE"
                                                .equals(
                                                        notificationAccessConfirmationActivity
                                                                .getPackageManager()
                                                                .getServiceInfo(
                                                                        notificationAccessConfirmationActivity
                                                                                .mComponentName,
                                                                        0)
                                                                .permission)) {
                                            Slog.e(
                                                    "NotificationAccessConfirmationActivity",
                                                    "Service "
                                                            + notificationAccessConfirmationActivity
                                                                    .mComponentName
                                                            + " lacks permission"
                                                            + " android.permission.BIND_NOTIFICATION_LISTENER_SERVICE");
                                            break;
                                        } else {
                                            notificationAccessConfirmationActivity.mNm
                                                    .setNotificationListenerAccessGranted(
                                                            notificationAccessConfirmationActivity
                                                                    .mComponentName,
                                                            true);
                                            notificationAccessConfirmationActivity.finish();
                                            break;
                                        }
                                    } catch (PackageManager.NameNotFoundException e) {
                                        Slog.e(
                                                "NotificationAccessConfirmationActivity",
                                                "Failed to get service info for "
                                                        + notificationAccessConfirmationActivity
                                                                .mComponentName,
                                                e);
                                        return;
                                    }
                                default:
                                    int i5 = NotificationAccessConfirmationActivity.$r8$clinit;
                                    notificationAccessConfirmationActivity.finish();
                                    break;
                            }
                        }
                    };
            alertParams.mNegativeButtonText = getString(R.string.deny);
            final int i2 = 1;
            alertParams.mNegativeButtonListener =
                    new DialogInterface.OnClickListener(this) { // from class:
                        // com.android.settings.notification.NotificationAccessConfirmationActivity$$ExternalSyntheticLambda1
                        public final /* synthetic */ NotificationAccessConfirmationActivity f$0;

                        {
                            this.f$0 = this;
                        }

                        @Override // android.content.DialogInterface.OnClickListener
                        public final void onClick(DialogInterface dialogInterface, int i22) {
                            int i3 = i2;
                            NotificationAccessConfirmationActivity
                                    notificationAccessConfirmationActivity = this.f$0;
                            switch (i3) {
                                case 0:
                                    int i4 = NotificationAccessConfirmationActivity.$r8$clinit;
                                    notificationAccessConfirmationActivity.getClass();
                                    try {
                                        if (!"android.permission.BIND_NOTIFICATION_LISTENER_SERVICE"
                                                .equals(
                                                        notificationAccessConfirmationActivity
                                                                .getPackageManager()
                                                                .getServiceInfo(
                                                                        notificationAccessConfirmationActivity
                                                                                .mComponentName,
                                                                        0)
                                                                .permission)) {
                                            Slog.e(
                                                    "NotificationAccessConfirmationActivity",
                                                    "Service "
                                                            + notificationAccessConfirmationActivity
                                                                    .mComponentName
                                                            + " lacks permission"
                                                            + " android.permission.BIND_NOTIFICATION_LISTENER_SERVICE");
                                            break;
                                        } else {
                                            notificationAccessConfirmationActivity.mNm
                                                    .setNotificationListenerAccessGranted(
                                                            notificationAccessConfirmationActivity
                                                                    .mComponentName,
                                                            true);
                                            notificationAccessConfirmationActivity.finish();
                                            break;
                                        }
                                    } catch (PackageManager.NameNotFoundException e) {
                                        Slog.e(
                                                "NotificationAccessConfirmationActivity",
                                                "Failed to get service info for "
                                                        + notificationAccessConfirmationActivity
                                                                .mComponentName,
                                                e);
                                        return;
                                    }
                                default:
                                    int i5 = NotificationAccessConfirmationActivity.$r8$clinit;
                                    notificationAccessConfirmationActivity.finish();
                                    break;
                            }
                        }
                    };
            AlertController.create(this, this, getWindow()).installContent(alertParams);
            getWindow().setCloseOnTouchOutside(false);
        } catch (PackageManager.NameNotFoundException e) {
            Slog.e(
                    "NotificationAccessConfirmationActivity",
                    "Couldn't find app with package name for " + this.mComponentName,
                    e);
            finish();
        }
    }

    @Override // android.app.Activity
    public final void onPause() {
        getWindow().clearFlags(NetworkAnalyticsConstants.DataPoints.FLAG_INTERFACE_NAME);
        super.onPause();
    }

    @Override // android.app.Activity
    public final void onResume() {
        super.onResume();
        getWindow().addFlags(NetworkAnalyticsConstants.DataPoints.FLAG_INTERFACE_NAME);
    }

    @Override // android.app.Activity
    public final void onBackPressed() {}
}
