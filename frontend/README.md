# 🏥 InsecureCare - Aplicación Web Vulnerable para Auditoría OWASP

> **UNIVERSIDAD DE COSTA RICA**  
> **SEDE DEL ATLÁNTICO - RECINTO PARAÍSO**  
> **CARRERA DE INFORMÁTICA EMPRESARIAL**  
> **CURSO:** IF0009 - Desarrollo de Software IV  
> **TEMA:** 06 - Seguridad Web y Móvil  
> **ACTIVIDAD:** Laboratorio de Auditoría Forense y Pentesting Web (Quiz 9)  

---

## ⚠️ Advertencia y Descargo de Responsabilidad Pedagógica
> **IMPORTANTE:** Esta aplicación web fue desarrollada **exclusivamente con propósitos educativos** para el entrenamiento práctico en auditoría de seguridad y mitigación de vulnerabilidades web (OWASP Top 10). Contiene vulnerabilidades reales y deliberadas (inyecciones, fallos de control de acceso, debilidades criptográficas y exposición de trazas). **No utilice este código ni sus patrones en entornos de producción bajo ninguna circunstancia.**

---

## 🏛️ Arquitectura del Sistema

```mermaid
flowchart LR
    A["Navegador Web<br/>(Angular 19 Standalone)"] <-->|HTTP / REST JSON<br/>(Puerto 4200)| B["Backend API<br/>(Spring Boot 3 + JPA)"]
    B <-->|JDBC In-Memory| C["Base de Datos H2<br/>(insecure_care_db)"]
    B -.->|Consola Web Abierta| D["H2 Console<br/>(/h2-console)"]
```

* **Frontend:** Angular 19 Standalone Components, Reactive Services con Angular Signals, interfaz responsiva con diseño clínico.
* **Backend:** Java 21 / 25, Spring Boot 3.3+, Spring Data JPA, H2 Database en memoria, JJWT.
* **Persistencia:** Base de datos H2 volátil con auto-inicialización (`schema.sql` y `data.sql`).

---

## 📋 Requisitos Previos

Asegúrese de contar con el siguiente software instalado en su máquina de desarrollo:

1. **Java Development Kit (JDK):** Versión 21 LTS o 25 LTS instalada y configurada en la variable `JAVA_HOME`.
   ```bash
   java -version
   ```
2. **Apache Maven:** Versión 3.9+ configurada en el `PATH`.
   ```bash
   mvn -version
   ```
3. **Node.js y npm:** Node.js v20 LTS o superior.
   ```bash
   node -v
   npm -v
   ```
4. **Visual Studio Code:** Con la extensión *Extension Pack for Java* y *Angular Language Service*.

---

## 🚀 Guía de Clonado, Configuración y Puesta en Marcha

### Opción A: Mediante Visual Studio Code Workspace (Recomendada)

1. Abra Visual Studio Code.
2. Vaya al menú **Archivo (File) > Abrir espacio de trabajo desde archivo... (Open Workspace from File...)**.
3. Seleccione el archivo:
   ```
   C:\Users\jonat\OneDrive\Documentos\UCR-2026\IF0009\web_apps\unsecure_web_app\unsecure_web_app.code-workspace
   ```
4. VS Code cargará simultáneamente las carpetas `backend` y `frontend`.
5. Presione `Ctrl + Shift + B` (o `Terminal > Run Build Task...`) y ejecute:
   * **Instalar Dependencias Frontend (npm install)** (solo la primera vez).
   * **Iniciar Backend (Spring Boot)**.
   * **Iniciar Frontend (Angular)**.

---

### Opción B: Mediante Terminal / Consola de Comandos

#### Paso 1: Levantar el Backend (Spring Boot)
Abra una terminal en la carpeta `backend`:
```bash
cd C:\Users\jonat\OneDrive\Documentos\UCR-2026\IF0009\web_apps\unsecure_web_app\backend
mvn clean compile
mvn spring-boot:run
```
El servidor backend iniciará en el puerto **`8080`**. Verifique los siguientes endpoints:
* **API REST:** `http://localhost:8080/api/expedientes`
* **Consola H2 Database:** `http://localhost:8080/h2-console`
  * *JDBC URL:* `jdbc:h2:mem:insecure_care_db`
  * *User Name:* `sa`
  * *Password:* *(dejar en blanco)*

#### Paso 2: Levantar el Frontend (Angular)
Abra una segunda terminal en la carpeta `frontend`:
```bash
cd C:\Users\jonat\OneDrive\Documentos\UCR-2026\IF0009\web_apps\unsecure_web_app\frontend
npm install
npm start
```
La aplicación cliente estará accesible inmediatamente en el navegador web:
* **URL de la Aplicación:** `http://localhost:4200`

---

## 👥 Cuentas de Usuario Preconfiguradas para Pruebas

Para facilitar el análisis de control de acceso y autenticación, la base de datos cuenta con las siguientes credenciales:

| Nombre de Usuario | Contraseña | Rol Asignado | Identificador | Propósito en la Auditoría |
| :--- | :--- | :--- | :---: | :--- |
| `carlos_mora` | `carlos123` | `PACIENTE` | ID: 3 | Usuario estándar. Propietario del Expediente #1. |
| `maria_rojas` | `maria123` | `PACIENTE` | ID: 4 | Usuario víctima. Propietaria del Expediente #2 (Diagnóstico psiquiátrico confidencial). |
| `ana_solis` | `ana2026` | `PACIENTE` | ID: 5 | Usuario con diagnóstico de Diabetes (Expediente #3). |
| `dr_vargas` | `doctor2026` | `MEDICO` | ID: 2 | Profesional de la salud autorizado. |
| `admin` | `admin123` | `ADMIN` | ID: 1 | Administrador del sistema hospitalario. |

---

## 🎯 Instrucciones para la Auditoría de Seguridad (Quiz 9)

Su objetivo como auditor de seguridad es explorar e interactuar con la aplicación (tanto desde la interfaz gráfica web como utilizando herramientas de inspección, cURL, Burp Suite o las DevTools del navegador) para identificar las **vulnerabilidades de seguridad OWASP Top 10** presentes en el sistema.

Para cada vulnerabilidad identificada, usted deberá determinar:
1. **Categoría OWASP Top 10** correspondiente.
2. **Endpoint / Componente Vulnerable** (Back-end o Front-end).
3. **Mecanismo o Payload de Explotación** utilizado para evidenciar la falla.
4. **Impacto en el Negocio / Confidencialidad de Datos Médicos**.
5. **Propuesta de Remediación con Código Seguro**.

Consulte el documento **`Quiz_9.md`** en la carpeta de evaluaciones para resolver el cuestionario de auditoría y completar su hoja de hallazgos (*Pentest Findings Sheet*).

