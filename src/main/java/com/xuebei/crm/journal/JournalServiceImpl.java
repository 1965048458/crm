package com.xuebei.crm.journal;

import com.xuebei.crm.customer.Contacts;
import com.xuebei.crm.customer.ContactsDept;
import com.xuebei.crm.customer.Customer;
import com.xuebei.crm.customer.CustomerMapper;
import com.xuebei.crm.exception.AuthenticationException;
import com.xuebei.crm.exception.InformationNotCompleteException;
import com.xuebei.crm.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.*;

@Service
public class JournalServiceImpl implements JournalService {

    @Autowired
    private JournalMapper journalMapper;

    @Autowired
    private CustomerMapper customerMapper;

    /**
     * 创建一个新日志
     * 注意：调用该方法前，需要将 session 中 userId 装载入 journal 对象中(Not checked)
     * @param journal
     */
    @Override
    public void createJournal(Journal journal) throws InformationNotCompleteException, AuthenticationException {
        checkBasicInfo(journal);
        checkVisitRecords(journal.getVisitRecords());

        journalMapper.createJournal(journal);
        insertVisitRecords(journal.getJournalId(), journal.getVisitRecords());
    }

    @Override
    public void modifyJournal(Journal journal) throws InformationNotCompleteException, AuthenticationException {
        checkBasicInfo(journal);
        Journal oldJournal = journalMapper.queryJournalById(journal.getJournalId());
        if (oldJournal == null || !oldJournal.getUserId().equals(journal.getUserId())) {
            throw new AuthenticationException("用户不拥有此日志");
        } else if (oldJournal.getHasSubmitted()) {
            throw new AuthenticationException("日志已提交");
        }
        checkVisitRecords(journal.getVisitRecords());

        journalMapper.updateJournal(journal);
        deleteVisitRecords(journal.getJournalId());
        insertVisitRecords(journal.getJournalId(), journal.getVisitRecords());
    }

    @Override
    public void deleteJournalById(String userId, String journalId) throws AuthenticationException {
        if (!journalMapper.userHasJournal(userId, journalId)) {
            throw new AuthenticationException("用户不拥有此日志");
        }

        deleteVisitRecords(journalId);
        journalMapper.deleteJournal(userId, journalId);
    }

    @Override
    public Journal queryJournalById(String userId, String journalId) throws AuthenticationException {
        if (!journalMapper.userHasJournal(userId, journalId))
            throw new AuthenticationException("用户不拥有此日志");

        Journal journal = journalMapper.queryJournalById(journalId);
        journal.setVisitRecords(journalMapper.queryVisitLogs(journalId));
        for (VisitRecord visitRecord: journal.getVisitRecords()) {
            visitRecord.setChosenContacts(journalMapper.queryVisitContacts(visitRecord.getVisitId()));
        }

        return journal;
    }

    /**
     * 检查 journal 对象中 type summary plan hasSubmitted 成员是否为空，若为空则抛出异常
     * @param journal 被检查的 journal 对象
     * @throws InformationNotCompleteException 信息不完整
     */
    private void checkBasicInfo(Journal journal) throws InformationNotCompleteException {
        if (journal.getType() == null)
            throw new InformationNotCompleteException("journal.type 不能为空");
        if (journal.getSummary() == null)
            throw new InformationNotCompleteException("journal.summary 不能为空");
        if (journal.getPlan() == null)
            throw new InformationNotCompleteException("journal.plan 不能为空");
        if (journal.getHasSubmitted() == null)
            throw new InformationNotCompleteException("journal.hasSubmitted 不能为空");
    }

