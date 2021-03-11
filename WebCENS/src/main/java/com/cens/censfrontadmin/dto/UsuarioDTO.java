/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cens.censfrontadmin.dto;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;
import org.json.JSONObject;
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
@Entity
public class UsuarioDTO implements Serializable {
    @Id
    private Long id;
    private String username;
    private String password;
    private String nombres;
    private String apellidos;
    private String mail;
    
    public UsuarioDTO(){
    }
    
    public UsuarioDTO(String jsonString) {
        JSONObject userJson = new JSONObject(jsonString);
        this.id = userJson.getLong("id");
        this.username = userJson.getString("username");
        this.password = userJson.getString("password");
        this.nombres = userJson.getString("nombres");
        this.apellidos = userJson.getString("apellidos");
        this.mail= userJson.getString("mail");
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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
