package cr.ac.ucr.if0009.insecurecare.repository;

import cr.ac.ucr.if0009.insecurecare.model.ExpedienteMedico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExpedienteRepository extends JpaRepository<ExpedienteMedico, Long> {
    List<ExpedienteMedico> findByUsuarioId(Long usuarioId);
}

