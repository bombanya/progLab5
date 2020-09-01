package please.help;

import java.util.Objects;

public class Address implements Cloneable{

    private String street;
    private String zipCode;

    public Address(){ }

    public String getStreet(){
        return street;
    }

    public String getZipCode(){
        return zipCode;
    }

    public void setStreet(String aStreet){
        street = aStreet;
    }

    public void setZipCode(String aZipCode) throws WrongDataException{
        if (aZipCode == null || aZipCode.length() >= 4) zipCode = aZipCode;
        else throw new WrongDataException();
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (other == null) return false;
        if (getClass() != other.getClass()) return false;
        return ((Address) other).getStreet().equals(this.street) &&
                ((Address) other).getZipCode().equals(this.zipCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(street, zipCode);
    }

    @Override
    public String toString() {
        return "Address{ " + "street = " + street + "; zipCode = " + zipCode + " }";
    }

    @Override
    public Address clone() throws CloneNotSupportedException {
        return (Address) super.clone();
    }
}
