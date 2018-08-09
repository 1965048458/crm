package com.xuebei.crm.journal;

import com.xuebei.crm.exception.AuthenticationException;
import com.xuebei.crm.exception.InformationNotCompleteException;
import com.xuebei.crm.opportunity.Opportunity;
import com.xuebei.crm.project.Project;
import com.xuebei.crm.user.User;

import java.util.*;

public interface JournalService {

    void createJournal(Journal journal) throws InformationNotCompleteException, AuthenticationException;

    void modifyJournal(Journal journal) throws InformationNotCompleteException, AuthenticationException;

    void deleteJournalById(String userId, String journalId) throws AuthenticationException;

    Journal queryJournalById(String userId, String journalId) throws AuthenticationException;

    List<Journal> searchJournal(JournalSearchParam param);

    List  searchDatail(String journalId);

    List<JournalCustomer> getAllContacts(String companyId);

    Set<String> getAllSubordinatesUserId(String userId);

    Set<Project> getAllSubordinatesProjects(Set<String> userGroup);

    Set<Opportunity> getAllSubordinatesOpportunity(Set<String> userGroup);
}
