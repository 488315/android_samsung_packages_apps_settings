package com.android.settings;

import android.R;
import android.app.KeyguardManager;
import android.app.admin.DevicePolicyManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.Signature;
import android.content.pm.UserInfo;
import android.content.res.TypedArray;
import android.database.DataSetObserver;
import android.graphics.drawable.Drawable;
import android.net.http.SslCertificate;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.os.RemoteException;
import android.os.UserHandle;
import android.os.UserManager;
import android.provider.Settings;
import android.security.IKeyChainService;
import android.security.KeyChain;
import android.util.ArraySet;
import android.util.Log;
import android.util.SparseArray;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.CompoundButton;
import android.widget.ExpandableListView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.fragment.app.FragmentActivity;
import com.android.internal.app.UnlaunchableAppActivity;
import com.android.internal.hidden_from_bootclasspath.android.os.Flags;
import com.android.internal.widget.LockPatternUtils;
import com.android.settings.TrustedCredentialsDialogBuilder;
import com.android.settings.TrustedCredentialsFragment;
import com.android.settings.TrustedCredentialsFragment.AliasOperation;
import com.android.settings.TrustedCredentialsSettings;
import com.android.settingslib.core.lifecycle.ObservableFragment;
import com.samsung.android.knox.SemPersonaManager;
import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.settings.display.SecDisplayUtils;
import com.samsung.android.settings.logging.LoggingHelper;
import java.security.cert.CertificateEncodingException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.function.IntConsumer;
import java.util.function.Supplier;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes.dex */
public class TrustedCredentialsFragment extends ObservableFragment implements TrustedCredentialsDialogBuilder.DelegateInterface {
    public static int SETTINTS_VIEW_CERTIFICATES_LIST_SWITCH;
    public static int SETTINTS_VIEW_CERTIFICATES_LIST_SWITCH_TURN_OFF;
    public static int SETTINTS_VIEW_CERTIFICATES_LIST_SWITCH_TURN_ON;
    public AliasOperation mAliasOperation;
    public ArraySet mConfirmedCredentialUsers;
    public IntConsumer mConfirmingCredentialListener;
    public int mConfirmingCredentialUser;
    public DevicePolicyManager mDevicePolicyManager;
    public ViewGroup mFragmentView;
    public GroupAdapter mGroupAdapter;
    public KeyguardManager mKeyguardManager;
    public int mTrustAllCaUserId;
    public UserManager mUserManager;
    public CertHolder mSelectedCertHolder = null;
    public String mDescription = null;
    public final Set mAliasLoaders = new ArraySet(2);
    public final SparseArray mKeyChainConnectionByProfileId = new SparseArray();
    public final AnonymousClass1 mProfileChangedReceiver = new BroadcastReceiver() { // from class: com.android.settings.TrustedCredentialsFragment.1
        @Override // android.content.BroadcastReceiver
        public final void onReceive(Context context, Intent intent) {
            TrustedCredentialsFragment trustedCredentialsFragment = TrustedCredentialsFragment.this;
            trustedCredentialsFragment.getClass();
            String action = intent.getAction();
            if (Flags.allowPrivateProfile() && android.multiuser.Flags.enablePrivateSpaceFeatures() && android.multiuser.Flags.handleInterleavedSettingsForPrivateSpace()) {
                UserHandle userHandle = (UserHandle) intent.getParcelableExtra("android.intent.extra.USER", UserHandle.class);
                if (userHandle == null) {
                    Log.w("TrustedCredentialsFragment", "received action " + action + " with missing user extra");
                    return;
                }
                UserInfo userInfo = trustedCredentialsFragment.mUserManager.getUserInfo(userHandle.getIdentifier());
                if (!"android.intent.action.PROFILE_AVAILABLE".equals(action) && !"android.intent.action.PROFILE_UNAVAILABLE".equals(action) && !"android.intent.action.PROFILE_ACCESSIBLE".equals(action)) {
                    return;
                }
                if (!userInfo.isManagedProfile() && !userInfo.isPrivateProfile()) {
                    return;
                }
            } else if (!"android.intent.action.MANAGED_PROFILE_AVAILABLE".equals(action) && !"android.intent.action.MANAGED_PROFILE_UNAVAILABLE".equals(action) && !"android.intent.action.MANAGED_PROFILE_UNLOCKED".equals(action)) {
                return;
            }
            TrustedCredentialsFragment.this.mGroupAdapter.load();
        }
    };

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class AdapterData {
        public final GroupAdapter mAdapter;
        public final SparseArray mCertHoldersByUserId = new SparseArray();
        public final TrustedCredentialsSettings.Tab mTab;

        /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
        public final class AliasLoader extends AsyncTask {
            public final FragmentActivity mContext;
            public View mLinearLayoutContainer;
            public ProgressBar mProgressBar;
            public TextView mTextView;

            public AliasLoader() {
                this.mContext = TrustedCredentialsFragment.this.getActivity();
                TrustedCredentialsFragment trustedCredentialsFragment = TrustedCredentialsFragment.this;
                ((ArraySet) trustedCredentialsFragment.mAliasLoaders).add(this);
                Iterator<UserHandle> it = trustedCredentialsFragment.mUserManager.getUserProfiles().iterator();
                while (it.hasNext()) {
                    AdapterData.this.mCertHoldersByUserId.put(it.next().getIdentifier(), new ArrayList());
                }
            }

            @Override // android.os.AsyncTask
            public final Object doInBackground(Object[] objArr) {
                List list;
                SparseArray sparseArray = new SparseArray();
                try {
                    synchronized (TrustedCredentialsFragment.this.mKeyChainConnectionByProfileId) {
                        try {
                            List<UserHandle> userProfiles = TrustedCredentialsFragment.this.mUserManager.getUserProfiles();
                            SparseArray sparseArray2 = new SparseArray(userProfiles.size());
                            Iterator<UserHandle> it = userProfiles.iterator();
                            int i = 0;
                            while (true) {
                                boolean z = true;
                                if (!it.hasNext()) {
                                    int i2 = 0;
                                    for (UserHandle userHandle : userProfiles) {
                                        int identifier = userHandle.getIdentifier();
                                        List<String> list2 = (List) sparseArray2.get(identifier);
                                        if (isCancelled()) {
                                            return new SparseArray();
                                        }
                                        KeyChain.KeyChainConnection keyChainConnection = (KeyChain.KeyChainConnection) TrustedCredentialsFragment.this.mKeyChainConnectionByProfileId.get(identifier);
                                        if (shouldSkipProfile(userHandle) || list2 == null || keyChainConnection == null) {
                                            sparseArray.put(identifier, new ArrayList(0));
                                        } else {
                                            IKeyChainService service = keyChainConnection.getService();
                                            ArrayList arrayList = new ArrayList(i);
                                            for (String str : list2) {
                                                X509Certificate certificate = KeyChain.toCertificate(service.getEncodedCaCertificate(str, z));
                                                AdapterData adapterData = AdapterData.this;
                                                ArrayList arrayList2 = arrayList;
                                                int i3 = identifier;
                                                arrayList2.add(new CertHolder(service, adapterData.mAdapter, adapterData.mTab, str, certificate, i3));
                                                i2++;
                                                publishProgress(Integer.valueOf(i2), Integer.valueOf(i));
                                                arrayList = arrayList2;
                                                identifier = i3;
                                                z = true;
                                            }
                                            ArrayList arrayList3 = arrayList;
                                            Collections.sort(arrayList3);
                                            sparseArray.put(identifier, arrayList3);
                                        }
                                        z = true;
                                    }
                                    return sparseArray;
                                }
                                UserHandle next = it.next();
                                int identifier2 = next.getIdentifier();
                                if (!shouldSkipProfile(next)) {
                                    KeyChain.KeyChainConnection bindAsUser = KeyChain.bindAsUser(this.mContext, next);
                                    TrustedCredentialsFragment.this.mKeyChainConnectionByProfileId.put(identifier2, bindAsUser);
                                    IKeyChainService service2 = bindAsUser.getService();
                                    int ordinal = AdapterData.this.mTab.ordinal();
                                    if (ordinal == 0) {
                                        list = service2.getSystemCaAliases().getList();
                                    } else {
                                        if (ordinal != 1) {
                                            throw new AssertionError();
                                        }
                                        list = service2.getUserCaAliases().getList();
                                    }
                                    if (isCancelled()) {
                                        return new SparseArray();
                                    }
                                    i += list.size();
                                    sparseArray2.put(identifier2, list);
                                }
                            }
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                } catch (RemoteException e) {
                    Log.e("TrustedCredentialsFragment", "Remote exception while loading aliases.", e);
                    return new SparseArray();
                } catch (InterruptedException e2) {
                    Log.e("TrustedCredentialsFragment", "InterruptedException while loading aliases.", e2);
                    return new SparseArray();
                }
            }

            @Override // android.os.AsyncTask
            public final void onPostExecute(Object obj) {
                List<CertHolder> list;
                SparseArray sparseArray = (SparseArray) obj;
                AdapterData.this.mCertHoldersByUserId.clear();
                int size = sparseArray.size();
                for (int i = 0; i < size; i++) {
                    AdapterData.this.mCertHoldersByUserId.put(sparseArray.keyAt(i), (List) sparseArray.valueAt(i));
                }
                AdapterData.this.mAdapter.notifyDataSetChanged();
                this.mProgressBar.setVisibility(8);
                this.mLinearLayoutContainer.setVisibility(0);
                if (AdapterData.this.mCertHoldersByUserId.size() == 1 && ((List) AdapterData.this.mCertHoldersByUserId.valueAt(0)).size() == 0) {
                    this.mTextView.setVisibility(0);
                } else {
                    this.mTextView.setVisibility(8);
                }
                this.mProgressBar.setProgress(0);
                ((ArraySet) TrustedCredentialsFragment.this.mAliasLoaders).remove(this);
                AdapterData adapterData = AdapterData.this;
                int i2 = TrustedCredentialsFragment.this.mTrustAllCaUserId;
                if (i2 != -10000) {
                    if (adapterData.mTab != TrustedCredentialsSettings.Tab.USER || (list = (List) adapterData.mCertHoldersByUserId.get(i2)) == null) {
                        return;
                    }
                    ArrayList arrayList = new ArrayList();
                    DevicePolicyManager devicePolicyManager = (DevicePolicyManager) this.mContext.getSystemService(DevicePolicyManager.class);
                    for (CertHolder certHolder : list) {
                        if (certHolder != null && !devicePolicyManager.isCaCertApproved(certHolder.mAlias, TrustedCredentialsFragment.this.mTrustAllCaUserId)) {
                            arrayList.add(certHolder);
                        }
                    }
                    if (arrayList.size() == 0) {
                        Log.w("TrustedCredentialsFragment", "no cert is pending approval for user " + TrustedCredentialsFragment.this.mTrustAllCaUserId);
                        return;
                    }
                    final TrustedCredentialsFragment trustedCredentialsFragment = TrustedCredentialsFragment.this;
                    trustedCredentialsFragment.getClass();
                    CertHolder[] certHolderArr = (CertHolder[]) arrayList.toArray(new CertHolder[arrayList.size()]);
                    TrustedCredentialsDialogBuilder trustedCredentialsDialogBuilder = new TrustedCredentialsDialogBuilder(trustedCredentialsFragment.getActivity(), trustedCredentialsFragment);
                    trustedCredentialsDialogBuilder.mDialogEventHandler.mCertHolders = certHolderArr;
                    trustedCredentialsDialogBuilder.P.mOnDismissListener = new DialogInterface.OnDismissListener() { // from class: com.android.settings.TrustedCredentialsFragment$$ExternalSyntheticLambda0
                        @Override // android.content.DialogInterface.OnDismissListener
                        public final void onDismiss(DialogInterface dialogInterface) {
                            TrustedCredentialsFragment trustedCredentialsFragment2 = TrustedCredentialsFragment.this;
                            trustedCredentialsFragment2.getActivity().getIntent().removeExtra("ARG_SHOW_NEW_FOR_USER");
                            trustedCredentialsFragment2.mTrustAllCaUserId = -10000;
                        }
                    };
                    trustedCredentialsDialogBuilder.show();
                }
            }

            @Override // android.os.AsyncTask
            public final void onPreExecute() {
                this.mProgressBar = (ProgressBar) TrustedCredentialsFragment.this.mFragmentView.findViewById(R.id.progress);
                this.mLinearLayoutContainer = TrustedCredentialsFragment.this.mFragmentView.findViewById(R.id.linearlayout_container);
                this.mProgressBar.setVisibility(0);
                this.mLinearLayoutContainer.setVisibility(8);
                TextView textView = (TextView) TrustedCredentialsFragment.this.mFragmentView.findViewById(R.id.text_empty);
                this.mTextView = textView;
                textView.setVisibility(0);
            }

            @Override // android.os.AsyncTask
            public final void onProgressUpdate(Object[] objArr) {
                Integer[] numArr = (Integer[]) objArr;
                int intValue = numArr[0].intValue();
                int intValue2 = numArr[1].intValue();
                if (intValue2 != this.mProgressBar.getMax()) {
                    this.mProgressBar.setMax(intValue2);
                }
                this.mProgressBar.setProgress(intValue);
            }

            public final boolean shouldSkipProfile(UserHandle userHandle) {
                return TrustedCredentialsFragment.this.mUserManager.isQuietModeEnabled(userHandle) || !TrustedCredentialsFragment.this.mUserManager.isUserUnlocked(userHandle.getIdentifier());
            }
        }

        public AdapterData(TrustedCredentialsSettings.Tab tab, GroupAdapter groupAdapter) {
            this.mAdapter = groupAdapter;
            this.mTab = tab;
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class AliasOperation extends AsyncTask {
        public final CertHolder mCertHolder;

        public AliasOperation(CertHolder certHolder) {
            this.mCertHolder = certHolder;
            TrustedCredentialsFragment.this.mAliasOperation = this;
            TrustedCredentialsFragment.SETTINTS_VIEW_CERTIFICATES_LIST_SWITCH = 4516;
            TrustedCredentialsFragment.SETTINTS_VIEW_CERTIFICATES_LIST_SWITCH_TURN_ON = 1000;
            TrustedCredentialsFragment.SETTINTS_VIEW_CERTIFICATES_LIST_SWITCH_TURN_OFF = 0;
        }

        @Override // android.os.AsyncTask
        public final Object doInBackground(Object[] objArr) {
            try {
                synchronized (TrustedCredentialsFragment.this.mKeyChainConnectionByProfileId) {
                    try {
                        KeyChain.KeyChainConnection keyChainConnection = (KeyChain.KeyChainConnection) TrustedCredentialsFragment.this.mKeyChainConnectionByProfileId.get(this.mCertHolder.mProfileId);
                        if (keyChainConnection == null) {
                            return Boolean.FALSE;
                        }
                        IKeyChainService service = keyChainConnection.getService();
                        if (this.mCertHolder.isSystemCert()) {
                            LoggingHelper.insertEventLogging(TrustedCredentialsFragment.SETTINTS_VIEW_CERTIFICATES_LIST_SWITCH, this.mCertHolder.mDeleted ? TrustedCredentialsFragment.SETTINTS_VIEW_CERTIFICATES_LIST_SWITCH_TURN_ON : TrustedCredentialsFragment.SETTINTS_VIEW_CERTIFICATES_LIST_SWITCH_TURN_OFF);
                        }
                        CertHolder certHolder = this.mCertHolder;
                        if (!certHolder.mDeleted) {
                            return Boolean.valueOf(service.deleteCaCertificate(certHolder.mAlias));
                        }
                        service.installCaCertificate(certHolder.mX509Cert.getEncoded());
                        return Boolean.TRUE;
                    } catch (Throwable th) {
                        throw th;
                    }
                }
            } catch (RemoteException | IllegalStateException | SecurityException | CertificateEncodingException e) {
                Log.w("TrustedCredentialsFragment", "Error while toggling alias " + this.mCertHolder.mAlias, e);
                return Boolean.FALSE;
            }
        }

        @Override // android.os.AsyncTask
        public final void onPostExecute(Object obj) {
            List list;
            if (((Boolean) obj).booleanValue()) {
                CertHolder certHolder = this.mCertHolder;
                if (certHolder.mTab.mSwitch) {
                    certHolder.mDeleted = !certHolder.mDeleted;
                } else {
                    SparseArray sparseArray = certHolder.mAdapter.mData.mCertHoldersByUserId;
                    if (sparseArray != null && (list = (List) sparseArray.get(certHolder.mProfileId)) != null) {
                        list.remove(certHolder);
                    }
                }
                this.mCertHolder.mAdapter.notifyDataSetChanged();
            } else {
                this.mCertHolder.mAdapter.load();
            }
            TrustedCredentialsFragment.this.mAliasOperation = null;
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class CertHolder implements Comparable {
        public final GroupAdapter mAdapter;
        public final String mAlias;
        public boolean mDeleted;
        public final int mProfileId;
        public final String mSubjectPrimary;
        public final String mSubjectSecondary;
        public final TrustedCredentialsSettings.Tab mTab;
        public final X509Certificate mX509Cert;

        public CertHolder(IKeyChainService iKeyChainService, GroupAdapter groupAdapter, TrustedCredentialsSettings.Tab tab, String str, X509Certificate x509Certificate, int i) {
            boolean z;
            this.mProfileId = i;
            this.mAdapter = groupAdapter;
            this.mTab = tab;
            this.mAlias = str;
            this.mX509Cert = x509Certificate;
            SslCertificate sslCertificate = new SslCertificate(x509Certificate);
            String cName = sslCertificate.getIssuedTo().getCName();
            String oName = sslCertificate.getIssuedTo().getOName();
            String uName = sslCertificate.getIssuedTo().getUName();
            if (oName.isEmpty()) {
                if (cName.isEmpty()) {
                    this.mSubjectPrimary = sslCertificate.getIssuedTo().getDName();
                    this.mSubjectSecondary = ApnSettings.MVNO_NONE;
                } else {
                    this.mSubjectPrimary = cName;
                    this.mSubjectSecondary = ApnSettings.MVNO_NONE;
                }
            } else if (cName.isEmpty()) {
                this.mSubjectPrimary = oName;
                this.mSubjectSecondary = uName;
            } else {
                this.mSubjectPrimary = oName;
                this.mSubjectSecondary = cName;
            }
            try {
                int ordinal = tab.ordinal();
                if (ordinal == 0) {
                    z = !iKeyChainService.containsCaAlias(str);
                } else {
                    if (ordinal != 1) {
                        throw new AssertionError();
                    }
                    z = false;
                }
                this.mDeleted = z;
            } catch (RemoteException e) {
                Log.e("TrustedCredentialsFragment", "Remote exception while checking if alias " + this.mAlias + " is deleted.", e);
                this.mDeleted = false;
            }
        }

        @Override // java.lang.Comparable
        public final int compareTo(Object obj) {
            CertHolder certHolder = (CertHolder) obj;
            int compareToIgnoreCase = this.mSubjectPrimary.compareToIgnoreCase(certHolder.mSubjectPrimary);
            return compareToIgnoreCase != 0 ? compareToIgnoreCase : this.mSubjectSecondary.compareToIgnoreCase(certHolder.mSubjectSecondary);
        }

        public final boolean equals(Object obj) {
            if (obj instanceof CertHolder) {
                return this.mAlias.equals(((CertHolder) obj).mAlias);
            }
            return false;
        }

        public final int hashCode() {
            return this.mAlias.hashCode();
        }

        public final boolean isSystemCert() {
            return this.mTab == TrustedCredentialsSettings.Tab.SYSTEM;
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class ChildAdapter extends BaseAdapter implements View.OnClickListener, AdapterView.OnItemClickListener {
        public static final /* synthetic */ int $r8$clinit = 0;
        public LinearLayout mContainerView;
        public final int mGroupPosition;
        public ViewGroup mHeaderView;
        public ImageView mIndicatorView;
        public boolean mIsListExpanded;
        public ListView mListView;
        public final AnonymousClass1 mObserver;
        public final GroupAdapter mParent;
        public final int[] mGroupExpandedStateSet = {R.attr.state_expanded};
        public final int[] mEmptyStateSet = new int[0];
        public final LinearLayout.LayoutParams mHideContainerLayoutParams = new LinearLayout.LayoutParams(-1, -2, 0.0f);
        public final LinearLayout.LayoutParams mHideListLayoutParams = new LinearLayout.LayoutParams(-1, 0);
        public final LinearLayout.LayoutParams mShowLayoutParams = new LinearLayout.LayoutParams(-1, -1, 1.0f);

        public ChildAdapter(GroupAdapter groupAdapter, int i) {
            DataSetObserver dataSetObserver = new DataSetObserver() { // from class: com.android.settings.TrustedCredentialsFragment.ChildAdapter.1
                @Override // android.database.DataSetObserver
                public final void onChanged() {
                    super.onChanged();
                    ChildAdapter.super.notifyDataSetChanged();
                }

                @Override // android.database.DataSetObserver
                public final void onInvalidated() {
                    super.onInvalidated();
                    ChildAdapter.super.notifyDataSetInvalidated();
                }
            };
            this.mIsListExpanded = true;
            this.mParent = groupAdapter;
            this.mGroupPosition = i;
            groupAdapter.registerDataSetObserver(dataSetObserver);
        }

        @Override // android.widget.Adapter
        public final int getCount() {
            return this.mParent.getChildrenCount(this.mGroupPosition);
        }

        @Override // android.widget.Adapter
        public final Object getItem(int i) {
            return this.mParent.getChild(this.mGroupPosition, i);
        }

        @Override // android.widget.Adapter
        public final long getItemId(int i) {
            this.mParent.getClass();
            return i;
        }

        @Override // android.widget.Adapter
        public final View getView(int i, View view, ViewGroup viewGroup) {
            return this.mParent.getChildView(this.mGroupPosition, i, false, view, viewGroup);
        }

        @Override // android.widget.BaseAdapter
        public final void notifyDataSetChanged() {
            this.mParent.notifyDataSetChanged();
        }

        @Override // android.widget.BaseAdapter
        public final void notifyDataSetInvalidated() {
            this.mParent.notifyDataSetInvalidated();
        }

        @Override // android.view.View.OnClickListener
        public final void onClick(View view) {
            String str;
            this.mIsListExpanded = this.mParent.checkGroupExpandableAndStartWarningActivity(this.mGroupPosition, true) && !this.mIsListExpanded;
            TextView textView = (TextView) view.findViewById(R.id.title);
            TrustedCredentialsFragment.this.mDescription = textView.getText().toString() + " ";
            if (this.mIsListExpanded) {
                str = TrustedCredentialsFragment.this.mDescription + TrustedCredentialsFragment.this.getString(R.string.sec_biometrics_disclaimer_expanded);
            } else {
                str = TrustedCredentialsFragment.this.mDescription + TrustedCredentialsFragment.this.getString(R.string.sec_biometrics_disclaimer_collapsed);
            }
            textView.setContentDescription(str);
            refreshViews();
        }

        @Override // android.widget.AdapterView.OnItemClickListener
        public final void onItemClick(AdapterView adapterView, View view, int i, long j) {
            TrustedCredentialsFragment trustedCredentialsFragment = TrustedCredentialsFragment.this;
            CertHolder child = this.mParent.getChild(this.mGroupPosition, i);
            TrustedCredentialsDialogBuilder trustedCredentialsDialogBuilder = new TrustedCredentialsDialogBuilder(trustedCredentialsFragment.getActivity(), trustedCredentialsFragment);
            trustedCredentialsDialogBuilder.mDialogEventHandler.mCertHolders = child == null ? new CertHolder[0] : new CertHolder[]{child};
            trustedCredentialsDialogBuilder.show();
        }

        public final void refreshViews() {
            this.mIndicatorView.setImageState(this.mIsListExpanded ? this.mGroupExpandedStateSet : this.mEmptyStateSet, false);
            this.mListView.setLayoutParams(this.mIsListExpanded ? this.mShowLayoutParams : this.mHideListLayoutParams);
            this.mContainerView.setLayoutParams(this.mIsListExpanded ? this.mShowLayoutParams : this.mHideContainerLayoutParams);
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class GroupAdapter extends BaseExpandableListAdapter implements ExpandableListView.OnGroupClickListener, ExpandableListView.OnChildClickListener {
        public final ArrayList mChildAdapters = new ArrayList();
        public final AdapterData mData;

        /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
        public final class ViewHolder {
            public TextView mSubjectPrimaryView;
            public TextView mSubjectSecondaryView;
            public CompoundButton mSwitch;
        }

        public GroupAdapter(TrustedCredentialsSettings.Tab tab) {
            this.mData = TrustedCredentialsFragment.this.new AdapterData(tab, this);
            load();
        }

        public final boolean checkGroupExpandableAndStartWarningActivity(int i, boolean z) {
            UserHandle userHandle = new UserHandle(this.mData.mCertHoldersByUserId.keyAt(i));
            int identifier = userHandle.getIdentifier();
            if (TrustedCredentialsFragment.this.mUserManager.isQuietModeEnabled(userHandle)) {
                if (z) {
                    TrustedCredentialsFragment.this.getActivity().startActivity(UnlaunchableAppActivity.createInQuietModeDialogIntent(identifier));
                }
                return false;
            }
            if (TrustedCredentialsFragment.this.mUserManager.isUserUnlocked(userHandle) || !new LockPatternUtils(TrustedCredentialsFragment.this.getActivity()).isSeparateProfileChallengeEnabled(identifier)) {
                return true;
            }
            if (z) {
                TrustedCredentialsFragment trustedCredentialsFragment = TrustedCredentialsFragment.this;
                Intent createConfirmDeviceCredentialIntent = trustedCredentialsFragment.mKeyguardManager.createConfirmDeviceCredentialIntent(null, null, identifier);
                if (createConfirmDeviceCredentialIntent != null) {
                    trustedCredentialsFragment.mConfirmingCredentialUser = identifier;
                    trustedCredentialsFragment.startActivityForResult(createConfirmDeviceCredentialIntent, 1);
                }
            }
            return false;
        }

        @Override // android.widget.ExpandableListAdapter
        public final long getChildId(int i, int i2) {
            return i2;
        }

        @Override // android.widget.ExpandableListAdapter
        public final View getChildView(int i, int i2, boolean z, View view, ViewGroup viewGroup) {
            View view2;
            ViewHolder viewHolder;
            CertHolder child = getChild(i, i2);
            TrustedCredentialsSettings.Tab tab = this.mData.mTab;
            if (view == null) {
                viewHolder = new ViewHolder();
                view2 = LayoutInflater.from(TrustedCredentialsFragment.this.getActivity()).inflate(R.layout.trusted_credential, viewGroup, false);
                view2.setTag(viewHolder);
                viewHolder.mSubjectPrimaryView = (TextView) view2.findViewById(R.id.trusted_credential_subject_primary);
                viewHolder.mSubjectSecondaryView = (TextView) view2.findViewById(R.id.trusted_credential_subject_secondary);
                CompoundButton compoundButton = (CompoundButton) view2.findViewById(R.id.trusted_credential_status);
                viewHolder.mSwitch = compoundButton;
                compoundButton.setOnClickListener(new View.OnClickListener() { // from class: com.android.settings.TrustedCredentialsFragment$GroupAdapter$$ExternalSyntheticLambda3
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view3) {
                        TrustedCredentialsFragment.GroupAdapter groupAdapter = TrustedCredentialsFragment.GroupAdapter.this;
                        TrustedCredentialsFragment.this.mSelectedCertHolder = (TrustedCredentialsFragment.CertHolder) view3.getTag();
                        view3.sendAccessibilityEvent(8);
                        TrustedCredentialsFragment trustedCredentialsFragment = TrustedCredentialsFragment.this;
                        TrustedCredentialsFragment.CertHolder certHolder = (TrustedCredentialsFragment.CertHolder) view3.getTag();
                        trustedCredentialsFragment.getClass();
                        trustedCredentialsFragment.new AliasOperation(certHolder).execute(new Void[0]);
                    }
                });
                viewHolder.mSwitch.setContentDescription(child.mSubjectPrimary);
            } else {
                view2 = view;
                viewHolder = (ViewHolder) view.getTag();
            }
            CertHolder certHolder = TrustedCredentialsFragment.this.mSelectedCertHolder;
            if (certHolder != null && child.equals(certHolder)) {
                viewHolder.mSwitch.sendAccessibilityEvent(8);
                TrustedCredentialsFragment.this.mSelectedCertHolder = null;
            }
            viewHolder.mSubjectPrimaryView.setText(child.mSubjectPrimary);
            viewHolder.mSubjectSecondaryView.setText(child.mSubjectSecondary);
            if (tab.mSwitch) {
                viewHolder.mSwitch.setChecked(!child.mDeleted);
                viewHolder.mSwitch.setEnabled(!TrustedCredentialsFragment.this.mUserManager.hasUserRestriction("no_config_credentials", new UserHandle(child.mProfileId)));
                Context applicationContext = TrustedCredentialsFragment.this.getActivity().getApplicationContext();
                if (Utils.isUserRemoveCertificateAllowedAsUser(applicationContext, child.mProfileId)) {
                    if (Utils.getEnterprisePolicyEnabled(applicationContext, "content://com.sec.knox.provider/CertificatePolicy", "isCaCertificateDisabledAsUser", new String[]{child.mAlias, String.valueOf(child.mProfileId)}) == 1) {
                        Log.d("Settings", "CaCertificateDisabledAsUser : disabled");
                        Log.w("TrustedCredentialsFragment", "Disabling switch as certificate " + child.mAlias + " is untrusted by MDM policy and can't be re-enabled");
                    }
                    viewHolder.mSwitch.setVisibility(0);
                    viewHolder.mSwitch.setTag(child);
                } else {
                    Log.w("TrustedCredentialsFragment", "Disabling switch as certificate " + child.mAlias + " removal is not allowed by MDM policy");
                }
                viewHolder.mSwitch.setEnabled(false);
                viewHolder.mSwitch.setVisibility(0);
                viewHolder.mSwitch.setTag(child);
            }
            return view2;
        }

        @Override // android.widget.ExpandableListAdapter
        public final int getChildrenCount(int i) {
            List list = (List) this.mData.mCertHoldersByUserId.valueAt(i);
            if (list != null) {
                return list.size();
            }
            return 0;
        }

        @Override // android.widget.ExpandableListAdapter
        public final Object getGroup(int i) {
            return new UserHandle(this.mData.mCertHoldersByUserId.keyAt(i));
        }

        @Override // android.widget.ExpandableListAdapter
        public final int getGroupCount() {
            return this.mData.mCertHoldersByUserId.size();
        }

        @Override // android.widget.ExpandableListAdapter
        public final long getGroupId(int i) {
            return this.mData.mCertHoldersByUserId.keyAt(i);
        }

        @Override // android.widget.ExpandableListAdapter
        public final View getGroupView(int i, boolean z, View view, ViewGroup viewGroup) {
            final int i2 = 1;
            final int i3 = 0;
            if (view == null) {
                LayoutInflater layoutInflater = (LayoutInflater) TrustedCredentialsFragment.this.getActivity().getSystemService("layout_inflater");
                StringBuilder sb = Utils.sBuilder;
                TypedArray obtainStyledAttributes = layoutInflater.getContext().obtainStyledAttributes(null, com.android.internal.R.styleable.Preference, R.attr.preferenceCategoryStyle, 0);
                int resourceId = obtainStyledAttributes.getResourceId(3, 0);
                obtainStyledAttributes.recycle();
                view = layoutInflater.inflate(resourceId, viewGroup, false);
                TypedValue typedValue = new TypedValue();
                TrustedCredentialsFragment.this.getActivity().getApplicationContext().getTheme().resolveAttribute(R.attr.selectableItemBackground, typedValue, true);
                view.setBackgroundResource(typedValue.resourceId);
                view.setEnabled(false);
            }
            TextView textView = (TextView) view.findViewById(R.id.title);
            UserInfo userInfo = TrustedCredentialsFragment.this.mUserManager.getUserInfo(this.mData.mCertHoldersByUserId.keyAt(i));
            if (userInfo.isManagedProfile()) {
                if (SemPersonaManager.isKnoxId(userInfo.id) && (SemPersonaManager.isAppSeparationUserId(userInfo.id) || SemPersonaManager.isSecureFolderId(userInfo.id))) {
                    TrustedCredentialsFragment trustedCredentialsFragment = TrustedCredentialsFragment.this;
                    trustedCredentialsFragment.mDescription = SemPersonaManager.getPersonaName(trustedCredentialsFragment.getActivity(), userInfo.id);
                    textView.setText(TrustedCredentialsFragment.this.mDescription);
                    textView.setContentDescription(TrustedCredentialsFragment.this.mDescription + " " + TrustedCredentialsFragment.this.getString(R.string.sec_biometrics_disclaimer_expanded));
                } else {
                    TrustedCredentialsFragment trustedCredentialsFragment2 = TrustedCredentialsFragment.this;
                    trustedCredentialsFragment2.mDescription = trustedCredentialsFragment2.mDevicePolicyManager.getResources().getString("Settings.WORK_CATEGORY_HEADER", new Supplier(this) { // from class: com.android.settings.TrustedCredentialsFragment$GroupAdapter$$ExternalSyntheticLambda0
                        public final /* synthetic */ TrustedCredentialsFragment.GroupAdapter f$0;

                        {
                            this.f$0 = this;
                        }

                        @Override // java.util.function.Supplier
                        public final Object get() {
                            int i4 = i3;
                            TrustedCredentialsFragment.GroupAdapter groupAdapter = this.f$0;
                            switch (i4) {
                                case 0:
                                    return TrustedCredentialsFragment.this.getString(R.string.category_work);
                                case 1:
                                    return TrustedCredentialsFragment.this.getString(R.string.category_private);
                                default:
                                    return TrustedCredentialsFragment.this.getString(R.string.category_personal);
                            }
                        }
                    });
                    textView.setText(TrustedCredentialsFragment.this.mDescription);
                    textView.setContentDescription(TrustedCredentialsFragment.this.mDescription + " " + TrustedCredentialsFragment.this.getString(R.string.sec_biometrics_disclaimer_expanded));
                }
            } else if (Flags.allowPrivateProfile() && android.multiuser.Flags.enablePrivateSpaceFeatures() && android.multiuser.Flags.handleInterleavedSettingsForPrivateSpace() && userInfo.isPrivateProfile()) {
                textView.setText(TrustedCredentialsFragment.this.mDevicePolicyManager.getResources().getString("Settings.PRIVATE_CATEGORY_HEADER", new Supplier(this) { // from class: com.android.settings.TrustedCredentialsFragment$GroupAdapter$$ExternalSyntheticLambda0
                    public final /* synthetic */ TrustedCredentialsFragment.GroupAdapter f$0;

                    {
                        this.f$0 = this;
                    }

                    @Override // java.util.function.Supplier
                    public final Object get() {
                        int i4 = i2;
                        TrustedCredentialsFragment.GroupAdapter groupAdapter = this.f$0;
                        switch (i4) {
                            case 0:
                                return TrustedCredentialsFragment.this.getString(R.string.category_work);
                            case 1:
                                return TrustedCredentialsFragment.this.getString(R.string.category_private);
                            default:
                                return TrustedCredentialsFragment.this.getString(R.string.category_personal);
                        }
                    }
                }));
            } else if (userInfo.isCloneProfile()) {
                TrustedCredentialsFragment trustedCredentialsFragment3 = TrustedCredentialsFragment.this;
                trustedCredentialsFragment3.mDescription = trustedCredentialsFragment3.getString(R.string.dual_app_title);
                textView.setText(TrustedCredentialsFragment.this.mDescription);
                textView.setContentDescription(TrustedCredentialsFragment.this.mDescription + " " + TrustedCredentialsFragment.this.getString(R.string.sec_biometrics_disclaimer_expanded));
            } else {
                TrustedCredentialsFragment trustedCredentialsFragment4 = TrustedCredentialsFragment.this;
                final int i4 = 2;
                trustedCredentialsFragment4.mDescription = trustedCredentialsFragment4.mDevicePolicyManager.getResources().getString("Settings.PERSONAL_CATEGORY_HEADER", new Supplier(this) { // from class: com.android.settings.TrustedCredentialsFragment$GroupAdapter$$ExternalSyntheticLambda0
                    public final /* synthetic */ TrustedCredentialsFragment.GroupAdapter f$0;

                    {
                        this.f$0 = this;
                    }

                    @Override // java.util.function.Supplier
                    public final Object get() {
                        int i42 = i4;
                        TrustedCredentialsFragment.GroupAdapter groupAdapter = this.f$0;
                        switch (i42) {
                            case 0:
                                return TrustedCredentialsFragment.this.getString(R.string.category_work);
                            case 1:
                                return TrustedCredentialsFragment.this.getString(R.string.category_private);
                            default:
                                return TrustedCredentialsFragment.this.getString(R.string.category_personal);
                        }
                    }
                });
                textView.setText(TrustedCredentialsFragment.this.mDescription);
                textView.setContentDescription(TrustedCredentialsFragment.this.mDescription + " " + TrustedCredentialsFragment.this.getString(R.string.sec_biometrics_disclaimer_expanded));
            }
            return view;
        }

        @Override // android.widget.ExpandableListAdapter
        public final boolean hasStableIds() {
            return false;
        }

        @Override // android.widget.ExpandableListAdapter
        public final boolean isChildSelectable(int i, int i2) {
            return true;
        }

        public final void load() {
            AdapterData adapterData = this.mData;
            Objects.requireNonNull(adapterData);
            adapterData.new AliasLoader().execute(new Void[0]);
        }

        @Override // android.widget.ExpandableListView.OnChildClickListener
        public final boolean onChildClick(ExpandableListView expandableListView, View view, int i, int i2, long j) {
            TrustedCredentialsFragment trustedCredentialsFragment = TrustedCredentialsFragment.this;
            CertHolder child = getChild(i, i2);
            TrustedCredentialsDialogBuilder trustedCredentialsDialogBuilder = new TrustedCredentialsDialogBuilder(trustedCredentialsFragment.getActivity(), trustedCredentialsFragment);
            trustedCredentialsDialogBuilder.mDialogEventHandler.mCertHolders = child == null ? new CertHolder[0] : new CertHolder[]{child};
            trustedCredentialsDialogBuilder.show();
            return true;
        }

        @Override // android.widget.ExpandableListView.OnGroupClickListener
        public final boolean onGroupClick(ExpandableListView expandableListView, View view, int i, long j) {
            return !checkGroupExpandableAndStartWarningActivity(i, true);
        }

        @Override // android.widget.ExpandableListAdapter
        public final CertHolder getChild(int i, int i2) {
            SparseArray sparseArray = this.mData.mCertHoldersByUserId;
            return (CertHolder) ((List) sparseArray.get(sparseArray.keyAt(i))).get(i2);
        }
    }

    @Override // androidx.fragment.app.Fragment
    public final void onActivityResult(int i, int i2, Intent intent) {
        if (i == 1) {
            int i3 = this.mConfirmingCredentialUser;
            IntConsumer intConsumer = this.mConfirmingCredentialListener;
            this.mConfirmingCredentialUser = -10000;
            this.mConfirmingCredentialListener = null;
            if (i2 == -1) {
                this.mConfirmedCredentialUsers.add(Integer.valueOf(i3));
                if (intConsumer != null) {
                    intConsumer.accept(i3);
                }
            }
        }
    }

    @Override // com.android.settingslib.core.lifecycle.ObservableFragment, androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        FragmentActivity activity = getActivity();
        this.mDevicePolicyManager = (DevicePolicyManager) activity.getSystemService(DevicePolicyManager.class);
        this.mUserManager = (UserManager) activity.getSystemService(UserManager.class);
        this.mKeyguardManager = (KeyguardManager) activity.getSystemService(KeyguardManager.class);
        this.mTrustAllCaUserId = activity.getIntent().getIntExtra("ARG_SHOW_NEW_FOR_USER", -10000);
        this.mConfirmedCredentialUsers = new ArraySet(2);
        this.mConfirmingCredentialUser = -10000;
        if (bundle != null) {
            this.mConfirmingCredentialUser = bundle.getInt("ConfirmingCredentialUser", -10000);
            ArrayList<Integer> integerArrayList = bundle.getIntegerArrayList("ConfirmedCredentialUsers");
            if (integerArrayList != null) {
                this.mConfirmedCredentialUsers.addAll(integerArrayList);
            }
        }
        IntentFilter intentFilter = new IntentFilter();
        if (Flags.allowPrivateProfile() && android.multiuser.Flags.enablePrivateSpaceFeatures() && android.multiuser.Flags.handleInterleavedSettingsForPrivateSpace()) {
            intentFilter.addAction("android.intent.action.PROFILE_AVAILABLE");
            intentFilter.addAction("android.intent.action.PROFILE_UNAVAILABLE");
            intentFilter.addAction("android.intent.action.PROFILE_ACCESSIBLE");
        } else {
            intentFilter.addAction("android.intent.action.MANAGED_PROFILE_AVAILABLE");
            intentFilter.addAction("android.intent.action.MANAGED_PROFILE_UNAVAILABLE");
            intentFilter.addAction("android.intent.action.MANAGED_PROFILE_UNLOCKED");
        }
        activity.registerReceiver(this.mProfileChangedReceiver, intentFilter);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r10v0 */
    /* JADX WARN: Type inference failed for: r10v1, types: [android.util.AttributeSet] */
    /* JADX WARN: Type inference failed for: r10v7 */
    /* JADX WARN: Type inference failed for: r10v8 */
    /* JADX WARN: Type inference failed for: r10v9 */
    /* JADX WARN: Type inference failed for: r15v8, types: [android.app.Activity, androidx.fragment.app.FragmentActivity] */
    /* JADX WARN: Type inference failed for: r1v15, types: [int] */
    /* JADX WARN: Type inference failed for: r1v17 */
    /* JADX WARN: Type inference failed for: r1v9 */
    @Override // androidx.fragment.app.Fragment
    public final View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        Bundle bundle2;
        ?? r1;
        SparseArray sparseParcelableArray;
        LayoutInflater layoutInflater2 = layoutInflater;
        Bundle bundle3 = bundle;
        boolean z = true;
        ViewGroup viewGroup2 = (ViewGroup) layoutInflater2.inflate(R.layout.trusted_credentials, viewGroup, false);
        this.mFragmentView = viewGroup2;
        LinearLayout linearLayout = (LinearLayout) viewGroup2.findViewById(R.id.linearlayout_container);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(Utils.getListHorizontalPadding(getContext()), -1);
        View view = new View(getContext());
        view.setLayoutParams(layoutParams);
        ?? r10 = 0;
        view.setBackgroundColor(getResources().getColor(R.color.sec_widget_round_and_bgcolor, null));
        linearLayout.addView(view, 0);
        View view2 = new View(getContext());
        view2.setLayoutParams(layoutParams);
        view2.setBackgroundColor(getResources().getColor(R.color.sec_widget_round_and_bgcolor, null));
        linearLayout.addView(view2);
        ViewGroup viewGroup3 = (ViewGroup) linearLayout.findViewById(R.id.content);
        viewGroup3.semSetRoundedCorners(15);
        viewGroup3.semSetRoundedCornerColor(15, getResources().getColor(R.color.sec_widget_round_and_bgcolor));
        GroupAdapter groupAdapter = new GroupAdapter(requireArguments().getInt("tab") == 0 ? TrustedCredentialsSettings.Tab.SYSTEM : TrustedCredentialsSettings.Tab.USER);
        this.mGroupAdapter = groupAdapter;
        int groupCount = groupAdapter.getGroupCount();
        int i = 0;
        while (i < groupCount) {
            if (bundle3 == null) {
                bundle2 = r10;
            } else {
                bundle2 = bundle3.getBundle("Group" + this.mGroupAdapter.mData.mCertHoldersByUserId.keyAt(i));
            }
            GroupAdapter groupAdapter2 = this.mGroupAdapter;
            UserInfo userInfo = TrustedCredentialsFragment.this.mUserManager.getUserInfo(groupAdapter2.mData.mCertHoldersByUserId.keyAt(i));
            if (Utils.shouldHideUser(userInfo.getUserHandle(), this.mUserManager)) {
                r1 = z;
            } else {
                boolean isManagedProfile = userInfo.isManagedProfile();
                if (Flags.allowPrivateProfile() && android.multiuser.Flags.enablePrivateSpaceFeatures() && android.multiuser.Flags.handleInterleavedSettingsForPrivateSpace()) {
                    isManagedProfile |= userInfo.isPrivateProfile();
                }
                GroupAdapter groupAdapter3 = this.mGroupAdapter;
                groupAdapter3.getClass();
                TrustedCredentialsFragment trustedCredentialsFragment = TrustedCredentialsFragment.this;
                ChildAdapter childAdapter = trustedCredentialsFragment.new ChildAdapter(groupAdapter3, i);
                groupAdapter3.mChildAdapters.add(childAdapter);
                LinearLayout linearLayout2 = (LinearLayout) layoutInflater2.inflate(R.layout.trusted_credential_list_container, viewGroup3, false);
                childAdapter.mContainerView = linearLayout2;
                linearLayout2.setSaveFromParentEnabled(false);
                ListView listView = (ListView) childAdapter.mContainerView.findViewById(R.id.cert_list);
                childAdapter.mListView = listView;
                listView.setAdapter((ListAdapter) childAdapter);
                childAdapter.mListView.setOnItemClickListener(childAdapter);
                childAdapter.mListView.setItemsCanFocus(z);
                childAdapter.mListView.setNestedScrollingEnabled(z);
                childAdapter.mListView.semSetVerticalScrollBarPaddingPosition((int) trustedCredentialsFragment.getResources().getDimension(R.dimen.sec_trusted_credentials_scrollbar_padding));
                childAdapter.mListView.semSetVerticalScrollBarPadding(z);
                int dimensionPixelSize = trustedCredentialsFragment.getContext().getResources().getDimensionPixelSize(R.dimen.sec_trusted_credentials_scrollbar_vertical_padding);
                childAdapter.mListView.semSetScrollBarBottomPadding(dimensionPixelSize);
                childAdapter.mListView.semSetScrollBarTopPadding(dimensionPixelSize);
                ViewGroup viewGroup4 = (ViewGroup) childAdapter.mContainerView.findViewById(R.id.header_view);
                childAdapter.mHeaderView = viewGroup4;
                viewGroup4.setOnClickListener(childAdapter);
                ImageView imageView = (ImageView) childAdapter.mHeaderView.findViewById(R.id.group_indicator);
                childAdapter.mIndicatorView = imageView;
                TypedArray obtainStyledAttributes = trustedCredentialsFragment.getActivity().obtainStyledAttributes(r10, com.android.internal.R.styleable.ExpandableListView, R.attr.expandableListViewStyle, 0);
                Drawable drawable = obtainStyledAttributes.getDrawable(0);
                Context applicationContext = trustedCredentialsFragment.getActivity().getApplicationContext();
                if (applicationContext == null) {
                    Signature[] signatureArr = SecDisplayUtils.SIGNATURES;
                    Log.secD("SecDisplayUtils", "getNightModeSetting() - context is null");
                } else if (!SecDisplayUtils.canChangeNightMode(applicationContext)) {
                    Log.secD("SecDisplayUtils", "getNightModeSetting() - Disabled");
                    if (!(!Utils.isCurrentThemeSupportNightTheme(applicationContext))) {
                        Utils.isMinimalBatteryUseEnabled(applicationContext);
                    }
                } else if (Settings.System.getIntForUser(applicationContext.getContentResolver(), "display_night_theme", 0, 0) == 1) {
                    drawable.setTint(trustedCredentialsFragment.getResources().getColor(R.color.sec_trusted_credentials_group_indicator_night_theme));
                }
                obtainStyledAttributes.recycle();
                imageView.setImageDrawable(drawable);
                FrameLayout frameLayout = (FrameLayout) childAdapter.mHeaderView.findViewById(R.id.header_content_container);
                r10 = 0;
                frameLayout.addView(childAdapter.mParent.getGroupView(childAdapter.mGroupPosition, true, null, frameLayout));
                if (bundle2 != null && (sparseParcelableArray = bundle2.getSparseParcelableArray("Container", Parcelable.class)) != null) {
                    childAdapter.mContainerView.restoreHierarchyState(sparseParcelableArray);
                }
                int groupCount2 = this.mGroupAdapter.getGroupCount();
                childAdapter.mHeaderView.setVisibility(groupCount2 > 1 ? 0 : 8);
                childAdapter.mHeaderView.findViewById(R.id.header_divider).setVisibility(isManagedProfile ? 0 : 8);
                boolean z2 = groupCount2 <= 2 || !isManagedProfile;
                if (bundle2 != null) {
                    z2 = bundle2.getBoolean("IsListExpanded");
                }
                childAdapter.mIsListExpanded = z2 && childAdapter.mParent.checkGroupExpandableAndStartWarningActivity(childAdapter.mGroupPosition, false);
                childAdapter.refreshViews();
                if (isManagedProfile) {
                    viewGroup3.addView(linearLayout2);
                } else {
                    viewGroup3.addView(linearLayout2, 0);
                }
                r1 = 1;
            }
            i += r1;
            bundle3 = bundle;
            z = r1;
            layoutInflater2 = layoutInflater;
            r10 = r10;
        }
        return this.mFragmentView;
    }

    @Override // com.android.settingslib.core.lifecycle.ObservableFragment, androidx.fragment.app.Fragment
    public final void onDestroy() {
        getActivity().unregisterReceiver(this.mProfileChangedReceiver);
        Iterator it = ((ArraySet) this.mAliasLoaders).iterator();
        while (it.hasNext()) {
            ((AdapterData.AliasLoader) it.next()).cancel(true);
        }
        ((ArraySet) this.mAliasLoaders).clear();
        AliasOperation aliasOperation = this.mAliasOperation;
        if (aliasOperation != null) {
            aliasOperation.cancel(true);
            this.mAliasOperation = null;
        }
        synchronized (this.mKeyChainConnectionByProfileId) {
            try {
                int size = this.mKeyChainConnectionByProfileId.size();
                for (int i = 0; i < size; i++) {
                    ((KeyChain.KeyChainConnection) this.mKeyChainConnectionByProfileId.valueAt(i)).close();
                }
                this.mKeyChainConnectionByProfileId.clear();
            } catch (Throwable th) {
                throw th;
            }
        }
        super.onDestroy();
    }

    @Override // com.android.settingslib.core.lifecycle.ObservableFragment, androidx.fragment.app.Fragment
    public final void onResume() {
        super.onResume();
        this.mFragmentView.requestLayout();
    }

    @Override // com.android.settingslib.core.lifecycle.ObservableFragment, androidx.fragment.app.Fragment
    public final void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        bundle.putIntegerArrayList("ConfirmedCredentialUsers", new ArrayList<>(this.mConfirmedCredentialUsers));
        bundle.putInt("ConfirmingCredentialUser", this.mConfirmingCredentialUser);
        GroupAdapter groupAdapter = this.mGroupAdapter;
        int size = groupAdapter.mChildAdapters.size();
        for (int i = 0; i < size; i++) {
            ChildAdapter childAdapter = (ChildAdapter) groupAdapter.mChildAdapters.get(i);
            String str = "Group" + groupAdapter.mData.mCertHoldersByUserId.keyAt(i);
            int i2 = ChildAdapter.$r8$clinit;
            childAdapter.getClass();
            Bundle bundle2 = new Bundle();
            SparseArray<? extends Parcelable> sparseArray = new SparseArray<>();
            childAdapter.mContainerView.saveHierarchyState(sparseArray);
            bundle2.putSparseParcelableArray("Container", sparseArray);
            bundle2.putBoolean("IsListExpanded", childAdapter.mIsListExpanded);
            bundle.putBundle(str, bundle2);
        }
    }
}
