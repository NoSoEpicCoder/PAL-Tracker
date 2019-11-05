package io.pivotal.pal.tracker;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TimeEntryController {

    private TimeEntryRepository timeEntryRepository;

    public TimeEntryController(TimeEntryRepository timeEntryRepository) {
        this.timeEntryRepository = timeEntryRepository;
    }

    @PostMapping("/time-entries")
    public ResponseEntity create(@RequestBody TimeEntry timeEntryToCreate) {
        TimeEntry createdTimeEntry = timeEntryRepository.create(timeEntryToCreate);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdTimeEntry);
    }

    @GetMapping("/time-entries/{id}")
    public ResponseEntity<TimeEntry> read(@PathVariable long id) {
        System.out.println("ID: " + id);
        TimeEntry readTimeEntry = timeEntryRepository.find(id);
        if (readTimeEntry != null) {
            return ResponseEntity.status(HttpStatus.OK).body(readTimeEntry);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @GetMapping("/time-entries")
    public ResponseEntity<List<TimeEntry>> list() {
        System.out.println("In List");
        List<TimeEntry> timeEntryList = timeEntryRepository.list();
        return ResponseEntity.status(HttpStatus.OK).body(timeEntryList);
    }

    @PutMapping("/time-entries/{id}")
    public ResponseEntity update(@PathVariable long id, @RequestBody TimeEntry timeEntry) {
        TimeEntry updatedTimeEntry = timeEntryRepository.update(id, timeEntry);
        if (updatedTimeEntry != null) {
            return ResponseEntity.status(HttpStatus.OK).body(updatedTimeEntry);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @DeleteMapping("/time-entries/{id}")
    public ResponseEntity delete(@PathVariable long id) {
        timeEntryRepository.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
    }
}
