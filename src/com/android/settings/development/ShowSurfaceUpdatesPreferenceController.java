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
public final class ShowSurfaceUpdatesPreferenceController
        extends DeveloperOptionsPreferenceController
        implements Preference.OnPreferenceChangeListener, PreferenceControllerMixin {
    static final int SURFACE_FLINGER_READ_CODE = 1010;
    static final String SURFACE_FLINGER_SERVICE_KEY = "SurfaceFlinger";
    public final IBinder mSurfaceFlinger;

    public ShowSurfaceUpdatesPreferenceController(Context context) {
        super(context);
        this.mSurfaceFlinger = ServiceManager.getService(SURFACE_FLINGER_SERVICE_KEY);
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final String getPreferenceKey() {
        return "show_screen_updates";
    }

    @Override // com.android.settingslib.development.DeveloperOptionsPreferenceController
    public final void onDeveloperOptionsSwitchDisabled() {
        super.onDeveloperOptionsSwitchDisabled();
        TwoStatePreference twoStatePreference = (TwoStatePreference) this.mPreference;
        if (twoStatePreference.isChecked()) {
            writeShowUpdatesSetting(false);
            twoStatePreference.setChecked(false);
        }
    }

    @Override // androidx.preference.Preference.OnPreferenceChangeListener
    public final boolean onPreferenceChange(Preference preference, Object obj) {
        writeShowUpdatesSetting(((Boolean) obj).booleanValue());
        return true;
    }

    public void updateShowUpdatesSetting() {
        try {
            if (this.mSurfaceFlinger != null) {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                obtain.writeInterfaceToken("android.ui.ISurfaceComposer");
                this.mSurfaceFlinger.transact(1010, obtain, obtain2, 0);
                obtain2.readInt();
                obtain2.readInt();
                ((TwoStatePreference) this.mPreference).setChecked(obtain2.readInt() != 0);
                obtain2.recycle();
                obtain.recycle();
            }
        } catch (RemoteException unused) {
        }
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final void updateState(Preference preference) {
        updateShowUpdatesSetting();
    }

    public void writeShowUpdatesSetting(boolean z) {
        try {
            if (this.mSurfaceFlinger != null) {
                Parcel obtain = Parcel.obtain();
                obtain.writeInterfaceToken("android.ui.ISurfaceComposer");
                obtain.writeInt(z ? 1 : 0);
                this.mSurfaceFlinger.transact(1002, obtain, null, 0);
                obtain.recycle();
            }
        } catch (RemoteException unused) {
        }
        updateShowUpdatesSetting();
    }
}
