package io.pivotal.pal.tracker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryTimeEntryRepository implements TimeEntryRepository {

    private Map<Long, TimeEntry> timeEntryMap = new HashMap<>();
    private long idCounter = 1L;

    @Override
    public TimeEntry create(TimeEntry timeEntry) {
        TimeEntry createdTimeEntry = new TimeEntry(idCounter, timeEntry.getProjectId(), timeEntry.getUserId(), timeEntry.getDate(), timeEntry.getHours());
        timeEntryMap.put(idCounter, createdTimeEntry);
        ++idCounter;
        return createdTimeEntry;
    }

    @Override
    public TimeEntry find(long id) {
        return timeEntryMap.get(id);
    }

    @Override
    public List<TimeEntry> list() {
        return new ArrayList<>(timeEntryMap.values());
    }

    @Override
    public TimeEntry update(long id, TimeEntry timeEntry) {
        if (timeEntryMap.containsKey(id)) {
            TimeEntry updatedTimeEntry = new TimeEntry(id, timeEntry.getProjectId(), timeEntry.getUserId(), timeEntry.getDate(), timeEntry.getHours());
            timeEntryMap.put(id, updatedTimeEntry);
            return updatedTimeEntry;
        } else {
            return null;
        }
    }

    @Override
    public void delete(long id) {
        timeEntryMap.remove(id);
    }
}
