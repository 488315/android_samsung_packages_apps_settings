package com.samsung.android.gtscell;

import android.os.Bundle;
import android.os.Handler;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;

import androidx.annotation.Keep;

import com.samsung.android.knox.net.apn.ApnSettings;

import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
@Keep
@Metadata(
        bv = {1, 0, 3},
        d1 = {
            "\u0000:\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\u0018\u0002\n"
                + "\u0000\n"
                + "\u0002\u0018\u0002\n"
                + "\u0000\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\b\u0002\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\b\u0002\n"
                + "\u0002\u0018\u0002\n"
                + "\u0000\n"
                + "\u0002\u0010\b\n"
                + "\u0000\n"
                + "\u0002\u0010\u0002\n"
                + "\u0000\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\b\u0005\b\u0007\u0018\u0000"
                + " \u00142\u00020\u0001:\u0002\u0014\u0015B\u001b\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\n"
                + "\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0005¢\u0006\u0002\u0010\u0006B\u000f\b\u0010\u0012\u0006\u0010\u0007\u001a\u00020\b¢\u0006\u0002\u0010"
                + "\tJ\b\u0010\f\u001a\u00020\r"
                + "H\u0016J\u000e\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u0011J\u0018\u0010\u0012\u001a\u00020\u000f2\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\u0013\u001a\u00020\r"
                + "H\u0016R\u000e\u0010\n"
                + "\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n"
                + "\u0000R\u0010\u0010\u0004\u001a\u0004\u0018\u00010\u0005X\u0082\u0004¢\u0006\u0002\n"
                + "\u0000R\u0010\u0010\u0002\u001a\u0004\u0018\u00010\u0003X\u0082\u0004¢\u0006\u0002\n"
                + "\u0000¨\u0006\u0016"
        },
        d2 = {
            "Lcom/samsung/android/gtscell/RemoteCallback;",
            "Landroid/os/Parcelable;",
            "listener",
            "Lcom/samsung/android/gtscell/RemoteCallback$OnResultListener;",
            "handler",
            "Landroid/os/Handler;",
            "(Lcom/samsung/android/gtscell/RemoteCallback$OnResultListener;Landroid/os/Handler;)V",
            "parcel",
            "Landroid/os/Parcel;",
            "(Landroid/os/Parcel;)V",
            "callback",
            "Lcom/samsung/android/gtscell/IRemoteCallback;",
            "describeContents",
            ApnSettings.MVNO_NONE,
            "sendResult",
            ApnSettings.MVNO_NONE,
            "result",
            "Landroid/os/Bundle;",
            "writeToParcel",
            "flags",
            "CREATOR",
            "OnResultListener",
            "gtscell_release"
        },
        k = 1,
        mv = {1, 1, 16})
/* loaded from: classes4.dex */
public final class RemoteCallback implements Parcelable {

