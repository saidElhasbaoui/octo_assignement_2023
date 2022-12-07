package ma.octo.assignement.service;

import ma.octo.assignement.domain.Audit;
import ma.octo.assignement.domain.util.EventType;
import ma.octo.assignement.repository.AuditTransferRepository;
import ma.octo.assignement.service.interfaces.IAudit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class AuditService implements IAudit {

    Logger LOGGER = LoggerFactory.getLogger(AuditService.class);

    private final AuditTransferRepository auditTransferRepository;

    @Autowired
    public AuditService(AuditTransferRepository auditTransferRepository) {
        this.auditTransferRepository = auditTransferRepository;
    }

    @Override
    public Audit auditTransfer(String message) {
        LOGGER.info("Audit de l'événement {}", EventType.TRANSFER);
        Audit audit = new Audit();
        audit.setEventType(EventType.TRANSFER);
        audit.setMessage(message);
        auditTransferRepository.save(audit);

        return audit;
    }

    @Override
    public Audit auditDeposit(String message) {

        LOGGER.info("Audit de l'événement {}", EventType.DEPOSIT);

        Audit audit = new Audit();
        audit.setEventType(EventType.DEPOSIT);
        audit.setMessage(message);
        auditTransferRepository.save(audit);

        return audit;
    }

    @Override
    public List<Audit> findAll() {
        List<Audit> audits = auditTransferRepository.findAll();

        return CollectionUtils.isEmpty(audits) ? null : audits;
    }
}
