package com.android.settings.applications.autofill;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.content.pm.ServiceInfo;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.os.UserHandle;
import android.service.autofill.AutofillServiceInfo;
import android.service.autofill.IAutoFillService;
import android.text.TextUtils;
import android.util.IconDrawableFactory;
import android.util.Log;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.OnLifecycleEvent;
import androidx.preference.Preference;
import androidx.preference.PreferenceGroup;
import androidx.preference.PreferenceScreen;

import com.android.internal.annotations.VisibleForTesting;
import com.android.internal.os.IResultReceiver;
import com.android.settings.R;
import com.android.settings.Utils;
import com.android.settings.core.BasePreferenceController;
import com.android.settingslib.utils.StringUtil;
import com.android.settingslib.widget.AppPreference;

import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class PasswordsPreferenceController extends BasePreferenceController
        implements LifecycleObserver {
    private static final boolean DEBUG = false;
    private static final String TAG = "AutofillSettings";
    private final IconDrawableFactory mIconFactory;
    private LifecycleOwner mLifecycleOwner;
    private final PackageManager mPm;
    private final List<AutofillServiceInfo> mServices;

    public PasswordsPreferenceController(Context context, String str) {
        super(context, str);
        this.mPm = context.getPackageManager();
        this.mIconFactory = IconDrawableFactory.newInstance(this.mContext);
        this.mServices = new ArrayList();
    }

    private void addPasswordPreferences(
            final Context context, final int i, PreferenceGroup preferenceGroup) {
        for (int i2 = 0; i2 < this.mServices.size(); i2++) {
            final AutofillServiceInfo autofillServiceInfo = this.mServices.get(i2);
            final AppPreference appPreference = new AppPreference(context);
            final ServiceInfo serviceInfo = autofillServiceInfo.getServiceInfo();
            appPreference.setTitle(serviceInfo.loadLabel(this.mPm));
            appPreference.setIcon(
                    Utils.getSafeIcon(
                            this.mIconFactory.getBadgedIcon(
                                    serviceInfo, serviceInfo.applicationInfo, i)));
            appPreference.setOnPreferenceClickListener(
                    new Preference
                            .OnPreferenceClickListener() { // from class:
                                                           // com.android.settings.applications.autofill.PasswordsPreferenceController$$ExternalSyntheticLambda0
                        @Override // androidx.preference.Preference.OnPreferenceClickListener
                        public final boolean onPreferenceClick(Preference preference) {
                            boolean lambda$addPasswordPreferences$0;
                            lambda$addPasswordPreferences$0 =
                                    PasswordsPreferenceController.lambda$addPasswordPreferences$0(
                                            serviceInfo,
                                            autofillServiceInfo,
                                            context,
                                            i,
                                            preference);
                            return lambda$addPasswordPreferences$0;
                        }
                    });
            MutableLiveData mutableLiveData = new MutableLiveData();
            mutableLiveData.observe(
                    this.mLifecycleOwner,
                    new Observer() { // from class:
                                     // com.android.settings.applications.autofill.PasswordsPreferenceController$$ExternalSyntheticLambda1
                        @Override // androidx.lifecycle.Observer
                        public final void onChanged(Object obj) {
                            PasswordsPreferenceController.this.lambda$addPasswordPreferences$1(
                                    appPreference, (Integer) obj);
                        }
                    });
            requestSavedPasswordCount(autofillServiceInfo, i, mutableLiveData);
            preferenceGroup.addPreference(appPreference);
        }
    }

    private int getUser() {
        UserHandle workProfileUser = getWorkProfileUser();
        return workProfileUser != null ? workProfileUser.getIdentifier() : UserHandle.myUserId();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$addPasswordPreferences$0(
            ServiceInfo serviceInfo,
            AutofillServiceInfo autofillServiceInfo,
            Context context,
            int i,
            Preference preference) {
        context.startActivityAsUser(
                new Intent("android.intent.action.MAIN")
                        .setClassName(
                                serviceInfo.packageName, autofillServiceInfo.getPasswordsActivity())
                        .setFlags(268435456),
                UserHandle.of(i));
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$addPasswordPreferences$1(
            AppPreference appPreference, Integer num) {
        appPreference.setSummary(
                StringUtil.getIcuPluralsString(
                        this.mContext, num.intValue(), R.string.autofill_passwords_count));
    }

    private void requestSavedPasswordCount(
            AutofillServiceInfo autofillServiceInfo, int i, MutableLiveData mutableLiveData) {
        Intent component =
                new Intent("android.service.autofill.AutofillService")
                        .setComponent(autofillServiceInfo.getServiceInfo().getComponentName());
        AutofillServiceConnection autofillServiceConnection =
                new AutofillServiceConnection(this.mContext, mutableLiveData);
        if (this.mContext.bindServiceAsUser(
                component, autofillServiceConnection, 1, UserHandle.of(i))) {
            autofillServiceConnection.mBound.set(true);
            this.mLifecycleOwner.getLifecycle().addObserver(autofillServiceConnection);
        }
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        addPasswordPreferences(
                preferenceScreen.getContext(),
                getUser(),
                (PreferenceGroup) preferenceScreen.findPreference(getPreferenceKey()));
        replaceEnterpriseStringTitle(
                preferenceScreen,
                "auto_sync_personal_account_data",
                "Settings.AUTO_SYNC_PERSONAL_DATA",
                R.string.account_settings_menu_auto_sync_personal);
        replaceEnterpriseStringTitle(
                preferenceScreen,
                "auto_sync_work_account_data",
                "Settings.AUTO_SYNC_WORK_DATA",
                R.string.account_settings_menu_auto_sync_work);
        replaceEnterpriseStringTitle(
                preferenceScreen,
                "auto_sync_private_account_data",
                "Settings.AUTO_SYNC_PRIVATE_DATA",
                R.string.account_settings_menu_auto_sync_private);
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        return this.mServices.isEmpty() ? 2 : 0;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ Class getBackgroundWorkerClass() {
        return null;
    }

    public List getBackupKeys() {
        return new ArrayList();
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ IntentFilter getIntentFilter() {
        return null;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Intent getLaunchIntent() {
        return null;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ int getSliceHighlightMenuRes() {
        return 0;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ String getStatusText() {
        return null;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlValue getValue() {
        return null;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean hasAsyncUpdate() {
        return false;
    }

    @VisibleForTesting
    public void init(LifecycleOwner lifecycleOwner, List<AutofillServiceInfo> list) {
        this.mLifecycleOwner = lifecycleOwner;
        for (int size = list.size() - 1; size >= 0; size--) {
            if (TextUtils.isEmpty(list.get(size).getPasswordsActivity())) {
                list.remove(size);
            }
        }
        this.mServices.clear();
        this.mServices.addAll(list);
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean isControllable() {
        return false;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean isPublicSlice() {
        return false;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean isSliceable() {
        return false;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Controllable$ControllableType needUserInteraction(
            Object obj) {
        return Controllable$ControllableType.NO_INTERACTION;
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    public void onCreate(LifecycleOwner lifecycleOwner) {
        init(lifecycleOwner, AutofillServiceInfo.getAvailableServices(this.mContext, getUser()));
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return false;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlResult setValue(ControlValue controlValue) {
        return null;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }

    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {}

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class AutofillServiceConnection implements ServiceConnection, LifecycleObserver {
        public final AtomicBoolean mBound = new AtomicBoolean();
        public final WeakReference mContext;
        public final MutableLiveData mData;

        public AutofillServiceConnection(Context context, MutableLiveData mutableLiveData) {
            this.mContext = new WeakReference(context);
            this.mData = mutableLiveData;
        }

        @Override // android.content.ServiceConnection
        public final void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            try {
                IAutoFillService.Stub.asInterface(iBinder)
                        .onSavedPasswordCountRequest(
                                new IResultReceiver
                                        .Stub() { // from class:
                                                  // com.android.settings.applications.autofill.PasswordsPreferenceController.AutofillServiceConnection.1
                                    public final void send(int i, Bundle bundle) {
                                        if (i == 0 && bundle != null) {
                                            AutofillServiceConnection.this.mData.postValue(
                                                    Integer.valueOf(bundle.getInt("result")));
                                        }
                                        AutofillServiceConnection.this.unbind();
                                    }
                                });
            } catch (RemoteException e) {
                Log.e(PasswordsPreferenceController.TAG, "Failed to fetch password count: " + e);
            }
        }

        @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
        public void unbind() {
            Context context;
            if (this.mBound.getAndSet(false) && (context = (Context) this.mContext.get()) != null) {
                context.unbindService(this);
            }
        }

        @Override // android.content.ServiceConnection
        public final void onServiceDisconnected(ComponentName componentName) {}
    }
}
