package com.fluffyyarn.store.security.application.port.in;

import com.fluffyyarn.store.security.domain.model.RoleName;
import lombok.*;

// TODO: preguntar al profe por qué usó setters si esta clase no tendría que mutar?
//Un Command es datos de entrada: se crea, se pasa al caso de uso y se descarta. No debería poder mutarse después
// TODO - Por que no es un RECORD?

// (TODO: Verificar solo este comentario) Command que especifica los atributos que necesita un Role
@Getter // Lombok - Genera los getters de todos los atributos.
@AllArgsConstructor // Lombok - Genera el constructor con todos los atributos.
@Builder // Lombok -
public class CreateRoleCommand {
  private RoleName name;
  private String description;
}
