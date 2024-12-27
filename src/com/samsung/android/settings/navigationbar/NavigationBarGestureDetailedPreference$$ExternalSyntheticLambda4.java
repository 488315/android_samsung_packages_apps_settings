package com.samsung.android.settings.navigationbar;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final /* synthetic */ class NavigationBarGestureDetailedPreference$$ExternalSyntheticLambda4
        implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ NavigationBarGestureDetailedPreference f$0;

    public /* synthetic */ NavigationBarGestureDetailedPreference$$ExternalSyntheticLambda4(
            NavigationBarGestureDetailedPreference navigationBarGestureDetailedPreference, int i) {
        this.$r8$classId = i;
        this.f$0 = navigationBarGestureDetailedPreference;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        NavigationBarGestureDetailedPreference navigationBarGestureDetailedPreference = this.f$0;
        switch (i) {
            case 0:
                navigationBarGestureDetailedPreference.updateDescription(
                        navigationBarGestureDetailedPreference.mAnimationIdx,
                        navigationBarGestureDetailedPreference.mA11yIdx);
                navigationBarGestureDetailedPreference.mSwipeSideAndBottomAnimator
                        .resumeAnimation();
                navigationBarGestureDetailedPreference.mIsWaiting = false;
                break;
            default:
                navigationBarGestureDetailedPreference.updateDescription(
                        navigationBarGestureDetailedPreference.mAnimationIdx,
                        navigationBarGestureDetailedPreference.mA11yIdx);
                navigationBarGestureDetailedPreference.mSwipeSideAndBottomAnimator
                        .resumeAnimation();
                navigationBarGestureDetailedPreference.mIsWaiting = false;
                break;
        }
    }
}
