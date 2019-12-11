package io.pivotal.pal.tracker;

import java.util.ArrayList;
import java.util.List;

public class InMemoryTimeEntryRepository implements TimeEntryRepository {
    private List<TimeEntry> timeEntries = new ArrayList();
    private long currentId = 1L;

    @Override
    public TimeEntry create(TimeEntry timeEntry) {
        long id = currentId++;
        TimeEntry entry = new TimeEntry(
                id,
                timeEntry.getProjectId(),
                timeEntry.getUserId(),
                timeEntry.getDate(),
                timeEntry.getHours()
        );

        timeEntries.add(entry);

        return entry;
    }

    @Override
    public TimeEntry find(long id) {
        return timeEntries.stream().filter(x -> x.getId() == id).findFirst().orElse(null);
    }

    @Override
    public List<TimeEntry> list() {
        return timeEntries;
    }

    @Override
    public TimeEntry update(long id, TimeEntry timeEntry) {
        if(timeEntries.stream().noneMatch(x -> x.getId() == id)){
            return null;
        }

        timeEntries.removeIf(x -> x.getId() == id);
        TimeEntry entry = new TimeEntry(
                id,
                timeEntry.getProjectId(),
                timeEntry.getUserId(),
                timeEntry.getDate(),
                timeEntry.getHours()
        );

        timeEntries.add(entry);
        return entry;
    }

    @Override
    public void delete(long id) {
        timeEntries.removeIf(x -> x.getId() == id);
    }
}
