package com.samsung.android.settings.applications.specialaccess;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.AppOpsManager;
import android.app.Dialog;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.pm.UserInfo;
import android.content.res.Resources;
import android.os.Binder;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.RemoteCallback;
import android.os.RemoteException;
import android.os.UserHandle;
import android.os.UserManager;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.EventLog;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertController;
import androidx.appcompat.app.AlertDialog;

import com.android.settings.R;
import com.android.settings.fuelgauge.BatteryUtils;
import com.android.settings.overlay.FeatureFactoryImpl;
import com.android.settings.users.UserDialogs;
import com.android.settingslib.RestrictedLockUtils;
import com.android.settingslib.RestrictedLockUtilsInternal;

import com.samsung.android.knox.EnterpriseDeviceAdminInfo;
import com.samsung.android.knox.EnterpriseDeviceManager;
import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.settings.analyzestorage.data.constant.FileType;
import com.samsung.android.settings.logging.SALogging;

import java.util.HashMap;
import java.util.Iterator;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class SecDeviceAdminAdd extends Activity {
    public static final /* synthetic */ int $r8$clinit = 0;
    public AlertDialog detailDialog;
    public Button mActionButton;
    public TextView mAddMsg;
    public ImageView mAddMsgExpander;
    public String mAddMsgText;
    public boolean mAdding;
    public boolean mAddingProfileOwner;
    public TextView mAdminDescription;
    public ImageView mAdminIcon;
    public TextView mAdminName;
    public ViewGroup mAdminPolicies;
    public boolean mAdminPoliciesInitialized;
    public TextView mAdminWarning;
    public AppOpsManager mAppOps;
    public Button mCancelButton;
    public DevicePolicyManager mDPM;
    public EnterpriseDeviceAdminInfo mDeviceAdmin;
    public EnterpriseDeviceManager mEDM;
    public Handler mHandler;
    public LayoutInflater mLayoutInflaternflater;
    public boolean mRefreshing;
    public ImageView mRestrictedIcon;
    public TextView mSupportMessage;
    public Button mUninstallButton;
    public boolean mWaitingForRemoveMsg;
    public final IBinder mToken = new Binder();
    public boolean mAddMsgEllipsized = true;
    public boolean mUninstalling = false;
    public boolean mShowWarningDialog = false;
    public boolean mShowRemoveDialog = false;
    public boolean mIsCalledFromSupportDialog = false;
    public final SecDeviceAdminAdd$$ExternalSyntheticLambda5 restrictedActionClickListener =
            new View.OnClickListener() { // from class:
                // com.samsung.android.settings.applications.specialaccess.SecDeviceAdminAdd$$ExternalSyntheticLambda5
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    RestrictedLockUtils.EnforcedAdmin checkIfRestrictionEnforced;
                    final SecDeviceAdminAdd secDeviceAdminAdd = SecDeviceAdminAdd.this;
                    if (!secDeviceAdminAdd.mActionButton.isEnabled()) {
                        Log.i("SecDeviceAdminAdd", "onClick : Action button is disabled");
                        if (secDeviceAdminAdd.isManagedProfile(secDeviceAdminAdd.mDeviceAdmin)
                                && secDeviceAdminAdd
                                        .mDeviceAdmin
                                        .getComponent()
                                        .equals(secDeviceAdminAdd.mDPM.getProfileOwner())) {
                            ComponentName profileOwnerAsUser =
                                    secDeviceAdminAdd.mDPM.getProfileOwnerAsUser(
                                            secDeviceAdminAdd.getUserId());
                            if (profileOwnerAsUser != null
                                    && secDeviceAdminAdd.mDPM
                                            .isOrganizationOwnedDeviceWithManagedProfile()) {
                                checkIfRestrictionEnforced =
                                        new RestrictedLockUtils.EnforcedAdmin(
                                                profileOwnerAsUser,
                                                "no_remove_managed_profile",
                                                UserHandle.of(secDeviceAdminAdd.getUserId()));
                            } else if (RestrictedLockUtilsInternal.hasBaseUserRestriction(
                                    secDeviceAdminAdd,
                                    UserManager.get(secDeviceAdminAdd)
                                            .getProfileParent(UserHandle.myUserId())
                                            .id,
                                    "no_remove_managed_profile")) {
                                return;
                            } else {
                                checkIfRestrictionEnforced =
                                        RestrictedLockUtilsInternal.checkIfRestrictionEnforced(
                                                secDeviceAdminAdd,
                                                UserManager.get(secDeviceAdminAdd)
                                                        .getProfileParent(UserHandle.myUserId())
                                                        .id,
                                                "no_remove_managed_profile");
                            }
                            if (checkIfRestrictionEnforced != null) {
                                RestrictedLockUtils.sendShowAdminSupportDetailsIntent(
                                        secDeviceAdminAdd, checkIfRestrictionEnforced);
                                return;
                            }
                            return;
                        }
                        return;
                    }
                    if (secDeviceAdminAdd.mAdding) {
                        Log.i("SecDeviceAdminAdd", "onClick : Activate the app");
                        if (Settings.Secure.getInt(
                                        secDeviceAdminAdd
                                                .getApplicationContext()
                                                .getContentResolver(),
                                        "rampart_blocked_device_admin_apps",
                                        0)
                                == 1) {
                            secDeviceAdminAdd.mHandler.post(
                                    new SecDeviceAdminAdd.AnonymousClass3(1, secDeviceAdminAdd));
                            return;
                        } else {
                            secDeviceAdminAdd.addAndFinish();
                            return;
                        }
                    }
                    if (secDeviceAdminAdd.isManagedProfile(secDeviceAdminAdd.mDeviceAdmin)
                            && secDeviceAdminAdd
                                    .mDeviceAdmin
                                    .getComponent()
                                    .equals(secDeviceAdminAdd.mDPM.getProfileOwner())) {
                        Log.i(
                                "SecDeviceAdminAdd",
                                "onClick : Delete all apps and data in this profile");
                        final int myUserId = UserHandle.myUserId();
                        UserDialogs.createRemoveDialog(
                                        secDeviceAdminAdd,
                                        myUserId,
                                        new DialogInterface.OnClickListener() { // from class:
                                            // com.samsung.android.settings.applications.specialaccess.SecDeviceAdminAdd.1
                                            @Override // android.content.DialogInterface.OnClickListener
                                            public final void onClick(
                                                    DialogInterface dialogInterface, int i) {
                                                UserManager.get(SecDeviceAdminAdd.this)
                                                        .removeUser(myUserId);
                                                SecDeviceAdminAdd.this.finish();
                                            }
                                        })
                                .show();
                        secDeviceAdminAdd.mShowRemoveDialog = true;
                        return;
                    }
                    if (secDeviceAdminAdd.mUninstalling) {
                        Log.i("SecDeviceAdminAdd", "onClick : Uninstall the app");
                        secDeviceAdminAdd.mDPM.uninstallPackageWithActiveAdmins(
                                secDeviceAdminAdd.mDeviceAdmin.getPackageName());
                        secDeviceAdminAdd.finish();
                    } else {
                        if (secDeviceAdminAdd.mWaitingForRemoveMsg) {
                            return;
                        }
                        try {
                            ActivityManager.getService().stopAppSwitches();
                        } catch (RemoteException unused) {
                        }
                        secDeviceAdminAdd.mWaitingForRemoveMsg = true;
                        secDeviceAdminAdd.mEDM.getRemoveWarning(
                                secDeviceAdminAdd.mDeviceAdmin.getComponent(),
                                new RemoteCallback(
                                        new RemoteCallback.OnResultListener() { // from class:
                                            // com.samsung.android.settings.applications.specialaccess.SecDeviceAdminAdd.2
                                            public final void onResult(Bundle bundle) {
                                                SecDeviceAdminAdd.this.continueRemoveAction(
                                                        bundle != null
                                                                ? bundle.getCharSequence(
                                                                        "android.app.extra.DISABLE_WARNING")
                                                                : null);
                                            }
                                        },
                                        secDeviceAdminAdd.mHandler));
                        secDeviceAdminAdd
                                .getWindow()
                                .getDecorView()
                                .getHandler()
                                .postDelayed(
                                        new SecDeviceAdminAdd.AnonymousClass3(0, secDeviceAdminAdd),
                                        2000L);
                    }
                }
            };

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.settings.applications.specialaccess.SecDeviceAdminAdd$3, reason: invalid class name */
    public final class AnonymousClass3 implements Runnable {
        public final /* synthetic */ int $r8$classId;
        public final /* synthetic */ Object this$0;

        public /* synthetic */ AnonymousClass3(int i, Object obj) {
            this.$r8$classId = i;
            this.this$0 = obj;
        }

        @Override // java.lang.Runnable
        public final void run() {
            switch (this.$r8$classId) {
                case 0:
                    ((SecDeviceAdminAdd) this.this$0).continueRemoveAction(null);
                    break;
                case 1:
                    SecDeviceAdminAdd secDeviceAdminAdd = (SecDeviceAdminAdd) this.this$0;
                    String string =
                            secDeviceAdminAdd
                                    .getApplicationContext()
                                    .getString(
                                            R.string.sec_device_admin_apps_activation_restricted);
                    int i = SecDeviceAdminAdd.$r8$clinit;
                    Toast toast = new Toast(secDeviceAdminAdd.getApplicationContext());
                    View inflate =
                            secDeviceAdminAdd.mLayoutInflaternflater.inflate(
                                    android.R.layout.calendar_view, (ViewGroup) null, false);
                    ((TextView) inflate.findViewById(android.R.id.message)).setText(string);
                    toast.setDuration(1);
                    toast.setView(inflate);
                    toast.show();
                    ((SecDeviceAdminAdd) this.this$0).detailDialog.dismiss();
                    break;
                default:
                    SecDeviceAdminAdd secDeviceAdminAdd2 = ((AnonymousClass7) this.this$0).this$0;
                    secDeviceAdminAdd2.mDPM.uninstallPackageWithActiveAdmins(
                            secDeviceAdminAdd2.mDeviceAdmin.getPackageName());
                    break;
            }
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.settings.applications.specialaccess.SecDeviceAdminAdd$4, reason: invalid class name */
    public final class AnonymousClass4 implements DialogInterface.OnDismissListener {
        public final /* synthetic */ int $r8$classId;
        public final /* synthetic */ SecDeviceAdminAdd this$0;

        public /* synthetic */ AnonymousClass4(SecDeviceAdminAdd secDeviceAdminAdd, int i) {
            this.$r8$classId = i;
            this.this$0 = secDeviceAdminAdd;
        }

        @Override // android.content.DialogInterface.OnDismissListener
        public final void onDismiss(DialogInterface dialogInterface) {
            switch (this.$r8$classId) {
                case 0:
                    this.this$0.finish();
                    break;
                case 1:
                    this.this$0.finish();
                    break;
                default:
                    SecDeviceAdminAdd secDeviceAdminAdd = this.this$0;
                    if (!secDeviceAdminAdd.mShowWarningDialog
                            && !secDeviceAdminAdd.mShowRemoveDialog) {
                        secDeviceAdminAdd.finish();
                        break;
                    }
                    break;
            }
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.settings.applications.specialaccess.SecDeviceAdminAdd$5, reason: invalid class name */
    public final class AnonymousClass5 implements DialogInterface.OnClickListener {
        public final /* synthetic */ int $r8$classId;
        public final /* synthetic */ SecDeviceAdminAdd this$0;

        public /* synthetic */ AnonymousClass5(SecDeviceAdminAdd secDeviceAdminAdd, int i) {
            this.$r8$classId = i;
            this.this$0 = secDeviceAdminAdd;
        }

        @Override // android.content.DialogInterface.OnClickListener
        public final void onClick(DialogInterface dialogInterface, int i) {
            int i2 = 1;
            switch (this.$r8$classId) {
                case 0:
                    SecDeviceAdminAdd secDeviceAdminAdd = this.this$0;
                    int i3 = SecDeviceAdminAdd.$r8$clinit;
                    if (Settings.Secure.getInt(
                                    secDeviceAdminAdd.getApplicationContext().getContentResolver(),
                                    "rampart_blocked_device_admin_apps",
                                    0)
                            != 1) {
                        this.this$0.addAndFinish();
                        break;
                    } else {
                        secDeviceAdminAdd.mHandler.post(new AnonymousClass3(i2, secDeviceAdminAdd));
                        break;
                    }
                case 1:
                    this.this$0.finish();
                    break;
                default:
                    try {
                        ActivityManager.getService().resumeAppSwitches();
                    } catch (RemoteException unused) {
                    }
                    SecDeviceAdminAdd secDeviceAdminAdd2 = this.this$0;
                    secDeviceAdminAdd2.mEDM.removeActiveAdmin(
                            secDeviceAdminAdd2.mDeviceAdmin.getComponent());
                    this.this$0.finish();
                    break;
            }
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.settings.applications.specialaccess.SecDeviceAdminAdd$7, reason: invalid class name */
    public final class AnonymousClass7 implements View.OnClickListener {
        public final /* synthetic */ int $r8$classId;
        public final /* synthetic */ SecDeviceAdminAdd this$0;

        public /* synthetic */ AnonymousClass7(SecDeviceAdminAdd secDeviceAdminAdd, int i) {
            this.$r8$classId = i;
            this.this$0 = secDeviceAdminAdd;
        }

        @Override // android.view.View.OnClickListener
        public final void onClick(View view) {
            switch (this.$r8$classId) {
                case 0:
                    SecDeviceAdminAdd secDeviceAdminAdd = this.this$0;
                    secDeviceAdminAdd.toggleMessageEllipsis(secDeviceAdminAdd.mAddMsg);
                    break;
                case 1:
                    EventLog.writeEvent(
                            90203, this.this$0.mDeviceAdmin.getActivityInfo().applicationInfo.uid);
                    AlertDialog alertDialog = this.this$0.detailDialog;
                    if (alertDialog != null) {
                        alertDialog.dismiss();
                    }
                    new Handler().postDelayed(new AnonymousClass3(2, this), 200L);
                    break;
                default:
                    EventLog.writeEvent(
                            90202, this.this$0.mDeviceAdmin.getActivityInfo().applicationInfo.uid);
                    AlertDialog alertDialog2 = this.this$0.detailDialog;
                    if (alertDialog2 != null) {
                        alertDialog2.dismiss();
                        break;
                    }
                    break;
            }
        }
    }

    public static void logSpecialPermissionChange(boolean z, String str) {
        int i = z ? 766 : 767;
        FeatureFactoryImpl featureFactoryImpl = FeatureFactoryImpl._factory;
        if (featureFactoryImpl == null) {
            throw new UnsupportedOperationException("No feature factory configured");
        }
        featureFactoryImpl.getMetricsFeatureProvider().action(FileType.SCC_SCRAP, i, 0, 0, str);
    }

    public final void addAndFinish() {
        try {
            logSpecialPermissionChange(true, this.mDeviceAdmin.getComponent().getPackageName());
            this.mEDM.setActiveAdmin(this.mDeviceAdmin.getComponent(), this.mRefreshing);
            EventLog.writeEvent(90201, this.mDeviceAdmin.getActivityInfo().applicationInfo.uid);
            insertListEventLogging(true);
            BatteryUtils.getInstance(this)
                    .clearForceAppStandby(this.mDeviceAdmin.getComponent().getPackageName());
            setResult(-1);
        } catch (RuntimeException e) {
            Log.w(
                    "SecDeviceAdminAdd",
                    "Exception trying to activate admin " + this.mDeviceAdmin.getComponent(),
                    e);
            if (this.mDPM.isAdminActive(this.mDeviceAdmin.getComponent())) {
                setResult(-1);
            }
        }
        if (this.mAddingProfileOwner) {
            try {
                this.mDPM.setProfileOwner(this.mDeviceAdmin.getComponent(), UserHandle.myUserId());
            } catch (RuntimeException unused) {
                setResult(0);
            }
        }
        finish();
    }

    public final void addDeviceAdminPolicies(boolean z) {
        if (this.mAdminPoliciesInitialized) {
            return;
        }
        boolean isAdminUser = UserManager.get(this).isAdminUser();
        Iterator<EnterpriseDeviceAdminInfo.PolicyInfo> it =
                this.mDeviceAdmin.getUsedPolicies().iterator();
        while (it.hasNext()) {
            EnterpriseDeviceAdminInfo.PolicyInfo next = it.next();
            int i = isAdminUser ? next.description : next.descriptionForSecondaryUsers;
            CharSequence text = getText(isAdminUser ? next.label : next.labelForSecondaryUsers);
            CharSequence text2 = z ? getText(i) : ApnSettings.MVNO_NONE;
            View inflate =
                    this.mLayoutInflaternflater.inflate(
                            R.layout.sec_app_permission_item, (ViewGroup) null);
            TextView textView = (TextView) inflate.findViewById(R.id.permission_group);
            TextView textView2 = (TextView) inflate.findViewById(R.id.permission_list);
            if (text != null) {
                textView.setText(text);
                if (text2 == ApnSettings.MVNO_NONE) {
                    textView2.setVisibility(8);
                } else {
                    textView2.setText(text2);
                }
            } else {
                textView.setText(text2);
                textView2.setVisibility(8);
            }
            this.mAdminPolicies.addView(inflate);
        }
        this.mAdminPoliciesInitialized = true;
    }

    public final void continueRemoveAction(CharSequence charSequence) {
        if (this.mWaitingForRemoveMsg) {
            this.mWaitingForRemoveMsg = false;
            if (charSequence == null) {
                try {
                    ActivityManager.getService().resumeAppSwitches();
                } catch (RemoteException unused) {
                }
                logSpecialPermissionChange(
                        false, this.mDeviceAdmin.getComponent().getPackageName());
                insertListEventLogging(false);
                this.mEDM.removeActiveAdmin(this.mDeviceAdmin.getComponent());
                finish();
                return;
            }
            try {
                ActivityManager.getService().stopAppSwitches();
            } catch (RemoteException unused2) {
            }
            Bundle bundle = new Bundle();
            bundle.putCharSequence("android.app.extra.DISABLE_WARNING", charSequence);
            if (isFinishing()) {
                return;
            }
            this.mShowWarningDialog = true;
            showDialog(1, bundle);
        }
    }

    public final void insertListEventLogging(boolean z) {
        HashMap hashMap = new HashMap();
        hashMap.put("pkgname", this.mDeviceAdmin.getComponent().getPackageName());
        hashMap.put("newValue", z ? "On" : "Off");
        SALogging.insertSALog(
                Integer.toString(FileType.SCC_SCRAP), Integer.toString(3912), hashMap, 0);
    }

    public final boolean isManagedProfile(EnterpriseDeviceAdminInfo enterpriseDeviceAdminInfo) {
        UserInfo userInfo =
                UserManager.get(this)
                        .getUserInfo(
                                UserHandle.getUserId(
                                        enterpriseDeviceAdminInfo.getActivityInfo()
                                                .applicationInfo
                                                .uid));
        if (userInfo != null) {
            return userInfo.isManagedProfile();
        }
        return false;
    }

    /* JADX WARN: Code restructure failed: missing block: B:50:0x0177, code lost:

       r10.activityInfo = r7;
       new android.app.admin.DeviceAdminInfo(r13, r10);
    */
    @Override // android.app.Activity
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void onCreate(android.os.Bundle r14) {
        /*
            Method dump skipped, instructions count: 1156
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.samsung.android.settings.applications.specialaccess.SecDeviceAdminAdd.onCreate(android.os.Bundle):void");
    }

    @Override // android.app.Activity
    public final Dialog onCreateDialog(int i, Bundle bundle) {
        if (i != 1) {
            return super.onCreateDialog(i, bundle);
        }
        CharSequence charSequence = bundle.getCharSequence("android.app.extra.DISABLE_WARNING");
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        AlertController.AlertParams alertParams = builder.P;
        alertParams.mMessage = charSequence;
        builder.setPositiveButton(R.string.dlg_ok, new AnonymousClass5(this, 2));
        builder.setNegativeButton(R.string.dlg_cancel, new AnonymousClass5(this, 1));
        alertParams.mOnDismissListener = new AnonymousClass4(this, 1);
        return builder.create();
    }

    @Override // android.app.Activity
    public final void onPause() {
        super.onPause();
        this.mActionButton.setEnabled(false);
        this.mAppOps.setUserRestriction(24, false, this.mToken);
        this.mAppOps.setUserRestriction(45, false, this.mToken);
        try {
            ActivityManager.getService().resumeAppSwitches();
        } catch (RemoteException unused) {
        }
    }

    @Override // android.app.Activity
    public final void onResume() {
        super.onResume();
        this.mActionButton.setEnabled(true);
        if (!this.mAddingProfileOwner) {
            this.mRestrictedIcon.setVisibility(8);
            this.mAdminIcon.setImageDrawable(this.mDeviceAdmin.loadIcon(getPackageManager()));
            this.mAdminName.setText(this.mDeviceAdmin.loadLabel(getPackageManager()));
            try {
                this.mAdminDescription.setText(
                        this.mDeviceAdmin.loadDescription(getPackageManager()));
                this.mAdminDescription.setVisibility(0);
            } catch (Resources.NotFoundException unused) {
                this.mAdminDescription.setVisibility(8);
            }
            if (TextUtils.isEmpty(this.mAddMsgText)) {
                this.mAddMsg.setVisibility(8);
                this.mAddMsgExpander.setVisibility(8);
            } else {
                this.mAddMsg.setText(this.mAddMsgText);
                this.mAddMsg.setVisibility(0);
            }
            if (this.mRefreshing
                    || this.mAddingProfileOwner
                    || !this.mDPM.isAdminActive(this.mDeviceAdmin.getComponent())) {
                addDeviceAdminPolicies(true);
                CharSequence loadLabel =
                        this.mDeviceAdmin
                                .getActivityInfo()
                                .applicationInfo
                                .loadLabel(getPackageManager());
                this.mAdminWarning.setText(
                        this.mDPM
                                .getResources()
                                .getString(
                                        "Settings.NEW_DEVICE_ADMIN_WARNING",
                                        new SecDeviceAdminAdd$$ExternalSyntheticLambda1(
                                                this, loadLabel, 2),
                                        loadLabel));
                setTitle(
                        this.mDPM
                                .getResources()
                                .getString(
                                        "Settings.ACTIVATE_DEVICE_ADMIN_APP",
                                        new SecDeviceAdminAdd$$ExternalSyntheticLambda0(this, 5)));
                this.mActionButton.setText(
                        this.mDPM
                                .getResources()
                                .getString(
                                        "Settings.ACTIVATE_THIS_DEVICE_ADMIN_APP",
                                        new SecDeviceAdminAdd$$ExternalSyntheticLambda0(this, 9)));
                if (!this.mDeviceAdmin.getActivityInfo().applicationInfo.isSystemApp()
                        && !this.mDeviceAdmin.getComponent().equals(this.mDPM.getProfileOwner())) {
                    this.mUninstallButton.setVisibility(0);
                    View findViewById = this.detailDialog.findViewById(R.id.divider);
                    if (findViewById != null) {
                        findViewById.setVisibility(0);
                    }
                }
                this.mSupportMessage.setVisibility(8);
                this.mAdding = true;
            } else {
                this.mAdding = false;
                if (this.mEDM.getAdminRemovable(this.mDeviceAdmin.getPackageName())) {
                    this.mActionButton.setEnabled(true);
                } else {
                    this.mActionButton.setEnabled(false);
                }
                boolean equals =
                        this.mDeviceAdmin.getComponent().equals(this.mDPM.getProfileOwner());
                boolean isManagedProfile = isManagedProfile(this.mDeviceAdmin);
                if (equals && isManagedProfile) {
                    this.mAdminWarning.setText(
                            this.mDPM
                                    .getResources()
                                    .getString(
                                            "Settings.WORK_PROFILE_ADMIN_POLICIES_WARNING",
                                            new SecDeviceAdminAdd$$ExternalSyntheticLambda0(
                                                    this, 8)));
                    this.mActionButton.setText(
                            this.mDPM
                                    .getResources()
                                    .getString(
                                            "Settings.REMOVE_WORK_PROFILE",
                                            new SecDeviceAdminAdd$$ExternalSyntheticLambda0(
                                                    this, 10)));
                    RestrictedLockUtils.EnforcedAdmin checkIfRestrictionEnforced =
                            RestrictedLockUtilsInternal.checkIfRestrictionEnforced(
                                    this,
                                    UserManager.get(this)
                                            .getProfileParent(UserHandle.myUserId())
                                            .id,
                                    "no_remove_managed_profile");
                    boolean hasBaseUserRestriction =
                            RestrictedLockUtilsInternal.hasBaseUserRestriction(
                                    this,
                                    UserManager.get(this)
                                            .getProfileParent(UserHandle.myUserId())
                                            .id,
                                    "no_remove_managed_profile");
                    if ((hasBaseUserRestriction
                                    && this.mDPM.isOrganizationOwnedDeviceWithManagedProfile())
                            || (checkIfRestrictionEnforced != null && !hasBaseUserRestriction)) {
                        this.mRestrictedIcon.setVisibility(0);
                    }
                    this.mActionButton.setEnabled(
                            checkIfRestrictionEnforced == null && !hasBaseUserRestriction);
                } else if (equals
                        || this.mDeviceAdmin
                                .getComponent()
                                .equals(this.mDPM.getDeviceOwnerComponentOnCallingUser())) {
                    if (equals) {
                        this.mAdminWarning.setText(
                                this.mDPM
                                        .getResources()
                                        .getString(
                                                "Settings.USER_ADMIN_POLICIES_WARNING",
                                                new SecDeviceAdminAdd$$ExternalSyntheticLambda0(
                                                        this, 11)));
                    } else {
                        if (this.mDPM.isDeviceManaged()) {
                            DevicePolicyManager devicePolicyManager = this.mDPM;
                            if (devicePolicyManager.getDeviceOwnerType(
                                            devicePolicyManager.getDeviceOwnerComponentOnAnyUser())
                                    == 1) {
                                this.mAdminWarning.setText(R.string.admin_financed_message);
                            }
                        }
                        this.mAdminWarning.setText(
                                this.mDPM
                                        .getResources()
                                        .getString(
                                                "Settings.DEVICE_ADMIN_POLICIES_WARNING",
                                                new SecDeviceAdminAdd$$ExternalSyntheticLambda0(
                                                        this, 1)));
                    }
                    this.mActionButton.setText(
                            this.mDPM
                                    .getResources()
                                    .getString(
                                            "Settings.REMOVE_DEVICE_ADMIN",
                                            new SecDeviceAdminAdd$$ExternalSyntheticLambda0(
                                                    this, 2)));
                    this.mActionButton.setEnabled(false);
                } else {
                    addDeviceAdminPolicies(false);
                    CharSequence loadLabel2 =
                            this.mDeviceAdmin
                                    .getActivityInfo()
                                    .applicationInfo
                                    .loadLabel(getPackageManager());
                    this.mAdminWarning.setText(
                            this.mDPM
                                    .getResources()
                                    .getString(
                                            "Settings.ACTIVE_DEVICE_ADMIN_WARNING",
                                            new SecDeviceAdminAdd$$ExternalSyntheticLambda1(
                                                    this, loadLabel2, 1),
                                            loadLabel2));
                    setTitle(R.string.active_device_admin_msg);
                    if (this.mUninstalling) {
                        this.mActionButton.setText(
                                this.mDPM
                                        .getResources()
                                        .getString(
                                                "Settings.REMOVE_AND_UNINSTALL_DEVICE_ADMIN",
                                                new SecDeviceAdminAdd$$ExternalSyntheticLambda0(
                                                        this, 3)));
                    } else {
                        this.mActionButton.setText(
                                this.mDPM
                                        .getResources()
                                        .getString(
                                                "Settings.REMOVE_DEVICE_ADMIN",
                                                new SecDeviceAdminAdd$$ExternalSyntheticLambda0(
                                                        this, 4)));
                    }
                    if ("com.sprint.ms.cdm".equals(this.mDeviceAdmin.getPackageName())) {
                        this.mActionButton.setEnabled(false);
                    }
                }
                CharSequence longSupportMessageForUser =
                        this.mDPM.getLongSupportMessageForUser(
                                this.mDeviceAdmin.getComponent(), UserHandle.myUserId());
                if (TextUtils.isEmpty(longSupportMessageForUser)) {
                    this.mSupportMessage.setVisibility(8);
                } else {
                    this.mSupportMessage.setText(longSupportMessageForUser);
                    this.mSupportMessage.setVisibility(0);
                }
            }
        }
        this.mAppOps.setUserRestriction(24, true, this.mToken);
        this.mAppOps.setUserRestriction(45, true, this.mToken);
    }

    @Override // android.app.Activity
    public final void onUserLeaveHint() {
        super.onUserLeaveHint();
        if (this.mIsCalledFromSupportDialog) {
            finish();
        }
    }

    public final void toggleMessageEllipsis(TextView textView) {
        int i;
        boolean z = !this.mAddMsgEllipsized;
        this.mAddMsgEllipsized = z;
        textView.setEllipsize(z ? TextUtils.TruncateAt.END : null);
        if (this.mAddMsgEllipsized) {
            Display defaultDisplay =
                    ((WindowManager) getSystemService("window")).getDefaultDisplay();
            i = defaultDisplay.getHeight() > defaultDisplay.getWidth() ? 5 : 2;
        } else {
            i = 15;
        }
        textView.setMaxLines(i);
        this.mAddMsgExpander.setImageResource(
                this.mAddMsgEllipsized
                        ? android.R.drawable.ft_avd_toarrow_animation
                        : android.R.drawable.ft_avd_toarrow);
    }
}
