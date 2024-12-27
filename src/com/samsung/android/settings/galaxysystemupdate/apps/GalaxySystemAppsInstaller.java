package com.samsung.android.settings.galaxysystemupdate.apps;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInstaller;
import android.net.Uri;
import android.os.Handler;
import android.util.Log;

import com.android.settings.AirplaneModeEnabler$$ExternalSyntheticOutline0;

import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class GalaxySystemAppsInstaller {
    public Uri apexPath;
    public Context context;
    public Handler handler;
    public List listeners;
    public PackageInstaller packageInstaller;
    public PackageInstallerListener packageInstallerListener;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class PackageInstallerListener extends BroadcastReceiver {
        public PackageInstallerListener() {}

        @Override // android.content.BroadcastReceiver
        public final void onReceive(Context context, Intent intent) {
            if (intent == null) {
                Log.i("GalaxySystemAppsInstaller", "intent is null");
                return;
            }
            String action = intent.getAction();
            if (action.equals("android.content.pm.action.SESSION_UPDATED")) {
                PackageInstaller.SessionInfo sessionInfo =
                        (PackageInstaller.SessionInfo)
                                intent.getParcelableExtra("android.content.pm.extra.SESSION");
                Log.i(
                        "GalaxySystemAppsInstaller",
                        " package : "
                                + sessionInfo.getAppPackageName()
                                + " session id : "
                                + sessionInfo.getSessionId()
                                + " install success");
                GalaxySystemAppsInstaller.this.notifyResult(true);
                GalaxySystemAppsInstaller.this.context.unregisterReceiver(this);
                return;
            }
            Log.i(
                    "GalaxySystemAppsInstaller",
                    action
                            + " : result ["
                            + intent.getIntExtra("android.content.pm.extra.LEGACY_STATUS", 1)
                            + "], message ["
                            + intent.getStringExtra("android.content.pm.extra.STATUS_MESSAGE")
                            + "], packageName ["
                            + intent.getStringExtra("android.content.pm.extra.PACKAGE_NAME")
                            + "]");
        }
    }

    public final void notifyResult(final boolean z) {
        this.handler.post(
                new Runnable() { // from class:
                                 // com.samsung.android.settings.galaxysystemupdate.apps.GalaxySystemAppsInstaller$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        GalaxySystemAppsInstaller galaxySystemAppsInstaller =
                                GalaxySystemAppsInstaller.this;
                        boolean z2 = z;
                        for (DeleteGalaxySystemAppsFragment.AnonymousClass1 anonymousClass1 :
                                galaxySystemAppsInstaller.listeners) {
                            anonymousClass1.getClass();
                            int i = DeleteGalaxySystemAppsFragment.$r8$clinit;
                            AirplaneModeEnabler$$ExternalSyntheticOutline0.m(
                                    "onResult : ", "DeleteGalaxySystemAppsFragment", z2);
                            DeleteGalaxySystemAppsFragment.this.downgradeGalaxySystemApps();
                            galaxySystemAppsInstaller.listeners.remove(anonymousClass1);
                        }
                        galaxySystemAppsInstaller.listeners.clear();
                    }
                });
    }
}
