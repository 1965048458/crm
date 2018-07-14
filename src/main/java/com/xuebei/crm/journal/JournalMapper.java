package com.xuebei.crm.journal;

import com.xuebei.crm.user.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

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

    // functions for table visit_log
    Integer deleteVisitLog(@Param("journalId") String journalId);

    Integer insertVisitLog(VisitRecord visitRecord);

    List<VisitRecord> queryVisitLogs(@Param("journalId") String journalId);


    // functions for table visit_contacts
    Integer deleteVisitContacts(@Param("visitId") String visitId);

    Integer insertVisitContacts(@Param("visitId") String visitId,
                                @Param("contactsId") String contactsId);

    List<String> queryVisitContacts(@Param("visitId") String visitId);

    // functions for table journal_receiver
    Integer deleteJournalReceiver(@Param("journalId") String journalId);

    Integer insertJournalReceiver(@Param("journalId") String journalId,
                                  @Param("receiverId") String receiverId);

    List<User> queryJournalReceiver(@Param("journalId") String journalId);

    // other functions
    Boolean isUserSameCompany(@Param("userIdA") String userIdA,
                              @Param("userIdB") String userIdB);

    List<Journal> searchMyJournal(JournalSearchParam param);

    List<Journal> searchReceivedJournal(JournalSearchParam param);
}
