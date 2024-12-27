package com.android.settings.vpn2;

import android.app.AppOpsManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkRequest;
import android.net.VpnManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.os.SystemProperties;
import android.os.UserHandle;
import android.os.UserManager;
import android.security.LegacyVpnProfileStore;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment$$ExternalSyntheticOutline0;
import androidx.fragment.app.FragmentActivity;
import androidx.preference.Preference;
import androidx.preference.PreferenceGroup;
import androidx.preference.PreferenceScreen;
import com.android.internal.net.LegacyVpnInfo;
import com.android.internal.net.VpnConfig;
import com.android.internal.net.VpnProfile;
import com.android.internal.widget.LockPatternUtils;
import com.android.settings.R;
import com.android.settings.Utils;
import com.android.settings.core.SubSettingLauncher;
import com.android.settings.dashboard.RestrictedDashboardFragment;
import com.android.settings.overlay.FeatureFactoryImpl;
import com.android.settings.password.ChooseLockGeneric;
import com.android.settings.utils.AndroidKeystoreAliasLoader;
import com.android.settings.widget.GearPreference;
import com.android.settingslib.RestrictedLockUtilsInternal;
import com.android.settingslib.applications.RecentAppOpsAccess;
import com.google.android.collect.Lists;
import com.samsung.android.knox.keystore.CertificateProvisioning;
import com.samsung.android.knox.net.apn.ApnSettings;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class VpnSettings extends RestrictedDashboardFragment implements Handler.Callback, Preference.OnPreferenceClickListener {
    public static final boolean DEBUG = Log.isLoggable("VpnSettings", 3);
    public static final NetworkRequest VPN_REQUEST = new NetworkRequest.Builder().removeCapability(15).removeCapability(13).removeCapability(14).build();
    public String[] caCert;
    public final Map mAppPreferences;
    public LegacyVpnInfo mConnectedLegacyVpn;
    public ConnectivityManager mConnectivityManager;
    public AdvancedVpnFeatureProvider mFeatureProvider;
    public final AnonymousClass4 mGearListener;
    public int mIsUserAddProfilesAllowed;
    public final Map mLegacyVpnPreferences;
    public final AnonymousClass5 mNetworkCallback;
    public PreferenceScreen mPreferenceScreen;
    public MenuItem mProgressMenuItem;
    public UcmAliasLoader mUcmAliasLoader;
    public boolean mUnavailable;
    public Handler mUpdater;
    public HandlerThread mUpdaterThread;
    public UserManager mUserManager;
    public MenuItem mVpnCreateMenuItem;
    public VpnManager mVpnManager;
    public PreferenceScreen mVpnPreferenceGroup;
    public String[] userCert;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class UcmAliasLoader extends AsyncTask {
        public final AndroidKeystoreAliasLoader androidKeystoreAliasLoader = new AndroidKeystoreAliasLoader(null);
        public boolean isRunning;

        /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
        public final class Credential {
            public final String alias;
            public final String type;

            public Credential(String str, String str2) {
                this.type = str;
                this.alias = str2;
            }
        }

        public UcmAliasLoader() {
        }

        public static Collection getAliasList(Collection collection, Credential[] credentialArr, boolean z) {
            ArrayList arrayList = new ArrayList();
            if (collection == null) {
                collection = new ArrayList();
            }
            if (credentialArr != null) {
                for (Credential credential : credentialArr) {
                    if (!z) {
                        arrayList.add(credential.alias);
                    } else if (credential.type.equals(CertificateProvisioning.CA_CERTIFICATE)) {
                        arrayList.add(credential.alias);
                    }
                }
            }
            if (arrayList.size() > 0) {
                Collections.sort(arrayList);
                Iterator it = arrayList.iterator();
                while (it.hasNext()) {
                    collection.add((String) it.next());
                }
            }
            return collection;
        }

        /* JADX WARN: Removed duplicated region for block: B:13:0x0080 A[EXC_TOP_SPLITTER, SYNTHETIC] */
        /* JADX WARN: Removed duplicated region for block: B:30:0x00aa A[SYNTHETIC] */
        @Override // android.os.AsyncTask
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final java.lang.Object doInBackground(java.lang.Object[] r10) {
            /*
                r9 = this;
                java.lang.Void[] r10 = (java.lang.Void[]) r10
                int r9 = android.os.Process.myUid()
                com.samsung.android.knox.ucm.core.UniversalCredentialUtil$UcmUriBuilder r10 = new com.samsung.android.knox.ucm.core.UniversalCredentialUtil$UcmUriBuilder
                java.lang.String r0 = "KNOX"
                r10.<init>(r0)
                r0 = 1
                com.samsung.android.knox.ucm.core.UniversalCredentialUtil$UcmUriBuilder r10 = r10.setResourceId(r0)
                com.samsung.android.knox.ucm.core.UniversalCredentialUtil$UcmUriBuilder r9 = r10.setUid(r9)
                java.lang.String r9 = r9.build()
                com.samsung.android.knox.ucm.core.UniversalCredentialUtil r10 = com.samsung.android.knox.ucm.core.UniversalCredentialUtil.getInstance()
                r1 = 0
                if (r10 != 0) goto L23
            L21:
                r9 = r1
                goto L2f
            L23:
                android.os.Bundle r9 = r10.saw(r9, r0)
                if (r9 == 0) goto L21
                java.lang.String r10 = "stringarrayresponse"
                java.lang.String[] r9 = r9.getStringArray(r10)
            L2f:
                if (r9 == 0) goto Lae
                int r10 = r9.length
                com.android.settings.vpn2.VpnSettings$UcmAliasLoader$Credential[] r10 = new com.android.settings.vpn2.VpnSettings.UcmAliasLoader.Credential[r10]
                int r0 = r9.length
                r2 = 0
                r3 = r2
            L37:
                if (r2 >= r0) goto Lad
                r4 = r9[r2]
                if (r4 == 0) goto Laa
                int r5 = r4.length()
                java.lang.String r6 = "VpnSettings"
                if (r5 > 0) goto L47
            L45:
                r5 = r1
                goto L7e
            L47:
                java.lang.String r5 = "com.samsung.ucs.ucsservice"
                android.os.IBinder r5 = android.os.ServiceManager.getService(r5)     // Catch: java.lang.Exception -> L77
                com.samsung.android.knox.ucm.core.IUcmService r5 = com.samsung.android.knox.ucm.core.IUcmService.Stub.asInterface(r5)     // Catch: java.lang.Exception -> L77
                if (r5 == 0) goto L45
                com.samsung.android.knox.ucm.core.ucmRetParcelable r5 = r5.getCertificateChain(r4)     // Catch: java.lang.Exception -> L77
                if (r5 == 0) goto L45
                byte[] r5 = r5.mData     // Catch: java.lang.Exception -> L77
                if (r5 != 0) goto L5e
                goto L45
            L5e:
                java.lang.String r7 = "X.509"
                java.security.cert.CertificateFactory r7 = java.security.cert.CertificateFactory.getInstance(r7)     // Catch: java.security.cert.CertificateException -> L70 java.lang.Exception -> L77
                java.io.ByteArrayInputStream r8 = new java.io.ByteArrayInputStream     // Catch: java.security.cert.CertificateException -> L70 java.lang.Exception -> L77
                r8.<init>(r5)     // Catch: java.security.cert.CertificateException -> L70 java.lang.Exception -> L77
                java.security.cert.Certificate r5 = r7.generateCertificate(r8)     // Catch: java.security.cert.CertificateException -> L70 java.lang.Exception -> L77
                java.security.cert.X509Certificate r5 = (java.security.cert.X509Certificate) r5     // Catch: java.security.cert.CertificateException -> L70 java.lang.Exception -> L77
                goto L7e
            L70:
                r5 = move-exception
                java.lang.String r7 = "Failed to toCertificate"
                android.util.Log.w(r6, r7, r5)     // Catch: java.lang.Exception -> L77
                goto L45
            L77:
                r5 = move-exception
                java.lang.String r7 = "Failed to getUCMCertificate"
                android.util.Log.w(r6, r7, r5)
                goto L45
            L7e:
                if (r5 == 0) goto Laa
                int r5 = r5.getBasicConstraints()     // Catch: java.lang.Exception -> La1
                if (r5 <= 0) goto L94
                int r5 = r3 + 1
                com.android.settings.vpn2.VpnSettings$UcmAliasLoader$Credential r7 = new com.android.settings.vpn2.VpnSettings$UcmAliasLoader$Credential     // Catch: java.lang.Exception -> L92
                java.lang.String r8 = "CACERT_"
                r7.<init>(r8, r4)     // Catch: java.lang.Exception -> L92
                r10[r3] = r7     // Catch: java.lang.Exception -> L92
                goto L9f
            L92:
                r3 = move-exception
                goto La4
            L94:
                int r5 = r3 + 1
                com.android.settings.vpn2.VpnSettings$UcmAliasLoader$Credential r7 = new com.android.settings.vpn2.VpnSettings$UcmAliasLoader$Credential     // Catch: java.lang.Exception -> L92
                java.lang.String r8 = "USRCERT_"
                r7.<init>(r8, r4)     // Catch: java.lang.Exception -> L92
                r10[r3] = r7     // Catch: java.lang.Exception -> L92
            L9f:
                r3 = r5
                goto Laa
            La1:
                r4 = move-exception
                r5 = r3
                r3 = r4
            La4:
                java.lang.String r4 = "Failed to convertToPemCert, "
                androidx.glance.session.SessionManagerImpl$scope$1$$ExternalSyntheticOutline0.m(r4, r3, r6)
                goto L9f
            Laa:
                int r2 = r2 + 1
                goto L37
            Lad:
                r1 = r10
            Lae:
                return r1
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.settings.vpn2.VpnSettings.UcmAliasLoader.doInBackground(java.lang.Object[]):java.lang.Object");
        }

        @Override // android.os.AsyncTask
        public final void onPostExecute(Object obj) {
            Credential[] credentialArr = (Credential[]) obj;
            this.isRunning = false;
            Collection aliasList = getAliasList(this.androidKeystoreAliasLoader.mKeyCertAliases, credentialArr, false);
            Collection aliasList2 = getAliasList(this.androidKeystoreAliasLoader.mCaCertAliases, credentialArr, true);
            VpnSettings.this.userCert = (String[]) aliasList.toArray(new String[0]);
            VpnSettings.this.caCert = (String[]) aliasList2.toArray(new String[0]);
            MenuItem menuItem = VpnSettings.this.mProgressMenuItem;
            if (menuItem != null) {
                menuItem.setVisible(false);
                VpnSettings.this.mVpnCreateMenuItem.setVisible(true);
            }
            VpnSettings.this.mVpnPreferenceGroup.setEnabled(true);
        }

        @Override // android.os.AsyncTask
        public final void onPreExecute() {
            this.isRunning = true;
            MenuItem menuItem = VpnSettings.this.mProgressMenuItem;
            if (menuItem != null) {
                menuItem.setVisible(true);
                VpnSettings.this.mVpnCreateMenuItem.setVisible(false);
            }
            VpnSettings.this.mVpnPreferenceGroup.setEnabled(false);
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public static class UpdatePreferences implements Runnable {
        public final VpnSettings mSettings;
        public List vpnProfiles = Collections.emptyList();
        public List vpnApps = Collections.emptyList();
        public Map connectedLegacyVpns = Collections.emptyMap();
        public Set connectedAppVpns = Collections.emptySet();
        public Set alwaysOnAppVpnInfos = Collections.emptySet();
        public String lockdownVpnKey = null;

        public UpdatePreferences(VpnSettings vpnSettings) {
            this.mSettings = vpnSettings;
        }

        @Override // java.lang.Runnable
        public final void run() {
            if (this.mSettings.canAddPreferences()) {
                ArraySet arraySet = new ArraySet();
                Iterator it = this.vpnProfiles.iterator();
                while (true) {
                    boolean z = false;
                    if (!it.hasNext()) {
                        break;
                    }
                    VpnProfile vpnProfile = (VpnProfile) it.next();
                    LegacyVpnPreference findOrCreatePreference = this.mSettings.findOrCreatePreference(vpnProfile, true);
                    if (this.connectedLegacyVpns.containsKey(vpnProfile.key)) {
                        findOrCreatePreference.setState$1(((LegacyVpnInfo) this.connectedLegacyVpns.get(vpnProfile.key)).state);
                    } else {
                        findOrCreatePreference.setState$1(-1);
                    }
                    String str = this.lockdownVpnKey;
                    if (str != null && str.equals(vpnProfile.key)) {
                        z = true;
                    }
                    if (findOrCreatePreference.mIsAlwaysOn != z) {
                        findOrCreatePreference.mIsAlwaysOn = z;
                        findOrCreatePreference.updateSummary();
                    }
                    boolean isLegacyType = VpnProfile.isLegacyType(vpnProfile.type);
                    if (findOrCreatePreference.mIsInsecureVpn != isLegacyType) {
                        findOrCreatePreference.mIsInsecureVpn = isLegacyType;
                        findOrCreatePreference.updateSummary();
                    }
                    arraySet.add(findOrCreatePreference);
                }
                for (LegacyVpnInfo legacyVpnInfo : this.connectedLegacyVpns.values()) {
                    LegacyVpnPreference findOrCreatePreference2 = this.mSettings.findOrCreatePreference(new VpnProfile(legacyVpnInfo.key), false);
                    findOrCreatePreference2.setState$1(legacyVpnInfo.state);
                    String str2 = this.lockdownVpnKey;
                    boolean z2 = str2 != null && str2.equals(legacyVpnInfo.key);
                    if (findOrCreatePreference2.mIsAlwaysOn != z2) {
                        findOrCreatePreference2.mIsAlwaysOn = z2;
                        findOrCreatePreference2.updateSummary();
                    }
                    arraySet.add(findOrCreatePreference2);
                }
                for (AppVpnInfo appVpnInfo : this.vpnApps) {
                    AppPreference findOrCreatePreference3 = this.mSettings.findOrCreatePreference(appVpnInfo);
                    if (this.connectedAppVpns.contains(appVpnInfo)) {
                        findOrCreatePreference3.setState$1(3);
                    } else {
                        findOrCreatePreference3.setState$1(-1);
                    }
                    boolean contains = this.alwaysOnAppVpnInfos.contains(appVpnInfo);
                    if (findOrCreatePreference3.mIsAlwaysOn != contains) {
                        findOrCreatePreference3.mIsAlwaysOn = contains;
                        findOrCreatePreference3.updateSummary();
                    }
                    arraySet.add(findOrCreatePreference3);
                }
                if (VpnSettings.DEBUG) {
                    this.mSettings.getClass();
                    Log.d("VpnSettings", "isAdvancedVpnSupported() : false");
                }
                this.mSettings.getClass();
                this.mSettings.setShownPreferences(arraySet);
            }
        }
    }

    /* JADX WARN: Type inference failed for: r0v4, types: [com.android.settings.vpn2.VpnSettings$4] */
    /* JADX WARN: Type inference failed for: r0v5, types: [com.android.settings.vpn2.VpnSettings$5] */
    public VpnSettings() {
        super("no_config_vpn");
        this.mLegacyVpnPreferences = new ArrayMap();
        this.mAppPreferences = new ArrayMap();
        this.userCert = null;
        this.caCert = null;
        this.mGearListener = new GearPreference.OnGearClickListener() { // from class: com.android.settings.vpn2.VpnSettings.4
            @Override // com.android.settings.widget.GearPreference.OnGearClickListener
            public final void onGearClick(GearPreference gearPreference) {
                boolean z = gearPreference instanceof LegacyVpnPreference;
                VpnSettings vpnSettings = VpnSettings.this;
                if (z) {
                    ConfigDialogFragment.show(vpnSettings, ((LegacyVpnPreference) gearPreference).mProfile, true, true, vpnSettings.userCert, vpnSettings.caCert);
                    return;
                }
                if (gearPreference instanceof AppPreference) {
                    AppPreference appPreference = (AppPreference) gearPreference;
                    boolean z2 = VpnSettings.DEBUG;
                    Context prefContext = vpnSettings.getPrefContext();
                    Bundle bundle = new Bundle();
                    bundle.putString("package", appPreference.mPackageName);
                    SubSettingLauncher subSettingLauncher = new SubSettingLauncher(prefContext);
                    String name = AppManagementFragment.class.getName();
                    SubSettingLauncher.LaunchRequest launchRequest = subSettingLauncher.mLaunchRequest;
                    launchRequest.mDestinationName = name;
                    launchRequest.mArguments = bundle;
                    launchRequest.mTitle = appPreference.mName;
                    launchRequest.mSourceMetricsCategory = 100;
                    launchRequest.mUserHandle = new UserHandle(appPreference.mUserId);
                    subSettingLauncher.launch();
                }
            }
        };
        this.mNetworkCallback = new ConnectivityManager.NetworkCallback() { // from class: com.android.settings.vpn2.VpnSettings.5
            @Override // android.net.ConnectivityManager.NetworkCallback
            public final void onAvailable(Network network) {
                Handler handler = VpnSettings.this.mUpdater;
                if (handler != null) {
                    handler.sendEmptyMessage(0);
                }
            }

            @Override // android.net.ConnectivityManager.NetworkCallback
            public final void onLost(Network network) {
                Handler handler = VpnSettings.this.mUpdater;
                if (handler != null) {
                    handler.sendEmptyMessage(0);
                }
            }
        };
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r9v3, types: [java.util.Set] */
    /* JADX WARN: Type inference failed for: r9v4, types: [java.util.Set] */
    /* JADX WARN: Type inference failed for: r9v5, types: [android.util.ArraySet] */
    public static List<AppVpnInfo> getVpnApps(Context context, boolean z, AdvancedVpnFeatureProvider advancedVpnFeatureProvider, AppOpsManager appOpsManager) {
        ?? singleton;
        ArrayList newArrayList = Lists.newArrayList();
        if (z) {
            singleton = new ArraySet();
            UserManager userManager = UserManager.get(context);
            for (UserHandle userHandle : userManager.getUserProfiles()) {
                if (!Utils.shouldHideUser(userHandle, userManager)) {
                    singleton.add(Integer.valueOf(userHandle.getIdentifier()));
                }
            }
        } else {
            singleton = Collections.singleton(Integer.valueOf(UserHandle.myUserId()));
        }
        advancedVpnFeatureProvider.getClass();
        List<AppOpsManager.PackageOps> packagesForOps = appOpsManager.getPackagesForOps(new int[]{47, 94});
        if (packagesForOps != null) {
            for (AppOpsManager.PackageOps packageOps : packagesForOps) {
                int userId = UserHandle.getUserId(packageOps.getUid());
                if (singleton.contains(Integer.valueOf(userId))) {
                    packageOps.getPackageName();
                    boolean z2 = false;
                    for (AppOpsManager.OpEntry opEntry : packageOps.getOps()) {
                        if (opEntry.getOp() == 47 || opEntry.getOp() == 94) {
                            if (opEntry.getMode() == 0) {
                                z2 = true;
                            }
                        }
                    }
                    if (z2) {
                        if ("com.samsung.android.fast".equals(packageOps.getPackageName())) {
                            if (context.getPackageManager().checkSignatures(RecentAppOpsAccess.ANDROID_SYSTEM_PACKAGE_NAME, "com.samsung.android.fast") != 0) {
                                Log.e("VpnSettings", "Secure Wi-Fi signature mismatched");
                            }
                        }
                        newArrayList.add(new AppVpnInfo(userId, packageOps.getPackageName()));
                    }
                }
            }
        }
        Collections.sort(newArrayList);
        return newArrayList;
    }

    public static void updatePreferenceGroup(PreferenceGroup preferenceGroup, Collection collection) {
        for (int preferenceCount = preferenceGroup.getPreferenceCount() - 1; preferenceCount >= 0; preferenceCount--) {
            Preference preference = preferenceGroup.getPreference(preferenceCount);
            if (collection.contains(preference)) {
                collection.remove(preference);
            } else {
                preferenceGroup.removePreference(preference);
            }
        }
    }

    public boolean canAddPreferences() {
        return isAdded();
    }

    public LegacyVpnPreference findOrCreatePreference(VpnProfile vpnProfile, boolean z) {
        boolean z2;
        LegacyVpnPreference legacyVpnPreference = (LegacyVpnPreference) ((ArrayMap) this.mLegacyVpnPreferences).get(vpnProfile.key);
        if (legacyVpnPreference == null) {
            legacyVpnPreference = new LegacyVpnPreference(getPrefContext(), null);
            legacyVpnPreference.setIcon(R.drawable.ic_vpn_key);
            legacyVpnPreference.mIconSize = 2;
            legacyVpnPreference.mOnGearClickListener = this.mGearListener;
            legacyVpnPreference.notifyChanged();
            legacyVpnPreference.setOnPreferenceClickListener(this);
            ((ArrayMap) this.mLegacyVpnPreferences).put(vpnProfile.key, legacyVpnPreference);
            z2 = true;
        } else {
            z2 = false;
        }
        if (z2 || z) {
            VpnProfile vpnProfile2 = legacyVpnPreference.mProfile;
            String str = vpnProfile2 != null ? vpnProfile2.name : null;
            String str2 = vpnProfile.name;
            if (!TextUtils.equals(str, str2)) {
                legacyVpnPreference.setTitle(str2);
                legacyVpnPreference.notifyHierarchyChanged();
            }
            legacyVpnPreference.mProfile = vpnProfile;
        }
        return legacyVpnPreference;
    }

    @Override // com.android.settings.dashboard.DashboardFragment
    /* renamed from: getLogTag */
    public final String getTAG() {
        return "VpnSettings";
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 100;
    }

    @Override // com.android.settings.dashboard.DashboardFragment, com.android.settings.core.InstrumentedPreferenceFragment
    public final int getPreferenceScreenResId() {
        return R.xml.vpn_settings2;
    }

    @Override // android.os.Handler.Callback
    public final boolean handleMessage(Message message) {
        int identifier;
        String alwaysOnVpnPackageForUser;
        VpnConfig vpnConfig;
        FragmentActivity activity = getActivity();
        if (activity == null) {
            return true;
        }
        Context applicationContext = activity.getApplicationContext();
        ArrayList newArrayList = Lists.newArrayList();
        for (String str : LegacyVpnProfileStore.list("VPN_")) {
            VpnProfile decode = VpnProfile.decode(str, LegacyVpnProfileStore.get("VPN_" + str));
            if (decode != null) {
                newArrayList.add(decode);
            }
        }
        List<AppVpnInfo> vpnApps = getVpnApps(applicationContext, true, this.mFeatureProvider, (AppOpsManager) applicationContext.getSystemService(AppOpsManager.class));
        LegacyVpnInfo legacyVpnInfo = this.mVpnManager.getLegacyVpnInfo(UserHandle.myUserId());
        this.mConnectedLegacyVpn = legacyVpnInfo;
        Map singletonMap = legacyVpnInfo != null ? Collections.singletonMap(legacyVpnInfo.key, legacyVpnInfo) : Collections.emptyMap();
        ArraySet arraySet = new ArraySet();
        for (UserHandle userHandle : this.mUserManager.getUserProfiles()) {
            if (!Utils.shouldHideUser(userHandle, this.mUserManager) && (vpnConfig = this.mVpnManager.getVpnConfig(userHandle.getIdentifier())) != null && !vpnConfig.legacy) {
                arraySet.add(new AppVpnInfo(userHandle.getIdentifier(), vpnConfig.user));
            }
        }
        ArraySet arraySet2 = new ArraySet();
        for (UserHandle userHandle2 : this.mUserManager.getUserProfiles()) {
            if (!Utils.shouldHideUser(userHandle2, this.mUserManager) && (alwaysOnVpnPackageForUser = this.mVpnManager.getAlwaysOnVpnPackageForUser((identifier = userHandle2.getIdentifier()))) != null) {
                arraySet2.add(new AppVpnInfo(identifier, alwaysOnVpnPackageForUser));
            }
        }
        String lockdownVpn = VpnUtils.getLockdownVpn();
        UpdatePreferences updatePreferences = new UpdatePreferences(this);
        updatePreferences.vpnProfiles = newArrayList;
        updatePreferences.connectedLegacyVpns = singletonMap;
        updatePreferences.lockdownVpnKey = lockdownVpn;
        updatePreferences.vpnApps = vpnApps;
        updatePreferences.connectedAppVpns = arraySet;
        updatePreferences.alwaysOnAppVpnInfos = arraySet2;
        activity.runOnUiThread(updatePreferences);
        synchronized (this) {
            try {
                Handler handler = this.mUpdater;
                if (handler != null) {
                    handler.removeMessages(0);
                    this.mUpdater.sendEmptyMessageDelayed(0, 1000L);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        return true;
    }

    public void init(PreferenceScreen preferenceScreen, AdvancedVpnFeatureProvider advancedVpnFeatureProvider) {
        this.mPreferenceScreen = preferenceScreen;
        this.mFeatureProvider = advancedVpnFeatureProvider;
    }

    @Override // com.android.settings.dashboard.RestrictedDashboardFragment, com.android.settings.SettingsPreferenceFragment, androidx.fragment.app.Fragment
    public final void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);
        this.mUserManager = (UserManager) getSystemService("user");
        this.mConnectivityManager = (ConnectivityManager) getSystemService("connectivity");
        this.mVpnManager = (VpnManager) getSystemService("vpn_management");
        FeatureFactoryImpl featureFactoryImpl = FeatureFactoryImpl._factory;
        if (featureFactoryImpl == null) {
            throw new UnsupportedOperationException("No feature factory configured");
        }
        AdvancedVpnFeatureProviderImpl advancedVpnFeatureProviderImpl = (AdvancedVpnFeatureProviderImpl) featureFactoryImpl.advancedVpnFeatureProvider$delegate.getValue();
        this.mFeatureProvider = advancedVpnFeatureProviderImpl;
        getContext();
        advancedVpnFeatureProviderImpl.getClass();
        boolean isUiRestricted = isUiRestricted();
        this.mUnavailable = isUiRestricted;
        setHasOptionsMenu(!isUiRestricted);
        this.mPreferenceScreen = getPreferenceScreen();
        this.mVpnPreferenceGroup = getPreferenceScreen();
        boolean z = SystemProperties.getBoolean("security.ucmcrypto", false);
        boolean z2 = SystemProperties.getBoolean("persist.security.ucmcrypto", false);
        if (z || z2) {
            UcmAliasLoader ucmAliasLoader = new UcmAliasLoader();
            this.mUcmAliasLoader = ucmAliasLoader;
            ucmAliasLoader.execute(new Void[0]);
        }
    }

    @Override // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment, androidx.fragment.app.Fragment
    public final void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
        super.onCreateOptionsMenu(menu, menuInflater);
        if (getContext().getResources().getBoolean(R.bool.config_show_vpn_options)) {
            if (!getContext().getPackageManager().hasSystemFeature("android.software.ipsec_tunnels")) {
                Log.wtf("VpnSettings", "FEATURE_IPSEC_TUNNELS missing from system, cannot create new VPNs");
                return;
            }
            menuInflater.inflate(R.menu.vpn, menu);
            MenuItem findItem = menu.findItem(R.id.menu_progress);
            this.mProgressMenuItem = findItem;
            findItem.setActionView(R.layout.sec_vpn_menu_progress_action_view);
            this.mProgressMenuItem.setShowAsAction(2);
            this.mVpnCreateMenuItem = menu.findItem(R.id.vpn_create);
            UcmAliasLoader ucmAliasLoader = this.mUcmAliasLoader;
            if (ucmAliasLoader == null || !ucmAliasLoader.isRunning) {
                this.mProgressMenuItem.setVisible(false);
                this.mVpnCreateMenuItem.setVisible(true);
            } else {
                this.mProgressMenuItem.setVisible(true);
                this.mVpnCreateMenuItem.setVisible(false);
            }
        }
    }

    @Override // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment, androidx.fragment.app.Fragment
    public final boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() != R.id.vpn_create) {
            return super.onOptionsItemSelected(menuItem);
        }
        if (this.mIsUserAddProfilesAllowed == 0) {
            return false;
        }
        long currentTimeMillis = System.currentTimeMillis();
        while (true) {
            if (!((ArrayMap) this.mLegacyVpnPreferences).containsKey(Long.toHexString(currentTimeMillis))) {
                ConfigDialogFragment.show(this, new VpnProfile(Long.toHexString(currentTimeMillis)), true, false, this.userCert, this.caCert);
                return true;
            }
            currentTimeMillis++;
        }
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment, com.android.settingslib.core.lifecycle.ObservablePreferenceFragment, androidx.fragment.app.Fragment
    public final void onPause() {
        if (this.mUnavailable) {
            super.onPause();
            return;
        }
        this.mConnectivityManager.unregisterNetworkCallback(this.mNetworkCallback);
        synchronized (this) {
            this.mUpdater.removeCallbacksAndMessages(null);
            this.mUpdater = null;
            this.mUpdaterThread.quit();
            this.mUpdaterThread = null;
        }
        super.onPause();
    }

    @Override // androidx.preference.Preference.OnPreferenceClickListener
    public final boolean onPreferenceClick(Preference preference) {
        PackageInfo packageInfo;
        if (preference instanceof LegacyVpnPreference) {
            VpnProfile vpnProfile = ((LegacyVpnPreference) preference).mProfile;
            LegacyVpnInfo legacyVpnInfo = this.mConnectedLegacyVpn;
            if (legacyVpnInfo != null && vpnProfile.key.equals(legacyVpnInfo.key)) {
                LegacyVpnInfo legacyVpnInfo2 = this.mConnectedLegacyVpn;
                if (legacyVpnInfo2.state == 3) {
                    try {
                        legacyVpnInfo2.intent.send();
                        return true;
                    } catch (Exception e) {
                        Log.w("VpnSettings", "Starting config intent failed", e);
                    }
                }
            }
            ConfigDialogFragment.show(this, vpnProfile, false, true, this.userCert, this.caCert);
            return true;
        }
        if (!(preference instanceof AppPreference)) {
            return false;
        }
        AppPreference appPreference = (AppPreference) preference;
        boolean z = appPreference.mState == 3;
        String str = appPreference.mPackageName;
        if (z) {
            AdvancedVpnFeatureProvider advancedVpnFeatureProvider = this.mFeatureProvider;
            getContext();
            advancedVpnFeatureProvider.getClass();
        } else {
            try {
                UserHandle of = UserHandle.of(appPreference.mUserId);
                Context createPackageContextAsUser = getContext().createPackageContextAsUser(getContext().getPackageName(), 0, of);
                Intent launchIntentForPackage = createPackageContextAsUser.getPackageManager().getLaunchIntentForPackage(str);
                if (launchIntentForPackage != null) {
                    createPackageContextAsUser.startActivityAsUser(launchIntentForPackage, of);
                    return true;
                }
            } catch (PackageManager.NameNotFoundException e2) {
                Log.w("VpnSettings", "VPN provider does not exist: " + appPreference.mPackageName, e2);
            }
        }
        try {
            packageInfo = appPreference.getContext().createPackageContextAsUser(appPreference.getContext().getPackageName(), 0, UserHandle.of(appPreference.mUserId)).getPackageManager().getPackageInfo(appPreference.mPackageName, 0);
        } catch (PackageManager.NameNotFoundException unused) {
            packageInfo = null;
        }
        PackageInfo packageInfo2 = packageInfo;
        String str2 = appPreference.mName;
        if (z) {
            AppDialogFragment.show(this, null, packageInfo2, str2, false, z);
        }
        return true;
    }

    @Override // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment, androidx.fragment.app.Fragment
    public final void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        for (int i = 0; i < menu.size(); i++) {
            if (isUiRestrictedByOnlyAdmin()) {
                RestrictedLockUtilsInternal.setMenuItemAsDisabledByAdmin(getPrefContext(), menu.getItem(i), getRestrictionEnforcedAdmin());
            } else {
                boolean z = !this.mUnavailable;
                MenuItem item = menu.getItem(i);
                if (this.mIsUserAddProfilesAllowed != -1 && item.getItemId() == R.id.vpn_create) {
                    z = z && this.mIsUserAddProfilesAllowed == 1;
                }
                item.setEnabled(z);
            }
        }
    }

    @Override // com.android.settings.dashboard.RestrictedDashboardFragment, com.android.settings.dashboard.DashboardFragment, com.android.settings.SettingsPreferenceFragment, com.android.settings.core.InstrumentedPreferenceFragment, com.android.settingslib.core.lifecycle.ObservablePreferenceFragment, androidx.fragment.app.Fragment
    public final void onResume() {
        super.onResume();
        if (!new LockPatternUtils(getContext()).isSecure(UserHandle.myUserId())) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle(R.string.vpn_secure_lock_dialog_title);
            builder.setMessage(R.string.vpn_secure_lock_dialog_message);
            builder.setPositiveButton(R.string.okay, new DialogInterface.OnClickListener() { // from class: com.android.settings.vpn2.VpnSettings.1
                @Override // android.content.DialogInterface.OnClickListener
                public final void onClick(DialogInterface dialogInterface, int i) {
                    SubSettingLauncher subSettingLauncher = new SubSettingLauncher(VpnSettings.this.getContext());
                    String name = ChooseLockGeneric.ChooseLockGenericFragment.class.getName();
                    SubSettingLauncher.LaunchRequest launchRequest = subSettingLauncher.mLaunchRequest;
                    launchRequest.mDestinationName = name;
                    VpnSettings.this.getClass();
                    launchRequest.mSourceMetricsCategory = 100;
                    subSettingLauncher.launch();
                }
            });
            builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() { // from class: com.android.settings.vpn2.VpnSettings.2
                @Override // android.content.DialogInterface.OnClickListener
                public final void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.cancel();
                    VpnSettings.this.finish();
                }
            });
            AlertDialog create = builder.create();
            create.setCanceledOnTouchOutside(false);
            create.setOnDismissListener(new DialogInterface.OnDismissListener() { // from class: com.android.settings.vpn2.VpnSettings.3
                @Override // android.content.DialogInterface.OnDismissListener
                public final void onDismiss(DialogInterface dialogInterface) {
                    VpnSettings.this.finish();
                }
            });
            create.show();
        }
        this.mIsUserAddProfilesAllowed = Utils.getEnterprisePolicyEnabled(getActivity(), "content://com.sec.knox.provider2/vpnPolicy", "isUserAddProfilesAllowed", new String[]{"false"});
        boolean hasUserRestriction = this.mUserManager.hasUserRestriction("no_config_vpn");
        this.mUnavailable = hasUserRestriction;
        if (hasUserRestriction) {
            if (!isUiRestrictedByOnlyAdmin()) {
                this.mEmptyTextView.setText(R.string.vpn_settings_not_available);
            }
            getPreferenceScreen().removeAll();
            return;
        }
        setEmptyView(this.mEmptyTextView);
        this.mEmptyTextView.setText(R.string.vpn_no_vpns_added);
        this.mConnectivityManager.registerNetworkCallback(VPN_REQUEST, this.mNetworkCallback);
        HandlerThread handlerThread = new HandlerThread("Refresh VPN list in background");
        this.mUpdaterThread = handlerThread;
        handlerThread.start();
        Handler handler = new Handler(this.mUpdaterThread.getLooper(), this);
        this.mUpdater = handler;
        handler.sendEmptyMessage(0);
        getActivity().invalidateOptionsMenu();
        if (Utils.getEnterprisePolicyEnabled(getActivity(), "content://com.sec.knox.provider/RestrictionPolicy4", "isVpnAllowed") == 0) {
            finish();
        }
    }

    public void setShownAdvancedPreferences(Collection<Preference> collection) {
        ((ArrayMap) this.mLegacyVpnPreferences).values().retainAll(collection);
        ((ArrayMap) this.mAppPreferences).values().retainAll(collection);
        PreferenceGroup preferenceGroup = (PreferenceGroup) this.mPreferenceScreen.findPreference("advanced_vpn_group");
        PreferenceGroup preferenceGroup2 = (PreferenceGroup) this.mPreferenceScreen.findPreference("vpn_group");
        AdvancedVpnFeatureProvider advancedVpnFeatureProvider = this.mFeatureProvider;
        getContext();
        advancedVpnFeatureProvider.getClass();
        preferenceGroup.setTitle((CharSequence) null);
        AdvancedVpnFeatureProvider advancedVpnFeatureProvider2 = this.mFeatureProvider;
        getContext();
        advancedVpnFeatureProvider2.getClass();
        preferenceGroup2.setTitle((CharSequence) null);
        updatePreferenceGroup(preferenceGroup, collection);
        updatePreferenceGroup(preferenceGroup2, collection);
        for (Preference preference : collection) {
            String packageName = preference instanceof LegacyVpnPreference ? ((LegacyVpnPreference) preference).getPackageName() : preference instanceof AppPreference ? ((AppPreference) preference).mPackageName : ApnSettings.MVNO_NONE;
            if (DEBUG) {
                DialogFragment$$ExternalSyntheticOutline0.m("setShownAdvancedPreferences() package name : ", packageName, "VpnSettings");
            }
            this.mFeatureProvider.getClass();
            if (TextUtils.equals(packageName, null)) {
                preferenceGroup.addPreference(preference);
            } else {
                preferenceGroup2.addPreference(preference);
            }
        }
        preferenceGroup.setVisible(preferenceGroup.getPreferenceCount() > 0);
        preferenceGroup2.setVisible(preferenceGroup2.getPreferenceCount() > 0);
    }

    public void setShownPreferences(Collection<Preference> collection) {
        ((ArrayMap) this.mLegacyVpnPreferences).values().retainAll(collection);
        ((ArrayMap) this.mAppPreferences).values().retainAll(collection);
        PreferenceScreen preferenceScreen = this.mPreferenceScreen;
        updatePreferenceGroup(preferenceScreen, collection);
        Iterator<Preference> it = collection.iterator();
        while (it.hasNext()) {
            preferenceScreen.addPreference(it.next());
        }
    }

    public AppPreference findOrCreatePreference(AppVpnInfo appVpnInfo) {
        AppPreference appPreference = (AppPreference) ((ArrayMap) this.mAppPreferences).get(appVpnInfo);
        if (appPreference == null) {
            appPreference = new AppPreference(getPrefContext(), appVpnInfo.userId, appVpnInfo.packageName);
            appPreference.mOnGearClickListener = this.mGearListener;
            appPreference.notifyChanged();
            appPreference.setOnPreferenceClickListener(this);
            ((ArrayMap) this.mAppPreferences).put(appVpnInfo, appPreference);
        }
        getContext();
        this.mFeatureProvider.getClass();
        return appPreference;
    }
}
