# Repositorio de configuración centralizada para Fluffy Yarn Store
# Cada servicio lee sus propiedades desde aquí vía Config Server (backend native).
# Convención de nombres: {spring.application.name}[-{perfil}].properties
# - Archivo base (sin perfil): aplica a todos los perfiles
# - Archivo de perfil: gana al base (sobreescribe claves)
# Perfiles soportados: dev, prod (no se usa test)