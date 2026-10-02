package com.fluffyyarn.store.identity.application.port.in.role;

import com.fluffyyarn.store.identity.domain.model.role.RoleName;
import lombok.*;

// El Command es la lista de parámetros que necesita (QUIEN??) sin las partes que el cliente no controla.
// El cliente no sabe el id (lo pone la DB) ni debe construir objetos de dominio.
//Un Command es datos de entrada: se crea, se pasa al caso de uso y se descarta. No debería poder mutarse después
// TODO: preguntar al profe por qué usó setters si esta clase no tendría que mutar?
// TODO - Por que no es un RECORD?

// (TODO: Verificar solo este comentario) Command que especifica los atributos que necesita un Role
@Getter // Lombok - Genera los getters de todos los atributos.
@AllArgsConstructor // Lombok - Genera el constructor con todos los atributos.
@Builder // Lombok -
public class CreateRoleCommand {
  private RoleName name;
  private String description;
}
