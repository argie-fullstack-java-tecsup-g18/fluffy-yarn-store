# Refactor en clase

1. Duplicación de código en la creación de un usuario por parte de un Admin role y el registro de un cliente desde la web. Identifique que se podía usar el patron Factory.
2. En el UserWebMapper identifiqué que había inconsistencia en un método, mientras todos usaban el patron builder implementado con Lombok había uno que usaba un constructor normal. Ajusté el WebMapper y el responseDto.
3. En el RoleService también identifiqué que a pesar de que ya le había puesto la anotación @builder al CreateRoleCommand, en el RoleService lo seguía creando con un constructor normal. 