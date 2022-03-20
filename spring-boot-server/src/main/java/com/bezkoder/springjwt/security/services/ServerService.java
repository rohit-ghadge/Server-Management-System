package com.bezkoder.springjwt.security.services;

import java.io.IOException;
import java.util.Collection;

import com.bezkoder.springjwt.models.Server;

/**
 * @author Rohit Ghadge
 * @version 1.0
 * @Date 8 Feb 2022
 */

public interface ServerService 
{
    Server create(Server server);
    Server ping(String ipAddress) throws IOException;
    Collection<Server> list(int limit);
    Server get(Long id);
    Server update(Server server);
    Boolean delete(Long id);
}