package es.local.ms.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import es.local.ms.model.Reserva;
import es.local.ms.service.ReservasService;

/* La anotación "@CrossOrigin" permitirá que el servicio sea llamado de forma
 * directa desde origenes externos.
 * En caso de estar usando una configuración con Spring Cloud Gateway, se deberá
 * retirar esta anotación, pues surgirían errores de cabeceras duplicadas, ya que
 * en el MS Spring Cloud Gateway ya se incluye la configuración para que no surja
 * el problema de CORS.
 */

//@CrossOrigin(origins="*")
@RestController
public class ReservasController {
	
	@Autowired
	ReservasService service;
	
	@GetMapping(value = "/reservas", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Reserva> recuperarReservas(){
		
		return service.recuperarReservas();
	}
	
	@PostMapping(value = "reservas/{plazas}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public void altaNuevaReserva(@RequestBody Reserva res, @PathVariable("plazas") Integer plazas) {
		
		service.nuevaReserva(res, plazas);		
	}
	
}
