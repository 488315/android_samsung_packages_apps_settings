package com.android.settings.enterprise;

import android.app.Activity;

import androidx.appcompat.app.AlertDialog;

import com.android.settingslib.enterprise.ActionDisabledLearnMoreButtonLauncher;

import java.util.Objects;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class ActionDisabledLearnMoreButtonLauncherImpl
        extends ActionDisabledLearnMoreButtonLauncher {
    public final Activity mActivity;
    public final AlertDialog.Builder mBuilder;

    public ActionDisabledLearnMoreButtonLauncherImpl(
            Activity activity, AlertDialog.Builder builder) {
        Objects.requireNonNull(activity, "activity cannot be null");
        this.mActivity = activity;
        Objects.requireNonNull(builder, "builder cannot be null");
        this.mBuilder = builder;
    }
}
