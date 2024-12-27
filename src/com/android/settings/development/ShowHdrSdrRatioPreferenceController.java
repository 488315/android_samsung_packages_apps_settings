package com.android.settings.development;

import android.content.Context;
import android.hardware.display.DisplayManager;
import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.view.Display;

import androidx.preference.Preference;
import androidx.preference.TwoStatePreference;

import com.android.settings.core.PreferenceControllerMixin;
import com.android.settingslib.development.DeveloperOptionsPreferenceController;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class ShowHdrSdrRatioPreferenceController extends DeveloperOptionsPreferenceController
        implements Preference.OnPreferenceChangeListener, PreferenceControllerMixin {
    public final boolean mIsHdrSdrRatioAvailable;
    public final IBinder mSurfaceFlinger;

    public ShowHdrSdrRatioPreferenceController(Context context) {
        super(context);
        this.mSurfaceFlinger = ServiceManager.getService("SurfaceFlinger");
        boolean z = false;
        Display display =
                ((DisplayManager) context.getSystemService(DisplayManager.class)).getDisplay(0);
        if (display != null && display.isHdrSdrRatioAvailable()) {
            z = true;
        }
        this.mIsHdrSdrRatioAvailable = z;
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final String getPreferenceKey() {
        return "show_hdr_sdr_ratio";
    }

    @Override // com.android.settingslib.development.DeveloperOptionsPreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public final boolean isAvailable() {
        return this.mIsHdrSdrRatioAvailable;
    }

    @Override // com.android.settingslib.development.DeveloperOptionsPreferenceController
    public final void onDeveloperOptionsSwitchDisabled() {
        super.onDeveloperOptionsSwitchDisabled();
        TwoStatePreference twoStatePreference = (TwoStatePreference) this.mPreference;
        if (twoStatePreference.isChecked()) {
            writeShowHdrSdrRatioSetting(false);
            twoStatePreference.setChecked(false);
        }
    }

    @Override // androidx.preference.Preference.OnPreferenceChangeListener
    public final boolean onPreferenceChange(Preference preference, Object obj) {
        writeShowHdrSdrRatioSetting(((Boolean) obj).booleanValue());
        return true;
    }

    public final void updateShowHdrSdrRatioSetting() {
        try {
            if (this.mSurfaceFlinger != null) {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                obtain.writeInterfaceToken("android.ui.ISurfaceComposer");
                obtain.writeInt(2);
                this.mSurfaceFlinger.transact(1043, obtain, obtain2, 0);
                ((TwoStatePreference) this.mPreference).setChecked(obtain2.readBoolean());
                obtain2.recycle();
                obtain.recycle();
            }
        } catch (RemoteException unused) {
        }
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final void updateState(Preference preference) {
        super.updateState(preference);
        updateShowHdrSdrRatioSetting();
    }

    public void writeShowHdrSdrRatioSetting(boolean z) {
        try {
            if (this.mSurfaceFlinger != null) {
                Parcel obtain = Parcel.obtain();
                obtain.writeInterfaceToken("android.ui.ISurfaceComposer");
                obtain.writeInt(z ? 1 : 0);
                this.mSurfaceFlinger.transact(1043, obtain, null, 0);
                obtain.recycle();
            }
        } catch (RemoteException unused) {
        }
        updateShowHdrSdrRatioSetting();
    }

    public ShowHdrSdrRatioPreferenceController(Context context, IBinder iBinder, boolean z) {
        super(context);
        this.mSurfaceFlinger = iBinder;
        this.mIsHdrSdrRatioAvailable = z;
    }
}
