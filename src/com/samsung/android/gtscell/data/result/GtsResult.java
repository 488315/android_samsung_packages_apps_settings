package com.samsung.android.gtscell.data.result;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.Keep;

import com.samsung.android.knox.net.apn.ApnSettings;

import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.jvm.internal.Intrinsics;

import kotlinx.android.parcel.Parcelize;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
@Parcelize
@Keep
@Metadata(
        bv = {1, 0, 3},
        d1 = {
            "\u00000\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\u0018\u0002\n"
                + "\u0000\n"
                + "\u0002\u0018\u0002\n"
                + "\u0000\n"
                + "\u0002\u0010\u0011\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\b\u0007\n"
                + "\u0002\u0010\b\n"
                + "\u0000\n"
                + "\u0002\u0010\u0002\n"
                + "\u0000\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\b\u0004\b\u0007\u0018\u00002\u00020\u0001:\u0002\u0014\u0015B\u001d\b\u0000\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005¢\u0006\u0002\u0010\u0007J"
                + "\t\u0010\r"
                + "\u001a\u00020\u000eHÖ\u0001J\u0019\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u000eHÖ\u0001R\u0019\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005¢\u0006\n\n"
                + "\u0002\u0010\n"
                + "\u001a\u0004\b\b\u0010\tR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n"
                + "\u0000\u001a\u0004\b\u000b\u0010\f¨\u0006\u0016"
        },
        d2 = {
            "Lcom/samsung/android/gtscell/data/result/GtsResult;",
            "Landroid/os/Parcelable;",
            "state",
            "Lcom/samsung/android/gtscell/data/result/GtsResult$State;",
            "itemResults",
            ApnSettings.MVNO_NONE,
            "Lcom/samsung/android/gtscell/data/result/GtsItemResult;",
            "(Lcom/samsung/android/gtscell/data/result/GtsResult$State;[Lcom/samsung/android/gtscell/data/result/GtsItemResult;)V",
            "getItemResults",
            "()[Lcom/samsung/android/gtscell/data/result/GtsItemResult;",
            "[Lcom/samsung/android/gtscell/data/result/GtsItemResult;",
            "getState",
            "()Lcom/samsung/android/gtscell/data/result/GtsResult$State;",
            "describeContents",
            ApnSettings.MVNO_NONE,
            "writeToParcel",
            ApnSettings.MVNO_NONE,
            "parcel",
            "Landroid/os/Parcel;",
            "flags",
            "Builder",
            "State",
            "gtscell_release"
        },
        k = 1,
        mv = {1, 1, 16})
/* loaded from: classes4.dex */
public final class GtsResult implements Parcelable {
    public static final Parcelable.Creator CREATOR = new Creator();
    private final GtsItemResult[] itemResults;
    private final State state;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    @Metadata(
            bv = {1, 0, 3},
            d1 = {
                "\u0000&\n"
                    + "\u0002\u0018\u0002\n"
                    + "\u0002\u0010\u0000\n"
                    + "\u0002\b\u0002\n"
                    + "\u0002\u0010!\n"
                    + "\u0002\u0018\u0002\n"
                    + "\u0002\b\u0003\n"
                    + "\u0002\u0018\u0002\n"
                    + "\u0002\b\u0005\n"
                    + "\u0002\u0018\u0002\n"
                    + "\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0006\u0010\u000e\u001a\u00020\u000fR\u0017\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004¢\u0006\b\n"
                    + "\u0000\u001a\u0004\b\u0006\u0010\u0007R\u001a\u0010\b\u001a\u00020"
                    + "\tX\u0086\u000e¢\u0006\u000e\n"
                    + "\u0000\u001a\u0004\b\n"
                    + "\u0010\u000b\"\u0004\b\f\u0010\r"
                    + "¨\u0006\u0010"
            },
            d2 = {
                "Lcom/samsung/android/gtscell/data/result/GtsResult$Builder;",
                ApnSettings.MVNO_NONE,
                "()V",
                "itemResults",
                ApnSettings.MVNO_NONE,
                "Lcom/samsung/android/gtscell/data/result/GtsItemResult;",
                "getItemResults",
                "()Ljava/util/List;",
                "state",
                "Lcom/samsung/android/gtscell/data/result/GtsResult$State;",
                "getState",
                "()Lcom/samsung/android/gtscell/data/result/GtsResult$State;",
                "setState",
                "(Lcom/samsung/android/gtscell/data/result/GtsResult$State;)V",
                "build",
                "Lcom/samsung/android/gtscell/data/result/GtsResult;",
                "gtscell_release"
            },
            k = 1,
            mv = {1, 1, 16})
    public static final class Builder {
        private final List<GtsItemResult> itemResults = new ArrayList();
        private State state = State.READY;

