package io.brainwork.hillcode.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A Commentaire.
 */
@Entity
@Table(name = "commentaire")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Commentaire implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "date_creation")
    private Integer dateCreation;

    @Column(name = "commentaire")
    private String commentaire;

    @ManyToOne
    @JsonIgnoreProperties("commentaires")
    private Article article;

    @ManyToOne
    @JsonIgnoreProperties("commentaires")
    private Utilisateur utilisateur;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getDateCreation() {
        return dateCreation;
    }

    public Commentaire dateCreation(Integer dateCreation) {
        this.dateCreation = dateCreation;
        return this;
    }

    public void setDateCreation(Integer dateCreation) {
        this.dateCreation = dateCreation;
    }

    public String getCommentaire() {
        return commentaire;
    }

    public Commentaire commentaire(String commentaire) {
        this.commentaire = commentaire;
        return this;
    }

    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
    }

    public Article getArticle() {
        return article;
    }

    public Commentaire article(Article article) {
        this.article = article;
        return this;
    }

    public void setArticle(Article article) {
        this.article = article;
    }

    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    public Commentaire utilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
        return this;
    }

    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Commentaire)) {
            return false;
        }
        return id != null && id.equals(((Commentaire) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Commentaire{" +
            "id=" + getId() +
            ", dateCreation=" + getDateCreation() +
            ", commentaire='" + getCommentaire() + "'" +
            "}";
    }
}
