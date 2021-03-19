package company.Models;

import java.util.HashMap;

public class Contact {

    // Data file contains variable amount of named input, HashMap is the best choice for this scenario
    private HashMap<String, String> contactInformation;

    public Contact() {
        this.contactInformation = new HashMap<>();
    }

    public Contact(HashMap<String, String> contactInformation) {
        this.contactInformation = contactInformation;
    }

    public HashMap<String, String> getContactInformation() {
        return contactInformation;
    }

    public void setContactInformation(HashMap<String, String> contactInformation) {
        this.contactInformation = contactInformation;
    }

    //    @Override
//    public String toString() {
//        return "Contact{" +
//                "contactInformation=" + contactInformation +
//                '}';
//
//    }
    @Override
    public String toString() {
        return "" + contactInformation;

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Contact)) return false;
        Contact contact = (Contact) o;
        return getContactInformation().equals(contact.getContactInformation());
    }

}
