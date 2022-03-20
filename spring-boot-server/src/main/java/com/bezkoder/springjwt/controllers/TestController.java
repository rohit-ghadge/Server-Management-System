package com.bezkoder.springjwt.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import com.bezkoder.springjwt.models.Response;
import com.bezkoder.springjwt.models.Server;
import com.bezkoder.springjwt.security.services.ServerServiceImpl;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.TimeUnit;

import static com.bezkoder.springjwt.enumeration.Status.SERVER_UP;
import static java.time.LocalDateTime.now;
import static java.util.Map.of;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.IMAGE_PNG_VALUE;


/**
 * @author Rohit Ghadge
 * @version 1.0
 * @Date 8 Feb 2022
 */

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/test")
public class TestController {
	
    private final ServerServiceImpl serverService;

    @GetMapping("/all")
	public String allAccess() {
		return "Anyone Can See Page (without token we can hit backend api).";
	}
		
	@GetMapping("/user")
	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	public String userAccess() {
		return "User Content.";
	}

	
	@GetMapping("/mod")
	@PreAuthorize("hasRole('MODERATOR')")
	public String moderatorAccess() {
		return "Moderator Board.";
	}

	@GetMapping("/admin")
	@PreAuthorize("hasRole('ADMIN')")
	public String adminAccess() {
		return "Admin Board.";
	}

	@GetMapping("/list")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Response> getServers() throws InterruptedException 
	{
	    return ResponseEntity.ok(
	            Response.builder()
	                    .timeStamp(now())
	                    .data(of("servers", serverService.list(30)))
	                    .message("Servers retrieved")
	                    .status(OK)
	                    .statusCode(OK.value())
	                    .build()
	    );
	}

    @GetMapping("/ping/{ipAddress}")
	@PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Response> pingServer(@PathVariable("ipAddress") String ipAddress) throws IOException 
    {
        Server server = serverService.ping(ipAddress);
        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(now())
                        .data(of("server", server))
                        .message(server.getStatus() == SERVER_UP ? "Ping success" : "Ping failed")
                        .status(OK)
                        .statusCode(OK.value())
                        .build()
        );
    }

    @PostMapping("/save")
	@PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Response> saveServer(@RequestBody @Valid Server server) 
    {
        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(now())
                        .data(of("server", serverService.create(server)))
                        .message("Server created")
                        .status(CREATED)
                        .statusCode(CREATED.value())
                        .build()
        );
    }

    @GetMapping("/get/{id}")
	@PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Response> getServer(@PathVariable("id") Long id) 
    {
        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(now())
                        .data(of("server", serverService.get(id)))
                        .message("Server retrieved")
                        .status(OK)
                        .statusCode(OK.value())
                        .build()
        );
    }

    @DeleteMapping("/delete/{id}")
	@PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Response> deleteServer(@PathVariable("id") Long id) 
    {
        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(now())
                        .data(of("deleted", serverService.delete(id)))
                        .message("Server deleted")
                        .status(OK)
                        .statusCode(OK.value())
                        .build()
        );
    }

    @GetMapping(path = "/image/{fileName}", produces = IMAGE_PNG_VALUE)
    public byte[] getServerImage(@PathVariable("fileName") String fileName) throws IOException 
    {
        return Files.readAllBytes(Paths.get(System.getProperty("user.home") + "/images/" + fileName));
    }
	
}
