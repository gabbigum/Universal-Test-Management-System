package com.utms.utmswebapi.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason="Project with such id and name does not exist. Please try adding the project first.")
public class NoSuchProjectException extends RuntimeException{}
