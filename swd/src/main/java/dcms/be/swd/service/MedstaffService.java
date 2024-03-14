package dcms.be.swd.service;

import dcms.be.swd.entity.Account;
import dcms.be.swd.entity.MedStaffInfo;
import dcms.be.swd.repository.AccountRepository;
import dcms.be.swd.repository.MedStaffInfoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class MedstaffService {

    private MedStaffInfoRepository medStaffInfoRepository;
    private AccountRepository accountRepository;

    public MedstaffService(MedStaffInfoRepository medStaffInfoRepository, AccountRepository accountRepository) {
        this.medStaffInfoRepository = medStaffInfoRepository;
        this.accountRepository = accountRepository;
    }

    public ResponseEntity<MedStaffInfo> deleteEmployee(MedStaffInfo medstaff) throws Exception {
        MedStaffInfo medStaffInfo = medStaffInfoRepository
                .findById(medstaff.getId())
                .orElseThrow(() -> new Exception("Employee is not found"));
        Account a = accountRepository
                .findAccountByUserId(medStaffInfo.getUser().getId())
                .orElseThrow(() -> new Exception("Account is not found"));
        a.setEmail(null);
        accountRepository.save(a);
        return new ResponseEntity<>(medstaff, HttpStatus.OK);
    }

    public ResponseEntity<MedStaffInfo> findEmployeeDetail(MedStaffInfo medstaff) throws Exception {
        MedStaffInfo medStaffInfo = medStaffInfoRepository
                .findById(medstaff.getId())
                .orElseThrow(() -> new Exception("Employee is not found"));
        return new ResponseEntity<>(medStaffInfo, HttpStatus.OK);

    }

    public ResponseEntity<MedStaffInfo> updateEmployee(MedStaffInfo medstaff) throws Exception {
        MedStaffInfo medStaffInfo = medStaffInfoRepository
                .findById(medstaff.getId())
                .orElseThrow(() -> new Exception("Employee is not found"));

        if (medstaff.getExperience() != null)
            medStaffInfo.setExperience(medstaff.getExperience());

        if (medstaff.getQuanlification() != null)
            medStaffInfo.setQuanlification(medstaff.getQuanlification());

        if (medstaff.getUser() != null) {
            medstaff.getUser().setId(medStaffInfo.getUser().getId());
            medStaffInfo.setUser(medstaff.getUser());
        }


        MedStaffInfo medStaffInfoUpdate = medStaffInfoRepository.save(medStaffInfo);
        return new ResponseEntity<>(medStaffInfoUpdate, HttpStatus.OK);
    }
}
