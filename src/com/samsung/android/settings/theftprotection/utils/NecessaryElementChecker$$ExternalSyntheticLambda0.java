package com.samsung.android.settings.theftprotection.utils;

import android.content.DialogInterface;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final /* synthetic */ class NecessaryElementChecker$$ExternalSyntheticLambda0
        implements DialogInterface.OnClickListener {
    public final /* synthetic */ int $r8$classId = 0;
    public final /* synthetic */ NecessaryElementChecker f$0;
    public final /* synthetic */ NecessaryElementChecker.Sequence f$1;
    public final /* synthetic */ NecessaryElementChecker.Sequence[] f$2;

    public /* synthetic */ NecessaryElementChecker$$ExternalSyntheticLambda0(
            NecessaryElementChecker necessaryElementChecker,
            NecessaryElementChecker.Sequence sequence,
            NecessaryElementChecker.Sequence[] sequenceArr) {
        this.f$0 = necessaryElementChecker;
        this.f$1 = sequence;
        this.f$2 = sequenceArr;
    }

    @Override // android.content.DialogInterface.OnClickListener
    public final void onClick(DialogInterface dialogInterface, int i) {
        switch (this.$r8$classId) {
            case 0:
                NecessaryElementChecker necessaryElementChecker = this.f$0;
                NecessaryElementChecker.Sequence sequence = this.f$1;
                NecessaryElementChecker.Sequence[] sequenceArr = this.f$2;
                sequence.resolveCondition(necessaryElementChecker.mContext);
                necessaryElementChecker.processNecessaryElements(
                        sequenceArr[sequence.ordinal() + 1]);
                break;
            default:
                NecessaryElementChecker necessaryElementChecker2 = this.f$0;
                NecessaryElementChecker.Sequence[] sequenceArr2 = this.f$2;
                NecessaryElementChecker.Sequence sequence2 = this.f$1;
                necessaryElementChecker2.getClass();
                necessaryElementChecker2.processNecessaryElements(
                        sequenceArr2[sequence2.ordinal() + 1]);
                break;
        }
    }

    public /* synthetic */ NecessaryElementChecker$$ExternalSyntheticLambda0(
            NecessaryElementChecker necessaryElementChecker,
            NecessaryElementChecker.Sequence[] sequenceArr,
            NecessaryElementChecker.Sequence sequence) {
        this.f$0 = necessaryElementChecker;
        this.f$2 = sequenceArr;
        this.f$1 = sequence;
    }
}
