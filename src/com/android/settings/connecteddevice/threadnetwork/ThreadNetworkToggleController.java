package com.android.settings.connecteddevice.threadnetwork;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.thread.ThreadNetworkController;
import android.net.thread.ThreadNetworkException;
import android.os.OutcomeReceiver;
import android.util.Log;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleEventObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;

import com.android.settings.R;
import com.android.settings.core.TogglePreferenceController;
import com.android.settings.network.telephony.ToggleSubscriptionDialogActivity;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.settings.cube.Controllable$ControllableType;
import com.samsung.android.settings.goodsettings.GoodSettingsContract;
import com.sec.ims.IMSParameter;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

import java.util.List;
import java.util.concurrent.Executor;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
@Metadata(
        d1 = {
            "\u0000b\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\u0018\u0002\n"
                + "\u0000\n"
                + "\u0002\u0018\u0002\n"
                + "\u0000\n"
                + "\u0002\u0010\u000e\n"
                + "\u0002\b\u0002\n"
                + "\u0002\u0018\u0002\n"
                + "\u0000\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\b\u0002\n"
                + "\u0002\u0010\u000b\n"
                + "\u0002\b\u0002\n"
                + "\u0002\u0018\u0002\n"
                + "\u0000\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\b\u0002\n"
                + "\u0002\u0010\u0002\n"
                + "\u0000\n"
                + "\u0002\u0018\u0002\n"
                + "\u0000\n"
                + "\u0002\u0010\b\n"
                + "\u0002\b\u0005\n"
                + "\u0002\u0018\u0002\n"
                + "\u0000\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\b\u0003\b\u0007\u0018\u0000"
                + " $2\u00020\u00012\u00020\u0002:\u0001$B\u0017\b\u0016\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0006¢\u0006\u0002\u0010\u0007B)\b\u0007\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\b\u001a\u00020"
                + "\t\u0012\b\u0010\n"
                + "\u001a\u0004\u0018\u00010\u000b¢\u0006\u0002\u0010\fJ\u0010\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u0018H\u0016J\b\u0010\u0019\u001a\u00020\u001aH\u0016J\b\u0010\u001b\u001a\u00020\u001aH\u0016J\b\u0010\u001c\u001a\u00020\u000eH\u0016J\b\u0010\u001d\u001a\u00020\u0013H\u0002J\u0018\u0010\u001e\u001a\u00020\u00162\u0006\u0010\u001f\u001a\u00020"
                + " 2\u0006\u0010!\u001a\u00020\"H\u0016J\u0010\u0010#\u001a\u00020\u000e2\u0006\u0010\u001c\u001a\u00020\u000eH\u0016R\u000e\u0010\b\u001a\u00020"
                + "\tX\u0082\u0004¢\u0006\u0002\n"
                + "\u0000R\u0011\u0010\r"
                + "\u001a\u00020\u000e8F¢\u0006\u0006\u001a\u0004\b\r"
                + "\u0010\u000fR\u0010\u0010\u0010\u001a\u0004\u0018\u00010\u0011X\u0082\u000e¢\u0006\u0002\n"
                + "\u0000R\u000e\u0010\u0012\u001a\u00020\u0013X\u0082\u0004¢\u0006\u0002\n"
                + "\u0000R\u0010\u0010\n"
                + "\u001a\u0004\u0018\u00010\u000bX\u0082\u0004¢\u0006\u0002\n"
                + "\u0000R\u000e\u0010\u0014\u001a\u00020\u000eX\u0082\u000e¢\u0006\u0002\n"
                + "\u0000¨\u0006%"
        },
        d2 = {
            "Lcom/android/settings/connecteddevice/threadnetwork/ThreadNetworkToggleController;",
            "Lcom/android/settings/core/TogglePreferenceController;",
            "Landroidx/lifecycle/LifecycleEventObserver;",
            "context",
            "Landroid/content/Context;",
            GoodSettingsContract.EXTRA_NAME.POLICY_KEY,
            ApnSettings.MVNO_NONE,
            "(Landroid/content/Context;Ljava/lang/String;)V",
            "executor",
            "Ljava/util/concurrent/Executor;",
            "threadController",
            "Lcom/android/settings/connecteddevice/threadnetwork/BaseThreadNetworkController;",
            "(Landroid/content/Context;Ljava/lang/String;Ljava/util/concurrent/Executor;Lcom/android/settings/connecteddevice/threadnetwork/BaseThreadNetworkController;)V",
            "isThreadSupportedOnDevice",
            ApnSettings.MVNO_NONE,
            "()Z",
            "preference",
            "Landroidx/preference/Preference;",
            "stateCallback",
            "Landroid/net/thread/ThreadNetworkController$StateCallback;",
            "threadEnabled",
            "displayPreference",
            ApnSettings.MVNO_NONE,
            "screen",
            "Landroidx/preference/PreferenceScreen;",
            "getAvailabilityStatus",
            ApnSettings.MVNO_NONE,
            "getSliceHighlightMenuRes",
            "isChecked",
            "newStateCallback",
            "onStateChanged",
            "source",
            "Landroidx/lifecycle/LifecycleOwner;",
            IMSParameter.CALL.EVENT,
            "Landroidx/lifecycle/Lifecycle$Event;",
            "setChecked",
            "Companion",
            "applications__sources__apps__SecSettings__android_common__SecSettings-core"
        },
        k = 1,
        mv = {1, 9, 0},
        xi = 48)