    /**
     * 检查访问记录对象中的 信息完整度 和 联系人权限
     * @param visitRecords 访问记录
     * @throws InformationNotCompleteException 信息不完整
     */
    private void checkVisitRecords(List<VisitRecord> visitRecords) throws InformationNotCompleteException {
        if (visitRecords == null)
            return;
        for (VisitRecord visitRecord: visitRecords) {
            if (visitRecord == null)
                throw new InformationNotCompleteException("visitRecord 不能为空");
            if (visitRecord.getVisitType() == null)
                throw new InformationNotCompleteException("visitType 不能为空");
            if (visitRecord.getVisitResult() == null)
                throw new InformationNotCompleteException("visitResult 不能为空");
//            if (visitRecord.getContactsIds() == null)
//                continue;
//            for (String contactsId: visitRecord.getContactsIds()) {
//                // TODO: contactsId 联系人是否为公司所有(权限)
//            }
        }
    }

    private void insertVisitRecords(String journalId, List<VisitRecord> visitRecords) {
        if (visitRecords == null)
            return;
        for (VisitRecord visitRecord: visitRecords) {
            visitRecord.setJournalId(journalId);
            journalMapper.insertVisitLog(visitRecord);
//            if (visitRecord.getContactsIds() == null)
//                continue;
            for (Contacts contacts: visitRecord.getChosenContacts()) {
                journalMapper.insertVisitContacts(visitRecord.getVisitId(), contacts.getContactsId());
            }
        }
    }

    private void deleteVisitRecords(String journalId) {
        List<VisitRecord> records = journalMapper.queryVisitLogs(journalId);
        for (VisitRecord record: records) {
            journalMapper.deleteVisitContacts(record.getVisitId());
        }
        journalMapper.deleteVisitLog(journalId);
    }

    @Override
    public List<Journal> searchJournal(JournalSearchParam param) {
        ///分解发送人字符串
        if (param.getSenderIds() != "" && param.getSenderIds() != null){
            param.setSdId(param.getSenderIds().trim().split(","));
        }

        List<Journal> myJournal = journalMapper.searchMyJournal(param);
        List<Journal> receivedJournal = new ArrayList<>();
        if (param.getUserId().equals("00284bca325c4e77b9f30c5671ec1c44")) {
            receivedJournal = journalMapper.searchReceivedJournal(param);
        }

        List<Journal> allJournalList = new ArrayList<>();

        allJournalList.addAll(myJournal);
        allJournalList.addAll(receivedJournal);

        if (param.getIsMine() != null && param.getIsMine() == 1 ){
            allJournalList.removeAll(receivedJournal);
        }
        allJournalList.sort((journal1, journal2)-> journal1.getCreateTs().before(journal2.getCreateTs())?1:-1);
        //统计日志有多少人已读
        for (Journal jn : allJournalList  ) {
            jn.setVisitRecords(journalMapper.queryVisitLogs(jn.getJournalId()));
            for (VisitRecord visitRecord: jn.getVisitRecords()) {
                visitRecord.setChosenContacts(journalMapper.queryVisitContacts(visitRecord.getVisitId()));
                for (Contacts contacts: visitRecord.getChosenContacts()) {
                    ContactsDept contactsDept = customerMapper.queryContactsDept(contacts.getContactsId());
                    contacts.setTotalName(contactsDept.toString());
                }
            }
            List<User> journalRead = journalMapper.searchRead(jn.getJournalId());
            jn.setReadNum(journalRead.size());
            // 查询日志补丁
            jn.setJournalPatches(journalMapper.queryJournalPatch(jn.getJournalId()));
            // 跟新是否是自己
            jn.setIsMine(jn.getUserId().equals(param.getUserId()));
        }
        return allJournalList;
    }

    @Override
    public  List searchDatail(String journalId){
        Journal myJournal = journalMapper.searchJournal(journalId);
        List<User> journalUnread = journalMapper.searchUnread(journalId);
        List<User> journalRead = journalMapper.searchRead(journalId);
        List result = new ArrayList();
        result.add(myJournal);
        result.add(journalUnread);
        result.add(journalRead);
        return result;
    }

    @Override
    public List<JournalCustomer> getAllContacts(String companyId) {
        List<JournalCustomer> customerList = journalMapper.queryJournalCustomersByCompanyId(companyId);
        for (JournalCustomer customer: customerList) {
            customer.setContactsGroup(journalMapper.queryContactsByCustomerId(customer.getCustomerId()));
        }
        return customerList;
    }
}
