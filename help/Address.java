package please.help;

import java.util.Objects;

public class Address implements Cloneable{

    private String street;
    private String zipCode;

    public Address(String street, String zipCode){
        this.street = street;
        this.zipCode = zipCode;
    }

    public String getStreet(){
        return street;
    }

    public String getZipCode(){
        return zipCode;
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
