package please.help;

import java.time.LocalDateTime;
import java.util.Objects;

public class Organization implements Comparable<Organization>, Cloneable{

    private final Long id;
    private final LocalDateTime creationDate;

    private final String name;
    private Coordinates coordinates;
    private final double annualTurnover;
    private final OrganizationType type;
    private Address officialAddress;

    public Organization(Long id, LocalDateTime creationDate, String name, Coordinates coordinates,
                        double annualTurnover, OrganizationType type, Address officialAddress ){
        this.id = id;
        this.creationDate = creationDate;
        this.name = name;
        this.coordinates = coordinates;
        this.annualTurnover = annualTurnover;
        this.type = type;
        this.officialAddress = officialAddress;
    }

    public String getName() {
        return name;
    }

    public Coordinates getCoordinates(){
        return coordinates;
    }

    public double getAnnualTurnover() {
        return annualTurnover;
    }

    public OrganizationType getType() {
        return type;
    }

    public Address getOfficialAddress() {
        return officialAddress;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public Long getId() {
        return id;
    }

    public String[] getAllFields(){
        return new String[]{
                getName(), String.valueOf(getCoordinates().getX()),
                String.valueOf(getCoordinates().getY()), String.valueOf(getAnnualTurnover()),
                getType().toString(), getOfficialAddress().getStreet(), getOfficialAddress().getZipCode()
        };
    }

    private void setOfficialAddress(Address officialAddress) {
        this.officialAddress = officialAddress;
    }

    private void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    @Override
    public int compareTo(Organization other) {
        return Double.compare(annualTurnover, other.getAnnualTurnover());
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (other == null) return false;
        if (getClass() != other.getClass()) return false;
        return ((Organization) other).getId().equals(this.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, creationDate);
    }

    @Override
    public String toString() {
        return "Organization{ " + "id = " + id + "; creationDate = " + creationDate
                + "; name = " + name + "; coordinates = " + coordinates
                + "; annualTurnover = " + annualTurnover + "; type = " + type
                + "; officialAddress = " + officialAddress + " }";
    }

    @Override
    public Organization clone() throws CloneNotSupportedException {
        Organization result = (Organization) super.clone();
        result.setCoordinates(coordinates.clone());
        result.setOfficialAddress(officialAddress.clone());
        return result;
    }
}
