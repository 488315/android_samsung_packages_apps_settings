package com.android.settings;

import android.R;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Process;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.UserHandle;
import android.security.KeyChain;
import android.security.keystore2.AndroidKeyStoreLoadStoreParameter;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.mediarouter.app.MediaRouteControllerDialog$VolumeGroupAdapter$$ExternalSyntheticOutline0;
import androidx.recyclerview.widget.RecyclerView;

import com.android.settings.core.instrumentation.InstrumentedDialogFragment;
import com.android.settings.wifi.helper.SavedWifiHelper;
import com.android.settingslib.RestrictedLockUtils;
import com.android.settingslib.RestrictedLockUtilsInternal;
import com.android.settingslib.core.lifecycle.Lifecycle;
import com.android.wifitrackerlib.SavedNetworkTracker;
import com.android.wifitrackerlib.SavedNetworkTracker$$ExternalSyntheticLambda0;
import com.android.wifitrackerlib.SavedNetworkTracker$$ExternalSyntheticLambda1;

import com.samsung.android.knox.EnterpriseContainerCallback;
import com.samsung.android.knox.custom.IKnoxCustomManager;
import com.samsung.android.knox.keystore.CertificateProvisioning;
import com.samsung.android.knox.ucm.core.IUcmService;
import com.samsung.android.knox.ucm.core.UniversalCredentialUtil;
import com.samsung.android.knox.ucm.core.ucmRetParcelable;
import com.samsung.android.knox.ucm.plugin.agent.UcmAgentService;
import com.samsung.android.knox.zt.devicetrust.cert.CertProvisionProfile;

