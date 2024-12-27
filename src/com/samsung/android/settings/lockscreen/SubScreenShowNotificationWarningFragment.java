package com.samsung.android.settings.lockscreen;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.android.settings.R;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class SubScreenShowNotificationWarningFragment extends DialogFragment {
    public DialogFragmentCheckListener mDialogFragmentCheckListener;
    public DialogFragmentListener mDialogFragmentListener;
    public int mType;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public interface DialogFragmentCheckListener {}

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public interface DialogFragmentListener {}

    @Override // androidx.fragment.app.DialogFragment,
              // android.content.DialogInterface.OnCancelListener
    public final void onCancel(DialogInterface dialogInterface) {
        Log.d(
                "SubScreenShowNotificationWarningFragment",
                " onCancel method switchchecked: "
                        + getArguments().getInt("arg_switch_checked_state"));
        DialogFragmentListener dialogFragmentListener = this.mDialogFragmentListener;
        int i = getArguments().getInt("arg_switch_checked_state");
        LockScreenNotificationSettings lockScreenNotificationSettings =
                (LockScreenNotificationSettings) dialogFragmentListener;
        lockScreenNotificationSettings.mSwitchBar.setChecked(i != 1);
        lockScreenNotificationSettings.onChanged(i != 1);
    }

    @Override // androidx.fragment.app.DialogFragment
    public final Dialog onCreateDialog(Bundle bundle) {
        this.mType = getArguments().getInt("dialog_type");
        View inflate =
                View.inflate(getActivity(), R.layout.lockscreen_notification_dialog_checkbox, null);
        final CheckBox checkBox =
                (CheckBox) inflate.findViewById(R.id.lockscreen_notification_checkbox);
        checkBox.setText(R.string.sec_lockscreen_notifications_warning_dialog_turn_on_sub_title);
        checkBox.setTextDirection(5);
        checkBox.setChecked(true);
        checkBox.setOnCheckedChangeListener(
                new CompoundButton
                        .OnCheckedChangeListener() { // from class:
                                                     // com.samsung.android.settings.lockscreen.SubScreenShowNotificationWarningFragment.1
                    @Override // android.widget.CompoundButton.OnCheckedChangeListener
                    public final void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                        ((LockScreenNotificationSettings)
                                                SubScreenShowNotificationWarningFragment.this
                                                        .mDialogFragmentCheckListener)
                                        .isCheckedCoverScreenShowNotificationCheckBox =
                                z;
                    }
                });
        int i = this.mType;
        if (i == 0) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle(R.string.sec_lockscreen_notifications_warning_dialog_title);
            builder.setMessage(R.string.sec_lockscreen_notifications_warning_dialog_message);
            final int i2 = 2;
            builder.setPositiveButton(
                    R.string.sec_lockscreen_notifications_warning_dialog_turn_off,
                    new DialogInterface.OnClickListener(
                            this) { // from class:
                                    // com.samsung.android.settings.lockscreen.SubScreenShowNotificationWarningFragment$$ExternalSyntheticLambda0
                        public final /* synthetic */ SubScreenShowNotificationWarningFragment f$0;

                        {
                            this.f$0 = this;
                        }

                        @Override // android.content.DialogInterface.OnClickListener
                        public final void onClick(DialogInterface dialogInterface, int i3) {
                            int i4 = i2;
                            SubScreenShowNotificationWarningFragment
                                    subScreenShowNotificationWarningFragment = this.f$0;
                            switch (i4) {
                                case 0:
                                    ((LockScreenNotificationSettings)
                                                    subScreenShowNotificationWarningFragment
                                                            .mDialogFragmentListener)
                                            .onPositiveClick();
                                    break;
                                case 1:
                                    LockScreenNotificationSettings lockScreenNotificationSettings =
                                            (LockScreenNotificationSettings)
                                                    subScreenShowNotificationWarningFragment
                                                            .mDialogFragmentListener;
                                    lockScreenNotificationSettings.mSwitchBar.setChecked(true);
                                    lockScreenNotificationSettings.onChanged(true);
                                    break;
                                case 2:
                                    ((LockScreenNotificationSettings)
                                                    subScreenShowNotificationWarningFragment
                                                            .mDialogFragmentListener)
                                            .onPositiveClick();
                                    break;
                                case 3:
                                    LockScreenNotificationSettings lockScreenNotificationSettings2 =
                                            (LockScreenNotificationSettings)
                                                    subScreenShowNotificationWarningFragment
                                                            .mDialogFragmentListener;
                                    lockScreenNotificationSettings2.mSwitchBar.setChecked(true);
                                    lockScreenNotificationSettings2.onChanged(true);
                                    break;
                                default:
                                    ((LockScreenNotificationSettings)
                                                    subScreenShowNotificationWarningFragment
                                                            .mDialogFragmentListener)
                                            .onPositiveClick();
                                    break;
                            }
                        }
                    });
            final int i3 = 3;
            builder.setNegativeButton(
                    R.string.sec_lockscreen_notifications_warning_dialog_cancel,
                    new DialogInterface.OnClickListener(
                            this) { // from class:
                                    // com.samsung.android.settings.lockscreen.SubScreenShowNotificationWarningFragment$$ExternalSyntheticLambda0
                        public final /* synthetic */ SubScreenShowNotificationWarningFragment f$0;

                        {
                            this.f$0 = this;
                        }

                        @Override // android.content.DialogInterface.OnClickListener
                        public final void onClick(DialogInterface dialogInterface, int i32) {
                            int i4 = i3;
                            SubScreenShowNotificationWarningFragment
                                    subScreenShowNotificationWarningFragment = this.f$0;
                            switch (i4) {
                                case 0:
                                    ((LockScreenNotificationSettings)
                                                    subScreenShowNotificationWarningFragment
                                                            .mDialogFragmentListener)
                                            .onPositiveClick();
                                    break;
                                case 1:
                                    LockScreenNotificationSettings lockScreenNotificationSettings =
                                            (LockScreenNotificationSettings)
                                                    subScreenShowNotificationWarningFragment
                                                            .mDialogFragmentListener;
                                    lockScreenNotificationSettings.mSwitchBar.setChecked(true);
                                    lockScreenNotificationSettings.onChanged(true);
                                    break;
                                case 2:
                                    ((LockScreenNotificationSettings)
                                                    subScreenShowNotificationWarningFragment
                                                            .mDialogFragmentListener)
                                            .onPositiveClick();
                                    break;
                                case 3:
                                    LockScreenNotificationSettings lockScreenNotificationSettings2 =
                                            (LockScreenNotificationSettings)
                                                    subScreenShowNotificationWarningFragment
                                                            .mDialogFragmentListener;
                                    lockScreenNotificationSettings2.mSwitchBar.setChecked(true);
                                    lockScreenNotificationSettings2.onChanged(true);
                                    break;
                                default:
                                    ((LockScreenNotificationSettings)
                                                    subScreenShowNotificationWarningFragment
                                                            .mDialogFragmentListener)
                                            .onPositiveClick();
                                    break;
                            }
                        }
                    });
            return builder.create();
        }
        if (i == 1) {
            AlertDialog.Builder builder2 = new AlertDialog.Builder(getActivity());
            builder2.setTitle(R.string.sec_cover_screen_show_notifications_warning_dialog_title);
            builder2.setMessage(
                    R.string.sec_cover_screen_show_notifications_warning_dialog_message);
            final int i4 = 0;
            builder2.setPositiveButton(
                    R.string.sec_cover_screen_show_notifications_warning_dialog_turn_on,
                    new DialogInterface.OnClickListener(
                            this) { // from class:
                                    // com.samsung.android.settings.lockscreen.SubScreenShowNotificationWarningFragment$$ExternalSyntheticLambda0
                        public final /* synthetic */ SubScreenShowNotificationWarningFragment f$0;

                        {
                            this.f$0 = this;
                        }

                        @Override // android.content.DialogInterface.OnClickListener
                        public final void onClick(DialogInterface dialogInterface, int i32) {
                            int i42 = i4;
                            SubScreenShowNotificationWarningFragment
                                    subScreenShowNotificationWarningFragment = this.f$0;
                            switch (i42) {
                                case 0:
                                    ((LockScreenNotificationSettings)
                                                    subScreenShowNotificationWarningFragment
                                                            .mDialogFragmentListener)
                                            .onPositiveClick();
                                    break;
                                case 1:
                                    LockScreenNotificationSettings lockScreenNotificationSettings =
                                            (LockScreenNotificationSettings)
                                                    subScreenShowNotificationWarningFragment
                                                            .mDialogFragmentListener;
                                    lockScreenNotificationSettings.mSwitchBar.setChecked(true);
                                    lockScreenNotificationSettings.onChanged(true);
                                    break;
                                case 2:
                                    ((LockScreenNotificationSettings)
                                                    subScreenShowNotificationWarningFragment
                                                            .mDialogFragmentListener)
                                            .onPositiveClick();
                                    break;
                                case 3:
                                    LockScreenNotificationSettings lockScreenNotificationSettings2 =
                                            (LockScreenNotificationSettings)
                                                    subScreenShowNotificationWarningFragment
                                                            .mDialogFragmentListener;
                                    lockScreenNotificationSettings2.mSwitchBar.setChecked(true);
                                    lockScreenNotificationSettings2.onChanged(true);
                                    break;
                                default:
                                    ((LockScreenNotificationSettings)
                                                    subScreenShowNotificationWarningFragment
                                                            .mDialogFragmentListener)
                                            .onPositiveClick();
                                    break;
                            }
                        }
                    });
            final int i5 = 1;
            builder2.setNegativeButton(
                    R.string.sec_cover_screen_show_notifications_warning_dialog_cancel,
                    new DialogInterface.OnClickListener(
                            this) { // from class:
                                    // com.samsung.android.settings.lockscreen.SubScreenShowNotificationWarningFragment$$ExternalSyntheticLambda0
                        public final /* synthetic */ SubScreenShowNotificationWarningFragment f$0;

                        {
                            this.f$0 = this;
                        }

                        @Override // android.content.DialogInterface.OnClickListener
                        public final void onClick(DialogInterface dialogInterface, int i32) {
                            int i42 = i5;
                            SubScreenShowNotificationWarningFragment
                                    subScreenShowNotificationWarningFragment = this.f$0;
                            switch (i42) {
                                case 0:
                                    ((LockScreenNotificationSettings)
                                                    subScreenShowNotificationWarningFragment
                                                            .mDialogFragmentListener)
                                            .onPositiveClick();
                                    break;
                                case 1:
                                    LockScreenNotificationSettings lockScreenNotificationSettings =
                                            (LockScreenNotificationSettings)
                                                    subScreenShowNotificationWarningFragment
                                                            .mDialogFragmentListener;
                                    lockScreenNotificationSettings.mSwitchBar.setChecked(true);
                                    lockScreenNotificationSettings.onChanged(true);
                                    break;
                                case 2:
                                    ((LockScreenNotificationSettings)
                                                    subScreenShowNotificationWarningFragment
                                                            .mDialogFragmentListener)
                                            .onPositiveClick();
                                    break;
                                case 3:
                                    LockScreenNotificationSettings lockScreenNotificationSettings2 =
                                            (LockScreenNotificationSettings)
                                                    subScreenShowNotificationWarningFragment
                                                            .mDialogFragmentListener;
                                    lockScreenNotificationSettings2.mSwitchBar.setChecked(true);
                                    lockScreenNotificationSettings2.onChanged(true);
                                    break;
                                default:
                                    ((LockScreenNotificationSettings)
                                                    subScreenShowNotificationWarningFragment
                                                            .mDialogFragmentListener)
                                            .onPositiveClick();
                                    break;
                            }
                        }
                    });
            return builder2.create();
        }
        if (i != 2) {
            throw new IllegalArgumentException("unknown type " + this.mType);
        }
        AlertDialog.Builder builder3 = new AlertDialog.Builder(getActivity());
        builder3.setTitle(R.string.sec_lockscreen_notifications_warning_dialog_turn_on_title);
        builder3.setView(inflate);
        builder3.setPositiveButton(
                R.string.sec_cover_screen_show_notifications_warning_dialog_turn_on,
                new DialogInterface
                        .OnClickListener() { // from class:
                                             // com.samsung.android.settings.lockscreen.SubScreenShowNotificationWarningFragment$$ExternalSyntheticLambda4
                    @Override // android.content.DialogInterface.OnClickListener
                    public final void onClick(DialogInterface dialogInterface, int i6) {
                        SubScreenShowNotificationWarningFragment
                                subScreenShowNotificationWarningFragment =
                                        SubScreenShowNotificationWarningFragment.this;
                        CheckBox checkBox2 = checkBox;
                        SubScreenShowNotificationWarningFragment.DialogFragmentCheckListener
                                dialogFragmentCheckListener =
                                        subScreenShowNotificationWarningFragment
                                                .mDialogFragmentCheckListener;
                        checkBox2.isChecked();
                        LockScreenNotificationSettings lockScreenNotificationSettings =
                                (LockScreenNotificationSettings) dialogFragmentCheckListener;
                        lockScreenNotificationSettings.onChanged(true);
                        Settings.Secure.putIntForUser(
                                lockScreenNotificationSettings.getContentResolver(),
                                "cover_screen_show_notification",
                                lockScreenNotificationSettings
                                                .isCheckedCoverScreenShowNotificationCheckBox
                                        ? 1
                                        : 0,
                                -2);
                        LockScreenNotificationSettings lockScreenNotificationSettings2 =
                                (LockScreenNotificationSettings)
                                        subScreenShowNotificationWarningFragment
                                                .mDialogFragmentListener;
                        lockScreenNotificationSettings2.mSwitchBar.setChecked(true);
                        lockScreenNotificationSettings2.onChanged(true);
                    }
                });
        final int i6 = 4;
        builder3.setNegativeButton(
                R.string.sec_lockscreen_notifications_warning_dialog_cancel,
                new DialogInterface.OnClickListener(
                        this) { // from class:
                                // com.samsung.android.settings.lockscreen.SubScreenShowNotificationWarningFragment$$ExternalSyntheticLambda0
                    public final /* synthetic */ SubScreenShowNotificationWarningFragment f$0;

                    {
                        this.f$0 = this;
                    }

                    @Override // android.content.DialogInterface.OnClickListener
                    public final void onClick(DialogInterface dialogInterface, int i32) {
                        int i42 = i6;
                        SubScreenShowNotificationWarningFragment
                                subScreenShowNotificationWarningFragment = this.f$0;
                        switch (i42) {
                            case 0:
                                ((LockScreenNotificationSettings)
                                                subScreenShowNotificationWarningFragment
                                                        .mDialogFragmentListener)
                                        .onPositiveClick();
                                break;
                            case 1:
                                LockScreenNotificationSettings lockScreenNotificationSettings =
                                        (LockScreenNotificationSettings)
                                                subScreenShowNotificationWarningFragment
                                                        .mDialogFragmentListener;
                                lockScreenNotificationSettings.mSwitchBar.setChecked(true);
                                lockScreenNotificationSettings.onChanged(true);
                                break;
                            case 2:
                                ((LockScreenNotificationSettings)
                                                subScreenShowNotificationWarningFragment
                                                        .mDialogFragmentListener)
                                        .onPositiveClick();
                                break;
                            case 3:
                                LockScreenNotificationSettings lockScreenNotificationSettings2 =
                                        (LockScreenNotificationSettings)
                                                subScreenShowNotificationWarningFragment
                                                        .mDialogFragmentListener;
                                lockScreenNotificationSettings2.mSwitchBar.setChecked(true);
                                lockScreenNotificationSettings2.onChanged(true);
                                break;
                            default:
                                ((LockScreenNotificationSettings)
                                                subScreenShowNotificationWarningFragment
                                                        .mDialogFragmentListener)
                                        .onPositiveClick();
                                break;
                        }
                    }
                });
        return builder3.create();
    }
}
