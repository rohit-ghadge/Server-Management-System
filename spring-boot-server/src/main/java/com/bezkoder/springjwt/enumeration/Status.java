package com.bezkoder.springjwt.enumeration;

/**
 * @author Rohit Ghadge
 * @version 1.0
 * @Date 8 Feb 2022
 */

public enum Status 
{
    SERVER_UP("SERVER_UP"),
    SERVER_DOWN("SERVER_DOWN");

    private final String status;

    Status(String status) 
    {
        this.status = status;
    }

    public String getStatus() 
    {
        return this.status;
    }
}