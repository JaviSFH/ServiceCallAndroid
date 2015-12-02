package com.example.javi.servicecall.models;

/**
 * @author Javier Martínez
 * @since 28/11/15
 *
 * Representa el objeto "Contacto".
 */
public class Contact {

    /** URL donde se encuentra la imagen del contacto */

    private String thumbnail;

    /** Nombre del contacto */
    private String name;

    /** Teléfono del contacto*/
    private String phone;

    public Contact(String contactPicture, String name, String phone) {
        this.thumbnail = contactPicture;
        this.name = name;
        this.phone = phone;
    }

    //region Getters and Setters
    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
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
