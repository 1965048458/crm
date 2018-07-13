package com.xuebei.crm.journal;

import com.xuebei.crm.exception.AuthenticationException;
import com.xuebei.crm.exception.InformationNotCompleteException;
import org.springframework.stereotype.Service;

public interface JournalService {

    void createJournal(Journal journal) throws InformationNotCompleteException, AuthenticationException;

    void modifyJournal(Journal journal) throws InformationNotCompleteException, AuthenticationException;

    void createJournal(String userId, String type, String summary, String plan, boolean hasSubmitted);

    Journal queryJournalById(String journalId);

    void modifyJournal(String journalId, String newSummary, String newPlan, boolean hasSubmitted);

    Integer deleteJournalById(String userId, String journalId);

}
