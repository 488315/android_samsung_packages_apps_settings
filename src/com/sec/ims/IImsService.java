package com.sec.ims;

import android.content.ContentValues;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

import com.sec.ims.cmc.CmcCallInfo;
import com.sec.ims.cmc.CmcRecordingInfo;
import com.sec.ims.cmc.ICmcRecordingListener;
import com.sec.ims.ft.IImsOngoingFtEventListener;
import com.sec.ims.im.IImSessionListener;
import com.sec.ims.settings.ImsProfile;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public interface IImsService extends IInterface {
    public static final String DESCRIPTOR = "com.sec.ims.IImsService";

    void changeAudioPath(int i) throws RemoteException;

    void changeAudioPathForSlot(int i, int i2) throws RemoteException;

    void deregisterAdhocProfile(int i) throws RemoteException;

    void deregisterAdhocProfileByPhoneId(int i, int i2) throws RemoteException;

    void deregisterProfile(List list, boolean z) throws RemoteException;

    void deregisterProfileByPhoneId(List list, boolean z, int i) throws RemoteException;

    void dump() throws RemoteException;

    void enableRcs(boolean z) throws RemoteException;

    void enableRcsByPhoneId(boolean z, int i) throws RemoteException;

    void enableService(String str, boolean z) throws RemoteException;

    void enableServiceByPhoneId(String str, boolean z, int i) throws RemoteException;

    void enableVoLte(boolean z) throws RemoteException;

    void enableVoLteByPhoneId(boolean z, int i) throws RemoteException;

    void finishDmConfig(int i, int i2) throws RemoteException;

    void forcedUpdateRegistration(ImsProfile imsProfile) throws RemoteException;

    void forcedUpdateRegistrationByPhoneId(ImsProfile imsProfile, int i) throws RemoteException;

    String getAvailableNetworkType(String str) throws RemoteException;

    int[] getCallCount(int i) throws RemoteException;

    CmcCallInfo getCmcCallInfo() throws RemoteException;

    ContentValues getConfigValues(String[] strArr, int i) throws RemoteException;

    ImsProfile[] getCurrentProfile() throws RemoteException;

    ImsProfile[] getCurrentProfileForSlot(int i) throws RemoteException;

    int getEpsFbCallCount(int i) throws RemoteException;

    boolean getGlobalSettingsValueToBoolean(String str, int i, boolean z) throws RemoteException;

    int getGlobalSettingsValueToInteger(String str, int i, int i2) throws RemoteException;

    String getGlobalSettingsValueToString(String str, int i, String str2) throws RemoteException;

    DialogEvent getLastDialogEvent(int i) throws RemoteException;

    String getMasterStringValue(int i) throws RemoteException;

    int getMasterValue(int i) throws RemoteException;

    int getNetworkType(int i) throws RemoteException;

    int getNrSaCallCount(int i) throws RemoteException;

    int getPhoneCount() throws RemoteException;

    String getRcsProfileType(int i) throws RemoteException;

    ImsRegistration[] getRegistrationInfo() throws RemoteException;

    ImsRegistration[] getRegistrationInfoByPhoneId(int i) throws RemoteException;

    ImsRegistration getRegistrationInfoByServiceType(String str, int i) throws RemoteException;

    int getRttMode(int i) throws RemoteException;

    int getVideocallType() throws RemoteException;

    boolean hasCrossSimImsService(int i) throws RemoteException;

    boolean hasVoLteSim() throws RemoteException;

    boolean hasVoLteSimByPhoneId(int i) throws RemoteException;

    boolean isCmcEmergencyCallSupported(int i) throws RemoteException;

    boolean isCmcEmergencyNumber(String str, int i) throws RemoteException;

    boolean isCmcPotentialEmergencyNumber(String str, int i) throws RemoteException;

    boolean isCrossSimCallingRegistered(int i) throws RemoteException;

    boolean isCrossSimCallingSupported() throws RemoteException;

    boolean isCrossSimCallingSupportedByPhoneId(int i) throws RemoteException;

    boolean isCrossSimPermanentBlocked(int i) throws RemoteException;

    boolean isForbidden() throws RemoteException;

    boolean isForbiddenByPhoneId(int i) throws RemoteException;

    boolean isImsEnabled() throws RemoteException;

    boolean isImsEnabledByPhoneId(int i) throws RemoteException;

    boolean isNonVerifiedMno(int i) throws RemoteException;

    boolean isQSSSuccessAuthAndLogin(int i) throws RemoteException;

    boolean isRcsEnabled() throws RemoteException;

    boolean isRegistered() throws RemoteException;

    boolean isRttCall(int i) throws RemoteException;

    boolean isServiceAvailable(String str, int i, int i2) throws RemoteException;

    boolean isServiceEnabled(String str) throws RemoteException;

    boolean isServiceEnabledByPhoneId(String str, int i) throws RemoteException;

    boolean isSupportVoWiFiDisable5GSA(int i) throws RemoteException;

    boolean isVoLteEnabled() throws RemoteException;

    boolean isVoLteEnabledByPhoneId(int i) throws RemoteException;

    boolean isVolteEnabledFromNetwork(int i) throws RemoteException;

    boolean isVolteSupportECT() throws RemoteException;

    boolean isVolteSupportEctByPhoneId(int i) throws RemoteException;

    int registerAdhocProfile(ImsProfile imsProfile) throws RemoteException;

    int registerAdhocProfileByPhoneId(ImsProfile imsProfile, int i) throws RemoteException;

    String registerAutoConfigurationListener(
            IAutoConfigurationListener iAutoConfigurationListener, int i) throws RemoteException;

    void registerCallback(ImsEventListener imsEventListener, String str) throws RemoteException;

    void registerCmcRecordingListener(int i, ICmcRecordingListener iCmcRecordingListener)
            throws RemoteException;

    String registerCmcRegistrationListenerForSlot(
            IImsRegistrationListener iImsRegistrationListener, int i) throws RemoteException;

    String registerCmsRegistrationListenerByPhoneId(
            ICentralMsgStoreService iCentralMsgStoreService, int i) throws RemoteException;

    void registerDialogEventListener(int i, IDialogEventListener iDialogEventListener)
            throws RemoteException;

    String registerDialogEventListenerByToken(int i, IDialogEventListener iDialogEventListener)
            throws RemoteException;

    void registerDmValueListener(IImsDmConfigListener iImsDmConfigListener) throws RemoteException;

    String registerEpdgListener(IEpdgListener iEpdgListener) throws RemoteException;

    String registerImSessionListener(IImSessionListener iImSessionListener) throws RemoteException;

    String registerImSessionListenerByPhoneId(IImSessionListener iImSessionListener, int i)
            throws RemoteException;

    String registerImsOngoingFtListener(IImsOngoingFtEventListener iImsOngoingFtEventListener)
            throws RemoteException;

    String registerImsOngoingFtListenerByPhoneId(
            IImsOngoingFtEventListener iImsOngoingFtEventListener, int i) throws RemoteException;

    void registerImsRegistrationListener(IImsRegistrationListener iImsRegistrationListener)
            throws RemoteException;

    String registerImsRegistrationListenerForSlot(
            IImsRegistrationListener iImsRegistrationListener, int i) throws RemoteException;

    void registerProfile(List list) throws RemoteException;

    void registerProfileByPhoneId(List list, int i) throws RemoteException;

    String registerRttEventListener(int i, IRttEventListener iRttEventListener)
            throws RemoteException;

    String registerSimMobilityStatusListenerByPhoneId(
            ISimMobilityStatusListener iSimMobilityStatusListener, int i) throws RemoteException;

    void sendCmcRecordingEvent(int i, int i2, CmcRecordingInfo cmcRecordingInfo)
            throws RemoteException;

    void sendDeregister(int i, int i2) throws RemoteException;

    void sendIidToken(String str, int i) throws RemoteException;

    void sendMsisdnNumber(String str, int i) throws RemoteException;

    void sendRttMessage(String str) throws RemoteException;

    void sendRttSessionModifyRequest(int i, boolean z) throws RemoteException;

    void sendRttSessionModifyResponse(int i, boolean z) throws RemoteException;

    void sendTryRegister() throws RemoteException;

    void sendTryRegisterByPhoneId(int i) throws RemoteException;

    void sendTryRegisterCms(int i) throws RemoteException;

    void sendVerificationCode(String str, int i) throws RemoteException;

    int setActiveImpu(int i, String str, String str2) throws RemoteException;

    int setActiveMsisdn(int i, String str, String str2) throws RemoteException;

    void setAutomaticMode(int i, boolean z) throws RemoteException;

    void setCrossSimPermanentBlocked(int i, boolean z) throws RemoteException;

    void setEmergencyPdnInfo(String str, String[] strArr, String str2, int i)
            throws RemoteException;

    void setIsimLoaded() throws RemoteException;

    void setNrInterworkingMode(int i, int i2) throws RemoteException;

    void setProvisionedStringValue(int i, String str) throws RemoteException;

    void setProvisionedValue(int i, int i2) throws RemoteException;

    void setRttMode(int i, int i2) throws RemoteException;

    void setSimRefreshed() throws RemoteException;

    boolean setVideocallType(int i) throws RemoteException;

    int startDmConfig(int i) throws RemoteException;

    int startLocalRingBackTone(int i, int i2, int i3) throws RemoteException;

    int stopLocalRingBackTone() throws RemoteException;

    void suspendRegister(boolean z, int i) throws RemoteException;

    void transferCall(String str, String str2) throws RemoteException;

    void triggerAutoConfigurationForApp(int i) throws RemoteException;

    void unRegisterEpdgListener(String str) throws RemoteException;

    void unregisterAutoConfigurationListener(String str, int i) throws RemoteException;

    void unregisterCallback(ImsEventListener imsEventListener) throws RemoteException;

    void unregisterCmcRegistrationListenerForSlot(String str, int i) throws RemoteException;

    void unregisterCmsRegistrationListenerByPhoneId(String str, int i) throws RemoteException;

    void unregisterDialogEventListener(int i, IDialogEventListener iDialogEventListener)
            throws RemoteException;

    void unregisterDialogEventListenerByToken(int i, String str) throws RemoteException;

    void unregisterDmValueListener(IImsDmConfigListener iImsDmConfigListener)
            throws RemoteException;

    void unregisterImSessionListener(String str) throws RemoteException;

    void unregisterImSessionListenerByPhoneId(String str, int i) throws RemoteException;

    void unregisterImsOngoingFtListener(String str) throws RemoteException;

    void unregisterImsOngoingFtListenerByPhoneId(String str, int i) throws RemoteException;

    void unregisterImsRegistrationListener(IImsRegistrationListener iImsRegistrationListener)
            throws RemoteException;

    void unregisterImsRegistrationListenerForSlot(String str, int i) throws RemoteException;

    void unregisterRttEventListener(int i, String str) throws RemoteException;

    void unregisterSimMobilityStatusListenerByPhoneId(String str, int i) throws RemoteException;

    boolean updateConfigValues(ContentValues contentValues, int i, int i2) throws RemoteException;

    int updateRegistration(ImsProfile imsProfile, int i) throws RemoteException;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public static class Default implements IImsService {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.sec.ims.IImsService
        public String getAvailableNetworkType(String str) throws RemoteException {
            return null;
        }

        @Override // com.sec.ims.IImsService
        public int[] getCallCount(int i) throws RemoteException {
            return null;
        }

        @Override // com.sec.ims.IImsService
        public CmcCallInfo getCmcCallInfo() throws RemoteException {
            return null;
        }

        @Override // com.sec.ims.IImsService
        public ContentValues getConfigValues(String[] strArr, int i) throws RemoteException {
            return null;
        }

        @Override // com.sec.ims.IImsService
        public ImsProfile[] getCurrentProfile() throws RemoteException {
            return null;
        }

        @Override // com.sec.ims.IImsService
        public ImsProfile[] getCurrentProfileForSlot(int i) throws RemoteException {
            return null;
        }

        @Override // com.sec.ims.IImsService
        public int getEpsFbCallCount(int i) throws RemoteException {
            return 0;
        }

        @Override // com.sec.ims.IImsService
        public boolean getGlobalSettingsValueToBoolean(String str, int i, boolean z)
                throws RemoteException {
            return false;
        }

        @Override // com.sec.ims.IImsService
        public int getGlobalSettingsValueToInteger(String str, int i, int i2)
                throws RemoteException {
            return 0;
        }

        @Override // com.sec.ims.IImsService
        public String getGlobalSettingsValueToString(String str, int i, String str2)
                throws RemoteException {
            return null;
        }

        @Override // com.sec.ims.IImsService
        public DialogEvent getLastDialogEvent(int i) throws RemoteException {
            return null;
        }

        @Override // com.sec.ims.IImsService
        public String getMasterStringValue(int i) throws RemoteException {
            return null;
        }

        @Override // com.sec.ims.IImsService
        public int getMasterValue(int i) throws RemoteException {
            return 0;
        }

        @Override // com.sec.ims.IImsService
        public int getNetworkType(int i) throws RemoteException {
            return 0;
        }

        @Override // com.sec.ims.IImsService
        public int getNrSaCallCount(int i) throws RemoteException {
            return 0;
        }

        @Override // com.sec.ims.IImsService
        public int getPhoneCount() throws RemoteException {
            return 0;
        }

        @Override // com.sec.ims.IImsService
        public String getRcsProfileType(int i) throws RemoteException {
            return null;
        }

        @Override // com.sec.ims.IImsService
        public ImsRegistration[] getRegistrationInfo() throws RemoteException {
            return null;
        }

        @Override // com.sec.ims.IImsService
        public ImsRegistration[] getRegistrationInfoByPhoneId(int i) throws RemoteException {
            return null;
        }

        @Override // com.sec.ims.IImsService
        public ImsRegistration getRegistrationInfoByServiceType(String str, int i)
                throws RemoteException {
            return null;
        }

        @Override // com.sec.ims.IImsService
        public int getRttMode(int i) throws RemoteException {
            return 0;
        }

        @Override // com.sec.ims.IImsService
        public int getVideocallType() throws RemoteException {
            return 0;
        }

        @Override // com.sec.ims.IImsService
        public boolean hasCrossSimImsService(int i) throws RemoteException {
            return false;
        }

        @Override // com.sec.ims.IImsService
        public boolean hasVoLteSim() throws RemoteException {
            return false;
        }

        @Override // com.sec.ims.IImsService
        public boolean hasVoLteSimByPhoneId(int i) throws RemoteException {
            return false;
        }

        @Override // com.sec.ims.IImsService
        public boolean isCmcEmergencyCallSupported(int i) throws RemoteException {
            return false;
        }

        @Override // com.sec.ims.IImsService
        public boolean isCmcEmergencyNumber(String str, int i) throws RemoteException {
            return false;
        }

        @Override // com.sec.ims.IImsService
        public boolean isCmcPotentialEmergencyNumber(String str, int i) throws RemoteException {
            return false;
        }

        @Override // com.sec.ims.IImsService
        public boolean isCrossSimCallingRegistered(int i) throws RemoteException {
            return false;
        }

        @Override // com.sec.ims.IImsService
        public boolean isCrossSimCallingSupported() throws RemoteException {
            return false;
        }

        @Override // com.sec.ims.IImsService
        public boolean isCrossSimCallingSupportedByPhoneId(int i) throws RemoteException {
            return false;
        }

        @Override // com.sec.ims.IImsService
        public boolean isCrossSimPermanentBlocked(int i) throws RemoteException {
            return false;
        }

        @Override // com.sec.ims.IImsService
        public boolean isForbidden() throws RemoteException {
            return false;
        }

        @Override // com.sec.ims.IImsService
        public boolean isForbiddenByPhoneId(int i) throws RemoteException {
            return false;
        }

        @Override // com.sec.ims.IImsService
        public boolean isImsEnabled() throws RemoteException {
            return false;
        }

        @Override // com.sec.ims.IImsService
        public boolean isImsEnabledByPhoneId(int i) throws RemoteException {
            return false;
        }

        @Override // com.sec.ims.IImsService
        public boolean isNonVerifiedMno(int i) throws RemoteException {
            return false;
        }

        @Override // com.sec.ims.IImsService
        public boolean isQSSSuccessAuthAndLogin(int i) throws RemoteException {
            return false;
        }

        @Override // com.sec.ims.IImsService
        public boolean isRcsEnabled() throws RemoteException {
            return false;
        }

        @Override // com.sec.ims.IImsService
        public boolean isRegistered() throws RemoteException {
            return false;
        }

        @Override // com.sec.ims.IImsService
        public boolean isRttCall(int i) throws RemoteException {
            return false;
        }

        @Override // com.sec.ims.IImsService
        public boolean isServiceAvailable(String str, int i, int i2) throws RemoteException {
            return false;
        }

        @Override // com.sec.ims.IImsService
        public boolean isServiceEnabled(String str) throws RemoteException {
            return false;
        }

        @Override // com.sec.ims.IImsService
        public boolean isServiceEnabledByPhoneId(String str, int i) throws RemoteException {
            return false;
        }

        @Override // com.sec.ims.IImsService
        public boolean isSupportVoWiFiDisable5GSA(int i) throws RemoteException {
            return false;
        }

        @Override // com.sec.ims.IImsService
        public boolean isVoLteEnabled() throws RemoteException {
            return false;
        }

        @Override // com.sec.ims.IImsService
        public boolean isVoLteEnabledByPhoneId(int i) throws RemoteException {
            return false;
        }

        @Override // com.sec.ims.IImsService
        public boolean isVolteEnabledFromNetwork(int i) throws RemoteException {
            return false;
        }

        @Override // com.sec.ims.IImsService
        public boolean isVolteSupportECT() throws RemoteException {
            return false;
        }

        @Override // com.sec.ims.IImsService
        public boolean isVolteSupportEctByPhoneId(int i) throws RemoteException {
            return false;
        }

        @Override // com.sec.ims.IImsService
        public int registerAdhocProfile(ImsProfile imsProfile) throws RemoteException {
            return 0;
        }

        @Override // com.sec.ims.IImsService
        public int registerAdhocProfileByPhoneId(ImsProfile imsProfile, int i)
                throws RemoteException {
            return 0;
        }

        @Override // com.sec.ims.IImsService
        public String registerAutoConfigurationListener(
                IAutoConfigurationListener iAutoConfigurationListener, int i)
                throws RemoteException {
            return null;
        }

        @Override // com.sec.ims.IImsService
        public String registerCmcRegistrationListenerForSlot(
                IImsRegistrationListener iImsRegistrationListener, int i) throws RemoteException {
            return null;
        }

        @Override // com.sec.ims.IImsService
        public String registerCmsRegistrationListenerByPhoneId(
                ICentralMsgStoreService iCentralMsgStoreService, int i) throws RemoteException {
            return null;
        }

        @Override // com.sec.ims.IImsService
        public String registerDialogEventListenerByToken(
                int i, IDialogEventListener iDialogEventListener) throws RemoteException {
            return null;
        }

        @Override // com.sec.ims.IImsService
        public String registerEpdgListener(IEpdgListener iEpdgListener) throws RemoteException {
            return null;
        }

        @Override // com.sec.ims.IImsService
        public String registerImSessionListener(IImSessionListener iImSessionListener)
                throws RemoteException {
            return null;
        }

        @Override // com.sec.ims.IImsService
        public String registerImSessionListenerByPhoneId(
                IImSessionListener iImSessionListener, int i) throws RemoteException {
            return null;
        }

        @Override // com.sec.ims.IImsService
        public String registerImsOngoingFtListener(
                IImsOngoingFtEventListener iImsOngoingFtEventListener) throws RemoteException {
            return null;
        }

        @Override // com.sec.ims.IImsService
        public String registerImsOngoingFtListenerByPhoneId(
                IImsOngoingFtEventListener iImsOngoingFtEventListener, int i)
                throws RemoteException {
            return null;
        }

        @Override // com.sec.ims.IImsService
        public String registerImsRegistrationListenerForSlot(
                IImsRegistrationListener iImsRegistrationListener, int i) throws RemoteException {
            return null;
        }

        @Override // com.sec.ims.IImsService
        public String registerRttEventListener(int i, IRttEventListener iRttEventListener)
                throws RemoteException {
            return null;
        }

        @Override // com.sec.ims.IImsService
        public String registerSimMobilityStatusListenerByPhoneId(
                ISimMobilityStatusListener iSimMobilityStatusListener, int i)
                throws RemoteException {
            return null;
        }

        @Override // com.sec.ims.IImsService
        public int setActiveImpu(int i, String str, String str2) throws RemoteException {
            return 0;
        }

        @Override // com.sec.ims.IImsService
        public int setActiveMsisdn(int i, String str, String str2) throws RemoteException {
            return 0;
        }

        @Override // com.sec.ims.IImsService
        public boolean setVideocallType(int i) throws RemoteException {
            return false;
        }

        @Override // com.sec.ims.IImsService
        public int startDmConfig(int i) throws RemoteException {
            return 0;
        }

        @Override // com.sec.ims.IImsService
        public int startLocalRingBackTone(int i, int i2, int i3) throws RemoteException {
            return 0;
        }

        @Override // com.sec.ims.IImsService
        public int stopLocalRingBackTone() throws RemoteException {
            return 0;
        }

        @Override // com.sec.ims.IImsService
        public boolean updateConfigValues(ContentValues contentValues, int i, int i2)
                throws RemoteException {
            return false;
        }

        @Override // com.sec.ims.IImsService
        public int updateRegistration(ImsProfile imsProfile, int i) throws RemoteException {
            return 0;
        }

        @Override // com.sec.ims.IImsService
        public void dump() throws RemoteException {}

        @Override // com.sec.ims.IImsService
        public void sendTryRegister() throws RemoteException {}

        @Override // com.sec.ims.IImsService
        public void setIsimLoaded() throws RemoteException {}

        @Override // com.sec.ims.IImsService
        public void setSimRefreshed() throws RemoteException {}

        @Override // com.sec.ims.IImsService
        public void changeAudioPath(int i) throws RemoteException {}

        @Override // com.sec.ims.IImsService
        public void deregisterAdhocProfile(int i) throws RemoteException {}

        @Override // com.sec.ims.IImsService
        public void enableRcs(boolean z) throws RemoteException {}

        @Override // com.sec.ims.IImsService
        public void enableVoLte(boolean z) throws RemoteException {}

        @Override // com.sec.ims.IImsService
        public void forcedUpdateRegistration(ImsProfile imsProfile) throws RemoteException {}

        @Override // com.sec.ims.IImsService
        public void registerDmValueListener(IImsDmConfigListener iImsDmConfigListener)
                throws RemoteException {}

        @Override // com.sec.ims.IImsService
        public void registerImsRegistrationListener(
                IImsRegistrationListener iImsRegistrationListener) throws RemoteException {}

        @Override // com.sec.ims.IImsService
        public void registerProfile(List list) throws RemoteException {}

        @Override // com.sec.ims.IImsService
        public void sendRttMessage(String str) throws RemoteException {}

        @Override // com.sec.ims.IImsService
        public void sendTryRegisterByPhoneId(int i) throws RemoteException {}

        @Override // com.sec.ims.IImsService
        public void sendTryRegisterCms(int i) throws RemoteException {}

        @Override // com.sec.ims.IImsService
        public void triggerAutoConfigurationForApp(int i) throws RemoteException {}

        @Override // com.sec.ims.IImsService
        public void unRegisterEpdgListener(String str) throws RemoteException {}

        @Override // com.sec.ims.IImsService
        public void unregisterCallback(ImsEventListener imsEventListener) throws RemoteException {}

        @Override // com.sec.ims.IImsService
        public void unregisterDmValueListener(IImsDmConfigListener iImsDmConfigListener)
                throws RemoteException {}

        @Override // com.sec.ims.IImsService
        public void unregisterImSessionListener(String str) throws RemoteException {}

        @Override // com.sec.ims.IImsService
        public void unregisterImsOngoingFtListener(String str) throws RemoteException {}

        @Override // com.sec.ims.IImsService
        public void unregisterImsRegistrationListener(
                IImsRegistrationListener iImsRegistrationListener) throws RemoteException {}

        @Override // com.sec.ims.IImsService
        public void changeAudioPathForSlot(int i, int i2) throws RemoteException {}

        @Override // com.sec.ims.IImsService
        public void deregisterAdhocProfileByPhoneId(int i, int i2) throws RemoteException {}

        @Override // com.sec.ims.IImsService
        public void deregisterProfile(List list, boolean z) throws RemoteException {}

        @Override // com.sec.ims.IImsService
        public void enableRcsByPhoneId(boolean z, int i) throws RemoteException {}

        @Override // com.sec.ims.IImsService
        public void enableService(String str, boolean z) throws RemoteException {}

        @Override // com.sec.ims.IImsService
        public void enableVoLteByPhoneId(boolean z, int i) throws RemoteException {}

        @Override // com.sec.ims.IImsService
        public void finishDmConfig(int i, int i2) throws RemoteException {}

        @Override // com.sec.ims.IImsService
        public void forcedUpdateRegistrationByPhoneId(ImsProfile imsProfile, int i)
                throws RemoteException {}

        @Override // com.sec.ims.IImsService
        public void registerCallback(ImsEventListener imsEventListener, String str)
                throws RemoteException {}

        @Override // com.sec.ims.IImsService
        public void registerCmcRecordingListener(int i, ICmcRecordingListener iCmcRecordingListener)
                throws RemoteException {}

        @Override // com.sec.ims.IImsService
        public void registerDialogEventListener(int i, IDialogEventListener iDialogEventListener)
                throws RemoteException {}

        @Override // com.sec.ims.IImsService
        public void registerProfileByPhoneId(List list, int i) throws RemoteException {}

        @Override // com.sec.ims.IImsService
        public void sendDeregister(int i, int i2) throws RemoteException {}

        @Override // com.sec.ims.IImsService
        public void sendIidToken(String str, int i) throws RemoteException {}

        @Override // com.sec.ims.IImsService
        public void sendMsisdnNumber(String str, int i) throws RemoteException {}

        @Override // com.sec.ims.IImsService
        public void sendRttSessionModifyRequest(int i, boolean z) throws RemoteException {}

        @Override // com.sec.ims.IImsService
        public void sendRttSessionModifyResponse(int i, boolean z) throws RemoteException {}

        @Override // com.sec.ims.IImsService
        public void sendVerificationCode(String str, int i) throws RemoteException {}

        @Override // com.sec.ims.IImsService
        public void setAutomaticMode(int i, boolean z) throws RemoteException {}

        @Override // com.sec.ims.IImsService
        public void setCrossSimPermanentBlocked(int i, boolean z) throws RemoteException {}

        @Override // com.sec.ims.IImsService
        public void setNrInterworkingMode(int i, int i2) throws RemoteException {}

        @Override // com.sec.ims.IImsService
        public void setProvisionedStringValue(int i, String str) throws RemoteException {}

        @Override // com.sec.ims.IImsService
        public void setProvisionedValue(int i, int i2) throws RemoteException {}

        @Override // com.sec.ims.IImsService
        public void setRttMode(int i, int i2) throws RemoteException {}

        @Override // com.sec.ims.IImsService
        public void suspendRegister(boolean z, int i) throws RemoteException {}

        @Override // com.sec.ims.IImsService
        public void transferCall(String str, String str2) throws RemoteException {}

        @Override // com.sec.ims.IImsService
        public void unregisterAutoConfigurationListener(String str, int i) throws RemoteException {}

        @Override // com.sec.ims.IImsService
        public void unregisterCmcRegistrationListenerForSlot(String str, int i)
                throws RemoteException {}

        @Override // com.sec.ims.IImsService
        public void unregisterCmsRegistrationListenerByPhoneId(String str, int i)
                throws RemoteException {}

        @Override // com.sec.ims.IImsService
        public void unregisterDialogEventListener(int i, IDialogEventListener iDialogEventListener)
                throws RemoteException {}

        @Override // com.sec.ims.IImsService
        public void unregisterDialogEventListenerByToken(int i, String str)
                throws RemoteException {}

        @Override // com.sec.ims.IImsService
        public void unregisterImSessionListenerByPhoneId(String str, int i)
                throws RemoteException {}

        @Override // com.sec.ims.IImsService
        public void unregisterImsOngoingFtListenerByPhoneId(String str, int i)
                throws RemoteException {}

        @Override // com.sec.ims.IImsService
        public void unregisterImsRegistrationListenerForSlot(String str, int i)
                throws RemoteException {}

        @Override // com.sec.ims.IImsService
        public void unregisterRttEventListener(int i, String str) throws RemoteException {}

        @Override // com.sec.ims.IImsService
        public void unregisterSimMobilityStatusListenerByPhoneId(String str, int i)
                throws RemoteException {}

        @Override // com.sec.ims.IImsService
        public void deregisterProfileByPhoneId(List list, boolean z, int i)
                throws RemoteException {}

        @Override // com.sec.ims.IImsService
        public void enableServiceByPhoneId(String str, boolean z, int i) throws RemoteException {}

        @Override // com.sec.ims.IImsService
        public void sendCmcRecordingEvent(int i, int i2, CmcRecordingInfo cmcRecordingInfo)
                throws RemoteException {}

        @Override // com.sec.ims.IImsService
        public void setEmergencyPdnInfo(String str, String[] strArr, String str2, int i)
                throws RemoteException {}
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public abstract static class Stub extends Binder implements IImsService {
        static final int TRANSACTION_changeAudioPath = 95;
        static final int TRANSACTION_changeAudioPathForSlot = 96;
        static final int TRANSACTION_deregisterAdhocProfile = 36;
        static final int TRANSACTION_deregisterAdhocProfileByPhoneId = 37;
        static final int TRANSACTION_deregisterProfile = 40;
        static final int TRANSACTION_deregisterProfileByPhoneId = 41;
        static final int TRANSACTION_dump = 119;
        static final int TRANSACTION_enableRcs = 85;
        static final int TRANSACTION_enableRcsByPhoneId = 86;
        static final int TRANSACTION_enableService = 81;
        static final int TRANSACTION_enableServiceByPhoneId = 82;
        static final int TRANSACTION_enableVoLte = 83;
        static final int TRANSACTION_enableVoLteByPhoneId = 84;
        static final int TRANSACTION_finishDmConfig = 105;
        static final int TRANSACTION_forcedUpdateRegistration = 45;
        static final int TRANSACTION_forcedUpdateRegistrationByPhoneId = 46;
        static final int TRANSACTION_getAvailableNetworkType = 12;
        static final int TRANSACTION_getCallCount = 87;
        static final int TRANSACTION_getCmcCallInfo = 99;
        static final int TRANSACTION_getConfigValues = 102;
        static final int TRANSACTION_getCurrentProfile = 31;
        static final int TRANSACTION_getCurrentProfileForSlot = 32;
        static final int TRANSACTION_getEpsFbCallCount = 88;
        static final int TRANSACTION_getGlobalSettingsValueToBoolean = 118;
        static final int TRANSACTION_getGlobalSettingsValueToInteger = 117;
        static final int TRANSACTION_getGlobalSettingsValueToString = 116;
        static final int TRANSACTION_getLastDialogEvent = 62;
        static final int TRANSACTION_getMasterStringValue = 64;
        static final int TRANSACTION_getMasterValue = 63;
        static final int TRANSACTION_getNetworkType = 11;
        static final int TRANSACTION_getNrSaCallCount = 89;
        static final int TRANSACTION_getPhoneCount = 3;
        static final int TRANSACTION_getRcsProfileType = 33;
        static final int TRANSACTION_getRegistrationInfo = 28;
        static final int TRANSACTION_getRegistrationInfoByPhoneId = 29;
        static final int TRANSACTION_getRegistrationInfoByServiceType = 30;
        static final int TRANSACTION_getRttMode = 109;
        static final int TRANSACTION_getVideocallType = 98;
        static final int TRANSACTION_hasCrossSimImsService = 129;
        static final int TRANSACTION_hasVoLteSim = 79;
        static final int TRANSACTION_hasVoLteSimByPhoneId = 80;
        static final int TRANSACTION_isCmcEmergencyCallSupported = 122;
        static final int TRANSACTION_isCmcEmergencyNumber = 123;
        static final int TRANSACTION_isCmcPotentialEmergencyNumber = 124;
        static final int TRANSACTION_isCrossSimCallingRegistered = 128;
        static final int TRANSACTION_isCrossSimCallingSupported = 131;
        static final int TRANSACTION_isCrossSimCallingSupportedByPhoneId = 130;
        static final int TRANSACTION_isCrossSimPermanentBlocked = 133;
        static final int TRANSACTION_isForbidden = 90;
        static final int TRANSACTION_isForbiddenByPhoneId = 91;
        static final int TRANSACTION_isImsEnabled = 67;
        static final int TRANSACTION_isImsEnabledByPhoneId = 68;
        static final int TRANSACTION_isNonVerifiedMno = 77;
        static final int TRANSACTION_isQSSSuccessAuthAndLogin = 50;
        static final int TRANSACTION_isRcsEnabled = 74;
        static final int TRANSACTION_isRegistered = 27;
        static final int TRANSACTION_isRttCall = 106;
        static final int TRANSACTION_isServiceAvailable = 76;
        static final int TRANSACTION_isServiceEnabled = 75;
        static final int TRANSACTION_isServiceEnabledByPhoneId = 78;
        static final int TRANSACTION_isSupportVoWiFiDisable5GSA = 127;
        static final int TRANSACTION_isVoLteEnabled = 69;
        static final int TRANSACTION_isVoLteEnabledByPhoneId = 70;
        static final int TRANSACTION_isVolteEnabledFromNetwork = 71;
        static final int TRANSACTION_isVolteSupportECT = 72;
        static final int TRANSACTION_isVolteSupportEctByPhoneId = 73;
        static final int TRANSACTION_registerAdhocProfile = 34;
        static final int TRANSACTION_registerAdhocProfileByPhoneId = 35;
        static final int TRANSACTION_registerAutoConfigurationListener = 21;
        static final int TRANSACTION_registerCallback = 1;
        static final int TRANSACTION_registerCmcRecordingListener = 126;
        static final int TRANSACTION_registerCmcRegistrationListenerForSlot = 120;
        static final int TRANSACTION_registerCmsRegistrationListenerByPhoneId = 25;
        static final int TRANSACTION_registerDialogEventListener = 58;
        static final int TRANSACTION_registerDialogEventListenerByToken = 60;
        static final int TRANSACTION_registerDmValueListener = 100;
        static final int TRANSACTION_registerEpdgListener = 52;
        static final int TRANSACTION_registerImSessionListener = 13;
        static final int TRANSACTION_registerImSessionListenerByPhoneId = 14;
        static final int TRANSACTION_registerImsOngoingFtListener = 17;
        static final int TRANSACTION_registerImsOngoingFtListenerByPhoneId = 18;
        static final int TRANSACTION_registerImsRegistrationListener = 54;
        static final int TRANSACTION_registerImsRegistrationListenerForSlot = 56;
        static final int TRANSACTION_registerProfile = 38;
        static final int TRANSACTION_registerProfileByPhoneId = 39;
        static final int TRANSACTION_registerRttEventListener = 113;
        static final int TRANSACTION_registerSimMobilityStatusListenerByPhoneId = 23;
        static final int TRANSACTION_sendCmcRecordingEvent = 125;
        static final int TRANSACTION_sendDeregister = 47;
        static final int TRANSACTION_sendIidToken = 10;
        static final int TRANSACTION_sendMsisdnNumber = 9;
        static final int TRANSACTION_sendRttMessage = 110;
        static final int TRANSACTION_sendRttSessionModifyRequest = 112;
        static final int TRANSACTION_sendRttSessionModifyResponse = 111;
        static final int TRANSACTION_sendTryRegister = 42;
        static final int TRANSACTION_sendTryRegisterByPhoneId = 44;
        static final int TRANSACTION_sendTryRegisterCms = 43;
        static final int TRANSACTION_sendVerificationCode = 8;
        static final int TRANSACTION_setActiveImpu = 6;
        static final int TRANSACTION_setActiveMsisdn = 7;
        static final int TRANSACTION_setAutomaticMode = 107;
        static final int TRANSACTION_setCrossSimPermanentBlocked = 132;
        static final int TRANSACTION_setEmergencyPdnInfo = 51;
        static final int TRANSACTION_setIsimLoaded = 4;
        static final int TRANSACTION_setNrInterworkingMode = 134;
        static final int TRANSACTION_setProvisionedStringValue = 66;
        static final int TRANSACTION_setProvisionedValue = 65;
        static final int TRANSACTION_setRttMode = 108;
        static final int TRANSACTION_setSimRefreshed = 5;
        static final int TRANSACTION_setVideocallType = 97;
        static final int TRANSACTION_startDmConfig = 104;
        static final int TRANSACTION_startLocalRingBackTone = 93;
        static final int TRANSACTION_stopLocalRingBackTone = 94;
        static final int TRANSACTION_suspendRegister = 48;
        static final int TRANSACTION_transferCall = 92;
        static final int TRANSACTION_triggerAutoConfigurationForApp = 115;
        static final int TRANSACTION_unRegisterEpdgListener = 53;
        static final int TRANSACTION_unregisterAutoConfigurationListener = 22;
        static final int TRANSACTION_unregisterCallback = 2;
        static final int TRANSACTION_unregisterCmcRegistrationListenerForSlot = 121;
        static final int TRANSACTION_unregisterCmsRegistrationListenerByPhoneId = 26;
        static final int TRANSACTION_unregisterDialogEventListener = 59;
        static final int TRANSACTION_unregisterDialogEventListenerByToken = 61;
        static final int TRANSACTION_unregisterDmValueListener = 101;
        static final int TRANSACTION_unregisterImSessionListener = 15;
        static final int TRANSACTION_unregisterImSessionListenerByPhoneId = 16;
        static final int TRANSACTION_unregisterImsOngoingFtListener = 19;
        static final int TRANSACTION_unregisterImsOngoingFtListenerByPhoneId = 20;
        static final int TRANSACTION_unregisterImsRegistrationListener = 55;
        static final int TRANSACTION_unregisterImsRegistrationListenerForSlot = 57;
        static final int TRANSACTION_unregisterRttEventListener = 114;
        static final int TRANSACTION_unregisterSimMobilityStatusListenerByPhoneId = 24;
        static final int TRANSACTION_updateConfigValues = 103;
        static final int TRANSACTION_updateRegistration = 49;

        /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
        public static class Proxy implements IImsService {
            private IBinder mRemote;

            public Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            @Override // com.sec.ims.IImsService
            public void changeAudioPath(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsService.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(95, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.IImsService
            public void changeAudioPathForSlot(int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsService.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(96, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.IImsService
            public void deregisterAdhocProfile(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsService.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(36, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.IImsService
            public void deregisterAdhocProfileByPhoneId(int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsService.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(37, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.IImsService
            public void deregisterProfile(List list, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsService.DESCRIPTOR);
                    obtain.writeList(list);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(40, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.IImsService
            public void deregisterProfileByPhoneId(List list, boolean z, int i)
                    throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsService.DESCRIPTOR);
                    obtain.writeList(list);
                    obtain.writeBoolean(z);
                    obtain.writeInt(i);
                    this.mRemote.transact(41, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.IImsService
            public void dump() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsService.DESCRIPTOR);
                    this.mRemote.transact(119, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.IImsService
            public void enableRcs(boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsService.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(85, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.IImsService
            public void enableRcsByPhoneId(boolean z, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsService.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    obtain.writeInt(i);
                    this.mRemote.transact(86, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.IImsService
            public void enableService(String str, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsService.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(81, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.IImsService
            public void enableServiceByPhoneId(String str, boolean z, int i)
                    throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsService.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeBoolean(z);
                    obtain.writeInt(i);
                    this.mRemote.transact(82, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.IImsService
            public void enableVoLte(boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsService.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(83, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.IImsService
            public void enableVoLteByPhoneId(boolean z, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsService.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    obtain.writeInt(i);
                    this.mRemote.transact(84, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.IImsService
            public void finishDmConfig(int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsService.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(105, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.IImsService
            public void forcedUpdateRegistration(ImsProfile imsProfile) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsService.DESCRIPTOR);
                    obtain.writeTypedObject(imsProfile, 0);
                    this.mRemote.transact(45, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.IImsService
            public void forcedUpdateRegistrationByPhoneId(ImsProfile imsProfile, int i)
                    throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsService.DESCRIPTOR);
                    obtain.writeTypedObject(imsProfile, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(46, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.IImsService
            public String getAvailableNetworkType(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsService.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(12, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.IImsService
            public int[] getCallCount(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsService.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(87, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createIntArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.IImsService
            public CmcCallInfo getCmcCallInfo() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsService.DESCRIPTOR);
                    this.mRemote.transact(99, obtain, obtain2, 0);
                    obtain2.readException();
                    return (CmcCallInfo) obtain2.readTypedObject(CmcCallInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.IImsService
            public ContentValues getConfigValues(String[] strArr, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsService.DESCRIPTOR);
                    obtain.writeStringArray(strArr);
                    obtain.writeInt(i);
                    this.mRemote.transact(102, obtain, obtain2, 0);
                    obtain2.readException();
                    return (ContentValues) obtain2.readTypedObject(ContentValues.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.IImsService
            public ImsProfile[] getCurrentProfile() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsService.DESCRIPTOR);
                    this.mRemote.transact(31, obtain, obtain2, 0);
                    obtain2.readException();
                    return (ImsProfile[]) obtain2.createTypedArray(ImsProfile.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.IImsService
            public ImsProfile[] getCurrentProfileForSlot(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsService.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(32, obtain, obtain2, 0);
                    obtain2.readException();
                    return (ImsProfile[]) obtain2.createTypedArray(ImsProfile.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.IImsService
            public int getEpsFbCallCount(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsService.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(88, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.IImsService
            public boolean getGlobalSettingsValueToBoolean(String str, int i, boolean z)
                    throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsService.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(118, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.IImsService
            public int getGlobalSettingsValueToInteger(String str, int i, int i2)
                    throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsService.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(117, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.IImsService
            public String getGlobalSettingsValueToString(String str, int i, String str2)
                    throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsService.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeString(str2);
                    this.mRemote.transact(116, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public String getInterfaceDescriptor() {
                return IImsService.DESCRIPTOR;
            }

            @Override // com.sec.ims.IImsService
            public DialogEvent getLastDialogEvent(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsService.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(62, obtain, obtain2, 0);
                    obtain2.readException();
                    return (DialogEvent) obtain2.readTypedObject(DialogEvent.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.IImsService
            public String getMasterStringValue(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsService.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(64, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.IImsService
            public int getMasterValue(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsService.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(63, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.IImsService
            public int getNetworkType(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsService.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(11, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.IImsService
            public int getNrSaCallCount(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsService.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(89, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.IImsService
            public int getPhoneCount() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsService.DESCRIPTOR);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.IImsService
            public String getRcsProfileType(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsService.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(33, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.IImsService
            public ImsRegistration[] getRegistrationInfo() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsService.DESCRIPTOR);
                    this.mRemote.transact(28, obtain, obtain2, 0);
                    obtain2.readException();
                    return (ImsRegistration[]) obtain2.createTypedArray(ImsRegistration.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.IImsService
            public ImsRegistration[] getRegistrationInfoByPhoneId(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsService.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(29, obtain, obtain2, 0);
                    obtain2.readException();
                    return (ImsRegistration[]) obtain2.createTypedArray(ImsRegistration.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.IImsService
            public ImsRegistration getRegistrationInfoByServiceType(String str, int i)
                    throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsService.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(30, obtain, obtain2, 0);
                    obtain2.readException();
                    return (ImsRegistration) obtain2.readTypedObject(ImsRegistration.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.IImsService
            public int getRttMode(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsService.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(109, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.IImsService
            public int getVideocallType() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsService.DESCRIPTOR);
                    this.mRemote.transact(98, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.IImsService
            public boolean hasCrossSimImsService(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsService.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(129, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.IImsService
            public boolean hasVoLteSim() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsService.DESCRIPTOR);
                    this.mRemote.transact(79, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.IImsService
            public boolean hasVoLteSimByPhoneId(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsService.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(80, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.IImsService
            public boolean isCmcEmergencyCallSupported(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsService.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(122, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.IImsService
            public boolean isCmcEmergencyNumber(String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsService.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(123, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.IImsService
            public boolean isCmcPotentialEmergencyNumber(String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsService.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(124, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.IImsService
            public boolean isCrossSimCallingRegistered(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsService.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(128, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.IImsService
            public boolean isCrossSimCallingSupported() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsService.DESCRIPTOR);
                    this.mRemote.transact(131, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.IImsService
            public boolean isCrossSimCallingSupportedByPhoneId(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsService.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(130, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.IImsService
            public boolean isCrossSimPermanentBlocked(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsService.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(133, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.IImsService
            public boolean isForbidden() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsService.DESCRIPTOR);
                    this.mRemote.transact(90, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.IImsService
            public boolean isForbiddenByPhoneId(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsService.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(91, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.IImsService
            public boolean isImsEnabled() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsService.DESCRIPTOR);
                    this.mRemote.transact(67, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.IImsService
            public boolean isImsEnabledByPhoneId(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsService.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(68, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.IImsService
            public boolean isNonVerifiedMno(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsService.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(77, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.IImsService
            public boolean isQSSSuccessAuthAndLogin(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsService.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(50, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.IImsService
            public boolean isRcsEnabled() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsService.DESCRIPTOR);
                    this.mRemote.transact(74, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.IImsService
            public boolean isRegistered() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsService.DESCRIPTOR);
                    this.mRemote.transact(27, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.IImsService
            public boolean isRttCall(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsService.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(106, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.IImsService
            public boolean isServiceAvailable(String str, int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsService.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(76, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.IImsService
            public boolean isServiceEnabled(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsService.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(75, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.IImsService
            public boolean isServiceEnabledByPhoneId(String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsService.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(78, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.IImsService
            public boolean isSupportVoWiFiDisable5GSA(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsService.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(127, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.IImsService
            public boolean isVoLteEnabled() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsService.DESCRIPTOR);
                    this.mRemote.transact(69, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.IImsService
            public boolean isVoLteEnabledByPhoneId(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsService.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(70, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.IImsService
            public boolean isVolteEnabledFromNetwork(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsService.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(71, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.IImsService
            public boolean isVolteSupportECT() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsService.DESCRIPTOR);
                    this.mRemote.transact(72, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.IImsService
            public boolean isVolteSupportEctByPhoneId(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsService.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(73, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.IImsService
            public int registerAdhocProfile(ImsProfile imsProfile) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsService.DESCRIPTOR);
                    obtain.writeTypedObject(imsProfile, 0);
                    this.mRemote.transact(34, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.IImsService
            public int registerAdhocProfileByPhoneId(ImsProfile imsProfile, int i)
                    throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsService.DESCRIPTOR);
                    obtain.writeTypedObject(imsProfile, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(35, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.IImsService
            public String registerAutoConfigurationListener(
                    IAutoConfigurationListener iAutoConfigurationListener, int i)
                    throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsService.DESCRIPTOR);
                    obtain.writeStrongInterface(iAutoConfigurationListener);
                    obtain.writeInt(i);
                    this.mRemote.transact(21, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.IImsService
            public void registerCallback(ImsEventListener imsEventListener, String str)
                    throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsService.DESCRIPTOR);
                    obtain.writeStrongInterface(imsEventListener);
                    obtain.writeString(str);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.IImsService
            public void registerCmcRecordingListener(
                    int i, ICmcRecordingListener iCmcRecordingListener) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsService.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeStrongInterface(iCmcRecordingListener);
                    this.mRemote.transact(126, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.IImsService
            public String registerCmcRegistrationListenerForSlot(
                    IImsRegistrationListener iImsRegistrationListener, int i)
                    throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsService.DESCRIPTOR);
                    obtain.writeStrongInterface(iImsRegistrationListener);
                    obtain.writeInt(i);
                    this.mRemote.transact(120, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.IImsService
            public String registerCmsRegistrationListenerByPhoneId(
                    ICentralMsgStoreService iCentralMsgStoreService, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsService.DESCRIPTOR);
                    obtain.writeStrongInterface(iCentralMsgStoreService);
                    obtain.writeInt(i);
                    this.mRemote.transact(25, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.IImsService
            public void registerDialogEventListener(
                    int i, IDialogEventListener iDialogEventListener) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsService.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeStrongInterface(iDialogEventListener);
                    this.mRemote.transact(58, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.IImsService
            public String registerDialogEventListenerByToken(
                    int i, IDialogEventListener iDialogEventListener) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsService.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeStrongInterface(iDialogEventListener);
                    this.mRemote.transact(60, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.IImsService
            public void registerDmValueListener(IImsDmConfigListener iImsDmConfigListener)
                    throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsService.DESCRIPTOR);
                    obtain.writeStrongInterface(iImsDmConfigListener);
                    this.mRemote.transact(100, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.IImsService
            public String registerEpdgListener(IEpdgListener iEpdgListener) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsService.DESCRIPTOR);
                    obtain.writeStrongInterface(iEpdgListener);
                    this.mRemote.transact(52, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.IImsService
            public String registerImSessionListener(IImSessionListener iImSessionListener)
                    throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsService.DESCRIPTOR);
                    obtain.writeStrongInterface(iImSessionListener);
                    this.mRemote.transact(13, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.IImsService
            public String registerImSessionListenerByPhoneId(
                    IImSessionListener iImSessionListener, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsService.DESCRIPTOR);
                    obtain.writeStrongInterface(iImSessionListener);
                    obtain.writeInt(i);
                    this.mRemote.transact(14, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.IImsService
            public String registerImsOngoingFtListener(
                    IImsOngoingFtEventListener iImsOngoingFtEventListener) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsService.DESCRIPTOR);
                    obtain.writeStrongInterface(iImsOngoingFtEventListener);
                    this.mRemote.transact(17, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.IImsService
            public String registerImsOngoingFtListenerByPhoneId(
                    IImsOngoingFtEventListener iImsOngoingFtEventListener, int i)
                    throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsService.DESCRIPTOR);
                    obtain.writeStrongInterface(iImsOngoingFtEventListener);
                    obtain.writeInt(i);
                    this.mRemote.transact(18, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.IImsService
            public void registerImsRegistrationListener(
                    IImsRegistrationListener iImsRegistrationListener) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsService.DESCRIPTOR);
                    obtain.writeStrongInterface(iImsRegistrationListener);
                    this.mRemote.transact(54, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.IImsService
            public String registerImsRegistrationListenerForSlot(
                    IImsRegistrationListener iImsRegistrationListener, int i)
                    throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsService.DESCRIPTOR);
                    obtain.writeStrongInterface(iImsRegistrationListener);
                    obtain.writeInt(i);
                    this.mRemote.transact(56, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.IImsService
            public void registerProfile(List list) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsService.DESCRIPTOR);
                    obtain.writeList(list);
                    this.mRemote.transact(38, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.IImsService
            public void registerProfileByPhoneId(List list, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsService.DESCRIPTOR);
                    obtain.writeList(list);
                    obtain.writeInt(i);
                    this.mRemote.transact(39, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.IImsService
            public String registerRttEventListener(int i, IRttEventListener iRttEventListener)
                    throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsService.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeStrongInterface(iRttEventListener);
                    this.mRemote.transact(113, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.IImsService
            public String registerSimMobilityStatusListenerByPhoneId(
                    ISimMobilityStatusListener iSimMobilityStatusListener, int i)
                    throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsService.DESCRIPTOR);
                    obtain.writeStrongInterface(iSimMobilityStatusListener);
                    obtain.writeInt(i);
                    this.mRemote.transact(23, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.IImsService
            public void sendCmcRecordingEvent(int i, int i2, CmcRecordingInfo cmcRecordingInfo)
                    throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsService.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeTypedObject(cmcRecordingInfo, 0);
                    this.mRemote.transact(125, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.IImsService
            public void sendDeregister(int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsService.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(47, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.IImsService
            public void sendIidToken(String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsService.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.IImsService
            public void sendMsisdnNumber(String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsService.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.IImsService
            public void sendRttMessage(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsService.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(110, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.IImsService
            public void sendRttSessionModifyRequest(int i, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsService.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(112, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.IImsService
            public void sendRttSessionModifyResponse(int i, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsService.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(111, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.IImsService
            public void sendTryRegister() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsService.DESCRIPTOR);
                    this.mRemote.transact(42, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.IImsService
            public void sendTryRegisterByPhoneId(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsService.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(44, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.IImsService
            public void sendTryRegisterCms(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsService.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(43, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.IImsService
            public void sendVerificationCode(String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsService.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.IImsService
            public int setActiveImpu(int i, String str, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsService.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.IImsService
            public int setActiveMsisdn(int i, String str, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsService.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.IImsService
            public void setAutomaticMode(int i, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsService.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(107, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.IImsService
            public void setCrossSimPermanentBlocked(int i, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsService.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(132, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.IImsService
            public void setEmergencyPdnInfo(String str, String[] strArr, String str2, int i)
                    throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsService.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeStringArray(strArr);
                    obtain.writeString(str2);
                    obtain.writeInt(i);
                    this.mRemote.transact(51, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.IImsService
            public void setIsimLoaded() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsService.DESCRIPTOR);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.IImsService
            public void setNrInterworkingMode(int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsService.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(134, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.IImsService
            public void setProvisionedStringValue(int i, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsService.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    this.mRemote.transact(66, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.IImsService
            public void setProvisionedValue(int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsService.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(65, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.IImsService
            public void setRttMode(int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsService.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(108, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.IImsService
            public void setSimRefreshed() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsService.DESCRIPTOR);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.IImsService
            public boolean setVideocallType(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsService.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(97, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.IImsService
            public int startDmConfig(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsService.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(104, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.IImsService
            public int startLocalRingBackTone(int i, int i2, int i3) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsService.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    this.mRemote.transact(93, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.IImsService
            public int stopLocalRingBackTone() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsService.DESCRIPTOR);
                    this.mRemote.transact(94, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.IImsService
            public void suspendRegister(boolean z, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsService.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    obtain.writeInt(i);
                    this.mRemote.transact(48, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.IImsService
            public void transferCall(String str, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsService.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(92, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.IImsService
            public void triggerAutoConfigurationForApp(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsService.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(115, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.IImsService
            public void unRegisterEpdgListener(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsService.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(53, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.IImsService
            public void unregisterAutoConfigurationListener(String str, int i)
                    throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsService.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(22, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.IImsService
            public void unregisterCallback(ImsEventListener imsEventListener)
                    throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsService.DESCRIPTOR);
                    obtain.writeStrongInterface(imsEventListener);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.IImsService
            public void unregisterCmcRegistrationListenerForSlot(String str, int i)
                    throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsService.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(121, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.IImsService
            public void unregisterCmsRegistrationListenerByPhoneId(String str, int i)
                    throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsService.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(26, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.IImsService
            public void unregisterDialogEventListener(
                    int i, IDialogEventListener iDialogEventListener) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsService.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeStrongInterface(iDialogEventListener);
                    this.mRemote.transact(59, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.IImsService
            public void unregisterDialogEventListenerByToken(int i, String str)
                    throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsService.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    this.mRemote.transact(61, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.IImsService
            public void unregisterDmValueListener(IImsDmConfigListener iImsDmConfigListener)
                    throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsService.DESCRIPTOR);
                    obtain.writeStrongInterface(iImsDmConfigListener);
                    this.mRemote.transact(101, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.IImsService
            public void unregisterImSessionListener(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsService.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(15, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.IImsService
            public void unregisterImSessionListenerByPhoneId(String str, int i)
                    throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsService.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(16, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.IImsService
            public void unregisterImsOngoingFtListener(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsService.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(19, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.IImsService
            public void unregisterImsOngoingFtListenerByPhoneId(String str, int i)
                    throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsService.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(20, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.IImsService
            public void unregisterImsRegistrationListener(
                    IImsRegistrationListener iImsRegistrationListener) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsService.DESCRIPTOR);
                    obtain.writeStrongInterface(iImsRegistrationListener);
                    this.mRemote.transact(55, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.IImsService
            public void unregisterImsRegistrationListenerForSlot(String str, int i)
                    throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsService.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(57, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.IImsService
            public void unregisterRttEventListener(int i, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsService.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    this.mRemote.transact(114, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.IImsService
            public void unregisterSimMobilityStatusListenerByPhoneId(String str, int i)
                    throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsService.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(24, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.IImsService
            public boolean updateConfigValues(ContentValues contentValues, int i, int i2)
                    throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsService.DESCRIPTOR);
                    obtain.writeTypedObject(contentValues, 0);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(103, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.IImsService
            public int updateRegistration(ImsProfile imsProfile, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsService.DESCRIPTOR);
                    obtain.writeTypedObject(imsProfile, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(49, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        public Stub() {
            attachInterface(this, IImsService.DESCRIPTOR);
        }

        public static IImsService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IImsService.DESCRIPTOR);
            return (queryLocalInterface == null || !(queryLocalInterface instanceof IImsService))
                    ? new Proxy(iBinder)
                    : (IImsService) queryLocalInterface;
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2)
                throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IImsService.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IImsService.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    ImsEventListener asInterface =
                            ImsEventListener.Stub.asInterface(parcel.readStrongBinder());
                    String readString = parcel.readString();
                    parcel.enforceNoDataAvail();
                    registerCallback(asInterface, readString);
                    parcel2.writeNoException();
                    return true;
                case 2:
                    ImsEventListener asInterface2 =
                            ImsEventListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    unregisterCallback(asInterface2);
                    parcel2.writeNoException();
                    return true;
                case 3:
                    int phoneCount = getPhoneCount();
                    parcel2.writeNoException();
                    parcel2.writeInt(phoneCount);
                    return true;
                case 4:
                    setIsimLoaded();
                    parcel2.writeNoException();
                    return true;
                case 5:
                    setSimRefreshed();
                    parcel2.writeNoException();
                    return true;
                case 6:
                    int readInt = parcel.readInt();
                    String readString2 = parcel.readString();
                    String readString3 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int activeImpu = setActiveImpu(readInt, readString2, readString3);
                    parcel2.writeNoException();
                    parcel2.writeInt(activeImpu);
                    return true;
                case 7:
                    int readInt2 = parcel.readInt();
                    String readString4 = parcel.readString();
                    String readString5 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int activeMsisdn = setActiveMsisdn(readInt2, readString4, readString5);
                    parcel2.writeNoException();
                    parcel2.writeInt(activeMsisdn);
                    return true;
                case 8:
                    String readString6 = parcel.readString();
                    int readInt3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    sendVerificationCode(readString6, readInt3);
                    parcel2.writeNoException();
                    return true;
                case 9:
                    String readString7 = parcel.readString();
                    int readInt4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    sendMsisdnNumber(readString7, readInt4);
                    parcel2.writeNoException();
                    return true;
                case 10:
                    String readString8 = parcel.readString();
                    int readInt5 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    sendIidToken(readString8, readInt5);
                    parcel2.writeNoException();
                    return true;
                case 11:
                    int readInt6 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int networkType = getNetworkType(readInt6);
                    parcel2.writeNoException();
                    parcel2.writeInt(networkType);
                    return true;
                case 12:
                    String readString9 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    String availableNetworkType = getAvailableNetworkType(readString9);
                    parcel2.writeNoException();
                    parcel2.writeString(availableNetworkType);
                    return true;
                case 13:
                    IImSessionListener asInterface3 =
                            IImSessionListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    String registerImSessionListener = registerImSessionListener(asInterface3);
                    parcel2.writeNoException();
                    parcel2.writeString(registerImSessionListener);
                    return true;
                case 14:
                    IImSessionListener asInterface4 =
                            IImSessionListener.Stub.asInterface(parcel.readStrongBinder());
                    int readInt7 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    String registerImSessionListenerByPhoneId =
                            registerImSessionListenerByPhoneId(asInterface4, readInt7);
                    parcel2.writeNoException();
                    parcel2.writeString(registerImSessionListenerByPhoneId);
                    return true;
                case 15:
                    String readString10 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    unregisterImSessionListener(readString10);
                    parcel2.writeNoException();
                    return true;
                case 16:
                    String readString11 = parcel.readString();
                    int readInt8 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    unregisterImSessionListenerByPhoneId(readString11, readInt8);
                    parcel2.writeNoException();
                    return true;
                case 17:
                    IImsOngoingFtEventListener asInterface5 =
                            IImsOngoingFtEventListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    String registerImsOngoingFtListener =
                            registerImsOngoingFtListener(asInterface5);
                    parcel2.writeNoException();
                    parcel2.writeString(registerImsOngoingFtListener);
                    return true;
                case 18:
                    IImsOngoingFtEventListener asInterface6 =
                            IImsOngoingFtEventListener.Stub.asInterface(parcel.readStrongBinder());
                    int readInt9 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    String registerImsOngoingFtListenerByPhoneId =
                            registerImsOngoingFtListenerByPhoneId(asInterface6, readInt9);
                    parcel2.writeNoException();
                    parcel2.writeString(registerImsOngoingFtListenerByPhoneId);
                    return true;
                case 19:
                    String readString12 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    unregisterImsOngoingFtListener(readString12);
                    parcel2.writeNoException();
                    return true;
                case 20:
                    String readString13 = parcel.readString();
                    int readInt10 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    unregisterImsOngoingFtListenerByPhoneId(readString13, readInt10);
                    parcel2.writeNoException();
                    return true;
                case 21:
                    IAutoConfigurationListener asInterface7 =
                            IAutoConfigurationListener.Stub.asInterface(parcel.readStrongBinder());
                    int readInt11 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    String registerAutoConfigurationListener =
                            registerAutoConfigurationListener(asInterface7, readInt11);
                    parcel2.writeNoException();
                    parcel2.writeString(registerAutoConfigurationListener);
                    return true;
                case 22:
                    String readString14 = parcel.readString();
                    int readInt12 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    unregisterAutoConfigurationListener(readString14, readInt12);
                    parcel2.writeNoException();
                    return true;
                case 23:
                    ISimMobilityStatusListener asInterface8 =
                            ISimMobilityStatusListener.Stub.asInterface(parcel.readStrongBinder());
                    int readInt13 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    String registerSimMobilityStatusListenerByPhoneId =
                            registerSimMobilityStatusListenerByPhoneId(asInterface8, readInt13);
                    parcel2.writeNoException();
                    parcel2.writeString(registerSimMobilityStatusListenerByPhoneId);
                    return true;
                case 24:
                    String readString15 = parcel.readString();
                    int readInt14 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    unregisterSimMobilityStatusListenerByPhoneId(readString15, readInt14);
                    parcel2.writeNoException();
                    return true;
                case 25:
                    ICentralMsgStoreService asInterface9 =
                            ICentralMsgStoreService.Stub.asInterface(parcel.readStrongBinder());
                    int readInt15 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    String registerCmsRegistrationListenerByPhoneId =
                            registerCmsRegistrationListenerByPhoneId(asInterface9, readInt15);
                    parcel2.writeNoException();
                    parcel2.writeString(registerCmsRegistrationListenerByPhoneId);
                    return true;
                case 26:
                    String readString16 = parcel.readString();
                    int readInt16 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    unregisterCmsRegistrationListenerByPhoneId(readString16, readInt16);
                    parcel2.writeNoException();
                    return true;
                case 27:
                    boolean isRegistered = isRegistered();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isRegistered);
                    return true;
                case 28:
                    ImsRegistration[] registrationInfo = getRegistrationInfo();
                    parcel2.writeNoException();
                    parcel2.writeTypedArray(registrationInfo, 1);
                    return true;
                case 29:
                    int readInt17 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    ImsRegistration[] registrationInfoByPhoneId =
                            getRegistrationInfoByPhoneId(readInt17);
                    parcel2.writeNoException();
                    parcel2.writeTypedArray(registrationInfoByPhoneId, 1);
                    return true;
                case 30:
                    String readString17 = parcel.readString();
                    int readInt18 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    ImsRegistration registrationInfoByServiceType =
                            getRegistrationInfoByServiceType(readString17, readInt18);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(registrationInfoByServiceType, 1);
                    return true;
                case 31:
                    ImsProfile[] currentProfile = getCurrentProfile();
                    parcel2.writeNoException();
                    parcel2.writeTypedArray(currentProfile, 1);
                    return true;
                case 32:
                    int readInt19 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    ImsProfile[] currentProfileForSlot = getCurrentProfileForSlot(readInt19);
                    parcel2.writeNoException();
                    parcel2.writeTypedArray(currentProfileForSlot, 1);
                    return true;
                case 33:
                    int readInt20 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    String rcsProfileType = getRcsProfileType(readInt20);
                    parcel2.writeNoException();
                    parcel2.writeString(rcsProfileType);
                    return true;
                case 34:
                    ImsProfile imsProfile = (ImsProfile) parcel.readTypedObject(ImsProfile.CREATOR);
                    parcel.enforceNoDataAvail();
                    int registerAdhocProfile = registerAdhocProfile(imsProfile);
                    parcel2.writeNoException();
                    parcel2.writeInt(registerAdhocProfile);
                    return true;
                case 35:
                    ImsProfile imsProfile2 =
                            (ImsProfile) parcel.readTypedObject(ImsProfile.CREATOR);
                    int readInt21 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int registerAdhocProfileByPhoneId =
                            registerAdhocProfileByPhoneId(imsProfile2, readInt21);
                    parcel2.writeNoException();
                    parcel2.writeInt(registerAdhocProfileByPhoneId);
                    return true;
                case 36:
                    int readInt22 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    deregisterAdhocProfile(readInt22);
                    parcel2.writeNoException();
                    return true;
                case 37:
                    int readInt23 = parcel.readInt();
                    int readInt24 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    deregisterAdhocProfileByPhoneId(readInt23, readInt24);
                    parcel2.writeNoException();
                    return true;
                case 38:
                    ArrayList readArrayList = parcel.readArrayList(getClass().getClassLoader());
                    parcel.enforceNoDataAvail();
                    registerProfile(readArrayList);
                    parcel2.writeNoException();
                    return true;
                case 39:
                    ArrayList readArrayList2 = parcel.readArrayList(getClass().getClassLoader());
                    int readInt25 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    registerProfileByPhoneId(readArrayList2, readInt25);
                    parcel2.writeNoException();
                    return true;
                case 40:
                    ArrayList readArrayList3 = parcel.readArrayList(getClass().getClassLoader());
                    boolean readBoolean = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    deregisterProfile(readArrayList3, readBoolean);
                    parcel2.writeNoException();
                    return true;
                case 41:
                    ArrayList readArrayList4 = parcel.readArrayList(getClass().getClassLoader());
                    boolean readBoolean2 = parcel.readBoolean();
                    int readInt26 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    deregisterProfileByPhoneId(readArrayList4, readBoolean2, readInt26);
                    parcel2.writeNoException();
                    return true;
                case 42:
                    sendTryRegister();
                    parcel2.writeNoException();
                    return true;
                case 43:
                    int readInt27 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    sendTryRegisterCms(readInt27);
                    parcel2.writeNoException();
                    return true;
                case 44:
                    int readInt28 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    sendTryRegisterByPhoneId(readInt28);
                    parcel2.writeNoException();
                    return true;
                case 45:
                    ImsProfile imsProfile3 =
                            (ImsProfile) parcel.readTypedObject(ImsProfile.CREATOR);
                    parcel.enforceNoDataAvail();
                    forcedUpdateRegistration(imsProfile3);
                    parcel2.writeNoException();
                    return true;
                case 46:
                    ImsProfile imsProfile4 =
                            (ImsProfile) parcel.readTypedObject(ImsProfile.CREATOR);
                    int readInt29 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    forcedUpdateRegistrationByPhoneId(imsProfile4, readInt29);
                    parcel2.writeNoException();
                    return true;
                case 47:
                    int readInt30 = parcel.readInt();
                    int readInt31 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    sendDeregister(readInt30, readInt31);
                    parcel2.writeNoException();
                    return true;
                case 48:
                    boolean readBoolean3 = parcel.readBoolean();
                    int readInt32 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    suspendRegister(readBoolean3, readInt32);
                    parcel2.writeNoException();
                    return true;
                case 49:
                    ImsProfile imsProfile5 =
                            (ImsProfile) parcel.readTypedObject(ImsProfile.CREATOR);
                    int readInt33 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int updateRegistration = updateRegistration(imsProfile5, readInt33);
                    parcel2.writeNoException();
                    parcel2.writeInt(updateRegistration);
                    return true;
                case 50:
                    int readInt34 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isQSSSuccessAuthAndLogin = isQSSSuccessAuthAndLogin(readInt34);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isQSSSuccessAuthAndLogin);
                    return true;
                case 51:
                    String readString18 = parcel.readString();
                    String[] createStringArray = parcel.createStringArray();
                    String readString19 = parcel.readString();
                    int readInt35 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setEmergencyPdnInfo(readString18, createStringArray, readString19, readInt35);
                    parcel2.writeNoException();
                    return true;
                case 52:
                    IEpdgListener asInterface10 =
                            IEpdgListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    String registerEpdgListener = registerEpdgListener(asInterface10);
                    parcel2.writeNoException();
                    parcel2.writeString(registerEpdgListener);
                    return true;
                case 53:
                    String readString20 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    unRegisterEpdgListener(readString20);
                    parcel2.writeNoException();
                    return true;
                case 54:
                    IImsRegistrationListener asInterface11 =
                            IImsRegistrationListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    registerImsRegistrationListener(asInterface11);
                    parcel2.writeNoException();
                    return true;
                case 55:
                    IImsRegistrationListener asInterface12 =
                            IImsRegistrationListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    unregisterImsRegistrationListener(asInterface12);
                    parcel2.writeNoException();
                    return true;
                case 56:
                    IImsRegistrationListener asInterface13 =
                            IImsRegistrationListener.Stub.asInterface(parcel.readStrongBinder());
                    int readInt36 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    String registerImsRegistrationListenerForSlot =
                            registerImsRegistrationListenerForSlot(asInterface13, readInt36);
                    parcel2.writeNoException();
                    parcel2.writeString(registerImsRegistrationListenerForSlot);
                    return true;
                case 57:
                    String readString21 = parcel.readString();
                    int readInt37 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    unregisterImsRegistrationListenerForSlot(readString21, readInt37);
                    parcel2.writeNoException();
                    return true;
                case 58:
                    int readInt38 = parcel.readInt();
                    IDialogEventListener asInterface14 =
                            IDialogEventListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    registerDialogEventListener(readInt38, asInterface14);
                    parcel2.writeNoException();
                    return true;
                case 59:
                    int readInt39 = parcel.readInt();
                    IDialogEventListener asInterface15 =
                            IDialogEventListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    unregisterDialogEventListener(readInt39, asInterface15);
                    parcel2.writeNoException();
                    return true;
                case 60:
                    int readInt40 = parcel.readInt();
                    IDialogEventListener asInterface16 =
                            IDialogEventListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    String registerDialogEventListenerByToken =
                            registerDialogEventListenerByToken(readInt40, asInterface16);
                    parcel2.writeNoException();
                    parcel2.writeString(registerDialogEventListenerByToken);
                    return true;
                case 61:
                    int readInt41 = parcel.readInt();
                    String readString22 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    unregisterDialogEventListenerByToken(readInt41, readString22);
                    parcel2.writeNoException();
                    return true;
                case 62:
                    int readInt42 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    DialogEvent lastDialogEvent = getLastDialogEvent(readInt42);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(lastDialogEvent, 1);
                    return true;
                case 63:
                    int readInt43 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int masterValue = getMasterValue(readInt43);
                    parcel2.writeNoException();
                    parcel2.writeInt(masterValue);
                    return true;
                case 64:
                    int readInt44 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    String masterStringValue = getMasterStringValue(readInt44);
                    parcel2.writeNoException();
                    parcel2.writeString(masterStringValue);
                    return true;
                case 65:
                    int readInt45 = parcel.readInt();
                    int readInt46 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setProvisionedValue(readInt45, readInt46);
                    parcel2.writeNoException();
                    return true;
                case 66:
                    int readInt47 = parcel.readInt();
                    String readString23 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    setProvisionedStringValue(readInt47, readString23);
                    parcel2.writeNoException();
                    return true;
                case 67:
                    boolean isImsEnabled = isImsEnabled();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isImsEnabled);
                    return true;
                case 68:
                    int readInt48 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isImsEnabledByPhoneId = isImsEnabledByPhoneId(readInt48);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isImsEnabledByPhoneId);
                    return true;
                case 69:
                    boolean isVoLteEnabled = isVoLteEnabled();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isVoLteEnabled);
                    return true;
                case 70:
                    int readInt49 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isVoLteEnabledByPhoneId = isVoLteEnabledByPhoneId(readInt49);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isVoLteEnabledByPhoneId);
                    return true;
                case 71:
                    int readInt50 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isVolteEnabledFromNetwork = isVolteEnabledFromNetwork(readInt50);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isVolteEnabledFromNetwork);
                    return true;
                case 72:
                    boolean isVolteSupportECT = isVolteSupportECT();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isVolteSupportECT);
                    return true;
                case 73:
                    int readInt51 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isVolteSupportEctByPhoneId = isVolteSupportEctByPhoneId(readInt51);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isVolteSupportEctByPhoneId);
                    return true;
                case 74:
                    boolean isRcsEnabled = isRcsEnabled();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isRcsEnabled);
                    return true;
                case 75:
                    String readString24 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean isServiceEnabled = isServiceEnabled(readString24);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isServiceEnabled);
                    return true;
                case 76:
                    String readString25 = parcel.readString();
                    int readInt52 = parcel.readInt();
                    int readInt53 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isServiceAvailable =
                            isServiceAvailable(readString25, readInt52, readInt53);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isServiceAvailable);
                    return true;
                case 77:
                    int readInt54 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isNonVerifiedMno = isNonVerifiedMno(readInt54);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isNonVerifiedMno);
                    return true;
                case 78:
                    String readString26 = parcel.readString();
                    int readInt55 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isServiceEnabledByPhoneId =
                            isServiceEnabledByPhoneId(readString26, readInt55);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isServiceEnabledByPhoneId);
                    return true;
                case 79:
                    boolean hasVoLteSim = hasVoLteSim();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(hasVoLteSim);
                    return true;
                case 80:
                    int readInt56 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean hasVoLteSimByPhoneId = hasVoLteSimByPhoneId(readInt56);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(hasVoLteSimByPhoneId);
                    return true;
                case 81:
                    String readString27 = parcel.readString();
                    boolean readBoolean4 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    enableService(readString27, readBoolean4);
                    parcel2.writeNoException();
                    return true;
                case 82:
                    String readString28 = parcel.readString();
                    boolean readBoolean5 = parcel.readBoolean();
                    int readInt57 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    enableServiceByPhoneId(readString28, readBoolean5, readInt57);
                    parcel2.writeNoException();
                    return true;
                case 83:
                    boolean readBoolean6 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    enableVoLte(readBoolean6);
                    parcel2.writeNoException();
                    return true;
                case 84:
                    boolean readBoolean7 = parcel.readBoolean();
                    int readInt58 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    enableVoLteByPhoneId(readBoolean7, readInt58);
                    parcel2.writeNoException();
                    return true;
                case 85:
                    boolean readBoolean8 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    enableRcs(readBoolean8);
                    parcel2.writeNoException();
                    return true;
                case 86:
                    boolean readBoolean9 = parcel.readBoolean();
                    int readInt59 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    enableRcsByPhoneId(readBoolean9, readInt59);
                    parcel2.writeNoException();
                    return true;
                case 87:
                    int readInt60 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int[] callCount = getCallCount(readInt60);
                    parcel2.writeNoException();
                    parcel2.writeIntArray(callCount);
                    return true;
                case 88:
                    int readInt61 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int epsFbCallCount = getEpsFbCallCount(readInt61);
                    parcel2.writeNoException();
                    parcel2.writeInt(epsFbCallCount);
                    return true;
                case 89:
                    int readInt62 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int nrSaCallCount = getNrSaCallCount(readInt62);
                    parcel2.writeNoException();
                    parcel2.writeInt(nrSaCallCount);
                    return true;
                case 90:
                    boolean isForbidden = isForbidden();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isForbidden);
                    return true;
                case 91:
                    int readInt63 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isForbiddenByPhoneId = isForbiddenByPhoneId(readInt63);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isForbiddenByPhoneId);
                    return true;
                case 92:
                    String readString29 = parcel.readString();
                    String readString30 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    transferCall(readString29, readString30);
                    parcel2.writeNoException();
                    return true;
                case 93:
                    int readInt64 = parcel.readInt();
                    int readInt65 = parcel.readInt();
                    int readInt66 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int startLocalRingBackTone =
                            startLocalRingBackTone(readInt64, readInt65, readInt66);
                    parcel2.writeNoException();
                    parcel2.writeInt(startLocalRingBackTone);
                    return true;
                case 94:
                    int stopLocalRingBackTone = stopLocalRingBackTone();
                    parcel2.writeNoException();
                    parcel2.writeInt(stopLocalRingBackTone);
                    return true;
                case 95:
                    int readInt67 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    changeAudioPath(readInt67);
                    parcel2.writeNoException();
                    return true;
                case 96:
                    int readInt68 = parcel.readInt();
                    int readInt69 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    changeAudioPathForSlot(readInt68, readInt69);
                    parcel2.writeNoException();
                    return true;
                case 97:
                    int readInt70 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean videocallType = setVideocallType(readInt70);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(videocallType);
                    return true;
                case 98:
                    int videocallType2 = getVideocallType();
                    parcel2.writeNoException();
                    parcel2.writeInt(videocallType2);
                    return true;
                case 99:
                    CmcCallInfo cmcCallInfo = getCmcCallInfo();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(cmcCallInfo, 1);
                    return true;
                case 100:
                    IImsDmConfigListener asInterface17 =
                            IImsDmConfigListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    registerDmValueListener(asInterface17);
                    parcel2.writeNoException();
                    return true;
                case 101:
                    IImsDmConfigListener asInterface18 =
                            IImsDmConfigListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    unregisterDmValueListener(asInterface18);
                    parcel2.writeNoException();
                    return true;
                case 102:
                    String[] createStringArray2 = parcel.createStringArray();
                    int readInt71 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    ContentValues configValues = getConfigValues(createStringArray2, readInt71);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(configValues, 1);
                    return true;
                case 103:
                    ContentValues contentValues =
                            (ContentValues) parcel.readTypedObject(ContentValues.CREATOR);
                    int readInt72 = parcel.readInt();
                    int readInt73 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean updateConfigValues =
                            updateConfigValues(contentValues, readInt72, readInt73);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(updateConfigValues);
                    return true;
                case 104:
                    int readInt74 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int startDmConfig = startDmConfig(readInt74);
                    parcel2.writeNoException();
                    parcel2.writeInt(startDmConfig);
                    return true;
                case 105:
                    int readInt75 = parcel.readInt();
                    int readInt76 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    finishDmConfig(readInt75, readInt76);
                    parcel2.writeNoException();
                    return true;
                case 106:
                    int readInt77 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isRttCall = isRttCall(readInt77);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isRttCall);
                    return true;
                case 107:
                    int readInt78 = parcel.readInt();
                    boolean readBoolean10 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setAutomaticMode(readInt78, readBoolean10);
                    parcel2.writeNoException();
                    return true;
                case 108:
                    int readInt79 = parcel.readInt();
                    int readInt80 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setRttMode(readInt79, readInt80);
                    parcel2.writeNoException();
                    return true;
                case 109:
                    int readInt81 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int rttMode = getRttMode(readInt81);
                    parcel2.writeNoException();
                    parcel2.writeInt(rttMode);
                    return true;
                case 110:
                    String readString31 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    sendRttMessage(readString31);
                    parcel2.writeNoException();
                    return true;
                case 111:
                    int readInt82 = parcel.readInt();
                    boolean readBoolean11 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    sendRttSessionModifyResponse(readInt82, readBoolean11);
                    parcel2.writeNoException();
                    return true;
                case 112:
                    int readInt83 = parcel.readInt();
                    boolean readBoolean12 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    sendRttSessionModifyRequest(readInt83, readBoolean12);
                    parcel2.writeNoException();
                    return true;
                case 113:
                    int readInt84 = parcel.readInt();
                    IRttEventListener asInterface19 =
                            IRttEventListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    String registerRttEventListener =
                            registerRttEventListener(readInt84, asInterface19);
                    parcel2.writeNoException();
                    parcel2.writeString(registerRttEventListener);
                    return true;
                case 114:
                    int readInt85 = parcel.readInt();
                    String readString32 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    unregisterRttEventListener(readInt85, readString32);
                    parcel2.writeNoException();
                    return true;
                case 115:
                    int readInt86 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    triggerAutoConfigurationForApp(readInt86);
                    parcel2.writeNoException();
                    return true;
                case 116:
                    String readString33 = parcel.readString();
                    int readInt87 = parcel.readInt();
                    String readString34 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    String globalSettingsValueToString =
                            getGlobalSettingsValueToString(readString33, readInt87, readString34);
                    parcel2.writeNoException();
                    parcel2.writeString(globalSettingsValueToString);
                    return true;
                case 117:
                    String readString35 = parcel.readString();
                    int readInt88 = parcel.readInt();
                    int readInt89 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int globalSettingsValueToInteger =
                            getGlobalSettingsValueToInteger(readString35, readInt88, readInt89);
                    parcel2.writeNoException();
                    parcel2.writeInt(globalSettingsValueToInteger);
                    return true;
                case 118:
                    String readString36 = parcel.readString();
                    int readInt90 = parcel.readInt();
                    boolean readBoolean13 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean globalSettingsValueToBoolean =
                            getGlobalSettingsValueToBoolean(readString36, readInt90, readBoolean13);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(globalSettingsValueToBoolean);
                    return true;
                case 119:
                    dump();
                    parcel2.writeNoException();
                    return true;
                case 120:
                    IImsRegistrationListener asInterface20 =
                            IImsRegistrationListener.Stub.asInterface(parcel.readStrongBinder());
                    int readInt91 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    String registerCmcRegistrationListenerForSlot =
                            registerCmcRegistrationListenerForSlot(asInterface20, readInt91);
                    parcel2.writeNoException();
                    parcel2.writeString(registerCmcRegistrationListenerForSlot);
                    return true;
                case 121:
                    String readString37 = parcel.readString();
                    int readInt92 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    unregisterCmcRegistrationListenerForSlot(readString37, readInt92);
                    parcel2.writeNoException();
                    return true;
                case 122:
                    int readInt93 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isCmcEmergencyCallSupported = isCmcEmergencyCallSupported(readInt93);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isCmcEmergencyCallSupported);
                    return true;
                case 123:
                    String readString38 = parcel.readString();
                    int readInt94 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isCmcEmergencyNumber = isCmcEmergencyNumber(readString38, readInt94);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isCmcEmergencyNumber);
                    return true;
                case 124:
                    String readString39 = parcel.readString();
                    int readInt95 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isCmcPotentialEmergencyNumber =
                            isCmcPotentialEmergencyNumber(readString39, readInt95);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isCmcPotentialEmergencyNumber);
                    return true;
                case 125:
                    int readInt96 = parcel.readInt();
                    int readInt97 = parcel.readInt();
                    CmcRecordingInfo cmcRecordingInfo =
                            (CmcRecordingInfo) parcel.readTypedObject(CmcRecordingInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    sendCmcRecordingEvent(readInt96, readInt97, cmcRecordingInfo);
                    parcel2.writeNoException();
                    return true;
                case 126:
                    int readInt98 = parcel.readInt();
                    ICmcRecordingListener asInterface21 =
                            ICmcRecordingListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    registerCmcRecordingListener(readInt98, asInterface21);
                    parcel2.writeNoException();
                    return true;
                case 127:
                    int readInt99 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isSupportVoWiFiDisable5GSA = isSupportVoWiFiDisable5GSA(readInt99);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isSupportVoWiFiDisable5GSA);
                    return true;
                case 128:
                    int readInt100 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isCrossSimCallingRegistered = isCrossSimCallingRegistered(readInt100);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isCrossSimCallingRegistered);
                    return true;
                case 129:
                    int readInt101 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean hasCrossSimImsService = hasCrossSimImsService(readInt101);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(hasCrossSimImsService);
                    return true;
                case 130:
                    int readInt102 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isCrossSimCallingSupportedByPhoneId =
                            isCrossSimCallingSupportedByPhoneId(readInt102);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isCrossSimCallingSupportedByPhoneId);
                    return true;
                case 131:
                    boolean isCrossSimCallingSupported = isCrossSimCallingSupported();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isCrossSimCallingSupported);
                    return true;
                case 132:
                    int readInt103 = parcel.readInt();
                    boolean readBoolean14 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setCrossSimPermanentBlocked(readInt103, readBoolean14);
                    parcel2.writeNoException();
                    return true;
                case 133:
                    int readInt104 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isCrossSimPermanentBlocked = isCrossSimPermanentBlocked(readInt104);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isCrossSimPermanentBlocked);
                    return true;
                case 134:
                    int readInt105 = parcel.readInt();
                    int readInt106 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setNrInterworkingMode(readInt105, readInt106);
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
