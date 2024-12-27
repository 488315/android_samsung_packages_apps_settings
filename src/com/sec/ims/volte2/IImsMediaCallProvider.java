package com.sec.ims.volte2;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.view.Surface;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public interface IImsMediaCallProvider extends IInterface {
    public static final String DESCRIPTOR = "com.sec.ims.volte2.IImsMediaCallProvider";

    void changeCameraCapabilities(int i, int i2, int i3) throws RemoteException;

    void deinitSurface(boolean z) throws RemoteException;

    void getCameraInfo(int i) throws RemoteException;

    int getDefaultCameraId() throws RemoteException;

    void getMaxZoom() throws RemoteException;

    void getZoom() throws RemoteException;

    void registerForVideoServiceEvent(IVideoServiceEventListener iVideoServiceEventListener)
            throws RemoteException;

    void requestCallDataUsage() throws RemoteException;

    void resetCameraId() throws RemoteException;

    void sendGeneralEvent(int i, int i2, int i3, String str) throws RemoteException;

    void sendLiveVideo(int i) throws RemoteException;

    void sendStillImage(int i, String str, int i2, String str2, int i3) throws RemoteException;

    void setCamera(String str) throws RemoteException;

    void setCameraEffect(int i) throws RemoteException;

    void setDeviceOrientation(int i) throws RemoteException;

    void setDisplaySurface(int i, Surface surface) throws RemoteException;

    void setPreviewSurface(int i, Surface surface) throws RemoteException;

    void setZoom(float f) throws RemoteException;

    void startCamera(Surface surface) throws RemoteException;

    void startEmoji(String str) throws RemoteException;

    void startRecord(String str) throws RemoteException;

    void startRender(boolean z) throws RemoteException;

    void startVideoRenderer(Surface surface) throws RemoteException;

    void stopCamera() throws RemoteException;

    void stopEmoji(int i) throws RemoteException;

    void stopRecord() throws RemoteException;

    void stopVideoRenderer() throws RemoteException;

    void swipeVideoSurface() throws RemoteException;

    void switchCamera() throws RemoteException;

    void unregisterForVideoServiceEvent(IVideoServiceEventListener iVideoServiceEventListener)
            throws RemoteException;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public static class Default implements IImsMediaCallProvider {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.sec.ims.volte2.IImsMediaCallProvider
        public int getDefaultCameraId() throws RemoteException {
            return 0;
        }

        @Override // com.sec.ims.volte2.IImsMediaCallProvider
        public void getMaxZoom() throws RemoteException {}

        @Override // com.sec.ims.volte2.IImsMediaCallProvider
        public void getZoom() throws RemoteException {}

        @Override // com.sec.ims.volte2.IImsMediaCallProvider
        public void requestCallDataUsage() throws RemoteException {}

        @Override // com.sec.ims.volte2.IImsMediaCallProvider
        public void resetCameraId() throws RemoteException {}

        @Override // com.sec.ims.volte2.IImsMediaCallProvider
        public void stopCamera() throws RemoteException {}

        @Override // com.sec.ims.volte2.IImsMediaCallProvider
        public void stopRecord() throws RemoteException {}

        @Override // com.sec.ims.volte2.IImsMediaCallProvider
        public void stopVideoRenderer() throws RemoteException {}

        @Override // com.sec.ims.volte2.IImsMediaCallProvider
        public void swipeVideoSurface() throws RemoteException {}

        @Override // com.sec.ims.volte2.IImsMediaCallProvider
        public void switchCamera() throws RemoteException {}

        @Override // com.sec.ims.volte2.IImsMediaCallProvider
        public void deinitSurface(boolean z) throws RemoteException {}

        @Override // com.sec.ims.volte2.IImsMediaCallProvider
        public void getCameraInfo(int i) throws RemoteException {}

        @Override // com.sec.ims.volte2.IImsMediaCallProvider
        public void registerForVideoServiceEvent(
                IVideoServiceEventListener iVideoServiceEventListener) throws RemoteException {}

        @Override // com.sec.ims.volte2.IImsMediaCallProvider
        public void sendLiveVideo(int i) throws RemoteException {}

        @Override // com.sec.ims.volte2.IImsMediaCallProvider
        public void setCamera(String str) throws RemoteException {}

        @Override // com.sec.ims.volte2.IImsMediaCallProvider
        public void setCameraEffect(int i) throws RemoteException {}

        @Override // com.sec.ims.volte2.IImsMediaCallProvider
        public void setDeviceOrientation(int i) throws RemoteException {}

        @Override // com.sec.ims.volte2.IImsMediaCallProvider
        public void setZoom(float f) throws RemoteException {}

        @Override // com.sec.ims.volte2.IImsMediaCallProvider
        public void startCamera(Surface surface) throws RemoteException {}

        @Override // com.sec.ims.volte2.IImsMediaCallProvider
        public void startEmoji(String str) throws RemoteException {}

        @Override // com.sec.ims.volte2.IImsMediaCallProvider
        public void startRecord(String str) throws RemoteException {}

        @Override // com.sec.ims.volte2.IImsMediaCallProvider
        public void startRender(boolean z) throws RemoteException {}

        @Override // com.sec.ims.volte2.IImsMediaCallProvider
        public void startVideoRenderer(Surface surface) throws RemoteException {}

        @Override // com.sec.ims.volte2.IImsMediaCallProvider
        public void stopEmoji(int i) throws RemoteException {}

        @Override // com.sec.ims.volte2.IImsMediaCallProvider
        public void unregisterForVideoServiceEvent(
                IVideoServiceEventListener iVideoServiceEventListener) throws RemoteException {}

        @Override // com.sec.ims.volte2.IImsMediaCallProvider
        public void setDisplaySurface(int i, Surface surface) throws RemoteException {}

        @Override // com.sec.ims.volte2.IImsMediaCallProvider
        public void setPreviewSurface(int i, Surface surface) throws RemoteException {}

        @Override // com.sec.ims.volte2.IImsMediaCallProvider
        public void changeCameraCapabilities(int i, int i2, int i3) throws RemoteException {}

        @Override // com.sec.ims.volte2.IImsMediaCallProvider
        public void sendGeneralEvent(int i, int i2, int i3, String str) throws RemoteException {}

        @Override // com.sec.ims.volte2.IImsMediaCallProvider
        public void sendStillImage(int i, String str, int i2, String str2, int i3)
                throws RemoteException {}
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public abstract static class Stub extends Binder implements IImsMediaCallProvider {
        static final int TRANSACTION_changeCameraCapabilities = 7;
        static final int TRANSACTION_deinitSurface = 17;
        static final int TRANSACTION_getCameraInfo = 12;
        static final int TRANSACTION_getDefaultCameraId = 20;
        static final int TRANSACTION_getMaxZoom = 18;
        static final int TRANSACTION_getZoom = 19;
        static final int TRANSACTION_registerForVideoServiceEvent = 23;
        static final int TRANSACTION_requestCallDataUsage = 6;
        static final int TRANSACTION_resetCameraId = 11;
        static final int TRANSACTION_sendGeneralEvent = 30;
        static final int TRANSACTION_sendLiveVideo = 22;
        static final int TRANSACTION_sendStillImage = 21;
        static final int TRANSACTION_setCamera = 1;
        static final int TRANSACTION_setCameraEffect = 25;
        static final int TRANSACTION_setDeviceOrientation = 4;
        static final int TRANSACTION_setDisplaySurface = 3;
        static final int TRANSACTION_setPreviewSurface = 2;
        static final int TRANSACTION_setZoom = 5;
        static final int TRANSACTION_startCamera = 8;
        static final int TRANSACTION_startEmoji = 28;
        static final int TRANSACTION_startRecord = 26;
        static final int TRANSACTION_startRender = 13;
        static final int TRANSACTION_startVideoRenderer = 14;
        static final int TRANSACTION_stopCamera = 9;
        static final int TRANSACTION_stopEmoji = 29;
        static final int TRANSACTION_stopRecord = 27;
        static final int TRANSACTION_stopVideoRenderer = 15;
        static final int TRANSACTION_swipeVideoSurface = 16;
        static final int TRANSACTION_switchCamera = 10;
        static final int TRANSACTION_unregisterForVideoServiceEvent = 24;

        /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
        public static class Proxy implements IImsMediaCallProvider {
            private IBinder mRemote;

            public Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            @Override // com.sec.ims.volte2.IImsMediaCallProvider
            public void changeCameraCapabilities(int i, int i2, int i3) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsMediaCallProvider.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IImsMediaCallProvider
            public void deinitSurface(boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsMediaCallProvider.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(17, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IImsMediaCallProvider
            public void getCameraInfo(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsMediaCallProvider.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(12, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IImsMediaCallProvider
            public int getDefaultCameraId() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsMediaCallProvider.DESCRIPTOR);
                    this.mRemote.transact(20, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public String getInterfaceDescriptor() {
                return IImsMediaCallProvider.DESCRIPTOR;
            }

            @Override // com.sec.ims.volte2.IImsMediaCallProvider
            public void getMaxZoom() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsMediaCallProvider.DESCRIPTOR);
                    this.mRemote.transact(18, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IImsMediaCallProvider
            public void getZoom() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsMediaCallProvider.DESCRIPTOR);
                    this.mRemote.transact(19, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IImsMediaCallProvider
            public void registerForVideoServiceEvent(
                    IVideoServiceEventListener iVideoServiceEventListener) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsMediaCallProvider.DESCRIPTOR);
                    obtain.writeStrongInterface(iVideoServiceEventListener);
                    this.mRemote.transact(23, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IImsMediaCallProvider
            public void requestCallDataUsage() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsMediaCallProvider.DESCRIPTOR);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IImsMediaCallProvider
            public void resetCameraId() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsMediaCallProvider.DESCRIPTOR);
                    this.mRemote.transact(11, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IImsMediaCallProvider
            public void sendGeneralEvent(int i, int i2, int i3, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsMediaCallProvider.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    obtain.writeString(str);
                    this.mRemote.transact(30, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IImsMediaCallProvider
            public void sendLiveVideo(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsMediaCallProvider.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(22, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IImsMediaCallProvider
            public void sendStillImage(int i, String str, int i2, String str2, int i3)
                    throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsMediaCallProvider.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeInt(i2);
                    obtain.writeString(str2);
                    obtain.writeInt(i3);
                    this.mRemote.transact(21, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IImsMediaCallProvider
            public void setCamera(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsMediaCallProvider.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IImsMediaCallProvider
            public void setCameraEffect(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsMediaCallProvider.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(25, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IImsMediaCallProvider
            public void setDeviceOrientation(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsMediaCallProvider.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IImsMediaCallProvider
            public void setDisplaySurface(int i, Surface surface) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsMediaCallProvider.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(surface, 0);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IImsMediaCallProvider
            public void setPreviewSurface(int i, Surface surface) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsMediaCallProvider.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(surface, 0);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IImsMediaCallProvider
            public void setZoom(float f) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsMediaCallProvider.DESCRIPTOR);
                    obtain.writeFloat(f);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IImsMediaCallProvider
            public void startCamera(Surface surface) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsMediaCallProvider.DESCRIPTOR);
                    obtain.writeTypedObject(surface, 0);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IImsMediaCallProvider
            public void startEmoji(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsMediaCallProvider.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(28, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IImsMediaCallProvider
            public void startRecord(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsMediaCallProvider.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(26, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IImsMediaCallProvider
            public void startRender(boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsMediaCallProvider.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(13, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IImsMediaCallProvider
            public void startVideoRenderer(Surface surface) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsMediaCallProvider.DESCRIPTOR);
                    obtain.writeTypedObject(surface, 0);
                    this.mRemote.transact(14, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IImsMediaCallProvider
            public void stopCamera() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsMediaCallProvider.DESCRIPTOR);
                    this.mRemote.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IImsMediaCallProvider
            public void stopEmoji(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsMediaCallProvider.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(29, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IImsMediaCallProvider
            public void stopRecord() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsMediaCallProvider.DESCRIPTOR);
                    this.mRemote.transact(27, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IImsMediaCallProvider
            public void stopVideoRenderer() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsMediaCallProvider.DESCRIPTOR);
                    this.mRemote.transact(15, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IImsMediaCallProvider
            public void swipeVideoSurface() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsMediaCallProvider.DESCRIPTOR);
                    this.mRemote.transact(16, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IImsMediaCallProvider
            public void switchCamera() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsMediaCallProvider.DESCRIPTOR);
                    this.mRemote.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IImsMediaCallProvider
            public void unregisterForVideoServiceEvent(
                    IVideoServiceEventListener iVideoServiceEventListener) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsMediaCallProvider.DESCRIPTOR);
                    obtain.writeStrongInterface(iVideoServiceEventListener);
                    this.mRemote.transact(24, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        public Stub() {
            attachInterface(this, IImsMediaCallProvider.DESCRIPTOR);
        }

        public static IImsMediaCallProvider asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface =
                    iBinder.queryLocalInterface(IImsMediaCallProvider.DESCRIPTOR);
            return (queryLocalInterface == null
                            || !(queryLocalInterface instanceof IImsMediaCallProvider))
                    ? new Proxy(iBinder)
                    : (IImsMediaCallProvider) queryLocalInterface;
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2)
                throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IImsMediaCallProvider.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IImsMediaCallProvider.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    String readString = parcel.readString();
                    parcel.enforceNoDataAvail();
                    setCamera(readString);
                    parcel2.writeNoException();
                    return true;
                case 2:
                    int readInt = parcel.readInt();
                    Surface surface = (Surface) parcel.readTypedObject(Surface.CREATOR);
                    parcel.enforceNoDataAvail();
                    setPreviewSurface(readInt, surface);
                    parcel2.writeNoException();
                    return true;
                case 3:
                    int readInt2 = parcel.readInt();
                    Surface surface2 = (Surface) parcel.readTypedObject(Surface.CREATOR);
                    parcel.enforceNoDataAvail();
                    setDisplaySurface(readInt2, surface2);
                    parcel2.writeNoException();
                    return true;
                case 4:
                    int readInt3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setDeviceOrientation(readInt3);
                    parcel2.writeNoException();
                    return true;
                case 5:
                    float readFloat = parcel.readFloat();
                    parcel.enforceNoDataAvail();
                    setZoom(readFloat);
                    parcel2.writeNoException();
                    return true;
                case 6:
                    requestCallDataUsage();
                    parcel2.writeNoException();
                    return true;
                case 7:
                    int readInt4 = parcel.readInt();
                    int readInt5 = parcel.readInt();
                    int readInt6 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    changeCameraCapabilities(readInt4, readInt5, readInt6);
                    parcel2.writeNoException();
                    return true;
                case 8:
                    Surface surface3 = (Surface) parcel.readTypedObject(Surface.CREATOR);
                    parcel.enforceNoDataAvail();
                    startCamera(surface3);
                    parcel2.writeNoException();
                    return true;
                case 9:
                    stopCamera();
                    parcel2.writeNoException();
                    return true;
                case 10:
                    switchCamera();
                    parcel2.writeNoException();
                    return true;
                case 11:
                    resetCameraId();
                    parcel2.writeNoException();
                    return true;
                case 12:
                    int readInt7 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    getCameraInfo(readInt7);
                    parcel2.writeNoException();
                    return true;
                case 13:
                    boolean readBoolean = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    startRender(readBoolean);
                    parcel2.writeNoException();
                    return true;
                case 14:
                    Surface surface4 = (Surface) parcel.readTypedObject(Surface.CREATOR);
                    parcel.enforceNoDataAvail();
                    startVideoRenderer(surface4);
                    parcel2.writeNoException();
                    return true;
                case 15:
                    stopVideoRenderer();
                    parcel2.writeNoException();
                    return true;
                case 16:
                    swipeVideoSurface();
                    parcel2.writeNoException();
                    return true;
                case 17:
                    boolean readBoolean2 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    deinitSurface(readBoolean2);
                    parcel2.writeNoException();
                    return true;
                case 18:
                    getMaxZoom();
                    parcel2.writeNoException();
                    return true;
                case 19:
                    getZoom();
                    parcel2.writeNoException();
                    return true;
                case 20:
                    int defaultCameraId = getDefaultCameraId();
                    parcel2.writeNoException();
                    parcel2.writeInt(defaultCameraId);
                    return true;
                case 21:
                    int readInt8 = parcel.readInt();
                    String readString2 = parcel.readString();
                    int readInt9 = parcel.readInt();
                    String readString3 = parcel.readString();
                    int readInt10 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    sendStillImage(readInt8, readString2, readInt9, readString3, readInt10);
                    parcel2.writeNoException();
                    return true;
                case 22:
                    int readInt11 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    sendLiveVideo(readInt11);
                    parcel2.writeNoException();
                    return true;
                case 23:
                    IVideoServiceEventListener asInterface =
                            IVideoServiceEventListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    registerForVideoServiceEvent(asInterface);
                    parcel2.writeNoException();
                    return true;
                case 24:
                    IVideoServiceEventListener asInterface2 =
                            IVideoServiceEventListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    unregisterForVideoServiceEvent(asInterface2);
                    parcel2.writeNoException();
                    return true;
                case 25:
                    int readInt12 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setCameraEffect(readInt12);
                    parcel2.writeNoException();
                    return true;
                case 26:
                    String readString4 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    startRecord(readString4);
                    parcel2.writeNoException();
                    return true;
                case 27:
                    stopRecord();
                    parcel2.writeNoException();
                    return true;
                case 28:
                    String readString5 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    startEmoji(readString5);
                    parcel2.writeNoException();
                    return true;
                case 29:
                    int readInt13 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    stopEmoji(readInt13);
                    parcel2.writeNoException();
                    return true;
                case 30:
                    int readInt14 = parcel.readInt();
                    int readInt15 = parcel.readInt();
                    int readInt16 = parcel.readInt();
                    String readString6 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    sendGeneralEvent(readInt14, readInt15, readInt16, readString6);
                    parcel2.writeNoException();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }
    }
}