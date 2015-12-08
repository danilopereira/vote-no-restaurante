package com.isengardcode.votenorestaurante.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.isengardcode.votenorestaurante.domain.Voter;
import com.isengardcode.votenorestaurante.repository.VoterRepository;
import com.isengardcode.votenorestaurante.web.rest.util.HeaderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Voter.
 */
@RestController
@RequestMapping("/api")
public class VoterResource {

    private final Logger log = LoggerFactory.getLogger(VoterResource.class);

    @Inject
    private VoterRepository voterRepository;

    /**
     * POST  /voters -> Create a new voter.
     */
    @RequestMapping(value = "/voters",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Voter> createVoter(@RequestBody Voter voter) throws URISyntaxException {
        log.debug("REST request to save Voter : {}", voter);
        if (voter.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new voter cannot already have an ID").body(null);
        }
        Voter result = voterRepository.save(voter);
        return ResponseEntity.created(new URI("/api/voters/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("voter", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /voters -> Updates an existing voter.
     */
    @RequestMapping(value = "/voters",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Voter> updateVoter(@RequestBody Voter voter) throws URISyntaxException {
        log.debug("REST request to update Voter : {}", voter);
        if (voter.getId() == null) {
            return createVoter(voter);
        }
        Voter result = voterRepository.save(voter);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("voter", voter.getId().toString()))
            .body(result);
    }

    /**
     * GET  /voters -> get all the voters.
     */
    @RequestMapping(value = "/voters",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Voter> getAllVoters() {
        log.debug("REST request to get all Voters");
        return voterRepository.findAll();
    }

    /**
     * GET  /voters/:id -> get the "id" voter.
     */
    @RequestMapping(value = "/voters/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Voter> getVoter(@PathVariable Long id) {
        log.debug("REST request to get Voter : {}", id);
        return Optional.ofNullable(voterRepository.findOne(id))
            .map(voter -> new ResponseEntity<>(
                voter,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /voters/:id -> delete the "id" voter.
     */
    @RequestMapping(value = "/voters/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteVoter(@PathVariable Long id) {
        log.debug("REST request to delete Voter : {}", id);
        voterRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("voter", id.toString())).build();
    }
}