        public final GtsResult build() {
            State state = this.state;
            Object[] array = this.itemResults.toArray(new GtsItemResult[0]);
            if (array != null) {
                return new GtsResult(state, (GtsItemResult[]) array);
            }
            throw new TypeCastException("null cannot be cast to non-null type kotlin.Array<T>");
        }

        public final List<GtsItemResult> getItemResults() {
            return this.itemResults;
        }

        public final State getState() {
            return this.state;
        }

        public final void setState(State state) {
            Intrinsics.checkParameterIsNotNull(state, "<set-?>");
            this.state = state;
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    @Metadata(
            bv = {1, 0, 3},
            k = 3,
            mv = {1, 1, 16})
    public static class Creator implements Parcelable.Creator {
        @Override // android.os.Parcelable.Creator
        public final Object createFromParcel(Parcel in) {
            Intrinsics.checkParameterIsNotNull(in, "in");
            State state = (State) Enum.valueOf(State.class, in.readString());
            int readInt = in.readInt();
            GtsItemResult[] gtsItemResultArr = new GtsItemResult[readInt];
            for (int i = 0; readInt > i; i++) {
                gtsItemResultArr[i] =
                        (GtsItemResult) in.readParcelable(GtsResult.class.getClassLoader());
            }
            return new GtsResult(state, gtsItemResultArr);
        }

        @Override // android.os.Parcelable.Creator
        public final Object[] newArray(int i) {
            return new GtsResult[i];
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    @Metadata(
            bv = {1, 0, 3},
            d1 = {
                "\u0000\f\n"
                    + "\u0002\u0018\u0002\n"
                    + "\u0002\u0010\u0010\n"
                    + "\u0002\b"
                    + "\t\b\u0086\u0001\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004j\u0002\b\u0005j\u0002\b\u0006j\u0002\b\u0007j\u0002\b\bj\u0002\b"
                    + "\t¨\u0006\n"
            },
            d2 = {
                "Lcom/samsung/android/gtscell/data/result/GtsResult$State;",
                ApnSettings.MVNO_NONE,
                "(Ljava/lang/String;I)V",
                "READY",
                "COMPLETE",
                "TIMEOUT",
                "EXTRA_ERROR",
                "URI_ERROR",
                "JSON_ERROR",
                "NO_SCREEN_PROVIDER",
                "gtscell_release"
            },
            k = 1,
            mv = {1, 1, 16})
    public enum State {
        READY,
        COMPLETE,
        TIMEOUT,
        EXTRA_ERROR,
        URI_ERROR,
        JSON_ERROR,
        NO_SCREEN_PROVIDER
    }

    public GtsResult(State state, GtsItemResult[] itemResults) {
        Intrinsics.checkParameterIsNotNull(state, "state");
        Intrinsics.checkParameterIsNotNull(itemResults, "itemResults");
        this.state = state;
        this.itemResults = itemResults;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public final GtsItemResult[] getItemResults() {
        return this.itemResults;
    }

    public final State getState() {
        return this.state;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int flags) {
        Intrinsics.checkParameterIsNotNull(parcel, "parcel");
        parcel.writeString(this.state.name());
        GtsItemResult[] gtsItemResultArr = this.itemResults;
        int length = gtsItemResultArr.length;
        parcel.writeInt(length);
        for (int i = 0; length > i; i++) {
            parcel.writeParcelable(gtsItemResultArr[i], flags);
        }
    }
}