    /* renamed from: CREATOR, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private final IRemoteCallback callback;
    private final Handler handler;
    private final OnResultListener listener;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    @Metadata(
            bv = {1, 0, 3},
            d1 = {
                "\u0000$\n"
                    + "\u0002\u0018\u0002\n"
                    + "\u0002\u0018\u0002\n"
                    + "\u0002\u0018\u0002\n"
                    + "\u0002\b\u0003\n"
                    + "\u0002\u0018\u0002\n"
                    + "\u0000\n"
                    + "\u0002\u0010\u0011\n"
                    + "\u0000\n"
                    + "\u0002\u0010\b\n"
                    + "\u0002\b\u0002\b\u0086\u0003\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0003J\u0010\u0010\u0004\u001a\u00020\u00022\u0006\u0010\u0005\u001a\u00020\u0006H\u0016J\u001d\u0010\u0007\u001a\n"
                    + "\u0012\u0006\u0012\u0004\u0018\u00010\u00020\b2\u0006\u0010\t\u001a\u00020\n"
                    + "H\u0016¢\u0006\u0002\u0010\u000b¨\u0006\f"
            },
            d2 = {
                "Lcom/samsung/android/gtscell/RemoteCallback$CREATOR;",
                "Landroid/os/Parcelable$Creator;",
                "Lcom/samsung/android/gtscell/RemoteCallback;",
                "()V",
                "createFromParcel",
                "parcel",
                "Landroid/os/Parcel;",
                "newArray",
                ApnSettings.MVNO_NONE,
                "size",
                ApnSettings.MVNO_NONE,
                "(I)[Lcom/samsung/android/gtscell/RemoteCallback;",
                "gtscell_release"
            },
            k = 1,
            mv = {1, 1, 16})
    /* renamed from: com.samsung.android.gtscell.RemoteCallback$CREATOR, reason: from kotlin metadata */
    public static final class Companion implements Parcelable.Creator<RemoteCallback> {
        private Companion() {}

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public RemoteCallback createFromParcel(Parcel parcel) {
            Intrinsics.checkParameterIsNotNull(parcel, "parcel");
            return new RemoteCallback(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public RemoteCallback[] newArray(int size) {
            return new RemoteCallback[size];
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    @Metadata(
            bv = {1, 0, 3},
            d1 = {
                "\u0000\u0016\n"
                    + "\u0002\u0018\u0002\n"
                    + "\u0002\u0010\u0000\n"
                    + "\u0000\n"
                    + "\u0002\u0010\u0002\n"
                    + "\u0000\n"
                    + "\u0002\u0018\u0002\n"
                    + "\u0000\bf\u0018\u00002\u00020\u0001J\u0012\u0010\u0002\u001a\u00020\u00032\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005H&¨\u0006\u0006"
            },
            d2 = {
                "Lcom/samsung/android/gtscell/RemoteCallback$OnResultListener;",
                ApnSettings.MVNO_NONE,
                "onResult",
                ApnSettings.MVNO_NONE,
                "result",
                "Landroid/os/Bundle;",
                "gtscell_release"
            },
            k = 1,
            mv = {1, 1, 16})
    public interface OnResultListener {
        void onResult(Bundle result);
    }

    public /* synthetic */ RemoteCallback(
            OnResultListener onResultListener,
            Handler handler,
            int i,
            DefaultConstructorMarker defaultConstructorMarker) {
        this(onResultListener, (i & 2) != 0 ? null : handler);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public final void sendResult(final Bundle result) {
        Intrinsics.checkParameterIsNotNull(result, "result");
        OnResultListener onResultListener = this.listener;
        if (onResultListener == null) {
            try {
                this.callback.sendResult(result);
                return;
            } catch (RemoteException unused) {
                return;
            }
        }
        Handler handler = this.handler;
        if (handler != null) {
            handler.post(
                    new Runnable() { // from class:
                                     // com.samsung.android.gtscell.RemoteCallback$sendResult$1
                        @Override // java.lang.Runnable
                        public final void run() {
                            RemoteCallback.OnResultListener onResultListener2;
                            onResultListener2 = RemoteCallback.this.listener;
                            onResultListener2.onResult(result);
                        }
                    });
        } else {
            onResultListener.onResult(result);
        }
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int flags) {
        Intrinsics.checkParameterIsNotNull(parcel, "parcel");
        parcel.writeStrongBinder(this.callback.asBinder());
    }

    public RemoteCallback(OnResultListener listener, Handler handler) {
        Intrinsics.checkParameterIsNotNull(listener, "listener");
        this.listener = listener;
        this.handler = handler;
        this.callback =
                new IRemoteCallback
                        .Stub() { // from class: com.samsung.android.gtscell.RemoteCallback.1
                    @Override // com.samsung.android.gtscell.IRemoteCallback
                    public void sendResult(Bundle data) {
                        Intrinsics.checkParameterIsNotNull(data, "data");
                        RemoteCallback.this.sendResult(data);
                    }
                };
    }

    public RemoteCallback(Parcel parcel) {
        Intrinsics.checkParameterIsNotNull(parcel, "parcel");
        this.listener = null;
        this.handler = null;
        IRemoteCallback asInterface = IRemoteCallback.Stub.asInterface(parcel.readStrongBinder());
        Intrinsics.checkExpressionValueIsNotNull(
                asInterface, "IRemoteCallback.Stub.asI…arcel.readStrongBinder())");
        this.callback = asInterface;
    }
}
