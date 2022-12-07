package ma.octo.assignement.repository;

import ma.octo.assignement.domain.Audit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface AuditTransferRepository extends JpaRepository<Audit, Long> {

}
