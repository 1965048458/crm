package com.xuebei.crm.journal;

import com.xuebei.crm.customer.BigCustomer;
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
    
    List<Journal> searchJournal2(String journalId);

    List  searchDatail(String journalId);

    List<JournalCustomer> getAllContacts(String companyId,String userId);

    List<ManageJournal> getJournalState(String userId);

    List<FollowJournal> getJournalFollow(String userId,float delay,float miss);

    List<FollowJournal> getJournalFollow(String userId,int index,float delay,float miss);
    
    List<BigCustomer> getAllCustomers(String companyId,String userId);

    Set<String> getAllSubordinatesUserId(String userId);

    Set<Project> getAllSubordinatesProjects(Set<String> userGroup);

    Set<Opportunity> getAllSubordinatesOpportunity(Set<String> userGroup);
}
