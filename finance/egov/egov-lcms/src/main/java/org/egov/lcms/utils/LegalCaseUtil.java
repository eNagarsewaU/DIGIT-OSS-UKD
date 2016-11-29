/**
 * eGov suite of products aim to improve the internal efficiency,transparency,
   accountability and the service delivery of the government  organizations.

    Copyright (C) <2015>  eGovernments Foundation

    The updated version of eGov suite of products as by eGovernments Foundation
    is available at http://www.egovernments.org

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program. If not, see http://www.gnu.org/licenses/ or
    http://www.gnu.org/licenses/gpl.html .

    In addition to the terms of the GPL license to be adhered to in using this
    program, the following additional terms are to be complied with:

	1) All versions of this program, verbatim or modified must carry this
	   Legal Notice.

	2) Any misrepresentation of the origin of the material is prohibited. It
	   is required that all modified versions of this material be marked in
	   reasonable ways as different from the original version.

	3) This license does not grant any rights to any user of the program
	   with regards to rights under trademark law for use of the trade names
	   or trademarks of eGovernments Foundation.

  In case of any queries, you can reach eGovernments Foundation at contact@egovernments.org.
 */
package org.egov.lcms.utils;

import java.util.List;
import java.util.Set;

import org.egov.commons.EgwStatus;
import org.egov.commons.dao.EgwStatusHibernateDAO;
import org.egov.eis.entity.Employee;
import org.egov.eis.service.EmployeeService;
import org.egov.eis.service.PositionMasterService;
import org.egov.infra.admin.master.entity.Department;
import org.egov.infra.admin.master.service.DepartmentService;
import org.egov.infra.filestore.entity.FileStoreMapper;
import org.egov.infra.utils.FileStoreUtils;
import org.egov.lcms.transactions.entity.AppealDocuments;
import org.egov.lcms.transactions.entity.BipartisanDetails;
import org.egov.lcms.transactions.entity.Judgment;
import org.egov.lcms.transactions.entity.JudgmentDocuments;
import org.egov.lcms.transactions.entity.JudgmentImpl;
import org.egov.lcms.transactions.entity.LcInterimOrderDocuments;
import org.egov.lcms.transactions.entity.LegalCase;
import org.egov.lcms.transactions.entity.LegalCaseDocuments;
import org.egov.lcms.transactions.entity.LegalCaseInterimOrder;
import org.egov.lcms.transactions.entity.PwrDocuments;
import org.egov.lcms.transactions.repository.JudgmentImplRepository;
import org.egov.lcms.transactions.repository.JudgmentRepository;
import org.egov.lcms.transactions.repository.LegalCaseInterimOrderRepository;
import org.egov.lcms.transactions.repository.LegalCaseRepository;
import org.egov.lcms.utils.constants.LcmsConstants;
import org.egov.pims.commons.Position;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
public class LegalCaseUtil {

    @Autowired
    private EgwStatusHibernateDAO egwStatusDAO;

    @Autowired
    private LegalCaseRepository legalCaseRepository;

    @Autowired
    private PositionMasterService positionMasterService;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private FileStoreUtils fileStoreUtils;

    @Autowired
    private JudgmentRepository judgmentRepository;
    
    @Autowired
    private  JudgmentImplRepository judgmentImplRepository;

    @Autowired
    private LegalCaseInterimOrderRepository legalCaseInterimOrderRepository;

    @Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = true)
    public EgwStatus getStatusForModuleAndCode(final String moduleName, final String statusCode) {
        final EgwStatus status = egwStatusDAO.getStatusByModuleAndCode(moduleName, statusCode);
        return status;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = true)
    public List<EgwStatus> getStatusForModule() {
        final List<EgwStatus> statusList = egwStatusDAO.getStatusByModule(LcmsConstants.MODULE_TYPE_LEGALCASE);
        return statusList;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = true)
    public Position getPositionByName(final String name) {
        return positionMasterService.getPositionByName(name);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = true)
    public Department getDepartmentByName(final String name) {
        return departmentService.getDepartmentByName(name);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = true)
    public List<BipartisanDetails> getBipartitionList(final LegalCase legalcase) {
        return legalCaseRepository.getBipartitionDetList(legalcase.getId());
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = true)
    public List<LegalCaseDocuments> getLegalCaseDocumentList(final LegalCase legalcase) {
        final List<LegalCaseDocuments> legalDoc = legalCaseRepository.getLegalCaseDocumentList(legalcase.getId());
        return legalDoc;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = true)
    public List<JudgmentDocuments> getJudgmentDocumentList(final Judgment judgment) {
        final List<JudgmentDocuments> judgmentDoc = judgmentRepository.getJudgmentDocumentList(judgment.getId());
        return judgmentDoc;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = true)
    public List<LcInterimOrderDocuments> getLcInterimOrderDocumentList(
            final LegalCaseInterimOrder legalCaseInterimOrder) {
        final List<LcInterimOrderDocuments> interimOrderDoc = legalCaseInterimOrderRepository
                .getLcInterimOrderDocumentList(legalCaseInterimOrder.getId());
        return interimOrderDoc;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = true)
    public Employee getEmployeeByUserName(final String userName) {
        final Employee employee = employeeService.getEmployeeByUserName(userName);
        return employee;

    }

    @Transactional
    public Set<FileStoreMapper> addToFileStore(final MultipartFile[] files) {
        return fileStoreUtils.addToFileStore(files, LcmsConstants.FILESTORE_MODULECODE);
    }

    public List<PwrDocuments> getPwrDocumentList(final LegalCase legalcase) {
        final List<PwrDocuments> pwrDoc = legalCaseRepository.getPwrDocumentList(legalcase.getPwrList().get(0).getId());
        return pwrDoc;

    }
    
    public List<AppealDocuments> getAppealDocumentList(final JudgmentImpl judgmentImpl) {
        final List<AppealDocuments> appealDoc = judgmentImplRepository.getAppealDocumentList(judgmentImpl.getAppeal().get(0).getId());
        return appealDoc;

    }
}
