package com.example.janmitr;


public class CoordinatorList {
    private String coordinatorName;
    private String coordinatorMobile;
    private String coordinatorAddress;
    private String buttonId;

    public CoordinatorList(String coordinatorName,String coordinatorMobile,String coordinatorAddress ,String buttonId) {

        this.coordinatorName = coordinatorName;
        this.coordinatorMobile = coordinatorMobile;
        this.coordinatorAddress = coordinatorAddress;
        this.buttonId = buttonId;
    }



    public String getCoordinatorName(){

        return coordinatorName;
    }

    public String getCoordinatorAddress(){
        return coordinatorAddress;
    }



    public String getCoordinatorMobile(){

        return coordinatorMobile;
    }

    public String getButtonId(){

        return buttonId;
    }

}
