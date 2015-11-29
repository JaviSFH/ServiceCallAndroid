package com.example.javi.servicecall.models;

/**
 * @author Javier Martínez
 * @since 28/11/15
 *
 * Representa el objeto "Contacto".
 */
public class Contact {

    /** URL donde se encuentra la imagen del contacto */
    private String contactPictureURL;

    /** Nombre del contacto */
    private String name;

    /** Teléfono del contacto*/
    private String phone;

    public Contact(String contactPicture, String name, String phone) {
        this.contactPictureURL = contactPicture;
        this.name = name;
        this.phone = phone;
    }

    //region Getters and Setters
    public String getContactPictureURL() {
        return contactPictureURL;
    }

    public void setContactPictureURL(String contactPictureURL) {
        this.contactPictureURL = contactPictureURL;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
    //endregion
}
