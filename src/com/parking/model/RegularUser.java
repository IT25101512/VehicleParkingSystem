package com.parking.model;

public class RegularUser extends User {
    private String membershipType;

    public RegularUser(int id, String username, String password, String email, String membershipType) {
        super(id, username, password, email, "USER", true);
        this.membershipType = membershipType;
    }

    public String getMembershipType() { return membershipType; }
    public void setMembershipType(String membershipType) { this.membershipType = membershipType; }
}