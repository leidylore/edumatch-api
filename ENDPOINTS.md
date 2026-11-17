# Documentación de los servicios web - EduMatch API

Evidencia GA7-220501096-AA5-EV03 · Base URL: `http://localhost:8080`
Formato de datos: JSON (`Content-Type: application/json`).

## 1. Autenticación (`/api/auth`)

### POST /api/auth/registro
Registra un estudiante. La contraseña se guarda cifrada (BCrypt).

Cuerpo:
```json
{ "nombre": "Leidy Ramos", "correo": "leidy@correo.com", "contrasena": "123456" }
```
Respuestas: `201 Created` (devuelve el usuario sin contraseña) · `400` datos inválidos · `409` correo ya registrado.

### POST /api/auth/login
Cuerpo:
```json
{ "correo": "leidy@correo.com", "contrasena": "123456" }
```
Respuestas:
- `200 OK` → `{ "mensaje": "Autenticación satisfactoria", "usuarioId": 1, "nombre": "Leidy Ramos" }`
- `401 Unauthorized` → `{ "mensaje": "Error en la autenticación" }`

## 2. Instituciones (`/api/instituciones`)

| Método | Ruta | Descripción | Respuestas |
|--------|------|-------------|-----------|
| GET | /api/instituciones | Lista todas | 200 |
| GET | /api/instituciones/{id} | Consulta una | 200, 404 |
| POST | /api/instituciones | Inserta | 201, 400 |
| PUT | /api/instituciones/{id} | Actualiza | 200, 400, 404 |
| DELETE | /api/instituciones/{id} | Elimina | 204, 404 |

Cuerpo (POST/PUT):
```json
{ "nombre": "Universidad del Valle", "ciudad": "Cali", "pais": "Colombia", "sitioWeb": "https://www.univalle.edu.co" }
```

## 3. Oportunidades (`/api/oportunidades`)

| Método | Ruta | Descripción | Respuestas |
|--------|------|-------------|-----------|
| GET | /api/oportunidades | Lista todas | 200 |
| GET | /api/oportunidades?tipo=BECA | Filtra por tipo (`BECA`, `CURSO`, `PROGRAMA_INTERCAMBIO`) | 200 |
| GET | /api/oportunidades/{id} | Consulta una | 200, 404 |
| POST | /api/oportunidades | Inserta | 201, 400, 404 (institución no existe) |
| PUT | /api/oportunidades/{id} | Actualiza | 200, 400, 404 |
| DELETE | /api/oportunidades/{id} | Elimina | 204, 404 |

Cuerpo (POST/PUT):
```json
{
  "titulo": "Beca de excelencia académica",
  "descripcion": "Apoyo para estudiantes de pregrado",
  "tipo": "BECA",
  "fechaLimite": "2027-03-31",
  "institucionId": 1
}
```

## 4. Postulaciones (`/api/postulaciones`)

| Método | Ruta | Descripción | Respuestas |
|--------|------|-------------|-----------|
| POST | /api/postulaciones | Crea una postulación | 201, 404, 409 (ya se postuló) |
| GET | /api/postulaciones/{id} | Consulta una | 200, 404 |
| GET | /api/postulaciones/usuario/{usuarioId} | Postulaciones de un estudiante | 200 |
| PUT | /api/postulaciones/{id}/estado | Cambia el estado | 200, 400, 404 |
| DELETE | /api/postulaciones/{id} | Cancela la postulación | 204, 404 |

Cuerpo POST:
```json
{ "usuarioId": 1, "oportunidadId": 1 }
```
Cuerpo PUT estado (`ENVIADA`, `EN_REVISION`, `ACEPTADA`, `RECHAZADA`):
```json
{ "estado": "EN_REVISION" }
```

## 5. Errores

Todos los errores devuelven JSON:
- `400`: `{ "campo": "mensaje de validación" }`
- `404` y `409`: `{ "mensaje": "descripción del error" }`
