package com.xuebei.crm.journal;

import com.xuebei.crm.customer.Contacts;
import com.xuebei.crm.customer.Department;
import com.xuebei.crm.user.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

@Mapper
public interface JournalMapper {

    // functions for table journal
    Integer createJournal(Journal journal);

    Journal queryJournalById(@Param("journalId") String journalId);

    Boolean userHasJournal(@Param("userId") String userId,
                           @Param("journalId") String journalId);

    Integer updateJournal(Journal journal);

    Integer deleteJournal(@Param("userId") String userId,
                          @Param("journalId") String journalId);

    List<Journal> findJournalDraft(@Param("userId") String userId);

    List<ManageJournal> getJournalState(@Param("userId") String userId,@Param("monthStart") Date monthStart);

    // functions for table visit_log
    Integer insertRepairDate(@Param("journalId") String journalId);

    Date queryRepairDate(@Param("journalId")String journalId);

    Integer deleteVisitLog(@Param("journalId") String journalId);

    Integer insertVisitLog(VisitRecord visitRecord);

    List<VisitRecord> queryVisitLogs(@Param("journalId") String journalId);


    // functions for table visit_contacts
    Integer deleteVisitContacts(@Param("visitId") String visitId);

    Integer insertVisitContacts(@Param("visitId") String visitId,
                                @Param("contactsId") String contactsId);

    List<Contacts> queryVisitContacts(@Param("visitId") String visitId);

    // functions for table journal_receiver
    Integer deleteJournalReceiver(@Param("journalId") String journalId);

    Integer insertJournalReceiver(@Param("journalId") String journalId,
                                  @Param("receiverId") String receiverId);

    List<User> queryJournalReceiver(@Param("journalId") String journalId);

    Integer receiverDeleteJournal(@Param("journalId") String journalId,
                                  @Param("receiverId") String receiverId);

    // function for journal attachment
    Integer insertJournalPatch(@Param("journalId") String journalId,
                               @Param("content") String content);

    // other functions
    Boolean isUserSameCompany(@Param("userIdA") String userIdA,
                              @Param("userIdB") String userIdB);

    List<User> queryColleagues(@Param("userId") String userId);

    List<Journal> searchMyJournal(JournalSearchParam param);

    List<Journal> searchReceivedJournal(JournalSearchParam param);

    Journal searchJournal(@Param("journalId") String journalId);

    List<User> searchUnread(@Param("journalId") String journalId);

    List<User> searchRead(@Param("journalId") String journalId);

    List<JournalCustomer> queryJournalCustomersByCompanyId(@Param("userId") String userId);

    List<Contacts> queryContactsByCustomerId(@Param("deptId") String deptId);

    List<JournalPatch> queryJournalPatch(@Param("journalId") String journalId);

    List<String> querySubordinatesByUserId(@Param("userId") String userId);

    List<String> queryDeptIdByJournalId(@Param("journalId") String journalId);

    List<String> queryDeptId(@Param("userId") String userId,
                             @Param("customerId")String customerId);
    
    //客户组织机构列表中显示的机构
    List<Department> searchDepts(@Param("customerId") String customerId,
                                 @Param("userId") String userId);


}
