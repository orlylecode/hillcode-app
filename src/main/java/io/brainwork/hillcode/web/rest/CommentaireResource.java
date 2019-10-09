package io.brainwork.hillcode.web.rest;

import io.brainwork.hillcode.domain.Commentaire;
import io.brainwork.hillcode.service.CommentaireService;
import io.brainwork.hillcode.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link io.brainwork.hillcode.domain.Commentaire}.
 */
@RestController
@RequestMapping("/api")
public class CommentaireResource {

    private final Logger log = LoggerFactory.getLogger(CommentaireResource.class);

    private static final String ENTITY_NAME = "commentaire";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CommentaireService commentaireService;

    public CommentaireResource(CommentaireService commentaireService) {
        this.commentaireService = commentaireService;
    }

    /**
     * {@code POST  /commentaires} : Create a new commentaire.
     *
     * @param commentaire the commentaire to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new commentaire, or with status {@code 400 (Bad Request)} if the commentaire has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/commentaires")
    public ResponseEntity<Commentaire> createCommentaire(@RequestBody Commentaire commentaire) throws URISyntaxException {
        log.debug("REST request to save Commentaire : {}", commentaire);
        if (commentaire.getId() != null) {
            throw new BadRequestAlertException("A new commentaire cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Commentaire result = commentaireService.save(commentaire);
        return ResponseEntity.created(new URI("/api/commentaires/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /commentaires} : Updates an existing commentaire.
     *
     * @param commentaire the commentaire to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated commentaire,
     * or with status {@code 400 (Bad Request)} if the commentaire is not valid,
     * or with status {@code 500 (Internal Server Error)} if the commentaire couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/commentaires")
    public ResponseEntity<Commentaire> updateCommentaire(@RequestBody Commentaire commentaire) throws URISyntaxException {
        log.debug("REST request to update Commentaire : {}", commentaire);
        if (commentaire.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Commentaire result = commentaireService.save(commentaire);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, commentaire.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /commentaires} : get all the commentaires.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of commentaires in body.
     */
    @GetMapping("/commentaires")
    public ResponseEntity<List<Commentaire>> getAllCommentaires(Pageable pageable) {
        log.debug("REST request to get a page of Commentaires");
        Page<Commentaire> page = commentaireService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /commentaires/:id} : get the "id" commentaire.
     *
     * @param id the id of the commentaire to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the commentaire, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/commentaires/{id}")
    public ResponseEntity<Commentaire> getCommentaire(@PathVariable Long id) {
        log.debug("REST request to get Commentaire : {}", id);
        Optional<Commentaire> commentaire = commentaireService.findOne(id);
        return ResponseUtil.wrapOrNotFound(commentaire);
    }

    /**
     * {@code DELETE  /commentaires/:id} : delete the "id" commentaire.
     *
     * @param id the id of the commentaire to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/commentaires/{id}")
    public ResponseEntity<Void> deleteCommentaire(@PathVariable Long id) {
        log.debug("REST request to delete Commentaire : {}", id);
        commentaireService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
