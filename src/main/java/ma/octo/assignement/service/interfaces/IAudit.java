package ma.octo.assignement.service.interfaces;

import ma.octo.assignement.domain.AuditTransfer;

import java.util.List;

public interface IAudit {

    AuditTransfer auditTransfer(String message);

    AuditTransfer auditDeposit(String message);

    List<AuditTransfer> findAll();
}
