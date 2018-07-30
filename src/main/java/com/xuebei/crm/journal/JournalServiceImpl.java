package com.xuebei.crm.journal;

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

    /**
     * 创建一个新日志
     * 注意：调用该方法前，需要将 session 中 userId 装载入 journal 对象中(Not checked)
     * @param journal
     */
    @Override
    public void createJournal(Journal journal) throws InformationNotCompleteException, AuthenticationException {
        checkBasicInfo(journal);
        checkVisitRecords(journal.getVisitRecords());
        checkReceivers(journal.getUserId(), journal.getReceivers());

        journalMapper.createJournal(journal);
        insertVisitRecords(journal.getJournalId(), journal.getVisitRecords());
        insertReceivers(journal.getJournalId(), journal.getReceivers());
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
        checkReceivers(journal.getUserId(), journal.getReceivers());

        journalMapper.updateJournal(journal);
        deleteVisitRecords(journal.getJournalId());
        insertVisitRecords(journal.getJournalId(), journal.getVisitRecords());
        journalMapper.deleteJournalReceiver(journal.getJournalId());
        insertReceivers(journal.getJournalId(), journal.getReceivers());
    }

    @Override
    public void deleteJournalById(String userId, String journalId) throws AuthenticationException {
        if (!journalMapper.userHasJournal(userId, journalId)) {
            throw new AuthenticationException("用户不拥有此日志");
        }

        deleteVisitRecords(journalId);
        journalMapper.deleteJournalReceiver(journalId);
        journalMapper.deleteJournal(userId, journalId);
    }

    @Override
    public Journal queryJournalById(String userId, String journalId) throws AuthenticationException {
        if (!journalMapper.userHasJournal(userId, journalId))
            throw new AuthenticationException("用户不拥有此日志");

        Journal journal = journalMapper.queryJournalById(journalId);
        journal.setVisitRecords(journalMapper.queryVisitLogs(journalId));
        for (VisitRecord visitRecord: journal.getVisitRecords()) {
            visitRecord.setContactsIds(journalMapper.queryVisitContacts(visitRecord.getVisitId()));
        }
        journal.setReceivers(journalMapper.queryJournalReceiver(journal.getJournalId()));

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
            if (visitRecord.getContactsIds() == null)
                continue;
//            for (String contactsId: visitRecord.getContactsIds()) {
//                // TODO: contactsId 联系人是否为公司所有(权限)
//            }
        }
    }

    /**
     * 检查接收人对象中的 信息完整度 和 接收人权限
     * @param currentUserId 当前登录用户的ID
     * @param receivers 接受者的 List 对象
     * @throws InformationNotCompleteException 接受者对象缺少必要的信息(userId)
     * @throws AuthenticationException 权限问题, 接收人非同公司
     */
    private void checkReceivers(String currentUserId, List<User> receivers)
            throws InformationNotCompleteException, AuthenticationException {
        if (receivers == null)
            return;
        for (User receiver: receivers) {
            if (receiver.getUserId() == null)
                throw new InformationNotCompleteException("receiver.userId 不能为空");
            String receiverUserId = receiver.getUserId();
            if (!journalMapper.isUserSameCompany(currentUserId, receiverUserId)) {
                throw new AuthenticationException("接收日志用户与发送日志用户不在同一公司");
            }
        }
    }

    private void insertVisitRecords(String journalId, List<VisitRecord> visitRecords) {
        if (visitRecords == null)
            return;
        for (VisitRecord visitRecord: visitRecords) {
            visitRecord.setJournalId(journalId);
            journalMapper.insertVisitLog(visitRecord);
            if (visitRecord.getContactsIds() == null)
                continue;
            for (String contactsId: visitRecord.getContactsIds()) {
                journalMapper.insertVisitContacts(visitRecord.getVisitId(), contactsId);
            }
        }
    }

    private void insertReceivers(String journalId, List<User> receivers) {
        if (receivers == null)
            return;
        for (User receiver: receivers) {
            journalMapper.insertJournalReceiver(journalId, receiver.getUserId());
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
        List<Journal> receivedJournal = journalMapper.searchReceivedJournal(param);
        List<Journal> allJournalList = new ArrayList<>();

        allJournalList.addAll(myJournal);
        allJournalList.addAll(receivedJournal);
        if (param.getIsMine() != null && param.getIsMine() == 1 ){
            allJournalList.removeAll(receivedJournal);
        }
        allJournalList.sort((journal1, journal2)-> journal1.getCreateTs().before(journal2.getCreateTs())?1:-1);
        //统计日志有多少人已读
        for (Journal jn : allJournalList  ) {
            List<User> journalRead = journalMapper.searchRead(jn.getJournalId());
            jn.setReadNum(journalRead.size());
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
    public Journal loadDetail(String journalId) {
        Journal journal = journalMapper.loadDetail(journalId);
        List<VisitRecord> records = journalMapper.loadVisitRecs(journalId);
        List<JournalPatch> patches = journalMapper.loadPatches(journalId);
        journal.setPatchList(patches);
        journal.setVisitRecords(records);
        return journal;
    }
}
