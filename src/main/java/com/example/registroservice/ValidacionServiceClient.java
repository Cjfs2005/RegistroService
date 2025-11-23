package com.example.registroservice;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Map;

@FeignClient(name = "ValidacionService")
public interface ValidacionServiceClient {

    @GetMapping("/validar/dni/{dni}")
    String validarDni(@PathVariable("dni") String dni);

    @GetMapping("/validar/correo/{correo}")
    String validarCorreo(@PathVariable("correo") String correo);
}
