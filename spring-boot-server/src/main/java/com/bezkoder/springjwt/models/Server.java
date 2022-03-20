package com.bezkoder.springjwt.models;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;

import com.bezkoder.springjwt.enumeration.Status;

import static javax.persistence.GenerationType.AUTO;

/**
 * @author Rohit Ghadge
 * @version 1.0
 * @Date 8 Feb 2022
 */

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Server 
{
    @Id
    @GeneratedValue(strategy = AUTO)
    private Long id;
    @Column(unique = true)
    @NotEmpty(message = "IP Address cannot be empty or null")
    private String ipAddress;
    private String name;
    private String memory;
    private String type;
    private String imageUrl;
    private Status status;
}