package com.local.contactos.service;

import java.util.List;

import com.local.contactos.model.Contacto;

/* Interfaz de la implementación de la capa de servicio, que es en la que se
 * implementa la lógica de negocio. */
public interface ContactoService {

	boolean agregarContacto(Contacto contacto);
	
	List<Contacto> recuperarContactos();
	
	void actualizarContacto(Contacto contacto);
	
	boolean eliminarContacto(int idContacto);
	
	Contacto buscarContacto(int idContacto);
	
}
