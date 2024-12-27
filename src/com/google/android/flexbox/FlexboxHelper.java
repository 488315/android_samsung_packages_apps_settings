package com.google.android.flexbox;

import android.util.SparseIntArray;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.BackEventCompat$$ExternalSyntheticOutline0;
import androidx.appcompat.util.SeslRoundedCorner$$ExternalSyntheticOutline0;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class FlexboxHelper {
    public boolean[] mChildrenFrozen;
    public final FlexContainer mFlexContainer;
    public int[] mIndexToFlexLine;
    public long[] mMeasureSpecCache;
    public long[] mMeasuredSizeCache;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class FlexLinesResult {
        public int mChildState;
        public List mFlexLines;
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class Order implements Comparable {
        public int index;
        public int order;

        @Override // java.lang.Comparable
        public final int compareTo(Object obj) {
            Order order = (Order) obj;
            int i = this.order;
            int i2 = order.order;
            return i != i2 ? i - i2 : this.index - order.index;
        }

        public final String toString() {
            StringBuilder sb = new StringBuilder("Order{order=");
            sb.append(this.order);
            sb.append(", index=");
            return BackEventCompat$$ExternalSyntheticOutline0.m(sb, this.index, '}');
        }
    }

    public FlexboxHelper(FlexContainer flexContainer) {
        this.mFlexContainer = flexContainer;
    }

    public static List constructFlexLinesForAlignContentCenter(int i, List list, int i2) {
        int i3 = (i - i2) / 2;
        ArrayList arrayList = new ArrayList();
        FlexLine flexLine = new FlexLine();
        flexLine.mCrossSize = i3;
        int size = list.size();
        for (int i4 = 0; i4 < size; i4++) {
            if (i4 == 0) {
                arrayList.add(flexLine);
            }
            arrayList.add((FlexLine) list.get(i4));
            if (i4 == list.size() - 1) {
                arrayList.add(flexLine);
            }
        }
        return arrayList;
    }

    public static int[] sortOrdersIntoReorderedIndices(
            int i, List list, SparseIntArray sparseIntArray) {
        Collections.sort(list);
        sparseIntArray.clear();
        int[] iArr = new int[i];
        Iterator it = ((ArrayList) list).iterator();
        int i2 = 0;
        while (it.hasNext()) {
            Order order = (Order) it.next();
            int i3 = order.index;
            iArr[i2] = i3;
            sparseIntArray.append(i3, order.order);
            i2++;
        }
        return iArr;
    }

    public final void addFlexLine(List list, FlexLine flexLine, int i, int i2) {
        flexLine.mSumCrossSizeBefore = i2;
        this.mFlexContainer.onNewFlexLineAdded(flexLine);
        flexLine.mLastIndex = i;
        list.add(flexLine);
    }

    /* JADX WARN: Code restructure failed: missing block: B:165:0x020d, code lost:

       if (r8 < (r15 + r21)) goto L101;
    */
    /* JADX WARN: Removed duplicated region for block: B:101:0x0337  */
    /* JADX WARN: Removed duplicated region for block: B:107:0x036a  */
    /* JADX WARN: Removed duplicated region for block: B:112:0x037b  */
    /* JADX WARN: Removed duplicated region for block: B:120:0x039e A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:130:0x031f  */
    /* JADX WARN: Removed duplicated region for block: B:131:0x0313  */
    /* JADX WARN: Removed duplicated region for block: B:132:0x0308  */
    /* JADX WARN: Removed duplicated region for block: B:133:0x02e3  */
    /* JADX WARN: Removed duplicated region for block: B:134:0x02d6  */
    /* JADX WARN: Removed duplicated region for block: B:135:0x02cb  */
    /* JADX WARN: Removed duplicated region for block: B:136:0x02b4  */
    /* JADX WARN: Removed duplicated region for block: B:137:0x02a4  */
    /* JADX WARN: Removed duplicated region for block: B:76:0x02a2  */
    /* JADX WARN: Removed duplicated region for block: B:79:0x02b2  */
    /* JADX WARN: Removed duplicated region for block: B:82:0x02bc  */
    /* JADX WARN: Removed duplicated region for block: B:85:0x02c6  */
    /* JADX WARN: Removed duplicated region for block: B:87:0x02d1  */
    /* JADX WARN: Removed duplicated region for block: B:90:0x02de  */
    /* JADX WARN: Removed duplicated region for block: B:93:0x0303  */
    /* JADX WARN: Removed duplicated region for block: B:95:0x030e  */
    /* JADX WARN: Removed duplicated region for block: B:98:0x031a  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void calculateFlexLines(
            com.google.android.flexbox.FlexboxHelper.FlexLinesResult r28,
            int r29,
            int r30,
            int r31,
            int r32,
            int r33,
            java.util.List r34) {
        /*
            Method dump skipped, instructions count: 957
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.google.android.flexbox.FlexboxHelper.calculateFlexLines(com.google.android.flexbox.FlexboxHelper$FlexLinesResult,"
                    + " int, int, int, int, int, java.util.List):void");
    }

    /* JADX WARN: Removed duplicated region for block: B:12:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:13:0x0032  */
    /* JADX WARN: Removed duplicated region for block: B:7:0x002d  */
    /* JADX WARN: Removed duplicated region for block: B:9:0x0040  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void checkSizeConstraints(android.view.View r7, int r8) {
        /*
            r6 = this;
            android.view.ViewGroup$LayoutParams r0 = r7.getLayoutParams()
            com.google.android.flexbox.FlexItem r0 = (com.google.android.flexbox.FlexItem) r0
            int r1 = r7.getMeasuredWidth()
            int r2 = r7.getMeasuredHeight()
            int r3 = r0.getMinWidth()
            r4 = 1
            if (r1 >= r3) goto L1b
            int r1 = r0.getMinWidth()
        L19:
            r3 = r4
            goto L27
        L1b:
            int r3 = r0.getMaxWidth()
            if (r1 <= r3) goto L26
            int r1 = r0.getMaxWidth()
            goto L19
        L26:
            r3 = 0
        L27:
            int r5 = r0.getMinHeight()
            if (r2 >= r5) goto L32
            int r2 = r0.getMinHeight()
            goto L3e
        L32:
            int r5 = r0.getMaxHeight()
            if (r2 <= r5) goto L3d
            int r2 = r0.getMaxHeight()
            goto L3e
        L3d:
            r4 = r3
        L3e:
            if (r4 == 0) goto L55
            r0 = 1073741824(0x40000000, float:2.0)
            int r1 = android.view.View.MeasureSpec.makeMeasureSpec(r1, r0)
            int r0 = android.view.View.MeasureSpec.makeMeasureSpec(r2, r0)
            r7.measure(r1, r0)
            r6.updateMeasureCache(r7, r8, r1, r0)
            com.google.android.flexbox.FlexContainer r6 = r6.mFlexContainer
            r6.updateViewCache(r7, r8)
        L55:
            return
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.google.android.flexbox.FlexboxHelper.checkSizeConstraints(android.view.View,"
                    + " int):void");
    }

    public final void clearFlexLines(int i, List list) {
        int i2 = this.mIndexToFlexLine[i];
        if (i2 == -1) {
            i2 = 0;
        }
        if (list.size() > i2) {
            list.subList(i2, list.size()).clear();
        }
        int[] iArr = this.mIndexToFlexLine;
        int length = iArr.length - 1;
        if (i > length) {
            Arrays.fill(iArr, -1);
        } else {
            Arrays.fill(iArr, i, length, -1);
        }
        long[] jArr = this.mMeasureSpecCache;
        int length2 = jArr.length - 1;
        if (i > length2) {
            Arrays.fill(jArr, 0L);
        } else {
            Arrays.fill(jArr, i, length2, 0L);
        }
    }

    public final List createOrders(int i) {
        ArrayList arrayList = new ArrayList(i);
        for (int i2 = 0; i2 < i; i2++) {
            FlexItem flexItem = (FlexItem) this.mFlexContainer.getFlexItemAt(i2).getLayoutParams();
            Order order = new Order();
            order.order = flexItem.getOrder();
            order.index = i2;
            arrayList.add(order);
        }
        return arrayList;
    }

    public final void determineCrossSize(int i, int i2, int i3) {
        int i4;
        int i5;
        FlexContainer flexContainer = this.mFlexContainer;
        int flexDirection = flexContainer.getFlexDirection();
        if (flexDirection == 0 || flexDirection == 1) {
            int mode = View.MeasureSpec.getMode(i2);
            int size = View.MeasureSpec.getSize(i2);
            i4 = mode;
            i5 = size;
        } else {
            if (flexDirection != 2 && flexDirection != 3) {
                throw new IllegalArgumentException(
                        SeslRoundedCorner$$ExternalSyntheticOutline0.m(
                                flexDirection, "Invalid flex direction: "));
            }
            i4 = View.MeasureSpec.getMode(i);
            i5 = View.MeasureSpec.getSize(i);
        }
        List<FlexLine> flexLinesInternal = flexContainer.getFlexLinesInternal();
        if (i4 == 1073741824) {
            int sumOfCrossSize = flexContainer.getSumOfCrossSize() + i3;
            int i6 = 0;
            if (flexLinesInternal.size() == 1) {
                ((FlexLine) flexLinesInternal.get(0)).mCrossSize = i5 - i3;
                return;
            }
            if (flexLinesInternal.size() >= 2) {
                int alignContent = flexContainer.getAlignContent();
                if (alignContent == 1) {
                    FlexLine flexLine = new FlexLine();
                    flexLine.mCrossSize = i5 - sumOfCrossSize;
                    flexLinesInternal.add(0, flexLine);
                    return;
                }
                if (alignContent == 2) {
                    flexContainer.setFlexLines(
                            constructFlexLinesForAlignContentCenter(
                                    i5, flexLinesInternal, sumOfCrossSize));
                    return;
                }
                if (alignContent == 3) {
                    if (sumOfCrossSize >= i5) {
                        return;
                    }
                    float size2 = (i5 - sumOfCrossSize) / (flexLinesInternal.size() - 1);
                    ArrayList arrayList = new ArrayList();
                    int size3 = flexLinesInternal.size();
                    float f = 0.0f;
                    while (i6 < size3) {
                        arrayList.add((FlexLine) flexLinesInternal.get(i6));
                        if (i6 != flexLinesInternal.size() - 1) {
                            FlexLine flexLine2 = new FlexLine();
                            if (i6 == flexLinesInternal.size() - 2) {
                                flexLine2.mCrossSize = Math.round(f + size2);
                                f = 0.0f;
                            } else {
                                flexLine2.mCrossSize = Math.round(size2);
                            }
                            int i7 = flexLine2.mCrossSize;
                            float f2 = (size2 - i7) + f;
                            if (f2 > 1.0f) {
                                flexLine2.mCrossSize = i7 + 1;
                                f2 -= 1.0f;
                            } else if (f2 < -1.0f) {
                                flexLine2.mCrossSize = i7 - 1;
                                f2 += 1.0f;
                            }
                            f = f2;
                            arrayList.add(flexLine2);
                        }
                        i6++;
                    }
                    flexContainer.setFlexLines(arrayList);
                    return;
                }
                if (alignContent == 4) {
                    if (sumOfCrossSize >= i5) {
                        flexContainer.setFlexLines(
                                constructFlexLinesForAlignContentCenter(
                                        i5, flexLinesInternal, sumOfCrossSize));
                        return;
                    }
                    int size4 = (i5 - sumOfCrossSize) / (flexLinesInternal.size() * 2);
                    ArrayList arrayList2 = new ArrayList();
                    FlexLine flexLine3 = new FlexLine();
                    flexLine3.mCrossSize = size4;
                    for (FlexLine flexLine4 : flexLinesInternal) {
                        arrayList2.add(flexLine3);
                        arrayList2.add(flexLine4);
                        arrayList2.add(flexLine3);
                    }
                    flexContainer.setFlexLines(arrayList2);
                    return;
                }
                if (alignContent == 5 && sumOfCrossSize < i5) {
                    float size5 = (i5 - sumOfCrossSize) / flexLinesInternal.size();
                    int size6 = flexLinesInternal.size();
                    float f3 = 0.0f;
                    while (i6 < size6) {
                        FlexLine flexLine5 = (FlexLine) flexLinesInternal.get(i6);
                        float f4 = flexLine5.mCrossSize + size5;
                        if (i6 == flexLinesInternal.size() - 1) {
                            f4 += f3;
                            f3 = 0.0f;
                        }
                        int round = Math.round(f4);
                        float f5 = (f4 - round) + f3;
                        if (f5 > 1.0f) {
                            round++;
                            f5 -= 1.0f;
                        } else if (f5 < -1.0f) {
                            round--;
                            f5 += 1.0f;
                        }
                        f3 = f5;
                        flexLine5.mCrossSize = round;
                        i6++;
                    }
                }
            }
        }
    }

    public final void determineMainSize(int i, int i2, int i3) {
        int size;
        int paddingLeft;
        int paddingRight;
        FlexContainer flexContainer = this.mFlexContainer;
        int flexItemCount = flexContainer.getFlexItemCount();
        boolean[] zArr = this.mChildrenFrozen;
        if (zArr == null) {
            this.mChildrenFrozen = new boolean[Math.max(flexItemCount, 10)];
        } else if (zArr.length < flexItemCount) {
            this.mChildrenFrozen = new boolean[Math.max(zArr.length * 2, flexItemCount)];
        } else {
            Arrays.fill(zArr, false);
        }
        if (i3 >= flexContainer.getFlexItemCount()) {
            return;
        }
        int flexDirection = flexContainer.getFlexDirection();
        int flexDirection2 = flexContainer.getFlexDirection();
        if (flexDirection2 == 0 || flexDirection2 == 1) {
            int mode = View.MeasureSpec.getMode(i);
            size = View.MeasureSpec.getSize(i);
            int largestMainSize = flexContainer.getLargestMainSize();
            if (mode != 1073741824) {
                size = Math.min(largestMainSize, size);
            }
            paddingLeft = flexContainer.getPaddingLeft();
            paddingRight = flexContainer.getPaddingRight();
        } else {
            if (flexDirection2 != 2 && flexDirection2 != 3) {
                throw new IllegalArgumentException(
                        SeslRoundedCorner$$ExternalSyntheticOutline0.m(
                                flexDirection, "Invalid flex direction: "));
            }
            int mode2 = View.MeasureSpec.getMode(i2);
            size = View.MeasureSpec.getSize(i2);
            if (mode2 != 1073741824) {
                size = flexContainer.getLargestMainSize();
            }
            paddingLeft = flexContainer.getPaddingTop();
            paddingRight = flexContainer.getPaddingBottom();
        }
        int i4 = paddingRight + paddingLeft;
        int[] iArr = this.mIndexToFlexLine;
        List flexLinesInternal = flexContainer.getFlexLinesInternal();
        int size2 = flexLinesInternal.size();
        for (int i5 = iArr != null ? iArr[i3] : 0; i5 < size2; i5++) {
            FlexLine flexLine = (FlexLine) flexLinesInternal.get(i5);
            int i6 = flexLine.mMainSize;
            if (i6 < size && flexLine.mAnyItemsHaveFlexGrow) {
                expandFlexItems(i, i2, flexLine, size, i4, false);
            } else if (i6 > size && flexLine.mAnyItemsHaveFlexShrink) {
                shrinkFlexItems(i, i2, flexLine, size, i4, false);
            }
        }
    }

    public final void ensureIndexToFlexLine(int i) {
        int[] iArr = this.mIndexToFlexLine;
        if (iArr == null) {
            this.mIndexToFlexLine = new int[Math.max(i, 10)];
        } else if (iArr.length < i) {
            this.mIndexToFlexLine =
                    Arrays.copyOf(this.mIndexToFlexLine, Math.max(iArr.length * 2, i));
        }
    }

    public final void ensureMeasureSpecCache(int i) {
        long[] jArr = this.mMeasureSpecCache;
        if (jArr == null) {
            this.mMeasureSpecCache = new long[Math.max(i, 10)];
        } else if (jArr.length < i) {
            this.mMeasureSpecCache =
                    Arrays.copyOf(this.mMeasureSpecCache, Math.max(jArr.length * 2, i));
        }
    }

    public final void ensureMeasuredSizeCache(int i) {
        long[] jArr = this.mMeasuredSizeCache;
        if (jArr == null) {
            this.mMeasuredSizeCache = new long[Math.max(i, 10)];
        } else if (jArr.length < i) {
            this.mMeasuredSizeCache =
                    Arrays.copyOf(this.mMeasuredSizeCache, Math.max(jArr.length * 2, i));
        }
    }

    public final void expandFlexItems(int i, int i2, FlexLine flexLine, int i3, int i4, boolean z) {
        int i5;
        int i6;
        int i7;
        boolean z2;
        int i8;
        double d;
        double d2;
        float f = flexLine.mTotalFlexGrow;
        if (f <= 0.0f || i3 < (i5 = flexLine.mMainSize)) {
            return;
        }
        float f2 = (i3 - i5) / f;
        flexLine.mMainSize = i4 + flexLine.mDividerLengthInMainSize;
        if (!z) {
            flexLine.mCrossSize = Integer.MIN_VALUE;
        }
        int i9 = 0;
        boolean z3 = false;
        int i10 = 0;
        float f3 = 0.0f;
        while (i9 < flexLine.mItemCount) {
            int i11 = flexLine.mFirstIndex + i9;
            FlexContainer flexContainer = this.mFlexContainer;
            View reorderedFlexItemAt = flexContainer.getReorderedFlexItemAt(i11);
            if (reorderedFlexItemAt == null || reorderedFlexItemAt.getVisibility() == 8) {
                i6 = i5;
                i7 = i10;
                z3 = z3;
            } else {
                FlexItem flexItem = (FlexItem) reorderedFlexItemAt.getLayoutParams();
                int flexDirection = flexContainer.getFlexDirection();
                if (flexDirection == 0 || flexDirection == 1) {
                    i6 = i5;
                    int measuredWidth = reorderedFlexItemAt.getMeasuredWidth();
                    long[] jArr = this.mMeasuredSizeCache;
                    if (jArr != null) {
                        measuredWidth = (int) jArr[i11];
                    }
                    int measuredHeight = reorderedFlexItemAt.getMeasuredHeight();
                    long[] jArr2 = this.mMeasuredSizeCache;
                    if (jArr2 != null) {
                        long j = jArr2[i11];
                        z2 = z3;
                        i8 = i10;
                        measuredHeight = (int) (j >> 32);
                    } else {
                        z2 = z3;
                        i8 = i10;
                    }
                    if (this.mChildrenFrozen[i11] || flexItem.getFlexGrow() <= 0.0f) {
                        z3 = z2;
                    } else {
                        float flexGrow = (flexItem.getFlexGrow() * f2) + measuredWidth;
                        if (i9 == flexLine.mItemCount - 1) {
                            flexGrow += f3;
                            f3 = 0.0f;
                        }
                        int round = Math.round(flexGrow);
                        if (round > flexItem.getMaxWidth()) {
                            round = flexItem.getMaxWidth();
                            this.mChildrenFrozen[i11] = true;
                            flexLine.mTotalFlexGrow -= flexItem.getFlexGrow();
                            z3 = true;
                        } else {
                            float f4 = (flexGrow - round) + f3;
                            double d3 = f4;
                            if (d3 > 1.0d) {
                                round++;
                                d = d3 - 1.0d;
                            } else {
                                if (d3 < -1.0d) {
                                    round--;
                                    d = d3 + 1.0d;
                                }
                                f3 = f4;
                                z3 = z2;
                            }
                            f4 = (float) d;
                            f3 = f4;
                            z3 = z2;
                        }
                        int childHeightMeasureSpecInternal =
                                getChildHeightMeasureSpecInternal(
                                        i2, flexItem, flexLine.mSumCrossSizeBefore);
                        int makeMeasureSpec = View.MeasureSpec.makeMeasureSpec(round, 1073741824);
                        reorderedFlexItemAt.measure(
                                makeMeasureSpec, childHeightMeasureSpecInternal);
                        int measuredWidth2 = reorderedFlexItemAt.getMeasuredWidth();
                        int measuredHeight2 = reorderedFlexItemAt.getMeasuredHeight();
                        updateMeasureCache(
                                reorderedFlexItemAt,
                                i11,
                                makeMeasureSpec,
                                childHeightMeasureSpecInternal);
                        flexContainer.updateViewCache(reorderedFlexItemAt, i11);
                        measuredWidth = measuredWidth2;
                        measuredHeight = measuredHeight2;
                    }
                    int max =
                            Math.max(
                                    i8,
                                    flexContainer.getDecorationLengthCrossAxis(reorderedFlexItemAt)
                                            + flexItem.getMarginBottom()
                                            + flexItem.getMarginTop()
                                            + measuredHeight);
                    flexLine.mMainSize =
                            flexItem.getMarginRight()
                                    + flexItem.getMarginLeft()
                                    + measuredWidth
                                    + flexLine.mMainSize;
                    i7 = max;
                } else {
                    int measuredHeight3 = reorderedFlexItemAt.getMeasuredHeight();
                    long[] jArr3 = this.mMeasuredSizeCache;
                    if (jArr3 != null) {
                        long j2 = jArr3[i11];
                        i6 = i5;
                        measuredHeight3 = (int) (j2 >> 32);
                    } else {
                        i6 = i5;
                    }
                    int measuredWidth3 = reorderedFlexItemAt.getMeasuredWidth();
                    long[] jArr4 = this.mMeasuredSizeCache;
                    if (jArr4 != null) {
                        measuredWidth3 = (int) jArr4[i11];
                    }
                    if (!this.mChildrenFrozen[i11] && flexItem.getFlexGrow() > 0.0f) {
                        float flexGrow2 = (flexItem.getFlexGrow() * f2) + measuredHeight3;
                        if (i9 == flexLine.mItemCount - 1) {
                            flexGrow2 += f3;
                            f3 = 0.0f;
                        }
                        int round2 = Math.round(flexGrow2);
                        if (round2 > flexItem.getMaxHeight()) {
                            round2 = flexItem.getMaxHeight();
                            this.mChildrenFrozen[i11] = true;
                            flexLine.mTotalFlexGrow -= flexItem.getFlexGrow();
                            z3 = true;
                        } else {
                            float f5 = (flexGrow2 - round2) + f3;
                            double d4 = f5;
                            if (d4 > 1.0d) {
                                round2++;
                                d2 = d4 - 1.0d;
                            } else {
                                if (d4 < -1.0d) {
                                    round2--;
                                    d2 = d4 + 1.0d;
                                }
                                f3 = f5;
                            }
                            f5 = (float) d2;
                            f3 = f5;
                        }
                        int childWidthMeasureSpecInternal =
                                getChildWidthMeasureSpecInternal(
                                        i, flexItem, flexLine.mSumCrossSizeBefore);
                        int makeMeasureSpec2 = View.MeasureSpec.makeMeasureSpec(round2, 1073741824);
                        reorderedFlexItemAt.measure(
                                childWidthMeasureSpecInternal, makeMeasureSpec2);
                        int measuredWidth4 = reorderedFlexItemAt.getMeasuredWidth();
                        int measuredHeight4 = reorderedFlexItemAt.getMeasuredHeight();
                        updateMeasureCache(
                                reorderedFlexItemAt,
                                i11,
                                childWidthMeasureSpecInternal,
                                makeMeasureSpec2);
                        flexContainer.updateViewCache(reorderedFlexItemAt, i11);
                        measuredWidth3 = measuredWidth4;
                        measuredHeight3 = measuredHeight4;
                    }
                    i7 =
                            Math.max(
                                    i10,
                                    flexContainer.getDecorationLengthCrossAxis(reorderedFlexItemAt)
                                            + flexItem.getMarginRight()
                                            + flexItem.getMarginLeft()
                                            + measuredWidth3);
                    flexLine.mMainSize =
                            flexItem.getMarginBottom()
                                    + flexItem.getMarginTop()
                                    + measuredHeight3
                                    + flexLine.mMainSize;
                }
                flexLine.mCrossSize = Math.max(flexLine.mCrossSize, i7);
            }
            i9++;
            i5 = i6;
            i10 = i7;
        }
        int i12 = i5;
        if (!z3 || i12 == flexLine.mMainSize) {
            return;
        }
        expandFlexItems(i, i2, flexLine, i3, i4, true);
    }

    public final int getChildHeightMeasureSpecInternal(int i, FlexItem flexItem, int i2) {
        FlexContainer flexContainer = this.mFlexContainer;
        int childHeightMeasureSpec =
                flexContainer.getChildHeightMeasureSpec(
                        i,
                        flexItem.getMarginBottom()
                                + flexItem.getMarginTop()
                                + flexContainer.getPaddingBottom()
                                + flexContainer.getPaddingTop()
                                + i2,
                        flexItem.getHeight());
        int size = View.MeasureSpec.getSize(childHeightMeasureSpec);
        return size > flexItem.getMaxHeight()
                ? View.MeasureSpec.makeMeasureSpec(
                        flexItem.getMaxHeight(), View.MeasureSpec.getMode(childHeightMeasureSpec))
                : size < flexItem.getMinHeight()
                        ? View.MeasureSpec.makeMeasureSpec(
                                flexItem.getMinHeight(),
                                View.MeasureSpec.getMode(childHeightMeasureSpec))
                        : childHeightMeasureSpec;
    }

    public final int getChildWidthMeasureSpecInternal(int i, FlexItem flexItem, int i2) {
        FlexContainer flexContainer = this.mFlexContainer;
        int childWidthMeasureSpec =
                flexContainer.getChildWidthMeasureSpec(
                        i,
                        flexItem.getMarginRight()
                                + flexItem.getMarginLeft()
                                + flexContainer.getPaddingRight()
                                + flexContainer.getPaddingLeft()
                                + i2,
                        flexItem.getWidth());
        int size = View.MeasureSpec.getSize(childWidthMeasureSpec);
        return size > flexItem.getMaxWidth()
                ? View.MeasureSpec.makeMeasureSpec(
                        flexItem.getMaxWidth(), View.MeasureSpec.getMode(childWidthMeasureSpec))
                : size < flexItem.getMinWidth()
                        ? View.MeasureSpec.makeMeasureSpec(
                                flexItem.getMinWidth(),
                                View.MeasureSpec.getMode(childWidthMeasureSpec))
                        : childWidthMeasureSpec;
    }

    public final void layoutSingleChildHorizontal(
            View view, FlexLine flexLine, int i, int i2, int i3, int i4) {
        FlexItem flexItem = (FlexItem) view.getLayoutParams();
        FlexContainer flexContainer = this.mFlexContainer;
        int alignItems = flexContainer.getAlignItems();
        if (flexItem.getAlignSelf() != -1) {
            alignItems = flexItem.getAlignSelf();
        }
        int i5 = flexLine.mCrossSize;
        if (alignItems != 0) {
            if (alignItems == 1) {
                if (flexContainer.getFlexWrap() != 2) {
                    int i6 = i2 + i5;
                    view.layout(
                            i,
                            (i6 - view.getMeasuredHeight()) - flexItem.getMarginBottom(),
                            i3,
                            i6 - flexItem.getMarginBottom());
                    return;
                }
                int measuredHeight = view.getMeasuredHeight();
                int marginTop = flexItem.getMarginTop() + measuredHeight + (i2 - i5);
                int measuredHeight2 = view.getMeasuredHeight();
                view.layout(
                        i, marginTop, i3, flexItem.getMarginTop() + measuredHeight2 + (i4 - i5));
                return;
            }
            if (alignItems == 2) {
                int marginTop2 =
                        ((flexItem.getMarginTop() + (i5 - view.getMeasuredHeight()))
                                        - flexItem.getMarginBottom())
                                / 2;
                if (flexContainer.getFlexWrap() != 2) {
                    int i7 = i2 + marginTop2;
                    view.layout(i, i7, i3, view.getMeasuredHeight() + i7);
                    return;
                } else {
                    int i8 = i2 - marginTop2;
                    view.layout(i, i8, i3, view.getMeasuredHeight() + i8);
                    return;
                }
            }
            if (alignItems == 3) {
                if (flexContainer.getFlexWrap() != 2) {
                    int max =
                            Math.max(
                                    flexLine.mMaxBaseline - view.getBaseline(),
                                    flexItem.getMarginTop());
                    view.layout(i, i2 + max, i3, i4 + max);
                    return;
                } else {
                    int max2 =
                            Math.max(
                                    view.getBaseline()
                                            + (flexLine.mMaxBaseline - view.getMeasuredHeight()),
                                    flexItem.getMarginBottom());
                    view.layout(i, i2 - max2, i3, i4 - max2);
                    return;
                }
            }
            if (alignItems != 4) {
                return;
            }
        }
        if (flexContainer.getFlexWrap() != 2) {
            view.layout(i, flexItem.getMarginTop() + i2, i3, flexItem.getMarginTop() + i4);
        } else {
            view.layout(i, i2 - flexItem.getMarginBottom(), i3, i4 - flexItem.getMarginBottom());
        }
    }

    public final void layoutSingleChildVertical(
            View view, FlexLine flexLine, boolean z, int i, int i2, int i3, int i4) {
        FlexItem flexItem = (FlexItem) view.getLayoutParams();
        int alignItems = this.mFlexContainer.getAlignItems();
        if (flexItem.getAlignSelf() != -1) {
            alignItems = flexItem.getAlignSelf();
        }
        int i5 = flexLine.mCrossSize;
        if (alignItems != 0) {
            if (alignItems == 1) {
                if (!z) {
                    view.layout(
                            ((i + i5) - view.getMeasuredWidth()) - flexItem.getMarginRight(),
                            i2,
                            ((i3 + i5) - view.getMeasuredWidth()) - flexItem.getMarginRight(),
                            i4);
                    return;
                }
                int measuredWidth = view.getMeasuredWidth();
                view.layout(
                        flexItem.getMarginLeft() + measuredWidth + (i - i5),
                        i2,
                        flexItem.getMarginLeft() + view.getMeasuredWidth() + (i3 - i5),
                        i4);
                return;
            }
            if (alignItems == 2) {
                ViewGroup.MarginLayoutParams marginLayoutParams =
                        (ViewGroup.MarginLayoutParams) view.getLayoutParams();
                int marginStart =
                        ((marginLayoutParams.getMarginStart() + (i5 - view.getMeasuredWidth()))
                                        - marginLayoutParams.getMarginEnd())
                                / 2;
                if (z) {
                    view.layout(i - marginStart, i2, i3 - marginStart, i4);
                    return;
                } else {
                    view.layout(i + marginStart, i2, i3 + marginStart, i4);
                    return;
                }
            }
            if (alignItems != 3 && alignItems != 4) {
                return;
            }
        }
        if (z) {
            view.layout(i - flexItem.getMarginRight(), i2, i3 - flexItem.getMarginRight(), i4);
        } else {
            view.layout(flexItem.getMarginLeft() + i, i2, flexItem.getMarginLeft() + i3, i4);
        }
    }

    public long makeCombinedLong(int i, int i2) {
        return (i & 4294967295L) | (i2 << 32);
    }

    public final void shrinkFlexItems(int i, int i2, FlexLine flexLine, int i3, int i4, boolean z) {
        int i5;
        boolean z2;
        int i6;
        int i7 = flexLine.mMainSize;
        float f = flexLine.mTotalFlexShrink;
        if (f <= 0.0f || i3 > i7) {
            return;
        }
        float f2 = (i7 - i3) / f;
        flexLine.mMainSize = i4 + flexLine.mDividerLengthInMainSize;
        if (!z) {
            flexLine.mCrossSize = Integer.MIN_VALUE;
        }
        int i8 = 0;
        boolean z3 = false;
        int i9 = 0;
        float f3 = 0.0f;
        while (i8 < flexLine.mItemCount) {
            int i10 = flexLine.mFirstIndex + i8;
            FlexContainer flexContainer = this.mFlexContainer;
            View reorderedFlexItemAt = flexContainer.getReorderedFlexItemAt(i10);
            if (reorderedFlexItemAt == null || reorderedFlexItemAt.getVisibility() == 8) {
                i5 = i9;
                z3 = z3;
            } else {
                FlexItem flexItem = (FlexItem) reorderedFlexItemAt.getLayoutParams();
                int flexDirection = flexContainer.getFlexDirection();
                if (flexDirection == 0 || flexDirection == 1) {
                    int measuredWidth = reorderedFlexItemAt.getMeasuredWidth();
                    long[] jArr = this.mMeasuredSizeCache;
                    if (jArr != null) {
                        measuredWidth = (int) jArr[i10];
                    }
                    int measuredHeight = reorderedFlexItemAt.getMeasuredHeight();
                    long[] jArr2 = this.mMeasuredSizeCache;
                    if (jArr2 != null) {
                        long j = jArr2[i10];
                        z2 = z3;
                        i6 = i9;
                        measuredHeight = (int) (j >> 32);
                    } else {
                        z2 = z3;
                        i6 = i9;
                    }
                    if (this.mChildrenFrozen[i10] || flexItem.getFlexShrink() <= 0.0f) {
                        z3 = z2;
                    } else {
                        float flexShrink = measuredWidth - (flexItem.getFlexShrink() * f2);
                        if (i8 == flexLine.mItemCount - 1) {
                            flexShrink += f3;
                            f3 = 0.0f;
                        }
                        int round = Math.round(flexShrink);
                        if (round < flexItem.getMinWidth()) {
                            round = flexItem.getMinWidth();
                            this.mChildrenFrozen[i10] = true;
                            flexLine.mTotalFlexShrink -= flexItem.getFlexShrink();
                            z3 = true;
                        } else {
                            float f4 = (flexShrink - round) + f3;
                            double d = f4;
                            if (d > 1.0d) {
                                round++;
                                f4 -= 1.0f;
                            } else if (d < -1.0d) {
                                round--;
                                f4 += 1.0f;
                            }
                            f3 = f4;
                            z3 = z2;
                        }
                        int childHeightMeasureSpecInternal =
                                getChildHeightMeasureSpecInternal(
                                        i2, flexItem, flexLine.mSumCrossSizeBefore);
                        int makeMeasureSpec = View.MeasureSpec.makeMeasureSpec(round, 1073741824);
                        reorderedFlexItemAt.measure(
                                makeMeasureSpec, childHeightMeasureSpecInternal);
                        int measuredWidth2 = reorderedFlexItemAt.getMeasuredWidth();
                        int measuredHeight2 = reorderedFlexItemAt.getMeasuredHeight();
                        updateMeasureCache(
                                reorderedFlexItemAt,
                                i10,
                                makeMeasureSpec,
                                childHeightMeasureSpecInternal);
                        flexContainer.updateViewCache(reorderedFlexItemAt, i10);
                        measuredWidth = measuredWidth2;
                        measuredHeight = measuredHeight2;
                    }
                    int max =
                            Math.max(
                                    i6,
                                    flexContainer.getDecorationLengthCrossAxis(reorderedFlexItemAt)
                                            + flexItem.getMarginBottom()
                                            + flexItem.getMarginTop()
                                            + measuredHeight);
                    flexLine.mMainSize =
                            flexItem.getMarginRight()
                                    + flexItem.getMarginLeft()
                                    + measuredWidth
                                    + flexLine.mMainSize;
                    i5 = max;
                } else {
                    int measuredHeight3 = reorderedFlexItemAt.getMeasuredHeight();
                    long[] jArr3 = this.mMeasuredSizeCache;
                    if (jArr3 != null) {
                        measuredHeight3 = (int) (jArr3[i10] >> 32);
                    }
                    int measuredWidth3 = reorderedFlexItemAt.getMeasuredWidth();
                    long[] jArr4 = this.mMeasuredSizeCache;
                    if (jArr4 != null) {
                        measuredWidth3 = (int) jArr4[i10];
                    }
                    if (!this.mChildrenFrozen[i10] && flexItem.getFlexShrink() > 0.0f) {
                        float flexShrink2 = measuredHeight3 - (flexItem.getFlexShrink() * f2);
                        if (i8 == flexLine.mItemCount - 1) {
                            flexShrink2 += f3;
                            f3 = 0.0f;
                        }
                        int round2 = Math.round(flexShrink2);
                        if (round2 < flexItem.getMinHeight()) {
                            round2 = flexItem.getMinHeight();
                            this.mChildrenFrozen[i10] = true;
                            flexLine.mTotalFlexShrink -= flexItem.getFlexShrink();
                            z3 = true;
                        } else {
                            float f5 = (flexShrink2 - round2) + f3;
                            double d2 = f5;
                            if (d2 > 1.0d) {
                                round2++;
                                f5 -= 1.0f;
                            } else if (d2 < -1.0d) {
                                round2--;
                                f5 += 1.0f;
                            }
                            f3 = f5;
                        }
                        int childWidthMeasureSpecInternal =
                                getChildWidthMeasureSpecInternal(
                                        i, flexItem, flexLine.mSumCrossSizeBefore);
                        int makeMeasureSpec2 = View.MeasureSpec.makeMeasureSpec(round2, 1073741824);
                        reorderedFlexItemAt.measure(
                                childWidthMeasureSpecInternal, makeMeasureSpec2);
                        int measuredWidth4 = reorderedFlexItemAt.getMeasuredWidth();
                        int measuredHeight4 = reorderedFlexItemAt.getMeasuredHeight();
                        updateMeasureCache(
                                reorderedFlexItemAt,
                                i10,
                                childWidthMeasureSpecInternal,
                                makeMeasureSpec2);
                        flexContainer.updateViewCache(reorderedFlexItemAt, i10);
                        measuredWidth3 = measuredWidth4;
                        measuredHeight3 = measuredHeight4;
                    }
                    i5 =
                            Math.max(
                                    i9,
                                    flexContainer.getDecorationLengthCrossAxis(reorderedFlexItemAt)
                                            + flexItem.getMarginRight()
                                            + flexItem.getMarginLeft()
                                            + measuredWidth3);
                    flexLine.mMainSize =
                            flexItem.getMarginBottom()
                                    + flexItem.getMarginTop()
                                    + measuredHeight3
                                    + flexLine.mMainSize;
                }
                flexLine.mCrossSize = Math.max(flexLine.mCrossSize, i5);
            }
            i8++;
            i9 = i5;
        }
        if (!z3 || i7 == flexLine.mMainSize) {
            return;
        }
        shrinkFlexItems(i, i2, flexLine, i3, i4, true);
    }

    public final void stretchViewHorizontally(View view, int i, int i2) {
        FlexItem flexItem = (FlexItem) view.getLayoutParams();
        int marginLeft = (i - flexItem.getMarginLeft()) - flexItem.getMarginRight();
        FlexContainer flexContainer = this.mFlexContainer;
        int min =
                Math.min(
                        Math.max(
                                marginLeft - flexContainer.getDecorationLengthCrossAxis(view),
                                flexItem.getMinWidth()),
                        flexItem.getMaxWidth());
        long[] jArr = this.mMeasuredSizeCache;
        int makeMeasureSpec =
                View.MeasureSpec.makeMeasureSpec(
                        jArr != null ? (int) (jArr[i2] >> 32) : view.getMeasuredHeight(),
                        1073741824);
        int makeMeasureSpec2 = View.MeasureSpec.makeMeasureSpec(min, 1073741824);
        view.measure(makeMeasureSpec2, makeMeasureSpec);
        updateMeasureCache(view, i2, makeMeasureSpec2, makeMeasureSpec);
        flexContainer.updateViewCache(view, i2);
    }

    public final void stretchViewVertically(View view, int i, int i2) {
        FlexItem flexItem = (FlexItem) view.getLayoutParams();
        int marginTop = (i - flexItem.getMarginTop()) - flexItem.getMarginBottom();
        FlexContainer flexContainer = this.mFlexContainer;
        int min =
                Math.min(
                        Math.max(
                                marginTop - flexContainer.getDecorationLengthCrossAxis(view),
                                flexItem.getMinHeight()),
                        flexItem.getMaxHeight());
        long[] jArr = this.mMeasuredSizeCache;
        int makeMeasureSpec =
                View.MeasureSpec.makeMeasureSpec(
                        jArr != null ? (int) jArr[i2] : view.getMeasuredWidth(), 1073741824);
        int makeMeasureSpec2 = View.MeasureSpec.makeMeasureSpec(min, 1073741824);
        view.measure(makeMeasureSpec, makeMeasureSpec2);
        updateMeasureCache(view, i2, makeMeasureSpec, makeMeasureSpec2);
        flexContainer.updateViewCache(view, i2);
    }

    public final void stretchViews(int i) {
        View reorderedFlexItemAt;
        FlexContainer flexContainer = this.mFlexContainer;
        if (i >= flexContainer.getFlexItemCount()) {
            return;
        }
        int flexDirection = flexContainer.getFlexDirection();
        if (flexContainer.getAlignItems() != 4) {
            for (FlexLine flexLine : flexContainer.getFlexLinesInternal()) {
                Iterator it = ((ArrayList) flexLine.mIndicesAlignSelfStretch).iterator();
                while (it.hasNext()) {
                    Integer num = (Integer) it.next();
                    View reorderedFlexItemAt2 =
                            flexContainer.getReorderedFlexItemAt(num.intValue());
                    if (flexDirection == 0 || flexDirection == 1) {
                        stretchViewVertically(
                                reorderedFlexItemAt2, flexLine.mCrossSize, num.intValue());
                    } else {
                        if (flexDirection != 2 && flexDirection != 3) {
                            throw new IllegalArgumentException(
                                    SeslRoundedCorner$$ExternalSyntheticOutline0.m(
                                            flexDirection, "Invalid flex direction: "));
                        }
                        stretchViewHorizontally(
                                reorderedFlexItemAt2, flexLine.mCrossSize, num.intValue());
                    }
                }
            }
            return;
        }
        int[] iArr = this.mIndexToFlexLine;
        List flexLinesInternal = flexContainer.getFlexLinesInternal();
        int size = flexLinesInternal.size();
        for (int i2 = iArr != null ? iArr[i] : 0; i2 < size; i2++) {
            FlexLine flexLine2 = (FlexLine) flexLinesInternal.get(i2);
            int i3 = flexLine2.mItemCount;
            for (int i4 = 0; i4 < i3; i4++) {
                int i5 = flexLine2.mFirstIndex + i4;
                if (i4 < flexContainer.getFlexItemCount()
                        && (reorderedFlexItemAt = flexContainer.getReorderedFlexItemAt(i5)) != null
                        && reorderedFlexItemAt.getVisibility() != 8) {
                    FlexItem flexItem = (FlexItem) reorderedFlexItemAt.getLayoutParams();
                    if (flexItem.getAlignSelf() == -1 || flexItem.getAlignSelf() == 4) {
                        if (flexDirection == 0 || flexDirection == 1) {
                            stretchViewVertically(reorderedFlexItemAt, flexLine2.mCrossSize, i5);
                        } else {
                            if (flexDirection != 2 && flexDirection != 3) {
                                throw new IllegalArgumentException(
                                        SeslRoundedCorner$$ExternalSyntheticOutline0.m(
                                                flexDirection, "Invalid flex direction: "));
                            }
                            stretchViewHorizontally(reorderedFlexItemAt, flexLine2.mCrossSize, i5);
                        }
                    }
                }
            }
        }
    }

    public final void updateMeasureCache(View view, int i, int i2, int i3) {
        long[] jArr = this.mMeasureSpecCache;
        if (jArr != null) {
            jArr[i] = makeCombinedLong(i2, i3);
        }
        long[] jArr2 = this.mMeasuredSizeCache;
        if (jArr2 != null) {
            jArr2[i] = makeCombinedLong(view.getMeasuredWidth(), view.getMeasuredHeight());
        }
    }
}