import java.io.ByteArrayInputStream;
import java.security.Key;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import javax.crypto.SecretKey;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes.dex */
public class UserCredentialsSettings extends SettingsPreferenceFragment
        implements View.OnClickListener {
    public static final SparseArray credentialViewTypes;
    protected SavedWifiHelper mSavedWifiHelper;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class CredentialAdapter extends RecyclerView.Adapter {
        public final List mItems;
        public final View.OnClickListener mListener;

        public CredentialAdapter(List list, View.OnClickListener onClickListener) {
            this.mItems = list;
            this.mListener = onClickListener;
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public final int getItemCount() {
            return this.mItems.size();
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public final void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
            ViewHolder viewHolder2 = (ViewHolder) viewHolder;
            UserCredentialsSettings.getCredentialView(
                    (Credential) this.mItems.get(i),
                    R.layout.user_credential_preference,
                    viewHolder2.itemView,
                    null,
                    false);
            viewHolder2.itemView.setTag(this.mItems.get(i));
            if (i == this.mItems.size() - 1) {
                viewHolder2.itemView.findViewById(R.id.divider).setVisibility(8);
            }
            viewHolder2.itemView.setOnClickListener(this.mListener);
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public final RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            return new ViewHolder(
                    LayoutInflater.from(viewGroup.getContext())
                            .inflate(R.layout.user_credential_preference, viewGroup, false));
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public static class CredentialDialogFragment extends InstrumentedDialogFragment
            implements DialogInterface.OnShowListener {

        /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
        public final class RemoveCredentialsTask extends AsyncTask {
            public final Fragment targetFragment;

            public RemoveCredentialsTask(Fragment fragment) {
                this.targetFragment = fragment;
            }

            @Override // android.os.AsyncTask
            public final Object doInBackground(Object[] objArr) {
                Credential[] credentialArr = (Credential[]) objArr;
                for (Credential credential : credentialArr) {
                    if (credential.isSystem()) {
                        try {
                            KeyChain.KeyChainConnection bind =
                                    KeyChain.bind(CredentialDialogFragment.this.getContext());
                            try {
                                try {
                                    bind.getService().removeKeyPair(credential.alias);
                                } catch (RemoteException e) {
                                    Log.w("CredentialDialogFragment", "Removing credentials", e);
                                }
                            } finally {
                                bind.close();
                            }
                        } catch (InterruptedException e2) {
                            Log.w("CredentialDialogFragment", "Connecting to KeyChain", e2);
                        } catch (NullPointerException e3) {
                            Log.w("CredentialDialogFragment", "context == null", e3);
                        }
                    } else {
                        try {
                            KeyStore keyStore =
                                    KeyStore.getInstance(CertProvisionProfile.PROVIDER_ANDROID);
                            keyStore.load(new AndroidKeyStoreLoadStoreParameter(102));
                            keyStore.deleteEntry(credential.alias);
                        } catch (Exception unused) {
                            throw new RuntimeException("Failed to delete keys from keystore.");
                        }
                    }
                }
                return credentialArr;
            }

            @Override // android.os.AsyncTask
            public final void onPostExecute(Object obj) {
                Credential[] credentialArr = (Credential[]) obj;
                Fragment fragment = this.targetFragment;
                if ((fragment instanceof UserCredentialsSettings) && fragment.isAdded()) {
                    UserCredentialsSettings userCredentialsSettings =
                            (UserCredentialsSettings) this.targetFragment;
                    for (Credential credential : credentialArr) {
                        String str = credential.alias;
                        if (userCredentialsSettings.isAdded()) {
                            userCredentialsSettings
                                    .getListView()
                                    .announceForAccessibility(
                                            userCredentialsSettings.getString(
                                                    R.string.user_credential_removed, str));
                        }
                    }
                    if (userCredentialsSettings.isAdded()) {
                        userCredentialsSettings.new AliasLoader().execute(new Void[0]);
                    }
                }
            }
        }

        @Override // com.android.settingslib.core.instrumentation.Instrumentable
        public final int getMetricsCategory() {
            return 533;
        }

        @Override // androidx.fragment.app.DialogFragment
        public final Dialog onCreateDialog(Bundle bundle) {
            final Credential credential = (Credential) getArguments().getParcelable("credential");
            View inflate =
                    getActivity()
                            .getLayoutInflater()
                            .inflate(R.layout.user_credential_dialog, (ViewGroup) null);
            ViewGroup viewGroup = (ViewGroup) inflate.findViewById(R.id.credential_container);
            View credentialView =
                    UserCredentialsSettings.getCredentialView(
                            credential, R.layout.user_credential, null, viewGroup, true);
            TextView textView = (TextView) credentialView.findViewById(R.id.contents_userkey);
            TextView textView2 = (TextView) credentialView.findViewById(R.id.contents_usercrt);
            TextView textView3 = (TextView) credentialView.findViewById(R.id.contents_cacrt);
            textView.setText("• " + textView.getText().toString());
            textView2.setText("• " + textView2.getText().toString());
            textView3.setText("• " + textView3.getText().toString());
            viewGroup.addView(credentialView);
            AlertDialog.Builder builder =
                    new AlertDialog.Builder(
                            getActivity(), R.style.sec_certificate_details_dialogtheme);
            builder.setView(inflate);
            builder.setTitle(R.string.user_credential_title);
            builder.setNegativeButton(R.string.cancel, (DialogInterface.OnClickListener) null);
            final int myUserId = UserHandle.myUserId();
            if (!RestrictedLockUtilsInternal.hasBaseUserRestriction(
                    getContext(), myUserId, "no_config_credentials")) {
                DialogInterface.OnClickListener onClickListener =
                        new DialogInterface
                                .OnClickListener() { // from class:
                                                     // com.android.settings.UserCredentialsSettings.CredentialDialogFragment.1
                            @Override // android.content.DialogInterface.OnClickListener
                            public final void onClick(DialogInterface dialogInterface, int i) {
                                RestrictedLockUtils.EnforcedAdmin checkIfRestrictionEnforced =
                                        RestrictedLockUtilsInternal.checkIfRestrictionEnforced(
                                                CredentialDialogFragment.this.getContext(),
                                                myUserId,
                                                "no_config_credentials");
                                if (checkIfRestrictionEnforced != null) {
                                    RestrictedLockUtils.sendShowAdminSupportDetailsIntent(
                                            CredentialDialogFragment.this.getContext(),
                                            checkIfRestrictionEnforced);
                                } else {
                                    CredentialDialogFragment credentialDialogFragment =
                                            CredentialDialogFragment.this;
                                    credentialDialogFragment.getContext();
                                    credentialDialogFragment
                                            .new RemoveCredentialsTask(
                                                    CredentialDialogFragment.this
                                                            .getTargetFragment())
                                            .execute(credential);
                                }
                                dialogInterface.dismiss();
                            }
                        };
                if (credential.storageType != 2) {
                    builder.setPositiveButton(R.string.sec_user_credential_remove, onClickListener);
                }
            }
            AlertDialog create = builder.create();
            create.setOnShowListener(this);
            return create;
        }

        @Override // android.content.DialogInterface.OnShowListener
        public final void onShow(DialogInterface dialogInterface) {
            if (((Credential) getArguments().getParcelable("credential")).mIsInUse) {
                ((AlertDialog) this.mDialog).getButton(-2).setEnabled(false);
            }
            Button button = ((AlertDialog) this.mDialog).getButton(-1);
            if (button != null) {
                button.setEnabled(
                        Utils.isUserRemoveCertificateAllowedAsUser(
                                getContext(), UserHandle.myUserId()));
            }
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class ViewHolder extends RecyclerView.ViewHolder {}

    static {
        SparseArray sparseArray = new SparseArray();
        credentialViewTypes = sparseArray;
        sparseArray.put(R.id.contents_userkey, Credential.Type.USER_KEY);
        sparseArray.put(R.id.contents_usercrt, Credential.Type.USER_CERTIFICATE);
        sparseArray.put(R.id.contents_cacrt, Credential.Type.CA_CERTIFICATE);
    }

    public static View getCredentialView(
            Credential credential, int i, View view, ViewGroup viewGroup, boolean z) {
        if (view == null) {
            view =
                    MediaRouteControllerDialog$VolumeGroupAdapter$$ExternalSyntheticOutline0.m(
                            viewGroup, i, viewGroup, false);
        }
        ((TextView) view.findViewById(R.id.alias)).setText(credential.alias);
        updatePurposeView((TextView) view.findViewById(R.id.purpose), credential);
        boolean z2 = credential.storageType == 2;
        int i2 = R.string.credential_for_wifi;
        if (z2) {
            TextView textView = (TextView) view.findViewById(R.id.purpose);
            if (!credential.isWifiForKnox) {
                i2 = R.string.credential_for_vpn_and_apps;
            }
            textView.setText(i2);
        } else {
            TextView textView2 = (TextView) view.findViewById(R.id.purpose);
            if (credential.isSystem()) {
                i2 = R.string.credential_for_vpn_and_apps;
            }
            textView2.setText(i2);
        }
        view.findViewById(R.id.contents).setVisibility(z ? 0 : 8);
        if (z) {
            updateUsedByViews(
                    (TextView) view.findViewById(R.id.credential_being_used_by_title),
                    (TextView) view.findViewById(R.id.credential_being_used_by_content),
                    credential);
            int i3 = 0;
            while (true) {
                SparseArray sparseArray = credentialViewTypes;
                if (i3 >= sparseArray.size()) {
                    break;
                }
                view.findViewById(sparseArray.keyAt(i3))
                        .setVisibility(
                                credential.storedTypes.contains(sparseArray.valueAt(i3)) ? 0 : 8);
                i3++;
            }
        }
        return view;
    }

    public static void updatePurposeView(TextView textView, Credential credential) {
        textView.setText(
                !credential.isSystem()
                        ? credential.mIsInUse
                                ? R.string.credential_for_wifi_in_use
                                : R.string.credential_for_wifi
                        : R.string.credential_for_vpn_and_apps);
    }

    public static void updateUsedByViews(
            TextView textView, TextView textView2, Credential credential) {
        credential.getClass();
        ArrayList arrayList = new ArrayList(credential.mUsedByNames);
        if (arrayList.size() <= 0) {
            textView.setVisibility(8);
            textView2.setVisibility(8);
        } else {
            textView.setVisibility(0);
            textView2.setText(String.join("\n", arrayList));
            textView2.setVisibility(0);
        }
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return IKnoxCustomManager.Stub.TRANSACTION_startProKioskMode;
    }

    @Override // android.view.View.OnClickListener
    public final void onClick(View view) {
        Credential credential = (Credential) view.getTag();
        if (credential == null) {
            return;
        }
        if (credential.mIsInUse) {
            SavedWifiHelper savedWifiHelper = this.mSavedWifiHelper;
            String str = credential.alias;
            SavedNetworkTracker savedNetworkTracker = savedWifiHelper.mSavedNetworkTracker;
            List list =
                    (List)
                            savedNetworkTracker.mWifiManager.getNetworkSuggestions().stream()
                                    .map(new SavedNetworkTracker$$ExternalSyntheticLambda0(0))
                                    .collect(Collectors.toList());
            list.addAll(savedNetworkTracker.mWifiManager.getConfiguredNetworks());
            credential.mUsedByNames =
                    new ArrayList(
                            (List)
                                    ((Set)
                                                    list.stream()
                                                            .filter(
                                                                    new SavedNetworkTracker$$ExternalSyntheticLambda1(
                                                                            str, 0))
                                                            .map(
                                                                    new SavedNetworkTracker$$ExternalSyntheticLambda0(
                                                                            2))
                                                            .collect(Collectors.toSet()))
                                            .stream().collect(Collectors.toList()));
        }
        showCredentialDialogFragment(credential);
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        SavedWifiHelper savedWifiHelper;
        super.onCreate(bundle);
        getActivity().setTitle(R.string.user_credentials);
        Context context = getContext();
        Lifecycle settingsLifecycle = getSettingsLifecycle();
        synchronized (SavedWifiHelper.sInstanceLock) {
            try {
                Map map = SavedWifiHelper.sTestInstances;
                if (map == null || !((ConcurrentHashMap) map).containsKey(context)) {
                    savedWifiHelper = new SavedWifiHelper(context, settingsLifecycle, null);
                } else {
                    savedWifiHelper =
                            (SavedWifiHelper)
                                    ((ConcurrentHashMap) SavedWifiHelper.sTestInstances)
                                            .get(context);
                    Log.w(
                            "SavedWifiHelper",
                            "The context owner use a test instance:" + savedWifiHelper);
                }
            } finally {
            }
        }
        this.mSavedWifiHelper = savedWifiHelper;
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onResume() {
        super.onResume();
        if (isAdded()) {
            new AliasLoader().execute(new Void[0]);
        }
    }

    public void showCredentialDialogFragment(Credential credential) {
        Bundle bundle = new Bundle();
        bundle.putParcelable("credential", credential);
        if (getFragmentManager().findFragmentByTag("CredentialDialogFragment") == null) {
            CredentialDialogFragment credentialDialogFragment = new CredentialDialogFragment();
            credentialDialogFragment.setTargetFragment(this, -1);
            credentialDialogFragment.setArguments(bundle);
            credentialDialogFragment.show(getFragmentManager(), "CredentialDialogFragment");
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class Credential implements Parcelable {
        public static final Parcelable.Creator<Credential> CREATOR = new AnonymousClass1();
        public final String alias;
        public boolean isWifiForKnox;
        public boolean mIsInUse;
        public List mUsedByNames;
        public final int storageType;
        public final EnumSet storedTypes;
        public final int uid;

        /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
        /* renamed from: com.android.settings.UserCredentialsSettings$Credential$1, reason: invalid class name */
        public final class AnonymousClass1 implements Parcelable.Creator {
            @Override // android.os.Parcelable.Creator
            public final Object createFromParcel(Parcel parcel) {
                Credential credential = new Credential(parcel.readString(), parcel.readInt());
                long readLong = parcel.readLong();
                for (Type type : Type.values()) {
                    if (((1 << type.ordinal()) & readLong) != 0) {
                        credential.storedTypes.add(type);
                    }
                }
                return credential;
            }

            @Override // android.os.Parcelable.Creator
            public final Object[] newArray(int i) {
                return new Credential[i];
            }
        }

        /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
        enum Type {
            CA_CERTIFICATE(CertificateProvisioning.CA_CERTIFICATE),
            USER_CERTIFICATE(CertificateProvisioning.USER_CERTIFICATE),
            USER_KEY("USRPKEY_", "USRSKEY_");

            final String[] prefix;

            Type(String... strArr) {
                this.prefix = strArr;
            }
        }

        public Credential(String str, int i) {
            this.mUsedByNames = new ArrayList();
            this.storedTypes = EnumSet.noneOf(Type.class);
            this.storageType = 0;
            this.isWifiForKnox = false;
            this.alias = str;
            this.uid = i;
        }

        @Override // android.os.Parcelable
        public final int describeContents() {
            return 0;
        }

        public final boolean isSystem() {
            return !(this.storageType == 2) && UserHandle.getAppId(this.uid) == 1000;
        }

        @Override // android.os.Parcelable
        public final void writeToParcel(Parcel parcel, int i) {
            parcel.writeString(this.alias);
            parcel.writeInt(this.uid);
            Iterator it = this.storedTypes.iterator();
            long j = 0;
            while (it.hasNext()) {
                j |= 1 << ((Type) it.next()).ordinal();
            }
            parcel.writeLong(j);
        }

        public Credential(String str) {
            this.mUsedByNames = new ArrayList();
            this.storedTypes = EnumSet.noneOf(Type.class);
            this.isWifiForKnox = false;
            this.alias = str;
            this.uid = 0;
            this.storageType = 2;
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class AliasLoader extends AsyncTask {
        public AliasLoader() {}

        public static X509Certificate getUCMCertificateForAlias(String str) {
            ucmRetParcelable certificateChain;
            byte[] bArr;
            if (str != null && str.length() > 0) {
                try {
                    IUcmService asInterface =
                            IUcmService.Stub.asInterface(
                                    ServiceManager.getService("com.samsung.ucs.ucsservice"));
                    if (asInterface == null
                            || (certificateChain = asInterface.getCertificateChain(str)) == null
                            || (bArr = certificateChain.mData) == null) {
                        return null;
                    }
                    try {
                        return (X509Certificate)
                                CertificateFactory.getInstance("X.509")
                                        .generateCertificate(new ByteArrayInputStream(bArr));
                    } catch (CertificateException e) {
                        Log.w("UserCredentialsSettings", "Failed to toCertificate", e);
                        return null;
                    }
                } catch (Exception e2) {
                    Log.w("UserCredentialsSettings", "Failed to getUCMCertificate", e2);
                }
            }
            return null;
        }

        public static SortedMap getUCMCredentials() {
            UniversalCredentialUtil universalCredentialUtil;
            X509Certificate uCMCertificateForAlias;
            UniversalCredentialUtil universalCredentialUtil2;
            X509Certificate uCMCertificateForAlias2;
            TreeMap treeMap = new TreeMap();
            String[] uCMCredentials = getUCMCredentials(1);
            Credential.Type type = Credential.Type.USER_CERTIFICATE;
            Credential.Type type2 = Credential.Type.CA_CERTIFICATE;
            Credential.Type type3 = Credential.Type.USER_KEY;
            if (uCMCredentials != null) {
                int length = uCMCredentials.length;
                for (int i = 0; i < length; i++) {
                    String str = uCMCredentials[i];
                    String lastPathSegment = Uri.parse(str).getLastPathSegment();
                    String friendlyName =
                            (str == null
                                            || (universalCredentialUtil2 =
                                                            UniversalCredentialUtil.getInstance())
                                                    == null)
                                    ? null
                                    : universalCredentialUtil2.getFriendlyName(
                                            UniversalCredentialUtil.getSource(str));
                    if (((Credential) treeMap.get(lastPathSegment + " [" + friendlyName + "]"))
                                    == null
                            && (uCMCertificateForAlias2 = getUCMCertificateForAlias(str)) != null) {
                        Credential credential =
                                new Credential(lastPathSegment + " [" + friendlyName + "]");
                        treeMap.put(lastPathSegment + " [" + friendlyName + "]", credential);
                        credential.storedTypes.add(type3);
                        if (uCMCertificateForAlias2.getBasicConstraints() > 0) {
                            credential.storedTypes.add(type2);
                        } else {
                            credential.storedTypes.add(type);
                        }
                    }
                }
            }
            String[] uCMCredentials2 = getUCMCredentials(3);
            if (uCMCredentials2 != null) {
                int length2 = uCMCredentials2.length;
                for (int i2 = 0; i2 < length2; i2++) {
                    String str2 = uCMCredentials2[i2];
                    String lastPathSegment2 = Uri.parse(str2).getLastPathSegment();
                    String friendlyName2 =
                            (str2 == null
                                            || (universalCredentialUtil =
                                                            UniversalCredentialUtil.getInstance())
                                                    == null)
                                    ? null
                                    : universalCredentialUtil.getFriendlyName(
                                            UniversalCredentialUtil.getSource(str2));
                    if (((Credential) treeMap.get(lastPathSegment2 + " [" + friendlyName2 + "]"))
                                    == null
                            && (uCMCertificateForAlias = getUCMCertificateForAlias(str2)) != null) {
                        Credential credential2 =
                                new Credential(lastPathSegment2 + " [" + friendlyName2 + "]");
                        treeMap.put(lastPathSegment2 + " [" + friendlyName2 + "]", credential2);
                        credential2.isWifiForKnox = true;
                        credential2.storedTypes.add(type3);
                        if (uCMCertificateForAlias.getBasicConstraints() > 0) {
                            credential2.storedTypes.add(type2);
                        } else {
                            credential2.storedTypes.add(type);
                        }
                    }
                }
            }
            return treeMap;
        }

        @Override // android.os.AsyncTask
        public final Object doInBackground(Object[] objArr) {
            int myUserId = UserHandle.myUserId();
            int uid = UserHandle.getUid(myUserId, 1000);
            int uid2 =
                    UserHandle.getUid(myUserId, EnterpriseContainerCallback.CONTAINER_MOUNT_STATUS);
            try {
                KeyStore keyStore = KeyStore.getInstance(CertProvisionProfile.PROVIDER_ANDROID);
                KeyStore keyStore2 = null;
                keyStore.load(null);
                if (myUserId == 0) {
                    keyStore2 = KeyStore.getInstance(CertProvisionProfile.PROVIDER_ANDROID);
                    keyStore2.load(new AndroidKeyStoreLoadStoreParameter(102));
                }
                ArrayList arrayList = new ArrayList();
                arrayList.addAll(((TreeMap) getCredentialsForUid(keyStore, uid)).values());
                arrayList.addAll(((TreeMap) getUCMCredentials()).values());
                if (keyStore2 != null) {
                    arrayList.addAll(((TreeMap) getCredentialsForUid(keyStore2, uid2)).values());
                }
                return arrayList;
            } catch (Exception e) {
                throw new RuntimeException("Failed to load credentials from Keystore.", e);
            }
        }

        public final SortedMap getCredentialsForUid(KeyStore keyStore, int i) {
            Key key;
            Credential.Type type;
            Credential.Type type2;
            try {
                TreeMap treeMap = new TreeMap();
                Enumeration<String> aliases = keyStore.aliases();
                while (aliases.hasMoreElements()) {
                    String nextElement = aliases.nextElement();
                    Credential credential = new Credential(nextElement, i);
                    if (!credential.isSystem()) {
                        credential.mIsInUse =
                                UserCredentialsSettings.this.mSavedWifiHelper.isCertificateInUse(
                                        nextElement);
                    }
                    try {
                        key = keyStore.getKey(nextElement, null);
                        type = Credential.Type.CA_CERTIFICATE;
                        type2 = Credential.Type.USER_CERTIFICATE;
                    } catch (NoSuchAlgorithmException | UnrecoverableKeyException e) {
                        Log.e(
                                "UserCredentialsSettings",
                                "Error tying to retrieve key: " + nextElement,
                                e);
                    }
                    if (key != null) {
                        if (!(key instanceof SecretKey)) {
                            credential.storedTypes.add(Credential.Type.USER_KEY);
                            Certificate[] certificateChain =
                                    keyStore.getCertificateChain(nextElement);
                            if (certificateChain != null) {
                                credential.storedTypes.add(type2);
                                if (certificateChain.length > 1) {
                                    credential.storedTypes.add(type);
                                }
                            }
                        }
                    } else if (keyStore.isCertificateEntry(nextElement)) {
                        credential.storedTypes.add(type);
                    } else {
                        credential.storedTypes.add(type2);
                    }
                    treeMap.put(nextElement, credential);
                }
                return treeMap;
            } catch (KeyStoreException e2) {
                throw new RuntimeException("Failed to load credential from Android Keystore.", e2);
            }
        }

        @Override // android.os.AsyncTask
        public final void onPostExecute(Object obj) {
            List list = (List) obj;
            if (UserCredentialsSettings.this.isAdded()) {
                if (list == null || list.size() == 0) {
                    TextView textView =
                            (TextView)
                                    UserCredentialsSettings.this.getView().findViewById(R.id.empty);
                    textView.setText(R.string.user_credential_none_installed);
                    textView.setTextAppearance(R.style.sec_credential_no_item_text_style);
                    UserCredentialsSettings.this.setEmptyView(textView);
                } else {
                    UserCredentialsSettings.this.setEmptyView(null);
                }
                if (UserCredentialsSettings.this.getContext() == null) {
                    Log.w("UserCredentialsSettings", "Context is null.");
                    return;
                }
                UserCredentialsSettings.this
                        .getListView()
                        .setAdapter(new CredentialAdapter(list, UserCredentialsSettings.this));
                UserCredentialsSettings.this.getListView().semSetRoundedCorners(12);
                UserCredentialsSettings.this
                        .getListView()
                        .semSetRoundedCornerColor(
                                12,
                                UserCredentialsSettings.this
                                        .getResources()
                                        .getColor(R.color.sec_widget_round_and_bgcolor));
            }
        }

        public static String[] getUCMCredentials(int i) {
            Bundle saw;
            String build =
                    new UniversalCredentialUtil.UcmUriBuilder("KNOX")
                            .setResourceId(i)
                            .setUid(Process.myUid())
                            .build();
            UniversalCredentialUtil universalCredentialUtil = UniversalCredentialUtil.getInstance();
            if (universalCredentialUtil == null
                    || (saw = universalCredentialUtil.saw(build, 1)) == null) {
                return null;
            }
            return saw.getStringArray(UcmAgentService.PLUGIN_STRINGARRAY_RESPONSE);
        }
    }
}
