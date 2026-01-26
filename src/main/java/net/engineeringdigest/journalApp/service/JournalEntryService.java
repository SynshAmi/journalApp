package net.engineeringdigest.journalApp.service;

import lombok.extern.slf4j.Slf4j;
import net.engineeringdigest.journalApp.entity.JournalEntry;
import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.repository.JournalEntryRepository;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class JournalEntryService {

    @Autowired
    private JournalEntryRepository journalEntryRepository;

    @Autowired
    private UserService userService;

    public List<JournalEntry> getAll()
    {
        return journalEntryRepository.findAll();
    }

    @Transactional
    public void saveEntry(JournalEntry journalEntry, String userName)
    {
        try {
            User user=userService.findByUserName(userName);
            journalEntry.setDate(LocalDateTime.now());
            JournalEntry save = journalEntryRepository.save(journalEntry);
            user.getJournalEntries().add(save);
            userService.saveUser(user);
        }
        catch (Exception e)
        {
            log.error("Exception ", e);
            throw new RuntimeException("Some error occurred while saving entry to user.", e);
        }
    }

    public void saveEntry(JournalEntry journalEntry)
    {
        try {
            journalEntryRepository.save(journalEntry);
        }
        catch (Exception e)
        {
            log.error("Exception ", e);
        }
    }

    public Optional<JournalEntry> findById(ObjectId id)
    {
        return journalEntryRepository.findById(id);
    }

    @Transactional
    public boolean deleteById(ObjectId id, String userName)
    {
        boolean deleted=false;
        try {
            User user=userService.findByUserName(userName);
            deleted=user.getJournalEntries().removeIf(x -> x.getId().equals(id));
            if(deleted)
            {
                userService.saveUser(user);
                journalEntryRepository.deleteById(id);
            }
        }
        catch (Exception e)
        {
            log.error("Exception ", e);
            throw new RuntimeException("Some error came while deleting the entry.");
        }
        return deleted;
    }

}

//controller ---> service ---> repository