package app.management.college.com.collegemanagement.model;

/**
 * Created by Sanjay on 9/14/2016.
 * CollegeManagement-Faculty
 */

public class AppliedLeavesItem {

    String AvailableLeaves;
    String IsHalfdayAllowed;
    String LeaveID;
    String LeaveName;
    String MaximumNoOfDays;
    String MinimumNoOfDays;
    String ShortName;
    String ApplicationID;
    String AppliedByID;
    String AppliedByName;
    String ApprovalStatus;
    String Comment;
    String LeaveAppliedDate;
    String LeaveDateFrom;
    String LeaveDateTo;
    String LeaveRequestSentTo;
    String LeaveStatusID;
    String Reason;

    public AppliedLeavesItem() {

    }


    public AppliedLeavesItem(
            String AvailableLeaves,
            String IsHalfdayAllowed,
            String LeaveID,
            String LeaveName,
            String MaximumNoOfDays,
            String MinimumNoOfDays,
            String ShortName,
            String ApplicationID,
            String AppliedByID,
            String AppliedByName,
            String ApprovalStatus,
            String Comment,
            String LeaveAppliedDate,
            String LeaveDateFrom,
            String LeaveDateTo,
            String LeaveRequestSentTo,
            String LeaveStatusID,
            String Reason
    ) {
        this.AvailableLeaves = AvailableLeaves;
        this.IsHalfdayAllowed = IsHalfdayAllowed;
        this.LeaveID = LeaveID;
        this.LeaveName = LeaveName;
        this.MaximumNoOfDays = MaximumNoOfDays;
        this.MinimumNoOfDays = MinimumNoOfDays;
        this.ShortName = ShortName;
        this.ApplicationID = ApplicationID;
        this.AppliedByID = AppliedByID;
        this.ApprovalStatus = ApprovalStatus;
        this.Comment = Comment;
        this.LeaveAppliedDate = LeaveAppliedDate;
        this.LeaveDateFrom = LeaveDateFrom;
        this.LeaveDateTo = LeaveDateTo;
        this.LeaveRequestSentTo = LeaveRequestSentTo;
        this.LeaveStatusID = LeaveStatusID;
        this.Reason = Reason;

    }

    public String getAvailableLeaves() {
        return AvailableLeaves;
    }

    public void setAvailableLeaves(String availableLeaves) {
        AvailableLeaves = availableLeaves;
    }

    public String getIsHalfdayAllowed() {
        return IsHalfdayAllowed;
    }

    public void setIsHalfdayAllowed(String isHalfdayAllowed) {
        IsHalfdayAllowed = isHalfdayAllowed;
    }

    public String getLeaveID() {
        return LeaveID;
    }

    public void setLeaveID(String leaveID) {
        LeaveID = leaveID;
    }

    public String getLeaveName() {
        return LeaveName;
    }

    public void setLeaveName(String leaveName) {
        LeaveName = leaveName;
    }

    public String getMaximumNoOfDays() {
        return MaximumNoOfDays;
    }

    public void setMaximumNoOfDays(String maximumNoOfDays) {
        MaximumNoOfDays = maximumNoOfDays;
    }

    public String getMinimumNoOfDays() {
        return MinimumNoOfDays;
    }

    public void setMinimumNoOfDays(String minimumNoOfDays) {
        MinimumNoOfDays = minimumNoOfDays;
    }

    public String getShortName() {
        return ShortName;
    }

    public void setShortName(String shortName) {
        ShortName = shortName;
    }

    public String getApplicationID() {
        return ApplicationID;
    }

    public void setApplicationID(String applicationID) {
        ApplicationID = applicationID;
    }

    public String getAppliedByID() {
        return AppliedByID;
    }

    public void setAppliedByID(String appliedByID) {
        AppliedByID = appliedByID;
    }

    public String getAppliedByName() {
        return AppliedByName;
    }

    public void setAppliedByName(String appliedByName) {
        AppliedByName = appliedByName;
    }

    public String getApprovalStatus() {
        return ApprovalStatus;
    }

    public void setApprovalStatus(String approvalStatus) {
        ApprovalStatus = approvalStatus;
    }

    public String getComment() {
        return Comment;
    }

    public void setComment(String comment) {
        Comment = comment;
    }

    public String getLeaveAppliedDate() {
        return LeaveAppliedDate;
    }

    public void setLeaveAppliedDate(String leaveAppliedDate) {
        LeaveAppliedDate = leaveAppliedDate;
    }

    public String getLeaveDateFrom() {
        return LeaveDateFrom;
    }

    public void setLeaveDateFrom(String leaveDateFrom) {
        LeaveDateFrom = leaveDateFrom;
    }

    public String getLeaveDateTo() {
        return LeaveDateTo;
    }

    public void setLeaveDateTo(String leaveDateTo) {
        LeaveDateTo = leaveDateTo;
    }

    public String getLeaveRequestSentTo() {
        return LeaveRequestSentTo;
    }

    public void setLeaveRequestSentTo(String leaveRequestSentTo) {
        LeaveRequestSentTo = leaveRequestSentTo;
    }

    public String getLeaveStatusID() {
        return LeaveStatusID;
    }

    public void setLeaveStatusID(String leaveStatusID) {
        LeaveStatusID = leaveStatusID;
    }

    public String getReason() {
        return Reason;
    }

    public void setReason(String reason) {
        Reason = reason;
    }


}
