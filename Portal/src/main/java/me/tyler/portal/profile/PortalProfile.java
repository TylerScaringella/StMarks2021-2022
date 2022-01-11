package me.tyler.portal.profile;

public class PortalProfile {
    private final String firstName, lastName;
    private long dob;
    private PortalClass portalClass;

    public PortalProfile(String firstName, String lastName, long dob) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.dob = dob;
        this.portalClass = PortalClass.NORMAL;
    }

    public PortalProfile(String firstName, String lastName) {
        this(firstName, lastName, 0);
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFullName() {
        return String.format("%s %s", getFirstName(), getLastName());
    }

    public long getDob() {
        return dob;
    }

    public PortalClass getPortalClass() {
        return portalClass;
    }

    public void setPortalClass(PortalClass portalClass) {
        this.portalClass = portalClass;
    }

    public void setDob(long dob) {
        this.dob = dob;
    }
}
