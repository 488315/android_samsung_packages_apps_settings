package com.google.android.setupcompat.internal;

import android.os.Bundle;
import android.os.RemoteException;

import com.google.android.setupcompat.ISetupCompatService;
import com.google.android.setupcompat.util.Logger;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final /* synthetic */ class SetupCompatServiceInvoker$$ExternalSyntheticLambda0
        implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ SetupCompatServiceInvoker f$0;
    public final /* synthetic */ String f$1;
    public final /* synthetic */ Bundle f$2;

    public /* synthetic */ SetupCompatServiceInvoker$$ExternalSyntheticLambda0(
            SetupCompatServiceInvoker setupCompatServiceInvoker, String str, Bundle bundle, int i) {
        this.$r8$classId = i;
        this.f$0 = setupCompatServiceInvoker;
        this.f$1 = str;
        this.f$2 = bundle;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                SetupCompatServiceInvoker setupCompatServiceInvoker = this.f$0;
                String str = this.f$1;
                Bundle bundle = this.f$2;
                setupCompatServiceInvoker.getClass();
                Logger logger = SetupCompatServiceInvoker.LOG;
                try {
                    ISetupCompatService service =
                            SetupCompatServiceProvider.getInstance(
                                            setupCompatServiceInvoker.context)
                                    .getService(
                                            setupCompatServiceInvoker
                                                    .waitTimeInMillisForServiceConnection,
                                            TimeUnit.MILLISECONDS);
                    if (service != null) {
                        ((ISetupCompatService.Stub.Proxy) service).validateActivity(bundle, str);
                    } else {
                        logger.w(
                                "BindBack failed since service reference is null. Are the"
                                    + " permissions valid?");
                    }
                    break;
                } catch (RemoteException | InterruptedException | TimeoutException e) {
                    logger.e(
                            "Exception occurred while " + str + " trying bind back to SetupWizard.",
                            e);
                    return;
                }
            default:
                SetupCompatServiceInvoker setupCompatServiceInvoker2 = this.f$0;
                String str2 = this.f$1;
                Bundle bundle2 = this.f$2;
                setupCompatServiceInvoker2.getClass();
                Logger logger2 = SetupCompatServiceInvoker.LOG;
                try {
                    ISetupCompatService service2 =
                            SetupCompatServiceProvider.getInstance(
                                            setupCompatServiceInvoker2.context)
                                    .getService(
                                            setupCompatServiceInvoker2
                                                    .waitTimeInMillisForServiceConnection,
                                            TimeUnit.MILLISECONDS);
                    if (service2 != null) {
                        ((ISetupCompatService.Stub.Proxy) service2).onFocusStatusChanged(bundle2);
                    } else {
                        logger2.w(
                                "Report focusChange failed since service reference is null. Are the"
                                    + " permission valid?");
                    }
                    break;
                } catch (RemoteException
                        | InterruptedException
                        | UnsupportedOperationException
                        | TimeoutException e2) {
                    logger2.e(
                            "Exception occurred while "
                                    + str2
                                    + " trying report windowFocusChange to SetupWizard.",
                            e2);
                    return;
                }
        }
    }
}
