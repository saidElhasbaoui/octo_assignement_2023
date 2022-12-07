package ma.octo.assignement.service.interfaces;

import ma.octo.assignement.domain.Audit;

import java.util.List;

public interface IAudit {

    Audit auditTransfer(String message);

    Audit auditDeposit(String message);

    List<Audit> findAll();
}
