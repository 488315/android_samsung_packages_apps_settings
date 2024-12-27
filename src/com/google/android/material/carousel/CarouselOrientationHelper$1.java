package com.google.android.material.carousel;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class CarouselOrientationHelper$1 {
    public final /* synthetic */ int $r8$classId;
    public final int orientation;
    public final /* synthetic */ CarouselLayoutManager val$carouselLayoutManager;

    public CarouselOrientationHelper$1(int i) {
        this.orientation = i;
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public CarouselOrientationHelper$1(CarouselLayoutManager carouselLayoutManager, int i) {
        this(1);
        this.$r8$classId = i;
        switch (i) {
            case 1:
                this.val$carouselLayoutManager = carouselLayoutManager;
                this(0);
                break;
            default:
                this.val$carouselLayoutManager = carouselLayoutManager;
                break;
        }
    }
}
