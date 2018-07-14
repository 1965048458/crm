package com.xuebei.crm.journal;

import com.xuebei.crm.exception.AuthenticationException;
import com.xuebei.crm.exception.InformationNotCompleteException;

import java.util.*;

public interface JournalService {

    void createJournal(Journal journal) throws InformationNotCompleteException, AuthenticationException;

    void modifyJournal(Journal journal) throws InformationNotCompleteException, AuthenticationException;

    void deleteJournalById(String userId, String journalId) throws AuthenticationException;

    Journal queryJournalById(String userId, String journalId) throws AuthenticationException;

    List<Journal> searchJournal(JournalSearchParam param);

    List  searchDatail(String journalId);
}
