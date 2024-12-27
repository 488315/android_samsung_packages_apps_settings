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

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class ShowRefreshRatePreferenceController extends DeveloperOptionsPreferenceController
        implements Preference.OnPreferenceChangeListener, PreferenceControllerMixin {
    static final int SURFACE_FLINGER_CODE = 1034;
    static final String SURFACE_FLINGER_SERVICE_KEY = "SurfaceFlinger";
    public final IBinder mSurfaceFlinger;

    public ShowRefreshRatePreferenceController(Context context) {
        super(context);
        this.mSurfaceFlinger = ServiceManager.getService(SURFACE_FLINGER_SERVICE_KEY);
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final String getPreferenceKey() {
        return "show_refresh_rate";
    }

    @Override // com.android.settingslib.development.DeveloperOptionsPreferenceController
    public final void onDeveloperOptionsSwitchDisabled() {
        super.onDeveloperOptionsSwitchDisabled();
        TwoStatePreference twoStatePreference = (TwoStatePreference) this.mPreference;
        if (twoStatePreference.isChecked()) {
            writeShowRefreshRateSetting(false);
            twoStatePreference.setChecked(false);
        }
    }

    @Override // androidx.preference.Preference.OnPreferenceChangeListener
    public final boolean onPreferenceChange(Preference preference, Object obj) {
        writeShowRefreshRateSetting(((Boolean) obj).booleanValue());
        return true;
    }

    public void updateShowRefreshRateSetting() {
        try {
            if (this.mSurfaceFlinger != null) {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                obtain.writeInterfaceToken("android.ui.ISurfaceComposer");
                obtain.writeInt(2);
                this.mSurfaceFlinger.transact(SURFACE_FLINGER_CODE, obtain, obtain2, 0);
                ((TwoStatePreference) this.mPreference).setChecked(obtain2.readBoolean());
                obtain2.recycle();
                obtain.recycle();
            }
        } catch (RemoteException unused) {
        }
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final void updateState(Preference preference) {
        updateShowRefreshRateSetting();
    }

    public void writeShowRefreshRateSetting(boolean z) {
        try {
            if (this.mSurfaceFlinger != null) {
                Parcel obtain = Parcel.obtain();
                obtain.writeInterfaceToken("android.ui.ISurfaceComposer");
                obtain.writeInt(z ? 1 : 0);
                this.mSurfaceFlinger.transact(SURFACE_FLINGER_CODE, obtain, null, 0);
                obtain.recycle();
            }
        } catch (RemoteException unused) {
        }
        updateShowRefreshRateSetting();
    }
}
