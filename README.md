# EduMatch API (Spring Boot + MySQL)

Evidencia GA7-220501096-AA5-EV03 · Diseño y desarrollo de servicios web - proyecto.

API REST para la aplicación EduMatch: registro/inicio de sesión, instituciones, oportunidades (becas, cursos, intercambios) y postulaciones. Cada servicio está documentado en [ENDPOINTS.md](ENDPOINTS.md).

## Requisitos
- Java 17
- Maven
- MySQL en `localhost:3306`

## Cómo ejecutarla
1. Abre `src/main/resources/application.properties` y cambia `spring.datasource.password` por tu contraseña de MySQL.
2. En la carpeta del proyecto ejecuta:
   ```bash
   mvn spring-boot:run
   ```
3. La API queda en `http://localhost:8080`. Las tablas se crean solas la primera vez.

## Estructura (estándares de codificación)
- Paquetes en minúscula: `model`, `repository`, `controller`, `dto`, `exception`, `config`.
- Clases en PascalCase, métodos y variables en camelCase.
- `model`: entidades (Usuario, Institucion, Oportunidad, Postulacion).
- `repository`: acceso a datos con Spring Data JPA.
- `controller`: servicios REST.
- `service`: lógica de registro y login.

## Control de versiones
```bash
git init
git add .
git commit -m "API EduMatch: servicios web del proyecto"
git branch -M main
git remote add origin https://github.com/leidylore/edumatch-api.git
git push -u origin main
```
