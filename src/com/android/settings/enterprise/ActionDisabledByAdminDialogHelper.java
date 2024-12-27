package com.android.settings.enterprise;

import android.app.Activity;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.hardware.biometrics.ParentalControlsUtilsInternal;
import android.os.UserHandle;
import android.provider.DeviceConfig;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import com.android.internal.util.Preconditions;
import com.android.settings.R;
import com.android.settingslib.RestrictedLockUtils;
import com.android.settingslib.RestrictedLockUtilsInternal;
import com.android.settingslib.Utils;
import com.android.settingslib.enterprise.BaseActionDisabledByAdminController;
import com.android.settingslib.enterprise.BiometricActionDisabledByAdminController;
import com.android.settingslib.enterprise.FinancedDeviceActionDisabledByAdminController;
import com.android.settingslib.enterprise.ManagedDeviceActionDisabledByAdminController;
import com.android.settingslib.enterprise.SupervisedDeviceActionDisabledByAdminController;

import com.samsung.android.knox.restriction.RestrictionPolicy;
import com.samsung.android.settings.knox.KnoxUtils;

import java.util.Objects;
import java.util.function.Supplier;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class ActionDisabledByAdminDialogHelper {
    public final BaseActionDisabledByAdminController mActionDisabledByAdminController;
    public final Activity mActivity;
    public final ViewGroup mDialogView;
    RestrictedLockUtils.EnforcedAdmin mEnforcedAdmin;
    public String mRestriction;

    public ActionDisabledByAdminDialogHelper(Activity activity, String str) {
        BaseActionDisabledByAdminController managedDeviceActionDisabledByAdminController;
        this.mActivity = activity;
        ViewGroup viewGroup =
                (ViewGroup)
                        LayoutInflater.from(activity)
                                .inflate(R.layout.support_details_dialog, (ViewGroup) null);
        this.mDialogView = viewGroup;
        DeviceAdminStringProviderImpl deviceAdminStringProviderImpl =
                new DeviceAdminStringProviderImpl(activity);
        UserHandle userHandle = UserHandle.SYSTEM;
        if (!TextUtils.equals("disallow_biometric", str)
                ? false
                : ParentalControlsUtilsInternal.parentConsentRequired(
                        activity,
                        (DevicePolicyManager) activity.getSystemService(DevicePolicyManager.class),
                        270,
                        new UserHandle(UserHandle.myUserId()))) {
            managedDeviceActionDisabledByAdminController =
                    new BiometricActionDisabledByAdminController(deviceAdminStringProviderImpl);
        } else {
            DevicePolicyManager devicePolicyManager =
                    (DevicePolicyManager) activity.getSystemService(DevicePolicyManager.class);
            if (DeviceConfig.getBoolean("device_policy_manager", "add-isfinanced-device", true)
                    ? devicePolicyManager.isFinancedDevice()
                    : devicePolicyManager.isDeviceManaged()
                            && devicePolicyManager.getDeviceOwnerType(
                                            devicePolicyManager.getDeviceOwnerComponentOnAnyUser())
                                    == 1) {
                managedDeviceActionDisabledByAdminController =
                        new FinancedDeviceActionDisabledByAdminController(
                                deviceAdminStringProviderImpl);
            } else {
                if (((DevicePolicyManager) activity.getSystemService(DevicePolicyManager.class))
                                .getProfileOwnerOrDeviceOwnerSupervisionComponent(
                                        new UserHandle(UserHandle.myUserId()))
                        != null) {
                    managedDeviceActionDisabledByAdminController =
                            new SupervisedDeviceActionDisabledByAdminController(
                                    deviceAdminStringProviderImpl, str);
                } else {
                    managedDeviceActionDisabledByAdminController =
                            new ManagedDeviceActionDisabledByAdminController(
                                    deviceAdminStringProviderImpl);
                    Objects.requireNonNull(userHandle);
                }
            }
        }
        this.mActionDisabledByAdminController = managedDeviceActionDisabledByAdminController;
        ((TextView) viewGroup.findViewById(R.id.admin_support_dialog_title))
                .setText(
                        ((DevicePolicyManager) activity.getSystemService(DevicePolicyManager.class))
                                .getResources()
                                .getString(
                                        "Settings.DISABLED_BY_IT_ADMIN_TITLE",
                                        new Supplier() { // from class:
                                                         // com.android.settings.enterprise.ActionDisabledByAdminDialogHelper$$ExternalSyntheticLambda0
                                            @Override // java.util.function.Supplier
                                            public final Object get() {
                                                return ActionDisabledByAdminDialogHelper.this
                                                        .mActivity.getString(
                                                        R.string.disabled_by_policy_title);
                                            }
                                        }));
    }

    public static int getEnforcementAdminUserId(RestrictedLockUtils.EnforcedAdmin enforcedAdmin) {
        UserHandle userHandle = enforcedAdmin.user;
        if (userHandle == null) {
            return -10000;
        }
        return userHandle.getIdentifier();
    }

    public final void initializeDialogViews(
            View view, RestrictedLockUtils.EnforcedAdmin enforcedAdmin, int i, String str) {
        ComponentName componentName = enforcedAdmin.component;
        if (componentName == null) {
            return;
        }
        BaseActionDisabledByAdminController baseActionDisabledByAdminController =
                this.mActionDisabledByAdminController;
        Preconditions.checkState(
                baseActionDisabledByAdminController.mLauncher != null,
                "must call initialize() first");
        baseActionDisabledByAdminController.mEnforcementAdminUserId = i;
        baseActionDisabledByAdminController.mEnforcedAdmin = enforcedAdmin;
        setAdminSupportIcon(view, componentName, i);
        Activity activity = this.mActivity;
        if (!RestrictedLockUtilsInternal.isAdminInCurrentUserOrProfile(activity, componentName)
                || !RestrictedLockUtils.isCurrentUserOrProfile(activity, i)) {
            String packageName = componentName.getPackageName();
            String str2 = KnoxUtils.mDeviceType;
            if (!packageName.equals(RestrictionPolicy.KC_COMPONENT_NAME.getPackageName())) {
                componentName = null;
            }
        }
        setAdminSupportTitle(view, str);
        setAdminSupportDetails(
                activity,
                view,
                new RestrictedLockUtils.EnforcedAdmin(
                        componentName, i != -10000 ? UserHandle.of(i) : null));
    }

    public final AlertDialog.Builder prepareDialogBuilder(
            String str, RestrictedLockUtils.EnforcedAdmin enforcedAdmin) {
        BaseActionDisabledByAdminController baseActionDisabledByAdminController =
                this.mActionDisabledByAdminController;
        Activity activity = this.mActivity;
        DialogInterface.OnClickListener positiveButtonListener =
                baseActionDisabledByAdminController.getPositiveButtonListener(
                        activity, enforcedAdmin);
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setPositiveButton(
                positiveButtonListener == null ? R.string.suggestion_button_close : R.string.okay,
                positiveButtonListener);
        builder.setView(this.mDialogView);
        prepareDialogBuilder(builder, str, enforcedAdmin);
        return builder;
    }

    /* JADX WARN: Removed duplicated region for block: B:16:0x0050  */
    /* JADX WARN: Removed duplicated region for block: B:32:0x00a6  */
    /* JADX WARN: Removed duplicated region for block: B:35:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void setAdminSupportDetails(
            android.app.Activity r4,
            android.view.View r5,
            com.android.settingslib.RestrictedLockUtils.EnforcedAdmin r6) {
        /*
            r3 = this;
            if (r6 == 0) goto La9
            android.content.ComponentName r0 = r6.component
            if (r0 != 0) goto L8
            goto La9
        L8:
            java.lang.String r0 = "device_policy"
            java.lang.Object r0 = r4.getSystemService(r0)
            android.app.admin.DevicePolicyManager r0 = (android.app.admin.DevicePolicyManager) r0
            android.content.ComponentName r1 = r6.component
            boolean r1 = com.android.settingslib.RestrictedLockUtilsInternal.isAdminInCurrentUserOrProfile(r4, r1)
            r2 = 0
            if (r1 == 0) goto L49
            int r1 = getEnforcementAdminUserId(r6)
            boolean r4 = com.android.settingslib.RestrictedLockUtils.isCurrentUserOrProfile(r4, r1)
            if (r4 != 0) goto L24
            goto L49
        L24:
            android.os.UserHandle r4 = r6.user
            if (r4 != 0) goto L32
            int r4 = android.os.UserHandle.myUserId()
            android.os.UserHandle r4 = android.os.UserHandle.of(r4)
            r6.user = r4
        L32:
            int r4 = android.os.Process.myUid()
            r1 = 1000(0x3e8, float:1.401E-42)
            boolean r4 = android.os.UserHandle.isSameApp(r4, r1)
            if (r4 == 0) goto L4b
            android.content.ComponentName r4 = r6.component
            int r1 = getEnforcementAdminUserId(r6)
            java.lang.CharSequence r4 = r0.getShortSupportMessageForUser(r4, r1)
            goto L4c
        L49:
            r6.component = r2
        L4b:
            r4 = r2
        L4c:
            android.content.ComponentName r6 = r6.component
            if (r6 == 0) goto L9b
            java.lang.String r6 = r6.getPackageName()
            java.lang.String r0 = com.samsung.android.settings.knox.KnoxUtils.mDeviceType
            android.content.ComponentName r0 = com.samsung.android.knox.restriction.RestrictionPolicy.KC_COMPONENT_NAME
            java.lang.String r0 = r0.getPackageName()
            boolean r6 = r6.equals(r0)
            com.android.settingslib.enterprise.BaseActionDisabledByAdminController r0 = r3.mActionDisabledByAdminController
            if (r6 == 0) goto L96
            java.lang.CharSequence r4 = r0.getAdminSupportContentString(r4)
            java.lang.String r4 = r4.toString()
            android.app.Activity r3 = r3.mActivity
            if (r3 == 0) goto L7e
            android.content.Context r3 = r3.getApplicationContext()
            com.samsung.android.knox.EnterpriseDeviceManager r3 = com.samsung.android.knox.EnterpriseDeviceManager.getInstance(r3)
            if (r3 == 0) goto L7e
            com.samsung.android.knox.restriction.RestrictionPolicy r2 = r3.getRestrictionPolicy()
        L7e:
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            if (r2 == 0) goto L8d
            java.lang.String r4 = r2.getKcActionDisabledText()
            r3.append(r4)
            goto L90
        L8d:
            r3.append(r4)
        L90:
            java.lang.String r3 = r3.toString()
        L94:
            r2 = r3
            goto L9b
        L96:
            java.lang.CharSequence r3 = r0.getAdminSupportContentString(r4)
            goto L94
        L9b:
            r3 = 2131361981(0x7f0a00bd, float:1.834373E38)
            android.view.View r3 = r5.findViewById(r3)
            android.widget.TextView r3 = (android.widget.TextView) r3
            if (r2 == 0) goto La9
            r3.setText(r2)
        La9:
            return
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.settings.enterprise.ActionDisabledByAdminDialogHelper.setAdminSupportDetails(android.app.Activity,"
                    + " android.view.View,"
                    + " com.android.settingslib.RestrictedLockUtils$EnforcedAdmin):void");
    }

    public void setAdminSupportIcon(View view, ComponentName componentName, int i) {
        ImageView imageView = (ImageView) view.requireViewById(R.id.admin_support_icon);
        Activity activity = this.mActivity;
        imageView.setImageDrawable(activity.getDrawable(R.drawable.ic_lock_closed));
        imageView.setImageTintList(Utils.getColorAttr(activity, android.R.attr.colorAccent));
    }

    public void setAdminSupportTitle(View view, String str) {
        TextView textView = (TextView) view.findViewById(R.id.admin_support_dialog_title);
        if (textView == null) {
            return;
        }
        textView.setText(this.mActionDisabledByAdminController.getAdminSupportTitle());
    }

    public void prepareDialogBuilder(
            AlertDialog.Builder builder,
            String str,
            RestrictedLockUtils.EnforcedAdmin enforcedAdmin) {
        Activity activity = this.mActivity;
        ActionDisabledLearnMoreButtonLauncherImpl actionDisabledLearnMoreButtonLauncherImpl =
                new ActionDisabledLearnMoreButtonLauncherImpl(activity, builder);
        BaseActionDisabledByAdminController baseActionDisabledByAdminController =
                this.mActionDisabledByAdminController;
        baseActionDisabledByAdminController.getClass();
        baseActionDisabledByAdminController.mLauncher = actionDisabledLearnMoreButtonLauncherImpl;
        this.mEnforcedAdmin = enforcedAdmin;
        this.mRestriction = str;
        initializeDialogViews(
                this.mDialogView,
                enforcedAdmin,
                getEnforcementAdminUserId(enforcedAdmin),
                this.mRestriction);
        baseActionDisabledByAdminController.setupLearnMoreButton(activity);
    }
}
