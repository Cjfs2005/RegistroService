package com.example.registroservice;

import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/registro")
public class RegistroController {
    @Autowired
    private ValidacionServiceClient validacionServiceClient;

    @PostMapping
    public ResponseEntity<Map<String, Object>> registrarUsuario(@RequestBody Map<String, String> request) {
        Map<String, Object> response = new HashMap<>();

        try {
            String dni = request.get("dni");
            String correo = request.get("correo");

            // Validar DNI usando Feign
            try {
                validacionServiceClient.validarDni(dni);
            } catch (FeignException.BadRequest e) {
                response.put("exito", false);
                response.put("mensaje", e.contentUTF8());
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }

            // Validar Correo usando Feign
            try {
                validacionServiceClient.validarCorreo(correo);
            } catch (FeignException.BadRequest e) {
                response.put("exito", false);
                response.put("mensaje", e.contentUTF8());
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }

            // Ambas validaciones exitosas
            response.put("exito", true);
            response.put("mensaje", "Validaciones exitosas");
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            response.put("exito", false);
            response.put("mensaje", "Error en el servidor: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}
