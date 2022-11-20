package ma.octo.assignement.service;

import ma.octo.assignement.domain.AuditTransfer;
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
    public AuditTransfer auditTransfer(String message) {
        LOGGER.info("Audit de l'événement {}", EventType.TRANSFER);
        AuditTransfer audit = new AuditTransfer();
        audit.setEventType(EventType.TRANSFER);
        audit.setMessage(message);
        auditTransferRepository.save(audit);

        return audit;
    }

    @Override
    public AuditTransfer auditDeposit(String message) {

        LOGGER.info("Audit de l'événement {}", EventType.DEPOSIT);

        AuditTransfer audit = new AuditTransfer();
        audit.setEventType(EventType.DEPOSIT);
        audit.setMessage(message);
        auditTransferRepository.save(audit);

        return audit;
    }

    @Override
    public List<AuditTransfer> findAll() {
        List<AuditTransfer> auditTransfers = auditTransferRepository.findAll();

        return CollectionUtils.isEmpty(auditTransfers) ? null : auditTransfers;
    }
}
