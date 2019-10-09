package io.brainwork.hillcode.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

/**
 * A Article.
 */
@Entity
@Table(name = "article")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Article implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "chemin_fichier")
    private String cheminFichier;

    @Column(name = "auteur")
    private String auteur;

    @Column(name = "date_creation")
    private Instant dateCreation;

    @Column(name = "date_derniere_modif")
    private Instant dateDerniereModif;

    @Column(name = "description")
    private String description;

    @Column(name = "jaime")
    private Integer jaime;

    @Column(name = "jaimepas")
    private Integer jaimepas;

    @Column(name = "partage")
    private Integer partage;

    @OneToMany(mappedBy = "article")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Commentaire> commentaires = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCheminFichier() {
        return cheminFichier;
    }

    public Article cheminFichier(String cheminFichier) {
        this.cheminFichier = cheminFichier;
        return this;
    }

    public void setCheminFichier(String cheminFichier) {
        this.cheminFichier = cheminFichier;
    }

    public String getAuteur() {
        return auteur;
    }

    public Article auteur(String auteur) {
        this.auteur = auteur;
        return this;
    }

    public void setAuteur(String auteur) {
        this.auteur = auteur;
    }

    public Instant getDateCreation() {
        return dateCreation;
    }

    public Article dateCreation(Instant dateCreation) {
        this.dateCreation = dateCreation;
        return this;
    }

    public void setDateCreation(Instant dateCreation) {
        this.dateCreation = dateCreation;
    }

    public Instant getDateDerniereModif() {
        return dateDerniereModif;
    }

    public Article dateDerniereModif(Instant dateDerniereModif) {
        this.dateDerniereModif = dateDerniereModif;
        return this;
    }

    public void setDateDerniereModif(Instant dateDerniereModif) {
        this.dateDerniereModif = dateDerniereModif;
    }

    public String getDescription() {
        return description;
    }

    public Article description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getJaime() {
        return jaime;
    }

    public Article jaime(Integer jaime) {
        this.jaime = jaime;
        return this;
    }

    public void setJaime(Integer jaime) {
        this.jaime = jaime;
    }

    public Integer getJaimepas() {
        return jaimepas;
    }

    public Article jaimepas(Integer jaimepas) {
        this.jaimepas = jaimepas;
        return this;
    }

    public void setJaimepas(Integer jaimepas) {
        this.jaimepas = jaimepas;
    }

    public Integer getPartage() {
        return partage;
    }

    public Article partage(Integer partage) {
        this.partage = partage;
        return this;
    }

    public void setPartage(Integer partage) {
        this.partage = partage;
    }

    public Set<Commentaire> getCommentaires() {
        return commentaires;
    }

    public Article commentaires(Set<Commentaire> commentaires) {
        this.commentaires = commentaires;
        return this;
    }

    public Article addCommentaires(Commentaire commentaire) {
        this.commentaires.add(commentaire);
        commentaire.setArticle(this);
        return this;
    }

    public Article removeCommentaires(Commentaire commentaire) {
        this.commentaires.remove(commentaire);
        commentaire.setArticle(null);
        return this;
    }

    public void setCommentaires(Set<Commentaire> commentaires) {
        this.commentaires = commentaires;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Article)) {
            return false;
        }
        return id != null && id.equals(((Article) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Article{" +
            "id=" + getId() +
            ", cheminFichier='" + getCheminFichier() + "'" +
            ", auteur='" + getAuteur() + "'" +
            ", dateCreation='" + getDateCreation() + "'" +
            ", dateDerniereModif='" + getDateDerniereModif() + "'" +
            ", description='" + getDescription() + "'" +
            ", jaime=" + getJaime() +
            ", jaimepas=" + getJaimepas() +
            ", partage=" + getPartage() +
            "}";
    }
}
