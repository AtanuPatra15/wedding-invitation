package com.wedinvitation.wedding_backend;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/rsvp")
@CrossOrigin(origins = "*")
public class RsvpController {

    private final RsvpRepository rsvpRepository;

    public RsvpController(RsvpRepository rsvpRepository) {
        this.rsvpRepository = rsvpRepository;
    }

    @PostMapping
    public Rsvp createRsvp(@RequestBody Rsvp rsvp) {
        if (rsvp.getGuestName() == null || rsvp.getGuestName().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Guest name is required");
        }
        if (rsvp.getAttending() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Attendance choice is required");
        }

        rsvp.setGuestName(rsvp.getGuestName().trim());
        rsvp.setAdultCount(normalizeCount(rsvp.getAdultCount()));
        rsvp.setChildCount(normalizeCount(rsvp.getChildCount()));

        if (!rsvp.getAttending()) {
            rsvp.setAdultCount(0);
            rsvp.setChildCount(0);
            rsvp.setFoodPreference(null);
        }

        return rsvpRepository.save(rsvp);
    }

    @GetMapping
    public List<Rsvp> getAllRsvps() {
        return rsvpRepository.findAll();
    }

    private int normalizeCount(Integer count) {
        if (count == null) {
            return 0;
        }
        return Math.max(0, Math.min(count, 50));
    }
}
