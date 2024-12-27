package com.android.settings.spa.app.specialaccess;

import android.content.pm.ApplicationInfo;

import androidx.appcompat.app.AppCompatDelegateImpl$$ExternalSyntheticOutline0;

import com.android.settingslib.spaprivileged.model.app.AppRecord;

import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class NfcTagAppsSettingsRecord implements AppRecord {
    public final ApplicationInfo app;
    public final NfcTagAppsSettingsController controller;
    public final boolean isSupported;

    public NfcTagAppsSettingsRecord(
            ApplicationInfo app,
            NfcTagAppsSettingsController nfcTagAppsSettingsController,
            boolean z) {
        Intrinsics.checkNotNullParameter(app, "app");
        this.app = app;
        this.controller = nfcTagAppsSettingsController;
        this.isSupported = z;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof NfcTagAppsSettingsRecord)) {
            return false;
        }
        NfcTagAppsSettingsRecord nfcTagAppsSettingsRecord = (NfcTagAppsSettingsRecord) obj;
        return Intrinsics.areEqual(this.app, nfcTagAppsSettingsRecord.app)
                && Intrinsics.areEqual(this.controller, nfcTagAppsSettingsRecord.controller)
                && this.isSupported == nfcTagAppsSettingsRecord.isSupported;
    }

    @Override // com.android.settingslib.spaprivileged.model.app.AppRecord
    public final ApplicationInfo getApp() {
        return this.app;
    }

    public final int hashCode() {
        return Boolean.hashCode(this.isSupported)
                + ((this.controller.hashCode() + (this.app.hashCode() * 31)) * 31);
    }

    public final String toString() {
        ApplicationInfo applicationInfo = this.app;
        StringBuilder sb = new StringBuilder("NfcTagAppsSettingsRecord(app=");
        sb.append(applicationInfo);
        sb.append(", controller=");
        sb.append(this.controller);
        sb.append(", isSupported=");
        return AppCompatDelegateImpl$$ExternalSyntheticOutline0.m(sb, this.isSupported, ")");
    }
}
