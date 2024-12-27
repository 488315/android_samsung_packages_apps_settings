package com.android.settings.spa.app.appcompat;

import android.content.pm.ApplicationInfo;

import androidx.compose.animation.TransitionData$$ExternalSyntheticOutline0;
import androidx.compose.animation.core.KeyframesSpec$KeyframeEntity$$ExternalSyntheticOutline0;

import com.android.settingslib.spaprivileged.model.app.AppRecord;

import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class UserAspectRatioAppListItemModel implements AppRecord {
    public final ApplicationInfo app;
    public final boolean canDisplay;
    public final boolean suggested;
    public final int userOverride;

    public UserAspectRatioAppListItemModel(ApplicationInfo app, int i, boolean z, boolean z2) {
        Intrinsics.checkNotNullParameter(app, "app");
        this.app = app;
        this.userOverride = i;
        this.suggested = z;
        this.canDisplay = z2;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof UserAspectRatioAppListItemModel)) {
            return false;
        }
        UserAspectRatioAppListItemModel userAspectRatioAppListItemModel =
                (UserAspectRatioAppListItemModel) obj;
        return Intrinsics.areEqual(this.app, userAspectRatioAppListItemModel.app)
                && this.userOverride == userAspectRatioAppListItemModel.userOverride
                && this.suggested == userAspectRatioAppListItemModel.suggested
                && this.canDisplay == userAspectRatioAppListItemModel.canDisplay;
    }

    @Override // com.android.settingslib.spaprivileged.model.app.AppRecord
    public final ApplicationInfo getApp() {
        return this.app;
    }

    public final int hashCode() {
        return Boolean.hashCode(this.canDisplay)
                + TransitionData$$ExternalSyntheticOutline0.m(
                        KeyframesSpec$KeyframeEntity$$ExternalSyntheticOutline0.m(
                                this.userOverride, this.app.hashCode() * 31, 31),
                        31,
                        this.suggested);
    }

    public final String toString() {
        return "UserAspectRatioAppListItemModel(app="
                + this.app
                + ", userOverride="
                + this.userOverride
                + ", suggested="
                + this.suggested
                + ", canDisplay="
                + this.canDisplay
                + ")";
    }
}
