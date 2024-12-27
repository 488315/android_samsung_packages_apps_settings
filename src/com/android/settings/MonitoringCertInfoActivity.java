package com.android.settings;

import android.R;
import android.app.Activity;
import android.app.admin.DevicePolicyManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.icu.text.MessageFormat;
import android.os.Bundle;
import android.os.UserHandle;

import androidx.appcompat.app.AlertController;
import androidx.appcompat.app.AlertDialog;

import com.android.settingslib.RestrictedLockUtils;
import com.android.settingslib.utils.StringUtil;

import java.util.HashMap;
import java.util.Locale;
import java.util.function.Supplier;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes.dex */
public class MonitoringCertInfoActivity extends Activity
        implements DialogInterface.OnClickListener, DialogInterface.OnDismissListener {
    public static final /* synthetic */ int $r8$clinit = 0;
    public int mUserId;

    @Override // android.content.DialogInterface.OnClickListener
    public final void onClick(DialogInterface dialogInterface, int i) {
        Intent intent = new Intent("com.android.settings.TRUSTED_CREDENTIALS_USER");
        intent.setPackage(getPackageName());
        intent.setFlags(335544320);
        intent.putExtra("ARG_SHOW_NEW_FOR_USER", this.mUserId);
        startActivity(intent);
        finish();
    }

    @Override // android.app.Activity
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        int intExtra =
                getIntent().getIntExtra("android.intent.extra.USER_ID", UserHandle.myUserId());
        this.mUserId = intExtra;
        UserHandle of = intExtra == -10000 ? null : UserHandle.of(intExtra);
        final DevicePolicyManager devicePolicyManager =
                (DevicePolicyManager) getSystemService(DevicePolicyManager.class);
        int intExtra2 = getIntent().getIntExtra("android.settings.extra.number_of_certificates", 1);
        String icuPluralsString =
                StringUtil.getIcuPluralsString(
                        this,
                        intExtra2,
                        RestrictedLockUtils.getProfileOrDeviceOwner(this, null, of) != null
                                ? R.string.ssl_ca_cert_settings_button
                                : R.string.ssl_ca_cert_dialog_title);
        setTitle(icuPluralsString);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        AlertController.AlertParams alertParams = builder.P;
        alertParams.mTitle = icuPluralsString;
        alertParams.mCancelable = true;
        builder.setPositiveButton(
                StringUtil.getIcuPluralsString(
                        this, intExtra2, R.string.ssl_ca_cert_settings_button),
                this);
        builder.setNeutralButton(R.string.cancel, null);
        alertParams.mOnDismissListener = this;
        if (devicePolicyManager.getProfileOwnerAsUser(this.mUserId) != null) {
            final int i = 0;
            MessageFormat messageFormat =
                    new MessageFormat(
                            devicePolicyManager
                                    .getResources()
                                    .getString(
                                            "Settings.WORK_PROFILE_INSTALLED_CERTIFICATE_AUTHORITY_WARNING",
                                            new Supplier(this) { // from class:
                                                // com.android.settings.MonitoringCertInfoActivity$$ExternalSyntheticLambda0
                                                public final /* synthetic */
                                                MonitoringCertInfoActivity f$0;

                                                {
                                                    this.f$0 = this;
                                                }

                                                @Override // java.util.function.Supplier
                                                public final Object get() {
                                                    switch (i) {
                                                        case 0:
                                                            MonitoringCertInfoActivity
                                                                    monitoringCertInfoActivity =
                                                                            this.f$0;
                                                            DevicePolicyManager
                                                                    devicePolicyManager2 =
                                                                            devicePolicyManager;
                                                            int i2 =
                                                                    MonitoringCertInfoActivity
                                                                            .$r8$clinit;
                                                            return String.format(
                                                                    monitoringCertInfoActivity
                                                                            .getResources()
                                                                            .getString(
                                                                                    R.string
                                                                                            .ssl_ca_cert_info_message,
                                                                                    devicePolicyManager2
                                                                                            .getProfileOwnerNameAsUser(
                                                                                                    monitoringCertInfoActivity
                                                                                                            .mUserId)),
                                                                    new Object[0]);
                                                        default:
                                                            MonitoringCertInfoActivity
                                                                    monitoringCertInfoActivity2 =
                                                                            this.f$0;
                                                            DevicePolicyManager
                                                                    devicePolicyManager3 =
                                                                            devicePolicyManager;
                                                            int i3 =
                                                                    MonitoringCertInfoActivity
                                                                            .$r8$clinit;
                                                            return String.format(
                                                                    monitoringCertInfoActivity2
                                                                            .getResources()
                                                                            .getString(
                                                                                    R.string
                                                                                            .ssl_ca_cert_info_message_device_owner,
                                                                                    devicePolicyManager3
                                                                                            .getDeviceOwnerNameOnAnyUser()),
                                                                    new Object[0]);
                                                    }
                                                }
                                            }),
                            Locale.getDefault());
            HashMap hashMap = new HashMap();
            hashMap.put("numberOfCertificates", Integer.valueOf(intExtra2));
            hashMap.put("orgName", devicePolicyManager.getProfileOwnerNameAsUser(this.mUserId));
            alertParams.mMessage = messageFormat.format(hashMap);
        } else if (devicePolicyManager.getDeviceOwnerComponentOnCallingUser() != null) {
            final int i2 = 1;
            MessageFormat messageFormat2 =
                    new MessageFormat(
                            devicePolicyManager
                                    .getResources()
                                    .getString(
                                            "Settings.DEVICE_OWNER_INSTALLED_CERTIFICATE_AUTHORITY_WARNING",
                                            new Supplier(this) { // from class:
                                                // com.android.settings.MonitoringCertInfoActivity$$ExternalSyntheticLambda0
                                                public final /* synthetic */
                                                MonitoringCertInfoActivity f$0;

                                                {
                                                    this.f$0 = this;
                                                }

                                                @Override // java.util.function.Supplier
                                                public final Object get() {
                                                    switch (i2) {
                                                        case 0:
                                                            MonitoringCertInfoActivity
                                                                    monitoringCertInfoActivity =
                                                                            this.f$0;
                                                            DevicePolicyManager
                                                                    devicePolicyManager2 =
                                                                            devicePolicyManager;
                                                            int i22 =
                                                                    MonitoringCertInfoActivity
                                                                            .$r8$clinit;
                                                            return String.format(
                                                                    monitoringCertInfoActivity
                                                                            .getResources()
                                                                            .getString(
                                                                                    R.string
                                                                                            .ssl_ca_cert_info_message,
                                                                                    devicePolicyManager2
                                                                                            .getProfileOwnerNameAsUser(
                                                                                                    monitoringCertInfoActivity
                                                                                                            .mUserId)),
                                                                    new Object[0]);
                                                        default:
                                                            MonitoringCertInfoActivity
                                                                    monitoringCertInfoActivity2 =
                                                                            this.f$0;
                                                            DevicePolicyManager
                                                                    devicePolicyManager3 =
                                                                            devicePolicyManager;
                                                            int i3 =
                                                                    MonitoringCertInfoActivity
                                                                            .$r8$clinit;
                                                            return String.format(
                                                                    monitoringCertInfoActivity2
                                                                            .getResources()
                                                                            .getString(
                                                                                    R.string
                                                                                            .ssl_ca_cert_info_message_device_owner,
                                                                                    devicePolicyManager3
                                                                                            .getDeviceOwnerNameOnAnyUser()),
                                                                    new Object[0]);
                                                    }
                                                }
                                            }),
                            Locale.getDefault());
            HashMap hashMap2 = new HashMap();
            hashMap2.put("numberOfCertificates", Integer.valueOf(intExtra2));
            hashMap2.put("orgName", devicePolicyManager.getDeviceOwnerNameOnAnyUser());
            alertParams.mMessage = messageFormat2.format(hashMap2);
        } else {
            alertParams.mIconId = R.drawable.stat_notify_error;
            builder.setMessage(R.string.ssl_ca_cert_warning_message);
        }
        builder.show();
    }

    @Override // android.content.DialogInterface.OnDismissListener
    public final void onDismiss(DialogInterface dialogInterface) {
        finish();
    }
}
