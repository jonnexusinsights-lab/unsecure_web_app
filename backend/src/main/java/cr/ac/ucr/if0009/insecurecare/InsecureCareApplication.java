package cr.ac.ucr.if0009.insecurecare;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class InsecureCareApplication {

    public static void main(String[] args) {
        SpringApplication.run(InsecureCareApplication.class, args);
        System.out.println("==================================================");
        System.out.println(" InsecureCare Medical Portal - Backend Iniciado");
        System.out.println(" Servidor REST: http://localhost:8080/api");
        System.out.println(" Consola H2:    http://localhost:8080/h2-console");
        System.out.println(" JDBC URL:      jdbc:h2:mem:insecure_care_db");
        System.out.println(" ADVERTENCIA:   Aplicacion intencionalmente vulnerable");
        System.out.println("==================================================");
    }
}

