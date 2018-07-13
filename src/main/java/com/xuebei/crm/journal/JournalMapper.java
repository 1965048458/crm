package com.xuebei.crm.journal;

import com.google.gson.annotations.Expose;
import com.xuebei.crm.user.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface JournalMapper {

    // functions for table journal
    Integer createJournal(Journal journal);

    Journal queryJournalById(String journalId);

    Boolean userHasJournal(@Param("userId")String userId,
                           @Param("journalId")String journalId);

    Integer updateJournal(Journal journal);

    Integer deleteJournal(@Param("userId") String userId,
                          @Param("journalId") String journalId);

    // functions for table visit_log
    Integer deleteVisitLog(@Param("journalId") String journalId);

    Integer insertVisitLogs(@Param("journalId") String journalId,
                            @Param("visitRecords") List<VisitRecord> visitRecords);

    List<VisitRecord> queryVisitLogs(@Param("journalId") String journalId);


    // functions for table visit_contacts
    Integer deleteVisitContacts(@Param("visitId") String visitId);

    Integer insertVisitContacts(@Param("visitId") String visitId,
                                @Param("contactsIds") List<String> contactsIds);

    List<String> queryVistContacts(@Param("visitId") String visitId);

    // functions for table journal_receiver
    Integer deleteJournalReceiver(@Param("journalId") String journalId);

    Integer insertJournalReceiver(@Param("journalId") String journalId,
                                  @Param("receivers") List<User> receivers);

    List<User> queryJournalReceiver(@Param("journalId") String journalId);

    // other functions
    Boolean isUserSameCompany(@Param("userIdA") String userIdA,
                              @Param("userIdB") String userIdB);
}
