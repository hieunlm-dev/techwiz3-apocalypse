/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vn.aptech.Entity;

import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author Thanh Sang
 */
@Getter
@Setter
public class User {

    private int id;
    private String full_name;
    private String phone_number;
    private String email;
    private String address;
    private String password;
    private String role;

}
