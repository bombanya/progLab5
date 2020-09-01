package please.help;

import java.time.LocalDateTime;
import java.util.Objects;

public class Organization implements Comparable<Organization>, Cloneable{

    private final long id;
    public static long nextId = 1;
    private final LocalDateTime creationDate;

    private String name;
    private Coordinates coordinates = new Coordinates();
    private double annualTurnover;
    private OrganizationType type;
    private Address officialAddress = new Address();

    public Organization(){
        id = nextId;
        nextId++;
        creationDate = java.time.LocalDateTime.now();
    }

    public Organization(int anId, LocalDateTime aCreationDate){
        id = anId;
        if (nextId <= id) nextId = id + 1;
        creationDate = aCreationDate;
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

    public String[] getAllFields(){
        return new String[]{
                getName(), String.valueOf(getCoordinates().getX()),
                String.valueOf(getCoordinates().getY()), String.valueOf(getAnnualTurnover()),
                getType().toString(), getOfficialAddress().getStreet(), getOfficialAddress().getZipCode()
        };
    }

    public long getId() {
        return id;
    }

    public void setName(String aName) throws WrongDataException{
        if (aName != null) name = aName;
        else throw new WrongDataException();
    }

    public void setCoordinateX(int aX) throws WrongDataException{
        coordinates.setX(aX);
    }

    public void setCoordinateY(int anY) throws WrongDataException{
        coordinates.setY(anY);
    }

    public void setAnnualTurnover(double anAnnualTurnover) throws WrongDataException{
        if (anAnnualTurnover > 0) annualTurnover = anAnnualTurnover;
        else throw new WrongDataException();
    }

    public void setType(OrganizationType aType) throws WrongDataException{
        if (aType != null) type = aType;
        else throw new WrongDataException();
    }

    public void setOfficialAddressStreet(String aStreet) {
        officialAddress.setStreet(aStreet);
    }

    public void setOfficialAddressZipCode(String aZipCode) throws WrongDataException{
        officialAddress.setZipCode(aZipCode);
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public void setOfficialAddress(Address officialAddress) {
        this.officialAddress = officialAddress;
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
        return ((Organization) other).getId() == this.id;
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
