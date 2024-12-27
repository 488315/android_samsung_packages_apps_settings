package com.samsung.android.settings.galaxysystemupdate.apps;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.android.settings.R;
import com.android.settings.Utils;

import com.samsung.android.settings.logging.LoggingHelper;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class GalaxySystemAppsInfoDialogFragment extends DialogFragment {
    public AlertDialog dialog;
    public boolean isSingular;
    public DeleteGalaxySystemAppsFragment$$ExternalSyntheticLambda0 listener;

    @Override // androidx.fragment.app.DialogFragment
    public final Dialog onCreateDialog(Bundle bundle) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        if (this.isSingular) {
            builder.setTitle(R.string.galaxy_system_app_uninstall_update);
            builder.setMessage(
                    Utils.isTablet()
                            ? R.string
                                    .galaxy_system_app_tablet_this_update_will_be_uninstalled_and_the_app_will_go_back
                            : R.string
                                    .galaxy_system_app_phone_this_update_will_be_uninstalled_and_the_app_will_go_back);
        } else {
            builder.setTitle(R.string.galaxy_system_app_uninstall_updates);
            builder.setMessage(
                    Utils.isTablet()
                            ? R.string
                                    .galaxy_system_app_tablet_these_updates_will_be_uninstalled_and_the_apps_will_go_back
                            : R.string
                                    .galaxy_system_app_phone_these_updates_will_be_uninstalled_and_the_apps_will_go_back);
        }
        builder.setPositiveButton(
                R.string.galaxy_system_app_uninstall_restart,
                (DialogInterface.OnClickListener) null);
        builder.setNegativeButton(R.string.cancel, (DialogInterface.OnClickListener) null);
        AlertDialog create = builder.create();
        this.dialog = create;
        create.setOnShowListener(
                new DialogInterface
                        .OnShowListener() { // from class:
                                            // com.samsung.android.settings.galaxysystemupdate.apps.GalaxySystemAppsInfoDialogFragment$$ExternalSyntheticLambda0
                    @Override // android.content.DialogInterface.OnShowListener
                    public final void onShow(DialogInterface dialogInterface) {
                        final GalaxySystemAppsInfoDialogFragment
                                galaxySystemAppsInfoDialogFragment =
                                        GalaxySystemAppsInfoDialogFragment.this;
                        final int i = 0;
                        galaxySystemAppsInfoDialogFragment
                                .dialog
                                .getButton(-1)
                                .setOnClickListener(
                                        new View
                                                .OnClickListener() { // from class:
                                                                     // com.samsung.android.settings.galaxysystemupdate.apps.GalaxySystemAppsInfoDialogFragment$$ExternalSyntheticLambda1
                                            @Override // android.view.View.OnClickListener
                                            public final void onClick(View view) {
                                                int i2 = i;
                                                GalaxySystemAppsInfoDialogFragment
                                                        galaxySystemAppsInfoDialogFragment2 =
                                                                galaxySystemAppsInfoDialogFragment;
                                                galaxySystemAppsInfoDialogFragment2.getClass();
                                                switch (i2) {
                                                    case 0:
                                                        LoggingHelper.insertEventLogging(
                                                                60180, 60182, 1);
                                                        galaxySystemAppsInfoDialogFragment2.listener
                                                                .onClickResult(true);
                                                        break;
                                                    default:
                                                        LoggingHelper.insertEventLogging(
                                                                60180, 60182, -1);
                                                        galaxySystemAppsInfoDialogFragment2.listener
                                                                .onClickResult(false);
                                                        galaxySystemAppsInfoDialogFragment2.dialog
                                                                .dismiss();
                                                        break;
                                                }
                                            }
                                        });
                        final int i2 = 1;
                        galaxySystemAppsInfoDialogFragment
                                .dialog
                                .getButton(-2)
                                .setOnClickListener(
                                        new View
                                                .OnClickListener() { // from class:
                                                                     // com.samsung.android.settings.galaxysystemupdate.apps.GalaxySystemAppsInfoDialogFragment$$ExternalSyntheticLambda1
                                            @Override // android.view.View.OnClickListener
                                            public final void onClick(View view) {
                                                int i22 = i2;
                                                GalaxySystemAppsInfoDialogFragment
                                                        galaxySystemAppsInfoDialogFragment2 =
                                                                galaxySystemAppsInfoDialogFragment;
                                                galaxySystemAppsInfoDialogFragment2.getClass();
                                                switch (i22) {
                                                    case 0:
                                                        LoggingHelper.insertEventLogging(
                                                                60180, 60182, 1);
                                                        galaxySystemAppsInfoDialogFragment2.listener
                                                                .onClickResult(true);
                                                        break;
                                                    default:
                                                        LoggingHelper.insertEventLogging(
                                                                60180, 60182, -1);
                                                        galaxySystemAppsInfoDialogFragment2.listener
                                                                .onClickResult(false);
                                                        galaxySystemAppsInfoDialogFragment2.dialog
                                                                .dismiss();
                                                        break;
                                                }
                                            }
                                        });
                    }
                });
        return this.dialog;
    }
}
