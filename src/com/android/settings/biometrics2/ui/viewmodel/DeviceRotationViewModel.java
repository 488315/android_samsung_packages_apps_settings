package com.android.settings.biometrics2.ui.viewmodel;

import android.R;
import android.app.Application;
import android.hardware.display.DisplayManager;
import android.util.Log;
import android.view.DisplayInfo;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.android.internal.annotations.VisibleForTesting;
import com.android.settings.MainClearConfirm$$ExternalSyntheticOutline0;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class DeviceRotationViewModel extends AndroidViewModel {
    public final DisplayInfo mDisplayInfo;

    @VisibleForTesting protected final DisplayManager.DisplayListener mDisplayListener;
    public final DisplayManager mDisplayManager;
    public final boolean mIsReverseDefaultRotation;
    public final MutableLiveData mLiveData;

    public DeviceRotationViewModel(Application application) {
        super(application);
        this.mDisplayInfo = new DisplayInfo();
        DisplayManager.DisplayListener displayListener =
                new DisplayManager
                        .DisplayListener() { // from class:
                                             // com.android.settings.biometrics2.ui.viewmodel.DeviceRotationViewModel.1
                    @Override // android.hardware.display.DisplayManager.DisplayListener
                    public final void onDisplayChanged(int i) {
                        int rotation = DeviceRotationViewModel.this.getRotation();
                        MainClearConfirm$$ExternalSyntheticOutline0.m(
                                "onDisplayChanged(",
                                "), rotation:",
                                i,
                                rotation,
                                "DeviceRotationViewModel");
                        DeviceRotationViewModel.this.mLiveData.postValue(Integer.valueOf(rotation));
                    }

                    @Override // android.hardware.display.DisplayManager.DisplayListener
                    public final void onDisplayAdded(int i) {}

                    @Override // android.hardware.display.DisplayManager.DisplayListener
                    public final void onDisplayRemoved(int i) {}
                };
        this.mDisplayListener = displayListener;
        this.mLiveData = new MutableLiveData();
        DisplayManager displayManager =
                (DisplayManager) application.getSystemService(DisplayManager.class);
        this.mDisplayManager = displayManager;
        displayManager.registerDisplayListener(displayListener, application.getMainThreadHandler());
        this.mIsReverseDefaultRotation =
                application.getResources().getBoolean(R.bool.config_shortPressEarlyOnStemPrimary);
    }

    public final LiveData getLiveData() {
        MutableLiveData mutableLiveData = this.mLiveData;
        Integer num = (Integer) mutableLiveData.getValue();
        int rotation = getRotation();
        if (num == null || num.intValue() != rotation) {
            Log.d(
                    "DeviceRotationViewModel",
                    "getLiveData, update rotation from " + num + " to " + rotation);
            mutableLiveData.setValue(Integer.valueOf(rotation));
        }
        return mutableLiveData;
    }

    @VisibleForTesting
    public int getRotation() {
        getApplication().getDisplay().getDisplayInfo(this.mDisplayInfo);
        return this.mIsReverseDefaultRotation
                ? (this.mDisplayInfo.rotation + 1) % 4
                : this.mDisplayInfo.rotation;
    }

    @Override // androidx.lifecycle.ViewModel
    public final void onCleared() {
        this.mDisplayManager.unregisterDisplayListener(this.mDisplayListener);
    }
}
