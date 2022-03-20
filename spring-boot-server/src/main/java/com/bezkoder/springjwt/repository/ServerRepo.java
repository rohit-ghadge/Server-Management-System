package com.bezkoder.springjwt.repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.bezkoder.springjwt.models.Server;

/**
 * @author Rohit Ghadge
 * @version 1.0
 * @Date 8 Feb 2022
 */

public interface ServerRepo extends JpaRepository<Server, Long> 
{
    Server findByIpAddress(String ipAddress);
}
