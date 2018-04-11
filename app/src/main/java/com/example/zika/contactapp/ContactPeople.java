package com.example.zika.contactapp;

import java.io.Serializable;

public class ContactPeople implements Serializable {

    public String name, phone, photoURI;


    public ContactPeople(){
// sta ce ti prazan konstruktor? :D
    }

    public ContactPeople(String name, String phone, String photoURI){

        this.phone = phone;
        this.name = name;
        this.photoURI = photoURI;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getPhotoURI() {
        return photoURI;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setPhotoURI(String photoURI) {
        this.photoURI = photoURI;
    }

    @Override
    public boolean equals(Object object) {

        if(object instanceof ContactPeople){
            ContactPeople temp = (ContactPeople) object;
            if(((ContactPeople) object).getName().equals(temp.getName())){

                return true;
            }
        }
        return false;
    }

    @Override
    public int hashCode() {
        return getName().hashCode();
    }
}
