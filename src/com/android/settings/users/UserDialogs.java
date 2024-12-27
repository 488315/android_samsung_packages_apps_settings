package com.android.settings.users;

import android.app.admin.DevicePolicyManager;
import android.app.admin.DevicePolicyResourcesManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.UserInfo;
import android.os.UserHandle;
import android.os.UserManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertController;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.FragmentActivity;

import com.android.settings.R;
import com.android.settings.Utils;

import java.util.function.Supplier;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class UserDialogs {
    public static AlertDialog createRemoveDialog(
            final Context context, int i, DialogInterface.OnClickListener onClickListener) {
        UserManager userManager = (UserManager) context.getSystemService("user");
        DevicePolicyManager devicePolicyManager =
                (DevicePolicyManager) context.getSystemService(DevicePolicyManager.class);
        UserInfo userInfo = userManager.getUserInfo(i);
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setPositiveButton(R.string.user_delete_button, onClickListener);
        View view = null;
        builder.setNegativeButton(android.R.string.cancel, (DialogInterface.OnClickListener) null);
        if (userInfo.isManagedProfile()) {
            final int i2 = 0;
            String string =
                    devicePolicyManager
                            .getResources()
                            .getString(
                                    "Settings.WORK_PROFILE_CONFIRM_REMOVE_TITLE",
                                    new Supplier() { // from class:
                                                     // com.android.settings.users.UserDialogs$$ExternalSyntheticLambda0
                                        @Override // java.util.function.Supplier
                                        public final Object get() {
                                            int i3 = i2;
                                            Context context2 = context;
                                            switch (i3) {
                                                case 0:
                                                    return context2.getString(
                                                            R.string
                                                                    .work_profile_confirm_remove_title);
                                                case 1:
                                                    return context2.getString(
                                                            R.string
                                                                    .work_profile_confirm_remove_message);
                                                case 2:
                                                    return context2.getString(
                                                            R.string
                                                                    .opening_paragraph_delete_profile_unknown_company);
                                                default:
                                                    return context2.getString(
                                                            R.string
                                                                    .work_profile_confirm_remove_message);
                                            }
                                        }
                                    });
            AlertController.AlertParams alertParams = builder.P;
            alertParams.mTitle = string;
            PackageManager packageManager = context.getPackageManager();
            DevicePolicyManager devicePolicyManager2 =
                    (DevicePolicyManager) context.getSystemService(DevicePolicyManager.class);
            ApplicationInfo adminApplicationInfo = Utils.getAdminApplicationInfo(context, i);
            if (adminApplicationInfo != null) {
                view =
                        ((LayoutInflater) context.getSystemService("layout_inflater"))
                                .inflate(R.layout.delete_managed_profile_dialog, (ViewGroup) null);
                ((ImageView) view.findViewById(R.id.delete_managed_profile_mdm_icon_view))
                        .setImageDrawable(packageManager.getApplicationIcon(adminApplicationInfo));
                final int i3 = 2;
                ((TextView) view.findViewById(R.id.delete_managed_profile_opening_paragraph))
                        .setText(
                                devicePolicyManager2
                                        .getResources()
                                        .getString(
                                                "Settings.WORK_PROFILE_MANAGED_BY",
                                                new Supplier() { // from class:
                                                                 // com.android.settings.users.UserDialogs$$ExternalSyntheticLambda0
                                                    @Override // java.util.function.Supplier
                                                    public final Object get() {
                                                        int i32 = i3;
                                                        Context context2 = context;
                                                        switch (i32) {
                                                            case 0:
                                                                return context2.getString(
                                                                        R.string
                                                                                .work_profile_confirm_remove_title);
                                                            case 1:
                                                                return context2.getString(
                                                                        R.string
                                                                                .work_profile_confirm_remove_message);
                                                            case 2:
                                                                return context2.getString(
                                                                        R.string
                                                                                .opening_paragraph_delete_profile_unknown_company);
                                                            default:
                                                                return context2.getString(
                                                                        R.string
                                                                                .work_profile_confirm_remove_message);
                                                        }
                                                    }
                                                }));
                final int i4 = 3;
                ((TextView) view.findViewById(R.id.delete_managed_profile_closing_paragraph))
                        .setText(
                                devicePolicyManager2
                                        .getResources()
                                        .getString(
                                                "Settings.WORK_PROFILE_CONFIRM_REMOVE_MESSAGE",
                                                new Supplier() { // from class:
                                                                 // com.android.settings.users.UserDialogs$$ExternalSyntheticLambda0
                                                    @Override // java.util.function.Supplier
                                                    public final Object get() {
                                                        int i32 = i4;
                                                        Context context2 = context;
                                                        switch (i32) {
                                                            case 0:
                                                                return context2.getString(
                                                                        R.string
                                                                                .work_profile_confirm_remove_title);
                                                            case 1:
                                                                return context2.getString(
                                                                        R.string
                                                                                .work_profile_confirm_remove_message);
                                                            case 2:
                                                                return context2.getString(
                                                                        R.string
                                                                                .opening_paragraph_delete_profile_unknown_company);
                                                            default:
                                                                return context2.getString(
                                                                        R.string
                                                                                .work_profile_confirm_remove_message);
                                                        }
                                                    }
                                                }));
                CharSequence applicationLabel =
                        packageManager.getApplicationLabel(adminApplicationInfo);
                CharSequence userBadgedLabel =
                        packageManager.getUserBadgedLabel(applicationLabel, new UserHandle(i));
                TextView textView =
                        (TextView)
                                view.findViewById(R.id.delete_managed_profile_device_manager_name);
                textView.setText(applicationLabel);
                if (!applicationLabel.toString().contentEquals(userBadgedLabel)) {
                    textView.setContentDescription(userBadgedLabel);
                }
            }
            if (view != null) {
                builder.setView(view);
            } else {
                DevicePolicyResourcesManager resources = devicePolicyManager.getResources();
                final int i5 = 1;
                alertParams.mMessage =
                        resources.getString(
                                "Settings.WORK_PROFILE_CONFIRM_REMOVE_MESSAGE",
                                new Supplier() { // from class:
                                                 // com.android.settings.users.UserDialogs$$ExternalSyntheticLambda0
                                    @Override // java.util.function.Supplier
                                    public final Object get() {
                                        int i32 = i5;
                                        Context context2 = context;
                                        switch (i32) {
                                            case 0:
                                                return context2.getString(
                                                        R.string.work_profile_confirm_remove_title);
                                            case 1:
                                                return context2.getString(
                                                        R.string
                                                                .work_profile_confirm_remove_message);
                                            case 2:
                                                return context2.getString(
                                                        R.string
                                                                .opening_paragraph_delete_profile_unknown_company);
                                            default:
                                                return context2.getString(
                                                        R.string
                                                                .work_profile_confirm_remove_message);
                                        }
                                    }
                                });
            }
        } else if (UserHandle.myUserId() == i) {
            builder.setTitle(R.string.user_confirm_remove_self_title);
            builder.setMessage(R.string.user_confirm_remove_self_message);
        } else if (userInfo.isRestricted()) {
            builder.setTitle(R.string.user_profile_confirm_remove_title);
            builder.setMessage(R.string.user_profile_confirm_remove_message);
        } else {
            builder.setTitle(R.string.user_confirm_remove_title);
            builder.setMessage(R.string.user_confirm_remove_message);
        }
        AlertDialog create = builder.create();
        create.setOnShowListener(new UserDialogs$$ExternalSyntheticLambda2(context));
        return create;
    }

    public static AlertDialog createRemoveGuestDialog(
            FragmentActivity fragmentActivity, DialogInterface.OnClickListener onClickListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(fragmentActivity);
        builder.setTitle(R.string.guest_remove_guest_dialog_title);
        builder.setMessage(R.string.user_exit_guest_confirm_message);
        builder.setPositiveButton(R.string.guest_remove_guest_confirm_button, onClickListener);
        builder.setNegativeButton(android.R.string.cancel, (DialogInterface.OnClickListener) null);
        AlertDialog create = builder.create();
        create.setOnShowListener(new UserDialogs$$ExternalSyntheticLambda2(fragmentActivity));
        return create;
    }

    public static AlertDialog createResetGuestDialog(
            FragmentActivity fragmentActivity, DialogInterface.OnClickListener onClickListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(fragmentActivity);
        builder.setTitle(R.string.guest_reset_guest_dialog_title);
        builder.setMessage(R.string.user_exit_guest_confirm_message);
        builder.setPositiveButton(R.string.guest_reset_guest_confirm_button, onClickListener);
        builder.setNegativeButton(android.R.string.cancel, (DialogInterface.OnClickListener) null);
        return builder.create();
    }
}