/* loaded from: classes2.dex */
public final class ThreadNetworkToggleController extends TogglePreferenceController
        implements LifecycleEventObserver {
    public static final int $stable = 8;
    private static final String TAG = "ThreadNetworkSettings";
    private final Executor executor;
    private Preference preference;
    private final ThreadNetworkController.StateCallback stateCallback;
    private final BaseThreadNetworkController threadController;
    private boolean threadEnabled;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public abstract /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[Lifecycle.Event.values().length];
            try {
                iArr[Lifecycle.Event.ON_START.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[Lifecycle.Event.ON_STOP.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ThreadNetworkToggleController(
            Context context,
            String key,
            Executor executor,
            BaseThreadNetworkController baseThreadNetworkController) {
        super(context, key);
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(key, "key");
        Intrinsics.checkNotNullParameter(executor, "executor");
        this.executor = executor;
        this.threadController = baseThreadNetworkController;
        this.stateCallback = newStateCallback();
    }

    private final ThreadNetworkController.StateCallback newStateCallback() {
        return new ThreadNetworkController
                .StateCallback() { // from class:
                                   // com.android.settings.connecteddevice.threadnetwork.ThreadNetworkToggleController$newStateCallback$1
            public final void onThreadEnableStateChanged(int i) {
                Preference preference;
                ThreadNetworkToggleController.this.threadEnabled = i == 1;
                preference = ThreadNetworkToggleController.this.preference;
                if (preference != null) {
                    ThreadNetworkToggleController.this.updateState(preference);
                }
            }

            public final void onDeviceRoleChanged(int i) {}
        };
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen screen) {
        Intrinsics.checkNotNullParameter(screen, "screen");
        super.displayPreference(screen);
        this.preference = screen.findPreference(getPreferenceKey());
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        return 2;
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ Class getBackgroundWorkerClass() {
        return null;
    }

    @Override // com.android.settings.core.TogglePreferenceController
    public /* bridge */ /* synthetic */ List getBackupKeys() {
        return super.getBackupKeys();
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ IntentFilter getIntentFilter() {
        return null;
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Intent getLaunchIntent() {
        return super.getLaunchIntent();
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public int getSliceHighlightMenuRes() {
        return R.string.menu_key_connected_devices;
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ String getStatusText() {
        return super.getStatusText();
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean hasAsyncUpdate() {
        return false;
    }

    @Override // com.android.settings.core.TogglePreferenceController
    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {
        super.ignoreUserInteraction();
    }

    @Override // com.android.settings.core.TogglePreferenceController
    /* renamed from: isChecked, reason: from getter */
    public boolean getThreadEnabled() {
        return this.threadEnabled;
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean isControllable() {
        return super.isControllable();
    }

    public final boolean isThreadSupportedOnDevice() {
        return this.threadController != null;
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Controllable$ControllableType needUserInteraction(
            Object obj) {
        return super.needUserInteraction(obj);
    }

    @Override // androidx.lifecycle.LifecycleEventObserver
    public void onStateChanged(LifecycleOwner source, Lifecycle.Event event) {
        Intrinsics.checkNotNullParameter(source, "source");
        Intrinsics.checkNotNullParameter(event, "event");
        if (this.threadController == null) {
            return;
        }
        int i = WhenMappings.$EnumSwitchMapping$0[event.ordinal()];
        if (i == 1) {
            this.threadController.registerStateCallback(this.executor, this.stateCallback);
        } else {
            if (i != 2) {
                return;
            }
            this.threadController.unregisterStateCallback(this.stateCallback);
        }
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return super.runDefaultAction();
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r3v0, types: [com.android.settings.connecteddevice.threadnetwork.ThreadNetworkToggleController$setChecked$1] */
    @Override // com.android.settings.core.TogglePreferenceController
    public boolean setChecked(boolean isChecked) {
        if (this.threadController == null) {
            return false;
        }
        if (isChecked == getThreadEnabled()) {
            return true;
        }
        final String str = isChecked ? ToggleSubscriptionDialogActivity.ARG_enable : "disable";
        this.threadController.setEnabled(
                isChecked,
                this.executor,
                new OutcomeReceiver() { // from class:
                                        // com.android.settings.connecteddevice.threadnetwork.ThreadNetworkToggleController$setChecked$1
                    @Override // android.os.OutcomeReceiver
                    public final void onError(Throwable th) {
                        ThreadNetworkException e = (ThreadNetworkException) th;
                        Intrinsics.checkNotNullParameter(e, "e");
                        Log.e("ThreadNetworkSettings", "Failed to " + str + " Thread", e);
                    }

                    @Override // android.os.OutcomeReceiver
                    public final void onResult(Object obj) {
                        Log.d("ThreadNetworkSettings", "Successfully " + str + " Thread");
                    }
                });
        return true;
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }

    /* JADX WARN: Illegal instructions before constructor call */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public ThreadNetworkToggleController(android.content.Context r4, java.lang.String r5) {
        /*
            r3 = this;
            java.lang.String r0 = "context"
            kotlin.jvm.internal.Intrinsics.checkNotNullParameter(r4, r0)
            java.lang.String r0 = "key"
            kotlin.jvm.internal.Intrinsics.checkNotNullParameter(r5, r0)
            java.util.concurrent.Executor r0 = r4.getMainExecutor()
            java.lang.String r1 = "getMainExecutor(...)"
            kotlin.jvm.internal.Intrinsics.checkNotNullExpressionValue(r0, r1)
            android.content.pm.PackageManager r1 = r4.getPackageManager()
            java.lang.String r2 = "android.hardware.thread_network"
            boolean r1 = r1.hasSystemFeature(r2)
            r2 = 0
            if (r1 != 0) goto L21
            goto L3c
        L21:
            java.lang.Class<android.net.thread.ThreadNetworkManager> r1 = android.net.thread.ThreadNetworkManager.class
            java.lang.Object r1 = r4.getSystemService(r1)
            android.net.thread.ThreadNetworkManager r1 = (android.net.thread.ThreadNetworkManager) r1
            if (r1 != 0) goto L2c
            goto L3c
        L2c:
            java.util.List r1 = r1.getAllThreadNetworkControllers()
            r2 = 0
            java.lang.Object r1 = r1.get(r2)
            android.net.thread.ThreadNetworkController r1 = (android.net.thread.ThreadNetworkController) r1
            com.android.settings.connecteddevice.threadnetwork.ThreadNetworkUtils$getThreadNetworkController$1 r2 = new com.android.settings.connecteddevice.threadnetwork.ThreadNetworkUtils$getThreadNetworkController$1
            r2.<init>(r1)
        L3c:
            r3.<init>(r4, r5, r0, r2)
            return
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.settings.connecteddevice.threadnetwork.ThreadNetworkToggleController.<init>(android.content.Context,"
                    + " java.lang.String):void");
    }
}