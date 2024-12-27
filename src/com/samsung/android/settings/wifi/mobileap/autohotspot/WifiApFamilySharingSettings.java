package com.samsung.android.settings.wifi.mobileap.autohotspot;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.InsetDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.view.menu.MenuItemImpl;
import androidx.appcompat.widget.ActionBarContextView$$ExternalSyntheticOutline0;
import androidx.core.content.res.TypedArrayUtils;
import androidx.fragment.app.FragmentActivity;
import androidx.picker3.widget.SeslColorPicker$16$$ExternalSyntheticOutline0;
import androidx.preference.Preference;
import androidx.preference.PreferenceCategory;
import androidx.preference.R$styleable;

import com.android.settings.AirplaneModeEnabler$$ExternalSyntheticOutline0;
import com.android.settings.MainClearConfirm$$ExternalSyntheticOutline0;
import com.android.settings.R;
import com.android.settings.SettingsActivity;
import com.android.settings.Utils;
import com.android.settings.core.SubSettingLauncher;
import com.android.settings.dashboard.DashboardFragment;
import com.android.settings.widget.SettingsMainSwitchBar;
import com.android.settingslib.widget.LayoutPreference;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.sdk.mobileservice.social.social.GroupDetailRequest;
import com.samsung.android.sdk.mobileservice.util.SdkLog;
import com.samsung.android.settings.WifiApUtils;
import com.samsung.android.settings.voiceinput.samsungaccount.contract.SaContract;
import com.samsung.android.settings.wifi.mobileap.WifiApCustomPreference;
import com.samsung.android.settings.wifi.mobileap.WifiApCustomPreference.AnonymousClass1;
import com.samsung.android.settings.wifi.mobileap.datamodels.WifiApFamilyMember;
import com.samsung.android.settings.wifi.mobileap.utils.WifiApFrameworkUtils;
import com.samsung.android.settings.wifi.mobileap.utils.WifiApSettingsUtils;
import com.samsung.android.settings.wifi.mobileap.utils.WifiApSmartTetheringApkUtils;
import com.samsung.android.settings.wifi.mobileap.views.WifiApDescriptionPreference;
import com.samsung.android.settings.wifi.mobileap.views.WifiApPreference;
import com.samsung.android.settings.wifi.mobileap.views.WifiApRoundButtonPreference;
import com.samsung.android.settings.wifi.mobileap.views.WifiApTextViewButtonLayoutPreference;
import com.samsung.android.wifi.SemWifiApSmartWhiteList;
import com.samsung.android.wifi.SemWifiManager;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public class WifiApFamilySharingSettings extends DashboardFragment
        implements CompoundButton.OnCheckedChangeListener {
    public static final /* synthetic */ int $r8$clinit = 0;
    public FragmentActivity mActivity;
    public boolean mAddAllowedDeviceButtonClicked;
    public WifiApTextViewButtonLayoutPreference mAddAllowedDeviceButtonLayoutPreference;
    public WifiApDescriptionPreference mAddAllowedDeviceDescription;
    public SemWifiApSmartWhiteList.SmartWhiteList mAllowedDeviceClicked;
    public FragmentActivity mContext;
    public EditText mDeviceNameOnDialogEditText;
    public LayoutPreference mDivider;
    public boolean mEditYourFamilyButtonClicked;
    public TextView mErrorTextOnDialogView;
    public SettingsMainSwitchBar mFamilyMainSwitchBar;
    public WifiApFamilySharingSettings mFragment;
    public WifiApRoundButtonPreference mInviteOrEditMembersButtonPreference;
    public WifiApTextViewButtonLayoutPreference mInviteOrEditYourFamilyButtonLayoutPreference;
    public boolean mIsAutoHotspotSupported;
    public WifiApDescriptionPreference mMyFamilyDescription;
    public PreferenceCategory mMyGroupPreferenceCategory;
    public WifiApPreference mNoGroupPreference;
    public List mPreviousAllowedDeviceList;
    public List mPreviousFamilyMemberList;
    public WifiApFamilySharingSwitchEnabler mWifiApFamilySharingSwitchEnabler;
    public AlertDialog mDeviceNameDialog = null;
    public String mTempDeviceNameOnDialogEditText = null;
    public final AnonymousClass1 mAutoHotspotCustomPreferenceListener =
            new AnonymousClass1(this, 0);
    public final AnonymousClass1 mOnEditMembersPreferenceClickListener =
            new AnonymousClass1(this, 1);
    public final AnonymousClass1 mOnFamilySwitchStateChangeListener = new AnonymousClass1(this, 2);
    public final AnonymousClass9 dialogNameTextWatcher =
            new TextWatcher() { // from class:
                                // com.samsung.android.settings.wifi.mobileap.autohotspot.WifiApFamilySharingSettings.9
                @Override // android.text.TextWatcher
                public final void afterTextChanged(Editable editable) {
                    boolean z;
                    EditText editText =
                            WifiApFamilySharingSettings.this.mDeviceNameOnDialogEditText;
                    if (editText != null) {
                        int length = editText.getText().toString().trim().length();
                        if (WifiApFamilySharingSettings.this.mDeviceNameOnDialogEditText.length()
                                        == 0
                                || length == 0) {
                            z = false;
                            WifiApFamilySharingSettings.this
                                    .mDeviceNameDialog
                                    .getButton(-1)
                                    .setEnabled(z);
                        }
                    }
                    z = true;
                    WifiApFamilySharingSettings.this.mDeviceNameDialog.getButton(-1).setEnabled(z);
                }

                @Override // android.text.TextWatcher
                public final void beforeTextChanged(
                        CharSequence charSequence, int i, int i2, int i3) {
                    if (charSequence == null || charSequence.toString().getBytes().length > 32) {
                        return;
                    }
                    WifiApFamilySharingSettings.this.mTempDeviceNameOnDialogEditText =
                            charSequence.toString();
                }

                @Override // android.text.TextWatcher
                public final void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                    if (charSequence.toString().getBytes().length <= 32) {
                        if (charSequence.toString().getBytes().length < 32) {
                            WifiApFamilySharingSettings.this.mErrorTextOnDialogView.setVisibility(
                                    8);
                            WifiApFamilySharingSettings wifiApFamilySharingSettings =
                                    WifiApFamilySharingSettings.this;
                            wifiApFamilySharingSettings.mDeviceNameOnDialogEditText
                                    .setBackgroundTintList(
                                            wifiApFamilySharingSettings
                                                    .mContext
                                                    .getResources()
                                                    .getColorStateList(
                                                            R.color
                                                                    .sec_wifi_ap_edit_text_background_color));
                            return;
                        }
                        return;
                    }
                    String str = WifiApFamilySharingSettings.this.mTempDeviceNameOnDialogEditText;
                    if (str == null || str.getBytes().length > 32) {
                        WifiApFamilySharingSettings.this.mDeviceNameOnDialogEditText.setText(
                                ApnSettings.MVNO_NONE);
                    } else if (charSequence.toString().length()
                                    - WifiApFamilySharingSettings.this
                                            .mTempDeviceNameOnDialogEditText.length()
                            > 1) {
                        String charSequence2 = charSequence.toString();
                        if (charSequence2.getBytes().length > charSequence2.length()) {
                            int i4 = 0;
                            int i5 = 0;
                            int i6 = 0;
                            while (i4 <= 32) {
                                i6 = Character.charCount(charSequence2.codePointAt(i5));
                                int i7 = i5 + i6;
                                i4 += charSequence2.substring(i5, i7).getBytes().length;
                                i5 = i7;
                            }
                            WifiApFamilySharingSettings.this.mDeviceNameOnDialogEditText.setText(
                                    charSequence2.substring(0, i5 - i6));
                        } else {
                            WifiApFamilySharingSettings.this.mDeviceNameOnDialogEditText.setText(
                                    charSequence2.substring(0, 32));
                        }
                    } else {
                        WifiApFamilySharingSettings wifiApFamilySharingSettings2 =
                                WifiApFamilySharingSettings.this;
                        wifiApFamilySharingSettings2.mDeviceNameOnDialogEditText.setText(
                                wifiApFamilySharingSettings2.mTempDeviceNameOnDialogEditText);
                    }
                    WifiApFamilySharingSettings.this.mErrorTextOnDialogView.setText(
                            R.string.wifi_ssid_max_byte_error);
                    WifiApFamilySharingSettings wifiApFamilySharingSettings3 =
                            WifiApFamilySharingSettings.this;
                    wifiApFamilySharingSettings3.mDeviceNameOnDialogEditText.setBackgroundTintList(
                            wifiApFamilySharingSettings3
                                    .mContext
                                    .getResources()
                                    .getColorStateList(R.color.sec_wifi_dialog_error_color));
                    WifiApFamilySharingSettings.this.mErrorTextOnDialogView.setVisibility(0);
                    SeslColorPicker$16$$ExternalSyntheticOutline0.m(
                            WifiApFamilySharingSettings.this.mDeviceNameOnDialogEditText);
                }
            };

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.settings.wifi.mobileap.autohotspot.WifiApFamilySharingSettings$1, reason: invalid class name */
    public final class AnonymousClass1
            implements WifiApCustomPreference.AutoHotspotCustomPreferenceListener,
                    Preference.OnPreferenceClickListener,
                    WifiApFamilySharingSwitchEnabler.OnStateChangeListener {
        public final /* synthetic */ int $r8$classId;
        public final /* synthetic */ WifiApFamilySharingSettings this$0;

        public /* synthetic */ AnonymousClass1(
                WifiApFamilySharingSettings wifiApFamilySharingSettings, int i) {
            this.$r8$classId = i;
            this.this$0 = wifiApFamilySharingSettings;
        }

        @Override // androidx.preference.Preference.OnPreferenceClickListener
        public boolean onPreferenceClick(Preference preference) {
            int i;
            WifiApFamilySharingSettings wifiApFamilySharingSettings = this.this$0;
            switch (this.$r8$classId) {
                case 1:
                    boolean isFamilySharingServiceRegisteredOn =
                            WifiApSmartTetheringApkUtils.isFamilySharingServiceRegisteredOn(
                                    wifiApFamilySharingSettings.mContext);
                    boolean isEmpty =
                            WifiApSmartTetheringApkUtils.getFamilyGroupId(
                                            wifiApFamilySharingSettings.mContext)
                                    .isEmpty();
                    int i2 = WifiApFamilySharingSettings.$r8$clinit;
                    StringBuilder sb =
                            new StringBuilder(
                                    "Edit Family Button`s onClick() - Triggered,"
                                        + " mEditYourFamilyButtonClicked: ");
                    sb.append(wifiApFamilySharingSettings.mEditYourFamilyButtonClicked);
                    sb.append(", isFamilySharingServiceRegistered: ");
                    sb.append(isFamilySharingServiceRegisteredOn);
                    sb.append(", isFamilyGroupIdEmpty: ");
                    ActionBarContextView$$ExternalSyntheticOutline0.m(
                            sb, isEmpty, "WifiApFamilySharingSettings");
                    if (!wifiApFamilySharingSettings.mEditYourFamilyButtonClicked) {
                        wifiApFamilySharingSettings.mEditYourFamilyButtonClicked = true;
                        if (isEmpty) {
                            WifiApSmartTetheringApkUtils
                                    .launchFamilyServiceRegisterActivityForResult(
                                            wifiApFamilySharingSettings.mFragment, 900);
                        } else {
                            FragmentActivity fragmentActivity =
                                    wifiApFamilySharingSettings.mActivity;
                            Log.d("WifiApSmartTetheringApkUtils", "Launching Edit group activity.");
                            ArrayList arrayList = new ArrayList();
                            arrayList.add(107);
                            String familyGroupId =
                                    WifiApSmartTetheringApkUtils.getFamilyGroupId(fragmentActivity);
                            GroupDetailRequest.Builder builder =
                                    new GroupDetailRequest.Builder(familyGroupId);
                            builder.mFeatureIdList = arrayList;
                            builder.mMaxGroupMemberCount = 6;
                            if (TextUtils.isEmpty("4r5b3r1h6a")) {
                                throw new NullPointerException("AppId should not be empty");
                            }
                            if (TextUtils.isEmpty(familyGroupId)) {
                                throw new NullPointerException("GroupId should not be empty");
                            }
                            GroupDetailRequest groupDetailRequest = new GroupDetailRequest(builder);
                            SdkLog.d("OpenSessionApi", "getIntentForGroupDetail");
                            Intent intent =
                                    new Intent(
                                            "com.samsung.android.mobileservice.action.ACTION_EXTERNAL_GROUP_DETAIL");
                            try {
                                i =
                                        fragmentActivity
                                                .getPackageManager()
                                                .getPackageInfo(SaContract.OLD_PACKAGE_NAME, 0)
                                                .versionCode;
                            } catch (PackageManager.NameNotFoundException e) {
                                if (SdkLog.ENG) {
                                    e.printStackTrace();
                                } else {
                                    SdkLog.d(
                                            "SEMS_SDK",
                                            "fatal exception! Trace not allowed.\n"
                                                    + e.getMessage());
                                }
                                i = -1;
                            }
                            if (i >= 1300500005) {
                                Bundle call =
                                        fragmentActivity
                                                .getContentResolver()
                                                .call(
                                                        new Uri.Builder()
                                                                .encodedAuthority(
                                                                        "com.samsung.android.mobileservice.social.common.provider.OpenSessionProvider")
                                                                .build(),
                                                        "getGroupDetailIntent",
                                                        (String) null,
                                                        groupDetailRequest.toBundle());
                                if (call == null) {
                                    SdkLog.d(
                                            "OpenSessionApi",
                                            "com.samsung.android.mobileservice.social.common.provider.OpenSessionProvider"
                                                + " getGroupDetailIntent method call is null");
                                    intent.putExtras(groupDetailRequest.toBundle());
                                } else {
                                    intent.putExtras(call);
                                }
                            } else {
                                intent.putExtras(groupDetailRequest.toBundle());
                            }
                            intent.setFlags(603979776);
                            fragmentActivity.startActivity(intent);
                        }
                    }
                    return true;
                default:
                    if (!wifiApFamilySharingSettings.mAddAllowedDeviceButtonClicked) {
                        wifiApFamilySharingSettings.mAddAllowedDeviceButtonClicked = true;
                        SubSettingLauncher subSettingLauncher =
                                new SubSettingLauncher(wifiApFamilySharingSettings.getActivity());
                        SubSettingLauncher.LaunchRequest launchRequest =
                                subSettingLauncher.mLaunchRequest;
                        launchRequest.mSourceMetricsCategory = 3400;
                        launchRequest.mDestinationName =
                                WifiApAllowedDeviceList.class.getCanonicalName();
                        subSettingLauncher.setTitleRes(
                                R.string.wifi_ap_mhs_add_allowed_device_title, null);
                        subSettingLauncher.launch();
                    }
                    return true;
            }
        }

        @Override // com.samsung.android.settings.wifi.mobileap.WifiApCustomPreference.AutoHotspotCustomPreferenceListener
        public void onPreferenceClicked(Object obj) {
            int i = WifiApFamilySharingSettings.$r8$clinit;
            Log.i(
                    "WifiApFamilySharingSettings",
                    "mAutoHotspotCustomPreferenceListener`s onPreferenceClicked() - Triggered");
            if (obj instanceof SemWifiApSmartWhiteList.SmartWhiteList) {
                WifiApFamilySharingSettings wifiApFamilySharingSettings = this.this$0;
                wifiApFamilySharingSettings.mAllowedDeviceClicked =
                        (SemWifiApSmartWhiteList.SmartWhiteList) obj;
                AlertDialog alertDialog = wifiApFamilySharingSettings.mDeviceNameDialog;
                if (alertDialog == null || !alertDialog.isShowing()) {
                    wifiApFamilySharingSettings.showDialog(11);
                }
            }
        }

        @Override // com.samsung.android.settings.wifi.mobileap.WifiApCustomPreference.AutoHotspotCustomPreferenceListener
        public void onSecondaryIconClicked(Object obj) {
            int i = WifiApFamilySharingSettings.$r8$clinit;
            Log.i(
                    "WifiApFamilySharingSettings",
                    "mAutoHotspotCustomPreferenceListener`s onSecondaryIconClicked() - Triggered");
            if (obj instanceof SemWifiApSmartWhiteList.SmartWhiteList) {
                SemWifiApSmartWhiteList.SmartWhiteList smartWhiteList =
                        (SemWifiApSmartWhiteList.SmartWhiteList) obj;
                if (SemWifiManager.MHSDBG) {
                    Log.d(
                            "WifiApFamilySharingSettings",
                            "mAutoHotspotCustomPreferenceListener`s onSecondaryIconClicked() -"
                                + " smartWhiteList`s name: "
                                    + smartWhiteList.getName()
                                    + ", mac: "
                                    + smartWhiteList.getMac());
                }
                SemWifiApSmartWhiteList.getInstance().removeWhiteList(smartWhiteList.getMac());
                this.this$0.mWifiApFamilySharingSwitchEnabler.updateSwitchState();
            }
        }

        @Override // com.samsung.android.settings.wifi.mobileap.autohotspot.WifiApFamilySharingSwitchEnabler.OnStateChangeListener
        public void onStateChanged(int i) {
            int i2 = WifiApFamilySharingSettings.$r8$clinit;
            MainClearConfirm$$ExternalSyntheticOutline0.m(
                    i,
                    "FamilySharing onStateChanged() - resultCode: ",
                    "WifiApFamilySharingSettings");
            WifiApFamilySharingSettings wifiApFamilySharingSettings = this.this$0;
            if (i == 5) {
                wifiApFamilySharingSettings.finish();
                return;
            }
            if (i == 1) {
                Toast.makeText(
                                wifiApFamilySharingSettings.mContext,
                                R.string.flight_mode_enabled_toast,
                                0)
                        .show();
                wifiApFamilySharingSettings.finish();
                return;
            }
            if (i == 3
                    && !WifiApFrameworkUtils.isFamilySharingSetOn(
                            wifiApFamilySharingSettings.mContext)) {
                wifiApFamilySharingSettings.finish();
                return;
            }
            if (i == 6) {
                Toast.makeText(
                                wifiApFamilySharingSettings.mContext,
                                R.string.smart_tethering_nearby_can_not_available,
                                1)
                        .show();
                wifiApFamilySharingSettings.finish();
                return;
            }
            wifiApFamilySharingSettings.getClass();
            Log.d("WifiApFamilySharingSettings", "updateFamilySharingPreference() - Triggered");
            SettingsMainSwitchBar settingsMainSwitchBar =
                    wifiApFamilySharingSettings.mFamilyMainSwitchBar;
            if (settingsMainSwitchBar != null) {
                settingsMainSwitchBar.setChecked(
                        WifiApFrameworkUtils.isFamilySharingSetOn(
                                wifiApFamilySharingSettings.mContext));
            }
            boolean isFamilySharingSetOn =
                    WifiApFrameworkUtils.isFamilySharingSetOn(wifiApFamilySharingSettings.mContext);
            boolean isAutoHotspotSetOn =
                    WifiApFrameworkUtils.isAutoHotspotSetOn(wifiApFamilySharingSettings.mContext);
            if (!isFamilySharingSetOn
                    && !WifiApSettingsUtils.isActiveNetworkHasInternet(
                            wifiApFamilySharingSettings.mContext)) {
                wifiApFamilySharingSettings.finish();
            }
            List familyMemberList =
                    WifiApSmartTetheringApkUtils.getFamilyMemberList(
                            wifiApFamilySharingSettings.mContext);
            ArrayList arrayList = (ArrayList) familyMemberList;
            if (!arrayList.equals(wifiApFamilySharingSettings.mPreviousFamilyMemberList)) {
                if (SemWifiManager.MHSDBG) {
                    Log.d(
                            "WifiApFamilySharingSettings",
                            "mPreviousWifiApFamilyMemberList : "
                                    + wifiApFamilySharingSettings.mPreviousFamilyMemberList
                                            .toString());
                    Log.d(
                            "WifiApFamilySharingSettings",
                            "newFamilyMemberList : "
                                    + wifiApFamilySharingSettings.mPreviousFamilyMemberList
                                            .toString());
                }
                Iterator it =
                        ((ArrayList) wifiApFamilySharingSettings.mPreviousFamilyMemberList)
                                .iterator();
                while (it.hasNext()) {
                    wifiApFamilySharingSettings.mMyGroupPreferenceCategory
                            .removePreferenceRecursively(((WifiApFamilyMember) it.next()).mGuid);
                }
                if (arrayList.size() > 0) {
                    Iterator it2 = arrayList.iterator();
                    int i3 = 1;
                    while (it2.hasNext()) {
                        WifiApFamilyMember wifiApFamilyMember = (WifiApFamilyMember) it2.next();
                        Context prefContext = wifiApFamilySharingSettings.getPrefContext();
                        AnonymousClass1 anonymousClass1 =
                                wifiApFamilySharingSettings.mAutoHotspotCustomPreferenceListener;
                        WifiApCustomPreference wifiApCustomPreference =
                                new WifiApCustomPreference(prefContext);
                        wifiApCustomPreference.mSecondaryIconClickListener =
                                wifiApCustomPreference.new AnonymousClass1();
                        Log.i(
                                "WifiApCustomPreference",
                                "WifiApCustomPreference() - Account name for Account preference"
                                    + " received");
                        wifiApCustomPreference.mSmartWhiteList = null;
                        wifiApCustomPreference.mBleScanAllowedDevice = null;
                        wifiApCustomPreference.mListener = anonymousClass1;
                        wifiApCustomPreference.setLayoutResource(
                                R.layout.sec_wifi_ap_custom_preference);
                        wifiApCustomPreference.setTitle(wifiApFamilyMember.mName);
                        wifiApCustomPreference.setKey(wifiApFamilyMember.mGuid);
                        if (wifiApFamilyMember.getPhoto() != null) {
                            wifiApCustomPreference.setIcon(wifiApFamilyMember.getPhoto());
                        } else {
                            wifiApCustomPreference.setIcon(
                                    R.drawable.sec_wifi_ap_profile_default_image);
                        }
                        wifiApCustomPreference.setOrder(i3);
                        wifiApFamilySharingSettings.mMyGroupPreferenceCategory.addPreference(
                                wifiApCustomPreference);
                        i3++;
                    }
                }
            }
            wifiApFamilySharingSettings.mPreviousFamilyMemberList = familyMemberList;
            if (wifiApFamilySharingSettings.mIsAutoHotspotSupported && arrayList.isEmpty()) {
                wifiApFamilySharingSettings.mMyFamilyDescription.setVisible(true);
                WifiApTextViewButtonLayoutPreference wifiApTextViewButtonLayoutPreference =
                        wifiApFamilySharingSettings.mInviteOrEditYourFamilyButtonLayoutPreference;
                boolean z = wifiApTextViewButtonLayoutPreference.mDividerAllowedBelow;
                wifiApTextViewButtonLayoutPreference.mDividerAllowedAbove = false;
                wifiApTextViewButtonLayoutPreference.mDividerAllowedBelow = z;
                wifiApTextViewButtonLayoutPreference.notifyChanged();
            } else if (wifiApFamilySharingSettings.mIsAutoHotspotSupported
                    || !arrayList.isEmpty()) {
                wifiApFamilySharingSettings.mMyFamilyDescription.setVisible(false);
                wifiApFamilySharingSettings.mNoGroupPreference.setVisible(false);
                WifiApTextViewButtonLayoutPreference wifiApTextViewButtonLayoutPreference2 =
                        wifiApFamilySharingSettings.mInviteOrEditYourFamilyButtonLayoutPreference;
                boolean z2 = wifiApTextViewButtonLayoutPreference2.mDividerAllowedBelow;
                wifiApTextViewButtonLayoutPreference2.mDividerAllowedAbove = true;
                wifiApTextViewButtonLayoutPreference2.mDividerAllowedBelow = z2;
                wifiApTextViewButtonLayoutPreference2.notifyChanged();
            } else {
                wifiApFamilySharingSettings.mNoGroupPreference.setVisible(true);
                WifiApTextViewButtonLayoutPreference wifiApTextViewButtonLayoutPreference3 =
                        wifiApFamilySharingSettings.mInviteOrEditYourFamilyButtonLayoutPreference;
                boolean z3 = wifiApTextViewButtonLayoutPreference3.mDividerAllowedBelow;
                wifiApTextViewButtonLayoutPreference3.mDividerAllowedAbove = false;
                wifiApTextViewButtonLayoutPreference3.mDividerAllowedBelow = z3;
                wifiApTextViewButtonLayoutPreference3.notifyChanged();
            }
            if (WifiApSmartTetheringApkUtils.getFamilyGroupId(wifiApFamilySharingSettings.mContext)
                    .isEmpty()) {
                wifiApFamilySharingSettings.mInviteOrEditMembersButtonPreference.setTitle(
                        R.string.wifi_ap_invite_members);
                wifiApFamilySharingSettings.mInviteOrEditYourFamilyButtonLayoutPreference.setTitle(
                        R.string.wifi_ap_invite_members);
            } else {
                wifiApFamilySharingSettings.mInviteOrEditMembersButtonPreference.setTitle(
                        R.string.wifi_ap_edit_members);
                wifiApFamilySharingSettings.mInviteOrEditYourFamilyButtonLayoutPreference.setTitle(
                        R.string.wifi_ap_edit_members);
            }
            boolean z4 = isFamilySharingSetOn && isAutoHotspotSetOn;
            if (!wifiApFamilySharingSettings.mIsAutoHotspotSupported || !z4) {
                wifiApFamilySharingSettings.mDivider.setVisible(false);
                wifiApFamilySharingSettings.mAddAllowedDeviceDescription.setVisible(false);
                Iterator it3 =
                        ((ArrayList) wifiApFamilySharingSettings.mPreviousAllowedDeviceList)
                                .iterator();
                while (it3.hasNext()) {
                    wifiApFamilySharingSettings.mMyGroupPreferenceCategory
                            .removePreferenceRecursively(
                                    ((SemWifiApSmartWhiteList.SmartWhiteList) it3.next()).getMac());
                }
                wifiApFamilySharingSettings.mAddAllowedDeviceButtonLayoutPreference.setVisible(
                        false);
                return;
            }
            wifiApFamilySharingSettings.mDivider.setVisible(true);
            wifiApFamilySharingSettings.mAddAllowedDeviceDescription.setVisible(true);
            ArrayList arrayList2 = new ArrayList();
            Iterator iterator = SemWifiApSmartWhiteList.getInstance().getIterator();
            if (iterator != null) {
                while (iterator.hasNext()) {
                    arrayList2.add((SemWifiApSmartWhiteList.SmartWhiteList) iterator.next());
                }
            }
            if (!((ArrayList) wifiApFamilySharingSettings.mPreviousAllowedDeviceList)
                    .equals(arrayList2)) {
                Iterator it4 =
                        ((ArrayList) wifiApFamilySharingSettings.mPreviousAllowedDeviceList)
                                .iterator();
                while (it4.hasNext()) {
                    wifiApFamilySharingSettings.mMyGroupPreferenceCategory
                            .removePreferenceRecursively(
                                    ((SemWifiApSmartWhiteList.SmartWhiteList) it4.next()).getMac());
                }
                Iterator it5 = arrayList2.iterator();
                int i4 = 100;
                while (it5.hasNext()) {
                    WifiApCustomPreference wifiApCustomPreference2 =
                            new WifiApCustomPreference(
                                    wifiApFamilySharingSettings.getPrefContext(),
                                    (SemWifiApSmartWhiteList.SmartWhiteList) it5.next(),
                                    wifiApFamilySharingSettings
                                            .mAutoHotspotCustomPreferenceListener);
                    wifiApCustomPreference2.setOrder(i4);
                    wifiApCustomPreference2.setEnabled(isAutoHotspotSetOn);
                    wifiApFamilySharingSettings.mMyGroupPreferenceCategory.addPreference(
                            wifiApCustomPreference2);
                    i4++;
                }
            }
            wifiApFamilySharingSettings.mPreviousAllowedDeviceList = arrayList2;
            wifiApFamilySharingSettings.mAddAllowedDeviceButtonLayoutPreference.setVisible(true);
        }
    }

    @Override // com.android.settings.dashboard.DashboardFragment
    /* renamed from: getLogTag */
    public final String getTAG() {
        return "WifiApFamilySharingSettings";
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 3400;
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment
    public final int getPreferenceScreenResId() {
        return R.xml.sec_wifi_ap_family_sharing_settings;
    }

    @Override // com.android.settings.SettingsPreferenceFragment, androidx.fragment.app.Fragment
    public final void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);
        Log.d("WifiApFamilySharingSettings", "onActivityCreated");
        SettingsActivity settingsActivity = (SettingsActivity) getActivity();
        if (this.mIsAutoHotspotSupported) {
            SettingsMainSwitchBar settingsMainSwitchBar = settingsActivity.mMainSwitch;
            this.mFamilyMainSwitchBar = settingsMainSwitchBar;
            if (settingsMainSwitchBar != null) {
                settingsMainSwitchBar.show();
                this.mFamilyMainSwitchBar.addOnSwitchChangeListener(this);
            }
        }
        setAutoRemoveInsetCategory(false);
        getListView().semSetRoundedCorners(15);
        getListView()
                .semSetRoundedCornerColor(
                        15, getResources().getColor(R.color.sec_widget_round_and_bgcolor));
        WifiApFamilySharingSwitchEnabler wifiApFamilySharingSwitchEnabler =
                new WifiApFamilySharingSwitchEnabler(this);
        this.mWifiApFamilySharingSwitchEnabler = wifiApFamilySharingSwitchEnabler;
        wifiApFamilySharingSwitchEnabler.mOnStateChangeListener =
                this.mOnFamilySwitchStateChangeListener;
    }

    @Override // com.android.settings.dashboard.DashboardFragment, androidx.fragment.app.Fragment
    public final void onActivityResult(int i, int i2, Intent intent) {
        MainClearConfirm$$ExternalSyntheticOutline0.m(
                "onActivityResult() - requestCode: ",
                ", resultCode(-1 for RESULT_OK) : ",
                i,
                i2,
                "WifiApFamilySharingSettings");
        if (i != 900) {
            this.mWifiApFamilySharingSwitchEnabler.onActivityResult(i, i2, intent);
        } else if (i2 == -1) {
            WifiApSmartTetheringApkUtils.launchSocialPickerForCreatingGroupActivity(this, 910);
        }
        super.onActivityResult(i, i2, intent);
    }

    @Override // android.widget.CompoundButton.OnCheckedChangeListener
    public final void onCheckedChanged(CompoundButton compoundButton, boolean z) {
        AirplaneModeEnabler$$ExternalSyntheticOutline0.m(
                "onSwitchChanged: ", "WifiApFamilySharingSettings", z);
        this.mWifiApFamilySharingSwitchEnabler.setChecked(z);
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        Log.d("WifiApFamilySharingSettings", "onCreate");
        FragmentActivity activity = getActivity();
        this.mActivity = activity;
        this.mFragment = this;
        this.mContext = activity;
        this.mIsAutoHotspotSupported = true;
        getPreferenceScreen();
        this.mMyGroupPreferenceCategory =
                (PreferenceCategory) findPreference("family_sharing_preference_category");
        this.mMyFamilyDescription =
                (WifiApDescriptionPreference)
                        findPreference("family_members_description_preference");
        this.mNoGroupPreference = (WifiApPreference) findPreference("no_group_preference");
        this.mInviteOrEditMembersButtonPreference =
                (WifiApRoundButtonPreference)
                        findPreference("invite_or_edit_members_button_preference");
        this.mDivider = (LayoutPreference) findPreference("divider_preference");
        this.mAddAllowedDeviceDescription =
                (WifiApDescriptionPreference)
                        findPreference("allowed_device_description_preference");
        this.mInviteOrEditYourFamilyButtonLayoutPreference =
                (WifiApTextViewButtonLayoutPreference)
                        findPreference("invite_or_edit_members_button_layout_preference");
        this.mAddAllowedDeviceButtonLayoutPreference =
                (WifiApTextViewButtonLayoutPreference)
                        findPreference("add_allowed_device_button_layout_preference");
        this.mInviteOrEditYourFamilyButtonLayoutPreference.setTitle(
                R.string.wifi_ap_invite_members);
        this.mAddAllowedDeviceButtonLayoutPreference.setTitle(
                R.string.wifi_ap_mhs_add_allowed_device_title);
        WifiApTextViewButtonLayoutPreference wifiApTextViewButtonLayoutPreference =
                this.mAddAllowedDeviceButtonLayoutPreference;
        boolean z = wifiApTextViewButtonLayoutPreference.mDividerAllowedBelow;
        wifiApTextViewButtonLayoutPreference.mDividerAllowedAbove = false;
        wifiApTextViewButtonLayoutPreference.mDividerAllowedBelow = z;
        wifiApTextViewButtonLayoutPreference.notifyChanged();
        this.mNoGroupPreference.setTitle(this.mContext.getString(R.string.wifi_ap_no_group));
        WifiApPreference wifiApPreference = this.mNoGroupPreference;
        wifiApPreference.mTextGravity = 17;
        wifiApPreference.notifyChanged();
        this.mMyFamilyDescription.setPaddingInDp(16, 0);
        this.mAddAllowedDeviceDescription.setPaddingInDp(16, 0);
        WifiApTextViewButtonLayoutPreference wifiApTextViewButtonLayoutPreference2 =
                this.mInviteOrEditYourFamilyButtonLayoutPreference;
        wifiApTextViewButtonLayoutPreference2.isPaddingSet = true;
        wifiApTextViewButtonLayoutPreference2.mPaddingTopInDp = 14;
        wifiApTextViewButtonLayoutPreference2.mPaddingBottomInDp = 14;
        wifiApTextViewButtonLayoutPreference2.mPaddingLeftInDp = 0;
        wifiApTextViewButtonLayoutPreference2.mPaddingRightInDp = 0;
        wifiApTextViewButtonLayoutPreference2.notifyChanged();
        WifiApTextViewButtonLayoutPreference wifiApTextViewButtonLayoutPreference3 =
                this.mAddAllowedDeviceButtonLayoutPreference;
        wifiApTextViewButtonLayoutPreference3.isPaddingSet = true;
        wifiApTextViewButtonLayoutPreference3.mPaddingTopInDp = 14;
        wifiApTextViewButtonLayoutPreference3.mPaddingBottomInDp = 22;
        wifiApTextViewButtonLayoutPreference3.mPaddingLeftInDp = 0;
        wifiApTextViewButtonLayoutPreference3.mPaddingRightInDp = 0;
        wifiApTextViewButtonLayoutPreference3.notifyChanged();
        this.mMyFamilyDescription.setTitle(R.string.wifi_ap_add_members_description);
        if (this.mIsAutoHotspotSupported) {
            this.mInviteOrEditYourFamilyButtonLayoutPreference.setVisible(true);
            this.mInviteOrEditMembersButtonPreference.setVisible(false);
        } else {
            this.mInviteOrEditYourFamilyButtonLayoutPreference.setVisible(false);
            this.mInviteOrEditMembersButtonPreference.setVisible(true);
        }
        this.mInviteOrEditMembersButtonPreference.setOnPreferenceClickListener(
                this.mOnEditMembersPreferenceClickListener);
        this.mInviteOrEditYourFamilyButtonLayoutPreference.setOnPreferenceClickListener(
                this.mOnEditMembersPreferenceClickListener);
        this.mAddAllowedDeviceButtonLayoutPreference.setOnPreferenceClickListener(
                new AnonymousClass1(this, 3));
        this.mAddAllowedDeviceDescription.setTitle(
                WifiApUtils.getString(
                        this.mContext, R.string.wifi_ap_mhs_add_allowed_device_description));
        this.mPreviousFamilyMemberList = new ArrayList();
        this.mPreviousAllowedDeviceList = new ArrayList();
        WifiApSmartTetheringApkUtils.startSmartTetheringApk(this.mContext, 1, null);
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.DialogCreatable
    public final Dialog onCreateDialog(int i) {
        final int i2 = 1;
        LayoutInflater layoutInflater =
                (LayoutInflater) getActivity().getSystemService("layout_inflater");
        final int i3 = 0;
        if (i != 11) {
            if (i != 21) {
                return null;
            }
            Log.i("WifiApFamilySharingSettings", "creating dialog  DIALOG_DELETE_FAMILY_GROUP");
            FragmentActivity activity = getActivity();
            boolean z = WifiApSettingsUtils.DBG;
            AlertDialog.Builder builder = new AlertDialog.Builder(activity, 0);
            builder.setTitle(R.string.wifi_ap_delete_family_group);
            builder.setMessage(R.string.wifi_ap_family_group_will_be_delete_description);
            builder.setNegativeButton(
                    R.string.common_cancel,
                    new DialogInterface.OnClickListener() { // from class:
                        // com.samsung.android.settings.wifi.mobileap.autohotspot.WifiApFamilySharingSettings.6
                        @Override // android.content.DialogInterface.OnClickListener
                        public final void onClick(DialogInterface dialogInterface, int i4) {
                            switch (i2) {
                                case 0:
                                    int i5 = WifiApFamilySharingSettings.$r8$clinit;
                                    Log.i(
                                            "WifiApFamilySharingSettings",
                                            "SmartWhiteListClicked`s onClick() :  device name has"
                                                + " not been changed");
                                    break;
                                default:
                                    int i6 = WifiApFamilySharingSettings.$r8$clinit;
                                    Log.i(
                                            "WifiApFamilySharingSettings",
                                            "DIALOG_DELETE_FAMILY_GROUP cancel button clicked");
                                    break;
                            }
                        }
                    });
            builder.setPositiveButton(
                    R.string.wifi_ap_delete,
                    new DialogInterface.OnClickListener(this) { // from class:
                        // com.samsung.android.settings.wifi.mobileap.autohotspot.WifiApFamilySharingSettings.5
                        public final /* synthetic */ WifiApFamilySharingSettings this$0;

                        {
                            this.this$0 = this;
                        }

                        @Override // android.content.DialogInterface.OnClickListener
                        public final void onClick(DialogInterface dialogInterface, int i4) {
                            switch (i2) {
                                case 0:
                                    if (this.this$0.mAllowedDeviceClicked != null) {
                                        if (SemWifiManager.MHSDBG) {
                                            int i5 = WifiApFamilySharingSettings.$r8$clinit;
                                            Log.d(
                                                    "WifiApFamilySharingSettings",
                                                    "SmartWhiteListClicked`s onClick() : (Modify)"
                                                        + " Removing Device Name-> "
                                                            + this.this$0.mAllowedDeviceClicked
                                                                    .getName()
                                                            + " mac-> "
                                                            + this.this$0.mAllowedDeviceClicked
                                                                    .getMac());
                                        }
                                        SemWifiApSmartWhiteList.getInstance()
                                                .modifyWhiteList(
                                                        this.this$0.mAllowedDeviceClicked.getMac(),
                                                        this.this$0
                                                                .mDeviceNameOnDialogEditText
                                                                .getText()
                                                                .toString());
                                        this.this$0.mWifiApFamilySharingSwitchEnabler
                                                .updateSwitchState();
                                        break;
                                    }
                                    break;
                                default:
                                    int i6 = WifiApFamilySharingSettings.$r8$clinit;
                                    Log.i(
                                            "WifiApFamilySharingSettings",
                                            "DIALOG_DELETE_FAMILY_GROUP delete button clicked");
                                    FragmentActivity fragmentActivity = this.this$0.mContext;
                                    Log.d(
                                            "WifiApSmartTetheringApkUtils",
                                            "Deleting group: "
                                                    .concat(
                                                            WifiApSmartTetheringApkUtils
                                                                    .getFamilyGroupId(
                                                                            fragmentActivity)));
                                    Intent intent = new Intent();
                                    intent.setClassName(
                                            "com.sec.mhs.smarttethering",
                                            "com.sec.mhs.smarttethering.WifiApGroupService");
                                    intent.putExtra("cmd_arg", 6);
                                    try {
                                        fragmentActivity.startService(intent);
                                        break;
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                            }
                        }
                    });
            AlertDialog create = builder.create();
            this.mDeviceNameDialog = create;
            return create;
        }
        Log.i(
                "WifiApFamilySharingSettings",
                "onCreateDialog() : create dialog  DIALOG_MODIFY_WHITE_LIST");
        View inflate =
                layoutInflater.inflate(
                        R.layout.sec_wifi_ap_auto_hotspot_white_list_edit_dialog, (ViewGroup) null);
        this.mDeviceNameOnDialogEditText = (EditText) inflate.findViewById(R.id.name);
        this.mErrorTextOnDialogView = (TextView) inflate.findViewById(R.id.error_text);
        this.mDeviceNameOnDialogEditText.setText(this.mAllowedDeviceClicked.getName());
        this.mDeviceNameOnDialogEditText.requestFocus();
        this.mDeviceNameOnDialogEditText.addTextChangedListener(this.dialogNameTextWatcher);
        EditText editText = this.mDeviceNameOnDialogEditText;
        editText.setFilters(new InputFilter[] {new Utils.EmojiInputFilter(editText.getContext())});
        FragmentActivity activity2 = getActivity();
        boolean z2 = WifiApSettingsUtils.DBG;
        AlertDialog.Builder builder2 = new AlertDialog.Builder(activity2, 0);
        builder2.setTitle(R.string.wifi_tether_otherdevices_name);
        builder2.setView(inflate);
        builder2.setPositiveButton(
                R.string.wifi_save,
                new DialogInterface.OnClickListener(this) { // from class:
                    // com.samsung.android.settings.wifi.mobileap.autohotspot.WifiApFamilySharingSettings.5
                    public final /* synthetic */ WifiApFamilySharingSettings this$0;

                    {
                        this.this$0 = this;
                    }

                    @Override // android.content.DialogInterface.OnClickListener
                    public final void onClick(DialogInterface dialogInterface, int i4) {
                        switch (i3) {
                            case 0:
                                if (this.this$0.mAllowedDeviceClicked != null) {
                                    if (SemWifiManager.MHSDBG) {
                                        int i5 = WifiApFamilySharingSettings.$r8$clinit;
                                        Log.d(
                                                "WifiApFamilySharingSettings",
                                                "SmartWhiteListClicked`s onClick() : (Modify)"
                                                    + " Removing Device Name-> "
                                                        + this.this$0.mAllowedDeviceClicked
                                                                .getName()
                                                        + " mac-> "
                                                        + this.this$0.mAllowedDeviceClicked
                                                                .getMac());
                                    }
                                    SemWifiApSmartWhiteList.getInstance()
                                            .modifyWhiteList(
                                                    this.this$0.mAllowedDeviceClicked.getMac(),
                                                    this.this$0
                                                            .mDeviceNameOnDialogEditText
                                                            .getText()
                                                            .toString());
                                    this.this$0.mWifiApFamilySharingSwitchEnabler
                                            .updateSwitchState();
                                    break;
                                }
                                break;
                            default:
                                int i6 = WifiApFamilySharingSettings.$r8$clinit;
                                Log.i(
                                        "WifiApFamilySharingSettings",
                                        "DIALOG_DELETE_FAMILY_GROUP delete button clicked");
                                FragmentActivity fragmentActivity = this.this$0.mContext;
                                Log.d(
                                        "WifiApSmartTetheringApkUtils",
                                        "Deleting group: "
                                                .concat(
                                                        WifiApSmartTetheringApkUtils
                                                                .getFamilyGroupId(
                                                                        fragmentActivity)));
                                Intent intent = new Intent();
                                intent.setClassName(
                                        "com.sec.mhs.smarttethering",
                                        "com.sec.mhs.smarttethering.WifiApGroupService");
                                intent.putExtra("cmd_arg", 6);
                                try {
                                    fragmentActivity.startService(intent);
                                    break;
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                        }
                    }
                });
        builder2.setNegativeButton(
                R.string.wifi_cancel,
                new DialogInterface.OnClickListener() { // from class:
                    // com.samsung.android.settings.wifi.mobileap.autohotspot.WifiApFamilySharingSettings.6
                    @Override // android.content.DialogInterface.OnClickListener
                    public final void onClick(DialogInterface dialogInterface, int i4) {
                        switch (i3) {
                            case 0:
                                int i5 = WifiApFamilySharingSettings.$r8$clinit;
                                Log.i(
                                        "WifiApFamilySharingSettings",
                                        "SmartWhiteListClicked`s onClick() :  device name has not"
                                            + " been changed");
                                break;
                            default:
                                int i6 = WifiApFamilySharingSettings.$r8$clinit;
                                Log.i(
                                        "WifiApFamilySharingSettings",
                                        "DIALOG_DELETE_FAMILY_GROUP cancel button clicked");
                                break;
                        }
                    }
                });
        AlertDialog create2 = builder2.create();
        this.mDeviceNameDialog = create2;
        create2.getWindow().setSoftInputMode(21);
        return this.mDeviceNameDialog;
    }

    @Override // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
        Log.d("WifiApFamilySharingSettings", "create options menu");
        menu.add(0, 1, 0, R.string.wifi_ap_invitations_menu).setShowAsAction(0);
        if (WifiApSmartTetheringApkUtils.isCurrentUserIsGroupOwner(this.mContext)) {
            menu.add(0, 3, 0, R.string.wifi_ap_delete_family_group).setShowAsAction(0);
        }
        super.onCreateOptionsMenu(menu, menuInflater);
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final View onCreateView(
            LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        Log.d("WifiApFamilySharingSettings", "onCreateView");
        return super.onCreateView(layoutInflater, viewGroup, bundle);
    }

    @Override // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onDestroyView() {
        super.onDestroyView();
        Log.d("WifiApFamilySharingSettings", "onDestroyView");
        SettingsMainSwitchBar settingsMainSwitchBar = this.mFamilyMainSwitchBar;
        if (settingsMainSwitchBar != null) {
            settingsMainSwitchBar.removeOnSwitchChangeListener(this);
        }
    }

    @Override // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final boolean onOptionsItemSelected(MenuItem menuItem) {
        int itemId = menuItem.getItemId();
        if (itemId == 1) {
            SubSettingLauncher subSettingLauncher = new SubSettingLauncher(getActivity());
            SubSettingLauncher.LaunchRequest launchRequest = subSettingLauncher.mLaunchRequest;
            launchRequest.mSourceMetricsCategory = 3400;
            launchRequest.mDestinationName = WifiApInvitationList.class.getCanonicalName();
            launchRequest.mTitle = getString(R.string.wifi_ap_invitations);
            subSettingLauncher.launch();
        } else if (itemId == 3) {
            showDialog(21);
        }
        return super.onOptionsItemSelected(menuItem);
    }

    @Override // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        MenuItemImpl menuItemImpl = (MenuItemImpl) menu.findItem(1);
        if (menuItemImpl != null) {
            menuItemImpl.setBadgeText(
                    WifiApSmartTetheringApkUtils.isThereAnyNewInvitation(this.mContext)
                            ? getContext().getString(R.string.sec_dashboard_badge_new)
                            : null);
        }
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onResume() {
        super.onResume();
        Log.d("WifiApFamilySharingSettings", "onResume");
        this.mAddAllowedDeviceButtonClicked = false;
        this.mEditYourFamilyButtonClicked = false;
        this.mWifiApFamilySharingSwitchEnabler.updateSwitchState();
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onViewCreated(View view, Bundle bundle) {
        super.onViewCreated(view, bundle);
        boolean z = TextUtils.getLayoutDirectionFromLocale(Locale.getDefault()) == 1;
        TypedArray typedArray = null;
        try {
            try {
                typedArray =
                        getContext()
                                .obtainStyledAttributes(
                                        null,
                                        R$styleable.PreferenceFragment,
                                        TypedArrayUtils.getAttr(
                                                getContext(),
                                                R.attr.preferenceFragmentStyle,
                                                android.R.attr.preferenceFragmentStyle),
                                        0);
                Drawable drawable = typedArray.getDrawable(1);
                int dimensionPixelSize = typedArray.getDimensionPixelSize(2, -1);
                super.setDivider(
                        !z
                                ? new InsetDrawable(
                                        drawable,
                                        getResources()
                                                .getDimensionPixelSize(
                                                        R.dimen
                                                                .wifi_ap_start_margin_for_divider_inset),
                                        0,
                                        0,
                                        0)
                                : new InsetDrawable(
                                        drawable,
                                        0,
                                        0,
                                        getResources()
                                                .getDimensionPixelSize(
                                                        R.dimen
                                                                .wifi_ap_start_margin_for_divider_inset),
                                        0));
                if (dimensionPixelSize != -1) {
                    super.setDividerHeight(dimensionPixelSize);
                }
            } catch (Exception e) {
                e.printStackTrace();
                if (typedArray == null) {
                    return;
                }
            }
            typedArray.recycle();
        } catch (Throwable th) {
            if (typedArray != null) {
                typedArray.recycle();
            }
            throw th;
        }
    }
}
