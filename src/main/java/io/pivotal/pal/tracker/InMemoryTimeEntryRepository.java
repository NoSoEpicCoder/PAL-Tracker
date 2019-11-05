package io.pivotal.pal.tracker;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class InMemoryTimeEntryRepository implements TimeEntryRepository {

    private Map<Long, TimeEntry> timeEntryMap = new HashMap<>();
    private long idCounter = 1L;

    public TimeEntry create(TimeEntry timeEntry) {
        TimeEntry createdTimeEntry = new TimeEntry(idCounter, timeEntry.getProjectId(), timeEntry.getUserId(), timeEntry.getDate(), timeEntry.getHours());
        timeEntryMap.put(idCounter, createdTimeEntry);
        ++idCounter;
        return createdTimeEntry;
    }

    public TimeEntry find(long id) {
        return timeEntryMap.get(id);
    }

    public List<TimeEntry> list() {
        return timeEntryMap.entrySet().stream().map(Map.Entry::getValue).collect(Collectors.toList());
    }

    public TimeEntry update(long id, TimeEntry timeEntry) {
        if (timeEntryMap.containsKey(id)) {
            TimeEntry updatedTimeEntry = new TimeEntry(id, timeEntry.getProjectId(), timeEntry.getUserId(), timeEntry.getDate(), timeEntry.getHours());
            timeEntryMap.put(id, updatedTimeEntry);
            return updatedTimeEntry;
        } else {
            return null;
        }
    }

    public void delete(long id) {
        timeEntryMap.remove(id);
    }
}
