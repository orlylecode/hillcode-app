package io.brainwork.hillcode.web.rest;

import io.brainwork.hillcode.HillcodeApp;
import io.brainwork.hillcode.domain.Article;
import io.brainwork.hillcode.repository.ArticleRepository;
import io.brainwork.hillcode.service.ArticleService;
import io.brainwork.hillcode.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static io.brainwork.hillcode.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link ArticleResource} REST controller.
 */
@SpringBootTest(classes = HillcodeApp.class)
public class ArticleResourceIT {

    private static final String DEFAULT_CHEMIN_FICHIER = "AAAAAAAAAA";
    private static final String UPDATED_CHEMIN_FICHIER = "BBBBBBBBBB";

    private static final String DEFAULT_AUTEUR = "AAAAAAAAAA";
    private static final String UPDATED_AUTEUR = "BBBBBBBBBB";

    private static final Instant DEFAULT_DATE_CREATION = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE_CREATION = Instant.now().truncatedTo(ChronoUnit.MILLIS);
    private static final Instant SMALLER_DATE_CREATION = Instant.ofEpochMilli(-1L);

    private static final Instant DEFAULT_DATE_DERNIERE_MODIF = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE_DERNIERE_MODIF = Instant.now().truncatedTo(ChronoUnit.MILLIS);
    private static final Instant SMALLER_DATE_DERNIERE_MODIF = Instant.ofEpochMilli(-1L);

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Integer DEFAULT_JAIME = 1;
    private static final Integer UPDATED_JAIME = 2;
    private static final Integer SMALLER_JAIME = 1 - 1;

    private static final Integer DEFAULT_JAIMEPAS = 1;
    private static final Integer UPDATED_JAIMEPAS = 2;
    private static final Integer SMALLER_JAIMEPAS = 1 - 1;

    private static final Integer DEFAULT_PARTAGE = 1;
    private static final Integer UPDATED_PARTAGE = 2;
    private static final Integer SMALLER_PARTAGE = 1 - 1;

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private ArticleService articleService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restArticleMockMvc;

