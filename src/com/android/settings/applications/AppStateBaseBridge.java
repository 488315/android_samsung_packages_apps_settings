package com.android.settings.applications;

import android.content.pm.ApplicationInfo;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import com.android.settingslib.applications.ApplicationsState;

import java.util.ArrayList;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class AppStateBaseBridge implements ApplicationsState.Callbacks {
    public final ApplicationsState.Session mAppSession;
    public final Callback mCallback;
    public boolean mForceLoadAllApps;
    public final MainHandler mHandler;
    public final MainHandler mMainHandler;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public interface Callback {
        void onExtraInfoUpdated();
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class MainHandler extends Handler {
        public final /* synthetic */ int $r8$classId;
        public final /* synthetic */ AppStateBaseBridge this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public /* synthetic */ MainHandler(
                AppStateBaseBridge appStateBaseBridge, Looper looper, int i) {
            super(looper);
            this.$r8$classId = i;
            this.this$0 = appStateBaseBridge;
        }

        @Override // android.os.Handler
        public final void handleMessage(Message message) {
            switch (this.$r8$classId) {
                case 0:
                    if (message.what == 1) {
                        this.this$0.mCallback.onExtraInfoUpdated();
                        break;
                    }
                    break;
                default:
                    int i = message.what;
                    AppStateBaseBridge appStateBaseBridge = this.this$0;
                    if (i == 1) {
                        appStateBaseBridge.loadAllExtraInfo();
                        appStateBaseBridge.mMainHandler.sendEmptyMessage(1);
                        break;
                    } else if (i == 2) {
                        ArrayList allApps = appStateBaseBridge.mAppSession.getAllApps();
                        int size = allApps.size();
                        String str = (String) message.obj;
                        int i2 = message.arg1;
                        for (int i3 = 0; i3 < size; i3++) {
                            ApplicationsState.AppEntry appEntry =
                                    (ApplicationsState.AppEntry) allApps.get(i3);
                            ApplicationInfo applicationInfo = appEntry.info;
                            if (applicationInfo.uid == i2
                                    && str.equals(applicationInfo.packageName)) {
                                appStateBaseBridge.updateExtraInfo(appEntry, str, i2);
                            }
                        }
                        appStateBaseBridge.mMainHandler.sendEmptyMessage(1);
                        break;
                    }
                    break;
            }
        }
    }

    public AppStateBaseBridge(ApplicationsState applicationsState, Callback callback) {
        this.mAppSession =
                applicationsState != null ? applicationsState.newSession(this, null) : null;
        this.mCallback = callback;
        this.mHandler =
                new MainHandler(
                        this,
                        applicationsState != null
                                ? applicationsState.mThread.getLooper()
                                : Looper.getMainLooper(),
                        1);
        this.mMainHandler = new MainHandler(this, Looper.getMainLooper(), 0);
    }

    public final void forceUpdate(int i, String str) {
        this.mHandler.obtainMessage(2, i, 0, str).sendToTarget();
    }

    public abstract void loadAllExtraInfo();

    @Override // com.android.settingslib.applications.ApplicationsState.Callbacks
    public final void onLoadEntriesCompleted() {
        this.mHandler.sendEmptyMessage(1);
    }

    @Override // com.android.settingslib.applications.ApplicationsState.Callbacks
    public final void onPackageListChanged() {
        this.mHandler.sendEmptyMessage(1);
    }

    public final void pause() {
        if (this.mForceLoadAllApps) {
            this.mAppSession.onPause();
            return;
        }
        ApplicationsState.Session session = this.mAppSession;
        synchronized (ApplicationsState.this.mEntriesMap) {
            try {
                if (session.mResumed) {
                    session.mResumed = false;
                    ApplicationsState.this.mSessionsChanged = true;
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void release() {
        this.mAppSession.onDestroy();
    }

    public final void resume(boolean z) {
        this.mForceLoadAllApps = z;
        if (z) {
            this.mAppSession.onResume();
            return;
        }
        ApplicationsState.Session session = this.mAppSession;
        synchronized (ApplicationsState.this.mEntriesMap) {
            try {
                if (!session.mResumed) {
                    session.mResumed = true;
                    ApplicationsState.this.mSessionsChanged = true;
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public abstract void updateExtraInfo(ApplicationsState.AppEntry appEntry, String str, int i);

    @Override // com.android.settingslib.applications.ApplicationsState.Callbacks
    public final void onAllSizesComputed() {}

    @Override // com.android.settingslib.applications.ApplicationsState.Callbacks
    public final void onLauncherInfoChanged() {}

    @Override // com.android.settingslib.applications.ApplicationsState.Callbacks
    public final void onPackageIconChanged() {}

    @Override // com.android.settingslib.applications.ApplicationsState.Callbacks
    public final void onPackageSizeChanged(String str) {}

    @Override // com.android.settingslib.applications.ApplicationsState.Callbacks
    public final void onRebuildComplete(ArrayList arrayList) {}

    @Override // com.android.settingslib.applications.ApplicationsState.Callbacks
    public final void onRunningStateChanged(boolean z) {}
}
