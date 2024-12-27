package com.samsung.android.settings.deviceinfo.softwareinfo;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.text.TextUtils;
import android.util.secutil.Log;

import com.android.settings.R;
import com.android.settings.core.BasePreferenceController;

import com.samsung.android.knox.EnterpriseDeviceManager;
import com.samsung.android.knox.EnterpriseKnoxManager;
import com.samsung.android.knox.dar.ddar.DualDarManager;
import com.samsung.android.knox.ddar.DualDARPolicy;
import com.samsung.android.knox.hdm.HdmManager;
import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;
import com.samsung.android.settings.knox.KnoxUtils;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class KnoxVersionPreferenceController extends BasePreferenceController {
    private static final String TAG = "KnoxVersionPreferenceController";

    public KnoxVersionPreferenceController(Context context, String str) {
        super(context, str);
    }

    private String showKnoxVersion() {
        String str = ApnSettings.MVNO_NONE;
        try {
            if (EnterpriseKnoxManager.getInstance().getVersion().getInternalVersion() != null) {
                str =
                        ApnSettings.MVNO_NONE
                                + (this.mContext
                                                .getResources()
                                                .getString(R.string.knox_knox_version)
                                        + " "
                                        + EnterpriseKnoxManager.getInstance()
                                                .getVersion()
                                                .getInternalVersion()
                                                .toString());
            }
        } catch (Exception e) {
            Log.i(TAG, "KnoxVersion Exception : " + e.getMessage());
            e.printStackTrace();
        }
        try {
            if (EnterpriseDeviceManager.getInstance(this.mContext)
                            .getEnterpriseSdkVer()
                            .getInternalVersion()
                    != null) {
                str =
                        str
                                + "\n"
                                + (this.mContext.getResources().getString(R.string.knox_api_level)
                                        + " "
                                        + EnterpriseDeviceManager.getAPILevel());
            }
        } catch (Exception e2) {
            Log.i(TAG, "KnoxVersion Exception : " + e2.getMessage());
            e2.printStackTrace();
        }
        try {
            PackageInfo packageInfo =
                    this.mContext
                            .getPackageManager()
                            .getPackageInfo("com.samsung.android.app.kfa", 128);
            if (packageInfo != null) {
                str =
                        str
                                + "\n"
                                + (this.mContext.getResources().getString(R.string.knox_ml_version)
                                        + " "
                                        + packageInfo.versionName.substring(0, r7.length() - 3));
            }
        } catch (PackageManager.NameNotFoundException e3) {
            Log.i(TAG, "KnoxVersion Exception : " + e3.getMessage());
        } catch (Exception e4) {
            Log.i(TAG, "KnoxVersion Exception : " + e4.getMessage());
        }
        try {
            StringBuilder sb = new StringBuilder();
            String dualDARVersion = DualDARPolicy.getDualDARVersion();
            if (dualDARVersion != null) {
                sb.append(this.mContext.getResources().getString(R.string.knox_dualdar_version));
                sb.append(" ");
                sb.append(dualDARVersion);
                String str2 = KnoxUtils.mDeviceType;
                if (DualDarManager.isOnDeviceOwnerEnabled()) {
                    sb.append(" ");
                    sb.append(this.mContext.getResources().getString(R.string.sec_enable_text_app));
                } else if (KnoxUtils.isDualDarEnabled(this.mContext)) {
                    sb.append(" ");
                    sb.append(this.mContext.getResources().getString(R.string.sec_enable_text_app));
                    sb.append(" (");
                    sb.append(
                            this.mContext
                                    .getResources()
                                    .getString(R.string.sec_work_profile_title));
                    sb.append(")");
                }
                str = str + "\n" + sb.toString();
            }
        } catch (Exception e5) {
            Log.i(TAG, "DualDARVersion Exception : " + e5.getMessage());
            e5.printStackTrace();
        }
        try {
            String hdmVersion = HdmManager.getHdmVersion();
            if (hdmVersion != null) {
                str =
                        str
                                + "\n"
                                + (this.mContext.getResources().getString(R.string.knox_hdm_version)
                                        + " "
                                        + hdmVersion);
            }
        } catch (Exception e6) {
            Log.i(TAG, "HdmVersion Exception : " + e6.getMessage());
            e6.printStackTrace();
        }
        try {
            PackageInfo packageInfo2 =
                    this.mContext
                            .getPackageManager()
                            .getPackageInfo("com.samsung.android.knox.mpos", 128);
            if (packageInfo2 != null) {
                Object obj = packageInfo2.applicationInfo.metaData.get("KNOX_MPOS_VERSION");
                Log.i(TAG, "knoxMposVersion ?  " + obj);
                if (obj != null) {
                    str = str + "\n" + obj;
                }
            }
        } catch (Exception e7) {
            Log.i(TAG, "KnoxmPOS Exception : " + e7.getMessage());
        }
        return TextUtils.isEmpty(str) ? this.mContext.getString(R.string.device_info_default) : str;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        return 0;
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

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public CharSequence getSummary() {
        return showKnoxVersion();
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlValue getValue() {
        return null;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean hasAsyncUpdate() {
        return false;
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
}
