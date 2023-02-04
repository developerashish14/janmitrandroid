package com.example.janmitr;

public class MemberList {
    private String memberName;
    private String memberId;
    private String CoordinatorId;

    public MemberList(String memberName,String memberId,String CoordinatorId) {

        this.memberName = memberName;
        this.memberId = memberId;
        this.CoordinatorId = CoordinatorId;
    }



    public String getMemberName(){

        return memberName;
    }

    public String getMemberId()
    {
        return memberId;
    }

    public String getCoordinatorId()
    {
        return CoordinatorId;
    }

}
