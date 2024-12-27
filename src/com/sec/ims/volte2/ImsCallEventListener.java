package com.sec.ims.volte2;

import android.os.RemoteException;

import com.sec.ims.volte2.data.ImsCallInfo;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public abstract class ImsCallEventListener extends IImsCallEventListener.Stub {
    @Override // com.sec.ims.volte2.IImsCallEventListener
    public void onAudioPathUpdated(String str) throws RemoteException {}

    @Override // com.sec.ims.volte2.IImsCallEventListener
    public void onCallEstablished(ImsCallInfo imsCallInfo) throws RemoteException {}

    @Override // com.sec.ims.volte2.IImsCallEventListener
    public void onCallModified(ImsCallInfo imsCallInfo) throws RemoteException {}

    @Override // com.sec.ims.volte2.IImsCallEventListener
    public void onCallResumed(ImsCallInfo imsCallInfo) throws RemoteException {}

    @Override // com.sec.ims.volte2.IImsCallEventListener
    public void onCallRinging(ImsCallInfo imsCallInfo) throws RemoteException {}

    @Override // com.sec.ims.volte2.IImsCallEventListener
    public void onCallRingingBack(ImsCallInfo imsCallInfo) throws RemoteException {}

    @Override // com.sec.ims.volte2.IImsCallEventListener
    public void onCallStarted(ImsCallInfo imsCallInfo) {}

    @Override // com.sec.ims.volte2.IImsCallEventListener
    public void onCallTrying(ImsCallInfo imsCallInfo) throws RemoteException {}

    @Override // com.sec.ims.volte2.IImsCallEventListener
    public void onVideoAvailable(ImsCallInfo imsCallInfo) throws RemoteException {}

    @Override // com.sec.ims.volte2.IImsCallEventListener
    public void onVideoHeld(ImsCallInfo imsCallInfo) throws RemoteException {}

    @Override // com.sec.ims.volte2.IImsCallEventListener
    public void onVideoResumed(ImsCallInfo imsCallInfo) throws RemoteException {}

    @Override // com.sec.ims.volte2.IImsCallEventListener
    public void onCallEnded(ImsCallInfo imsCallInfo, int i) throws RemoteException {}

    @Override // com.sec.ims.volte2.IImsCallEventListener
    public void onCallModifyRequested(ImsCallInfo imsCallInfo, int i) throws RemoteException {}

    @Override // com.sec.ims.volte2.IImsCallEventListener
    public void onConferenceParticipantAdded(ImsCallInfo imsCallInfo, String str)
            throws RemoteException {}

    @Override // com.sec.ims.volte2.IImsCallEventListener
    public void onConferenceParticipantRemoved(ImsCallInfo imsCallInfo, String str)
            throws RemoteException {}

    @Override // com.sec.ims.volte2.IImsCallEventListener
    public void onIncomingCall(ImsCallInfo imsCallInfo, String str) {}

    @Override // com.sec.ims.volte2.IImsCallEventListener
    public void onIncomingPreAlerting(ImsCallInfo imsCallInfo, String str) {}

    @Override // com.sec.ims.volte2.IImsCallEventListener
    public void onCallHeld(ImsCallInfo imsCallInfo, boolean z, boolean z2) throws RemoteException {}

    @Override // com.sec.ims.volte2.IImsCallEventListener
    public void onDedicatedBearerEvent(ImsCallInfo imsCallInfo, int i, int i2)
            throws RemoteException {}

    @Override // com.sec.ims.volte2.IImsCallEventListener
    public void onRtpLossRateNoti(int i, float f, float f2, int i2) throws RemoteException {}
}
