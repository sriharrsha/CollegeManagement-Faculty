package app.management.college.com.collegemanagement.api.FeedbackReply;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FeedbackReplyRequest implements Parcelable {

    public static final Creator<FeedbackReplyRequest> CREATOR = new Creator<FeedbackReplyRequest>() {
        @Override
        public FeedbackReplyRequest createFromParcel(Parcel in) {
            return new FeedbackReplyRequest(in);
        }

        @Override
        public FeedbackReplyRequest[] newArray(int size) {
            return new FeedbackReplyRequest[size];
        }
    };
    @SerializedName("RefID")
    @Expose
    private Integer refID;
    @SerializedName("Reply")
    @Expose
    private String reply;

    public FeedbackReplyRequest(Parcel in) {
        reply = in.readString();
    }

    public FeedbackReplyRequest() {

    }

    /**
     *
     * @return
     * The refID
     */
    public Integer getRefID() {
        return refID;
    }

    /**
     *
     * @param refID
     * The RefID
     */
    public void setRefID(Integer refID) {
        this.refID = refID;
    }

    /**
     *
     * @return
     * The reply
     */
    public String getReply() {
        return reply;
    }

    /**
     *
     * @param reply
     * The Reply
     */
    public void setReply(String reply) {
        this.reply = reply;
    }

    @Override
    public String toString() {
        return "FeedbackReplyRequest{" +
                "refID=" + refID +
                ", reply='" + reply + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(reply);
    }
}