package com.movil.cens.app.data.usuario;

import java.io.Serializable;
/*
{
        "id": 1,
        "username": "jperez",
        "password": "123",
        "nombres": "Juan",
        "apellidos": "Perez",
        "mail": "jperez@gmail.com"
    }
* **/
public class UsuarioDTO implements Serializable {
    private Integer id;
    private String username;
    private String password;
    private String nombres;
    private String apellidos;
    private String mail;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }
}
