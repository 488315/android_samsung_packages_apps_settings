package com.android.settings.development;

import android.content.Context;
import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;
import android.os.ServiceManager;

import androidx.preference.Preference;
import androidx.preference.TwoStatePreference;

import com.android.settings.core.PreferenceControllerMixin;
import com.android.settingslib.development.DeveloperOptionsPreferenceController;

import com.samsung.android.knox.EnterpriseContainerCallback;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class HardwareOverlaysPreferenceController extends DeveloperOptionsPreferenceController
        implements Preference.OnPreferenceChangeListener, PreferenceControllerMixin {
    static final int SURFACE_FLINGER_READ_CODE = 1010;
    public final IBinder mSurfaceFlinger;

    public HardwareOverlaysPreferenceController(Context context) {
        super(context);
        this.mSurfaceFlinger = ServiceManager.getService("SurfaceFlinger");
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final String getPreferenceKey() {
        return "disable_overlays";
    }

    @Override // com.android.settingslib.development.DeveloperOptionsPreferenceController
    public final void onDeveloperOptionsSwitchDisabled() {
        super.onDeveloperOptionsSwitchDisabled();
        TwoStatePreference twoStatePreference = (TwoStatePreference) this.mPreference;
        if (twoStatePreference.isChecked()) {
            writeHardwareOverlaysSetting(false);
            twoStatePreference.setChecked(false);
        }
    }

    @Override // androidx.preference.Preference.OnPreferenceChangeListener
    public final boolean onPreferenceChange(Preference preference, Object obj) {
        writeHardwareOverlaysSetting(((Boolean) obj).booleanValue());
        return true;
    }

    public void updateHardwareOverlaysSetting() {
        if (this.mSurfaceFlinger == null) {
            return;
        }
        try {
            Parcel obtain = Parcel.obtain();
            Parcel obtain2 = Parcel.obtain();
            obtain.writeInterfaceToken("android.ui.ISurfaceComposer");
            this.mSurfaceFlinger.transact(1010, obtain, obtain2, 0);
            obtain2.readInt();
            obtain2.readInt();
            obtain2.readInt();
            obtain2.readInt();
            ((TwoStatePreference) this.mPreference).setChecked(obtain2.readInt() != 0);
            obtain2.recycle();
            obtain.recycle();
        } catch (RemoteException unused) {
        }
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final void updateState(Preference preference) {
        updateHardwareOverlaysSetting();
    }

    public void writeHardwareOverlaysSetting(boolean z) {
        if (this.mSurfaceFlinger == null) {
            return;
        }
        try {
            Parcel obtain = Parcel.obtain();
            obtain.writeInterfaceToken("android.ui.ISurfaceComposer");
            obtain.writeInt(z ? 1 : 0);
            this.mSurfaceFlinger.transact(
                    EnterpriseContainerCallback.CONTAINER_PACKAGE_UNINSTALL_SUCCESS,
                    obtain,
                    null,
                    0);
            obtain.recycle();
        } catch (RemoteException unused) {
        }
        updateHardwareOverlaysSetting();
    }
}
