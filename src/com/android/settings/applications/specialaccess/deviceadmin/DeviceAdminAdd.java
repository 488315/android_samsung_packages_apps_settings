package com.android.settings.applications.specialaccess.deviceadmin;

import android.R;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.AppOpsManager;
import android.app.Dialog;
import android.app.admin.DevicePolicyManager;
import android.content.DialogInterface;
import android.content.pm.UserInfo;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Binder;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.RemoteException;
import android.os.UserHandle;
import android.os.UserManager;
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

import androidx.appcompat.app.AlertDialog;
import androidx.core.widget.NestedScrollView;

import com.android.settings.fuelgauge.BatteryUtils;
import com.android.settings.overlay.FeatureFactoryImpl;
import com.android.settingslib.RestrictedLockUtils;
import com.android.settingslib.RestrictedLockUtilsInternal;

import com.samsung.android.knox.EnterpriseDeviceAdminInfo;
import com.samsung.android.knox.EnterpriseDeviceManager;
import com.samsung.android.knox.net.apn.ApnSettings;

import java.util.Iterator;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class DeviceAdminAdd extends Activity {
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
    public NestedScrollView mScrollView;
    public TextView mSupportMessage;
    public Button mUninstallButton;
    public boolean mWaitingForRemoveMsg;
    public final IBinder mToken = new Binder();
    public boolean mAddMsgEllipsized = true;
    public boolean mUninstalling = false;
    public boolean mIsCalledFromSupportDialog = false;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.android.settings.applications.specialaccess.deviceadmin.DeviceAdminAdd$2, reason: invalid class name */
    public final class AnonymousClass2 implements DialogInterface.OnClickListener {
        public final /* synthetic */ int $r8$classId;
        public final /* synthetic */ DeviceAdminAdd this$0;

        public /* synthetic */ AnonymousClass2(DeviceAdminAdd deviceAdminAdd, int i) {
            this.$r8$classId = i;
            this.this$0 = deviceAdminAdd;
        }

        @Override // android.content.DialogInterface.OnClickListener
        public final void onClick(DialogInterface dialogInterface, int i) {
            switch (this.$r8$classId) {
                case 0:
                    this.this$0.addAndFinish();
                    break;
                default:
                    try {
                        ActivityManager.getService().resumeAppSwitches();
                    } catch (RemoteException unused) {
                    }
                    DeviceAdminAdd deviceAdminAdd = this.this$0;
                    deviceAdminAdd.mEDM.removeActiveAdmin(
                            deviceAdminAdd.mDeviceAdmin.getComponent());
                    this.this$0.finish();
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
        featureFactoryImpl.getMetricsFeatureProvider().action(0, i, 0, 0, str);
    }

    public final void addAndFinish() {
        try {
            logSpecialPermissionChange(true, this.mDeviceAdmin.getComponent().getPackageName());
            this.mEDM.setActiveAdmin(this.mDeviceAdmin.getComponent(), this.mRefreshing);
            EventLog.writeEvent(90201, this.mDeviceAdmin.getActivityInfo().applicationInfo.uid);
            BatteryUtils.getInstance(this)
                    .clearForceAppStandby(this.mDeviceAdmin.getComponent().getPackageName());
            setResult(-1);
        } catch (RuntimeException e) {
            Log.w(
                    "DeviceAdminAdd",
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
            Drawable drawable = getDrawable(R.drawable.jog_dial_arrow_short_right);
            View inflate =
                    this.mLayoutInflaternflater.inflate(
                            com.android.settings.R.layout.app_permission_item, (ViewGroup) null);
            TextView textView =
                    (TextView) inflate.findViewById(com.android.settings.R.id.permission_group);
            TextView textView2 =
                    (TextView) inflate.findViewById(com.android.settings.R.id.permission_list);
            ((ImageView) inflate.findViewById(com.android.settings.R.id.perm_icon))
                    .setImageDrawable(drawable);
            if (text != null) {
                textView.setText(text);
                textView2.setText(text2);
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
            showDialog(1, bundle);
        }
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

    /* JADX WARN: Code restructure failed: missing block: B:49:0x0176, code lost:

       r9.activityInfo = r2;
       new android.app.admin.DeviceAdminInfo(r12, r9);
    */
    @Override // android.app.Activity
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void onCreate(android.os.Bundle r13) {
        /*
            Method dump skipped, instructions count: 1112
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.settings.applications.specialaccess.deviceadmin.DeviceAdminAdd.onCreate(android.os.Bundle):void");
    }

    @Override // android.app.Activity
    public final Dialog onCreateDialog(int i, Bundle bundle) {
        if (i != 1) {
            return super.onCreateDialog(i, bundle);
        }
        CharSequence charSequence = bundle.getCharSequence("android.app.extra.DISABLE_WARNING");
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.P.mMessage = charSequence;
        builder.setPositiveButton(
                com.android.settings.R.string.dlg_ok, new AnonymousClass2(this, 1));
        builder.setNegativeButton(
                com.android.settings.R.string.dlg_cancel, (DialogInterface.OnClickListener) null);
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
            findViewById(com.android.settings.R.id.restricted_icon).setVisibility(8);
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
                                        new DeviceAdminAdd$$ExternalSyntheticLambda1(
                                                this, loadLabel, 2),
                                        loadLabel));
                setTitle(
                        this.mDPM
                                .getResources()
                                .getString(
                                        "Settings.ACTIVATE_DEVICE_ADMIN_APP",
                                        new DeviceAdminAdd$$ExternalSyntheticLambda0(this, 5)));
                this.mActionButton.setText(
                        this.mDPM
                                .getResources()
                                .getString(
                                        "Settings.ACTIVATE_THIS_DEVICE_ADMIN_APP",
                                        new DeviceAdminAdd$$ExternalSyntheticLambda0(this, 9)));
                if (!this.mDeviceAdmin.getActivityInfo().applicationInfo.isSystemApp()
                        && !this.mDeviceAdmin.getComponent().equals(this.mDPM.getProfileOwner())) {
                    this.mUninstallButton.setVisibility(0);
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
                                            new DeviceAdminAdd$$ExternalSyntheticLambda0(this, 8)));
                    this.mActionButton.setText(
                            this.mDPM
                                    .getResources()
                                    .getString(
                                            "Settings.REMOVE_WORK_PROFILE",
                                            new DeviceAdminAdd$$ExternalSyntheticLambda0(
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
                        findViewById(com.android.settings.R.id.restricted_icon).setVisibility(0);
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
                                                new DeviceAdminAdd$$ExternalSyntheticLambda0(
                                                        this, 11)));
                    } else {
                        if (this.mDPM.isDeviceManaged()) {
                            DevicePolicyManager devicePolicyManager = this.mDPM;
                            if (devicePolicyManager.getDeviceOwnerType(
                                            devicePolicyManager.getDeviceOwnerComponentOnAnyUser())
                                    == 1) {
                                this.mAdminWarning.setText(
                                        com.android.settings.R.string.admin_financed_message);
                            }
                        }
                        this.mAdminWarning.setText(
                                this.mDPM
                                        .getResources()
                                        .getString(
                                                "Settings.DEVICE_ADMIN_POLICIES_WARNING",
                                                new DeviceAdminAdd$$ExternalSyntheticLambda0(
                                                        this, 1)));
                    }
                    this.mActionButton.setText(
                            this.mDPM
                                    .getResources()
                                    .getString(
                                            "Settings.REMOVE_DEVICE_ADMIN",
                                            new DeviceAdminAdd$$ExternalSyntheticLambda0(this, 2)));
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
                                            new DeviceAdminAdd$$ExternalSyntheticLambda1(
                                                    this, loadLabel2, 1),
                                            loadLabel2));
                    setTitle(com.android.settings.R.string.active_device_admin_msg);
                    if (this.mUninstalling) {
                        this.mActionButton.setText(
                                this.mDPM
                                        .getResources()
                                        .getString(
                                                "Settings.REMOVE_AND_UNINSTALL_DEVICE_ADMIN",
                                                new DeviceAdminAdd$$ExternalSyntheticLambda0(
                                                        this, 3)));
                    } else {
                        this.mActionButton.setText(
                                this.mDPM
                                        .getResources()
                                        .getString(
                                                "Settings.REMOVE_DEVICE_ADMIN",
                                                new DeviceAdminAdd$$ExternalSyntheticLambda0(
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

    public final void toggleMessageEllipsis(View view) {
        int i;
        TextView textView = (TextView) view;
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
                        ? R.drawable.ft_avd_toarrow_animation
                        : R.drawable.ft_avd_toarrow);
    }
}
