package com.samsung.android.settings.accessibility.advanced.flashnotification;

import androidx.fragment.app.FragmentActivity;

import com.samsung.android.settings.accessibility.advanced.flashnotification.CameraFlashNotiAppPickerSearchFragment.AnonymousClass1;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final /* synthetic */ class CameraFlashNotiAppPickerSearchFragment$$ExternalSyntheticLambda0
        implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ CameraFlashNotiAppPickerSearchFragment f$0;

    public /* synthetic */ CameraFlashNotiAppPickerSearchFragment$$ExternalSyntheticLambda0(
            CameraFlashNotiAppPickerSearchFragment cameraFlashNotiAppPickerSearchFragment, int i) {
        this.$r8$classId = i;
        this.f$0 = cameraFlashNotiAppPickerSearchFragment;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        CameraFlashNotiAppPickerSearchFragment cameraFlashNotiAppPickerSearchFragment = this.f$0;
        switch (i) {
            case 0:
                FragmentActivity activity = cameraFlashNotiAppPickerSearchFragment.getActivity();
                if (activity != null) {
                    activity.runOnUiThread(
                            new CameraFlashNotiAppPickerSearchFragment$$ExternalSyntheticLambda0(
                                    cameraFlashNotiAppPickerSearchFragment, 1));
                    break;
                }
                break;
            default:
                cameraFlashNotiAppPickerSearchFragment.appPickerListView.submitList(
                        FlashNotificationUtil.getCameraFlashNotiInstalledAppDataList(
                                cameraFlashNotiAppPickerSearchFragment.context,
                                cameraFlashNotiAppPickerSearchFragment.installedAppSet));
                cameraFlashNotiAppPickerSearchFragment.loadingViewController.showContent(false);
                cameraFlashNotiAppPickerSearchFragment.appPickerListView.mOnStateChangeListener =
                        cameraFlashNotiAppPickerSearchFragment.new AnonymousClass1();
                break;
        }
    }
}