    private Article article;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ArticleResource articleResource = new ArticleResource(articleService);
        this.restArticleMockMvc = MockMvcBuilders.standaloneSetup(articleResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Article createEntity(EntityManager em) {
        Article article = new Article()
            .cheminFichier(DEFAULT_CHEMIN_FICHIER)
            .auteur(DEFAULT_AUTEUR)
            .dateCreation(DEFAULT_DATE_CREATION)
            .dateDerniereModif(DEFAULT_DATE_DERNIERE_MODIF)
            .description(DEFAULT_DESCRIPTION)
            .jaime(DEFAULT_JAIME)
            .jaimepas(DEFAULT_JAIMEPAS)
            .partage(DEFAULT_PARTAGE);
        return article;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Article createUpdatedEntity(EntityManager em) {
        Article article = new Article()
            .cheminFichier(UPDATED_CHEMIN_FICHIER)
            .auteur(UPDATED_AUTEUR)
            .dateCreation(UPDATED_DATE_CREATION)
            .dateDerniereModif(UPDATED_DATE_DERNIERE_MODIF)
            .description(UPDATED_DESCRIPTION)
            .jaime(UPDATED_JAIME)
            .jaimepas(UPDATED_JAIMEPAS)
            .partage(UPDATED_PARTAGE);
        return article;
    }

    @BeforeEach
    public void initTest() {
        article = createEntity(em);
    }

    @Test
    @Transactional
    public void createArticle() throws Exception {
        int databaseSizeBeforeCreate = articleRepository.findAll().size();

        // Create the Article
        restArticleMockMvc.perform(post("/api/articles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(article)))
            .andExpect(status().isCreated());

        // Validate the Article in the database
        List<Article> articleList = articleRepository.findAll();
        assertThat(articleList).hasSize(databaseSizeBeforeCreate + 1);
        Article testArticle = articleList.get(articleList.size() - 1);
        assertThat(testArticle.getCheminFichier()).isEqualTo(DEFAULT_CHEMIN_FICHIER);
        assertThat(testArticle.getAuteur()).isEqualTo(DEFAULT_AUTEUR);
        assertThat(testArticle.getDateCreation()).isEqualTo(DEFAULT_DATE_CREATION);
        assertThat(testArticle.getDateDerniereModif()).isEqualTo(DEFAULT_DATE_DERNIERE_MODIF);
        assertThat(testArticle.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testArticle.getJaime()).isEqualTo(DEFAULT_JAIME);
        assertThat(testArticle.getJaimepas()).isEqualTo(DEFAULT_JAIMEPAS);
        assertThat(testArticle.getPartage()).isEqualTo(DEFAULT_PARTAGE);
    }

    @Test
    @Transactional
    public void createArticleWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = articleRepository.findAll().size();

        // Create the Article with an existing ID
        article.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restArticleMockMvc.perform(post("/api/articles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(article)))
            .andExpect(status().isBadRequest());

        // Validate the Article in the database
        List<Article> articleList = articleRepository.findAll();
        assertThat(articleList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllArticles() throws Exception {
        // Initialize the database
        articleRepository.saveAndFlush(article);

        // Get all the articleList
        restArticleMockMvc.perform(get("/api/articles?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(article.getId().intValue())))
            .andExpect(jsonPath("$.[*].cheminFichier").value(hasItem(DEFAULT_CHEMIN_FICHIER.toString())))
            .andExpect(jsonPath("$.[*].auteur").value(hasItem(DEFAULT_AUTEUR.toString())))
            .andExpect(jsonPath("$.[*].dateCreation").value(hasItem(DEFAULT_DATE_CREATION.toString())))
            .andExpect(jsonPath("$.[*].dateDerniereModif").value(hasItem(DEFAULT_DATE_DERNIERE_MODIF.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].jaime").value(hasItem(DEFAULT_JAIME)))
            .andExpect(jsonPath("$.[*].jaimepas").value(hasItem(DEFAULT_JAIMEPAS)))
            .andExpect(jsonPath("$.[*].partage").value(hasItem(DEFAULT_PARTAGE)));
    }
    
    @Test
    @Transactional
    public void getArticle() throws Exception {
        // Initialize the database
        articleRepository.saveAndFlush(article);

        // Get the article
        restArticleMockMvc.perform(get("/api/articles/{id}", article.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(article.getId().intValue()))
            .andExpect(jsonPath("$.cheminFichier").value(DEFAULT_CHEMIN_FICHIER.toString()))
            .andExpect(jsonPath("$.auteur").value(DEFAULT_AUTEUR.toString()))
            .andExpect(jsonPath("$.dateCreation").value(DEFAULT_DATE_CREATION.toString()))
            .andExpect(jsonPath("$.dateDerniereModif").value(DEFAULT_DATE_DERNIERE_MODIF.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.jaime").value(DEFAULT_JAIME))
            .andExpect(jsonPath("$.jaimepas").value(DEFAULT_JAIMEPAS))
            .andExpect(jsonPath("$.partage").value(DEFAULT_PARTAGE));
    }

    @Test
    @Transactional
    public void getNonExistingArticle() throws Exception {
        // Get the article
        restArticleMockMvc.perform(get("/api/articles/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateArticle() throws Exception {
        // Initialize the database
        articleService.save(article);

        int databaseSizeBeforeUpdate = articleRepository.findAll().size();

        // Update the article
        Article updatedArticle = articleRepository.findById(article.getId()).get();
        // Disconnect from session so that the updates on updatedArticle are not directly saved in db
        em.detach(updatedArticle);
        updatedArticle
            .cheminFichier(UPDATED_CHEMIN_FICHIER)
            .auteur(UPDATED_AUTEUR)
            .dateCreation(UPDATED_DATE_CREATION)
            .dateDerniereModif(UPDATED_DATE_DERNIERE_MODIF)
            .description(UPDATED_DESCRIPTION)
            .jaime(UPDATED_JAIME)
            .jaimepas(UPDATED_JAIMEPAS)
            .partage(UPDATED_PARTAGE);

        restArticleMockMvc.perform(put("/api/articles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedArticle)))
            .andExpect(status().isOk());

        // Validate the Article in the database
        List<Article> articleList = articleRepository.findAll();
        assertThat(articleList).hasSize(databaseSizeBeforeUpdate);
        Article testArticle = articleList.get(articleList.size() - 1);
        assertThat(testArticle.getCheminFichier()).isEqualTo(UPDATED_CHEMIN_FICHIER);
        assertThat(testArticle.getAuteur()).isEqualTo(UPDATED_AUTEUR);
        assertThat(testArticle.getDateCreation()).isEqualTo(UPDATED_DATE_CREATION);
        assertThat(testArticle.getDateDerniereModif()).isEqualTo(UPDATED_DATE_DERNIERE_MODIF);
        assertThat(testArticle.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testArticle.getJaime()).isEqualTo(UPDATED_JAIME);
        assertThat(testArticle.getJaimepas()).isEqualTo(UPDATED_JAIMEPAS);
        assertThat(testArticle.getPartage()).isEqualTo(UPDATED_PARTAGE);
    }

    @Test
    @Transactional
    public void updateNonExistingArticle() throws Exception {
        int databaseSizeBeforeUpdate = articleRepository.findAll().size();

        // Create the Article

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restArticleMockMvc.perform(put("/api/articles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(article)))
            .andExpect(status().isBadRequest());

        // Validate the Article in the database
        List<Article> articleList = articleRepository.findAll();
        assertThat(articleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteArticle() throws Exception {
        // Initialize the database
        articleService.save(article);

        int databaseSizeBeforeDelete = articleRepository.findAll().size();

        // Delete the article
        restArticleMockMvc.perform(delete("/api/articles/{id}", article.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Article> articleList = articleRepository.findAll();
        assertThat(articleList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Article.class);
        Article article1 = new Article();
        article1.setId(1L);
        Article article2 = new Article();
        article2.setId(article1.getId());
        assertThat(article1).isEqualTo(article2);
        article2.setId(2L);
        assertThat(article1).isNotEqualTo(article2);
        article1.setId(null);
        assertThat(article1).isNotEqualTo(article2);
    }
}
